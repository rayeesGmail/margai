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
- `ArchitectureTest` (ArchUnit) — the AWS SDK only in `ai.internal.bedrock` (Bedrock) and
  `auth.internal.email` (SES), each service SDK in its own package, only the `ai` module touches
  `AiClient`, only the difficulty router produces a routed `RouteDecision`, controllers live in `web`
  packages. `ModelIdLiteralTest` — no model id literal in `ai` sources.
- `AuthFlowTest`, `SecurityChainTest`, `AuthControllerTest` and the `auth` unit tests — OTP request
  and verify end to end, the chain's 401 envelopes, token rotation and reuse detection, rate limits,
  and a log-appender proof that the module never logs a code (see "Auth" below).
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
  `margai.ai.prompts.<name>.version`, else the highest present. `_`-prefixed groups
  (`_protocol.v1.stg`) hold shared model-facing fragments: the tool description and the repair
  message. Every edit needs the eval gate and a line in `docs/prompt-changelog.md`.
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

The only way to reach Bedrock from a developer machine is a human-launched run with AWS credentials
in the SDK's default chain; Claude sessions cannot (CLAUDE.md). The developer identity is the
**IAM Identity Center (SSO) profile `margai`** with Bedrock permissions, created under founder
workstream F8 on 2026-09-12 (TECH_PLAN §7.4, §7.6) — not the account root user. The SDK resolves
that profile only because the `sso` and `ssooidc` modules are on the runtime classpath beside
`signin` (which serves the older `aws login` session in the default profile); without them the
chain refuses the profile with *"the `sso` service module must be on the class path"* while the
AWS CLI, which has its own resolver, works fine. With Docker running:

```bash
aws sso login --profile margai    # when the cached token has expired
cd server && AWS_PROFILE=margai BEDROCK_LIVE=1 ./mvnw test -Dtest=BedrockSmokeTest -Dsurefire.failIfNoSpecifiedTests=false
```

`BedrockSmokeTest` makes two `smoke` calls on the `cheap` tier and asserts: both `ai_calls` rows
`ok` with non-zero input and output tokens, the forced tool echoed the number, the first row wrote
the prompt cache and the second read it. The rows are printed. `BEDROCK_LIVE` is unset again
afterwards; without it the test is skipped and `./mvnw verify` never touches AWS. To run the API
itself against Bedrock: `AWS_PROFILE=margai BEDROCK_LIVE=1 ./mvnw spring-boot:run`.

## Auth (D7): OTP by email, tokens, rate limits

TECH_PLAN §3.2, §3.4, §3.7, §9.1; the D7 founder ruling in DECISIONS.md (2026-09-08). Routes under
`/api/v1/auth`: `POST /otp/request` with `{email}` — or `{phone}` once `margai.auth.otp.channels`
includes `sms`, which waits for the DLT template (TRACKER F1) — answers `{challenge_id,
resend_after_s, channel}`; `POST /otp/verify` with `{challenge_id, code}` answers `{access_token,
refresh_token, expires_in, is_new_user, user}`; `POST /refresh` with `{refresh_token}` answers the
rotated pair. Access tokens are 15-minute HS256 JWTs (`sub`, `role`, `lang`, `jti`); refresh tokens
rotate inside per-device families and presenting a spent one revokes the family. Limits: 3 codes an
hour per destination, a 30-second resend cooldown, 5 attempts per code, 10 requests an hour per client
address, 60 requests a minute per signed-in user. Errors are the `{error: {code, message_en,
message_user_lang, details}}` envelope in the caller's `Accept-Language` (`hi`, `hi-Latn`, else English).

Locally nothing needs configuring: with `margai.auth.otp.sender = log` (the default) the code is
printed by the logger `margai.otp.sandbox` — that log line is your inbox:

```bash
SERVER_PORT=8081 ./mvnw spring-boot:run
curl -s -X POST localhost:8081/api/v1/auth/otp/request -H 'Content-Type: application/json' \
     -d '{"email":"you@example.com"}'
# → {"challenge_id":"…","resend_after_s":30,"channel":"email"}; the server log shows
#   [sandbox email] to y***@example.com — Your MARG AI sign-in code is 123456. …
curl -s -X POST localhost:8081/api/v1/auth/otp/verify -H 'Content-Type: application/json' \
     -d '{"challenge_id":"…","code":"123456"}'
curl -s -X POST localhost:8081/api/v1/auth/refresh -H 'Content-Type: application/json' \
     -d '{"refresh_token":"…"}'
# D10: the account behind the token, and the language switch; logout revokes this device's family
curl -s localhost:8081/api/v1/me -H 'Authorization: Bearer <access_token>'
curl -s -X PATCH localhost:8081/api/v1/me -H 'Content-Type: application/json' \
     -H 'Authorization: Bearer <access_token>' -d '{"language":"hi"}'
curl -s -i -X POST localhost:8081/api/v1/auth/logout -H 'Content-Type: application/json' \
     -H 'Authorization: Bearer <access_token>' -d '{"refresh_token":"…"}'     # → 204
```

