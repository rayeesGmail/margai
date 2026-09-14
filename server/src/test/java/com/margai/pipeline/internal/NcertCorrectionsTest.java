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

    private static NcertCorrection correction(int page, NcertCorrection.Kind kind, String transcribed, String printed,
            String at) {
        return new NcertCorrection("phy11-part1", BookLanguage.en, (short) 7, page, kind, transcribed, printed, at,
                "read against the page", null);
    }

    private static ExtractedPage page(int page, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) 7, page, new BigDecimal("0.90"), null, List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, String text) {
        return new NcertPage.Paragraph(section, text, false, List.of());
    }
}
