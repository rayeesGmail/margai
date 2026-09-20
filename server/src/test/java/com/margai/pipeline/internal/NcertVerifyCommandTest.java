package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Usage;
import com.margai.ai.tasks.NcertPageVerifier;
import com.margai.ai.tasks.PageVerdicts;
import com.margai.ai.tasks.VerifyItem;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertVerificationRow;
import com.margai.curriculum.api.ParagraphExtraction;
import com.margai.curriculum.api.ParagraphVerification;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert verify} over three loaded rows of chapter 7 — one of them straddling pages 1 and 2 —
 * with a stub in place of the verifying model (D15, DECISIONS 2026-09-14 "the pair"). Each page is read
 * once with the parts of the paragraphs printed on it; the model's spans are held to what code can
 * check before anything is flagged; a verdict lands on every row all of whose pages were read; a
 * second run reads nothing it has read; and the verifier may not be the model that transcribed.
 */
class NcertVerifyCommandTest {

    private static final UUID TRANSCRIBED_P1 = UUID.randomUUID();
    private static final UUID TRANSCRIBED_P2 = UUID.randomUUID();
    private static final String STRADDLING = "A sentence that runs on and finishes on the next page.";
    private static final String VECTOR = "F = - G m_1m_2 / |r|^3 r_hat where G is the constant.";

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final PipelineCommandTest.RecordingImport imports = new PipelineCommandTest.RecordingImport();
    private final StubVerifier verifier = new StubVerifier();
    private final Map<UUID, String> ledger = new HashMap<>();
    private AiClientInfo client = new AiClientInfo("anthropic", List.of("ledger", "breaker"));
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
        store.put("source/ncert/2022-ed/en/phy11-part1/keph107.pdf", pdf(2), "application/pdf");
        page(1);
        page(2);
        imports.paragraphsAnswer = rows();
        ledger.put(TRANSCRIBED_P1, "claude-opus-5");
        ledger.put(TRANSCRIBED_P2, "claude-opus-5");
        build();
    }

    /** The command line; picocli makes the command objects here, so a test that swaps the client rebuilds it. */
    private void build() {
        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertVerifyCommand.class) {
                    return cls.cast(new NcertVerifyCommand(store, imports, verifier, ids -> filter(ids),
                            new NcertExtractCommandTest.StubSpend(), client,
                            new PipelineProperties(72, 10, 1, "claude-sonnet-5", 100, 0), writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    @Test
    void withoutReadPagesOnlyTheFreeChecksRunAndNoModelIsCalled() {
        assertThat(run()).isZero();

        assertThat(verifier.calls).isEmpty();
        assertThat(out.toString())
                .contains("## where rows start against where the print starts paragraphs")
                .contains("## joins across page breaks against the print")
                .contains("## figure_refs against the paragraph and the chapter's captions")
                .contains("no model was called: add --read-pages for the second read");
    }

    @Test
    void eachPageIsReadOnceWithThePartsOfTheParagraphsPrintedOnIt() {
        assertThat(run("--read-pages")).isZero();

        assertThat(verifier.calls).containsExactly(1, 2);
        assertThat(verifier.items.get(0)).containsExactly(
                new VerifyItem(1, "Early in our lives, we become aware of gravity.", false, false),
                new VerifyItem(2, "A sentence that runs on", false, true));
        assertThat(verifier.items.get(1)).containsExactly(
                new VerifyItem(1, "and finishes on the next page.", true, false),
                new VerifyItem(2, VECTOR, false, false));
    }

    @Test
    void aVerdictLandsOnEveryRowAndAFlagIsListedForAdjudication() {
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");

        assertThat(run("--read-pages")).isZero();

        assertThat(imports.verifications).extracting(NcertVerificationRow::address)
                .containsExactly("ch 7 §7.1 ¶1", "ch 7 §7.1 ¶2", "ch 7 §7.2 ¶1");
        ParagraphVerification vector = imports.verifications.get(2).verification();
        assertThat(vector.verdict()).isEqualTo(ParagraphVerification.Verdict.differs);
        assertThat(vector.differences()).containsExactly(
                new ParagraphVerification.Difference(2, "|r|^3 r where", "|r|^3 r_hat where"));
        assertThat(vector.textSha256()).isEqualTo(ParagraphVerification.sha256(VECTOR));
        assertThat(vector.model()).isEqualTo("claude-sonnet-5");
        assertThat(vector.promptVersion()).isEqualTo("ncert_verify.v1");
        ParagraphVerification straddling = imports.verifications.get(1).verification();
        assertThat(straddling.verdict()).isEqualTo(ParagraphVerification.Verdict.matches);
        assertThat(straddling.aiCallIds()).hasSize(2);
        assertThat(out.toString())
                .contains("## the second read's flags — adjudicate these against the page")
                .contains("- ch 7 p2 §7.2 ¶1: printed \"|r|^3 r where\" · transcribed \"|r|^3 r_hat where\"");
    }

    /** The ruling's normalisation, in code: a verifier that re-spaced a span has found nothing. */
    @Test
    void aDifferenceOnlyInSpacingOrAGlyphVariantIsSetAsideByCode() {
        verifier.differs(2, 2, "G m_1 m_2 / |r|^3", "G m_1m_2 / |r|^3");
        verifier.differs(1, 1, "aware of gravity ≈", "aware of gravity ≅");

        run("--read-pages");

        assertThat(imports.verifications).allSatisfy(row ->
                assertThat(row.verification().verdict()).isEqualTo(ParagraphVerification.Verdict.matches));
        assertThat(out.toString())
                .contains("## set aside by code: the spans differ only in spacing or a glyph variant, or not at all")
                .contains("- ch 7 p2 §7.2 ¶1: printed \"G m_1 m_2 / |r|^3\" · transcribed \"G m_1m_2 / |r|^3\"");
    }

    /**
     * A misquoted claim is not a match: the verifier said something differs and could not say where, so
     * nothing is known about the row — it is not judged, and it is not counted clean (spec-auditor, D15).
     */
    /** Sonnet listed spans it had checked as differences with both sides the same (2026-09-15): no difference. */
    @Test
    void identicalSpansAreSetAsideAndTheRowMatches() {
        verifier.differs(2, 2, "|r|^3 r_hat where", "|r|^3 r_hat where");

        run("--read-pages");

        assertThat(imports.verifications.get(2).verification().verdict()).isEqualTo(ParagraphVerification.Verdict.matches);
        assertThat(out.toString())
                .contains("## set aside by code: the spans differ only in spacing or a glyph variant, or not at all")
                .contains("- ch 7 p2 §7.2 ¶1: printed \"|r|^3 r_hat where\" · transcribed \"|r|^3 r_hat where\"");
    }

    @Test
    void aTranscribedSpanTheRowDoesNotCarryLeavesTheRowNotJudged() {
        verifier.differs(2, 2, "where G is a constant", "where G was the constant");

        run("--read-pages");

        assertThat(imports.verifications.get(2).verification().verdict()).isEqualTo(ParagraphVerification.Verdict.not_judged);
        assertThat(out.toString())
                .contains("## set aside by code: the verifier quoted a transcription the row does not carry")
                .contains("- ch 7 p2 §7.2 ¶1: transcribed \"where G was the constant\"");
    }

    @Test
    void aFlagTheFounderHasRuledOnIsNotRaisedAgain() throws IOException {
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 2, kind: false_positive,
                     printed: "|r|^3 r where", transcribed: "|r|^3 r_hat where", reason: "read against the page"}
                """);
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");

        run("--read-pages");

        assertThat(imports.verifications.get(2).verification().verdict()).isEqualTo(ParagraphVerification.Verdict.matches);
        assertThat(out.toString()).contains("flags set aside by the founder's rulings in ncert-corrections.yaml: 1");
    }

    @Test
    void anItemTheVerifierReturnedNoVerdictForIsNotJudged() {
        verifier.only.put(1, List.of(1));

        run("--read-pages");

        assertThat(imports.verifications.get(1).verification().verdict())
                .isEqualTo(ParagraphVerification.Verdict.not_judged);
        assertThat(out.toString())
                .contains("## items the verifier returned no verdict for")
                .contains("- ch 7 p1 §7.1 ¶2");
    }

    @Test
    void textNotOnThePageAndTextNoRowCarriesAreReported() {
        verifier.notOnPage(1, 1);
        verifier.omitted.put(2, List.of("F_GA = Gm(2m) / 1 j_hat"));

        run("--read-pages");

        assertThat(imports.verifications.getFirst().verification().verdict())
                .isEqualTo(ParagraphVerification.Verdict.not_on_page);
        assertThat(out.toString())
                .contains("## rows the verifier could not find on their page")
                .contains("- ch 7 p1 §7.1 ¶1 \"Early in our lives, we become aware\"")
                .contains("## running text the page prints that no row carries")
                .contains("- ch 7 p2: \"F_GA = Gm(2m) / 1 j_hat\"");
    }

    /** Verifying Opus with Opus is no second read: the ledger says who transcribed, and the run refuses. */
    @Test
    void theVerifierMayNotBeTheModelThatTranscribed() {
        ledger.put(TRANSCRIBED_P2, "claude-sonnet-5");

        assertThat(run("--read-pages")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(verifier.calls).isEmpty();
        assertThat(out.toString()).contains("the verifier must not be the transcriber")
                .contains("claude-sonnet-5 transcribed 1 of these rows")
                .contains("visionsonnet");
    }

    /** Fixtures recorded as verdicts on real rows would be a stand-in mistaken for the real read. */
    @Test
    void theSecondReadRefusesTheFakeClient() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));
        build();

        assertThat(run("--read-pages")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(verifier.calls).isEmpty();
        assertThat(imports.verifications).isEmpty();
        assertThat(out.toString()).contains("the AI client is the fake").contains("AI_LIVE=1");
    }

    /** The ruling names the verifier; a forgotten profile would otherwise verify with whatever VISION is. */
    @Test
    void theSecondReadRefusesAVerifyTierThatIsNotTheRulingsVerifier() {
        verifier.model = "claude-haiku-4-5";

        assertThat(run("--read-pages")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(verifier.calls).isEmpty();
        assertThat(out.toString()).contains("the verify tier is claude-haiku-4-5, not claude-sonnet-5")
                .contains("visionsonnet");
    }

    /** A read by any other model — an earlier misconfigured run — is neither reused nor counted. */
    @Test
    void aPageReadByAnotherModelIsReadAgainAndItsVerdictsAreNotCounted() {
        verifier.model = "claude-sonnet-5";
        verifier.answeringModel = "claude-haiku-4-5";
        run("--read-pages");
        imports.verifications.clear();
        out.getBuffer().setLength(0);

        verifier.answeringModel = "claude-sonnet-5";
        run("--read-pages");

        assertThat(verifier.calls).containsExactly(1, 2, 1, 2);
        assertThat(imports.verifications).allSatisfy(row ->
                assertThat(row.verification().model()).isEqualTo("claude-sonnet-5"));
    }

    /** The runbook's promise: a ruling added after a read takes effect at ₹0, on the free run too. */
    @Test
    void aFreeRunAppliesARulingAddedSinceFromTheArtefactWithoutCallingTheModel() throws IOException {
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");
        run("--read-pages");
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 2, kind: false_positive,
                     printed: "|r|^3 r where", transcribed: "|r|^3 r_hat where", reason: "read against the page"}
                """);
        imports.verifications.clear();
        out.getBuffer().setLength(0);

        assertThat(run()).isZero();

        assertThat(verifier.calls).containsExactly(1, 2);
        assertThat(imports.verifications.get(2).verification().verdict()).isEqualTo(ParagraphVerification.Verdict.matches);
        assertThat(out.toString()).contains("| 7 | 3 | 3 | 3 | 0 | 0 | 0 | 0 | 100.0% |")
                .contains("no model was called");
    }

    /** A corpus whose transcriber the ledger cannot name — loaded into a database without its ai_calls — could be the verifier's own. */
    @Test
    void theSecondReadRefusesRowsWhoseTranscriberTheLedgerCannotName() {
        ledger.remove(TRANSCRIBED_P2);

        assertThat(run("--read-pages")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(verifier.calls).isEmpty();
        assertThat(out.toString()).contains("the ledger cannot name the model that transcribed 1 of these rows");
    }

    /** A run that paid for its pages says what they cost even when recording the verdicts refuses. */
    @Test
    void theCostIsReportedWhenRecordingTheVerdictsRefuses() {
        imports.refuseVerifications = true;

        assertThat(run("--read-pages")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("| 3 | 9000 | 3000 | 1200 | 0 | ₹4.41 |")
                .contains("the text has changed since it was verified");
    }

    /**
     * The seeded run listed two figure captions and two section headings as running text no row carries
     * (2026-09-15). Code can recognise both, so they are set aside, listed, and not counted.
     */
    @Test
    void anOmittedHeadingOrCaptionIsSetAsideByCode() {
        verifier.omitted.put(2, List.of("7.4 THE GRAVITATIONAL CONSTANT", "5.2.2 Inheritance of One Gene",
                "Fig. 7.3 Gravitational force on m_1 due to m_2", "Table 7.1 Data from measurement",
                "v_x = v cos theta", "3.84 × 10^8 m is the distance"));

        run("--read-pages");

        assertThat(out.toString())
                .contains("## running text the page prints that no row carries\n\n- ch 7 p2: \"v_x = v cos theta\"\n"
                        + "- ch 7 p2: \"3.84 × 10^8 m is the distance\"\n")
                .contains("## set aside by code: a heading or a caption listed as omitted text")
                .contains("- ch 7 p2: \"7.4 THE GRAVITATIONAL CONSTANT\"")
                .contains("- ch 7 p2: \"Table 7.1 Data from measurement\"")
                .contains("2 passages no row carries");
    }

    @Test
    void thePageLevelSignalsAreCountedBesideTheCleanShare() {
        verifier.omitted.put(2, List.of("v_x = v cos theta"));

        run("--read-pages");

        assertThat(out.toString()).contains("not in the clean share, adjudicate before recording it: 2 page-level start"
                + " flags, 0 numbered equations the print carries that the rows do not, 1 passages no row carries");
    }

    /** The free check that closes the second read's blind spot: a numbered equation dropped from a row. */
    @Test
    void aNumberedEquationThePrintCarriesAndTheRowsDoNotIsReportedAndCountedBesideTheShare() throws IOException {
        store.put("source/ncert/2022-ed/en/phy11-part1/keph107.pdf", numbering(), "application/pdf");
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow vector = rows.get(2);
        rows.set(2, new NcertParagraphRow(vector.chapterNo(), vector.section(), vector.paraNo(),
                "Numbered on the page as (7.5) and referred to again.", true, List.of(), vector.extraction()));
        imports.paragraphsAnswer = rows;

        run();

        assertThat(out.toString())
                .contains("| chapter | pages compared | page breaks judged | page breaks undecided | start flags"
                        + " | join flags | figure flags | equation flags | starts paired |")
                .contains("## numbered equations the print carries that the rows do not")
                .contains("ch 7 p2: the print numbers (7.5) twice, the rows carry it once"
                        + " — a displayed equation dropped, its number altered, or a reference to it lost")
                .contains("1 numbered equations the print carries that the rows do not");
    }

    @Test
    void theBooksCleanShareIsNotComputedWhileASelectedChapterHasNoRows() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), Files.readString(inputs.resolve(NcertRegisterCommand.FILE))
                + "      - {no: 8, en: keph108.pdf}\n");

        run("--read-pages");

        assertThat(out.toString()).contains("| 7 | 3 | 3 | 3 | 0 | 0 | 0 | 0 | 100.0% |")
                .contains("clean for the book (PLAN D15 ✅): not computed — a selected chapter has no loaded rows");
    }

    /**
     * A page is paid for when it is read, so it is written when it is read: the first calibration run was
     * stopped after two pages and lost both, because the artefact was flushed every ten (2026-09-15).
     */
    @Test
    void everyPageReadIsInTheArtefactBeforeTheNextIsSent() {
        List<Integer> pagesStoredWhenCalled = new ArrayList<>();
        verifier.onCall = () -> {
            String key = ContentKeys.verify("phy11-part1", BookLanguage.en);
            pagesStoredWhenCalled.add(store.exists(key) ? VerifyJsonl.read(key, store.get(key)).size() : 0);
        };

        run("--read-pages");

        assertThat(pagesStoredWhenCalled).containsExactly(0, 1);
    }

    /** The seeded recall run reads a scratch database and must never write into the real artefact (DECISIONS 2026-09-15). */
    @Test
    void anArtefactTagKeepsTheReadsInAnArtefactOfTheirOwn() {
        assertThat(run("--read-pages", "--artefact-tag", "seeded")).isZero();

        assertThat(store.exists("verify/phy11-part1/en.seeded.jsonl")).isTrue();
        assertThat(store.exists(ContentKeys.verify("phy11-part1", BookLanguage.en))).isFalse();
        assertThat(out.toString()).contains("artefact: verify/phy11-part1/en.seeded.jsonl (2 pages)");
    }

    @Test
    void anArtefactTagThatIsNotAPlainWordIsRefused() {
        assertThat(run("--read-pages", "--artefact-tag", "../en")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--artefact-tag must be lowercase letters, digits and hyphens");
    }

    @Test
    void pagesAndRedoWithoutReadPagesAreRefused() {
        assertThat(run("--pages", "2")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("--pages and --redo choose what --read-pages reads");
    }

    @Test
    void rowsLoadedBeforePageOffsetsExistedAreRefusedWithTheRemedy() {
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow first = rows.getFirst();
        rows.set(0, new NcertParagraphRow(first.chapterNo(), first.section(), first.paraNo(), first.text(), false,
                List.of(), new ParagraphExtraction(List.of(1), List.of(), new BigDecimal("0.9"), TRANSCRIBED_P1, null)));
        imports.paragraphsAnswer = rows;

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("1 row(s) of chapter 7 were loaded before page offsets existed")
                .contains("ncert load --book phy11-part1 --lang en --chapters 7");
    }

    @Test
    void aSecondRunReadsNothingItHasReadAndAppliesARulingAddedSince() throws IOException {
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");
        run("--read-pages");
        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 2, kind: misprint,
                     printed: "|r|^3 r where", transcribed: "|r|^3 r_hat where", reason: "the book prints it"}
                """);
        imports.verifications.clear();
        out.getBuffer().setLength(0);

        assertThat(run("--read-pages")).isZero();

        assertThat(verifier.calls).containsExactly(1, 2);
        assertThat(imports.verifications.get(2).verification().verdict()).isEqualTo(ParagraphVerification.Verdict.matches);
        assertThat(out.toString()).contains("| pages | read this run | from earlier runs |").contains("| 2 | 0 | 2 |");
    }

    /** A page whose text changed since it was read is read again: the verdict judged other words. */
    @Test
    void aPageWhoseTextChangedIsReadAgain() {
        run("--read-pages");
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow vector = rows.get(2);
        rows.set(2, new NcertParagraphRow(vector.chapterNo(), vector.section(), vector.paraNo(),
                "F = - G m_1m_2 / |r|^3 r where G is the constant.", false, List.of(), vector.extraction()));
        imports.paragraphsAnswer = rows;

        run("--read-pages");

        assertThat(verifier.calls).containsExactly(1, 2, 2);
    }

    /**
     * A correction that splits or joins a paragraph moves every later row of its section down a number.
     * The page's words are unchanged, so the read still holds: a row is matched to it by the hash of its
     * part, never by the address it had when the page was read (founder's ruling, 2026-09-16).
     */
    @Test
    void aRowThatOnlyMovedDownItsSectionKeepsTheReadItAlreadyHas() {
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");
        run("--read-pages");
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow vector = rows.get(2);
        rows.set(2, new NcertParagraphRow(vector.chapterNo(), vector.section(), (short) 2, vector.text(),
                vector.hasEquations(), vector.figureRefs(), vector.extraction()));
        imports.paragraphsAnswer = rows;
        imports.verifications.clear();

        run("--read-pages");

        assertThat(verifier.calls).containsExactly(1, 2);
        // The verdict the read gave that part, now at the address the row moved to — not merely some verdict.
        assertThat(imports.verifications).filteredOn(row -> row.address().equals("ch 7 §7.2 ¶2")).singleElement()
                .satisfies(row -> {
                    assertThat(row.verification().verdict()).isEqualTo(ParagraphVerification.Verdict.differs);
                    assertThat(row.verification().differences()).singleElement().satisfies(difference ->
                            assertThat(difference.printed()).isEqualTo("|r|^3 r where"));
                });
    }

    /**
     * What a split correction really leaves behind, and the path that pays for chapter 7: the page's parts
     * are no longer the ones that were read, so the halves of the split paragraph have no verdict — but the
     * untouched paragraph beside them on the same page keeps its own, for nothing, on a free run.
     */
    @Test
    void onAPageASplitChangedTheUntouchedPartKeepsItsVerdictAndTheSplitHalvesDoNot() {
        run("--read-pages");
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow vector = rows.get(2);
        rows.set(2, new NcertParagraphRow((short) 7, "7.2", (short) 1, "F = - G m_1m_2 / |r|^3 r_hat", true,
                List.of(), vector.extraction()));
        rows.add(new NcertParagraphRow((short) 7, "7.2", (short) 2, "where G is the constant.", true, List.of(),
                vector.extraction()));
        imports.paragraphsAnswer = rows;
        imports.verifications.clear();

        assertThat(run()).isZero();

        assertThat(verifier.calls).containsExactly(1, 2);
        assertThat(imports.verifications).extracting(NcertVerificationRow::address)
                .containsExactly("ch 7 §7.1 ¶1", "ch 7 §7.1 ¶2");
        assertThat(out.toString()).contains("| 7 | 4 | 2 | 2 | 0 | 0 | 0 | 0 | — (2 rows without a verdict) |");
    }

    /**
     * Two paragraphs printing the same words on one page cannot be told apart by their words, and after a
     * renumbering the address one of them now has is the address the other had when the page was read. The
     * read is mapped to the page's parts in order, so each keeps its own verdict and neither takes the
     * other's.
     */
    @Test
    void twoPartsOfOnePagePrintingTheSameWordsKeepTheirOwnVerdictsAcrossARenumbering() {
        imports.paragraphsAnswer = List.of(rows().getFirst(), duplicate((short) 1), duplicate((short) 2));
        verifier.differs(2, 2, "Answers", "Answer");
        run("--read-pages");
        imports.paragraphsAnswer = List.of(rows().getFirst(), duplicate((short) 2), duplicate((short) 3));
        imports.verifications.clear();

        run("--read-pages");

        assertThat(verifier.calls).containsExactly(1, 2);
        assertThat(imports.verifications).extracting(NcertVerificationRow::address, row -> row.verification().verdict())
                .contains(tuple("ch 7 §7.2 ¶2", ParagraphVerification.Verdict.matches),
                        tuple("ch 7 §7.2 ¶3", ParagraphVerification.Verdict.differs));
    }

    /** A paragraph of §7.2 printing the one word page 2 prints twice. */
    private static NcertParagraphRow duplicate(short paraNo) {
        return new NcertParagraphRow((short) 7, "7.2", paraNo, "Answer", false, List.of(),
                new ParagraphExtraction(List.of(2), List.of(0), new BigDecimal("0.9"), TRANSCRIBED_P2, null));
    }

    @Test
    void pagesRestrictsTheReadAndOnlyRowsWhosePagesWereAllReadGetAVerdict() {
        assertThat(run("--read-pages", "--pages", "2")).isZero();

        assertThat(verifier.calls).containsExactly(2);
        assertThat(imports.verifications).extracting(NcertVerificationRow::address).containsExactly("ch 7 §7.2 ¶1");
    }

    /** PLAN D15's ✅, "% paragraphs extracted cleanly per book", from the second read and the free checks together. */
    @Test
    void theCleanShareIsReportedPerChapterAndForTheBook() {
        verifier.differs(2, 2, "|r|^3 r where", "|r|^3 r_hat where");

        run("--read-pages");

        assertThat(out.toString())
                .contains("| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |")
                .contains("| 7 | 3 | 3 | 2 | 1 | 0 | 0 | 0 | 66.7% |")
                .contains("clean for the book (PLAN D15 ✅): 2 of 3 paragraphs, 66.7%");
    }

    /**
     * A free check's flag is a measured guess, so an adjudicated one needs a way off the clean share
     * (D15, 2026-09-19): phy11-part1's twelve join flags were all read against their pages and ruled
     * noise, and cost the book twelve rows that no entry could return. A {@code noise} ruling names the
     * check and the words the paragraph starts with, and the row counts clean again.
     */
    @Test
    void aFreeCheckFlagRuledNoiseLeavesTheCleanShare() throws IOException {
        List<NcertParagraphRow> rows = new ArrayList<>(rows());
        NcertParagraphRow first = rows.getFirst();
        rows.set(0, new NcertParagraphRow(first.chapterNo(), first.section(), first.paraNo(), first.text(),
                false, List.of("Fig. 7.9"), first.extraction()));
        imports.paragraphsAnswer = rows;

        run("--read-pages");
        assertThat(out.toString()).contains("| 7 | 3 | 3 | 3 | 0 | 0 | 0 | 1 | 66.7% |");

        Files.writeString(inputs.resolve(NcertCorrectionsYamlReader.FILE), """
                corrections:
                  - {book: phy11-part1, lang: en, chapter: 7, page: 1, kind: noise, flag: figure,
                     at: "Early in our lives", address: "ch 7 §7.1 ¶1",
                     reason: "the figure is printed on the page and the paragraph is its caption's subject"}
                """);
        out.getBuffer().setLength(0);

        run("--read-pages");

        assertThat(out.toString()).contains("| 7 | 3 | 3 | 3 | 0 | 0 | 0 | 0 | 100.0% |")
                .contains("free-check flags set aside by the founder's rulings")
                .contains("ch 7 §7.1 ¶1");
    }

    /** Without --read-pages, a verdict already on a row still counts, as long as it names the row's current text. */
    @Test
    void storedVerdictsOnTheRowsCountWithoutReadingAgain() {
        List<NcertParagraphRow> rows = new ArrayList<>();
        for (NcertParagraphRow row : rows()) {
            rows.add(new NcertParagraphRow(row.chapterNo(), row.section(), row.paraNo(), row.text(), false, List.of(),
                    row.extraction().withVerification(new ParagraphVerification(ParagraphVerification.Verdict.matches,
                            List.of(), ParagraphVerification.sha256(row.text()), List.of(), "claude-sonnet-5",
                            "ncert_verify.v1"))));
        }
        imports.paragraphsAnswer = rows;

        run();

        assertThat(verifier.calls).isEmpty();
        assertThat(out.toString()).contains("| 7 | 3 | 3 | 3 | 0 | 0 | 0 | 0 | 100.0% |");
    }

    @Test
    void aStoredVerdictByAnotherModelDoesNotCount() {
        List<NcertParagraphRow> rows = new ArrayList<>();
        for (NcertParagraphRow row : rows()) {
            rows.add(new NcertParagraphRow(row.chapterNo(), row.section(), row.paraNo(), row.text(), false, List.of(),
                    row.extraction().withVerification(new ParagraphVerification(ParagraphVerification.Verdict.matches,
                            List.of(), ParagraphVerification.sha256(row.text()), List.of(), "claude-haiku-4-5",
                            "ncert_verify.v1"))));
        }
        imports.paragraphsAnswer = rows;

        run();

        assertThat(out.toString()).contains("| 7 | 3 | 0 | 0 | 0 | 0 | 0 | 0 | — (3 rows without a verdict) |");
    }

    @Test
    void theCostComesFromTheLedgerAndTheIndependenceIsStated() {
        run("--read-pages");

        assertThat(out.toString())
                .contains("verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (3 rows)")
                .contains("| calls | input | output | cache read | cache write | cost |")
                .contains("| 3 | 9000 | 3000 | 1200 | 0 | ₹4.41 |")
                .contains("verify/phy11-part1/en.jsonl");
    }

    private Map<UUID, String> filter(Collection<UUID> ids) {
        Map<UUID, String> found = new HashMap<>();
        ids.stream().filter(ledger::containsKey).forEach(id -> found.put(id, ledger.get(id)));
        return found;
    }

    private int run(String... extra) {
        List<String> args = new ArrayList<>(List.of("ncert", "verify", "--book", "phy11-part1",
                "--inputs", inputs.toString(), "--reports", reports.toString()));
        args.addAll(List.of(extra));
        return commandLine.execute(args.toArray(String[]::new));
    }

    private static List<NcertParagraphRow> rows() {
        return List.of(
                new NcertParagraphRow((short) 7, "7.1", (short) 1, "Early in our lives, we become aware of gravity.",
                        false, List.of(), new ParagraphExtraction(List.of(1), List.of(0), new BigDecimal("0.9"),
                                TRANSCRIBED_P1, null)),
                new NcertParagraphRow((short) 7, "7.1", (short) 2, STRADDLING, false, List.of(),
                        new ParagraphExtraction(List.of(1, 2), List.of(0, 24), new BigDecimal("0.9"), TRANSCRIBED_P1, null)),
                new NcertParagraphRow((short) 7, "7.2", (short) 1, VECTOR, true, List.of(),
                        new ParagraphExtraction(List.of(2), List.of(0), new BigDecimal("0.9"), TRANSCRIBED_P2, null)));
    }

    private void page(int page) {
        store.put(ContentKeys.page("phy11-part1", BookLanguage.en, (short) 7, page),
                ("page image 7/" + page).getBytes(StandardCharsets.UTF_8), "image/png");
    }

    /** A legible chapter PDF of flush-left prose, so the free checks have a layer to read. */
    private static byte[] pdf(int pages) throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument()) {
            for (int index = 0; index < pages; index++) {
                org.apache.pdfbox.pdmodel.PDPage pdPage = new org.apache.pdfbox.pdmodel.PDPage();
                document.addPage(pdPage);
                try (var content = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, pdPage)) {
                    content.beginText();
                    content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(
                            org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 11);
                    content.setLeading(14);
                    content.newLineAtOffset(50, 740);
                    content.showText("This is the text of the page and it is written in the words that we use,");
                    content.newLine();
                    content.showText("with the same of and to in a that as it for on by an which be are this.");
                    content.endText();
                }
            }
            var bytes = new java.io.ByteArrayOutputStream();
            document.save(bytes);
            return bytes.toByteArray();
        }
    }

    /** The same chapter, whose second page numbers an equation and then refers to that number. */
    private static byte[] numbering() throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument document = org.apache.pdfbox.Loader.loadPDF(pdf(2))) {
            try (var content = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, document.getPage(1),
                    org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.APPEND, true)) {
                content.beginText();
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(
                        org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 11);
                content.setLeading(14);
                content.newLineAtOffset(50, 700);
                content.showText("V^2 = G M / (R + h) (7.5)");
                content.newLine();
                content.showText("and from equation (7.5), the speed of the page is what we use it for.");
                content.endText();
            }
            var bytes = new java.io.ByteArrayOutputStream();
            document.save(bytes);
            return bytes.toByteArray();
        }
    }

    /** Stands in for the verifying model: every item matches unless a test says otherwise. */
    static final class StubVerifier implements NcertPageVerifier {

        final List<Integer> calls = new ArrayList<>();
        final List<List<VerifyItem>> items = new ArrayList<>();
        final Map<Integer, Map<Integer, PageVerdicts.ItemVerdict>> answers = new HashMap<>();
        final Map<Integer, List<Integer>> only = new HashMap<>();
        final Map<Integer, List<String>> omitted = new HashMap<>();
        String model = "claude-sonnet-5";
        String answeringModel = "claude-sonnet-5";
        Runnable onCall = () -> {
        };

        void differs(int page, int item, String printed, String transcribed) {
            answers.computeIfAbsent(page, p -> new HashMap<>()).put(item, new PageVerdicts.ItemVerdict(item,
                    PageVerdicts.Verdict.differs, List.of(new PageVerdicts.Difference(printed, transcribed))));
        }

        void notOnPage(int page, int item) {
            answers.computeIfAbsent(page, p -> new HashMap<>()).put(item,
                    new PageVerdicts.ItemVerdict(item, PageVerdicts.Verdict.not_on_page, List.of()));
        }

        @Override
        public AiResponse<PageVerdicts> verify(String bookTitle, short chapter, int page, List<ImagePart> images,
                List<VerifyItem> given, AiCallContext ctx) {
            onCall.run();
            calls.add(page);
            items.add(given);
            List<PageVerdicts.ItemVerdict> verdicts = new ArrayList<>();
            for (VerifyItem item : given) {
                if (only.containsKey(page) && !only.get(page).contains(item.number())) {
                    continue;
                }
                verdicts.add(answers.getOrDefault(page, Map.of()).getOrDefault(item.number(),
                        new PageVerdicts.ItemVerdict(item.number(), PageVerdicts.Verdict.matches, List.of())));
            }
            return new AiResponse<>(new PageVerdicts(verdicts, omitted.getOrDefault(page, List.of())), Usage.none(),
                    answeringModel, Duration.ZERO, UUID.randomUUID());
        }

        @Override
        public String model() {
            return model;
        }

        @Override
        public String promptVersion() {
            return "ncert_verify.v1";
        }
    }
}
