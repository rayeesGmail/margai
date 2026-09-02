---
paths:
  - "server/**"
---

# server/ rules

- Spring Boot 4 modular monolith; package layout and module boundaries follow the D3-approved
  technical plan in docs/. No microservices, no second deployable.
- `cd server && ./mvnw verify` must pass before any commit (enforced by scripts/precommit-gate.sh).
- Every service-layer change ships with tests; controllers are tested via MockMvc.
- REST base `/api/v1`; error envelope `{error: {code, message_en, message_user_lang}}`;
  per-user rate limits; destructive or paid actions idempotent via `Idempotency-Key` (DEV_SPEC §5).
- `correct_key` never appears in any client payload; judging is server-side only.
- Schema changes only via a Flyway migration under `src/main/resources/db/migration/`
  (`V<n>__<snake_name>.sql`, never edit an applied one) plus the matching JPA entity change.
  Naming: snake_case, UUID PK `gen_random_uuid()`, `created_at`, `updated_at` (DEV_SPEC §3).
- Configuration (model IDs, tiers, limits, prices, feature flags) comes from config / SSM,
  never from code constants. No credentials anywhere in the tree; `FakeAiClient` is the default.
- Structured JSON logs with request IDs end-to-end; every Bedrock call writes an `ai_calls` row.
- Copy shown to students is externalised (ARB on the app side; server returns codes + both languages).
