package com.margai.curriculum.internal;

import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.ParagraphEmbedding;
import com.margai.curriculum.api.ParagraphToEmbed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code embedding} column of {@code ncert_paragraphs} (TECH_PLAN §4.9, §6.3 {@code ncert
 * embed}). Runs inside {@link CurriculumImportService}'s transaction.
 *
 * <p><strong>A null embedding is the whole state machine.</strong> It means "this row has no
 * vector for the text it currently carries", and the three things that can happen to a paragraph
 * all land on it correctly:
 *
 * <ul>
 * <li>a new paragraph is inserted with a null embedding, so the next {@code ncert embed} picks it
 *     up;</li>
 * <li>a reloaded paragraph whose text <em>changed</em> has its embedding cleared by
 *     {@link #clearFor}, because the vector describes words the row no longer has — without this
 *     a corrected paragraph would keep a vector for the text it was corrected away from, and
 *     nothing would ever say so;</li>
 * <li>a deleted paragraph takes its vector with it.</li>
 * </ul>
 *
 * <p>So resumability and staleness are one mechanism, and {@code ncert embed} re-run over a book
 * it has already embedded costs nothing.
 */
@Component
class NcertEmbeddings {

    /**
     * Reading order, in SQL: the section compared number by number so 7.2 precedes 7.10, which
     * {@code string_to_array(section, '.')::int[]} does directly. The Java comparator in
     * {@link NcertParagraphImporter} sorts rows already in memory; this sorts in the database
     * because the point of the query is not to load the book.
     */
    private static final String READING_ORDER =
            " ORDER BY p.chapter_no, string_to_array(p.section, '.')::int[], p.para_no";

    private final NcertBookRepository books;
    private final EntityManager entityManager;

    NcertEmbeddings(NcertBookRepository books, EntityManager entityManager) {
        this.books = books;
        this.entityManager = entityManager;
    }

    List<ParagraphToEmbed> waiting(String bookCode, boolean redo) {
        UUID bookId = book(bookCode);
        Query query = entityManager.createNativeQuery("""
                SELECT p.id, p.chapter_no, p.section, p.para_no, p.text_en
                  FROM ncert_paragraphs p
                 WHERE p.book_id = :book
                   AND p.text_en IS NOT NULL AND btrim(p.text_en) <> ''
                   AND (:redo = TRUE OR p.embedding IS NULL)"""
                + READING_ORDER);
        query.setParameter("book", bookId);
        query.setParameter("redo", redo);
        List<ParagraphToEmbed> waiting = new ArrayList<>();
        for (Object row : query.getResultList()) {
            Object[] columns = (Object[]) row;
            waiting.add(new ParagraphToEmbed((UUID) columns[0], ((Number) columns[1]).shortValue(),
                    (String) columns[2], ((Number) columns[3]).shortValue(), (String) columns[4]));
        }
        return waiting;
    }

    /**
     * Every id is checked against the book before any vector is written, so a mismatched batch
     * names all of them at once and writes nothing. A vector on the wrong paragraph is a wrong
     * anchor under every answer that retrieves it, and unlike a wrong transcription nobody can
     * see it by reading the row.
     */
    int store(String bookCode, List<ParagraphEmbedding> embeddings) {
        UUID bookId = book(bookCode);
        if (embeddings.isEmpty()) {
            return 0;
        }
        List<UUID> ids = embeddings.stream().map(ParagraphEmbedding::paragraphId).toList();
        List<UUID> foreign = new ArrayList<>(ids);
        foreign.removeAll(idsIn(bookId, ids));
        if (!foreign.isEmpty()) {
            throw new CurriculumImportException(foreign.size() + " embedding(s) name a paragraph that is not in "
                    + bookCode + ", nothing was written: " + foreign.stream().map(UUID::toString).sorted().toList());
        }
        int written = 0;
        for (ParagraphEmbedding embedding : embeddings) {
            written += entityManager.createNativeQuery("""
                            UPDATE ncert_paragraphs
                               SET embedding = CAST(:vector AS vector), updated_at = now()
                             WHERE id = :id""")
                    .setParameter("vector", Vectors.literal(embedding.vector()))
                    .setParameter("id", embedding.paragraphId())
                    .executeUpdate();
        }
        return written;
    }

    /**
     * Drops the vectors of rows whose text a load has just changed. Called with the ids the load
     * actually updated, so an idempotent re-load — which changes nothing — clears nothing and the
     * book stays embedded.
     */
    int clearFor(Collection<UUID> changed) {
        if (changed.isEmpty()) {
            return 0;
        }
        return entityManager.createNativeQuery(
                        "UPDATE ncert_paragraphs SET embedding = NULL WHERE id IN (:ids) AND embedding IS NOT NULL")
                .setParameter("ids", changed)
                .executeUpdate();
    }

    /** How many of these rows carry a vector — what a load reports it is about to throw away. */
    long embeddedAmong(Collection<UUID> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return ((Number) entityManager.createNativeQuery(
                        "SELECT count(*) FROM ncert_paragraphs WHERE id IN (:ids) AND embedding IS NOT NULL")
                .setParameter("ids", ids)
                .getSingleResult()).longValue();
    }

    @SuppressWarnings("unchecked")
    private List<UUID> idsIn(UUID bookId, List<UUID> ids) {
        return entityManager.createNativeQuery(
                        "SELECT id FROM ncert_paragraphs WHERE book_id = :book AND id IN (:ids)")
                .setParameter("book", bookId)
                .setParameter("ids", ids)
                .getResultList();
    }

    private UUID book(String bookCode) {
        return books.findByCode(bookCode)
                .orElseThrow(() -> new CurriculumImportException(
                        "book '" + bookCode + "' is not registered — run `ncert register` first"))
                .getId();
    }
}
