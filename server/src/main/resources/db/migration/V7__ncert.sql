-- V7__ncert.sql — D14 (TECH_PLAN §2.3, §2.9)
-- Purpose: the NCERT layer of SPEC §9 item 2 — the ~12 NEET-relevant books (EN + HI) and their
-- paragraphs, addressable down to the paragraph so every answer can carry an anchor (SPEC §6.3).
--
-- ROLLBACK:
-- DROP TABLE IF EXISTS ncert_paragraphs;
-- DROP TABLE IF EXISTS ncert_books;
-- END ROLLBACK

-- One row per book, both languages on it: the same Class 11 Physics Part-I exists as an English
-- and a Hindi edition of one book, and a paragraph is a pair of texts, not two paragraphs.
--
-- subject is the BOOK's subject, so it admits 'biology' — NCERT ships one Biology book while the
-- taxonomy splits Biology into botany and zoology (syllabus_nodes.subject). A paragraph reaches
-- that split through node_id at the D23 anchor pass, which is where it belongs (DECISIONS D14).
--
-- s3_key_* is the book's source PREFIX in the content bucket, not one object: NCERT publishes
-- chapter-wise PDFs (bio11 = kebo101.pdf … kebo119.pdf + prelims), so the per-chapter file list
-- and its file → chapter_no mapping live in the founder-owned pipeline/inputs/books.yaml, where
-- phy11-part2's keph201 = Chapter 8 is a reviewed line rather than an inference (DECISIONS D14).
--
-- pages_* are filled by `ncert render` per language; NULL until that language has been rendered.
CREATE TABLE ncert_books (
    id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    code         VARCHAR(16)  NOT NULL,
    subject      VARCHAR(16)  NOT NULL,
    class_level  SMALLINT     NOT NULL,
    part         SMALLINT,
    title_en     VARCHAR(160) NOT NULL,
    title_hi     VARCHAR(160),
    edition_year SMALLINT     NOT NULL,
    s3_key_en    VARCHAR(256),
    s3_key_hi    VARCHAR(256),
    pages_en     INTEGER,
    pages_hi     INTEGER,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ncert_books_code_key             UNIQUE (code),
    CONSTRAINT ncert_books_subject_check        CHECK (subject IN ('physics', 'chemistry', 'biology')),
    CONSTRAINT ncert_books_class_level_check    CHECK (class_level IN (11, 12)),
    CONSTRAINT ncert_books_part_check           CHECK (part IS NULL OR part BETWEEN 1 AND 4),
    CONSTRAINT ncert_books_edition_year_check   CHECK (edition_year BETWEEN 2000 AND 2100),
    CONSTRAINT ncert_books_pages_en_check       CHECK (pages_en IS NULL OR pages_en > 0),
    CONSTRAINT ncert_books_pages_hi_check       CHECK (pages_hi IS NULL OR pages_hi > 0)
);

-- (book_id, chapter_no, section, para_no) is the paragraph address the app displays as
-- "Class 11 Physics, Ch 7, §7.9" (SPEC §6.3) and the upsert key of `ncert extract|load`
-- (TECH_PLAN §6.3), so every component is NOT NULL: text that precedes a chapter's first
-- numbered section carries the chapter's own number as its section ('7').
--
-- node_id is set by the D23 anchor pass; embedding by `ncert embed` at D17, whose V8 adds the
-- HNSW index once rows exist (§2.9). tsv is generated, so it is never written by the loader:
-- 'english' over text_en and 'simple' over text_hi, the Hindi half being stemmer-less on purpose
-- (§4.9's hybrid retriever leans on the embedding's cross-lingual space for Hindi, §6.4).
CREATE TABLE ncert_paragraphs (
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    book_id       UUID        NOT NULL,
    chapter_no    SMALLINT    NOT NULL,
    section       VARCHAR(16) NOT NULL,
    para_no       SMALLINT    NOT NULL,
    node_id       UUID,
    text_en       TEXT,
    text_hi       TEXT,
    figure_refs   JSONB,
    has_equations BOOLEAN     NOT NULL DEFAULT false,
    embedding     vector(1024),
    tsv           tsvector    GENERATED ALWAYS AS
                      (to_tsvector('english', coalesce(text_en, ''))
                       || to_tsvector('simple', coalesce(text_hi, ''))) STORED,
    extraction    JSONB,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ncert_paragraphs_address_key     UNIQUE (book_id, chapter_no, section, para_no),
    CONSTRAINT ncert_paragraphs_book_id_fkey    FOREIGN KEY (book_id) REFERENCES ncert_books(id) ON DELETE RESTRICT,
    CONSTRAINT ncert_paragraphs_node_id_fkey    FOREIGN KEY (node_id) REFERENCES syllabus_nodes(id) ON DELETE RESTRICT,
    CONSTRAINT ncert_paragraphs_chapter_check   CHECK (chapter_no > 0),
    CONSTRAINT ncert_paragraphs_para_no_check   CHECK (para_no > 0),
    CONSTRAINT ncert_paragraphs_text_check      CHECK (text_en IS NOT NULL OR text_hi IS NOT NULL)
);

CREATE INDEX ncert_paragraphs_tsv_idx     ON ncert_paragraphs USING GIN (tsv);
CREATE INDEX ncert_paragraphs_node_id_idx ON ncert_paragraphs (node_id);
