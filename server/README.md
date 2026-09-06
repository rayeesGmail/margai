# server/ — Spring Boot modular monolith

Java 25 (latest LTS) · Spring Boot 4.1 · Maven (wrapper) · Flyway · PostgreSQL 18 with pgvector + pg_trgm ·
Spring Modulith (module boundaries as a test). One deployable, no microservices (DEV_SPEC §2,
TECH_PLAN §1). All model access goes through the single `AiClient` interface (see "AI seam"
below); `FakeAiClient` is the default outside a human-launched `BEDROCK_LIVE=1` run.

## Run locally

```bash
docker compose up -d --wait db          # from the repo root; Postgres 18 + pgvector on :5432
cd server && ./mvnw spring-boot:run     # http://localhost:8080/actuator/health → {"status":"UP", components.db UP}
```

`./mvnw spring-boot:run` activates the `local` profile (TECH_PLAN §1.2 "developer default", set in
`pom.xml`): Flyway applies `db/migration` and then the `db/seed` test taxonomy — two subjects, six
chapters, one archetype track, three synthetic cutoffs — against the compose db. The packaged jar
activates no profile, so a deployed environment never sees the seed.

`./mvnw` uses `JAVA_HOME` if set, otherwise the highest JDK known to `/usr/libexec/java_home`;
`scripts/dev-setup.sh` links the Homebrew `openjdk@25` there. Connection defaults target the compose
db and can be overridden with `DB_URL`, `DB_USER`, `DB_PASSWORD`. If something else already owns
port 8080 on your machine, `SERVER_PORT=8081 ./mvnw spring-boot:run`.

## Build and test

`cd server && ./mvnw verify` — required before every commit (enforced by `scripts/precommit-gate.sh`).
Tests share one `pgvector/pgvector:pg18` container per JVM (`TestcontainersConfiguration`), so Docker
must be running; the compose db is not used by tests. What runs (TECH_PLAN §8):

- `ModularityTest` — Spring Modulith verifies the §1.3–§1.4 module boundaries.
- `ArchitectureTest` (ArchUnit) — only `ai.internal.bedrock` imports the AWS SDK, only the `ai`
  module touches `AiClient`, only the difficulty router produces a routed `RouteDecision`,
  controllers live in `web` packages. `ModelIdLiteralTest` — no model id literal in `ai` sources.
- `MargaiApplicationTests` — boot proof: migrations apply, every JPA entity validates against them
  (`ddl-auto: validate`), health is UP, and the seed is absent without a profile.
- `*ConstraintsTest` — `@DataJpaTest` slices per module: checks, partial uniques, foreign keys.
- `SeedTaxonomyTest` — the `test` profile loads the seed taxonomy and its graph is acyclic.
- `MigrationReversibilityTest` — empty database → latest → every `-- ROLLBACK:` block newest first
  → only `flyway_schema_history` remains.
- `AiSeamFlowTest` and the `ai` unit tests — the AI seam on the fake: ledger row per outcome,
  breaker, tier policy, schema repair, retries, the Bedrock request mapping without a network.
- `BedrockSmokeTest` — skipped unless `BEDROCK_LIVE=1` (see below).

## AI seam (TECH_PLAN §4.1, §4.8, §4.11)

`com.margai.ai` owns the one `AiClient` bean: a decorator chain, outermost first,
`ledger > breaker > tier-policy > schema > retry` around an inner client — `FakeAiClient` by
default, `BedrockAiClient` when the `bedrock` profile is active. The chain is logged at startup
(`AiClient chain: …`) and the same breaker and ledger run in every profile.

- **Ledger** — every call, every outcome, one `ai_calls` row (`ok`, `invalid_output`, `timeout`,
  `error`, `breaker`) with tokens and `cost_paise` computed at insert from `margai.ai.prices-json`
  and `margai.ai.usd-inr`; written in its own transaction. Metrics `ai.calls`, `ai.cost.paise`,
  `ai.latency`.
- **Breaker** — `margai.ai.budget.user-daily-paise` (₹25) and `global-daily-paise` per IST day.
- **Tier policy** — `reason` needs a `RouteDecision` (router, `verification()`, `generation()`);
  `vision` needs images.
- **Schema** — the output record's JSON schema (snake_case, all fields required, no extras) is the
  forced Bedrock tool's input schema and the validator's schema; one repair retry, then
  `InvalidOutputException`.
- **Retry** — two jittered retries on throttling and 5xx; timeouts (20 s, `margai.ai.call-timeout`)
  are not retried.
- **Prompts** — `src/main/resources/prompts/<name>.v<N>.stg`, StringTemplate 4 group files with a
  `system` template (the cached prefix) and a `user` template; active version per prompt from
  `margai.ai.prompts.<name>.version`, else the highest present. Every edit needs the eval gate and
  a line in `docs/prompt-changelog.md`.
- **Fake fixtures** — `ai-fixtures/<prompt>.<case>.json` (the output object only): main resources
  are the runtime default, test resources add cases; `variables.fixture_case` picks one, otherwise a
  deterministic hash of the rendered user prompt does; `_`-prefixed cases are failure cases reachable
  only by name; `<case>.repaired.json` answers the repair retry. Usage is realistic (≈ 4 chars per
  token, simulated prompt cache) and rows carry the configured model id.
- **Config** — `margai.ai.*` in `application.yml` holds the local defaults (tier model ids, embed
  model, prices, budgets, batch minimum, timeout, prompt versions); SSM overrides them per
  environment through environment variables (`MARGAI_AI_TIER_CHEAP`, …). No model id, price or
  limit lives in Java.

### Live smoke (D5 acceptance, founder-run)

The only way to reach Bedrock from a developer machine is a human-launched run with the AWS SSO
profile; Claude sessions cannot (CLAUDE.md). With Docker running:

```bash
cd server && BEDROCK_LIVE=1 AWS_PROFILE=<your sso profile> ./mvnw test -Dtest=BedrockSmokeTest -Dsurefire.failIfNoSpecifiedTests=false
```

`BedrockSmokeTest` makes two `smoke` calls on the `cheap` tier and asserts: both `ai_calls` rows
`ok` with non-zero input and output tokens, the forced tool echoed the number, the first row wrote
the prompt cache and the second read it. The rows are printed. `BEDROCK_LIVE` is unset again
afterwards; without it the test is skipped and `./mvnw verify` never touches AWS. To run the API
itself against Bedrock: `BEDROCK_LIVE=1 AWS_PROFILE=<profile> ./mvnw spring-boot:run`.

## Modules and schema

Packages `com.margai.<module>` with `api` (public), `internal` and, where a module owns HTTP, `web`
(TECH_PLAN §1.3). A module owns its tables; other modules hold ids, never JPA associations, and
read through the owner's `api`.

Schema changes only via Flyway migrations in `src/main/resources/db/migration/` plus the matching
JPA entity change; every migration carries its undo in a `-- ROLLBACK:` block (see the README
there). Rules that apply here: `.claude/rules/server.md`, `.claude/rules/ai-layer.md`.
