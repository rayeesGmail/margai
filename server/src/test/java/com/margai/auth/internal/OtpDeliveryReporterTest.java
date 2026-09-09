package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * PLAN D11 "delivery-rate logging" (TECH_PLAN §10.1, §10.2): on its schedule the reporter writes
 * one INFO line per configured channel with every number of the report as {@code key=value} —
 * what CloudWatch Logs Insights parses — and a rate that does not exist reads {@code n/a}. Never
 * a destination, never a code: the line carries nothing but the report.
 */
class OtpDeliveryReporterTest {

    private static final Instant SINCE = Instant.parse("2026-09-09T04:30:00Z");

    private final OtpMetrics metrics = mock(OtpMetrics.class);
    private final ListAppender<ILoggingEvent> authLog = new ListAppender<>();

    @BeforeEach
    void setUp() {
        authLog.start();
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).addAppender(authLog);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).detachAppender(authLog);
    }

    @Test
    void oneLinePerConfiguredChannelWithEveryNumber() {
        when(metrics.report()).thenReturn(new OtpDeliveryReport(SINCE, List.of(
                new OtpChannelReport("sms", 0, 0, 0, 0, 0, 0, null, null),
                new OtpChannelReport("email", 5, 1, 4, 3, 2, 1, 0.8, 0.6))));

        reporter(Set.of(OtpChannel.email)).logDeliveryRates();

        assertThat(authLog.list).hasSize(1);
        assertThat(authLog.list.get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(authLog.list.get(0).getFormattedMessage()).isEqualTo("otp delivery channel=email"
                + " since=2026-09-09T04:30:00Z sent=5 send_failed=1 verified=4 first_attempt=3 wrong_codes=2"
                + " expired_unverified=1 success_rate=0.800 first_attempt_rate=0.600");
    }

    @Test
    void aChannelWithoutRatesReadsNotAvailableAndEveryEnabledChannelGetsALine() {
        when(metrics.report()).thenReturn(new OtpDeliveryReport(SINCE, List.of(
                new OtpChannelReport("sms", 0, 0, 0, 0, 0, 0, null, null),
                new OtpChannelReport("email", 3, 0, 2, 1, 1, 0, 0.667, 0.333))));

        reporter(Set.of(OtpChannel.email, OtpChannel.sms)).logDeliveryRates();

        assertThat(authLog.list).extracting(ILoggingEvent::getFormattedMessage).containsExactly(
                "otp delivery channel=sms since=2026-09-09T04:30:00Z sent=0 send_failed=0 verified=0 first_attempt=0"
                        + " wrong_codes=0 expired_unverified=0 success_rate=n/a first_attempt_rate=n/a",
                "otp delivery channel=email since=2026-09-09T04:30:00Z sent=3 send_failed=0 verified=2 first_attempt=1"
                        + " wrong_codes=1 expired_unverified=0 success_rate=0.667 first_attempt_rate=0.333");
    }

    private OtpDeliveryReporter reporter(Set<OtpChannel> channels) {
        AuthProperties properties = new AuthProperties(
                new AuthProperties.Jwt("", "", Duration.ofMinutes(15), Duration.ofDays(30)),
                new AuthProperties.Otp("", Duration.ofMinutes(5), 5, Duration.ofSeconds(30), 6, channels,
                        AuthProperties.Sender.log, "", "ap-south-1", Duration.ofHours(1)),
                Duration.ofMinutes(2));
        return new OtpDeliveryReporter(metrics, properties);
    }
}
