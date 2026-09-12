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

    /** What {@code page} leaves behind for the next call, or null when it carried no paragraphs. */
    public static PreviousPage of(NcertPage page) {
        if (page.paragraphs().isEmpty()) {
            return null;
        }
        NcertPage.Paragraph last = page.paragraphs().getLast();
        return new PreviousPage(last.section(), last.paraNo(), page.tail());
    }
}
