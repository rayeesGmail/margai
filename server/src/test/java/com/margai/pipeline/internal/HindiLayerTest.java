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

    /**
     * The ृ of प्रकृति on p2 and p7 sits inside the letter before it, so PDFBox hands it over folded into that
     * letter as a combining grave; before the decoder read the mark back the page had no प्रकृति at all and
     * the check named the row's word as not on it (D16, the first Hindi book's 27 such lines).
     */
    @Test
    void aVowelSignThePdfReaderFoldsIntoItsLetterIsStillRead() throws IOException {
        assumeTrue(Files.exists(KHPH101), "founder's NCERT PDFs not on this machine");

        List<HindiLayer.Page> pages = HindiLayer.pages(Files.readAllBytes(KHPH101));

        assertThat(pages.get(1).devanagari()).contains("प्रकृति");
        assertThat(pages.get(6).devanagari()).contains("प्रकृति");
        assertThat(pages).allSatisfy(page -> assertThat(page.devanagari().chars()
                .filter(c -> c >= 0x0300 && c <= 0x036F).count()).as("combining marks left in a page").isZero());
    }

    /**
     * ch 3 p1 types पढ़ेंगे's nukta after its vowel sign, and ch 4 p16 sets पट्टियों's ट्ट in ChanakyaThin inside
     * a Walkman word: both pages once read without the word, which the check then named (D16).
     */
    @Test
    void aWordTypedOutOfOrderOrInASecondFaceIsRead() throws IOException {
        Path khph103 = HINDI.resolve("phy11-part1/khph103.pdf");
        Path khph104 = HINDI.resolve("phy11-part1/khph104.pdf");
        assumeTrue(Files.exists(khph104), "founder's NCERT PDFs not on this machine");

        assertThat(HindiLayer.pages(Files.readAllBytes(khph103)).get(0).devanagari()).contains("पढ़ेंगे");
        assertThat(HindiLayer.pages(Files.readAllBytes(khph104)).get(15).devanagari()).contains("पट्टियों");
    }

    /**
     * The noise the first Hindi book's check carried, from the way the layer was read, gone (D16): a decimal
     * point set in the Chanakya face read inside its number (ch 3 p2's 35.6, once 35 . 6); a condensed line's
     * space glyphs kept as spaces (ch 5 p11, once one word); the example marker ▶, which PDFBox gives as a
     * bullet, dropped (ch 4 p4) while Wingdings' list bullets are read as bullets (ch 4 p5's five); the Symbol
     * face's ∞, which came through as ¥ (ch 7 p9); and dv over dt found in the Latin read by baseline (ch 2 p4).
     */
    @Test
    void theLayerReadsNumbersSpacesMarkersAndSymbolsAsPrinted() throws IOException {
        assumeTrue(Files.exists(KHPH101), "founder's NCERT PDFs not on this machine");
        Path book = HINDI.resolve("phy11-part1");

        assertThat(page(book, 3, 2).devanagari()).contains("35.6 °C").doesNotContain("35 . 6");
        assertThat(page(book, 5, 11).devanagari()).contains("मान लीजिए");
        assertThat(page(book, 4, 4).devanagari()).doesNotContain("•");
        assertThat(page(book, 4, 5).unicode().chars().filter(c -> c == '•').count()).isEqualTo(5);
        assertThat(page(book, 7, 9).devanagari()).contains("∞").doesNotContain("¥");
        assertThat(page(book, 2, 4).latin()).containsPattern("\\bdv\\b").containsPattern("\\bdt\\b");
    }

    private static HindiLayer.Page page(Path book, int chapter, int page) throws IOException {
        return HindiLayer.pages(Files.readAllBytes(book.resolve("khph10" + chapter + ".pdf"))).get(page - 1);
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
