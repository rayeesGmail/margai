package com.margai.auth.api;

import java.time.Instant;
import java.util.List;

/**
 * {@code GET /admin/metrics/otp} (TECH_PLAN §3.7 ops rows, §10.3; D11) and the hourly log line:
 * one row per channel, every channel listed in a fixed order. The window is the process lifetime
 * — {@code since} is when this server instance started counting — because the §10.2 counters are
 * per-process by design (CloudWatch turns them into one-minute series at F8/D73) and a failed
 * delivery leaves no row to query (D7), so one window serves every number (DECISIONS D11).
 */
public record OtpDeliveryReport(Instant since, List<OtpChannelReport> channels) {

    public OtpDeliveryReport {
        channels = List.copyOf(channels);
    }
}
