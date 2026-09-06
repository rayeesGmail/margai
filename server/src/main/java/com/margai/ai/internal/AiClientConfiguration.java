package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiClientInfo;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Wires the one {@link AiClient} bean as the decorator chain of TECH_PLAN §4.1, outermost
 * first: ledger → breaker → tier policy → schema validation → retry → the inner client, which
 * is {@link FakeAiClient} unless the {@code bedrock} profile supplies a Bedrock runtime client.
 * The same breaker and ledger therefore run in every profile (DEV_SPEC §13.7 item 6).
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
    AiClientInfo aiClientInfo() {
        return new AiClientInfo("fake", CHAIN);
    }

    @Bean
    AiClient aiClient(AiProperties properties, PromptRegistry prompts, StructuredOutput codec,
            ResourcePatternResolver resolver, AiCallLedger ledger, CostCalculator cost, MeterRegistry meters,
            AiClientInfo info) {
        AiClient inner = new FakeAiClient(properties, prompts, codec, resolver);
        AiClient chain = new LedgerAiClient(
                new BudgetBreakerAiClient(
                        new TierPolicyAiClient(
                                new SchemaValidatingAiClient(
                                        new RetryingAiClient(inner))),
                        ledger, properties.budget()),
                ledger, cost, properties, prompts, meters);
        log.info("AiClient chain: {}", info);
        return chain;
    }
}
