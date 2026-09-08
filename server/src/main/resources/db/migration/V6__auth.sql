-- V6__auth.sql — D7 (TECH_PLAN §2.2)
-- Purpose: login by a verified identifier that is either a phone (E.164) or an email (founder ruling at the D7 plan review, 2026-09-08, amending §2.2 while the SMS DLT template F1 is blocked) — users gains email, and the auth module's otp_challenges (channel + destination) and refresh_tokens land (§1.3).
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS refresh_tokens;
-- DROP TABLE IF EXISTS otp_challenges;
-- ALTER TABLE users DROP CONSTRAINT IF EXISTS users_identifier_status_check;
-- ALTER TABLE users ADD CONSTRAINT users_phone_status_check CHECK (status = 'deleted' OR phone IS NOT NULL);
-- DROP INDEX IF EXISTS users_email_key;
-- ALTER TABLE users DROP COLUMN IF EXISTS email;
-- END ROLLBACK

-- Emails are stored lowercased, so the partial unique index (which mirrors users_phone_key) is
-- the whole uniqueness rule. An active account needs at least one verified identifier, phone or
-- email; both are nulled on deletion (§2.10 anonymisation), which is why the check is relaxed for
-- status = 'deleted' exactly as the V2 phone-only check was.
ALTER TABLE users ADD COLUMN email VARCHAR(254);

CREATE UNIQUE INDEX users_email_key ON users (email) WHERE email IS NOT NULL;

ALTER TABLE users DROP CONSTRAINT users_phone_status_check;
ALTER TABLE users ADD CONSTRAINT users_identifier_status_check CHECK (status = 'deleted' OR phone IS NOT NULL OR email IS NOT NULL);

-- One row per OTP sent. destination is the E.164 phone (channel = 'sms') or the lowercased email
-- (channel = 'email'). code_hash is the SHA-256 hex of the code with the pepper (§3.2); the code
-- itself is never stored. No foreign key to users: a login challenge precedes the account.
-- Rows live 24 h and go with the nightly purge (§2.10).
CREATE TABLE otp_challenges (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    channel     VARCHAR(8)   NOT NULL,
    destination VARCHAR(254) NOT NULL,
    purpose     VARCHAR(16)  NOT NULL,
    code_hash   CHAR(64)     NOT NULL,
    attempts    SMALLINT     NOT NULL DEFAULT 0,
    expires_at  TIMESTAMPTZ  NOT NULL,
    verified_at TIMESTAMPTZ,
    request_ip  INET,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT otp_challenges_channel_check CHECK (channel IN ('sms', 'email')),
    CONSTRAINT otp_challenges_purpose_check CHECK (purpose IN ('login', 'parent_consent'))
);

-- The hourly per-destination cap (§3.4) and the resend cooldown (§3.2) query it.
CREATE INDEX otp_challenges_destination_created_at_idx ON otp_challenges (destination, created_at);

-- token_hash is the SHA-256 hex of the opaque 256-bit refresh token; the token itself is never
-- stored (§3.2). family_id is one family per login/device: each refresh rotates the token, keeps
-- the family and links the successor through replaced_by_id; reuse of a rotated token revokes the
-- whole family (§3.2). Logout revokes the family; account deletion revokes every family (§2.10).
CREATE TABLE refresh_tokens (
    id             UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID         NOT NULL,
    token_hash     CHAR(64)     NOT NULL,
    family_id      UUID         NOT NULL,
    expires_at     TIMESTAMPTZ  NOT NULL,
    revoked_at     TIMESTAMPTZ,
    replaced_by_id UUID,
    device_label   VARCHAR(80),
    last_used_at   TIMESTAMPTZ,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT refresh_tokens_token_hash_key      UNIQUE (token_hash),
    CONSTRAINT refresh_tokens_user_id_fkey        FOREIGN KEY (user_id)        REFERENCES users(id)          ON DELETE RESTRICT,
    CONSTRAINT refresh_tokens_replaced_by_id_fkey FOREIGN KEY (replaced_by_id) REFERENCES refresh_tokens(id) ON DELETE RESTRICT
);

-- Per-user listing and revocation on deletion; per-family rotation, reuse detection and logout.
CREATE INDEX refresh_tokens_user_id_idx   ON refresh_tokens (user_id);
CREATE INDEX refresh_tokens_family_id_idx ON refresh_tokens (family_id);
