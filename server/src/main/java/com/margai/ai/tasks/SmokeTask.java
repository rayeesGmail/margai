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
 * The D5 connectivity smoke (TECH_PLAN §4.1 last paragraph): one {@code cheap} call on the
 * {@code smoke} prompt whose forced tool returns a {@link SmokeAnswer}. On the fake it proves the
 * chain end to end; under {@code BEDROCK_LIVE=1} the two-call smoke test proves forced tool
 * use, real token counts and a prompt-cache read on the second call (§13.2 item 1).
 */
@Component
public class SmokeTask {

    private final AiClient ai;
    private final PromptRef prompt;

    SmokeTask(AiClient ai, PromptRegistry prompts) {
        this.ai = ai;
        this.prompt = prompts.require("smoke");
    }

    public AiResponse<SmokeAnswer> run(int number, AiCallContext ctx) {
        return ai.complete(AiRequest.of(AiFeature.smoke, Tier.cheap, prompt, Map.of("number", number),
                SmokeAnswer.class, ctx));
    }
}
