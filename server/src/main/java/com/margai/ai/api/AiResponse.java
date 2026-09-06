package com.margai.ai.api;

import java.time.Duration;
import java.util.UUID;

/**
 * Result of a call (TECH_PLAN §4.1). {@code aiCallId} is the ledger row and is set by the
 * outermost decorator; below it the field is null.
 */
public record AiResponse<T>(T output, Usage usage, String modelId, Duration latency, UUID aiCallId) {

    public AiResponse<T> withAiCallId(UUID aiCallId) {
        return new AiResponse<>(output, usage, modelId, latency, aiCallId);
    }
}
