-- V1__extensions.sql — D4 (TECH_PLAN §2.9)
-- Purpose: create the PostgreSQL extensions the schema depends on (pgvector for embeddings, pg_trgm for trigram search) so local Docker and RDS start from the same state.
--
-- ROLLBACK:
-- DROP EXTENSION IF EXISTS pg_trgm;
-- DROP EXTENSION IF EXISTS vector;
-- END ROLLBACK

CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS pg_trgm;