Since D10 a first login also creates the empty `student_profiles` row, and a brand-new account
starts in the `Accept-Language` of the verify call (`hi`, `hi-Latn`, else English); `GET /me`
answers `{user, profile}`, `PATCH /me` takes any of the TECH_PLAN §3.7 fields (absent = unchanged)
and names every bad one with a reason code, and `POST /auth/logout` needs a bearer and answers 204
whatever the token's state. For a short access-token life in a demo, `MARGAI_AUTH_JWT_ACCESS_TTL=30s`
(the JWT validator allows a further 60 s of clock skew).

Secrets: `MARGAI_AUTH_JWT_SECRET` and `MARGAI_AUTH_OTP_PEPPER` (base64, 256-bit; from SSM in AWS,
TECH_PLAN §7.3). When unset the server makes a random value per boot and says so with a WARN, so
tokens and pending codes die on a restart. For stable local tokens export them once in your shell
(for example `export MARGAI_AUTH_JWT_SECRET=$(openssl rand -base64 32)`); nothing reads a `.env` file.

Real email through SES is founder-run, with credentials in the SDK default chain exactly as for
Bedrock: verify a sender identity in SES ap-south-1 — and, while the account is in the SES sandbox,
the recipient addresses you test with (TRACKER F10) — then
`MARGAI_AUTH_OTP_SENDER=ses MARGAI_AUTH_OTP_EMAIL_FROM=you@yourdomain.in SERVER_PORT=8081 ./mvnw spring-boot:run`.

### Seeing the OTP metric (D11)

SPEC §11 sets "OTP success ≥ 98% first attempt". The server counts `otp.sent`, `otp.verified`
(tagged `first_attempt`), `otp.failed` and `otp.send_failed` per channel (TECH_PLAN §10.2) and,
until CloudWatch exists (F8/D73), shows them three ways — all since this instance started:

```bash
# 1. the admin report: flag your own row by hand (TECH_PLAN §3.7), then sign in again so the JWT carries role = admin
docker compose exec -T db psql -U margai -d margai -c "update users set role = 'admin' where email = 'you@example.com'"
curl -s localhost:8081/api/v1/admin/metrics/otp -H 'Authorization: Bearer <admin access_token>'
# → {"since":"…","channels":[{"channel":"sms",…},{"channel":"email","sent":5,"send_failed":0,"verified":4,
#    "verified_first_attempt":3,"wrong_codes":1,"expired_unverified":1,"success_rate":0.8,"first_attempt_rate":0.6}]}
#    a student's token → 403 FORBIDDEN; no token → 401 AUTH_REQUIRED
# 2. the raw counters through the actuator (admin only; health stays public)
curl -s 'localhost:8081/actuator/metrics/otp.verified?tag=channel:email&tag=first_attempt:true' \
     -H 'Authorization: Bearer <admin access_token>'
# 3. the log: one line per enabled channel every margai.auth.otp.report-every (1h; 1m for a demo)
MARGAI_AUTH_OTP_REPORT_EVERY=1m SERVER_PORT=8081 ./mvnw spring-boot:run
#   … OtpDeliveryReporter : otp delivery channel=email since=… sent=5 send_failed=0 verified=4 first_attempt=3
#     wrong_codes=1 expired_unverified=1 success_rate=0.800 first_attempt_rate=0.600
```

`expired_unverified` — codes that reached their expiry without ever being verified — is what an
email that never arrived looks like from the server, and the number to watch until SES delivery
events exist. `first_attempt_rate` is SPEC §11's number; `success_rate` counts any match.

## Modules and schema

Packages `com.margai.<module>` with `api` (public), `internal` and, where a module owns HTTP, `web`
(TECH_PLAN §1.3). A module owns its tables; other modules hold ids, never JPA associations, and
read through the owner's `api`.

Schema changes only via Flyway migrations in `src/main/resources/db/migration/` plus the matching
JPA entity change; every migration carries its undo in a `-- ROLLBACK:` block (see the README
there). Rules that apply here: `.claude/rules/server.md`, `.claude/rules/ai-layer.md`.
