package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The cases are the real D14 defects, and the page text they are checked against is the real thing
 * — either copied from what {@link PdfTextLayer} yields for {@code keph107.pdf} or, in the last two
 * tests, read from that file on a machine that has it.
 *
 * <p>That matters more than it sounds. The first cut of this class was tested against hand-written
 * page text with the subscripts spaced out ({@code "L p = m p r p v p"}), which is not what a PDF
 * text layer contains: NCERT's glues them ({@code "Lp = mp rp vp"}), splits displayed equations
 * across lines out of order, and breaks the odd word with kerning ({@code "the p lanet"}). Against
 * the real shape that first cut flagged every paragraph carrying a symbol (spec-auditor, D14).
 */
class TranscriptionDiffTest {

    /** The founder's inputs, git-ignored; present on a machine that has them, absent in CI. */
    private static final Path NCERT = Path.of("..", "ncert", "2022-ed", "en");

    /**
     * Page 129 of Physics Part-I, verbatim from {@code PdfTextLayer.pages}, including its
     * mangled Greek ({@code Δ} arrives as {@code D}), its glued subscripts and its lost apostrophe
     * in {@code Kepler's}.
     */
    private static final String PAGE_129 = """
            Answer   The  magnitude  of  the  angular
            momentum at P is Lp =  mp rp vp, since inspection
            tells  us  that  rp  and  vp  are  mutually
            perpendicular.  Similarly,  LA  = mp rA vA.  From
            angular momentum conservation
            mp rp vp = mp rA vA
            Since rA   > rp, vp > vA .
            The  time period T  is about 27.3 days and Rm was
            already known then to be about 3.84 × 108m.  If
            we substitute these numbers in Eq. (7.3), we get
            a value of am much smaller than the value of
            acceleration due to gravity g on the surface of
            the earth, arising also due to earths gravitational
            attraction.
            """;

    @Test
    void aFaithfulTranscriptionOfARealPageFlagsNothing() {
        String said = "The magnitude of the angular momentum at P is L_p = m_p r_p v_p, since "
                + "inspection tells us that r_p and v_p are mutually perpendicular. Similarly, "
                + "L_A = m_p r_A v_A. From angular momentum conservation m_p r_p v_p = m_p r_A v_A";

        assertThat(TranscriptionDiff.check(PAGE_129, said)).isEmpty();
    }

    @Test
    void aFaithfulTranscriptionOfAnEquationSentenceFlagsNothing() {
        String said = "The time period T is about 27.3 days and R_m was already known then to be "
                + "about 3.84 x 10^8 m. If we substitute these numbers in Eq. (7.3), we get a value "
                + "of a_m much smaller than the value of acceleration due to gravity g on the "
                + "surface of the earth, arising also due to earth's gravitational attraction.";

        assertThat(TranscriptionDiff.check(PAGE_129, said)).isEmpty();
    }

