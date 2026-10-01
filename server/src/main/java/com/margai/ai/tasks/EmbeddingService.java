package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;

/**
 * Text into a vector (TECH_PLAN §4.1's {@code EmbeddingService}, §4.9): the one way anything in
 * this codebase gets an embedding — the doubt cache's near-match lookup, {@code HybridRetriever}'s
 * query vector, the pipeline's {@code ncert embed} and D23's {@code anchors link}.
 *
 * <p>The two methods differ only in what the provider is told the text <em>is</em>, and that is
 * not a detail: the pinned model embeds a stored passage and a student's question into the same
 * space but from different sides of it, and using the wrong one quietly costs retrieval accuracy
 * that no test failure would ever point at. Hence two named methods rather than a boolean.
 *
 * <p>There is no prompt here and no model output to validate — the provider returns 1,024 floats
 * whose width {@code CohereEmbeddingClient} has already checked against
 * {@code margai.ai.embed.dimensions}, the pin the stored vectors and the {@code vector(n)} columns
 * both depend on.
 */
public interface EmbeddingService {

    /** A passage being stored, to be found later — {@code ncert embed}'s side of the space. */
    float[] ofDocument(String text, AiCallContext ctx);

    /** A question being asked, looking for passages — the retriever's side of the space. */
    float[] ofQuery(String text, AiCallContext ctx);
}
