package com.margai.curriculum.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.ParagraphMatch;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The SQL half of hybrid retrieval against a real {@code pgvector} database — the only place the
 * vector operator, the generated {@code tsv} and V8's HNSW index are exercised as they will
 * actually run. Mocking any of it would test the mock.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainersConfiguration.class, NcertParagraphRetrieval.class})
class NcertParagraphRetrievalTest {

    private static final int WIDTH = 1024;

    @Autowired
    private NcertParagraphRetrieval retrieval;

    @Autowired
    private NcertBookRepository books;

    @Autowired
    private JdbcTemplate jdbc;

    private UUID physics;
    private UUID biology;

    @BeforeEach
    void emptyTheBooks() {
        jdbc.update("DELETE FROM ncert_paragraphs");
        jdbc.update("DELETE FROM ncert_books");
        physics = books.saveAndFlush(new NcertBook(new NcertBookRow("phy11-part1", BookSubject.physics,
                (short) 11, (short) 1, "Physics Part-I", null, (short) 2023, "source/p/", null))).getId();
        biology = books.saveAndFlush(new NcertBook(new NcertBookRow("bio11", BookSubject.biology,
                (short) 11, null, "Biology", null, (short) 2023, "source/b/", null))).getId();
    }

    @Test
    void returnsTheNearestVectorsClosestFirstScoredAsSimilarity() {
        paragraph(physics, 7, "7.9", 1, "Gravitational potential energy.", null, unit(0));
        paragraph(physics, 7, "7.9", 2, "Escape speed.", null, unit(1));

        List<ParagraphMatch> matches = retrieval.nearestByEmbedding(unit(0), null, null, 8);

        assertThat(matches).extracting(ParagraphMatch::address)
                .containsExactly("phy11-part1 ch 7 §7.9 ¶1", "phy11-part1 ch 7 §7.9 ¶2");
        assertThat(matches.getFirst().score()).as("identical vectors: cosine similarity 1")
                .isCloseTo(1.0, org.assertj.core.data.Offset.offset(1e-6));
        assertThat(matches.get(1).score()).as("orthogonal vectors: cosine similarity 0")
                .isCloseTo(0.0, org.assertj.core.data.Offset.offset(1e-6));
    }

    /**
     * A paragraph without a vector is not in the index and cannot be found by the vector half —
     * which is why `ncert embed` leaving rows behind is a reported number and not a silent one.
     */
    @Test
    void aParagraphWithNoVectorIsInvisibleToTheVectorHalf() {
        paragraph(physics, 7, "7.9", 1, "Gravitational potential energy.", null, null);

        assertThat(retrieval.nearestByEmbedding(unit(0), null, null, 8)).isEmpty();
    }

    @Test
    void staysInsideTheSubjectWhenOneIsGiven() {
        paragraph(physics, 7, "7.9", 1, "Gravitational potential energy.", null, unit(0));
        paragraph(biology, 1, "1.1", 1, "The living world.", null, unit(0));

        assertThat(retrieval.nearestByEmbedding(unit(0), BookSubject.physics, null, 8))
                .extracting(ParagraphMatch::bookCode).containsExactly("phy11-part1");
        assertThat(retrieval.nearestByEmbedding(unit(0), null, null, 8))
                .as("no subject filter reaches every book").hasSize(2);
    }

    @Test
    void honoursTheLimit() {
        paragraph(physics, 7, "7.9", 1, "Escape speed of a body.", null, unit(0));
        paragraph(physics, 7, "7.9", 2, "Escape speed again.", null, unit(0));
        paragraph(physics, 7, "7.9", 3, "Escape speed once more.", null, unit(0));

        assertThat(retrieval.nearestByEmbedding(unit(0), null, null, 2)).hasSize(2);
        assertThat(retrieval.matchingText("escape speed", null, null, 2)).hasSize(2);
    }

