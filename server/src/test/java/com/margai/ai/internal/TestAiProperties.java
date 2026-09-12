package com.margai.ai.internal;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

/**
 * {@link AiProperties} for unit tests: one place to build the record so a new configuration key
 * does not touch every test that needs a client. Model ids are placeholders — the point of the
 * config is that no test and no source knows a real one.
 */
public final class TestAiProperties {

    public static final String CHEAP = "cheap-model";
    public static final String REASON = "reason-model";
    public static final String VISION = "vision-model";
    public static final String EMBED = "embed-model";
    public static final int DIMENSIONS = 1024;

    private TestAiProperties() {
    }

    /** The three completion tiers and the embedding pin, with no price table. */
    public static AiProperties standard() {
        return withEmbed(EMBED, DIMENSIONS);
    }

    public static AiProperties withEmbed(String model, int dimensions) {
        return new AiProperties(AiProperties.Provider.anthropic,
                new AiProperties.Tiers(model(CHEAP), model(REASON), model(VISION)),
                new AiProperties.Embed("cohere", model, dimensions, true),
                "{}", BigDecimal.ONE, new AiProperties.Budget(1, 1), 100, 1024, Duration.ofSeconds(20),
                new AiProperties.Anthropic(""), new AiProperties.Cohere("", "https://example.invalid"),
                new AiProperties.Bedrock("ap-south-1"), Map.of());
    }

    /** A cheap-shaped model: a temperature, no reasoning, no effort. */
    public static AiProperties.Model model(String id) {
        return new AiProperties.Model(id, 0.0, AiProperties.Thinking.disabled, null, 4096);
    }
}
