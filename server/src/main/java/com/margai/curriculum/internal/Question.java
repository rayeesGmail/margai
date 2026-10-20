package com.margai.curriculum.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import com.margai.curriculum.api.QuestionSource;
import com.margai.curriculum.api.QuestionTagging;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

/**
 * {@code questions} (TECH_PLAN §2.3, migration V9): the question bank of SPEC §9 item 3, PYQs
 * from D19. A PYQ is addressed by {@code (exam, year, paper_code, question_no)}, the upsert key
 * of {@code pyq load}.
 *
 * <p><b>The answer is server-only</b> (TECH_PLAN §2.3, §0.4 #4, CLAUDE.md hard rule 1):
 * {@code correctKey}, {@code acceptedKeys} and {@code answerStatus} — "multiple" or "dropped" is
 * itself a hint — are {@link JsonIgnore}d and no response record may carry them before the
 * student's answer is recorded; judging a "multiple" question reads {@code acceptedKeys}, never
 * {@code correctKey} alone. The D31 serving code and its §8.3 payload scan are where that is
 * proven end to end.
 *
 * <p>The columns later days write — solutions, difficulty, average time, the distractor map, the
 * embedding, the audit status and the verification — are mapped by the day that writes them
 * (D20–D23), as {@link NcertParagraph} left its embedding to D17. {@code verified} is mapped now
 * so that a reload changing what was asked drops it.
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 16)
    private QuestionSource source;

    @Column(updatable = false, length = 8)
    private String exam;

    @Column(updatable = false)
    private Short year;

    @Column(updatable = false, length = 16)
    private String paperCode;

    @Column(updatable = false)
    private Short questionNo;

    @Column(nullable = false)
    private UUID nodeId;

    @Column(nullable = false)
    private boolean outOfSyllabus;

    @Column(nullable = false)
    private String stemEn;

    private String stemHi;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private List<QuestionOption> options;

    @JsonIgnore
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 1)
    private String correctKey;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private AnswerStatus answerStatus;

    /** An array, not a list: Hibernate maps a {@code List} field to JSON, the column is {@code VARCHAR(1)[]}. */
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] acceptedKeys;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private List<QuestionFigure> figures;

    @JdbcTypeCode(SqlTypes.JSON)
    private QuestionExtraction extraction;

    @JdbcTypeCode(SqlTypes.JSON)
    private QuestionTagging tagging;

    @Column(nullable = false)
    private boolean verified;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected Question() {
        // JPA
    }

    /** A PYQ of the paper {@code (exam, year, paperCode)} on the node its tag resolved to. */
    public Question(String exam, short year, String paperCode, UUID nodeId, PyqQuestionRow row) {
        this.source = QuestionSource.pyq;
        this.exam = exam;
        this.year = year;
        this.paperCode = paperCode;
        this.questionNo = row.questionNo();
        apply(nodeId, row);
    }

    /**
     * {@code pyq load}'s upsert on the PYQ's natural key. Everything the paper file and the tag
     * say is replaced, so the current approved file and tag win outright. A change to what was
     * asked or what the key accepts drops {@code verified}: a solution verified against other
     * words or another key no longer stands (D20's verification re-runs). Returns whether
     * anything changed.
     */
    public boolean apply(UUID nodeId, PyqQuestionRow row) {
        boolean askedChanged = !Objects.equals(stemEn, row.stemEn())
                || !Objects.equals(options, row.options())
                || answerStatus != row.answerStatus()
                || !Objects.equals(correctKey, row.correctKey())
                || !Objects.equals(getAcceptedKeys(), row.acceptedKeys());
        boolean changed = askedChanged
                || !Objects.equals(this.nodeId, nodeId)
                || outOfSyllabus != row.outOfSyllabus()
                || !Objects.equals(stemHi, row.stemHi())
                || !Objects.equals(figures, row.figures())
                || !Objects.equals(extraction, row.extraction())
                || !Objects.equals(tagging, row.tagging());
        if (changed) {
            this.nodeId = nodeId;
            this.outOfSyllabus = row.outOfSyllabus();
            this.stemEn = row.stemEn();
            this.stemHi = row.stemHi();
            this.options = row.options();
            this.answerStatus = row.answerStatus();
            this.correctKey = row.correctKey();
            this.acceptedKeys = row.acceptedKeys() == null ? null : row.acceptedKeys().toArray(String[]::new);
            this.figures = row.figures();
            this.extraction = row.extraction();
            this.tagging = row.tagging();
            if (askedChanged) {
                this.verified = false;
            }
        }
        return changed;
    }

    public UUID getId() {
        return id;
    }

    public QuestionSource getSource() {
        return source;
    }

    public String getExam() {
        return exam;
    }

    public Short getYear() {
        return year;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public Short getQuestionNo() {
        return questionNo;
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public boolean isOutOfSyllabus() {
        return outOfSyllabus;
    }

    public String getStemEn() {
        return stemEn;
    }

    public String getStemHi() {
        return stemHi;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    @JsonIgnore
    public String getCorrectKey() {
        return correctKey;
    }

    @JsonIgnore
    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    @JsonIgnore
    public List<String> getAcceptedKeys() {
        return acceptedKeys == null ? null : List.of(acceptedKeys);
    }

    public List<QuestionFigure> getFigures() {
        return figures;
    }

    public QuestionExtraction getExtraction() {
        return extraction;
    }

    public QuestionTagging getTagging() {
        return tagging;
    }

    public boolean isVerified() {
        return verified;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
