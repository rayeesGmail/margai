/**
 * pipeline module (TECH_PLAN §1.3, §6): the content commands the founder runs under the
 * {@code pipeline} profile — picocli over the founder-owned inputs in {@code pipeline/inputs/}
 * (§6.2), every command idempotent and re-runnable, failing loudly, writing its report under
 * {@code pipeline/reports/} (§6.3). Owns no tables: loads go through the {@code curriculum} api.
 * Allowed dependencies per §1.4: {@code common :: api}, {@code curriculum :: api},
 * {@code storage :: api} (D14, the content bucket), {@code ai :: tasks} (D14, the extraction task
 * — a feature module calls tasks, never the {@code AiClient} seam, §4.1), {@code ai :: api}
 * (D14, reading back what a run cost from the ledger for its report, §10.5) and
 * {@code ai :: retrieval} (D15, the concept-query run that ends {@code ncert embed}; D23's
 * {@code anchors link} uses the same component, §4.9).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "pipeline",
        allowedDependencies = {"common :: api", "curriculum :: api", "storage :: api",
                "ai :: tasks", "ai :: api", "ai :: retrieval"})
package com.margai.pipeline;
