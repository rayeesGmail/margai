package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PyqLoadReport;
import com.margai.curriculum.api.PyqPaperRow;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * {@code pyq load} (D19): the paper upserted on its register id, its questions on the PYQ natural
 * key, each question's secondary topics replaced by the file's, all inside
 * {@link CurriculumImportService}'s transaction. Before anything is written: the register id must
 * name the same exam, year and booklet code it named before, no other id may hold that paper, no
 * question of the paper already in the bank may be missing from the file, and every tag must hold
 * against this database's taxonomy — a chapter or topic of the printed section's subjects, an
 * out-of-syllabus question on a chapter, at most two secondaries, distinct, never the primary.
 * After the write, the paper's questions are read back and counted per printed section: a count that
 * differs from the section's declared size throws, and the whole load rolls back (PLAN D19 ✅).
 */
@Component
class PyqImporter {

    static final int MAX_SECONDARY = 2;

    private final PyqPaperRepository papers;
    private final QuestionRepository questions;
    private final QuestionTopicRepository topics;
    private final SyllabusNodeRepository nodes;

    PyqImporter(PyqPaperRepository papers, QuestionRepository questions, QuestionTopicRepository topics,
            SyllabusNodeRepository nodes) {
        this.papers = papers;
        this.questions = questions;
        this.topics = topics;
        this.nodes = nodes;
    }

