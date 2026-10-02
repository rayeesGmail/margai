package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The Hindi books' text layer read as a check (SPEC §12.2, DECISIONS 2026-10-02): the Chanakya runs decoded,
 * the Bookman, Times and Symbol runs — digits, Latin, symbols, bullets — kept as the Unicode they already are.
 * Against the books themselves, as the decoder was proved (D16).
 */
class HindiLayerTest {

    private static final Path HINDI = Path.of("..", "ncert", "2022-ed", "hi");
    private static final Path KHPH101 = HINDI.resolve("phy11-part1/khph101.pdf");

    @Test
    void aPageReadsAsTheWordsItPrints() throws IOException {
        assumeTrue(Files.exists(KHPH101), "founder's NCERT PDFs not on this machine");

        List<HindiLayer.Page> pages = HindiLayer.pages(Files.readAllBytes(KHPH101));

        assertThat(pages).hasSize(12);
        assertThat(pages.get(8).devanagari()).contains("मात्रक एवं मापन").contains("विमीय");
        assertThat(pages.get(9).devanagari()).contains("सारांश");
    }

    /**
     * What Phase B and C found and no Devanagari check could: p4 prints seven bullets that Opus 5.5 dropped,
     * and p5 carries the × of "3 × 10^-3 m" in its layer though the page does not draw it (D16).
     */
    @Test
    void theUnicodeSpansCarryTheBulletsAndTheUndrawnTimesSign() throws IOException {
        assumeTrue(Files.exists(KHPH101), "founder's NCERT PDFs not on this machine");

        List<HindiLayer.Page> pages = HindiLayer.pages(Files.readAllBytes(KHPH101));

        assertThat(pages.get(3).unicode().chars().filter(c -> c == '•').count()).isEqualTo(7);
        assertThat(pages.get(4).unicode()).contains("×");
        assertThat(pages.get(3).unicode()).doesNotContain("lkjk").as("no Chanakya glyph text among the Unicode runs");
    }

    /** All 79 Hindi chapter files: no Latin letter is left in a decoded Chanakya run — every glyph is mapped. */
    @Test
    void everyHindiChapterDecodesWithNoGlyphLeftOver() throws IOException {
        assumeTrue(Files.exists(KHPH101), "founder's NCERT PDFs not on this machine");

        int files = 0;
        List<String> leftOver = new ArrayList<>();
        try (var books = Files.list(HINDI)) {
            for (Path book : books.filter(Files::isDirectory).sorted().toList()) {
                try (var chapters = Files.list(book)) {
                    for (Path chapter : chapters
                            .filter(path -> path.getFileName().toString().matches("[a-z]{4}\\d{3}\\.pdf"))
                            .sorted().toList()) {
                        files++;
                        List<HindiLayer.Page> pages = HindiLayer.pages(Files.readAllBytes(chapter));
                        for (int page = 0; page < pages.size(); page++) {
                            String decoded = pages.get(page).decodedOnly();
                            if (decoded.chars().anyMatch(c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                                leftOver.add(book.getFileName() + "/" + chapter.getFileName() + " p" + (page + 1));
                            }
                        }
                    }
                }
            }
        }

        assertThat(files).isEqualTo(79);
        assertThat(leftOver).isEmpty();
    }
}
