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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.ApiCallAttemptTimeoutException;
import software.amazon.awssdk.core.exception.ApiCallTimeoutException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.InternalServerException;
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

    private static final Logger log = LoggerFactory.getLogger(BedrockAiClient.class);

    private final BedrockRuntimeClient runtime;
    private final AiProperties properties;
    private final PromptRegistry prompts;
    private final StructuredOutput codec;
    private final ConverseRequestMapper mapper;
    private final BedrockEmbeddingClient embeddings;

    public BedrockAiClient(BedrockRuntimeClient runtime, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec) {
        this.runtime = runtime;
        this.properties = properties;
        this.prompts = prompts;
        this.codec = codec;
        this.mapper = new ConverseRequestMapper(codec, prompts, properties.maxOutputTokens());
        this.embeddings = new BedrockEmbeddingClient(runtime, properties, codec);
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

    /**
     * Delegated to {@link BedrockEmbeddingClient}, which is also wired on its own when only the
     * embedding half of the seam is Bedrock — completions from Anthropic's own API, embeddings
     * from here (2026-09-20). Keeping one implementation means the two wirings cannot drift.
     */
    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        return embeddings.embed(request);
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
