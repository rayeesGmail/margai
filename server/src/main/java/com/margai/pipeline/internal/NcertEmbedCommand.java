package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiSpend;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.ParagraphEmbedding;
import com.margai.curriculum.api.ParagraphToEmbed;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert embed --book} (TECH_PLAN §6.3, §4.9, §6.4): a vector for every paragraph of a book,
 * so the vector half of hybrid retrieval has something to search.
 *
 * <p>It embeds {@code text_en} and only that (§6.4). Hindi is not a second pass here — the pinned
 * model is multilingual, so a Hindi question is meant to reach the English paragraph through the
 * model's own cross-lingual space, with the {@code tsv} match over {@code text_hi} beside it.
 * Whether that actually holds is what this day's query run measures, and it is the reason the book
 * is embedded before the other nine are extracted: provider, model and width move together
 * (`margai.ai.embed`), so finding the pin wrong across 894 paragraphs is cheap and finding it
 * wrong across ~9,000 is not (founder decision 2026-09-19).
 *
 * <p>It resumes, because a paragraph waits for a vector exactly when its embedding is null — which
 * is also how {@code ncert load} expresses staleness. So a second run over an embedded book calls
 * for nothing and costs nothing, and a run interrupted at paragraph 800 keeps its 800.
 */
@Component
@Profile("pipeline")
@Command(name = "embed", mixinStandardHelpOptions = true,
        description = "Embed the book's English paragraphs for retrieval; resumes, and re-embeds only what changed.")
class NcertEmbedCommand extends NcertBookCommand {

    @Option(names = "--redo", description = "Embed every paragraph again, replacing the vectors already stored.")
    boolean redo;

    /** {@code --queries none}: embed without scoring, said out loud rather than by a missing file. */
    static final String SKIP_QUERIES = "none";

    @Option(names = "--queries", paramLabel = "FILE", defaultValue = "../eval/retrieval-queries.json",
            description = "The concept queries to run afterwards, or 'none' (default: ${DEFAULT-VALUE}).")
    Path queriesFile;

    private final CurriculumImport imports;
    private final EmbeddingService embeddings;
    private final HybridRetriever retriever;
    private final AiSpend spend;
    private final AiClientInfo client;
    private final PipelineProperties properties;

    NcertEmbedCommand(CurriculumImport imports, EmbeddingService embeddings, HybridRetriever retriever,
            AiSpend spend, AiClientInfo client, PipelineProperties properties, Reports reports) {
        super(reports);
        this.imports = imports;
        this.embeddings = embeddings;
        this.retriever = retriever;
        this.spend = spend;
        this.client = client;
        this.properties = properties;
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        guardEnglish();
        guardLiveClient();

        String runId = "pipeline-ncert-embed-" + UUID.randomUUID();
        report.line("request id: " + runId);
        report.line("chunk: one paragraph, its English text as loaded (§6.4)");
        AiCallContext ctx = AiCallContext.system(runId);

        List<RetrievalQuery> queries = queriesFor(definition, report);
        List<Short> chapterNumbers = selected.stream().map(BookDefinition.Chapter::no).toList();
        boolean wholeBook = chapterNumbers.size() == definition.chapters().size();
        List<ParagraphToEmbed> waiting = imports.paragraphsToEmbed(definition.code(),
                wholeBook ? List.of() : chapterNumbers, redo);
        report.read(waiting.size() + " paragraph(s) waiting for a vector"
                + (wholeBook ? "" : " in chapters " + chapterNumbers)
                + (redo ? " (--redo: every selected paragraph, embedded or not)" : ""));

        int embedded = 0;
        Map<Short, Integer> perChapter = new TreeMap<>();
        List<ParagraphEmbedding> batch = new ArrayList<>();
        List<RetrievalRun.Result> results = List.of();
        try {
            for (ParagraphToEmbed paragraph : waiting) {
                batch.add(new ParagraphEmbedding(paragraph.paragraphId(),
                        embeddings.ofDocument(paragraph.text(), ctx)));
                perChapter.merge(paragraph.chapterNo(), 1, Integer::sum);
                if (batch.size() >= properties.embedBatchSize()) {
                    embedded += imports.storeEmbeddings(definition.code(), batch);
                    batch.clear();
                }
            }
            embedded += imports.storeEmbeddings(definition.code(), batch);
            results = RetrievalRun.run(queries, definition, retriever, ctx);
        } finally {
            // After all the paid work and before the scoring, which can still refuse (§10.5): a
            // run that dies has still spent, and the report is where the founder reads what it
            // spent. The query embeddings are on the same request id, so this is the whole bill.
            AiSpend.RunSpend bill = spend.of(runId);
            report.section("cost").table(List.of("calls", "input tokens", "spent"),
                    List.of(List.of(String.valueOf(bill.calls()),
                            String.valueOf(bill.usage().inputTokens()), bill.rupees())));
        }

        List<List<String>> rows = new ArrayList<>();
        for (BookDefinition.Chapter chapter : selected) {
            rows.add(List.of(String.valueOf(chapter.no()),
                    String.valueOf(perChapter.getOrDefault(chapter.no(), 0))));
        }
        report.section("paragraphs embedded per chapter").table(List.of("chapter", "embedded"), rows);

        List<ParagraphToEmbed> stillWaiting = imports.paragraphsToEmbed(definition.code(),
                wholeBook ? List.of() : chapterNumbers, false);
        report.section("ncert_paragraphs.embedding")
                .table(List.of("embedded this run", "still without a vector"),
                        List.of(List.of(String.valueOf(embedded), String.valueOf(stillWaiting.size()))));
        report.section("paragraphs still without a vector")
                .list(stillWaiting.stream().map(ParagraphToEmbed::address).toList(),
                        "none — every English paragraph of the book is embedded");

        RetrievalRun.report(results, queries, report);
    }

