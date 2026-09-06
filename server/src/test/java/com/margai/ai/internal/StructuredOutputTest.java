package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

/**
 * TECH_PLAN §4.11 structured output: the schema derived from a record is what Bedrock's forced
 * tool receives and what the answer is validated against — snake_case names, every component
 * required, no extra properties, enums by name, nested records and lists inline.
 */
class StructuredOutputTest {

    enum Kind {
        option,
        numeric
    }

    record Anchor(String bookCode, int chapterNo) {
    }

    record Step(int index, String text) {
    }

    record Answer(String finalAnswer, List<Step> steps, Kind kind, Anchor anchor) {
    }

    private final StructuredOutput codec = new StructuredOutput();
    private final Usage usage = new Usage(10, 5, 0, 0);

    @Test
    void schemaIsSnakeCaseAllRequiredAndClosed() {
        JsonNode schema = codec.schemaFor(Answer.class);

        assertThat(schema.path("type").asString()).isEqualTo("object");
        assertThat(schema.path("additionalProperties").asBoolean()).isFalse();
        assertThat(schema.has("$schema")).isFalse();
        assertThat(schema.path("required")).extracting(JsonNode::asString)
                .containsExactlyInAnyOrder("final_answer", "steps", "kind", "anchor");
        assertThat(schema.path("properties").path("final_answer").path("type").asString()).isEqualTo("string");
        assertThat(schema.path("properties").path("kind").path("enum")).extracting(JsonNode::asString)
                .containsExactly("option", "numeric");
        JsonNode steps = schema.path("properties").path("steps");
        assertThat(steps.path("type").asString()).isEqualTo("array");
        assertThat(steps.path("items").path("properties").path("index").path("type").asString()).isEqualTo("integer");
        assertThat(steps.path("items").path("additionalProperties").asBoolean()).isFalse();
        JsonNode anchor = schema.path("properties").path("anchor");
        assertThat(anchor.path("required")).extracting(JsonNode::asString).containsExactlyInAnyOrder("book_code", "chapter_no");
        assertThat(codec.schemaJson(Answer.class)).contains("\"book_code\"");
    }

    @Test
    void validJsonDecodesIntoTheRecord() {
        JsonNode json = codec.parse("""
                {"final_answer": "B", "steps": [{"index": 1, "text": "apply v = u + at"}],
                 "kind": "option", "anchor": {"book_code": "PHY11", "chapter_no": 3}}
                """, usage, "m");

        Answer answer = codec.decode(Answer.class, json, usage, "m");

        assertThat(answer).isEqualTo(new Answer("B", List.of(new Step(1, "apply v = u + at")), Kind.option,
                new Anchor("PHY11", 3)));
        assertThat(codec.validate(Answer.class, json)).isEmpty();
    }

    @Test
    void missingWrongTypedAndExtraFieldsAreReportedWithTheirPaths() {
        JsonNode json = codec.parse("""
                {"final_answer": 42, "kind": "essay", "anchor": {"book_code": "PHY11", "chapter_no": 3},
                 "bonus": true}
                """, usage, "m");

        assertThatThrownBy(() -> codec.decode(Answer.class, json, usage, "cheap-model"))
                .isInstanceOf(InvalidOutputException.class)
                .satisfies(e -> {
                    InvalidOutputException invalid = (InvalidOutputException) e;
                    assertThat(invalid.errors()).anySatisfy(m -> assertThat(m).contains("steps"));
                    assertThat(invalid.errors()).anySatisfy(m -> assertThat(m).contains("final_answer"));
                    assertThat(invalid.errors()).anySatisfy(m -> assertThat(m).contains("kind"));
                    assertThat(invalid.errors()).anySatisfy(m -> assertThat(m).contains("bonus"));
                    assertThat(invalid.usage()).isEqualTo(usage);
                    assertThat(invalid.modelId()).isEqualTo("cheap-model");
                    assertThat(invalid.outputJson()).contains("essay");
                });
    }

    @Test
    void nonJsonTextIsInvalidOutput() {
        assertThatThrownBy(() -> codec.parse("Sure! Here is the answer: B", usage, "m"))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("not valid JSON");
    }

    @Test
    void schemaIsGeneratedOncePerType() {
        assertThat(codec.schemaFor(Answer.class)).isSameAs(codec.schemaFor(Answer.class));
    }
}
