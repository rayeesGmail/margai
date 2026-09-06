package com.margai.curriculum.internal;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/** Composite key of {@code syllabus_prerequisites}: the edge {@code from → to}. */
@Embeddable
public record SyllabusPrerequisiteId(UUID fromNodeId, UUID toNodeId) implements Serializable {
}
