package com.margai.ai.api;

import java.time.Duration;
import java.util.UUID;

/**
 * Result of a call (TECH_PLAN §4.1). {@code aiCallId} is the ledger row and is set by the
 * outermost decorator; below it the field is null. {@code attempts} counts the model calls the
 * request took — retries and the repair retry fold into one response and one ledger row
 * (DECISIONS D5) — and feeds the {@code ai.attempts} meter.
 */
public record AiResponse<T>(T output, Usage usage, String modelId, Duration latency, UUID aiCallId, int attempts) {

    public AiResponse(T output, Usage usage, String modelId, Duration latency, UUID aiCallId) {
        this(output, usage, modelId, latency, aiCallId, 1);
    }

    public AiResponse<T> withAiCallId(UUID aiCallId) {
        return new AiResponse<>(output, usage, modelId, latency, aiCallId, attempts);
    }

    public AiResponse<T> withAttempts(int attempts) {
        return new AiResponse<>(output, usage, modelId, latency, aiCallId, attempts);
    }
}
