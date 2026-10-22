package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.Usage;
import com.margai.ai.tasks.PyqTag;
import com.margai.ai.tasks.QuestionTagger;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code pyq tag} over a four-question approved file with a recording tagger in place of the model:
 * each printed subject is tagged with its own outline, every tag is checked against the taxonomy and
 * one that fails is kept with its problem, a second run calls only for what has no valid tag, and
 * nothing is called for the fake client, a missing approved file or one that fails its register.
 */
class PyqTagCommandTest {

    static final String CODE = "neet-2025-45";

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertRenderCommandTest.RecordingStore store = new NcertRenderCommandTest.RecordingStore();
    private final RecordingTagger tagger = new RecordingTagger();
    private AiClientInfo client = new AiClientInfo("anthropic", List.of("ledger", "breaker"));
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        writeInputs(inputs, approved(PaperFile.Answer.single("2")));
        tagger.tags.put(1, new PyqTag("PHY.11.ROT.MOI", List.of("PHY.11.ROT.COM"), false, new BigDecimal("0.93"), "moment of inertia"));
        tagger.tags.put(2, new PyqTag("PHY.11.ROT", List.of(), true, new BigDecimal("0.62"), "a topic since removed"));
        tagger.tags.put(3, new PyqTag("CHE.11.BASICS.MOLE", List.of(), false, new BigDecimal("0.9"), "the mole"));
        tagger.tags.put(4, new PyqTag("PHY.11.ROT.MOI", List.of(), false, new BigDecimal("0.9"), "printed under chemistry"));
        build();
    }

    @Test
    void tagsEachSubjectWithItsOutlineAndKeepsABadTagWithItsProblem() {
        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(tagger.numbers).containsExactly(List.of(1, 2), List.of(3, 4));
        assertThat(tagger.outlines.getFirst().getFirst().name()).isEqualTo("Physics");
        assertThat(tagger.outlines.getFirst()).noneMatch(line -> "Chemistry".equals(line.name()));
        assertThat(tagger.outlines.getLast().getFirst().name()).isEqualTo("Chemistry");
        List<PyqTagLine> lines = PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(CODE)));
        assertThat(lines).extracting(PyqTagLine::valid).containsExactly(true, true, true, false);
        assertThat(lines.getLast().problem()).contains("is physics, but the question is printed under chemistry");
        assertThat(out.toString()).contains("Q4: the primary PHY.11.ROT.MOI is physics")
                .contains("Q2 → PHY.11.ROT: a topic since removed")
                .contains("| physics | PHY.11.ROT Systems of Particles and Rotational Motion | 2 |")
                .contains("the founder's sample");
    }

    @Test
    void aSecondRunCallsOnlyForTheQuestionWithoutAValidTag() {
        run();
        tagger.numbers.clear();
        tagger.tags.put(4, new PyqTag("CHE.11.BASICS", List.of(), false, new BigDecimal("0.8"), "fixed"));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(tagger.numbers).containsExactly(List.of(4));
        assertThat(PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(CODE)))).allMatch(PyqTagLine::valid);

        assertThat(run("--redo", "--questions", "1")).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(tagger.numbers.getLast()).containsExactly(1);
    }

    /** A stem corrected after tagging (README §1 ruling 5's transcription checks) is tagged again. */
    @Test
    void aQuestionWhoseWordsChangedIsTaggedAgain() throws IOException {
        run();
        tagger.numbers.clear();
        PaperFile corrected = PyqTagCommandTest.approved(PaperFile.Answer.single("2"));
        List<PaperFile.Question> questions = new ArrayList<>(corrected.questions());
        PaperFile.Question first = questions.getFirst();
        questions.set(0, new PaperFile.Question(first.no(), "Question 1, corrected against the page", null, first.options(),
                first.figures(), first.answer(), first.extraction(), first.asPrinted()));
        writeInputs(inputs, new PaperFile(corrected.paper(), corrected.transcribedFrom(), questions));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(tagger.numbers).containsExactly(List.of(1), List.of(4));
    }

    /**
     * {@code pyq_tag.v2}: a figure question reaches the tagger with each figure's description where the booklet
     * prints it, and only a question that prints figures changes its fingerprint — a figure-free question hashes
     * as under v1, so its tag stays current and only the figure questions are re-tagged.
     */
    @Test
    void aFigureQuestionReachesTheTaggerWithItsFiguresAndOnlyItsFingerprintChanges() throws IOException {
        PaperFile file = approved(PaperFile.Answer.single("2"));
        List<PaperFile.Question> questions = new ArrayList<>(file.questions());
        PaperFile.Question first = questions.getFirst();
        String before = PyqTagCommand.textSha(first);
        PaperFile.Question figured = new PaperFile.Question(first.no(), first.stemEn(), null, first.options(),
                List.of(new QuestionFigure("stem", 1, "a rod pivoted at one end"), new QuestionFigure("option:2", 1, "a disc")),
                first.answer(), first.extraction(), first.asPrinted());
        questions.set(0, figured);
        writeInputs(inputs, new PaperFile(file.paper(), file.transcribedFrom(), questions));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        QuestionTagger.Question asked = tagger.asked.getFirst();
        assertThat(asked.stemFigures()).containsExactly("a rod pivoted at one end");
        assertThat(asked.options()).extracting(QuestionTagger.Option::figures)
                .containsExactly(List.of(), List.of("a disc"), List.of(), List.of());
        assertThat(PyqTagCommand.textSha(figured)).isNotEqualTo(before);
        assertThat(PyqTagCommand.textSha(questions.get(1))).isEqualTo(PyqExtractCommand.sha256(
                "Question 2\n1\ta\n2\tb\n3\tc\n4\td".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
    }

    /**
     * A question whose answer can never be decoded is kept as a failed line and the rest of its batch is
     * tagged; the batch is retried question by question, so its other calls are paid twice that run.
     */
    @Test
    void aQuestionWhoseAnswerCannotBeReadIsKeptAsFailedAndTheRestOfItsBatchIsTagged() {
        tagger.failing.add(2);

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_OK);

        assertThat(tagger.numbers).containsExactly(List.of(1, 2), List.of(1), List.of(2), List.of(3, 4));
        List<PyqTagLine> lines = PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(CODE)));
        assertThat(lines.get(0).valid()).isTrue();
        assertThat(lines.get(1).problem()).startsWith("the call failed: the answer could not be read")
                .contains("no tag for 2");
        assertThat(lines.get(1).primary()).isNull();
        assertThat(out.toString()).contains("Q2: the call failed").contains("cost (from the ai_calls ledger)");
    }

    /** When every question of a batch fails again alone, the failure is the run's: it stops rather than pay twice for every batch. */
    @Test
    void aBatchWhoseEveryQuestionFailsAloneStopsTheRun() {
        tagger.failing.addAll(List.of(1, 2));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(tagger.numbers).containsExactly(List.of(1, 2), List.of(1), List.of(2));
        assertThat(out.toString()).contains("questions tagged before the run stopped").contains("cost (from the ai_calls ledger)");
    }

    /** The budget breaker is the run's failure, never a question's: the run stops, keeps what it paid for, reports the bill. */
    @Test
    void theBudgetBreakerStopsTheRunLoudlyAndKeepsTheTagsPaidFor() {
        tagger.breaking.add(3);

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(tagger.numbers).containsExactly(List.of(1, 2), List.of(3, 4));
        assertThat(PyqTagJsonl.read("k", store.get(ContentKeys.pyqTags(CODE))))
                .extracting(PyqTagLine::question).containsExactly((short) 1, (short) 2);
        assertThat(out.toString()).contains("questions tagged before the run stopped")
                .contains("cost (from the ai_calls ledger)").contains("AiBudgetExceededException");
    }

    /** A committed report reads the same on every machine (Report): the approved file is named as given. */
    @Test
    void namesTheApprovedFileAsGiven() {
        Path relative = Path.of("").toAbsolutePath().relativize(inputs);

        int exit = commandLine.execute("pyq", "tag", "--paper", CODE, "--inputs", relative.toString(),
                "--reports", reports.toString());

        assertThat(exit).isEqualTo(InputFileCommand.EXIT_OK);
        assertThat(out.toString())
                .contains("approved file: " + relative.resolve("papers").resolve(CODE + ".json").normalize() + " (4 questions)");
    }

    @Test
    void refusesAReasoningModelThatIsNotThePyqTagger() {
        tagger.model = "claude-haiku-5-5";

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(tagger.numbers).isEmpty();
        assertThat(out.toString()).contains("the reasoning tier is claude-haiku-5-5, not claude-sonnet-5-5");
    }

    @Test
    void refusesTheFakeClient() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));
        build();

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(tagger.numbers).isEmpty();
        assertThat(out.toString()).contains("the AI client is the fake");
    }

    @Test
    void refusesAnApprovedFileThatFailsItsRegister() throws IOException {
        writeInputs(inputs, approved(null));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(tagger.numbers).isEmpty();
        assertThat(out.toString()).contains("fails its register entry").contains("question 4 has no answer");
    }

    @Test
    void refusesWithoutAnApprovedFile() throws IOException {
        Files.delete(inputs.resolve("papers").resolve(CODE + ".json"));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(out.toString()).contains("the draft is checked, approved and committed");
    }

    private int run(String... extra) {
        List<String> args = new ArrayList<>(List.of("pyq", "tag", "--paper", CODE,
                "--inputs", inputs.toString(), "--reports", reports.toString()));
        args.addAll(List.of(extra));
        return commandLine.execute(args.toArray(String[]::new));
    }

    private void build() {
        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(new PipelineCommandTest.RecordingImport(), writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == PyqTagCommand.class) {
                    return cls.cast(new PyqTagCommand(store, tagger, new NcertExtractCommandTest.StubSpend(), client,
                            PyqExtractCommandTest.pyq(), writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    /** The register of a four-question paper (physics 1–2, chemistry 3–4), the taxonomy, and the approved file. */
    static void writeInputs(Path inputs, PaperFile approved) throws IOException {
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
                      - file: source/neet-pyq-sources/2025/test-booklet.pdf
                        sha256: %1$s
                        languages: hi-en
                        pages: 1-2
                    sections:
                      - {subject: physics, from: 1, to: 2}
                      - {subject: chemistry, from: 3, to: 4}
                    marking: {correct: 4, wrong: -1, unanswered: 0, scheme_source: booklet p1}
                    key:
                      file: source/neet-pyq-sources/2025/test-key.pdf
                      sha256: %1$s
                      tier: mirrored-official
                      from: a mirror
                      downloaded: 2026-10-20
                      special:
                        - {question: 2, accepted: [1, 2]}
                        - {question: 3, dropped: true}
                """.formatted("c".repeat(64)));
        Files.copy(Path.of(InputsMixin.DEFAULT_INPUTS).resolve(TaxonomyLoadCommand.FILE),
                inputs.resolve(TaxonomyLoadCommand.FILE), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        Files.createDirectories(inputs.resolve("papers"));
        Files.writeString(inputs.resolve("papers").resolve(CODE + ".json"), PaperFileJson.write(approved));
    }

    /** Four questions: 1 single, 2 multiple {1,2}, 3 dropped, 4 {@code fourth}. */
    static PaperFile approved(PaperFile.Answer fourth) {
        List<PaperFile.Answer> answers = new ArrayList<>(List.of(PaperFile.Answer.single("3"),
                PaperFile.Answer.multiple(List.of("2", "1")), PaperFile.Answer.dropped()));
        answers.add(fourth);
        List<PaperFile.Question> questions = new ArrayList<>();
        for (int no = 1; no <= 4; no++) {
            questions.add(new PaperFile.Question((short) no, "Question " + no, null,
                    List.of(new QuestionOption("1", "a", null), new QuestionOption("2", "b", null),
                            new QuestionOption("3", "c", null), new QuestionOption("4", "d", null)),
                    List.of(), answers.get(no - 1),
                    new QuestionExtraction(List.of(1), List.of(UUID.fromString("00000000-0000-4000-8000-0000000000a1")),
                            "claude-opus-5-5", "pyq_extract.v1", List.of()), null));
        }
        return new PaperFile(CODE, "source/neet-pyq-sources/2025/test-booklet.pdf", questions);
    }

    static final class RecordingTagger implements QuestionTagger {

        final Map<Integer, PyqTag> tags = new HashMap<>();
        final List<List<Integer>> numbers = new ArrayList<>();
        final List<Question> asked = new ArrayList<>();
        final List<List<OutlineLine>> outlines = new ArrayList<>();
        /** Questions whose answer can never be decoded, failing any batch they are in, as completeBatch rethrows. */
        final java.util.Set<Integer> failing = new java.util.HashSet<>();
        /** Questions whose call meets the budget breaker. */
        final java.util.Set<Integer> breaking = new java.util.HashSet<>();
        String model = "claude-sonnet-5-5";

        @Override
        public List<AiResponse<PyqTag>> tag(List<OutlineLine> outline, List<Question> questions, AiCallContext ctx) {
            outlines.add(outline);
            numbers.add(questions.stream().map(Question::number).toList());
            asked.addAll(questions);
            questions.stream().filter(question -> failing.contains(question.number())).findFirst().ifPresent(question -> {
                throw new com.margai.ai.api.InvalidOutputException(List.of("no tag for " + question.number()), "{}",
                        new Usage(10, 5, 0, 0), "claude-sonnet-5-5");
            });
            questions.stream().filter(question -> breaking.contains(question.number())).findFirst().ifPresent(question -> {
                throw new com.margai.ai.api.AiBudgetExceededException(
                        com.margai.ai.api.AiBudgetExceededException.Scope.global, 1000, 1000);
            });
            return questions.stream().map(question -> new AiResponse<>(
                    tags.getOrDefault(question.number(), new PyqTag("PHY.11.ROT", List.of(), false, BigDecimal.ONE, "r")),
                    new Usage(100, 20, 0, 0), "claude-sonnet-5-5", Duration.ofMillis(3), UUID.randomUUID())).toList();
        }

        @Override
        public String model() {
            return model;
        }

        @Override
        public String promptVersion() {
            return "pyq_tag.v1";
        }
    }

    /** The answer statuses the fixture's questions carry, for the load test. */
    static final List<AnswerStatus> STATUSES = List.of(AnswerStatus.single, AnswerStatus.multiple, AnswerStatus.dropped,
            AnswerStatus.single);
}
