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
        assertThat(boundary.orElseThrow().firstPage()).isEqualTo(4);
        assertThat(boundary.orElseThrow().heading()).isEqualTo("SUMMARY");
        assertThat(boundary.orElseThrow().covers(3)).isFalse();
        assertThat(boundary.orElseThrow().covers(4)).isTrue();
        assertThat(boundary.orElseThrow().covers(6)).isTrue();
    }

    /** Chemistry prints it in title case, and its exercises carry no heading at all. */
    @Test
    void theMatchIsCaseInsensitive() {
        List<String> pages = List.of(prose("1.1 Types of Solutions"), prose("1.2 Expressing Concentration"),
                prose("Summary"), prose("1.5 A solution of glucose in water is labelled"));

        assertThat(ChapterApparatus.find(pages).orElseThrow().heading()).isEqualTo("SUMMARY");
        assertThat(ChapterApparatus.find(pages).orElseThrow().firstPage()).isEqualTo(3);
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

        assertThat(ChapterApparatus.find(pages).orElseThrow().firstPage()).isEqualTo(5);
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
     * half, and the one file whose text layer is garbled correctly refusing to answer.
     */
    @Test
    void everyEnglishChapterOfEverySubjectHasABoundaryInItsBackHalf() throws IOException {
        assumeTrue(Files.isDirectory(NCERT), "founder's NCERT PDFs not on this machine");

        int files = 0;
        int withBoundary = 0;
        int pages = 0;
        int apparatus = 0;
        List<String> withoutBoundary = new java.util.ArrayList<>();

        try (var books = Files.list(NCERT)) {
            for (Path book : books.filter(Files::isDirectory).sorted().toList()) {
                try (var chapters = Files.list(book)) {
                    for (Path chapter : chapters
                            .filter(path -> path.getFileName().toString().matches("[a-z]{4}\\d{3}\\.pdf"))
                            .sorted().toList()) {
                        files++;
                        List<String> text = PdfTextLayer.pages(Files.readAllBytes(chapter));
                        pages += text.size();
                        Optional<ChapterApparatus.Boundary> boundary = ChapterApparatus.find(text);
                        if (boundary.isEmpty()) {
                            withoutBoundary.add(book.getFileName() + "/" + chapter.getFileName());
                            continue;
                        }
                        withBoundary++;
                        apparatus += text.size() - boundary.orElseThrow().firstPage() + 1;
                        assertThat(boundary.orElseThrow().firstPage())
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
        assertThat(100.0 * apparatus / pages).isBetween(15.0, 20.0);
    }
}
