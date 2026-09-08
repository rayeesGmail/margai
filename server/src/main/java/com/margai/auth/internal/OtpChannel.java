package com.margai.auth.internal;

/**
 * {@code otp_challenges.channel} (TECH_PLAN §2.2 as amended at D7): how the code reaches the
 * student. {@code sms} is designed in and switched on by {@code margai.auth.otp.channels} once
 * the DLT template (TRACKER F1) exists; {@code email} is the channel until then.
 */
public enum OtpChannel {
    sms, email
}
