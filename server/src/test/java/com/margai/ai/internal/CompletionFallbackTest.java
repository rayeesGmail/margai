package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.margai.ai.api.Tier;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.mock.env.MockEnvironment;

/**
 * The completion fallback (CS-2 §4.11, TECH_PLAN §15.12): which provider serves completions, the
 * startup refusals that keep the outage switch from failing at the outage, and the warning that keeps
 * a primary model change from leaving the fallback on the old model (founder ruling, DECISIONS
 * 2026-10-20).
 */
class CompletionFallbackTest {

    private static final String PRICES = """
            {"cheap-model": {"input": 1, "output": 5},
             "reason-model": {"input": 2, "output": 10},
             "vision-model": {"input": 1, "output": 5},
             "embed-model": {"input": 0.1},
             "global.p.cheap-model": {"input": 1, "output": 5, "cache_read": 0.1, "cache_write": 1.25},
             "global.p.reason-model": {"input": 2, "output": 10, "cache_read": 0.2, "cache_write": 2.5},
             "global.p.vision-model": {"input": 1, "output": 5, "cache_read": 0.1, "cache_write": 1.25},
             "global.p.uncached-model": {"input": 1, "output": 5}}
            """;

    private final PriceTable table = PriceTable.parse(PRICES);
    private final AiProperties mirrored = TestAiProperties.withFallback("global.p.cheap-model",
            "global.p.reason-model", "global.p.vision-model");

    // ── the route ─────────────────────────────────────────────────────────────────────────────────

    @Test
    void theFlagOffRoutesToTheProviderAndOnToTheFallbackProvider() {
        assertThat(CompletionRoute.of(new MockEnvironment()))
                .isEqualTo(new CompletionRoute(AiProperties.Provider.anthropic, false));
        assertThat(CompletionRoute.of(new MockEnvironment().withProperty("margai.flags.ai-fallback", "true")))
                .isEqualTo(new CompletionRoute(AiProperties.Provider.bedrock, true));
        assertThat(CompletionRoute.of(new MockEnvironment().withProperty("margai.ai.provider", "bedrock")))
                .isEqualTo(new CompletionRoute(AiProperties.Provider.bedrock, false));
    }

    @Test
    void theFallbacksClientNameSaysSo() {
        assertThat(new CompletionRoute(AiProperties.Provider.bedrock, true).clientName()).isEqualTo("bedrock (fallback)");
        assertThat(new CompletionRoute(AiProperties.Provider.anthropic, false).clientName()).isEqualTo("anthropic");
    }

    /** The fallback model is the primary tier's model on the fallback's id, so it keeps the primary's shape. */
    @Test
    void aFallbackTierKeepsItsPrimarysRequestShapeUnderTheFallbacksId() {
        AiProperties.Model primary = mirrored.modelOf(Tier.reason);
        AiProperties.Model fallback = mirrored.fallbackModelOf(Tier.reason);

        assertThat(fallback.id()).isEqualTo("global.p.reason-model");
        assertThat(fallback).usingRecursiveComparison().ignoringFields("id").isEqualTo(primary);
        assertThat(mirrored.fallbackModelFor(Tier.embed)).isEqualTo(mirrored.embed().model());
    }

    /** The chain's junction: the ledger stamps the fallback's ids exactly when the inner client is the fallback. */
    @Test
    void theLedgerTakesTheFallbacksIdsOnlyFromAFallbackInnerClient() {
        assertThat(AiClientConfiguration.ledgerModelIds(new InnerAiClient("bedrock (fallback)", null, true), mirrored)
                .apply(Tier.reason)).isEqualTo("global.p.reason-model");
        assertThat(AiClientConfiguration.ledgerModelIds(new InnerAiClient("anthropic", null, false), mirrored)
                .apply(Tier.reason)).isEqualTo(TestAiProperties.REASON);
    }

    // ── the startup checks ─────────────────────────────────────────────────────────────────────────

    @Test
    void theFlagOnWithNoFallbackIdsIsRefused() {
        assertThatThrownBy(() -> AiConfiguration.checkFallback(TestAiProperties.standard(), new AiFlags(true), table))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("margai.flags.ai-fallback");
        AiConfiguration.checkFallback(TestAiProperties.standard(), new AiFlags(false), table);
    }

