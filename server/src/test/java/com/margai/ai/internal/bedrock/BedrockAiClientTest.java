package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.StructuredOutput;
import com.margai.ai.internal.TestAiProperties;
import com.margai.ai.tasks.SmokeAnswer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.core.exception.ApiCallTimeoutException;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException;
import software.amazon.awssdk.services.bedrockruntime.model.BedrockRuntimeException;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseOutput;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.ModelTimeoutException;
import software.amazon.awssdk.services.bedrockruntime.model.StopReason;
import software.amazon.awssdk.services.bedrockruntime.model.ThrottlingException;
import software.amazon.awssdk.services.bedrockruntime.model.TokenUsage;
import software.amazon.awssdk.services.bedrockruntime.model.ToolUseBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ValidationException;
import tools.jackson.databind.JsonNode;

/**
 * TECH_PLAN §4.11 without the network: usage mapping incl. cache tokens, the tool input as the
 * typed answer, a response without a tool call as invalid output, SDK failures classified for
 * the retry decorator and the ledger. The embedding wire shapes are
 * {@link BedrockEmbeddingClientTest}'s since the halves were split (2026-09-20).
 */
class BedrockAiClientTest {

    private static final Usage USAGE = new Usage(14, 4, 4_500, 0);

    private final StructuredOutput codec = new StructuredOutput();

    private static ConverseResponse response(ContentBlock... content) {
        return ConverseResponse.builder()
                .output(ConverseOutput.fromMessage(Message.builder().content(content).build()))
                .stopReason(StopReason.TOOL_USE)
                .usage(TokenUsage.builder().inputTokens(14).outputTokens(4).cacheReadInputTokens(4_500).build())
                .build();
    }

    @Test
    void usageIncludesCacheTokensAndTreatsMissingCountsAsZero() {
        assertThat(BedrockAiClient.toUsage(TokenUsage.builder().inputTokens(14).outputTokens(4)
                .cacheReadInputTokens(4_500).cacheWriteInputTokens(null).build())).isEqualTo(USAGE);
        assertThat(BedrockAiClient.toUsage(null)).isEqualTo(Usage.none());
    }

    @Test
    void theToolInputIsTheTypedAnswer() {
        ConverseResponse converse = response(
                ContentBlock.fromText("Calling the tool."),
                ContentBlock.fromToolUse(ToolUseBlock.builder().toolUseId("t1").name("smoke")
                        .input(Document.fromMap(Map.of("greeting", Document.fromString("ok"), "number", Document.fromNumber(7))))
                        .build()));

        JsonNode input = BedrockAiClient.toolInput(converse, USAGE, "m");

        assertThat(codec.decode(SmokeAnswer.class, input, USAGE, "m")).isEqualTo(new SmokeAnswer("ok", 7));
    }

    @Test
    void aResponseWithoutAToolCallIsInvalidOutputThatStillBillsItsTokens() {
        ConverseResponse converse = ConverseResponse.builder()
                .output(ConverseOutput.fromMessage(Message.builder().content(ContentBlock.fromText("Sure!")).build()))
                .stopReason(StopReason.END_TURN)
                .build();

        assertThatThrownBy(() -> BedrockAiClient.toolInput(converse, USAGE, "m"))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("end_turn")
                .satisfies(e -> assertThat(((InvalidOutputException) e).usage()).isEqualTo(USAGE));
    }

    /** As on the primary (D14): a truncated tool input can still validate, so the cut-off itself is refused. */
    @Test
    void aResponseCutOffAtTheTokenLimitIsInvalidOutputNamingTheKnobAndNotRepaired() {
        ConverseResponse converse = ConverseResponse.builder()
                .output(ConverseOutput.fromMessage(Message.builder().content(ContentBlock.fromToolUse(
                        ToolUseBlock.builder().toolUseId("t1").name("smoke")
                                .input(Document.fromMap(Map.of("greeting", Document.fromString("o"),
                                        "number", Document.fromNumber(7))))
                                .build())).build()))
                .stopReason(StopReason.MAX_TOKENS)
                .build();

        assertThatThrownBy(() -> BedrockAiClient.toolInput(converse, USAGE, "m"))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("margai.ai.max-output-tokens")
                .satisfies(e -> assertThat(((InvalidOutputException) e).repairable()).isFalse());
    }

