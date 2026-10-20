package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.BookSubject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * {@code eval/retrieval-queries.json}: the committed set is read and holds to the bars PLAN D15 and
 * D17 set, and the refusals that keep a typo from scoring as a retrieval failure. Since D17 the file
 * covers the whole corpus, so every expected address names its book, and the book must be one of
 * the query's subject — the subject is what retrieval filters on (TECH_PLAN §4.3 stage 6).
 */
class RetrievalQueriesReaderTest {

    /** The committed set, read from the repository as the commands read it. */
    static final Path COMMITTED = Path.of("..", "eval", "retrieval-queries.json");

    /** The committed book list the addresses are checked against. */
    static final Path BOOKS = InputReadersTest.INPUTS.resolve(NcertRegisterCommand.FILE);

    @TempDir
    Path directory;

    /** D15's fifteen, kept unchanged as the baseline D17 is compared against. */
    @Test
    void theBaselineIsTheD15SetOnTheFirstBook() {
        List<RetrievalQuery> baseline = RetrievalQueriesReader.read(COMMITTED).stream()
                .filter(query -> query.set() == RetrievalQuery.QuerySet.baseline).toList();

        assertThat(baseline).hasSize(15);
        assertThat(baseline).allSatisfy(query -> {
            assertThat(query.subject()).isEqualTo(BookSubject.physics);
            assertThat(query.expect()).allSatisfy(address ->
                    assertThat(address.book()).as("%s", query.id()).isEqualTo("phy11-part1"));
        });
        assertThat(baseline.stream().filter(RetrievalQuery::isHindi)).hasSize(5);
    }

    /**
     * D17's acceptance set has the shape its bar is written for (DECISIONS 2026-10-18): fifteen, five
     * a subject, each subject two English, two Hindi and one Hinglish; at least five in the founder's
     * student phrasing; and none on phy11-part1, the book the D15 baseline tuned retrieval on.
     */
    @Test
    void theAcceptanceSetHasTheShapeItsBarIsWrittenFor() {
        List<RetrievalQuery> acceptance = RetrievalQueriesReader.read(COMMITTED).stream()
                .filter(query -> query.set() == RetrievalQuery.QuerySet.acceptance).toList();

        assertThat(acceptance).hasSize(RetrievalRun.ACCEPTANCE_SIZE);
        for (BookSubject subject : BookSubject.values()) {
            List<RetrievalQuery> ofSubject = acceptance.stream().filter(query -> query.subject() == subject).toList();
            assertThat(ofSubject).as(subject.name()).hasSize(RetrievalRun.PER_SUBJECT);
            assertThat(ofSubject).as(subject + ": two English, two Hindi, one Hinglish")
                    .extracting(RetrievalQuery::language)
                    .containsExactlyInAnyOrder("en", "en", "hi", "hi", "hinglish");
        }
        assertThat(acceptance).filteredOn(query -> query.note().startsWith("Written by the founder"))
                .as("the founder's student phrasing").hasSizeGreaterThanOrEqualTo(5);
        assertThat(acceptance).allSatisfy(query -> assertThat(query.expect())
                .noneMatch(address -> address.book().equals("phy11-part1")));
    }

    /** The founder's four held-out queries (DECISIONS 2026-10-19), one per subject at least, none English. */
    @Test
    void theHeldOutSetIsTheFoundersFour() {
        List<RetrievalQuery> heldOut = RetrievalQueriesReader.read(COMMITTED).stream()
                .filter(query -> query.set() == RetrievalQuery.QuerySet.heldout).toList();

        assertThat(heldOut).hasSize(RetrievalRun.HELD_OUT_SIZE);
        assertThat(heldOut).extracting(RetrievalQuery::subject)
                .contains(BookSubject.physics, BookSubject.chemistry, BookSubject.biology);
        assertThat(heldOut).noneMatch(query -> query.language().equals("en"));
        assertThat(heldOut).allSatisfy(query -> assertThat(query.note()).startsWith("Held out by the founder"));
    }

