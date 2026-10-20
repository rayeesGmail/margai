-- V9__questions.sql — D19 (TECH_PLAN §2.3, §2.9, §15.6)
-- Purpose: the PYQ half of SPEC §9 item 3's question bank — the official papers as the founder's
-- register lists them, their questions with the answer key held server-side, secondary topic tags,
-- and the `pyq tag` ledger category. §2.3 as amended by the founder's D19 plan rulings of
-- 2026-10-20 (DECISIONS): pyq_papers, a nullable correct_key with answer_status and accepted_keys,
-- out_of_syllabus (§15.6), figures, extraction and tagging.
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS question_topics;
-- DROP TABLE IF EXISTS questions;
-- DROP TABLE IF EXISTS pyq_papers;
-- ALTER TABLE ai_calls DROP CONSTRAINT IF EXISTS ai_calls_feature_check;
-- ALTER TABLE ai_calls ADD CONSTRAINT ai_calls_feature_check CHECK (feature IN ('doubt', 'doubt_route', 'doubt_verify', 'doubt_render', 'doubt_extract', 'plan', 'mentor_message', 'classify', 'srs_variant', 'extract_document', 'embed', 'pipeline_extract', 'pipeline_solution', 'pipeline_verify', 'pipeline_distractor', 'pipeline_difficulty', 'pipeline_trap', 'pipeline_generate', 'eval', 'smoke'));
-- END ROLLBACK

-- One row per official question paper — one booklet code of one sitting — loaded by
-- `pyq load --paper` from the founder-owned register pipeline/inputs/pyq-papers.yaml; code is the
-- register's id ('neet-2025-45') and paper_code the booklet code as printed ('45', 'S6', 'E1').
-- exam_date is the IST calendar date of the sitting (§11.1).
--
-- official_hindi records that the booklet carries NTA's own Hindi text: the parked Hindi pass
-- extracts it rather than translating (founder ruling, 2026-10-20).
--
-- The JSONB columns are read whole, never queried by key (§2.1). sections is
-- [{subject, section, from, to, attempt}], which a mock reads to assemble the paper (D35). marking
-- is the paper's own scheme — +4/−1/0 from the booklet's instructions, and NTA's published rule for
-- dropped questions and for multiple accepted answers with the notice cited; mock scoring follows
-- it, never a default (§15.6, founder ruling 2026-10-20). key_source is the answer key's provenance:
-- tier (official | archived-official | mirrored-official), where from, download date, file, sha256.
-- source is the booklet file(s) with sha256 and the pages read.
CREATE TABLE pyq_papers (
    id             UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    code           VARCHAR(32) NOT NULL,
    exam           VARCHAR(8)  NOT NULL,
    year           SMALLINT    NOT NULL,
    paper_code     VARCHAR(16) NOT NULL,
    exam_date      DATE        NOT NULL,
    official_hindi BOOLEAN     NOT NULL,
    question_count SMALLINT    NOT NULL,
    sections       JSONB       NOT NULL,
    marking        JSONB       NOT NULL,
    key_source     JSONB       NOT NULL,
    source         JSONB       NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT pyq_papers_code_key             UNIQUE (code),
    CONSTRAINT pyq_papers_paper_key            UNIQUE (exam, year, paper_code),
    CONSTRAINT pyq_papers_exam_check           CHECK (exam IN ('NEET', 'AIPMT')),
    CONSTRAINT pyq_papers_year_check           CHECK (year BETWEEN 2000 AND 2100),
    CONSTRAINT pyq_papers_question_count_check CHECK (question_count > 0)
);

