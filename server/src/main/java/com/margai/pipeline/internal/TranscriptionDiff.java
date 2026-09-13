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
 * <p>It asks one question, one-sided and threshold-free: <b>does the page contain this word?</b> A
 * word the paragraph uses more often than the whole page holds it is invented or misread, which is
 * how {@code Kepler's third law} became {@code For the moon} at D14. There is no budget, no
 * tolerance, and no need to locate the paragraph within the page.
 *
 * <p>It asked a second question once — is this symbol on the page, with {@code m_p} glued back to
 * the {@code mp} the layer holds — and the chapter-7 dry run retired it: 51 flags, every one a
 * false positive. The reason is worth keeping, because it bounds what any layer-based check can
 * ever do here. PDFBox emits a <em>displayed</em> equation by typographic row rather than in
 * reading order, so {@code R_m²} arrives as {@code 2 / m / R} and a page of them arrives as
 * {@code 22 / fi E / mVmV GmM} — base and script are not merely reordered, their association is
 * gone. Inline symbols in running prose do glue ({@code Lp = mp rp vp}), but nothing in a
 * transcription says which kind a given symbol was. Verifying a formula therefore needs the page
 * <em>image</em>, which is what {@code ncert verify --read-pages} is for.
 *
 * <p>Nothing the page has and the model did not produce is ever flagged: captions, running heads,
 * table interiors and the apparatus are all skipped deliberately.
 *
 * <p><b>What it cannot see, stated plainly, because a check that cries "none" when it looked at
 * nothing is worse than no check.</b> Punctuation is squashed away, so a dropped multiplication
 * sign, a lost leading minus and a dropped prime are invisible to it — and on this corpus they are
 * invisible in principle rather than by omission: NCERT sets those glyphs in a Symbol font with no
 * Unicode mapping, so {@code Kepler's} reaches the text layer as {@code Keplers}, and the prime the
 * model stands accused of dropping is not in the layer either. Nor can it see a wrong symbol, per
 * the paragraph above. Those defects are addressed by instruction (FIX 2) and caught, if at all, by
 * a second read of the page image or by the founder's eye on it.
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

        // One line per distinct divergence: a word misread three times in a paragraph is one thing
        // to adjudicate, not three.
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
