package com.margai.account.api;

/**
 * SPEC §5.1 Q3 "How are you preparing?" — decides learn-block style and batch-sync features.
 * Constants are the lowercase database and wire codes.
 */
public enum CoachingMode {
    classroom, online, self_study, mix
}
