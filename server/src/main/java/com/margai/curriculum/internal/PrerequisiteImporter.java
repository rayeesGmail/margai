package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * {@code taxonomy prerequisites} (TECH_PLAN §6.3): both endpoints must be chapters already in
 * {@code syllabus_nodes}; edges upserted on the pair; then Kahn's algorithm over every edge in the
 * database, existing and new — a non-empty remainder is a cycle and fails the run (PLAN D13 ✅).
 * Runs inside {@link CurriculumImportService}'s transaction, so the failed run writes nothing.
 */
@Component
class PrerequisiteImporter {

    private final SyllabusNodeRepository nodes;
    private final SyllabusPrerequisiteRepository prerequisites;

    PrerequisiteImporter(SyllabusNodeRepository nodes, SyllabusPrerequisiteRepository prerequisites) {
        this.nodes = nodes;
        this.prerequisites = prerequisites;
    }

    PrerequisiteLoadReport load(List<PrerequisiteRow> rows) {
        Set<String> codes = rows.stream()
                .flatMap(row -> Stream.of(row.fromCode(), row.toCode()))
                .collect(Collectors.toCollection(TreeSet::new));
        Map<String, SyllabusNode> byCode = NodeLookup.resolve(nodes, codes, "prerequisite endpoints");
        List<String> notChapters = codes.stream().filter(code -> byCode.get(code).getKind() != NodeKind.chapter).toList();
        if (!notChapters.isEmpty()) {
            throw new CurriculumImportException("prerequisites are chapter-level (TECH_PLAN §2.3); not chapters: " + notChapters);
        }

        Map<UUID, String> codeById = new HashMap<>();
        nodes.findAll().forEach(node -> codeById.put(node.getId(), node.getCode()));
        Set<SyllabusPrerequisiteId> existing = prerequisites.findAll().stream()
                .map(SyllabusPrerequisite::getId)
                .collect(Collectors.toCollection(HashSet::new));

        Set<SyllabusPrerequisiteId> inFile = new HashSet<>();
        int inserted = 0;
        int unchanged = 0;
        for (PrerequisiteRow row : rows) {
            SyllabusPrerequisiteId id = new SyllabusPrerequisiteId(
                    byCode.get(row.fromCode()).getId(), byCode.get(row.toCode()).getId());
            if (!inFile.add(id)) {
                throw new CurriculumImportException("edge " + row.fromCode() + " -> " + row.toCode() + " appears twice in the file");
            }
            if (existing.contains(id)) {
                unchanged++;
            } else {
                prerequisites.save(new SyllabusPrerequisite(id.fromNodeId(), id.toNodeId()));
                inserted++;
            }
        }
        prerequisites.flush();

        Set<SyllabusPrerequisiteId> all = new HashSet<>(existing);
        all.addAll(inFile);
        List<UUID> cycle = remainderAfterKahn(all);
        if (!cycle.isEmpty()) {
            throw new CurriculumImportException("the prerequisite graph has a cycle among "
                    + cycle.stream().map(codeById::get).sorted().toList());
        }
        List<String> orphanEdges = existing.stream()
                .filter(id -> !inFile.contains(id))
                .map(id -> codeById.get(id.fromNodeId()) + " -> " + codeById.get(id.toNodeId()))
                .sorted()
                .toList();
        Set<UUID> withEdges = all.stream()
                .flatMap(id -> Stream.of(id.fromNodeId(), id.toNodeId()))
                .collect(Collectors.toSet());
        return new PrerequisiteLoadReport(inserted, unchanged, all.size(), withEdges.size(), orphanEdges);
    }

    /**
     * Kahn's algorithm (TECH_PLAN §6.3 "cycle check"): peel every node without incoming edges and
     * return what is left with an incoming edge — empty for an acyclic graph, the cycle's members
     * (and whatever depends on them) otherwise.
     */
    static List<UUID> remainderAfterKahn(Set<SyllabusPrerequisiteId> edges) {
        Map<UUID, List<UUID>> outgoing = new HashMap<>();
        Map<UUID, Integer> indegree = new HashMap<>();
        for (SyllabusPrerequisiteId edge : edges) {
            outgoing.computeIfAbsent(edge.fromNodeId(), from -> new ArrayList<>()).add(edge.toNodeId());
            indegree.merge(edge.toNodeId(), 1, Integer::sum);
            indegree.putIfAbsent(edge.fromNodeId(), 0);
        }
        Deque<UUID> ready = new ArrayDeque<>();
        indegree.forEach((node, degree) -> {
            if (degree == 0) {
                ready.push(node);
            }
        });
        while (!ready.isEmpty()) {
            UUID node = ready.pop();
            for (UUID next : outgoing.getOrDefault(node, List.of())) {
                if (indegree.merge(next, -1, Integer::sum) == 0) {
                    ready.push(next);
                }
            }
        }
        return indegree.entrySet().stream().filter(entry -> entry.getValue() > 0).map(Map.Entry::getKey).toList();
    }
}
