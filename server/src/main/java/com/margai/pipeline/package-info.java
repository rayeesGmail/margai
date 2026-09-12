/**
 * pipeline module (TECH_PLAN §1.3, §6): the content commands the founder runs under the
 * {@code pipeline} profile — picocli over the founder-owned inputs in {@code pipeline/inputs/}
 * (§6.2), every command idempotent and re-runnable, failing loudly, writing its report under
 * {@code pipeline/reports/} (§6.3). Owns no tables: loads go through the {@code curriculum} api.
 * Allowed dependencies per §1.4: {@code common :: api} and {@code curriculum :: api};
 * {@code ai :: api} and {@code storage :: api} join at D14 with {@code ncert extract}.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "pipeline",
        allowedDependencies = {"common :: api", "curriculum :: api"})
package com.margai.pipeline;
