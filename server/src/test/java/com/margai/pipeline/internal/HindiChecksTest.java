package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * A Hindi page's rows held to its decoded layer (SPEC §12.2, DECISIONS 2026-10-02) — the checks that found
 * every real defect of the Phase B and C trials, where neither paid second read did (D16).
 */
class HindiChecksTest {

    /** Phase B: Opus 5 wrote दाशिमक where the print has दाश्मिक — a halant lost, a word the page never prints. */
    @Test
    void aWordTheRowsCarryAndThePageDoesNotIsNamed() {
        String page = "1.28 को दो दाश्मिक स्थानों तक निकटित करने पर";
        String rows = "1.28 को दो दाशिमक स्थानों तक निकटित करने पर";

        assertThat(HindiChecks.devanagari(page, rows)).containsExactly("'दाशिमक' 1x in the rows, 0x on the page");
    }

    @Test
    void aFaithfulTranscriptionRaisesNothing() {
        String page = "भौतिकी एक मात्रात्मक विज्ञान है।";

        assertThat(HindiChecks.devanagari(page, page)).isEmpty();
        assertThat(HindiChecks.spans("3.00 × 10 –3 •", "3.00 × 10^-3 •")).isEmpty();
    }

    /**
     * Phase C: khph101 p5's × is in the layer and not drawn on the page, and Opus 5.5 dropped seven bullets on
     * p4 — symbols the layer carries more often than the rows. Digits and Latin the layer carries alone are not
     * named: a table, a running head or a page number puts them there.
     */
    @Test
    void aSymbolTheLayerCarriesMoreOftenThanTheRowsIsNamed() {
        assertThat(HindiChecks.spans("0.003 m = 3 × 10 –3 m • • 9", "0.003 m = 3 10^-3 m"))
                .containsExactly("'×' 1x on the page, 0x in the rows", "'•' 2x on the page, 0x in the rows");
    }

    /** A digit or a Latin token the rows carry and the layer does not is a misreading, whichever way it fell. */
    @Test
    void aDigitOrLatinTokenOnlyTheRowsCarryIsNamed() {
        assertThat(HindiChecks.spans("23080 μm SI", "23086 μm SI"))
                .containsExactly("'23086' 1x in the rows, 0x on the page");
    }

    /**
     * Measured on the Phase B and C rows of khph101 (D16): the layer's visarga ः is often the rows' colon, a
     * word the layer splits across lines makes the page one short, and the prompt names Greek letters and
     * writes √ as sqrt — none of them a defect, so none is named.
     */
    @Test
    void whatTheLayerAndTheConventionsMakeDifferentIsNotNamed() {
        assertThat(HindiChecks.devanagari("अतः यह प्रायः है", "अत: यह प्राय: है")).isEmpty();
        assertThat(HindiChecks.devanagari("यह है वह है", "यह है वह है और है")).containsExactly("'और' 1x in the rows, 0x on the page");
        assertThat(HindiChecks.spans("π r √ 2 ± 4", "pi r sqrt(2) ± 4 ±")).isEmpty();
    }

    /**
     * The first Hindi book's word lines that were not misreadings (D16): a word the page breaks at a line end
     * with no hyphen, and the zero-width joiner a row writes after a visible halant. A word only broken inside
     * a line is still named.
     */
    @Test
    void aWordBrokenAtALineEndOrJoinedByAJoinerIsOnThePage() {
        assertThat(HindiChecks.devanagari("सार्थक अंकों की संख्या अत्यधि\nक (अनन्त) है।", "संख्या अत्यधिक (अनन्त) है।"))
                .isEmpty();
        assertThat(HindiChecks.devanagari("दिक्स्थान में", "दिक्‌स्थान में")).isEmpty();
        assertThat(HindiChecks.devanagari("संख्या अत्यधि क है।", "संख्या अत्यधिक है।"))
                .containsExactly("'अत्यधिक' 1x in the rows, 0x on the page");
    }

    /** The हृ glyph with the aa-stroke prints ह्य in बाह्य and ह्र + ा in ह्रास: a page word holding it is either. */
    @Test
    void theHookedHaWithTheAaStrokeIsEitherConjunct() {
        assertThat(HindiChecks.devanagari("कोई बाहृा बल", "कोई बाह्य बल")).isEmpty();
        assertThat(HindiChecks.devanagari("ऊतक के हृास को", "ऊतक के ह्रास को")).isEmpty();
        assertThat(HindiChecks.devanagari("कोई बाहृा बल", "कोई बाहर बल")).containsExactly("'बाहर' 1x in the rows, 0x on the page");
    }

    /**
     * The rows' notation read as the page prints it (D16): -> and _vec are arrows, "to" is the range word of an
     * integral, a one-letter subscript runs into the next symbol in m_ix_i, a spelled Greek letter runs into a
     * symbol in lambdaA, and the page draws ≃ as a tilde over a bar.
     */
    @Test
    void theRowsNotationIsReadAsThePagePrintsIt() {
        assertThat(HindiChecks.spans("lim Δt → 0 ∫ 1 0 x A →", "lim(Delta t -> 0) integral(0 to 1) x A_vec")).isEmpty();
        assertThat(HindiChecks.spans("m x i i", "sum m_ix_i")).isEmpty();
        assertThat(HindiChecks.spans("λ A", "lambdaA")).isEmpty();
        assertThat(HindiChecks.spans("1 + x ~ 1", "1 + x ≃ 1")).isEmpty();
        assertThat(HindiChecks.spans("1 + x = 1", "1 + x ≃ 1")).containsExactly("'≃' 1x in the rows, 0x on the page");
    }

    /**
     * A stacked fraction is drawn a glyph at a time, d d v t, so the page's lines never hold dv or dt; its Latin
     * read by baseline does. That reading only finds a token the rows carry — it never counts one.
     */
    @Test
    void aTokenTheLayerHoldsOnlyByBaselineIsFound() {
        assertThat(HindiChecks.spans("a = d\nd\nv\nt", "dv\ndt", "a = dv / dt")).isEmpty();
        assertThat(HindiChecks.spans("a = d\nd\nv\nt", "", "a = dv / dt"))
                .containsExactly("'dv' 1x in the rows, 0x on the page", "'dt' 1x in the rows, 0x on the page");
    }

    @Test
    void theShareOfThePagesWordsTheRowsCarry() {
        assertThat(HindiChecks.coverage("एक दो तीन चार", "एक दो")).isEqualTo(0.5);
        assertThat(HindiChecks.coverage("", "एक")).isEqualTo(1.0);
    }
}
