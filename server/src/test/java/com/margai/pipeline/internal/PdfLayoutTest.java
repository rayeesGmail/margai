package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Where printed paragraphs start, read from the glyph positions of the PDF's text layer (D15). The
 * geometry was measured on {@code keph107.pdf} on 2026-09-14: body text at 10 pt, a paragraph's first
 * line set 18 pt in from the line below it, the column margins moving between verso and recto pages
 * (109.7 or 73.7 pt), a boxed statement setting its own margin, and the first line after a section
 * heading flush. The synthetic cases pin each rule; the real pages check them against the print.
 */
class PdfLayoutTest {

    private static final Path CHAPTER_7 = Path.of("../ncert/2022-ed/en/phy11-part1/keph107.pdf");
    private static final Path CHAPTER_6 = Path.of("../ncert/2022-ed/en/phy11-part1/keph106.pdf");
    private static final Path BIO_CHAPTER_1 = Path.of("../ncert/2022-ed/en/bio11/kebo101.pdf");
    private static final Path BIO_CHAPTER_2 = Path.of("../ncert/2022-ed/en/bio11/kebo102.pdf");
    private static final Path BIO_CHAPTER_4 = Path.of("../ncert/2022-ed/en/bio11/kebo104.pdf");
    private static final double WIDTH = 657;

    @Test
    void aLineSetInFromTheLineBelowItStartsAParagraph() {
        PdfLayout.PageShape shape = shape(
                line(127.7, 100, "Bookman", "This clearly shows that the force due to"),
                line(109.7, 112, "Bookman", "earth's gravity decreases with distance. If one"),
                line(109.7, 124, "Bookman", "assumes that the gravitational force due to the"),
                line(127.7, 136, "Bookman", "The quotation is essentially from Newton's"),
                line(109.7, 148, "Bookman", "famous treatise called Mathematical Principles"));

        assertThat(shape.starts()).containsExactly("This clearly shows that the force", "The quotation is essentially from Newton's");
        assertThat(shape.top()).isEqualTo(PdfLayout.Top.starts);
    }

    @Test
    void aFlushFirstLineContinuesThePreviousPage() {
        PdfLayout.PageShape shape = shape(
                line(355.7, 100, "Bookman", "cases, a simple law results when you do that :"),
                line(355.7, 112, "Bookman", "the force of attraction between a hollow shell"),
                line(373.7, 124, "Bookman", "The gravitational force is attractive, i.e., the"),
                line(355.7, 136, "Bookman", "force F is along the line joining the masses."));

        assertThat(shape.top()).isEqualTo(PdfLayout.Top.continues);
        assertThat(shape.starts()).containsExactly("The gravitational force is attractive, i.e.,");
    }

    /** A boxed statement sets every line 24 pt in from the column; that is a margin, not twenty indents. */
    @Test
    void aBoxWithItsOwnMarginIsNotAParagraphPerLine() {
        PdfLayout.PageShape shape = shape(
                line(319.7, 100, "Bookman", "For two special cases, a simple law results"),
                line(343.7, 124, "Bookman,Bold", "The force of attraction between a hollow"),
                line(343.7, 136, "Bookman,Bold", "spherical shell of uniform density and a"),
                line(343.7, 148, "Bookman", "Qualitatively this can be understood as"),
                line(343.7, 160, "Bookman", "follows: Gravitational forces caused by the"));

        assertThat(shape.starts()).isEmpty();
    }

