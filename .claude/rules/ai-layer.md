---
paths:
  - "server/**/ai/**"
  - "server/src/main/resources/prompts/**"
  - "eval/**"
---

# AI layer rules (DEV_SPEC §4, §13.4)

- All model access goes through the single `AiClient` interface. No feature code calls a provider
  directly. Implementations: in the `live` profile (`AI_LIVE=1` locally, the ECS task definition in
  AWS; TECH_PLAN §1.2) the provider `margai.ai.provider` names — `AnthropicAiClient` for completions
  joined to `CohereEmbeddingClient` for embeddings (`CompositeAiClient`), or the dormant
  `BedrockAiClient`; `FakeAiClient` everywhere else (fixtures under `ai-fixtures/`). All of them run
  behind the same decorator chain — ledger, breaker, tier policy, schema validation, retry
  (TECH_PLAN §4.1). Only the `ai` module's internal packages and its task classes in `ai.tasks`
  touch `AiClient`; feature modules call task classes (ArchUnit).
- Each provider's SDK or HTTP client stays inside its own package under `ai.internal.<provider>`
  (ArchUnit). Provider API keys are the auth model: SSM SecureString deployed, untracked local
  environment on a laptop, never in code, config, logs or a repo file
  (docs/runbooks/ai-provider-keys.md).
- Per-tier request shape is config, not a code branch: the models differ in what they *accept*
  (the reasoning model rejects `temperature`, the cheap one rejects `effort`), so
  `margai.ai.tier.<t>.{temperature,thinking,effort,cache-min-tokens}` carries it and an absent key
  means the field is not sent. A cached prefix shorter than the model's `cache-min-tokens` is not
  cached at all — the startup warning says so, do not ignore it.
- The embedding pin is provider + model + dimension together. Changing any of the three means
  re-embedding the corpus and re-indexing, never a config flip alone (TECH_PLAN §4.9).
- Prompt templates live in `server/src/main/resources/prompts/<name>.v<N>.stg` (StringTemplate 4
  group files with a `system` template, the cached prefix, and a `user` template; the active
  version is `margai.ai.prompts.<name>.version`, TECH_PLAN §4.12). Shared model-facing fragments
  such as the tool description and the repair message live in `_`-prefixed fragment groups
  (`_protocol.v<N>.stg`). Never inline a model-facing string in Java.
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
