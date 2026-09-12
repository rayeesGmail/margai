package com.margai.curriculum.internal;

import com.margai.common.api.Category;
import com.margai.curriculum.api.SeatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code cutoffs} (TECH_PLAN §2.3, migration V3): SPEC §9 item 4 cutoff tables by year, category
 * and quota scope ({@code AIQ} or a state code), unique per {@code (year, category, quota_scope,
 * seat_type)}. Loaded from {@code pipeline/inputs/cutoffs.csv}.
 */
@Entity
@Table(name = "cutoffs")
public class Cutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private short year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Category category;

    @Column(nullable = false, length = 8)
    private String quotaScope;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SeatType seatType;

    @Column(nullable = false)
    private short qualifyingMarks;

    @Column(length = 120)
    private String source;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected Cutoff() {
        // JPA
    }

    public Cutoff(short year, Category category, String quotaScope, SeatType seatType, short qualifyingMarks,
            String source) {
        this.year = year;
        this.category = category;
        this.quotaScope = quotaScope;
        this.seatType = seatType;
        this.qualifyingMarks = qualifyingMarks;
        this.source = source;
    }

    /** The D13 loader's upsert (TECH_PLAN §6.3), matched on the natural key; returns whether anything changed. */
    public boolean apply(short qualifyingMarks, String source) {
        boolean changed = this.qualifyingMarks != qualifyingMarks || !Objects.equals(this.source, source);
        if (changed) {
            this.qualifyingMarks = qualifyingMarks;
            this.source = source;
        }
        return changed;
    }

    /** {@code "year category quota_scope seat_type"}: the natural key as the report prints it. */
    public String naturalKey() {
        return year + " " + category + " " + quotaScope + " " + seatType;
    }

    public UUID getId() {
        return id;
    }

    public short getYear() {
        return year;
    }

    public Category getCategory() {
        return category;
    }

    public String getQuotaScope() {
        return quotaScope;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public short getQualifyingMarks() {
        return qualifyingMarks;
    }

    public String getSource() {
        return source;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
