package com.margai.auth.internal;

import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

/**
 * PLAN D11 "delivery-rate logging": every {@code margai.auth.otp.report-every} (an hour by
 * default, the same wait before the first line) one INFO line per enabled channel with the whole
 * {@link OtpDeliveryReport} as {@code key=value} pairs — the shape CloudWatch Logs Insights parses
 * once the logs flow there (TECH_PLAN §10.1; F8), and a plain grep locally. A rate that does not
 * exist yet reads {@code n/a}. The line carries nothing but the report: no destination, no code
 * (§9.6). The schedule is taken from the bound {@link AuthProperties.Otp#reportEvery()} — one
 * source of truth, in whatever notation the configuration used (§11.5) — on the application's
 * {@link TaskScheduler}, which {@code common}'s {@code SchedulingConfiguration} switches on.
 */
@Component
class OtpDeliveryReporter {

    private static final Logger log = LoggerFactory.getLogger(OtpDeliveryReporter.class);

    private final OtpMetrics metrics;
    private final AuthProperties.Otp policy;
    private final TaskScheduler scheduler;

    OtpDeliveryReporter(OtpMetrics metrics, AuthProperties properties, TaskScheduler scheduler) {
        this.metrics = metrics;
        this.policy = properties.otp();
        this.scheduler = scheduler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void schedule() {
        Duration every = policy.reportEvery();
        scheduler.scheduleWithFixedDelay(this::logDeliveryRates, Instant.now().plus(every), every);
    }

    public void logDeliveryRates() {
        OtpDeliveryReport report = metrics.report();
        for (OtpChannelReport channel : report.channels()) {
            if (!policy.allows(OtpChannel.valueOf(channel.channel()))) {
                continue;
            }
            log.info("otp delivery channel={} since={} sent={} send_failed={} verified={} first_attempt={}"
                    + " wrong_codes={} expired_unverified={} success_rate={} first_attempt_rate={}",
                    channel.channel(), report.since(), channel.sent(), channel.sendFailed(), channel.verified(),
                    channel.verifiedFirstAttempt(), channel.wrongCodes(), channel.expiredUnverified(),
                    rate(channel.successRate()), rate(channel.firstAttemptRate()));
        }
    }

    private static String rate(Double rate) {
        return rate == null ? "n/a" : String.format(Locale.ROOT, "%.3f", rate);
    }
}
