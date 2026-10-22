package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.AsPrintedDisplay;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionAsPrinted;
import com.margai.curriculum.api.TagRuling;
import com.margai.curriculum.api.TagStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code pyq load} over the four-question approved file and its tags, with the curriculum module
 * recorded: each question goes over with its tag and NTA's answer in the shape the database holds —
 * a multiple-answer question's lowest key as its key and every accepted key sorted, a dropped one with
 * neither — the report's first table is the D19 count per printed section, the marking rules are
 * named pending, and nothing is loaded while a question lacks a valid tag.
 */
class PyqLoadCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final PipelineCommandTest.RecordingImport imports = new PipelineCommandTest.RecordingImport();
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        PyqTagCommandTest.writeInputs(inputs, PyqTagCommandTest.approved(PaperFile.Answer.single("2")));
        tags(true);
        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == PyqLoadCommand.class) {
                    return cls.cast(new PyqLoadCommand(store, imports, PyqExtractCommandTest.pyq(), writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    @Test
    void loadsEveryQuestionWithItsTagAndNtasAnswer() {
        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(imports.loadedPaper.code()).isEqualTo(PyqTagCommandTest.CODE);
        List<PyqQuestionRow> rows = imports.loadedQuestions;
        assertThat(rows).extracting(PyqQuestionRow::answerStatus).containsExactlyElementsOf(PyqTagCommandTest.STATUSES);
        assertThat(rows.get(1).correctKey()).isEqualTo("1");
        assertThat(rows.get(1).acceptedKeys()).containsExactly("1", "2");
        assertThat(rows.get(2).correctKey()).isNull();
        assertThat(rows.get(2).acceptedKeys()).isNull();
        assertThat(rows.getFirst().nodeCode()).isEqualTo("PHY.11.ROT.MOI");
        assertThat(rows.getFirst().secondaryNodeCodes()).containsExactly("PHY.11.ROT.COM");
        assertThat(rows.getFirst().tagging().model()).isEqualTo("claude-sonnet-5-5");
        assertThat(out.toString()).contains("the D19 check")
                .contains("| physics | — | 2 | 2 | 0 | 0 | yes |")
                .contains("| chemistry | — | 2 | 2 | 0 | 0 | yes |")
                .contains("Q2: multiple").contains("Q3: dropped")
                .contains("PENDING — the dropped and multiple-answer rules: NTA's notice for 2025 is not sourced")
                .contains("misprints kept as printed");
        assertThat(rows).allSatisfy(row -> assertThat(row.asPrinted()).isEmpty());
    }

    /** A misprint kept as printed goes over with its question, and the report counts it as the database holds it. */
    @Test
    void carriesTheMisprintsKeptAsPrinted() throws IOException {
        PaperFile approved = PyqTagCommandTest.approved(PaperFile.Answer.single("2"));
        List<PaperFile.Question> questions = new ArrayList<>(approved.questions());
        PaperFile.Question first = questions.getFirst();
        questions.set(0, new PaperFile.Question(first.no(), first.stemEn(), first.stemHi(), first.options(), first.figures(),
                first.answer(), first.extraction(),
                List.of(new QuestionAsPrinted("stem", "Question", "The question", "'The' is missing before 'Question'",
                                AsPrintedDisplay.record_only),
                        new QuestionAsPrinted("stem", "Question 1", "Question 2", "'1' is printed where '2' is meant",
                                AsPrintedDisplay.show))));
        PyqTagCommandTest.writeInputs(inputs, new PaperFile(approved.paper(), approved.transcribedFrom(), questions));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(imports.loadedQuestions.getFirst().asPrinted()).extracting(QuestionAsPrinted::display)
                .containsExactly(AsPrintedDisplay.record_only, AsPrintedDisplay.show);
        assertThat(out.toString()).contains("Q1: 1 show, 1 record_only").contains("show 1, record_only 1");
    }

    /**
     * A tag strictly below the review floor (0.5, the founder's ruling of 2026-10-21) loads as {@code review}, never as
     * a normal tag; a tag at the floor is the tagger's; the report counts both and lists the review ones.
     */
    @Test
    void aTagBelowTheReviewFloorLoadsAsReview() {
        List<PyqTagLine> lines = new ArrayList<>(PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(PyqTagCommandTest.CODE))));
        lines.set(0, confidence(lines.get(0), "0.49"));
        lines.set(1, confidence(lines.get(1), "0.5"));
        store.put(ContentKeys.pyqTags(PyqTagCommandTest.CODE), PyqTagJsonl.write(lines), "application/jsonl");

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(imports.loadedQuestions).extracting(PyqQuestionRow::tagStatus)
                .containsExactly(TagStatus.review, TagStatus.model, TagStatus.model, TagStatus.model);
        assertThat(out.toString()).contains("whose tag each question carries").contains("model 3, review 1, founder 0")
                .contains("Q1 → PHY.11.ROT.MOI: review");
    }

    /**
     * A question the founder ruled loads his ruling as {@code founder}, whole, with the tagger's tag of its current
     * words kept beside it; a ruled question needs no valid tag of its own; the report lists the rulings, the
     * taxonomy-gap list and the questions with no NCERT anchor (DECISIONS 2026-10-21).
     */
    @Test
    void aRulingLoadsInPlaceOfTheTaggersTag() throws IOException {
        tags(false);
        Files.writeString(inputs.resolve(PyqTagRulingsYamlReader.FILE), """
                papers:
                  - code: neet-2025-45
                    approved_file_sha256: %s
                    rulings:
                      - question: 1
                        primary: PHY.11.ROT
                        anchor: phy11-part1 ch 6 §6.1
                        gap: PHY.11.ROT.NEW — a proposed node
                        ruled: 2026-10-21
                        note: the chapter, not the topic
                      - question: 4
                        primary: CHE.11.BASICS
                        anchor: none
                        ruled: 2026-10-22
                        note: NCERT cut it and the syllabus lists it
                """.formatted(Report.sha256(inputs.resolve("papers").resolve(PyqTagCommandTest.CODE + ".json"))));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        List<PyqQuestionRow> rows = imports.loadedQuestions;
        assertThat(rows).extracting(PyqQuestionRow::tagStatus)
                .containsExactly(TagStatus.founder, TagStatus.model, TagStatus.model, TagStatus.founder);
        assertThat(rows.getFirst().nodeCode()).isEqualTo("PHY.11.ROT");
        assertThat(rows.getFirst().secondaryNodeCodes()).isEmpty();
        TagRuling kept = rows.getFirst().tagging().ruling();
        assertThat(kept.replacedPrimary()).isEqualTo("PHY.11.ROT.MOI");
        assertThat(kept.replacedSecondary()).containsExactly("PHY.11.ROT.COM");
        assertThat(kept.replacedOutOfSyllabus()).isFalse();
        assertThat(kept.anchor()).isEqualTo("phy11-part1 ch 6 §6.1");
        assertThat(kept.ruled()).isEqualTo("2026-10-21");
        assertThat(rows.getFirst().tagging().aiCallId()).isNotNull();
        assertThat(rows.get(3).tagging().ruling().replacedPrimary()).isNull();
        assertThat(rows.get(3).tagging().aiCallId()).isNull();
        String report = out.toString();
        assertThat(report).contains("(2 for this paper)").contains("model 2, review 0, founder 2")
                .contains("- Q1 → PHY.11.ROT (ruled 2026-10-21; the tagger's PHY.11.ROT.MOI, 0.9): the chapter, not the topic")
                .contains("- Q4 → CHE.11.BASICS (ruled 2026-10-22; the tagger has no tag of its current words)")
                .contains("- Q1: PHY.11.ROT.NEW — a proposed node");
        assertThat(report.substring(report.indexOf("## no NCERT anchor"))).startsWith("## no NCERT anchor")
                .contains("this paper: 1 of 4 (25.0%)").contains("- Q4")
                .contains("the bank: 1 of 4 (25.0%) — ABOVE the founder's ~2% line: for him to revisit the policy");
    }

    /** The founder's ~2% line (2026-10-22): flagged strictly above it, within it otherwise. */
    @Test
    void theBankLineFlagsNoAnchorQuestionsAboveTheFoundersShare() {
        BigDecimal share = new BigDecimal("0.02");

        assertThat(PyqLoadCommand.bankLine(1, 180, share)).isEqualTo("the bank: 1 of 180 (0.6%) — within the founder's ~2% line");
        assertThat(PyqLoadCommand.bankLine(36, 1800, share)).contains("36 of 1800 (2.0%) — within");
        assertThat(PyqLoadCommand.bankLine(37, 1800, share)).contains("ABOVE the founder's ~2% line");
        assertThat(PyqLoadCommand.bankLine(0, 0, share)).isEqualTo("the bank: 0 of 0 (0%) — within the founder's ~2% line");
    }

    private static PyqTagLine confidence(PyqTagLine line, String confidence) {
        return new PyqTagLine(line.question(), line.primary(), line.secondary(), line.outOfSyllabus(), new BigDecimal(confidence),
                line.reason(), line.aiCallId(), line.model(), line.prompt(), line.questionSha256(), line.problem());
    }

    /** A committed report reads the same on every machine (Report): the approved file is named as given. */
    @Test
    void namesTheApprovedFileAsGiven() {
        Path relative = Path.of("").toAbsolutePath().relativize(inputs);

        int exit = commandLine.execute("pyq", "load", "--paper", PyqTagCommandTest.CODE, "--inputs", relative.toString(),
                "--reports", reports.toString());

        assertThat(exit).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(out.toString())
                .contains("approved file: " + relative.resolve("papers").resolve(PyqTagCommandTest.CODE + ".json").normalize());
    }

    @Test
    void loadsNothingWhileAQuestionLacksAValidTag() {
        tags(false);

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(imports.loadedQuestions).isNull();
        assertThat(out.toString()).contains("1 question(s) without a valid tag").contains("Q4: a problem");
    }

    @Test
    void loadsNothingFromAnApprovedFileWhoseAnswersDisagreeWithTheRegister() throws IOException {
        PyqTagCommandTest.writeInputs(inputs, PyqTagCommandTest.approved(PaperFile.Answer.dropped()));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(imports.loadedQuestions).isNull();
        assertThat(out.toString()).contains("question 4 is dropped in the file but not among the register's special entries");
    }

    private int run() {
        return commandLine.execute("pyq", "load", "--paper", PyqTagCommandTest.CODE, "--inputs", inputs.toString(),
                "--reports", reports.toString());
    }

    @Test
    void loadsNothingWhoseTagWasMadeOfOtherWords() {
        List<PyqTagLine> lines = new ArrayList<>(PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(PyqTagCommandTest.CODE))));
        PyqTagLine first = lines.getFirst();
        lines.set(0, new PyqTagLine(first.question(), first.primary(), first.secondary(), first.outOfSyllabus(),
                first.confidence(), first.reason(), first.aiCallId(), first.model(), first.prompt(), "0".repeat(64), null));
        store.put(ContentKeys.pyqTags(PyqTagCommandTest.CODE), PyqTagJsonl.write(lines), "application/jsonl");

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(imports.loadedQuestions).isNull();
        assertThat(out.toString()).contains("Q1 was tagged on other words than the approved file has now");
    }

    /** The tag artefact, every tag valid unless {@code allValid} is false, when question 4's carries a problem. */
    private void tags(boolean allValid) {
        List<PaperFile.Question> questions = PyqTagCommandTest.approved(PaperFile.Answer.single("2")).questions();
        List<PyqTagLine> lines = new ArrayList<>();
        UUID call = UUID.fromString("00000000-0000-4000-8000-0000000000b1");
        lines.add(new PyqTagLine((short) 1, "PHY.11.ROT.MOI", List.of("PHY.11.ROT.COM"), false, new BigDecimal("0.9"), "r",
                call, "claude-sonnet-5-5", "pyq_tag.v1", PyqTagCommand.textSha(questions.get(0)), null));
        lines.add(new PyqTagLine((short) 2, "PHY.11.ROT", List.of(), false, new BigDecimal("0.9"), "r", call,
                "claude-sonnet-5-5", "pyq_tag.v1", PyqTagCommand.textSha(questions.get(1)), null));
        lines.add(new PyqTagLine((short) 3, "CHE.11.BASICS.MOLE", List.of(), false, new BigDecimal("0.9"), "r", call,
                "claude-sonnet-5-5", "pyq_tag.v1", PyqTagCommand.textSha(questions.get(2)), null));
        lines.add(new PyqTagLine((short) 4, "CHE.11.BASICS", List.of(), false, new BigDecimal("0.9"), "r", call,
                "claude-sonnet-5-5", "pyq_tag.v1", PyqTagCommand.textSha(questions.get(3)), allValid ? null : "a problem"));
        store.put(ContentKeys.pyqTags(PyqTagCommandTest.CODE), PyqTagJsonl.write(lines), "application/jsonl");
    }
}
