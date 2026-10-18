package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiSpend;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.CurriculumImport;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert retrieve} (D17; PLAN D17, TECH_PLAN §6.3, §6.4): the concept queries of
 * {@code eval/retrieval-queries.json} through {@link HybridRetriever} over the whole corpus, each
 * filtered to its own subject as the doubt pipeline filters (§4.3 stage 6), and the acceptance set
 * once more with no subject filter as the founder's diagnostic (DECISIONS 2026-10-18). Scored against
 * the addresses each query was written to reach, with the founder's bar read from the filtered
 * acceptance run. It embeds the queries and nothing else, and writes nothing to the corpus.
 *
 * <p>Separate from {@code ncert embed}'s closing run because that run is one book's, and D17's
 * question is the corpus's: whether a question finds its paragraph among all ten books of its
 * subject, Hindi and Hinglish included.
 */
@Component
@Profile("pipeline")
@Command(name = "retrieve", mixinStandardHelpOptions = true,
        description = "Run the concept queries over the whole corpus — each filtered to its subject, the acceptance "
                + "set again with no filter — and score them; embeds the queries only, changes nothing.")
class NcertRetrieveCommand extends InputFileCommand {

    static final String FILE = "retrieval-queries.json";

    @Option(names = "--queries", paramLabel = "FILE", defaultValue = "../eval/retrieval-queries.json",
            description = "The concept queries (default: ${DEFAULT-VALUE}, relative to the working directory).")
    Path queriesFile;

    private final CurriculumImport imports;
    private final HybridRetriever retriever;
    private final AiSpend spend;
    private final AiClientInfo client;

    NcertRetrieveCommand(CurriculumImport imports, HybridRetriever retriever, AiSpend spend, AiClientInfo client,
            Reports reports) {
        super(reports);
        this.imports = imports;
        this.retriever = retriever;
        this.spend = spend;
        this.client = client;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    Path inputPath() {
        return queriesFile;
    }

    @Override
    void run(Path inputFile, Report report) {
        guardLiveClient(inputFile);
        List<RetrievalQuery> queries = RetrievalQueriesReader.read(inputFile);
        List<BookDefinition> books = BooksYamlReader.read(io.input(NcertRegisterCommand.FILE));
        RetrievalQueriesReader.checkBooks(inputFile, queries, books);
        List<RetrievalQuery> acceptance = queries.stream()
                .filter(query -> query.set() == RetrievalQuery.QuerySet.acceptance).toList();
        report.read(queries.size() + " concept queries — " + count(queries, RetrievalQuery.QuerySet.acceptance)
                + " acceptance, " + count(queries, RetrievalQuery.QuerySet.baseline) + " baseline, "
                + count(queries, RetrievalQuery.QuerySet.diagnostic) + " diagnostic");

        corpus(books, report);

        String runId = "pipeline-ncert-retrieve-" + UUID.randomUUID();
        report.line("request id: " + runId);
        AiCallContext ctx = AiCallContext.system(runId);
        List<RetrievalRun.Result> filtered = List.of();
        List<RetrievalRun.Result> unfiltered = List.of();
        try {
            filtered = RetrievalRun.run(queries, retriever, ctx);
            unfiltered = RetrievalRun.runUnfiltered(acceptance, retriever, ctx);
        } finally {
            // The query embeddings are the whole bill; read from the ledger, never computed (§10.5).
            AiSpend.RunSpend bill = spend.of(runId);
            report.section("cost").table(List.of("calls", "input tokens", "spent"),
                    List.of(List.of(String.valueOf(bill.calls()),
                            String.valueOf(bill.usage().inputTokens()), bill.rupees())));
        }
        RetrievalRun.reportCorpus(filtered, unfiltered, report, expectedText(filtered));
    }

    /**
     * What the run searched: every registered book's paragraphs with and without a vector. A row
     * without one is not in the vector index at all, so a corpus that is not fully embedded is a
     * reason a query missed that has nothing to do with retrieval.
     */
    private void corpus(List<BookDefinition> books, Report report) {
        List<List<String>> rows = new ArrayList<>();
        long without = 0;
        for (BookDefinition book : books) {
            long embedded = imports.embeddedParagraphs(book.code());
            long waiting = imports.paragraphsToEmbed(book.code(), List.of(), false).size();
            without += waiting;
            rows.add(List.of(book.code(), book.row().subject().name(), String.valueOf(embedded), String.valueOf(waiting)));
        }
        report.section("the corpus searched — " + (without == 0 ? "every English paragraph carries a vector"
                        : without + " English paragraph(s) WITHOUT a vector, invisible to the vector half"))
                .table(List.of("book", "subject", "with a vector", "without"), rows);
    }

    /** The English text at each address a miss expected, read once per book. */
    private Function<RetrievalQuery.Address, String> expectedText(List<RetrievalRun.Result> results) {
        Map<String, List<Short>> chapters = results.stream()
                .filter(result -> !result.inAnswerSet())
                .flatMap(result -> result.query().expect().stream())
                .collect(Collectors.groupingBy(RetrievalQuery.Address::book,
                        Collectors.mapping(RetrievalQuery.Address::chapter, Collectors.toList())));
        Map<String, String> texts = new HashMap<>();
        chapters.forEach((book, numbers) -> imports.alignmentRows(book, numbers.stream().distinct().toList())
                .forEach(row -> texts.put(key(book, row), row.textEn())));
        return address -> texts.get(address.toString());
    }

    /** Spelled as {@link RetrievalQuery.Address#toString()} spells an address: "bio11 ch 3 §3.2 ¶4". */
    private static String key(String book, AlignmentRow row) {
        return book + " " + row.address();
    }

    private static long count(List<RetrievalQuery> queries, RetrievalQuery.QuerySet set) {
        return queries.stream().filter(query -> query.set() == set).count();
    }

    /**
     * The fake client answers an embed call with a fixture vector, so every query would be compared
     * against the real corpus with a vector that means nothing: the run would score, the scores
     * would be noise, and nothing would say so.
     */
    private void guardLiveClient(Path inputFile) {
        if (!client.isLive()) {
            throw new InputFormatException(inputFile, 0,
                    "the AI client is the fake (" + client + "): its query vectors mean nothing, so every score would "
                            + "be noise — run with AI_LIVE=1 and the live profile");
        }
    }
}
