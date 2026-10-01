package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Consecutive paragraphs that are really one sentence cut in half.
 *
 * <p>Found on the first full-book extraction: 32 of 969 paragraphs of Physics Part-I began in the
 * middle of a sentence, always at a printed line end — "The scheme is now for international usage"
 * followed by "in scientific, technical, industrial and commercial work." The cause is tiling. A
 * page is sent to the model as two overlapping bands so its small glyphs arrive unscaled, and the
 * model treated the bottom edge of a band as the end of a paragraph (D14). The prompt now says a
 * band boundary is never a paragraph boundary; this is the check that says whether it worked.
 *
 * <p>It matters beyond tidiness. A paragraph is what a student is shown as the source behind an
 * explanation, and one that opens "in scientific, technical and commercial work." reads as
 * nonsense; from D17 it is also what gets embedded, so a fragment becomes a retrieval unit.
 *
 * <p>The signature is deliberately narrow, because NCERT does legitimately open a paragraph in
 * lower case after a displayed equation — "where v_x = dx/dt". Both halves must agree that the
 * sentence is unfinished: the first ends with no terminal punctuation at all, and the second opens
 * with a lower-case <em>word</em> rather than a symbol. That pair is not something the book does.
 */
final class SplitSentences {

    /** How a finished paragraph ends. A closing bracket counts: "... (3.30a)" is finished. */
    private static final Pattern FINISHED = Pattern.compile("[.?!:)\\]]\\s*$");

    /**
     * A lower-case English word, as opposed to a symbol like {@code a(t)} or {@code v_x}. The word
     * may be the whole of what was cut off, so it may end the sentence itself: "…nigrum and" +
     * "melongena. Human beings…" (bio11 §1.2.1, D15).
     */
    private static final Pattern OPENS_LOWERCASE_WORD = Pattern.compile("^[a-z]{2,}[\\s,.;:]");

    private SplitSentences() {
    }

    /**
     * Pairs of adjacent paragraphs that read as one interrupted sentence, as report lines.
     *
     * @param rows the chapter's paragraphs, in the order they are printed
     */
    static List<String> find(List<NcertParagraphRow> rows) {
        List<String> found = new ArrayList<>();
        for (int index = 1; index < rows.size(); index++) {
            NcertParagraphRow before = rows.get(index - 1);
            NcertParagraphRow after = rows.get(index);
            if (before.chapterNo() != after.chapterNo() || !before.section().equals(after.section())) {
                continue;
            }
            String left = before.text() == null ? "" : before.text().stripTrailing();
            String right = after.text() == null ? "" : after.text().stripLeading();
            if (left.isEmpty() || right.isEmpty()
                    || FINISHED.matcher(left).find() || !OPENS_LOWERCASE_WORD.matcher(right).find()) {
                continue;
            }
            found.add("ch " + after.chapterNo() + " §" + after.section() + " ¶" + after.paraNo()
                    + " continues the sentence ¶" + before.paraNo() + " stopped in the middle of: \"…"
                    + tail(left) + "\" + \"" + head(right) + "…\"");
        }
        return List.copyOf(found);
    }

    private static String tail(String text) {
        String flat = text.replaceAll("\\s+", " ");
        return flat.length() <= 45 ? flat : flat.substring(flat.length() - 45);
    }

    private static String head(String text) {
        String flat = text.replaceAll("\\s+", " ");
        return flat.length() <= 45 ? flat : flat.substring(0, 45);
    }
}
