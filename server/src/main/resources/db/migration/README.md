# Flyway migrations

Schema changes happen ONLY here, plus the matching JPA entity change (CLAUDE.md hard rule,
`.claude/rules/server.md`). Naming: `V<n>__<snake_name>.sql`; never edit a migration that has
been applied anywhere. The first migration (D4) creates the `vector` and `pg_trgm` extensions so
local Docker and RDS start from the same state.

This directory is intentionally empty until D4; it exists so Flyway's location check passes at boot.
