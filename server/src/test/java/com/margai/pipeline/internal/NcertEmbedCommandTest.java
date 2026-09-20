package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.internal.TestAiProperties;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphEmbedding;
import com.margai.curriculum.api.ParagraphRetrievalRepository;
import com.margai.curriculum.api.ParagraphToEmbed;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert embed} with a recording embedder in place of the provider: every waiting paragraph
 * is embedded once as a <em>document</em>, the vectors are committed in batches so an interrupted
 * run keeps what it paid for, a book with nothing waiting calls for nothing, and the two refusals
 * hold — the fake client, and a {@code --lang hi} pass §6.4 does not have.
 */
class NcertEmbedCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final StubEmbeddings embeddings = new StubEmbeddings();
    private final EmbeddingImport imports = new EmbeddingImport();
    private final NcertExtractCommandTest.StubSpend spend = new NcertExtractCommandTest.StubSpend();
    private AiClientInfo client = new AiClientInfo("cohere", List.of("ledger"));

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), """
                books:
                  - code: phy11-part1
                    subject: physics
                    class_level: 11
                    part: 1
                    title_en: "Physics Part-I, Textbook for Class XI"
                    edition_year: 2023
                    source:
                      en: source/ncert/2022-ed/en/phy11-part1/
                      hi: source/ncert/2022-ed/hi/phy11-part1/
                    chapters:
                      - {no: 6, en: keph106.pdf, hi: hhph106.pdf}
                      - {no: 7, en: keph107.pdf, hi: hhph107.pdf}
                """);
        imports.waiting = List.of(
                new ParagraphToEmbed(UUID.randomUUID(), (short) 6, "6.9", (short) 1, "Kinetic energy is one half."),
                new ParagraphToEmbed(UUID.randomUUID(), (short) 7, "7.9", (short) 1, "Gravitational potential."),
                new ParagraphToEmbed(UUID.randomUUID(), (short) 7, "7.9", (short) 2, "W = -G M m / r."));
    }

    @Test
    void embedsEveryWaitingParagraphOnceAsADocument() {
        assertThat(run()).isZero();

        assertThat(embeddings.documents)
                .containsExactly("Kinetic energy is one half.", "Gravitational potential.", "W = -G M m / r.");
        assertThat(embeddings.queries).as("nothing here is a query").isEmpty();
        assertThat(imports.stored).hasSize(3);
        assertThat(report()).contains("| 6 | 1 |").contains("| 7 | 2 |");
    }

    /** §10.5: the cost comes from the ledger, and it is in the report even on the way out. */
    @Test
    void reportsTheRunsCostFromTheLedger() {
        run();

        assertThat(spend.asked).singleElement().asString().startsWith("pipeline-ncert-embed-");
        assertThat(report()).contains("| 3 | 9000 | ₹4.41 |");
    }

    /** A run interrupted late keeps what it paid for: vectors are committed as it goes. */
    @Test
    void commitsInBatchesRatherThanOnceAtTheEnd() {
        assertThat(run(2)).isZero();

        assertThat(imports.batchSizes).as("two, then the remainder").containsExactly(2, 1);
    }

    @Test
    void aBookWithNothingWaitingCallsForNothing() {
        imports.waiting = List.of();

        assertThat(run()).isZero();

        assertThat(embeddings.documents).isEmpty();
        assertThat(report()).contains("none — every English paragraph of the book is embedded");
    }

    /**
     * The first live run died on call 101 of 894 against a trial key's 100-per-minute cap and threw
     * away the vectors bought since the last commit — paid for, then discarded, with the rows left
     * null for the next run to buy again (2026-09-20). What is in hand is committed before the
     * failure propagates.
     */
    @Test
    void aFailureMidBatchKeepsTheVectorsAlreadyPaidFor() {
        embeddings.failOnCall = 3;

        assertThat(run(100)).isEqualTo(1);

        assertThat(imports.stored).as("the two bought before the failure are kept").hasSize(2);
        assertThat(report()).contains("the embedding provider is out of quota");
    }

    /**
     * A fixture vector stored on a real row is invisible: every retrieval over it is wrong, nothing
     * fails, and it reads as a bad embedding pin rather than as a run that never reached a provider.
     */
    @Test
    void refusesToEmbedOnTheFakeClient() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));

        assertThat(run()).isEqualTo(1);

        assertThat(embeddings.documents).isEmpty();
        assertThat(imports.stored).isEmpty();
        assertThat(report()).contains("the AI client is the fake");
    }

    /**
     * `--chapters` is inherited from {@link NcertBookCommand} and advertised as "only these
     * chapter numbers". It has to reach the query, not just the report: a run that embedded and
     * paid for all 894 paragraphs while reporting one chapter's count is the silent no-op this
     * command refuses the fake client and `--lang hi` to prevent (spec-auditor, D15).
     */
    @Test
    void chaptersNarrowsWhatIsEmbeddedAndNotOnlyWhatIsReported() {
        assertThat(run(100, "--chapters", "7")).isZero();

        assertThat(imports.chaptersAsked).as("the selection reaches the query")
                .containsExactly(List.of((short) 7), List.of((short) 7));
        assertThat(report()).contains("in chapters [7]");
    }

    @Test
    void thewholeBookAsksForEveryChapter() {
        assertThat(run()).isZero();

        assertThat(imports.chaptersAsked).allSatisfy(asked -> assertThat(asked).isEmpty());
    }

    /**
     * The default is a relative path, so a run launched from the wrong directory would embed a
     * whole book, spend, and produce no acceptance evidence. It refuses before any call.
     */
    @Test
    void refusesWhenTheQuerySetIsMissingRatherThanEmbeddingWithNoEvidence() {
        int status = commandLine().execute("ncert", "embed", "--book", "phy11-part1",
                "--queries", inputs.resolve("absent.json").toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString());

        assertThat(status).isEqualTo(1);
        assertThat(embeddings.documents).as("nothing was paid for").isEmpty();
        assertThat(report()).contains("no concept queries at").contains("--queries none");
    }

    // ── the D15 experiment: --context section ───────────────────────────────────────────────────

    /** Default is §6.4 as written, so the spec'd behaviour runs unless the experiment is named. */
    @Test
    void byDefaultTheParagraphIsEmbeddedBareAsTheSpecSays() {
        assertThat(run()).isZero();

        assertThat(embeddings.documents).containsExactly(
                "Kinetic energy is one half.", "Gravitational potential.", "W = -G M m / r.");
        assertThat(report()).contains("the paragraph's own text_en, and nothing else");
    }

    @Test
    void contextSectionPrefixesTheSectionTitleToWhatIsEmbedded() throws IOException {
        sectionTitles("""
                books:
                  phy11-part1:
                    "6.9": "Moment of inertia"
                    "7": "Gravitation"
                """);

        imports.waiting = List.of(
                new ParagraphToEmbed(UUID.randomUUID(), (short) 6, "6.9", (short) 1,
                        "The kinetic energy of a rotating body is one half I omega squared."),
                new ParagraphToEmbed(UUID.randomUUID(), (short) 7, "7.9", (short) 1,
                        "The gravitational potential energy of a body at a height above the ground."));

        assertThat(run(100, "--context", "section")).isZero();

        assertThat(embeddings.documents).containsExactly(
                "6.9 Moment of inertia · The kinetic energy of a rotating body is one half I omega squared.",
                "7.9 Gravitation · The gravitational potential energy of a body at a height above the ground.");
        assertThat(report()).contains("the section title prefixed");
    }

    /** A sub-section is about its section's subject, so it inherits the title it has no line for. */
    @Test
    void aSectionWithNoTitleOfItsOwnFallsBackToItsParent() {
        NcertSectionTitles titles = NcertSectionTitles.read(
                Path.of("..", "pipeline", "inputs", NcertSectionTitles.FILE), "phy11-part1");

        assertThat(titles.titleFor("5.11.2")).isEqualTo("Collisions in One Dimension");
        assertThat(titles.titleFor("5.11.9")).as("no line of its own: its parent's")
                .isEqualTo("Collisions");
        assertThat(titles.titleFor("9.9")).as("nothing anywhere: embedded bare").isNull();
    }

    /** The committed harvest is the experiment's foundation; a bad one voids the result. */
    @Test
    void theCommittedSectionTitlesCoverTheQueriesTheExperimentTurnsOn() {
        NcertSectionTitles titles = NcertSectionTitles.read(
                Path.of("..", "pipeline", "inputs", NcertSectionTitles.FILE), "phy11-part1");

        assertThat(titles.size()).isGreaterThanOrEqualTo(70);
        assertThat(titles.titleFor("5.3")).isEqualTo("Work");
        assertThat(titles.titleFor("6.9")).isEqualTo("Moment of inertia");
        assertThat(titles.titleFor("4.10")).isEqualTo("Circular motion");
        assertThat(titles.titleFor("1.3.1"))
                .isEqualTo("Rules for Arithmetic Operations with Significant Figures");
        assertThat(titles.embeddingInput("5.3", "(iii) the force and displacement"))
                .isEqualTo("5.3 Work · (iii) the force and displacement");
    }

    @Test
    void contextSectionLeavesFragmentsUnembeddedAndNamesThem() throws IOException {
        sectionTitles("""
                books:
                  phy11-part1:
                    "6.9": "Moment of inertia"
                    "7": "Gravitation"
                """);
        imports.waiting = List.of(
                new ParagraphToEmbed(UUID.randomUUID(), (short) 7, "7.9", (short) 1, "Answer"),
                new ParagraphToEmbed(UUID.randomUUID(), (short) 7, "7.9", (short) 2,
                        "A paragraph long enough to answer a question on its own."));

        assertThat(run(100, "--context", "section")).isZero();

        assertThat(embeddings.documents).as("only the paragraph that could ever be retrieved")
                .containsExactly("7.9 Gravitation · A paragraph long enough to answer a question on its own.");
        assertThat(report()).contains("fragments left unembedded").contains("\"Answer\"");
    }

    /** A book the file does not carry must fail rather than quietly embed bare. */
    @Test
    void contextSectionRefusesABookTheTitlesFileDoesNotCarry() throws IOException {
        sectionTitles("""
                books:
                  bio11:
                    "1.1": "The Living World"
                """);

        assertThat(run(100, "--context", "section")).isEqualTo(1);

        assertThat(embeddings.documents).isEmpty();
        assertThat(report()).contains("carries no section titles for book 'phy11-part1'");
    }

    private void sectionTitles(String yaml) throws IOException {
        Files.writeString(inputs.resolve(NcertSectionTitles.FILE), yaml);
    }

    /** §6.4 pins the canonical text; a `--lang hi` run would silently do nothing. */
    @Test
    void refusesAHindiPassBecauseTheVectorIsOverTheEnglishText() {
        int status = commandLine().execute("ncert", "embed", "--book", "phy11-part1", "--lang", "hi",
                "--queries", NcertEmbedCommand.SKIP_QUERIES,
                "--inputs", inputs.toString(), "--reports", reports.toString());

        assertThat(status).isEqualTo(1);
        assertThat(embeddings.documents).isEmpty();
        assertThat(report()).contains("no --lang hi pass");
    }

    private int run() {
        return run(100);
    }

    private int run(int batchSize) {
        return run(batchSize, new String[0]);
    }

    /** Hermetic by default: the committed query set is {@link RetrievalRunTest}'s subject. */
    private int run(int batchSize, String... extra) {
        List<String> args = new ArrayList<>(List.of("ncert", "embed", "--book", "phy11-part1",
                "--queries", NcertEmbedCommand.SKIP_QUERIES,
                "--inputs", inputs.toString(), "--reports", reports.toString()));
        args.addAll(List.of(extra));
        return commandLine(batchSize).execute(args.toArray(String[]::new));
    }

    private CommandLine commandLine() {
        return commandLine(100);
    }

    private CommandLine commandLine(int batchSize) {
        Reports writer = new Reports(ReportTest.CLOCK);
        PipelineProperties properties = new PipelineProperties(72, 10, 1, "claude-sonnet-5", batchSize, 0, 40);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(
                new PipelineCommandTest.RecordingImport(), writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertEmbedCommand.class) {
                    return cls.cast(new NcertEmbedCommand(imports, embeddings, noRetriever(), spend, client,
                            properties, writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        return PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    private String report() {
        return out.toString();
    }

    /**
     * A real retriever over an empty corpus: the command's query run is exercised for its wiring
     * and its report, while what retrieval actually returns is {@link HybridRetrieverTest}'s
     * subject and the founder's scored run's.
     */
    static HybridRetriever noRetriever() {
        return new HybridRetriever(new ParagraphRetrievalRepository() {
            @Override
            public List<com.margai.curriculum.api.ParagraphMatch> nearestByEmbedding(float[] query,
                    com.margai.curriculum.api.BookSubject subject, UUID nodeId, int limit) {
                return List.of();
            }

            @Override
            public List<com.margai.curriculum.api.ParagraphMatch> matchingText(String query,
                    com.margai.curriculum.api.BookSubject subject, UUID nodeId, int limit) {
                return List.of();
            }
        }, new StubEmbeddings(), TestAiProperties.standard());
    }

    /** Records what was embedded and on which side of the space, and answers a 1,024-wide vector. */
    static final class StubEmbeddings implements EmbeddingService {

        final List<String> documents = new ArrayList<>();
        final List<String> queries = new ArrayList<>();
        /** Which call refuses, 1-based; 0 never refuses. */
        int failOnCall;

        @Override
        public float[] ofDocument(String text, AiCallContext ctx) {
            if (failOnCall > 0 && documents.size() + 1 == failOnCall) {
                throw com.margai.ai.api.AiUnavailableException.retryable("HTTP_429",
                        "HTTP_429 from the embedding provider", null);
            }
            documents.add(text);
            return vector(documents.size());
        }

        @Override
        public float[] ofQuery(String text, AiCallContext ctx) {
            queries.add(text);
            return vector(queries.size());
        }

        private static float[] vector(int seed) {
            float[] values = new float[1024];
            values[0] = seed;
            return values;
        }
    }

    /** The curriculum door as `ncert embed` uses it: what is waiting, and what was stored when. */
    static final class EmbeddingImport extends PipelineCommandTest.RecordingImport {

        List<ParagraphToEmbed> waiting = List.of();
        final List<ParagraphEmbedding> stored = new ArrayList<>();
        final List<Integer> batchSizes = new ArrayList<>();
        final List<Collection<Short>> chaptersAsked = new ArrayList<>();

        @Override
        public List<ParagraphToEmbed> paragraphsToEmbed(String bookCode, Collection<Short> chapters, boolean redo) {
            chaptersAsked.add(List.copyOf(chapters));
            // Second call of the run: the report's "still without a vector" line, after storing.
            return waiting.stream().filter(paragraph -> stored.stream()
                    .noneMatch(embedding -> embedding.paragraphId().equals(paragraph.paragraphId()))).toList();
        }

        @Override
        public int storeEmbeddings(String bookCode, List<ParagraphEmbedding> embeddings) {
            if (!embeddings.isEmpty()) {
                batchSizes.add(embeddings.size());
            }
            stored.addAll(embeddings);
            return embeddings.size();
        }

        @Override
        public List<NcertParagraphRow> paragraphs(String bookCode, BookLanguage language, Collection<Short> chapters) {
            return List.of();
        }
    }
}
