package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.TestAiProperties;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.tasks.QueryRewriter;
import com.margai.ai.tasks.SearchQuery;
import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.ParagraphMatch;
import com.margai.curriculum.api.ParagraphRetrievalRepository;
import com.margai.curriculum.api.ParagraphToEmbed;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert retrieve} over a stub corpus: each query is filtered to its own subject and the
 * acceptance set runs once more with no filter, the corpus is reported before the scores, a miss is
 * shown beside the text it was expected to reach, and the fake client is refused before any query
 * is embedded. What retrieval returns on the real corpus is the founder's run's subject.
 */
class NcertRetrieveCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final NcertEmbedCommandTest.StubEmbeddings embeddings = new NcertEmbedCommandTest.StubEmbeddings();
    private final StubCorpus corpus = new StubCorpus();
    private final CorpusImport imports = new CorpusImport();
    private final StubRewriter rewriter = new StubRewriter();
    private AiClientInfo client = new AiClientInfo("cohere", List.of("ledger"));
    private Path queries;

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
                    chapters:
                      - {no: 7, en: keph107.pdf}
                  - code: bio11
                    subject: biology
                    class_level: 11
                    title_en: "Biology, Textbook for Class XI"
                    edition_year: 2023
                    source:
                      en: source/ncert/2022-ed/en/bio11/
                    chapters:
                      - {no: 1, en: kebo101.pdf}
                """);
        queries = inputs.resolve("retrieval-queries.json");
        Files.writeString(queries, """
                {"queries": [
                  {"id": "a1", "set": "acceptance", "subject": "physics", "language": "en", "text": "orbit energy",
                   "expect": [{"book": "phy11-part1", "chapter": 7, "section": "7.9", "para": 1}], "note": "n"},
                  {"id": "a2", "set": "acceptance", "subject": "biology", "language": "hi", "text": "कोशिका क्या है",
                   "expect": [{"book": "bio11", "chapter": 1, "section": "1.1", "para": 1}], "note": "n"},
                  {"id": "b1", "set": "baseline", "subject": "physics", "language": "en", "text": "escape speed",
                   "expect": [{"book": "phy11-part1", "chapter": 7, "section": "7.9", "para": 1}], "note": "n"}]}""");
    }

    /** §4.3 stage 6 filters by subject; the founder's diagnostic runs the acceptance set without one. */
    @Test
    void filtersEachQueryToItsSubjectThenRunsTheAcceptanceSetUnfiltered() {
        assertThat(run()).isZero();

        assertThat(corpus.subjects).containsExactly(BookSubject.physics, BookSubject.biology, BookSubject.physics,
                null, null);
        assertThat(embeddings.queries).containsExactly("orbit energy", "कोशिका क्या है", "escape speed",
                "orbit energy", "कोशिका क्या है");
        assertThat(report()).contains("acceptance — no subject filter")
                .contains("baseline — D15's fifteen over the whole corpus")
                .contains("3 concept queries — 2 acceptance, 1 baseline, 0 diagnostic, 0 heldout");
    }

    /** A two-query set cannot meet a bar written for fifteen, however it scores. */
    @Test
    void readsTheBarFromTheFilteredAcceptanceRun() {
        assertThat(run()).isZero();

        assertThat(report()).contains("the D17 bar (founder, DECISIONS 2026-10-18) — NOT MET")
                .contains("| in the answer set | ≥ 13 of 15 | 1 of 2 | no |")
                .contains("| every Hindi query | all | 0 of 1 | no |");
    }

    @Test
    void showsAMissBesideTheTextItWasExpectedToReach() {
        assertThat(run()).isZero();

        assertThat(report()).contains("misses — what was expected, and what came back (1)")
                .contains("expected bio11 ch 1 §1.1 ¶1 · Living things grow.")
                .contains("bio11 ch 1 §1.1 ¶2");
    }

    /** A row without a vector is invisible to the vector half: a reason to miss that is not retrieval's. */
    @Test
    void reportsTheCorpusItSearched() {
        imports.waiting = 4;

        assertThat(run()).isZero();

        assertThat(report()).contains("8 English paragraph(s) WITHOUT a vector")
                .contains("| phy11-part1 | physics | 10 | 4 |");
    }

    @Test
    void refusesTheFakeClientBeforeEmbeddingAnyQuery() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));

        assertThat(run()).isEqualTo(1);

        assertThat(embeddings.queries).isEmpty();
        assertThat(report()).contains("the AI client is the fake");
    }

    @Test
    void refusesAnAddressInABookOfAnotherSubjectBeforeEmbeddingAnyQuery() throws IOException {
        Files.writeString(queries, """
                {"queries": [{"id": "a1", "set": "acceptance", "subject": "physics", "language": "en", "text": "x",
                  "expect": [{"book": "bio11", "chapter": 1, "section": "1.1", "para": 1}], "note": "n"}]}""");

        assertThat(run()).isEqualTo(1);

        assertThat(embeddings.queries).isEmpty();
        assertThat(report()).contains("bio11 is biology");
    }

    @Test
    void aMissingQueryFileIsAUsageError() {
        int status = commandLine().execute("ncert", "retrieve", "--queries", inputs.resolve("absent.json").toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString());

        assertThat(status).isEqualTo(2);
        assertThat(embeddings.queries).isEmpty();
    }

    // ── D18: --rewrite, the question fused with its English search version (DECISIONS 2026-10-19) ──

    /** Every non-English query is rewritten, and the English acceptance ones for the diagnostic; not the English baseline. */
    @Test
    void rewritesTheNonEnglishQueriesAndTheEnglishAcceptanceOnes() {
        assertThat(runRewrite()).isZero();

        assertThat(rewriter.questions).containsExactly("orbit energy", "कोशिका क्या है");
        assertThat(report()).contains("ncert retrieve rewrite")
                .contains("the search versions the model wrote")
                .contains("SV: कोशिका क्या है");
    }

    /** The Biology Hindi query misses on its own text and is reached through its search version, fused. */
    @Test
    void readsD17sTickFromTheFusedRunWithTheUnfusedBesideIt() {
        corpus.reachedBySearchVersion = true;

        assertThat(runRewrite()).isZero();

        assertThat(report()).contains("D17's tick, fused (founder, DECISIONS 2026-10-19) — NOT MET")
                .contains("| every Hindi query | all | 1 of 1 | yes |")
                .contains("the same checks, unfused (for comparison)")
                .contains("| every Hindi query | all | 0 of 1 | no |")
                .contains("| held-out queries | ≥ 3 of 4 | 0 of 0 | no |");
    }

    /**
     * The English acceptance queries rewritten too are scored apart, and never enter the bar: here the
     * Physics query is reached only through its search version, so the diagnostic counts it and the fused
     * tick does not.
     */
    @Test
    void scoresTheRewrittenEnglishAcceptanceQueriesApartFromTheBar() {
        corpus.physicsOnlyBySearchVersion = true;

        assertThat(runRewrite()).isZero();

        assertThat(report()).contains("the English acceptance queries rewritten too (founder's diagnostic, not part of the bar)")
                .contains("| rewritten and fused | all | 1 | 1/1 (100%) |")
                .contains("| unfused | all | 1 | 0/1 (0%) |");
        assertThat(report().substring(report().indexOf("D17's tick, fused"), report().indexOf("the same checks, unfused")))
                .contains("| in the answer set | ≥ 13 of 15 | 0 of 2 | no |");
    }

    /** Grounding in each mode beside the ranks, for the founder to see which queries a search version alone grounds. */
    @Test
    void showsGroundingInEachModeQueryByQuery() {
        assertThat(runRewrite()).isZero();

        assertThat(report()).contains("| grounded unfused | grounded fused |")
                .as("the Biology Hindi query misses in both modes but is grounded in both, at similarity 0.8")
                .contains("| a2 | acceptance | biology | hi | miss | miss | yes | yes |");
    }

    @Test
    void withoutRewriteNothingIsRewritten() {
        assertThat(run()).isZero();

        assertThat(rewriter.questions).isEmpty();
        assertThat(report()).doesNotContain("D17's tick, fused");
    }

    private int run() {
        return commandLine().execute("ncert", "retrieve", "--queries", queries.toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    private int runRewrite() {
        return commandLine().execute("ncert", "retrieve", "--rewrite", "--queries", queries.toString(),
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    /** Answers "SV: " and the question, and records what it was asked to rewrite. */
    static final class StubRewriter implements QueryRewriter {

        final List<String> questions = new ArrayList<>();

        @Override
        public AiResponse<SearchQuery> run(String question, AiCallContext ctx) {
            questions.add(question);
            return new AiResponse<>(new SearchQuery("SV: " + question), new Usage(10, 5, 0, 0), "stub",
                    Duration.ZERO, UUID.randomUUID());
        }
    }

    private CommandLine commandLine() {
        Reports writer = new Reports(ReportTest.CLOCK);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertRetrieveCommand.class) {
                    return cls.cast(new NcertRetrieveCommand(imports,
                            new HybridRetriever(corpus, embeddings, TestAiProperties.standard()), rewriter,
                            new NcertExtractCommandTest.StubSpend(), client, writer));
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
     * Physics finds its expected paragraph; Biology finds the neighbouring one, so its Hindi query
     * misses. Every call records the subject it was filtered to.
     */
    static final class StubCorpus implements ParagraphRetrievalRepository {

        final List<BookSubject> subjects = new ArrayList<>();
        /** When set, the text half finds the Biology query's expected paragraph for a search version only. */
        boolean reachedBySearchVersion;
        /** When set, the Physics query's expected paragraph comes back only for its search version. */
        boolean physicsOnlyBySearchVersion;

        @Override
        public List<ParagraphMatch> nearestByEmbedding(float[] query, BookSubject subject, UUID nodeId, int limit) {
            subjects.add(subject);
            if (subject == BookSubject.biology) {
                return List.of(match("bio11", 1, "1.1", 2, "Cells divide."));
            }
            if (physicsOnlyBySearchVersion) {
                return List.of(match("phy11-part1", 7, "7.9", 2, "A neighbouring paragraph."));
            }
            return List.of(match("phy11-part1", 7, "7.9", 1, "Energy of an orbiting satellite."));
        }

        @Override
        public List<ParagraphMatch> matchingText(String query, BookSubject subject, UUID nodeId, int limit) {
            if (reachedBySearchVersion && subject == BookSubject.biology && query.startsWith("SV: ")) {
                return List.of(match("bio11", 1, "1.1", 1, "Living things grow."));
            }
            if (physicsOnlyBySearchVersion && subject == BookSubject.physics && query.startsWith("SV: ")) {
                return List.of(match("phy11-part1", 7, "7.9", 1, "Energy of an orbiting satellite."));
            }
            return List.of();
        }

        private static ParagraphMatch match(String book, int chapter, String section, int para, String text) {
            return new ParagraphMatch(UUID.randomUUID(), book, (short) chapter, section, (short) para, text, null, 0.8);
        }
    }

    /** The curriculum door as the command reads it: the corpus counts and the rows a miss expected. */
    static final class CorpusImport extends PipelineCommandTest.RecordingImport {

        int waiting;

        @Override
        public long embeddedParagraphs(String bookCode) {
            return 10;
        }

        @Override
        public List<ParagraphToEmbed> paragraphsToEmbed(String bookCode, Collection<Short> chapters, boolean redo) {
            List<ParagraphToEmbed> rows = new ArrayList<>();
            for (int index = 0; index < waiting; index++) {
                rows.add(new ParagraphToEmbed(UUID.randomUUID(), (short) 1, "1.1", (short) (index + 1), "text"));
            }
            return rows;
        }

        @Override
        public List<AlignmentRow> alignmentRows(String bookCode, Collection<Short> chapters) {
            return bookCode.equals("bio11")
                    ? List.of(new AlignmentRow(UUID.randomUUID(), (short) 1, "1.1", (short) 1, "Living things grow.",
                            null, List.of(1), List.of()))
                    : List.of();
        }
    }
}
