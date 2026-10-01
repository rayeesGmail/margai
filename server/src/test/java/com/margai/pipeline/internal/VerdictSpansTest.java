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

    /**
     * The calibration's three false-flag classes code can see (2026-09-15): a radical, a single-token
     * exponent and a trailing full stop, each written two ways that name one thing.
     */
    @Test
    void aRadicalASingleTokenExponentAndATrailingFullStopAreWrittenTwoWaysForOneThing() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("− 4√2 G m / l", "− 4 sqrt(2) G m / l")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("√(l / g)", "sqrt(l / g)")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("6.67 × 10^-11 × (459 × 60)^2", "6.67 × 10^(-11) × (459 × 60)^2")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("– 4 G M m / 5 R", "– 4 G M m / 5 R .")).isTrue();
    }

    /** The seeded run's "?" the verifier dropped (2026-09-15): trailing sentence punctuation, like a full stop. */
    @Test
    void aTrailingQuestionOrExclamationMarkIsLikeAFullStop() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("traverse BAC and CPB", "traverse BAC and CPB ?")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("what a result", "what a result!")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("CPB? Answer", "CPB Answer")).isFalse();
    }

    /** Only a single token loses its brackets: around a quotient or a sum they still say what is covered. */
    @Test
    void aBracketAroundMoreThanOneTokenStillCounts() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("sqrt(l / g)", "sqrt(l) / g")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("e^(-E/kT)", "e^-E/kT")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("10^-11", "10^11")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("equal. Then", "equal Then")).isFalse();
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

    /**
     * The load writes a zero exponent the book set as a degree sign into the convention
     * ({@link DimensionalBrackets}, 2026-09-17), so the row no longer reads character for character
     * against the print. That is a difference code made and code must therefore set aside, or every
     * dimensional formula in chapter 1 would be flagged at the next paid read. Outside a dimensional
     * bracket a degree sign still means what it says.
     */
    @Test
    void aZeroExponentTheLoadRewroteIsNotADifference() {
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("[M° L^3 T°]", "[M^0 L^3 T^0]")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("volume is [M° L^3 T°],", "volume is [M^0 L^3 T^0],")).isTrue();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("[M L^-3 T°]", "[M L^-3 T^0]")).isTrue();

        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("inclined at 30°", "inclined at 30^0")).isFalse();
        assertThat(VerdictSpans.sameExceptSpacingAndGlyphs("the symbol L°m", "the symbol L^0m")).isFalse();
    }

    @Test
    void aSpanIsCarriedHoweverTheVerifierSpacedIt() {
        String part = "F = - G m_1m_2 / |r|^3 r_hat where G is the constant.";

        assertThat(VerdictSpans.carries(part, "G m_1 m_2 / |r|^3 r_hat")).isTrue();
        assertThat(VerdictSpans.carries(part, "where G was the constant")).isFalse();
        assertThat(VerdictSpans.carries(part, " ")).isFalse();
    }
}