    PyqLoadReport load(PyqPaperRow row, List<PyqQuestionRow> rows) {
        boolean paperInserted = false;
        boolean paperUpdated = false;
        PyqPaper paper = papers.findByCode(row.code()).orElse(null);
        papers.findByExamAndYearAndPaperCode(row.exam(), row.year(), row.paperCode())
                .filter(other -> !other.getCode().equals(row.code()))
                .ifPresent(other -> {
                    throw new CurriculumImportException("NEET " + row.year() + " code " + row.paperCode()
                            + " is already loaded as '" + other.getCode() + "', not '" + row.code() + "'");
                });
        if (paper == null) {
            paper = papers.save(new PyqPaper(row));
            paperInserted = true;
        } else if (!paper.isPaper(row)) {
            throw new CurriculumImportException("register id '" + row.code() + "' was " + paper.getExam() + " "
                    + paper.getYear() + " code " + paper.getPaperCode() + " and now names " + row.exam() + " "
                    + row.year() + " code " + row.paperCode() + " — a paper's identity never changes under its id");
        } else {
            paperUpdated = paper.apply(row);
        }
        papers.flush();

        Map<String, SyllabusNode> nodesByCode = resolve(rows);
        Map<String, UUID> nodeIds = new HashMap<>();
        nodesByCode.forEach((code, node) -> nodeIds.put(code, node.getId()));
        Map<Short, Question> existing = new HashMap<>();
        questions.findBySourceAndExamAndYearAndPaperCodeOrderByQuestionNo(QuestionSource.pyq, row.exam(), row.year(),
                row.paperCode()).forEach(question -> existing.put(question.getQuestionNo(), question));
        Set<Short> named = rows.stream().map(PyqQuestionRow::questionNo).collect(Collectors.toSet());
        List<Short> orphans = existing.keySet().stream().filter(no -> !named.contains(no)).sorted().toList();
        if (!orphans.isEmpty()) {
            throw new CurriculumImportException("questions " + orphans + " of this paper are in the bank but not in the file "
                    + "— a question leaves the bank only deliberately, never by a reload");
        }

        int inserted = 0;
        int updated = 0;
        int unchanged = 0;
        Set<Short> inFile = new HashSet<>();
        for (PyqQuestionRow question : rows) {
            if (!inFile.add(question.questionNo())) {
                throw new CurriculumImportException("question " + question.questionNo() + " appears twice in the file");
            }
            requireTag(row, question, nodesByCode);
            UUID nodeId = nodeIds.get(question.nodeCode());
            Question stored = existing.get(question.questionNo());
            boolean topicsChanged;
            if (stored == null) {
                stored = questions.save(new Question(row.exam(), row.year(), row.paperCode(), nodeId, question));
                inserted++;
                topicsChanged = !question.secondaryNodeCodes().isEmpty();
            } else {
                boolean changed = stored.apply(nodeId, question);
                topicsChanged = !currentTopics(stored.getId()).equals(ids(question.secondaryNodeCodes(), nodeIds));
                if (changed || topicsChanged) {
                    updated++;
                } else {
                    unchanged++;
                }
            }
            if (topicsChanged) {
                replaceTopics(stored.getId(), ids(question.secondaryNodeCodes(), nodeIds));
            }
        }
        questions.flush();
        topics.flush();

        List<Question> after = questions.findBySourceAndExamAndYearAndPaperCodeOrderByQuestionNo(QuestionSource.pyq,
                row.exam(), row.year(), row.paperCode());
        requireDeclaredCounts(row, after);
        Map<UUID, String> codes = nodes.findAllById(after.stream().map(Question::getNodeId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(SyllabusNode::getId, SyllabusNode::getCode));
        Map<UUID, Long> topicCounts = topics.findByIdQuestionIdIn(after.stream().map(Question::getId).toList()).stream()
                .collect(Collectors.groupingBy(QuestionTopic::getQuestionId, Collectors.counting()));
        List<PyqLoadReport.StoredQuestion> stored = after.stream().map(question -> new PyqLoadReport.StoredQuestion(
                question.getQuestionNo(), codes.get(question.getNodeId()),
                topicCounts.getOrDefault(question.getId(), 0L).intValue(), question.isOutOfSyllabus(),
                !question.getFigures().isEmpty(), question.getAnswerStatus(), question.getAsPrinted().size())).toList();
        return new PyqLoadReport(paperInserted, paperUpdated, inserted, updated, unchanged, stored);
    }

    /** Every primary and secondary code of the file to its node, refusing the codes the taxonomy lacks. */
    private Map<String, SyllabusNode> resolve(List<PyqQuestionRow> rows) {
        Set<String> wanted = new LinkedHashSet<>();
        rows.forEach(row -> {
            wanted.add(row.nodeCode());
            wanted.addAll(row.secondaryNodeCodes());
        });
        Map<String, SyllabusNode> found = new HashMap<>();
        nodes.findByCodeIn(wanted).forEach(node -> found.put(node.getCode(), node));
        Set<String> missing = new TreeSet<>(wanted);
        missing.removeAll(found.keySet());
        if (!missing.isEmpty()) {
            throw new CurriculumImportException("the tags name node codes the taxonomy does not hold: " + missing);
        }
        return found;
    }

    /**
     * The loader's own check of a tag against the taxonomy in this database (TECH_PLAN §6.3: the loader's
     * invariants are the safety net), not trusted from the tag run: the primary and each secondary a chapter
     * or topic of a subject the printed section is tagged in, an out-of-syllabus question on a chapter, at
     * most two secondaries, distinct, never the primary.
     */
    private static void requireTag(PyqPaperRow paper, PyqQuestionRow question, Map<String, SyllabusNode> nodesByCode) {
        String at = "question " + question.questionNo();
        PaperSection section = paper.sectionOf(question.questionNo());
        if (section == null) {
            throw new CurriculumImportException(at + " is in no printed section of the paper");
        }
        if (question.secondaryNodeCodes().size() > MAX_SECONDARY
                || question.secondaryNodeCodes().contains(question.nodeCode())
                || new HashSet<>(question.secondaryNodeCodes()).size() != question.secondaryNodeCodes().size()) {
            throw new CurriculumImportException(at + " has secondary topics " + question.secondaryNodeCodes()
                    + " — at most two, distinct, never the primary");
        }
        List<String> codes = new ArrayList<>(List.of(question.nodeCode()));
        codes.addAll(question.secondaryNodeCodes());
        for (String code : codes) {
            SyllabusNode node = nodesByCode.get(code);
            if (node.getKind() != NodeKind.chapter && node.getKind() != NodeKind.topic) {
                throw new CurriculumImportException(at + " is tagged " + code + ", a " + node.getKind()
                        + " — a tag is a chapter or a topic");
            }
            if (!section.subject().taggedIn().contains(node.getSubject())) {
                throw new CurriculumImportException(at + " is tagged " + code + " (" + node.getSubject()
                        + ") but is printed under " + section.subject());
            }
        }
        if (question.outOfSyllabus() && nodesByCode.get(question.nodeCode()).getKind() != NodeKind.chapter) {
            throw new CurriculumImportException(at + " is out of syllabus but tagged " + question.nodeCode()
                    + ", not a chapter — it keeps the nearest chapter");
        }
    }

    private Set<UUID> currentTopics(UUID questionId) {
        return topics.findByIdQuestionIdIn(List.of(questionId)).stream().map(QuestionTopic::getNodeId)
                .collect(Collectors.toSet());
    }

    private static Set<UUID> ids(List<String> codes, Map<String, UUID> nodeIds) {
        return codes.stream().map(nodeIds::get).collect(Collectors.toSet());
    }

    private void replaceTopics(UUID questionId, Set<UUID> wanted) {
        List<QuestionTopic> current = topics.findByIdQuestionIdIn(List.of(questionId));
        List<QuestionTopic> stale = new ArrayList<>();
        Set<UUID> have = new HashSet<>();
        for (QuestionTopic topic : current) {
            if (wanted.contains(topic.getNodeId())) {
                have.add(topic.getNodeId());
            } else {
                stale.add(topic);
            }
        }
        topics.deleteAll(stale);
        topics.flush();
        for (UUID nodeId : wanted) {
            if (!have.contains(nodeId)) {
                topics.save(new QuestionTopic(questionId, nodeId));
            }
        }
    }

    /** The ✅ inside the transaction: each printed section holds exactly the questions it declares. */
    private static void requireDeclaredCounts(PyqPaperRow row, List<Question> after) {
        List<String> mismatches = new ArrayList<>();
        for (PaperSection section : row.sections()) {
            long loaded = after.stream().filter(question -> section.contains(question.getQuestionNo())).count();
            if (loaded != section.size()) {
                mismatches.add(section.subject() + (section.section() == null ? "" : " " + section.section()) + ": "
                        + loaded + " loaded, " + section.size() + " declared");
            }
        }
        if (after.size() != row.questionCount()) {
            mismatches.add("the paper: " + after.size() + " loaded, " + row.questionCount() + " declared");
        }
        if (!mismatches.isEmpty()) {
            throw new CurriculumImportException("the paper's questions do not match its printed sections — "
                    + String.join("; ", mismatches));
        }
    }
}
