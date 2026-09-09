package com.margai.auth.internal;

import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * {@link OtpMetrics} over the {@link OtpService} counters and the challenge table (TECH_PLAN
 * §10.2, §10.3; PLAN D11). Reads, never writes: the counters are read back from the registry the
 * service increments them in, and the repository answers the one count the counters cannot —
 * codes that expired unverified — over the same window, this process's lifetime. Nothing here
 * needs a transaction and nothing here logs.
 */
@Service
class OtpMetricsService implements OtpMetrics {

    private final MeterRegistry meters;
    private final OtpChallengeRepository challenges;
    private final IstClock clock;
    private final Instant since;

    OtpMetricsService(MeterRegistry meters, OtpChallengeRepository challenges, IstClock clock) {
        this.meters = meters;
        this.challenges = challenges;
        this.clock = clock;
        this.since = clock.now();
    }

    @Override
    public OtpDeliveryReport report() {
        Instant now = clock.now();
        List<OtpChannelReport> rows = new ArrayList<>(OtpChannel.values().length);
        for (OtpChannel channel : OtpChannel.values()) {
            long sent = count(OtpService.SENT_METRIC, channel);
            long verified = count(OtpService.VERIFIED_METRIC, channel);
            long firstAttempt = count(OtpService.VERIFIED_METRIC, channel, OtpService.FIRST_ATTEMPT_TAG, "true");
            rows.add(new OtpChannelReport(
                    channel.name(),
                    sent,
                    count(OtpService.SEND_FAILED_METRIC, channel),
                    verified,
                    firstAttempt,
                    count(OtpService.FAILED_METRIC, channel),
                    challenges.countExpiredUnverified(channel, OtpPurpose.login, since, now),
                    rate(verified, sent),
                    rate(firstAttempt, sent)));
        }
        return new OtpDeliveryReport(since, rows);
    }

    /** Every counter of that name and channel, whatever its other tags; 0 when none was ever incremented. */
    private long count(String metric, OtpChannel channel, String... moreTags) {
        double total = 0;
        for (Counter counter : meters.find(metric).tag(OtpService.CHANNEL_TAG, channel.name()).tags(moreTags).counters()) {
            total += counter.count();
        }
        return Math.round(total);
    }

    private static Double rate(long part, long whole) {
        return whole == 0 ? null : Math.round(1000.0 * part / whole) / 1000.0;
    }
}
