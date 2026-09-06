package com.margai.curriculum.internal;

import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.Subject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code syllabus_nodes} (TECH_PLAN §2.3, migration V3): the syllabus tree of SPEC §9 item 1.
 * {@code code} ({@code PHY.11.ROT}) is the stable identifier used everywhere; the database
 * enforces that a {@code subject} node has no parent. {@code weightage_marks_avg} is computed at
 * D22; {@code neet_relevant = false} is SPEC §6.2's "NTA never asked it".
 */
@Entity
@Table(name = "syllabus_nodes")
public class SyllabusNode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Subject subject;

    private Short classLevel;

    private UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private NodeKind kind;

    @Column(nullable = false, length = 160)
    private String nameEn;

    @Column(length = 160)
    private String nameHi;

    @Column(nullable = false)
    private int sortOrder;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal weightageMarksAvg;

    private Integer defaultLearnMinutes;

    @Column(nullable = false)
    private boolean neetRelevant;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected SyllabusNode() {
        // JPA
    }

    public SyllabusNode(String code, Subject subject, Short classLevel, UUID parentId, NodeKind kind,
            String nameEn, int sortOrder) {
        this.code = code;
        this.subject = subject;
        this.classLevel = classLevel;
        this.parentId = parentId;
        this.kind = kind;
        this.nameEn = nameEn;
        this.sortOrder = sortOrder;
        this.weightageMarksAvg = BigDecimal.ZERO;
        this.neetRelevant = true;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Subject getSubject() {
        return subject;
    }

    public Short getClassLevel() {
        return classLevel;
    }

    public UUID getParentId() {
        return parentId;
    }

    public NodeKind getKind() {
        return kind;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameHi() {
        return nameHi;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public BigDecimal getWeightageMarksAvg() {
        return weightageMarksAvg;
    }

    public Integer getDefaultLearnMinutes() {
        return defaultLearnMinutes;
    }

    public boolean isNeetRelevant() {
        return neetRelevant;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
