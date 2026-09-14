package com.margai.ai.tasks;

import java.math.BigDecimal;
import java.util.List;

/**
 * Output record of the {@code ncert_extract} prompt (TECH_PLAN §6.3): one page's paragraphs in
 * reading order, each under its printed section, plus the model's confidence in having read the
 * page correctly.
 *
 * <p>The chapter number is absent on purpose — it comes from the founder-reviewed
 * {@code books.yaml}, not from the model (DECISIONS D14). So is the paragraph number, since v3
 * (D15): the model returned one through v2, counting on from where the previous page ended, and
 * every defect that cost money on 2026-09-14 was that count — a phantom empty continuation, a
 * quoted tail "completed" into a sentence the book does not contain, continuations left
 * unflagged. The model now says only whether its first paragraph continues the previous page;
 * {@code ncert load} counts (DECISIONS 2026-09-14).
 *
 * @param paragraphs in reading order, possibly empty for a page that is all figure or cover
 * @param confidence 0–1, how sure the model is of this page's text and addresses
 */
public record NcertPage(List<Paragraph> paragraphs, BigDecimal confidence) {

    /**
     * How much of a page travels with the next page's call: enough to recognise a sentence that
     * runs on, and no more — the tail is context for one decision, never text to output, and the
     * 600 characters v2 sent were what the model set about "completing" (ch 6 p8, D15).
     */
    public static final int TAIL_LENGTH = 200;

    public NcertPage {
        paragraphs = paragraphs == null ? List.of() : List.copyOf(paragraphs);
        requireFlagOnFirstOnly(paragraphs);
    }

    /**
     * The one thing a page's paragraph list can get wrong as a list: a continuation flag anywhere
     * but on the first paragraph. Shared with the JSONL page shape, so an artefact written by an
     * older build or by hand is held to the same rule as the model's output.
     */
    public static void requireFlagOnFirstOnly(List<Paragraph> paragraphs) {
        for (int index = 1; index < paragraphs.size(); index++) {
            if (paragraphs.get(index).continuesPreviousPage()) {
                throw new IllegalArgumentException("continues_previous_page is set on paragraph " + (index + 1)
                        + " (section " + paragraphs.get(index).section() + "), but only a page's first paragraph "
                        + "can continue the previous page");
            }
        }
    }

    /**
     * The ending of this page's text, as the next page's call receives it (TECH_PLAN §6.3, "the
     * previous page's tail for paragraph continuity"): null when the page carried no paragraphs,
     * so a plate or a figure page does not hand the next page a stale tail.
     */
    public String tail() {
        if (paragraphs.isEmpty()) {
            return null;
        }
        String text = paragraphs.getLast().text();
        return text.length() <= TAIL_LENGTH ? text : text.substring(text.length() - TAIL_LENGTH);
    }

    /**
     * One paragraph as printed.
     *
     * <p>There is deliberately no {@code has_equations} field. Whether a paragraph holds an
     * equation is decided from its transcribed text in Java: asked to judge it, the model flagged
     * prose that merely stated a law in words, and a model is never asked for what code can
     * compute (.claude/rules/ai-layer.md, D14). There is no paragraph number for the same reason
     * (D15, above).
     *
     * <p>A blank text is refused here, in the record itself, so that it surfaces where the model's
     * output is decoded — an {@code InvalidOutputException} the schema layer answers with one
     * repair call — and not an hour later when the loader meets it (ch 4 p2, D15).
     *
     * @param section              the numbered heading it falls under ("7.9"), or the chapter's
     *                             own number for text before the first numbered section
     * @param text                 the paragraph, transcribed, never summarised
     * @param continuesPreviousPage true only for a page's first paragraph, when it is the rest of
     *                             the paragraph the previous page ended in the middle of
     * @param figureRefs           figure and table labels the paragraph refers to, as printed
     */
    public record Paragraph(
            String section,
            String text,
            boolean continuesPreviousPage,
            List<String> figureRefs) {

        public Paragraph {
            if (text == null || text.isBlank()) {
                throw new IllegalArgumentException("the paragraph at section " + section
                        + " has no text — a paragraph that continues the previous page still carries the words "
                        + "printed on this page, and a page with nothing to transcribe returns no paragraphs");
            }
            figureRefs = figureRefs == null ? List.of() : List.copyOf(figureRefs);
        }
    }
}
