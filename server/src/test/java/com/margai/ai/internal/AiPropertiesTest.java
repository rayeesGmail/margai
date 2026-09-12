package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.Tier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * TECH_PLAN §11.5, §7.3: {@code margai.ai.*} binds from application.yml, is validated at
 * startup, and every configured model is priced — a tier pointing at an unpriced model must
 * not boot. Model ids are asserted structurally, never as literals (.claude/rules/ai-layer.md).
 */
class AiPropertiesTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer())
            .withConfiguration(UserConfigurations.of(AiConfiguration.class));

    @Test
    void localDefaultsBindFromApplicationYml() {
        runner.run(context -> {
            AiProperties properties = context.getBean(AiProperties.class);
            assertThat(properties.provider()).isEqualTo(AiProperties.ANTHROPIC);
            assertThat(properties.bedrock().region()).isEqualTo("ap-south-1");
            assertThat(properties.modelFor(Tier.cheap)).isNotBlank().isEqualTo(properties.modelFor(Tier.vision));
            assertThat(properties.modelFor(Tier.reason)).isNotBlank().isNotEqualTo(properties.modelFor(Tier.cheap));
            assertThat(properties.embed().model()).isNotBlank();
            assertThat(properties.embed().provider()).isNotBlank();
            assertThat(properties.embed().dimensions()).isEqualTo(1024);
            assertThat(properties.modelFor(Tier.embed)).isEqualTo(properties.embed().model());
            assertThat(properties.usdInr()).isPositive();
            assertThat(properties.budget().userDailyPaise()).isEqualTo(2_500L);
            assertThat(properties.budget().globalDailyPaise()).isEqualTo(50_000L);
            assertThat(properties.batchMinRecords()).isEqualTo(100);
            assertThat(properties.maxOutputTokens()).isEqualTo(1024);
            assertThat(properties.callTimeout()).isEqualTo(java.time.Duration.ofSeconds(20));
            assertThat(properties.promptVersions()).containsEntry("smoke", 1);
            assertThat(properties.anthropic().apiKey()).isEmpty();
            assertThat(properties.cohere().apiKey()).isEmpty();
            assertThat(properties.cohere().baseUrl()).startsWith("https://");

            PriceTable prices = context.getBean(PriceTable.class);
            assertThat(prices.modelIds()).contains(properties.modelFor(Tier.cheap), properties.modelFor(Tier.reason),
                    properties.modelFor(Tier.vision), properties.embed().model());
            assertThat(prices.priceOf(properties.modelFor(Tier.reason)).output())
                    .isGreaterThan(prices.priceOf(properties.modelFor(Tier.cheap)).output());
            assertThat(context.getBean(CostCalculator.class)).isNotNull();
            assertThat(context.getBean(PromptRegistry.class).names()).contains("smoke");
        });
    }

    /**
     * §4.11: the request shape is per model, and "send nothing" has to be expressible — the
     * reasoning model rejects a temperature outright and the cheap one rejects an effort.
     */
    @Test
    void eachTierCarriesTheRequestShapeItsModelAccepts() {
        runner.run(context -> {
            AiProperties properties = context.getBean(AiProperties.class);
            AiProperties.Model cheap = properties.modelOf(Tier.cheap);
            AiProperties.Model reason = properties.modelOf(Tier.reason);

            assertThat(cheap.temperature()).isZero();
            assertThat(cheap.effort()).isNull();
            assertThat(reason.temperature()).isNull();
            assertThat(reason.effort()).isEqualTo(AiProperties.Effort.medium);
            assertThat(reason.thinking()).isEqualTo(AiProperties.Thinking.disabled);
            assertThat(cheap.cacheMinTokens()).isGreaterThan(reason.cacheMinTokens());
        });
    }

    /**
     * The cache tripwire's own subject (founder ruling 2026-09-12): the prompts that ship must
     * actually be cacheable on the tier they run on, or the cost model's cache rate is fiction.
     * Test-only prompts are excluded — "shipped" means present in main resources.
     */
    @Test
    void everyShippedPromptsCachedPrefixClearsTheCheapModelsMinimum() {
        runner.run(context -> {
            AiProperties properties = context.getBean(AiProperties.class);
            PromptRegistry prompts = context.getBean(PromptRegistry.class);
            int floor = properties.modelOf(Tier.cheap).cacheMinTokens();

            List<String> shipped = prompts.names().stream()
                    .filter(name -> !name.startsWith(PromptRegistry.FRAGMENT_PREFIX))
                    .filter(name -> Files.exists(Path.of("src/main/resources/prompts",
                            name + ".v" + prompts.activeVersion(name) + ".stg")))
                    .toList();

            assertThat(shipped).isNotEmpty();
            for (String name : shipped) {
                assertThat(FakeAiClient.tokens(prompts.systemPrefix(name)))
                        .as("cached prefix of prompt %s against the cheap tier's %d-token minimum", name, floor)
                        .isGreaterThanOrEqualTo(floor);
            }
        });
    }

    @Test
    void aTierWithoutItsModelsCacheMinimumDoesNotBoot() {
        runner.withPropertyValues("margai.ai.tier.cheap.cache-min-tokens=0")
                .run(context -> assertThat(context).hasFailed());
    }

    @Test
    void aTierPointingAtAnUnpricedModelDoesNotBoot() {
        runner.withPropertyValues("margai.ai.tier.reason.id=unpriced-model").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).rootCause().hasMessageContaining("unpriced-model");
        });
    }

    @Test
    void anEmbedModelWithoutAnInputPriceDoesNotBoot() {
        runner.withPropertyValues("margai.ai.embed.model=unpriced-embed").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).rootCause().hasMessageContaining("unpriced-embed");
        });
    }

    @Test
    void validationRejectsABlankProviderABlankRegionAndAZeroBudget() {
        runner.withPropertyValues("margai.ai.provider=").run(context -> assertThat(context).hasFailed());
        runner.withPropertyValues("margai.ai.bedrock.region=").run(context -> assertThat(context).hasFailed());
        runner.withPropertyValues("margai.ai.budget.user-daily-paise=0")
                .run(context -> assertThat(context).hasFailed());
        runner.withPropertyValues("margai.ai.embed.dimensions=0").run(context -> assertThat(context).hasFailed());
    }

    @Test
    void aConfiguredPromptVersionThatDoesNotExistDoesNotBoot() {
        runner.withPropertyValues("margai.ai.prompts.smoke.version=9").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).rootCause().hasMessageContaining("smoke.v9.stg");
        });
    }
}
