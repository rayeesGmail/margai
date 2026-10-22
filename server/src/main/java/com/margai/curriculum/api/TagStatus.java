package com.margai.curriculum.api;

/**
 * Whose tag a question carries ({@code questions.tag_status}, migration V11; the founder's approval of
 * 2026-10-21): {@code model} for the tagger's tag at or above the review floor, {@code review} for one below it
 * — it counts toward no topic's weightage and is not picked for topic practice until the founder rules, while a
 * full past-paper mock still serves it — and {@code founder} for a tag he ruled.
 */
public enum TagStatus {
    model,
    review,
    founder
}