    @Test
    void everyQueryRecordsHowItWasWritten() {
        assertThat(RetrievalQueriesReader.read(COMMITTED)).allSatisfy(query -> {
            assertThat(query.expect()).as("%s expects a paragraph", query.id()).isNotEmpty();
            assertThat(query.note()).as("%s records how it was written", query.id()).isNotBlank();
        });
    }

    /** Every address is in a real book of the query's subject, in a chapter that book prints. */
    @Test
    void everyExpectedAddressIsInABookOfTheQuerysSubject() {
        List<RetrievalQuery> queries = RetrievalQueriesReader.read(COMMITTED);

        RetrievalQueriesReader.checkBooks(COMMITTED, queries, BooksYamlReader.read(BOOKS));
        queries.forEach(query -> query.expect().forEach(address -> {
            assertThat(address.section()).as("%s: section", query.id()).startsWith(address.chapter() + ".");
            assertThat(address.para()).as("%s: paragraph", query.id()).isPositive();
        }));
    }

    /** The baseline must cover its book, not cluster on the chapters that were easy to search. */
    @Test
    void theBaselineReachesEveryChapterOfItsBook() {
        assertThat(RetrievalQueriesReader.read(COMMITTED).stream()
                .filter(query -> query.set() == RetrievalQuery.QuerySet.baseline)
                .flatMap(query -> query.expect().stream())
                .map(RetrievalQuery.Address::chapter)
                .distinct().sorted().toList())
                .containsExactly((short) 1, (short) 2, (short) 3, (short) 4, (short) 5, (short) 6, (short) 7);
    }

    @Test
    void readsAHinglishQuery() throws IOException {
        Path file = write(query("q1", "acceptance", "physics", "hinglish", "phy11-part1", 1, "1.1", 1));

        RetrievalQuery read = RetrievalQueriesReader.read(file).getFirst();

        assertThat(read.isHinglish()).isTrue();
        assertThat(read.isHindi()).as("Hinglish is scored on its own line, not as Hindi").isFalse();
        assertThat(read.set()).isEqualTo(RetrievalQuery.QuerySet.acceptance);
        assertThat(read.subject()).isEqualTo(BookSubject.physics);
        assertThat(read.expect()).containsExactly(new RetrievalQuery.Address("phy11-part1", (short) 1, "1.1", (short) 1));
    }

