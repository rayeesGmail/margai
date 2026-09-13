package com.margai.ai.tasks;

/**
 * Where the previous page of this chapter ended, handed to the next page's call (TECH_PLAN §6.3,
 * "the previous page's tail for paragraph continuity").
 *
 * <p>The tail alone is not enough, and that gap was a real defect: the prompt asks for paragraph
 * numbers that restart with a *section* rather than with a page, and for a continuation to keep
 * the number it already had — neither of which a model can honour without being told the address
 * it is continuing from. With only the text, it restarts at 1 on every page, and two unrelated
 * paragraphs then collide at one address (spec-auditor, D14).
 *
 * @param section the section the previous page ended in, as printed ("7.9")
 * @param paraNo  the number of its last paragraph within that section
 * @param tail    the ending of that paragraph's text, so a continuation can be recognised
 */
public record PreviousPage(String section, int paraNo, String tail) {

    /** The state at the top of a chapter: no page has been read yet. */
    public static PreviousPage none() {
        return null;
    }

    /**
     * What {@code page} leaves behind for the next call, given what the page before it left.
     *
     * <p>A page with no paragraphs — a chapter plate, a full-page figure, a blank verso, a page of
     * pure table — does not reset the address: it carries the running section and paragraph number
     * across untouched and drops only the tail, because there is no text on it for the next page to
     * continue from. Letting such a page return nothing was the first fix's own defect: the next
     * page would have restarted numbering at 1, which is the failure the address was added to
     * prevent, and figure pages are common mid-chapter in Biology (spec-auditor, D14).
     */
    public static PreviousPage of(NcertPage page, PreviousPage before) {
        if (page.paragraphs().isEmpty()) {
            return before == null ? null : new PreviousPage(before.section(), before.paraNo(), null);
        }
        NcertPage.Paragraph last = page.paragraphs().getLast();
        return new PreviousPage(last.section(), last.paraNo(), page.tail());
    }
}
