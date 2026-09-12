package com.margai.ai.internal.cohere;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.StructuredOutput;
import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import tools.jackson.databind.JsonNode;

/**
 * TECH_PLAN §4.9: the request carries the pinned model, the provider's own document-versus-query
 * input type and the width only where the model accepts it; the response is checked against that
 * width before a vector can reach a {@code vector(n)} column; the ledger never gets a zero token
 * count; and HTTP failures become the typed failure of §4.11.
 */
class CohereEmbeddingClientTest {

    private static final int DIMENSIONS = 4;

    private final StructuredOutput codec = new StructuredOutput();

    private CohereEmbeddingClient client(boolean sendOutputDimension) {
        return new CohereEmbeddingClient(null,
                new AiProperties.Embed("cohere", "embed-test", DIMENSIONS, sendOutputDimension), codec);
    }

    private static EmbedRequest request() {
        return new EmbedRequest(AiFeature.embed, "hello", EmbedRequest.InputType.search_query,
                AiCallContext.system("r"));
    }

    @Test
    void theRequestPinsTheModelTheInputTypeAndTheWidth() {
        Map<String, Object> body = client(true).requestBody(request());

        assertThat(body).containsEntry("model", "embed-test")
                .containsEntry("input_type", "search_query")
                .containsEntry("truncate", CohereEmbeddingClient.TRUNCATE)
                .containsEntry("output_dimension", DIMENSIONS);
        assertThat(body.get("texts")).isEqualTo(java.util.List.of("hello"));
        assertThat(body.get("embedding_types")).isEqualTo(java.util.List.of(CohereEmbeddingClient.FLOAT));
    }

    /** The v3 line errors on an explicit width, so the swap back is configuration, not code. */
    @Test
    void theWidthIsOmittedWhenTheModelDoesNotAcceptIt() {
        assertThat(client(false).requestBody(request())).doesNotContainKey("output_dimension");
    }

    @Test
    void bothResponseShapesParseAndAWrongWidthIsInvalidOutput() {
        CohereEmbeddingClient client = client(true);

        assertThat(client.vector(parse("{\"embeddings\": {\"float\": [[1.0, 0.0, 0.0, 0.0]]}}"), "b"))
                .containsExactly(1f, 0f, 0f, 0f);
        assertThat(client.vector(parse("{\"embeddings\": [[0.0, 1.0, 0.0, 0.0]]}"), "b"))
                .containsExactly(0f, 1f, 0f, 0f);

        assertThatThrownBy(() -> client.vector(parse("{\"embeddings\": [[0.1, 0.2]]}"), "b"))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("2 dimensions");
        assertThatThrownBy(() -> client.vector(parse("{\"embeddings\": {}}"), "b"))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("0 dimensions");
    }

    @Test
    void inputTokensComeFromTheBilledCountThenTheModelsCountThenAnEstimate() {
        CohereEmbeddingClient client = client(true);

        assertThat(client.inputTokens(parse("{\"meta\": {\"billed_units\": {\"input_tokens\": 3}}}"), "hello"))
                .isEqualTo(3);
        assertThat(client.inputTokens(parse("{\"meta\": {\"tokens\": {\"input_tokens\": 2}}}"), "hello"))
                .isEqualTo(2);
        assertThat(client.inputTokens(parse("{}"), "hello world!")).isEqualTo(3);
        assertThat(client.inputTokens(parse("{\"meta\": {}}"), "hi")).isEqualTo(1);
    }

    @Test
    void rateLimitsAndServerFailuresRetryAndTheRestDoNot() {
        CohereEmbeddingClient client = client(true);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CohereEmbeddingClient.RETRY_AFTER_HEADER, "2");

        AiUnavailableException throttled = client.translate(HttpClientErrorException.create(
                HttpStatus.TOO_MANY_REQUESTS, "slow down", headers, new byte[0], null));
        assertThat(throttled.isRetryable()).isTrue();
        assertThat(throttled.retryAfter()).hasValue(Duration.ofSeconds(2));
        assertThat(throttled.code()).isEqualTo("HTTP_429");

        assertThat(client.translate(HttpServerErrorException.create(HttpStatus.BAD_GATEWAY, "down",
                new HttpHeaders(), new byte[0], null)).isRetryable()).isTrue();

        AiUnavailableException unauthorized = client.translate(HttpClientErrorException.create(
                HttpStatus.UNAUTHORIZED, "no", new HttpHeaders(), new byte[0], null));
        assertThat(unauthorized.isRetryable()).isFalse();
        assertThat(unauthorized.code()).isEqualTo("HTTP_401");
    }

    @Test
    void aRequestThatRanOutOfTimeIsATimeoutAndAConnectionFailureIsNot() {
        assertThat(CohereEmbeddingClient.isTimeout(
                new ResourceAccessException("timed out", new HttpTimeoutException("t")))).isTrue();
        assertThat(CohereEmbeddingClient.isTimeout(
                new ResourceAccessException("refused", new IOException("refused")))).isFalse();
    }

    private JsonNode parse(String json) {
        return codec.parse(json, null, "embed-test");
    }
}
