package com.margai.ai.internal;

import java.math.BigDecimal;

/**
 * One row of the price table (TECH_PLAN §4.8, §13.2 item 1): USD per million tokens. Prices a
 * model does not have (an embedding model's output, a gated model's batch rate) are zero.
 */
public record ModelPrice(BigDecimal input, BigDecimal output, BigDecimal cacheRead, BigDecimal cacheWrite,
        BigDecimal batchInput, BigDecimal batchOutput) {

    public boolean hasChatPrices() {
        return input.signum() > 0 && output.signum() > 0;
    }
}
