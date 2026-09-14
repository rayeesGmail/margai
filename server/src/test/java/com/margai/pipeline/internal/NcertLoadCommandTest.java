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
 * {@code ncert load} over a JSONL written by hand: paragraph numbers are assigned here, per
 * section in reading order across pages (v3, D15); a paragraph that straddles a page break is
 * loaded as one paragraph with both pages recorded; a page of pure figure counts against coverage
 * without becoming a row; and the report carries the per-chapter and per-book coverage that
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

    /** The model labels sections and the loader counts: ¶1, ¶2, ¶3 per section, across pages. */
    @Test
    void paragraphsAreNumberedPerSectionInReadingOrderAcrossPages() {
        jsonl(page(7, 1, "0.95", p("7", "Text above the first heading."), p("7.1", "First."), p("7.1", "Second.")),
                page(7, 2, "0.95", p("7.1", "Third, on the next page."), p("7.2", "A new section."), p("7.2", "Its second.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address).containsExactly(
                "ch 7 §7 ¶1", "ch 7 §7.1 ¶1", "ch 7 §7.1 ¶2", "ch 7 §7.1 ¶3", "ch 7 §7.2 ¶1", "ch 7 §7.2 ¶2");
    }

    /** The second page's first paragraph is flagged, so both halves become one row at one address. */
    @Test
    void aParagraphStraddlingAPageBreakIsLoadedAsOneParagraph() {
        jsonl(
                page(7, 1, "0.95", p("7.1", "The first paragraph."), p("7.1", "A sentence that runs on")),
                page(7, 2, "0.80", continuing("7.1", "and finishes on the next page."), p("7.2", "A new section.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).hasSize(3);
        NcertParagraphRow straddling = imports.rows.get(1);
        assertThat(straddling.address()).isEqualTo("ch 7 §7.1 ¶2");
        assertThat(straddling.text()).isEqualTo("A sentence that runs on and finishes on the next page.");
        assertThat(straddling.extraction().pages()).containsExactly(1, 2);
        assertThat(straddling.extraction().confidence()).isEqualByComparingTo("0.80");
        assertThat(imports.rows.get(2).address()).isEqualTo("ch 7 §7.2 ¶1");
    }

    /**
     * The model once mislabelled one paragraph mid-section and then returned to the section; a
     * counter that restarted at 1 would have given two paragraphs one address. A section keeps
     * counting wherever it resumes, so an address can never be claimed twice.
     */
    @Test
    void aSectionResumingAfterAnotherKeepsCounting() {
        jsonl(page(7, 1, "0.95", p("7.1", "One."), p("7.2", "Aside."), p("7.1", "Two.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address)
                .containsExactly("ch 7 §7.1 ¶1", "ch 7 §7.2 ¶1", "ch 7 §7.1 ¶2");
    }

    /** A figure page is read correctly and yields nothing; that is text yield, not lost coverage. */
    @Test
    void aFigureOnlyPageLowersTextYieldNotCoverage() {
        imports.renderedPagesAnswer = 4;
        jsonl(
                page(7, 1, "0.95", p("7.1", "Text.")),
                page(7, 2, "0.99"),
                page(7, 3, "0.95", p("7.2", "More text.")),
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
        jsonl(page(7, 1, "0.95", p("7.1", "Text.")),
                page(7, 2, "0.95", p("7.1", "More.")),
                page(7, 3, "0.95", p("7.1", "Still more.")));

        run();

        assertThat(out.toString())
                .contains("| 12 | 3 | 3 | 3 | 25.0% |")
                .contains("INCOMPLETE: 9 rendered pages are not in the extraction");
    }

    @Test
    void coverageIsUnavailableUntilRenderHasRecordedThePageCount() {
        imports.renderedPagesAnswer = null;
        jsonl(page(7, 1, "0.95", p("7.1", "Text.")));

        run();

        assertThat(out.toString())
                .contains("| unknown | 1 | 1 | 1 | — |")
                .contains("coverage unavailable: ncert_books.pages_en is not set");
    }

    /** A paragraph may legitimately run from page 10 to page 12 across a full-page figure. */
    @Test
    void aParagraphContinuesAcrossAnInterveningFigurePage() {
        imports.renderedPagesAnswer = 3;
        jsonl(page(7, 1, "0.95", p("7.1", "A sentence that runs on")),
                page(7, 2, "0.99"),
                page(7, 3, "0.95", continuing("7.1", "and finishes after the figure.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).hasSize(1);
        assertThat(imports.rows.getFirst().text())
                .isEqualTo("A sentence that runs on and finishes after the figure.");
        assertThat(imports.rows.getFirst().extraction().pages()).containsExactly(1, 3);
    }

    /**
     * A page missing from the JSONL is not a figure page. The pipeline reaches that state by
     * design — an interrupted render, a targeted `--redo --pages`, a resumed extract over a
     * different `--chapters` — and assuming a hole was blank would concatenate two unrelated
     * paragraphs silently, which is the original blocker in another guise (spec-auditor, D14).
     */
    @Test
    void anAbsentPageBetweenTwoHalvesIsRefusedNotAssumedBlank() {
        imports.renderedPagesAnswer = 3;
        jsonl(page(7, 1, "0.95", p("7.1", "The first half runs on")),
                page(7, 3, "0.95", continuing("7.1", "and claims to finish it.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("page 3 says its first paragraph (§7.1) continues the previous page")
                .contains("page 2 is not in the extraction, so whether it broke the paragraph is unknown")
                .contains("ncert extract --redo --chapters 7 --pages 1,3");
        assertThat(imports.rows).isNull();
    }

    /** A continuation attaches to the nearest page that carried text, whatever the model meant. */
    @Test
    void aContinuationJoinsTheNearestTextBearingPage() {
        imports.renderedPagesAnswer = 3;
        jsonl(page(7, 1, "0.95", p("7.1", "The first paragraph.")),
                page(7, 2, "0.95", p("7.1", "The second runs on")),
                page(7, 3, "0.95", continuing("7.1", "and finishes here.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text)
                .containsExactly("The first paragraph.", "The second runs on and finishes here.");
    }

    /** The flag says "the rest of the previous paragraph"; a new heading says it is not. The loader refuses rather than guesses. */
    @Test
    void aContinuationIntoADifferentSectionIsRefused() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95", p("7.1", "The end of section 7.1.")),
                page(7, 2, "0.95", continuing("7.2", "flagged as continuing but under a new heading.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("the previous page ended in §7.1, not §7.2")
                .contains("ncert extract --redo --chapters 7 --pages 1,2");
        assertThat(imports.rows).isNull();
    }

    @Test
    void aContinuationOnTheFirstPageOfAChapterIsRefused() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", continuing("7.1", "nothing precedes this in the chapter.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("no paragraph precedes it in chapter 7")
                .contains("ncert extract --redo --chapters 7 --pages 1");
    }

    /**
     * Every refusal, not the first. The first full-book load stopped at the earliest offender,
     * which would have had the founder re-extract two pages, re-load, and meet the next one — a
     * model call and three minutes each time. Nothing is written either way (D14).
     */
    @Test
    void everyRefusalIsNamedAtOnceWithOneRedoPerChapter() {
        imports.renderedPagesAnswer = 5;
        jsonl(page(7, 1, "0.95", p("7.1", "The first paragraph of the section")),
                page(7, 3, "0.95", continuing("7.1", "across a page that is absent")),
                page(7, 4, "0.95", p("7.2", "A new section")),
                page(7, 5, "0.95", continuing("7.3", "flagged into yet another section.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("2 page(s) claim a continuation that cannot be one")
                .contains("page 3 says its first paragraph (§7.1) continues the previous page")
                .contains("page 5 says its first paragraph (§7.3) continues the previous page")
                // One command per chapter, naming every page that needs re-extracting.
                .contains("ncert extract --redo --chapters 7 --pages 1,3,4,5");
        assertThat(imports.rows).isNull();
    }

    /**
     * Pages 61–62 of Chapter 4, and 108–109 of Chapter 6: an Example's question ends the page and
     * its Answer opens the next, and the model flagged the Answer as a continuation. A label is a
     * fact the book prints, not a typographic judgement, so the loader clears the flag instead of
     * refusing — and says so in the report, because a silent repair of model output is not one.
     */
    @Test
    void anAnswerOpeningThePageIsANewParagraphAndReported() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95", p("7.1", "What is the tension in the string?")),
                page(7, 2, "0.95", continuing("7.1", "Answer As the string is inextensible, both move together."),
                        p("7.1", "Thus the equation for the motion of the trolley follows.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::paraNo).containsExactly((short) 1, (short) 2, (short) 3);
        assertThat(imports.rows.get(1).text()).startsWith("Answer");
        assertThat(out.toString())
                .contains("## page-break repairs to the model's continuation flags")
                .contains("page 2 opens with \"Answer\"")
                .contains("flag is cleared");
    }

    /**
     * Pages 3–4 of Chapter 1: "…namely four." ends the page and the paragraph runs on, flush left,
     * after the full stop. A test that refused this as "the first half is a finished sentence"
     * was wrong on the first real book, and the join must go through.
     */
    @Test
    void aParagraphRunningOnAfterAFullStopIsJoined() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95", p("7.1", "All these numbers have four significant figures, namely four.")),
                page(7, 2, "0.95", continuing("7.1", "This shows that the location of decimal point is of no consequence.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).hasSize(1);
        assertThat(imports.rows.getFirst().text())
                .isEqualTo("All these numbers have four significant figures, namely four. "
                        + "This shows that the location of decimal point is of no consequence.");
    }

    /** Validating a stripped section while keying on the raw one split a section in two. */
    @Test
    void aSectionIsNormalisedOnceSoWhitespaceCannotSplitIt() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95", p("7.1", "First half")),
                page(7, 2, "0.95", continuing(" 7.1 ", "and second half.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).hasSize(1);
        assertThat(imports.rows.getFirst().section()).isEqualTo("7.1");
        assertThat(imports.rows.getFirst().text()).isEqualTo("First half and second half.");
    }

    @Test
    void aRefusalNamesTheChapterThePageAndTheRealJsonlKey() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 4, "0.95", p("12.4", "Wrong chapter.")));

        run();

        // The report's own header names the book; the refusal names the edition's file, the
        // chapter and the page — never the literal "<lang>" of the key template.
        assertThat(out.toString()).contains("ncert extract --redo --chapters 7 --pages 4")
                .contains("en.jsonl")
                .doesNotContain("<lang>");
    }

    @Test
    void anExtractionHoldingMorePagesThanWereRenderedIsFlaggedNotReportedOverAHundred() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95", p("7.1", "One.")),
                page(7, 2, "0.95", p("7.2", "Two.")),
                page(7, 3, "0.95", p("7.3", "Three.")));

        run();

        assertThat(out.toString()).contains("100% (stale: 3 extracted of 2 rendered)");
    }

    @Test
    void aSectionFromAnotherChapterFailsTheRun() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", p("12.4", "A section from the wrong chapter.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString())
                .contains("section '12.4' belongs to chapter 12, not to chapter 7")
                .contains("the page was read as the wrong chapter");
        assertThat(imports.rows).isNull();
    }

    @Test
    void aSectionThatIsNotASectionNumberFailsTheRun() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95", p("Gravitation", "A heading, not a number.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("section 'Gravitation' is not a printed section number");
    }

    @Test
    void figureRefsAndEquationsSurviveTheJoin() {
        imports.renderedPagesAnswer = 2;
        jsonl(page(7, 1, "0.95",
                new NcertPage.Paragraph("7.9", "First half with", false, List.of("Fig. 7.9"))),
                page(7, 2, "0.95",
                        new NcertPage.Paragraph("7.9", "an equation E = mc^2.", true, List.of("Table 7.1"))));

        run();

        NcertParagraphRow row = imports.rows.getFirst();
        assertThat(row.text()).isEqualTo("First half with an equation E = mc^2.");
        // Computed in Java from the joined text: the equation arrived on the second page, and a
        // paragraph is still a paragraph with an equation wherever its halves fell (D14).
        assertThat(row.hasEquations()).isTrue();
        assertThat(row.figureRefs()).containsExactly("Fig. 7.9", "Table 7.1");
    }

    /** The rows the load deleted are named under their own heading, so a corpus event's losses are on the record. */
    @Test
    void theAddressesTheLoadDeletedAreReported() {
        imports.orphansAnswer = List.of("ch 7 §7.3 ¶23", "ch 7 §7.3 ¶24");
        jsonl(page(7, 1, "0.95", p("7.1", "Text.")));

        run();

        assertThat(out.toString())
                .contains("## addresses in the database this extraction no longer carried — deleted")
                .contains("- ch 7 §7.3 ¶23\n- ch 7 §7.3 ¶24");
    }

    @Test
    void theEditionBeingLoadedIsPassedThrough() {
        jsonl(page(7, 1, "0.95", p("7.1", "Text.")));

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
                List.of(paragraphs), null);
    }

    private static NcertPage.Paragraph p(String section, String text) {
        return new NcertPage.Paragraph(section, text, false, List.of());
    }

    private static NcertPage.Paragraph continuing(String section, String text) {
        return new NcertPage.Paragraph(section, text, true, List.of());
    }

    /** Records what the command hands over and answers as a first clean load would. */
    static final class RecordingLoad extends PipelineCommandTest.RecordingImport {

        List<NcertParagraphRow> rows;
        String book;
        BookLanguage language;
        List<String> orphansAnswer = List.of();

        @Override
        public NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
            this.book = bookCode;
            this.language = language;
            this.rows = new ArrayList<>(rows);
            Map<Short, Integer> perChapter = new java.util.TreeMap<>();
            rows.forEach(row -> perChapter.merge(row.chapterNo(), 1, Integer::sum));
            return new NcertLoadReport(rows.size(), 0, 0, perChapter, orphansAnswer);
        }
    }
}
