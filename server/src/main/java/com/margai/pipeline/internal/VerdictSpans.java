package com.margai.pipeline.internal;

import java.util.regex.Pattern;

/**
 * What code decides about the verifier's spans before anything is flagged (D15, the ruling's
 * normalisation list): never ask a model to judge what code can compute (.claude/rules/ai-layer.md).
 *
 * <p>Two spans that are equal once spacing is removed and the glyph variants the ruling names are made
 * one — ≅ ≃ ≈ (and the convention's "approx="), the minus and its dashes, the quotation marks, × and · —
 * name no difference, whatever the model said. And a transcribed span the row does not carry, compared
 * the same way, is the verifier misquoting the transcription: it cannot be found, so it cannot be
 * adjudicated, and it is counted as the verifier's error rather than raised as the corpus's.
 *
 * <p>Brackets are deliberately not normalised: whether a bracket changes what an expression means is
 * the prompt's question — "G (Mm / d^2) L" is not "G Mm / d^2 L" (founder, D15 plan question 3).
 */
final class VerdictSpans {

    private static final Pattern SPACE = Pattern.compile("\\s+");
    private static final Pattern APPROX = Pattern.compile("approx=|[≅≃≈]");
    private static final Pattern MINUS = Pattern.compile("[−–—‐‑]");
    private static final Pattern SINGLE_QUOTE = Pattern.compile("[’‘`]");
    private static final Pattern DOUBLE_QUOTE = Pattern.compile("[“”]");
    private static final Pattern TIMES = Pattern.compile("[·⋅]");

    private VerdictSpans() {
    }

    static String normalise(String text) {
        String squashed = SPACE.matcher(text == null ? "" : text).replaceAll("");
        squashed = APPROX.matcher(squashed).replaceAll("≈");
        squashed = MINUS.matcher(squashed).replaceAll("-");
        squashed = SINGLE_QUOTE.matcher(squashed).replaceAll("'");
        squashed = DOUBLE_QUOTE.matcher(squashed).replaceAll("\"");
        return TIMES.matcher(squashed).replaceAll("×");
    }

    /** The two spans differ only in spacing or a glyph variant. */
    static boolean sameExceptSpacingAndGlyphs(String printed, String transcribed) {
        return normalise(printed).equals(normalise(transcribed));
    }

    /** The part of the row the verifier read carries this span, however it was spaced. */
    static boolean carries(String partText, String transcribed) {
        String span = normalise(transcribed);
        return !span.isEmpty() && normalise(partText).contains(span);
    }
}
