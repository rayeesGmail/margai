package com.margai.ai.retrieval;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.internal.TestAiProperties;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.ParagraphMatch;
import com.margai.curriculum.api.ParagraphRetrievalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * The fusion half of hybrid retrieval, with a stub repository in place of the SQL: rank fusion
 * rather than score fusion, one passage per paragraph however many halves found it, the token cap,
 * the similarity floor, and §4.3 stage 6's retry without the node filter before grounding is
 * declared failed.
 */
class HybridRetrieverTest {

    private static final AiCallContext CTX = AiCallContext.system("test");
    private static final UUID NODE = UUID.randomUUID();

    private final StubRepository repository = new StubRepository();
    private final StubEmbeddings embeddings = new StubEmbeddings();

    @Test
    void fusesTheTwoHalvesByRankSoTheTextHalfsUnboundedScoreCannotDominate() {
        // The text half's ts_rank is two orders of magnitude larger than any cosine similarity.
        // Summed, it would decide the order by itself; fused by rank it cannot.
        ParagraphMatch both = paragraph("7.9", 1, 0.62);
        ParagraphMatch textOnly = paragraph("7.1", 2, 95.0);
        repository.vector = List.of(both);
        repository.text = List.of(textOnly, both.withScore(80.0));

        List<RetrievedPassages.Passage> passages = retrieve().passages();

        assertThat(passages).extracting(passage -> passage.paragraph().section())
                .as("found by both halves, so it outranks the text half's top hit")
                .containsExactly("7.9", "7.1");
        assertThat(passages.getFirst().foundBy()).isEqualTo("both");
        assertThat(passages.get(1).foundBy()).isEqualTo("text");
    }

    @Test
    void oneParagraphFoundByBothHalvesIsOnePassageCarryingBothRanks() {
        ParagraphMatch match = paragraph("7.9", 1, 0.62);
        repository.vector = List.of(match);
        repository.text = List.of(match.withScore(12.0));

        List<RetrievedPassages.Passage> passages = retrieve().passages();

        assertThat(passages).hasSize(1);
        assertThat(passages.getFirst().vectorRank()).isEqualTo(1);
        assertThat(passages.getFirst().textRank()).isEqualTo(1);
        assertThat(passages.getFirst().similarity()).isEqualTo(0.62);
    }

    /** The query goes to the provider as a query, never as a stored document (§4.9). */
    @Test
    void embedsTheQuestionOnTheQuerySideOfTheSpace() {
        repository.vector = List.of(paragraph("7.9", 1, 0.62));

        retrieve();

        assertThat(embeddings.queries).containsExactly("why does gravity hold us down");
        assertThat(embeddings.documents).isEmpty();
    }

    /**
     * A keyword coincidence is not grounding. Every question contains some word that appears
     * somewhere in NCERT, so if a full-text hit could satisfy the floor, R2 would be unfalsifiable.
     */
    @Test
    void aTextOnlyMatchIsNotGroundingHoweverWellItScores() {
        repository.vector = List.of();
        repository.text = List.of(paragraph("7.1", 1, 99.0));

        RetrievedPassages retrieved = retrieve();

        assertThat(retrieved.grounded()).isFalse();
        assertThat(retrieved.passages()).isEmpty();
    }

    @Test
    void aVectorMatchBelowTheFloorIsNotGrounding() {
        repository.vector = List.of(paragraph("7.9", 1, 0.29));

        assertThat(retrieve().grounded()).isFalse();
    }

    @Test
    void aVectorMatchAtTheFloorIsGrounding() {
        repository.vector = List.of(paragraph("7.9", 1, 0.30));

        assertThat(retrieve().grounded()).isTrue();
    }

    /**
     * §4.3 stage 6: the guessed node is the likeliest thing to be wrong, and refusing to answer
     * because a guess was wrong would charge the router's mistake to the student.
     */
    @Test
    void retriesWithoutTheNodeFilterBeforeDeclaringAGroundingFailure() {
        repository.underNode = List.of();
        repository.vector = List.of(paragraph("7.9", 1, 0.62));

        RetrievedPassages retrieved = retriever().retrieve("why does gravity hold us down",
                BookSubject.physics, NODE, CTX);

        assertThat(retrieved.grounded()).isTrue();
        assertThat(retrieved.nodeFilterDropped()).isTrue();
        assertThat(repository.nodesAsked).containsExactly(NODE, NODE, null, null);
    }

