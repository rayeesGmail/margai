package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.AsPrintedDisplay;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.PyqLoadReport;
import com.margai.curriculum.api.PyqPaperRow;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionAsPrinted;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import com.margai.curriculum.api.QuestionTagging;
import com.margai.curriculum.api.TagStatus;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * {@code pyq load}'s import end to end against a database of its own (D19): a paper and its tagged
 * questions land with their secondary topics, a second load changes nothing, a changed tag moves the
 * question and replaces its topics, and every refusal — a node the taxonomy lacks, an id that now
 * names another paper, a secondary that repeats the primary, a section that does not hold what it
 * declares — writes nothing at all.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class PyqLoadTest {

    private static final Path TAXONOMY = InputReadersTest.INPUTS.resolve(TaxonomyLoadCommand.FILE);
    private static final UUID PAGE_CALL = UUID.fromString("00000000-0000-4000-8000-0000000000a1");
    private static final UUID TAG_CALL = UUID.fromString("00000000-0000-4000-8000-0000000000b1");

    @Autowired
    private CurriculumImport imports;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void aTaxonomyAndNoQuestions() {
        jdbc.update("DELETE FROM question_topics");
        jdbc.update("DELETE FROM questions");
        jdbc.update("DELETE FROM pyq_papers");
        if (jdbc.queryForObject("SELECT count(*) FROM syllabus_nodes WHERE code = 'PHY.11.ROT.MOI'", Integer.class) == 0) {
            imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        }
    }

    @Test
    void loadsThePaperItsQuestionsAndTheirTopicsAndASecondLoadChangesNothing() {
        PyqLoadReport first = imports.loadPaper(paper(), questions());

        assertThat(first.paperInserted()).isTrue();
        assertThat(first.inserted()).isEqualTo(4);
        assertThat(first.stored()).extracting(PyqLoadReport.StoredQuestion::no)
                .containsExactly((short) 1, (short) 2, (short) 3, (short) 4);
        assertThat(first.stored().get(1).answerStatus()).isEqualTo(AnswerStatus.multiple);
        assertThat(first.stored().get(2).answerStatus()).isEqualTo(AnswerStatus.dropped);
        assertThat(first.stored().get(0).secondaryTopics()).isEqualTo(1);
        assertThat(first.stored().get(0).hasFigure()).isTrue();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM question_topics", Integer.class)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT accepted_keys::text FROM questions WHERE question_no = 2", String.class))
                .isEqualTo("{1,2}");
        assertThat(first.stored()).extracting(PyqLoadReport.StoredQuestion::asPrintedShow).containsExactly(1, 0, 0, 0);
        assertThat(first.stored()).extracting(PyqLoadReport.StoredQuestion::asPrintedRecordOnly).containsExactly(1, 0, 0, 0);
        assertThat(jdbc.queryForObject("SELECT as_printed->0->>'intended' FROM questions WHERE question_no = 1", String.class))
                .isEqualTo("The question");
        assertThat(jdbc.queryForObject("SELECT as_printed->0->>'display' FROM questions WHERE question_no = 1", String.class))
                .isEqualTo("record_only");
        assertThat(jdbc.queryForObject("SELECT as_printed::text FROM questions WHERE question_no = 2", String.class))
                .isEqualTo("[]");
        assertThat(first.stored()).extracting(PyqLoadReport.StoredQuestion::tagStatus)
                .containsExactly(TagStatus.model, TagStatus.model, TagStatus.model, TagStatus.review);
        assertThat(jdbc.queryForList("SELECT tag_status FROM questions ORDER BY question_no", String.class))
                .containsExactly("model", "model", "model", "review");

        PyqLoadReport second = imports.loadPaper(paper(), questions());

        assertThat(second.paperInserted()).isFalse();
        assertThat(second.paperUpdated()).isFalse();
        assertThat(second.unchanged()).isEqualTo(4);
        assertThat(second.updated()).isZero();
    }

    /** A note on a misprint kept as printed is the question's own data: a changed note updates the row. */
    @Test
    void aChangedAsPrintedNoteUpdatesTheQuestion() {
        imports.loadPaper(paper(), questions());
        List<PyqQuestionRow> renoted = new ArrayList<>(questions());
        PyqQuestionRow first = renoted.getFirst();
        renoted.set(0, new PyqQuestionRow(first.questionNo(), first.nodeCode(), first.secondaryNodeCodes(), first.outOfSyllabus(),
                first.stemEn(), first.stemHi(), first.options(), first.answerStatus(), first.correctKey(), first.acceptedKeys(),
                first.figures(), first.extraction(), first.tagging(),
                List.of(new QuestionAsPrinted("stem", "Question", "The question", "the article 'The' is missing",
                        AsPrintedDisplay.record_only)), first.tagStatus()));

        PyqLoadReport again = imports.loadPaper(paper(), renoted);

        assertThat(again.updated()).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT as_printed->0->>'note' FROM questions WHERE question_no = 1", String.class))
                .isEqualTo("the article 'The' is missing");
    }

    @Test
    void aChangedTagMovesTheQuestionAndReplacesItsTopics() {
        imports.loadPaper(paper(), questions());
        List<PyqQuestionRow> retagged = new ArrayList<>(questions());
        retagged.set(0, question(1, "PHY.11.ROT.TORQUE", List.of("PHY.11.ROT.CONSANG"), AnswerStatus.single, "3", null));

        PyqLoadReport result = imports.loadPaper(paper(), retagged);

        assertThat(result.updated()).isEqualTo(1);
        assertThat(result.stored().getFirst().nodeCode()).isEqualTo("PHY.11.ROT.TORQUE");
        assertThat(jdbc.queryForList("SELECT n.code FROM question_topics t JOIN syllabus_nodes n ON n.id = t.node_id",
                String.class)).containsExactly("PHY.11.ROT.CONSANG");
    }

    @Test
    void aNodeTheTaxonomyLacksWritesNothing() {
        List<PyqQuestionRow> rows = new ArrayList<>(questions());
        rows.set(3, question(4, "PHY.11.NOPE", List.of(), AnswerStatus.single, "1", null));

        assertThatThrownBy(() -> imports.loadPaper(paper(), rows))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("[PHY.11.NOPE]");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM pyq_papers", Integer.class)).isZero();
    }

    @Test
    void aSectionThatDoesNotHoldWhatItDeclaresWritesNothing() {
        assertThatThrownBy(() -> imports.loadPaper(paper(), questions().subList(0, 3)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("chemistry: 1 loaded, 2 declared");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM questions", Integer.class)).isZero();
    }

    @Test
    void anIdThatNowNamesAnotherPaperIsRefused() {
        imports.loadPaper(paper(), questions());
        PyqPaperRow other = PyqAssemblyTest.paper(4, List.of()).row();
        PyqPaperRow moved = new PyqPaperRow(other.code(), other.exam(), other.year(), "46", other.examDate(),
                other.officialHindi(), other.questionCount(), other.sections(), other.marking(), other.keySource(), other.source());

        assertThatThrownBy(() -> imports.loadPaper(moved, questions()))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("a paper's identity never changes");
    }

    /** The loader checks a tag against its own database's taxonomy, never trusting the tag run's verdict. */
    @Test
    void aTagThatFailsTheTaxonomyHereIsRefused() {
        List<PyqQuestionRow> wrongSubject = new ArrayList<>(questions());
        wrongSubject.set(0, question(1, "CHE.11.BASICS.MOLE", List.of(), AnswerStatus.single, "3", null));
        assertThatThrownBy(() -> imports.loadPaper(paper(), wrongSubject))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("is printed under physics");

        List<PyqQuestionRow> outOnATopic = new ArrayList<>(questions());
        PyqQuestionRow first = question(1, "PHY.11.ROT.MOI", List.of(), AnswerStatus.single, "3", null);
        outOnATopic.set(0, new PyqQuestionRow(first.questionNo(), first.nodeCode(), first.secondaryNodeCodes(), true,
                first.stemEn(), first.stemHi(), first.options(), first.answerStatus(), first.correctKey(), first.acceptedKeys(),
                first.figures(), first.extraction(), first.tagging(), first.asPrinted(), first.tagStatus()));
        assertThatThrownBy(() -> imports.loadPaper(paper(), outOnATopic))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("it keeps the nearest chapter");

        List<PyqQuestionRow> onAUnit = new ArrayList<>(questions());
        onAUnit.set(0, question(1, "PHY.U05", List.of(), AnswerStatus.single, "3", null));
        assertThatThrownBy(() -> imports.loadPaper(paper(), onAUnit))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("a tag is a chapter or a topic");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM questions", Integer.class)).isZero();
    }

    @Test
    void aQuestionInTheBankButNotInTheFileRefusesTheLoad() {
        imports.loadPaper(paper(), questions());

        assertThatThrownBy(() -> imports.loadPaper(paper(), questions().subList(0, 3)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("questions [4] of this paper are in the bank but not in the file");
    }

    @Test
    void aSecondaryThatRepeatsThePrimaryIsRefused() {
        List<PyqQuestionRow> rows = new ArrayList<>(questions());
        rows.set(0, question(1, "PHY.11.ROT.MOI", List.of("PHY.11.ROT.MOI"), AnswerStatus.single, "3", null));

        assertThatThrownBy(() -> imports.loadPaper(paper(), rows))
                .isInstanceOf(CurriculumImportException.class).hasMessageContaining("never the primary");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM questions", Integer.class)).isZero();
    }

    /** The four-question paper of {@link PyqAssemblyTest#paper}: physics 1–3, chemistry 4 — here 1–2 and 3–4. */
    private static PyqPaperRow paper() {
        PyqPaperRow row = PyqAssemblyTest.paper(4, List.of()).row();
        return new PyqPaperRow(row.code(), row.exam(), row.year(), row.paperCode(), row.examDate(), row.officialHindi(),
                row.questionCount(), List.of(
                        new com.margai.curriculum.api.PaperSection(com.margai.curriculum.api.PaperSubject.physics, null,
                                (short) 1, (short) 2, (short) 2),
                        new com.margai.curriculum.api.PaperSection(com.margai.curriculum.api.PaperSubject.chemistry, null,
                                (short) 3, (short) 4, (short) 2)),
                row.marking(), row.keySource(), row.source());
    }

    private static List<PyqQuestionRow> questions() {
        return List.of(
                question(1, "PHY.11.ROT.MOI", List.of("PHY.11.ROT.COM"), AnswerStatus.single, "3", null),
                question(2, "PHY.11.ROT", List.of(), AnswerStatus.multiple, "1", List.of("1", "2")),
                question(3, "CHE.11.BASICS.MOLE", List.of(), AnswerStatus.dropped, null, null),
                question(4, "CHE.11.BASICS.MOLE", List.of(), AnswerStatus.single, "2", null));
    }

    private static PyqQuestionRow question(int no, String node, List<String> secondary, AnswerStatus status, String key,
            List<String> accepted) {
        return new PyqQuestionRow((short) no, node, secondary, false, "Question " + no, null,
                List.of(new QuestionOption("1", "a", null), new QuestionOption("2", "b", null),
                        new QuestionOption("3", "c", null), new QuestionOption("4", "d", null)),
                status, key, accepted,
                no == 1 ? List.of(new QuestionFigure("stem", 3, "a uniform rod pivoted at one end")) : List.of(),
                new QuestionExtraction(List.of(3), List.of(PAGE_CALL), "claude-opus-5-5", "pyq_extract.v1", List.of()),
                new QuestionTagging(TAG_CALL, "claude-sonnet-5-5", "pyq_tag.v1", new BigDecimal("0.90"), "a reason"),
                no == 1 ? List.of(
                        new QuestionAsPrinted("stem", "Question", "The question", "'The' is missing before 'Question'",
                                AsPrintedDisplay.record_only),
                        new QuestionAsPrinted("stem", "Question " + no, "Question 2", "'1' is printed where '2' is meant",
                                AsPrintedDisplay.show))
                        : List.of(),
                no == 4 ? TagStatus.review : TagStatus.model);
    }
}
