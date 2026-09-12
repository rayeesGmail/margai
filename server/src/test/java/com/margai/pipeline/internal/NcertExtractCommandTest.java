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
        page(8, 1);
        page(8, 2);
        page(9, 1);

        Reports writer = new Reports(ReportTest.CLOCK);
        PipelineProperties properties = new PipelineProperties(72, 2);
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
        List<ExtractedPage> written = ExtractJsonl.read(
                store.get(ContentKeys.extract("phy11-part2", BookLanguage.en)));
        assertThat(written).extracting(ExtractedPage::address).containsExactly("8/1", "8/2", "9/1");
        assertThat(written.getFirst().paragraphs()).hasSize(1);
        assertThat(written.getFirst().aiCallId()).isNotNull();
    }

    /** §6.3's "the previous page's tail for paragraph continuity". */
    @Test
    void eachPageCarriesThePreviousPagesTailAndEachChapterStartsFresh() {
        run();

        assertThat(extract.tails).containsExactly(null, "text of 8/1", null);
    }

    /**
     * The D14 blocker: the tail alone cannot tell a model what paragraph number to continue from,
     * so the address travels with it and a new chapter starts from nothing.
     */
    @Test
    void eachPageAlsoCarriesTheAddressThePreviousPageEndedAt() {
        run();

        assertThat(extract.addresses).containsExactly(null, "7.9 ¶1", null);
    }

    /** A resumed run must continue the numbering too, not restart it at the first uncalled page. */
    @Test
    void aResumedRunCarriesTheAddressFromThePageAlreadyInTheJsonl() {
        commandLine.execute("ncert", "extract", "--book", "phy11-part2", "--chapters", "8", "--pages", "1",
                "--inputs", inputs.toString(), "--reports", reports.toString());
        extract.addresses.clear();

        run();

        assertThat(extract.calls).contains("8/2");
        assertThat(extract.addresses).containsExactly("7.9 ¶1", null);
    }

    @Test
    void aSecondRunCallsForNothing() {
        run();
        extract.calls.clear();

        assertThat(run()).isZero();

        assertThat(extract.calls).isEmpty();
        assertThat(out.toString()).contains("| 3 | 0 | 3 | 3 |");
    }

    @Test
    void redoCallsAgainForPagesAlreadyDone() {
        run();
        extract.calls.clear();

        assertThat(commandLine.execute("ncert", "extract", "--book", "phy11-part2", "--redo",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(extract.calls).containsExactly("8/1", "8/2", "9/1");
    }

    @Test
    void aPageWithNoParagraphsIsStillRecordedAsRead() {
        extract.empty.add("8/2");

        run();
        extract.calls.clear();
        run();

        assertThat(extract.calls).isEmpty();
        List<ExtractedPage> written = ExtractJsonl.read(
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

    private void page(int chapter, int page) {
        store.put(ContentKeys.page("phy11-part2", BookLanguage.en, (short) chapter, page),
                ("page image " + chapter + "/" + page).getBytes(StandardCharsets.UTF_8), "image/png");
    }

    /** Stands in for the model: one paragraph per page, named so the tail is checkable. */
    static final class RecordingExtract implements NcertPageExtractor {

        final List<String> calls = new ArrayList<>();
        final List<String> tails = new ArrayList<>();
        final List<String> addresses = new ArrayList<>();
        final List<String> empty = new ArrayList<>();
        final List<String> lowConfidence = new ArrayList<>();

        @Override
        public AiResponse<NcertPage> read(String bookTitle, short chapter, int page, ImagePart image,
                PreviousPage previous, AiCallContext ctx) {
            String address = chapter + "/" + page;
            calls.add(address);
            tails.add(previous == null ? null : previous.tail());
            addresses.add(previous == null ? null : previous.section() + " ¶" + previous.paraNo());
            List<NcertPage.Paragraph> paragraphs = empty.contains(address) ? List.of()
                    : List.of(new NcertPage.Paragraph("7.9", 1, "text of " + address, false, List.of()));
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
