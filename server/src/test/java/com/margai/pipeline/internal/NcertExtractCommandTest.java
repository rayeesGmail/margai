package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Usage;
import com.margai.ai.tasks.NcertPage;
import com.margai.ai.tasks.NcertPageExtractor;
import com.margai.ai.tasks.PreviousPage;
import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert extract} over a rendered two-chapter book with a recording task in place of the
 * model: every page is called once with the previous page's tail, the JSONL is one line per page
 * in book order, a second run calls for nothing, {@code --redo} calls again, a page with no
 * paragraphs is still recorded as read, and the report carries the ledger's cost line.
 */
class NcertExtractCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final RecordingExtract extract = new RecordingExtract();
    private final StubSpend spend = new StubSpend();
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), """
                books:
                  - code: phy11-part2
                    subject: physics
                    class_level: 11
                    part: 2
                    title_en: "Physics Part-II, Textbook for Class XI"
                    edition_year: 2023
                    source:
                      en: source/ncert/2022-ed/en/phy11-part2/
                    chapters:
                      - {no: 8, en: keph201.pdf}
                      - {no: 9, en: keph202.pdf}
                """);
        // The source PDFs: extract reads their text layer to find where the apparatus starts. These
        // carry prose and no Summary, so no boundary is found and every page is sent — which is what
        // the tests below assume unless they replace the source themselves.
        store.put("source/ncert/2022-ed/en/phy11-part2/keph201.pdf",
                pdfWithText("7.1 the first section of the chapter", "7.2 the second section of it",
                        "7.3 the third section of the chapter"), "application/pdf");
        store.put("source/ncert/2022-ed/en/phy11-part2/keph202.pdf",
                pdfWithText("8.1 the first section of the chapter", "8.2 the second section of it"),
                "application/pdf");
        page(8, 1);
        page(8, 2);
        page(9, 1);

        Reports writer = new Reports(ReportTest.CLOCK);
        PipelineProperties properties = new PipelineProperties(72, 2, 1, "claude-sonnet-5", 100);
        CommandLine.IFactory siblings =
                PipelineCommandTest.siblingFactory(new PipelineCommandTest.RecordingImport(), writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertExtractCommand.class) {
                    return cls.cast(new NcertExtractCommand(store, extract, spend, properties, writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    @Test
    void callsEveryPageOnceAndWritesTheJsonlInBookOrder() {
        assertThat(run()).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "9/1");
        List<ExtractedPage> written = ExtractJsonl.read(ContentKeys.extract("phy11-part2", BookLanguage.en),
                store.get(ContentKeys.extract("phy11-part2", BookLanguage.en)));
        assertThat(written).extracting(ExtractedPage::address).containsExactly("8/1", "8/2", "9/1");
        assertThat(written.getFirst().paragraphs()).hasSize(1);
        assertThat(written.getFirst().aiCallId()).isNotNull();
    }

    /** §6.3's "the previous page's tail for paragraph continuity". */
    @Test
    void eachPageCarriesThePreviousPagesTailAndEachChapterStartsFresh() {
        run();

        assertThat(extract.addresses).containsExactly(null, "7.9", null);
    }

    /**
     * The section travels with the tail so a page with no heading keeps its section, and a new
     * chapter starts from nothing. Through v2 the paragraph number travelled too; since v3 the
     * loader counts, so there is no number to carry (D15).
     */
    @Test
    void eachPageAlsoCarriesTheSectionThePreviousPageEndedIn() {
        run();

        assertThat(extract.addresses).containsExactly(null, "7.9", null);
    }

    /**
     * A figure page must not reset the state: it has no text of its own, so it carries the
     * running section across and drops only the tail. Without this the next page would have to
     * guess its section — through v2 it also restarted the numbering, the original blocker,
     * reintroduced by its own first fix.
     */
    @Test
    void aTextFreePageCarriesTheAddressAcrossAndDropsOnlyTheTail() {
        page(8, 3);
        extract.empty.add("8/2");

        assertThat(run()).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "8/3", "9/1");
        assertThat(extract.addresses).containsExactly(null, "7.9", "7.9", null);
    }

    /** A resumed run must carry the section and tail forward too, not start the first uncalled page cold. */
    @Test
    void aResumedRunCarriesTheAddressFromThePageAlreadyInTheJsonl() {
        commandLine.execute("ncert", "extract", "--book", "phy11-part2", "--chapters", "8", "--pages", "1",
                "--inputs", inputs.toString(), "--reports", reports.toString());
        extract.addresses.clear();

        run();

        assertThat(extract.calls).contains("8/2");
        assertThat(extract.addresses).containsExactly("7.9", null);
    }

    @Test
    void aSecondRunCallsForNothing() {
        run();
        extract.calls.clear();

        assertThat(run()).isZero();

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("| 3 | 0 | 3 | 0 | 3 |");
    }

    /**
     * The remedy a load refusal prints is `--redo --chapters C --pages N`. If a `--pages`-filtered
     * page did not advance the state, that command would call page N with no section and no tail,
     * the model would guess, and the founder would pay to reproduce the same refusal
     * (spec-auditor, D14).
     */
    @Test
    void aTargetedRedoStillCarriesTheAddressFromTheSkippedPages() {
        run();
        extract.calls.clear();
        extract.addresses.clear();

        assertThat(commandLine.execute("ncert", "extract", "--book", "phy11-part2", "--redo",
                "--chapters", "8", "--pages", "2",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(extract.calls).containsExactly("8/2");
        assertThat(extract.addresses).containsExactly("7.9");
    }

    @Test
    void redoCallsAgainForPagesAlreadyDone() {
        run();
        extract.calls.clear();

        assertThat(commandLine.execute("ncert", "extract", "--book", "phy11-part2", "--redo",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "9/1");
    }

    /** A degree sign not after a number is the text layer's τ copied through; the report names it whether or not a layer was fed. */
    @Test
    void aStrayDegreeSignIsFlaggedInTheReport() {
        extract.degree.add("8/2");

        run();

        assertThat(out.toString())
                .contains("## notation to adjudicate")
                .contains("ch 8 p2 §7.9 #1: a degree sign not after a number");
    }

    @Test
    void aPageWithNoParagraphsIsStillRecordedAsRead() {
        extract.empty.add("8/2");

        run();
        extract.calls.clear();
        run();

        assertThat(extract.calls).isEmpty();
        List<ExtractedPage> written = ExtractJsonl.read(ContentKeys.extract("phy11-part2", BookLanguage.en),
                store.get(ContentKeys.extract("phy11-part2", BookLanguage.en)));
        assertThat(written).extracting(ExtractedPage::address).containsExactly("8/1", "8/2", "9/1");
        assertThat(written.get(1).paragraphs()).isEmpty();
    }

    @Test
    void lowConfidencePagesAreListedForTheFounder() {
        extract.lowConfidence.add("9/1");

        run();

        assertThat(out.toString())
                .contains("## low-confidence pages (below 0.80)")
                .contains("ch 9 page 1 — confidence 0.40");
    }

    @Test
    void theReportCarriesTheLedgersCostLine() {
        run();

        assertThat(out.toString())
                .contains("## cost (from the ai_calls ledger)")
                .contains("| calls | input | output | cache read | cache write | cost |")
                .contains("| 3 | 9000 | 3000 | 1200 | 0 | ₹4.41 |");
        assertThat(spend.asked).hasSize(1).allMatch(id -> id.startsWith("pipeline-ncert-extract-"));
    }

    /** An interrupted render leaves gaps; the pages that exist are the pages extracted. */
    @Test
    void aPartlyRenderedChapterIsExtractedForThePagesThatExist() {
        store.objects.remove(ContentKeys.page("phy11-part2", BookLanguage.en, (short) 8, 1));
        page(8, 5);

        assertThat(run()).isZero();

        assertThat(extract.calls).containsExactly("8/2", "8/5", "9/1");
    }

    /**
     * The apparatus is never sent. Two prompt revisions failed to stop the model reading NCERT's
     * chapter-numbered exercises as sections; not showing it the page is the version that cannot
     * fail (D14).
     */
    @Test
    void theEndOfChapterApparatusIsNeverSentAndIsRecordedAsSkipped() throws IOException {
        store.put("source/ncert/2022-ed/en/phy11-part2/keph201.pdf",
                pdfWithText("7.1 the first section of the chapter as we have written it here",
                        "7.2 the second section of the chapter as it is printed on the page",
                        "SUMMARY",
                        "7.1 Answer the following questions that are set for the student to do"),
                "application/pdf");
        page(8, 3);
        page(8, 4);

        assertThat(run()).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "9/1");
        assertThat(out.toString())
                .contains("## end-of-chapter apparatus (never sent to the model)")
                .contains("| 8 | page 3 | SUMMARY | 2 |");

        List<ExtractedPage> written = ExtractJsonl.read(ContentKeys.extract("phy11-part2", BookLanguage.en),
                store.get(ContentKeys.extract("phy11-part2", BookLanguage.en)));
        assertThat(written).extracting(ExtractedPage::address).contains("8/3", "8/4");
        assertThat(written.stream().filter(ExtractedPage::wasSkipped)).hasSize(2)
                .allSatisfy(page -> assertThat(page.skipped()).isEqualTo("apparatus from SUMMARY"));
    }

    /**
     * The page's own text layer travels with the image as the character authority (D14 §6.1
     * reversal): the characters vision fumbles — 1 against l, a prime, a leading minus, an
     * exponent — are already in it, because these books are digitally typeset.
     */
    @Test
    void aLegibleTextLayerIsSentWithEveryPage() {
        assertThat(run()).isZero();

        assertThat(out.toString()).contains("checked: 3 of the 3 page(s) called this run");
        assertThat(extract.pageTexts).hasSize(3).doesNotContainNull();
        assertThat(extract.pageTexts.get(0)).contains("7.1 the first section of the chapter");
        assertThat(extract.pageTexts.get(1)).contains("7.2 the second section of it");
        assertThat(extract.pageTexts.get(2)).contains("8.1 the first section of the chapter");
        assertThat(out.toString())
                .contains("## text layer (authoritative for characters where it is legible)")
                .contains("| 8 | fed as the character authority |");
    }

    /** Hindi, and one Chemistry file, have no usable layer — the image is then the only source. */
    @Test
    void anIllegibleTextLayerIsWithheldAndSaidSoInTheReport() throws IOException {
        store.put("source/ncert/2022-ed/en/phy11-part2/keph201.pdf", garbledPdf(3), "application/pdf");

        assertThat(run()).isZero();

        assertThat(extract.pageTexts.subList(0, 2)).containsOnlyNulls();
        assertThat(out.toString()).contains("| 8 | withheld: illegible |")
                // The count is the point: chapter 9's layer is legible and chapter 8's is not, so
                // one page of the three was checked. Without it the section reads "none" and a
                // founder takes that for a clean bill of health (spec-auditor, D14).
                .contains("checked: 1 of the 3 page(s) called this run (the rest had no usable text layer)");
    }

    /** A chapter whose text layer cannot be read sends every page: skipping blind would drop teaching. */
    @Test
    void aChapterWithNoDetectableApparatusSendsEveryPage() {
        assertThat(run()).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "9/1");
        assertThat(out.toString()).contains("not found: every page is sent");
    }

    @Test
    void anUnrenderedChapterFailsTheRunAndSaysWhatToDo() {
        store.objects.keySet().removeIf(key -> key.startsWith("pages/phy11-part2/en/9/"));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("chapter 9 of 'phy11-part2' has no rendered pages")
                .contains("run `ncert render` first");
    }

    private int run() {
        return commandLine.execute("ncert", "extract", "--book", "phy11-part2",
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    /**
     * A PDF whose text layer says something, so {@link ChapterApparatus} has a page to read. Each
     * argument is one page; the filler makes the page legible English by the measured standard.
     */
    private static byte[] pdfWithText(String... pageTexts) throws IOException {
        return pdf(java.util.Arrays.stream(pageTexts)
                .map(text -> List.of(text,
                        "This is the text of the page and it is written in the words that we use,",
                        "with the same of and to in a that as it for on by an which be are this."))
                .toList());
    }

    /**
     * The {@code chem11-part2/kech202.pdf} case: a custom-encoded font whose text extracts as a
     * Caesar-shifted alphabet. No filler — a page of this has enough words to be judged and none of
     * them are English, which is exactly why {@link PdfTextLayer} scores it near zero.
     */
    private static byte[] garbledPdf(int pages) throws IOException {
        List<String> page = List.of(
                "LVRPHULVP DQG WKH VWUXFWXUH RI PDWWHU LQ WKH ILUVW FKDSWHU RI WKLV ERRN",
                "DQG WKH ZRUGV WKDW DUH SULQWHG KHUH DUH QRW WKH ZRUGV WKH IRQW FODLPV");
        return pdf(java.util.stream.IntStream.range(0, pages).mapToObj(index -> page).toList());
    }

    private static byte[] pdf(List<List<String>> pages) throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument()) {
            for (List<String> lines : pages) {
                org.apache.pdfbox.pdmodel.PDPage pdPage = new org.apache.pdfbox.pdmodel.PDPage();
                document.addPage(pdPage);
                try (var content = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, pdPage)) {
                    content.beginText();
                    content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(
                            org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 11);
                    content.setLeading(14);
                    content.newLineAtOffset(50, 740);
                    for (int line = 0; line < lines.size(); line++) {
                        if (line > 0) {
                            content.newLine();
                        }
                        content.showText(lines.get(line));
                    }
                    content.endText();
                }
            }
            var bytes = new java.io.ByteArrayOutputStream();
            document.save(bytes);
            return bytes.toByteArray();
        }
    }

    private void page(int chapter, int page) {
        store.put(ContentKeys.page("phy11-part2", BookLanguage.en, (short) chapter, page),
                ("page image " + chapter + "/" + page).getBytes(StandardCharsets.UTF_8), "image/png");
    }

    /** Stands in for the model: one paragraph per page, named so the tail is checkable. */
    static final class RecordingExtract implements NcertPageExtractor {

        final List<String> calls = new ArrayList<>();
        final List<String> addresses = new ArrayList<>();
        final List<Integer> imageCounts = new ArrayList<>();
        final List<String> pageTexts = new ArrayList<>();
        final List<String> empty = new ArrayList<>();
        final List<String> degree = new ArrayList<>();
        final List<String> lowConfidence = new ArrayList<>();

        @Override
        public AiResponse<NcertPage> read(String bookTitle, short chapter, int page, List<ImagePart> images,
                String pageText, PreviousPage previous, AiCallContext ctx) {
            String address = chapter + "/" + page;
            calls.add(address);
            imageCounts.add(images.size());
            pageTexts.add(pageText);
            addresses.add(previous == null ? null : previous.section());
            String text = degree.contains(address) ? "Where ° is the restoring couple of " + address : "text of " + address;
            List<NcertPage.Paragraph> paragraphs = empty.contains(address) ? List.of()
                    : List.of(new NcertPage.Paragraph("7.9", text, false, List.of()));
            BigDecimal confidence = lowConfidence.contains(address) ? new BigDecimal("0.40") : new BigDecimal("0.95");
            return new AiResponse<>(new NcertPage(paragraphs, confidence), Usage.none(), "fake",
                    Duration.ZERO, UUID.randomUUID());
        }
    }

    /** Stands in for the ledger read, with a bill the report must print verbatim. */
    static final class StubSpend implements AiSpend {

        final List<String> asked = new ArrayList<>();

        @Override
        public RunSpend of(String requestId) {
            asked.add(requestId);
            return new RunSpend(3, 441, new Usage(9000, 3000, 1200, 0));
        }
    }
}
