package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The free half of {@code ncert verify} (D15): the loaded rows held against the print's typography.
 * The cases are chapter 7's own — run 9's two wrong joins and extra figure part, run 11's law statement
 * set flush-left — so each check is shown catching a defect a run really produced.
 */
class LayoutChecksTest {

    @Test
    void aPageWhoseRowsStartWhereThePrintStartsIsNotFlagged() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        row("7.3", 2, "This clearly shows that the force due to gravity", 4),
                        row("7.3", 3, "The quotation is essentially from Newton's treatise", 4)),
                shapes(shape(4, PdfLayout.Top.starts, "This clearly shows that the force",
                        "This clearly shows that the force", "The quotation is essentially from Newtons")));

        assertThat(result.flags()).isEmpty();
        assertThat(result.pagesCompared()).isEqualTo(1);
    }

    /** Run 11 gave Newton's law statement its own row; the print sets it flush, so the print disagrees — and says which. */
    @Test
    void rowsAndPrintedStartsThatDoNotMeetAreNamedBothWays() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        row("7.3", 2, "This clearly shows that the force", 4),
                        row("7.3", 3, "Every body in the universe attracts every other body", 4),
                        row("7.3", 4, "The quotation is essentially from Newton's", 4)),
                shapes(shape(4, PdfLayout.Top.starts, "This clearly shows that the force",
                        "This clearly shows that the force", "The quotation is essentially from Newtons",
                        "Stated Mathematically, Newtons gravitation")));

        assertThat(result.flags()).singleElement().satisfies(flag -> {
            assertThat(flag.kind()).isEqualTo(LayoutChecks.Kind.starts);
            assertThat(flag.page()).isEqualTo(4);
            assertThat(flag.address()).isNull();
            assertThat(flag.message()).isEqualTo("ch 7 p4: 3 rows start here, the print starts 3 paragraphs"
                    + " — rows the print does not start: §7.3 ¶3 \"Every body in the universe attracts every\""
                    + " · printed starts no row begins with: \"Stated Mathematically, Newtons gravitation\"");
        });
    }

    /** Run 9 joined page 4's indented "This clearly shows…" onto page 3's last paragraph. */
    @Test
    void aJoinWherePrintOpensTheNextPageWithANewParagraphIsFlaggedOnTheRow() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        new NcertParagraphRow((short) 7, "7.3", (short) 1,
                                "Legend has it that an apple fell. This clearly shows that the force", false, List.of(),
                                new ParagraphExtraction(List.of(3, 4), List.of(0, 34), new BigDecimal("0.9"), null, null))),
                shapes(shape(3, PdfLayout.Top.starts, "Legend has it that observing", "Legend has it that observing"),
                        shape(4, PdfLayout.Top.starts, "This clearly shows that the force", "This clearly shows that the force")));

        assertThat(result.flags()).filteredOn(flag -> flag.kind() == LayoutChecks.Kind.join).singleElement()
                .satisfies(flag -> {
                    assertThat(flag.address()).isEqualTo("ch 7 §7.3 ¶1");
                    assertThat(flag.message()).isEqualTo("ch 7 §7.3 ¶1 runs from p3 onto p4, but p4 opens a new"
                            + " paragraph: \"This clearly shows that the force\"");
                });
        assertThat(result.boundariesJudged()).isEqualTo(1);
    }

    /** Run 9 again, the other way: page 10's first line continues page 9, and the rows started afresh. */
    @Test
    void aFreshStartWherePrintContinuesThePreviousPageIsFlaggedOnTheRowThatStarts() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        row("7.8", 3, "If the object was thrown initially with a speed", 9),
                        row("7.8", 4, "Eqs. (7.26) and (7.27) must be equal. Hence", 10)),
                shapes(shape(9, PdfLayout.Top.starts, "If the object was thrown", "If the object was thrown initially"),
                        shape(10, PdfLayout.Top.continues, "Eqs. (7.26) and (7.27) must")));

        assertThat(result.flags()).filteredOn(flag -> flag.kind() == LayoutChecks.Kind.join).singleElement()
                .satisfies(flag -> {
                    assertThat(flag.address()).isEqualTo("ch 7 §7.8 ¶4");
                    assertThat(flag.message()).isEqualTo("ch 7 §7.8 ¶4 starts a paragraph at the top of p10, but"
                            + " the print continues p9's: \"Eqs. (7.26) and (7.27) must\"");
                });
    }

    /** A page that opens with a display or a figure cannot say; it is counted, never flagged. */
    @Test
    void anUndecidablePageTopIsCountedNotFlagged() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        row("7.3", 1, "For the gravitational force between an extended object", 5),
                        row("7.3", 2, "From the principle of superposition", 6)),
                shapes(shape(5, PdfLayout.Top.starts, "For the gravitational force", "For the gravitational force between an"),
                        shape(6, PdfLayout.Top.unknown, null, "From the principle of superposition")));

        assertThat(result.flags()).isEmpty();
        assertThat(result.boundariesUndecided()).isEqualTo(1);
    }

    /** Run 9 added "Fig. 7.1(b)" to a paragraph that names only 7.1a. */
    @Test
    void aFigureRefTheParagraphNeverMentionsIsFlagged() {
        NcertParagraphRow row = new NcertParagraphRow((short) 7, "7.2", (short) 2,
                "1. Law of orbits : All planets move in elliptical orbits (Fig. 7.1a).", false,
                List.of("Fig. 7.1a", "Fig. 7.1(b)"),
                new ParagraphExtraction(List.of(2), List.of(0), new BigDecimal("0.9"), null, null));

        LayoutChecks.Result result = LayoutChecks.check(List.of(row),
                shapes(shape(2, PdfLayout.Top.continues, "proposed a definitive model", List.of("fig 7.1", "fig 7.2"))));

        assertThat(result.flags()).filteredOn(flag -> flag.kind() == LayoutChecks.Kind.figure)
                .extracting(LayoutChecks.Flag::message)
                .containsExactly("ch 7 §7.2 ¶2: figure_refs carries \"Fig. 7.1(b)\", which the paragraph never mentions");
    }

    @Test
    void aMentionMissingFromFigureRefsAndARefNoCaptionPrintsAreFlagged() {
        NcertParagraphRow row = new NcertParagraphRow((short) 7, "7.3", (short) 7,
                "as shown in Fig. 7.4 and Fig. 7.12.", false, List.of("Fig. 7.12"),
                new ParagraphExtraction(List.of(4), List.of(0), new BigDecimal("0.9"), null, null));

        LayoutChecks.Result result = LayoutChecks.check(List.of(row),
                shapes(shape(4, PdfLayout.Top.starts, "as shown in", List.of("fig 7.3", "fig 7.4"))));

        assertThat(result.flags()).filteredOn(flag -> flag.kind() == LayoutChecks.Kind.figure)
                .extracting(LayoutChecks.Flag::message)
                .containsExactly(
                        "ch 7 §7.3 ¶7: figure_refs carries \"Fig. 7.12\", which no caption in chapter 7 prints",
                        "ch 7 §7.3 ¶7: the paragraph mentions fig 7.4, which figure_refs does not carry");
    }

    /**
     * Chapter 7 page 7 after the split correction: the print does start the paragraph the row starts, but
     * the layer maps no glyph for its math, so the two openings only meet once what is left of the line is
     * paired with it. The counts agreeing is what makes the pairing safe (founder's ruling, 2026-09-16).
     */
    @Test
    void aPrintedStartTheLayerStrippedOfItsMathIsPairedWithItsRow() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(
                        row("7.6", 3, "This is clearly less than the value of g on the surface of earth", 7),
                        row("7.6", 4, "For h/R_E << 1, using binomial expression, g(h) ≅ g (1 - 2h / R_E)", 7)),
                shapes(shape(7, PdfLayout.Top.continues, "This is clearly less than the value",
                        "This is clearly less than the value", "For , using binomial expression,")));

        assertThat(result.flags()).isEmpty();
    }

    @Test
    void aPageTheChapterPdfDoesNotHaveIsReportedNotCompared() {
        LayoutChecks.Result result = LayoutChecks.check(List.of(row("7.3", 1, "Text of a page.", 20)),
                shapes(shape(1, PdfLayout.Top.starts, "Something", "Something")));

        assertThat(result.pagesCompared()).isZero();
        assertThat(result.flags()).singleElement().satisfies(flag ->
                assertThat(flag.message()).isEqualTo("ch 7 p20: the chapter's PDF has 1 pages — nothing to compare"));
    }

    private static NcertParagraphRow row(String section, int paraNo, String text, int page) {
        return new NcertParagraphRow((short) 7, section, (short) paraNo, text, false, List.of(),
                new ParagraphExtraction(List.of(page), List.of(0), new BigDecimal("0.9"), null, null));
    }

    /** Page shapes for pages 1..max, the named ones as given and the rest blank. */
    private static List<PdfLayout.PageShape> shapes(Numbered... shapes) {
        int last = 0;
        for (Numbered shape : shapes) {
            last = Math.max(last, shape.page());
        }
        List<PdfLayout.PageShape> pages = new ArrayList<>();
        for (int page = 1; page <= last; page++) {
            pages.add(new PdfLayout.PageShape(List.of(), PdfLayout.Top.unknown, null, List.of()));
        }
        for (Numbered shape : shapes) {
            pages.set(shape.page() - 1, shape.shape());
        }
        return pages;
    }

    private static Numbered shape(int page, PdfLayout.Top top, String topLine, String... starts) {
        return new Numbered(page, new PdfLayout.PageShape(List.of(starts), top, topLine, List.of()));
    }

    private static Numbered shape(int page, PdfLayout.Top top, String topLine, List<String> captions) {
        return new Numbered(page, new PdfLayout.PageShape(List.of(topLine), top, topLine, captions));
    }

    private record Numbered(int page, PdfLayout.PageShape shape) {
    }
}
