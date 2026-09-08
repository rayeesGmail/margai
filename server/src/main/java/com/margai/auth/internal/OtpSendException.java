package com.margai.auth.internal;

/**
 * Delivery failed after the challenge was stored. Deliberately carries a short, PII-free reason
 * and no provider cause: the handler logs it at ERROR as an {@code INTERNAL} failure (TECH_PLAN
 * §3.3), and provider messages can quote the recipient (§9.6).
 */
final class OtpSendException extends RuntimeException {

    OtpSendException(String reason) {
        super(reason);
    }
}
