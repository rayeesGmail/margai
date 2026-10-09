package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The founder's corrections file ({@code pipeline/inputs/ncert-corrections.yaml}, D15): the outcome of
 * adjudicating what {@code ncert verify} flagged, applied by {@code ncert load} to the extraction's
 * pages before numbering. Every entry names its page and a span that must be found there exactly
 * once, so a correction can never land on a paragraph it was not written for.
 */
class NcertCorrectionsTest {

    @TempDir
    Path dir;

    @Test
    void readsEveryKindWithItsOwnFields() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: text,
                     transcribed: "|r|^3 r_hat", printed: "|r|^3 r", address: "ch 7 §7.3 ¶5",
                     reason: the third form prints the vector r}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 8, kind: join, at: "and hence the acceleration",
                     reason: "flush-left on the page, the rest of the paragraph above"}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 6, kind: split, at: "The bar AB has",
                     reason: indented on the page}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: misprint, printed: "4p/3",
                     transcribed: "4p/3", reason: the book prints p for pi}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 9, kind: false_positive, printed: "W_o",
                     transcribed: "W_o", reason: the verifier misread the subscript}
                """);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections).extracting(NcertCorrection::kind).containsExactly(NcertCorrection.Kind.text,
                NcertCorrection.Kind.join, NcertCorrection.Kind.split, NcertCorrection.Kind.misprint,
                NcertCorrection.Kind.false_positive);
        NcertCorrection first = corrections.getFirst();
        assertThat(first.book()).isEqualTo("phy11-part1");
        assertThat(first.language()).isEqualTo(BookLanguage.en);
        assertThat(first.chapter()).isEqualTo((short) 7);
        assertThat(first.page()).isEqualTo(4);
        assertThat(first.transcribed()).isEqualTo("|r|^3 r_hat");
        assertThat(first.printed()).isEqualTo("|r|^3 r");
        assertThat(first.address()).isEqualTo("ch 7 §7.3 ¶5");
        assertThat(corrections.get(1).at()).isEqualTo("and hence the acceleration");
    }

    /**
     * A ruling on one of the free checks (D15, 2026-09-19). The clean share marks a row unclean when a
     * join or figure flag names it, so the twelve join flags of phy11-part1 — every one adjudicated
     * against its page and ruled noise — cost the book twelve rows that no other entry could return. The
     * flag is named the way every other entry is, by page and by the words the paragraph starts with,
     * never by an address, which a join or a split moves.
     */
    @Test
    void readsARulingOnAFreeCheckFlag() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 1, page: 4, kind: noise, flag: join,
                     at: "This shows that the location of", address: "ch 1 §1.3 ¶5",
                     reason: "§1.3 ¶4 ends on a full stop, so nothing is cut across the page break"}
                """);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections).singleElement().satisfies(entry -> {
            assertThat(entry.kind()).isEqualTo(NcertCorrection.Kind.noise);
            assertThat(entry.flag()).isEqualTo(LayoutChecks.Kind.join);
            assertThat(entry.at()).isEqualTo("This shows that the location of");
            assertThat(entry.kind().changesText()).isFalse();
        });
    }

    @Test
    void aRulingOnAFreeCheckNeedsAFlagItCanName() throws IOException {
        Path noFlag = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 1, page: 4, kind: noise, at: "This shows", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(noFlag))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (noise): 'flag' is required");

        // The page-level start flags are outside the clean share already; 85 entries a book would bury
        // the file and rule on nothing (founder, 2026-09-19).
        Path startFlag = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 1, page: 4, kind: noise, flag: starts,
                     at: "This shows", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(startFlag))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("flag must be one of [join, figure]");
    }

    @Test
    void anEmptyListIsNoCorrections() throws IOException {
        assertThat(NcertCorrectionsYamlReader.read(write("corrections: []\n"))).isEmpty();
    }

    @Test
    void aMisspeltKeyIsRefused() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: text, transcibed: x, printed: y, reason: r}
                """);

        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1: unknown key 'transcibed'");
    }

    @Test
    void eachKindRequiresItsOwnFieldsAndOnlyThose() throws IOException {
        Path noPrinted = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: text, transcribed: x, reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(noPrinted))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (text): 'printed' is required");

        Path joinWithSpans = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 8, kind: join, at: "and hence", printed: x, reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(joinWithSpans))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (join): 'printed' does not belong to this kind");

        Path noReason = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 8, kind: join, at: "and hence"}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(noReason))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (join): 'reason' is required");
    }

    @Test
    void aTextCorrectionReplacesItsSpanOnItsPage() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "F = - G m_1m_2 / |r|^3 r_hat where G is")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                text(4, "|r|^3 r_hat", "|r|^3 r")));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text()).isEqualTo("F = - G m_1m_2 / |r|^3 r where G is");
        assertThat(applied.notes()).containsExactly(
                "ch 7 page 4 §7.3: \"|r|^3 r_hat\" → \"|r|^3 r\" (the third form prints the vector r)");
    }

    /**
     * A span is copied from the verify report, which quotes the loaded row — single-spaced — while the
     * extraction may carry a line break or a double space there; the load single-spaces anyway, so the
     * match is made on single-spaced text (spec-auditor, D15).
     */
    @Test
    void aSpanCopiedFromTheReportMatchesTheExtractionsUnevenSpacing() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "F = - G m_1m_2 /  |r|^3\nr_hat where G is")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                text(4, "|r|^3 r_hat where", "|r|^3 r where")));

        assertThat(applied.pages().getFirst().paragraphs().getFirst().text()).isEqualTo("F = - G m_1m_2 / |r|^3 r where G is");
    }

    /** Found twice, a span does not say which of the two the founder meant. */
    @Test
    void aSpanFoundTwiceOnThePageIsRefused() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "r_hat and"), p("7.3", "again r_hat")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(text(4, "r_hat", "r"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("text correction on ch 7 page 4: \"r_hat\" occurs 2 times there, not once");
    }

    @Test
    void aSpanNotOnThePageIsRefused() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "F = G m_1m_2 / r^2")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(text(4, "r_hat", "r"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("text correction on ch 7 page 4: \"r_hat\" occurs 0 times there, not once");
    }

    /** A page of a selected chapter that the extraction does not carry cannot be corrected; saying so beats skipping it. */
    @Test
    void aPageTheExtractionDoesNotCarryIsRefused() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "Text.")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(text(5, "Text", "Words"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("text correction on ch 7 page 5: that page is not in the extraction");
    }

    @Test
    void aJoinMergesAParagraphIntoTheOneBeforeItOnThePage() {
        List<ExtractedPage> pages = List.of(page(8,
                p("7.6", "Thus the force is F (d) = G M_E m (R_E - d) / R_E^3 (7.18)"),
                new NcertPage.Paragraph("7.6", "and hence the acceleration due to gravity", false, List.of("Fig. 7.8"))));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(8, NcertCorrection.Kind.join, null, null, "and hence the acceleration")));

        List<NcertPage.Paragraph> paragraphs = applied.pages().getFirst().paragraphs();
        assertThat(paragraphs).hasSize(1);
        assertThat(paragraphs.getFirst().text())
                .isEqualTo("Thus the force is F (d) = G M_E m (R_E - d) / R_E^3 (7.18) and hence the acceleration due to gravity");
        assertThat(paragraphs.getFirst().figureRefs()).containsExactly("Fig. 7.8");
    }

    /** At the top of a page, joining means continuing the previous page's paragraph — the flag the load joins on. */
    @Test
    void aJoinOnAPagesFirstParagraphFlagsItAsAContinuation() {
        List<ExtractedPage> pages = List.of(page(3, p("7.3", "Ends mid")), page(4, p("7.3", "sentence here.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(4, NcertCorrection.Kind.join, null, null, "sentence here")));

        assertThat(applied.pages().get(1).paragraphs().getFirst().continuesPreviousPage()).isTrue();
    }

    @Test
    void aSplitStartsANewParagraphAtItsSpanAndTheFigureRefFollowsItsMention() {
        List<ExtractedPage> pages = List.of(page(6, new NcertPage.Paragraph("7.4",
                "For two special cases. The bar AB has two small lead spheres (Fig. 7.6).", false, List.of("Fig. 7.6"))));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(6, NcertCorrection.Kind.split, null, null, "The bar AB has")));

        List<NcertPage.Paragraph> paragraphs = applied.pages().getFirst().paragraphs();
        assertThat(paragraphs).extracting(NcertPage.Paragraph::text)
                .containsExactly("For two special cases.", "The bar AB has two small lead spheres (Fig. 7.6).");
        assertThat(paragraphs).extracting(NcertPage.Paragraph::section).containsExactly("7.4", "7.4");
        assertThat(paragraphs.get(0).figureRefs()).isEmpty();
        assertThat(paragraphs.get(1).figureRefs()).containsExactly("Fig. 7.6");
        assertThat(paragraphs.get(1).continuesPreviousPage()).isFalse();
    }

    /** At the very start of a page's continuation, a split means "this is not a continuation". */
    @Test
    void aSplitAtTheStartOfAContinuationClearsTheFlag() {
        List<ExtractedPage> pages = List.of(page(5, p("7.3", "For two special")),
                page(6, new NcertPage.Paragraph("7.3", "The bar AB has two small lead spheres", true, List.of())));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(6, NcertCorrection.Kind.split, null, null, "The bar AB has")));

        assertThat(applied.pages().get(1).paragraphs().getFirst().continuesPreviousPage()).isFalse();
    }

    @Test
    void aSplitWhereAParagraphAlreadyStartsIsRefused() {
        List<ExtractedPage> pages = List.of(page(6, p("7.4", "The bar AB has two small lead spheres")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(
                correction(6, NcertCorrection.Kind.split, null, null, "The bar AB has"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("split correction on ch 7 page 6: a paragraph already starts at \"The bar AB has\"");
    }

    @Test
    void aJoinOnTheFirstParagraphOfTheChapterIsRefused() {
        List<ExtractedPage> pages = List.of(page(1, p("7.1", "Early in our lives")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(
                correction(1, NcertCorrection.Kind.join, null, null, "Early in our lives"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("join correction on ch 7 page 1: \"Early in our lives\" has no paragraph before it to join");
    }

    @Test
    void readsAFigureRefAndADropEntry() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: figure_ref, at: "Three equal masses",
                     remove: "Fig. 7.5", reason: "only part (b) mentions it"}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: drop, at: "The total force on m_1",
                     reason: "part of the Fig. 7.4 caption"}
                """);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections.getFirst().kind()).isEqualTo(NcertCorrection.Kind.figure_ref);
        assertThat(corrections.getFirst().removeRef()).isEqualTo("Fig. 7.5");
        assertThat(corrections.getFirst().addRef()).isNull();
        assertThat(corrections.get(1).kind()).isEqualTo(NcertCorrection.Kind.drop);
        assertThat(corrections.get(1).at()).isEqualTo("The total force on m_1");
    }

    @Test
    void aFigureRefEntryNamesExactlyOneOfRemoveOrAdd() throws IOException {
        Path both = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: figure_ref, at: "x", remove: "Fig. 7.5",
                     add: "Fig. 7.4", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(both))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (figure_ref): give exactly one of 'remove' or 'add'");
        Path neither = write("""
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: figure_ref, at: "x", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(neither))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (figure_ref): give exactly one of 'remove' or 'add'");
    }

    @Test
    void aFigureRefIsRemovedFromOrAddedToTheParagraphHoldingItsSpan() {
        List<ExtractedPage> pages = List.of(page(4,
                new NcertPage.Paragraph("7.3", "Example 7.2 Three equal masses of m kg each.", false, List.of("Fig. 7.5")),
                new NcertPage.Paragraph("7.3", "(b) What is the force (see Fig. 7.5)", false, List.of())));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                figureRef(4, "Three equal masses", "Fig. 7.5", null),
                figureRef(4, "What is the force", null, "Fig. 7.5")));

        List<NcertPage.Paragraph> paragraphs = applied.pages().getFirst().paragraphs();
        assertThat(paragraphs.get(0).figureRefs()).isEmpty();
        assertThat(paragraphs.get(1).figureRefs()).containsExactly("Fig. 7.5");
        assertThat(applied.notes()).containsExactly(
                "ch 7 page 4 §7.3: \"Fig. 7.5\" removed from the paragraph holding \"Three equal masses\" (read against the page)",
                "ch 7 page 4 §7.3: \"Fig. 7.5\" added to the paragraph holding \"What is the force\" (read against the page)");
    }

    @Test
    void removingAFigureRefTheParagraphDoesNotCarryIsRefused() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "Example 7.2 Three equal masses.")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(figureRef(4, "Three equal", "Fig. 7.5", null))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("figure_ref correction on ch 7 page 4: the paragraph holding \"Three equal\" carries no \"Fig. 7.5\"");
    }

    @Test
    void aDropRemovesTheParagraphStartingWithItsWords() {
        List<ExtractedPage> pages = List.of(page(4, p("7.3", "as shown in Fig 7.4."),
                p("7.3", "The total force on m_1 is F_1 = sum"), p("7.3", "Example 7.2 Three equal masses.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(4, NcertCorrection.Kind.drop, null, null, "The total force on m_1")));

        assertThat(applied.pages().getFirst().paragraphs()).extracting(NcertPage.Paragraph::text)
                .containsExactly("as shown in Fig 7.4.", "Example 7.2 Three equal masses.");
        assertThat(applied.notes()).containsExactly(
                "ch 7 page 4 §7.3: the paragraph starting \"The total force on m_1\" dropped (read against the page)");
    }

    /** A continuation is the rest of a paragraph, and a paragraph the next page continues has a rest; both are text corrections. */
    @Test
    void aDropThatWouldCutAParagraphInHalfIsRefused() {
        List<ExtractedPage> continuing = List.of(page(3, p("7.3", "Ends mid")),
                page(4, new NcertPage.Paragraph("7.3", "sentence here.", true, List.of())));
        assertThatThrownBy(() -> NcertCorrections.apply(continuing, List.of(
                correction(4, NcertCorrection.Kind.drop, null, null, "sentence here"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("drop correction on ch 7 page 4: \"sentence here\" continues the previous page's paragraph");

        List<ExtractedPage> continued = List.of(page(3, p("7.3", "Starts here and runs")),
                page(4, new NcertPage.Paragraph("7.3", "on to the next page.", true, List.of())));
        assertThatThrownBy(() -> NcertCorrections.apply(continued, List.of(
                correction(3, NcertCorrection.Kind.drop, null, null, "Starts here"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("drop correction on ch 7 page 3: page 4 continues the paragraph starting \"Starts here\"");
    }

    /**
     * A footnote takes its number where its page puts it, after the last paragraph printed above it, and
     * the two editions break their pages in different places: the Hindi Physics Part I prints a footnote
     * of §4.8 at the foot of p10 and Example 4.6 on p11, where the English has both on p10 — so the Hindi
     * footnote was numbered before the Example and each sat beside the other's English (D16). No join or
     * split reorders two paragraphs; a {@code defer} entry names the one that waits.
     */
    @Test
    void readsADeferEntry() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 4, page: 10, kind: defer, at: "* किसी पिण्ड की साम्यावस्था",
                     reason: "the English numbers Example 4.6 before this footnote"}
                """);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections.getFirst().kind()).isEqualTo(NcertCorrection.Kind.defer);
        assertThat(corrections.getFirst().at()).isEqualTo("* किसी पिण्ड की साम्यावस्था");
        assertThat(corrections.getFirst().kind().changesText()).isTrue();
    }

    @Test
    void aDeferNamesItsParagraphAndChangesNoWord() {
        List<ExtractedPage> pages = List.of(
                page(10, p("7.8", "Equation (7.11) implies that F = 0."), p("7.8", "* Equilibrium of a body requires more.")),
                page(11, p("7.8", "Example 7.6 A mass of 6 kg hangs.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(10, NcertCorrection.Kind.defer, null, null, "* Equilibrium of a body")));

        assertThat(applied.pages()).isEqualTo(pages);
        assertThat(applied.deferred()).containsExactly(new NcertCorrections.Deferred((short) 7, 10, 1));
        assertThat(applied.notes()).containsExactly("ch 7 page 10 §7.8: the paragraph starting \"* Equilibrium of a body\" "
                + "takes its number after the next paragraph of its section (read against the page)");
    }

    /** The entry names words, not a position: a later split above the paragraph moves it down the page, and the mark with it. */
    @Test
    void aDeferFollowsItsParagraphWhenALaterEntryMovesIt() {
        List<ExtractedPage> pages = List.of(
                page(10, p("7.8", "Equation (7.11) implies that F = 0. Example 7.5 follows."),
                        p("7.8", "* Equilibrium of a body requires more.")),
                page(11, p("7.8", "Example 7.6 A mass of 6 kg hangs.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(10, NcertCorrection.Kind.defer, null, null, "* Equilibrium of a body"),
                correction(10, NcertCorrection.Kind.split, null, null, "Example 7.5 follows")));

        assertThat(applied.deferred()).containsExactly(new NcertCorrections.Deferred((short) 7, 10, 2));
    }

    /** Half a paragraph cannot wait while its other half is numbered: that is a join or a split to write first. */
    @Test
    void aDeferOfAParagraphThatRunsAcrossAPageBreakIsRefused() {
        List<ExtractedPage> continuing = List.of(page(3, p("7.3", "Ends mid")),
                page(4, new NcertPage.Paragraph("7.3", "sentence here.", true, List.of()), p("7.3", "Another.")));
        assertThatThrownBy(() -> NcertCorrections.apply(continuing, List.of(
                correction(4, NcertCorrection.Kind.defer, null, null, "sentence here"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("defer correction on ch 7 page 4: \"sentence here\" continues the previous page's paragraph");

        List<ExtractedPage> continued = List.of(page(3, p("7.3", "Starts here and runs")),
                page(4, new NcertPage.Paragraph("7.3", "on to the next page.", true, List.of()), p("7.3", "Another.")));
        assertThatThrownBy(() -> NcertCorrections.apply(continued, List.of(
                correction(3, NcertCorrection.Kind.defer, null, null, "Starts here"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("defer correction on ch 7 page 3: page 4 continues the paragraph starting \"Starts here\"");
    }

    /** A footnote is never what the next page continues, so one at the foot of a page whose body runs on can wait. */
    @Test
    void aFootnoteBelowAParagraphThatRunsOnCanBeDeferred() {
        List<ExtractedPage> pages = List.of(
                page(3, p("7.3", "The body starts here and runs"), p("7.3", "* A footnote under it.")),
                page(4, new NcertPage.Paragraph("7.3", "on to the next page.", true, List.of()), p("7.3", "Another.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(3, NcertCorrection.Kind.defer, null, null, "* A footnote")));

        assertThat(applied.deferred()).containsExactly(new NcertCorrections.Deferred((short) 7, 3, 1));
    }

    /**
     * A section the Hindi print numbers differently from the English (D16): phy11-part2's Hindi goes from
     * 8.5.4 to "8.5.6" where the English prints 8.5.5, and puts "13.8.1 सरल लोलक" under a 13.8 the English
     * does not have. The transcription copies the heading, so its rows sit at an address no English row
     * has, and a join never crosses a section.
     */
    @Test
    void readsASectionEntry() throws IOException {
        Path file = write("""
                corrections:
                  - {book: phy11-part2, lang: hi, chapter: 8, page: 8, kind: section, at: "जब किसी तार पर",
                     section: "8.5.5", reason: "the Hindi prints 8.5.6 after 8.5.4; the English numbers it 8.5.5"}
                """);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections).singleElement().satisfies(entry -> {
            assertThat(entry.kind()).isEqualTo(NcertCorrection.Kind.section);
            assertThat(entry.at()).isEqualTo("जब किसी तार पर");
            assertThat(entry.section()).isEqualTo("8.5.5");
            assertThat(entry.kind().changesText()).isTrue();
        });
    }

    /**
     * The English sections are canonical and their rows carry the vectors and the anchors a student taps,
     * so only a Hindi entry re-addresses. A section number is quoted: unquoted, YAML reads 8.10 as 8.1.
     */
    @Test
    void aSectionEntryIsForTheHindiEditionAndNamesAPrintedSectionOfItsChapter() throws IOException {
        Path english = write("""
                corrections:
                  - {book: phy11-part2, lang: en, chapter: 8, page: 8, kind: section, at: "When a wire",
                     section: "8.5.5", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(english))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (section): only a Hindi entry re-addresses a section");

        Path unquoted = write("""
                corrections:
                  - {book: phy11-part2, lang: hi, chapter: 8, page: 8, kind: section, at: "जब किसी", section: 8.10, reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(unquoted))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (section): 'section' must be quoted");

        Path otherChapter = write("""
                corrections:
                  - {book: phy11-part2, lang: hi, chapter: 8, page: 8, kind: section, at: "जब किसी", section: "9.5.5", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(otherChapter))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (section): '9.5.5' is not a section of chapter 8");

        Path noSection = write("""
                corrections:
                  - {book: phy11-part2, lang: hi, chapter: 8, page: 8, kind: section, at: "जब किसी", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(noSection))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (section): 'section' is required");

        Path stray = write("""
                corrections:
                  - {book: phy11-part2, lang: hi, chapter: 8, page: 8, kind: join, at: "जब किसी", section: "8.5.5", reason: r}
                """);
        assertThatThrownBy(() -> NcertCorrectionsYamlReader.read(stray))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("correction 1 (join): 'section' does not belong to this kind");
    }

    /** From the paragraph the heading opens, every paragraph of that section in the chapter takes the English number. */
    @Test
    void aSectionEntryReaddressesItsSectionFromItsFirstParagraphOn() {
        List<ExtractedPage> pages = List.of(
                page(8, p("7.5.4", "The strain perpendicular to the force."), p("7.5.6", "When a wire is put under stress,")),
                page(9, new NcertPage.Paragraph("7.5.6", "work is done against the forces.", true, List.of()),
                        p("7.5.6", "This work is stored in the wire."), p("7.6", "Applications of elastic behaviour.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(section(8, "When a wire", "7.5.5")));

        assertThat(applied.pages()).flatExtracting(ExtractedPage::paragraphs).extracting(NcertPage.Paragraph::section)
                .containsExactly("7.5.4", "7.5.5", "7.5.5", "7.5.5", "7.6");
        assertThat(applied.pages().get(1).paragraphs().getFirst().continuesPreviousPage()).isTrue();
        assertThat(applied.notes()).containsExactly("ch 7 page 8 §7.5.6: the English numbers it §7.5.5, from \"When a wire\" "
                + "on — 3 paragraph(s) (read against the page)");
    }

    /**
     * A sub-heading the English does not print: once its paragraphs are back in the section above, the
     * Hindi-only opening the heading followed can be joined to the paragraph the English starts with —
     * the entries apply in file order.
     */
    @Test
    void aSubHeadingTheEnglishDoesNotPrintFoldsIntoItsSectionAndThenJoins() {
        List<ExtractedPage> pages = List.of(page(12,
                p("7.8", "No physical example is purely simple harmonic."),
                p("7.8.1", "It is said that Galileo measured the periods."), p("7.8.1", "We shall show that this is SHM.")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                section(12, "It is said that Galileo", "7.8"),
                correction(12, NcertCorrection.Kind.join, null, null, "It is said that Galileo")));

        assertThat(applied.pages().getFirst().paragraphs()).containsExactly(
                p("7.8", "No physical example is purely simple harmonic. It is said that Galileo measured the periods."),
                p("7.8", "We shall show that this is SHM."));
    }

    /** The entry is anchored where the heading is: naming a later paragraph of the section, or its own number, is a mistake. */
    @Test
    void aSectionEntryMustNameItsSectionsFirstParagraphAndANewNumber() {
        List<ExtractedPage> pages = List.of(
                page(8, p("7.5.6", "When a wire is put under stress.")),
                page(9, p("7.5.6", "This work is stored in the wire.")));

        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(section(9, "This work is stored", "7.5.5"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("section correction on ch 7 page 9: §7.5.6 has a paragraph before \"This work is stored\" "
                        + "— name the first paragraph under its heading");
        assertThatThrownBy(() -> NcertCorrections.apply(pages, List.of(section(8, "When a wire", "7.5.6"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("section correction on ch 7 page 8: \"When a wire\" is in §7.5.6 already");
    }

    /** Rulings on the verifier's flags change no text; verify reads them, the load only counts them. */
    @Test
    void misprintAndFalsePositiveRulingsChangeNothing() {
        List<ExtractedPage> pages = List.of(page(4, p("7.5", "M_E = (4p/3) R_E^3 rho")));

        NcertCorrections.Applied applied = NcertCorrections.apply(pages, List.of(
                correction(4, NcertCorrection.Kind.misprint, "4p/3", "4p/3", null)));

        assertThat(applied.pages()).isEqualTo(pages);
        assertThat(applied.notes()).isEmpty();
        assertThat(applied.rulings()).isEqualTo(1);
    }

    /** Only this edition's entries for the chapters being loaded apply; the rest of the file is another load's business. */
    @Test
    void entriesForOtherChaptersBooksOrEditionsAreNotThisLoadsBusiness() {
        List<NcertCorrection> file = List.of(
                text(4, "r_hat", "r"),
                new NcertCorrection("bio11", BookLanguage.en, (short) 7, 4, NcertCorrection.Kind.text, "a", "b", null, "r", null),
                new NcertCorrection("phy11-part1", BookLanguage.hi, (short) 7, 4, NcertCorrection.Kind.text, "a", "b", null, "r", null),
                new NcertCorrection("phy11-part1", BookLanguage.en, (short) 8, 4, NcertCorrection.Kind.text, "a", "b", null, "r", null));

        assertThat(NcertCorrections.forLoad(file, "phy11-part1", BookLanguage.en, List.of((short) 7)))
                .containsExactly(file.getFirst());
    }

    private Path write(String yaml) throws IOException {
        Path file = dir.resolve("ncert-corrections-" + UUID.randomUUID() + ".yaml");
        Files.writeString(file, yaml);
        return file;
    }

    private static NcertCorrection text(int page, String transcribed, String printed) {
        return new NcertCorrection("phy11-part1", BookLanguage.en, (short) 7, page, NcertCorrection.Kind.text,
                transcribed, printed, null, "the third form prints the vector r", null);
    }

    /**
     * The file the founder's rulings really live in. A typo in it fails a load by name and writes nothing,
     * which costs a round trip to the founder's machine to discover; this reads it where it is committed.
     */
    @Test
    void theCommittedRulingsFileParses() {
        Path file = Path.of("../pipeline/inputs/" + NcertCorrectionsYamlReader.FILE);

        List<NcertCorrection> corrections = NcertCorrectionsYamlReader.read(file);

        assertThat(corrections).isNotEmpty()
                .allSatisfy(correction -> assertThat(correction.reason()).isNotBlank());
    }

    private static NcertCorrection correction(int page, NcertCorrection.Kind kind, String transcribed, String printed,
            String at) {
        return new NcertCorrection("phy11-part1", BookLanguage.en, (short) 7, page, kind, transcribed, printed, at,
                "read against the page", null);
    }

    private static NcertCorrection figureRef(int page, String at, String remove, String add) {
        return new NcertCorrection("phy11-part1", BookLanguage.en, (short) 7, page, NcertCorrection.Kind.figure_ref,
                null, null, at, "read against the page", null, remove, add, null, null);
    }

    private static NcertCorrection section(int page, String at, String section) {
        return new NcertCorrection("phy11-part2", BookLanguage.hi, (short) 7, page, NcertCorrection.Kind.section,
                null, null, at, "read against the page", null, null, null, null, section);
    }

    private static ExtractedPage page(int page, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) 7, page, new BigDecimal("0.90"), null, List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, String text) {
        return new NcertPage.Paragraph(section, text, false, List.of());
    }
}
