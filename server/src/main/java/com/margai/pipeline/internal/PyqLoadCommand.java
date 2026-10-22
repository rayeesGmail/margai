package com.margai.pipeline.internal;

import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PyqLoadReport;
import com.margai.curriculum.api.PyqQuestionRow;
import com.margai.curriculum.api.QuestionTagging;
import com.margai.curriculum.api.TagRuling;
import com.margai.curriculum.api.TagStatus;
import com.margai.storage.api.ObjectStore;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * {@code pyq load --paper}: the register entry, the approved paper file and its tags into
 * {@code pyq_papers}, {@code questions} and {@code question_topics} in one transaction (D19). No model
 * is called.
 *
 * <p>Refused before the database is touched: an approved file that fails its register entry
 * ({@link PaperFileJson#problems} — every declared question once, (1) to (4), an answer shaped like
 * its status and agreeing with the founder's special entries), and a question without a valid tag
 * in {@code tags/pyq/{paper}.jsonl} made of its current words — unless the founder ruled its tag in
 * {@code pyq-tag-rulings.yaml}, whose ruling loads in its place as {@code founder} (DECISIONS 2026-10-21), the
 * tagger's tag kept beside it; a tagger's tag below the review floor loads as {@code review}. Refused inside the transaction, rolling
 * everything back: a tag that does not hold against the database's taxonomy, a question of the paper
 * in the bank but missing from the file, and a paper whose questions in the database do not number
 * what each printed section declares. The report's first table is the D19 ✅ — the count per printed section
 * against the booklet's — read back from the database, with the figure questions per section the
 * founder's ruling 4 asks for.
 */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Load a paper's approved file and tags into the question bank; the report is the D19 count check.")
class PyqLoadCommand extends PyqPaperCommand {

    private final ObjectStore content;
    private final CurriculumImport imports;
    private final PyqProperties pyq;

    PyqLoadCommand(ObjectStore content, CurriculumImport imports, PyqProperties pyq, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
        this.pyq = pyq;
    }

    @Override
    void run(PaperDefinition paper, Report report) {
        Path approved = approvedFile(pyq, paper);
        if (!Files.isRegularFile(approved)) {
            throw new InputFormatException(approved, 0, "does not exist — the draft is checked, approved and committed "
                    + "to the inputs before a paper is loaded");
        }
        PaperFile file = PaperFileJson.read(approved);
        List<String> problems = PaperFileJson.problems(file, paper, true);
        if (!problems.isEmpty()) {
            throw new InputFormatException(approved, 0, "fails its register entry: " + String.join("; ", problems));
        }
        String tagsKey = ContentKeys.pyqTags(paper.code());
        if (!content.exists(tagsKey)) {
            throw new InputFormatException(Path.of(tagsKey), 0, "does not exist — run `pyq tag --paper " + paper.code()
                    + "` first");
        }
        Map<Short, PyqTagLine> tags = PyqTagJsonl.read(tagsKey, content.get(tagsKey)).stream()
                .collect(Collectors.toMap(PyqTagLine::question, Function.identity(), (first, second) -> second));
        Map<Short, PyqTagRuling> rulings = rulings(pyq, paper, report);
        List<String> untagged = new ArrayList<>();
        for (PaperFile.Question question : file.questions()) {
            PyqTagLine tag = tags.get(question.no());
            if (rulings.containsKey(question.no())) {
                continue;
            }
            if (tag == null) {
                untagged.add("Q" + question.no() + " has no tag");
            } else if (!tag.valid()) {
                untagged.add("Q" + question.no() + ": " + tag.problem());
            } else if (!PyqTagCommand.textSha(question).equals(tag.questionSha256())) {
                untagged.add("Q" + question.no() + " was tagged on other words than the approved file has now");
            }
        }
        if (!untagged.isEmpty()) {
            throw new InputFormatException(Path.of(tagsKey), 0, untagged.size() + " question(s) without a valid tag — "
                    + String.join("; ", untagged) + " — re-tag them with `pyq tag --redo --questions N`");
        }
        report.line("content store: " + content.describe());
        report.line("approved file: " + approved.normalize());
        report.line("tags: " + tagsKey);

        List<PyqQuestionRow> rows = file.questions().stream()
                .map(question -> rulings.containsKey(question.no())
                        ? ruled(question, current(question, tags.get(question.no())), rulings.get(question.no()))
                        : row(question, tags.get(question.no()), pyq.tagReviewFloor()))
                .toList();
        PyqLoadReport result = imports.loadPaper(paper.row(), rows);

        List<List<String>> counts = new ArrayList<>();
        for (PaperSection section : paper.row().sections()) {
            List<PyqLoadReport.StoredQuestion> in = result.stored().stream().filter(question -> section.contains(question.no())).toList();
            counts.add(List.of(section.subject().name(), section.section() == null ? "—" : section.section(),
                    String.valueOf(section.size()), String.valueOf(in.size()),
                    String.valueOf(in.stream().filter(PyqLoadReport.StoredQuestion::hasFigure).count()),
                    String.valueOf(in.stream().filter(PyqLoadReport.StoredQuestion::outOfSyllabus).count()),
                    in.size() == section.size() ? "yes" : "NO"));
        }
        report.section("the D19 check — questions per printed section, as the database holds them").table(
                List.of("subject", "section", "declared", "loaded", "with a figure", "out of syllabus", "match"), counts);
        report.line("every section matches the count its booklet declares (the load refuses otherwise, inside its "
                + "transaction): " + result.stored().size() + " of " + paper.row().questionCount() + " questions");
        report.section("load").table(List.of("paper", "inserted", "updated", "unchanged"),
                List.of(List.of(result.paperInserted() ? "inserted" : result.paperUpdated() ? "updated" : "unchanged",
                        String.valueOf(result.inserted()), String.valueOf(result.updated()), String.valueOf(result.unchanged()))));
        int shown = result.stored().stream().mapToInt(PyqLoadReport.StoredQuestion::asPrintedShow).sum();
        int recorded = result.stored().stream().mapToInt(PyqLoadReport.StoredQuestion::asPrintedRecordOnly).sum();
        report.section("misprints kept as printed — `show` ones the app can show as an \"as printed in the original "
                        + "paper\" note, `record_only` ones kept without (as the database holds them)")
                .list(result.stored().stream().filter(question -> question.asPrintedShow() + question.asPrintedRecordOnly() > 0)
                        .map(question -> "Q" + question.no() + ": " + question.asPrintedShow() + " show, "
                                + question.asPrintedRecordOnly() + " record_only").toList(), "none")
                .line("show " + shown + ", record_only " + recorded);
        report.section("whose tag each question carries (questions.tag_status) — a `review` tag counts toward no topic's "
                        + "weightage and is not picked for topic practice until the founder rules")
                .line(Arrays.stream(TagStatus.values()).map(status -> status + " " + result.stored().stream()
                        .filter(question -> question.tagStatus() == status).count()).collect(Collectors.joining(", ")))
                .list(result.stored().stream().filter(question -> question.tagStatus() == TagStatus.review)
                        .map(question -> "Q" + question.no() + " → " + question.nodeCode() + ": review").toList(), "none");
        report.section("the founder's tag rulings — each loaded in place of the tagger's tag (`founder`)")
                .list(rulings.values().stream().map(ruling -> "Q" + ruling.question() + " → " + ruling.primary()
                        + (ruling.secondary().isEmpty() ? "" : " + " + String.join(", ", ruling.secondary()))
                        + (ruling.outOfSyllabus() ? ", out of syllabus" : "") + " (ruled " + ruling.ruled() + "; "
                        + replaced(current(question(file, ruling.question()), tags.get(ruling.question()))) + "): "
                        + ruling.note()).toList(), "none");
        report.section("the taxonomy-gap list — a node proposed for each, for the founder to rule")
                .list(rulings.values().stream().filter(ruling -> ruling.gap() != null)
                        .map(ruling -> "Q" + ruling.question() + ": " + ruling.gap()).toList(), "none");
        List<String> noAnchor = rulings.values().stream().filter(PyqTagRuling::noNcertAnchor)
                .map(ruling -> "Q" + ruling.question()).toList();
        report.section("no NCERT anchor — no AI solution is written for these (hard rule 2; the founder, DECISIONS "
                        + "2026-10-22); they count toward weightage, stay out of topic practice, and a mock serves them "
                        + "with NTA's key and one line")
                .line("this paper: " + share(noAnchor.size(), file.questions().size()))
                .list(noAnchor, "none")
                .line(bankLine(noAnchorRulings, result.bankQuestions(), pyq.noAnchorRevisitShare()));
        report.section("answers other than one key (NTA's final key; the register's special entries)")
                .list(result.stored().stream().filter(question -> question.answerStatus() != AnswerStatus.single)
                        .map(question -> "Q" + question.no() + ": " + question.answerStatus()).toList(), "none");
        report.section("marking").line("correct / incorrect: " + paper.row().marking().correct() + " / "
                        + paper.row().marking().wrong() + " (" + paper.row().marking().schemeSource() + ")")
                .line(paper.row().marking().rulesSourced()
                        ? "blank " + paper.row().marking().unanswered() + "; dropped and multiple-answer rules: "
                                + paper.row().marking().droppedRule() + ", " + paper.row().marking().multipleRule()
                                + " (" + paper.row().marking().rulesSource() + ")"
                        : "PENDING — " + (paper.row().marking().unanswered() == null ? "the blank mark, " : "")
                                + "the dropped and multiple-answer rules: NTA's notice for " + paper.row().year()
                                + " is not sourced (F18), and no mock may score this paper until it is");
        report.section("key provenance").line(paper.row().keySource().tier() + " — " + paper.row().keySource().from()
                + ", downloaded " + paper.row().keySource().downloaded() + ", " + paper.row().keySource().file()
                + " (sha256 " + paper.row().keySource().sha256() + ")");
        report.line("official Hindi in the booklet: " + (paper.row().officialHindi() ? "yes — for the parked Hindi pass" : "no"));
    }

    /**
     * The approved question with its tag, the answer in the shape the database's check holds; a tag below the
     * review floor is stored as {@code review}.
     */
    static PyqQuestionRow row(PaperFile.Question question, PyqTagLine tag, BigDecimal reviewFloor) {
        return row(question, tag.primary(), tag.secondary(), tag.outOfSyllabus(),
                new QuestionTagging(tag.aiCallId(), tag.model(), tag.prompt(), tag.confidence(), tag.reason()),
                tag.belowFloor(reviewFloor) ? TagStatus.review : TagStatus.model);
    }

    /**
     * The approved question with the founder's ruling in place of the tagger's tag, stored as {@code founder}; the
     * tagger's tag of its current words ({@code current}, null when it has none) is kept beside the ruling.
     */
    static PyqQuestionRow ruled(PaperFile.Question question, PyqTagLine current, PyqTagRuling ruling) {
        TagRuling kept = new TagRuling(ruling.ruled().toString(), ruling.note(), ruling.anchor(), ruling.gap(),
                current == null ? null : current.primary(), current == null ? List.of() : current.secondary(),
                current == null ? null : current.outOfSyllabus());
        QuestionTagging tagging = current == null ? new QuestionTagging(null, null, null, null, null, kept)
                : new QuestionTagging(current.aiCallId(), current.model(), current.prompt(), current.confidence(),
                        current.reason(), kept);
        return row(question, ruling.primary(), ruling.secondary(), ruling.outOfSyllabus(), tagging, TagStatus.founder);
    }

    private static PyqQuestionRow row(PaperFile.Question question, String primary, List<String> secondary,
            boolean outOfSyllabus, QuestionTagging tagging, TagStatus status) {
        PaperFile.Answer answer = question.answer();
        String correctKey = switch (answer.status()) {
            case single -> answer.key();
            case multiple -> answer.keys().stream().sorted().findFirst().orElseThrow();
            case dropped -> null;
        };
        List<String> accepted = answer.status() == AnswerStatus.multiple ? answer.keys().stream().sorted().toList() : null;
        return new PyqQuestionRow(question.no(), primary, secondary, outOfSyllabus, question.stemEn(), question.stemHi(),
                question.options(), answer.status(), correctKey, accepted, question.figures(), question.extraction(), tagging,
                question.asPrinted(), status);
    }

    /** The question's tag when it is valid and of the question's current words, else null. */
    private static PyqTagLine current(PaperFile.Question question, PyqTagLine tag) {
        return tag != null && tag.valid() && PyqTagCommand.textSha(question).equals(tag.questionSha256()) ? tag : null;
    }

    private static PaperFile.Question question(PaperFile file, short no) {
        return file.questions().stream().filter(question -> question.no() == no).findFirst().orElseThrow();
    }

    /**
     * The bank's share of questions with no NCERT anchor against the founder's revisit line: the rulings file's
     * {@code anchor: none} entries across papers over the PYQs in the bank after the load.
     */
    static String bankLine(long noAnchor, long bank, BigDecimal revisitShare) {
        boolean above = bank > 0 && BigDecimal.valueOf(noAnchor).compareTo(revisitShare.multiply(BigDecimal.valueOf(bank))) > 0;
        return "the bank: " + share(noAnchor, bank) + " — " + (above
                ? "ABOVE the founder's ~" + revisitShare.movePointRight(2).stripTrailingZeros().toPlainString()
                        + "% line: for him to revisit the policy (DECISIONS 2026-10-22)"
                : "within the founder's ~" + revisitShare.movePointRight(2).stripTrailingZeros().toPlainString() + "% line");
    }

    /** What a ruling replaced, for the report. */
    private static String replaced(PyqTagLine tag) {
        return tag == null ? "the tagger has no tag of its current words"
                : "the tagger's " + tag.primary() + (tag.outOfSyllabus() ? ", out of syllabus" : "") + ", " + tag.confidence();
    }
}
