package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * The boundary rule, and the corpus it was derived from. The synthetic cases pin the behaviour; the
 * corpus case is the one that matters, because this rule exists only because a rule derived from a
 * single Physics chapter would have been wrong about Chemistry (D14).
 */
class ChapterApparatusTest {

    private static final Path NCERT = Path.of("..", "ncert", "2022-ed", "en");

    /** Enough common words that the page reads as English; legibility is measured, not assumed. */
    private static String prose(String... lines) {
        return String.join("\n", lines) + "\n"
                + "This is the text of the page and it is written in the words that we use, "
                + "with the same of and to in a that as it for on by an which be are this.";
    }

    @Test
    void theApparatusStartsAtTheSummaryAndRunsToTheEnd() {
        List<String> pages = List.of(prose("7.1 INTRODUCTION"), prose("7.2 KEPLER"), prose("7.3 MORE"),
                prose("SUMMARY"), prose("POINTS TO PONDER"), prose("EXERCISES", "7.1 Answer the following"));

        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.find(pages, BookLanguage.en);

        assertThat(boundary).isPresent();
        assertThat(boundary.orElseThrow().page()).isEqualTo(4);
        assertThat(boundary.orElseThrow().heading()).isEqualTo("SUMMARY");
        assertThat(boundary.orElseThrow().covers(3)).isFalse();
        assertThat(boundary.orElseThrow().covers(5)).isTrue();
        assertThat(boundary.orElseThrow().covers(6)).isTrue();
    }

    /**
     * The heading's own page is apparatus only when nothing is taught above the heading. Until
     * 2026-09-24 it always was, and bio11 lost prose in 14 of its 19 chapters to it.
     */
    @Test
    void theHeadingsPageIsSentWhenTeachingIsPrintedAboveIt() {
        ChapterApparatus.Boundary taught = new ChapterApparatus.Boundary(12, "SUMMARY", 9);
        ChapterApparatus.Boundary bare = new ChapterApparatus.Boundary(12, "SUMMARY", 0);

        assertThat(taught.covers(12)).isFalse();
        assertThat(taught.covers(13)).isTrue();
        assertThat(taught.itsPage()).isEqualTo("sent: 9 prose line(s) above the heading");
        assertThat(bare.covers(12)).isTrue();
        assertThat(bare.itsPage()).isEqualTo("not sent: nothing taught above the heading");
    }

    /** A heading whose place on the page is unknown sends the page: a wasted call is recoverable, lost teaching is not. */
    @Test
    void anUnplacedHeadingSendsItsPage() {
        ChapterApparatus.Boundary unplaced = new ChapterApparatus.Boundary(12, "SUMMARY",
                ChapterApparatus.Boundary.UNPLACED);

        assertThat(unplaced.covers(12)).isFalse();
        assertThat(unplaced.covers(13)).isTrue();
        assertThat(unplaced.itsPage()).isEqualTo("sent: the heading could not be placed on it");
    }

    /** Chemistry prints it in title case, and its exercises carry no heading at all. */
    @Test
    void theMatchIsCaseInsensitive() {
        List<String> pages = List.of(prose("1.1 Types of Solutions"), prose("1.2 Expressing Concentration"),
                prose("Summary"), prose("1.5 A solution of glucose in water is labelled"));

        assertThat(ChapterApparatus.find(pages, BookLanguage.en).orElseThrow().heading()).isEqualTo("SUMMARY");
        assertThat(ChapterApparatus.find(pages, BookLanguage.en).orElseThrow().page()).isEqualTo(3);
    }

    /**
     * A Physics chapter's first page lists Summary, Points to Ponder and Exercises in its contents
     * sidebar. Matching that would skip the whole chapter — so only the back half is searched.
     */
    @Test
    void theContentsSidebarOnPageOneIsNotTheApparatus() {
        List<String> pages = List.of(prose("7.1 INTRODUCTION", "SUMMARY", "POINTS TO PONDER", "EXERCISES"),
                prose("7.2 MORE"), prose("7.3 MORE"), prose("7.4 MORE"),
                prose("SUMMARY"), prose("EXERCISES"));

        assertThat(ChapterApparatus.find(pages, BookLanguage.en).orElseThrow().page()).isEqualTo(5);
    }

    @Test
    void aChapterWithNoApparatusHeadingSendsEveryPage() {
        List<String> pages = List.of(prose("7.1 ONE"), prose("7.2 TWO"), prose("7.3 THREE"), prose("7.4 FOUR"));

        assertThat(ChapterApparatus.find(pages, BookLanguage.en)).isEmpty();
    }

