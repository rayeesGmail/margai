package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** The ruling's normalisation list, and the one thing deliberately left off it (D15). */
class VerdictSpansTest {

    @Test
    void spacingAndTheNamedGlyphVariantsAreNotDifferences() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("G m_1 m_2", "G m_1m_2")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("g(h) ≅ g", "g (h) ≃ g")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("(V_i)_min ≈ 11.2", "(V_i)_min approx= 11.2")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("W(r) = − G M_E m", "W(r) = - G M_E m")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("Kepler’s law", "Kepler's law")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("6.67 · 10^-11", "6.67 × 10^-11")).isTrue();
    }

    @Test
    void aSymbolOrAWordIsADifference() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("|r|^3 r", "|r|^3 r_hat")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("g(h) ≅ g", "g(h) = g")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("F'_GB", "F_GB")).isFalse();
    }

    /** Whether a bracket changes the meaning is the prompt's question, never code's (plan question 3). */
    @Test
    void bracketsAreNotNormalisedAway() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("G (Mm / d^2) L", "G Mm / d^2 L")).isFalse();
    }

    @Test
    void aSpanIsCarriedHoweverTheVerifierSpacedIt() {
        String part = "F = - G m_1m_2 / |r|^3 r_hat where G is the constant.";

        assertThat(VerdictSpans.carries(part, "G m_1 m_2 / |r|^3 r_hat")).isTrue();
        assertThat(VerdictSpans.carries(part, "where G was the constant")).isFalse();
        assertThat(VerdictSpans.carries(part, " ")).isFalse();
    }
}
