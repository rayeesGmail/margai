package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * {@code taxonomy load} (TECH_PLAN §6.3): the file checked against itself — codes unique, every
 * parent named in the file with the kind and subject the tree demands, a topic's class level its
 * chapter's, sort orders unique among siblings — then upserted on {@code code}, parents first, with
 * every column the file carries; {@code weightage_marks_avg} is D22's and stays. Runs inside
 * {@link CurriculumImportService}'s transaction.
 */
@Component
class TaxonomyImporter {

    /** The kind each kind must hang from (TECH_PLAN §2.3). */
    private static final Map<NodeKind, NodeKind> PARENT_KIND = Map.of(
            NodeKind.unit, NodeKind.subject,
            NodeKind.chapter, NodeKind.unit,
            NodeKind.topic, NodeKind.chapter);

    private final SyllabusNodeRepository nodes;

    TaxonomyImporter(SyllabusNodeRepository nodes) {
        this.nodes = nodes;
    }

    TaxonomyLoadReport load(List<SyllabusNodeRow> rows) {
        Map<String, SyllabusNodeRow> file = new LinkedHashMap<>();
        for (SyllabusNodeRow row : rows) {
            if (file.put(row.code(), row) != null) {
                throw new CurriculumImportException("code '" + row.code() + "' appears twice in the file");
            }
        }
        rows.forEach(row -> checkParent(row, file));
        checkSiblingOrder(rows);

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
        if (row.kind() == NodeKind.topic && !Objects.equals(row.classLevel(), parent.classLevel())) {
            throw new CurriculumImportException("topic " + row.code() + " has class_level " + row.classLevel()
                    + " but its chapter " + parent.code() + " has " + parent.classLevel());
        }
    }

    /** Two siblings with one {@code sort_order} would render in an arbitrary order (D26 grid, plan cards). */
    private static void checkSiblingOrder(List<SyllabusNodeRow> rows) {
        Map<String, Map<Integer, String>> seen = new HashMap<>();
        for (SyllabusNodeRow row : rows) {
            String parent = row.parentCode() == null ? "(root)" : row.parentCode();
            String other = seen.computeIfAbsent(parent, key -> new HashMap<>()).put(row.sortOrder(), row.code());
            if (other != null) {
                throw new CurriculumImportException("sort_order " + row.sortOrder() + " appears twice under " + parent
                        + " (" + other + " and " + row.code() + ")");
            }
        }
    }

    private static Map<Subject, Map<NodeKind, Integer>> counts(List<SyllabusNodeRow> rows) {
        Map<Subject, Map<NodeKind, Integer>> counts = new EnumMap<>(Subject.class);
        for (SyllabusNodeRow row : rows) {
            counts.computeIfAbsent(row.subject(), subject -> new EnumMap<>(NodeKind.class)).merge(row.kind(), 1, Integer::sum);
        }
        return counts;
    }
}
