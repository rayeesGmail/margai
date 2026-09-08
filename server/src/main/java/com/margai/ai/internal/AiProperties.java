package com.margai.ai.internal;

import com.margai.ai.api.Tier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * {@code margai.ai.*} (TECH_PLAN §11.5, §7.3): model ids per tier, the embedding model, the
 * price table, budgets, the batch minimum and prompt versions. Bound from
 * {@code application.yml} defaults and environment variables ({@code MARGAI_AI_TIER_CHEAP}),
 * validated at startup. No model id, price or limit lives in code (.claude/rules/ai-layer.md).
 *
 * @param region          Bedrock region the calls are made from
 * @param tier            model id per completion tier (inference profile ids)
 * @param embed           embedding model id
 * @param pricesJson      {@code {model_id: {input, output, cache_read, cache_write, batch_input,
 *                        batch_output}}} in USD per million tokens
 * @param usdInr          rupees per dollar used for {@code cost_paise}
 * @param budget          daily caps in paise (§4.8)
 * @param batchMinRecords Bedrock batch minimum; 0 disables batch mode (§4.11)
 * @param maxOutputTokens cap on generated tokens per call
 * @param callTimeout     wall-clock limit of one real-time model call (§4.11: 20 s)
 * @param prompts         active version per prompt name (§4.12)
 */
@ConfigurationProperties(prefix = "margai.ai")
@Validated
public record AiProperties(
        @NotBlank String region,
        @NotNull @Valid Tiers tier,
        @NotNull @Valid Embed embed,
        @NotBlank String pricesJson,
        @NotNull @Positive BigDecimal usdInr,
        @NotNull @Valid Budget budget,
        @Min(0) int batchMinRecords,
        @Min(1) int maxOutputTokens,
        @NotNull Duration callTimeout,
        Map<String, @Valid Prompt> prompts) {

    public AiProperties {
        prompts = prompts == null ? Map.of() : Map.copyOf(prompts);
    }

    public String modelFor(Tier tier) {
        return switch (tier) {
            case cheap -> this.tier.cheap();
            case reason -> this.tier.reason();
            case vision -> this.tier.vision();
            case embed -> this.embed.model();
        };
    }

    /** Prompt name → configured active version, for prompts that configure one. */
    public Map<String, Integer> promptVersions() {
        Map<String, Integer> versions = new LinkedHashMap<>();
        prompts.forEach((name, prompt) -> {
            if (prompt != null && prompt.version() != null) {
                versions.put(name, prompt.version());
            }
        });
        return Map.copyOf(versions);
    }

    public record Tiers(@NotBlank String cheap, @NotBlank String reason, @NotBlank String vision) {
    }

    public record Embed(@NotBlank String model) {
    }

    public record Budget(@Positive long userDailyPaise, @Positive long globalDailyPaise) {
    }

    public record Prompt(@Min(1) Integer version) {
    }
}
