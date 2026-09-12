package com.margai.curriculum.api;

import java.util.List;

/**
 * What {@code ncert register} did (TECH_PLAN §6.3): the upsert counts on {@code ncert_books.code}
 * and the books already in the database that {@code books.yaml} no longer names — reported and
 * kept, as every D13 loader keeps its orphans (DECISIONS 2026-09-12 D13), because a book's
 * paragraphs outlive an edit to the input file.
 */
public record NcertRegisterReport(int inserted, int updated, int unchanged, List<String> orphans) {

    public NcertRegisterReport {
        orphans = orphans == null ? List.of() : List.copyOf(orphans);
    }

    public int total() {
        return inserted + updated + unchanged;
    }
}
