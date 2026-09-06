# Flyway migrations

Schema changes happen ONLY here, plus the matching JPA entity change (CLAUDE.md hard rule,
`.claude/rules/server.md`). The column-by-column source is docs/TECH_PLAN.md §2; the schedule of
which PLAN day lands which version is §2.9.

## Rules

- Naming `V<n>__<snake_name>.sql`, the next free integer; never edit a migration that has been
  applied anywhere — a correction is a new migration.
- Conventions (TECH_PLAN §2.1): snake_case; `id UUID PRIMARY KEY DEFAULT gen_random_uuid()`;
  `created_at` / `updated_at TIMESTAMPTZ NOT NULL DEFAULT now()`; enumerations as `VARCHAR` +
  `CHECK`, never PostgreSQL enum types; foreign keys `ON DELETE RESTRICT`; append-only tables
  (`practice_events`, `ai_calls`, `billing_events`) carry no `updated_at`.
- Every migration is reversible. Its header carries the exact undo, one statement per line:

  ```sql
  -- ROLLBACK:
  -- DROP TABLE IF EXISTS student_profiles;
  -- DROP TABLE IF EXISTS users;
  -- END ROLLBACK
  ```

  `MigrationReversibilityTest` (TECH_PLAN §8.2) migrates an empty database to latest, executes
  these blocks newest first — a `rollback/U<n>__<name>.sql` file takes precedence when the undo
  is non-trivial — and asserts that only `flyway_schema_history` remains. A migration without a
  rollback block fails that test.

## Locations

- `db/migration/` — this directory; applied everywhere.
- `db/seed/` — repeatable migrations (`R__*.sql`) with synthetic data for local development and
  tests; only the `local` and `test` profiles add it to `spring.flyway.locations`. Production
  never sees it; the real taxonomy arrives through the D13 loader.

## Versions

| Version | PLAN day | Creates |
|---|---|---|
| V1 `extensions` | D4 | `vector`, `pg_trgm` |
| V2 `identity` | D4 | `users`, `student_profiles` |
| V3 `curriculum_core` | D4 | `syllabus_nodes`, `syllabus_prerequisites`, `archetype_tracks`, `archetype_track_steps`, `cutoffs` |
| V4 `chapter_status` | D4 | `chapter_status` |
