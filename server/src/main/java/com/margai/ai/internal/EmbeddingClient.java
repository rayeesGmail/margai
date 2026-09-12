package com.margai.ai.internal;

import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;

/**
 * The embedding half of the seam (TECH_PLAN §4.1, §4.9). Embeddings come from their own provider
 * once model access is direct rather than through one cloud API, so the two halves are wired
 * separately and joined by {@link CompositeAiClient}.
 */
public interface EmbeddingClient {

    AiResponse<float[]> embed(EmbedRequest request);
}
