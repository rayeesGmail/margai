package com.margai.ai.internal;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * The price table of TECH_PLAN §4.8, parsed from {@code margai.ai.prices-json}: model id →
 * {@link ModelPrice}. Keys inside a model are {@code input}, {@code output}, {@code cache_read},
 * {@code cache_write}, {@code batch_input}, {@code batch_output}; a missing key is zero. A model
 * priced higher for long prompts adds {@code long_above}, the prompt-token threshold, and
 * {@code long}, an object of the same six keys.
 */
public final class PriceTable {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    private final Map<String, ModelPrice> prices;

    private PriceTable(Map<String, ModelPrice> prices) {
        this.prices = Map.copyOf(prices);
    }

    public static PriceTable parse(String json) {
        JsonNode root = JSON.readTree(json);
        if (!root.isObject()) {
            throw new IllegalArgumentException("prices-json must be an object keyed by model id");
        }
        Map<String, ModelPrice> prices = new LinkedHashMap<>();
        root.properties().forEach(entry -> {
            JsonNode model = entry.getValue();
            if (!model.isObject()) {
                throw new IllegalArgumentException("prices-json." + entry.getKey() + " must be an object");
            }
            prices.put(entry.getKey(), row(entry.getKey(), model));
        });
        return new PriceTable(prices);
    }

    /** One model's rates, with its long-prompt rates when it has them (DECISIONS 2026-10-20). */
    private static ModelPrice row(String modelId, JsonNode model) {
        ModelPrice rates = rates(model);
        JsonNode longPrompt = model.path("long");
        JsonNode longAbove = model.path("long_above");
        if (longPrompt.isMissingNode() && longAbove.isMissingNode()) {
            return rates;
        }
        if (!longPrompt.isObject() || !longAbove.isIntegralNumber() || longAbove.longValue() <= 0) {
            throw new IllegalArgumentException("prices-json." + modelId + ": long-prompt rates need both"
                    + " \"long_above\", a positive token count, and \"long\", an object of rates");
        }
        ModelPrice longRates = rates(longPrompt);
        if (!longRates.hasChatPrices()) {
            throw new IllegalArgumentException("prices-json." + modelId + ".long has no input and output price");
        }
        return new ModelPrice(rates.input(), rates.output(), rates.cacheRead(), rates.cacheWrite(),
                rates.batchInput(), rates.batchOutput(), longAbove.longValue(), longRates);
    }

    private static ModelPrice rates(JsonNode model) {
        return new ModelPrice(
                price(model, "input"), price(model, "output"),
                price(model, "cache_read"), price(model, "cache_write"),
                price(model, "batch_input"), price(model, "batch_output"));
    }

    private static BigDecimal price(JsonNode model, String key) {
        JsonNode value = model.path(key);
        if (value.isMissingNode() || value.isNull()) {
            return BigDecimal.ZERO;
        }
        if (!value.isNumber()) {
            throw new IllegalArgumentException("prices-json: " + key + " must be a number, got " + value);
        }
        BigDecimal price = value.decimalValue();
        if (price.signum() < 0) {
            throw new IllegalArgumentException("prices-json: " + key + " must not be negative");
        }
        return price;
    }

    public boolean has(String modelId) {
        return prices.containsKey(modelId);
    }

    public ModelPrice priceOf(String modelId) {
        ModelPrice price = prices.get(modelId);
        if (price == null) {
            throw new IllegalArgumentException("no price configured for model " + modelId
                    + " (margai.ai.prices-json)");
        }
        return price;
    }

    public Set<String> modelIds() {
        return prices.keySet();
    }
}
