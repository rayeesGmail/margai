package com.margai.curriculum.api;

import com.margai.common.api.Category;

/**
 * One row of {@code pipeline/inputs/cutoffs.csv} (TECH_PLAN §2.3, §6.2). Upserted by
 * {@code cutoffs load} on (year, category, quotaScope, seatType) (§6.3). {@code source} names the
 * notice the number was taken from and may be null.
 */
public record CutoffRow(
        short year,
        Category category,
        String quotaScope,
        SeatType seatType,
        short qualifyingMarks,
        String source) {
}
