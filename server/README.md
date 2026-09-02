# server/ — Spring Boot modular monolith

Java (latest LTS) · Spring Boot 4.x · Maven · Flyway · PostgreSQL 18 with pgvector + pg_trgm.
One deployable, no microservices (DEV_SPEC §2). All model access goes through the single
`AiClient` interface; `FakeAiClient` is the default outside a human-launched `BEDROCK_LIVE=1` profile.

Scaffolded at **D2** (PLAN.md). Until `./mvnw` exists here, `scripts/precommit-gate.sh` skips the
server check with a loud warning. Build and test: `cd server && ./mvnw verify`.

Rules that apply here: `.claude/rules/server.md`, `.claude/rules/ai-layer.md`.
