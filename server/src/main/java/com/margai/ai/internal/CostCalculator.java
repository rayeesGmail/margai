package com.margai.ai.internal;

import com.margai.ai.api.Usage;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@code cost_paise} at insert time (TECH_PLAN §4.8):
 * {@code HALF_UP((in·p_in + out·p_out + cr·p_cr + cw·p_cw) / 1e6 × usd_inr × 100)}.
 * Batch rows use the batch input and output prices; cache tokens are priced the same either way.
 * A price change never rewrites history because the value is stored, not derived.
 */
public final class CostCalculator {

    private static final BigDecimal MILLION = BigDecimal.valueOf(1_000_000);
    private static final BigDecimal PAISE_PER_RUPEE = BigDecimal.valueOf(100);

    private final PriceTable prices;
    private final BigDecimal usdInr;

    public CostCalculator(PriceTable prices, BigDecimal usdInr) {
        this.prices = prices;
        this.usdInr = usdInr;
    }

    public long paise(String modelId, Usage usage, boolean batch) {
        ModelPrice price = prices.priceOf(modelId);
        BigDecimal input = batch ? price.batchInput() : price.input();
        BigDecimal output = batch ? price.batchOutput() : price.output();
        BigDecimal usd = BigDecimal.valueOf(usage.inputTokens()).multiply(input)
                .add(BigDecimal.valueOf(usage.outputTokens()).multiply(output))
                .add(BigDecimal.valueOf(usage.cacheReadTokens()).multiply(price.cacheRead()))
                .add(BigDecimal.valueOf(usage.cacheWriteTokens()).multiply(price.cacheWrite()))
                .divide(MILLION);
        return usd.multiply(usdInr).multiply(PAISE_PER_RUPEE).setScale(0, RoundingMode.HALF_UP).longValueExact();
    }
}
