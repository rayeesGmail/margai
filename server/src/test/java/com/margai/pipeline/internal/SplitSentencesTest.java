package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.NcertParagraphRow;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The pairs here are real ones, copied from the first full extraction of Physics Part-I, where 32
 * of 969 paragraphs began in the middle of the previous one's sentence.
 */
class SplitSentencesTest {

    private static NcertParagraphRow row(String section, int paraNo, String text) {
        return new NcertParagraphRow((short) 1, section, (short) paraNo, text, false, List.of(), null);
    }

    @Test
    void aSentenceCutInHalfIsFound() {
        List<String> found = SplitSentences.find(List.of(
                row("1.2", 6, "The scheme is now for international usage"),
                row("1.2", 7, "in scientific, technical, industrial and commercial work. Because SI "
                        + "units used a decimal system, conversions within the system are quick.")));

        assertThat(found).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("§1.2 ¶7").contains("international usage").contains("in scientific");
    }

    /** NCERT does this on purpose after a displayed equation; it is not a split sentence. */
    @Test
    void aLowerCaseOpeningAfterAFinishedEquationIsNotASplit() {
        assertThat(SplitSentences.find(List.of(
                row("3.7.1", 8, "The velocity is the derivative of the position vector. (3.30a)"),
                row("3.7.1", 9, "where v_x = dx/dt, v_y = dy/dt and the direction follows.")))).isEmpty();
    }

    /** A symbol is not a lower-case word: "a(t) = dv/dt" opens an equation, not a continuation. */
    @Test
    void aParagraphOpeningWithASymbolIsNotASplit() {
        assertThat(SplitSentences.find(List.of(
                row("3.7.2", 10, "Differentiating the velocity gives the acceleration"),
                row("3.7.2", 11, "a(t) = dv/dt = +4.0 j_hat, along the y-direction.")))).isEmpty();
    }

    @Test
    void twoFinishedParagraphsAreNotASplit() {
        assertThat(SplitSentences.find(List.of(
                row("1.2", 1, "The first paragraph ends properly."),
                row("1.2", 2, "the second opens in lower case but the first was finished.")))).isEmpty();
    }

    @Test
    void paragraphsInDifferentSectionsAreNeverPaired() {
        assertThat(SplitSentences.find(List.of(
                row("1.2", 9, "The last paragraph of a section ends without a full stop"),
                row("1.3", 1, "in the next section, which is a different thing entirely.")))).isEmpty();
    }

    @Test
    void nothingToPairIsNotASplit() {
        assertThat(SplitSentences.find(List.of())).isEmpty();
        assertThat(SplitSentences.find(List.of(row("1.2", 1, "Only one paragraph")))).isEmpty();
    }
}
