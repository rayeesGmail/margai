package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.NcertPage;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The cases are real. `keph101.pdf` page 7 prints the same quantity twice: the running text of
 * §1.5 sets a zero exponent as a degree sign — the page's own text layer holds
 * {@code [M° L3 T°]}, U+00B0 — while the displayed equations below it set the digit,
 * {@code [M0 L3 T0]}. The transcription is faithful to the layer and so carries the degree; a
 * dimensional formula that reads "M degrees" is not what the book means, and would not match
 * {@code M^0} at retrieval (D15, the chapter-1 staging run, 2026-09-17).
 *
 * <p>The rule is deliberately narrow, because a degree sign is not always a lost zero. Chemistry
 * sets the limiting molar conductivity as {@code Λ°m} and the layer renders the Lambda as a plain
 * L, so {@code L°m} in `chem12-part1` chapter 2 is a Greek letter to recover, not an exponent
 * (22 occurrences). An angle keeps its degree sign by the frozen prompt's own convention, and the
 * stray degree that stands for a Symbol-font τ is {@link NotationFlags}' business, not this one.
 * Only a bracket whose whole content is a dimensional formula is touched.
 */
class DimensionalBracketsTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "it is said to possess zero dimension in mass [M°], | it is said to possess zero dimension in mass [M^0],",
            "zero dimension in time [T°] and three dimensions | zero dimension in time [T^0] and three dimensions",
            "the dimensional formula of the volume is [M° L^3 T°] | the dimensional formula of the volume is [M^0 L^3 T^0]",
            "that of speed or velocity is [M° L T^-1]. | that of speed or velocity is [M^0 L T^-1].",
            "Similarly, [M° L T^-2] is the dimensional formula | Similarly, [M^0 L T^-2] is the dimensional formula",
            "and [M L^-3 T°] that of mass density. | and [M L^-3 T^0] that of mass density.",
    })
    void aZeroExponentSetAsADegreeSignBecomesCaretZero(String transcribed, String expected) {
        assertThat(DimensionalBrackets.normalise(transcribed)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Chemistry's limiting molar conductivity: the layer's L is a Lambda, not a dimension.
            "conductivity and is represented by the symbol L°m .",
            "Lm = L°m – A c^(1/2)",
            // An angle keeps its degree sign, written once and only after its number (the prompt).
            "the plane is inclined at 30° to the horizontal",
            "a rotation of 90° about the z axis",
            // The Symbol-font tau the layer renders as a degree sign — NotationFlags' case, not ours.
            "Where ° is the restoring couple per unit angle of twist",
            // A bracket that is not a dimensional formula.
            "the dimensional equations of volume [V], speed [v], force [F]",
            // Already in the convention: normalising twice changes nothing.
            "the dimensional formula of the volume is [M^0 L^3 T^0]",
    })
    void aDegreeThatIsNotAZeroExponentIsLeftAlone(String text) {
        assertThat(DimensionalBrackets.normalise(text)).isEqualTo(text);
    }

    @Test
    void theRuleIsIdempotent() {
        String once = DimensionalBrackets.normalise("volume is [M° L^3 T°]");
        assertThat(DimensionalBrackets.normalise(once)).isEqualTo(once);
    }

    @Test
    void nothingToNormaliseIsReturnedUnchanged() {
        assertThat(DimensionalBrackets.normalise("")).isEmpty();
        assertThat(DimensionalBrackets.normalise(null)).isNull();
    }

    /** Like every other deterministic change the load makes, each rewrite is named in the report. */
    @Test
    void everyRewrittenParagraphIsNamedAndTheRestAreUntouched() {
        ExtractedPage page = page(7, "1.5",
                "the dimensional formula of the volume is [M° L^3 T°], and that of speed is [M° L T^-1].");
        ExtractedPage plain = page(8, "1.6", "the plane is inclined at 30° to the horizontal");

        DimensionalBrackets.Applied applied = DimensionalBrackets.apply(List.of(page, plain));

        assertThat(applied.pages().get(0).paragraphs().get(0).text())
                .isEqualTo("the dimensional formula of the volume is [M^0 L^3 T^0], and that of speed is [M^0 L T^-1].");
        assertThat(applied.pages().get(1)).isEqualTo(plain);
        assertThat(applied.notes()).singleElement().asString()
                .contains("ch 1 p7", "1.5").contains("[M^0 L^3 T^0]");
    }

    @Test
    void aChapterWithNoDimensionalFormulaIsLeftExactlyAsItWas() {
        List<ExtractedPage> pages = List.of(page(3, "7.2", "a rotation of 90° about the axis"));

        DimensionalBrackets.Applied applied = DimensionalBrackets.apply(pages);

        assertThat(applied.pages()).isEqualTo(pages);
        assertThat(applied.notes()).isEmpty();
    }

    private static ExtractedPage page(int number, String section, String text) {
        return new ExtractedPage((short) 1, number, null, null,
                List.of(new NcertPage.Paragraph(section, text, false, List.of())), null);
    }
}
