package com.margai.pipeline.internal;

import com.margai.curriculum.api.PrerequisiteRow;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code prerequisites.csv} (TECH_PLAN §6.2) into {@link PrerequisiteRow}s: two node codes per
 * row, no self-loop, no repeated edge. Whether the codes exist and whether the graph is acyclic
 * is the loader's business, against the database (§6.3).
 */
final class PrerequisitesCsvReader {

    static final String[] HEADER = {"from_code", "to_code"};

    private PrerequisitesCsvReader() {
    }

    static List<PrerequisiteRow> read(Path file) {
        List<PrerequisiteRow> rows = new ArrayList<>();
        Set<String> edges = new HashSet<>();
        for (CsvInput.Row row : CsvInput.read(file, HEADER)) {
            String from = row.code("from_code");
            String to = row.code("to_code");
            if (from.equals(to)) {
                throw row.error("a node cannot be its own prerequisite: " + from);
            }
            if (!edges.add(from + "->" + to)) {
                throw row.error("edge " + from + " -> " + to + " appears twice");
            }
            rows.add(new PrerequisiteRow(from, to));
        }
        return rows;
    }
}
