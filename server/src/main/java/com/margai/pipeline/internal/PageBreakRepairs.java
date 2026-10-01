package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Two deterministic corrections to the model's page-break decisions, made before the paragraphs
 * are numbered and joined, each reported by name so nothing here is silent.
 *
 * <p>Every page call is told where the previous page ended — its section and the end of its
 * text — and the model says whether its own first paragraph <em>continues</em> that one. That is
 * a typographic judgement (an indent starts a paragraph, flush-left continues one) and it stays
 * with the model: a test that refused the join whenever the first half ended in a full stop was
 * wrong on the first real page it met, where a paragraph genuinely ran on across the break after
 * a finished sentence (Chapter 1, page 3 to 4, checked on the rendered image).
 *
 * <p>Two cases, though, are not judgements, and code decides them:
 *
 * <ul>
 * <li><b>The page re-transcribes the previous page's ending.</b> The call carries that ending
 * for continuity and says it is never part of this page's output. Where the page's first
 * paragraph nonetheless contains a span that is the end of the previous one, the span is
 * dropped — and so is anything the model put in front of it, because words that precede a
 * verbatim copy of the previous page's last line cannot be on this page. What remains is, by
 * that same evidence, a continuation, and is flagged as one whatever the model said. Chapter 4
 * page 15 repeated "much smaller (even by 2 or 3 orders of magnitude) than static or sliding
 * friction" from page 14 (D14); chapter 6 page 8 invented "are made of the same material and
 * have the same thickness, then", repeated the whole quoted tail, and only then transcribed the
 * page's real words, on two calls out of two (D15).
 * <p>The lead-in clause holds only where the repeat stands at the head of the page, so it is
 * bounded: where more characters precede the span than the span is long, nothing is cut — the page
 * is left whole and named for a human. A re-transcription comes first and its lead-in is an
 * invention; a span found deep in the page is the book using the same words twice. Chapter 6 pages
 * 18–19 was the second kind — page 18 broke mid-sentence on "to be satisfied for mechanical" and
 * page 19 printed that wording again two sentences later — and 124 characters of the
 * coplanar-forces case were cut before the bound existed (2026-09-17).</li>
 * <li><b>A printed label opens the page.</b> "Answer", "Solution" and "Example 4.9" are set in
 * bold by the book and always begin a paragraph. Where the page's first paragraph is flagged as
 * continuing the previous page and opens with one of these, the flag is cleared. Both real
 * collisions of the first full book were this: an Example's question ending page 61 and its
 * Answer opening page 62 (Chapter 4), and the same at pages 108–109 (Chapter 6).</li>
 * </ul>
 *
 * <p>Through v2 the label case renumbered every later paragraph of the section, because the
 * model had numbered them. Since v3 the loader numbers, so a repair here moves a flag or cuts a
 * span and nothing else (D15).
 */
final class PageBreakRepairs {

    /** What the book sets in bold at the start of a paragraph that is never a continuation. */
    private static final Pattern LABEL = Pattern.compile("^(Answer|Solution|Example\\s+\\d+(\\.\\d+)*)\\b");
    /** Shorter than this and a shared span is a coincidence of wording, not a repeated tail. */
    private static final int MIN_REPEAT = 40;
    /** How far back into the previous paragraph a repeat is looked for. */
    private static final int TAIL_WINDOW = 400;
    /** How far into the page's first paragraph a repeat is looked for. */
    private static final int HEAD_WINDOW = 600;

    private PageBreakRepairs() {
    }

    /** The pages with the repairs applied, and one line per repair for the report. */
    record Repaired(List<ExtractedPage> pages, List<String> notes) {
    }

    static Repaired apply(List<ExtractedPage> pages) {
        List<String> notes = new ArrayList<>();
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
                String where = "ch " + page.chapterNo() + " §" + first.section().strip() + ": page " + page.page();

                Repeat repeat = repeatedTail(last.text(), first.text());
                if (repeat != null && repeat.leadIn() >= repeat.length()) {
                    // A re-transcription stands at the head of the page. A match further into it than
                    // the match is long is the book repeating its own wording, and the words before it
                    // are this page's (D15, 2026-09-17: ch 6 pages 18-19 lost the coplanar-forces case
                    // this way — silently, but for one line of the report).
                    notes.add(where + " carries a " + repeat.length() + "-character span that also ends page "
                            + previous.page() + ", but " + repeat.leadIn() + " characters stand in front of it — "
                            + "further in than the span is long, so this is the book repeating a phrase and not "
                            + "the page repeating the previous one: left whole, read it against the page");
                } else if (repeat != null) {
                    if (repeat.remainder().isEmpty()) {
                        paragraphs.removeFirst();
                        notes.add(where + "'s first paragraph was nothing but the end of page " + previous.page()
                                + "'s — removed");
                    } else {
                        paragraphs.set(0, new NcertPage.Paragraph(last.section(), repeat.remainder(), true,
                                first.figureRefs()));
                        notes.add(where + " repeated the end of page " + previous.page() + "'s paragraph; the repeated "
                                + repeat.length() + " characters were dropped"
                                + (repeat.leadIn() == 0 ? "" : ", and the " + repeat.leadIn()
                                        + " characters before the repeat with them, since they cannot be on this page")
                                + "; what remains is treated as continuing that paragraph");
                    }
                }

                if (!paragraphs.isEmpty()) {
                    first = paragraphs.getFirst();
                    if (first.continuesPreviousPage() && LABEL.matcher(first.text().strip()).find()) {
                        paragraphs.set(0, new NcertPage.Paragraph(first.section(), first.text(), false,
                                first.figureRefs()));
                        notes.add(where + " opens with \"" + first.text().strip().split("\\s+")[0]
                                + "\" flagged as continuing page " + previous.page() + "'s paragraph — a label always "
                                + "begins a new paragraph, so the flag is cleared");
                    }
                }
            }
            ExtractedPage repaired = new ExtractedPage(page.chapterNo(), page.page(), page.confidence(),
                    page.aiCallId(), List.copyOf(paragraphs), page.skipped());
            out.add(repaired);
            if (!repaired.paragraphs().isEmpty()) {
                previous = repaired;
            }
        }
        return new Repaired(List.copyOf(out), List.copyOf(notes));
    }

    /**
     * A repeat of the previous paragraph's ending found in the next paragraph: how long it was,
     * how many characters the model put before it, and the text left after it.
     */
    record Repeat(int leadIn, int length, String remainder) {
    }

    /**
     * The longest ending of the previous paragraph, at least {@link #MIN_REPEAT} characters, that
     * appears within the opening of the next one; null when none does. Whitespace is collapsed on
     * both sides first, because a line break in one and a space in the other is not a difference.
     */
    static Repeat repeatedTail(String previousText, String nextText) {
        String prev = previousText == null ? "" : previousText.strip().replaceAll("\\s+", " ");
        String next = nextText == null ? "" : nextText.strip().replaceAll("\\s+", " ");
        String tail = prev.length() > TAIL_WINDOW ? prev.substring(prev.length() - TAIL_WINDOW) : prev;
        String head = next.length() > HEAD_WINDOW ? next.substring(0, HEAD_WINDOW) : next;
        for (int length = Math.min(tail.length(), head.length()); length >= MIN_REPEAT; length--) {
            int at = head.indexOf(tail.substring(tail.length() - length));
            if (at >= 0) {
                return new Repeat(next.substring(0, at).strip().length(), length, next.substring(at + length).strip());
            }
        }
        return null;
    }
}
