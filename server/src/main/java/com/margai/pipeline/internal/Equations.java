package com.margai.pipeline.internal;

import java.util.regex.Pattern;

/**
 * Whether a transcribed paragraph contains a mathematical or chemical expression
 * ({@code ncert_paragraphs.has_equations}, TECH_PLAN §2.3).
 *
 * <p>This used to be a field the model filled in, and the model over-flagged: "The square of the
 * time period of revolution of a planet is proportional to the cube of the semi-major axis" is a
 * law stated in words, with no expression in it, and came back flagged. The transcription
 * conventions make the answer computable — every expression the prompt produces is written with
 * the same small set of marks — so it is computed, not judged (D14; the principle is in
 * .claude/rules/ai-layer.md: never ask a model for what code can decide).
 *
 * <p>It matters more than a boolean usually would: it is the filter a later equation-verification
 * pass runs on, so a false positive is wasted human attention and a false negative hides an error.
 */
final class Equations {

    /** An approximation, in the fixed spelling the prompt requires. */
    private static final String APPROXIMATION = "approx=";

    /** A reaction or relation arrow: 2H_2 + O_2 -> 2H_2O, N_2 + 3H_2 <-> 2NH_3. */
    private static final String ARROW = "<->|->|<=|>=|!=";

    /** A symbol given a value: "v = u + at", "M_E =". Not "x = y" inside prose about algebra. */
    private static final String ASSIGNMENT = "(?<![A-Za-z])[A-Za-z]'?(?:_[A-Za-z0-9]+)?\\s*=\\s*[^=]";

    /** An exponent: r^2, 10^-11, R_E^2. */
    private static final String EXPONENT = "[A-Za-z0-9)\\]]\\s*\\^\\s*[-+]?[0-9A-Za-z(]";

    /** A subscript: v_0, M_E, r_21. */
    private static final String SUBSCRIPT = "[A-Za-z0-9)\\]]_[0-9A-Za-z(]";

    /** A root, in the fixed spelling the prompt requires. */
    private static final String ROOT = "\\bsqrt\\s*\\(";

    /** A chemical state attached to a formula: (s), (l), (g), (aq). */
    private static final String STATE = "\\((?:s|l|g|aq)\\)(?=[^A-Za-z]|$)";

    /** Scientific notation, in the fixed spelling the prompt requires: 3.84 x 10^8. */
    private static final String SCIENTIFIC = "\\bx\\s*10\\s*\\^";

    private static final Pattern EXPRESSION = Pattern.compile(String.join("|",
            Pattern.quote(APPROXIMATION), ARROW, ASSIGNMENT, EXPONENT, SUBSCRIPT, ROOT, STATE, SCIENTIFIC));

    private Equations() {
    }

    /** Whether this paragraph's text carries an expression. Null or blank text does not. */
    static boolean present(String text) {
        return text != null && EXPRESSION.matcher(text).find();
    }
}
