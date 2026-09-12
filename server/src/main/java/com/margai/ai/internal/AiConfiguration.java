package com.margai.ai.internal;

import com.margai.ai.api.Tier;
import java.util.List;
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
@EnableConfigurationProperties(AiProperties.class)
class AiConfiguration {

    /** The completion tiers; {@code embed} is priced and checked separately. */
    private static final List<Tier> COMPLETION_TIERS = List.of(Tier.cheap, Tier.reason, Tier.vision);

    private static final Logger log = LoggerFactory.getLogger(AiConfiguration.class);

    @Bean
    PriceTable priceTable(AiProperties properties) {
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
        return table;
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
