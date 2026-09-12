package com.margai.ai.internal.cohere;

import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.StructuredOutput;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;

/**
 * The live embedding client (TECH_PLAN §4.9), active only in the {@code live} profile: one text
 * per call to the provider's embed endpoint, the input type carried through as the provider's own
 * document-versus-query distinction, and the vector width asserted against
 * {@code margai.ai.embed.dimensions} — the pin that the stored vectors and the {@code vector(n)}
 * columns of §2.3 both depend on.
 *
 * <p>Called over the framework's own HTTP client rather than a provider SDK: the contract is five
 * request fields and one response array, and a dependency whose only job is that shape would be
 * a dependency to keep current for nothing (DECISIONS 2026-09-12).
 *
 * <p>{@code output_dimension} is sent only when the configured model accepts it — the v4 line and
 * newer do, the v3 line errors on it — which is why it is a configuration flag beside the model
 * id and not a code branch on the model name.
 */
public final class CohereEmbeddingClient implements EmbeddingClient {

    static final String EMBED_PATH = "/v2/embed";
    static final String RETRY_AFTER_HEADER = "Retry-After";
    /** How the endpoint truncates an input longer than the model's window. */
    static final String TRUNCATE = "END";
    static final String FLOAT = "float";

    private static final Logger log = LoggerFactory.getLogger(CohereEmbeddingClient.class);

    private final RestClient http;
    private final AiProperties.Embed embed;
    private final StructuredOutput codec;

    public CohereEmbeddingClient(RestClient http, AiProperties.Embed embed, StructuredOutput codec) {
        this.http = http;
        this.embed = embed;
        this.codec = codec;
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        long started = System.nanoTime();
        String body;
        try {
            body = http.post()
                    .uri(EMBED_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(codec.toJson(requestBody(request)))
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException e) {
            throw translate(e);
        } catch (ResourceAccessException e) {
            throw isTimeout(e)
                    ? AiUnavailableException.timeout(message(e), e)
                    : AiUnavailableException.retryable(e.getClass().getSimpleName(), message(e), e);
        }
        JsonNode root = codec.parse(body, null, embed.model());
        float[] vector = vector(root, body);
        Usage usage = new Usage(inputTokens(root, request.text()), 0, 0, 0);
        return new AiResponse<>(vector, usage, embed.model(), Duration.ofNanos(System.nanoTime() - started), null);
    }

    /** The request body of §4.9: one text, the pinned model, and the width where it is accepted. */
    Map<String, Object> requestBody(EmbedRequest request) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("model", embed.model());
        payload.put("texts", List.of(request.text()));
        payload.put("input_type", request.inputType().name());
        payload.put("embedding_types", List.of(FLOAT));
        payload.put("truncate", TRUNCATE);
        if (embed.sendOutputDimension()) {
            payload.put("output_dimension", embed.dimensions());
        }
        return payload;
    }

    /**
     * {@code embeddings.float[0]} for a typed response, {@code embeddings[0]} for the plain one,
     * checked against the configured width: a vector of the wrong length must never reach a
     * {@code vector(n)} column or a similarity comparison.
     */
    float[] vector(JsonNode root, String body) {
        JsonNode embeddings = root.path("embeddings");
        JsonNode values = embeddings.isObject() ? embeddings.path(FLOAT).path(0) : embeddings.path(0);
        if (!values.isArray() || values.size() != embed.dimensions()) {
            throw new InvalidOutputException(List.of("embedding has " + (values.isArray() ? values.size() : 0)
                    + " dimensions, expected " + embed.dimensions() + " (margai.ai.embed)"), body, null, embed.model());
        }
        float[] vector = new float[embed.dimensions()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (float) values.get(i).asDouble();
        }
        return vector;
    }

    /**
     * Input tokens for the ledger (§4.8 "tokens from the response"): the billed count, else the
     * model's own count, else an estimate — never silently zero, which would starve the breaker.
     */
    int inputTokens(JsonNode root, String text) {
        JsonNode meta = root.path("meta");
        for (String path : List.of("billed_units", "tokens")) {
            JsonNode count = meta.path(path).path("input_tokens");
            if (count.isNumber()) {
                return Math.max(1, count.asInt());
            }
        }
        int estimate = Math.max(1, text.length() / 4);
        log.warn("embedding response carried no token count; estimating {} input tokens", estimate);
        return estimate;
    }

    /** HTTP failures → the typed failure of §4.11: rate limits and 5xx retryable, the rest permanent. */
    AiUnavailableException translate(RestClientResponseException e) {
        int status = e.getStatusCode().value();
        String code = "HTTP_" + status;
        String message = code + " from the embedding provider";
        if (status == 429) {
            return AiUnavailableException.retryable(code, message, e, retryAfter(e));
        }
        if (status >= 500) {
            return AiUnavailableException.retryable(code, message, e);
        }
        return AiUnavailableException.permanent(code, message, e);
    }

    private static Duration retryAfter(RestClientResponseException e) {
        String value = e.getResponseHeaders() == null ? null : e.getResponseHeaders().getFirst(RETRY_AFTER_HEADER);
        if (value == null) {
            return null;
        }
        try {
            return Duration.ofMillis(Math.round(Double.parseDouble(value.trim()) * 1000));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static String message(Throwable failure) {
        return failure.getClass().getSimpleName() + ": " + failure.getMessage();
    }

    /** A request that ran out of time rather than a connection that failed. */
    static boolean isTimeout(Throwable failure) {
        for (Throwable cause = failure; cause != null; cause = cause.getCause() == cause ? null : cause.getCause()) {
            if (cause instanceof HttpTimeoutException || cause instanceof SocketTimeoutException
                    || cause instanceof InterruptedIOException) {
                return true;
            }
        }
        return false;
    }
}
