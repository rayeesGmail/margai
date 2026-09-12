# pipeline/ — content batch jobs

Taxonomy load → NCERT ingest (EN + HI, embeddings) → PYQ ingest + AI solutions + verification →
stats → anchors → seed generation (TECH_PLAN §6.3). Java commands under the `pipeline` Spring
profile (picocli, §6.1); every step idempotent and re-runnable (upsert by natural keys), failing
loudly, never half-writing. AI calls go through `AiClient` and the cost ledger like everything else.

- `inputs/` — the founder-owned data files (§6.2), never edited by the pipeline; see `inputs/README.md`.
- `reports/` — one markdown report per run, `<date>-<command>.md`, committed as the day's evidence (§6.3).
- `data/` — git-ignored scratch for downloaded PDFs and intermediate files.

## Running a command

From `server/`, with the compose database up (`docker compose up db` at the repository root):

    ./mvnw -q -DskipTests package
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline taxonomy load
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline taxonomy prerequisites
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline backbone load
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline cutoffs load

### The NCERT layer (D14–D16)

`register` is database-only. The other three read and write the content bucket, so they need
`AWS_PROFILE=margai`, and `extract` calls a model, so it needs `AI_LIVE=1`, the `live` profile and
the provider key — a live run is human-launched by DEV_SPEC §13.7. The full procedure, the cost
estimate and the D14 acceptance check are in `docs/runbooks/ncert-ingest.md`.

    … --spring.profiles.active=pipeline ncert register
    AWS_PROFILE=margai … ncert render  --book bio11 --lang en
    AI_LIVE=1 AWS_PROFILE=margai … --spring.profiles.active=pipeline,live ncert extract --book bio11 --lang en
    AWS_PROFILE=margai … ncert load    --book bio11 --lang en

| Option | Commands | Meaning |
|---|---|---|
| `--book CODE` | render, extract, load | the book code from `books.yaml` (required) |
| `--lang en\|hi` | render, extract, load | which edition (default `en`; Hindi waits for D16) |
| `--chapters N,N` | render, extract, load | only these printed chapter numbers |
| `--pages N,N` | extract | only these page numbers within each selected chapter |
| `--redo` | extract | call again for pages already in the JSONL, and pay again |

Each command is idempotent: `render` skips page images already in the bucket, `extract` skips pages
already in the JSONL, `load` upserts on the paragraph address.

`--inputs DIR` and `--reports DIR` default to `../pipeline/inputs` and `../pipeline/reports`, relative
to the working directory; `--help` works on every command. Exit codes: 0 ok, 1 the run failed (the
report says why), 2 usage or a missing input. The database comes from the same variables as the api
(`DB_URL`, `DB_USER`, `DB_PASSWORD`, compose defaults otherwise). The `pipeline` profile starts no web
server and uses the production Flyway locations only, so the `db/seed` test taxonomy never meets a
real load — against a developer database that carries the seed, the reports list its stale shapes as
orphans and leave them in place. Live model commands (D14 onwards) add the `live` profile through
`AI_LIVE=1` (DEV_SPEC §13.7), with `margai.ai.provider` choosing the provider inside it.

Rules that apply here: `.claude/rules/pipeline.md`.
