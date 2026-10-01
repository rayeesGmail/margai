package com.margai.curriculum.api;

import java.util.List;
import java.util.UUID;

/**
 * The SQL half of hybrid retrieval (TECH_PLAN §1.3, §4.3 stage 6, §4.9). It lives in
 * {@code curriculum.api} and not in the {@code ai} module for a module-boundary reason worth
 * stating: {@code ncert_paragraphs} is the curriculum module's table, and a module owns its
 * tables. So the queries live with the table and {@code ai.retrieval.HybridRetriever} composes and
 * fuses their results — one component split across two modules, deliberately.
 *
 * <p>Both halves take the same filters, because a hybrid retriever that filtered one half and not
 * the other would return a fused list whose two sources disagreed about what was eligible.
 */
public interface ParagraphRetrievalRepository {

    /**
     * Nearest paragraphs by cosine distance over {@code embedding}, closest first, scored as
     * similarity in 0..1. Rows with no vector cannot match — they are simply not in the index —
     * which is why {@code ncert embed} leaving paragraphs behind is a reported number and not a
     * silent one.
     *
     * @param subject only books of this subject, or every book when null
     * @param nodeId  only paragraphs anchored to this syllabus node (D23), or every one when null
     */
    List<ParagraphMatch> nearestByEmbedding(float[] query, BookSubject subject, UUID nodeId, int limit);

    /**
     * Full-text matches over the generated {@code tsv}, best first, scored by {@code ts_rank}. The
     * column carries English stemmed and Hindi unstemmed in one vector (V7), so one query reaches
     * both editions — and a query is parsed with {@code websearch_to_tsquery}, which takes a
     * student's words as typed instead of demanding operators and throwing on a stray character.
     */
    List<ParagraphMatch> matchingText(String query, BookSubject subject, UUID nodeId, int limit);
}
