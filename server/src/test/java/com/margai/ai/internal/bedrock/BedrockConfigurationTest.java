package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.InnerAiClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import com.margai.ai.internal.TestAiProperties;
import com.margai.ai.tasks.SmokeAnswer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mock.env.MockEnvironment;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseOutput;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.StopReason;
import software.amazon.awssdk.services.bedrockruntime.model.ToolUseBlock;

/**
 * Bedrock serving completions (TECH_PLAN §15.12): under `margai.flags.ai-fallback` the inner client says
 * it is the fallback, calls the fallback's model ids, and is joined to the pinned embedding client
 * rather than embedding for itself; as the configured provider it calls the primary tiers' ids.
 */
class BedrockConfigurationTest {

    private final List<ConverseRequest> sent = new ArrayList<>();
    private final AiProperties properties = TestAiProperties.withFallback("global.p.cheap-model",
            "global.p.reason-model", "global.p.vision-model");
    private final PromptRegistry prompts =
            PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of());
    private final EmbeddingClient pinned = request -> {
        throw new UnsupportedOperationException("the pinned embedding client, not called here");
    };

    @Test
    void underTheFlagItIsTheFallbackAndCallsTheFallbacksModel() {
        InnerAiClient inner = new BedrockConfiguration().liveAiClient(runtime(), properties, prompts,
                new StructuredOutput(), pinned, new MockEnvironment().withProperty("margai.flags.ai-fallback", "true"));

        assertThat(inner.name()).isEqualTo("bedrock (fallback)");
        assertThat(inner.fallback()).isTrue();
        assertThat(inner.client().complete(request(Tier.cheap)).modelId()).isEqualTo("global.p.cheap-model");
        assertThat(sent).singleElement().extracting(ConverseRequest::modelId).isEqualTo("global.p.cheap-model");
    }

    @Test
    void asTheConfiguredProviderItCallsThePrimaryTiersModel() {
        InnerAiClient inner = new BedrockConfiguration().liveAiClient(runtime(), properties, prompts,
                new StructuredOutput(), pinned, new MockEnvironment().withProperty("margai.ai.provider", "bedrock"));

        assertThat(inner.name()).isEqualTo("bedrock");
        assertThat(inner.fallback()).isFalse();
        assertThat(inner.client().complete(request(Tier.reason)).modelId()).isEqualTo(TestAiProperties.REASON);
    }

    private static AiRequest<SmokeAnswer> request(Tier tier) {
        return AiRequest.of(AiFeature.smoke, tier, PromptRef.named("smoke"), Map.of("number", 7), SmokeAnswer.class,
                AiCallContext.system("t"));
    }

    private BedrockRuntimeClient runtime() {
        return new BedrockRuntimeClient() {
            @Override
            public String serviceName() {
                return "bedrock-runtime";
            }

            @Override
            public void close() {
            }

            @Override
            public ConverseResponse converse(ConverseRequest request) {
                sent.add(request);
                return ConverseResponse.builder()
                        .output(ConverseOutput.fromMessage(Message.builder().content(ContentBlock.fromToolUse(
                                ToolUseBlock.builder().toolUseId("t1").name("echo")
                                        .input(Document.fromMap(Map.of("greeting", Document.fromString("ok"),
                                                "number", Document.fromNumber(7))))
                                        .build())).build()))
                        .stopReason(StopReason.TOOL_USE)
                        .build();
            }
        };
    }
}
