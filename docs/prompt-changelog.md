# Prompt changelog

One line per change to any template under `server/src/main/resources/prompts/*.st`, model tier
routing, or retrieval parameters (`.claude/rules/ai-layer.md`). Every entry names the eval run
that gated it. Newest first.

| Date | Template / area | Change | Eval result | Commit |
|---|---|---|---|---|
| 2026-09-06 | `smoke` v1 (new) | D5 connectivity smoke: mentor persona + NEET syllabus outline as a system prefix above the 4,096-token cache minimum, user turn asks for one forced tool call (`SmokeAnswer`) | placeholder PASS, 0 fixtures (suite arrives D23) | d5-ai-seam T2 |
