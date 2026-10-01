package com.margai.pipeline.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Figure and table labels as NCERT prints them — "Fig. 7.1(a)", "Fig.7.6", "Figure 10.2", "Table 7.1"
 * — read into one comparable shape (D15). A correction that splits a paragraph moves each figure
 * reference with the half that mentions it, and {@code ncert verify} checks every reference against
 * the paragraph's own words, so both need to know that "Fig. 7.1a" and "Fig. 7.1 (a)" are one label.
 */
final class FigureLabels {

    /**
     * The word, the chapter-numbered label, and a part letter written "(a)" or glued on as "7.1a" — a
     * bare letter after a space is the next word ("Fig 7.3 a point"), not a part.
     */
    private static final Pattern LABEL = Pattern.compile(
            "\\b(Fig(?:ure)?s?\\.?|Table)\\s*(\\d{1,2}\\.\\d{1,2})(?:\\s*\\(([a-z])\\)|([a-z])(?![a-z]))?",
            Pattern.CASE_INSENSITIVE);

    private FigureLabels() {
    }

    /**
     * One label.
     *
     * @param kind   {@code fig} or {@code table}
     * @param number "7.1"
     * @param part   "a", or null when the label names the whole figure
     */
    record Label(String kind, String number, String part) {

        /** The label without its part: "fig 7.1" for "Fig. 7.1(a)". */
        String base() {
            return kind + " " + number;
        }

        @Override
        public String toString() {
            return base() + (part == null ? "" : part);
        }
    }

    /** Every label the text mentions, in order. */
    static List<Label> in(String text) {
        List<Label> labels = new ArrayList<>();
        if (text == null) {
            return labels;
        }
        Matcher matcher = LABEL.matcher(text);
        while (matcher.find()) {
            labels.add(label(matcher));
        }
        return labels;
    }

    /** The label a figure_refs entry names, when it names one. */
    static Optional<Label> of(String ref) {
        Matcher matcher = LABEL.matcher(ref == null ? "" : ref.strip());
        return matcher.lookingAt() ? Optional.of(label(matcher)) : Optional.empty();
    }

    private static Label label(Matcher matcher) {
        String kind = matcher.group(1).toLowerCase(Locale.ROOT).startsWith("t") ? "table" : "fig";
        String part = matcher.group(3) != null ? matcher.group(3) : matcher.group(4);
        return new Label(kind, matcher.group(2), part == null ? null : part.toLowerCase(Locale.ROOT));
    }
}
