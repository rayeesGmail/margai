package com.margai.ai.api;

import java.time.Duration;
import java.util.Optional;

/**
 * The model could not be reached or did not answer (TECH_PLAN §4.11): throttling, 5xx,
 * timeouts, access denied. {@code retryable} drives {@code RetryingAiClient}; {@code timeout}
 * becomes the ledger status; {@code code} is the ledger's {@code error_code}. The UI renders
 * {@code AI_UNAVAILABLE} (§3.3).
 *
 * <p>{@code retryAfter} carries the provider's own wait hint when it sends one (the direct
 * APIs answer a 429 with a {@code retry-after} header); the retry decorator prefers it over its
 * own backoff, so a rate limit is waited out for as long as the provider asked and no longer.
 */
public final class AiUnavailableException extends RuntimeException {

    private final String code;
    private final boolean retryable;
    private final boolean timeout;
    private final Duration retryAfter;

    public AiUnavailableException(String code, String message, Throwable cause, boolean retryable, boolean timeout) {
        this(code, message, cause, retryable, timeout, null);
    }

    public AiUnavailableException(String code, String message, Throwable cause, boolean retryable, boolean timeout,
            Duration retryAfter) {
        super(message, cause);
        this.code = code;
        this.retryable = retryable;
        this.timeout = timeout;
        this.retryAfter = retryAfter;
    }

    public static AiUnavailableException retryable(String code, String message, Throwable cause) {
        return new AiUnavailableException(code, message, cause, true, false, null);
    }

    /** Retryable with the provider's wait hint; a null or negative hint is simply absent. */
    public static AiUnavailableException retryable(String code, String message, Throwable cause, Duration retryAfter) {
        Duration hint = retryAfter != null && !retryAfter.isNegative() && !retryAfter.isZero() ? retryAfter : null;
        return new AiUnavailableException(code, message, cause, true, false, hint);
    }

    public static AiUnavailableException permanent(String code, String message, Throwable cause) {
        return new AiUnavailableException(code, message, cause, false, false, null);
    }

    /** A 20 s real-time call that did not return; not retried (§4.11 retries throttling and 5xx only). */
    public static AiUnavailableException timeout(String message, Throwable cause) {
        return new AiUnavailableException("timeout", message, cause, false, true, null);
    }

    public String code() {
        return code;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean isTimeout() {
        return timeout;
    }

    /** The provider's wait hint for this failure, when it sent one. */
    public Optional<Duration> retryAfter() {
        return Optional.ofNullable(retryAfter);
    }
}
