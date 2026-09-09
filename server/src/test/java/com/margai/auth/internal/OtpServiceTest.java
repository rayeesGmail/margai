package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.SignIn;
import com.margai.account.api.UserSummary;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.OtpException;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.UserRole;
import com.margai.common.api.ValidationException;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.net.InetAddress;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * TECH_PLAN §3.2, §3.4, §3.7, §9.1 as unit rules on a fixed clock: channel gating, cooldown,
 * hourly cap with the right wait, store-then-send with cleanup on failure, wrong codes and
 * attempts, dead challenges, sign-in and tokens on a match — and not one code in the module's log.
 */
class OtpServiceTest {

    private static final Instant NOW = Instant.parse("2026-09-08T10:00:00Z");
    private static final LoginIdentifier.Email EMAIL = new LoginIdentifier.Email("student@example.com");
    private static final LoginIdentifier.Phone PHONE = new LoginIdentifier.Phone("+919876543210");
    private static final byte[] PEPPER = "unit-test-pepper-of-32-bytes-len".getBytes();

    private final OtpChallengeRepository challenges = mock(OtpChallengeRepository.class);
    private final OtpSender sender = mock(OtpSender.class);
    private final Accounts accounts = mock(Accounts.class);
    private final TokenService tokens = mock(TokenService.class);
    private final SimpleMeterRegistry meters = new SimpleMeterRegistry();
    private final ListAppender<ILoggingEvent> authLog = new ListAppender<>();

    private OtpService service;

