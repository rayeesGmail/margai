package com.margai.common.api;

import java.time.Duration;
import java.util.Map;

/**
 * 429 outcomes (TECH_PLAN §3.3, §3.4): the general per-user limit and the OTP request limits.
 * {@code retryAfter} travels twice — as the {@code Retry-After} header in whole seconds and as
 * {@code details.retry_after_s} — so both curl and the app can read it.
 */
public final class RateLimitedException extends ApiException {

    private final long retryAfterSeconds;

    private RateLimitedException(ErrorCode code, Duration retryAfter) {
        super(code, Map.of("retry_after_s", wholeSeconds(retryAfter)));
        this.retryAfterSeconds = wholeSeconds(retryAfter);
    }

    public static RateLimitedException general(Duration retryAfter) {
        return new RateLimitedException(ErrorCode.RATE_LIMITED, retryAfter);
    }

    public static RateLimitedException otp(Duration retryAfter) {
        return new RateLimitedException(ErrorCode.OTP_RATE_LIMITED, retryAfter);
    }

    public long retryAfterSeconds() {
        return retryAfterSeconds;
    }

    /** Rounded up, never below one second: a client told "0" would retry at once. */
    static long wholeSeconds(Duration retryAfter) {
        if (retryAfter == null || retryAfter.isNegative() || retryAfter.isZero()) {
            return 1;
        }
        long seconds = retryAfter.getSeconds();
        return retryAfter.getNano() > 0 ? seconds + 1 : Math.max(seconds, 1);
    }
}
