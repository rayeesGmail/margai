package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
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

    /** phy11-part2 ch 8, the second Hindi book's staged chapter: स्प्रिंग on p1 and विरुद्ध on p8 read as printed (D16). */
    @Test
    void theSecondBooksStagedChapterReadsItsSpellings() throws IOException {
        Path khph201 = HINDI.resolve("phy11-part2/khph201.pdf");
        assumeTrue(Files.exists(khph201), "founder's NCERT PDFs not on this machine");

        List<HindiLayer.Page> pages = HindiLayer.pages(Files.readAllBytes(khph201));

        assertThat(pages.get(0).devanagari()).contains("कुण्डलित स्प्रिंग");
        assertThat(pages.get(7).devanagari()).contains("विरुद्ध");
    }

    /**
     * Marks the fonts draw with no width, read with their letters (D16): the nukta of फ़िजिओलॉजी set above the
     * line once cut it into three lines (phy11-part2 ch 9 p5); the below-base ट of ch 12 p6's संघट्ट, past its
     * ट's width, read as a word of its own; ch 8 p5's display प्रतिबल split at the rakar drawn inside its short
     * i; a half श् with no width still opens its word after a space (ch 11 p12's एक शृंखला); and an e-sign or a
     * ू typed one letter late is read on the letter it is drawn on (phy11-part1 ch 6 p27's सेकेंड, ch 7 p11's न्यून).
     */
    @Test
    void marksDrawnWithNoWidthAreReadWithTheirLetters() throws IOException {
        Path part2 = HINDI.resolve("phy11-part2");
        Path part1 = HINDI.resolve("phy11-part1");
        assumeTrue(Files.exists(part2.resolve("khph202.pdf")) && Files.exists(part1.resolve("khph106.pdf")),
                "founder's NCERT PDFs not on this machine");

        assertThat(devanagari(part2.resolve("khph202.pdf"), 5)).contains(nfc("(फ़िजिओलॉजी)"));
        assertThat(devanagari(part2.resolve("khph205.pdf"), 6)).contains("संघट्ट करके").doesNotContain("संघट ्ट");
        assertThat(devanagari(part2.resolve("khph201.pdf"), 5)).contains("प्रतिबल =");
        assertThat(devanagari(part2.resolve("khph204.pdf"), 12)).contains("एक शृंखला");
        assertThat(devanagari(part2.resolve("khph204.pdf"), 14)).contains("है, किंतु");
        assertThat(devanagari(part1.resolve("khph106.pdf"), 27)).contains("सेकेंड");
        assertThat(devanagari(part1.resolve("khph107.pdf"), 11)).contains("न्यून");
    }

    /**
     * The same rules on the glyphs as those pages draw them (x, y, width, size, text), so they hold without the
     * books: the raised nukta, the below-base ट past its ट, the rakar inside the short i, the half श् with no
     * width after a space, the e-sign and the ू typed a letter late, and a justified line's ू between its letters.
     */
    @Test
    void theLayersRulesForMarksHoldOnTheGlyphsAsDrawn() {
        assertThat(line(
                g(458.60, 458.40, 5.77, "("), g(464.33, 458.40, 2.81, "f"), g(467.10, 458.40, 3.20, "I"),
                g(470.28, 456.24, 0.07, "+"), g(470.28, 458.40, 2.37, "k"), g(472.60, 458.40, 2.82, "Q"),
                g(475.37, 458.40, 2.81, "f"), g(478.13, 458.40, 7.02, "t"), g(485.10, 458.40, 7.93, "v"),
                g(492.98, 458.40, 2.37, "k"), g(495.29, 458.40, 0.07, "s"), g(495.31, 458.40, 7.36, "y"),
                g(502.61, 458.40, 2.37, "k"), g(504.93, 458.40, 0.07, "W"), g(504.95, 458.40, 7.02, "t"),
                g(511.92, 458.40, 2.81, "h"), g(514.67, 458.40, 5.77, ")")))
                .isEqualTo(nfc("(फ़िजिओलॉजी)"));
        assertThat(line(
                g(82.44, 695.76, 6.79, "l"), g(89.17, 695.76, 0.07, "a"), g(89.18, 695.76, 3.91, "?"),
                g(93.04, 695.76, 2.37, "k"), g(95.40, 695.76, 5.25, "V"), g(102.84, 695.76, 0.07, "ð"),
                g(102.84, 695.76, 5.20, " "), g(106.82, 695.76, 8.27, "d"), g(115.07, 695.76, 3.93, "j"),
                g(118.98, 695.76, 6.08, "o"), g(125.05, 695.76, 0.07, "s"), g(125.09, 695.76, 2.82, "Q")))
                .isEqualTo("संघट्ट करके");
        assertThat(line(
                g(71.40, 581.52, 5.43, "i"), g(76.92, 581.52, 2.79, "f"), g(76.92, 581.52, 0.05, "z"),
                g(79.80, 581.52, 5.35, "r"), g(85.08, 581.52, 5.95, "c"), g(91.08, 581.52, 7.32, "y")))
                .isEqualTo("प्रतिबल");
        assertThat(line(
                g(375.05, 537.60, 5.64, ","), g(380.66, 537.60, 8.27, "d"), g(391.80, 537.60, 5.20, " "),
                g(396.64, 537.60, 0.07, "Ü"), g(396.67, 537.60, 2.37, "k"), g(399.03, 537.60, 0.07, "à"),
                g(399.06, 537.60, 5.85, "["), g(404.88, 537.60, 2.37, "k"), g(407.21, 537.60, 7.36, "y"),
                g(414.53, 537.60, 2.37, "k")))
                .isEqualTo("एक शृंखला");
        assertThat(line(
                g(139.20, 582.24, 6.87, "l"), g(145.93, 582.24, 6.16, "o"), g(145.93, 582.24, 0.07, "s"),
                g(151.93, 582.24, 0.07, "a"), g(151.93, 582.24, 0.07, "s"), g(151.93, 582.24, 2.86, "Q"),
                g(154.81, 582.24, 6.58, "M")))
                .isEqualTo("सेकेंड");
        assertThat(line(
                g(94.56, 188.88, 1.97, "U"), g(96.24, 188.88, 3.80, ";"), g(100.08, 188.88, 3.42, "u"),
                g(100.08, 188.88, 0.04, "w")))
                .isEqualTo("न्यून");
        assertThat(line(
                g(285.56, 696.96, 5.20, "n"), g(292.02, 696.96, 0.07, "w"), g(293.35, 696.96, 3.93, "j"),
                g(298.53, 696.96, 2.81, "h")))
                .isEqualTo("दूरी");
    }

    private static PdfLayout.Glyph g(double x, double y, double width, String text) {
        return new PdfLayout.Glyph(x, y, width, 13.0, "Walkman-Chanakya905Normal", text);
    }

    private static String line(PdfLayout.Glyph... glyphs) {
        return HindiLayer.page(List.of(glyphs)).devanagari();
    }

    private static String devanagari(Path chapter, int page) throws IOException {
        return HindiLayer.pages(Files.readAllBytes(chapter)).get(page - 1).devanagari();
    }

    private static String nfc(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFC);
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
