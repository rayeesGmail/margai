package com.margai.pipeline.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Every transcribed paragraph, checked character by character against the page's own text layer.
 *
 * <p>The D14 audit found the transcription errors by reading pages against the database by eye, at
 * roughly an hour a chapter, and the errors it found were never in the prose: a {@code 1} read as
 * {@code l}, {@code m_p} read as {@code m_r}, a lost exponent, an exponent read as its reciprocal.
 * Each of those is a difference between two strings, which is a thing code can see. This turns the
 * founder's audit from free-reading for misread glyphs into adjudicating flagged lines.
 *
 * <p><b>Everything here compares squashed text</b> — letters and digits only, every space, line
 * break and punctuation mark removed — because that is the only shape in which the two sides are
 * comparable. A real NCERT text layer (measured on {@code keph107.pdf}, D14) glues an inline
 * subscript to its base, {@code L_p} arriving as {@code Lp} and {@code 3.84 × 10⁸} as
 * {@code 3.84 × 108}; splits a displayed equation's subscripts onto lines of their own in an order
 * unrelated to reading order; and breaks the occasional word with kerning, {@code the p lanet}. A
 * token-level comparison against that produces a false flag on every paragraph that carries a
 * symbol, which is precisely the set of paragraphs this exists to check.
 *
 * <p>It asks two questions, both one-sided and both threshold-free. <b>Does the page contain this
 * word?</b> — a word the paragraph uses more often than the whole page holds it is invented or
 * misread, which is how {@code Kepler's third law} became {@code For the moon} at D14. And <b>is
 * this symbol on the page?</b> — every {@code m_p}, {@code R_E}, {@code 10^8} the transcription
 * writes is glued back into the form the layer holds ({@code mp}, {@code RE}, {@code 108}) and
 * looked for, which is how {@code m_p} read as {@code m_r} is caught: {@code mr} is nowhere on that
 * page. Neither question has a budget or a tolerance to tune, and neither depends on locating the
 * paragraph within the page — the two things that made a first, count-based cut of this class flag
 * correct work.
 *
 * <p>Nothing the page has and the model did not produce is ever flagged: captions, running heads,
 * table interiors and the apparatus are all skipped deliberately.
 *
 * <p><b>What it cannot see, stated plainly, because a check that cries "none" when it looked at
 * nothing is worse than no check.</b> Punctuation is squashed away, so a dropped multiplication
 * sign, a lost leading minus and a dropped prime are invisible to it — and on this corpus they are
 * invisible in principle rather than by omission: NCERT sets those glyphs in a Symbol font with no
 * Unicode mapping, so {@code Kepler's} reaches the text layer as {@code Keplers}, and the prime the
 * model stands accused of dropping is not in the layer either. A superscript with an operator in it
 * ({@code (1.52)^(3/2)}) has no single glued form and is skipped. Those defects are addressed by
 * instruction (FIX 2) and caught, if at all, by the founder's eye on the rendered image.
 *
 * <p>This also shares a failure mode with the text layer being fed to the model as the character
 * authority: where the layer is subtly wrong but legible, the model copies it and this agrees. That
 * residual is not covered by the structural flag — which cannot see characters at all — but by the
 * founder reading a few paragraphs against the rendered image (DECISIONS 2026-09-13).
 */
final class TranscriptionDiff {

    /** Words our conventions introduce that the printed page never spells out. */
    private static final Set<String> NOTATION = Set.of(
            "sqrt", "approx", "hat", "x", "illegible",
            "alpha", "beta", "gamma", "delta", "theta", "lambda", "mu", "pi", "rho", "sigma",
            "omega", "phi", "psi", "epsilon", "eta", "nu", "tau", "chi", "kappa");

    private static final Pattern WORD = Pattern.compile("[A-Za-z]+");

    /** Everything squashing keeps. */
    private static final Pattern NOT_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]+");

    /**
     * One subscripted or superscripted symbol as the prompt asks for it — {@code m_p}, {@code R_E},
     * {@code T_M}, {@code 10^8}, {@code R_E^2} — and deliberately nothing with a bracket or an
     * operator in the script ({@code (1.52)^(3/2)}), which has no single glued form to look for.
     */
    private static final Pattern SCRIPT = Pattern.compile("[A-Za-z0-9]+(?:[_^][A-Za-z0-9]+)+");

    /** Shorter than this and a "word" is a symbol fragment, not a word (see the class note). */
    private static final int MIN_WORD = 3;

    private TranscriptionDiff() {
    }

    /**
     * What this paragraph says that its page does not. Empty means the characters agree.
     *
     * @param pageText  the page's text layer, private-use codepoints already decoded
     * @param paragraph the transcription to check
     */
    static List<String> check(String pageText, String paragraph) {
        if (pageText == null || pageText.isBlank() || paragraph == null || paragraph.isBlank()) {
            return List.of();
        }
        String said = squash(withoutNotation(paragraph));
        String page = squash(pageText);
        if (said.isEmpty() || page.isEmpty()) {
            return List.of();
        }
        List<String> findings = new ArrayList<>();

        // A word the paragraph uses more often than the whole page holds it is invented or
        // misread, wherever on the page it sits. Whole-page and case-insensitive, so this cannot
        // fire on a paragraph that was merely placed wrongly.
        String lowerPage = page.toLowerCase(Locale.ROOT);
        String lowerSaid = said.toLowerCase(Locale.ROOT);
        for (String word : words(paragraph)) {
            // Both sides counted the same way — as substrings of squashed text — so a word the
            // layer happens to have glued to its neighbour still counts as present.
            int here = occurrences(lowerSaid, word);
            int onPage = occurrences(lowerPage, word);
            if (onPage < here) {
                findings.add("'" + word + "' " + here + "x here, " + onPage + "x on the page");
            }
        }

        // Every subscript and superscript the transcription writes, glued back into the shape the
        // text layer holds it in, must be somewhere on the page. This is the check that catches the
        // defect class the audit was full of — m_p read as m_r — and it is exact: no budget, no
        // threshold, no located span. Case matters, because M_E against M_e is one of the defects.
        Matcher script = SCRIPT.matcher(withoutNotation(paragraph));
        while (script.find()) {
            String glued = squash(script.group());
            if (glued.length() > 1 && !page.contains(glued)) {
                findings.add("'" + script.group() + "' is not on the page (as '" + glued + "')");
            }
        }
        // One line per distinct divergence: a symbol misread three times in a paragraph is one
        // thing to adjudicate, not three.
        return List.copyOf(new java.util.LinkedHashSet<>(findings));
    }

    /** The paragraph's own distinct words, our notation removed, symbol fragments excluded. */
    private static Set<String> words(String paragraph) {
        Set<String> words = new java.util.LinkedHashSet<>();
        Matcher matcher = WORD.matcher(withoutNotation(paragraph));
        while (matcher.find()) {
            if (matcher.group().length() >= MIN_WORD) {
                words.add(matcher.group().toLowerCase(Locale.ROOT));
            }
        }
        return words;
    }

    private static String withoutNotation(String text) {
        StringBuilder kept = new StringBuilder(text.length());
        int at = 0;
        Matcher matcher = WORD.matcher(text);
        while (matcher.find()) {
            if (NOTATION.contains(matcher.group().toLowerCase(Locale.ROOT))) {
                kept.append(text, at, matcher.start()).append(' ');
                at = matcher.end();
            }
        }
        return kept.append(text.substring(at)).toString();
    }

    private static String squash(String text) {
        return NOT_ALPHANUMERIC.matcher(text).replaceAll("");
    }

    /** Non-overlapping occurrences of one string in another. */
    private static int occurrences(String haystack, String needle) {
        int found = 0;
        int at = haystack.indexOf(needle);
        while (at >= 0) {
            found++;
            at = haystack.indexOf(needle, at + needle.length());
        }
        return found;
    }
}
