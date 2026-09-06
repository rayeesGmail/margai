---
paths:
  - "server/**/ai/**"
  - "server/src/main/resources/prompts/**"
  - "eval/**"
---

# AI layer rules (DEV_SPEC §4, §13.4)

- All model access goes through the single `AiClient` interface. No feature code calls Bedrock
  directly. Implementations: `BedrockAiClient` (the `bedrock` profile: `BEDROCK_LIVE=1` locally,
  the ECS task definition in AWS; TECH_PLAN §1.2) and `FakeAiClient` (the default everywhere
  else; fixtures under `ai-fixtures/`). Both run behind the same decorator chain — ledger,
  breaker, tier policy, schema validation, retry (TECH_PLAN §4.1) — and only task classes in
  `ai.tasks` call `AiClient` (ArchUnit).
- Prompt templates live in `server/src/main/resources/prompts/<name>.v<N>.stg` (StringTemplate 4
  group files with a `system` template, the cached prefix, and a `user` template; the active
  version is `margai.ai.prompts.<name>.version`, TECH_PLAN §4.12); never inline prompt strings.
- Any change to prompts, tier routing or retrieval requires: `cd eval && ./run.sh` passing
  (≥97% correct, zero unverified numericals) AND a line in `docs/prompt-changelog.md`.
  `scripts/precommit-gate.sh` blocks the commit until the eval stamp matches.
- REASON-tier calls carry a `RouteDecision`, produced only by the difficulty router,
  `RouteDecision.verification()` or `RouteDecision.generation()` (TECH_PLAN §4.2);
  `TierPolicyAiClient` rejects any other REASON request. Direct tier selection is a bug.
- No answer without NCERT retrieval grounding. Numericals are verified by an independent
  re-solve; mismatch → one regeneration → honest fallback + audit queue. Never render unverified.
- Cache writes only after `verified=true`. Lookup: exact hash, then vector similarity >0.93
  within the same subject.
- Every call writes an `ai_calls` row (tokens from the response, cost from the config price table).
  The per-user daily budget breaker applies in every profile, including dev.
- Model IDs, prompts versions, limits and prices come from config (SSM), never code constants.
- "NTA trap" notes only when a linked PYQ backs them (Evidence rule).