-- §2.3's question bank: PYQs now, generated questions later (SPEC §9 item 3). A PYQ is addressed by
-- (exam, year, paper_code, question_no) — the natural key and `pyq load`'s upsert key (§6.3) — and
-- the four are required only when source = 'pyq'. The composite foreign key ties a PYQ to its
-- paper's marking and sections without a paper_id column; it is MATCH SIMPLE, so a generated row
-- with NULLs there is unconstrained. The natural key's unique index also serves a paper's questions
-- in order (mocks, §15.6).
--
-- node_id is the primary topic or chapter; an out-of-syllabus question (§15.6: flagged at tagging,
-- excluded from a mock's score) keeps the nearest chapter. options is [{key, textEn, textHi}] —
-- the JSONB keys are the records' component names, camelCase, as ncert_paragraphs.extraction's are
-- — with keys '1'–'4' as NTA prints them.
--
-- The answer: answer_status 'single' carries correct_key alone; 'multiple' (NTA accepted more than
-- one option) carries every accepted key in accepted_keys and the lowest of them in correct_key;
-- 'dropped' (NTA dropped the question) carries neither. Every key is '1'–'4' (questions_keys_check);
-- a duplicate inside accepted_keys is the loader's refusal, not a constraint. correct_key,
-- accepted_keys and answer_status are server-only until the student's answer is recorded (§2.3,
-- §0.4 #4, CLAUDE.md hard rule 1): 'multiple' or 'dropped' is itself a hint. Judging reads
-- accepted_keys for a 'multiple' question, never correct_key alone. The explicit NULL tests in
-- questions_answer_check stop a NULL array or a NULL element from passing it as unknown.
--
-- figures is [{at: 'stem' | 'option:<key>', page, description}], recorded at D19 and cropped and
-- checked before D31; a figure question is never served without its crop. extraction is the
-- transcription's provenance (pages, ai_call ids, model, prompt version, checks); tagging is the
-- tag's (ai_call id, model, prompt version, confidence, reason).
--
-- The HNSW index on embedding comes with V10 at D23 (§2.9). generated_from_error_entry_id names the
-- error entry a variant came from (D51); error_entries lands at D49, whose migration adds the
-- foreign key.
CREATE TABLE questions (
    id                            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    source                        VARCHAR(16)  NOT NULL,
    exam                          VARCHAR(8),
    year                          SMALLINT,
    paper_code                    VARCHAR(16),
    question_no                   SMALLINT,
    node_id                       UUID         NOT NULL,
    out_of_syllabus               BOOLEAN      NOT NULL DEFAULT false,
    stem_en                       TEXT         NOT NULL,
    stem_hi                       TEXT,
    options                       JSONB        NOT NULL,
    correct_key                   CHAR(1),
    answer_status                 VARCHAR(8)   NOT NULL DEFAULT 'single',
    accepted_keys                 VARCHAR(1)[],
    solution_md_en                TEXT,
    solution_md_hi                TEXT,
    difficulty                    NUMERIC(3,2),
    avg_time_sec                  INTEGER,
    distractor_map                JSONB,
    embedding                     vector(1024),
    verified                      BOOLEAN      NOT NULL DEFAULT false,
    audit_status                  VARCHAR(16),
    verification                  JSONB,
    generated_from_error_entry_id UUID,
    figures                       JSONB        NOT NULL DEFAULT '[]',
    extraction                    JSONB,
    tagging                       JSONB,
    created_at                    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at                    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT questions_pyq_key             UNIQUE (source, exam, year, paper_code, question_no),
    CONSTRAINT questions_paper_fkey          FOREIGN KEY (exam, year, paper_code) REFERENCES pyq_papers(exam, year, paper_code) ON DELETE RESTRICT,
    CONSTRAINT questions_node_id_fkey        FOREIGN KEY (node_id) REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT questions_source_check        CHECK (source IN ('pyq', 'generated')),
    CONSTRAINT questions_exam_check          CHECK (exam IS NULL OR exam IN ('NEET', 'AIPMT')),
    CONSTRAINT questions_pyq_key_check       CHECK (source <> 'pyq' OR (exam IS NOT NULL AND year IS NOT NULL AND paper_code IS NOT NULL AND question_no IS NOT NULL)),
    CONSTRAINT questions_question_no_check   CHECK (question_no IS NULL OR question_no > 0),
    CONSTRAINT questions_answer_status_check CHECK (answer_status IN ('single', 'multiple', 'dropped')),
    CONSTRAINT questions_answer_check        CHECK (
        (answer_status = 'single' AND correct_key IS NOT NULL AND accepted_keys IS NULL)
        OR (answer_status = 'multiple' AND correct_key IS NOT NULL AND accepted_keys IS NOT NULL
            AND array_position(accepted_keys, NULL) IS NULL
            AND cardinality(accepted_keys) >= 2 AND correct_key = ANY (accepted_keys))
        OR (answer_status = 'dropped' AND correct_key IS NULL AND accepted_keys IS NULL)
    ),
    CONSTRAINT questions_keys_check          CHECK ((correct_key IS NULL OR correct_key IN ('1', '2', '3', '4'))
        AND (accepted_keys IS NULL OR accepted_keys <@ ARRAY['1', '2', '3', '4']::VARCHAR(1)[])),
    CONSTRAINT questions_difficulty_check    CHECK (difficulty IS NULL OR difficulty BETWEEN 0 AND 1),
    CONSTRAINT questions_avg_time_sec_check  CHECK (avg_time_sec IS NULL OR avg_time_sec > 0),
    CONSTRAINT questions_audit_status_check  CHECK (audit_status IS NULL OR audit_status IN ('auto', 'founder_ok', 'flagged'))
);

-- §2.3's three lookup paths: by topic and difficulty band, by source and year (per-year counts,
-- stats), and verified questions by topic.
CREATE INDEX questions_node_id_difficulty_idx ON questions (node_id, difficulty);
CREATE INDEX questions_source_year_idx        ON questions (source, year);
CREATE INDEX questions_verified_idx           ON questions (node_id) WHERE verified;

-- Secondary topics only (§2.3): the primary is questions.node_id, and a question carries at most
-- two secondaries — both service checks, not expressible as constraints. Both timestamps, as every
-- non-append-only table (§2.1) and as syllabus_prerequisites, the other key-only table, carries.
CREATE TABLE question_topics (
    question_id UUID        NOT NULL,
    node_id     UUID        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT question_topics_pkey             PRIMARY KEY (question_id, node_id),
    CONSTRAINT question_topics_question_id_fkey FOREIGN KEY (question_id) REFERENCES questions(id)      ON DELETE RESTRICT,
    CONSTRAINT question_topics_node_id_fkey     FOREIGN KEY (node_id)     REFERENCES syllabus_nodes(id) ON DELETE RESTRICT
);

-- The primary key serves lookups by question; this covers a topic's secondary questions.
CREATE INDEX question_topics_node_id_idx ON question_topics (node_id);

-- `pyq tag` writes its calls under their own ledger category (AiFeature: adding a value is a
-- one-line migration on the CHECK). The list is V5's plus 'pipeline_tag'.
ALTER TABLE ai_calls DROP CONSTRAINT ai_calls_feature_check;
ALTER TABLE ai_calls ADD CONSTRAINT ai_calls_feature_check CHECK (feature IN (
    'doubt', 'doubt_route', 'doubt_verify', 'doubt_render', 'doubt_extract',
    'plan', 'mentor_message', 'classify', 'srs_variant', 'extract_document', 'embed',
    'pipeline_extract', 'pipeline_solution', 'pipeline_verify', 'pipeline_distractor',
    'pipeline_difficulty', 'pipeline_trap', 'pipeline_generate', 'pipeline_tag',
    'eval', 'smoke'
));
