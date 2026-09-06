-- R__test_taxonomy.sql — D4 (TECH_PLAN §2.9)
-- Purpose: the D4 "test taxonomy" — a two-subject, six-chapter syllabus tree with prerequisite edges, one archetype track and three cutoff rows, for local development and tests.
--
-- Loaded ONLY by the local and test profiles (a second spring.flyway.locations entry pointing at
-- db/seed). Production never sees this file; the real taxonomy arrives through the D13 loader.
-- All values are synthetic: names follow NCERT chapter titles for realism, but learn minutes,
-- weeks, step order and cutoff marks are invented.
--
-- Repeatable migration: Flyway re-runs it whenever its checksum changes, so every statement is
-- idempotent (INSERT ... ON CONFLICT on the natural key). Rows carry fixed UUIDs of the form
-- 00000000-0000-4000-8000-0000000000NN so tests can reference them directly:
--   01–02 subjects, 11–12 units, 21–26 chapters, 31–32 topics, 41 track, 51–57 steps, 61–63 cutoffs.
--
-- The rows are also removed by the V3 table drops; the block below undoes only the seed.
-- ROLLBACK:
-- DELETE FROM cutoffs WHERE id IN ('00000000-0000-4000-8000-000000000061', '00000000-0000-4000-8000-000000000062', '00000000-0000-4000-8000-000000000063');
-- DELETE FROM archetype_track_steps WHERE id IN ('00000000-0000-4000-8000-000000000051', '00000000-0000-4000-8000-000000000052', '00000000-0000-4000-8000-000000000053', '00000000-0000-4000-8000-000000000054', '00000000-0000-4000-8000-000000000055', '00000000-0000-4000-8000-000000000056', '00000000-0000-4000-8000-000000000057');
-- DELETE FROM archetype_tracks WHERE id = '00000000-0000-4000-8000-000000000041';
-- DELETE FROM syllabus_prerequisites WHERE (from_node_id, to_node_id) IN (('00000000-0000-4000-8000-000000000021', '00000000-0000-4000-8000-000000000022'), ('00000000-0000-4000-8000-000000000022', '00000000-0000-4000-8000-000000000023'), ('00000000-0000-4000-8000-000000000024', '00000000-0000-4000-8000-000000000025'), ('00000000-0000-4000-8000-000000000025', '00000000-0000-4000-8000-000000000026'));
-- DELETE FROM syllabus_nodes WHERE id IN ('00000000-0000-4000-8000-000000000031', '00000000-0000-4000-8000-000000000032');
-- DELETE FROM syllabus_nodes WHERE id IN ('00000000-0000-4000-8000-000000000021', '00000000-0000-4000-8000-000000000022', '00000000-0000-4000-8000-000000000023', '00000000-0000-4000-8000-000000000024', '00000000-0000-4000-8000-000000000025', '00000000-0000-4000-8000-000000000026');
-- DELETE FROM syllabus_nodes WHERE id IN ('00000000-0000-4000-8000-000000000011', '00000000-0000-4000-8000-000000000012');
-- DELETE FROM syllabus_nodes WHERE id IN ('00000000-0000-4000-8000-000000000001', '00000000-0000-4000-8000-000000000002');
-- END ROLLBACK

-- ---------------------------------------------------------------------------------------------
-- Syllabus tree. Levels are inserted parent-first so the self-referencing foreign key holds.
-- ---------------------------------------------------------------------------------------------

-- Subject roots (kind = subject, class_level NULL, no parent).
INSERT INTO syllabus_nodes (id, code, subject, class_level, parent_id, kind, name_en, name_hi, sort_order, default_learn_minutes) VALUES
    ('00000000-0000-4000-8000-000000000001', 'PHY', 'physics',   NULL, NULL, 'subject', 'Physics',   'भौतिकी',        1, NULL),
    ('00000000-0000-4000-8000-000000000002', 'CHE', 'chemistry', NULL, NULL, 'subject', 'Chemistry', 'रसायन विज्ञान', 2, NULL)
ON CONFLICT (code) DO UPDATE SET
    subject               = EXCLUDED.subject,
    class_level           = EXCLUDED.class_level,
    parent_id             = EXCLUDED.parent_id,
    kind                  = EXCLUDED.kind,
    name_en               = EXCLUDED.name_en,
    name_hi               = EXCLUDED.name_hi,
    sort_order            = EXCLUDED.sort_order,
    default_learn_minutes = EXCLUDED.default_learn_minutes,
    updated_at            = now();

