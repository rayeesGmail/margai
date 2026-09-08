package com.margai.common.api;

/**
 * The error codes of TECH_PLAN §3.3 with the HTTP status each travels on. The same strings are
 * the keys of the server message catalogs and of the app's ARB files (§3.3 last paragraph).
 */
public enum ErrorCode {
    VALIDATION_FAILED(400),
    IMAGE_UNREADABLE(400),
    IDEMPOTENCY_CONFLICT(400),
    AUTH_REQUIRED(401),
    AUTH_EXPIRED(401),
    AUTH_INVALID(401),
    OTP_INVALID(401),
    OTP_EXPIRED(401),
    FORBIDDEN(403),
    CONSENT_REQUIRED(403),
    PRO_REQUIRED(403),
    NOT_FOUND(404),
    STATE_CONFLICT(409),
    DOUBT_LIMIT_REACHED(422),
    DOUBT_UNVERIFIED(422),
    NOT_A_QUESTION(422),
    RATE_LIMITED(429),
    OTP_RATE_LIMITED(429),
    INTERNAL(500),
    AI_UNAVAILABLE(503),
    AI_BUDGET_EXCEEDED(503);

    private final int httpStatus;

    ErrorCode(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int httpStatus() {
        return httpStatus;
    }
}
