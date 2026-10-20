package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.PromptRegistry;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * An English, textbook-style search version of a student's question (founder ruling 2026-10-18,
 * DECISIONS 2026-10-19): one call on the CHEAP tier and the {@code query_rewrite} prompt, whose tool
 * returns a {@link SearchQuery}. Retrieval runs on the original question and this version and fuses
 * the two ({@code HybridRetriever}), never on the rewrite alone.
 *
 * <p>Measured standalone at D18; at D37 the same field rides on the doubt router's call (TECH_PLAN
 * §4.2), so this task is the measuring stand-in, not the production path. Its calls are logged as
 * {@link AiFeature#eval} — they are an evaluation, and the router's own feature, {@code doubt_route},
 * should carry production routing spend only (Claude, DECISIONS 2026-10-19). Every call goes through
 * the client's decorator chain, so each writes its {@code ai_calls} row.
 */
@Component
public class QueryRewriteTask implements QueryRewriter {

    private final AiClient ai;
    private final PromptRef prompt;

    QueryRewriteTask(AiClient ai, PromptRegistry prompts) {
        this.ai = ai;
        this.prompt = prompts.require("query_rewrite");
    }

    @Override
    public AiResponse<SearchQuery> run(String question, AiCallContext ctx) {
        return ai.complete(AiRequest.of(AiFeature.eval, Tier.cheap, prompt, Map.of("question", question),
                SearchQuery.class, ctx));
    }
}
