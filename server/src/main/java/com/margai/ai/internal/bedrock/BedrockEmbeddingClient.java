package com.margai.ai.internal.bedrock;

import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.EmbeddingClient;
import com.margai.ai.internal.StructuredOutput;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import tools.jackson.databind.JsonNode;

/**
 * Embeddings over Bedrock's InvokeModel (TECH_PLAN §4.9), separable from {@link BedrockAiClient}
 * because the two halves of the seam are two providers: since 2026-09-20 completions come from
 * Anthropic's own API while embeddings come from here, joined by {@code CompositeAiClient}.
 * {@link BedrockAiClient} delegates to this, so {@code margai.ai.provider = bedrock} still embeds
 * for itself and the dormant single-provider path is unchanged.
 *
 * <p>The wire shape is chosen by the model <em>family</em> prefix, never by an id: Titan takes
 * {@code inputText} and answers {@code embedding}, Cohere takes {@code texts} and answers
 * {@code embeddings} either plainly or keyed by type. The id itself is configuration
 * ({@code ModelIdLiteralTest}).
 */
public final class BedrockEmbeddingClient implements EmbeddingClient {

    /** The response header Bedrock sets on InvokeModel with the input token count. */
    static final String INPUT_COUNT_HEADER = "X-Amzn-Bedrock-Input-Token-Count";

    /** The family whose request and response differ from Cohere's; a prefix, not an id. */
    static final String TITAN = "amazon.titan";

    private static final Logger log = LoggerFactory.getLogger(BedrockEmbeddingClient.class);

    private final BedrockRuntimeClient runtime;
    private final AiProperties properties;
    private final StructuredOutput codec;

    public BedrockEmbeddingClient(BedrockRuntimeClient runtime, AiProperties properties, StructuredOutput codec) {
        this.runtime = runtime;
        this.properties = properties;
        this.codec = codec;
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
            throw BedrockAiClient.translate(e);
        }
        String body = response.body().asUtf8String();
        Optional<String> header = response.sdkHttpResponse().firstMatchingHeader(INPUT_COUNT_HEADER);
        Embedding embedding = parseEmbedding(modelId, body, null);
        Usage usage = new Usage(inputTokens(header, embedding, request.text()), 0, 0, 0);
        return new AiResponse<>(embedding.values(), usage, modelId, Duration.ofNanos(System.nanoTime() - started), null);
    }

    /** A parsed embedding and the token count the body carried, if the model family reports one. */
    public record Embedding(float[] values, OptionalInt bodyTokens) {
    }

    /**
     * The embedding request body per model family; the family prefix selects the wire shape, the
     * id is config.
     *
     * <p>{@code output_dimension} is sent only when the configured model accepts it — the v4 line
     * does and the v3 line errors on it, which is why it is a configuration flag beside the model
     * id rather than a branch on the model name. It is not optional in practice: v4 answers 1,536
     * dimensions unless asked otherwise, and the column is {@code vector(1024)}.
     */
    String embedBody(String modelId, EmbedRequest request) {
        if (modelId.startsWith(TITAN)) {
            return codec.toJson(Map.of("inputText", request.text(), "dimensions", properties.embed().dimensions(),
                    "normalize", true));
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("texts", List.of(request.text()));
        payload.put("input_type", request.inputType().name());
        payload.put("truncate", "END");
        if (properties.embed().sendOutputDimension()) {
            payload.put("output_dimension", properties.embed().dimensions());
        }
        return codec.toJson(payload);
    }

    /** Cohere: {@code embeddings[0]} (or {@code embeddings.float[0]}); Titan: {@code embedding} + {@code inputTextTokenCount}. */
    Embedding parseEmbedding(String modelId, String body, Usage usage) {
        JsonNode root = codec.parse(body, usage, modelId);
        JsonNode vector;
        OptionalInt bodyTokens = OptionalInt.empty();
        if (modelId.startsWith(TITAN)) {
            vector = root.path("embedding");
            JsonNode count = root.path("inputTextTokenCount");
            if (count.isIntegralNumber()) {
                bodyTokens = OptionalInt.of(count.asInt());
            }
        } else {
            JsonNode embeddings = root.path("embeddings");
            vector = embeddings.isObject() ? embeddings.path("float").path(0) : embeddings.path(0);
        }
        int dimensions = properties.embed().dimensions();
        if (!vector.isArray() || vector.size() != dimensions) {
            throw new InvalidOutputException(List.of("embedding has " + (vector.isArray() ? vector.size() : 0)
                    + " dimensions, expected " + dimensions + " (margai.ai.embed)"), body, usage, modelId);
        }
        float[] values = new float[dimensions];
        for (int i = 0; i < values.length; i++) {
            values[i] = (float) vector.get(i).asDouble();
        }
        return new Embedding(values, bodyTokens);
    }

    /**
     * Input tokens for the ledger (§4.8 "tokens from the response"): the response header, else the
     * body count (Titan), else an estimate — never silently zero, which would starve the breaker.
     * Cohere's Bedrock answer carries no count in its body, so the header is what keeps the ledger
     * billing real tokens rather than an estimate (measured 2026-09-20).
     */
    static int inputTokens(Optional<String> header, Embedding embedding, String text) {
        if (header.isPresent()) {
            return Integer.parseInt(header.get().trim());
        }
        if (embedding.bodyTokens().isPresent()) {
            return embedding.bodyTokens().getAsInt();
        }
        int estimate = Math.max(1, text.length() / 4);
        log.warn("embedding response carried no token count; estimating {} input tokens", estimate);
        return estimate;
    }
}
