package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.margai.MutableClock;
import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * The D11 delivery report (TECH_PLAN §10.2; SPEC §11 "OTP success ≥ 98% first attempt"): the
 * §10.2 counters read back per channel, the repository's count of codes that died unverified over
 * the same window — the process lifetime, {@code since} — and the two ratios, rounded to three
 * decimals and absent when nothing was sent. Every channel is listed, in enum order.
 */
class OtpMetricsServiceTest {

    private static final Instant STARTED = Instant.parse("2026-09-09T04:30:00Z");

    private final SimpleMeterRegistry meters = new SimpleMeterRegistry();
    private final OtpChallengeRepository challenges = mock(OtpChallengeRepository.class);
    private final MutableClock clock = new MutableClock(STARTED);
    private final OtpMetricsService service = new OtpMetricsService(meters, challenges, new IstClock(clock));

    @Test
    void theReportReadsTheCountersAndTheDeadCodesSinceTheProcessStarted() {
        count(OtpService.SENT_METRIC, "email", 5);
        count(OtpService.SEND_FAILED_METRIC, "email", 1);
        countVerified("email", true, 3);
        countVerified("email", false, 1);
        count(OtpService.FAILED_METRIC, "email", 2);
        clock.advance(Duration.ofHours(1));
        when(challenges.countExpiredUnverified(eq(OtpChannel.email), eq(OtpPurpose.login), eq(STARTED),
                eq(STARTED.plus(Duration.ofHours(1))))).thenReturn(1L);

        OtpDeliveryReport report = service.report();

        assertThat(report.since()).isEqualTo(STARTED);
        assertThat(report.channels()).extracting(OtpChannelReport::channel).containsExactly("sms", "email");
        OtpChannelReport email = report.channels().get(1);
        assertThat(email.sent()).isEqualTo(5);
        assertThat(email.sendFailed()).isEqualTo(1);
        assertThat(email.verified()).isEqualTo(4);
        assertThat(email.verifiedFirstAttempt()).isEqualTo(3);
        assertThat(email.wrongCodes()).isEqualTo(2);
        assertThat(email.expiredUnverified()).isEqualTo(1);
        assertThat(email.successRate()).isEqualTo(0.8);
        assertThat(email.firstAttemptRate()).isEqualTo(0.6);
    }

    @Test
    void aChannelWithoutTrafficIsAllZerosAndNoRates() {
        when(challenges.countExpiredUnverified(any(), any(), any(), any())).thenReturn(0L);

        OtpChannelReport sms = service.report().channels().get(0);

        assertThat(sms.channel()).isEqualTo("sms");
        assertThat(sms.sent()).isZero();
        assertThat(sms.sendFailed()).isZero();
        assertThat(sms.verified()).isZero();
        assertThat(sms.verifiedFirstAttempt()).isZero();
        assertThat(sms.wrongCodes()).isZero();
        assertThat(sms.expiredUnverified()).isZero();
        assertThat(sms.successRate()).as("no division by zero, no fake 0%").isNull();
        assertThat(sms.firstAttemptRate()).isNull();
    }

    @Test
    void ratesAreRoundedToThreeDecimals() {
        count(OtpService.SENT_METRIC, "email", 3);
        countVerified("email", true, 1);
        countVerified("email", false, 1);
        when(challenges.countExpiredUnverified(any(), any(), any(), any())).thenReturn(0L);

        OtpChannelReport email = service.report().channels().get(1);

        assertThat(email.successRate()).isEqualTo(0.667);
        assertThat(email.firstAttemptRate()).isEqualTo(0.333);
    }

    private void count(String metric, String channel, int times) {
        meters.counter(metric, OtpService.CHANNEL_TAG, channel).increment(times);
    }

    private void countVerified(String channel, boolean firstAttempt, int times) {
        meters.counter(OtpService.VERIFIED_METRIC, OtpService.CHANNEL_TAG, channel,
                OtpService.FIRST_ATTEMPT_TAG, Boolean.toString(firstAttempt)).increment(times);
    }
}
