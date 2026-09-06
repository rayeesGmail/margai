package com.margai.ai.internal;

/** Outcome recorded in {@code ai_calls.status} (TECH_PLAN §2.8); lowercase database codes. */
public enum AiCallStatus {
    ok,
    error,
    timeout,
    breaker,
    invalid_output
}
