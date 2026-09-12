package com.margai.ai.internal.anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.core.http.Headers;
import com.anthropic.errors.AnthropicException;
import com.anthropic.errors.AnthropicIoException;
import com.anthropic.errors.AnthropicRetryableException;
import com.anthropic.errors.AnthropicServiceException;
import com.anthropic.errors.InternalServerException;
import com.anthropic.errors.RateLimitException;
import com.anthropic.models.messages.ContentBlock;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.ToolUseBlock;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.CompletionClient;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import tools.jackson.databind.JsonNode;

/**
 * The live completion client (TECH_PLAN §4.1, §4.11), active only in the {@code live} profile
 * with {@code margai.ai.provider = anthropic}: the Messages API with forced tool use for typed
 * output, the tool input decoded and validated by {@link StructuredOutput}, usage including cache
 * read and write tokens. SDK failures become {@link AiUnavailableException} with the retryable and
 * timeout flags the retry decorator and the ledger read, carrying the provider's own
 * {@code retry-after} hint when a rate limit sends one. Model ids come from configuration.
 */
public final class AnthropicAiClient implements CompletionClient {

    /** The provider's wait hint on a rate limit, in seconds. */
    static final String RETRY_AFTER_HEADER = "retry-after";

    private final AnthropicClient client;
    private final AiProperties properties;
    private final PromptRegistry prompts;
    private final StructuredOutput codec;
    private final MessageRequestMapper mapper;

    public AnthropicAiClient(AnthropicClient client, AiProperties properties, PromptRegistry prompts,
            StructuredOutput codec) {
        this.client = client;
        this.properties = properties;
        this.prompts = prompts;
        this.codec = codec;
        this.mapper = new MessageRequestMapper(codec, prompts, properties.maxOutputTokens());
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        long started = System.nanoTime();
        RenderedPrompt prompt = prompts.render(request.prompt(), request.variables());
        AiProperties.Model model = properties.modelOf(request.tier());
        MessageCreateParams params = mapper.toRequest(model, prompt, request);
        Message response;
        try {
            response = client.messages().create(params);
        } catch (AnthropicException e) {
            throw translate(e);
        }
        Usage usage = toUsage(response.usage());
        JsonNode output = toolInput(response, usage, model.id(), codec);
        T typed = codec.decode(request.outputType(), output, usage, model.id());
        return new AiResponse<>(typed, usage, model.id(), Duration.ofNanos(System.nanoTime() - started), null);
    }

    /** {@code inputTokens} excludes the cached tokens, which are priced separately (§4.8). */
    static Usage toUsage(com.anthropic.models.messages.Usage usage) {
        if (usage == null) {
            return Usage.none();
        }
        return new Usage(tokens(usage.inputTokens()), tokens(usage.outputTokens()),
                tokens(usage.cacheReadInputTokens().orElse(0L)),
                tokens(usage.cacheCreationInputTokens().orElse(0L)));
    }

    /** The forced tool's input is the answer; a response without a tool call is invalid output. */
    static JsonNode toolInput(Message response, Usage usage, String modelId, StructuredOutput codec) {
        for (ContentBlock block : response.content()) {
            Optional<ToolUseBlock> toolUse = block.toolUse();
            if (toolUse.isPresent()) {
                return codec.fromPlain(toolUse.get()._input().convert(Map.class));
            }
        }
        String stopReason = response.stopReason().map(Object::toString).orElse("none");
        throw new InvalidOutputException(List.of("no tool call in the response (stop reason " + stopReason + ")"),
                null, usage, modelId);
    }

    /**
     * SDK failures → the typed failure of §4.11: retryable for rate limits and 5xx with the
     * provider's wait hint where it sent one, timeout for a read that never returned, else
     * permanent (a bad request, a missing or refused key, an unknown model).
     */
    static AiUnavailableException translate(AnthropicException e) {
        String code = e.getClass().getSimpleName();
        String message = code + ": " + e.getMessage();
        return switch (e) {
            case RateLimitException rateLimit ->
                    AiUnavailableException.retryable(code, message, rateLimit, retryAfter(rateLimit.headers()));
            case InternalServerException server -> AiUnavailableException.retryable(code, message, server);
            case AnthropicRetryableException retryable -> AiUnavailableException.retryable(code, message, retryable);
            case AnthropicIoException io -> isTimeout(io)
                    ? AiUnavailableException.timeout(message, io)
                    : AiUnavailableException.retryable(code, message, io);
            case AnthropicServiceException service when service.statusCode() >= 500 ->
                    AiUnavailableException.retryable(code, message, service);
            default -> AiUnavailableException.permanent(code, message, e);
        };
    }

    /** The {@code retry-after} header in seconds, or null when it is absent or unreadable. */
    static Duration retryAfter(Headers headers) {
        if (headers == null) {
            return null;
        }
        for (String value : headers.values(RETRY_AFTER_HEADER)) {
            try {
                return Duration.ofMillis(Math.round(Double.parseDouble(value.trim()) * 1000));
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    /** A read that ran out of time; the SDK reports it as an I/O failure with a timeout cause. */
    static boolean isTimeout(Throwable failure) {
        for (Throwable cause = failure; cause != null; cause = cause.getCause() == cause ? null : cause.getCause()) {
            if (cause instanceof SocketTimeoutException || cause instanceof InterruptedIOException) {
                return true;
            }
        }
        return false;
    }

    private static int tokens(long count) {
        return (int) Math.min(Integer.MAX_VALUE, Math.max(0, count));
    }
}
