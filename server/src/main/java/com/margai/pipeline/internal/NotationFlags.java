package com.margai.pipeline.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Free checks on a transcription's notation, beside the character diff — signs that a glyph the
 * text layer garbled was copied through instead of read from the image.
 *
 * <p>The first case is real and belongs to the strongest model: chapter 7 page 6 of phy11-part1
 * sets τ in a Symbol font that the layer renders as a degree sign, and Opus 5 wrote "Where ° is
 * the restoring couple per unit angle of twist" twice (D15). A degree sign only ever follows a
 * number, so one that does not is the layer's τ, θ or ρ standing where a letter should be. Like
 * the character diff, this routes a human's attention and never blocks a run (TECH_PLAN §6.3).
 */
final class NotationFlags {

    /** A degree sign whose preceding non-space character is not a digit. */
    private static final Pattern STRAY_DEGREE = Pattern.compile("(?<![0-9])(?<![0-9] )°");

    private NotationFlags() {
    }

    /** One line per finding, quoting the words around it. */
    static List<String> check(String text) {
        List<String> findings = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return findings;
        }
        Matcher matcher = STRAY_DEGREE.matcher(text);
        while (matcher.find()) {
            int from = Math.max(0, matcher.start() - 12);
            int to = Math.min(text.length(), matcher.end() + 24);
            findings.add("a degree sign not after a number — the layer's Greek letter copied through?: \"…"
                    + text.substring(from, to).replaceAll("\\s+", " ") + "…\"");
        }
        return findings;
    }
}