    @BeforeEach
    void setUp() {
        service = service(Set.of(OtpChannel.email));
        when(challenges.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        authLog.start();
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).addAppender(authLog);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).detachAppender(authLog);
    }

    @Test
    void aDisabledChannelIsAFieldError() {
        assertThatThrownBy(() -> service.request(PHONE, null, Language.en))
                .isInstanceOf(ValidationException.class)
                .satisfies(failure -> assertThat(((ValidationException) failure).details())
                        .containsEntry("phone", "channel.unavailable"));
        verify(challenges, never()).save(any());
        verify(sender, never()).send(any());

        assertThat(service(Set.of(OtpChannel.email, OtpChannel.sms)).request(PHONE, null, Language.en).channel())
                .isEqualTo(OtpChannel.sms);
    }

    @Test
    void aRequestInsideTheCooldownIsRateLimitedWithTheRemainingWait() {
        when(challenges.findFirstByDestinationAndPurposeOrderByCreatedAtDesc(EMAIL.address(), OtpPurpose.login))
                .thenReturn(Optional.of(challengeCreatedAt(NOW.minusSeconds(10))));

        assertThatThrownBy(() -> service.request(EMAIL, null, Language.en))
                .isInstanceOf(RateLimitedException.class)
                .satisfies(failure -> {
                    assertThat(((RateLimitedException) failure).code()).isEqualTo(ErrorCode.OTP_RATE_LIMITED);
                    assertThat(((RateLimitedException) failure).retryAfterSeconds()).isEqualTo(20);
                });
        verify(sender, never()).send(any());
    }

    @Test
    void theHourlyCapWaitsUntilTheOldestRequestLeavesTheWindow() {
        when(challenges.findFirstByDestinationAndPurposeOrderByCreatedAtDesc(EMAIL.address(), OtpPurpose.login))
                .thenReturn(Optional.of(challengeCreatedAt(NOW.minusSeconds(120))));
        when(challenges.countByDestinationAndPurposeAndCreatedAtAfter(EMAIL.address(), OtpPurpose.login, NOW.minus(Duration.ofHours(1))))
                .thenReturn(3L);
        when(challenges.findFirstByDestinationAndPurposeAndCreatedAtAfterOrderByCreatedAtAsc(EMAIL.address(), OtpPurpose.login,
                NOW.minus(Duration.ofHours(1)))).thenReturn(Optional.of(challengeCreatedAt(NOW.minus(Duration.ofMinutes(50)))));

        assertThatThrownBy(() -> service.request(EMAIL, null, Language.en))
                .isInstanceOf(RateLimitedException.class)
                .satisfies(failure -> assertThat(((RateLimitedException) failure).retryAfterSeconds()).isEqualTo(600));
    }

    @Test
    void aFreshRequestStoresAHashSendsTheCodeAndAnswersWithTheChallenge() throws Exception {
        OtpRequested requested = service.request(EMAIL, InetAddress.getByName("203.0.113.9"), Language.hi);

        ArgumentCaptor<OtpChallenge> saved = ArgumentCaptor.forClass(OtpChallenge.class);
        verify(challenges).save(saved.capture());
        ArgumentCaptor<OtpDelivery> delivered = ArgumentCaptor.forClass(OtpDelivery.class);
        verify(sender).send(delivered.capture());

        OtpChallenge row = saved.getValue();
        OtpDelivery delivery = delivered.getValue();
        assertThat(requested.channel()).isEqualTo(OtpChannel.email);
        assertThat(requested.resendAfterSeconds()).isEqualTo(30);
        assertThat(requested.challengeId()).isEqualTo(row.getId());
        assertThat(row.getDestination()).isEqualTo("student@example.com");
        assertThat(row.getExpiresAt()).isEqualTo(NOW.plus(Duration.ofMinutes(5)));
        assertThat(row.getRequestIp()).isEqualTo(InetAddress.getByName("203.0.113.9"));
        assertThat(delivery.code()).matches("\\d{6}");
        assertThat(delivery.language()).isEqualTo(Language.hi);
        assertThat(delivery.destination()).isEqualTo("student@example.com");
        assertThat(row.getCodeHash()).matches("[0-9a-f]{64}").doesNotContain(delivery.code());
        assertThat(OtpCodes.matches(row.getCodeHash(), PEPPER, row.getId(), delivery.code())).isTrue();
        assertThat(meters.counter(OtpService.SENT_METRIC, "channel", "email").count()).isEqualTo(1.0);
    }

    @Test
    void aDeliveryFailureRemovesTheChallengeAgain() {
        doThrow(new OtpSendException("MessageRejected")).when(sender).send(any());

        assertThatThrownBy(() -> service.request(EMAIL, null, Language.en)).isInstanceOf(OtpSendException.class);

        ArgumentCaptor<OtpChallenge> saved = ArgumentCaptor.forClass(OtpChallenge.class);
        verify(challenges).save(saved.capture());
        verify(challenges).deleteById(saved.getValue().getId());
        assertThat(meters.counter(OtpService.SEND_FAILED_METRIC, "channel", "email").count()).isEqualTo(1.0);
        assertThat(meters.find(OtpService.SENT_METRIC).counter()).isNull();
    }

    @Test
    void aWrongCodeCountsAnAttemptAndReportsWhatIsLeft() {
        OtpChallenge challenge = liveChallenge("111111");
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));

        assertThatThrownBy(() -> service.verify(challenge.getId(), "222222", "dev", Language.en))
                .isInstanceOf(OtpException.class)
                .satisfies(failure -> {
                    assertThat(((OtpException) failure).code()).isEqualTo(ErrorCode.OTP_INVALID);
                    assertThat(((OtpException) failure).details()).containsEntry("attempts_left", 4);
                });
        assertThat(challenge.getAttempts()).isEqualTo((short) 1);
        verify(challenges).save(challenge);
        verify(accounts, never()).signIn(any(), any());
        assertThat(meters.counter(OtpService.FAILED_METRIC, "channel", "email").count())
                .as("otp.failed carries the channel like the other three (D11)").isEqualTo(1.0);
    }

    @Test
    void aCleanVerifyIsAFirstAttemptAndOneAfterAWrongCodeIsNot() {
        UserSummary user = new UserSummary(UUID.randomUUID(), null, EMAIL.address(), Language.en, UserRole.student, null);
        when(accounts.signIn(EMAIL, Language.en)).thenReturn(new SignIn(user, false));
        when(tokens.issue(any(), any())).thenReturn(new TokenPair("a", "r", 900));
        OtpChallenge clean = liveChallenge("111111");
        when(challenges.lockById(clean.getId())).thenReturn(Optional.of(clean));
        OtpChallenge retried = liveChallenge("222222");
        when(challenges.lockById(retried.getId())).thenReturn(Optional.of(retried));

        service.verify(clean.getId(), "111111", "dev", Language.en);
        assertThatThrownBy(() -> service.verify(retried.getId(), "999999", "dev", Language.en)).isInstanceOf(OtpException.class);
        service.verify(retried.getId(), "222222", "dev", Language.en);

        // SPEC §11 "OTP success ≥ 98% first attempt": the tag makes that ratio computable from the counters.
        assertThat(meters.counter(OtpService.VERIFIED_METRIC, "channel", "email", "first_attempt", "true").count())
                .isEqualTo(1.0);
        assertThat(meters.counter(OtpService.VERIFIED_METRIC, "channel", "email", "first_attempt", "false").count())
                .isEqualTo(1.0);
        assertThat(meters.find(OtpService.VERIFIED_METRIC).counters()).hasSize(2);
    }

    @Test
    void theFifthWrongCodeExhaustsTheChallengeAndLaterTriesAreExpired() {
        OtpChallenge challenge = liveChallenge("111111");
        for (int i = 0; i < 4; i++) {
            challenge.recordFailedAttempt(NOW);
        }
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));

        assertThatThrownBy(() -> service.verify(challenge.getId(), "000000", "dev", Language.en))
                .isInstanceOf(OtpException.class)
                .satisfies(failure -> {
                    assertThat(((OtpException) failure).code()).isEqualTo(ErrorCode.OTP_INVALID);
                    assertThat(((OtpException) failure).details()).containsEntry("attempts_left", 0);
                });
        assertThatThrownBy(() -> service.verify(challenge.getId(), "111111", "dev", Language.en))
                .as("even the right code is refused once the challenge is exhausted")
                .isInstanceOf(OtpException.class)
                .satisfies(failure -> assertThat(((OtpException) failure).code()).isEqualTo(ErrorCode.OTP_EXPIRED));
    }

    @Test
    void unknownUsedAndExpiredChallengesAreExpired() {
        UUID unknown = UUID.randomUUID();
        when(challenges.lockById(unknown)).thenReturn(Optional.empty());
        OtpChallenge used = liveChallenge("111111");
        used.markVerified(NOW.minusSeconds(60));
        when(challenges.lockById(used.getId())).thenReturn(Optional.of(used));
        OtpChallenge stale = new OtpChallenge(UUID.randomUUID(), OtpChannel.email, EMAIL.address(), OtpPurpose.login,
                OtpCodes.hash(PEPPER, UUID.randomUUID(), "111111"), NOW.minusSeconds(1), null, NOW.minusSeconds(301));
        when(challenges.lockById(stale.getId())).thenReturn(Optional.of(stale));

        for (UUID id : List.of(unknown, used.getId(), stale.getId())) {
            assertThatThrownBy(() -> service.verify(id, "111111", "dev", Language.en))
                    .isInstanceOf(OtpException.class)
                    .satisfies(failure -> assertThat(((OtpException) failure).code()).isEqualTo(ErrorCode.OTP_EXPIRED));
        }
        verify(accounts, never()).signIn(any(), any());
    }

    @Test
    void theRightCodeSignsInAndOpensATokenFamily() {
        OtpChallenge challenge = liveChallenge("424242");
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));
        UserSummary user = new UserSummary(UUID.randomUUID(), null, EMAIL.address(), Language.en, UserRole.student, null);
        when(accounts.signIn(EMAIL, Language.en)).thenReturn(new SignIn(user, true));
        TokenPair pair = new TokenPair("access", "refresh", 900);
        when(tokens.issue(user, "Pixel")).thenReturn(pair);

        OtpVerified verified = service.verify(challenge.getId(), " 424242 ", "Pixel", Language.en);

        assertThat(verified.tokens()).isEqualTo(pair);
        assertThat(verified.user()).isEqualTo(user);
        assertThat(verified.isNewUser()).isTrue();
        assertThat(challenge.isVerified()).isTrue();
        assertThat(challenge.getVerifiedAt()).isEqualTo(NOW);
        assertThat(meters.counter(OtpService.VERIFIED_METRIC, "channel", "email", "first_attempt", "true").count())
                .isEqualTo(1.0);
    }

    @Test
    void aPhoneChallengeSignsInByPhone() {
        OtpChallenge challenge = new OtpChallenge(UUID.randomUUID(), OtpChannel.sms, PHONE.e164(), OtpPurpose.login, "",
                NOW.plusSeconds(300), null, NOW);
        ReflectionTestUtils.setField(challenge, "codeHash", OtpCodes.hash(PEPPER, challenge.getId(), "777777"));
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));
        UserSummary user = new UserSummary(UUID.randomUUID(), PHONE.e164(), null, Language.en, UserRole.student, null);
        when(accounts.signIn(PHONE, Language.en)).thenReturn(new SignIn(user, false));
        when(tokens.issue(eq(user), anyString())).thenReturn(new TokenPair("a", "r", 900));

        assertThat(service.verify(challenge.getId(), "777777", "dev", Language.en).isNewUser()).isFalse();
        verify(accounts).signIn(PHONE, Language.en);
    }

    @Test
    void theCallersLanguageIsTheSuggestionForANewAccount() {
        OtpChallenge challenge = liveChallenge("424242");
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));
        UserSummary user = new UserSummary(UUID.randomUUID(), null, EMAIL.address(), Language.hi, UserRole.student, null);
        when(accounts.signIn(EMAIL, Language.hi)).thenReturn(new SignIn(user, true));
        when(tokens.issue(eq(user), anyString())).thenReturn(new TokenPair("a", "r", 900));

        assertThat(service.verify(challenge.getId(), "424242", "dev", Language.hi).user().language()).isEqualTo(Language.hi);
        verify(accounts).signIn(EMAIL, Language.hi);
    }

    @Test
    void theServiceNeverLogsACode() {
        service.request(EMAIL, null, Language.en);
        ArgumentCaptor<OtpDelivery> delivered = ArgumentCaptor.forClass(OtpDelivery.class);
        verify(sender).send(delivered.capture());
        String code = delivered.getValue().code();
        OtpChallenge challenge = liveChallenge(code);
        when(challenges.lockById(challenge.getId())).thenReturn(Optional.of(challenge));
        UserSummary user = new UserSummary(UUID.randomUUID(), null, EMAIL.address(), Language.en, UserRole.student, null);
        when(accounts.signIn(EMAIL, Language.en)).thenReturn(new SignIn(user, true));
        when(tokens.issue(any(), any())).thenReturn(new TokenPair("a", "r", 900));
        assertThatThrownBy(() -> service.verify(challenge.getId(), "000000", "dev", Language.en)).isInstanceOf(OtpException.class);
        service.verify(challenge.getId(), code, "dev", Language.en);

        assertThat(authLog.list).isNotEmpty();
        assertThat(authLog.list).extracting(ILoggingEvent::getFormattedMessage)
                .noneMatch(line -> line.contains(code))
                .noneMatch(line -> line.contains("student@example.com"));
    }

    private OtpService service(Set<OtpChannel> channels) {
        AuthProperties properties = new AuthProperties(
                new AuthProperties.Jwt("", "", Duration.ofMinutes(15), Duration.ofDays(30)),
                new AuthProperties.Otp("", Duration.ofMinutes(5), 5, Duration.ofSeconds(30), 6, channels,
                        AuthProperties.Sender.log, "", "ap-south-1"),
                Duration.ofMinutes(2));
        AuthKeys keys = new AuthKeys(new SecretKeySpec(new byte[32], "HmacSHA256"), null, PEPPER);
        return new OtpService(challenges, sender, accounts, tokens, properties, new RateLimitProperties(3, 10, 60, 60),
                keys, new IstClock(Clock.fixed(NOW, ZoneOffset.UTC)), meters);
    }

    private static OtpChallenge liveChallenge(String code) {
        UUID id = UUID.randomUUID();
        return new OtpChallenge(id, OtpChannel.email, EMAIL.address(), OtpPurpose.login, OtpCodes.hash(PEPPER, id, code),
                NOW.plusSeconds(300), null, NOW);
    }

    private static OtpChallenge challengeCreatedAt(Instant createdAt) {
        OtpChallenge challenge = liveChallenge("000000");
        ReflectionTestUtils.setField(challenge, "createdAt", createdAt);
        return challenge;
    }
}
