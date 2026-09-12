package com.margai.curriculum.api;

import java.util.List;
import java.util.Map;

/**
 * What {@code cutoffs load} did (TECH_PLAN §6.3): upsert counts, the file's rows per year, and
 * the natural keys present in {@code cutoffs} that the file no longer names
 * ({@code "year category quota_scope seat_type"}, sorted, left in place).
 */
public record CutoffLoadReport(
        int inserted,
        int updated,
        int unchanged,
        Map<Short, Integer> rowsPerYear,
        List<String> orphans) {
}