-- Units (kind = unit, class 11).
INSERT INTO syllabus_nodes (id, code, subject, class_level, parent_id, kind, name_en, name_hi, sort_order, default_learn_minutes) VALUES
    ('00000000-0000-4000-8000-000000000011', 'PHY.11.MECH', 'physics',   11, '00000000-0000-4000-8000-000000000001', 'unit', 'Mechanics',          'यांत्रिकी',    1, NULL),
    ('00000000-0000-4000-8000-000000000012', 'CHE.11.PHYS', 'chemistry', 11, '00000000-0000-4000-8000-000000000002', 'unit', 'Physical Chemistry', 'भौतिक रसायन', 1, NULL)
ON CONFLICT (code) DO UPDATE SET
    subject               = EXCLUDED.subject,
    class_level           = EXCLUDED.class_level,
    parent_id             = EXCLUDED.parent_id,
    kind                  = EXCLUDED.kind,
    name_en               = EXCLUDED.name_en,
    name_hi               = EXCLUDED.name_hi,
    sort_order            = EXCLUDED.sort_order,
    default_learn_minutes = EXCLUDED.default_learn_minutes,
    updated_at            = now();

-- Chapters (kind = chapter, class 11), sort_order 1..3 within each unit.
INSERT INTO syllabus_nodes (id, code, subject, class_level, parent_id, kind, name_en, name_hi, sort_order, default_learn_minutes) VALUES
    ('00000000-0000-4000-8000-000000000021', 'PHY.11.KIN',    'physics',   11, '00000000-0000-4000-8000-000000000011', 'chapter', 'Motion in a Straight Line',                 'सरल रेखा में गति',                        1, 240),
    ('00000000-0000-4000-8000-000000000022', 'PHY.11.NLM',    'physics',   11, '00000000-0000-4000-8000-000000000011', 'chapter', 'Laws of Motion',                            'गति के नियम',                             2, 300),
    ('00000000-0000-4000-8000-000000000023', 'PHY.11.ROT',    'physics',   11, '00000000-0000-4000-8000-000000000011', 'chapter', 'System of Particles and Rotational Motion', 'कणों के निकाय तथा घूर्णी गति',             3, 420),
    ('00000000-0000-4000-8000-000000000024', 'CHE.11.MOLE',   'chemistry', 11, '00000000-0000-4000-8000-000000000012', 'chapter', 'Some Basic Concepts of Chemistry',          'रसायन विज्ञान की कुछ मूल अवधारणाएँ',     1, 240),
    ('00000000-0000-4000-8000-000000000025', 'CHE.11.ATOM',   'chemistry', 11, '00000000-0000-4000-8000-000000000012', 'chapter', 'Structure of Atom',                         'परमाणु की संरचना',                       2, 360),
    ('00000000-0000-4000-8000-000000000026', 'CHE.11.THERMO', 'chemistry', 11, '00000000-0000-4000-8000-000000000012', 'chapter', 'Thermodynamics',                            'ऊष्मागतिकी',                             3, 360)
ON CONFLICT (code) DO UPDATE SET
    subject               = EXCLUDED.subject,
    class_level           = EXCLUDED.class_level,
    parent_id             = EXCLUDED.parent_id,
    kind                  = EXCLUDED.kind,
    name_en               = EXCLUDED.name_en,
    name_hi               = EXCLUDED.name_hi,
    sort_order            = EXCLUDED.sort_order,
    default_learn_minutes = EXCLUDED.default_learn_minutes,
    updated_at            = now();

-- Topics (kind = topic, class 11) under PHY.11.ROT.
INSERT INTO syllabus_nodes (id, code, subject, class_level, parent_id, kind, name_en, name_hi, sort_order, default_learn_minutes) VALUES
    ('00000000-0000-4000-8000-000000000031', 'PHY.11.ROT.TORQUE', 'physics', 11, '00000000-0000-4000-8000-000000000023', 'topic', 'Torque and Angular Momentum', 'बल आघूर्ण और कोणीय संवेग', 1, 120),
    ('00000000-0000-4000-8000-000000000032', 'PHY.11.ROT.MOI',    'physics', 11, '00000000-0000-4000-8000-000000000023', 'topic', 'Moment of Inertia',           'जड़त्व आघूर्ण',              2, 150)
