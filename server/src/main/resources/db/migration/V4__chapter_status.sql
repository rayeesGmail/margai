-- V4__chapter_status.sql — D4 (TECH_PLAN §2.4)
-- Purpose: per-student, per-chapter coverage state — the SPEC §5.1 Q4 three states plus long-press "feels weak", later refined by behaviour (source tells which).
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS chapter_status;
-- END ROLLBACK

-- ability_estimate and ability_confidence are left NULL by self-report; the D33+ signals fill
-- them and stamp last_signal_at.
CREATE TABLE chapter_status (
    id                 UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id            UUID         NOT NULL,
    node_id            UUID         NOT NULL,
    status             VARCHAR(16)  NOT NULL DEFAULT 'untouched',
    feels_weak         BOOLEAN      NOT NULL DEFAULT false,
    source             VARCHAR(16)  NOT NULL,
    ability_estimate   NUMERIC(3,2),
    ability_confidence NUMERIC(3,2),
    last_signal_at     TIMESTAMPTZ,
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT chapter_status_user_id_node_id_key UNIQUE (user_id, node_id),
    CONSTRAINT chapter_status_user_id_fkey        FOREIGN KEY (user_id) REFERENCES users(id)          ON DELETE RESTRICT,
    CONSTRAINT chapter_status_node_id_fkey        FOREIGN KEY (node_id) REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT chapter_status_status_check        CHECK (status IN ('untouched', 'ongoing', 'covered')),
    CONSTRAINT chapter_status_source_check        CHECK (source IN ('self_report', 'inferred', 'timetable', 'diagnostic'))
);

-- The unique key already serves lookups by user_id; this covers per-chapter aggregates.
CREATE INDEX chapter_status_node_id_idx ON chapter_status (node_id);
