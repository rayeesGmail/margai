package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiClientInfo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Wires the one {@link AiClient} bean (TECH_PLAN §4.1). The inner client is
 * {@link FakeAiClient} unless the {@code bedrock} profile is active; the decorator chain around
 * it lands in the next task of D5.
 */
@Configuration(proxyBeanMethods = false)
class AiClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AiClientConfiguration.class);

    @Bean
    StructuredOutput structuredOutput() {
        return new StructuredOutput();
    }

    @Bean
    AiClientInfo aiClientInfo() {
        return new AiClientInfo("fake", List.of());
    }

    @Bean
    AiClient aiClient(AiProperties properties, PromptRegistry prompts, StructuredOutput codec,
            ResourcePatternResolver resolver, AiClientInfo info) {
        AiClient client = new FakeAiClient(properties, prompts, codec, resolver);
        log.info("AiClient chain: {}", info);
        return client;
    }
}
