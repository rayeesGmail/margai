# server/ — Spring Boot modular monolith

Java 25 (latest LTS) · Spring Boot 4.1 · Maven (wrapper) · Flyway · PostgreSQL 18 with pgvector + pg_trgm.
One deployable, no microservices (DEV_SPEC §2). All model access goes through the single
`AiClient` interface (arrives D5); `FakeAiClient` is the default outside a human-launched `BEDROCK_LIVE=1` profile.

## Run locally

```bash
docker compose up -d --wait db          # from the repo root; Postgres 18 + pgvector on :5432
cd server && ./mvnw spring-boot:run     # http://localhost:8080/actuator/health → {"status":"UP", components.db UP}
```

`./mvnw` uses `JAVA_HOME` if set, otherwise the highest JDK known to `/usr/libexec/java_home`;
`scripts/dev-setup.sh` links the Homebrew `openjdk@25` there. Connection defaults target the compose
db and can be overridden with `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Build and test

`cd server && ./mvnw verify` — required before every commit (enforced by `scripts/precommit-gate.sh`).
The integration test starts its own `pgvector/pgvector:pg18` container via Testcontainers, so Docker
must be running; the compose db is not used by tests.

Schema changes only via Flyway migrations in `src/main/resources/db/migration/` plus the matching
JPA entity change (first migration lands at D4). Rules that apply here: `.claude/rules/server.md`,
`.claude/rules/ai-layer.md`.
