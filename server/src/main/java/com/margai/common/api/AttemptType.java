package com.margai.common.api;

/**
 * SPEC §5.1 Q1 "Which one is you?" — sets the plan archetype. Stored as
 * {@code student_profiles.attempt_type} (account) and as {@code archetype_tracks.code}
 * (curriculum), which is why it lives in common (TECH_PLAN §2.2, §2.3).
 * Constants are the lowercase database and wire codes (DECISIONS, D4).
 */
public enum AttemptType {
    /** First attempt, Class 11 (two-year track). */
    fresher_2yr,
    /** First attempt, Class 12 (one-year track). */
    fresher_1yr,
    /** First repeat. */
    dropper,
    /** Second or later repeat. */
    repeater
}
