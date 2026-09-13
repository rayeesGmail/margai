package com.margai.curriculum.api;

import java.util.Objects;

/**
 * One book of {@code pipeline/inputs/books.yaml} as {@code ncert register} hands it over
 * (TECH_PLAN §6.2, §6.3): the columns of {@code ncert_books} and nothing else. The per-chapter
 * PDF list that the same YAML file carries stays in the pipeline — it addresses source objects,
 * never a database column (DECISIONS D14).
 *
 * @param s3KeyEn the book's source PREFIX in the content bucket, not one object (NCERT publishes
 *                chapter-wise PDFs); null until that edition exists in the bucket
 */
public record NcertBookRow(
        String code,
        BookSubject subject,
        short classLevel,
        Short part,
        String titleEn,
        String titleHi,
        short editionYear,
        String s3KeyEn,
        String s3KeyHi) {

    public NcertBookRow {
        Objects.requireNonNull(code, "code");
        Objects.requireNonNull(subject, "subject");
        Objects.requireNonNull(titleEn, "titleEn");
    }

    /** The source prefix for one edition, or null when the book has no edition in that language. */
    public String s3Key(BookLanguage language) {
        return language == BookLanguage.en ? s3KeyEn : s3KeyHi;
    }
}
