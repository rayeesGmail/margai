package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Two deterministic corrections to the model's page-break decisions, made before the halves are
 * joined, each reported by name so nothing here is silent.
 *
 * <p>Every page call is told where the previous page ended — its section and paragraph number —
 * and must decide whether its own first paragraph <em>continues</em> that one or <em>starts</em>
 * the next. That is a typographic judgement (an indent starts a paragraph, flush-left continues
 * one) and it stays with the model: a test that refused the join whenever the first half ended in
 * a full stop was wrong on the first real page it met, where a paragraph genuinely ran on across
 * the break after a finished sentence (Chapter 1, page 3 to 4, checked on the rendered image).
 *
 * <p>Two cases, though, are not judgements, and code decides them:
 *
 * <ul>
 * <li><b>A printed label opens the page.</b> "Answer", "Solution" and "Example 4.9" are set in
 * bold by the book and always begin a paragraph. Where the page's first paragraph carries the
 * previous page's number and opens with one of these, it is the <em>next</em> paragraph, and so is
 * everything after it in that section — on that page and on every later page, because each page's
 * numbering was chained from the one before. Both real collisions of the first full book were
 * this: an Example's question ending page 61 and its Answer opening page 62 (Chapter 4), and the
 * same at pages 108–109 (Chapter 6).</li>
 * <li><b>The page re-transcribes the previous page's tail.</b> The call carries that tail for
 * continuity and says not to repeat it; where the page's first paragraph nonetheless begins with a
 * span that is the end of the previous one, the span is dropped. Chapter 4 page 15 repeated
 * "much smaller (even by 2 or 3 orders of magnitude) than static or sliding friction" from page
 * 14, and the joined row said it twice.</li>
 * </ul>
 */
final class PageBreakRepairs {

    /** What the book sets in bold at the start of a paragraph that is never a continuation. */
    private static final Pattern LABEL = Pattern.compile("^(Answer|Solution|Example\\s+\\d+(\\.\\d+)*)\\b");

    /** Shorter than this and a shared span is a coincidence of wording, not a repeated tail. */
    private static final int MIN_REPEAT = 40;

    /** How far back into the previous paragraph a repeat is looked for. */
    private static final int TAIL_WINDOW = 400;

    private PageBreakRepairs() {
    }

    /** The pages with the repairs applied, and one line per repair for the report. */
    record Repaired(List<ExtractedPage> pages, List<String> notes) {
    }

    static Repaired apply(List<ExtractedPage> pages) {
        List<String> notes = new ArrayList<>();
        // chapter → section → the shift every paragraph of that section carries from a page on.
        Map<Short, Map<String, List<int[]>>> shifts = new HashMap<>();
        List<ExtractedPage> out = new ArrayList<>(pages.size());
        ExtractedPage previous = null;
        for (ExtractedPage page : pages) {
            if (previous != null && previous.chapterNo() != page.chapterNo()) {
                previous = null;
            }
            List<NcertPage.Paragraph> paragraphs = new ArrayList<>(page.paragraphs());
            if (previous != null && !paragraphs.isEmpty()) {
                NcertPage.Paragraph last = previous.paragraphs().getLast();
                NcertPage.Paragraph first = paragraphs.getFirst();
                boolean sameAddress = last.section().strip().equals(first.section().strip())
                        && last.paraNo() == first.paraNo();
                if (sameAddress) {
                    String trimmed = withoutRepeatedTail(last.text(), first.text());
                    if (trimmed != null) {
                        paragraphs.set(0, new NcertPage.Paragraph(first.section(), first.paraNo(), trimmed,
                                first.figureRefs()));
                        notes.add("ch " + page.chapterNo() + " §" + first.section() + " ¶" + first.paraNo()
                                + ": page " + page.page() + " repeated the end of page " + previous.page()
                                + "'s paragraph; the repeated " + (first.text().strip().length() - trimmed.length())
                                + " characters were dropped");
                        first = paragraphs.getFirst();
                    }
                    if (LABEL.matcher(first.text().strip()).find()) {
                        shifts.computeIfAbsent(page.chapterNo(), c -> new HashMap<>())
                                .computeIfAbsent(first.section().strip(), s -> new ArrayList<>())
                                .add(new int[] {page.page()});
                        notes.add("ch " + page.chapterNo() + " §" + first.section() + ": page " + page.page()
                                + " opens with \"" + first.text().strip().split("\\s+")[0] + "\" at ¶" + first.paraNo()
                                + ", the number page " + previous.page() + " ended on — a label always begins a "
                                + "new paragraph, so ¶" + first.paraNo() + " and everything after it in §"
                                + first.section() + " move up by one");
                    }
                }
            }
            List<NcertPage.Paragraph> renumbered = new ArrayList<>(paragraphs.size());
            for (NcertPage.Paragraph paragraph : paragraphs) {
                int shift = shiftFor(shifts, page.chapterNo(), paragraph.section().strip(), page.page());
                renumbered.add(shift == 0 ? paragraph : new NcertPage.Paragraph(paragraph.section(),
                        paragraph.paraNo() + shift, paragraph.text(), paragraph.figureRefs()));
            }
            ExtractedPage repaired = new ExtractedPage(page.chapterNo(), page.page(), page.confidence(),
                    page.aiCallId(), renumbered, page.skipped());
            out.add(repaired);
            if (!repaired.paragraphs().isEmpty()) {
                previous = repaired;
            }
        }
        return new Repaired(List.copyOf(out), List.copyOf(notes));
    }

    /** How many places a paragraph in this chapter, section and page moves up: one per label repair at or before it. */
    private static int shiftFor(Map<Short, Map<String, List<int[]>>> shifts, short chapter, String section, int page) {
        List<int[]> from = shifts.getOrDefault(chapter, Map.of()).getOrDefault(section, List.of());
        int shift = 0;
        for (int[] start : from) {
            if (page >= start[0]) {
                shift++;
            }
        }
        return shift;
    }

    /**
     * The next page's text with the previous paragraph's repeated ending removed, or null when
     * nothing was repeated. Whitespace is collapsed on both sides first, because a line break in
     * one and a space in the other is not a difference.
     */
    static String withoutRepeatedTail(String previousText, String nextText) {
        String prev = previousText == null ? "" : previousText.strip().replaceAll("\\s+", " ");
        String next = nextText == null ? "" : nextText.strip().replaceAll("\\s+", " ");
        String tail = prev.length() > TAIL_WINDOW ? prev.substring(prev.length() - TAIL_WINDOW) : prev;
        for (int length = Math.min(tail.length(), next.length()); length >= MIN_REPEAT; length--) {
            if (tail.endsWith(next.substring(0, length))) {
                return next.substring(length).strip();
            }
        }
        return null;
    }
}
