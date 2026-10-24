package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Usage;
import com.margai.ai.tasks.PyqPage;
import com.margai.ai.tasks.PyqPageExtractor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.UnaryOperator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code pyq extract} over a three-page booklet with a recording task in place of the model: every
 * page is called once, a question running onto the next page is joined with the next call told the
 * number it ended inside, the JSONL is one line per page, the draft carries every question with its
 * answer empty, a second run calls for nothing, and nothing is called before the fake client, a
 * VISION model that is not the transcriber, a booklet whose bytes are not the manifest's, a page
 * that renders as blank paper, or a run at another resolution than the paper's pages were drawn at
 * is refused.
 */
class PyqExtractCommandTest {

    private static final String BOOKLET = "source/neet-pyq-sources/2025/test-booklet.pdf";

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    @TempDir
    Path drafts;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final RecordingPyqExtract extract = new RecordingPyqExtract();
    private AiClientInfo client = new AiClientInfo("anthropic", List.of("ledger", "breaker"));
    private CommandLine commandLine;
    private byte[] booklet;

    @BeforeEach
    void setUp() throws IOException {
        booklet = pdf(true, true, false);
        store.put(BOOKLET, booklet, "application/pdf");
        register("hi-en", "1-2", PyqExtractCommand.sha256(booklet));
        extract.pages.put(1, new PyqPage(List.of(new PyqPage.Heading("PHYSICS", 1)),
                List.of(item(1, false, "First question", 4), item(2, false, "Second question, which runs on", 2)),
                new BigDecimal("0.97")));
        extract.pages.put(2, new PyqPage(List.of(new PyqPage.Heading("CHEMISTRY", 3)),
                List.of(item(2, true, "", 2, 3), item(3, false, "Third question", 4), item(4, false, "Fourth question", 4)),
                new BigDecimal("0.95")));
        extract.imageKept = page -> store.exists(ContentKeys.pyqPage("neet-2025-45", page));
        build();
    }

    @Test
    void readsEveryPageJoinsTheQuestionThatCrossesAndWritesTheDraft() throws IOException {
        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(extract.calls).containsExactly(1, 2);
        assertThat(extract.inQuestion).containsExactly(null, 2);
        assertThat(extract.bilingual).containsExactly(true, true);
        assertThat(extract.pageTexts).containsExactly(null, null);
        assertThat(PyqExtractJsonl.read("k", store.get(ContentKeys.pyqExtract("neet-2025-45")))).hasSize(2);

        PaperFile draft = PaperFileJson.read(drafts.resolve("neet-2025-45.draft.json"));
        assertThat(draft.questions()).hasSize(4);
        PaperFile.Question second = draft.questions().get(1);
        assertThat(second.options()).extracting(option -> option.key()).containsExactly("1", "2", "3", "4");
        assertThat(second.extraction().pages()).containsExactly(1, 2);
        assertThat(second.extraction().model()).isEqualTo("claude-opus-5-5");
        assertThat(second.extraction().promptVersion()).isEqualTo("pyq_extract.v1");
        assertThat(draft.questions()).allSatisfy(question -> assertThat(question.answer()).isNull());
        assertThat(out.toString()).contains("- read: 1 paper(s) in the register")
                .contains("| physics | — | 2 | 2 | 0 |").contains("| chemistry | — | 2 | 2 | 0 |")
                .contains("4.41").contains("answers empty until NTA's key is filled in at review");
    }

    /** The picture each call read is kept before the call, and its line proves which picture it was. */
    @Test
    void keepsEveryPageImageTheModelReadAndRecordsItsHash() throws IOException {
        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(extract.imageKeptAtCall).containsExactly(true, true);
        for (PyqExtractedPage line : lines()) {
            String key = "pages/pyq/neet-2025-45/00" + line.page() + ".png";
            byte[] stored = store.get(key);
            assertThat(line.image().key()).isEqualTo(key);
            assertThat(line.image().sha256()).isEqualTo(PyqExtractCommand.sha256(stored));
            assertThat(line.image().dpi()).isEqualTo(72);
            assertThat(line.image().renderer()).startsWith("PDFBox 3.").contains(", Java ");
            assertThat(line.image().tiles()).isEqualTo(1);
            assertThat(line.image().rerendered()).isFalse();
            assertThat(Files.readAllBytes(drafts.resolve("pages/neet-2025-45/00" + line.page() + ".png"))).isEqualTo(stored);
            assertThat(out.toString()).contains("| " + line.page() + " | " + line.image().sha256() + " | kept with this run's call |");
        }
    }

