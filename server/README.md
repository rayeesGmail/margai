# server/ — Spring Boot modular monolith

Java 25 (latest LTS) · Spring Boot 4.1 · Maven (wrapper) · Flyway · PostgreSQL 18 with pgvector + pg_trgm ·
Spring Modulith (module boundaries as a test). One deployable, no microservices (DEV_SPEC §2,
TECH_PLAN §1). All model access goes through the single `AiClient` interface (arrives D5);
`FakeAiClient` is the default outside a human-launched `BEDROCK_LIVE=1` profile.

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
- `MargaiApplicationTests` — boot proof: migrations apply, every JPA entity validates against them
  (`ddl-auto: validate`), health is UP, and the seed is absent without a profile.
- `*ConstraintsTest` — `@DataJpaTest` slices per module: checks, partial uniques, foreign keys.
- `SeedTaxonomyTest` — the `test` profile loads the seed taxonomy and its graph is acyclic.
- `MigrationReversibilityTest` — empty database → latest → every `-- ROLLBACK:` block newest first
  → only `flyway_schema_history` remains.

## Modules and schema

Packages `com.margai.<module>` with `api` (public), `internal` and, where a module owns HTTP, `web`
(TECH_PLAN §1.3). A module owns its tables; other modules hold ids, never JPA associations, and
read through the owner's `api`.

Schema changes only via Flyway migrations in `src/main/resources/db/migration/` plus the matching
JPA entity change; every migration carries its undo in a `-- ROLLBACK:` block (see the README
there). Rules that apply here: `.claude/rules/server.md`, `.claude/rules/ai-layer.md`.