    @Test
    void aGroundingFailureUnderNoNodeFilterIsNotRetried() {
        repository.vector = List.of();
        repository.text = List.of();

        RetrievedPassages retrieved = retrieve();

        assertThat(retrieved.grounded()).isFalse();
        assertThat(retrieved.nodeFilterDropped()).isFalse();
        assertThat(repository.nodesAsked).containsExactly(null, null);
    }

    /**
     * A passage that would overflow the cap is skipped, not truncated — half a paragraph is a
     * misquotation of NCERT — and the next, smaller one is still considered.
     */
    @Test
    void theTokenCapSkipsAnOversizedPassageAndKeepsConsideringTheRest() {
        repository.vector = List.of(
                paragraph("7.9", 1, 0.90),
                withText(paragraph("7.10", 2, 0.80), "x".repeat(4 * 2600)),
                paragraph("7.11", 3, 0.70));

        List<RetrievedPassages.Passage> passages = retrieve().passages();

        assertThat(passages).extracting(passage -> passage.paragraph().section())
                .containsExactly("7.9", "7.11");
    }

    /** The corpus either holds the answer or it does not; the prompt's budget is a later question. */
    @Test
    void groundingIsJudgedBeforeTheTokenCapNotAfterIt() {
        repository.vector = List.of(withText(paragraph("7.9", 1, 0.90), "x".repeat(4 * 2600)));

        RetrievedPassages retrieved = retrieve();

        assertThat(retrieved.grounded()).isTrue();
        assertThat(retrieved.passages()).as("grounded, but nothing fits this prompt").isEmpty();
    }

    @Test
    void asksEachHalfForItsConfiguredNumberOfCandidates() {
        repository.vector = List.of(paragraph("7.9", 1, 0.62));

        retrieve();

        assertThat(repository.limitsAsked).containsExactly(8, 8);
    }

    private RetrievedPassages retrieve() {
        return retriever().retrieve("why does gravity hold us down", BookSubject.physics, null, CTX);
    }

    private HybridRetriever retriever() {
        return new HybridRetriever(repository, embeddings, TestAiProperties.standard());
    }

    private static ParagraphMatch paragraph(String section, int paraNo, double score) {
        return new ParagraphMatch(UUID.randomUUID(), "phy11-part1", (short) 7, section, (short) paraNo,
                "The gravitational potential energy of a body.", null, score);
    }

    private static ParagraphMatch withText(ParagraphMatch match, String text) {
        return new ParagraphMatch(match.paragraphId(), match.bookCode(), match.chapterNo(), match.section(),
                match.paraNo(), text, null, match.score());
    }

    /** Answers the two halves, and records the node filter and limit each call carried. */
    static final class StubRepository implements ParagraphRetrievalRepository {

        List<ParagraphMatch> vector = List.of();
        List<ParagraphMatch> text = List.of();
        /** What both halves answer while a node filter is in force; null means "the usual answer". */
        List<ParagraphMatch> underNode;
        final List<UUID> nodesAsked = new ArrayList<>();
        final List<Integer> limitsAsked = new ArrayList<>();

        @Override
        public List<ParagraphMatch> nearestByEmbedding(float[] query, BookSubject subject, UUID nodeId, int limit) {
            nodesAsked.add(nodeId);
            limitsAsked.add(limit);
            return nodeId != null && underNode != null ? underNode : vector;
        }

        @Override
        public List<ParagraphMatch> matchingText(String query, BookSubject subject, UUID nodeId, int limit) {
            nodesAsked.add(nodeId);
            limitsAsked.add(limit);
            return nodeId != null && underNode != null ? underNode : text;
        }
    }

    static final class StubEmbeddings implements EmbeddingService {

        final List<String> documents = new ArrayList<>();
        final List<String> queries = new ArrayList<>();

        @Override
        public float[] ofDocument(String text, AiCallContext ctx) {
            documents.add(text);
            return new float[1024];
        }

        @Override
        public float[] ofQuery(String text, AiCallContext ctx) {
            queries.add(text);
            return new float[1024];
        }
    }
}