    @Test
    void refusesAQueryThatExpectsNothing() throws IOException {
        Path file = write("""
                {"queries": [{"id": "q1", "set": "acceptance", "subject": "physics", "language": "en",
                  "text": "why", "expect": [], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("expects no paragraph");
    }

    /** A misspelt key would otherwise become a query with no expectation and score as a miss. */
    @Test
    void refusesAnUnknownKey() throws IOException {
        Path file = write("""
                {"queries": [{"id": "q1", "set": "acceptance", "subject": "physics", "language": "en",
                  "text": "why", "expects": [], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("unknown key 'expects'");
    }

    /** The D15 file named one book at its root; addresses name theirs now, so the old key is refused. */
    @Test
    void refusesTheOldOneBookRoot() throws IOException {
        Path file = write("""
                {"book": "phy11-part1", "queries": []}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("unknown key 'book'");
    }

    @Test
    void refusesAnAddressWithoutItsBook() throws IOException {
        Path file = write("""
                {"queries": [{"id": "q1", "set": "acceptance", "subject": "physics", "language": "en",
                  "text": "why", "expect": [{"chapter": 1, "section": "1.1", "para": 1}], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("has no 'book'");
    }

    @Test
    void refusesTwoQueriesWithTheSameId() throws IOException {
        Path file = write("{\"queries\": [" + body("q1", "acceptance", "physics", "en", "phy11-part1", 1, "1.1", 1)
                + ", " + body("q1", "acceptance", "physics", "en", "phy11-part1", 1, "1.1", 2) + "]}");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("two queries with id 'q1'");
    }

    @Test
    void refusesALanguageItDoesNotScore() throws IOException {
        Path file = write(query("q1", "acceptance", "physics", "fr", "phy11-part1", 1, "1.1", 1));

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("language 'fr'");
    }

    @Test
    void refusesASetThatIsNotOneOfTheThree() throws IOException {
        Path file = write(query("q1", "tuning", "physics", "en", "phy11-part1", 1, "1.1", 1));

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("set 'tuning'");
    }

    /** Retrieval filters on the book's subject, so botany or zoology would filter on nothing. */
    @Test
    void refusesASubjectThatIsNotABookSubject() throws IOException {
        Path file = write(query("q1", "acceptance", "botany", "en", "bio11", 1, "1.1", 1));

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("subject 'botany'");
    }

    @Test
    void refusesABookTheListDoesNotHold() throws IOException {
        Path file = write(query("q1", "acceptance", "physics", "en", "phy13", 1, "1.1", 1));
        List<RetrievalQuery> queries = RetrievalQueriesReader.read(file);

        assertThatThrownBy(() -> RetrievalQueriesReader.checkBooks(file, queries, BooksYamlReader.read(BOOKS)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("book 'phy13'");
    }

    /**
     * A query filtered to Physics can never reach a Chemistry paragraph, so an address in another
     * subject's book would score as a retrieval miss that retrieval was never allowed to avoid.
     */
    @Test
    void refusesAnAddressInABookOfAnotherSubject() throws IOException {
        Path file = write(query("q1", "acceptance", "physics", "en", "chem11-part1", 1, "1.1", 1));
        List<RetrievalQuery> queries = RetrievalQueriesReader.read(file);

        assertThatThrownBy(() -> RetrievalQueriesReader.checkBooks(file, queries, BooksYamlReader.read(BOOKS)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("chem11-part1 is chemistry");
    }

    /** Part-II books number their chapters on from Part-I's, so chapter 1 is not in phy11-part2. */
    @Test
    void refusesAChapterTheBookDoesNotPrint() throws IOException {
        Path file = write(query("q1", "acceptance", "physics", "en", "phy11-part2", 1, "1.1", 1));
        List<RetrievalQuery> queries = RetrievalQueriesReader.read(file);

        assertThatThrownBy(() -> RetrievalQueriesReader.checkBooks(file, queries, BooksYamlReader.read(BOOKS)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("phy11-part2 has no chapter 1");
    }

    /** Chapter numbers repeat across books (phy11-part1 and phy12-part1 both open at 1): the book decides. */
    @Test
    void matchesAnExpectedAddressAndNothingElse() {
        RetrievalQuery query = new RetrievalQuery("q1", RetrievalQuery.QuerySet.acceptance, BookSubject.physics, "en",
                "why", List.of(new RetrievalQuery.Address("phy11-part1", (short) 7, "7.9", (short) 1)), "n");

        assertThat(query.isExpected("phy11-part1", (short) 7, "7.9", (short) 1)).isTrue();
        assertThat(query.isExpected("phy12-part1", (short) 7, "7.9", (short) 1)).isFalse();
        assertThat(query.isExpected("phy11-part1", (short) 7, "7.9", (short) 2)).isFalse();
        assertThat(query.isExpected("phy11-part1", (short) 7, "7.10", (short) 1)).isFalse();
        assertThat(query.isExpected("phy11-part1", (short) 6, "7.9", (short) 1)).isFalse();
    }

    private static String query(String id, String set, String subject, String language, String book,
            int chapter, String section, int para) {
        return "{\"queries\": [" + body(id, set, subject, language, book, chapter, section, para) + "]}";
    }

    private static String body(String id, String set, String subject, String language, String book,
            int chapter, String section, int para) {
        return """
                {"id": "%s", "set": "%s", "subject": "%s", "language": "%s", "text": "why",
                 "expect": [{"book": "%s", "chapter": %d, "section": "%s", "para": %d}], "note": "n"}"""
                .formatted(id, set, subject, language, book, chapter, section, para);
    }

    private Path write(String json) throws IOException {
        Path file = directory.resolve("retrieval-queries.json");
        Files.writeString(file, json);
        return file;
    }
}
