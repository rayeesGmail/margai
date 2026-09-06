package com.margai.ai.api;

/**
 * The per-user or global daily AI budget is spent (TECH_PLAN §4.8, SPEC §3 "per-user AI cost
 * circuit breakers exist"). Callers degrade honestly: doubts return {@code AI_BUDGET_EXCEEDED},
 * the planner takes the deterministic path, classification waits for the sweeper.
 */
public final class AiBudgetExceededException extends RuntimeException {

    public enum Scope {
        user,
        global
    }

    private final Scope scope;
    private final long spentPaise;
    private final long limitPaise;

    public AiBudgetExceededException(Scope scope, long spentPaise, long limitPaise) {
        super(scope + " daily AI budget exceeded: spent " + spentPaise + " of " + limitPaise + " paise");
        this.scope = scope;
        this.spentPaise = spentPaise;
        this.limitPaise = limitPaise;
    }

    public Scope scope() {
        return scope;
    }

    public long spentPaise() {
        return spentPaise;
    }

    public long limitPaise() {
        return limitPaise;
    }
}
