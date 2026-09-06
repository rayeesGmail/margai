package com.margai.ai.api;

/**
 * The model could not be reached or did not answer (TECH_PLAN §4.11): throttling, 5xx,
 * timeouts, access denied. {@code retryable} drives {@code RetryingAiClient}; {@code timeout}
 * becomes the ledger status; {@code code} is the ledger's {@code error_code}. The UI renders
 * {@code AI_UNAVAILABLE} (§3.3).
 */
public final class AiUnavailableException extends RuntimeException {

    private final String code;
    private final boolean retryable;
    private final boolean timeout;

    public AiUnavailableException(String code, String message, Throwable cause, boolean retryable, boolean timeout) {
        super(message, cause);
        this.code = code;
        this.retryable = retryable;
        this.timeout = timeout;
    }

    public static AiUnavailableException retryable(String code, String message, Throwable cause) {
        return new AiUnavailableException(code, message, cause, true, false);
    }

    public static AiUnavailableException permanent(String code, String message, Throwable cause) {
        return new AiUnavailableException(code, message, cause, false, false);
    }

    public static AiUnavailableException timeout(String message, Throwable cause) {
        return new AiUnavailableException("timeout", message, cause, true, true);
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
}