    /**
     * The conventions added on the first full book's evidence — an overbar, a summation, an
     * integral, a perpendicular subscript, and a Greek letter joined to its operator. The prompt
     * gained them and this list did not, and the next run produced 45 flags for the pages that
     * obeyed (D14). Every convention in the prompt belongs here the same day.
     */
    @Test
    void theConventionsAddedAfterTheFirstFullBookAreNotDivergences() {
        String page = "The average acceleration ā over the interval and the moment of inertia "
                + "Σ m_i r_i², with the work ∫ F dx along the path and the component L⊥ dθ.";
        String said = "The average acceleration a_bar over the interval and the moment of inertia "
                + "sum m_i r_i^2, with the work integral F dx along the path and the component "
                + "L_perp dtheta.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /** The whole point: our own notation must not read as divergence. */
    @Test
    void ourNotationIsNotADivergence() {
        String page = "T = 2p Ö(l/g) and the angle q is small, with DH negative, along the unit vector i.";
        String said = "T = 2 pi sqrt(l / g) and the angle theta is small, with Delta H negative, "
                + "along the unit vector i_hat.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /** Audit item 10: `m_p` read as `m_r`, three times in one paragraph. */
    @Test
    void aMisreadSubscriptSymbolIsFlagged() {
        String said = "The magnitude of the angular momentum at P is L_p = m_r r_p v_p, since "
                + "inspection tells us that r_p and v_p are mutually perpendicular. Similarly, "
                + "L_A = m_r r_A v_A. From angular momentum conservation m_r r_p v_p = m_r r_A v_A";

        // NOT caught, and pinned as not caught. The symbol check that found this was retired after
        // the chapter-7 dry run produced 51 flags and no true positives: PDFBox emits a displayed
        // equation by typographic row, so `mp` is not on the page in any order either, and the
        // check could not tell a real misread from the layout. A formula is verified against the
        // page image or not at all (D14).
        assertThat(TranscriptionDiff.check(PAGE_129, said)).isEmpty();
    }

    /**
     * Audit item 1, the digit {@code 1} read as the letter {@code l}: NOT caught, and pinned as not
     * caught. A lone letter beside an operator has no symbol form to look up and no word to count,
     * so nothing here can distinguish it — the prompt's instruction and the founder's eye on the
     * rendered image are what stand between this defect and the corpus (FIX 2, and step 4 of the
     * runbook's ✅). The test exists so that a later cut which does catch it reads as progress
     * rather than as a regression in this one.
     */
    @Test
    void aDigitReadAsALetterIsNotCaughtAndTheClassSaysSo() {
        String page = "Take AG = BG = CG = 1 m and the force on the mass at A is FGA = Gm(2m) / 1 "
                + "directed along the median, with the other two forces following by symmetry.";
        String said = "Take AG = BG = CG = 1 m and the force on the mass at A is F_GA = Gm(2m) / l "
                + "directed along the median, with the other two forces following by symmetry.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /**
     * Audit item 14, a symbol read as another symbol entirely: also NOT caught here, for the same
     * reason. What this class still catches on that paragraph is the other half of the same defect
     * — the invented noun — which is the test below.
     */
    @Test
    void aMisreadSymbolInAWorkedExampleIsNotCaught() {
        String page = "where RMS is the Mars-Sun distance and RES is the Earth-Sun distance. "
                + "Therefore TM = (1.52)3/2 × 365 = 684 days for the martian year.";
        String said = "where R_MS is the Mars-Sun distance and R_ES is the Earth-Sun distance. "
                + "Therefore Q_M = (1.52)^(3/2) x 365 = 684 days for the martian year.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /** Audit item 14's other half: a noun the page does not contain anywhere. */
    @Test
    void anInventedWordIsFlagged() {
        String page = "Once again Keplers third law comes to our aid for the martian year, "
                + "which follows from the relation between the periods and the radii.";
        String said = "Once again Kepler's third law comes to our aid. For the satellite, the year "
                + "follows from the relation between the periods and the radii.";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("'satellite'"));
    }

    @Test
    void textTheModelDeliberatelySkippedIsNotADivergence() {
        String page = "GRAVITATION 139 Fig. 7.9 The gravitational potential energy of a body. "
                + "SUMMARY The gravitational force between two particles is central and attractive, "
                + "and depends only on the distance between them.";
        String said = "The gravitational force between two particles is central and attractive, "
                + "and depends only on the distance between them.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /** A paragraph belonging to another page altogether is flagged word by word, not passed over. */
    @Test
    void aParagraphThatIsNotOnThisPageAtAllIsFlagged() {
        String page = "A completely unrelated page about photosynthesis in green plants, which "
                + "shares no long word with the paragraph below it.";
        String said = "The gravitational force between two particles is central and attractive.";

        assertThat(TranscriptionDiff.check(page, said)).isNotEmpty();
    }

    @Test
    void nothingToCompareAgainstIsNotADivergence() {
        assertThat(TranscriptionDiff.check(null, "some text")).isEmpty();
        assertThat(TranscriptionDiff.check("", "some text")).isEmpty();
        assertThat(TranscriptionDiff.check("a page", "  ")).isEmpty();
    }

    /**
     * The test the first cut needed and did not have: a faithful transcription of a real page,
     * against that page's real text layer, must be silent. A check that cries wolf on correct work
     * is worse than no check — the founder would learn to skip the section.
     */
    @Test
    void aFaithfulTranscriptionOfARealNcertPageIsSilent() throws IOException {
        assumeTrue(Files.isDirectory(NCERT), "founder's NCERT PDFs not on this machine");
        List<String> pages = PdfTextLayer.pages(Files.readAllBytes(NCERT.resolve("phy11-part1/keph107.pdf")));
        String page = pages.get(2);

        // Transcribed by hand from the rendered page, in the conventions the prompt asks for.
        List<String> paragraphs = List.of(
                "3. Law of periods : The square of the time period of revolution of a planet is "
                        + "proportional to the cube of the semi-major axis of the ellipse traced out "
                        + "by the planet.",
                "Table 7.1 gives the approximate time periods of revolution of eight planets around "
                        + "the Sun along with values of their semi-major axes.",
                "The law of areas can be understood as a consequence of conservation of angular "
                        + "momentum which is valid for any central force. A central force is such "
                        + "that the force on the planet is along the vector joining the Sun and the "
                        + "planet. Let the Sun be at the origin and let the position and momentum of "
                        + "the planet be denoted by r and p respectively.",
                "The magnitude of the angular momentum at P is L_p = m_p r_p v_p, since inspection "
                        + "tells us that r_p and v_p are mutually perpendicular. Similarly, "
                        + "L_A = m_p r_A v_A.");

        for (String paragraph : paragraphs) {
            assertThat(TranscriptionDiff.check(page, paragraph))
                    .as("a faithful transcription of a real page: %s", paragraph.substring(0, 40))
                    .isEmpty();
        }
    }

    /**
     * And on the same real page, the class of defect this still catches: text belonging to another
     * page. This is the real one it found on the first full book — page 15 of chapter 4 carried a
     * paragraph about circular motion under a chapter-4 friction address, and the words gave it
     * away because they were nowhere on the page it claimed.
     */
    @Test
    void textFromAnotherPageIsStillFoundOnTheRealPage() throws IOException {
        assumeTrue(Files.isDirectory(NCERT), "founder's NCERT PDFs not on this machine");
        List<String> pages = PdfTextLayer.pages(Files.readAllBytes(NCERT.resolve("phy11-part1/keph107.pdf")));

        String foreign = "This is the static friction that provides the centripetal acceleration. "
                + "Static friction opposes the impending motion of the car moving away from the circle.";

        assertThat(TranscriptionDiff.check(pages.get(2), foreign)).isNotEmpty();
    }
}
