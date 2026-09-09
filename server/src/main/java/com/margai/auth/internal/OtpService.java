package com.margai.auth.internal;

import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.SignIn;
import com.margai.common.api.ApiException;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.OtpException;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.ValidationException;
import io.micrometer.core.instrument.MeterRegistry;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OTP login (TECH_PLAN §3.2, §3.4, §3.7, §9.1). {@link #request} refuses a channel that is not
 * enabled, a destination inside its 30-second cooldown or over its hourly cap, then stores a
 * challenge and sends the code; a delivery failure removes the challenge again so the cap stays
 * fair. {@link #verify} treats an unknown, used, expired or exhausted challenge as
 * {@code OTP_EXPIRED}, counts a wrong code against the challenge and reports the attempts left,
 * and on a match signs the student in and opens a token family. This class never logs a code.
 */
@Service
public class OtpService {

    static final String SENT_METRIC = "otp.sent";
    static final String VERIFIED_METRIC = "otp.verified";
    static final String FAILED_METRIC = "otp.failed";
    static final String SEND_FAILED_METRIC = "otp.send_failed";
    /** Reason code on the identifier field when its channel is not enabled (TECH_PLAN §3.3 details). */
    static final String CHANNEL_UNAVAILABLE = "channel.unavailable";

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private static final Duration CAP_WINDOW = Duration.ofHours(1);

    private final OtpChallengeRepository challenges;
    private final OtpSender sender;
    private final Accounts accounts;
    private final TokenService tokens;
    private final AuthProperties.Otp policy;
    private final int hourlyCapPerDestination;
    private final byte[] pepper;
    private final IstClock clock;
    private final MeterRegistry meters;

    OtpService(OtpChallengeRepository challenges, OtpSender sender, Accounts accounts, TokenService tokens,
            AuthProperties properties, RateLimitProperties limits, AuthKeys keys, IstClock clock, MeterRegistry meters) {
        this.challenges = challenges;
        this.sender = sender;
        this.accounts = accounts;
        this.tokens = tokens;
        this.policy = properties.otp();
        this.hourlyCapPerDestination = limits.otpRequestPerDestinationHourly();
        this.pepper = keys.otpPepper();
        this.clock = clock;
        this.meters = meters;
    }

    /** Not transactional on purpose: the challenge commits before the code leaves the process. */
    public OtpRequested request(LoginIdentifier identifier, InetAddress clientAddress, Language language) {
        OtpChannel channel = Identifiers.channelOf(identifier);
        if (!policy.allows(channel)) {
            throw ValidationException.of(channel == OtpChannel.sms ? "phone" : "email", CHANNEL_UNAVAILABLE);
        }
        Instant now = clock.now();
        String destination = identifier.value();
        enforceCooldown(destination, now);
        enforceHourlyCap(destination, now);

        UUID challengeId = UUID.randomUUID();
        String code = OtpCodes.generate(policy.codeLength());
        challenges.save(new OtpChallenge(challengeId, channel, destination, OtpPurpose.login,
                OtpCodes.hash(pepper, challengeId, code), now.plus(policy.ttl()), clientAddress, now));
        try {
            sender.send(new OtpDelivery(channel, destination, code, language, policy.ttl()));
        } catch (OtpSendException failed) {
            challenges.deleteById(challengeId);
            meters.counter(SEND_FAILED_METRIC, "channel", channel.name()).increment();
            log.warn("otp delivery failed to {} via {}: {}", Identifiers.mask(identifier), channel, failed.getMessage());
            throw failed;
        }
        meters.counter(SENT_METRIC, "channel", channel.name()).increment();
        log.info("otp sent to {} via {} (challenge {})", Identifiers.mask(identifier), channel, challengeId);
        return new OtpRequested(challengeId, policy.resendCooldown().toSeconds(), channel);
    }

    /**
     * {@code suggested} is the caller's {@code Accept-Language}: the language a brand-new account
     * starts in (SPEC §5; DECISIONS D10). An existing account keeps its own.
     */
    @Transactional(noRollbackFor = ApiException.class)
    public OtpVerified verify(UUID challengeId, String code, String deviceLabel, Language suggested) {
        Instant now = clock.now();
        // Row lock: parallel guesses at one challenge serialise, so attempts can never pass the cap.
        OtpChallenge challenge = challenges.lockById(challengeId).orElseThrow(OtpException::expired);
        if (challenge.getPurpose() != OtpPurpose.login || challenge.isVerified() || challenge.isExpired(now)
                || challenge.isExhausted(policy.maxAttempts())) {
            throw OtpException.expired();
        }
        if (!OtpCodes.matches(challenge.getCodeHash(), pepper, challenge.getId(), code)) {
            int used = challenge.recordFailedAttempt(now);
            challenges.save(challenge);
            meters.counter(FAILED_METRIC).increment();
            throw OtpException.invalid(Math.max(0, policy.maxAttempts() - used));
        }
        challenge.markVerified(now);
        challenges.save(challenge);

        SignIn signIn = accounts.signIn(Identifiers.of(challenge.getChannel(), challenge.getDestination()), suggested);
        TokenPair pair = tokens.issue(signIn.user(), deviceLabel);
        meters.counter(VERIFIED_METRIC, "channel", challenge.getChannel().name()).increment();
        log.info("otp verified for {} (challenge {}, new user: {})",
                Identifiers.mask(challenge.getChannel(), challenge.getDestination()), challengeId, signIn.isNew());
        return new OtpVerified(pair, signIn.user(), signIn.isNew());
    }

    private void enforceCooldown(String destination, Instant now) {
        Optional<OtpChallenge> latest = challenges.findFirstByDestinationAndPurposeOrderByCreatedAtDesc(destination,
                OtpPurpose.login);
        if (latest.isPresent() && latest.get().getCreatedAt() != null) {
            Duration age = Duration.between(latest.get().getCreatedAt(), now);
            if (age.compareTo(policy.resendCooldown()) < 0) {
                throw RateLimitedException.otp(policy.resendCooldown().minus(age));
            }
        }
    }

    private void enforceHourlyCap(String destination, Instant now) {
        Instant windowStart = now.minus(CAP_WINDOW);
        long recent = challenges.countByDestinationAndPurposeAndCreatedAtAfter(destination, OtpPurpose.login, windowStart);
        if (recent >= hourlyCapPerDestination) {
            Instant oldestInWindow = challenges
                    .findFirstByDestinationAndPurposeAndCreatedAtAfterOrderByCreatedAtAsc(destination, OtpPurpose.login,
                            windowStart)
                    .map(OtpChallenge::getCreatedAt)
                    .orElse(now);
            throw RateLimitedException.otp(Duration.between(now, oldestInWindow.plus(CAP_WINDOW)));
        }
    }
}
