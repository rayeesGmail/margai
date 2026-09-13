package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.NcertPage;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The cases are the real page breaks of the first full extraction of Physics Part-I, checked on
 * the rendered pages: an Answer opening the page after its Example's question (twice), a paragraph
 * genuinely running on after a full stop (once — the case a "finished sentence" test got wrong),
 * and a page that re-transcribed the previous page's last line.
 */
class PageBreakRepairsTest {

    private static ExtractedPage page(int page, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) 4, page, new BigDecimal("0.9"), null, List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, int no, String text) {
        return new NcertPage.Paragraph(section, no, text, List.of());
    }

    /** Pages 61–62 of Chapter 4: the question ends the page, the Answer opens the next. */
    @Test
    void anAnswerOpeningThePageIsTheNextParagraphAndEverythingAfterItMovesUp() {
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(13, p("4.9.1", 12, "For theta_max = 15 degrees, mu_s = 0.27"),
                        p("4.9.1", 13, "What is the acceleration of the block and trolley system? Neglect the mass of the string.")),
                page(14, p("4.9.1", 13, "Answer As the string is inextensible, the block and the trolley have the same acceleration."),
                        p("4.9.1", 14, "Thus the equation for the motion of the trolley is T - 8 = 20 a."),
                        p("4.10", 1, "A new section restarts at one and does not move.")),
                page(15, p("4.9.1", 15, "Rolling friction again has a complex origin."))));

        assertThat(repaired.pages().get(0).paragraphs()).extracting(NcertPage.Paragraph::paraNo).containsExactly(12, 13);
        assertThat(repaired.pages().get(1).paragraphs()).extracting(NcertPage.Paragraph::paraNo).containsExactly(14, 15, 1);
        // Page 15's numbering was chained from page 14's, so it moves with it.
        assertThat(repaired.pages().get(2).paragraphs()).extracting(NcertPage.Paragraph::paraNo).containsExactly(16);
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("§4.9.1").contains("opens with \"Answer\"").contains("move up by one");
    }

    /**
     * Pages 3–4 of Chapter 1: "…namely four." ends the page and "This shows that the location of
     * decimal point…" opens the next, flush left. One paragraph, broken at a full stop. Nothing
     * here may touch it — the previous rule that refused it was wrong.
     */
    @Test
    void aParagraphRunningOnAfterAFullStopIsLeftToTheModel() {
        List<ExtractedPage> pages = List.of(
                page(3, p("1.3", 3, "All these numbers have the same number of significant figures, namely four.")),
                page(4, p("1.3", 3, "This shows that the location of decimal point is of no consequence."),
                        p("1.3", 4, "The example gives the following rules.")));

        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(pages);

        assertThat(repaired.pages()).isEqualTo(pages);
        assertThat(repaired.notes()).isEmpty();
    }

    /** Page 15 of Chapter 4 began by repeating page 14's last line. */
    @Test
    void aRepeatedTailIsDroppedFromTheContinuation() {
        String tail = "For the same weight, rolling friction is much smaller (even by 2 or 3 orders of magnitude) than static or sliding friction.";
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(14, p("4.9.1", 18, "A body like a ring rolling without slipping will suffer no friction. " + tail)),
                page(15, p("4.9.1", 18, "is much smaller (even by 2 or 3 orders of magnitude) than static or sliding friction. "
                        + "Rolling friction again has a complex origin."))));

        assertThat(repaired.pages().get(1).paragraphs().getFirst().text())
                .isEqualTo("Rolling friction again has a complex origin.");
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("repeated the end of page 14");
    }

    @Test
    void aShortCoincidenceOfWordingIsNotARepeat() {
        assertThat(PageBreakRepairs.withoutRepeatedTail(
                "The force on the block is along the plane.", "the plane is smooth and the block slides")).isNull();
    }

    @Test
    void aLabelThatIsNotAtThePreviousPagesAddressIsNotARepair() {
        List<ExtractedPage> pages = List.of(
                page(1, p("4.1", 2, "The question, ending the page.")),
                page(2, p("4.1", 3, "Answer It was numbered correctly, as the next paragraph.")));

        assertThat(PageBreakRepairs.apply(pages).notes()).isEmpty();
    }

    @Test
    void aTextFreePageBetweenDoesNotBreakTheChain() {
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(5, p("6.7.4", 3, "Show that the angular momentum remains constant.")),
                page(6),
                page(7, p("6.7.4", 3, "Answer Let the particle with velocity v be at point P."))));

        assertThat(repaired.pages().get(2).paragraphs().getFirst().paraNo()).isEqualTo(4);
    }
}
