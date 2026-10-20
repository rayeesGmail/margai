package com.margai.curriculum.internal;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/** Composite key of {@code question_topics}: a question and one of its secondary topics. */
@Embeddable
public record QuestionTopicId(UUID questionId, UUID nodeId) implements Serializable {
}
