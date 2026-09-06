package com.margai.ai.api;

import java.util.Objects;
import java.util.UUID;

/**
 * Who and what a call is for (TECH_PLAN §4.1): the user whose budget it counts against (null
 * for system, pipeline and eval calls), the request id echoed into the ledger and the logs, and
 * whether a nightly caller may defer it into a batch.
 */
public record AiCallContext(UUID userId, String requestId, boolean batchable) {

    public AiCallContext {
        Objects.requireNonNull(requestId, "requestId");
    }

    public static AiCallContext forUser(UUID userId, String requestId) {
        return new AiCallContext(Objects.requireNonNull(userId, "userId"), requestId, false);
    }

    public static AiCallContext system(String requestId) {
        return new AiCallContext(null, requestId, false);
    }

    public AiCallContext asBatchable() {
        return new AiCallContext(userId, requestId, true);
    }
}
