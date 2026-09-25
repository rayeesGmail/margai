package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.find(pages);

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

        assertThat(ChapterApparatus.find(pages).orElseThrow().heading()).isEqualTo("SUMMARY");
        assertThat(ChapterApparatus.find(pages).orElseThrow().page()).isEqualTo(3);
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

        assertThat(ChapterApparatus.find(pages).orElseThrow().page()).isEqualTo(5);
    }

    @Test
    void aChapterWithNoApparatusHeadingSendsEveryPage() {
        List<String> pages = List.of(prose("7.1 ONE"), prose("7.2 TWO"), prose("7.3 THREE"), prose("7.4 FOUR"));

        assertThat(ChapterApparatus.find(pages)).isEmpty();
    }

    /**
     * The fail-safe direction: a text layer we cannot read must not be used to decide what to skip,
     * because skipping a page we misread would drop real teaching. One file in the corpus is like
     * this, and it gets every page sent.
     */
    @Test
    void anIllegibleTextLayerYieldsNoBoundary() {
        List<String> garbled = List.of(
                "LVRPHULVP DQG WKH VWUXFWXUH RI PDWWHU LQ WKH ILUVW FKDSWHU RI WKLV ERRN",
                "VXPPDUB WKH IROORZLQJ TXHVWLRQV DUH IRU SUDFWLFH DQG UHYLVLRQ RI WKH XQLW",
                "SUMMARY");

        assertThat(ChapterApparatus.find(garbled)).isEmpty();
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
     * half, the one file whose text layer is garbled correctly refusing to answer, and every heading
     * placed on its page so the teaching above it is sent.
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
                        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.find(text)
                                .map(found -> found.placedIn(pdf));
                        if (boundary.isEmpty()) {
                            withoutBoundary.add(book.getFileName() + "/" + chapter.getFileName());
                            continue;
                        }
                        withBoundary++;
                        ChapterApparatus.Boundary placed = boundary.orElseThrow();
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
        assertThat(withoutBoundary)
                .as("only the one file whose text layer is custom-encoded may go undetected")
                .containsExactly("chem11-part2/kech202.pdf");
        assertThat(withBoundary).isEqualTo(78);
        assertThat(unplaced).as("every heading the text layer finds, the glyph positions place").isEmpty();
        // Measured 2026-09-24: 53 of the 78 heading pages carry teaching above the heading (one of
        // them, phy12-part2 ch 14 p18, only a figure caption — a wasted call, the safe direction), and
        // 240 of 1,690 pages (14.2%) are apparatus, down from 17.6% when the heading's page went too.
        assertThat(sent).hasSize(53);
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
