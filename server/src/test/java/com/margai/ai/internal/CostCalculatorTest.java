package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.Usage;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * TECH_PLAN §4.8: cost in whole paise from per-million-token USD prices and {@code usd_inr},
 * rounded up so any usage costs at least one paisa; batch rows at the batch input/output rates;
 * cache tokens priced separately.
 */
class CostCalculatorTest {

    private static final String CHAT = "chat-model";
    private static final String EMBED = "embed-model";

    private final PriceTable prices = PriceTable.parse("""
            {"chat-model": {"input": 1.00, "output": 5.00, "cache_read": 0.10, "cache_write": 1.25,
                            "batch_input": 0.50, "batch_output": 2.50},
             "embed-model": {"input": 0.10}}
            """);

    private final CostCalculator calculator = new CostCalculator(prices, new BigDecimal("90"));

    @Test
    void onDemandCallWithCacheReads() {
        // (1000×1.00 + 200×5.00 + 4000×0.10) / 1e6 = 0.0024 USD → 0.216 INR → 21.6 paise → 22
        assertThat(calculator.paise(CHAT, new Usage(1000, 200, 4000, 0), false)).isEqualTo(22);
    }

    @Test
    void cacheWritesArePricedAtTheWriteRate() {
        // (100×1.00 + 10×5.00 + 4000×1.25) / 1e6 = 0.00515 USD → 0.4635 INR → 46.35 → 47 (up)
        assertThat(calculator.paise(CHAT, new Usage(100, 10, 0, 4000), false)).isEqualTo(47);
    }

    @Test
    void batchRowsUseTheBatchRates() {
        // (1000×0.50 + 200×2.50) / 1e6 = 0.001 USD → 0.09 INR → 9 paise
        assertThat(calculator.paise(CHAT, new Usage(1000, 200, 0, 0), true)).isEqualTo(9);
    }

    @Test
    void roundsUpToWholePaiseSoAnyUsageCostsAtLeastOne() {
        // 500×1.00 / 1e6 = 0.0005 USD → 0.045 INR → 4.5 paise → 5
        assertThat(calculator.paise(CHAT, new Usage(500, 0, 0, 0), false)).isEqualTo(5);
        // 1×1.00 / 1e6 USD → 0.009 paise → 1: a sub-paisa call never escapes the breaker
        assertThat(calculator.paise(CHAT, new Usage(1, 0, 0, 0), false)).isEqualTo(1);
        // exact amounts are not inflated: 200×5.00 / 1e6 = 0.001 USD → 9 paise exactly
        assertThat(calculator.paise(CHAT, new Usage(0, 200, 0, 0), false)).isEqualTo(9);
        assertThat(calculator.paise(CHAT, Usage.none(), false)).isZero();
    }

    @Test
    void embeddingModelHasOnlyAnInputPrice() {
        // 20000×0.10 / 1e6 = 0.002 USD → 0.18 INR → 18 paise
        assertThat(calculator.paise(EMBED, new Usage(20000, 0, 0, 0), false)).isEqualTo(18);
        assertThat(prices.priceOf(EMBED).output()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(prices.priceOf(EMBED).hasChatPrices()).isFalse();
    }

    @Test
    void unknownModelIsAConfigurationError() {
        assertThatThrownBy(() -> calculator.paise("nobody", new Usage(1, 1, 0, 0), false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nobody")
                .hasMessageContaining("prices-json");
    }

    @Test
    void priceTableRejectsBadShapes() {
        assertThatThrownBy(() -> PriceTable.parse("[]")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PriceTable.parse("{\"m\": {\"input\": \"one\"}}"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("input");
        assertThatThrownBy(() -> PriceTable.parse("{\"m\": {\"input\": -1}}"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("negative");
    }

    // ── long-prompt rates (Claude Haiku 5.5 above 100,000 prompt tokens; DECISIONS 2026-10-20) ─────────

    private final PriceTable tiered = PriceTable.parse("""
            {"tiered": {"input": 0.10, "output": 0.50, "cache_read": 0.01, "cache_write": 0.125,
                        "batch_input": 0.05, "batch_output": 0.25,
                        "long_above": 100000,
                        "long": {"input": 0.50, "output": 2.50, "cache_read": 0.05, "cache_write": 0.625,
                                 "batch_input": 0.25, "batch_output": 1.25}}}
            """);

    private final CostCalculator tieredCalculator = new CostCalculator(tiered, new BigDecimal("90"));

    /** At the threshold the prompt is not over it: the standard rates. */
    @Test
    void aPromptAtTheThresholdIsBilledAtTheStandardRates() {
        // (100000×0.10 + 1000×0.50) / 1e6 = 0.0105 USD → 0.945 INR → 94.5 paise → 95
        assertThat(tieredCalculator.paise("tiered", new Usage(100_000, 1000, 0, 0), false)).isEqualTo(95);
    }

    /** One token over, counting cached tokens, and the whole call — output included — is at the long rates. */
    @Test
    void aPromptOverTheThresholdIsBilledWhollyAtTheLongRatesCachedTokensCounting() {
        // prompt = 1 + 90000 + 10000 = 100001 > 100000:
        // (1×0.50 + 1000×2.50 + 90000×0.05 + 10000×0.625) / 1e6 = 0.0132505 USD → 1.192545 INR → 119.25 → 120
        assertThat(tieredCalculator.paise("tiered", new Usage(1, 1000, 90_000, 10_000), false)).isEqualTo(120);
    }

    @Test
    void aLongBatchCallUsesTheLongBatchRates() {
        // (200000×0.25 + 1000×1.25) / 1e6 = 0.05125 USD → 4.6125 INR → 461.25 → 462
        assertThat(tieredCalculator.paise("tiered", new Usage(200_000, 1000, 0, 0), true)).isEqualTo(462);
    }

    @Test
    void longPromptRatesNeedBothTheThresholdAndTheRates() {
        assertThatThrownBy(() -> PriceTable.parse("{\"m\": {\"input\": 1, \"output\": 1, \"long_above\": 100000}}"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("long_above");
        assertThatThrownBy(() -> PriceTable.parse("{\"m\": {\"input\": 1, \"output\": 1, \"long\": {\"input\": 2}}}"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("long_above");
        assertThatThrownBy(() -> PriceTable.parse(
                "{\"m\": {\"input\": 1, \"output\": 1, \"long_above\": 10, \"long\": {\"input\": 2}}}"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("no input and output price");
    }
}
