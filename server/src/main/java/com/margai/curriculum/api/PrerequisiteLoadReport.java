package com.margai.curriculum.api;

import java.util.List;

/**
 * What {@code taxonomy prerequisites} did (TECH_PLAN §6.3): the edges inserted and already
 * present, the whole graph's size after the run, and the edges in {@code syllabus_prerequisites}
 * that the file no longer names ({@code "FROM -> TO"}, sorted, left in place). A report exists
 * only for an acyclic graph; a cycle throws instead.
 */
public record PrerequisiteLoadReport(
        int inserted,
        int unchanged,
        int edgesInDatabase,
        int nodesWithEdges,
        List<String> orphanEdges) {
}
