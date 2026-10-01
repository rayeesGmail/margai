package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * {@code eval/retrieval-queries.json}: the committed set is read and holds to the bar PLAN D15's
 * ✅ sets, and the refusals that keep a typo from scoring as a retrieval failure.
 */
class RetrievalQueriesReaderTest {

    /** The committed set, read from the repository as the command reads it. */
    static final Path COMMITTED = Path.of("..", "eval", "retrieval-queries.json");

    @TempDir
    Path directory;

    @Test
    void theCommittedSetMeetsTheAcceptanceBar() {
        RetrievalQueriesReader.QuerySet set = RetrievalQueriesReader.read(COMMITTED);

        assertThat(set.book()).isEqualTo("phy11-part1");
        assertThat(set.queries()).as("D17's bar, met on the first book at D15").hasSizeGreaterThanOrEqualTo(15);
        assertThat(set.queries().stream().filter(RetrievalQuery::isHindi))
                .as("§6.4: at least five Hindi queries, each reaching an English paragraph")
                .hasSizeGreaterThanOrEqualTo(5);
        assertThat(set.queries()).allSatisfy(query -> {
            assertThat(query.expect()).as("%s expects a paragraph", query.id()).isNotEmpty();
            assertThat(query.note()).as("%s records how it was written", query.id()).isNotBlank();
        });
    }

    /** Every expectation must be inside the book the set names — chapters 1–7 of Part-I. */
    @Test
    void everyExpectedAddressIsInTheBook() {
        RetrievalQueriesReader.read(COMMITTED).queries().forEach(query ->
                query.expect().forEach(address -> {
                    assertThat(address.chapter()).as("%s: chapter", query.id()).isBetween((short) 1, (short) 7);
                    assertThat(address.section()).as("%s: section", query.id())
                            .startsWith(address.chapter() + ".");
                    assertThat(address.para()).as("%s: paragraph", query.id()).isPositive();
                }));
    }

    /** The whole set must cover the book, not cluster on the chapters that were easy to search. */
    @Test
    void theSetReachesEveryChapterOfTheBook() {
        assertThat(RetrievalQueriesReader.read(COMMITTED).queries().stream()
                .flatMap(query -> query.expect().stream())
                .map(RetrievalQuery.Address::chapter)
                .distinct().sorted().toList())
                .containsExactly((short) 1, (short) 2, (short) 3, (short) 4, (short) 5, (short) 6, (short) 7);
    }

    @Test
    void refusesAQueryThatExpectsNothing() throws IOException {
        Path file = write("""
                {"book": "phy11-part1", "queries": [
                  {"id": "q1", "language": "en", "text": "why", "expect": [], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("expects no paragraph");
    }

    /** A misspelt key would otherwise become a query with no expectation and score as a miss. */
    @Test
    void refusesAnUnknownKey() throws IOException {
        Path file = write("""
                {"book": "phy11-part1", "queries": [
                  {"id": "q1", "language": "en", "text": "why", "expects": [], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("unknown key 'expects'");
    }

    @Test
    void refusesTwoQueriesWithTheSameId() throws IOException {
        Path file = write("""
                {"book": "phy11-part1", "queries": [
                  {"id": "q1", "language": "en", "text": "a", "expect": [{"chapter":1,"section":"1.1","para":1}], "note": "n"},
                  {"id": "q1", "language": "en", "text": "b", "expect": [{"chapter":1,"section":"1.1","para":2}], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("two queries with id 'q1'");
    }

    @Test
    void refusesALanguageThatIsNeitherEnglishNorHindi() throws IOException {
        Path file = write("""
                {"book": "phy11-part1", "queries": [
                  {"id": "q1", "language": "fr", "text": "a", "expect": [{"chapter":1,"section":"1.1","para":1}], "note": "n"}]}""");

        assertThatThrownBy(() -> RetrievalQueriesReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("language 'fr'");
    }

    @Test
    void matchesAnExpectedAddressAndNothingElse() {
        RetrievalQuery query = new RetrievalQuery("q1", "en", "why",
                List.of(new RetrievalQuery.Address((short) 7, "7.9", (short) 1)), "n");

        assertThat(query.isExpected((short) 7, "7.9", (short) 1)).isTrue();
        assertThat(query.isExpected((short) 7, "7.9", (short) 2)).isFalse();
        assertThat(query.isExpected((short) 7, "7.10", (short) 1)).isFalse();
        assertThat(query.isExpected((short) 6, "7.9", (short) 1)).isFalse();
    }

    private Path write(String json) throws IOException {
        Path file = directory.resolve("retrieval-queries.json");
        Files.writeString(file, json);
        return file;
    }
}
