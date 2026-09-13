package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;

/**
 * The numbers in these tests are the measured ones: the twelve taught pages of Chapter 7 of
 * Physics Part-I ran 0.76 to 1.14 against their own text layer, so the thresholds have to pass all
 * of that and still catch a page that lost a third of itself.
 */
class PageCoverageTest {

    private static String characters(int count) {
        return "word ".repeat(count / 4);
    }

    @Test
    void aPageTranscribedInFullSaysNothing() {
        assertThat(PageCoverage.check(characters(2000), characters(1700))).isEmpty();
    }

    /** Page 3 of the chapter, the worst real ratio of the dry run: 1818 of 2386 characters. */
    @Test
    void theWorstRealPageOfTheDryRunIsNotFlagged() {
        assertThat(PageCoverage.ratio(characters(2386), characters(1818))).isCloseTo(0.76, within(0.02));
        assertThat(PageCoverage.check(characters(2386), characters(1818))).isEmpty();
    }

    /**
     * Page 7 came back with more characters than its layer holds, and that is correct: our notation
     * spells out what the layer either loses or prints as one glyph — sqrt for √, approx= for ≅.
     */
    @Test
    void aPageWhoseNotationIsLongerThanThePagesIsNotFlagged() {
        assertThat(PageCoverage.check(characters(1763), characters(2008))).isEmpty();
    }

    @Test
    void aPageThatLostItsSecondHalfIsFlagged() {
        assertThat(PageCoverage.check(characters(2000), characters(800)))
                .hasValueSatisfying(reason -> assertThat(reason).contains("text is missing"));
    }

    @Test
    void aPageReturningNothingIsFlagged() {
        assertThat(PageCoverage.check(characters(2000), ""))
                .hasValueSatisfying(reason -> assertThat(reason).contains("text is missing"));
    }

    /** The tiling failure: the overlap between two bands transcribed as if it were new text. */
    @Test
    void aPageTranscribedTwiceIsFlagged() {
        assertThat(PageCoverage.check(characters(2000), characters(3400)))
                .hasValueSatisfying(reason -> assertThat(reason).contains("transcribed twice"));
    }

    @Test
    void aPageWithTooLittleTextToJudgeIsNotJudged() {
        assertThat(PageCoverage.check("Fig. 7.9", "")).isEmpty();
        assertThat(PageCoverage.check(null, "text")).isEmpty();
        assertThat(PageCoverage.check("a page", null)).isEmpty();
        assertThat(PageCoverage.ratio("Fig. 7.9", "")).isNegative();
    }
}
