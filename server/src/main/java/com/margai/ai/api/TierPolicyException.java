package com.margai.ai.api;

/**
 * A request that breaks the tier rules of TECH_PLAN §4.2 — {@code reason} without a
 * {@link RouteDecision}, a decision for a different tier, {@code vision} without images. This
 * is a programming error in the caller, never a model outcome, hence an
 * {@link IllegalArgumentException}; the ledger still records it as {@code error}.
 */
public final class TierPolicyException extends IllegalArgumentException {

    public TierPolicyException(String message) {
        super(message);
    }
}
