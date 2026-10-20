package com.margai.ai.internal.bedrock;

import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Tier;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.CompletionClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
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
import software.amazon.awssdk.services.bedrockruntime.model.StopReason;
import software.amazon.awssdk.services.bedrockruntime.model.ThrottlingException;
import software.amazon.awssdk.services.bedrockruntime.model.TokenUsage;
import tools.jackson.databind.JsonNode;

/**
 * The Bedrock completion client (TECH_PLAN §4.1, §4.11, §15.12), wired in the {@code live} profile
 * wherever {@code CompletionRoute} sends completions to Bedrock — the fallback while
 * {@code margai.flags.ai-fallback} is on: Converse with the answer's tool for typed output, the
 * tool input decoded and validated by {@link StructuredOutput}, usage including cache read and write
 * tokens. Embeddings are not this client's: the pinned embedding client is joined to it by
 * {@code CompositeAiClient}, as it is to the Anthropic one. SDK failures become
 * {@link AiUnavailableException} with the retryable and timeout flags the retry decorator and the
 * ledger read. Model ids come from configuration.
 */
public final class BedrockAiClient implements CompletionClient {

    private final BedrockRuntimeClient runtime;
    private final Function<Tier, AiProperties.Model> models;
    private final PromptRegistry prompts;
    private final StructuredOutput codec;
    private final ConverseRequestMapper mapper;

    /**
     * @param models each tier's model and request shape — the fallback's when Bedrock serves as the
     *               fallback, the primary tiers when it is the configured provider
     */
    public BedrockAiClient(BedrockRuntimeClient runtime, AiProperties properties,
            Function<Tier, AiProperties.Model> models, PromptRegistry prompts, StructuredOutput codec) {
        this.runtime = runtime;
        this.models = models;
        this.prompts = prompts;
        this.codec = codec;
        this.mapper = new ConverseRequestMapper(codec, prompts, properties.maxOutputTokens());
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        long started = System.nanoTime();
        RenderedPrompt prompt = prompts.render(request.prompt(), request.variables());
        AiProperties.Model model = models.apply(request.tier());
        String modelId = model.id();
        ConverseRequest converse = mapper.toRequest(model, prompt, request);
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

    static Usage toUsage(TokenUsage usage) {
        if (usage == null) {
            return Usage.none();
        }
        return new Usage(zeroIfNull(usage.inputTokens()), zeroIfNull(usage.outputTokens()),
                zeroIfNull(usage.cacheReadInputTokens()), zeroIfNull(usage.cacheWriteInputTokens()));
    }

    /**
     * The answer tool's input is the answer; a response without a tool call is invalid output, and so
     * are the two the Anthropic client refuses (D14, D16): one cut off at the output-token limit — a
     * truncated tool input can still validate, a page transcription missing its last paragraphs or a
     * doubt answer missing its end, silently — and a refusal, never repaired since a retry sends the
     * same input to the same classifier. Bedrock reports its own filters as {@code content_filtered}
     * and {@code guardrail_intervened}, and a model refusal as a {@code refusal} stop it has no
     * constant for; all three are refusals here.
     */
    static JsonNode toolInput(ConverseResponse response, Usage usage, String modelId) {
        String stopReason = response.stopReasonAsString() == null ? "none" : response.stopReasonAsString();
        if (response.stopReason() == StopReason.CONTENT_FILTERED || response.stopReason() == StopReason.GUARDRAIL_INTERVENED
                || "refusal".equalsIgnoreCase(stopReason)) {
            throw new InvalidOutputException(List.of("the model refused or the content was filtered (" + stopReason
                    + ") — a classifier's decline, not a malformed answer, so it is not retried"),
                    null, usage, modelId, false);
        }
        if (response.stopReason() == StopReason.MAX_TOKENS) {
            throw new InvalidOutputException(List.of("the answer was cut off at the output-token limit ("
                    + usage.outputTokens() + " tokens) — raise margai.ai.max-output-tokens for this workload"),
                    null, usage, modelId, false);
        }
        if (response.output() != null && response.output().message() != null) {
            for (ContentBlock block : response.output().message().content()) {
                if (block.toolUse() != null) {
                    return Documents.toJson(block.toolUse().input());
                }
            }
        }
        throw new InvalidOutputException(List.of("no tool call in the response (stop reason "
                + stopReason + ")"), null, usage, modelId);
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
