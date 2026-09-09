/**
 * ops module (TECH_PLAN §1.3): the founder's admin peek, audit-queue review and cost views —
 * read-only by construction (§9.3: no write repositories here), every route {@code role = admin}
 * (§3.7). Owns no tables. Opened at D11 with {@code GET /admin/metrics/otp} (§10.3 stub);
 * {@code GET /admin/costs} joins at D65, the rest at D75. Allowed dependencies per §1.4: every
 * module's {@code api} — today {@code common :: api} and {@code auth :: api}.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "ops",
        allowedDependencies = {"common :: api", "auth :: api"})
package com.margai.ops;
