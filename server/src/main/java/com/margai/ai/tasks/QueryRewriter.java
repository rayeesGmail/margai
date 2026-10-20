package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;

/**
 * An English, textbook-style search version of a student's question (D18; DECISIONS 2026-10-19). The
 * pipeline depends on this rather than on {@link QueryRewriteTask}, so {@code ncert retrieve --rewrite}
 * can be driven end to end without a model, as {@link NcertPageVerifier} lets {@code ncert verify} be.
 */
public interface QueryRewriter {

    AiResponse<SearchQuery> run(String question, AiCallContext ctx);
}
