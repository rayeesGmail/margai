# pipeline/ — content batch jobs

Taxonomy load → NCERT ingest (EN + HI, embeddings) → PYQ ingest + AI solutions + verification →
stats → backbone → seed generation → cutoffs (DEV_SPEC §7). Run as CLI; every step idempotent
and re-runnable (upsert by natural keys). AI calls go through `AiClient` and the cost ledger like
everything else.

Tooling and language are decided in the **D3** technical plan; first jobs land in **Week 3**
(D13–D24). Nothing here yet by design.

Rules that apply here: `.claude/rules/pipeline.md`.
