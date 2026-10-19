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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 * <p>A question may come with an English search version of itself (D18, DECISIONS 2026-10-19): the
 * halves then run for both texts and the four lists fuse the same way, never the rewrite alone.
 */
@Component
public class HybridRetriever {

    private static final Logger log = LoggerFactory.getLogger(HybridRetriever.class);

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
        return retrieve(question, null, subject, nodeId, ctx);
    }

    /**
     * Retrieve for one question and, when given, its English textbook-style search version (founder
     * ruling 2026-10-18, DECISIONS 2026-10-19). Both halves run for the question and for the search
     * version, and all four ranked lists are fused by the same reciprocal-rank fusion — never the
     * search version alone, so a rewrite that drifts can add passages but cannot take the question's
     * own away. A paragraph several lists found keeps its best rank in each half and its highest
     * similarity, and grounding is judged on that highest similarity — so a search version alone can
     * ground a question whose own vector is under the floor (Claude's choice, DECISIONS 2026-10-19, for
     * the founder to overrule). A null or blank search version is the unfused retrieval, exactly.
     *
     * @param searchVersion the question rewritten for the textbooks ({@code query_rewrite}), or null
     */
    public RetrievedPassages retrieve(String question, String searchVersion, BookSubject subject, UUID nodeId,
            AiCallContext ctx) {
        List<Probe> probes = new ArrayList<>();
        probes.add(new Probe(question, embeddings.ofQuery(question, ctx)));
        if (searchVersion != null && !searchVersion.isBlank()) {
            probes.add(new Probe(searchVersion, embeddings.ofQuery(searchVersion, ctx)));
        }
        RetrievedPassages first = search(probes, subject, nodeId, false);
        RetrievedPassages result = first.grounded() || nodeId == null ? first : search(probes, subject, null, true);
        // Every retrieval, in production as in the pipeline (founder ruling, DECISIONS 2026-10-19): which
        // text grounded it, since a search version alone can ground a question.
        log.info("retrieval request_id={} grounded_via={} search_version={} passages={} node_filter_dropped={}",
                ctx.requestId(), result.groundedVia(), probes.size() > 1, result.passages().size(),
                result.nodeFilterDropped());
        return result;
    }

    /** One text retrieval runs for, with its query-side vector. */
    private record Probe(String text, float[] vector) {
    }

    private RetrievedPassages search(List<Probe> probes, BookSubject subject, UUID nodeId, boolean nodeFilterDropped) {
        Map<UUID, Fusing> fusing = new LinkedHashMap<>();
        // Which text's own vector cleared the floor: the question's is probe 0, the search version's probe 1.
        boolean[] cleared = new boolean[probes.size()];
        for (int index = 0; index < probes.size(); index++) {
            Probe probe = probes.get(index);
            List<ParagraphMatch> byVector = paragraphs.nearestByEmbedding(probe.vector(), subject, nodeId,
                    config.kVector());
            cleared[index] = byVector.stream()
                    .anyMatch(match -> match.score() >= config.similarityFloor().doubleValue());
            rank(byVector, fusing, true);
            rank(paragraphs.matchingText(probe.text(), subject, nodeId, config.kText()), fusing, false);
        }

        List<RetrievedPassages.Passage> fused = fusing.values().stream()
                .map(Fusing::passage)
                .sorted((left, right) -> Double.compare(right.fusedScore(), left.fusedScore()))
                .toList();

        // Grounding is judged on everything retrieved, before the token cap: a corpus that holds
        // the answer still holds it when the cap keeps the passage out of this particular prompt. A
        // passage's similarity is the highest any vector list gave it, so this is "some text's vector
        // cleared the floor".
        RetrievedPassages.GroundedVia via = groundedVia(cleared);
        if (via == RetrievedPassages.GroundedVia.none) {
            return RetrievedPassages.groundingFailure(nodeFilterDropped);
        }
        return new RetrievedPassages(underTokenCap(fused), nodeFilterDropped, true, via);
    }

    private static RetrievedPassages.GroundedVia groundedVia(boolean[] cleared) {
        boolean question = cleared[0];
        boolean searchVersion = cleared.length > 1 && cleared[1];
        if (question && searchVersion) {
            return RetrievedPassages.GroundedVia.both;
        }
        if (question) {
            return RetrievedPassages.GroundedVia.question;
        }
        return searchVersion ? RetrievedPassages.GroundedVia.search_version : RetrievedPassages.GroundedVia.none;
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

        /** The best rank each half gave it and the highest similarity, over every list that found it. */
        private void add(int rank, Double similarity, boolean vector) {
            if (vector) {
                this.vectorRank = vectorRank == null ? rank : Math.min(vectorRank, rank);
                this.similarity = this.similarity == null ? similarity : Math.max(this.similarity, similarity);
            } else {
                this.textRank = textRank == null ? rank : Math.min(textRank, rank);
            }
        }

        private RetrievedPassages.Passage passage() {
            return new RetrievedPassages.Passage(match, score, vectorRank, textRank, similarity);
        }
    }
}
