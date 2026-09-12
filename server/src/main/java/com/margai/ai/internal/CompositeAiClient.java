package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;

/**
 * One seam over two providers (TECH_PLAN §4.1): completions from one, embeddings from another.
 * It is the innermost client the decorator chain wraps when the completion provider does not
 * embed, so the ledger, breaker, tier policy, schema validation and retries apply to both halves
 * exactly as they do to a single-provider client. {@code completeBatch} is inherited from
 * {@link AiClient}: the bounded on-demand loop of §4.11.
 */
public final class CompositeAiClient implements AiClient {

    private final CompletionClient completions;
    private final EmbeddingClient embeddings;

    public CompositeAiClient(CompletionClient completions, EmbeddingClient embeddings) {
        this.completions = completions;
        this.embeddings = embeddings;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        return completions.complete(request);
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        return embeddings.embed(request);
    }
}
