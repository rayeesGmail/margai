package com.margai.ai.retrieval;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.ParagraphMatch;
import com.margai.curriculum.api.ParagraphRetrievalRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The one retrieval component (TECH_PLAN §4.3 stage 6, §4.9): a question in, NCERT passages out.
 * Used by the doubt pipeline from D37, by {@code ncert embed}'s query run today, and by D23's
 * anchor linking.
 *
 * <p>Hybrid because each half has a known blind spot and they are not the same blind spot. The
 * vector half cannot see "Eq. (7.35)" — a citation carries almost no meaning to embed. The text
 * half cannot see "why does a bucket of water not fall when swung overhead", because the book
 * never uses those words. Either alone loses a class of question that the other answers easily.
 *
 * <p>They are fused by <strong>rank</strong>, not by score, and that is not a stylistic choice:
 * cosine similarity lives in 0..1 and {@code ts_rank} is unbounded, so adding or averaging them
 * would let the text half's scale silently dominate. Reciprocal-rank fusion sums
 * {@code 1 / (rrfK + rank)} over the halves that found a paragraph, which asks only that each half
 * can order its own results.
 *
 * <p>The floor is applied to vector similarity alone. A full-text hit is evidence that the words
 * appear; it is not evidence that the passage is about the question, and letting a keyword
 * coincidence satisfy the grounding rule would make R2 ("no AI answer without retrieval
 * grounding") unfalsifiable — every query contains some word that appears somewhere in NCERT.
 */
@Component
public class HybridRetriever {

    private final ParagraphRetrievalRepository paragraphs;
    private final EmbeddingService embeddings;
    private final AiProperties.Retrieval config;

    public HybridRetriever(ParagraphRetrievalRepository paragraphs, EmbeddingService embeddings,
            AiProperties properties) {
        this.paragraphs = paragraphs;
        this.embeddings = embeddings;
        this.config = properties.retrieval();
    }

    /**
     * Retrieve for one question. When a node filter is given and nothing above the floor comes
     * back under it, the search is retried without it before grounding is declared failed — the
     * guessed node is the likeliest thing to be wrong, and refusing to answer because a guess was
     * wrong would be the router's mistake charged to the student (§4.3 stage 6).
     *
     * @param subject the subject to stay within, or null for every book
     * @param nodeId  the guessed syllabus node (D23 anchors), or null
     */
    public RetrievedPassages retrieve(String question, BookSubject subject, UUID nodeId, AiCallContext ctx) {
        float[] query = embeddings.ofQuery(question, ctx);
        RetrievedPassages first = search(question, query, subject, nodeId, false);
        if (first.grounded() || nodeId == null) {
            return first;
        }
        return search(question, query, subject, null, true);
    }

    private RetrievedPassages search(String question, float[] query, BookSubject subject, UUID nodeId,
            boolean nodeFilterDropped) {
        List<ParagraphMatch> byVector = paragraphs.nearestByEmbedding(query, subject, nodeId, config.kVector());
        List<ParagraphMatch> byText = paragraphs.matchingText(question, subject, nodeId, config.kText());

        Map<UUID, Fusing> fusing = new LinkedHashMap<>();
        rank(byVector, fusing, true);
        rank(byText, fusing, false);

        List<RetrievedPassages.Passage> fused = fusing.values().stream()
                .map(Fusing::passage)
                .sorted((left, right) -> Double.compare(right.fusedScore(), left.fusedScore()))
                .toList();

        // Grounding is judged on everything retrieved, before the token cap: a corpus that holds
        // the answer still holds it when the cap keeps the passage out of this particular prompt.
        boolean grounded = fused.stream().anyMatch(passage -> passage.similarity() != null
                && passage.similarity() >= config.similarityFloor().doubleValue());
        if (!grounded) {
            return RetrievedPassages.groundingFailure(nodeFilterDropped);
        }
        return new RetrievedPassages(underTokenCap(fused), nodeFilterDropped, true);
    }

    /** Deduplicated by paragraph as it goes: one paragraph found by both halves is one passage. */
    private void rank(List<ParagraphMatch> half, Map<UUID, Fusing> fusing, boolean vector) {
        for (int index = 0; index < half.size(); index++) {
            ParagraphMatch match = half.get(index);
            int rank = index + 1;
            Fusing entry = fusing.computeIfAbsent(match.paragraphId(), id -> new Fusing(match));
            entry.add(rank, vector ? match.score() : null, vector);
            entry.score += 1.0 / (config.rrfK() + rank);
        }
    }

    /**
     * Passages in fused order until the cap is reached, estimated at ~4 characters per token — the
     * same estimate §4.11's cache tripwire uses, and for the same reason: the real count is the
     * provider's and is not knowable here. A passage that would overflow the cap is skipped rather
     * than truncated (half a paragraph is a misquotation of NCERT) and the next, smaller one is
     * still considered.
     */
    private List<RetrievedPassages.Passage> underTokenCap(List<RetrievedPassages.Passage> fused) {
        List<RetrievedPassages.Passage> kept = new ArrayList<>();
        int tokens = 0;
        for (RetrievedPassages.Passage passage : fused) {
            String text = passage.paragraph().text();
            int estimate = text == null ? 0 : Math.max(1, text.length() / 4);
            if (tokens + estimate > config.tokenCap()) {
                continue;
            }
            kept.add(passage);
            tokens += estimate;
        }
        return kept;
    }

    /** One paragraph's accumulating fusion state: the ranks each half gave it, and the sum. */
    private static final class Fusing {

        private final ParagraphMatch match;
        private double score;
        private Integer vectorRank;
        private Integer textRank;
        private Double similarity;

        private Fusing(ParagraphMatch match) {
            this.match = match;
        }

        private void add(int rank, Double similarity, boolean vector) {
            if (vector) {
                this.vectorRank = rank;
                this.similarity = similarity;
            } else {
                this.textRank = rank;
            }
        }

        private RetrievedPassages.Passage passage() {
            return new RetrievedPassages.Passage(match, score, vectorRank, textRank, similarity);
        }
    }
}
