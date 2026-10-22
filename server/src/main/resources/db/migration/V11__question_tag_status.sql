-- V11__question_tag_status.sql — D19 (TECH_PLAN §2.3)
-- Purpose: whose tag a question carries, so a doubtful one never counts as a normal tag (the founder's
-- approval of 2026-10-21; DECISIONS): a model tag below the review floor loads as 'review' and is listed for
-- the founder, and a tag he rules loads as 'founder'.
--
-- ROLLBACK:
-- ALTER TABLE questions DROP CONSTRAINT IF EXISTS questions_tag_status_check;
-- ALTER TABLE questions DROP COLUMN IF EXISTS tag_status;
-- END ROLLBACK

-- tag_status is 'model' (the tagger's tag, at or above the floor), 'review' (the tagger's tag below the
-- floor, margai.pipeline.pyq.tag-review-floor) or 'founder' (pipeline/inputs/pyq-tag-rulings.yaml). A column,
-- not a key of `tagging` (§2.1: JSONB is never queried by key), because readers filter on it: a 'review'
-- question counts toward no topic's weightage (D22) and is not picked for topic practice (D31) until the
-- founder rules, while a full past-paper mock still serves it (D35).
ALTER TABLE questions ADD COLUMN tag_status VARCHAR(16) NOT NULL DEFAULT 'model';
ALTER TABLE questions ADD CONSTRAINT questions_tag_status_check CHECK (tag_status IN ('model', 'review', 'founder'));
