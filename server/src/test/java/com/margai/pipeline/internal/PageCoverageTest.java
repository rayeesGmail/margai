package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.margai.pipeline.internal.PageCoverage.Assessment;
import com.margai.pipeline.internal.PageCoverage.Verdict;
import org.junit.jupiter.api.Test;

/**
 * The numbers here are the measured ones. The ratio was recalibrated on 2026-09-23 against every
 * page of the only two books that exist — 301 pages that were sent to the model and returned
 * paragraphs — and the two subjects' distributions turned out to be the same shape: Physics
 * Part-I runs 0.31 to 1.16 (median 0.95), Biology 11 runs 0.32 to 0.99 (median 0.90). Every one of
 * the eight pages below the old 0.60 was adjudicated a correct skip — tables, tint boxes and
 * figure interiors, which the prompt says not to transcribe and which the layer counts anyway — so
 * the floor sits below the lowest page either book has, and the check is a tripwire for real loss
 * rather than a table detector.
 */
class PageCoverageTest {

    private static String characters(int count) {
        return "word ".repeat(count / 4);
    }

    /** A layer that reads as running text: each sentence is long enough to count as a run. */
    private static String prose(int sentences) {
        return "The living world comprises an amazing diversity of living organisms. ".repeat(sentences);
    }

    /** A full-page figure's layer: labels, and the only full stops are inside them. */
    private static String labels() {
        return "PLANT KINGDOM 25 Figure 3.1 Algae : (a) Green algae (i) Volvox (ii) Ulothrix "
                + "(iii) Spirogyra (b) Brown algae (i) Laminaria (ii) Fucus (c) Red algae";
    }

    @Test
    void aPageTranscribedInFullSaysNothing() {
        assertThat(PageCoverage.of(characters(2000), characters(1700)).verdict()).isEqualTo(Verdict.MATCHED);
    }

    /** Page 3 of the chapter-7 dry run, the worst ratio of the twelve the first calibration saw. */
    @Test
    void theWorstRealPageOfTheDryRunIsNotFlagged() {
        Assessment assessment = PageCoverage.of(characters(2386), characters(1818));
        assertThat(assessment.ratio()).isCloseTo(0.76, within(0.02));
        assertThat(assessment.verdict()).isEqualTo(Verdict.MATCHED);
    }

    /**
     * Page 7 came back with more characters than its layer holds, and that is correct: our notation
     * spells out what the layer either loses or prints as one glyph — sqrt for √, approx= for ≅.
     */
    @Test
    void aPageWhoseNotationIsLongerThanThePagesIsNotFlagged() {
        assertThat(PageCoverage.of(characters(1763), characters(2008)).verdict()).isEqualTo(Verdict.MATCHED);
    }

    /**
     * The recalibration itself. Two thirds of {@code phy11-part1} ch 1 p2 is Table 1.1 and 68% of
     * {@code bio11} ch 4 p15 is Table 4.2's cells; both were adjudicated correct, and both were
     * flagged as missing text for a year of sessions because the layer counts a table and the
     * transcription is told not to.
     */
    @Test
    void aTableHeavyPageIsNotAMissingTextFlag() {
        assertThat(PageCoverage.of(characters(2876), characters(896)).ratio()).isCloseTo(0.31, within(0.02));
        assertThat(PageCoverage.of(characters(2876), characters(896)).verdict()).isEqualTo(Verdict.MATCHED);
        assertThat(PageCoverage.of(characters(1845), characters(587)).verdict()).isEqualTo(Verdict.MATCHED);
    }

    /** Below anything either book has ever shown: a page that came back a fifth of itself. */
    @Test
    void aPageThatLostMostOfItselfIsFlagged() {
        Assessment assessment = PageCoverage.of(characters(2000), characters(400));
        assertThat(assessment.verdict()).isEqualTo(Verdict.TOO_LITTLE_CAME_BACK);
        assertThat(assessment.reason()).contains("text is missing");
    }

    /**
     * A page that returned nothing is not a ratio and never was. Eleven of {@code bio11}'s
     * fourteen coverage flags were this: five unit openers, five biographies and the plates, every
     * one of them a page the prompt tells the model to return nothing for.
     */
    @Test
    void aPageThatReturnedNothingIsItsOwnVerdictAndNotAMissingTextFlag() {
        Assessment assessment = PageCoverage.of(prose(12), "");
        assertThat(assessment.verdict()).isEqualTo(Verdict.NOTHING_CAME_BACK);
        assertThat(assessment.reason()).contains("nothing came back").doesNotContain("text is missing");
    }

    /**
     * What separates the two kinds of empty page, and the only thing that does: a biography's
     * layer reads as sentences and a plate's reads as labels. Their character counts do not
     * separate them — Alfonso Corti's page holds 376 and the cell-diagram plate holds 466.
     */
    @Test
    void anEmptyPageCarriesWhetherItsLayerReadsAsProse() {
        assertThat(PageCoverage.of(prose(6), "").sentenceRuns()).isEqualTo(6);
        assertThat(PageCoverage.of(labels(), "").sentenceRuns()).isZero();
        assertThat(PageCoverage.of(prose(6), "").reason()).contains("6 sentence-length runs");
        assertThat(PageCoverage.of(labels(), "").reason()).contains("no sentence-length run");
    }

    /** The tiling failure: the overlap between two bands transcribed as if it were new text. */
    @Test
    void aPageTranscribedTwiceIsFlagged() {
        Assessment assessment = PageCoverage.of(characters(2000), characters(3400));
        assertThat(assessment.verdict()).isEqualTo(Verdict.TOO_MUCH_CAME_BACK);
        assertThat(assessment.reason()).contains("transcribed twice");
    }

    /**
     * The floor stays — a ratio against 200 characters means nothing, and without it a plate whose
     * caption the model transcribed would read as "transcribed twice" — but it stops being silent:
     * the verdict names the page so the report can list it.
     */
    @Test
    void aLayerTooThinToJudgeIsNotJudgedAndSaysHowThinItIs() {
        Assessment assessment = PageCoverage.of(characters(200), characters(150));
        assertThat(assessment.verdict()).isEqualTo(Verdict.NOT_JUDGED);
        assertThat(assessment.pageCharacters()).isEqualTo(200);
        assertThat(assessment.ratio()).isNegative();
    }

    /**
     * The blind spot closed: {@code bio11} ch 14 p2 is Alfonso Corti's biography at 376 characters,
     * so the old floor judged it not at all and no report named it either way. It was the fifth
     * biography, found by hand, and the rule it proves — that the model skips a biography — held 5
     * of 5 only because someone went looking.
     */
    @Test
    void aThinLayerThatReturnedNothingIsAnEmptyPageRatherThanAnUnjudgedOne() {
        Assessment corti = PageCoverage.of(prose(3), "");
        assertThat(corti.pageCharacters()).isLessThan(400);
        assertThat(corti.verdict()).isEqualTo(Verdict.NOTHING_CAME_BACK);
        assertThat(corti.sentenceRuns()).isEqualTo(3);
    }

    @Test
    void aPageWithNoLayerAtAllIsNotJudged() {
        assertThat(PageCoverage.of(null, "text").verdict()).isEqualTo(Verdict.NOT_JUDGED);
        assertThat(PageCoverage.of("a page", null).verdict()).isEqualTo(Verdict.NOT_JUDGED);
    }
}