    /**
     * A heading is an exact line, so an illegible layer can hide one — "VXPPDUB" is SUMMARY shifted —
     * but never invent one. chem11-part2 ch 8 shifts its body and sets its Summary heading in a font
     * that is not shifted; without the heading its exercises, numbered 8.1, 8.2, were sent (2026-09-30).
     */
    @Test
    void anIllegibleLayerStillYieldsAHeadingItPrintsPlainly() {
        List<String> garbled = List.of(
                "LVRPHULVP DQG WKH VWUXFWXUH RI PDWWHU LQ WKH ILUVW FKDSWHU RI WKLV ERRN",
                "VXPPDUB\nWKH IROORZLQJ TXHVWLRQV DUH IRU SUDFWLFH DQG UHYLVLRQ RI WKH XQLW",
                "summary\nLQ WKLV XQLW ZH KDYH OHDUQW",
                "8.1 ZKDW DUH KBEULGLVDWLRQ VWDWHV");

        assertThat(PdfTextLayer.isLegible(garbled)).isFalse();
        assertThat(ChapterApparatus.find(garbled, BookLanguage.en).orElseThrow().page()).isEqualTo(3);
    }

    /**
     * What is taught above the heading is read from prose, and an illegible layer carries none, so
     * its heading is never placed: the heading's page is sent, the pages after it are not.
     */
    @Test
    void anIllegibleLayersHeadingIsLeftUnplacedSoItsPageIsSent() {
        List<String> garbled = List.of(
                "LVRPHULVP DQG WKH VWUXFWXUH RI PDWWHU LQ WKH ILUVW FKDSWHU RI WKLV ERRN",
                "WKH IROORZLQJ TXHVWLRQV DUH IRU SUDFWLFH DQG UHYLVLRQ RI WKH XQLW",
                "summary\nLQ WKLV XQLW ZH KDYH OHDUQW",
                "8.1 ZKDW DUH KBEULGLVDWLRQ VWDWHV");

        // No PDF behind it: an illegible layer's heading is never looked for among the page's glyphs.
        ChapterApparatus.Boundary boundary = ChapterApparatus.locate(garbled, new byte[0], BookLanguage.en).orElseThrow();

        assertThat(boundary.proseLinesAbove()).isEqualTo(ChapterApparatus.Boundary.UNPLACED);
        assertThat(boundary.covers(3)).isFalse();
        assertThat(boundary.covers(4)).isTrue();
    }

    /**
     * The Hindi books set every word in Walkman-Chanakya with no Unicode map, so the layer carries the
     * font's glyph codes, not Devanagari — but a heading is still an exact line of them: सारांश is
     * {@code lkjka'k} (D16; the PARKED "Hindi apparatus boundary" row's fourth route, founder 2026-10-01).
     */
    @Test
    void aHindiChapterEndsAtItsEncodedSummary() {
        List<String> pages = List.of("1-1 Hkwfedk\nHkkSfrdh D;k gS", "1-2 HkkSfrdh dk {ks=k", "1-3 vkSj Hkh",
                "lkjka'k\nHkkSfrdh esa geus i<+k", "vH;kl\n1-1 fuEufyf[kr dk mÙkj nhft,");

        ChapterApparatus.Boundary boundary = ChapterApparatus.find(pages, BookLanguage.hi).orElseThrow();

        assertThat(boundary.page()).isEqualTo(4);
        assertThat(boundary.heading()).isEqualTo("सारांश");
    }

    /**
     * Typesetters keyed सारांश three ways, Chemistry 12's overprinted heading reads as a fourth, and
     * अभ्यास heads the two chapters whose layer lists it first.
     */
    @Test
    void everySpellingOfTheHindiHeadingsTheBooksUseIsFound() {
        for (String typed : List.of("lkjka'k", "lkjak'k", "Lkkjka'k", "lkjka'kaaaa")) {
            assertThat(ChapterApparatus.find(List.of("1-1 ,d", "1-2 nks", typed), BookLanguage.hi))
                    .as(typed).map(ChapterApparatus.Boundary::heading).hasValue("सारांश");
        }
        assertThat(ChapterApparatus.find(List.of("1-1 ,d", "1-2 nks", "vH;kl"), BookLanguage.hi))
                .map(ChapterApparatus.Boundary::heading).hasValue("अभ्यास");
    }

