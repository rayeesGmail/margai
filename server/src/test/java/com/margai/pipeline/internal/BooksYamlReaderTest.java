package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.BookSubject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * {@code books.yaml} (TECH_PLAN §6.2) read over the committed input and over malformed files:
 * every refusal names the book, and the Part-II chapter offset — the trap this file exists to
 * make explicit — is read as the printed chapter number, not the file's sequence.
 */
class BooksYamlReaderTest {

    /** Surefire runs from {@code server/}; the inputs live beside it (InputsMixin's default). */
    static final Path INPUTS = Path.of(InputsMixin.DEFAULT_INPUTS);

    @TempDir
    Path dir;

    @Test
    void readsTheCommittedBooks() {
        List<BookDefinition> books = BooksYamlReader.read(INPUTS.resolve(NcertRegisterCommand.FILE));
        Map<String, BookDefinition> byCode = books.stream()
                .collect(Collectors.toMap(BookDefinition::code, Function.identity()));

        assertThat(books).hasSize(10);
        assertThat(books.stream().mapToInt(book -> book.chapters().size()).sum()).isEqualTo(79);
        assertThat(byCode.keySet()).contains("bio11", "bio12", "phy11-part1", "phy11-part2",
                "chem11-part1", "chem11-part2", "phy12-part1", "phy12-part2", "chem12-part1", "chem12-part2");

        BookDefinition bio11 = byCode.get("bio11");
        assertThat(bio11.row().subject()).isEqualTo(BookSubject.biology);
        assertThat(bio11.row().classLevel()).isEqualTo((short) 11);
        assertThat(bio11.row().part()).isNull();
        assertThat(bio11.row().titleEn()).isEqualTo("Biology, Textbook for Class XI");
        assertThat(bio11.row().titleHi()).isNull();
        assertThat(bio11.row().editionYear()).isEqualTo((short) 2022);
        assertThat(bio11.chapters()).hasSize(19);
        assertThat(bio11.has(BookLanguage.en)).isTrue();
        assertThat(bio11.has(BookLanguage.hi)).isTrue();
    }

    /** The reason this file is explicit: keph201.pdf is Chapter 8, and an anchor must say 8. */
    @Test
    void aPartTwoChapterKeepsItsPrintedNumber() {
        Map<String, BookDefinition> byCode = BooksYamlReader.read(INPUTS.resolve(NcertRegisterCommand.FILE))
                .stream().collect(Collectors.toMap(BookDefinition::code, Function.identity()));

        BookDefinition part2 = byCode.get("phy11-part2");
        assertThat(part2.chapters().getFirst().no()).isEqualTo((short) 8);
        assertThat(part2.chapters().getFirst().fileEn()).isEqualTo("keph201.pdf");
        assertThat(part2.chapters().getLast().no()).isEqualTo((short) 14);
        assertThat(part2.sourceKey(BookLanguage.en, part2.chapters().getFirst()))
                .isEqualTo("source/ncert/2022-ed/en/phy11-part2/keph201.pdf");
        assertThat(part2.sourceKey(BookLanguage.hi, part2.chapters().getFirst()))
                .isEqualTo("source/ncert/2022-ed/hi/phy11-part2/khph201.pdf");
    }

    @Test
    void everyChapterOfEveryBookAddressesBothEditions() {
        for (BookDefinition book : BooksYamlReader.read(INPUTS.resolve(NcertRegisterCommand.FILE))) {
            for (BookDefinition.Chapter chapter : book.chapters()) {
                assertThat(book.sourceKey(BookLanguage.en, chapter)).endsWith(".pdf").contains("/en/");
                assertThat(book.sourceKey(BookLanguage.hi, chapter)).endsWith(".pdf").contains("/hi/");
            }
        }
    }

    @Test
    void anUnknownKeyIsRefused() {
        assertThatThrownBy(() -> read("""
                books:
                  - code: bio11
                    subject: biology
                    class_level: 11
                    titel_en: "typo"
                """))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("unknown key 'titel_en'");
    }

    @Test
    void aSourcePrefixMustEndInASlash() {
        assertThatThrownBy(() -> read(book("source/ncert/2022-ed/en/bio11", "- {no: 1, en: kebo101.pdf}")))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("must end in '/'");
    }

    @Test
    void chaptersMustAscendAndAppearOnce() {
        assertThatThrownBy(() -> read(book("source/ncert/2022-ed/en/bio11/",
                "- {no: 2, en: kebo102.pdf}\n      - {no: 2, en: kebo102.pdf}")))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("chapter 2 does not follow chapter 2");
    }

    @Test
    void aChapterOfAnEditionThatExistsMustNameItsFile() {
        assertThatThrownBy(() -> read(book("source/ncert/2022-ed/en/bio11/", "- {no: 1, hi: khbo101.pdf}")))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("'en' is required");
    }

    @Test
    void aFileMustBeAPdfName() {
        assertThatThrownBy(() -> read(book("source/ncert/2022-ed/en/bio11/", "- {no: 1, en: kebo101}")))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("must be a .pdf file name");
    }

    @Test
    void anUnknownSubjectIsRefused() {
        assertThatThrownBy(() -> read("""
                books:
                  - code: bot11
                    subject: botany
                    class_level: 11
                    title_en: "Botany"
                    edition_year: 2022
                    source:
                      en: source/x/
                    chapters:
                      - {no: 1, en: a.pdf}
                """))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("subject must be one of");
    }

    @Test
    void aRepeatedBookCodeIsRefused() {
        assertThatThrownBy(() -> read(book("source/a/", "- {no: 1, en: a.pdf}") + book("source/a/", "- {no: 1, en: a.pdf}")
                .replace("books:\n", "")))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("appears twice");
    }

    private static String book(String sourceEn, String chapters) {
        return """
                books:
                  - code: bio11
                    subject: biology
                    class_level: 11
                    title_en: "Biology, Textbook for Class XI"
                    edition_year: 2022
                    source:
                      en: %s
                    chapters:
                      %s
                """.formatted(sourceEn, chapters);
    }

    private List<BookDefinition> read(String yaml) {
        try {
            Path file = dir.resolve("books.yaml");
            Files.writeString(file, yaml);
            return BooksYamlReader.read(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
