package com.margai.pipeline.internal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Where a chapter stops teaching and starts examining. Everything from the Summary onward — Points
 * to Ponder, Exercises, Answers, appendices — is apparatus: no paragraph in it is something a
 * student should ever be anchored to (SPEC §6.3), so the pages after the heading are never sent to the
 * model at all, and the heading's own page only when teaching is printed above it
 * ({@link Boundary#sendsItsPage}).
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
 *   <li>17.6% of all pages sit at or past the boundary; 53 of the 78 heading pages carry teaching
 *       above the heading and are sent, which leaves 14.2% of all pages never sent (2026-09-24).
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
     * The page the chapter's apparatus starts on, or empty when there is none to be found — which
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
                    return Optional.of(new Boundary(index + 1, heading.toUpperCase(Locale.ROOT), Boundary.UNPLACED));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @param page            the 1-based page the heading is on; every page after it is apparatus
     * @param heading         the heading that identified it, for the run report
     * @param proseLinesAbove the prose lines printed above the heading on its page, or {@link #UNPLACED}
     *                        when the heading's position has not been, or could not be, read
     */
    record Boundary(int page, String heading, int proseLinesAbove) {

        /** The heading's position on its page is not known. */
        static final int UNPLACED = -1;

        /**
         * This boundary with the heading placed on its page by its glyph positions ({@link PdfLayout}).
         * Position and not the text layer's line order, because the layer can carry a page's lines in
         * any order: bio11 ch 14 p11 prints §14.6's "Occupational Respiratory Disorders" above its
         * Summary, and its layer has the heading on the first line.
         */
        Boundary placedIn(byte[] pdf) {
            PdfLayout.Apparatus found = PdfLayout.page(pdf, page, heading).apparatus();
            return new Boundary(page, heading, found.located() ? found.proseLinesAbove() : UNPLACED);
        }

        /**
         * Whether the heading's own page is sent. Until 2026-09-24 it never was, and whatever was
         * printed above the heading went with it: prose in 14 of bio11's 19 chapters, including a named
         * subsection of §14.6 that reached no row, and no metric could see it — coverage counts a
         * skipped page as legitimately skipped and the second read reads only what was sent. So the
         * page is withheld only when the print shows nothing taught above the heading; a heading that
         * could not be placed sends it, because a wasted call is recoverable and lost teaching is not.
         * The prompt already skips the Summary; what this class exists to keep from the model — the
         * chapter-numbered exercises — can now reach it on this one page per chapter, below the
         * heading, where the second read and the start check both watch.
         */
        boolean sendsItsPage() {
            return proseLinesAbove != 0;
        }

        /** Whether a page is apparatus and never sent. */
        boolean covers(int page) {
            return page > this.page || (page == this.page && !sendsItsPage());
        }

        /** What happens to the heading's page, for the run report. */
        String itsPage() {
            if (proseLinesAbove == UNPLACED) {
                return "sent: the heading could not be placed on it";
            }
            return sendsItsPage()
                    ? "sent: " + proseLinesAbove + " prose line(s) above the heading"
                    : "not sent: nothing taught above the heading";
        }
    }
}
