package com.margai.curriculum.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Repository slice (TECH_PLAN §8.1): migration V7 applies and its constraints hold — the unique
 * book code, the paragraph address as the upsert key of {@code ncert extract|load} (§6.3), the
 * "a paragraph has text in at least one language" check, and the generated {@code tsv} that the
 * D17 hybrid retriever searches. The JSONB columns round-trip as their records.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class NcertConstraintsTest {

    @Autowired
    private NcertBookRepository books;

    @Autowired
    private NcertParagraphRepository paragraphs;

    @Autowired
    private EntityManager entityManager;

    @Test
    void bookCodeIsUnique() {
        books.saveAndFlush(new NcertBook(book("bio11")));

        assertThatThrownBy(() -> books.saveAndFlush(new NcertBook(book("bio11"))))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ncert_books_code_key");
    }

    @Test
    void theParagraphAddressIsUniquePerBook() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("phy11-part1")));
        paragraphs.saveAndFlush(new NcertParagraph(book.getId(), paragraph((short) 7, "7.9", (short) 1), BookLanguage.en));

        assertThatThrownBy(() -> paragraphs.saveAndFlush(
                new NcertParagraph(book.getId(), paragraph((short) 7, "7.9", (short) 1), BookLanguage.en)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ncert_paragraphs_address_key");
    }

    @Test
    void theSameAddressInAnotherBookIsAnotherParagraph() {
        NcertBook bio = books.saveAndFlush(new NcertBook(book("bio11")));
        NcertBook physics = books.saveAndFlush(new NcertBook(book("phy11-part1")));

        paragraphs.saveAndFlush(new NcertParagraph(bio.getId(), paragraph((short) 7, "7.9", (short) 1), BookLanguage.en));
        paragraphs.saveAndFlush(new NcertParagraph(physics.getId(), paragraph((short) 7, "7.9", (short) 1), BookLanguage.en));

        assertThat(paragraphs.countByBookId(bio.getId())).isEqualTo(1);
        assertThat(paragraphs.countByBookId(physics.getId())).isEqualTo(1);
    }

    @Test
    void bothEditionsLiveOnOneRow() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("bio11")));
        NcertParagraph paragraph = paragraphs.saveAndFlush(
                new NcertParagraph(book.getId(), paragraph((short) 6, "6.4", (short) 2), BookLanguage.en));

        paragraph.apply(new NcertParagraphRow((short) 6, "6.4", (short) 2, "हिन्दी पाठ",
                false, List.of(), extraction()), BookLanguage.hi);
        paragraphs.saveAndFlush(paragraph);

        assertThat(paragraph.getTextEn()).isEqualTo("The English paragraph.");
        assertThat(paragraph.getTextHi()).isEqualTo("हिन्दी पाठ");
        assertThat(paragraphs.countByBookId(book.getId())).isEqualTo(1);
    }

    /**
     * The row record refuses null text, so a textless paragraph cannot be built through the api at
     * all; this pins the database's own guard against anything that writes around it.
     */
    @Test
    void aParagraphNeedsTextInAtLeastOneLanguage() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("bio11")));

        assertThatThrownBy(() -> entityManager.createNativeQuery(
                        "INSERT INTO ncert_paragraphs (book_id, chapter_no, section, para_no) "
                                + "VALUES (:book, 1, '1.1', 1)")
                .setParameter("book", book.getId())
                .executeUpdate())
                .hasMessageContaining("ncert_paragraphs_text_check");
    }

    @Test
    void theJsonbColumnsRoundTrip() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("phy11-part1")));
        NcertParagraphRow row = new NcertParagraphRow((short) 7, "7.9", (short) 3,
                "The gravitational field of a point mass.", true, List.of("Fig 7.9", "Fig 7.10"),
                new ParagraphExtraction(List.of(12, 13), new BigDecimal("0.94"), null));
        UUID id = paragraphs.saveAndFlush(new NcertParagraph(book.getId(), row, BookLanguage.en)).getId();
        entityManager.clear();

        NcertParagraph reloaded = paragraphs.findById(id).orElseThrow();
        assertThat(reloaded.getFigureRefs()).containsExactly("Fig 7.9", "Fig 7.10");
        assertThat(reloaded.isHasEquations()).isTrue();
        assertThat(reloaded.getExtraction().pages()).containsExactly(12, 13);
        assertThat(reloaded.getExtraction().confidence()).isEqualByComparingTo("0.94");
        assertThat(reloaded.getNodeId()).isNull();
    }

    @Test
    void theGeneratedTsvSearchesBothEditions() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("bio11")));
        NcertParagraph paragraph = new NcertParagraph(book.getId(),
                new NcertParagraphRow((short) 6, "6.4", (short) 1,
                        "Photosynthesis converts light energy into chemical energy.",
                        false, List.of(), extraction()),
                BookLanguage.en);
        paragraph.apply(new NcertParagraphRow((short) 6, "6.4", (short) 1, "प्रकाश संश्लेषण",
                false, List.of(), extraction()), BookLanguage.hi);
        paragraphs.saveAndFlush(paragraph);
        entityManager.clear();

        assertThat(matches(book.getId(), "english", "photosynthesis")).isEqualTo(1);
        assertThat(matches(book.getId(), "english", "converts")).isEqualTo(1);
        assertThat(matches(book.getId(), "simple", "संश्लेषण")).isEqualTo(1);
        assertThat(matches(book.getId(), "english", "mitochondria")).isZero();
    }

    @Test
    void renderedPageCountsAreRecordedPerEdition() {
        NcertBook book = books.saveAndFlush(new NcertBook(book("bio11")));

        assertThat(book.applyRenderedPages(BookLanguage.en, 264)).isTrue();
        assertThat(book.applyRenderedPages(BookLanguage.en, 264)).isFalse();
        books.saveAndFlush(book);

        assertThat(book.getPages(BookLanguage.en)).isEqualTo(264);
        assertThat(book.getPages(BookLanguage.hi)).isNull();
    }

    private long matches(UUID bookId, String configuration, String term) {
        return ((Number) entityManager.createNativeQuery(
                        "SELECT count(*) FROM ncert_paragraphs "
                                + "WHERE book_id = :book AND tsv @@ to_tsquery(CAST(:cfg AS regconfig), :term)")
                .setParameter("book", bookId)
                .setParameter("cfg", configuration)
                .setParameter("term", term)
                .getSingleResult())
                .longValue();
    }

    private static NcertBookRow book(String code) {
        return new NcertBookRow(code, BookSubject.biology, (short) 11, null,
                "Biology, Textbook for Class XI", null, (short) 2022,
                "source/ncert/2022-ed/en/" + code + "/", null);
    }

    private static NcertParagraphRow paragraph(short chapter, String section, short paraNo) {
        return new NcertParagraphRow(chapter, section, paraNo, "The English paragraph.",
                false, List.of(), extraction());
    }

    private static ParagraphExtraction extraction() {
        return new ParagraphExtraction(List.of(1), new BigDecimal("0.99"), null);
    }
}
