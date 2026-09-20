package com.margai.curriculum.api;

import java.util.Objects;
import java.util.UUID;

/**
 * One vector on its way into {@code ncert_paragraphs.embedding} ({@code ncert embed}). The width
 * is not checked here — {@code margai.ai.embed.dimensions} and the {@code vector(n)} column are
 * held equal by {@code EmbeddingDimensionTest}, and the embedding client refuses a vector of the
 * wrong length before it ever reaches this record.
 *
 * <p>The array is a component of a record, so {@code equals} is reference equality on it. Nothing
 * compares two of these — they are written and discarded — and copying 1,024 floats per paragraph
 * to buy an equality no caller uses would be worse.
 */
public record ParagraphEmbedding(UUID paragraphId, float[] vector) {

    public ParagraphEmbedding {
        Objects.requireNonNull(paragraphId, "paragraphId");
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("no vector for paragraph " + paragraphId);
        }
    }
}
