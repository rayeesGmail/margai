package com.margai.curriculum.api;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * What {@code ncert load} did (TECH_PLAN §6.3): the upsert counts on the paragraph address, the
 * paragraphs per chapter the founder scans, and the addresses already in the database that this
 * edition's JSONL no longer carried — deleted and named (DECISIONS 2026-09-14; through D14 they
 * were kept, as every other loader keeps its orphans). A re-extraction that cuts paragraphs
 * differently is the case that produces them, and since v3 the loader numbers them, so a
 * one-page redo shifts every address after it.
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
