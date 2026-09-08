package com.margai.common.api;

import java.util.Map;

/**
 * OTP outcomes (TECH_PLAN §3.3, 401): a wrong code with the attempts left, or a challenge that
 * is no longer usable — unknown, already used, past its 5 minutes or out of attempts — for which
 * the one remedy is a new code (DECISIONS 2026-09-08, D7).
 */
public final class OtpException extends ApiException {

    private OtpException(ErrorCode code, Map<String, ?> details) {
        super(code, details);
    }

    public static OtpException invalid(int attemptsLeft) {
        return new OtpException(ErrorCode.OTP_INVALID, Map.of("attempts_left", attemptsLeft));
    }

    public static OtpException expired() {
        return new OtpException(ErrorCode.OTP_EXPIRED, null);
    }
}