    /** Case carries meaning in the encoding (L is a half स, l a full one), and each edition has its own headings. */
    @Test
    void theHindiMatchIsExactAndTheEditionsDoNotShareHeadings() {
        assertThat(ChapterApparatus.find(List.of("1-1 ,d", "1-2 nks", "LKJKA'K"), BookLanguage.hi)).isEmpty();
        assertThat(ChapterApparatus.find(List.of("1-1 ,d", "1-2 nks", "SUMMARY"), BookLanguage.hi)).isEmpty();
        assertThat(ChapterApparatus.find(List.of(prose("7.1 ONE"), prose("7.2 TWO"), "lkjka'k"), BookLanguage.en))
                .isEmpty();
    }

    /**
     * The Hindi rule against the books: all 79 chapter files. Every one has its boundary in the back half,
     * none is placed — the layer has no words to count prose above the heading by, so the heading's page
     * is sent (the kech202 rule, DECISIONS 2026-09-30) — and the pages after the heading are the 223 a
     * pymupdf scan of the same files counted (D16).
     */
    @Test
    void everyHindiChapterHasABoundaryInItsBackHalfLeftUnplaced() throws IOException {
        Path hindi = Path.of("..", "ncert", "2022-ed", "hi");
        assumeTrue(Files.exists(hindi.resolve("phy11-part1/khph101.pdf")), "founder's NCERT PDFs not on this machine");

        int files = 0;
        int apparatus = 0;
        List<String> withoutBoundary = new java.util.ArrayList<>();
        java.util.Map<String, Integer> headings = new java.util.TreeMap<>();
        java.util.Map<String, String> pagesFound = new java.util.TreeMap<>();
        try (var books = Files.list(hindi)) {
            for (Path book : books.filter(Files::isDirectory).sorted().toList()) {
                try (var chapters = Files.list(book)) {
                    for (Path chapter : chapters
                            .filter(path -> path.getFileName().toString().matches("[a-z]{4}\\d{3}\\.pdf"))
                            .sorted().toList()) {
                        files++;
                        byte[] pdf = Files.readAllBytes(chapter);
                        List<String> text = PdfTextLayer.pages(pdf);
                        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.locate(text, pdf, BookLanguage.hi);
                        if (boundary.isEmpty()) {
                            withoutBoundary.add(book.getFileName() + "/" + chapter.getFileName());
                            continue;
                        }
                        ChapterApparatus.Boundary found = boundary.orElseThrow();
                        assertThat(PdfTextLayer.isLegible(text)).as("%s's layer", chapter.getFileName()).isFalse();
                        assertThat(found.page()).as("%s: the apparatus must be in the back half", chapter.getFileName())
                                .isGreaterThan(text.size() / 2);
                        assertThat(found.proseLinesAbove()).isEqualTo(ChapterApparatus.Boundary.UNPLACED);
                        headings.merge(found.heading(), 1, Integer::sum);
                        apparatus += (int) java.util.stream.IntStream.rangeClosed(1, text.size())
                                .filter(found::covers).count();
                        pagesFound.put(chapter.getFileName().toString(), found.page() + "/" + text.size());
                    }
                }
            }
        }

        assertThat(files).as("every chapter file of all ten Hindi books").isEqualTo(79);
        assertThat(withoutBoundary).isEmpty();
        assertThat(headings.values().stream().mapToInt(Integer::intValue).sum()).isEqualTo(79);
        assertThat(apparatus).as("boundary page / pages per file: %s", pagesFound).isEqualTo(223);
    }

    @Test
    void privateUseCodepointsAreDecodedSoTheGravitationChapterIsReadable() {
        String encoded = "SUMMARY".chars()
                .collect(StringBuilder::new, (sb, c) -> sb.append((char) (c + 0xF000)), StringBuilder::append)
                .toString();

        assertThat(PdfTextLayer.decodePrivateUse(encoded)).isEqualTo("SUMMARY");
    }

