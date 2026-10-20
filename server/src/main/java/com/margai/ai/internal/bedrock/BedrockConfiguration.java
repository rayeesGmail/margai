package com.margai.ai.internal.bedrock;

import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.CompletionRoute;
import com.margai.ai.internal.CompositeAiClient;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.awscore.retry.AwsRetryStrategy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * Bedrock serving completions (TECH_PLAN §4.11, §15.12): in the {@code live} profile wherever
 * {@link CompletionRoute} sends them here — the completion fallback while
 * {@code margai.flags.ai-fallback} is on (CS-2 §4.11, ruling Q4: switched by configuration and a
 * restart during an Anthropic outage, never the primary), or {@code margai.ai.provider = bedrock},
 * which nothing sets. Under the fallback each tier calls the fallback's model id, an inference
 * profile for the primary tier's model, in that model's request shape. Embeddings are the pinned
 * embedding client's, joined as they are to the Anthropic client, so the switch never touches them.
 *
 * <p>Credentials come from the SDK's default chain — locally the IAM Identity Center profile F8
 * created, {@code AWS_PROFILE=margai} (TECH_PLAN §7.4); in AWS the task role; the region and the
 * call timeout are configuration. The SDK does not retry — the retry decorator owns that policy
 * (DECISIONS D5) — so a failure is never retried twice over. The fallback is called ready only
 * after the founder's smoke of one call per runtime tier (DECISIONS 2026-10-20).
 */
@Configuration(proxyBeanMethods = false)
@Profile("live")
@Conditional(CompletionRoute.OnBedrock.class)
class BedrockConfiguration {

    /**
     * Shared with {@link BedrockEmbeddingConfiguration}, which declares the same client: under the
     * fallback both are active, and whichever registers first serves both — conditional on both sides,
     * so the outage switch does not depend on the order the two are scanned in.
     */
    @Bean
    @ConditionalOnMissingBean(BedrockRuntimeClient.class)
    BedrockRuntimeClient bedrockRuntimeClient(AiProperties properties) {
        return BedrockRuntimeClient.builder()
                .region(Region.of(properties.bedrock().region()))
                .overrideConfiguration(override -> override
                        .apiCallTimeout(properties.callTimeout())
                        .apiCallAttemptTimeout(properties.callTimeout())
                        .retryStrategy(AwsRetryStrategy.doNotRetry()))
                .build();
    }

    @Bean
    InnerAiClient liveAiClient(BedrockRuntimeClient runtime, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec, EmbeddingClient embeddings, Environment environment) {
        CompletionRoute route = CompletionRoute.of(environment);
        BedrockAiClient completions = new BedrockAiClient(runtime, properties,
                route.fallback() ? properties::fallbackModelOf : properties::modelOf, prompts, codec);
        return new InnerAiClient(route.clientName(), new CompositeAiClient(completions, embeddings), route.fallback());
    }
}
