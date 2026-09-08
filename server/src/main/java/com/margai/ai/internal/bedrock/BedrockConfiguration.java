package com.margai.ai.internal.bedrock;

import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.awscore.retry.AwsRetryStrategy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * The {@code bedrock} profile (TECH_PLAN §1.2): {@code BEDROCK_LIVE=1} locally, the task
 * definition in AWS. Credentials come from the SDK's default chain (an {@code aws login} session,
 * a named profile, or the task role in AWS);
 * the region and the call timeout are configuration. The SDK does not retry — the retry
 * decorator owns that policy (DECISIONS D5) — so a failure is never retried twice over.
 */
@Configuration(proxyBeanMethods = false)
@Profile("bedrock")
class BedrockConfiguration {

    @Bean
    BedrockRuntimeClient bedrockRuntimeClient(AiProperties properties) {
        return BedrockRuntimeClient.builder()
                .region(Region.of(properties.region()))
                .overrideConfiguration(override -> override
                        .apiCallTimeout(properties.callTimeout())
                        .apiCallAttemptTimeout(properties.callTimeout())
                        .retryStrategy(AwsRetryStrategy.doNotRetry()))
                .build();
    }

    @Bean
    InnerAiClient liveAiClient(BedrockRuntimeClient runtime, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec) {
        return new InnerAiClient("bedrock", new BedrockAiClient(runtime, properties, prompts, codec));
    }
}
