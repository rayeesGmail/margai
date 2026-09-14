package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
     * case, a box ornament glued before a capital dropped ("tExample 7.1"). Subscripts, primes and
     * apostrophes are where the two sides differ, and none of them is a letter.
     */
    static String opening(String text) {
        String letters = (text == null ? "" : text.strip()).replaceFirst("^[a-z](?=[A-Z])", "")
                .replaceAll("[^A-Za-z]", "").toLowerCase(Locale.ROOT);
        return letters.length() > OPENING_LETTERS ? letters.substring(0, OPENING_LETTERS) : letters;
    }

    /** Whether two openings are the same paragraph's, allowing one to be shorter than the other. */
    static boolean sameOpening(String left, String right) {
        String a = opening(left);
        String b = opening(right);
        int length = Math.min(a.length(), b.length());
        return length >= MIN_OPENING_LETTERS ? a.substring(0, length).equals(b.substring(0, length)) : a.equals(b);
    }
}
