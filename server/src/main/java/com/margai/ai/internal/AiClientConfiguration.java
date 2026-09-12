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
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Wires the one {@link AiClient} bean as the decorator chain of TECH_PLAN §4.1, outermost
 * first: ledger → breaker → tier policy → schema validation → retry → the inner client, which
 * is {@link FakeAiClient} unless the {@code live} profile supplies an {@link InnerAiClient}
 * (§1.2). The same breaker and ledger therefore run in every profile (DEV_SPEC §13.7 item 6),
 * and against every provider.
 */
@Configuration(proxyBeanMethods = false)
class AiClientConfiguration {

    static final List<String> CHAIN = List.of("ledger", "breaker", "tier-policy", "schema", "retry");

    private static final Logger log = LoggerFactory.getLogger(AiClientConfiguration.class);

    @Bean
    StructuredOutput structuredOutput() {
        return new StructuredOutput();
    }

    @Bean
    AiClientInfo aiClientInfo(ObjectProvider<InnerAiClient> live) {
        InnerAiClient inner = live.getIfAvailable();
        return new AiClientInfo(inner == null ? AiClientInfo.FAKE : inner.name(), CHAIN);
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
