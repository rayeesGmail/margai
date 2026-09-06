package com.margai.ai.api;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * One {@link AiClient#complete} call (TECH_PLAN §4.1). Immutable; the {@code with…} methods copy.
 *
 * @param feature    ledger category ({@code ai_calls.feature})
 * @param tier       model tier; the model id comes from config
 * @param route      required when {@code tier == reason} (§4.2), otherwise null
 * @param prompt     template name; the active version is resolved by the prompt registry
 * @param variables  template variables ({@code fixture_case} selects a fake fixture)
 * @param images     required when {@code tier == vision}
 * @param outputType record the JSON schema is derived from and the output is decoded into
 * @param ctx        user id (nullable), request id, batchable flag
 * @param repair     set by the schema-validating decorator for the one repair retry, else null
 */
public record AiRequest<T>(
        AiFeature feature,
        Tier tier,
        RouteDecision route,
        PromptRef prompt,
        Map<String, Object> variables,
        List<ImagePart> images,
        Class<T> outputType,
        AiCallContext ctx,
        Repair repair) {

    public AiRequest {
        Objects.requireNonNull(feature, "feature");
        Objects.requireNonNull(tier, "tier");
        Objects.requireNonNull(prompt, "prompt");
        Objects.requireNonNull(outputType, "outputType");
        Objects.requireNonNull(ctx, "ctx");
        if (tier == Tier.embed) {
            throw new IllegalArgumentException("embed is not a completion tier; use AiClient.embed");
        }
        variables = variables == null
                ? Map.of() : Collections.unmodifiableMap(new LinkedHashMap<>(variables));
        images = images == null ? List.of() : List.copyOf(images);
    }

    public static <T> AiRequest<T> of(AiFeature feature, Tier tier, PromptRef prompt,
            Map<String, Object> variables, Class<T> outputType, AiCallContext ctx) {
        return new AiRequest<>(feature, tier, null, prompt, variables, List.of(), outputType, ctx, null);
    }

    public AiRequest<T> withRoute(RouteDecision route) {
        return new AiRequest<>(feature, tier, route, prompt, variables, images, outputType, ctx, repair);
    }

    public AiRequest<T> withImages(List<ImagePart> images) {
        return new AiRequest<>(feature, tier, route, prompt, variables, images, outputType, ctx, repair);
    }

    public AiRequest<T> withRepair(Repair repair) {
        return new AiRequest<>(feature, tier, route, prompt, variables, images, outputType, ctx, repair);
    }
}
