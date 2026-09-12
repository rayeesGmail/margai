package com.margai.ai.internal.bedrock;

import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.awscore.retry.AwsRetryStrategy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * The dormant provider (TECH_PLAN §1.2, §4.11): reachable only in the {@code live} profile with
 * {@code margai.ai.provider = bedrock}, which nothing sets while model access is direct — the
 * account's Marketplace subscription for the models is refused (DECISIONS 2026-09-12), so this
 * path is kept whole and unused rather than deleted, and is the way back if that changes.
 * Credentials come from the SDK's default chain — locally the IAM Identity Center profile F8
 * created, {@code AWS_PROFILE=margai} (TECH_PLAN §7.4); in AWS the task role; the region and the
 * call timeout are configuration. The SDK does not retry — the retry decorator owns that policy
 * (DECISIONS D5) — so a failure is never retried twice over.
 *
 * <p>Reviving it means supplying this provider's model ids and their price rows by environment:
 * the defaults in {@code application.yml} price the direct-API models only.
 */
@Configuration(proxyBeanMethods = false)
@Profile("live")
@ConditionalOnProperty(name = "margai.ai.provider", havingValue = AiProperties.BEDROCK)
class BedrockConfiguration {

    @Bean
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
            StructuredOutput codec) {
        return new InnerAiClient(AiProperties.Provider.bedrock.name(),
                new BedrockAiClient(runtime, properties, prompts, codec));
    }
}
