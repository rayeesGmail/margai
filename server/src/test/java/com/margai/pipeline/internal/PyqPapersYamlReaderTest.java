package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.PyqPaperRow;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * {@code pyq-papers.yaml}, the founder's register of official papers (D19): a well-formed paper
 * reads into its row and the code's special key entries, and every way the declared count, the
 * sections, the booklets, the key's provenance or the special entries can disagree is refused by
 * name before anything is counted.
 */
class PyqPapersYamlReaderTest {

    private static final String SHA_A = "a".repeat(64);
    private static final String SHA_B = "b".repeat(64);

    @TempDir
    Path dir;

    @Test
    void readsAPaperWithItsSectionsKeyAndSpecialEntries() throws IOException {
        List<PaperDefinition> papers = PyqPapersYamlReader.read(write(paper2025()));

        assertThat(papers).hasSize(1);
        PaperDefinition paper = papers.getFirst();
        PyqPaperRow row = paper.row();
        assertThat(row.code()).isEqualTo("neet-2025-45");
        assertThat(row.paperCode()).isEqualTo("45");
        assertThat(row.examDate()).isEqualTo(LocalDate.of(2025, 5, 4));
        assertThat(row.officialHindi()).isTrue();
        assertThat(row.sections()).extracting(PaperSection::subject)
                .containsExactly(PaperSubject.physics, PaperSubject.chemistry, PaperSubject.botany, PaperSubject.zoology);
        assertThat(row.sections()).extracting(PaperSection::size).containsExactly(45, 45, 45, 45);
        assertThat(row.source().getFirst().pages()).hasSize(45).startsWith(2).endsWith(46);
        assertThat(row.marking().rulesSourced()).isFalse();
        assertThat(row.keySource().tier()).isEqualTo("mirrored-official");
        assertThat(row.keySource().downloaded()).isEqualTo(LocalDate.of(2026, 10, 20));
        assertThat(paper.specials()).containsExactly(
                new PaperDefinition.KeySpecial((short) 40, false, List.of("1", "2")),
                new PaperDefinition.KeySpecial((short) 63, false, List.of("1", "2")));
        assertThat(paper.transcribed().languages()).isEqualTo("hi-en");
    }

    @Test
    void sectionBIsReadWithItsAttemptCountAndADropAsADrop() throws IOException {
        String yaml = paper2025()
                .replace("question_count: 180", "question_count: 200")
                .replace("- {subject: physics, from: 1, to: 45}", "- {subject: physics, section: A, from: 1, to: 35}\n"
                        + "      - {subject: physics, section: B, from: 36, to: 50, attempt: 10}")
                .replace("- {subject: chemistry, from: 46, to: 90}", "- {subject: chemistry, from: 51, to: 100}")
                .replace("- {subject: botany, from: 91, to: 135}", "- {subject: botany, from: 101, to: 150}")
                .replace("- {subject: zoology, from: 136, to: 180}", "- {subject: zoology, from: 151, to: 200}")
                .replace("- {question: 63, accepted: [1, 2]}", "- {question: 95, dropped: true}");

        PaperDefinition paper = PyqPapersYamlReader.read(write(yaml)).getFirst();

        assertThat(paper.row().sections().get(1)).isEqualTo(
                new PaperSection(PaperSubject.physics, "B", (short) 36, (short) 50, (short) 10));
        assertThat(paper.row().sections().get(2).attempt()).isEqualTo((short) 50);
        assertThat(paper.specials().get(1)).isEqualTo(new PaperDefinition.KeySpecial((short) 95, true, List.of()));
    }

