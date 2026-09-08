package com.margai.common.api;

import java.util.Map;

/** The 401 family of TECH_PLAN §3.3 for tokens: none, expired (refresh now), invalid (re-login). */
public final class AuthException extends ApiException {

    private AuthException(ErrorCode code, Map<String, ?> details) {
        super(code, details);
    }

    public static AuthException required() {
        return new AuthException(ErrorCode.AUTH_REQUIRED, null);
    }

    public static AuthException expired() {
        return new AuthException(ErrorCode.AUTH_EXPIRED, null);
    }

    public static AuthException invalid() {
        return new AuthException(ErrorCode.AUTH_INVALID, null);
    }

    public static AuthException forbidden() {
        return new AuthException(ErrorCode.FORBIDDEN, null);
    }
}
