package com.margai.curriculum.api;

import java.util.Collection;
import java.util.List;

/**
 * The curriculum module's door for the pipeline (TECH_PLAN §1.3, §6.3): the D13 loads. Every
 * method is one transaction that upserts by the natural key, reports what it found in the
 * database that the file no longer names (orphans, listed for the founder, kept in place —
 * DECISIONS 2026-09-12 D13; the one exception is a track's own steps, which follow the file), and
 * throws {@link CurriculumImportException} — rolling the whole run back — when the file
 * contradicts itself or the taxonomy it is loading into.
 */
public interface CurriculumImport {

    /**
     * {@code taxonomy load}: parents before children, upsert on {@code code}, every column of the
     * file set; {@code weightage_marks_avg} is D22's and untouched. Refused: a parent the file does
     * not name or of the wrong kind or subject, a topic whose class level differs from its chapter's,
     * two siblings sharing a {@code sort_order}.
     */
    TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows);

    /**
     * {@code taxonomy prerequisites}: both endpoints must be chapters already loaded; upsert on the
     * pair; then Kahn's algorithm over every edge in the database — a non-empty remainder fails the
     * run (PLAN D13 ✅ "graph has no cycles").
     */
    PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows);

    /**
     * {@code backbone load}: tracks upserted on {@code code}, steps on (track, sequence) with a
     * {@code learn} step naming a chapter, {@code revision} a unit, {@code mock} a subject; sequences
     * the file no longer has are removed, so a shortened track leaves no ghost steps. Refused: a
     * chapter learned twice by one track, and a chapter learned before a prerequisite the same track
     * also learns (the edges already in the database, at sequence granularity).
     */
    BackboneLoadReport loadBackbone(List<ArchetypeTrackRow> tracks);

    /** {@code cutoffs load}: upsert on (year, category, quota_scope, seat_type). */
    CutoffLoadReport loadCutoffs(List<CutoffRow> rows);

    /**
     * {@code ncert register} (D14): upsert on {@code ncert_books.code}. Refused: one code claimed
     * twice, and two books claiming the same (subject, class level, part) — the pair a student's
     * "Class 11 Physics Part-I" means, which two books would make ambiguous.
     */
    NcertRegisterReport registerBooks(List<NcertBookRow> rows);

    /**
     * {@code ncert render} (D14): the page count of one edition, which the coverage percentage at
     * {@code ncert load} divides by. Refused when the book is not registered.
     */
    void recordRenderedPages(String bookCode, BookLanguage language, int pages);

    /**
     * How many pages {@code ncert render} produced for one edition, or null if it has not run.
     * This is the denominator of {@code ncert load}'s coverage: dividing by the pages that were
     * *extracted* instead would report an extraction that stopped a third of the way through a
     * book as complete (spec-auditor, D14).
     */
    Integer renderedPages(String bookCode, BookLanguage language);

    /**
     * {@code ncert load} (D14): upsert on the paragraph address, the text landing in the column of
     * the edition being loaded — so D16's Hindi pass fills {@code text_hi} beside the English
     * paragraph at that address instead of making a second row. Refused: an unregistered book, and
     * one address twice in the same extraction.
     */
    NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows);

    /**
     * What {@code ncert verify} checks (D15): the rows of the given chapters that carry this
     * edition's text, as the load wrote them — their text, figure references and this edition's
     * provenance, verdict included. Ordered by chapter, then section as printed (7.2 before 7.10),
     * then paragraph number, which is reading order. Refused when the book is not registered.
     */
    List<NcertParagraphRow> paragraphs(String bookCode, BookLanguage language, Collection<Short> chapters);

    /**
     * {@code ncert verify --read-pages} (D15): each verdict onto the row at its address, inside this
     * edition's provenance, replacing any earlier verdict. Refused as a whole, writing nothing: an
     * address the book does not have, and a verdict whose text hash is not the row's current text —
     * the row was reloaded with different words after it was read. Returns the rows written.
     */
    int recordVerifications(String bookCode, BookLanguage language, List<NcertVerificationRow> verdicts);

    /**
     * {@code ncert embed} (D15): the book's paragraphs that still need a vector, in reading order.
     * A paragraph needs one when its {@code embedding} is null — which is also how staleness is
     * expressed, since {@code ncert load} clears the embedding of any row whose text it changes —
     * so a re-run embeds only what is missing and costs nothing for what is not. {@code redo}
     * takes the whole book regardless. Paragraphs with no English text are skipped: §6.4 embeds
     * {@code text_en} and nothing else. Refused when the book is not registered.
     */
    List<ParagraphToEmbed> paragraphsToEmbed(String bookCode, boolean redo);

    /**
     * {@code ncert embed} (D15): the vectors onto their rows. Refused as a whole, writing nothing,
     * when any id is not a paragraph of this book — a vector on the wrong paragraph is a wrong
     * anchor under every answer that retrieves it, and it would be invisible. Returns rows written.
     */
    int storeEmbeddings(String bookCode, List<ParagraphEmbedding> embeddings);
}