    /**
     * The rule against the books themselves: every chapter file of Physics, Chemistry and Biology.
     * This is the assertion the design rests on — a boundary in every file, none of it in the front
     * half, and every heading placed on its page so the teaching above it is sent, except in the one
     * file whose text layer is garbled, where the heading's page is sent unplaced.
     */
    @Test
    void everyEnglishChapterOfEverySubjectHasABoundaryInItsBackHalf() throws IOException {
        // The directory exists everywhere — its manifest is committed — so the guard is a PDF.
        assumeTrue(Files.exists(NCERT.resolve("phy11-part1/keph107.pdf")), "founder's NCERT PDFs not on this machine");

        int files = 0;
        int withBoundary = 0;
        int pages = 0;
        int apparatus = 0;
        List<String> withoutBoundary = new java.util.ArrayList<>();
        List<String> unplaced = new java.util.ArrayList<>();
        List<String> sent = new java.util.ArrayList<>();
        java.util.Map<String, ChapterApparatus.Boundary> found = new java.util.HashMap<>();

        try (var books = Files.list(NCERT)) {
            for (Path book : books.filter(Files::isDirectory).sorted().toList()) {
                try (var chapters = Files.list(book)) {
                    for (Path chapter : chapters
                            .filter(path -> path.getFileName().toString().matches("[a-z]{4}\\d{3}\\.pdf"))
                            .sorted().toList()) {
                        files++;
                        byte[] pdf = Files.readAllBytes(chapter);
                        List<String> text = PdfTextLayer.pages(pdf);
                        pages += text.size();
                        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.locate(text, pdf, BookLanguage.en);
                        if (boundary.isEmpty()) {
                            withoutBoundary.add(book.getFileName() + "/" + chapter.getFileName());
                            continue;
                        }
                        withBoundary++;
                        ChapterApparatus.Boundary placed = boundary.orElseThrow();
                        found.put(book.getFileName() + "/" + chapter.getFileName(), placed);
                        apparatus += (int) java.util.stream.IntStream.rangeClosed(1, text.size())
                                .filter(placed::covers).count();
                        if (placed.proseLinesAbove() == ChapterApparatus.Boundary.UNPLACED) {
                            unplaced.add(book.getFileName() + "/" + chapter.getFileName());
                        }
                        if (placed.sendsItsPage()) {
                            sent.add(book.getFileName() + "/" + chapter.getFileName());
                        }
                        assertThat(boundary.orElseThrow().page())
                                .as("%s: the apparatus must be in the back half", chapter.getFileName())
                                .isGreaterThan(text.size() / 2);
                    }
                }
            }
        }

        assertThat(files).as("every chapter file of all ten books").isEqualTo(79);
        assertThat(withoutBoundary).as("every file, the custom-encoded one included").isEmpty();
        assertThat(withBoundary).isEqualTo(79);
        // kech202's body is shifted and its "summary" heading on p36 is not; p37-39 are its exercises.
        assertThat(found.get("chem11-part2/kech202.pdf").page()).isEqualTo(36);
        assertThat(unplaced).as("every heading placed, except where the layer carries no prose to place it by")
                .containsExactly("chem11-part2/kech202.pdf");
        // Measured 2026-09-24: 53 of the 78 legible heading pages carry teaching above the heading (one
        // of them, phy12-part2 ch 14 p18, only a figure caption — a wasted call, the safe direction), and
        // 240 of 1,690 pages (14.2%) are apparatus, down from 17.6% when the heading's page went too.
        // kech202 adds its unplaced heading page to the sent and its three exercise pages to the
        // apparatus (2026-09-30).
        assertThat(sent).hasSize(54);
        assertThat(apparatus).isEqualTo(243);
        assertThat(100.0 * apparatus / pages).isBetween(13.5, 15.0);
        // The two closed books, where the page-level skip cost real teaching. bio11's fourteen agree
        // with the day log's independent pymupdf count; phy11-part1's five do not — that count saw two,
        // because chapter 7's layer is private-use encoded (so Example 7.8 above its Summary was
        // invisible to it) and chapter 6 p32 opens both columns with the rotating-chair passage.
        assertThat(sent).filteredOn(chapter -> chapter.startsWith("bio11/")).containsExactly(
                "bio11/kebo102.pdf", "bio11/kebo103.pdf", "bio11/kebo105.pdf", "bio11/kebo106.pdf",
                "bio11/kebo107.pdf", "bio11/kebo108.pdf", "bio11/kebo109.pdf", "bio11/kebo110.pdf",
                "bio11/kebo112.pdf", "bio11/kebo113.pdf", "bio11/kebo114.pdf", "bio11/kebo115.pdf",
                "bio11/kebo116.pdf", "bio11/kebo117.pdf");
        assertThat(sent).filteredOn(chapter -> chapter.startsWith("phy11-part1/")).containsExactly(
                "phy11-part1/keph102.pdf", "phy11-part1/keph104.pdf", "phy11-part1/keph105.pdf",
                "phy11-part1/keph106.pdf", "phy11-part1/keph107.pdf");
    }
}
