package com.margai.pipeline.internal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Where a chapter stops teaching and starts examining. Everything from the Summary onward — Points
 * to Ponder, Exercises, Answers, appendices — is apparatus: no paragraph in it is something a
 * student should ever be anchored to (SPEC §6.3), so those pages are never sent to the model at all.
 *
 * <p>This exists because asking the model to skip the apparatus did not work. NCERT numbers its
 * exercises with the chapter number, so Chapter 7's questions run 7.1, 7.2, 7.3 — indistinguishable
 * in shape from its section numbers — and a page of them arriving with no heading reads as a fresh
 * run of sections. Two prompt revisions failed to stop it, the second making it worse. Not sending
 * the page is the only version of this that cannot fail (D14).
 *
 * <p>The rule is measured, not assumed. Over all 79 English chapter files of all ten books:
 * <ul>
 *   <li>a boundary is found in <b>79 of 79</b>, at a mean of 87% through the chapter;
 *   <li>{@code SUMMARY} fires in 76, {@code EXERCISES} in two bio12 chapters and
 *       {@code POINTS TO PONDER} in one phy12-part2 chapter, whose Summary heading is drawn rather
 *       than typed — whichever comes first is still apparatus, so the boundary lands correctly;
 *   <li>Chemistry writes {@code Summary} in title case and gives its exercises no heading at all,
 *       which is why the match is case-insensitive and why the Summary is the one that matters;
 *   <li>17.6% of all pages sit past the boundary.
 * </ul>
 *
 * <p>Only the back half is searched: a Physics chapter's first page carries a contents sidebar
 * listing "Summary", "Points to Ponder" and "Exercises", and matching that would skip whole
 * chapters. Restricted to the back half there are <b>no</b> false positives in the corpus.
 */
final class ChapterApparatus {

    /** The headings that open the end matter, as printed in one book or another. */
    private static final List<String> HEADINGS = List.of(
            "SUMMARY", "POINTS TO PONDER", "EXERCISES", "ADDITIONAL EXERCISES", "ANSWERS", "APPENDIX");

    private ChapterApparatus() {
    }

    /**
     * The first page of the chapter's apparatus, or empty when there is none to be found — which
     * means every page is sent. Empty is the safe answer and is returned whenever the text layer
     * cannot be trusted ({@code kech202.pdf} is the corpus's one such file): skipping a page we
     * cannot read would risk dropping real teaching, and the guards downstream exist for that case.
     *
     * @param pageTexts the chapter's pages in order, from {@link PdfTextLayer#pages}
     */
    static Optional<Boundary> find(List<String> pageTexts) {
        if (pageTexts.isEmpty() || !PdfTextLayer.isLegible(pageTexts)) {
            return Optional.empty();
        }
        for (int index = pageTexts.size() / 2; index < pageTexts.size(); index++) {
            for (String line : pageTexts.get(index).split("\\R")) {
                String heading = line.strip().replaceAll("\\s+", " ");
                if (HEADINGS.stream().anyMatch(heading::equalsIgnoreCase)) {
                    return Optional.of(new Boundary(index + 1, heading.toUpperCase(Locale.ROOT)));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @param firstPage the 1-based page where the apparatus begins; this page and every page after
     *                  it in the chapter is apparatus
     * @param heading   the heading that identified it, for the run report
     */
    record Boundary(int firstPage, String heading) {

        boolean covers(int page) {
            return page >= firstPage;
        }
    }
}
