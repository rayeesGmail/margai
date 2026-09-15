package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.internal.StructuredOutput;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

/**
 * The verifier's output held to what a verdict means (D15): a verdict of {@code differs} names where,
 * and every other verdict names nothing. A shape that breaks this is refused where the output is
 * decoded, so the schema layer's one repair call answers it rather than the report.
 */
class PageVerdictsTest {

    @Test
    void aDifferenceIsNamedByBothItsSpans() {
        PageVerdicts.ItemVerdict verdict = new PageVerdicts.ItemVerdict(3, PageVerdicts.Verdict.differs,
                List.of(new PageVerdicts.Difference("|r|^3 r where", "|r|^3 r_hat where")));

        assertThat(verdict.differences()).singleElement()
                .extracting(PageVerdicts.Difference::printed).isEqualTo("|r|^3 r where");
    }

    @Test
    void differsWithoutADifferenceIsRefused() {
        assertThatThrownBy(() -> new PageVerdicts.ItemVerdict(3, PageVerdicts.Verdict.differs, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("item 3 is 'differs' but names no difference");
    }

    @Test
    void aVerdictThatIsNotDiffersNamesNoDifference() {
        assertThatThrownBy(() -> new PageVerdicts.ItemVerdict(2, PageVerdicts.Verdict.matches,
                List.of(new PageVerdicts.Difference("a", "b"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("item 2 is 'matches' and must name no difference");
    }

    /** A span the pipeline must find in the row cannot be empty. */
    @Test
    void aBlankSpanIsRefused() {
        assertThatThrownBy(() -> new PageVerdicts.Difference("r", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("both spans must quote text");
    }

    /**
     * Two equal spans name no difference, and code can see that for nothing (VerdictSpans), so they are
     * accepted here and set aside by the pipeline. Refusing them at decode cost a repair call on page after
     * page of the first calibration run, where Sonnet listed spans it had checked (2026-09-15).
     */
    @Test
    void identicalSpansAreAcceptedForThePipelineToSetAside() {
        assertThat(new PageVerdicts.Difference("r_hat_21", "r_hat_21").printed()).isEqualTo("r_hat_21");
    }

    @Test
    void absentListsAreEmpty() {
        PageVerdicts verdicts = new PageVerdicts(null, null);

        assertThat(verdicts.verdicts()).isEmpty();
        assertThat(verdicts.omitted()).isEmpty();
    }

    /**
     * The calibration of 2026-09-15: with the output's first field named {@code items}, Sonnet 5 read the
     * tool as taking one {@code items} parameter and sent its whole, correct answer as a string inside it —
     * {"items":"{\"items\":[…],\"omitted\":[]}"} — on every page, so each page cost a repair call. A field
     * named after a JSON-Schema keyword sits in the tool schema as {"items":{"type":"array","items":…}}.
     */
    @Test
    void noFieldOfAPipelineOutputIsNamedAfterAJsonSchemaKeyword() {
        StructuredOutput codec = new StructuredOutput();
        for (Class<?> output : List.of(PageVerdicts.class, NcertPage.class)) {
            assertThat(propertyNames(codec.schemaFor(output))).as("tool schema properties of %s", output.getSimpleName())
                    .doesNotContainAnyElementsOf(List.of("items", "properties", "type", "required", "enum",
                            "additionalProperties", "anyOf", "oneOf", "allOf", "not", "$ref", "format"));
        }
    }

    private static List<String> propertyNames(JsonNode schema) {
        List<String> names = new ArrayList<>();
        JsonNode properties = schema.get("properties");
        if (properties != null) {
            for (String name : properties.propertyNames()) {
                names.add(name);
                names.addAll(propertyNames(properties.get(name)));
            }
        }
        JsonNode items = schema.get("items");
        if (items != null && items.isObject()) {
            names.addAll(propertyNames(items));
        }
        return names;
    }
}
