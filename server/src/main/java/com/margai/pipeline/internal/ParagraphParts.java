package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A loaded paragraph divided back into what each of its pages printed (D15). {@code ncert load} joins
 * the halves of a paragraph that straddles a page break and records where each page's part begins;
 * {@code ncert verify} holds each page to its own part, because the second half of a sentence is not
 * printed on the page its first half is read from.
 */
final class ParagraphParts {

    /** How many letters of a paragraph's opening decide whether two openings are the same. */
    private static final int OPENING_LETTERS = 12;
    /** Below this many letters on either side an opening is compared whole. */
    private static final int MIN_OPENING_LETTERS = 6;
    /** How many letters of the layer's line are looked for in the row when the line's math is missing. */
    private static final int MATH_DROPPED_LETTERS = 24;
    /** How many letters the row may carry in that stretch that the layer's line does not. */
    private static final int MATH_DROPPED_ALLOWANCE = 12;
    /** How many letters must agree before the first gap, so a line is not paired with any paragraph. */
    private static final int MATH_DROPPED_PREFIX = 3;

    /**
     * A subscript or superscript in the transcription's notation. The text layer sets these on a
     * baseline of their own, so the layer's line carries "M" where the transcription writes M_E.
     */
    private static final Pattern SCRIPT = Pattern.compile("[_^](\\([^)]*\\)|-?[A-Za-z0-9]+)");

    private ParagraphParts() {
    }

    /**
     * @param page                  the page within the chapter's PDF
     * @param text                  what that page prints of the paragraph
     * @param continuesFromPrevious the paragraph began on an earlier page
     * @param continuesOnNext       the paragraph runs on to a later page
     */
    record Part(int page, String text, boolean continuesFromPrevious, boolean continuesOnNext) {
    }

    /** Whether the row carries an offset for each of its pages; a row loaded before D15 does not. */
    static boolean divisible(NcertParagraphRow row) {
        ParagraphExtraction extraction = row.extraction();
        return extraction != null && !extraction.pages().isEmpty()
                && extraction.pageStarts().size() == extraction.pages().size();
    }

    static List<Part> of(NcertParagraphRow row) {
        if (!divisible(row)) {
            throw new IllegalArgumentException(row.address() + " has no page offsets — reload its chapter with "
                    + "`ncert load` (no model, no cost) before verifying it");
        }
        List<Integer> pages = row.extraction().pages();
        List<Integer> starts = row.extraction().pageStarts();
        List<Part> parts = new ArrayList<>(pages.size());
        for (int index = 0; index < pages.size(); index++) {
            int from = Math.min(starts.get(index), row.text().length());
            int to = index + 1 < pages.size() ? Math.min(starts.get(index + 1), row.text().length()) : row.text().length();
            parts.add(new Part(pages.get(index), row.text().substring(from, Math.max(from, to)).strip(),
                    index > 0, index + 1 < pages.size()));
        }
        return List.copyOf(parts);
    }

    /**
     * A paragraph's opening in a form the transcription and the text layer share: letters only, lower
     * case, a box ornament glued before a capital dropped ("tExample 7.1"), the transcription's
     * subscripts and exponents dropped. Those, primes and apostrophes are where the two sides differ.
     */
    static String opening(String text) {
        String letters = letters(text);
        return letters.length() > OPENING_LETTERS ? letters.substring(0, OPENING_LETTERS) : letters;
    }

    /** Whether two openings are the same paragraph's, allowing one to be shorter than the other. */
    static boolean sameOpening(String left, String right) {
        String a = opening(left);
        String b = opening(right);
        int length = Math.min(a.length(), b.length());
        return length >= MIN_OPENING_LETTERS ? a.substring(0, length).equals(b.substring(0, length)) : a.equals(b);
    }

    /**
     * Whether a line of the text layer is a row's opening with the line's math missing. A page's symbol
     * fonts are often mapped to no Unicode at all, so the layer gives chapter 7 page 7's "For h/R_E &lt;&lt; 1,
     * using binomial expression," as "For , using binomial expression," — the row's letters with a run cut
     * out of them. The line's letters must therefore all appear, in order, close together at the row's
     * opening, and enough of them must agree before the first gap that the line cannot be paired with just
     * any paragraph. Only the layer may be missing letters: a row that is missing the print's is a defect,
     * and this is not the place to hide it.
     */
    static boolean openingWithMathDropped(String printedLine, String rowText) {
        String line = letters(printedLine);
        String row = letters(rowText);
        if (line.length() > MATH_DROPPED_LETTERS) {
            line = line.substring(0, MATH_DROPPED_LETTERS);
        }
        if (line.length() < MIN_OPENING_LETTERS || !row.startsWith(line.substring(0, MATH_DROPPED_PREFIX))) {
            return false;
        }
        int within = Math.min(row.length(), line.length() + MATH_DROPPED_ALLOWANCE);
        int at = 0;
        for (int index = 0; index < within && at < line.length(); index++) {
            if (row.charAt(index) == line.charAt(at)) {
                at++;
            }
        }
        return at == line.length();
    }

    /** A text's letters in the form the transcription and the layer share: see {@link #opening}. */
    private static String letters(String text) {
        return SCRIPT.matcher((text == null ? "" : text.strip()).replaceFirst("^[a-z](?=[A-Z])", ""))
                .replaceAll("").replaceAll("[^A-Za-z]", "").toLowerCase(Locale.ROOT);
    }
}
