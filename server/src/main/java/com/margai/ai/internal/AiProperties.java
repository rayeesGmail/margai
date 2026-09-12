package com.margai.ai.internal;

import com.margai.ai.api.Tier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
 * {@code margai.ai.*} (TECH_PLAN §11.5, §7.3): the provider, model ids per tier with the request
 * shape each model accepts, the embedding pin, the price table, budgets, the batch minimum and
 * prompt versions. Bound from {@code application.yml} defaults and environment variables
 * ({@code MARGAI_AI_TIER_CHEAP_ID}), validated at startup. No model id, price or limit lives in
 * code (.claude/rules/ai-layer.md).
 *
 * @param provider        completion provider: {@code anthropic} (default) or {@code bedrock}
 * @param tier            model and request shape per completion tier
 * @param embed           embedding provider, model and dimension — pinned together (§4.9)
 * @param pricesJson      {@code {model_id: {input, output, cache_read, cache_write, batch_input,
 *                        batch_output}}} in USD per million tokens
 * @param usdInr          rupees per dollar used for {@code cost_paise}
 * @param budget          daily caps in paise (§4.8)
 * @param batchMinRecords records below which {@code completeBatch} stays on-demand; 0 disables
 *                        batch mode entirely (§4.11)
 * @param maxOutputTokens cap on generated tokens per call
 * @param callTimeout     wall-clock limit of one real-time model call (§4.11: 20 s)
 * @param anthropic       completion provider connection settings
 * @param cohere          embedding provider connection settings
 * @param bedrock         settings only the dormant Bedrock client reads
 * @param prompts         active version per prompt name (§4.12)
 */
@ConfigurationProperties(prefix = "margai.ai")
@Validated
public record AiProperties(
        @NotNull Provider provider,
        @NotNull @Valid Tiers tier,
        @NotNull @Valid Embed embed,
        @NotBlank String pricesJson,
        @NotNull @Positive BigDecimal usdInr,
        @NotNull @Valid Budget budget,
        @Min(0) int batchMinRecords,
        @Min(1) int maxOutputTokens,
        @NotNull Duration callTimeout,
        @NotNull @Valid Anthropic anthropic,
        @NotNull @Valid Cohere cohere,
        @NotNull @Valid Bedrock bedrock,
        Map<String, @Valid Prompt> prompts) {

    /**
     * The completion providers a build knows how to wire. Typed, so an unknown value fails binding
     * at startup instead of matching neither provider's condition and leaving the chain on the fake
     * — which is what a bare string allowed. The constants below are the same names, because
     * {@code @ConditionalOnProperty(havingValue = …)} needs a compile-time constant.
     */
    public enum Provider {
        anthropic,
        bedrock
    }

    /** The provider values {@code margai.ai.provider} accepts; {@link Provider} is the authority. */
    public static final String ANTHROPIC = "anthropic";
    public static final String BEDROCK = "bedrock";

    public AiProperties {
        prompts = prompts == null ? Map.of() : Map.copyOf(prompts);
    }

    /** The model id billed and stamped on the ledger row for this tier. */
    public String modelFor(Tier tier) {
        return tier == Tier.embed ? embed.model() : modelOf(tier).id();
    }

    /** The model and request shape of a completion tier; {@code embed} has neither. */
    public Model modelOf(Tier tier) {
        return switch (tier) {
            case cheap -> this.tier.cheap();
            case reason -> this.tier.reason();
            case vision -> this.tier.vision();
            case embed -> throw new IllegalArgumentException("embed has no completion model; use embed().model()");
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

    public record Tiers(@NotNull @Valid Model cheap, @NotNull @Valid Model reason, @NotNull @Valid Model vision) {
    }

    /**
     * One tier's model and what that model accepts on the wire (§4.11). These are capabilities,
     * not preferences: the current reasoning models reject {@code temperature} outright and the
     * cheap model rejects {@code effort}, so "send nothing" has to be expressible — a null
     * temperature or effort is omitted from the request rather than defaulted.
     *
     * @param id              provider model id, never a code constant
     * @param temperature     sampling temperature, or null where the model rejects the field
     * @param thinking        {@code disabled} or {@code adaptive} (§4.11)
     * @param effort          reasoning effort, or null where the model rejects the field
     * @param cacheMinTokens  the model's minimum cacheable prefix; below it a cache point is a
     *                        silent no-op, which {@code AiConfiguration} warns about at startup.
     *                        Required, so that adding a tier means looking the figure up
     */
    public record Model(
            @NotBlank String id,
            @DecimalMin("0.0") @DecimalMax("1.0") Double temperature,
            @NotNull Thinking thinking,
            Effort effort,
            @Positive int cacheMinTokens) {
    }

    /** Whether a tier's calls carry reasoning (§4.11); constants are the config spellings. */
    public enum Thinking {
        disabled,
        adaptive
    }

    /** Reasoning effort, where the model accepts it; constants are the config spellings. */
    public enum Effort {
        low,
        medium,
        high,
        xhigh,
        max
    }

    /**
     * The embedding pin (§4.9). Provider, model and dimension move together: the stored vectors
     * are only comparable to vectors from the same three, so changing any of them means
     * re-embedding the whole corpus and re-indexing, never a config flip alone
     * (DECISIONS 2026-09-12).
     *
     * @param provider            embedding provider ({@code cohere})
     * @param model               provider model id
     * @param dimensions          vector width; must equal the {@code vector(n)} columns of §2.3
     * @param sendOutputDimension whether the request pins the width explicitly — the v4 line and
     *                            newer accept it, the v3 line does not and errors on it
     */
    public record Embed(
            @NotBlank String provider,
            @NotBlank String model,
            @Min(1) int dimensions,
            boolean sendOutputDimension) {
    }

    /**
     * The completion provider's connection settings. The key is the whole auth model now — there
     * is no cloud role behind these calls — so it comes from SSM as a SecureString in a deployed
     * environment and from an untracked local file on a laptop, never from this repository
     * (§9.2, CLAUDE.md). Blank is the default and is refused when a live client is wired.
     */
    public record Anthropic(@NotNull String apiKey) {
    }

    /** The embedding provider's connection settings; the key rule of {@link Anthropic} applies. */
    public record Cohere(@NotNull String apiKey, @NotBlank String baseUrl) {
    }

    /** Settings the dormant Bedrock client reads; unused while {@code provider = anthropic}. */
    public record Bedrock(@NotBlank String region) {
    }

    public record Budget(@Positive long userDailyPaise, @Positive long globalDailyPaise) {
    }

    public record Prompt(@Min(1) Integer version) {
    }
}
