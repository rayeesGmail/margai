package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    /** `ncert verify` judges each page for its own part of a straddling paragraph, so the row says where each part starts. */
    @Test
    void eachRowSaysWhereEachOfItsPagesPartBegins() {
        jsonl(
                page(7, 1, "0.95", p("7.1", "The first paragraph."), p("7.1", "A sentence that runs on ")),
                page(7, 2, "0.80", continuing("7.1", "\nand finishes on the next page."), p("7.2", "A new section.")));

        run();

        assertThat(imports.rows.get(0).extraction().pageStarts()).containsExactly(0);
        NcertParagraphRow straddling = imports.rows.get(1);
        assertThat(straddling.extraction().pageStarts()).containsExactly(0, 24);
        assertThat(straddling.text().substring(24)).isEqualTo("and finishes on the next page.");
    }

    /**
     * A scratch run's draw (D16, the 5.5 validation) is loaded as the model wrote it: the rulings name
     * spans of the canonical draw, so none is applied, and the canonical artefact is not read.
     */
    @Test
    void aTaggedLoadReadsItsOwnArtefactAndAppliesNoCorrections() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: text, transcribed: "r_hat",
                     printed: "r", reason: the page prints the vector}
                """);
        jsonl(page(7, 4, "0.95", p("7.3", "The canonical draw.")));
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.en, "opus55"),
                ExtractJsonl.write(List.of(page(7, 4, "0.95", p("7.3", "F = - G m_1m_2 / |r|^3 r_hat.")))),
                "application/jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--artefact-tag", "opus55",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text).containsExactly("F = - G m_1m_2 / |r|^3 r_hat.");
        assertThat(out.toString()).contains("extract/phy11-part1/en.opus55.jsonl")
                .contains("not applied to a scratch run");
    }

    /**
     * The guard that keeps a trial out of the frozen corpus: every canonical book is embedded, a
     * scratch database never is, so a tagged load into a book with an embedded paragraph is refused.
     */
    @Test
    void aTaggedLoadRefusesABookThatHasEmbeddedParagraphs() {
        imports.embedded = 3;
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.en, "opus55"),
                ExtractJsonl.write(List.of(page(7, 4, "0.95", p("7.3", "A trial draw.")))), "application/jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--artefact-tag", "opus55",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(imports.rows).isNull();
        assertThat(out.toString()).contains("3 embedded paragraph(s)").contains("scratch database");
    }

    /** A row kept for the other edition but stripped of this one's text is named, as a deleted one is. */
    @Test
    void theRowsALoadClearsItsEditionFromAreNamed() {
        imports.clearedAnswer = List.of("ch 7 §7.9 ¶2");
        jsonl(page(7, 1, "0.95", p("7.9", "One paragraph.")));

        assertThat(run()).isZero();

        assertThat(out.toString())
                .contains("## rows the other edition still carries — this edition's text cleared from them")
                .contains("- ch 7 §7.9 ¶2");
    }

    /** The founder's adjudication lands in the load: a correction is applied before numbering, and named. */
    @Test
    void theCorrectionsFileIsAppliedAndEveryCorrectionIsReported() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: text, transcribed: "r_hat",
                     printed: "r", reason: the page prints the vector}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: split, at: "The quotation",
                     reason: indented on the page}
                  - {book: phy11-part1, lang: en, chapter: 7, page: 4, kind: false_positive, printed: "m_2",
                     transcribed: "m_2", reason: the verifier misread}
                """);
        jsonl(page(7, 4, "0.95", p("7.3", "F = - G m_1m_2 / |r|^3 r_hat. The quotation is from Principia.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text)
                .containsExactly("F = - G m_1m_2 / |r|^3 r.", "The quotation is from Principia.");
        assertThat(out.toString())
                .contains("## corrections from ncert-corrections.yaml (founder-adjudicated, each one named)")
                .contains("- ch 7 page 4 §7.3: \"r_hat\" → \"r\" (the page prints the vector)")
                .contains("- ch 7 page 4 §7.3: a new paragraph starts at \"The quotation\" (indented on the page)")
                .contains("rulings on verifier flags that change no text: 1");
    }

    /**
     * The Hindi Physics Part I prints a footnote of §4.8 at the foot of p10 and Example 4.6 at the top of
     * p11; the English has both on p10, the footnote last. Numbered in page order the Hindi footnote sat
     * beside the English Example and the Example beside the footnote (D16). A deferred paragraph waits
     * for the next one its section starts and takes the number after it — and stays on its own page.
     */
    @Test
    void aDeferredFootnoteIsNumberedAfterTheNextParagraphOfItsSection() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 10, kind: defer, at: "* Equilibrium of a body",
                     reason: the other edition numbers the Example first}
                """);
        imports.renderedPagesAnswer = 11;
        jsonl(page(7, 10, "0.95", p("7.8", "Equation (7.11) implies that F = 0."),
                        p("7.8", "* Equilibrium of a body requires more.")),
                page(7, 11, "0.95", p("7.8", "Example 7.6 A mass of 6 kg hangs."), p("7.8", "Answer The free-body diagram.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactly(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.8 ¶1", "Equation (7.11) implies that F = 0."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.8 ¶2", "Example 7.6 A mass of 6 kg hangs."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.8 ¶3", "* Equilibrium of a body requires more."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.8 ¶4", "Answer The free-body diagram."));
        assertThat(imports.rows.get(2).extraction().pages()).containsExactly(10);
        assertThat(out.toString()).contains("- ch 7 page 10 §7.8: the paragraph starting \"* Equilibrium of a body\" "
                + "takes its number after the next paragraph of its section (the other edition numbers the Example first)");
    }

    /** The body paragraph above a deferred footnote still takes the next page's continuation, and that is not a new paragraph for the footnote to follow. */
    @Test
    void aContinuationPassesOverADeferredFootnote() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 11, kind: defer, at: "* We are not considering",
                     reason: the other edition numbers the next paragraph first}
                """);
        imports.renderedPagesAnswer = 12;
        jsonl(page(7, 11, "0.95", p("7.9", "The spring force is proportional to the stretch (for small"),
                        p("7.9", "* We are not considering charged bodies.")),
                page(7, 12, "0.95", continuing("7.9", "displacements)."), p("7.9", "There are four fundamental forces.")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text).containsExactly(
                "The spring force is proportional to the stretch (for small displacements).",
                "There are four fundamental forces.",
                "* We are not considering charged bodies.");
    }

    /**
     * phy11-part2's Hindi starts part (b) of an answer above the radian footnote and runs it onto the next page;
     * the English numbers the footnote first (D16). The move is made on whole rows, after the page break is
     * joined, so (b) keeps its second half and the footnote takes the place before it.
     */
    @Test
    void aMovedFootnoteIsNumberedAfterTheRowItNamesAndTheRowItPassedKeepsItsContinuation() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: move, at: "* The radian",
                     after: "Answer (a)", after_page: 7, reason: the English numbers the footnote before (b)}
                """, page(7, 7, "0.95", p("7.4", "Answer (a) The period is 2 s."), p("7.4", "(b) The speed at the mean"),
                        p("7.4", "* The radian is a ratio.")),
                page(7, 8, "0.95", continuing("7.4", "position is A omega."), p("7.4", "Example 7.5 Next.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Answer (a) The period is 2 s."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "* The radian is a ratio."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶3", "(b) The speed at the mean position is A omega."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶4", "Example 7.5 Next."));
        assertThat(out.toString()).contains("- ch 7 page 7 §7.4: the row starting \"* The radian\" is numbered after the "
                + "row starting \"Answer (a)\" (page 7) (the English numbers the footnote before (b))");
    }

    /** A footnote the Hindi prints before rows the English numbers first waits for them: a move is later as well as earlier. */
    @Test
    void aMovedFootnoteCanBeNumberedLaterThanItIsPrinted() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 6, kind: move, at: "* rad may be dropped",
                     after: "where s(x, t)", after_page: 7, reason: the English numbers it after the next page's rows}
                """, page(7, 6, "0.95", p("7.3", "omega is the angular frequency."), p("7.3", "* rad may be dropped.")),
                page(7, 7, "0.95", p("7.3", "nu is measured in hertz."), p("7.3", "where s(x, t) is the displacement."),
                        p("7.3", "Example 7.2 A wave.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶1", "omega is the angular frequency."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶2", "nu is measured in hertz."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶3", "where s(x, t) is the displacement."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶4", "* rad may be dropped."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶5", "Example 7.2 A wave."));
    }

    /**
     * The numbering's own net under a move: a row it cannot find — which the corrections step refuses first, by
     * name, for a continuation — is refused, never skipped.
     */
    @Test
    void aMoveTheNumberingCannotPlaceIsRefused() {
        List<ExtractedPage> pages = List.of(page(7, 2, "0.95", p("7.2", "Pressure is a scalar.")));

        assertThatThrownBy(() -> NcertLoadCommand.number(pages, "hi.jsonl", Set.of(),
                List.of(new NcertCorrections.Moved((short) 7, 2, 4, 2, 0, "* Nowhere", "Pressure is"))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("1 move correction(s) cannot be placed")
                .hasMessageContaining("the row starting \"* Nowhere\" or the row starting \"Pressure is\" (page 2) is not a row of its own");
    }

    /** A footnote one edition prints a page late, under the next heading, goes back to the section of the row it follows. */
    @Test
    void aMovedRowTakesTheSectionOfTheRowItFollowsAndBothSectionsAreNumberedAgain() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 3, kind: move, at: "* STP means",
                     after: "Pressure is a scalar.", after_page: 2, reason: the English prints it on page 2}
                """, page(7, 2, "0.95", p("7.2", "Pressure is a scalar."), p("7.2", "Example 7.1 Two thigh bones.")),
                page(7, 3, "0.95", p("7.2.1", "Pascal's law."), p("7.2.1", "* STP means standard temperature.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶1", "Pressure is a scalar."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶2", "* STP means standard temperature."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶3", "Example 7.1 Two thigh bones."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2.1 ¶1", "Pascal's law."));
        assertThat(imports.rows.stream().filter(row -> row.text().startsWith("* STP")).findFirst().orElseThrow()
                .extraction().pages()).containsExactly(3);
    }

    /** With nothing after it in its section there is nothing to follow, and the entry is wrong about the page. */
    @Test
    void aDeferredParagraphWithNoLaterParagraphInItsSectionIsRefused() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 10, kind: defer, at: "* Equilibrium of a body",
                     reason: the other edition numbers the Example first}
                """);
        imports.renderedPagesAnswer = 11;
        jsonl(page(7, 10, "0.95", p("7.8", "Equation (7.11) implies that F = 0."),
                        p("7.8", "* Equilibrium of a body requires more.")),
                page(7, 11, "0.95", p("7.9", "A new section begins.")));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("ch 7 page 10: the deferred paragraph starting \"* Equilibrium of a body requires more.\" "
                + "has no later paragraph of §7.8 to follow");
        assertThat(imports.rows).isNull();
    }

    /**
     * A Hindi load puts back the visargas the transcriber wrote as colons before it applies a correction,
     * so an entry is written against the words the rows will show — and it names every one, and every
     * glued colon it could not place (D16).
     */
    @Test
    void aHindiLoadPutsBackAVisargaWrittenAsAColonBeforeTheCorrections() throws IOException {
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
                      hi: source/ncert/2022-ed/hi/phy11-part1/
                    chapters:
                      - {no: 7, en: keph107.pdf, hi: khph107.pdf}
                """);
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 4, kind: split, at: "क्रमशः x तथा y",
                     reason: the English row begins here}
                """);
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.hi), ExtractJsonl.write(List.of(page(7, 4, "0.95",
                p("7.3", "अत: बल F = ma है: क्रमश: x तथा y अक्षों पर।"), p("7.3", "ज्ञात: m = 5 kg")))), "application/jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text)
                .containsExactly("अतः बल F = ma है:", "क्रमशः x तथा y अक्षों पर।", "ज्ञात: m = 5 kg");
        assertThat(out.toString())
                .contains("## a visarga the model wrote as a colon (deterministic, each one named)")
                .contains("- ch 7 p4 §7.3: a visarga written as a colon — अतः, क्रमशः")
                .contains("## a word glued to a colon that the visarga list does not know — adjudicate")
                .contains("- ch 7 p4 §7.3: ज्ञात:");
    }

    /**
     * phy11-part2's Hindi prints "8.5.6" where the English prints 8.5.5 (D16): a section entry puts the
     * section's rows at the English address, across the page break, and numbers them from ¶1 there.
     */
    @Test
    void aHindiSectionTheEnglishNumbersOtherwiseLoadsAtTheEnglishAddress() throws IOException {
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
                      hi: source/ncert/2022-ed/hi/phy11-part1/
                    chapters:
                      - {no: 7, en: keph107.pdf, hi: khph107.pdf}
                """);
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 8, kind: section, at: "जब किसी तार पर",
                     section: "7.5.5", reason: the Hindi prints 7.5.6 after 7.5.4}
                """);
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.hi), ExtractJsonl.write(List.of(
                page(7, 8, "0.95", p("7.5.4", "पार्श्विक विकृति।"), p("7.5.6", "जब किसी तार पर तनन प्रतिबल")),
                page(7, 9, "0.95", continuing("7.5.6", "आक्षेपित किया जाता है।"), p("7.5.6", "यह कार्य संग्रहित होता है।")))),
                "application/jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address).containsExactly(
                "ch 7 §7.5.4 ¶1", "ch 7 §7.5.5 ¶1", "ch 7 §7.5.5 ¶2");
        assertThat(imports.rows.get(1).text()).isEqualTo("जब किसी तार पर तनन प्रतिबल आक्षेपित किया जाता है।");
        assertThat(out.toString()).contains("- ch 7 page 8 §7.5.6: the English numbers it §7.5.5, from \"जब किसी तार पर\" "
                + "on — 3 paragraph(s) (the Hindi prints 7.5.6 after 7.5.4)");
    }

    /** The step is the Hindi edition's: an English load neither runs it nor reports on it. */
    @Test
    void anEnglishLoadHasNoVisargaStep() {
        jsonl(page(7, 4, "0.95", p("7.3", "Note: the force is F = ma.")));

        assertThat(run()).isZero();

        assertThat(out.toString()).doesNotContain("visarga");
    }

    /**
     * The dry run (D16) reads a local copy of the extraction, corrects and numbers as a load does, and hands
     * the rows to the rolled-back preview — never to the load — under a report named for what it is, so a
     * simulation before a reload is the loader itself rather than a copy of it.
     */
    @Test
    void aDryRunReadsALocalExtractionAndWritesNothing() throws IOException {
        Path local = inputs.resolve("en.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 1, "0.95", p("7.1", "First."), p("7.1", "Second.")))));

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--dry-run", "--extraction",
                local.toString(), "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.rows).isNull();
        assertThat(imports.previewed).extracting(NcertParagraphRow::address)
                .containsExactly("ch 7 §7.1 ¶1", "ch 7 §7.1 ¶2");
        assertThat(out.toString()).contains("DRY RUN: nothing is written")
                .contains("a local file read in place of extract/phy11-part1/en.jsonl, not the content store")
                .doesNotContain("content store: ");
        try (var written = Files.list(reports)) {
            assertThat(written.map(file -> file.getFileName().toString()).toList())
                    .singleElement().asString().endsWith("-ncert-load-dry-run.md");
        }
    }

    /** A load of record reads the content store's artefact: a local file or a pairs file is a dry run's. */
    @Test
    void aLocalExtractionOrAPairsFileIsForADryRunOnly() {
        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--extraction", "en.jsonl",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--extraction and --pairs belong to --dry-run");
        assertThat(imports.rows).isNull();
        assertThat(imports.previewed).isNull();
    }

    /** Each mode's misuse is refused by name before anything is read, and nothing is loaded or previewed. */
    @Test
    void aDryRunsOptionsAreRefusedWhereTheyCannotMeanWhatTheySay() throws IOException {
        Path local = inputs.resolve("en.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 1, "0.95", p("7.1", "First.")))));

        assertThat(dryRun("--pairs", inputs.resolve("pairs.jsonl").toString())).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(dryRun("--extraction", local.toString(), "--artefact-tag", "opus55"))
                .isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(dryRun("--extraction", inputs.resolve("missing.jsonl").toString()))
                .isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--pairs sets a Hindi load's rows beside the English ones: give --lang hi")
                .contains("--artefact-tag names another — give one").contains("no local extraction at");
        assertThat(imports.rows).isNull();
        assertThat(imports.previewed).isNull();
    }

    /**
     * The JSONL names neither book nor edition, so a local file is held to what it must be: a page of the
     * chapters asked for, in the edition's script — the English file handed to a Hindi dry run is refused.
     */
    @Test
    void aLocalExtractionMustBeThisEditionsOfTheseChapters() throws IOException {
        hindiEdition();
        Path english = inputs.resolve("en.jsonl");
        Files.write(english, ExtractJsonl.write(List.of(page(7, 4, "0.95", p("7.3", "F = ma.")))));
        Path otherChapter = inputs.resolve("ch8.jsonl");
        Files.write(otherChapter, ExtractJsonl.write(List.of(page(8, 1, "0.95", p("8.1", "एक अनुभाग।")))));

        assertThat(dryRun("--lang", "hi", "--extraction", english.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(dryRun("--lang", "hi", "--extraction", otherChapter.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("the local extraction carries no Devanagari — it is not extract/phy11-part1/hi.jsonl")
                .contains("the local extraction carries no page of chapter(s) [7]");
        assertThat(imports.previewed).isNull();
    }

    /** A dry run reports what the load would do, never what it did; a load of record names its input's hash. */
    @Test
    void aDryRunSaysWhatALoadWouldDoAndALoadNamesItsInputsHash() throws IOException {
        imports.orphansAnswer = List.of("ch 7 §7.1 ¶3");
        Path local = inputs.resolve("en.jsonl");
        byte[] bytes = ExtractJsonl.write(List.of(page(7, 1, "0.95", p("7.1", "First."))));
        Files.write(local, bytes);
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.en), bytes, "application/jsonl");

        assertThat(dryRun("--extraction", local.toString())).isZero();
        assertThat(out.toString()).contains("no longer carried — a load would delete them")
                .doesNotContain("no longer carried — deleted")
                .contains("sha256 " + Report.sha256(bytes) + " — a local file read in place of extract/phy11-part1/en.jsonl");

        assertThat(run()).isZero();
        assertThat(out.toString()).contains("extraction: extract/phy11-part1/en.jsonl sha256 " + Report.sha256(bytes))
                .contains("no longer carried — deleted");
    }

    /**
     * A Hindi load is held to the English rows section by section (D16: English is canonical), and its dry
     * run writes the pairs it would leave — an English row with no Hindi beside it shows as such.
     */
    @Test
    void aHindiDryRunNamesTheSectionsOffOneToOneAndWritesThePairs() throws IOException {
        hindiEdition();
        imports.paragraphsAnswer = List.of(english("7.3", 1, "F = ma."), english("7.3", 2, "Where m is the mass."),
                english("7.4", 1, "A new section."));
        Path local = inputs.resolve("hi.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 4, "0.95",
                p("7.3", "F = ma है। जहाँ m द्रव्यमान है।"), p("7.4", "एक नया अनुभाग।")))));
        Path pairs = inputs.resolve("pairs.jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi", "--dry-run",
                "--extraction", local.toString(), "--pairs", pairs.toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(out.toString()).contains("1 of 2 sections one to one").contains("| 7 | 7.3 | 2 | 1 |")
                .doesNotContain("| 7 | 7.4 |");
        assertThat(Files.readAllLines(pairs)).hasSize(3)
                .first().asString().contains("\"address\":\"ch 7 §7.3 ¶1\"").contains("\"hi\":\"F = ma है।");
        assertThat(Files.readAllLines(pairs).get(1)).contains("\"en\":\"Where m is the mass.\"").doesNotContain("\"hi\"");
    }

    /**
     * phy12-part1's Hindi does not print §3.12's English ¶5 (D16). Numbered in order, the row after it sat beside ¶5
     * and every later row one off; an `unprinted` entry on that row leaves ¶5 to the English, the section is held to
     * the English as one to one, and the dry run names the address and writes it English-only.
     */
    @Test
    void anUnprintedEntryLeavesItsNumberToTheEnglishAndTheSectionPairsOneToOne() throws IOException {
        hindiEdition();
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 4, kind: unprinted, at: "नियम (b)",
                     reason: the Hindi does not print English ¶2}
                """);
        imports.paragraphsAnswer = List.of(english("7.3", 1, "Rule (a)."), english("7.3", 2, "It holds at a point too."),
                english("7.3", 3, "Rule (b)."), english("7.4", 1, "A new section."));
        Path local = inputs.resolve("hi.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 4, "0.95",
                p("7.3", "नियम (a)।"), p("7.3", "नियम (b)।"), p("7.4", "एक नया अनुभाग।")))));
        Path pairs = inputs.resolve("pairs.jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi", "--dry-run",
                "--extraction", local.toString(), "--pairs", pairs.toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.previewed).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactly(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶1", "नियम (a)।"),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.3 ¶3", "नियम (b)।"),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "एक नया अनुभाग।"));
        assertThat(out.toString()).contains("2 of 2 sections one to one")
                .contains("- ch 7 page 4 §7.3: the English paragraph before the row starting \"नियम (b)\" is not printed "
                        + "in this edition — the row takes the number after it (the Hindi does not print English ¶2)")
                .contains("left without Hindi by an `unprinted` entry")
                .contains("- ch 7 §7.3 ¶2");
        assertThat(Files.readAllLines(pairs).get(1)).contains("\"address\":\"ch 7 §7.3 ¶2\"").contains("\"unprinted\":true")
                .contains("\"en\":\"It holds at a point too.\"").doesNotContain("\"hi\"");
        assertThat(Files.readAllLines(pairs).get(2)).contains("\"en\":\"Rule (b).\"").contains("\"hi\":\"नियम (b)।\"")
                .doesNotContain("\"unprinted\"");
    }

    /**
     * bio11's Hindi does not print §4.2.11.7's last English paragraph (2026-10-17): no row follows it, so the entry
     * names the row before it with `after`, the number after that row stays the English row's alone, and the section
     * is held to the English as one to one.
     */
    @Test
    void anUnprintedEntryAfterItsRowLeavesTheSectionsLastNumberToTheEnglish() throws IOException {
        hindiEdition();
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 4, kind: unprinted, at: "नियम (b)", after: true,
                     reason: the Hindi does not print English ¶3}
                """);
        imports.paragraphsAnswer = List.of(english("7.3", 1, "Rule (a)."), english("7.3", 2, "Rule (b)."),
                english("7.3", 3, "It holds at a point too."), english("7.4", 1, "A new section."));
        Path local = inputs.resolve("hi.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 4, "0.95",
                p("7.3", "नियम (a)।"), p("7.3", "नियम (b)।"), p("7.4", "एक नया अनुभाग।")))));
        Path pairs = inputs.resolve("pairs.jsonl");

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi", "--dry-run",
                "--extraction", local.toString(), "--pairs", pairs.toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(imports.previewed).extracting(NcertParagraphRow::address).containsExactly(
                "ch 7 §7.3 ¶1", "ch 7 §7.3 ¶2", "ch 7 §7.4 ¶1");
        assertThat(out.toString()).contains("2 of 2 sections one to one")
                .contains("- ch 7 page 4 §7.3: the English paragraph after the row starting \"नियम (b)\" is not printed "
                        + "in this edition — the number after the row is left to it (the Hindi does not print English ¶3)")
                .contains("- ch 7 §7.3 ¶3");
        assertThat(Files.readAllLines(pairs).get(2)).contains("\"address\":\"ch 7 §7.3 ¶3\"").contains("\"unprinted\":true")
                .doesNotContain("\"hi\"");
    }

    /** The number left after a row goes with it: a move numbers its new place with that number after the row. */
    @Test
    void aMoveKeepsTheNumberLeftAfterItsRow() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "* A footnote", after: true,
                     reason: the Hindi does not print the English paragraph after the footnote}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: move, at: "* A footnote",
                     after: "Rule (a)", after_page: 7, reason: the English numbers the footnote last}
                """, page(7, 7, "0.95", p("7.4", "* A footnote."), p("7.4", "Rule (a).")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Rule (a)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "* A footnote."));
        assertThat(out.toString()).contains("- ch 7 §7.4 ¶3");
    }

    /** A deferred row with `after` leaves the number after its own when it takes one, after the row it waits for. */
    @Test
    void aDeferredRowLeavesTheNumberAfterItWhenItIsNumbered() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: defer, at: "* A footnote",
                     reason: the English numbers the next paragraph first}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "* A footnote", after: true,
                     reason: the Hindi does not print the section's last English paragraph}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "* A footnote.")),
                page(7, 8, "0.95", p("7.4", "Rule (b).")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactly(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Rule (a)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "Rule (b)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶3", "* A footnote."));
        assertThat(out.toString()).contains("- ch 7 §7.4 ¶4");
    }

    /** `after` is for a section's last paragraph: on a row another follows, the gap is that row's to name. */
    @Test
    void anUnprintedEntryAfterARowAnotherFollowsIsRefused() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "Rule (a)", after: true,
                     reason: the Hindi does not print English ¶2}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "Rule (b).")))).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(imports.rows).isNull();
        assertThat(out.toString()).contains("1 unprinted correction(s) with after name a row another follows")
                .contains("ch 7 §7.4 ¶1 is not its section's last row").contains("Name the row after the gap, without after.");
    }

    /**
     * bio11's Hindi prints §10.5 with no heading of its own (2026-10-17), so the transcription numbered its paragraph
     * as the fifth of §10.4.2; a `section` entry on that paragraph numbers it §10.5 ¶1 and leaves the four before it.
     */
    @Test
    void aSectionEntryOnALaterParagraphNumbersItFromOneInItsSection() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: section, at: "Rule (c)", section: "7.5",
                     reason: the Hindi prints §7.5 with no heading}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "Rule (b)."), p("7.4", "Rule (c).")),
                page(7, 8, "0.95", new NcertPage.Paragraph("7.4", "It holds.", true, List.of()), p("7.6", "Next.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactly(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Rule (a)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "Rule (b)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.5 ¶1", "Rule (c). It holds."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.6 ¶1", "Next."));
    }

    /** Two English paragraphs in a row the edition does not print: two entries on the one row leave two numbers. */
    @Test
    void twoUnprintedEntriesOnOneRowLeaveTwoNumbers() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "Rule (b)",
                     reason: the Hindi does not print English ¶2}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "Rule (b)",
                     reason: nor English ¶3}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "Rule (b).")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address).containsExactly("ch 7 §7.4 ¶1", "ch 7 §7.4 ¶4");
        assertThat(out.toString()).contains("- ch 7 §7.4 ¶2").contains("- ch 7 §7.4 ¶3");
    }

    /** The number left belongs to the row's place in the English: a move into another section takes it there. */
    @Test
    void aRowMovedToAnotherSectionTakesTheNumberItLeavesWithIt() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 3, kind: unprinted, at: "* STP means",
                     reason: the Hindi does not print the English paragraph before the footnote}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 3, kind: move, at: "* STP means",
                     after: "Pressure is a scalar.", after_page: 2, reason: the English prints it on page 2}
                """, page(7, 2, "0.95", p("7.2", "Pressure is a scalar."), p("7.2", "Example 7.1 Two thigh bones.")),
                page(7, 3, "0.95", p("7.2.1", "Pascal's law."), p("7.2.1", "* STP means standard temperature.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶1", "Pressure is a scalar."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶3", "* STP means standard temperature."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2 ¶4", "Example 7.1 Two thigh bones."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.2.1 ¶1", "Pascal's law."));
        String report = out.toString();
        String left = report.substring(report.indexOf("left without Hindi by an `unprinted` entry"));
        assertThat(left.substring(0, left.indexOf("\n## "))).contains("- ch 7 §7.2 ¶2").doesNotContain("§7.2.1");
    }

    /** Without the entry the same section is named as off one to one, by its counts and the numbers left: none. */
    @Test
    void aSectionMissingAnEnglishParagraphIsNamedWithoutTheEntry() throws IOException {
        hindiEdition();
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), "corrections: []\n");
        imports.paragraphsAnswer = List.of(english("7.3", 1, "Rule (a)."), english("7.3", 2, "It holds at a point too."),
                english("7.3", 3, "Rule (b)."));
        Path local = inputs.resolve("hi.jsonl");
        Files.write(local, ExtractJsonl.write(List.of(page(7, 4, "0.95", p("7.3", "नियम (a)।"), p("7.3", "नियम (b)।")))));

        assertThat(commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi", "--dry-run",
                "--extraction", local.toString(), "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(out.toString()).contains("0 of 1 sections one to one").contains("| 7 | 7.3 | 3 | 2 | 0 |");
    }

    /** A move numbers its sections again from ¶1 and still leaves the number an `unprinted` entry leaves: it goes with its row. */
    @Test
    void aMoveKeepsTheNumberAnUnprintedEntryLeaves() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "Rule (b)",
                     reason: the Hindi does not print English ¶2}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: move, at: "* A footnote",
                     after: "Rule (a)", after_page: 7, reason: the English numbers the footnote first}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "Rule (b)."), p("7.4", "* A footnote.")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactlyInAnyOrder(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Rule (a)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "* A footnote."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶4", "Rule (b)."));
        assertThat(out.toString()).contains("- ch 7 §7.4 ¶3");
    }

    /** A deferred row an `unprinted` entry names leaves its number when it takes one, after the row it waits for. */
    @Test
    void aDeferredRowLeavesTheNumberWhenItIsNumbered() throws IOException {
        assertThat(loadHindi("""
                corrections:
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: defer, at: "* A footnote",
                     reason: the English numbers the next paragraph first}
                  - {book: phy11-part1, lang: hi, chapter: 7, page: 7, kind: unprinted, at: "* A footnote",
                     reason: the Hindi does not print the English paragraph before the footnote}
                """, page(7, 7, "0.95", p("7.4", "Rule (a)."), p("7.4", "* A footnote.")),
                page(7, 8, "0.95", p("7.4", "Rule (b).")))).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::address, NcertParagraphRow::text).containsExactly(
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶1", "Rule (a)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶2", "Rule (b)."),
                org.assertj.core.groups.Tuple.tuple("ch 7 §7.4 ¶4", "* A footnote."));
    }

    /** The numbering's own net: a row it cannot find is refused, never skipped. */
    @Test
    void anUnprintedEntryTheNumberingCannotPlaceIsRefused() {
        List<ExtractedPage> pages = List.of(page(7, 2, "0.95", p("7.2", "Pressure is a scalar.")));

        assertThatThrownBy(() -> NcertLoadCommand.number(pages, "hi.jsonl", Set.of(), List.of(),
                List.of(new NcertCorrections.Unprinted((short) 7, 2, 4, "* Nowhere", false))))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("1 unprinted correction(s) cannot be placed")
                .hasMessageContaining("ch 7 page 2: the row starting \"* Nowhere\" is not a row of its own");
    }

    @Test
    void withoutACorrectionsFileTheLoadSaysSo() {
        jsonl(page(7, 1, "0.95", p("7.1", "Text.")));

        assertThat(run()).isZero();

        assertThat(out.toString()).contains("no ncert-corrections.yaml under the inputs: nothing to apply");
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

    /**
     * A footnote sits at the foot of the page, so it is the page's last paragraph — but it is not
     * what the next page continues. Before this, chapter 5's definition of potential energy read
     * "* The variation of g with height is discussed in Chapter 7 on Gravitation. energy V(x) is
     * defined if…": the footnote was polluted and the body paragraph it stole from was left
     * truncated at "the potential". Four of phy11-part1's nine footnotes had taken a continuation
     * this way, costing two paragraphs each (D15, the corpus event of 2026-09-17).
     */
    @Test
    void aContinuationJoinsTheBodyParagraphAndNotAFootnoteBelowIt() {
        imports.renderedPagesAnswer = 8;
        jsonl(page(7, 7, "0.95",
                        p("7.7", "Mathematically, (for simplicity, in one dimension) the potential"),
                        p("7.7", "* The variation of g with height is discussed in Chapter 7 on Gravitation.")),
                page(7, 8, "0.95", continuing("7.7", "energy V(x) is defined if the force F(x) can be written as")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text).containsExactly(
                "Mathematically, (for simplicity, in one dimension) the potential energy V(x) is defined "
                        + "if the force F(x) can be written as",
                "* The variation of g with height is discussed in Chapter 7 on Gravitation.");
    }

    /**
     * The print sets some footnote marks against their first word — the Hindi Physics Part I has
     * "*Delta t->0 सीमा में…" (ch 3 p15) — and a rule that wanted a space after the asterisk took that
     * footnote for body text: the next page's continuation joined it, and the paragraph it belonged to
     * was left cut at "अर्थात्" (D16).
     */
    @Test
    void aFootnoteWhoseMarkTouchesItsFirstWordIsStillAFootnote() {
        imports.renderedPagesAnswer = 16;
        jsonl(page(7, 15, "0.95",
                        p("7.7", "समरूप त्रिभुजों की भुजाओं का अनुपात बराबर होगा, अर्थात्"),
                        p("7.7", "*Delta t->0 सीमा में Delta r, r के लंबवत् हो जाता है ।")),
                page(7, 16, "0.95", continuing("7.7", "|Delta v| / v = |Delta r| / R")));

        assertThat(run()).isZero();

        assertThat(imports.rows).extracting(NcertParagraphRow::text).containsExactly(
                "समरूप त्रिभुजों की भुजाओं का अनुपात बराबर होगा, अर्थात् |Delta v| / v = |Delta r| / R",
                "*Delta t->0 सीमा में Delta r, r के लंबवत् हो जाता है ।");
    }

    /**
     * NCERT prints the plural when a sentence names several parts at once — "Figs. 3.15(a) to (d)",
     * "Figures 4.8(b)" — and the rule that decides what figure_refs may hold took only the singular,
     * so four real references were dropped from phy11-part1 and `ncert verify` then reported the
     * paragraphs as mentioning a figure they do not carry (D15, 2026-09-17). A reference naming no
     * figure or table at all is still dropped.
     */
    @Test
    void aPluralFigureLabelIsAReferenceAndAnEquationNumberIsNot() {
        imports.renderedPagesAnswer = 1;
        jsonl(page(7, 1, "0.95",
                new NcertPage.Paragraph("7.7", "As shown, the velocity turns with the particle.",
                        false, List.of("Figs. 3.15(a) to (d)", "Figures 4.8(b)", "Eq. (7.5)", "4.8(c)"))));

        assertThat(run()).isZero();

        assertThat(imports.rows).singleElement()
                .extracting(NcertParagraphRow::figureRefs).asInstanceOf(
                        org.assertj.core.api.InstanceOfAssertFactories.list(String.class))
                .containsExactly("Figs. 3.15(a) to (d)", "Figures 4.8(b)");
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

    /**
     * Sonnet set displayed equations on their own lines inside a paragraph's text (D15, run 5);
     * the rule asks for single spaces, and the loader makes it so rather than asking again.
     */
    @Test
    void lineBreaksInsideAParagraphAreNormalisedToSingleSpaces() {
        jsonl(page(7, 5, "0.95", p("7.3", "(b) Now if the mass at vertex A is doubled then\nF'_GA = 4Gm^2 j_hat\n"
                + "F'_GB = F_GB and F'_GC = F_GC")));

        run();

        assertThat(imports.rows.getFirst().text())
                .isEqualTo("(b) Now if the mass at vertex A is doubled then F'_GA = 4Gm^2 j_hat F'_GB = F_GB and F'_GC = F_GC");
    }

    /**
     * figure_refs holds figure and table labels; a model put "Eq. (7.5)" in it (D15, the Opus
     * run). A rule can ask; the loader guarantees, and names what it dropped.
     */
    @Test
    void aFigureRefThatIsNotAFigureOrTableLabelIsDroppedAndReported() {
        jsonl(page(7, 7, "0.95",
                new NcertPage.Paragraph("7.6", "Consider a point mass m at a height h (Fig. 7.8(a)); from Eq. (7.5)",
                        false, List.of("Fig. 7.8(a)", "Eq. (7.5)", "Table 7.1", "Figure 10.2 b"))));

        run();

        assertThat(imports.rows.getFirst().figureRefs()).containsExactly("Fig. 7.8(a)", "Table 7.1", "Figure 10.2 b");
        assertThat(out.toString())
                .contains("## figure_refs that are not figure or table labels — dropped")
                .contains("ch 7 §7.6 ¶1: \"Eq. (7.5)\"");
    }

    /**
     * The Hindi edition's labels are चित्र and सारणी, with चित्रों when a sentence names several parts
     * and the number sometimes inside the brackets. The first Hindi book's load named all 177 of its
     * real labels as dropped (phy11-part1, D16), which buries the one entry that is not a label.
     */
    @Test
    void aHindiFigureOrTableLabelIsAReferenceAndAHindiEquationNumberIsNot() {
        jsonl(page(7, 2, "0.95",
                new NcertPage.Paragraph("7.2", "चित्र 7.1a में दीर्घवृत्त दिखाया गया है; समीकरण (7.5) देखिए।",
                        false, List.of("चित्र 7.1a", "सारणी 7.1", "चित्रों 2.2 (a), 2.2 (b) तथा 2.2 (c)", "चित्र (3.9b)",
                                "समीकरण (7.5)", "4.8(c)"))));

        run();

        assertThat(imports.rows.getFirst().figureRefs())
                .containsExactly("चित्र 7.1a", "सारणी 7.1", "चित्रों 2.2 (a), 2.2 (b) तथा 2.2 (c)", "चित्र (3.9b)");
        assertThat(out.toString())
                .contains("ch 7 §7.2 ¶1: \"समीकरण (7.5)\"")
                .contains("ch 7 §7.2 ¶1: \"4.8(c)\"")
                .doesNotContain("ch 7 §7.2 ¶1: \"चित्र")
                .doesNotContain("ch 7 §7.2 ¶1: \"सारणी");
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

    private int dryRun(String... options) {
        List<String> arguments = new ArrayList<>(List.of("ncert", "load", "--book", "phy11-part1", "--dry-run",
                "--inputs", inputs.toString(), "--reports", reports.toString()));
        arguments.addAll(List.of(options));
        return commandLine.execute(arguments.toArray(String[]::new));
    }

    private void jsonl(ExtractedPage... pages) {
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.en), ExtractJsonl.write(List.of(pages)),
                "application/jsonl");
    }

    /** A Hindi load of the test book with these corrections and pages: the edition a `section` or `move` entry is for. */
    private int loadHindi(String corrections, ExtractedPage... pages) throws IOException {
        hindiEdition();
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), corrections);
        store.put(ContentKeys.extract("phy11-part1", BookLanguage.hi), ExtractJsonl.write(List.of(pages)), "application/jsonl");
        return commandLine.execute("ncert", "load", "--book", "phy11-part1", "--lang", "hi",
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    private static ExtractedPage page(int chapter, int page, String confidence, NcertPage.Paragraph... paragraphs) {
        return new ExtractedPage((short) chapter, page, new BigDecimal(confidence), UUID.randomUUID(),
                List.of(paragraphs), null);
    }

    /** The test book with a Hindi edition registered beside its English one. */
    private void hindiEdition() throws IOException {
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
                      hi: source/ncert/2022-ed/hi/phy11-part1/
                    chapters:
                      - {no: 7, en: keph107.pdf, hi: khph107.pdf}
                """);
    }

    private static NcertParagraphRow english(String section, int paraNo, String text) {
        return new NcertParagraphRow((short) 7, section, (short) paraNo, text, false, List.of(),
                new ParagraphExtraction(List.of(4), new BigDecimal("0.95"), null));
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
        List<String> clearedAnswer = List.of();
        long embedded;

        @Override
        public long embeddedParagraphs(String bookCode) {
            return embedded;
        }

        @Override
        public NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
            this.book = bookCode;
            this.language = language;
            this.rows = new ArrayList<>(rows);
            return answer(rows);
        }

        /** A dry run's rows, which a load of record never sees. */
        List<NcertParagraphRow> previewed;

        @Override
        public NcertLoadReport previewParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
            this.previewed = new ArrayList<>(rows);
            return answer(rows);
        }

        private NcertLoadReport answer(List<NcertParagraphRow> rows) {
            Map<Short, Integer> perChapter = new java.util.TreeMap<>();
            rows.forEach(row -> perChapter.merge(row.chapterNo(), 1, Integer::sum));
            return new NcertLoadReport(rows.size(), 0, 0, perChapter, orphansAnswer, 0, 0, clearedAnswer);
        }
    }
}
