package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert load} over a JSONL written by hand: a paragraph that straddles a page break is
 * loaded as one paragraph with both pages recorded, a page of pure figure counts against coverage
 * without becoming a row, and the report carries the per-chapter and per-book coverage that
 * PLAN D15's ✅ asks for.
 */
class NcertLoadCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final RecordingLoad imports = new RecordingLoad();
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), """
                books:
                  - code: phy11-part1
                    subject: physics
                    class_level: 11
                    part: 1
                    title_en: "Physics Part-I, Textbook for Class XI"
                    edition_year: 2022
                    source:
                      en: source/ncert/2022-ed/en/phy11-part1/
                    chapters:
                      - {no: 7, en: keph107.pdf}
                """);

        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertLoadCommand.class) {
                    return cls.cast(new NcertLoadCommand(store, imports, writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    /** The prompt gives a continuation the number it already had, so both halves share an address. */
    @Test
    void aParagraphStraddlingAPageBreakIsLoadedAsOneParagraph() {
        jsonl(
                page(7, 1, "0.95", paragraph("7.1", 1, "The first paragraph."),
                        paragraph("7.1", 2, "A sentence that runs on")),
                page(7, 2, "0.80", paragraph("7.1", 2, "and finishes on the next page."),
                        paragraph("7.2", 1, "A new section.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).hasSize(3);
        NcertParagraphRow straddling = imports.rows.get(1);
        assertThat(straddling.address()).isEqualTo("ch 7 §7.1 ¶2");
        assertThat(straddling.text()).isEqualTo("A sentence that runs on and finishes on the next page.");
        assertThat(straddling.extraction().pages()).containsExactly(1, 2);
        assertThat(straddling.extraction().confidence()).isEqualByComparingTo("0.80");
    }

    /** A figure page is read correctly and yields nothing; that is text yield, not lost coverage. */
    @Test
    void aFigureOnlyPageLowersTextYieldNotCoverage() {
        imports.renderedPagesAnswer = 4;
        jsonl(
                page(7, 1, "0.95", paragraph("7.1", 1, "Text.")),
                page(7, 2, "0.99"),
                page(7, 3, "0.95", paragraph("7.2", 1, "More text.")),
                page(7, 4, "0.99"));

        run();

        assertThat(imports.rows).hasSize(2);
        assertThat(out.toString())
                .contains("| chapter | pages extracted | pages with text | paragraphs | text yield |")
                .contains("| 7 | 4 | 2 | 2 | 50.0% |")
                .contains("| pages rendered | pages extracted | pages with text | paragraphs | coverage |")
                .contains("| 4 | 4 | 2 | 2 | 100.0% |");
    }

    /**
     * The figure the founder reads must divide by what was rendered: an extraction that stopped a
     * third of the way in reported itself complete before this (spec-auditor, D14).
     */
    @Test
    void coverageDividesByTheRenderedPagesAndSaysSoWhenPagesAreMissing() {
        imports.renderedPagesAnswer = 12;
        jsonl(page(7, 1, "0.95", paragraph("7.1", 1, "Text.")),
                page(7, 2, "0.95", paragraph("7.1", 2, "More.")),
                page(7, 3, "0.95", paragraph("7.1", 3, "Still more.")));

        run();

        assertThat(out.toString())
                .contains("| 12 | 3 | 3 | 3 | 25.0% |")
                .contains("INCOMPLETE: 9 rendered pages are not in the extraction");
    }

    @Test
    void coverageIsUnavailableUntilRenderHasRecordedThePageCount() {
        imports.renderedPagesAnswer = null;
        jsonl(page(7, 1, "0.95", paragraph("7.1", 1, "Text.")));

        run();

        assertThat(out.toString())
                .contains("| unknown | 1 | 1 | 1 | — |")
                .contains("coverage unavailable: ncert_books.pages_en is not set");
    }

    /**
     * The failure mode the blocker created: a model that restarts numbering at 1 on every page
     * collides two different paragraphs at one address. Merging them silently is what the
     * 20-paragraph spot check would not catch, so the run stops and names both pages.
     */
    @Test
    void twoDifferentParagraphsAtOneAddressFailTheRun() {
        imports.renderedPagesAnswer = 3;
        jsonl(page(7, 1, "0.95", paragraph("7.1", 1, "The first paragraph.")),
                page(7, 2, "0.95", paragraph("7.1", 2, "Second page, second paragraph.")),
                page(7, 3, "0.95", paragraph("7.1", 1, "Third page restarting at one.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("ch 7 §7.1 ¶1 is claimed by page 1 and page 3")
                .contains("cannot be one paragraph continuing across a page break")
                .contains("ncert extract --redo --chapters 7 --pages 1,3");
        assertThat(imports.rows).isNull();
    }

    @Test
    void aSectionFromAnotherChapterFailsTheRun() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", paragraph("12.4", 1, "A section from the wrong chapter.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("section '12.4' belongs to chapter 12, not to chapter 7")
                .contains("the page was read as the wrong chapter");
        assertThat(imports.rows).isNull();
    }

    @Test
    void aSectionThatIsNotASectionNumberFailsTheRun() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", paragraph("Gravitation", 1, "A heading, not a number.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("section 'Gravitation' is not a printed section number");
    }

    @Test
    void aParagraphNumberBelowOneFailsTheRun() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", paragraph("7.1", 0, "Numbered from zero.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("§7.1 has paragraph number 0");
    }

    @Test
    void figureRefsAndEquationsSurviveTheJoin() {
        jsonl(page(7, 1, "0.95",
                new NcertPage.Paragraph("7.9", 1, "First half with", false, List.of("Fig. 7.9"))),
                page(7, 2, "0.95",
                        new NcertPage.Paragraph("7.9", 1, "an equation E = mc^2.", true, List.of("Table 7.1"))));

        run();

        NcertParagraphRow row = imports.rows.getFirst();
        assertThat(row.text()).isEqualTo("First half with an equation E = mc^2.");
        assertThat(row.hasEquations()).isTrue();
        assertThat(row.figureRefs()).containsExactly("Fig. 7.9", "Table 7.1");
    }

    @Test
    void theEditionBeingLoadedIsPassedThrough() {
        jsonl(page(7, 1, "0.95", paragraph("7.1", 1, "Text.")));

        run();

        assertThat(imports.language).isEqualTo(BookLanguage.en);
        assertThat(imports.book).isEqualTo("phy11-part1");
    }

    @Test
    void anUnextractedBookFailsTheRunAndSaysWhatToDo() {
        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("no extraction at extract/phy11-part1/en.jsonl")
                .contains("run `ncert extract` first");
        assertThat(imports.rows).isNull();
    }

    private int run() {
        return commandLine.execute("ncert", "load", "--book", "phy11-part1",
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    private void jsonl(ExtractedPage... pages) {
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.en), ExtractJsonl.write(List.of(pages)),
                "application/jsonl");
    }

    private static ExtractedPage page(int chapter, int page, String confidence, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) chapter, page, new BigDecimal(confidence), UUID.randomUUID(),
                List.of(paragraphs));
    }

    private static NcertPage.Paragraph paragraph(String section, int paraNo, String text) {
        return new NcertPage.Paragraph(section, paraNo, text, false, List.of());
    }

    /** Records what the command hands over and answers as a first clean load would. */
    static final class RecordingLoad extends PipelineCommandTest.RecordingImport {

        List<NcertParagraphRow> rows;
        String book;
        BookLanguage language;

        @Override
        public NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
            this.book = bookCode;
            this.language = language;
            this.rows = new ArrayList<>(rows);
            Map<Short, Integer> perChapter = new java.util.TreeMap<>();
            rows.forEach(row -> perChapter.merge(row.chapterNo(), 1, Integer::sum));
            return new NcertLoadReport(rows.size(), 0, 0, perChapter, List.of());
        }
    }
}
