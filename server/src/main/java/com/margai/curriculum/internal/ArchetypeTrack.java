package com.margai.curriculum.internal;

import com.margai.common.api.AttemptType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code archetype_tracks} (TECH_PLAN §2.3, migration V3): the plan backbone of SPEC §9 item 5,
 * one track per {@link AttemptType}. Loaded from {@code pipeline/inputs/archetypes.yaml} at D13.
 */
@Entity
@Table(name = "archetype_tracks")
public class ArchetypeTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 16)
    private AttemptType code;

    @Column(nullable = false, length = 160)
    private String nameEn;

    @Column(length = 160)
    private String nameHi;

    private Short weeks;

    @Column(columnDefinition = "text")
    private String descriptionMd;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected ArchetypeTrack() {
        // JPA
    }

    public ArchetypeTrack(AttemptType code, String nameEn, Short weeks) {
        this.code = code;
        this.nameEn = nameEn;
        this.weeks = weeks;
    }

    public UUID getId() {
        return id;
    }

    public AttemptType getCode() {
        return code;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameHi() {
        return nameHi;
    }

    public Short getWeeks() {
        return weeks;
    }

    public String getDescriptionMd() {
        return descriptionMd;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
