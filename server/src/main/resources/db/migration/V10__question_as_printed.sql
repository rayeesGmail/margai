-- V10__question_as_printed.sql — D19 (TECH_PLAN §2.3)
-- Purpose: the misprints an official paper makes and the bank keeps as printed, each with its intended
-- reading, so the app can show an "as printed in the original paper" note beside the paper's own words, as
-- the NCERT errata notes do (founder, 2026-10-21; DECISIONS; PLAN D31, D40).
--
-- ROLLBACK:
-- ALTER TABLE questions DROP COLUMN IF EXISTS as_printed;
-- END ROLLBACK

-- as_printed is [{at: 'stem' | 'option:<key>', printed, intended, note}], read whole, never queried by key
-- (§2.1): `printed` is the span the stem or option still prints, `intended` what the paper meant there, and
-- `note` the one line shown to the student. Empty for a question the paper printed right, and for every
-- generated question.
ALTER TABLE questions ADD COLUMN as_printed JSONB NOT NULL DEFAULT '[]';
