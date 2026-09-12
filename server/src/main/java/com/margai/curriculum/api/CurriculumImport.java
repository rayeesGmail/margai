package com.margai.curriculum.api;

import java.util.List;

/**
 * The curriculum module's door for the pipeline (TECH_PLAN §1.3, §6.3): the D13 loads. Every
 * method is one transaction that upserts by the natural key, reports what it found in the
 * database that the file no longer names (orphans, listed for the founder, kept in place —
 * DECISIONS 2026-09-12 D13; the one exception is a track's own steps, which follow the file), and
 * throws {@link CurriculumImportException} — rolling the whole run back — when the file
 * contradicts itself or the taxonomy it is loading into.
 */
public interface CurriculumImport {

    /**
     * {@code taxonomy load}: parents before children, upsert on {@code code}, every column of the
     * file set; {@code weightage_marks_avg} is D22's and untouched. Refused: a parent the file does
     * not name or of the wrong kind or subject, a topic whose class level differs from its chapter's,
     * two siblings sharing a {@code sort_order}.
     */
    TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows);

    /**
     * {@code taxonomy prerequisites}: both endpoints must be chapters already loaded; upsert on the
     * pair; then Kahn's algorithm over every edge in the database — a non-empty remainder fails the
     * run (PLAN D13 ✅ "graph has no cycles").
     */
    PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows);

    /**
     * {@code backbone load}: tracks upserted on {@code code}, steps on (track, sequence) with a
     * {@code learn} step naming a chapter, {@code revision} a unit, {@code mock} a subject; sequences
     * the file no longer has are removed, so a shortened track leaves no ghost steps. Refused: a
     * chapter learned twice by one track, and a chapter learned before a prerequisite the same track
     * also learns (the edges already in the database, at sequence granularity).
     */
    BackboneLoadReport loadBackbone(List<ArchetypeTrackRow> tracks);

    /** {@code cutoffs load}: upsert on (year, category, quota_scope, seat_type). */
    CutoffLoadReport loadCutoffs(List<CutoffRow> rows);
}
