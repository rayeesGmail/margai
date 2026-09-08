package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.Tier;
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
            assertThat(properties.region()).isEqualTo("ap-south-1");
            assertThat(properties.tier().cheap()).isNotBlank().isEqualTo(properties.tier().vision());
            assertThat(properties.tier().reason()).isNotBlank().isNotEqualTo(properties.tier().cheap());
            assertThat(properties.embed().model()).isNotBlank();
            assertThat(properties.modelFor(Tier.embed)).isEqualTo(properties.embed().model());
            assertThat(properties.usdInr()).isPositive();
            assertThat(properties.budget().userDailyPaise()).isEqualTo(2_500L);
            assertThat(properties.budget().globalDailyPaise()).isEqualTo(50_000L);
            assertThat(properties.batchMinRecords()).isEqualTo(100);
            assertThat(properties.maxOutputTokens()).isEqualTo(1024);
            assertThat(properties.callTimeout()).isEqualTo(java.time.Duration.ofSeconds(20));
            assertThat(properties.promptVersions()).containsEntry("smoke", 1);

            PriceTable prices = context.getBean(PriceTable.class);
            assertThat(prices.modelIds()).contains(properties.tier().cheap(), properties.tier().reason(),
                    properties.tier().vision(), properties.embed().model());
            assertThat(prices.priceOf(properties.tier().reason()).output())
                    .isGreaterThan(prices.priceOf(properties.tier().cheap()).output());
            assertThat(context.getBean(CostCalculator.class)).isNotNull();
            assertThat(context.getBean(PromptRegistry.class).names()).contains("smoke");
        });
    }

    @Test
    void aTierPointingAtAnUnpricedModelDoesNotBoot() {
        runner.withPropertyValues("margai.ai.tier.reason=unpriced-model").run(context -> {
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
    void validationRejectsABlankRegionAndAZeroBudget() {
        runner.withPropertyValues("margai.ai.region=").run(context -> assertThat(context).hasFailed());
        runner.withPropertyValues("margai.ai.budget.user-daily-paise=0")
                .run(context -> assertThat(context).hasFailed());
    }

    @Test
    void aConfiguredPromptVersionThatDoesNotExistDoesNotBoot() {
        runner.withPropertyValues("margai.ai.prompts.smoke.version=9").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).rootCause().hasMessageContaining("smoke.v9.stg");
        });
    }
}
