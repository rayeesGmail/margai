package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.StructuredOutput;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The v3 page shape (D15): the model returns section, text and one continues-previous-page flag
 * per paragraph, and the loader assigns the paragraph numbers. What the model can get wrong
 * about that shape is refused where its output is decoded, so the page is re-called on the spot
 * — the phantom empty paragraph at ch 4 p2 of phy11-part1 reached the JSONL and was found an
 * hour later by `ncert load` (TRACKER 2026-09-14).
 */
class NcertPageTest {

    private final StructuredOutput codec = new StructuredOutput();

    @Test
    void aWellFormedPageDecodesWithItsFlag() {
        NcertPage page = decode("""
                {"paragraphs": [
                   {"section": "6.2", "text": "of Fig. 6.11 had different masses.", "continues_previous_page": true, "figure_refs": ["Fig. 6.11"]},
                   {"section": "6.3", "text": "Equipped with the definition of the centre of mass.", "continues_previous_page": false, "figure_refs": []}],
                 "confidence": 0.92}
                """);

        assertThat(page.paragraphs()).hasSize(2);
        assertThat(page.paragraphs().getFirst().continuesPreviousPage()).isTrue();
        assertThat(page.paragraphs().getFirst().figureRefs()).containsExactly("Fig. 6.11");
        assertThat(page.paragraphs().getLast().continuesPreviousPage()).isFalse();
        assertThat(page.confidence()).isEqualByComparingTo("0.92");
    }

    /** ch 4 p2: a continuation object with nothing in it, for a paragraph that did not continue. */
    @Test
    void aBlankParagraphIsRefusedWhereTheOutputIsDecodedAndIsRepairable() {
        assertThatThrownBy(() -> decode("""
                {"paragraphs": [
                   {"section": "4.1", "text": "  ", "continues_previous_page": true, "figure_refs": []},
                   {"section": "4.2", "text": "The question posed above appears to be simple.", "continues_previous_page": false, "figure_refs": []}],
                 "confidence": 0.95}
                """))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("no text")
                .hasMessageContaining("4.1")
                .satisfies(e -> assertThat(((InvalidOutputException) e).repairable()).isTrue());
    }

    /** Only a page's first paragraph can continue the previous page; anything else is a numbering error in disguise. */
    @Test
    void aContinuesFlagOnAnyParagraphButTheFirstIsRefused() {
        assertThatThrownBy(() -> decode("""
                {"paragraphs": [
                   {"section": "7.3", "text": "The first paragraph.", "continues_previous_page": false, "figure_refs": []},
                   {"section": "7.3", "text": "The second, wrongly flagged.", "continues_previous_page": true, "figure_refs": []}],
                 "confidence": 0.95}
                """))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("continues_previous_page")
                .hasMessageContaining("first paragraph");
    }

    /** A plate or a full-page figure: no paragraphs is a valid page, not a refusal. */
    @Test
    void aPageWithNoParagraphsIsValid() {
        NcertPage empty = new NcertPage(List.of(), BigDecimal.ONE);

        assertThat(empty.paragraphs()).isEmpty();
        assertThat(new NcertPage(null, BigDecimal.ONE).paragraphs()).isEmpty();
    }

    private NcertPage decode(String json) {
        return codec.decode(NcertPage.class, codec.parse(json, Usage.none(), "fake"), Usage.none(), "fake");
    }
}
