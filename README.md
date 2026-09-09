# MARG AI

AI mentor app for NEET. The product contract is `docs/SPEC.md`; how it is built is `docs/TECH_PLAN.md`
(the technical plan approved at D3: architecture, data model, API, AI pipeline, app, pipeline, infra,
testing), with `docs/DEV_SPEC.md` §13 as the working agreements and its §2–12 as reference;
`docs/PLAN.md` is the 14-week schedule and `docs/TRACKER.md` the live state; choices the specs leave
open are logged in `docs/DECISIONS.md`. Working agreements for agentic sessions are in `CLAUDE.md`.

| Directory | What | Check |
|---|---|---|
| `server/` | Spring Boot 4 API, Java 25, Maven, Flyway, Postgres 18 + pgvector | `cd server && ./mvnw verify` |
| `app/` | Flutter client (Android first), Riverpod, ARB en/hi | `cd app && flutter analyze && flutter test` |
| `pipeline/` | founder-owned inputs (`inputs/`) and run reports (`reports/`) for the content pipeline; the commands themselves run in `server/` under the `pipeline` profile (TECH_PLAN §6) — from Week 3 | |
| `eval/` | AI eval gate, required after any prompt/routing/retrieval change | `cd eval && ./run.sh` |
| `scripts/` | commit gate, path/secret guards, `dev-setup.sh`, `ui.sh` (drives the app on a device for a proof, see app/README) | |

## Fresh clone to running stack (macOS, Apple Silicon)

Prerequisites: [Homebrew](https://brew.sh) and Docker Desktop, running.

```bash
git clone https://github.com/rayeesGmail/margai.git && cd margai
scripts/dev-setup.sh                 # JDK 25, Flutter stable, Android SDK; add --emulator for an AVD
docker compose up -d --wait db       # Postgres 18 + pgvector on localhost:5432 (margai/margai)
cd server && ./mvnw verify           # builds, runs the Testcontainers boot test
./mvnw spring-boot:run               # http://localhost:8080/actuator/health → {"status":"UP"}
cd ../app && flutter run             # on a USB-debugging phone or the AVD
```

Everything is idempotent; re-run `scripts/dev-setup.sh` after a machine change. Databases and
secrets: local values are the compose defaults above; real values live in SSM and are never in the tree.

## CI

`.github/workflows/ci.yml` runs on every pull request and on pushes to `main`: guardrail scripts and
secret scan, `mvnw verify` on Java 25, `flutter analyze` + `flutter test` on stable, and the eval gate.
