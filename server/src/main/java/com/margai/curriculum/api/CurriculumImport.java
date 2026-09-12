package com.margai.curriculum.api;

import java.util.List;

/**
 * The curriculum module's door for the pipeline (TECH_PLAN §1.3, §6.3): the D13 loads. Every
 * method is one transaction that upserts by the natural key, never deletes, reports what it found
 * in the database that the file no longer names (orphans, listed for the founder, kept in place —
 * DECISIONS 2026-09-12 D13), and throws {@link CurriculumImportException} — rolling the whole run
 * back — when the file contradicts the taxonomy it is loading into.
 */
public interface CurriculumImport {

    /**
     * {@code taxonomy load}: parents before children, upsert on {@code code}, every column of the
     * file set; {@code weightage_marks_avg} is D22's and untouched.
     */
    TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows);

    /**
     * {@code taxonomy prerequisites}: both endpoints must be chapters already loaded; upsert on the
     * pair; then Kahn's algorithm over every edge in the database — a non-empty remainder fails the
     * run (PLAN D13 ✅ "graph has no cycles").
     */
    PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows);
}
