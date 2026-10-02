package com.margai.pipeline.internal;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Hindi page's rows held to its text layer as {@link HindiLayer} reads it — the checks that found every real
 * defect of the D16 trials, where neither paid second read found one (SPEC §12.2, DECISIONS 2026-10-02). They
 * route attention like the English layer checks and never refuse a run.
 *
 * <p>Two of them, because the layer carries two kinds of text. Its Devanagari is decoded Chanakya: a word the
 * rows carry more often than the page is a misreading or an addition (Opus 5's दाशिमक for the printed
 * दाश्मिक). Its digits, Latin and symbols are Unicode already: a symbol the page carries more often than the rows
 * is one the transcription lost (khph101 p5's undrawn ×, p4's seven bullets), and a digit or Latin token the
 * rows carry more often than the page was misread. Digits and Latin only the page carries are not named — a
 * table, a running head or a page number puts them there.
 */
final class HindiChecks {

    /**
     * A Devanagari word: letters, signs and marks, without the danda — and without the visarga, which the
     * layer prints where the rows often write a colon (अतः, अत:).
     */
    private static final Pattern WORD = Pattern.compile("[ऀ-ंऄ-ॣ०-ॿ]+");

    /**
     * The tokens of the Unicode spans: a number, a Latin word, or one of the symbols no convention renames —
     * Greek letters (named by the prompt), √ (sqrt), ° (a zero exponent's ^0) and ± (often a Symbol glyph the
     * layer cannot map) are left out, measured on khph101 (D16).
     */
    private static final Pattern TOKEN = Pattern.compile("\\d+(?:\\.\\d+)?|[A-Za-z]{2,}|[×÷•≤≥≈≅≃→∞∝]");

    /** The tokens that are symbols, whose loss the page-side count catches. */
    private static final Pattern SYMBOL = Pattern.compile("[×÷•≤≥≈≅≃→∞∝]");

    /** The prompt's spellings for what the page prints as a symbol; never a Latin word the page lacks. */
    private static final java.util.Set<String> NOTATION = java.util.Set.of("pi", "theta", "omega", "rho", "mu",
            "lambda", "alpha", "beta", "gamma", "delta", "sigma", "phi", "psi", "tau", "nu", "epsilon", "eta",
            "kappa", "chi", "xi", "zeta", "sqrt", "hat", "vec", "bar", "approx", "integral", "sum", "partial",
            "perp", "par", "infinity");

    private HindiChecks() {
    }

    /**
     * The Devanagari words the rows carry and the decoded page does not: misreadings and additions. A word the
     * page carries fewer times is not named — a word the layer splits across lines makes the page one short.
     */
    static List<String> devanagari(String page, String rows) {
        Map<String, Integer> onPage = counts(WORD, page);
        Map<String, Integer> inRows = counts(WORD, rows);
        List<String> findings = new ArrayList<>();
        inRows.forEach((word, count) -> {
            if (!onPage.containsKey(word)) {
                findings.add("'" + word + "' " + count + "x in the rows, 0x on the page");
            }
        });
        return findings;
    }

    /**
     * The page's digits, Latin and symbols against the rows: symbols the page carries more often (lost), then
     * digits and Latin the rows carry and the page does not (misread). The page is its whole text, decoded runs
     * included — some books set their digits in the Chanakya font, where they decode to themselves.
     */
    static List<String> spans(String page, String rows) {
        Map<String, Integer> onPage = counts(TOKEN, page);
        Map<String, Integer> inRows = counts(TOKEN, rows);
        List<String> findings = new ArrayList<>();
        onPage.forEach((token, count) -> {
            int carried = inRows.getOrDefault(token, 0);
            if (SYMBOL.matcher(token).matches() && count > carried) {
                findings.add("'" + token + "' " + count + "x on the page, " + carried + "x in the rows");
            }
        });
        inRows.forEach((token, count) -> {
            if (!onPage.containsKey(token) && !NOTATION.contains(token.toLowerCase(java.util.Locale.ROOT))) {
                findings.add("'" + token + "' " + count + "x in the rows, 0x on the page");
            }
        });
        return findings;
    }

    /** The share of the page's Devanagari words the rows carry; an empty page is fully covered. */
    static double coverage(String page, String rows) {
        Map<String, Integer> onPage = counts(WORD, page);
        Map<String, Integer> inRows = counts(WORD, rows);
        int total = onPage.values().stream().mapToInt(Integer::intValue).sum();
        if (total == 0) {
            return 1.0;
        }
        int carried = onPage.entrySet().stream()
                .mapToInt(entry -> Math.min(entry.getValue(), inRows.getOrDefault(entry.getKey(), 0))).sum();
        return (double) carried / total;
    }

    /** What one run found, page by page, for the report extract and verify both write. */
    static final class Findings {

        /** Below this share of a page's Devanagari words in its rows, the page is listed (khph101: 0.90–0.99, its table page 0.35). */
        static final double COVERAGE_FLOOR = 0.6;

        private final List<String> words = new ArrayList<>();
        private final List<String> spans = new ArrayList<>();
        private final List<String> coverage = new ArrayList<>();
        private int pages;

        /** One page's rows against its layer; the Summary's page is not judged for coverage — its rows must not carry the Summary. */
        void check(String at, String layer, String rows, boolean summaryPage) {
            pages++;
            devanagari(layer, rows).forEach(finding -> words.add(at + ": " + finding));
            spans(layer, rows).forEach(finding -> spans.add(at + ": " + finding));
            double share = coverage(layer, rows);
            if (!summaryPage && share < COVERAGE_FLOOR) {
                coverage.add(at + ": the rows carry " + Math.round(share * 100) + "% of the page's Devanagari words");
            }
        }

        void report(Report report, String checkedOf) {
            String none = pages == 0 ? "nothing was checked" : "none on the pages checked";
            report.section("Hindi rows against the decoded layer — a check only; the layer is never sent or stored")
                    .line("checked: " + pages + checkedOf)
                    .line("")
                    .line("words the rows carry that the page does not — a misreading or an addition:")
                    .list(words, none)
                    .line("")
                    .line("symbols the page carries more often, digits and Latin only the rows carry:")
                    .list(spans, none)
                    .line("")
                    .line("pages whose rows carry little of the page's Devanagari — an omission, a table or a sidebar:")
                    .list(coverage, none);
        }
    }

    private static Map<String, Integer> counts(Pattern pattern, String text) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        if (text == null) {
            return counts;
        }
        Matcher matcher = pattern.matcher(Normalizer.normalize(text, Normalizer.Form.NFC));
        while (matcher.find()) {
            counts.merge(matcher.group(), 1, Integer::sum);
        }
        return counts;
    }
}
