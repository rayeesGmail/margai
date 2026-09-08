package com.margai.ai.api;

import java.util.Optional;

/**
 * The only ticket to the {@code reason} tier (TECH_PLAN §4.2, DECISIONS D3.23). Three producers
 * exist: the difficulty router, verification (the verifier always runs on {@code reason}) and
 * generation (variants, pipeline solutions). The constructor is private; an architecture test
 * confines {@link #router(RouterVerdict)} to {@code ai.tasks.DifficultyRouter}.
 * {@code TierPolicyAiClient} rejects a {@code reason} request that carries no decision.
 */
public final class RouteDecision {

    public enum Producer {
        router,
        verification,
        generation
    }

    private final Tier tier;
    private final boolean numerical;
    private final Producer producer;
    private final RouterVerdict verdict;

    private RouteDecision(Tier tier, boolean numerical, Producer producer, RouterVerdict verdict) {
        this.tier = tier;
        this.numerical = numerical;
        this.producer = producer;
        this.verdict = verdict;
    }

    /** Independent numerical verification always runs on the reasoning tier. */
    public static RouteDecision verification() {
        return new RouteDecision(Tier.reason, true, Producer.verification, null);
    }

    /** Variant and pipeline solution generation run on the reasoning tier. */
    public static RouteDecision generation() {
        return new RouteDecision(Tier.reason, false, Producer.generation, null);
    }

    /**
     * The router's verdict with the rule that lives here, not in the model:
     * {@code is_numerical ⇒ reason}.
     */
    public static RouteDecision router(RouterVerdict verdict) {
        Tier tier = verdict.isNumerical() ? Tier.reason : verdict.tier();
        return new RouteDecision(tier, verdict.isNumerical(), Producer.router, verdict);
    }

    public Tier tier() {
        return tier;
    }

    public boolean isNumerical() {
        return numerical;
    }

    public Producer producer() {
        return producer;
    }

    public Optional<RouterVerdict> verdict() {
        return Optional.ofNullable(verdict);
    }

    @Override
    public String toString() {
        return "RouteDecision[" + producer + " → " + tier + (numerical ? ", numerical" : "") + "]";
    }
}
