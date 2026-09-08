package com.margai.auth.internal;

import java.util.UUID;

/** {@code POST /auth/otp/request} outcome (TECH_PLAN §3.7): the challenge to verify, the resend gap, and where the code went. */
public record OtpRequested(UUID challengeId, long resendAfterSeconds, OtpChannel channel) {
}
