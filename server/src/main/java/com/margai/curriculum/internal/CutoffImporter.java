package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 * {@code cutoffs load} (TECH_PLAN §6.3): upsert on (year, category, quota_scope, seat_type); rows
 * the file no longer names are reported as orphans and kept. Runs inside
 * {@link CurriculumImportService}'s transaction.
 */
@Component
class CutoffImporter {

    private final CutoffRepository cutoffs;

    CutoffImporter(CutoffRepository cutoffs) {
        this.cutoffs = cutoffs;
    }

    CutoffLoadReport load(List<CutoffRow> rows) {
        Map<String, Cutoff> existing = new HashMap<>();
        cutoffs.findAll().forEach(cutoff -> existing.put(cutoff.naturalKey(), cutoff));

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        Set<String> inFile = new HashSet<>();
        Map<Short, Integer> rowsPerYear = new TreeMap<>();
        for (CutoffRow row : rows) {
            String key = row.year() + " " + row.category() + " " + row.quotaScope() + " " + row.seatType();
            if (!inFile.add(key)) {
                throw new CurriculumImportException("the key " + key + " appears twice in the file");
            }
            rowsPerYear.merge(row.year(), 1, Integer::sum);
            Cutoff cutoff = existing.get(key);
            if (cutoff == null) {
                cutoffs.save(new Cutoff(row.year(), row.category(), row.quotaScope(), row.seatType(),
                        row.qualifyingMarks(), row.source()));
                inserted++;
            } else if (cutoff.apply(row.qualifyingMarks(), row.source())) {
                updated++;
            } else {
                unchanged++;
            }
        }
        cutoffs.flush();

        List<String> orphans = existing.keySet().stream().filter(key -> !inFile.contains(key)).sorted().toList();
        return new CutoffLoadReport(inserted, updated, unchanged, rowsPerYear, orphans);
    }
}
