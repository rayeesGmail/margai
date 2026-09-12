package com.margai.ai.internal.anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.CompositeAiClient;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * The completion provider in the {@code live} profile (TECH_PLAN §1.2): {@code AI_LIVE=1}
 * locally, the task definition in AWS, with {@code margai.ai.provider} choosing between this and
 * the dormant Bedrock client. The key is the whole auth model — it comes from SSM as a
 * SecureString, never from the repository — and the SDK does not retry, because the retry
 * decorator owns that policy (DECISIONS D5), so a failure is never retried twice over.
 *
 * <p>Completions and embeddings come from different providers now, so the innermost client is the
 * two of them joined ({@link CompositeAiClient}); the decorator chain above it is unchanged.
 */
@Configuration(proxyBeanMethods = false)
@Profile("live")
@ConditionalOnProperty(name = "margai.ai.provider", havingValue = AiProperties.ANTHROPIC, matchIfMissing = true)
class AnthropicConfiguration {

    @Bean
    AnthropicClient anthropicClient(AiProperties properties) {
        return AnthropicOkHttpClient.builder()
                .apiKey(key(properties))
                .maxRetries(0)
                .timeout(properties.callTimeout())
                .build();
    }

    @Bean
    InnerAiClient liveAiClient(AnthropicClient client, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec, EmbeddingClient embeddings) {
        return new InnerAiClient(AiProperties.Provider.anthropic.name(),
                new CompositeAiClient(new AnthropicAiClient(client, properties, prompts, codec), embeddings));
    }

    /** A live client without a key is a misconfiguration, not a runtime surprise. */
    private static String key(AiProperties properties) {
        String key = properties.anthropic().apiKey();
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("margai.ai.anthropic.api-key is blank: the live profile needs the "
                    + "model provider key from SSM (/margai/beta/ai/anthropic/*) or, on a laptop, from the "
                    + "untracked local environment");
        }
        return key;
    }
}
