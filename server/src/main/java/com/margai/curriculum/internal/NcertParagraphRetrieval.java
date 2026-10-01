package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.ParagraphMatch;
import com.margai.curriculum.api.ParagraphRetrievalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link ParagraphRetrievalRepository} over {@code ncert_paragraphs} (TECH_PLAN §4.3 stage 6,
 * §4.9). Parameterised native SQL throughout, which §8 sanctions for exactly this: neither
 * pgvector's {@code <=>} nor {@code websearch_to_tsquery} has a JPQL spelling.
 *
 * <p>The vector query orders by the operator the index understands rather than by the score it
 * returns — {@code ORDER BY embedding <=> query} can use V8's HNSW index, while ordering by the
 * {@code 1 - distance} alias computed from it cannot, and would quietly become a full scan of the
 * corpus as it grows.
 *
 * <p>Every optional filter is written {@code CAST(:x AS type) IS NULL OR …}: a bare parameter in
 * an {@code IS NULL} test gives PostgreSQL nothing to infer a type from, and the cast is what
 * keeps "no subject filter" expressible as a null rather than as a second query.
 */
@Repository
@Transactional(readOnly = true)
class NcertParagraphRetrieval implements ParagraphRetrievalRepository {

    private static final String COLUMNS =
            "p.id, b.code, p.chapter_no, p.section, p.para_no, p.text_en, p.text_hi";

    /**
     * The tsv holds English stemmed and Hindi unstemmed in one vector (V7), so the query is parsed
     * both ways and OR'd: the english parse reaches {@code text_en}'s stems, the simple parse
     * reaches {@code text_hi}'s tokens. Parsing one way only would leave the other edition
     * unreachable by the half of retrieval that exists to catch what embeddings miss.
     *
     * <p><strong>The terms are then OR'd rather than AND'd</strong> (founder decision 2026-09-20,
     * DECISIONS; TECH_PLAN §4.3 stage 6 amended). {@code websearch_to_tsquery} joins bare terms
     * with {@code &}, so a paragraph had to contain <em>every</em> content word of the question:
     * measured over phy11-part1's 894 verified paragraphs, that found the expected paragraph for
     * 1 of the 15 concept queries, against 6 when OR'd, and made the full-text half return nothing
     * at all for 13 of them. An all-or-nothing filter on a student's whole sentence is not a
     * retrieval strategy; ranking is, and {@code ts_rank} does that job.
     *
     * <p>The negation operator is stripped before the rewrite, and that is not cosmetic: a hyphen
     * between tokens parses as NOT, so the physics query {@code v - u} becomes {@code 'v' & !'u'},
     * and OR-ing that unchanged would match every paragraph that lacks "u" — nearly the whole
     * book. Stripping {@code !} makes a minus an ordinary separator, which over a physics corpus
     * is what a student means by it. Quoted phrases survive: {@code <->} carries no {@code &}.
     *
     * <p>Written out twice rather than computed once in a {@code LATERAL} alias: Hibernate's
     * native-query handling reads the alias as a schema qualifier and the statement never reaches
     * PostgreSQL. Repeating it costs one more parse of a short string.
     */
    private static final String TSQUERY = """
            replace(regexp_replace((websearch_to_tsquery('english', :text)
                                 || websearch_to_tsquery('simple', :text))::text, '!', '', 'g'), '&', '|')::tsquery""";

    private static final String FILTERS = """
             AND (CAST(:subject AS varchar) IS NULL OR b.subject = CAST(:subject AS varchar))
             AND (CAST(:node AS uuid) IS NULL OR p.node_id = CAST(:node AS uuid))
            """;

    private final EntityManager entityManager;

    NcertParagraphRetrieval(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ParagraphMatch> nearestByEmbedding(float[] query, BookSubject subject, UUID nodeId, int limit) {
        Query sql = entityManager.createNativeQuery("SELECT " + COLUMNS
                + ", 1 - (p.embedding <=> CAST(:query AS vector)) AS score\n"
                + "  FROM ncert_paragraphs p\n"
                + "  JOIN ncert_books b ON b.id = p.book_id\n"
                + " WHERE p.embedding IS NOT NULL\n"
                + FILTERS
                + " ORDER BY p.embedding <=> CAST(:query AS vector)\n"
                + " LIMIT :limit");
        sql.setParameter("query", Vectors.literal(query));
        return matches(filters(sql, subject, nodeId, limit));
    }

    @Override
    public List<ParagraphMatch> matchingText(String query, BookSubject subject, UUID nodeId, int limit) {
        Query sql = entityManager.createNativeQuery("SELECT " + COLUMNS
                + ", ts_rank(p.tsv, " + TSQUERY + ") AS score\n"
                + "  FROM ncert_paragraphs p\n"
                + "  JOIN ncert_books b ON b.id = p.book_id\n"
                + " WHERE p.tsv @@ " + TSQUERY + "\n"
                + FILTERS
                + " ORDER BY score DESC\n"
                + " LIMIT :limit");
        sql.setParameter("text", query);
        return matches(filters(sql, subject, nodeId, limit));
    }

    private static Query filters(Query sql, BookSubject subject, UUID nodeId, int limit) {
        return sql.setParameter("subject", subject == null ? null : subject.name())
                .setParameter("node", nodeId)
                .setParameter("limit", limit);
    }

    private static List<ParagraphMatch> matches(Query sql) {
        List<ParagraphMatch> matches = new ArrayList<>();
        for (Object row : sql.getResultList()) {
            Object[] columns = (Object[]) row;
            matches.add(new ParagraphMatch((UUID) columns[0], (String) columns[1],
                    ((Number) columns[2]).shortValue(), (String) columns[3], ((Number) columns[4]).shortValue(),
                    (String) columns[5], (String) columns[6], ((Number) columns[7]).doubleValue()));
        }
        return matches;
    }
}
