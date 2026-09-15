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
 * <p>Brackets are otherwise not normalised: whether a bracket changes what an expression means is the
 * prompt's question (DECISIONS 2026-09-14, the brackets row). The exceptions are the brackets around a
 * single token under a radical or an exponent, which change nothing, and a trailing full stop — the
 * three false-flag classes the chapter-7 calibration showed code can see (DECISIONS 2026-09-15).
 */
final class VerdictSpans {

    private static final Pattern SPACE = Pattern.compile("\\s+");
    private static final Pattern APPROX = Pattern.compile("approx=|[≅≃≈]");
    private static final Pattern MINUS = Pattern.compile("[−–—‐‑]");
    private static final Pattern SINGLE_QUOTE = Pattern.compile("[’‘`]");
    private static final Pattern DOUBLE_QUOTE = Pattern.compile("[“”]");
    private static final Pattern TIMES = Pattern.compile("[·⋅]");
    /** A radical around one token: √2 and sqrt(2) name one thing; √(l/g) keeps its bracket. */
    private static final Pattern RADICAL_OF_ONE_TOKEN = Pattern.compile("√\\(([A-Za-z0-9_.]+)\\)");
    /** An exponent of one token: 10^-11 and 10^(-11) name one thing; e^(-E/kT) keeps its bracket. */
    private static final Pattern EXPONENT_OF_ONE_TOKEN = Pattern.compile("\\^\\((-?[A-Za-z0-9_.]+)\\)");
    private static final Pattern TRAILING_FULL_STOP = Pattern.compile("\\.+$");

    private VerdictSpans() {
    }

    static String normalise(String text) {
        String squashed = SPACE.matcher(text == null ? "" : text).replaceAll("");
        squashed = APPROX.matcher(squashed).replaceAll("≈");
        squashed = MINUS.matcher(squashed).replaceAll("-");
        squashed = SINGLE_QUOTE.matcher(squashed).replaceAll("'");
        squashed = DOUBLE_QUOTE.matcher(squashed).replaceAll("\"");
        squashed = TIMES.matcher(squashed).replaceAll("×");
        // The calibration's three code-visible false-flag classes (DECISIONS 2026-09-15): the convention's
        // sqrt() for √ and bracketed exponents, and a full stop the verifier dropped after a display.
        squashed = RADICAL_OF_ONE_TOKEN.matcher(squashed.replace("sqrt(", "√(")).replaceAll("√$1");
        squashed = EXPONENT_OF_ONE_TOKEN.matcher(squashed).replaceAll("^$1");
        return TRAILING_FULL_STOP.matcher(squashed).replaceAll("");
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
