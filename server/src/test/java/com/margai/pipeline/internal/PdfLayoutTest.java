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

    private static List<PdfLayout.PageShape> chapterSeven() throws IOException {
        // CI has no PDFs: guard on the file itself, not the directory whose manifest is committed.
        assumeTrue(Files.isRegularFile(CHAPTER_7), "the founder's NCERT PDFs are not on this machine");
        return PdfLayout.pages(Files.readAllBytes(CHAPTER_7));
    }

    private static PdfLayout.PageShape shape(List<?>... lines) {
        List<PdfLayout.Glyph> glyphs = new ArrayList<>();
        Arrays.stream(lines).forEach(line -> line.forEach(glyph -> glyphs.add((PdfLayout.Glyph) glyph)));
        return PdfLayout.shape(glyphs, WIDTH);
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
