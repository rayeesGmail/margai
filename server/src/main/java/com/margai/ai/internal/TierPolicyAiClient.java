package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.Tier;
import com.margai.ai.api.TierPolicyException;

/**
 * TECH_PLAN §4.1 {@code TierPolicyAiClient}, §4.13 row "REASON only via the router": a
 * {@code reason} request must carry a {@link com.margai.ai.api.RouteDecision}, a decision must
 * agree with the request's tier, and a {@code vision} request must carry images. Embeddings
 * have no tier choice and pass through.
 */
public final class TierPolicyAiClient implements AiClient {

    private final AiClient inner;

    public TierPolicyAiClient(AiClient inner) {
        this.inner = inner;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        if (request.tier() == Tier.reason && request.route() == null) {
            throw new TierPolicyException("reason tier needs a RouteDecision (TECH_PLAN §4.2): "
                    + request.feature() + "/" + request.prompt().name());
        }
        if (request.route() != null && request.route().tier() != request.tier()) {
            throw new TierPolicyException("request tier " + request.tier() + " contradicts " + request.route()
                    + ": " + request.feature() + "/" + request.prompt().name());
        }
        if (request.tier() == Tier.vision && request.images().isEmpty()) {
            throw new TierPolicyException("vision tier needs at least one image: "
                    + request.feature() + "/" + request.prompt().name());
        }
        return inner.complete(request);
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        return inner.embed(request);
    }
}
