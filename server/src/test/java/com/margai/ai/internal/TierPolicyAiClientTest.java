package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.RouteDecision;
import com.margai.ai.api.Tier;
import com.margai.ai.api.TierPolicyException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §4.13 "REASON only via the router": the policy check that backs the type. */
class TierPolicyAiClientTest {

    private static final AiCallContext CTX = AiCallContext.system("req");

    private final StubAiClient inner = new StubAiClient();
    private final TierPolicyAiClient policy = new TierPolicyAiClient(inner);

    private static AiRequest<String> request(Tier tier, AiFeature feature) {
        return AiRequest.of(feature, tier, PromptRef.named("echo"), Map.of(), String.class, CTX);
    }

    @Test
    void reasonWithoutADecisionIsRejectedBeforeTheInnerClientSeesIt() {
        assertThatThrownBy(() -> policy.complete(request(Tier.reason, AiFeature.doubt_verify)))
                .isInstanceOf(TierPolicyException.class)
                .hasMessageContaining("RouteDecision")
                .hasMessageContaining("doubt_verify/echo");
        assertThat(inner.requests).isEmpty();
    }

    @Test
    void reasonWithADecisionPasses() {
        policy.complete(request(Tier.reason, AiFeature.doubt_verify).withRoute(RouteDecision.verification()));

        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void aDecisionForAnotherTierIsRejected() {
        assertThatThrownBy(() -> policy.complete(request(Tier.cheap, AiFeature.doubt).withRoute(RouteDecision.generation())))
                .isInstanceOf(TierPolicyException.class)
                .hasMessageContaining("cheap")
                .hasMessageContaining("generation");
        assertThat(inner.requests).isEmpty();
    }

    @Test
    void cheapWithoutADecisionPasses() {
        policy.complete(request(Tier.cheap, AiFeature.doubt_route));

        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void visionNeedsImages() {
        assertThatThrownBy(() -> policy.complete(request(Tier.vision, AiFeature.doubt_extract)))
                .isInstanceOf(TierPolicyException.class)
                .hasMessageContaining("image");

        policy.complete(request(Tier.vision, AiFeature.doubt_extract)
                .withImages(List.of(new ImagePart(new byte[] {1}, "image/png"))));

        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void embeddingsPassThrough() {
        policy.embed(new EmbedRequest(AiFeature.embed, "text", EmbedRequest.InputType.search_query, CTX));

        assertThat(inner.embeds).hasSize(1);
    }
}