    /**
     * The concept queries, when the set on disk is written against this book. A set written for
     * another book is not run and says so — embedding the other nine books must not depend on a
     * query set that exists for one.
     *
     * <p>A <em>missing</em> file refuses the run instead, before anything is paid for. The default
     * is a relative path, so a command launched from the wrong directory would otherwise embed a
     * whole book, spend the money and produce no acceptance evidence — the silent no-op this
     * command already refuses the fake client and {@code --lang hi} to prevent. {@code --queries
     * none} is the way to say it was meant.
     */
    private List<RetrievalQuery> queriesFor(BookDefinition definition, Report report) {
        if (SKIP_QUERIES.equals(queriesFile.toString())) {
            report.line("concept queries: skipped by --queries none");
            return List.of();
        }
        if (!Files.isRegularFile(queriesFile)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "no concept queries at " + queriesFile.toAbsolutePath() + " — this run would embed the book, "
                            + "spend, and produce no acceptance evidence (PLAN D15 ✅). Run from `server/`, pass "
                            + "--queries with the right path, or --queries none to embed without scoring");
        }
        RetrievalQueriesReader.QuerySet set = RetrievalQueriesReader.read(queriesFile);
        if (!set.book().equals(definition.code())) {
            report.line("concept queries: the set at " + queriesFile + " is written against '" + set.book()
                    + "', not '" + definition.code() + "' — not run");
            return List.of();
        }
        report.line("concept queries: " + set.queries().size() + " from " + queriesFile
                + " (" + set.queries().stream().filter(RetrievalQuery::isHindi).count() + " in Hindi)");
        return set.queries();
    }

    /**
     * §6.4 pins the canonical text: one vector per paragraph, over {@code text_en}. A
     * {@code --lang hi} run would silently do nothing, which is worse than refusing.
     */
    private void guardEnglish() {
        if (language != BookLanguage.en) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "embedding is over the canonical English text (§6.4): the multilingual pin is what carries a "
                            + "Hindi query to an English paragraph, so there is no --lang " + language + " pass");
        }
    }

    /**
     * The fake client answers an embed call with a fixture, and a fixture vector written into the
     * corpus is invisible: every retrieval over it is wrong, nothing fails, and it reads as a bad
     * embedding pin rather than as a run that never reached a provider.
     */
    private void guardLiveClient() {
        if (!client.isLive()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the AI client is the fake (" + client + "): its vectors would be stored on the real rows and "
                            + "no check would ever see them — run with AI_LIVE=1 and the live profile");
        }
    }
}
