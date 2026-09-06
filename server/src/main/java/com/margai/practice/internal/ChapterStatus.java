package com.margai.practice.internal;

import com.margai.practice.api.CoverageStatus;
import com.margai.practice.api.StatusSource;
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
 * {@code chapter_status} (TECH_PLAN §2.4, migration V4): one row per {@code (user, node)}.
 * SPEC §5.1 Q4 writes {@code status} and {@code feels_weak} with {@code source = self_report};
 * behaviour and the diagnostic refine {@code ability_estimate} later. References to
 * {@code users} and {@code syllabus_nodes} are ids, not associations: those tables belong to
 * other modules (§1.3).
 */
@Entity
@Table(name = "chapter_status")
public class ChapterStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID nodeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CoverageStatus status;

    @Column(nullable = false)
    private boolean feelsWeak;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private StatusSource source;

    @Column(precision = 3, scale = 2)
    private BigDecimal abilityEstimate;

    @Column(precision = 3, scale = 2)
    private BigDecimal abilityConfidence;

    private Instant lastSignalAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected ChapterStatus() {
        // JPA
    }

    public ChapterStatus(UUID userId, UUID nodeId, StatusSource source) {
        this.userId = userId;
        this.nodeId = nodeId;
        this.status = CoverageStatus.untouched;
        this.feelsWeak = false;
        this.source = source;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public CoverageStatus getStatus() {
        return status;
    }

    public boolean isFeelsWeak() {
        return feelsWeak;
    }

    public StatusSource getSource() {
        return source;
    }

    public BigDecimal getAbilityEstimate() {
        return abilityEstimate;
    }

    public BigDecimal getAbilityConfidence() {
        return abilityConfidence;
    }

    public Instant getLastSignalAt() {
        return lastSignalAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
