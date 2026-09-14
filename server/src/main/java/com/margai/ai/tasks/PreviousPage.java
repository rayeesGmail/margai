package com.margai.ai.tasks;

/**
 * Where the previous page of this chapter ended, handed to the next page's call (TECH_PLAN §6.3,
 * "the previous page's tail for paragraph continuity").
 *
 * <p>Through v2 this also carried the last paragraph's number, because the model was counting.
 * Since v3 the loader counts (DECISIONS 2026-09-14), and the model needs only two things from
 * the page before: the section it was in, so a page with no heading carries that section
 * forward rather than guessing one, and the end of its text, so the model can say whether this
 * page's first paragraph is the rest of that sentence.
 *
 * @param section the section the previous page ended in, as printed ("7.9")
 * @param tail    the ending of that page's last paragraph, {@link NcertPage#TAIL_LENGTH}
 *                characters at most; null after a page that carried no text
 */
public record PreviousPage(String section, String tail) {

    /** The state at the top of a chapter: no page has been read yet. */
    public static PreviousPage none() {
        return null;
    }

    /**
     * What {@code page} leaves behind for the next call, given what the page before it left.
     *
     * <p>A page with no paragraphs — a chapter plate, a full-page figure, a blank verso, a page of
     * pure table — does not reset the section: it carries the running section across untouched
     * and drops only the tail, because there is no text on it for the next page to continue from.
     * Figure pages are common mid-chapter in Biology (spec-auditor, D14).
     */
    public static PreviousPage of(NcertPage page, PreviousPage before) {
        if (page.paragraphs().isEmpty()) {
            return before == null ? null : new PreviousPage(before.section(), null);
        }
        return new PreviousPage(page.paragraphs().getLast().section(), page.tail());
    }
}
