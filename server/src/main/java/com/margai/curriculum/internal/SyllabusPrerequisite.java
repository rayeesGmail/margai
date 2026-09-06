package com.margai.curriculum.internal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code syllabus_prerequisites} (TECH_PLAN §2.3, migration V3): one edge of the prerequisite
 * graph of SPEC §9 item 1. {@code from_node_id} must be learned before {@code to_node_id}. The
 * D13 loader keeps the graph acyclic; the database only forbids self-edges.
 */
@Entity
@Table(name = "syllabus_prerequisites")
public class SyllabusPrerequisite {

    @EmbeddedId
    private SyllabusPrerequisiteId id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected SyllabusPrerequisite() {
        // JPA
    }

    public SyllabusPrerequisite(UUID fromNodeId, UUID toNodeId) {
        this.id = new SyllabusPrerequisiteId(fromNodeId, toNodeId);
    }

    public SyllabusPrerequisiteId getId() {
        return id;
    }

    public UUID getFromNodeId() {
        return id.fromNodeId();
    }

    public UUID getToNodeId() {
        return id.toNodeId();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
