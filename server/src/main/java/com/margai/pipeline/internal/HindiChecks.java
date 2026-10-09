package com.margai.pipeline.internal;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

    /**
     * The prompt's spellings for what the page prints as a symbol; never a Latin word the page lacks. "to" is
     * the rows' range word — integral(a to b), sum(i=1 to n) — never a limit arrow (D16).
     */
    private static final Set<String> NOTATION = Set.of("pi", "theta", "omega", "rho", "mu",
            "lambda", "alpha", "beta", "gamma", "delta", "sigma", "phi", "psi", "tau", "nu", "epsilon", "eta",
            "kappa", "chi", "xi", "zeta", "sqrt", "hat", "vec", "bar", "approx", "integral", "sum", "partial",
            "perp", "par", "infinity", "to");

    /** The rows' arrows: a limit or a map written ->, a vector written _vec, each an → the page draws. */
    private static final Pattern ARROW = Pattern.compile("->|_vec");

    /** A one-letter subscript run into the next symbol, m_ix_i or A_xB_x, which the page sets apart. */
    private static final Pattern RUN_ON_SUBSCRIPT = Pattern.compile("(_[A-Za-z0-9])(?=[A-Za-z][_^])");

    /**
     * The हृ glyph and the aa-stroke, which the books type for two conjuncts the font draws alike: ह्य in बाह्य
     * (phy11-part2) and ह्र + ा in ह्रास, अपह्रासित (Biology). The glyphs cannot say which, so the decoder keeps
     * them as typed and a page word holding them stands for either reading (D16).
     */
    private static final String HOOKED_HA_AA = "हृा";

    /** The joiners a row may write between a visible halant and the next letter (दिक्‌स्थान); the layer has none. */
    private static final Pattern JOINER = Pattern.compile("[\\u200C\\u200D]");

    private HindiChecks() {
    }

    /**
     * The Devanagari words the rows carry and the decoded page does not: misreadings and additions. A word the
     * page carries fewer times is not named — a word the layer splits across lines makes the page one short —
     * and a word the page breaks at a line end with no hyphen (अत्यधि|क) is on it.
     */
    static List<String> devanagari(String page, String rows) {
        Map<String, Integer> onPage = counts(WORD, page);
        Set<String> alsoOnPage = lineEndJoins(page);
        onPage.keySet().stream().filter(word -> word.contains(HOOKED_HA_AA)).forEach(word -> {
            alsoOnPage.add(word.replace(HOOKED_HA_AA, "ह्य"));
            alsoOnPage.add(word.replace(HOOKED_HA_AA, "ह्रा"));
        });
        Map<String, Integer> inRows = counts(WORD, rows);
        List<String> findings = new ArrayList<>();
        inRows.forEach((word, count) -> {
            if (!onPage.containsKey(word) && !alsoOnPage.contains(word)) {
                findings.add("'" + word + "' " + count + "x in the rows, 0x on the page");
            }
        });
        return findings;
    }

    static List<String> spans(String page, String rows) {
        return spans(page, "", rows);
    }

    /**
     * The page's digits, Latin and symbols against the rows: symbols the page carries more often (lost), then
     * digits and Latin the rows carry and the page does not (misread). The page is its whole text, decoded runs
     * included — some books set their digits in the Chanakya font, where they decode to themselves. The rows
     * are read in the page's notation first: their arrows as →, a run-on subscript set apart. A token the rows
     * carry is also found in the page's Latin read by baseline ({@link HindiLayer.Page#latin()}), or as a
     * spelled Greek letter with one letter after it (lambdaA), and a ≃, ≈ or ≅ in the ~ the page draws it with.
     */
    static List<String> spans(String page, String pageLatin, String rows) {
        Map<String, Integer> onPage = counts(TOKEN, page);
        Set<String> alsoOnPage = counts(TOKEN, pageLatin).keySet();
        String asPrinted = RUN_ON_SUBSCRIPT.matcher(ARROW.matcher(rows == null ? "" : rows).replaceAll(" → "))
                .replaceAll("$1 ");
        Map<String, Integer> inRows = counts(TOKEN, asPrinted);
        boolean tilde = page != null && page.indexOf('~') >= 0;
        List<String> findings = new ArrayList<>();
        onPage.forEach((token, count) -> {
            int carried = inRows.getOrDefault(token, 0);
            if (SYMBOL.matcher(token).matches() && count > carried) {
                findings.add("'" + token + "' " + count + "x on the page, " + carried + "x in the rows");
            }
        });
        inRows.forEach((token, count) -> {
            boolean found = onPage.containsKey(token) || alsoOnPage.contains(token) || notation(token)
                    || (tilde && "≃≈≅".contains(token));
            if (!found) {
                findings.add("'" + token + "' " + count + "x in the rows, 0x on the page");
            }
        });
        return findings;
    }

    /** A spelling of the prompt's, alone or with one letter after it — λA written lambdaA. */
    private static boolean notation(String token) {
        String lower = token.toLowerCase(Locale.ROOT);
        return NOTATION.contains(lower) || NOTATION.stream()
                .anyMatch(name -> lower.startsWith(name) && lower.length() == name.length() + 1);
    }

    /** Each line's last word joined to the next line's first: a word broken at a line end with no hyphen. */
    private static Set<String> lineEndJoins(String page) {
        Set<String> joins = new HashSet<>();
        if (page == null) {
            return joins;
        }
        String[] lines = page.split("\n");
        for (int index = 0; index + 1 < lines.length; index++) {
            Matcher end = WORD.matcher(lines[index]);
            String last = null;
            while (end.find()) {
                last = end.end() == lines[index].length() ? end.group() : null;
            }
            Matcher start = WORD.matcher(lines[index + 1]);
            if (last != null && start.find() && start.start() == 0) {
                joins.add(Normalizer.normalize(last + start.group(), Normalizer.Form.NFC));
            }
        }
        return joins;
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
        void check(String at, HindiLayer.Page layer, String rows, boolean summaryPage) {
            pages++;
            devanagari(layer.devanagari(), rows).forEach(finding -> words.add(at + ": " + finding));
            spans(layer.devanagari(), layer.latin(), rows).forEach(finding -> spans.add(at + ": " + finding));
            double share = coverage(layer.devanagari(), rows);
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
        Matcher matcher = pattern.matcher(Normalizer.normalize(JOINER.matcher(text).replaceAll(""), Normalizer.Form.NFC));
        while (matcher.find()) {
            counts.merge(matcher.group(), 1, Integer::sum);
        }
        return counts;
    }
}
