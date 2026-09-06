package com.margai.common.api;

/**
 * NEET reservation category (SPEC §5.1 Q6, optional for the student). Stored as
 * {@code student_profiles.category} (account) and {@code cutoffs.category} (curriculum), which
 * is why it lives in common. Constants are the lowercase database and wire codes.
 */
public enum Category {
    general, obc, sc, st, ews
}
