package com.margai.curriculum.api;

import java.util.List;
import java.util.Map;

/**
 * What {@code taxonomy load} did (TECH_PLAN §6.3 "nodes per subject/kind, orphans"):
 * {@code counts} is the file's nodes by subject and kind, {@code orphans} the codes present in
 * {@code syllabus_nodes} that the file no longer names, sorted, left in place.
 */
public record TaxonomyLoadReport(
        int inserted,
        int updated,
        int unchanged,
        Map<Subject, Map<NodeKind, Integer>> counts,
        List<String> orphans) {

    public int total() {
        return inserted + updated + unchanged;
    }
}