    /**
     * <strong>The text half ANDs the query's words.</strong> {@code websearch_to_tsquery} joins
     * bare terms with {@code &}, so a paragraph must contain every content word of the question,
     * not any of them. Pinned here because it is not visible in the SQL and it decides how much
     * work the full-text half actually does: a student's whole-sentence question carries six or
     * eight content words and few NCERT paragraphs hold all of them, so on natural questions this
     * half may fire rarely or never — which would make "hybrid" retrieval vector retrieval with a
     * second query attached. Whether that is what happens is one of the things D15's scored run
     * measures; the remedy, if so, is a founder decision about the query's boolean shape, not a
     * silent switch to OR here.
     */
    @Test
    void theTextHalfRequiresEveryWordOfTheQueryNotAnyOfThem() {
        paragraph(physics, 7, "7.9", 1, "The escape speed of a body.", null, null);

        assertThat(retrieval.matchingText("escape speed", null, null, 8))
                .as("both words present").hasSize(1);
        assertThat(retrieval.matchingText("escape speed satellite orbit", null, null, 8))
                .as("two of the four words are absent, so the paragraph does not match at all")
                .isEmpty();
    }

    @Test
    void findsEnglishTextThroughTheStemmedHalfOfTheTsv() {
        paragraph(physics, 7, "7.9", 1, "The escaping body reaches escape speed.", null, null);
        paragraph(physics, 7, "7.1", 1, "Kepler described planetary motion.", null, null);

        List<ParagraphMatch> matches = retrieval.matchingText("escape speed", null, null, 8);

        assertThat(matches).extracting(ParagraphMatch::section).containsExactly("7.9");
        assertThat(matches.getFirst().score()).as("ts_rank, which is unbounded and not a similarity")
                .isGreaterThan(0.0);
    }

    /**
     * The Hindi half of the tsv is stemmer-less on purpose (V7), so a Hindi query has to reach it
     * through the {@code simple} parse. Parsing the query as English only would make the Hindi
     * edition unreachable by the half of retrieval that exists to catch what embeddings miss.
     */
    @Test
    void findsHindiTextThroughTheSimpleHalfOfTheTsv() {
        paragraph(physics, 7, "7.9", 1, "Gravitational potential energy.",
                "गुरुत्वीय स्थितिज ऊर्जा का मान।", null);

        assertThat(retrieval.matchingText("गुरुत्वीय ऊर्जा", null, null, 8))
                .extracting(ParagraphMatch::section).containsExactly("7.9");
    }

    /** A student types words, not operators; `websearch_to_tsquery` must not throw on them. */
    @Test
    void takesAStudentsWordsAsTypedIncludingPunctuationThatWouldBreakAnOperatorSyntax() {
        paragraph(physics, 7, "7.9", 1, "The escape speed of a body.", null, null);

        assertThat(retrieval.matchingText("what is escape speed??? & why", null, null, 8))
                .extracting(ParagraphMatch::section).containsExactly("7.9");
        assertThat(retrieval.matchingText("!!!", null, null, 8)).as("no words, no matches").isEmpty();
    }

    @Test
    void bothTextsTravelSoAHindiOnlyMatchStillHasSomethingToShow() {
        paragraph(physics, 7, "7.9", 1, "Gravitational potential energy.", "गुरुत्वीय स्थितिज ऊर्जा।", unit(0));

        ParagraphMatch match = retrieval.nearestByEmbedding(unit(0), null, null, 8).getFirst();

        assertThat(match.textEn()).isEqualTo("Gravitational potential energy.");
        assertThat(match.textHi()).isEqualTo("गुरुत्वीय स्थितिज ऊर्जा।");
        assertThat(match.text()).as("English is canonical for grounding (§6.4)")
                .isEqualTo("Gravitational potential energy.");
    }

    @Test
    void filtersBothHalvesByTheSyllabusNodeWhenOneIsGiven() {
        paragraph(physics, 7, "7.9", 1, "The escape speed of a body.", null, unit(0));
        UUID unanchored = UUID.randomUUID();

        assertThat(retrieval.nearestByEmbedding(unit(0), null, unanchored, 8)).isEmpty();
        assertThat(retrieval.matchingText("escape speed", null, unanchored, 8)).isEmpty();
    }

    private void paragraph(UUID bookId, int chapter, String section, int paraNo, String textEn, String textHi,
            float[] embedding) {
        jdbc.update("""
                INSERT INTO ncert_paragraphs (book_id, chapter_no, section, para_no, text_en, text_hi, embedding)
                VALUES (?, ?, ?, ?, ?, ?, CAST(? AS vector))""",
                bookId, chapter, section, paraNo, textEn, textHi,
                embedding == null ? null : Vectors.literal(embedding));
    }

    /** A unit vector along one axis: two of them are identical or exactly orthogonal. */
    private static float[] unit(int axis) {
        float[] values = new float[WIDTH];
        values[axis] = 1;
        return values;
    }
}