ON CONFLICT (code) DO UPDATE SET
    subject               = EXCLUDED.subject,
    class_level           = EXCLUDED.class_level,
    parent_id             = EXCLUDED.parent_id,
    kind                  = EXCLUDED.kind,
    name_en               = EXCLUDED.name_en,
    name_hi               = EXCLUDED.name_hi,
    sort_order            = EXCLUDED.sort_order,
    default_learn_minutes = EXCLUDED.default_learn_minutes,
    updated_at            = now();

-- ---------------------------------------------------------------------------------------------
-- Prerequisite edges: from_node_id must be learned before to_node_id.
-- ---------------------------------------------------------------------------------------------
INSERT INTO syllabus_prerequisites (from_node_id, to_node_id) VALUES
    ('00000000-0000-4000-8000-000000000021', '00000000-0000-4000-8000-000000000022'),  -- KIN  -> NLM
    ('00000000-0000-4000-8000-000000000022', '00000000-0000-4000-8000-000000000023'),  -- NLM  -> ROT
    ('00000000-0000-4000-8000-000000000024', '00000000-0000-4000-8000-000000000025'),  -- MOLE -> ATOM
    ('00000000-0000-4000-8000-000000000025', '00000000-0000-4000-8000-000000000026')   -- ATOM -> THERMO
ON CONFLICT (from_node_id, to_node_id) DO NOTHING;

-- ---------------------------------------------------------------------------------------------
-- One archetype track with seven steps: six learn steps over the chapters, then one mock step
-- on the Physics subject node.
-- ---------------------------------------------------------------------------------------------
INSERT INTO archetype_tracks (id, code, name_en, name_hi, weeks, description_md) VALUES
    ('00000000-0000-4000-8000-000000000041', 'dropper', 'Dropper track (test seed)', 'ड्रॉपर ट्रैक (परीक्षण)', 7, 'Synthetic backbone for local development and tests.')
ON CONFLICT (code) DO UPDATE SET
    name_en        = EXCLUDED.name_en,
    name_hi        = EXCLUDED.name_hi,
    weeks          = EXCLUDED.weeks,
    description_md = EXCLUDED.description_md,
    updated_at     = now();

INSERT INTO archetype_track_steps (id, track_id, node_id, sequence, phase, target_week) VALUES
    ('00000000-0000-4000-8000-000000000051', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000021', 1, 'learn', 1),  -- KIN
    ('00000000-0000-4000-8000-000000000052', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000022', 2, 'learn', 2),  -- NLM
    ('00000000-0000-4000-8000-000000000053', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000023', 3, 'learn', 3),  -- ROT
    ('00000000-0000-4000-8000-000000000054', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000024', 4, 'learn', 4),  -- MOLE
    ('00000000-0000-4000-8000-000000000055', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000025', 5, 'learn', 5),  -- ATOM
    ('00000000-0000-4000-8000-000000000056', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000026', 6, 'learn', 6),  -- THERMO
    ('00000000-0000-4000-8000-000000000057', '00000000-0000-4000-8000-000000000041', '00000000-0000-4000-8000-000000000001', 7, 'mock',  7)   -- PHY (subject)
ON CONFLICT (track_id, sequence) DO UPDATE SET
    node_id     = EXCLUDED.node_id,
    phase       = EXCLUDED.phase,
    target_week = EXCLUDED.target_week,
    updated_at  = now();

-- ---------------------------------------------------------------------------------------------
-- Cutoffs: three synthetic AIQ rows for 2025.
-- ---------------------------------------------------------------------------------------------
INSERT INTO cutoffs (id, year, category, quota_scope, seat_type, qualifying_marks, source) VALUES
    ('00000000-0000-4000-8000-000000000061', 2025, 'general', 'AIQ', 'govt_mbbs',  500, 'test taxonomy seed — synthetic, not real cutoffs'),
    ('00000000-0000-4000-8000-000000000062', 2025, 'obc',     'AIQ', 'govt_mbbs',  480, 'test taxonomy seed — synthetic, not real cutoffs'),
    ('00000000-0000-4000-8000-000000000063', 2025, 'general', 'AIQ', 'qualifying', 100, 'test taxonomy seed — synthetic, not real cutoffs')
ON CONFLICT (year, category, quota_scope, seat_type) DO UPDATE SET
    qualifying_marks = EXCLUDED.qualifying_marks,
    source           = EXCLUDED.source,
    updated_at       = now();
