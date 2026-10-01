package com.margai.ai.internal.bedrock;

import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.StructuredOutput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.awscore.retry.AwsRetryStrategy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * The embedding half of the seam on Bedrock (TECH_PLAN §4.9), wired on
 * {@code margai.ai.embed.provider} independently of the completion provider — so completions can
 * stay on Anthropic's own API while embeddings come from here, joined by {@code CompositeAiClient}.
 *
 * <p>Why this exists, 2026-09-20 (DECISIONS): the Cohere direct key is a trial key capped at 1,000
 * calls a month and the corpus needs about nine thousand, and no payment method can be attached to
 * that account. Bedrock serves the <em>same</em> model — so the pin's meaning and D15's measured
 * result survive the move — and it bills through the AWS account that already pays for S3 and SES.
 * It also removes a secret rather than adding one: embeddings authenticate by IAM and there is no
 * key to hold or rotate.
 *
 * <p>Note this is not the dormant Bedrock path of {@link BedrockConfiguration}, which is the whole
 * provider and is still unreachable — the Anthropic models there need a Marketplace subscription
 * the account cannot buy (DECISIONS 2026-09-12). Serverless embedding models are a different
 * mechanism and are reachable today.
 *
 * <p>Credentials come from the SDK's default chain — {@code AWS_PROFILE=margai} on a laptop
 * (TECH_PLAN §7.4), the task role in AWS — and the SDK does not retry, because the retry decorator
 * owns that policy (DECISIONS D5).
 */
@Configuration(proxyBeanMethods = false)
@Profile("live")
@ConditionalOnProperty(name = "margai.ai.embed.provider", havingValue = AiProperties.BEDROCK)
class BedrockEmbeddingConfiguration {

    /**
     * Shared with {@link BedrockConfiguration} when the completion provider is Bedrock too: that
     * configuration declares the same bean, and only one of them may win. Conditional rather than
     * duplicated, so "both halves on Bedrock" stays a supported configuration rather than a
     * context that refuses to start.
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
    EmbeddingClient embeddingClient(BedrockRuntimeClient runtime, AiProperties properties, StructuredOutput codec) {
        return new BedrockEmbeddingClient(runtime, properties, codec);
    }
}
