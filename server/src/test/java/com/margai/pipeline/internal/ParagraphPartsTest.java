package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/** A loaded row divided back into what each of its pages printed, by the offsets the load recorded (D15). */
class ParagraphPartsTest {

    @Test
    void aSinglePageRowIsOnePart() {
        List<ParagraphParts.Part> parts = ParagraphParts.of(row("Early in our lives.", List.of(1), List.of(0)));

        assertThat(parts).containsExactly(new ParagraphParts.Part(1, "Early in our lives.", false, false));
    }

    @Test
    void aStraddlingRowIsDividedAtItsPageStarts() {
        List<ParagraphParts.Part> parts = ParagraphParts.of(
                row("A sentence that runs on and finishes on the next page.", List.of(4, 5), List.of(0, 24)));

        assertThat(parts).containsExactly(
                new ParagraphParts.Part(4, "A sentence that runs on", false, true),
                new ParagraphParts.Part(5, "and finishes on the next page.", true, false));
    }

    @Test
    void aRowLoadedBeforeOffsetsExistedCannotBeDivided() {
        NcertParagraphRow old = row("Text.", List.of(4), List.of());

        assertThat(ParagraphParts.divisible(old)).isFalse();
        assertThatThrownBy(() -> ParagraphParts.of(old))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ch 7 §7.3 ¶1 has no page offsets");
    }

    @Test
    void theOpeningWordsCompareAcrossTheLayersNotation() {
        assertThat(ParagraphParts.opening("Select two points F_1 and F_2. Take a length"))
                .isEqualTo(ParagraphParts.opening("Select two points F and F"));
        assertThat(ParagraphParts.opening("Example 7.1 Let the speed of the planet"))
                .isEqualTo(ParagraphParts.opening("tExample 7.1 Let the speed of"));
        assertThat(ParagraphParts.opening("Stated Mathematically, Newton's gravitation law"))
                .isEqualTo(ParagraphParts.opening("Stated Mathematically, Newtons gravitation"));
        assertThat(ParagraphParts.opening("The quotation is essentially"))
                .isNotEqualTo(ParagraphParts.opening("The gravitational force is"));
        // The layer sets a subscript or an exponent on a baseline of its own, so its line reads "M"
        // where the transcription writes M_E (chapter 7 page 8).
        assertThat(ParagraphParts.sameOpening("where M_E = mass of earth, m = mass", "where M = mass of earth,")).isTrue();
        assertThat(ParagraphParts.sameOpening("Given k = 10^-13 s^2 m^(-3) and the", "Given k = 10 s m and the")).isTrue();
    }

    /**
     * Chapter 7 page 7: the layer maps no glyph for the symbol fonts, so the line the print starts with
     * "For h/R_E << 1, using binomial expression," reaches us with its math cut out. The split correction
     * that made this row is right and the opening comparison missed it (founder's ruling, 2026-09-16).
     */
    @Test
    void anOpeningTheLayerStrippedOfItsMathIsStillTheSameOpening() {
        assertThat(ParagraphParts.sameOpening("For , using binomial expression,",
                "For h/R_E << 1, using binomial expression, g(h) ≅ g (1 - 2h / R_E)")).isFalse();
        assertThat(ParagraphParts.openingWithMathDropped("For , using binomial expression,",
                "For h/R_E << 1, using binomial expression, g(h) ≅ g (1 - 2h / R_E)")).isTrue();
    }

    @Test
    void anOpeningThatIsAnotherParagraphsIsNotPairedWithIt() {
        // Different paragraphs of chapter 7 page 4, one of them a subsequence of the other's letters.
        assertThat(ParagraphParts.openingWithMathDropped("The force on m is directed",
                "The total force on m_1 is F_1 = Gm_2 m_1 / r_21^2")).isFalse();
        assertThat(ParagraphParts.openingWithMathDropped("Stated Mathematically, Newtons gravitation",
                "Every body in the universe attracts every other body")).isFalse();
        // Too little of the line survives to say whose opening it was.
        assertThat(ParagraphParts.openingWithMathDropped("For ,", "For h/R_E << 1, using binomial")).isFalse();
    }

    static NcertParagraphRow row(String text, List<Integer> pages, List<Integer> starts) {
        return new NcertParagraphRow((short) 7, "7.3", (short) 1, text, false, List.of(),
                new ParagraphExtraction(pages, starts, new BigDecimal("0.90"), null, null));
    }
}