    @Test
    void aFallbackProviderThatIsThePrimaryIsRefused() {
        AiProperties same = new AiProperties(AiProperties.Provider.anthropic, AiProperties.Provider.anthropic,
                mirrored.fallback(), mirrored.tier(), mirrored.embed(), mirrored.retrieval(), mirrored.pricesJson(),
                mirrored.usdInr(), mirrored.budget(), mirrored.batchMinRecords(), mirrored.maxOutputTokens(),
                mirrored.callTimeout(), mirrored.anthropic(), mirrored.cohere(), mirrored.bedrock(), mirrored.prompts());

        assertThatThrownBy(() -> AiConfiguration.checkFallback(same, new AiFlags(false), table))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("another provider");
    }

    /** A cached call at a zero rate would under-count exactly when the fallback carries the traffic. */
    @Test
    void aFallbackIdWithoutBothCachePricesIsRefused() {
        AiProperties uncached = TestAiProperties.withFallback("global.p.uncached-model", "global.p.reason-model",
                "global.p.vision-model");

        assertThatThrownBy(() -> AiConfiguration.checkFallback(uncached, new AiFlags(false), table))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("margai.ai.fallback.tier.cheap")
                .hasMessageContaining("cache_read");
    }

    /** The founder's warning: a primary that moved without its fallback (the Haiku 5.5 switch's risk). */
    @Test
    void aFallbackThatNoLongerNamesItsPrimaryIsWarnedNotRefused() {
        AiProperties stale = TestAiProperties.withFallback("global.p.cheap-model", "global.p.reason-model-5",
                "global.p.vision-model");
        String prices = PRICES.replace("\"global.p.uncached-model\"",
                "\"global.p.reason-model-5\": {\"input\": 2, \"output\": 10, \"cache_read\": 0.2, \"cache_write\": 2.5},"
                        + " \"global.p.uncached-model\"");

        ListAppender<ILoggingEvent> log = new ListAppender<>();
        log.start();
        Logger logger = (Logger) LoggerFactory.getLogger(AiConfiguration.class);
        logger.addAppender(log);
        try {
            AiConfiguration.checkFallback(stale, new AiFlags(false), PriceTable.parse(prices));
            AiConfiguration.checkFallback(mirrored, new AiFlags(false), table);
        } finally {
            logger.detachAppender(log);
        }

        assertThat(log.list).singleElement().extracting(ILoggingEvent::getFormattedMessage).asString()
                .contains("margai.ai.fallback.tier.reason = global.p.reason-model-5")
                .contains("reason-model");
    }

    @Test
    void aProfileMirrorsItsPrimaryOnlyWithNothingButADateOrVersionAfterIt() {
        assertThat(AiConfiguration.mirrors("global.anthropic.claude-haiku-5-5", "claude-haiku-5-5")).isTrue();
        assertThat(AiConfiguration.mirrors("apac.anthropic.claude-haiku-4-5-20251001-v1:0", "claude-haiku-4-5")).isTrue();
        assertThat(AiConfiguration.mirrors("claude-sonnet-5", "claude-sonnet-5")).isTrue();
        assertThat(AiConfiguration.mirrors("global.anthropic.claude-sonnet-5-5", "claude-sonnet-5")).isFalse();
        assertThat(AiConfiguration.mirrors("global.anthropic.claude-haiku-4-5", "claude-haiku-5-5")).isFalse();
    }

    // ── the shipped configuration ──────────────────────────────────────────────────────────────────

    /** application.yml: the flag off, Bedrock the fallback provider, every fallback id priced and mirrored. */
    @Test
    void theShippedFallbackBootsWithTheFlagOffAndMirrorsEveryTier() {
        new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withConfiguration(UserConfigurations.of(AiConfiguration.class))
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    AiProperties properties = context.getBean(AiProperties.class);
                    assertThat(context.getBean(AiFlags.class).aiFallback()).isFalse();
                    assertThat(properties.fallbackProvider()).isEqualTo(AiProperties.Provider.bedrock);
                    for (Tier tier : java.util.List.of(Tier.cheap, Tier.reason, Tier.vision)) {
                        assertThat(AiConfiguration.mirrors(properties.fallbackModelFor(tier), properties.modelFor(tier)))
                                .as(tier.name()).isTrue();
                    }
                });
    }
}
