package com.margai.ai.api;

import java.util.List;

/**
 * The single AI seam (TECH_PLAN §4.1, DECISIONS D3.18): two primitives, text and optional
 * images to a typed JSON record, and text to a vector. Feature behaviour lives in the task
 * classes of {@code ai.tasks}, the only callers; every implementation and decorator runs behind
 * the same interface so the ledger, breaker, tier policy, schema validation and retries apply to
 * the Bedrock and the fake client alike.
 */
public interface AiClient {

    /** Text + optional images → the typed JSON output of {@code request.outputType()}. */
    <T> AiResponse<T> complete(AiRequest<T> request);

    /**
     * The same contract for many requests: one response and one ledger row per request. At D5
     * this is the bounded on-demand loop of §4.11 (concurrency 4) over {@link #complete}; a
     * Bedrock batch job for ≥ {@code margai.ai.batch-min-records} requests arrives with its first
     * caller (D55+). Runs to completion; the first failure is rethrown with the others suppressed.
     */
    default <T> List<AiResponse<T>> completeBatch(List<AiRequest<T>> requests) {
        return OnDemandBatch.run(requests, this::complete, OnDemandBatch.CONCURRENCY);
    }

    /** Text → {@code vector(1024)} (§4.9). */
    AiResponse<float[]> embed(EmbedRequest request);
}
