# F8 — AWS beta stack runbook

Founder workstream F8 (TRACKER) builds the beta stack on the TECH_PLAN §7.6 timeline; every
`terraform apply` is run by the founder. This file collects the post-provision smoke checks, one
block per milestone, in the order §7.6 lists them. The D70 restore drill (TECH_PLAN §7.5) is
added here when it is scripted. Runbooks live in `docs/runbooks/` (TECH_PLAN §7.5).

Facts confirmed before provisioning (console check #3, closed 2026-09-04, DECISIONS.md D4 row):
PostgreSQL 18.6 on `db.t4g.small` in ap-south-1; pgvector 0.8.1 per the AWS release notes.

## RDS post-provision smoke (D55 milestone)

1. Connect to the new instance with the `/margai/beta/db/*` credentials (SSO profile, never
   static keys) and run:

   ```sql
   SELECT name, default_version FROM pg_available_extensions WHERE name = 'vector';
   ```

   Assert one row with `default_version` ≥ 0.8. The `describe-db-engine-versions` API does not
   expose extension catalogs, so this query is the definitive check that HNSW indexes
   (pgvector ≥ 0.5, TECH_PLAN D3.6) will build on this instance.

2. Then let migration V1 create the extensions (`CREATE EXTENSION IF NOT EXISTS vector` and
   `pg_trgm`, TECH_PLAN §2.9); the D55 boot against RDS is the first live Flyway run.
