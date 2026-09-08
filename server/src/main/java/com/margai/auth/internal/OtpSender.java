package com.margai.auth.internal;

/**
 * The delivery port (TECH_PLAN §1.4 "only auth imports the SMS client", extended to the SES
 * client at D7). Implementations: {@code LoggingOtpSender} — the sandbox, selected by
 * {@code margai.auth.otp.sender = log} — and {@code SesOtpSender} for email; the DLT SMS adapter
 * joins at D11 when TRACKER F1 lands. A failure is an {@link OtpSendException} whose message
 * carries no destination.
 */
interface OtpSender {

    void send(OtpDelivery delivery);
}
