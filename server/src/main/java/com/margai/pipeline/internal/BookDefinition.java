package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertBookRow;
import java.util.List;

/**
 * One book of {@code books.yaml} as the pipeline needs it (TECH_PLAN §6.2): the database columns
 * in {@link NcertBookRow}, plus the per-chapter source PDFs, which address objects in the content
 * bucket and are no part of any table (DECISIONS D14).
 */
record BookDefinition(NcertBookRow row, List<Chapter> chapters) {

    BookDefinition {
        chapters = List.copyOf(chapters);
    }

    String code() {
        return row.code();
    }

    /** Whether this book has an edition in that language at all. */
    boolean has(BookLanguage language) {
        return row.s3Key(language) != null;
    }

    /**
     * The source key of one chapter's PDF: the book's prefix and the chapter's file name for that
     * edition — {@code source/ncert/2022-ed/en/bio11/kebo107.pdf}.
     */
    String sourceKey(BookLanguage language, Chapter chapter) {
        return row.s3Key(language) + chapter.file(language);
    }

    /**
     * One chapter's source PDFs. {@code no} is the number printed in the book, which the files do
     * not carry: a Part-II folder numbers its files from 01 while its chapters start at 8
     * (keph201.pdf = Chapter 8), and the printed number is what every anchor means (SPEC §6.3).
     */
    record Chapter(short no, String fileEn, String fileHi) {

        String file(BookLanguage language) {
            return language == BookLanguage.en ? fileEn : fileHi;
        }
    }
}
