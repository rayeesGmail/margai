package com.margai.pipeline.internal;

import com.margai.ai.tasks.PyqTag;
import com.margai.ai.tasks.QuestionTagger;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The taxonomy as {@code pyq tag} needs it (D19): a printed subject's outline as data for the
 * {@code pyq_tag} template — units, their chapters, the chapters' topics, in the founder's order — and
 * the check of a returned tag against it, which is code's job, not the model's: the codes exist, are
 * chapters or topics of the printed subject, the secondaries differ from the primary and each other,
 * and an out-of-syllabus question sits on a chapter.
 *
 * <p>Any biology section — Biology, Botany or Zoology — is tagged against botany and zoology together:
 * the taxonomy files under botany some topics a Zoology section may ask (biotechnology, biomolecules,
 * microbes), and a question must be tagged where the syllabus places it, not forced onto a wrong
 * chapter of its printed half (spec-auditor; DECISIONS 2026-10-20).
 */
final class TaxonomyOutline {

    private final Map<String, SyllabusNodeRow> byCode = new LinkedHashMap<>();
    private final Map<String, List<SyllabusNodeRow>> children = new LinkedHashMap<>();

    TaxonomyOutline(List<SyllabusNodeRow> nodes) {
        for (SyllabusNodeRow node : nodes) {
            byCode.put(node.code(), node);
            children.computeIfAbsent(node.parentCode() == null ? "" : node.parentCode(), parent -> new ArrayList<>()).add(node);
        }
        children.values().forEach(list -> list.sort(Comparator.comparingInt(SyllabusNodeRow::sortOrder)));
    }

    /** The subjects of the taxonomy a question printed under {@code printed} may be tagged in. */
    static Set<Subject> subjects(PaperSubject printed) {
        return printed.taggedIn();
    }

    /** The outline of a printed subject: the same lines for every question printed under it. */
    List<QuestionTagger.OutlineLine> outline(PaperSubject printed) {
        List<QuestionTagger.OutlineLine> lines = new ArrayList<>();
        for (SyllabusNodeRow root : children.getOrDefault("", List.of())) {
            if (root.kind() == NodeKind.subject && subjects(printed).contains(root.subject())) {
                lines.add(new QuestionTagger.OutlineLine(0, NodeKind.subject.name(), null, root.nameEn()));
                write(lines, root.code(), 1);
            }
        }
        return lines;
    }

    private void write(List<QuestionTagger.OutlineLine> lines, String parent, int depth) {
        for (SyllabusNodeRow node : children.getOrDefault(parent, List.of())) {
            lines.add(new QuestionTagger.OutlineLine(depth, node.kind().name(), node.code(), node.nameEn()));
            write(lines, node.code(), depth + 1);
        }
    }

    /** What is wrong with a tag for a question printed under {@code printed}, or null when it holds. */
    String problem(PaperSubject printed, PyqTag tag) {
        String primary = check(printed, tag.primary(), "primary");
        if (primary != null) {
            return primary;
        }
        if (tag.outOfSyllabus() && byCode.get(tag.primary()).kind() != NodeKind.chapter) {
            return "the question is out of syllabus but its primary " + tag.primary() + " is not a chapter — it keeps the "
                    + "nearest chapter";
        }
        Set<String> seen = new HashSet<>(Set.of(tag.primary()));
        for (String secondary : tag.secondary()) {
            String problem = check(printed, secondary, "secondary");
            if (problem != null) {
                return problem;
            }
            if (!seen.add(secondary)) {
                return "the secondary " + secondary + " repeats the primary or another secondary";
            }
        }
        return null;
    }

    private String check(PaperSubject printed, String code, String role) {
        SyllabusNodeRow node = byCode.get(code);
        if (node == null) {
            return "the " + role + " " + code + " is not a code of the taxonomy";
        }
        if (node.kind() != NodeKind.chapter && node.kind() != NodeKind.topic) {
            return "the " + role + " " + code + " is a " + node.kind() + ", not a chapter or a topic";
        }
        if (!subjects(printed).contains(node.subject())) {
            return "the " + role + " " + code + " is " + node.subject() + ", but the question is printed under " + printed;
        }
        return null;
    }

    /** The chapter a chapter or topic code sits in, for the report's distribution. */
    String chapterOf(String code) {
        SyllabusNodeRow node = byCode.get(code);
        while (node != null && node.kind() != NodeKind.chapter) {
            node = node.parentCode() == null ? null : byCode.get(node.parentCode());
        }
        return node == null ? code : node.code() + " " + node.nameEn();
    }
}
