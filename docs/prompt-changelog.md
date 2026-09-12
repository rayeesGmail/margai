# Prompt changelog

One line per change to any template under `server/src/main/resources/prompts/*.stg` (prompt
groups and `_`-prefixed fragment groups), model tier routing, or retrieval parameters
(`.claude/rules/ai-layer.md`). Every entry names the eval run that gated it. Newest first.

| Date | Template / area | Change | Eval result | Commit |
|---|---|---|---|---|
| 2026-09-12 | model tier routing + the embedding model | Provider switch (DECISIONS 2026-09-12, TECH_PLAN §4.11): REASON moves to the current Sonnet for both the real-time and the batch lane, CHEAP and VISION to the 4.5 cheap model, all as bare direct-API ids; each tier gains the request shape its model accepts (`temperature`, `thinking`, `effort`) and REASON ships thinking-off at effort medium, so **routing behaviour changes even where the tier does not** — the D23/D39 eval compares thinking-on on hard numericals before beta. The embedding model becomes the v4 line at `output_dimension` 1,024, which **redefines every vector's meaning**: the D17 retrieval harness must cover Hindi and Hinglish, and a swap back to the v3 line happens before the D16 corpus embedding if it underperforms | placeholder PASS, 0 fixtures (suite arrives D23) — the real gate for this cannot run until the suite and a key exist, which is why both riders name a day | 6925a41 (+ the audit fixes) |
| 2026-09-12 | `_protocol` v1, `smoke` v1 | Header comments only, no template text: they named Converse and the Bedrock mapper, and the cache-minimum note now points at `margai.ai.tier.*.cache-min-tokens` instead of a hard-coded figure. Rendered prompts byte-identical | placeholder PASS, 0 fixtures | the audit-fix commit |
| 2026-09-06 | `_protocol` v1 (new) | Shared model-facing fragments for every Converse request: the forced tool's description and the repair tool-result message (moved out of Java on the spec-auditor's finding) | placeholder PASS, 0 fixtures (suite arrives D23) | d5-ai-seam audit fixes |
| 2026-09-06 | `smoke` v1 (new) | D5 connectivity smoke: mentor persona + NEET syllabus outline as a system prefix above the 4,096-token cache minimum, user turn asks for one forced tool call (`SmokeAnswer`) | placeholder PASS, 0 fixtures (suite arrives D23) | d5-ai-seam T2 |
