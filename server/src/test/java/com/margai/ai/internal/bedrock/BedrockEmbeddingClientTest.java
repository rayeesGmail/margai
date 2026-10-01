package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.StructuredOutput;
import com.margai.ai.internal.TestAiProperties;
import java.util.Optional;
import java.util.OptionalInt;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

/**
 * Embeddings over Bedrock's InvokeModel: both wire shapes by model family, the width the pin
 * demands, and the token fallback the ledger depends on.
 *
 * <p>The live path this covers was measured on 2026-09-20 against
 * {@code global.cohere.embed-v4:0} in ap-south-1: it answers {@code embeddings.float[0]} with
 * 1,024 floats when {@code output_dimension} is sent, and carries no token count in its body.
 */
class BedrockEmbeddingClientTest {

    private static final String COHERE = "cohere.embed-v4:0";
    private static final String TITAN = "amazon.titan-embed-text-v2:0";

    private final StructuredOutput codec = new StructuredOutput();

    /**
     * v4 answers 1,536 dimensions unless asked otherwise and the column is {@code vector(1024)},
     * so an unsent width is not a preference — it is a run that cannot be stored.
     */
    @Test
    void theCohereBodyCarriesTheWidthWhenTheModelAcceptsIt() {
        JsonNode body = codec.parse(client(COHERE, true).embedBody(COHERE, request()), null, null);

        assertThat(body.path("texts").get(0).asString()).isEqualTo("hello");
        assertThat(body.path("input_type").asString()).isEqualTo("search_query");
        assertThat(body.path("truncate").asString()).isEqualTo("END");
        assertThat(body.path("output_dimension").asInt()).isEqualTo(1024);
    }

    /** The v3 line errors on the field, which is why it is a flag beside the id and not a branch. */
    @Test
    void theCohereBodyOmitsTheWidthWhenTheModelRejectsIt() {
        JsonNode body = codec.parse(client(COHERE, false).embedBody(COHERE, request()), null, null);

        assertThat(body.has("output_dimension")).isFalse();
        assertThat(body.path("texts").get(0).asString()).isEqualTo("hello");
    }

    @Test
    void theTitanBodyIsADifferentShapeEntirely() {
        JsonNode body = codec.parse(client(TITAN, true).embedBody(TITAN, request()), null, null);

        assertThat(body.path("inputText").asString()).isEqualTo("hello");
        assertThat(body.path("dimensions").asInt()).isEqualTo(1024);
        assertThat(body.has("output_dimension")).as("Titan names the width its own way").isFalse();
    }

    @Test
    void bothCohereResponseShapesParse() {
        BedrockEmbeddingClient client = client(COHERE, true);
        String ones = "1.0,".repeat(1023) + "1.0";

        BedrockEmbeddingClient.Embedding plain = client.parseEmbedding(COHERE,
                "{\"embeddings\": [[" + ones + "]], \"response_type\": \"embeddings_floats\"}", null);
        assertThat(plain.values()).hasSize(1024).contains(1f);
        assertThat(plain.bodyTokens()).isEmpty();

        // What the live model actually returned on 2026-09-20.
        BedrockEmbeddingClient.Embedding typed = client.parseEmbedding(COHERE,
                "{\"embeddings\": {\"float\": [[" + ones + "]]}}", null);
        assertThat(typed.values()).hasSize(1024);
        assertThat(typed.bodyTokens()).as("Cohere's Bedrock answer carries no count").isEmpty();
    }

    @Test
    void titanCarriesItsOwnTokenCountInTheBody() {
        String ones = "1.0,".repeat(1023) + "1.0";

        BedrockEmbeddingClient.Embedding titan = client(TITAN, true).parseEmbedding(TITAN,
                "{\"embedding\": [" + ones + "], \"inputTextTokenCount\": 2}", null);

        assertThat(titan.values()).hasSize(1024);
        assertThat(titan.bodyTokens()).hasValue(2);
    }

    /** A vector of the wrong width must never reach a {@code vector(n)} column or a comparison. */
    @Test
    void aVectorOfTheWrongWidthIsRefused() {
        assertThatThrownBy(() -> client(COHERE, true).parseEmbedding(COHERE, "{\"embeddings\": [[0.1, 0.2]]}", null))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("2 dimensions");
    }

    /**
     * Header, then the body, then an estimate — never silently zero, which would starve the
     * breaker. Cohere's Bedrock answer has no body count, so the header is what keeps the ledger
     * billing real tokens.
     */
    @Test
    void inputTokensComeFromTheHeaderThenTheBodyThenAnEstimate() {
        BedrockEmbeddingClient.Embedding counted =
                new BedrockEmbeddingClient.Embedding(new float[0], OptionalInt.of(2));
        BedrockEmbeddingClient.Embedding uncounted =
                new BedrockEmbeddingClient.Embedding(new float[0], OptionalInt.empty());

        assertThat(BedrockEmbeddingClient.inputTokens(Optional.of(" 3 "), counted, "hello")).isEqualTo(3);
        assertThat(BedrockEmbeddingClient.inputTokens(Optional.empty(), counted, "hello")).isEqualTo(2);
        assertThat(BedrockEmbeddingClient.inputTokens(Optional.empty(), uncounted, "hello world!")).isEqualTo(3);
        assertThat(BedrockEmbeddingClient.inputTokens(Optional.empty(), uncounted, "hi")).isEqualTo(1);
    }

    private BedrockEmbeddingClient client(String model, boolean sendWidth) {
        AiProperties base = TestAiProperties.withEmbed(model, 1024);
        AiProperties properties = new AiProperties(base.provider(), base.tier(),
                new AiProperties.Embed(AiProperties.BEDROCK, model, 1024, sendWidth), base.retrieval(),
                base.pricesJson(), base.usdInr(), base.budget(), base.batchMinRecords(), base.maxOutputTokens(),
                base.callTimeout(), base.anthropic(), base.cohere(), base.bedrock(), base.prompts());
        return new BedrockEmbeddingClient(null, properties, codec);
    }

    private static EmbedRequest request() {
        return new EmbedRequest(AiFeature.embed, "hello", EmbedRequest.InputType.search_query,
                AiCallContext.system("r"));
    }
}
