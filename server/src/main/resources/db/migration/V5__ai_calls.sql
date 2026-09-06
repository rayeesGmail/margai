-- V5__ai_calls.sql — D5 (TECH_PLAN §2.8)
-- Purpose: append-only cost ledger — one row per Bedrock call, every feature and every outcome, including failures and fake-client calls (TECH_PLAN §4.8).
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS ai_calls;
-- END ROLLBACK

-- Append-only: no updated_at (§2.1). user_id is NULL for system and pipeline calls and is nulled
-- on account purge (§2.10); the users row itself is a tombstone, so RESTRICT never fires.
-- cost_paise is computed at insert from the config price table (§4.8); a price change never
-- rewrites history. Month partitioning is PARKED until volume asks for it.
CREATE TABLE ai_calls (
    id                 UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id            UUID,
    feature            VARCHAR(24)  NOT NULL,
    model_id           VARCHAR(120) NOT NULL,
    tier               VARCHAR(8)   NOT NULL,
    prompt_name        VARCHAR(64),
    prompt_version     SMALLINT,
    input_tokens       INTEGER,
    output_tokens      INTEGER,
    cache_read_tokens  INTEGER,
    cache_write_tokens INTEGER,
    batch              BOOLEAN      NOT NULL DEFAULT false,
    latency_ms         INTEGER,
    status             VARCHAR(16)  NOT NULL,
    error_code         VARCHAR(64),
    cost_paise         BIGINT       NOT NULL,
    request_id         VARCHAR(64),
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ai_calls_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT ai_calls_feature_check CHECK (feature IN (
        'doubt', 'doubt_route', 'doubt_verify', 'doubt_render', 'doubt_extract',
        'plan', 'mentor_message', 'classify', 'srs_variant', 'extract_document', 'embed',
        'pipeline_extract', 'pipeline_solution', 'pipeline_verify', 'pipeline_distractor',
        'pipeline_difficulty', 'pipeline_trap', 'pipeline_generate',
        'eval', 'smoke'
    )),
    CONSTRAINT ai_calls_tier_check   CHECK (tier IN ('cheap', 'reason', 'vision', 'embed')),
    CONSTRAINT ai_calls_status_check CHECK (status IN ('ok', 'error', 'timeout', 'breaker', 'invalid_output'))
);

-- Per-feature spend over time (nightly ai_spend_daily rebuild, §4.8) and per-user daily budget
-- checks by the breaker.
CREATE INDEX ai_calls_feature_created_at_idx ON ai_calls (feature, created_at);
CREATE INDEX ai_calls_user_id_created_at_idx ON ai_calls (user_id, created_at);
