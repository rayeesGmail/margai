package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;

/**
 * The content bucket's key scheme (DECISIONS 2026-09-12 F8, D14): prefixes sit at the bucket's
 * root, and a page image carries the chapter it came from, because NCERT's sources are
 * chapter-wise PDFs and page numbers run per chapter.
 *
 * <pre>
 * source/ncert/2022-ed/{lang}/{book}/{file}.pdf   the founder's upload, mirroring the local tree
 * pages/{book}/{lang}/{chapter}/{page}.png        `ncert render`
 * extract/{book}/{lang}.jsonl                     `ncert extract`
 * verify/{book}/{lang}.jsonl                      `ncert verify --read-pages`
 * </pre>
 */
final class ContentKeys {

    /** Page numbers are zero-padded so a plain key listing sorts into reading order. */
    private static final String PAGE_FORMAT = "%03d.png";

    private ContentKeys() {
    }

    static String pagePrefix(String book, BookLanguage language, short chapter) {
        return "pages/" + book + "/" + language + "/" + chapter + "/";
    }

    static String page(String book, BookLanguage language, short chapter, int page) {
        return pagePrefix(book, language, chapter) + PAGE_FORMAT.formatted(page);
    }

    static String extract(String book, BookLanguage language) {
        return "extract/" + book + "/" + language + ".jsonl";
    }

    /** {@code ncert verify --read-pages}' artefact: one line per page read (D15). */
    static String verify(String book, BookLanguage language) {
        return verify(book, language, null);
    }

    /**
     * The same, under a tag for a scratch run — the seeded recall run reads a copy of the database and
     * must never write into the real artefact (DECISIONS 2026-09-15): {@code verify/{book}/{lang}.{tag}.jsonl}.
     */
    static String verify(String book, BookLanguage language, String tag) {
        return "verify/" + book + "/" + language + (tag == null ? "" : "." + tag) + ".jsonl";
    }

    /**
     * The page number a page key carries. Read back from the listing rather than assumed from the
     * count, so a chapter rendered in part — the state an interrupted render leaves — is extracted
     * for the pages that exist instead of for 1..n.
     */
    static int pageNumber(String pageKey) {
        String name = pageKey.substring(pageKey.lastIndexOf('/') + 1);
        int dot = name.indexOf('.');
        return Integer.parseInt(dot < 0 ? name : name.substring(0, dot));
    }
}
