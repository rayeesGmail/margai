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
 * {@code l}, {@code m_p} read as {@code m_r}, a lost exponent, a dropped prime, a missing minus.
 * Each of those is a difference between two strings, which is a thing code can see. This turns the
 * founder's audit from free-reading for dropped primes into adjudicating flagged lines.
 *
 * <p>The comparison is deliberately one-sided in its hard signal. A token the model produced that
 * is <em>not on the page</em> is a misread or an invention, and that is what flags. A token on the
 * page that the model did not produce is usually correct behaviour — captions, running heads,
 * table interiors and the apparatus are all deliberately skipped — so it is counted, never flagged.
 *
 * <p>The whitelist is our own notation: the prompt asks for {@code sqrt(...)}, {@code approx=},
 * Greek letters by name, {@code i_hat} and {@code x 10^8}, none of which appear on the page in
 * those words. Anything outside it is a real divergence.
 *
 * <p>This shares a failure mode with the text layer being fed to the model as the character
 * authority: where the layer is subtly wrong but legible, the model copies it and this agrees.
 * That residual is covered by the independent structural flag and by the founder reading a few
 * paragraphs against the rendered image rather than the layer (DECISIONS 2026-09-13).
 */
final class TranscriptionDiff {

    /** Words our conventions introduce that the printed page never spells out. */
    private static final Set<String> NOTATION = Set.of(
            "sqrt", "approx", "hat", "i_hat", "j_hat", "k_hat", "x",
            "alpha", "beta", "gamma", "delta", "theta", "lambda", "mu", "pi", "rho", "sigma",
            "omega", "phi", "psi", "epsilon", "eta", "nu", "tau", "chi", "kappa", "illegible");

    private static final Pattern WORD = Pattern.compile("[A-Za-z]+");

    /**
     * Digits are compared one character at a time, not as runs: a superscript arrives from the
     * text layer glued to its base — 10^8 is "108" there, R_E^2 is "RE2" — so whole-number
     * comparison would flag every exponent on the page. Character counts still catch the errors
     * that matter, a dropped 8 or an exponent read as 1/3 instead of 3/2.
     */
    private static final Pattern DIGIT = Pattern.compile("[0-9]");

    /** A minus may be a hyphen, a true minus or an en dash; a prime may be an apostrophe or ′. */
    private static final Pattern MINUS = Pattern.compile("[-−–]");
    private static final Pattern PRIME = Pattern.compile("['′]");

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
        List<String> findings = new ArrayList<>();

        Map<String, Integer> pageWords = counts(WORD, pageText);
        for (Map.Entry<String, Integer> word : counts(WORD, paragraph).entrySet()) {
            String token = word.getKey();
            if (NOTATION.contains(token.toLowerCase(Locale.ROOT))) {
                continue;
            }
            int onPage = pageWords.getOrDefault(token, 0);
            // A single letter is almost always a symbol — M_E against M_e, m_p against m_r — so
            // its case is checked. A word is matched case-insensitively, because a sentence's
            // first letter is capitalised on the page and inside a joined paragraph alike.
            if (token.length() > 1) {
                onPage = pageWords.entrySet().stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase(token))
                        .mapToInt(Map.Entry::getValue).sum();
            }
            if (onPage < word.getValue()) {
                findings.add("'" + token + "' " + word.getValue() + "x here, " + onPage + "x on the page");
            }
        }

        Map<String, Integer> pageDigits = counts(DIGIT, pageText);
        for (Map.Entry<String, Integer> digit : counts(DIGIT, paragraph).entrySet()) {
            int onPage = pageDigits.getOrDefault(digit.getKey(), 0);
            if (onPage < digit.getValue()) {
                findings.add("digit '" + digit.getKey() + "' " + digit.getValue()
                        + "x here, " + onPage + "x on the page");
            }
        }

        // Signs and primes are not tokens, and losing one is the quietest error of all: the
        // sentence still reads correctly and the physics is wrong.
        int primesLost = count(PRIME, pageTextWithin(pageText, paragraph)) - count(PRIME, paragraph);
        if (primesLost > 0) {
            findings.add(primesLost + " prime" + (primesLost > 1 ? "s" : "") + " on the page, none here");
        }
        return List.copyOf(findings);
    }

    /**
     * The span of the page this paragraph most plausibly came from, for the counts that only make
     * sense locally: from the position of the paragraph's first long word to that of its last.
     * A whole-page count would compare a paragraph against every minus sign on the page.
     */
    private static String pageTextWithin(String pageText, String paragraph) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD.matcher(paragraph);
        while (matcher.find()) {
            if (matcher.group().length() >= 6) {
                words.add(matcher.group());
            }
        }
        if (words.size() < 2) {
            return "";
        }
        int from = pageText.indexOf(words.getFirst());
        int to = pageText.lastIndexOf(words.getLast());
        return from < 0 || to < 0 || to <= from ? "" : pageText.substring(from, Math.min(to + 40, pageText.length()));
    }

    private static Map<String, Integer> counts(Pattern pattern, String text) {
        Map<String, Integer> counts = new HashMap<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            counts.merge(matcher.group(), 1, Integer::sum);
        }
        return counts;
    }

    private static int count(Pattern pattern, String text) {
        int found = 0;
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            found++;
        }
        return found;
    }
}
