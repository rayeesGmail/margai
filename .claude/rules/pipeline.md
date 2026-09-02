---
paths:
  - "pipeline/**"
---

# pipeline/ rules

- Every step is a CLI command, idempotent and re-runnable: upsert by natural keys
  (`syllabus_nodes.code`, book/chapter/para addressing, question source+year+number).
- Order matters (DEV_SPEC §7): taxonomy → NCERT ingest → PYQ ingest/solutions/verification →
  stats → backbone → seed generation → cutoffs. A step must fail loudly, never half-write.
- Founder-owned inputs (taxonomy CSV, prerequisites CSV, archetype YAML, cutoffs CSV) are data
  files in the repo; the pipeline never edits them.
- AI calls (solutions, distractor maps, embeddings) go through `AiClient` with the cost ledger and
  Bedrock batch mode; generated solutions are `verified=false` until the verification pass passes.
- Every run prints a coverage/quality report the founder can spot-check (PLAN.md D14–D22 ✅ checks).
- Source PDFs and large artefacts live in S3 `content/`, not in git; `pipeline/data/` is ignored.
