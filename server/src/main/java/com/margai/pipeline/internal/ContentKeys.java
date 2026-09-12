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
}
