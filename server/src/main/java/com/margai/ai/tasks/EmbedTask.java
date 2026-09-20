package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.EmbedRequest;
import org.springframework.stereotype.Component;

/**
 * {@link EmbeddingService} over the {@code AiClient} seam (TECH_PLAN §4.1, §4.9). Like every task
 * class it is the only kind of caller of the client, so the decorator chain — ledger, breaker,
 * retry — sits under every embedding exactly as it sits under every completion, and each call
 * writes its own {@code ai_calls} row (feature {@code embed}).
 */
@Component
class EmbedTask implements EmbeddingService {

    private final AiClient ai;

    EmbedTask(AiClient ai) {
        this.ai = ai;
    }

    @Override
    public float[] ofDocument(String text, AiCallContext ctx) {
        return embed(text, EmbedRequest.InputType.search_document, ctx);
    }

    @Override
    public float[] ofQuery(String text, AiCallContext ctx) {
        return embed(text, EmbedRequest.InputType.search_query, ctx);
    }

    private float[] embed(String text, EmbedRequest.InputType inputType, AiCallContext ctx) {
        return ai.embed(new EmbedRequest(AiFeature.embed, text, inputType, ctx)).output();
    }
}
