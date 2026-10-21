package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionAsPrinted;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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
                List.of(new QuestionAsPrinted("stem", "Question", "The question", "'The' is missing before 'Question'"))));
        PyqTagCommandTest.writeInputs(inputs, new PaperFile(approved.paper(), approved.transcribedFrom(), questions));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(imports.loadedQuestions.getFirst().asPrinted()).extracting(QuestionAsPrinted::intended)
                .containsExactly("The question");
        assertThat(out.toString()).contains("Q1: 1 note(s)");
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
