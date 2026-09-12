package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link CurriculumImport} over the D4 tables (TECH_PLAN §2.3, §6.3). Each load is one
 * transaction: the file is checked against itself and against the database first, rows are
 * upserted by natural key, and a {@link CurriculumImportException} anywhere rolls everything
 * back. Nothing is deleted — rows the file no longer names are reported as orphans (DECISIONS
 * 2026-09-12 D13).
 */
@Service
@Transactional
class CurriculumImportService implements CurriculumImport {

    /** The kind each kind must hang from (TECH_PLAN §2.3). */
    private static final Map<NodeKind, NodeKind> PARENT_KIND = Map.of(
            NodeKind.unit, NodeKind.subject,
            NodeKind.chapter, NodeKind.unit,
            NodeKind.topic, NodeKind.chapter);

    private final SyllabusNodeRepository nodes;
    private final SyllabusPrerequisiteRepository prerequisites;

    CurriculumImportService(SyllabusNodeRepository nodes, SyllabusPrerequisiteRepository prerequisites) {
        this.nodes = nodes;
        this.prerequisites = prerequisites;
    }

    @Override
    public TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows) {
        Map<String, SyllabusNodeRow> file = new LinkedHashMap<>();
        for (SyllabusNodeRow row : rows) {
            if (file.put(row.code(), row) != null) {
                throw new CurriculumImportException("code '" + row.code() + "' appears twice in the file");
            }
        }
        rows.forEach(row -> checkParent(row, file));

        Map<String, SyllabusNode> existing = new HashMap<>();
        nodes.findAll().forEach(node -> existing.put(node.getCode(), node));
        Map<String, UUID> ids = new HashMap<>();
        existing.forEach((code, node) -> ids.put(code, node.getId()));

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        // NodeKind is declared subject, unit, chapter, topic: sorting by kind puts every parent before its children.
        List<SyllabusNodeRow> parentsFirst = rows.stream()
                .sorted(Comparator.comparingInt(row -> row.kind().ordinal()))
                .toList();
        for (SyllabusNodeRow row : parentsFirst) {
            UUID parentId = row.parentCode() == null ? null : ids.get(row.parentCode());
            SyllabusNode node = existing.get(row.code());
            if (node == null) {
                node = new SyllabusNode(row.code(), row.subject(), row.classLevel(), parentId, row.kind(), row.nameEn(),
                        row.sortOrder());
                node.apply(row, parentId);
                node = nodes.save(node);
                inserted++;
            } else if (node.apply(row, parentId)) {
                updated++;
            } else {
                unchanged++;
            }
            ids.put(row.code(), node.getId());
        }
        nodes.flush();

        List<String> orphans = existing.keySet().stream().filter(code -> !file.containsKey(code)).sorted().toList();
        return new TaxonomyLoadReport(inserted, updated, unchanged, counts(rows), orphans);
    }

    private static void checkParent(SyllabusNodeRow row, Map<String, SyllabusNodeRow> file) {
        if (row.kind() == NodeKind.subject) {
            if (row.parentCode() != null) {
                throw new CurriculumImportException("subject " + row.code() + " must not have a parent");
            }
            return;
        }
        SyllabusNodeRow parent = file.get(row.parentCode());
        if (parent == null) {
            throw new CurriculumImportException("parent '" + row.parentCode() + "' of " + row.code() + " is not in the file");
        }
        NodeKind expected = PARENT_KIND.get(row.kind());
        if (parent.kind() != expected) {
            throw new CurriculumImportException("the parent of a " + row.kind() + " must be a " + expected + "; "
                    + row.code() + " hangs from the " + parent.kind() + " " + parent.code());
        }
        if (parent.subject() != row.subject()) {
            throw new CurriculumImportException(row.code() + " is " + row.subject() + " but its parent "
                    + parent.code() + " is " + parent.subject());
        }
    }

    private static Map<Subject, Map<NodeKind, Integer>> counts(List<SyllabusNodeRow> rows) {
        Map<Subject, Map<NodeKind, Integer>> counts = new EnumMap<>(Subject.class);
        for (SyllabusNodeRow row : rows) {
            counts.computeIfAbsent(row.subject(), subject -> new EnumMap<>(NodeKind.class)).merge(row.kind(), 1, Integer::sum);
        }
        return counts;
    }

    @Override
    public PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows) {
        Set<String> codes = rows.stream()
                .flatMap(row -> Stream.of(row.fromCode(), row.toCode()))
                .collect(Collectors.toCollection(TreeSet::new));
        Map<String, SyllabusNode> byCode = nodes.findByCodeIn(codes).stream()
                .collect(Collectors.toMap(SyllabusNode::getCode, Function.identity()));
        List<String> missing = codes.stream().filter(code -> !byCode.containsKey(code)).toList();
        if (!missing.isEmpty()) {
            throw new CurriculumImportException("prerequisite endpoints not in the taxonomy: " + missing);
        }
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
