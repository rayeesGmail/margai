package com.margai.curriculum.api;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * What {@code ncert load} did (TECH_PLAN §6.3): the upsert counts on the paragraph address, the
 * paragraphs per chapter the founder scans, and the addresses already in the database that this
 * edition's JSONL no longer carries — reported and kept, as every loader keeps its orphans
 * (DECISIONS 2026-09-12 D13). A re-extraction that splits paragraphs differently is the case
 * that produces them.
 */
public record NcertLoadReport(
        int inserted,
        int updated,
        int unchanged,
        Map<Short, Integer> perChapter,
        List<String> orphans) {

    public NcertLoadReport {
        perChapter = perChapter == null ? Map.of() : new TreeMap<>(perChapter);
        orphans = orphans == null ? List.of() : List.copyOf(orphans);
    }

    public int total() {
        return inserted + updated + unchanged;
    }
}
