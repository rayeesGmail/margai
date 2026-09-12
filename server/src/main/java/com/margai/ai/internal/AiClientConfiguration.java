package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiClientInfo;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Wires the one {@link AiClient} bean as the decorator chain of TECH_PLAN §4.1, outermost
 * first: ledger → breaker → tier policy → schema validation → retry → the inner client, which
 * is {@link FakeAiClient} unless the {@code live} profile supplies an {@link InnerAiClient}
 * (§1.2). The same breaker and ledger therefore run in every profile (DEV_SPEC §13.7 item 6),
 * and against every provider.
 *
 * <p>The {@code live} profile without a provider client is a refusal, not a fallback: falling back
 * to the fake there would serve fixtures as if they were answers — no retrieval grounding, no
 * numerical verification, no honest fallback (CLAUDE.md hard rules) — and the only sign would be
 * one startup log line. A provider that names nothing wireable therefore fails the context.
 */
@Configuration(proxyBeanMethods = false)
class AiClientConfiguration {

    static final List<String> CHAIN = List.of("ledger", "breaker", "tier-policy", "schema", "retry");

    /** The profile that promises a real provider (§1.2); {@code AI_LIVE=1} adds it. */
    static final String LIVE_PROFILE = "live";

    private static final Logger log = LoggerFactory.getLogger(AiClientConfiguration.class);

    @Bean
    StructuredOutput structuredOutput() {
        return new StructuredOutput();
    }

    @Bean
    AiClientInfo aiClientInfo(ObjectProvider<InnerAiClient> live, Environment environment, AiProperties properties) {
        InnerAiClient inner = live.getIfAvailable();
        requireProviderInLiveProfile(inner, environment, properties);
        return new AiClientInfo(inner == null ? AiClientInfo.FAKE : inner.name(), CHAIN);
    }

    /**
     * Under the {@code live} profile a provider client must exist. Both provider configurations are
     * conditional on {@code margai.ai.provider}, so a value that matches neither would otherwise
     * leave the chain wrapping the fake and start the application anyway.
     */
    static void requireProviderInLiveProfile(InnerAiClient inner, Environment environment, AiProperties properties) {
        if (inner == null && environment.matchesProfiles(LIVE_PROFILE)) {
            throw new IllegalStateException("the " + LIVE_PROFILE + " profile is active but margai.ai.provider = "
                    + properties.provider() + " wired no client: the fake must never stand in for a provider"
                    + " (TECH_PLAN §1.2, §4.1)");
        }
    }

    @Bean
    AiClient aiClient(AiProperties properties, PromptRegistry prompts, StructuredOutput codec,
            ResourcePatternResolver resolver, AiCallLedger ledger, CostCalculator cost, MeterRegistry meters,
            ObjectProvider<InnerAiClient> live, AiClientInfo info) {
        InnerAiClient inner = live.getIfAvailable(
                () -> new InnerAiClient(AiClientInfo.FAKE, new FakeAiClient(properties, prompts, codec, resolver)));
        AiClient chain = new LedgerAiClient(
                new BudgetBreakerAiClient(
                        new TierPolicyAiClient(
                                new SchemaValidatingAiClient(
                                        new RetryingAiClient(inner.client()))),
                        ledger, properties.budget()),
                ledger, cost, properties, prompts, meters);
        log.info("AiClient chain: {}", info);
        return chain;
    }
}