    @Test
    void anEnglishBookletIsTheOneTranscribed() throws IOException {
        String yaml = paper2025().replace("""
                    booklets:
                """, """
                    booklets:
                      - file: source/neet-pyq-sources/2025/english.pdf
                        sha256: %s
                        languages: en
                        pages: 1-21
                """.formatted(SHA_B));

        PaperDefinition paper = PyqPapersYamlReader.read(write(yaml)).getFirst();

        assertThat(paper.transcribed().file()).isEqualTo("source/neet-pyq-sources/2025/english.pdf");
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(delimiter = '|', quoteCharacter = '"', textBlock = """
            a gap between sections            | from: 46, to: 90          | from: 47, to: 90            | the chemistry section starts at question 47 but question 46
            an overlap between sections       | from: 46, to: 90          | from: 45, to: 90            | the chemistry section starts at question 45 but question 46
            a count the sections miss         | question_count: 180       | question_count: 181         | the sections end at question 180 but the booklet declares 181
            a publisher's key                 | tier: mirrored-official   | tier: publisher             | a publisher or coaching key is never a source
            a file outside source/            | file: source/neet-pyq-sources/2025/neet-2025-final | file: keys/neet-2025-final | must be a .pdf of the founder's pack
            a retired compilation as a key    | file: source/neet-pyq-sources/2025/neet-2025-final | file: source/pyq/papers/neet2023_with_answer_key.pdf\\n#  | compilations and publisher keys are never a source
            the reference-only retype         | file: source/neet-pyq-sources/2025/neet-2025-45-booklet-hi-en.pdf | file: source/neet-pyq-sources/reference-only-do-not-load/retype.pdf | the pack's reference-only retype
            pages out of order                | pages: 2-46               | pages: [10-46, 2-9]         | lists page 2 after page 46
            an exam outside NEET and AIPMT    | exam: NEET                | exam: JEE                   | exam must be one of [AIPMT, NEET]
            Hindi denied on a bilingual file  | official_hindi: true      | official_hindi: false       | official_hindi is false but a booklet is hi-en
            a lower-case section letter       | subject: physics, from: 1 | subject: physics, section: a, from: 1 | section must be one capital letter
            an attempt above the section size | subject: physics, from: 1, to: 45 | subject: physics, from: 1, to: 45, attempt: 46 | 'attempt' must be between 1 and 45
            two entries for one question      | {question: 63, accepted: [1, 2]} | {question: 40, dropped: true} | question 40 has two special entries
            an entry neither dropped nor accepting | {question: 63, accepted: [1, 2]} | {question: 63} | must be either 'dropped: true' or 'accepted
            a repeated accepted key           | {question: 40, accepted: [1, 2]} | {question: 40, accepted: [1, 1]} | accepts '1'
            a short sha256                    | sha256: %2$s              | sha256: abc                 | must be 64 lowercase hex digits
            a page listed twice               | pages: 2-46               | pages: [2-46, 46]           | lists page 46 twice
            Hindi without a bilingual file    | languages: hi-en          | languages: en               | official_hindi is true but no booklet is hi-en
            half-sourced special rules        | scheme_source: booklet p1, instruction 4 | scheme_source: booklet p1, instruction 4\\n      dropped_rule: award-all | sourced together from the year's notice
            one accepted key                  | {question: 40, accepted: [1, 2]} | {question: 40, accepted: [1]} | accepts one key
            an accepted key outside 1-4       | {question: 40, accepted: [1, 2]} | {question: 40, accepted: [1, 5]} | accepts '5'
            a special entry outside the paper | {question: 63, accepted: [1, 2]} | {question: 181, accepted: [1, 2]} | 'question' must be between 1 and 180
            a drop that also accepts keys     | {question: 63, accepted: [1, 2]} | {question: 63, dropped: true, accepted: [1, 2]} | must be either 'dropped: true' or 'accepted
            an unknown key                    | official_hindi: true      | official_hindi: true\\n    hindi: yes | unknown key 'hindi'
            an exam date in another year      | exam_date: 2025-05-04     | exam_date: 2024-05-04       | is not in 2025
            """)
    void refusesAPaperThatDisagreesWithItself(String name, String from, String to, String message) throws IOException {
        String yaml = paper2025();
        String target = from.replace("%2$s", SHA_A);
        assertThat(yaml).as("the fixture carries '%s'", target).contains(target);
        Path file = write(yaml.replaceFirst(java.util.regex.Pattern.quote(target),
                java.util.regex.Matcher.quoteReplacement(to.replace("\\n", "\n"))));

        assertThatThrownBy(() -> PyqPapersYamlReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("paper neet-2025-45")
                .hasMessageContaining(message);
    }

    @Test
    void refusesOneIdRegisteredTwice() throws IOException {
        String paper = paper2025().replace("papers:\n", "");
        String yaml = "papers:\n" + paper + paper.replace("paper_code: \"45\"", "paper_code: \"46\"");

        assertThatThrownBy(() -> PyqPapersYamlReader.read(write(yaml)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("paper 'neet-2025-45' appears twice");
    }

    @Test
    void refusesTwoBookletsInOneLanguage() throws IOException {
        String yaml = paper2025().replace("""
                    booklets:
                """, """
                    booklets:
                      - file: source/neet-pyq-sources/2025/another-bilingual.pdf
                        sha256: %s
                        languages: hi-en
                        pages: 1-48
                """.formatted(SHA_B));

        assertThatThrownBy(() -> PyqPapersYamlReader.read(write(yaml)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("two booklets are 'hi-en'");
    }

    @Test
    void refusesOnePaperRegisteredTwiceUnderTwoIds() throws IOException {
        String paper = paper2025().replace("papers:\n", "");
        String yaml = "papers:\n" + paper + paper.replace("code: neet-2025-45", "code: neet-2025-45-copy");

        assertThatThrownBy(() -> PyqPapersYamlReader.read(write(yaml)))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("NEET 2025 code 45 is already registered under another id");
    }

    private Path write(String yaml) throws IOException {
        Path file = dir.resolve("pyq-papers.yaml");
        Files.writeString(file, yaml);
        return file;
    }

    private static String paper2025() {
        return """
                papers:
                  - code: neet-2025-45
                    exam: NEET
                    year: 2025
                    paper_code: "45"
                    exam_date: 2025-05-04
                    official_hindi: true
                    question_count: 180
                    booklets:
                      - file: source/neet-pyq-sources/2025/neet-2025-45-booklet-hi-en.pdf
                        sha256: %1$s
                        languages: hi-en
                        pages: 2-46
                    sections:
                      - {subject: physics, from: 1, to: 45}
                      - {subject: chemistry, from: 46, to: 90}
                      - {subject: botany, from: 91, to: 135}
                      - {subject: zoology, from: 136, to: 180}
                    marking:
                      correct: 4
                      wrong: -1
                      unanswered: 0
                      scheme_source: booklet p1, instruction 4
                    key:
                      file: source/neet-pyq-sources/2025/neet-2025-final-key-nta-codes-45-48.pdf
                      sha256: %2$s
                      tier: mirrored-official
                      from: NTA final key, mirrored (MANIFEST.csv)
                      downloaded: 2026-10-20
                      special:
                        - {question: 40, accepted: [1, 2]}
                        - {question: 63, accepted: [1, 2]}
                """.formatted(SHA_A, SHA_B);
    }
}
