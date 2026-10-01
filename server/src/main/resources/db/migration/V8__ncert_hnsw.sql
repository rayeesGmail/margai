-- V8__ncert_hnsw.sql — D15 (TECH_PLAN §2.9, §4.9)
-- Purpose: the approximate-nearest-neighbour index the vector half of hybrid retrieval reads
-- (§4.3 stage 6). V7 left `ncert_paragraphs.embedding` unindexed with the note "once rows exist";
-- `ncert embed` fills it, so the index lands with the command that writes the column.
--
-- Scheduled at D17 in §2.9 and pulled to D15 by the founder's decision of 2026-09-19 (DECISIONS):
-- the first verified book is embedded and its retrieval proved before the other nine are
-- extracted, because provider + model + dimension move together and a wrong pin re-embeds the
-- whole corpus.
--
-- ROLLBACK:
-- DROP INDEX IF EXISTS ncert_paragraphs_embedding_idx;
-- END ROLLBACK

-- Cosine, because the pinned model returns vectors whose direction carries the meaning and whose
-- magnitude does not; `m` and `ef_construction` are §4.9's figures, shared with `questions.embedding`
-- at V10 and `doubt_cache.embedding` at V20 so the three indexes behave alike.
--
-- Built here on an empty column rather than after the first load. pgvector builds an index faster
-- over rows that already exist, but a migration cannot wait for a pipeline run, and the corpus is
-- ~900 paragraphs today and ~9,000 at D17 — a size where the difference is not measurable. An
-- HNSW index accepts inserts after creation, so nothing about the order changes what is queryable.
CREATE INDEX ncert_paragraphs_embedding_idx ON ncert_paragraphs
    USING hnsw (embedding vector_cosine_ops) WITH (m = 16, ef_construction = 64);
