# pipeline/ — content batch jobs

Taxonomy load → NCERT ingest (EN + HI, embeddings) → PYQ ingest + AI solutions + verification →
stats → anchors → seed generation (TECH_PLAN §6.3). Java commands under the `pipeline` Spring
profile (picocli, §6.1); every step idempotent and re-runnable (upsert by natural keys), failing
loudly, never half-writing. AI calls go through `AiClient` and the cost ledger like everything else.

- `inputs/` — the founder-owned data files (§6.2), never edited by the pipeline; see `inputs/README.md`.
- `reports/` — one markdown report per run, `<date>-<command>.md`, committed as the day's evidence (§6.3).
- `data/` — git-ignored scratch for downloaded PDFs and intermediate files.

## Running a command (D13)

From `server/`, with the compose database up (`docker compose up db` at the repository root):

    ./mvnw -q -DskipTests package
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline taxonomy load
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline taxonomy prerequisites
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline backbone load
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline cutoffs load

`--inputs DIR` and `--reports DIR` default to `../pipeline/inputs` and `../pipeline/reports`, relative
to the working directory; `--help` works on every command. Exit codes: 0 ok, 1 the run failed (the
report says why), 2 usage or a missing input. The database comes from the same variables as the api
(`DB_URL`, `DB_USER`, `DB_PASSWORD`, compose defaults otherwise). The `pipeline` profile starts no web
server and uses the production Flyway locations only, so the `db/seed` test taxonomy never meets a
real load — against a developer database that carries the seed, the reports list its stale shapes as
orphans and leave them in place. Live model commands (D14 onwards) add the `live` profile through
`AI_LIVE=1` (DEV_SPEC §13.7), with `margai.ai.provider` choosing the provider inside it.

Rules that apply here: `.claude/rules/pipeline.md`.
