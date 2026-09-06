-- V2__identity.sql — D4 (TECH_PLAN §2.2)
-- Purpose: identity and account tables — users (phone-keyed, soft-deletable account) and the 1:1 student_profiles row that holds the onboarding answers (SPEC §5.1 Q1–Q7).
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS student_profiles;
-- DROP TABLE IF EXISTS users;
-- END ROLLBACK

-- phone is E.164. It is NOT NULL while status = 'active' and set to NULL on deletion (§9.6
-- anonymisation), which is why uniqueness is a partial index rather than a column constraint.
CREATE TABLE users (
    id                UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    phone             VARCHAR(16),
    phone_verified_at TIMESTAMPTZ,
    display_name      VARCHAR(80),
    language          VARCHAR(8)   NOT NULL DEFAULT 'en',
    role              VARCHAR(16)  NOT NULL DEFAULT 'student',
    status            VARCHAR(16)  NOT NULL DEFAULT 'active',
    deleted_at        TIMESTAMPTZ,
    purge_after       DATE,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT users_language_check     CHECK (language IN ('en', 'hi', 'hinglish')),
    CONSTRAINT users_role_check         CHECK (role IN ('student', 'admin')),
    CONSTRAINT users_status_check       CHECK (status IN ('active', 'deleted')),
    CONSTRAINT users_phone_status_check CHECK (status = 'deleted' OR phone IS NOT NULL)
);

CREATE UNIQUE INDEX users_phone_key ON users (phone) WHERE phone IS NOT NULL;

-- Columns scorecard, board_marks and exam_date are filled by later days (TECH_PLAN §2.2 †) but
-- declared now so the row shape is stable. scorecard/board_marks hold confirmed fields only,
-- never an image reference (SPEC §5.2).
CREATE TABLE student_profiles (
    id                        UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                   UUID         NOT NULL,
    attempt_type              VARCHAR(16),
    target_year               SMALLINT,
    coaching_mode             VARCHAR(16),
    coaching_provider         VARCHAR(16),
    hours_weekday             NUMERIC(3,1),
    hours_weekend             NUMERIC(3,1),
    goal                      VARCHAR(16),
    state_code                CHAR(2),
    category                  VARCHAR(8),
    dob                       DATE,
    is_minor                  BOOLEAN      NOT NULL DEFAULT false,
    last_neet_year            SMALLINT,
    last_neet_score           SMALLINT,
    last_neet_rank            INTEGER,
    scorecard                 JSONB,
    board_marks               JSONB,
    onboarding_step           VARCHAR(24)  NOT NULL DEFAULT 'intro',
    onboarding_completed_at   TIMESTAMPTZ,
    exam_date                 DATE,
    morning_notification_time TIME         NOT NULL DEFAULT '07:00',
    current_streak            INTEGER      NOT NULL DEFAULT 0,
    longest_streak            INTEGER      NOT NULL DEFAULT 0,
    last_active_ist_date      DATE,
    created_at                TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at                TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT student_profiles_user_id_key            UNIQUE (user_id),
    CONSTRAINT student_profiles_user_id_fkey           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT student_profiles_attempt_type_check     CHECK (attempt_type IN ('fresher_1yr', 'fresher_2yr', 'dropper', 'repeater')),
    CONSTRAINT student_profiles_coaching_mode_check    CHECK (coaching_mode IN ('classroom', 'online', 'self_study', 'mix')),
    CONSTRAINT student_profiles_coaching_provider_check CHECK (coaching_provider IN ('pw', 'aakash', 'allen', 'unacademy', 'other')),
    CONSTRAINT student_profiles_goal_check             CHECK (goal IN ('govt_mbbs', 'private_ok', 'bds_other', 'qualify')),
    CONSTRAINT student_profiles_category_check         CHECK (category IN ('general', 'obc', 'sc', 'st', 'ews'))
);
