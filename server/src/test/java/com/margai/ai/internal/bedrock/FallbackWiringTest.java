package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;

import com.anthropic.client.AnthropicClient;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.StructuredOutput;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * The D70 drill's path, booted (TECH_PLAN §15.12): under the `live` profile with the shipped
 * configuration, the flag decides which provider configuration wires — on, Bedrock as the fallback,
 * no Anthropic client and so no key needed; off, Anthropic — and the Bedrock runtime client both
 * Bedrock configurations declare resolves to one bean whichever registers first.
 */
class FallbackWiringTest {

    private static final Class<?> AI = load("com.margai.ai.internal.AiConfiguration");
    private static final Class<?> ANTHROPIC = load("com.margai.ai.internal.anthropic.AnthropicConfiguration");

    private static ApplicationContextRunner runner(Class<?>... configurations) {
        return new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(context -> context.getEnvironment().setActiveProfiles("live"))
                .withBean(StructuredOutput.class, StructuredOutput::new)
                .withConfiguration(UserConfigurations.of(configurations));
    }

    @Test
    void theFlagOnWiresBedrockAsTheFallbackInEitherRegistrationOrder() {
        for (Class<?>[] order : new Class<?>[][] {
                {AI, ANTHROPIC, BedrockConfiguration.class, BedrockEmbeddingConfiguration.class},
                {AI, ANTHROPIC, BedrockEmbeddingConfiguration.class, BedrockConfiguration.class}}) {
            runner(order).withPropertyValues("margai.flags.ai-fallback=true").run(context -> {
                assertThat(context).hasNotFailed();
                assertThat(context).hasSingleBean(BedrockRuntimeClient.class);
                assertThat(context).doesNotHaveBean(AnthropicClient.class);
                InnerAiClient inner = context.getBean(InnerAiClient.class);
                assertThat(inner.name()).isEqualTo("bedrock (fallback)");
                assertThat(inner.fallback()).isTrue();
            });
        }
    }

    @Test
    void theFlagOffWiresAnthropicWithBedrockForEmbeddingsOnly() {
        runner(AI, ANTHROPIC, BedrockConfiguration.class, BedrockEmbeddingConfiguration.class)
                .withPropertyValues("margai.ai.anthropic.api-key=placeholder-for-the-builder")
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context).hasSingleBean(BedrockRuntimeClient.class);
                    assertThat(context).hasSingleBean(AnthropicClient.class);
                    InnerAiClient inner = context.getBean(InnerAiClient.class);
                    assertThat(inner.name()).isEqualTo("anthropic");
                    assertThat(inner.fallback()).isFalse();
                });
    }

    private static Class<?> load(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
