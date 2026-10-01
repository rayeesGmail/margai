package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
 *       above the heading and are sent, which leaves 14.2% of all pages never sent (2026-09-24);
 *   <li>{@code kech202.pdf}'s layer is shifted, so its heading page is sent unplaced ({@link #locate})
 *       and only its three exercise pages are skipped — 243 of 1,690 pages never sent (2026-09-30).
 *   <li>Hindi (D16, 2026-10-01): all 79 Hindi chapter files, whose layer is Chanakya glyph codes, find
 *       their boundary from the encoded headings ({@code HINDI_HEADINGS}); none is placed, so every
 *       heading page is sent and the 223 of 1,720 pages after the headings are not.
 * </ul>
 *
 * <p>Only the back half is searched: a Physics chapter's first page carries a contents sidebar
 * listing "Summary", "Points to Ponder" and "Exercises", and matching that would skip whole
 * chapters. Restricted to the back half there are <b>no</b> false positives in the corpus.
 */
final class ChapterApparatus {

    /** The headings that open the English end matter, as printed in one book or another; any case. */
    private static final List<String> HEADINGS = List.of(
            "SUMMARY", "POINTS TO PONDER", "EXERCISES", "ADDITIONAL EXERCISES", "ANSWERS", "APPENDIX");

    /**
     * The Hindi headings as the layer carries them — Walkman-Chanakya glyph codes, since the books have
     * no Unicode map — each with the Devanagari it prints, for the report. Matched exactly: case is a
     * different glyph in this encoding. Measured over all 79 Hindi chapter files (D16, 2026-10-01): a
     * boundary in every back half, सारांश keyed three ways by the typesetters, अभ्यास first in the layer
     * on the two pages it shares with the Summary; the only front-half hits are Physics contents sidebars.
     * The fourth सारांश is how PDFBox reads Chemistry 12's: six files set it in KrutiDev501 Bold printed
     * five times over itself, and the stripper drops the repeated letters but keeps four anusvaras.
     */
    private static final Map<String, String> HINDI_HEADINGS = Map.of(
            "lkjka'k", "सारांश",
            "lkjak'k", "सारांश",
            "Lkkjka'k", "सारांश",
            "lkjka'kaaaa", "सारांश",
            "vH;kl", "अभ्यास");

    private ChapterApparatus() {
    }

    /**
     * The page the chapter's apparatus starts on, or empty when there is none to be found — which
     * means every page is sent. The layer's legibility is not asked: a heading is an exact line, so a
     * layer that is not the page's words can hide one but never invent one. {@code kech202.pdf}, the
     * corpus's one such English file, shifts its body and prints its Summary heading in a font that is
     * not shifted; while this answered empty for an illegible layer, its exercises — numbered 8.1, 8.2,
     * the shape D14 found read as sections — were sent (2026-09-30).
     *
     * @param pageTexts the chapter's pages in order, from {@link PdfTextLayer#pages}
     * @param language  the edition, whose headings are looked for
     */
    static Optional<Boundary> find(List<String> pageTexts, BookLanguage language) {
        if (pageTexts.isEmpty()) {
            return Optional.empty();
        }
        for (int index = pageTexts.size() / 2; index < pageTexts.size(); index++) {
            for (String line : pageTexts.get(index).split("\\R")) {
                Optional<String> heading = heading(line.strip().replaceAll("\\s+", " "), language);
                if (heading.isPresent()) {
                    return Optional.of(new Boundary(index + 1, heading.get(), Boundary.UNPLACED));
                }
            }
        }
        return Optional.empty();
    }

    /** The heading a layer line is, as the report names it, or empty when it is none of this edition's. */
    private static Optional<String> heading(String line, BookLanguage language) {
        if (language == BookLanguage.hi) {
            return Optional.ofNullable(HINDI_HEADINGS.get(line));
        }
        return HEADINGS.stream().filter(line::equalsIgnoreCase).findFirst().map(match -> line.toUpperCase(Locale.ROOT));
    }

    /**
     * The boundary as {@code ncert extract} uses it: found in the text layer and placed on its page by
     * the glyph positions — but only on a legible layer. Placing counts the prose above the heading,
     * and prose is recognised by its words, so a shifted layer could count none above a heading that
     * has teaching over it and withhold the page. Unplaced, the heading's page is sent; the pages
     * after it are apparatus either way.
     *
     * @param pageTexts the chapter's pages in order, from {@link PdfTextLayer#pages}
     * @param pdf       the chapter PDF those pages were read from
     * @param language  the edition, whose headings are looked for
     */
    static Optional<Boundary> locate(List<String> pageTexts, byte[] pdf, BookLanguage language) {
        Optional<Boundary> found = find(pageTexts, language);
        return PdfTextLayer.isLegible(pageTexts) ? found.map(boundary -> boundary.placedIn(pdf)) : found;
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
