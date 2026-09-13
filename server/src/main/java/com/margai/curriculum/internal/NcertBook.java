package com.margai.curriculum.internal;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.NcertBookRow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * {@code ncert_books} (TECH_PLAN §2.3, migration V7): one row per book with both editions on it,
 * the NCERT layer of SPEC §9 item 2. {@code code} is the language-neutral book code
 * ({@code bio11}, {@code phy11-part1}) — NCERT's own file codes carry the language
 * ({@code kebo1}, {@code khbo1}) and a row spans both (DECISIONS D14).
 *
 * <p>{@code pagesEn}/{@code pagesHi} are the rendered page counts, written by {@code ncert render}
 * per language and null until it has run; {@code s3KeyEn}/{@code s3KeyHi} are source prefixes.
 */
@Entity
@Table(name = "ncert_books")
public class NcertBook {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 16)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BookSubject subject;

    @Column(nullable = false)
    private short classLevel;

    private Short part;

    @Column(nullable = false, length = 160)
    private String titleEn;

    @Column(length = 160)
    private String titleHi;

    @Column(nullable = false)
    private short editionYear;

    @Column(length = 256)
    private String s3KeyEn;

    @Column(length = 256)
    private String s3KeyHi;

    private Integer pagesEn;

    private Integer pagesHi;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected NcertBook() {
        // JPA
    }

    public NcertBook(NcertBookRow row) {
        this.code = row.code();
        apply(row);
    }

    /**
     * {@code ncert register}'s upsert (TECH_PLAN §6.3), matched on {@code code}: every column the
     * founder's file carries. The rendered page counts are the pipeline's and stay. Returns
     * whether anything changed, so a re-run reports a no-op as one.
     */
    public boolean apply(NcertBookRow row) {
        boolean changed = subject != row.subject()
                || classLevel != row.classLevel()
                || !Objects.equals(part, row.part())
                || !Objects.equals(titleEn, row.titleEn())
                || !Objects.equals(titleHi, row.titleHi())
                || editionYear != row.editionYear()
                || !Objects.equals(s3KeyEn, row.s3KeyEn())
                || !Objects.equals(s3KeyHi, row.s3KeyHi());
        if (changed) {
            this.subject = row.subject();
            this.classLevel = row.classLevel();
            this.part = row.part();
            this.titleEn = row.titleEn();
            this.titleHi = row.titleHi();
            this.editionYear = row.editionYear();
            this.s3KeyEn = row.s3KeyEn();
            this.s3KeyHi = row.s3KeyHi();
        }
        return changed;
    }

    /** {@code ncert render} records how many page images one edition produced. */
    public boolean applyRenderedPages(BookLanguage language, int pages) {
        Integer current = getPages(language);
        if (current != null && current == pages) {
            return false;
        }
        if (language == BookLanguage.en) {
            this.pagesEn = pages;
        } else {
            this.pagesHi = pages;
        }
        return true;
    }

    public Integer getPages(BookLanguage language) {
        return language == BookLanguage.en ? pagesEn : pagesHi;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BookSubject getSubject() {
        return subject;
    }

    public short getClassLevel() {
        return classLevel;
    }

    public Short getPart() {
        return part;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleHi() {
        return titleHi;
    }

    public short getEditionYear() {
        return editionYear;
    }

    public String getS3KeyEn() {
        return s3KeyEn;
    }

    public String getS3KeyHi() {
        return s3KeyHi;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
