package com.margai.ai.api;

import java.util.Objects;

/**
 * One {@link AiClient#embed} call (TECH_PLAN §4.9). The input type follows the embedding
 * model's own distinction between stored documents and lookup queries.
 */
public record EmbedRequest(AiFeature feature, String text, InputType inputType, AiCallContext ctx) {

    public enum InputType {
        search_document,
        search_query
    }

    public EmbedRequest {
        Objects.requireNonNull(feature, "feature");
        Objects.requireNonNull(inputType, "inputType");
        Objects.requireNonNull(ctx, "ctx");
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("nothing to embed");
        }
    }
}
