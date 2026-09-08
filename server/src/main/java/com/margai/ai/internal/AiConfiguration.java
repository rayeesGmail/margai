package com.margai.ai.internal;

import com.margai.ai.api.Tier;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Configuration-derived beans of the ai module (TECH_PLAN §4.8, §4.12, §11.5): the price
 * table, checked at startup so every configured model can be billed, the cost calculator and
 * the prompt registry. The client chain itself is wired in {@code AiClientConfiguration}.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AiProperties.class)
class AiConfiguration {

    @Bean
    PriceTable priceTable(AiProperties properties) {
        PriceTable table = PriceTable.parse(properties.pricesJson());
        for (Tier tier : List.of(Tier.cheap, Tier.reason, Tier.vision)) {
            String modelId = properties.modelFor(tier);
            if (!table.has(modelId) || !table.priceOf(modelId).hasChatPrices()) {
                throw new IllegalStateException("margai.ai.tier." + tier + " = " + modelId
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
        return PromptRegistry.fromClasspath(resolver, properties.promptVersions());
    }
}