    @Test
    void theFirstLineAfterASectionHeadingStartsAParagraph() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman", "currently accepted value is given by the"),
                line(109.7, 112, "Bookman", "measurements of the constant in many labs"),
                line(109.7, 137, "Bookman-Demi", "7.5 ACCELERATION DUE TO GRAVITY OF"),
                line(109.7, 170, "Bookman", "The earth can be imagined to be a sphere made"),
                line(109.7, 182, "Bookman", "of a large number of concentric spherical shells"));

        assertThat(shape.starts()).containsExactly("The earth can be imagined to");
    }

    @Test
    void aBoldExampleOrAnswerLabelStartsAParagraph() {
        PdfLayout.PageShape shape = shape(
                line(367.7, 100, "Bookman-Demi", "Example 7.4 Two uniform solid spheres"),
                line(367.7, 112, "Bookman", "of equal radii R, but mass M and 4 M have"),
                line(355.7, 200, "Bookman-Demi", "Answer The projectile is acted upon by two"),
                line(355.7, 212, "Bookman", "mutually opposing gravitational forces of the"));

        assertThat(shape.starts()).containsExactly("Example 7.4 Two uniform solid spheres", "Answer The projectile is acted upon");
    }

    /**
     * The v3 prompt's own segmentation rules: a printed item marker opens its paragraph and a numbered
     * law set as its own paragraph stays one — flush-left, so no indent says so (chapter 7 page 3).
     */
    @Test
    void aNumberedLawOrAnItemMarkerStartsAParagraphFlushAsItIs() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman", "2. Law of areas : The line that joins any planet"),
                line(109.7, 112, "Bookman", "to the Sun sweeps equal areas in equal intervals"),
                line(109.7, 124, "Bookman", "3. Law of periods : The square of the time period"),
                line(109.7, 136, "Bookman", "of revolution of a planet is proportional to the"),
                line(109.7, 148, "Bookman", "(a) What is the force acting on a mass 2m placed"),
                line(109.7, 160, "Bookman", "at the centroid G of the triangle and on the"),
                line(109.7, 172, "Bookman", "(ii) Once again Kepler's third law comes to aid"));

        assertThat(shape.starts()).containsExactly("2. Law of areas : The", "3. Law of periods : The",
                "(a) What is the force acting", "(ii) Once again Kepler's third law");
    }

    /** A worked example's box sets an ornament glyph before its label: "tExample 7.3" in the layer. */
    @Test
    void anExampleLabelBehindABoxOrnamentStillStartsAParagraph() {
        PdfLayout.PageShape shape = shape(
                line(83.4, 100, "Wingdings", "tExample 7.3 Find the potential energy of"),
                line(85.7, 112, "Bookman", "a system of four particles placed at the"),
                line(85.7, 124, "Bookman", "vertices of a square of side l. Also obtain"));

        assertThat(shape.starts()).containsExactly("tExample 7.3 Find the potential energy");
    }

    /** "7.6 ACCELERATION DUE TO GRAVITY BELOW" / "AND ABOVE THE SURFACE OF EARTH" is one heading on two lines. */
    @Test
    void aHeadingsSecondLineOfCapitalsIsNotAParagraph() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman-Demi", "7.6 ACCELERATION DUE TO GRAVITY BELOW"),
                line(109.7, 112, "Bookman-Demi", "AND ABOVE THE SURFACE OF EARTH"),
                line(109.7, 136, "Bookman", "Consider a point mass m at a height h above"),
                line(109.7, 148, "Bookman", "the surface of the earth as shown in the figure"));

        assertThat(shape.starts()).containsExactly("Consider a point mass m at");
    }

    /** "Using the relation" then a displayed equation: no line below to compare with, so the column's margin decides. */
    @Test
    void anIndentedProseLineBeforeADisplayStartsAParagraphButAnIndentedEquationDoesNot() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman", "the earth, h = 0, and we get the speed"),
                line(109.7, 112, "Bookman", "which is the minimum for the object to"),
                line(109.7, 124, "Bookman", "escape the pull of the earth altogether."),
                line(127.7, 160, "Bookman", "Using the relation"),
                line(133.7, 220, "Bookman", "G = 6.67 x 10-11 N m2/kg2"),
                line(109.7, 260, "Bookman", "measured independently by applying a known"),
                line(109.7, 272, "Bookman", "torque and measuring the angle of twist."));

        assertThat(shape.starts()).containsExactly("Using the relation");
    }

    @Test
    void aPageThatOpensWithADisplayedEquationCannotSayWhetherItContinues() {
        PdfLayout.PageShape shape = shape(
                glyphs(140, 100, 10, "Bookman,Italic", "F"),
                glyphs(150, 103, 6, "Bookman", "GA"),
                line(73.7, 160, "Bookman", "From the principle of superposition and the law"),
                line(73.7, 172, "Bookman", "of vector addition, the resultant gravitational"));

        assertThat(shape.top()).isEqualTo(PdfLayout.Top.unknown);
    }

    /**
     * Chapter 7 page 7: the right column's margin (319.7 pt) sits left of the page's middle and carries
     * more flush lines than the left column's, so "the commonest start in the left half" took the right
     * column for the left and read the page as one column. The left margin is the leftmost start that
     * several prose lines share.
     */
    @Test
    void aRightColumnThatStartsLeftOfTheMiddleAndOutnumbersTheLeftIsStillTheRightColumn() {
        PdfLayout.PageShape shape = shape(
                line(91.7, 100, "Bookman", "If the mass m is situated on the surface of"),
                line(73.7, 112, "Bookman", "earth, then r = R and the gravitational force on"),
                line(73.7, 124, "Bookman", "it is, from the equation above it on the page"),
                line(73.7, 136, "Bookman", "known quantity. The measurement of G by the"),
                line(319.7, 100, "Bookman", "its distance from the centre of the earth is"),
                line(319.7, 112, "Bookman", "the force on the point mass m , we get from"),
                line(337.7, 124, "Bookman", "The acceleration experienced by the point"),
                line(319.7, 136, "Bookman", "mass is F(h)/m and we get the relation here"),
                line(319.7, 148, "Bookman", "surface of earth : For h and R we can see"),
                line(319.7, 160, "Bookman", "expand the right hand side of the equation"));

        assertThat(shape.starts()).containsExactly("If the mass m is situated", "The acceleration experienced by the point");
    }

    /**
     * Chapter 7 page 11: the left column opens with the displayed equations that finish page 10's
     * paragraph, and they carry no text layer at all — so its first line in the layer is the indented
     * "A point to note…", 67 pt below where the right column starts. Something the layer cannot see is
     * above it, and the page cannot say whether it continues.
     */
    @Test
    void aLeftColumnWhoseFirstLineSitsFarBelowTheRightColumnsCannotSayWhetherItContinues() {
        PdfLayout.PageShape shape = shape(
                line(91.7, 184, "Bookman", "A point to note is that the speed of the projectile"),
                line(73.7, 196, "Bookman", "is zero at N, but is nonzero when it strikes the"),
                line(73.7, 208, "Bookman", "heavier sphere 4 M. The calculation of this speed"),
                line(73.7, 220, "Bookman", "is left as an exercise to the students of the book"),
                line(319.7, 117, "Bookman", "traverses a distance with speed V. Its time and"),
                line(319.7, 129, "Bookman", "period T therefore is the one given below here"),
                line(319.7, 141, "Bookman", "on substitution of value of V from the equation"));

        assertThat(shape.top()).isEqualTo(PdfLayout.Top.unknown);
        assertThat(shape.starts()).contains("A point to note is that");
    }

    /** The running head is smaller than the body; it is never the page's first line. */
    @Test
    void theRunningHeadIsNotTheFirstLine() {
        PdfLayout.PageShape shape = shape(
                glyphs(546.5, 83, 8, "Bookman", "PHYSICS"),
                line(109.7, 108, "Bookman", "earth's gravity decreases with distance. If one"),
                line(109.7, 120, "Bookman", "assumes that the gravitational force due to the"),
                line(109.7, 132, "Bookman", "earth decreases in proportion to the inverse"));

        assertThat(shape.top()).isEqualTo(PdfLayout.Top.continues);
    }

    @Test
    void aBoldCaptionLabelIsCollectedAndAProseMentionIsNot() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman", "Fig. 7.8(b)), so that its distance from the centre"),
                line(109.7, 112, "Bookman", "of the earth is (R_E - d) as shown before."),
                glyphs(109.7, 700, 9, "Bookman,BoldIt", "Fig. 7.3 Gravitational force on m1 due to m2"),
                glyphs(355.7, 700, 9, "Bookman,BoldIt", "Table 7.1 Data from measurement of planetary"));

        assertThat(shape.captions()).containsExactly("fig 7.3", "table 7.1");
    }

    /**
     * The printed number of a displayed equation survives in the layer even where the equation itself does
     * not, so it is collected as the print carries it — every occurrence, in reading order.
     */
    @Test
    void everyPrintedEquationNumberIsCollectedAsOftenAsItIsPrinted() {
        PdfLayout.PageShape shape = shape(
                line(109.7, 100, "Bookman", "Equating R.H.S of Eqs. (7.33) and (7.34) and cancelling out m,"),
                line(300.0, 130, "Bookman", "V^2 = G M_E / (R_E + h) (7.35)"),
                line(109.7, 160, "Bookman", "Thus V decreases as h increases. From equation (7.35),"),
                line(109.7, 700, "Bookman", "which is 1.52 (not a number of this chapter's)"));

        assertThat(shape.equationNumbers()).containsExactly("(7.33)", "(7.34)", "(7.35)", "(7.35)");
    }

    /** The real chapter: page 11, whose displayed (7.35) is also referred to in the running text. */
    @Test
    void chapterSevenPageElevenNumbersItsEquationsAsPrinted() throws IOException {
        assertThat(chapterSeven().get(10).equationNumbers())
                .filteredOn("(7.35)"::equals).hasSize(3);
    }

    /** The real chapter: page 4, where run 9 joined an indented top line onto page 3. */
    @Test
    void chapterSevenPageFourAsPrinted() throws IOException {
        List<PdfLayout.PageShape> pages = chapterSeven();
        PdfLayout.PageShape page4 = pages.get(3);

        assertThat(page4.top()).isEqualTo(PdfLayout.Top.starts);
        assertThat(page4.starts()).anyMatch(start -> start.startsWith("This clearly shows"))
                .anyMatch(start -> start.startsWith("The quotation is essentially"))
                .anyMatch(start -> start.startsWith("Stated Mathematically"))
                .anyMatch(start -> start.startsWith("The gravitational force is attractive"))
                .anyMatch(start -> start.startsWith("Before we can apply"))
                .noneMatch(start -> start.startsWith("Every body in the universe"))
                .noneMatch(start -> start.startsWith("Equation"));
        assertThat(page4.captions()).contains("fig 7.3", "fig 7.4");
    }

    /** Page 6: "The bar AB has…" opens indented — run 9 glued it onto page 5's last paragraph. */
    @Test
    void chapterSevenPageSixAsPrinted() throws IOException {
        PdfLayout.PageShape page6 = chapterSeven().get(5);

        assertThat(page6.top()).isEqualTo(PdfLayout.Top.starts);
        assertThat(page6.starts()).anyMatch(start -> start.startsWith("The bar AB has"))
                .anyMatch(start -> start.startsWith("Observation of"))
                .anyMatch(start -> start.startsWith("Since Cavendish"))
                .anyMatch(start -> start.startsWith("The earth can be imagined"))
                .anyMatch(start -> start.startsWith("For a point inside the earth"))
                .anyMatch(start -> start.startsWith("Again consider the earth"))
                .anyMatch(start -> start.startsWith("We assume that the entire earth"))
                .noneMatch(start -> start.startsWith("calculate G from"));
    }

    @Test
    void chapterSevenPageTenAsPrinted() throws IOException {
        PdfLayout.PageShape page10 = chapterSeven().get(9);

        assertThat(page10.top()).isEqualTo(PdfLayout.Top.starts);
        assertThat(page10.starts()).anyMatch(start -> start.startsWith("By the principle of energy"))
                .anyMatch(start -> start.startsWith("Example 7.4"))
                .anyMatch(start -> start.startsWith("Answer"))
                .anyMatch(start -> start.startsWith("The neutral point"))
                .anyMatch(start -> start.startsWith("Using the relation"))
                .noneMatch(start -> start.startsWith("mutually opposing"));
    }

    /**
     * Chapter 6 page 17: after the display "L_x = K_1, L_y = K_2 and L_z = K_3 (6.29 b)" the print sets
     * "Here K_1, K_2 and K_3 are constants; L_x, L_y and" 17.7 pt in, and the line below it returns to
     * the column margin — a first line by every rule this class already holds. §6.7.2 ¶20 had swallowed
     * it, and **no flag in any verify report names it**: the check never saw the printed start (found by
     * reading the page's line geometry, 2026-09-19).
     */
    @Test
    void chapterSixPageSeventeenStartsWhereTheParagraphOpensWithMath() throws IOException {
        assertThat(chapterSix().get(16).starts()).anyMatch(start -> start.startsWith("Here K"));
    }

    /**
     * Page 23's "where m_i is the mass of the particle" is the same shape — indented after the display
     * for k_i, and §6.9 ¶1 had swallowed it — but this one the check does see, and names as a printed
     * start no row begins with. It is here as a guard: whatever makes page 17 visible must not cost
     * page 23, whose line carries subscripts of its own.
     */
    @Test
    void chapterSixPageTwentyThreeAlreadyStartsWhereTheParagraphOpensWithMath() throws IOException {
        assertThat(chapterSix().get(22).starts()).anyMatch(start -> start.startsWith("where m"));
    }

    /**
     * The cost of admitting math-bearing prose, caught on the first real run (2026-09-19): chapter 6
     * page 27 opens with "= 2π × angular speed in rev/s", a displayed definition continuing from page 26,
     * set 35.7 pt in. Three words among seven tokens kept it out of prose before; counting only tokens of
     * two characters or more let it in, and the indent band then read it as a paragraph start — which
     * flagged §6.10 ¶13 as a row running across a page that opens a new paragraph. A paragraph never
     * opens with an operator.
     */
    @Test
    void aLineOpeningWithAnOperatorIsADisplayCarriedOnAndNotAParagraph() throws IOException {
        assertThat(chapterSix().get(26).starts()).noneMatch(start -> start.startsWith("="));
        assertThat(chapterSix().get(26).top()).isNotEqualTo(PdfLayout.Top.starts);
    }

    /**
     * Biology sets its sub-headings in Title Case — "1.2.2 Genus", "4.1.4 Coelom" — where Physics
     * sets "7.5 ACCELERATION DUE TO GRAVITY". The heading rule was measured on Physics and demanded
     * capitals, so on {@code bio11} no heading was ever recognised, the "first line after a heading
     * starts a paragraph" rule never fired, and a page of five headed paragraphs reported that the
     * print starts none of them: 262 of the 357 rows named by the first whole-book run (2026-09-23).
     */
    @Test
    void aTitleCaseSectionHeadingIsStillAHeading() {
        PdfLayout.PageShape shape = shape(
                line(174, 100, "Bookman", "as Homo sapiens. The scientific name is written"),
                line(174, 125, "Bookman-Demi", "1.2.2 Genus"),
                line(174, 137, "Bookman-Demi", "Genus comprises a group of related species which"),
                line(174, 149, "Bookman", "has more characters in common in comparison to"));

        assertThat(shape.starts()).containsExactly("Genus comprises a group of related");
    }

    /**
     * The limit of every start rule on this book, pinned so it is not "fixed" speculatively again.
     *
     * <p>bio11 sets its top-level section headings in a face PDFBox cannot map. Chapter 2 page 10
     * prints "2.4 KINGDOM PLANTAE", "2.5 KINGDOM ANIMALIA" and "2.6 VIRUSES, VIROIDS, PRIONS AND
     * LICHENS" — pymupdf reads all three as bold 13 pt — and the layer PDFBox hands us carries only
     * the body text: the paragraphs run from one section into the next with nothing between them.
     * So the three rows that open those sections can be paired with a printed start by no rule over
     * this layer, and they are three of the report's remaining page-level start flags.
     *
     * <p>On 2026-09-24 a rule for a heading line carrying only its number was written against this
     * page and reverted, because the number is missing too: what pymupdf shows as "2.4" set apart
     * from its title is not what this layer contains. A fix here has to come from somewhere other
     * than the characters — the vertical gap the missing heading leaves is the only trace of it.
     */
    @Test
    void aSmallCapsHeadingIsNotSmallText() {
        PdfLayout.PageShape shape = shape(
                concat(glyphs(42, 100, 13, "Bookman,Bold", "2.4 K"),
                        glyphs(75, 100, 9, "Bookman,Bold", "INGDOM PLANTAE")),
                line(42, 125, "Bookman", "Kingdom Plantae includes all eukaryotic chlorophyll"),
                line(42, 137, "Bookman", "organisms commonly called plants. A few members are"),
                line(42, 149, "Bookman", "heterotrophic such as the insectivorous plants or para"));

        assertThat(shape.starts()).containsExactly("Kingdom Plantae includes all eukaryotic chlorophyll");
        assertThat(shape.top()).isEqualTo(PdfLayout.Top.starts);
    }

    /** ch 2 p10 sets 2.4, 2.5 and 2.6 this way, and named all three of their paragraphs as unstarted. */
    @Test
    void bioElevenPageTenStartsAParagraphUnderEachOfItsSmallCapsHeadings() throws IOException {
        assertThat(biologyChapterTwo().get(9).starts())
                .anyMatch(start -> start.startsWith("Kingdom Plantae includes"))
                .anyMatch(start -> start.startsWith("This kingdom is characterised"))
                .anyMatch(start -> start.startsWith("In the five kingdom classification"))
                .hasSize(6);
        assertThat(biologyChapterTwo().get(9).top()).isEqualTo(PdfLayout.Top.starts);
    }

    /** "14.2.1 Respiratory Volumes and / Capacities": the wrap is the heading, not its first paragraph. */
    @Test
    void aTitleCaseHeadingWrappedToASecondLineIsStillTheHeading() {
        PdfLayout.PageShape shape = shape(
                line(174, 100, "Bookman", "using a spirometer which helps in clinical work"),
                line(174, 125, "Bookman-Demi", "14.2.1 Respiratory Volumes and"),
                line(174, 137, "Bookman-Demi", "Capacities"),
                line(174, 149, "Bookman-Demi", "Tidal Volume (TV): Volume of air inspired or"),
                line(174, 161, "Bookman", "expired during a normal respiration. It is approx."));

        assertThat(shape.starts()).containsExactly("Tidal Volume (TV): Volume of air");
    }

    /**
     * The signal the join check never looked at. A justified paragraph's last line is ragged and every
     * other line is set to the column's measure, so a short last line proves the paragraph closed on
     * this page — which is the only thing that separates bio11's real page-break joins from its false
     * ones, the book setting a page's first line flush left whether it continues or not.
     */
    @Test
    void aLastLineShortOfTheMeasureEndsItsParagraph() {
        PdfLayout.PageShape shape = shape(
                line(174, 100, "Bookman", "gametes. Fertilisation is internal and development"),
                line(174, 112, "Bookman", "is indirect having a larval stage which is quite"),
                line(174, 124, "Bookman", "morphologically distinct."));

        assertThat(shape.bottom()).isEqualTo(PdfLayout.Bottom.ends);
    }

    @Test
    void aLastLineSetToTheFullMeasureRunsOnToTheNextPage() {
        PdfLayout.PageShape shape = shape(
                line(174, 100, "Bookman", "an internal endoderm, are called diploblastic ani"),
                line(174, 112, "Bookman", "layer, mesoglea, is present in between the ectode"),
                line(174, 124, "Bookman", "and the endoderm as shown in the figure (Figure 4"));

        assertThat(shape.bottom()).isEqualTo(PdfLayout.Bottom.continues);
    }

    /** A figure caption below the text sets its own margin, so it is not the paragraph's last line. */
    @Test
    void aCaptionBelowTheTextIsNotWhatTheBottomIsMeasuredOn() {
        PdfLayout.PageShape shape = shape(
                line(174, 100, "Bookman", "Bryophytes include the various mosses and liverwo"),
                line(174, 112, "Bookman", "commonly growing in moist shaded areas in the hil"),
                line(95, 260, "Bookman-Demi", "Figure 3.2 Bryophytes: A liverwort"));

        assertThat(shape.bottom()).isEqualTo(PdfLayout.Bottom.continues);
    }

    /** bio11 ch 1 p7: five Title-Case headings, five paragraphs, and the check saw none of them. */
    @Test
    void bioElevenPageSevenStartsAParagraphUnderEveryTitleCaseHeading() throws IOException {
        assertThat(biologyChapterOne().get(6).starts())
                .anyMatch(start -> start.startsWith("Genus comprises"))
                .anyMatch(start -> start.startsWith("The next category"))
                .anyMatch(start -> start.startsWith("You have seen earlier"))
                .anyMatch(start -> start.startsWith("This category includes"))
                .anyMatch(start -> start.startsWith("Classes comprising"));
    }

    /** The two bio11 page ends the 2026-09-23 adjudication settled by hand, now settled by code. */
    @Test
    void bioElevenPageEndsAreReadTheWayTheAdjudicationReadThem() throws IOException {
        assertThat(biologyChapterFour().get(3).bottom()).isEqualTo(PdfLayout.Bottom.ends);
        assertThat(biologyChapterFour().get(1).bottom()).isEqualTo(PdfLayout.Bottom.continues);
    }

    /**
     * bio11 ch 4 p10 is a figure page whose body sits in one column of 29 prose lines, with two lines
     * reaching across the notional gutter. Reading order ends in the right column where there is one,
     * and those two outvoted the twenty-nine that are the page: the bottom read "continues" and the
     * join flag on p11 survived a ruling of noise (found on the first real run, 2026-09-23).
     */
    @Test
    void aFewLinesAcrossTheGutterAreNotTheColumnReadingOrderEndsIn() throws IOException {
        assertThat(biologyChapterFour().get(9).bottom()).isEqualTo(PdfLayout.Bottom.ends);
    }

    /**
     * The page a chapter's Summary starts on carries teaching above the heading and apparatus below it
     * (D15, 2026-09-24). Cut there, the page is what was taught on it: nothing below the heading in its
     * column, and none of the right column when the heading is in the left, because reading order has
     * already left the teaching behind.
     */
    @Test
    void aPageCutAtItsApparatusHeadingKeepsOnlyWhatIsPrintedAboveIt() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(91.7, 100, "Bookman", "When fats are used in respiration, the RQ is"),
                line(73.7, 112, "Bookman", "less than one, as shown in the equation that"),
                line(73.7, 124, "Bookman", "follows the example which the book sets here."),
                line(91.7, 136, "Bookman", "When proteins are respiratory substrates the"),
                line(73.7, 148, "Bookman", "ratio would be about nine tenths of the whole."),
                line(73.7, 172, "Bookman,Bold", "SUMMARY"),
                line(91.7, 190, "Bookman", "Plants unlike animals have no special systems"),
                line(73.7, 202, "Bookman", "for breathing or for the exchange of the gases."),
                line(373.7, 100, "Bookman", "Cellular respiration is the breakdown of the"),
                line(355.7, 112, "Bookman", "food materials within the cell to release the"),
                line(355.7, 124, "Bookman", "energy that is trapped for the synthesis of it."));

        assertThat(shape.starts()).containsExactly("When fats are used in respiration,",
                "When proteins are respiratory substrates the");
        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 5));
    }

    /** A heading in the right column leaves the whole left column taught, and what is above it in its own. */
    @Test
    void aHeadingInTheRightColumnKeepsTheLeftColumnWhole() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(91.7, 100, "Bookman", "Occupational respiratory disorders are caused"),
                line(73.7, 112, "Bookman", "by the dust of the industries where grinding or"),
                line(73.7, 124, "Bookman", "stone breaking is done and so many workers get"),
                line(73.7, 136, "Bookman", "exposed to it every day of their working lives."),
                line(373.7, 100, "Bookman", "Long exposure can give rise to inflammation"),
                line(355.7, 112, "Bookman", "leading to fibrosis and thus causing a serious"),
                line(355.7, 136, "Bookman,Bold", "SUMMARY"),
                line(373.7, 154, "Bookman", "Cells utilise oxygen for the metabolism of the"),
                line(355.7, 166, "Bookman", "food and they produce the gas carbon dioxide."));

        assertThat(shape.starts()).containsExactly("Occupational respiratory disorders are caused",
                "Long exposure can give rise to");
        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 6));
    }

    /**
     * bio11 sets one column and centres its Summary heading, which puts the heading where a second
     * column would begin (ch 14 p11: x = 262 on a 576 pt page). With no column there, the cut is by
     * height alone — read as a right-column heading, the whole page was kept, Summary and all.
     */
    @Test
    void aCentredHeadingOnAOneColumnPageCutsByHeightAlone() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(42, 106, "Bookman", "Occupational Respiratory Disorders: In certain"),
                line(42, 120, "Bookman", "industries, especially those involving grinding"),
                line(42, 134, "Bookman", "so much dust is produced that the defence of"),
                line(300, 227, "Bookman,Bold", "SUMMARY"),
                line(79, 267, "Bookman", "Cells utilise oxygen for metabolism and produce"),
                line(79, 281, "Bookman", "energy along with substances like carbon dioxide"),
                line(97, 337, "Bookman", "The first step in respiration is breathing by"),
                line(79, 351, "Bookman", "which atmospheric air is taken in by the lungs."));

        assertThat(shape.starts()).isEmpty();
        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 3));
    }

    /**
     * Physics sets its Summary a point smaller than its text. On phy11-part1 ch 4 p18 the Summary
     * outnumbers the teaching above it, so the page's commonest size is the Summary's — and measured
     * that way the teaching was not body text and the page read as having nothing above its heading.
     */
    @Test
    void theTextAboveTheCutSetsItsOwnBodySize() {
        List<List<PdfLayout.Glyph>> summary = new ArrayList<>();
        for (int line = 0; line < 12; line++) {
            summary.add(glyphs(140, 311 + 11 * line, 9, "Bookman", "Newton's second law of motion states the rate of change"));
        }
        List<Object> page = new ArrayList<>(List.of(
                line(94, 181, "Bookman", "The important thing to remember is that an"),
                line(82, 193, "Bookman", "action-reaction pair consists of mutual forces"),
                line(82, 205, "Bookman", "which are always equal and opposite between"),
                line(82, 216, "Bookman", "two bodies. Two forces on the same body which"),
                line(122, 290, "Bookman,Bold", "SUMMARY")));
        page.addAll(summary);

        PdfLayout.PageShape shape = cut("SUMMARY", page.toArray(List[]::new));

        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 4));
    }

    /**
     * Physics centres its Summary across both columns (phy11-part1 ch 2 p9: x = 258 on a 603 pt page),
     * and reading order finishes both columns above it before the Summary begins. Read as a left-column
     * heading, the cut dropped the whole right column — Example 2.7's "Answer" and the Fig. 2.8 caption,
     * both printed above the heading (2026-09-25, the second read's one figure flag on the book).
     */
    @Test
    void aHeadingCentredAcrossTwoColumnsCutsBothAtItsHeight() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(91.7, 100, "Bookman", "For the car of a particular make, the braking"),
                line(73.7, 112, "Bookman", "distance was found to be ten metres for a speed"),
                line(73.7, 124, "Bookman", "of eleven metres per second and the rest of it."),
                line(373.7, 100, "Bookman", "Answer The ruler drops under free fall and so"),
                line(355.7, 112, "Bookman", "the initial velocity is zero and the rest of it"),
                line(355.7, 124, "Bookman", "follows from the equations that we have seen."),
                line(355.7, 150, "Bookman,Bold", "Fig. 2.8 Measuring the reaction time."),
                line(300, 200, "Bookman,Bold", "SUMMARY"),
                line(91.7, 220, "Bookman", "An object is said to be in motion if its position"),
                line(73.7, 232, "Bookman", "changes with time and the rest of the summary."),
                line(373.7, 220, "Bookman", "Instantaneous velocity or simply velocity is"),
                line(355.7, 232, "Bookman", "defined as the limit of the average velocity."));

        assertThat(shape.starts()).containsExactly("For the car of a particular", "Answer The ruler drops under free");
        assertThat(shape.captions()).containsExactly("fig 2.8");
        // Seven: the caption is set at body size in words, so it reads as prose, as on every page.
        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 7));
    }

    /** The real page the synthetic case above stands for. */
    @Test
    void physicsChapterTwoPageNineKeepsItsRightColumnAboveTheSummary() throws IOException {
        Path chapter = Path.of("../ncert/2022-ed/en/phy11-part1/keph102.pdf");
        assumeTrue(Files.isRegularFile(chapter), "the founder's NCERT PDFs are not on this machine");

        PdfLayout.PageShape page9 = PdfLayout.page(Files.readAllBytes(chapter), 9, "SUMMARY");

        assertThat(page9.starts()).anyMatch(start -> start.startsWith("Answer The ruler drops"))
                .noneMatch(start -> start.startsWith("An object is said"));
        assertThat(page9.captions()).contains("fig 2.8");
    }

    /** A Summary at the top of its page leaves nothing taught on it; the running head is not prose. */
    @Test
    void aHeadingAtTheTopLeavesNoProseAboveIt() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                glyphs(73.7, 60, 7, "Bookman", "RESPIRATION IN PLANTS"),
                line(73.7, 100, "Bookman,Bold", "SUMMARY"),
                line(91.7, 118, "Bookman", "Plants unlike animals have no special systems"),
                line(73.7, 130, "Bookman", "for breathing or for the exchange of the gases."),
                line(73.7, 142, "Bookman", "The stomata and lenticels allow gas exchange."));

        assertThat(shape.starts()).isEmpty();
        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 0));
    }

    /**
     * bio11 sets its Summary heading with a large initial and small capitals, so the heading may arrive
     * as runs of two sizes; it is still the heading.
     */
    @Test
    void aSmallCapsHeadingIsFound() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(91.7, 100, "Bookman", "When fats are used in respiration, the RQ is"),
                line(73.7, 112, "Bookman", "less than one, as shown in the equation that"),
                line(73.7, 124, "Bookman", "follows the example which the book sets here."),
                concat(glyphs(73.7, 150, 13, "Bookman,Bold", "S"), glyphs(80.2, 150, 9, "Bookman,Bold", "UMMARY")),
                line(91.7, 168, "Bookman", "Plants unlike animals have no special systems"));

        assertThat(shape.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 3));
    }

    /**
     * A heading the geometry cannot find cuts nothing, and says so: the caller then sends the page,
     * because sending a page is recoverable and discarding teaching is not.
     */
    @Test
    void aHeadingThatIsNotOnThePageCutsNothing() {
        PdfLayout.PageShape shape = cut("SUMMARY",
                line(91.7, 100, "Bookman", "When fats are used in respiration, the RQ is"),
                line(73.7, 112, "Bookman", "less than one, as shown in the equation that"),
                line(91.7, 124, "Bookman", "When proteins are respiratory substrates the"));

        assertThat(shape.starts()).hasSize(2);
        assertThat(shape.apparatus().located()).isFalse();
    }

    /** A page not asked about carries no apparatus reading at all. */
    @Test
    void aPageNotAskedAboutHasNoApparatusReading() {
        assertThat(shape(line(91.7, 100, "Bookman", "When fats are used in respiration, the RQ is"))
                .apparatus()).isNull();
    }

    /**
     * The two pages the finding rests on. bio11 ch 14 p11 prints §14.6's "Occupational Respiratory
     * Disorders" above its Summary, and its text layer carries the heading <em>first</em> — which is
     * why the cut is made by position and not by the layer's line order. Ch 1 p9 opens with its Summary.
     */
    @Test
    void biologyChapterFourteenPageElevenCarriesTeachingAboveItsSummary() throws IOException {
        Path chapter = Path.of("../ncert/2022-ed/en/bio11/kebo114.pdf");
        assumeTrue(Files.isRegularFile(chapter), "the founder's NCERT PDFs are not on this machine");

        PdfLayout.PageShape page11 = PdfLayout.pages(Files.readAllBytes(chapter), 11, "SUMMARY").get(10);

        assertThat(page11.apparatus()).isEqualTo(new PdfLayout.Apparatus(true, 6));
        assertThat(page11.topLine()).startsWith("Occupational Respiratory Disorders");
        assertThat(page11.starts()).noneMatch(start -> start.startsWith("The first step in respiration"));
    }

    @Test
    void biologyChapterOnePageNineOpensWithItsSummary() throws IOException {
        Path chapter = Path.of("../ncert/2022-ed/en/bio11/kebo101.pdf");
        assumeTrue(Files.isRegularFile(chapter), "the founder's NCERT PDFs are not on this machine");

        assertThat(PdfLayout.pages(Files.readAllBytes(chapter), 9, "SUMMARY").get(8).apparatus())
                .isEqualTo(new PdfLayout.Apparatus(true, 0));
    }

    private static List<PdfLayout.PageShape> biologyChapterOne() throws IOException {
        assumeTrue(Files.isRegularFile(BIO_CHAPTER_1), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(BIO_CHAPTER_1));
    }

    private static List<PdfLayout.PageShape> biologyChapterTwo() throws IOException {
        assumeTrue(Files.isRegularFile(BIO_CHAPTER_2), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(BIO_CHAPTER_2));
    }

    private static List<PdfLayout.PageShape> biologyChapterFour() throws IOException {
        assumeTrue(Files.isRegularFile(BIO_CHAPTER_4), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(BIO_CHAPTER_4));
    }

    private static List<PdfLayout.PageShape> chapterSeven() throws IOException {
        // CI has no PDFs: guard on the file itself, not the directory whose manifest is committed.
        assumeTrue(Files.isRegularFile(CHAPTER_7), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(CHAPTER_7));
    }

    private static List<PdfLayout.PageShape> chapterSix() throws IOException {
        assumeTrue(Files.isRegularFile(CHAPTER_6), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(CHAPTER_6));
    }

    private static PdfLayout.PageShape shape(List<?>... lines) {
        List<PdfLayout.Glyph> glyphs = new ArrayList<>();
        Arrays.stream(lines).forEach(line -> line.forEach(glyph -> glyphs.add((PdfLayout.Glyph) glyph)));
        return PdfLayout.shape(glyphs, WIDTH);
    }

    private static PdfLayout.PageShape cut(String heading, List<?>... lines) {
        List<PdfLayout.Glyph> glyphs = new ArrayList<>();
        Arrays.stream(lines).forEach(line -> line.forEach(glyph -> glyphs.add((PdfLayout.Glyph) glyph)));
        return PdfLayout.shape(glyphs, WIDTH, heading);
    }

    /** One line built from runs of different sizes — a heading set in small caps. */
    private static List<PdfLayout.Glyph> concat(List<PdfLayout.Glyph> first, List<PdfLayout.Glyph> rest) {
        List<PdfLayout.Glyph> all = new ArrayList<>(first);
        all.addAll(rest);
        return all;
    }

    private static List<PdfLayout.Glyph> line(double x, double y, String font, String text) {
        return glyphs(x, y, 10, font, text);
    }

    /** One glyph per character, half an em wide, the way a text layer reports a line. */
    private static List<PdfLayout.Glyph> glyphs(double x, double y, double size, String font, String text) {
        List<PdfLayout.Glyph> glyphs = new ArrayList<>();
        double at = x;
        for (int index = 0; index < text.length(); index++) {
            String character = String.valueOf(text.charAt(index));
            double width = size * 0.5;
            if (!character.isBlank()) {
                glyphs.add(new PdfLayout.Glyph(at, y, width, size, font, character));
            }
            at += width;
        }
        return glyphs;
    }
}
