-- V3__curriculum_core.sql — D4 (TECH_PLAN §2.3)
-- Purpose: curriculum backbone — the syllabus tree (subject/unit/chapter/topic), its prerequisite edges, archetype tracks with their ordered steps, and the cutoffs config table.
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS cutoffs;
-- DROP TABLE IF EXISTS archetype_track_steps;
-- DROP TABLE IF EXISTS archetype_tracks;
-- DROP TABLE IF EXISTS syllabus_prerequisites;
-- DROP TABLE IF EXISTS syllabus_nodes;
-- END ROLLBACK

-- code is the stable identifier used everywhere (PHY.11.ROT, PHY.11.ROT.TORQUE). Subject roots
-- have no parent and no class_level; weightage_marks_avg is filled at D22.
CREATE TABLE syllabus_nodes (
    id                    UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    code                  VARCHAR(32)   NOT NULL,
    subject               VARCHAR(16)   NOT NULL,
    class_level           SMALLINT,
    parent_id             UUID,
    kind                  VARCHAR(8)    NOT NULL,
    name_en               VARCHAR(160)  NOT NULL,
    name_hi               VARCHAR(160),
    sort_order            INTEGER       NOT NULL,
    weightage_marks_avg   NUMERIC(6,2)  NOT NULL DEFAULT 0,
    default_learn_minutes INTEGER,
    neet_relevant         BOOLEAN       NOT NULL DEFAULT true,
    created_at            TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at            TIMESTAMPTZ   NOT NULL DEFAULT now(),
    CONSTRAINT syllabus_nodes_code_key           UNIQUE (code),
    CONSTRAINT syllabus_nodes_parent_id_fkey     FOREIGN KEY (parent_id) REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT syllabus_nodes_subject_check      CHECK (subject IN ('physics', 'chemistry', 'botany', 'zoology')),
    CONSTRAINT syllabus_nodes_class_level_check  CHECK (class_level IN (11, 12)),
    CONSTRAINT syllabus_nodes_kind_check         CHECK (kind IN ('subject', 'unit', 'chapter', 'topic')),
    CONSTRAINT syllabus_nodes_subject_root_check CHECK (kind <> 'subject' OR parent_id IS NULL)
);

CREATE INDEX syllabus_nodes_parent_id_idx    ON syllabus_nodes (parent_id);
CREATE INDEX syllabus_nodes_subject_kind_idx ON syllabus_nodes (subject, kind);

-- Edge semantics: from_node_id must be learned before to_node_id (from is a prerequisite of to).
-- Acyclicity is a D13 loader check plus a repository test; it is not expressible as a constraint.
CREATE TABLE syllabus_prerequisites (
    from_node_id UUID        NOT NULL,
    to_node_id   UUID        NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT syllabus_prerequisites_pkey              PRIMARY KEY (from_node_id, to_node_id),
    CONSTRAINT syllabus_prerequisites_from_node_id_fkey FOREIGN KEY (from_node_id) REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT syllabus_prerequisites_to_node_id_fkey   FOREIGN KEY (to_node_id)   REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT syllabus_prerequisites_from_to_check     CHECK (from_node_id <> to_node_id)
);

-- The primary key already serves lookups by from_node_id; this covers "what must I learn first".
CREATE INDEX syllabus_prerequisites_to_node_id_idx ON syllabus_prerequisites (to_node_id);

-- Config table, loaded from pipeline/inputs/archetypes.yaml (SPEC §9.5).
CREATE TABLE archetype_tracks (
    id             UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    code           VARCHAR(16)   NOT NULL,
    name_en        VARCHAR(160)  NOT NULL,
    name_hi        VARCHAR(160),
    weeks          SMALLINT,
    description_md TEXT,
    created_at     TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ   NOT NULL DEFAULT now(),
    CONSTRAINT archetype_tracks_code_key   UNIQUE (code),
    CONSTRAINT archetype_tracks_code_check CHECK (code IN ('fresher_2yr', 'fresher_1yr', 'dropper', 'repeater'))
);

CREATE TABLE archetype_track_steps (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    track_id    UUID        NOT NULL,
    node_id     UUID        NOT NULL,
    sequence    INTEGER     NOT NULL,
    phase       VARCHAR(8)  NOT NULL,
    target_week SMALLINT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT archetype_track_steps_track_id_sequence_key UNIQUE (track_id, sequence),
    CONSTRAINT archetype_track_steps_track_id_fkey         FOREIGN KEY (track_id) REFERENCES archetype_tracks(id) ON DELETE RESTRICT,
    CONSTRAINT archetype_track_steps_node_id_fkey          FOREIGN KEY (node_id)  REFERENCES syllabus_nodes(id)   ON DELETE RESTRICT,
    CONSTRAINT archetype_track_steps_phase_check           CHECK (phase IN ('learn', 'mock', 'revision'))
);

CREATE INDEX archetype_track_steps_node_id_idx ON archetype_track_steps (node_id);

-- Config table. quota_scope is 'AIQ' or a two-letter state code (free text by design, §2.3).
CREATE TABLE cutoffs (
    id               UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    year             SMALLINT     NOT NULL,
    category         VARCHAR(8)   NOT NULL,
    quota_scope      VARCHAR(8)   NOT NULL,
    seat_type        VARCHAR(16)  NOT NULL,
    qualifying_marks SMALLINT     NOT NULL,
    source           VARCHAR(120),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT cutoffs_year_category_quota_scope_seat_type_key UNIQUE (year, category, quota_scope, seat_type),
    CONSTRAINT cutoffs_category_check  CHECK (category IN ('general', 'obc', 'sc', 'st', 'ews')),
    CONSTRAINT cutoffs_seat_type_check CHECK (seat_type IN ('govt_mbbs', 'private_mbbs', 'bds', 'qualifying'))
);
