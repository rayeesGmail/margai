package com.margai.curriculum.internal;

import com.margai.curriculum.api.TrackPhase;
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
 * {@code archetype_track_steps} (TECH_PLAN §2.3, migration V3): one ordered step of a track,
 * unique per {@code (track_id, sequence)}.
 */
@Entity
@Table(name = "archetype_track_steps")
public class ArchetypeTrackStep {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID trackId;

    @Column(nullable = false)
    private UUID nodeId;

    @Column(nullable = false)
    private int sequence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private TrackPhase phase;

    private Short targetWeek;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected ArchetypeTrackStep() {
        // JPA
    }

    public ArchetypeTrackStep(UUID trackId, UUID nodeId, int sequence, TrackPhase phase, Short targetWeek) {
        this.trackId = trackId;
        this.nodeId = nodeId;
        this.sequence = sequence;
        this.phase = phase;
        this.targetWeek = targetWeek;
    }

    /** The D13 loader's upsert (TECH_PLAN §6.3), matched on (track, sequence); returns whether anything changed. */
    public boolean apply(UUID nodeId, TrackPhase phase, Short targetWeek) {
        boolean changed = !this.nodeId.equals(nodeId)
                || this.phase != phase
                || !Objects.equals(this.targetWeek, targetWeek);
        if (changed) {
            this.nodeId = nodeId;
            this.phase = phase;
            this.targetWeek = targetWeek;
        }
        return changed;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTrackId() {
        return trackId;
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public int getSequence() {
        return sequence;
    }

    public TrackPhase getPhase() {
        return phase;
    }

    public Short getTargetWeek() {
        return targetWeek;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
