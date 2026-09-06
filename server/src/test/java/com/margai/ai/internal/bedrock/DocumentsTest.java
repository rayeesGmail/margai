package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.document.Document;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/** JSON ↔ SDK Document round trip keeps shapes, integral numbers and nulls. */
class DocumentsTest {

    private final JsonMapper mapper = JsonMapper.builder().build();

    @Test
    void roundTripPreservesTheTree() {
        JsonNode json = mapper.readTree("""
                {"greeting": "ok", "number": 7, "ratio": 0.25, "big": 12345678901234,
                 "flags": [true, false, null], "nested": {"steps": [{"index": 1, "text": "x"}]}}
                """);

        Document document = Documents.fromJson(json);
        JsonNode back = Documents.toJson(document);

        // Node classes may differ (IntNode 7 vs LongNode 7); the JSON, which validation and decoding see, must not.
        assertThat(back.toString()).isEqualTo(json.toString());
        assertThat(back.path("number").isIntegralNumber()).isTrue();
        assertThat(back.path("big").isIntegralNumber()).isTrue();
        assertThat(back.path("ratio").isIntegralNumber()).isFalse();
        assertThat(document.asMap().get("greeting").asString()).isEqualTo("ok");
        assertThat(document.asMap().get("number").asNumber().intValue()).isEqualTo(7);
        assertThat(document.asMap().get("flags").asList().get(2).isNull()).isTrue();
    }

    @Test
    void nullAndMissingBecomeANullDocument() {
        assertThat(Documents.fromJson(null).isNull()).isTrue();
        assertThat(Documents.fromJson(mapper.missingNode()).isNull()).isTrue();
        assertThat(Documents.toJson(null).isNull()).isTrue();
    }
}
