/**
 * ai module (TECH_PLAN §1.3, §4): the single {@code AiClient} seam, its provider and fake
 * implementations, the decorator chain (ledger, breaker, tier policy, schema validation, retry),
 * prompts, and from later days the router, retrieval and verification. Owns {@code ai_calls}
 * from D5 ({@code ai_spend_daily} D65, {@code audit_queue} D39). Allowed dependencies per §1.4:
 * {@code common :: api}, {@code curriculum :: api}. Only this module talks to a model provider, and
 * inside it only the one package per provider — the model SDK in {@code internal.anthropic}, the
 * embedding provider's HTTP calls in {@code internal.cohere}, the dormant AWS client in
 * {@code internal.bedrock} (§1.4; ArchUnit rules from D5, extended 2026-09-12).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "ai",
        allowedDependencies = {"common :: api", "curriculum :: api"})
package com.margai.ai;
