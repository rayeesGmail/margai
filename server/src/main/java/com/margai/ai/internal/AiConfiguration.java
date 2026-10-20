package com.margai.ai.internal;

import com.margai.ai.api.Tier;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Configuration-derived beans of the ai module (TECH_PLAN §4.8, §4.12, §11.5): the price
 * table, checked at startup so every configured model can be billed, the cost calculator and
 * the prompt registry, whose cached prefixes are sized against each model's cache minimum.
 * The client chain itself is wired in {@code AiClientConfiguration}.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({AiProperties.class, AiFlags.class})
class AiConfiguration {

    /** The completion tiers; {@code embed} is priced and checked separately. */
    private static final List<Tier> COMPLETION_TIERS = List.of(Tier.cheap, Tier.reason, Tier.vision);

    private static final Logger log = LoggerFactory.getLogger(AiConfiguration.class);

    @Bean
    PriceTable priceTable(AiProperties properties, AiFlags flags) {
        PriceTable table = PriceTable.parse(properties.pricesJson());
        for (Tier tier : COMPLETION_TIERS) {
            String modelId = properties.modelFor(tier);
            if (!table.has(modelId) || !table.priceOf(modelId).hasChatPrices()) {
                throw new IllegalStateException("margai.ai.tier." + tier + ".id = " + modelId
                        + " has no input and output price in margai.ai.prices-json");
            }
        }
        String embedModel = properties.embed().model();
        if (!table.has(embedModel) || table.priceOf(embedModel).input().signum() <= 0) {
            throw new IllegalStateException("margai.ai.embed.model = " + embedModel
                    + " has no input price in margai.ai.prices-json");
        }
        checkFallback(properties, flags, table);
        return table;
    }

    /**
     * The completion fallback (TECH_PLAN §15.12), checked at every start rather than at the outage
     * that switches to it. Refused: the flag on with no fallback ids, a fallback provider that is the
     * primary, and a fallback id without input, output and both cache prices — a cached call priced
     * at zero would let the breaker under-count exactly when the fallback is carrying the traffic.
     * Warned, not refused (founder ruling, DECISIONS 2026-10-20): the condition — a fallback id that no
     * longer names its tier's primary model, which is what a primary switch leaves behind when the
     * fallback is not moved with it — is Claude's reading of "when a tier's primary model changes". A
     * pipeline profile that pins a tier for one run trips it by design.
     */
    static void checkFallback(AiProperties properties, AiFlags flags, PriceTable table) {
        if (properties.fallback() == null) {
            if (flags.aiFallback()) {
                throw new IllegalStateException("margai.flags.ai-fallback is on but margai.ai.fallback.tier names"
                        + " no model: the switch would have nothing to switch to (TECH_PLAN §15.12)");
            }
            return;
        }
        if (properties.fallbackProvider() == properties.provider()) {
            throw new IllegalStateException("margai.ai.fallback-provider = " + properties.fallbackProvider()
                    + " is margai.ai.provider too: a fallback must be another provider (CS-2 §4.11)");
        }
        for (Tier tier : COMPLETION_TIERS) {
            String fallbackId = properties.fallbackModelFor(tier);
            if (!table.has(fallbackId) || !table.priceOf(fallbackId).hasChatPrices()
                    || !table.priceOf(fallbackId).hasCachePrices()) {
                throw new IllegalStateException("margai.ai.fallback.tier." + tier + " = " + fallbackId
                        + " has no input, output, cache_read and cache_write price in margai.ai.prices-json");
            }
            String primaryId = properties.modelFor(tier);
            if (!mirrors(fallbackId, primaryId)) {
                log.warn("margai.ai.fallback.tier.{} = {} does not name margai.ai.tier.{}.id = {}: under the fallback"
                        + " that tier would answer on another model. Expected while a pipeline profile pins the tier"
                        + " for a run; otherwise move the fallback id with the primary (DECISIONS 2026-10-20)",
                        tier, fallbackId, tier, primaryId);
            }
        }
    }

    /**
     * Whether a fallback id names the primary's model: the primary id at the start or after a dot (a
     * provider's profile prefix), then nothing but a date or a version suffix — so a primary id is not
     * mirrored by a longer id that merely begins with it.
     */
    static boolean mirrors(String fallbackId, String primaryId) {
        return Pattern.matches("(.*\\.)?" + Pattern.quote(primaryId) + "(-\\d{8})?(-v\\d+(:\\d+)?)?", fallbackId);
    }

    @Bean
    CostCalculator costCalculator(PriceTable prices, AiProperties properties) {
        return new CostCalculator(prices, properties.usdInr());
    }

    @Bean
    PromptRegistry promptRegistry(ResourcePatternResolver resolver, AiProperties properties) {
        PromptRegistry prompts = PromptRegistry.fromClasspath(resolver, properties.promptVersions());
        checkCachedPrefixes(prompts, properties);
        return prompts;
    }

    /**
     * The cache tripwire (founder ruling 2026-09-12). A prefix shorter than the model's minimum
     * cacheable length is not cached at all, and nothing in the response says so: the call simply
     * costs full input price for ever, which quietly halves the margin the cost model assumes
     * (§4.8, the 55% cache-rate target of SPEC §11). The minimums differ per model — the cheap
     * model's is four times the reasoning model's — so this is per tier, and it is a warning, not
     * a refusal: a deliberately short prompt (the difficulty router) is a fair choice, an
     * accidentally short one is not, and only the author can tell them apart.
     */
    static void checkCachedPrefixes(PromptRegistry prompts, AiProperties properties) {
        for (String name : prompts.names()) {
            if (name.startsWith(PromptRegistry.FRAGMENT_PREFIX)) {
                continue;
            }
            int estimate = FakeAiClient.tokens(prompts.systemPrefix(name));
            for (Tier tier : COMPLETION_TIERS) {
                AiProperties.Model model = properties.modelOf(tier);
                if (estimate < model.cacheMinTokens()) {
                    log.warn("prompt {} has a ~{}-token cached prefix, below the {} tier model's {}-token minimum:"
                            + " on that tier the cache point is a silent no-op and every call pays full input price"
                            + " (margai.ai.tier.{}.cache-min-tokens)", name, estimate, tier, model.cacheMinTokens(),
                            tier);
                }
            }
        }
    }
}
