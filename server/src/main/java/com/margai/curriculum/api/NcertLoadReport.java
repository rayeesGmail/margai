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
 *
 * <p>The last two are about paid work this load destroyed (D15): {@code embeddingsCleared} counts
 * rows whose English text it rewrote, whose vectors described the old words and were dropped, and
 * {@code embeddedOrphans} counts deleted rows that carried one. Both are re-earned by the next
 * {@code ncert embed} for the price of the paragraphs involved — but silently losing embeddings is
 * exactly the kind of thing a report exists to say out loud.
 *
 * <p>{@code cleared} (D16) names the rows this load no longer carried that the other edition still
 * does: they are kept, and only this edition's text and provenance are removed from them — an English
 * row losing its words also loses its vector, counted in {@code embeddingsCleared}.
 */
public record NcertLoadReport(
        int inserted,
        int updated,
        int unchanged,
        Map<Short, Integer> perChapter,
        List<String> orphans,
        int embeddingsCleared,
        int embeddedOrphans,
        List<String> cleared) {

    public NcertLoadReport {
        perChapter = perChapter == null ? Map.of() : new TreeMap<>(perChapter);
        orphans = orphans == null ? List.of() : List.copyOf(orphans);
        cleared = cleared == null ? List.of() : List.copyOf(cleared);
    }

    public int total() {
        return inserted + updated + unchanged;
    }
}
