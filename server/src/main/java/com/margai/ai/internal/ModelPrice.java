package com.margai.ai.internal;

import java.math.BigDecimal;

/**
 * One row of the price table (TECH_PLAN §4.8, §13.2 item 1): USD per million tokens. Prices a
 * model does not have (an embedding model's output, a gated model's batch rate) are zero.
 *
 * <p>A model may price long prompts higher — Claude Haiku 5.5 does above 100,000 prompt tokens
 * (DECISIONS 2026-10-20). Then {@code longAbove} is that threshold and {@code longPrompt} the rates
 * a whole call is billed at once its prompt passes it; otherwise {@code longAbove} is zero and
 * {@code longPrompt} null.
 */
public record ModelPrice(BigDecimal input, BigDecimal output, BigDecimal cacheRead, BigDecimal cacheWrite,
        BigDecimal batchInput, BigDecimal batchOutput, long longAbove, ModelPrice longPrompt) {

    /** A model priced the same at every prompt length. */
    public ModelPrice(BigDecimal input, BigDecimal output, BigDecimal cacheRead, BigDecimal cacheWrite,
            BigDecimal batchInput, BigDecimal batchOutput) {
        this(input, output, cacheRead, cacheWrite, batchInput, batchOutput, 0, null);
    }

    public boolean hasChatPrices() {
        return input.signum() > 0 && output.signum() > 0;
    }

    /** Both prompt-cache rates set: a cached call priced at zero would under-bill silently. */
    public boolean hasCachePrices() {
        return cacheRead.signum() > 0 && cacheWrite.signum() > 0;
    }

    /**
     * The rates one call is billed at: the long-prompt rates once its prompt passes the threshold,
     * these otherwise. The prompt is what the provider counts — input, cache-read and cache-write
     * tokens together, so a long cached prefix crosses it even when most of it is a cache hit — and
     * each call is priced on its own.
     */
    public ModelPrice forPrompt(long promptTokens) {
        return longPrompt != null && promptTokens > longAbove ? longPrompt : this;
    }
}