    /** Code 45's case: lines with no image key, no image stored — kept with no model call, and a rerun proves them. */
    @Test
    void imagesOnlyKeepsTheImagesOfPagesReadBeforeAndCallsNoModel() throws IOException {
        code45State(null);
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));
        build();
        Map<Integer, String> drawn = drawn();

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(extract.calls).isEmpty();
        assertThat(lines()).hasSize(2).allSatisfy(line -> {
            assertThat(line.image().sha256()).isEqualTo(drawn.get(line.page()))
                    .isEqualTo(PyqExtractCommand.sha256(store.get(line.image().key())));
            assertThat(line.image().rerendered()).isTrue();
        });
        assertThat(Files.readAllBytes(drafts.resolve("pages/neet-2025-45/002.png")))
                .isEqualTo(store.get("pages/pyq/neet-2025-45/002.png"));
        assertThat(out.toString()).contains("kept now — re-rendered").contains("2 image(s) kept now, 0 checked");

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(out.toString()).contains("the same bytes as the image its line records (re-rendered)")
                .contains("0 image(s) kept now, 2 checked").contains("(unchanged)");
    }

    @Test
    void imagesOnlyRefusesALineWhoseImageIsMissingFromTheStore() throws IOException {
        Map<Integer, String> drawn = drawn();
        code45State(page -> new PyqExtractedPage.Image(ContentKeys.pyqPage("neet-2025-45", page), drawn.get(page), 72,
                PdfPageRenderer.version(), 1, false));

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("page 1: the image in the store at pages/pyq/neet-2025-45/001.png is missing");
    }

    @Test
    void imagesOnlyRefusesAPaperNotReadYet() {
        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("no page of neet-2025-45 has been read yet");
    }

    @Test
    void imagesOnlyRefusesAPageThatDrawsDifferentlyFromTheImageItsLineRecords() {
        run();
        rewrite(line -> line.page() == 1 ? line.withImage(new PyqExtractedPage.Image(line.image().key(), "0".repeat(64),
                line.image().dpi(), line.image().renderer(), line.image().tiles(), false)) : line);

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("page 1 draws differently now").contains("could not prove");
    }

    /** A stop mid-run keeps and reports the images kept before it; the refused page keeps none. */
    @Test
    void imagesOnlyRefusesAStoredImageThatIsNotTheBookletsDrawing() {
        code45State(null);
        store.put("pages/pyq/neet-2025-45/002.png", new byte[] {1, 2, 3}, "image/png");

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("page 2: the image in the store").contains("is not what the booklet draws now")
                .contains("page images handled before the run stopped").contains("rewritten with the 1 image record(s) kept");
        assertThat(lines()).extracting(line -> line.image() != null).containsExactly(true, false);
    }

    /**
     * One paper at one resolution (DECISIONS 2026-10-22): a run that would keep a page drawn at another dpi is
     * refused before any call, naming the pages to read again; with a paper already at two resolutions — a change
     * that stopped part-way — reading just those pages again with --change-resolution brings it back to one.
     */
    @Test
    void refusesToKeepAPageDrawnAtAnotherResolution() {
        run();
        rewrite(line -> line.page() == 1 ? line.withImage(new PyqExtractedPage.Image(line.image().key(), line.image().sha256(),
                150, line.image().renderer(), line.image().tiles(), false)) : line);
        extract.calls.clear();

        assertThat(run("--redo", "--pages", "2")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("pages drawn at another resolution than this run's 72 dpi, 1 tile(s): "
                        + "{150 dpi, 1 tile(s)=[1]}")
                .doesNotContain("keep the paper's").contains("--redo --change-resolution --pages 1");

        assertThat(run("--redo", "--change-resolution", "--pages", "1")).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(extract.calls).containsExactly(1);
        assertThat(lines()).allSatisfy(line -> assertThat(line.image().dpi()).isEqualTo(72));
    }

    /** Not even a --redo of every page reads a paper again at another resolution unless --change-resolution says so. */
    @Test
    void aFullRedoAtAnotherResolutionNeedsChangeResolution() {
        run();
        rewrite(line -> line.withImage(new PyqExtractedPage.Image(line.image().key(), line.image().sha256(), 150,
                line.image().renderer(), line.image().tiles(), false)));
        extract.calls.clear();

        assertThat(run("--redo")).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("{150 dpi, 1 tile(s)=[1, 2]}")
                .contains("keep the paper's with -Dmargai.pipeline.render-dpi=150 -Dmargai.pipeline.page-tiles=1");

        assertThat(run("--redo", "--change-resolution", "--pages", "1")).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(extract.calls).isEmpty();

        assertThat(run("--redo", "--change-resolution")).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(extract.calls).containsExactly(1, 2);
        assertThat(lines()).allSatisfy(line -> assertThat(line.image().dpi()).isEqualTo(72));
    }

    /** The tiling counts as the resolution does: a page cut into other bands than this run's is refused too. */
    @Test
    void refusesToKeepAPageDrawnAtAnotherTiling() {
        run();
        rewrite(line -> line.page() == 2 ? line.withImage(new PyqExtractedPage.Image(line.image().key(), line.image().sha256(),
                line.image().dpi(), line.image().renderer(), 2, false)) : line);
        extract.calls.clear();

        assertThat(run("--redo", "--pages", "1")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("{72 dpi, 2 tile(s)=[2]}").contains("--redo --change-resolution --pages 2")
                .doesNotContain("keep the paper's");
    }

    @Test
    void changeResolutionGoesWithRedo() {
        assertThat(run("--change-resolution")).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(run("--images-only", "--change-resolution")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--change-resolution reads pages again, so it goes with --redo");
        assertThat(extract.calls).isEmpty();
    }

    @Test
    void imagesOnlyRefusesAPaperReadAtAnotherResolution() {
        Map<Integer, String> drawn = drawn();
        code45State(page -> new PyqExtractedPage.Image(ContentKeys.pyqPage("neet-2025-45", page), drawn.get(page), 150,
                PdfPageRenderer.version(), 1, false));

        assertThat(run("--images-only")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("{150 dpi, 1 tile(s)=[1, 2]}").contains("one paper is read at one resolution");
    }

    @Test
    void imagesOnlyIsNeverARedo() {
        assertThat(run("--images-only", "--redo")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--images-only calls no model");
    }

    @Test
    void aSecondRunCallsForNothingAndRedoCallsAgain() {
        run();
        extract.calls.clear();

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(extract.calls).isEmpty();

        assertThat(run("--redo", "--pages", "2")).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(extract.calls).containsExactly(2);
        assertThat(extract.inQuestion.getLast()).isEqualTo(2);
    }

    @Test
    void refusesTheFakeClient() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));
        build();

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(store.exists(ContentKeys.pyqExtract("neet-2025-45"))).isFalse();
        assertThat(out.toString()).contains("the AI client is the fake").contains("AI_LIVE=1");
    }

    @Test
    void refusesAVisionTierThatIsNotThePyqTranscriber() {
        extract.model = "claude-haiku-5-5";

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("the vision tier is claude-haiku-5-5, not claude-opus-5-5")
                .contains("visionopus55");
    }

    @Test
    void refusesABookletThatIsNotTheManifestsFile() throws IOException {
        register("hi-en", "1-2", "0".repeat(64));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("the booklet in the store is sha256").contains("not the file the founder checked");
    }

    /** A run stopped mid-booklet keeps every page it paid for and reports the bill, so a rerun resumes. */
    @Test
    void refusesAPageThatRendersAsBlankPaperAndKeepsThePagesBeforeIt() throws IOException {
        register("hi-en", "1-3", PyqExtractCommand.sha256(booklet));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).containsExactly(1, 2);
        assertThat(out.toString()).contains("page 3 of " + BOOKLET + " rendered as blank paper")
                .contains("2 page(s) called this run are kept").contains("cost (from the ai_calls ledger)");
        assertThat(PyqExtractJsonl.read("k", store.get(ContentKeys.pyqExtract("neet-2025-45"))))
                .extracting(PyqExtractedPage::page).containsExactly(1, 2);
    }

    @Test
    void refusesToReadAPageAheadOfThePageBeforeIt() {
        assertThat(run("--pages", "2")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("page 2 would be read before page 1");
    }

    @Test
    void refusesADraftDirectoryInsideTheInputs() {
        int exit = commandLine.execute("pyq", "extract", "--paper", "neet-2025-45", "--inputs", inputs.toString(),
                "--reports", reports.toString(), "--drafts", inputs.resolve("papers").toString());

        assertThat(exit).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("is inside the founder's inputs");
    }

    /** A committed report reads the same on every machine (Report): the draft is named as given, never made absolute. */
    @Test
    void theReportNamesTheDraftAsGiven() {
        Path relative = Path.of("").toAbsolutePath().relativize(drafts);

        int exit = commandLine.execute("pyq", "extract", "--paper", "neet-2025-45", "--inputs", inputs.toString(),
                "--reports", reports.toString(), "--drafts", relative.toString());

        assertThat(exit).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(out.toString()).contains("draft: " + relative.resolve("neet-2025-45.draft.json").normalize() + " (4 questions");
    }

    @Test
    void anEnglishBookletsLegibleTextLayerTravelsWithItsPage() throws IOException {
        register("en", "1-2", PyqExtractCommand.sha256(booklet));
        Files.writeString(inputs.resolve(PyqPaperCommand.FILE), Files.readString(inputs.resolve(PyqPaperCommand.FILE))
                .replace("official_hindi: true", "official_hindi: false"));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(extract.bilingual).containsExactly(false, false);
        assertThat(extract.pageTexts).allSatisfy(text -> assertThat(text).contains("the words that we use"));
    }

    /**
     * Code 45's real state: the JSONL written before images were kept — its lines carry no {@code image}
     * key at all — and no page image in the store; {@code image}, when given, puts a record on each line.
     */
    private void code45State(IntFunction<PyqExtractedPage.Image> image) {
        List<PyqExtractedPage> written = new ArrayList<>();
        for (int page = 1; page <= 2; page++) {
            PyqPage read = extract.pages.get(page);
            written.add(new PyqExtractedPage(page, read.confidence(), UUID.randomUUID(), "claude-opus-5-5", "pyq_extract.v1",
                    read.headings(), read.questions(), image == null ? null : image.apply(page)));
        }
        String jsonl = new String(PyqExtractJsonl.write(written), StandardCharsets.UTF_8).replace(",\"image\":null", "");
        if (image == null) {
            assertThat(jsonl).doesNotContain("\"image\"");
        }
        store.put(ContentKeys.pyqExtract("neet-2025-45"), jsonl.getBytes(StandardCharsets.UTF_8), "application/jsonl");
    }

    /** Each register page's sha256 as the booklet draws it at the test's dpi. */
    private Map<Integer, String> drawn() {
        Map<Integer, String> shas = new HashMap<>();
        new PdfPageRenderer(72).render(booklet, page -> page > 2, (png, page) -> shas.put(page, PyqExtractCommand.sha256(png)));
        return shas;
    }

    private List<PyqExtractedPage> lines() {
        return PyqExtractJsonl.read("k", store.get(ContentKeys.pyqExtract("neet-2025-45")));
    }

    private void rewrite(UnaryOperator<PyqExtractedPage> change) {
        store.put(ContentKeys.pyqExtract("neet-2025-45"), PyqExtractJsonl.write(lines().stream().map(change).toList()),
                "application/jsonl");
    }

    private int run(String... extra) {
        List<String> args = new ArrayList<>(List.of("pyq", "extract", "--paper", "neet-2025-45",
                "--inputs", inputs.toString(), "--reports", reports.toString(), "--drafts", drafts.toString()));
        args.addAll(List.of(extra));
        return commandLine.execute(args.toArray(String[]::new));
    }

    private void build() {
        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(new PipelineCommandTest.RecordingImport(), writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == PyqExtractCommand.class) {
                    return cls.cast(new PyqExtractCommand(store, extract, new NcertExtractCommandTest.StubSpend(), client,
                            properties(), pyq(), writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    static PipelineProperties properties() {
        return new PipelineProperties(72, 10, 1, "claude-sonnet-5", "claude-opus-5", "claude-opus-5-5", 100, 0, 40);
    }

    static PyqProperties pyq() {
        return new PyqProperties("claude-opus-5-5", "claude-sonnet-5-5", 2, new BigDecimal("0.70"), new BigDecimal("0.50"),
                new BigDecimal("0.02"), "papers");
    }

    private void register(String languages, String pages, String sha256) throws IOException {
        Files.writeString(inputs.resolve(PyqPaperCommand.FILE), """
                papers:
                  - code: neet-2025-45
                    exam: NEET
                    year: 2025
                    paper_code: "45"
                    exam_date: 2025-05-04
                    official_hindi: true
                    question_count: 4
                    booklets:
                      - file: %s
                        sha256: %s
                        languages: %s
                        pages: %s
                    sections:
                      - {subject: physics, from: 1, to: 2}
                      - {subject: chemistry, from: 3, to: 4}
                    marking: {correct: 4, wrong: -1, unanswered: 0, scheme_source: booklet p1}
                    key:
                      file: source/neet-pyq-sources/2025/test-key.pdf
                      sha256: %s
                      tier: mirrored-official
                      from: a mirror
                      downloaded: 2026-10-20
                """.formatted(BOOKLET, sha256, languages, pages, "c".repeat(64)));
    }

    static PyqPage.Item item(int number, boolean continues, String stem, int options) {
        return item(number, continues, stem, options, 1);
    }

    /** {@code options} options keyed from {@code first}. */
    static PyqPage.Item item(int number, boolean continues, String stem, int options, int first) {
        List<PyqPage.Option> list = new ArrayList<>();
        for (int key = first; key < first + options; key++) {
            list.add(new PyqPage.Option(String.valueOf(key), "option " + key));
        }
        return new PyqPage.Item(number, continues, stem, list, List.of(), false);
    }

    /** Pages of dense text, or blank paper where {@code withText} is false. */
    static byte[] pdf(boolean... withText) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (boolean text : withText) {
                PDPage page = new PDPage();
                document.addPage(page);
                if (!text) {
                    continue;
                }
                try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                    content.setLeading(15);
                    content.newLineAtOffset(40, 750);
                    for (int line = 0; line < 45; line++) {
                        content.showText("This is the text of the page and it is written in the words that we use, "
                                + "with the same of and to in a that as it for on.");
                        content.newLine();
                    }
                    content.endText();
                }
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            document.save(bytes);
            return bytes.toByteArray();
        }
    }

    static final class RecordingPyqExtract implements PyqPageExtractor {

        final Map<Integer, PyqPage> pages = new HashMap<>();
        final List<Integer> calls = new ArrayList<>();
        final List<Integer> inQuestion = new ArrayList<>();
        final List<Boolean> bilingual = new ArrayList<>();
        final List<String> pageTexts = new ArrayList<>();
        /** Whether the page's image was already in the store when its call came. */
        final List<Boolean> imageKeptAtCall = new ArrayList<>();
        IntPredicate imageKept = page -> false;
        String model = "claude-opus-5-5";

        @Override
        public AiResponse<PyqPage> read(String paper, int page, List<ImagePart> images, boolean bilingual,
                String pageText, Integer inQuestion, AiCallContext ctx) {
            calls.add(page);
            imageKeptAtCall.add(imageKept.test(page));
            this.inQuestion.add(inQuestion);
            this.bilingual.add(bilingual);
            pageTexts.add(pageText);
            PyqPage read = pages.getOrDefault(page, new PyqPage(List.of(), List.of(), new BigDecimal("0.99")));
            return new AiResponse<>(read, new Usage(100, 50, 0, 0), model, Duration.ofMillis(5), UUID.randomUUID());
        }

        @Override
        public String model() {
            return model;
        }

        @Override
        public String promptVersion() {
            return "pyq_extract.v1";
        }
    }
}
