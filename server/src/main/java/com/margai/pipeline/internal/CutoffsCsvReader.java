package com.margai.pipeline.internal;

import com.margai.common.api.Category;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.SeatType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code cutoffs.csv} (TECH_PLAN §6.2) into {@link CutoffRow}s: the natural key
 * (year, category, quota_scope, seat_type) unique within the file, enums from the V3 CHECKs,
 * {@code quota_scope} at most 8 characters, {@code source} at most 120 (TECH_PLAN §2.3).
 */
final class CutoffsCsvReader {

    static final String[] HEADER = {"year", "category", "quota_scope", "seat_type", "qualifying_marks", "source"};
    static final int QUOTA_SCOPE_MAX_LENGTH = 8;
    static final int SOURCE_MAX_LENGTH = 120;
    /**
     * Sanity bounds that catch a typo, not exam parameters: the exam's total marks and first year
     * belong under {@code margai.exam.*} (TECH_PLAN §11.5) the day a feature computes with them (D22).
     */
    static final int FIRST_YEAR = 2000;
    static final int LAST_YEAR = 2100;
    static final int MAX_MARKS = 1000;

    private CutoffsCsvReader() {
    }

    static List<CutoffRow> read(Path file) {
        List<CutoffRow> rows = new ArrayList<>();
        Set<String> keys = new HashSet<>();
        for (CsvInput.Row row : CsvInput.read(file, HEADER)) {
            short year = row.requiredShort("year", FIRST_YEAR, LAST_YEAR);
            Category category = row.requiredEnum("category", Category.class);
            String quotaScope = row.required("quota_scope", QUOTA_SCOPE_MAX_LENGTH);
            SeatType seatType = row.requiredEnum("seat_type", SeatType.class);
            if (!keys.add(year + "/" + category + "/" + quotaScope + "/" + seatType)) {
                throw row.error("the key " + year + " " + category + " " + quotaScope + " " + seatType + " appears twice");
            }
            rows.add(new CutoffRow(year, category, quotaScope, seatType,
                    row.requiredShort("qualifying_marks", 0, MAX_MARKS),
                    row.optional("source", SOURCE_MAX_LENGTH)));
        }
        return rows;
    }
}
