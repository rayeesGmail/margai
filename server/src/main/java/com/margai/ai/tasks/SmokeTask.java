package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.RouteDecision;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.PromptRegistry;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * The D5 connectivity smoke (TECH_PLAN §4.1 last paragraph): one call on the {@code smoke} prompt
 * whose forced tool returns a {@link SmokeAnswer}. On the fake it proves the chain end to end;
 * under {@code AI_LIVE=1} the smoke test proves forced tool use, real token counts and a
 * prompt-cache read on the second call (§13.2 item 1).
 *
 * <p>Both completion tiers are reachable here because they are different models with different
 * request shapes (§4.11), and a provider switch has to be proven on each. The reasoning-tier
 * call carries a generation decision — a named producer of §4.2, the same one the pipeline's
 * solution generation uses — because the tier policy admits no request without one.
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
        return ai.complete(request(Tier.cheap, number, ctx));
    }

    /** The same prompt on the reasoning tier, to prove that model's request shape. */
    public AiResponse<SmokeAnswer> runOnReasonTier(int number, AiCallContext ctx) {
        return ai.complete(request(Tier.reason, number, ctx).withRoute(RouteDecision.generation()));
    }

    /** The same prompt with an image, to prove the vision tier accepts image input. */
    public AiResponse<SmokeAnswer> runOnVisionTier(int number, ImagePart image, AiCallContext ctx) {
        return ai.complete(request(Tier.vision, number, ctx).withImages(List.of(image)));
    }

    private AiRequest<SmokeAnswer> request(Tier tier, int number, AiCallContext ctx) {
        return AiRequest.of(AiFeature.smoke, tier, prompt, Map.of("number", number), SmokeAnswer.class, ctx);
    }
}
