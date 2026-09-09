package com.margai.auth.api;

/**
 * One channel of the {@link OtpDeliveryReport}. Counts are the §10.2 counters — codes handed to
 * the sender ({@code sent}), refused by it ({@code send_failed}), matched ({@code verified}, of
 * which {@code verified_first_attempt} matched on the student's first try), wrong codes typed
 * ({@code wrong_codes}, per attempt) — plus the one number no counter can carry: codes that
 * reached their expiry unverified ({@code expired_unverified}), which is what an email that never
 * arrived looks like from the server. The two ratios are over {@code sent}: {@code success_rate}
 * is {@code verified / sent}, {@code first_attempt_rate} is SPEC §11's number; both are rounded
 * to three decimals and absent when nothing was sent, so no zero ever masquerades as a rate.
 */
public record OtpChannelReport(
        String channel,
        long sent,
        long sendFailed,
        long verified,
        long verifiedFirstAttempt,
        long wrongCodes,
        long expiredUnverified,
        Double successRate,
        Double firstAttemptRate) {
}
