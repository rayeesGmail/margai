package com.margai.ai.internal;

import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;

/**
 * The completion half of the seam (TECH_PLAN §4.1). A provider that answers prompts but does not
 * embed implements this rather than {@link com.margai.ai.api.AiClient}, so no implementation has
 * to carry a method it cannot honour; {@link CompositeAiClient} joins it to an
 * {@link EmbeddingClient} to make the whole seam.
 */
public interface CompletionClient {

    <T> AiResponse<T> complete(AiRequest<T> request);
}
