package com.margai.curriculum.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.KeyProvenance;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PaperMarking;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSourceFile;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.PyqPaperRow;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import com.margai.curriculum.api.QuestionSource;
import com.margai.curriculum.api.QuestionTagging;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.TagStatus;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Repository slice (TECH_PLAN §8.1): migration V9 applies and its constraints hold — the paper's
 * identity, the PYQ natural key and its paper, the answer-status check in every direction the
 * founder's D19 rulings gave it, the '1'–'4' key domain, and the secondary-topic key — and V11's tag status. The JSONB
 * columns round-trip as their records, and the answer never serialises from the entity.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class QuestionConstraintsTest {

    @Autowired
    private SyllabusNodeRepository nodes;

    @Autowired
    private PyqPaperRepository papers;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private QuestionTopicRepository topics;

    @Autowired
    private EntityManager entityManager;

    private UUID chapter;

    private UUID otherChapter;

    @BeforeEach
    void aPaperAndTwoChapters() {
        SyllabusNode physics = nodes.saveAndFlush(
                new SyllabusNode("QPHY", Subject.physics, null, null, NodeKind.subject, "Physics", 1));
        chapter = nodes.saveAndFlush(new SyllabusNode("QPHY.11.ROT", Subject.physics, (short) 11, physics.getId(),
                NodeKind.chapter, "Rotational Motion", 1)).getId();
        otherChapter = nodes.saveAndFlush(new SyllabusNode("QPHY.11.KIN", Subject.physics, (short) 11, physics.getId(),
                NodeKind.chapter, "Motion in a Straight Line", 2)).getId();
        papers.saveAndFlush(new PyqPaper(paper("neet-2025-45", "45")));
    }

    @Test
    void aPaperIsOneBookletCodeOfOneSitting() {
        assertThatThrownBy(() -> papers.saveAndFlush(new PyqPaper(paper("neet-2025-45-again", "45"))))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("pyq_papers_paper_key");
    }

    @Test
    void thePaperRoundTripsItsRegisterEntry() {
        entityManager.clear();

        PyqPaper read = papers.findByCode("neet-2025-45").orElseThrow();
        PyqPaperRow row = paper("neet-2025-45", "45");

        assertThat(read.getSections()).isEqualTo(row.sections());
        assertThat(read.getMarking()).isEqualTo(row.marking());
        assertThat(read.getKeySource()).isEqualTo(row.keySource());
        assertThat(read.getSource()).isEqualTo(row.source());
        assertThat(read.apply(row)).isFalse();
    }

    @Test
    void aPyqIsAddressedByItsPaperAndNumber() {
        questions.saveAndFlush(pyq("45", single((short) 1, "3")));

        assertThatThrownBy(() -> questions.saveAndFlush(pyq("45", single((short) 1, "2"))))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("questions_pyq_key");
    }

    @Test
    void aPyqNeedsItsPaper() {
        assertThatThrownBy(() -> questions.saveAndFlush(pyq("99", single((short) 1, "3"))))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("questions_paper_fkey");
    }

    @Test
    void eachAnswerStatusHoldsWhatItSays() {
        questions.saveAndFlush(pyq("45", single((short) 1, "3")));
        questions.saveAndFlush(pyq("45", answer((short) 2, AnswerStatus.multiple, "1", List.of("1", "2"))));
        questions.saveAndFlush(pyq("45", answer((short) 3, AnswerStatus.dropped, null, null)));
        entityManager.clear();

        List<Question> read = questions.findBySourceAndExamAndYearAndPaperCodeOrderByQuestionNo(
                QuestionSource.pyq, "NEET", (short) 2025, "45");

        assertThat(read).extracting(Question::getAnswerStatus)
                .containsExactly(AnswerStatus.single, AnswerStatus.multiple, AnswerStatus.dropped);
        assertThat(read.get(1).getAcceptedKeys()).containsExactly("1", "2");
        assertThat(read.get(2).getCorrectKey()).isNull();
    }

    static Stream<Arguments> contradictions() {
        return Stream.of(
                Arguments.of("single without a key", AnswerStatus.single, null, null, "questions_answer_check"),
                Arguments.of("single with accepted keys", AnswerStatus.single, "1", List.of("1", "2"), "questions_answer_check"),
                Arguments.of("multiple without accepted keys", AnswerStatus.multiple, "1", null, "questions_answer_check"),
                Arguments.of("multiple with one accepted key", AnswerStatus.multiple, "1", List.of("1"), "questions_answer_check"),
                Arguments.of("multiple whose key is not accepted", AnswerStatus.multiple, "3", List.of("1", "2"), "questions_answer_check"),
                Arguments.of("dropped with a key", AnswerStatus.dropped, "1", null, "questions_answer_check"),
                Arguments.of("dropped with accepted keys", AnswerStatus.dropped, null, List.of("1", "2"), "questions_answer_check"),
                Arguments.of("a key outside 1-4", AnswerStatus.single, "A", null, "questions_keys_check"),
                Arguments.of("an accepted key outside 1-4", AnswerStatus.multiple, "1", List.of("1", "5"), "questions_keys_check"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("contradictions")
    void anAnswerThatContradictsItselfIsRefused(String name, AnswerStatus status, String key, List<String> accepted,
            String constraint) {
        assertThatThrownBy(() -> questions.saveAndFlush(pyq("45", answer((short) 1, status, key, accepted))))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining(constraint);
    }

    @Test
    void theQuestionRoundTripsAndAReloadOfTheSameRowChangesNothing() {
        PyqQuestionRow row = single((short) 1, "3");
        questions.saveAndFlush(pyq("45", row));
        entityManager.clear();

        Question read = questions.findAll().getFirst();

        assertThat(read.getOptions()).isEqualTo(row.options());
        assertThat(read.getFigures()).isEqualTo(row.figures());
        assertThat(read.getExtraction()).isEqualTo(row.extraction());
        assertThat(read.getTagging()).isEqualTo(row.tagging());
        assertThat(read.apply(chapter, row)).isFalse();
        assertThat(read.apply(otherChapter, row)).isTrue();
    }

    /** V11: whose tag a question carries is one of three values, and a changed status is a change. */
    @Test
    void theTagStatusIsOneOfThreeAndAChangeIsAnUpdate() {
        PyqQuestionRow row = single((short) 1, "3");
        Question saved = questions.saveAndFlush(pyq("45", row));
        entityManager.clear();

        Question read = questions.findAll().getFirst();
        assertThat(read.getTagStatus()).isEqualTo(TagStatus.model);
        assertThat(read.apply(chapter, new PyqQuestionRow(row.questionNo(), row.nodeCode(), row.secondaryNodeCodes(),
                row.outOfSyllabus(), row.stemEn(), row.stemHi(), row.options(), row.answerStatus(), row.correctKey(),
                row.acceptedKeys(), row.figures(), row.extraction(), row.tagging(), row.asPrinted(), TagStatus.review)))
                .isTrue();
        assertThat(read.getTagStatus()).isEqualTo(TagStatus.review);
        assertThatThrownBy(() -> entityManager.createNativeQuery("UPDATE questions SET tag_status = 'guessed' WHERE id = ?1")
                .setParameter(1, saved.getId()).executeUpdate())
                .hasStackTraceContaining("questions_tag_status_check");
    }

    @Test
    void aSecondaryTopicIsKeyedOnTheQuestionAndNode() {
        Question question = questions.saveAndFlush(pyq("45", single((short) 1, "3")));
        topics.saveAndFlush(new QuestionTopic(question.getId(), otherChapter));
        entityManager.clear();

        assertThat(topics.findByIdQuestionIdIn(List.of(question.getId())))
                .extracting(QuestionTopic::getNodeId).containsExactly(otherChapter);
    }

    @Test
    void theAnswerNeverSerialisesFromTheEntity() {
        Question multiple = questions.saveAndFlush(
                pyq("45", answer((short) 2, AnswerStatus.multiple, "1", List.of("1", "2"))));

        String json = JsonMapper.builder().build().writeValueAsString(multiple);

        assertThat(json).doesNotContain("correctKey").doesNotContain("acceptedKeys").doesNotContain("answerStatus")
                .contains("\"stemEn\"");
    }

    private Question pyq(String paperCode, PyqQuestionRow row) {
        return new Question("NEET", (short) 2025, paperCode, chapter, row);
    }

    private static PyqQuestionRow single(short no, String key) {
        return answer(no, AnswerStatus.single, key, null);
    }

    private static PyqQuestionRow answer(short no, AnswerStatus status, String key, List<String> accepted) {
        return new PyqQuestionRow(no, "QPHY.11.ROT", List.of(), false,
                "A wheel rolls without slipping. Which point moves fastest?", null,
                List.of(new QuestionOption("1", "the top", null), new QuestionOption("2", "the centre", null),
                        new QuestionOption("3", "the contact point", null), new QuestionOption("4", "all equally", null)),
                status, key, accepted,
                List.of(new QuestionFigure("stem", 3, "a wheel on a level road, P at the top, Q at the contact point")),
                new QuestionExtraction(List.of(3), List.of(UUID.randomUUID()), "claude-opus-5-5", "pyq_extract.v1", List.of()),
                new QuestionTagging(UUID.randomUUID(), "claude-sonnet-5-5", "pyq_tag.v1", new BigDecimal("0.92"),
                        "rolling motion: velocity of points on a rolling wheel"),
                List.of(), TagStatus.model);
    }

    private static PyqPaperRow paper(String code, String paperCode) {
        return new PyqPaperRow(code, "NEET", (short) 2025, paperCode, LocalDate.of(2025, 5, 4), true, (short) 180,
                List.of(new PaperSection(PaperSubject.physics, null, (short) 1, (short) 45, (short) 45),
                        new PaperSection(PaperSubject.chemistry, null, (short) 46, (short) 90, (short) 45),
                        new PaperSection(PaperSubject.botany, null, (short) 91, (short) 135, (short) 45),
                        new PaperSection(PaperSubject.zoology, null, (short) 136, (short) 180, (short) 45)),
                new PaperMarking(4, -1, 0, "booklet p1, instruction 4", null, null, null),
                new KeyProvenance("mirrored-official", "a third-party mirror", LocalDate.of(2026, 10, 20),
                        "source/neet-pyq-sources/2025/neet-2025-final-key-nta-codes-45-48.pdf", "0f39fa77"),
                List.of(new PaperSourceFile("source/neet-pyq-sources/2025/neet-2025-45-booklet-hi-en.pdf", "b90c598c",
                        "hi-en", List.of(2, 3))));
    }
}
