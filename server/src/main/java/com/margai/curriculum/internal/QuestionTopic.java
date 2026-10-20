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
 * {@code question_topics} (TECH_PLAN §2.3, migration V9): a secondary topic a question draws on.
 * The primary is {@code questions.node_id}; at most two secondaries, never the primary — both
 * held by {@code pyq load}, since neither is a constraint the database can express.
 */
@Entity
@Table(name = "question_topics")
public class QuestionTopic {

    @EmbeddedId
    private QuestionTopicId id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected QuestionTopic() {
        // JPA
    }

    public QuestionTopic(UUID questionId, UUID nodeId) {
        this.id = new QuestionTopicId(questionId, nodeId);
    }

    public QuestionTopicId getId() {
        return id;
    }

    public UUID getQuestionId() {
        return id.questionId();
    }

    public UUID getNodeId() {
        return id.nodeId();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
