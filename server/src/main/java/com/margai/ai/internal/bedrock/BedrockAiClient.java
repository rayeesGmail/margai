package com.margai.ai.internal.bedrock;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.ApiCallAttemptTimeoutException;
import software.amazon.awssdk.core.exception.ApiCallTimeoutException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.InternalServerException;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import software.amazon.awssdk.services.bedrockruntime.model.ModelNotReadyException;
import software.amazon.awssdk.services.bedrockruntime.model.ModelTimeoutException;
import software.amazon.awssdk.services.bedrockruntime.model.ServiceQuotaExceededException;
import software.amazon.awssdk.services.bedrockruntime.model.ServiceUnavailableException;
import software.amazon.awssdk.services.bedrockruntime.model.ThrottlingException;
import software.amazon.awssdk.services.bedrockruntime.model.TokenUsage;
import tools.jackson.databind.JsonNode;

/**
 * The live client (TECH_PLAN §4.1, §4.11), active only in the {@code bedrock} profile: Converse
 * with forced tool use for typed output, the tool input decoded and validated by
 * {@link StructuredOutput}, usage including cache read and write tokens; InvokeModel for
 * embeddings. SDK failures become {@link AiUnavailableException} with the retryable and timeout
 * flags the retry decorator and the ledger read. Model ids come from configuration.
 */
public final class BedrockAiClient implements AiClient {

    /** The response header Bedrock sets on InvokeModel with the input token count. */
    static final String INPUT_COUNT_HEADER = "X-Amzn-Bedrock-Input-Token-Count";
    static final int EMBEDDING_DIMENSIONS = 1024;

    private final BedrockRuntimeClient runtime;
    private final AiProperties properties;
    private final PromptRegistry prompts;
    private final StructuredOutput codec;
    private final ConverseRequestMapper mapper;

    public BedrockAiClient(BedrockRuntimeClient runtime, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec) {
        this.runtime = runtime;
        this.properties = properties;
        this.prompts = prompts;
        this.codec = codec;
        this.mapper = new ConverseRequestMapper(codec, properties.maxOutputTokens());
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        long started = System.nanoTime();
        RenderedPrompt prompt = prompts.render(request.prompt(), request.variables());
        String modelId = properties.modelFor(request.tier());
        ConverseRequest converse = mapper.toRequest(modelId, prompt, request);
        ConverseResponse response;
        try {
            response = runtime.converse(converse);
        } catch (SdkException e) {
            throw translate(e);
        }
        Usage usage = toUsage(response.usage());
        JsonNode output = toolInput(response, usage, modelId);
        T typed = codec.decode(request.outputType(), output, usage, modelId);
        return new AiResponse<>(typed, usage, modelId, Duration.ofNanos(System.nanoTime() - started), null);
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        long started = System.nanoTime();
        String modelId = properties.embed().model();
        InvokeModelResponse response;
        try {
            response = runtime.invokeModel(InvokeModelRequest.builder()
                    .modelId(modelId)
                    .contentType("application/json")
                    .accept("application/json")
                    .body(SdkBytes.fromUtf8String(embedBody(modelId, request)))
                    .build());
        } catch (SdkException e) {
            throw translate(e);
        }
        String body = response.body().asUtf8String();
        Optional<String> header = response.sdkHttpResponse().firstMatchingHeader(INPUT_COUNT_HEADER);
        float[] vector = parseEmbedding(modelId, body, header, null);
        Usage usage = new Usage(header.map(Integer::parseInt).orElse(0), 0, 0, 0);
        return new AiResponse<>(vector, usage, modelId, Duration.ofNanos(System.nanoTime() - started), null);
    }

    /** The embedding request body per model family; the family prefix selects the wire shape, the id is config. */
    String embedBody(String modelId, EmbedRequest request) {
        if (modelId.startsWith("amazon.titan")) {
            return codec.toJson(Map.of("inputText", request.text(), "dimensions", EMBEDDING_DIMENSIONS,
                    "normalize", true));
        }
        return codec.toJson(Map.of("texts", List.of(request.text()), "input_type", request.inputType().name(),
                "truncate", "END"));
    }

    /** Cohere: {@code embeddings[0]} (or {@code embeddings.float[0]}); Titan: {@code embedding}. */
    float[] parseEmbedding(String modelId, String body, Optional<String> countHeader, Usage usage) {
        JsonNode root = codec.parse(body, usage, modelId);
        JsonNode vector;
        if (modelId.startsWith("amazon.titan")) {
            vector = root.path("embedding");
        } else {
            JsonNode embeddings = root.path("embeddings");
            vector = embeddings.isObject() ? embeddings.path("float").path(0) : embeddings.path(0);
        }
        if (!vector.isArray() || vector.size() != EMBEDDING_DIMENSIONS) {
            throw new InvalidOutputException(List.of("embedding has " + (vector.isArray() ? vector.size() : 0)
                    + " dimensions, expected " + EMBEDDING_DIMENSIONS), body, usage, modelId);
        }
        float[] values = new float[EMBEDDING_DIMENSIONS];
        for (int i = 0; i < values.length; i++) {
            values[i] = (float) vector.get(i).asDouble();
        }
        return values;
    }

    static Usage toUsage(TokenUsage usage) {
        if (usage == null) {
            return Usage.none();
        }
        return new Usage(zeroIfNull(usage.inputTokens()), zeroIfNull(usage.outputTokens()),
                zeroIfNull(usage.cacheReadInputTokens()), zeroIfNull(usage.cacheWriteInputTokens()));
    }

    /** The forced tool's input is the answer; a response without a tool call is invalid output. */
    static JsonNode toolInput(ConverseResponse response, Usage usage, String modelId) {
        if (response.output() != null && response.output().message() != null) {
            for (ContentBlock block : response.output().message().content()) {
                if (block.toolUse() != null) {
                    return Documents.toJson(block.toolUse().input());
                }
            }
        }
        throw new InvalidOutputException(List.of("no tool call in the response (stop reason "
                + response.stopReasonAsString() + ")"), null, usage, modelId);
    }

    /** SDK failures → the typed failure of §4.11: retryable for throttling and 5xx, timeout, else permanent. */
    static AiUnavailableException translate(SdkException e) {
        String code = e.getClass().getSimpleName();
        String message = code + ": " + e.getMessage();
        return switch (e) {
            case ApiCallTimeoutException timeout -> AiUnavailableException.timeout(message, timeout);
            case ApiCallAttemptTimeoutException timeout -> AiUnavailableException.timeout(message, timeout);
            case ModelTimeoutException timeout -> AiUnavailableException.timeout(message, timeout);
            case ThrottlingException t -> AiUnavailableException.retryable(code, message, t);
            case ServiceUnavailableException s -> AiUnavailableException.retryable(code, message, s);
            case InternalServerException i -> AiUnavailableException.retryable(code, message, i);
            case ModelNotReadyException m -> AiUnavailableException.retryable(code, message, m);
            case ServiceQuotaExceededException q -> AiUnavailableException.retryable(code, message, q);
            case AwsServiceException service when service.statusCode() >= 500 ->
                    AiUnavailableException.retryable(code, message, service);
            default -> AiUnavailableException.permanent(code, message, e);
        };
    }

    private static int zeroIfNull(Integer value) {
        return value == null ? 0 : value;
    }
}
