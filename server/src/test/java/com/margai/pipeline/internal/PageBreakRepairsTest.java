package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.NcertPage;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The cases are real page breaks of phy11-part1, checked on the rendered pages: an Answer opening
 * the page after its Example's question (twice), a paragraph genuinely running on after a full
 * stop (once — the case a "finished sentence" test got wrong), a page that re-transcribed the
 * previous page's last line, and — D15 — a page that invented a lead-in to make the quoted tail a
 * sentence and then repeated the tail in full (ch 6 p8, deterministic on two calls).
 *
 * <p>Since v3 the repairs move a flag or cut a repeated span; there is no number to renumber.
 */
class PageBreakRepairsTest {

    private static ExtractedPage page(int page, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) 4, page, new BigDecimal("0.9"), null, List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, String text) {
        return new NcertPage.Paragraph(section, text, false, List.of());
    }

    private static NcertPage.Paragraph continuing(String section, String text) {
        return new NcertPage.Paragraph(section, text, true, List.of());
    }

    /** Pages 61–62 of Chapter 4: the question ends the page, the Answer opens the next. */
    @Test
    void anAnswerOpeningAFlaggedPageIsANewParagraphAndTheFlagIsClearedAndReported() {
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(13, p("4.9.1", "What is the acceleration of the block and trolley system? Neglect the mass of the string.")),
                page(14, continuing("4.9.1", "Answer As the string is inextensible, the block and the trolley have the same acceleration."),
                        p("4.9.1", "Thus the equation for the motion of the trolley is T - 8 = 20 a."))));

        NcertPage.Paragraph answer = repaired.pages().get(1).paragraphs().getFirst();
        assertThat(answer.continuesPreviousPage()).isFalse();
        assertThat(answer.text()).startsWith("Answer As the string");
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("§4.9.1").contains("page 14 opens with \"Answer\"").contains("flag is cleared");
    }

    /**
     * Pages 3–4 of Chapter 1: "…namely four." ends the page and "This shows that the location of
     * decimal point…" opens the next, flush left. One paragraph, broken at a full stop. Nothing
     * here may touch the model's flag — the previous rule that refused it was wrong.
     */
    @Test
    void aParagraphRunningOnAfterAFullStopIsLeftToTheModel() {
        List<ExtractedPage> pages = List.of(
                page(3, p("1.3", "All these numbers have the same number of significant figures, namely four.")),
                page(4, continuing("1.3", "This shows that the location of decimal point is of no consequence."),
                        p("1.3", "The example gives the following rules.")));

        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(pages);

        assertThat(repaired.pages()).isEqualTo(pages);
        assertThat(repaired.notes()).isEmpty();
    }

    /** Page 15 of Chapter 4 began by repeating page 14's last line. */
    @Test
    void aRepeatedTailIsDroppedFromTheContinuation() {
        String tail = "For the same weight, rolling friction is much smaller (even by 2 or 3 orders of magnitude) than static or sliding friction.";
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(14, p("4.9.1", "A body like a ring rolling without slipping will suffer no friction. " + tail)),
                page(15, continuing("4.9.1", "is much smaller (even by 2 or 3 orders of magnitude) than static or sliding friction. "
                        + "Rolling friction again has a complex origin."))));

        NcertPage.Paragraph first = repaired.pages().get(1).paragraphs().getFirst();
        assertThat(first.text()).isEqualTo("Rolling friction again has a complex origin.");
        assertThat(first.continuesPreviousPage()).isTrue();
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("repeated the end of page 14");
    }

    /**
     * ch 6 p8 of phy11-part1 (D15): page 7 ends "…Suppose, the three squares that make up the L
     * shaped lamina" and page 8 opens "of Fig. 6.11 had different masses." The model wrote an
     * invented clause, then the whole quoted tail, then the page's real words — and did not flag
     * the paragraph as continuing. Words before a verbatim repeat of the previous page's ending
     * cannot be on this page, so they go with the repeat, and what remains is a continuation.
     */
    @Test
    void anInventedLeadInBeforeARepeatedTailIsDroppedWithItAndTheRestContinues() {
        String tail = "The centre of mass of the L-shape lies on the line OD. We could have guessed this without "
                + "calculations. Can you tell why? Suppose, the three squares that make up the L shaped lamina";
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(7, p("6.2", "Y = [1(1/2) + 1(1/2) + 1(3/2)] kg m / (1 + 1 + 1) kg = 5/6 m. " + tail)),
                page(8, p("6.2", "are made of the same material and have the same thickness, then " + tail
                        + " of Fig. 6.11 had different masses. How will you then determine the centre of mass of the lamina?"),
                        p("6.3", "Equipped with the definition of the centre of mass, we are now in a position."))));

        List<NcertPage.Paragraph> paragraphs = repaired.pages().get(1).paragraphs();
        assertThat(paragraphs).hasSize(2);
        assertThat(paragraphs.getFirst().text())
                .isEqualTo("of Fig. 6.11 had different masses. How will you then determine the centre of mass of the lamina?");
        assertThat(paragraphs.getFirst().continuesPreviousPage()).isTrue();
        assertThat(paragraphs.getLast().section()).isEqualTo("6.3");
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("page 8 repeated the end of page 7")
                .contains("63 characters before the repeat")
                .contains("continuing");
    }

    /**
     * The real one, and the reason the rule has a bound (D15, the phy11-part1 corpus event of
     * 2026-09-17). Chapter 6 page 18 ends mid-sentence on "to be satisfied for mechanical", and page
     * 19 both completes it and, two sentences later, prints the same wording again — the book
     * repeats the phrase. The repeat was found at that second, innocent occurrence, and the 124
     * characters of real prose in front of it were cut on the reasoning that nothing can precede a
     * re-transcribed tail: the coplanar-forces case was lost from the corpus, silently but for one
     * line of the load report. A re-transcription stands at the head of the page; a match further
     * into it than its own length is a coincidence of wording, and nothing is cut.
     */
    @Test
    void aRepeatFurtherIntoThePageThanItsOwnLengthIsACoincidenceAndNothingIsCut() {
        String opening = "equilibrium of a rigid body. In a number of problems all the forces acting on the body "
                + "are coplanar. Then we need only three conditions to be satisfied for mechanical equilibrium. "
                + "Two of these conditions correspond to translational equilibrium.";
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(18, p("6.8", "Eq. (6.31a) and (6.31b) give six independent conditions to be "
                        + "satisfied for mechanical")),
                page(19, continuing("6.8", opening))));

        List<NcertPage.Paragraph> paragraphs = repaired.pages().get(1).paragraphs();
        assertThat(paragraphs).hasSize(1);
        assertThat(paragraphs.getFirst().text()).isEqualTo(opening);
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("page 19").contains("left whole");
    }

    /** A first paragraph that is nothing but the previous page's ending is not a paragraph at all. */
    @Test
    void aPureRepeatIsRemovedEntirely() {
        String tail = "the moment of inertia of the body about the axis of rotation is the sum over all its particles.";
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(29, p("6.12", "By definition " + tail)),
                page(30, continuing("6.12", tail), p("6.12", "Note L = L_z + L_perp (6.42c)"))));

        List<NcertPage.Paragraph> paragraphs = repaired.pages().get(1).paragraphs();
        assertThat(paragraphs).hasSize(1);
        assertThat(paragraphs.getFirst().text()).isEqualTo("Note L = L_z + L_perp (6.42c)");
        assertThat(paragraphs.getFirst().continuesPreviousPage()).isFalse();
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("nothing but the end of page 29").contains("removed");
    }

    @Test
    void aShortCoincidenceOfWordingIsNotARepeat() {
        List<ExtractedPage> pages = List.of(
                page(1, p("4.1", "The force on the block is along the plane.")),
                page(2, continuing("4.1", "the plane is smooth and the block slides")));

        assertThat(PageBreakRepairs.apply(pages).pages()).isEqualTo(pages);
    }

    @Test
    void aLabelOnAPageTheModelDidNotFlagIsNotARepair() {
        List<ExtractedPage> pages = List.of(
                page(1, p("4.1", "The question, ending the page.")),
                page(2, p("4.1", "Answer It was read correctly, as a new paragraph.")));

        assertThat(PageBreakRepairs.apply(pages).notes()).isEmpty();
    }

    @Test
    void aTextFreePageBetweenDoesNotBreakTheChain() {
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(List.of(
                page(5, p("6.7.4", "Show that the angular momentum remains constant.")),
                page(6),
                page(7, continuing("6.7.4", "Answer Let the particle with velocity v be at point P."))));

        assertThat(repaired.pages().get(2).paragraphs().getFirst().continuesPreviousPage()).isFalse();
        assertThat(repaired.notes()).singleElement(org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .contains("page 7 opens with \"Answer\"");
    }
}
