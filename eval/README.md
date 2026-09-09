# eval/ — the eval gate

Hand-verified question fixtures (`fixtures/`, ~60 at D23, ~150 by D47, ~200 target) plus a runner
(`run.sh`) that drives the doubt pipeline against them. **Gate: ≥97% correct final answers and zero
unverified numericals served** (DEV_SPEC §4.5).

`run.sh` is a placeholder until D23: it passes with 0 fixtures and fails closed the moment fixtures
exist without a runner. On PASS it writes `eval/.last-pass` (gitignored), a content hash of the
AI-touching paths (`server/src/main/resources/prompts/`, `server/**/ai/**`, any path segment naming
a router, routing or retrieval under `server/` or `eval/` in either case — `DifficultyRouter`,
`ParagraphRetrievalRepository` — and `eval/fixtures/*.json`; the app's go_router is outside the rule
since D12). `scripts/precommit-gate.sh` refuses to commit a
change to those paths unless the stamp matches the current content — so after any prompt, routing
or retrieval change: `cd eval && ./run.sh`, then commit. CI runs `eval/run.sh` on every push and PR.

Slash command: `/evalgate`.
