package com.margai.curriculum.api;

/**
 * One row of {@code pipeline/inputs/prerequisites.csv} (TECH_PLAN §6.2): {@code fromCode} is
 * learned before {@code toCode} (DECISIONS 2026-09-06 D4). Upserted by
 * {@code taxonomy prerequisites} on the pair (§6.3).
 */
public record PrerequisiteRow(String fromCode, String toCode) {
}