    /** As on the primary (D16): a refusal or a filter is a decline, so it costs no paid repair call. */
    @Test
    void aRefusalOrAFilterIsInvalidOutputThatIsNotRepaired() {
        for (String stop : List.of("content_filtered", "guardrail_intervened", "refusal")) {
            ConverseResponse converse = ConverseResponse.builder()
                    .output(ConverseOutput.fromMessage(Message.builder().content(ContentBlock.fromText("No.")).build()))
                    .stopReason(stop)
                    .build();

            assertThatThrownBy(() -> BedrockAiClient.toolInput(converse, USAGE, "m"))
                    .as(stop)
                    .isInstanceOf(InvalidOutputException.class)
                    .hasMessageContaining(stop)
                    .satisfies(e -> assertThat(((InvalidOutputException) e).repairable()).isFalse());
        }
    }

    @Test
    void sdkFailuresAreClassifiedForTheRetryDecoratorAndTheLedger() {
        AiUnavailableException throttled = BedrockAiClient.translate(ThrottlingException.builder().message("slow").build());
        assertThat(throttled.isRetryable()).isTrue();
        assertThat(throttled.isTimeout()).isFalse();
        assertThat(throttled.code()).isEqualTo("ThrottlingException");

        assertThat(BedrockAiClient.translate(BedrockRuntimeException.builder().statusCode(503).message("gw").build())
                .isRetryable()).isTrue();

        AiUnavailableException timeout = BedrockAiClient.translate(ApiCallTimeoutException.create(20_000));
        assertThat(timeout.isTimeout()).isTrue();
        assertThat(timeout.isRetryable()).isFalse();
        assertThat(BedrockAiClient.translate(ModelTimeoutException.builder().message("m").build()).isTimeout()).isTrue();

        AiUnavailableException denied = BedrockAiClient.translate(AccessDeniedException.builder().statusCode(403).message("no").build());
        assertThat(denied.isRetryable()).isFalse();
        assertThat(denied.code()).isEqualTo("AccessDeniedException");
        assertThat(BedrockAiClient.translate(ValidationException.builder().statusCode(400).message("bad").build())
                .isRetryable()).isFalse();
    }

    /**
     * Under the fallback the tier's model is the fallback's (TECH_PLAN §15.12): the request goes to that
     * profile and the response — and so the ledger row — names it.
     */
    @Test
    void callsTheModelTheTiersModelFunctionNames() {
        List<ConverseRequest> sent = new ArrayList<>();
        BedrockRuntimeClient runtime = new BedrockRuntimeClient() {
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
                return response(ContentBlock.fromToolUse(ToolUseBlock.builder().toolUseId("t1").name("echo")
                        .input(Document.fromMap(Map.of("greeting", Document.fromString("ok"),
                                "number", Document.fromNumber(7))))
                        .build()));
            }
        };
        AiProperties properties = TestAiProperties.withFallback("global.p.cheap-model", "global.p.reason-model",
                "global.p.vision-model");
        BedrockAiClient client = new BedrockAiClient(runtime, properties, properties::fallbackModelOf,
                PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of()), codec);

        AiResponse<SmokeAnswer> answer = client.complete(AiRequest.of(AiFeature.smoke, Tier.reason,
                PromptRef.named("smoke"), Map.of("number", 7), SmokeAnswer.class, AiCallContext.system("t")));

        assertThat(sent).singleElement().extracting(ConverseRequest::modelId).isEqualTo("global.p.reason-model");
        assertThat(answer.modelId()).isEqualTo("global.p.reason-model");
        assertThat(answer.output()).isEqualTo(new SmokeAnswer("ok", 7));
    }
}
