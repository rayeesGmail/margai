package com.margai.curriculum.internal;

import com.margai.curriculum.api.KeyProvenance;
import com.margai.curriculum.api.PaperMarking;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSourceFile;
import com.margai.curriculum.api.PyqPaperRow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

/**
 * {@code pyq_papers} (migration V9): one official question paper — one booklet code of one sitting
 * — as the founder's register {@code pipeline/inputs/pyq-papers.yaml} lists it. Its questions
 * reference it by {@code (exam, year, paper_code)}, which is also how a mock (D35) finds its
 * sections and marking (TECH_PLAN §15.6).
 */
@Entity
@Table(name = "pyq_papers")
public class PyqPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Column(nullable = false, length = 8)
    private String exam;

    @Column(nullable = false)
    private short year;

    @Column(nullable = false, length = 16)
    private String paperCode;

    @Column(nullable = false)
    private LocalDate examDate;

    @Column(nullable = false)
    private boolean officialHindi;

    @Column(nullable = false)
    private short questionCount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private List<PaperSection> sections;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private PaperMarking marking;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private KeyProvenance keySource;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private List<PaperSourceFile> source;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected PyqPaper() {
        // JPA
    }

    public PyqPaper(PyqPaperRow row) {
        this.code = row.code();
        this.exam = row.exam();
        this.year = row.year();
        this.paperCode = row.paperCode();
        apply(row);
    }

    /**
     * {@code pyq load}'s upsert on the register id. The paper's identity — exam, year and booklet
     * code — is the key its questions hang on, so a register entry that changes it under the same
     * id is refused by the caller, never rewritten here. Returns whether anything changed.
     */
    public boolean apply(PyqPaperRow row) {
        boolean changed = !Objects.equals(examDate, row.examDate())
                || officialHindi != row.officialHindi()
                || questionCount != row.questionCount()
                || !Objects.equals(sections, row.sections())
                || !Objects.equals(marking, row.marking())
                || !Objects.equals(keySource, row.keySource())
                || !Objects.equals(source, row.source());
        if (changed) {
            this.examDate = row.examDate();
            this.officialHindi = row.officialHindi();
            this.questionCount = row.questionCount();
            this.sections = row.sections();
            this.marking = row.marking();
            this.keySource = row.keySource();
            this.source = row.source();
        }
        return changed;
    }

    /** Whether the row carries the same exam, year and booklet code as this paper. */
    public boolean isPaper(PyqPaperRow row) {
        return exam.equals(row.exam()) && year == row.year() && paperCode.equals(row.paperCode());
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getExam() {
        return exam;
    }

    public short getYear() {
        return year;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public boolean isOfficialHindi() {
        return officialHindi;
    }

    public short getQuestionCount() {
        return questionCount;
    }

    public List<PaperSection> getSections() {
        return sections;
    }

    public PaperMarking getMarking() {
        return marking;
    }

    public KeyProvenance getKeySource() {
        return keySource;
    }

    public List<PaperSourceFile> getSource() {
        return source;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
