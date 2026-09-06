/**
 * ai module (TECH_PLAN §1.3, §4): the single {@code AiClient} seam, its Bedrock and fake
 * implementations, the decorator chain (ledger, breaker, tier policy, schema validation, retry),
 * prompts, and from later days the router, retrieval and verification. Owns {@code ai_calls}
 * from D5 ({@code ai_spend_daily} D65, {@code audit_queue} D39). Allowed dependencies per §1.4:
 * {@code common :: api}, {@code curriculum :: api}. Only this module imports the Bedrock SDK
 * (§1.4; ArchUnit rule from D5).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "ai",
        allowedDependencies = {"common :: api", "curriculum :: api"})
package com.margai.ai;
