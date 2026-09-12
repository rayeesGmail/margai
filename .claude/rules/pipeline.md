---
paths:
  - "pipeline/**"
  - "server/**/pipeline/**"
---

# pipeline/ rules

- Every step is a CLI command, idempotent and re-runnable: upsert by natural keys
  (`syllabus_nodes.code`, book/chapter/para addressing, question source+year+number).
- Order matters (TECH_PLAN §6.3): taxonomy — `taxonomy load`, `taxonomy prerequisites`,
  `backbone load`, `cutoffs load` (D13) → NCERT — `ncert register|render|extract|load|align|embed`
  (D14–D17) → PYQ — `pyq load|solve|verify|distractors` (D19–D21) → `stats compute` (D22) →
  `anchors link`, `eval snapshot` (D23) → seed generation and trap mining (unscheduled, §12.2) →
  `cache seed` (D76). A step must fail loudly, never half-write: one transaction per natural-key batch.
- Founder-owned inputs live in `pipeline/inputs/` (taxonomy CSV, prerequisites CSV, archetypes YAML,
  cutoffs CSV, books YAML, PYQ papers JSON — TECH_PLAN §6.2) as data files in the repo; the pipeline
  never edits them. Every command writes `pipeline/reports/<date>-<command>.md`, committed as the
  day's evidence (§6.3).
- The commands are Java under the `pipeline` Spring profile (picocli, TECH_PLAN §6.1) in the
  `pipeline` module (`server/**/pipeline/**`), which reaches other modules only through their `api`
  packages. AI calls (solutions, distractor maps, embeddings) go through `AiClient` with the cost
  ledger, batched through `completeBatch` above the configured minimum (§4.11); generated solutions
  are `verified=false` until the verification pass passes.
- Every run prints a coverage/quality report the founder can spot-check (PLAN.md D13–D22 ✅ checks).
- Source PDFs and large artefacts live in S3 `content/`, not in git; `pipeline/data/` is ignored.
