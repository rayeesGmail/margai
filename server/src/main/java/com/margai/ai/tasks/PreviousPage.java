package com.margai.ai.tasks;

/**
 * Where the previous page of this chapter ended, handed to the next page's call (TECH_PLAN §6.3).
 *
 * <p>Through v2 this carried the last paragraph's number, because the model was counting, and
 * the end of its text, so a continuation could be recognised. Since v3 the loader counts
 * (DECISIONS 2026-09-14), and since v3's first measurement the same afternoon no text travels
 * either: the cheap model echoed the quoted ending on two of twelve pages, once verbatim and once
 * as a paraphrase no repair can see, while the continuation itself is a judgement about this
 * page's own typography — a first line that begins mid-sentence, or flush left where the page's
 * paragraphs are indented. What the model still needs from the page before is its section, so a
 * page with no heading carries that section forward rather than guessing one.
 *
 * @param section the section the previous page ended in, as printed ("7.9")
 */
public record PreviousPage(String section) {

    /** The state at the top of a chapter: no page has been read yet. */
    public static PreviousPage none() {
        return null;
    }

    /**
     * What {@code page} leaves behind for the next call, given what the page before it left.
     *
     * <p>A page with no paragraphs — a chapter plate, a full-page figure, a blank verso, a page of
     * pure table — does not reset the section: it carries the running section across untouched.
     * Figure pages are common mid-chapter in Biology (spec-auditor, D14).
     */
    public static PreviousPage of(NcertPage page, PreviousPage before) {
        if (page.paragraphs().isEmpty()) {
            return before;
        }
        return new PreviousPage(page.paragraphs().getLast().section());
    }
}
