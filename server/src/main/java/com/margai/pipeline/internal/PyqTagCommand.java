package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.tasks.PyqTag;
import com.margai.ai.tasks.QuestionTagger;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import com.margai.storage.api.ObjectStore;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code pyq tag --paper}: every question of the paper's approved file onto the syllabus (D19's
 * tagging pass) — one REASON call per question through {@code completeBatch}, the founder's
 * Sonnet 5.5 batched (README §1) — into {@code tags/pyq/{paper}.jsonl}.
 *
 * <p>It reads only the approved, committed file (never a draft) and refuses one that fails its
 * register entry; it refuses the fake client and any REASON model but
 * {@code margai.pipeline.pyq.tag-model}. Every tag is checked against the taxonomy in code
 * ({@link TaxonomyOutline}) and a tag that fails is kept with its problem, never loaded. A batch with a
 * question whose answer cannot be decoded is retried question by question and that question kept as a
 * failed line; any other failure stops the run. The artefact is flushed after every batch and whatever
 * stops the run, and the ledger's bill is reported either way. It resumes: a question is called for again only with no
 * valid tag, a tag of other words than the file's now (a corrected stem) or of another prompt version,
 * or under {@code --redo}. The report gives the founder the spread per chapter, every out-of-syllabus
 * question, the low-confidence tags and a seeded sample of {@value #SAMPLE}.
 */
@Component
@Profile("pipeline")
@Command(name = "tag", mixinStandardHelpOptions = true,
        description = "Tag every question of a paper's approved file onto the syllabus with the REASON tier; resumes.")
class PyqTagCommand extends PyqPaperCommand {

    static final int SAMPLE = 40;
    static final String TAXONOMY = "taxonomy.csv";
    /** Where a figure is printed ({@code questions.figures.at}): with the stem, or as or beside option N. */
    static final String STEM = "stem";
    static final String OPTION = "option:";

    @Option(names = "--questions", paramLabel = "N[,N…]", split = ",",
            description = "Only these question numbers (default: every question without a current valid tag).")
    List<Integer> questions;

    @Option(names = "--redo", description = "Call again for questions already tagged, replacing their tags.")
    boolean redo;

    private final ObjectStore content;
    private final QuestionTagger tagger;
    private final AiSpend spend;
    private final AiClientInfo client;
    private final PyqProperties pyq;

    PyqTagCommand(ObjectStore content, QuestionTagger tagger, AiSpend spend, AiClientInfo client, PyqProperties pyq,
            Reports reports) {
        super(reports);
        this.content = content;
        this.tagger = tagger;
        this.spend = spend;
        this.client = client;
        this.pyq = pyq;
    }

    @Override
    void run(PaperDefinition paper, Report report) {
        if (!client.isLive()) {
            throw new InputFormatException(Path.of(FILE), 0, "the AI client is the fake (" + client + "): fixture tags "
                    + "would be written to the paper's tag artefact — run with AI_LIVE=1 and the live profile");
        }
        if (!Objects.equals(tagger.model(), pyq.tagModel())) {
            throw new InputFormatException(Path.of(FILE), 0, "the reasoning tier is " + tagger.model() + ", not "
                    + pyq.tagModel() + ", the PYQ tagger (margai.pipeline.pyq.tag-model; the founder's D19 ruling)");
        }
        Path approved = approvedFile(pyq, paper);
        if (!Files.isRegularFile(approved)) {
            throw new InputFormatException(approved, 0, "does not exist — the draft is checked, approved and committed "
                    + "to the inputs before a paper is tagged");
        }
        PaperFile file = PaperFileJson.read(approved);
        List<String> problems = PaperFileJson.problems(file, paper, true);
        if (!problems.isEmpty()) {
            throw new InputFormatException(approved, 0, "fails its register entry: " + String.join("; ", problems));
        }
        TaxonomyOutline taxonomy = new TaxonomyOutline(TaxonomyCsvReader.read(io.input(TAXONOMY)));
        report.line("content store: " + content.describe());
        report.line("approved file: " + approved.normalize() + " (" + file.questions().size() + " questions)");
        report.line("tagger: " + tagger.model() + ", " + tagger.promptVersion());
        // Read before any call, so a malformed rulings file costs nothing.
        Map<Short, PyqTagRuling> rulings = rulings(pyq, paper, report);

        String tagsKey = ContentKeys.pyqTags(paper.code());
        TreeMap<Short, PyqTagLine> done = new TreeMap<>();
        if (content.exists(tagsKey)) {
            PyqTagJsonl.read(tagsKey, content.get(tagsKey)).forEach(line -> done.put(line.question(), line));
        }
        Map<PaperSubject, List<PaperFile.Question>> toTag = new LinkedHashMap<>();
        for (PaperFile.Question question : file.questions()) {
            boolean selected = questions == null || questions.isEmpty() || questions.contains((int) question.no());
            if (selected && (redo || !current(done.get(question.no()), question))) {
                toTag.computeIfAbsent(paper.row().sectionOf(question.no()).subject(), subject -> new ArrayList<>()).add(question);
            }
        }
        String runId = "pipeline-pyq-tag-" + UUID.randomUUID();
        report.line("request id: " + runId);
        AiCallContext ctx = AiCallContext.system(runId);
        int[] called = {0};
        try {
            for (Map.Entry<PaperSubject, List<PaperFile.Question>> group : toTag.entrySet()) {
                List<QuestionTagger.OutlineLine> outline = taxonomy.outline(group.getKey());
                List<PaperFile.Question> pending = group.getValue();
                for (int from = 0; from < pending.size(); from += pyq.tagBatchSize()) {
                    List<PaperFile.Question> batch = pending.subList(from, Math.min(from + pyq.tagBatchSize(), pending.size()));
                    tagBatch(taxonomy, group.getKey(), outline, batch, done, ctx);
                    called[0] += batch.size();
                    flush(tagsKey, done);
                }
            }
        } catch (RuntimeException e) {
            // Whatever stops the run, the tags paid for are kept and the bill reported.
            flush(tagsKey, done);
            report.section("questions tagged before the run stopped").line(called[0] + " question(s) this run; their tags "
                    + "are kept in " + tagsKey);
            reportCost(report, runId);
            throw e;
        }

        List<PyqTagLine> lines = file.questions().stream().map(question -> done.get(question.no()))
                .filter(Objects::nonNull).toList();
        List<PyqTagLine> valid = lines.stream().filter(PyqTagLine::valid).toList();
        report.section("questions").table(List.of("in the file", "tagged this run", "with a valid tag", "with a problem", "untagged"),
                List.of(List.of(String.valueOf(file.questions().size()), String.valueOf(called[0]), String.valueOf(valid.size()),
                        String.valueOf(lines.size() - valid.size()), String.valueOf(file.questions().size() - lines.size()))));
        report.section("tags with a problem — re-tag with `pyq tag --redo --questions N`")
                .list(lines.stream().filter(line -> !line.valid()).map(line -> "Q" + line.question() + ": " + line.problem()).toList());
        List<List<String>> perSection = new ArrayList<>();
        for (PaperSection section : paper.row().sections()) {
            Map<String, Long> chapters = valid.stream().filter(line -> section.contains(line.question()))
                    .collect(Collectors.groupingBy(line -> taxonomy.chapterOf(line.primary()), TreeMap::new, Collectors.counting()));
            chapters.forEach((chapter, count) -> perSection.add(List.of(section.subject().name()
                    + (section.section() == null ? "" : " " + section.section()), chapter, String.valueOf(count))));
        }
        report.section("primary tags per chapter").table(List.of("section", "chapter", "questions"), perSection);
        report.section("out of syllabus — for the founder to check")
                .list(valid.stream().filter(PyqTagLine::outOfSyllabus)
                        .map(line -> "Q" + line.question() + " → " + line.primary() + ": " + line.reason()
                                + (rulings.containsKey(line.question()) ? " (ruled — below)" : "")).toList(), "none");
        report.section("ruled by the founder — `pyq load` puts each ruling in place of the tagger's tag")
                .list(rulings.values().stream().map(ruling -> "Q" + ruling.question() + ": "
                        + tagged(done.get(ruling.question())) + " → ruled " + ruling.primary()
                        + (ruling.outOfSyllabus() ? ", out of syllabus" : ", in syllabus")
                        + problem(taxonomy, paper.row().sectionOf(ruling.question()).subject(), ruling)).toList(), "none");
        List<String> noAnchor = rulings.values().stream().filter(PyqTagRuling::noNcertAnchor)
                .map(ruling -> "Q" + ruling.question()).toList();
        report.section("no NCERT anchor (the founder's rulings) — no AI solution is written for these")
                .line("this paper: " + share(noAnchor.size(), file.questions().size()))
                .list(noAnchor, "none");
        report.section("not yet in the corpus (the founder's rulings) — the current book prints it only in a table or "
                        + "figure the corpus does not hold yet; no AI solution until the table and figure pass brings it in")
                .list(rulings.values().stream().filter(PyqTagRuling::notYetInCorpus)
                        .map(ruling -> "Q" + ruling.question()).toList(), "none");
        report.section("the founder's review list — below the review floor (" + pyq.tagReviewFloor() + "): each loads as "
                        + "`review`, never as a normal tag, until he rules")
                .list(valid.stream().filter(line -> !rulings.containsKey(line.question()))
                        .filter(line -> line.belowFloor(pyq.tagReviewFloor()))
                        .map(line -> "Q" + line.question() + " → " + line.primary() + " (" + line.confidence() + "): "
                                + line.reason()).toList(), "none");
        report.section("low-confidence tags (below " + pyq.tagLowConfidence() + ") — a routing signal, not a guarantee")
                .list(valid.stream().filter(line -> line.confidence() != null
                                && line.confidence().compareTo(pyq.tagLowConfidence()) < 0)
                        .map(line -> "Q" + line.question() + " → " + line.primary() + " (" + line.confidence() + "): " + line.reason())
                        .toList());
        report.section("the founder's sample — " + SAMPLE + " questions, seeded by the paper's id")
                .table(List.of("question", "primary", "secondary", "reason"), sample(paper, valid).stream()
                        .map(line -> List.of(String.valueOf(line.question()), line.primary(), String.join(", ", line.secondary()),
                                line.reason()))
                        .toList());
        reportCost(report, runId);
        report.line("tags: " + tagsKey + " (" + done.size() + " questions)");
    }

    /**
     * One batch. Only a question whose answer cannot be decoded — {@link InvalidOutputException}, after the
     * schema layer's repair — is the question's own failure: the batch is then retried question by
     * question (its other calls paid a second time, the ledger's bill says so) and that question is kept
     * as a failed line, so on the next run it costs only itself. Anything else — the budget breaker, a
     * provider that refuses the account, a tier policy — is the run's failure and stops it loudly.
     */
    private void tagBatch(TaxonomyOutline taxonomy, PaperSubject printed, List<QuestionTagger.OutlineLine> outline,
            List<PaperFile.Question> batch, Map<Short, PyqTagLine> done, AiCallContext ctx) {
        try {
            List<AiResponse<PyqTag>> responses = tagger.tag(outline, batch.stream().map(q -> asked(printed, q)).toList(), ctx);
            for (int index = 0; index < batch.size(); index++) {
                done.put(batch.get(index).no(), line(taxonomy, printed, batch.get(index), responses.get(index)));
            }
        } catch (InvalidOutputException batchFailure) {
            InvalidOutputException last = batchFailure;
            int failed = 0;
            for (PaperFile.Question question : batch) {
                try {
                    AiResponse<PyqTag> response = tagger.tag(outline, List.of(asked(printed, question)), ctx).getFirst();
                    done.put(question.no(), line(taxonomy, printed, question, response));
                } catch (InvalidOutputException failure) {
                    done.put(question.no(), PyqTagLine.failed(question.no(), textSha(question), tagger.promptVersion(),
                            "the answer could not be read: " + failure.getMessage()));
                    last = failure;
                    failed++;
                }
            }
            // Every question failing again alone is not a question's failure but the run's — an answer cut off
            // at the output limit, say — so the run stops rather than paying twice for every batch after this.
            if (failed == batch.size()) {
                throw last;
            }
        }
    }

    private PyqTagLine line(TaxonomyOutline taxonomy, PaperSubject printed, PaperFile.Question question,
            AiResponse<PyqTag> response) {
        PyqTag tag = response.output();
        return new PyqTagLine(question.no(), tag.primary(), tag.secondary(), tag.outOfSyllabus(), tag.confidence(),
                tag.reason(), response.aiCallId(), response.modelId(), tagger.promptVersion(), textSha(question),
                taxonomy.problem(printed, tag));
    }

    /** The tagger's tag as the ruled-questions list shows it. */
    private static String tagged(PyqTagLine line) {
        return line == null ? "not tagged" : !line.valid() ? "no valid tag" : "tagged " + line.primary()
                + (line.outOfSyllabus() ? ", out of syllabus" : "") + " (" + line.confidence() + ")";
    }

    /** What the taxonomy says against a ruling, so the founder sees it before {@code pyq load} refuses it. */
    private static String problem(TaxonomyOutline taxonomy, PaperSubject printed, PyqTagRuling ruling) {
        String problem = taxonomy.problem(printed, new PyqTag(ruling.primary(), ruling.secondary(), ruling.outOfSyllabus(),
                BigDecimal.ONE, ruling.note()));
        return problem == null ? "" : " — PROBLEM: " + problem;
    }

    /** A valid tag of this question's current words under the current prompt. */
    private boolean current(PyqTagLine line, PaperFile.Question question) {
        return line != null && line.valid() && textSha(question).equals(line.questionSha256())
                && tagger.promptVersion().equals(line.prompt());
    }

    /** The question as the tagger reads it: the stem and options, each figure's description where it is printed. */
    static QuestionTagger.Question asked(PaperSubject printed, PaperFile.Question question) {
        return new QuestionTagger.Question(question.no(), printed.name(), question.stemEn(), figuresAt(question, STEM),
                question.options().stream().map(option -> new QuestionTagger.Option(option.key(), option.textEn(),
                        figuresAt(question, OPTION + option.key()))).toList());
    }

    private static List<String> figuresAt(PaperFile.Question question, String at) {
        return question.figures().stream().filter(figure -> at.equals(figure.at())).map(QuestionFigure::description).toList();
    }

    /**
     * The sha256 of the words the tagger reads — the stem, each option and, for a question that prints figures,
     * each figure's place and description — so a corrected question is re-tagged. A question without figures
     * hashes as it did before the figures were read ({@code pyq_tag.v1}), so {@code pyq load} still takes its v1
     * tag; {@code pyq tag} re-tags a tag of another prompt version, so a v2 run over a v1 paper names its
     * questions ({@code --redo --questions}).
     */
    static String textSha(PaperFile.Question question) {
        StringBuilder text = new StringBuilder(question.stemEn());
        for (QuestionOption option : question.options()) {
            text.append('\n').append(option.key()).append('\t').append(option.textEn());
        }
        for (QuestionFigure figure : question.figures()) {
            text.append('\n').append(figure.at()).append('\t').append(figure.description());
        }
        return PyqExtractCommand.sha256(text.toString().getBytes(StandardCharsets.UTF_8));
    }

    private void flush(String key, Map<Short, PyqTagLine> done) {
        content.put(key, PyqTagJsonl.write(new ArrayList<>(done.values())), "application/jsonl");
    }

    private void reportCost(Report report, String runId) {
        AiSpend.RunSpend bill = spend.of(runId);
        report.section("cost (from the ai_calls ledger)").table(
                List.of("calls", "input", "output", "cache read", "cache write", "cost"),
                List.of(List.of(String.valueOf(bill.calls()),
                        String.valueOf(bill.usage().inputTokens()), String.valueOf(bill.usage().outputTokens()),
                        String.valueOf(bill.usage().cacheReadTokens()), String.valueOf(bill.usage().cacheWriteTokens()),
                        bill.rupees())));
    }

    /** A sample the founder can reproduce: the same paper always draws the same questions. */
    static List<PyqTagLine> sample(PaperDefinition paper, List<PyqTagLine> valid) {
        List<PyqTagLine> shuffled = new ArrayList<>(valid);
        Collections.shuffle(shuffled, new Random(paper.code().hashCode()));
        List<PyqTagLine> sample = new ArrayList<>(shuffled.subList(0, Math.min(SAMPLE, shuffled.size())));
        sample.sort(Comparator.comparingInt(PyqTagLine::question));
        return sample;
    }
}
