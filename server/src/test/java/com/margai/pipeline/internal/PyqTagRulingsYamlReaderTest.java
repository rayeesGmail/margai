package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.tasks.PyqTag;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The founder's tag rulings file (DECISIONS 2026-10-21) read strictly: each paper's rulings by question, a field
 * left out empty rather than the model's, {@code anchor: none} as "no NCERT anchor", every malformed entry refused
 * with the file named — and a paper's rulings applied only to the approved file they were made against, and only
 * for a paper the register lists.
 */
class PyqTagRulingsYamlReaderTest {

    static final String SHA = "c".repeat(64);

    @TempDir
    Path dir;

    @Test
    void readsEachPapersRulingsByQuestion() throws IOException {
        Map<String, PyqTagRulingsYamlReader.PaperRulings> read = PyqTagRulingsYamlReader.read(write("""
                papers:
                  - code: neet-2025-45
                    approved_file_sha256: %s
                    rulings:
                      - question: 95
                        primary: BOT.11.CELL
                        out_of_syllabus: false
                        anchor: bio11 Unit 3's opening essay
                        gap: BOT.11.CELL.OPENER — Unit opening
                        ruled: 2026-10-21
                        note: Not out of syllabus.
                      - question: 7
                        primary: PHY.11.ROT
                        secondary: [PHY.11.ROT.MOI]
                        anchor: none
                        ruled: 2026-10-22
                        note: NCERT cut it; the syllabus lists it.
                """.formatted(SHA)));

        assertThat(read.get("neet-2025-45").approvedFileSha256()).isEqualTo(SHA);
        Map<Short, PyqTagRuling> rulings = read.get("neet-2025-45").byQuestion();
        assertThat(rulings).containsOnlyKeys((short) 7, (short) 95);
        PyqTagRuling cell = rulings.get((short) 95);
        assertThat(cell.primary()).isEqualTo("BOT.11.CELL");
        assertThat(cell.secondary()).isEmpty();
        assertThat(cell.outOfSyllabus()).isFalse();
        assertThat(cell.gap()).isEqualTo("BOT.11.CELL.OPENER — Unit opening");
        assertThat(cell.ruled()).isEqualTo(LocalDate.of(2026, 10, 21));
        assertThat(cell.noNcertAnchor()).isFalse();
        PyqTagRuling cut = rulings.get((short) 7);
        assertThat(cut.secondary()).containsExactly("PHY.11.ROT.MOI");
        assertThat(cut.gap()).isNull();
        assertThat(cut.noNcertAnchor()).isTrue();
    }

    @Test
    void refusesWhatTheFounderCouldMistype() throws IOException {
        String ok = """
                      - question: 1
                        primary: PHY.11.ROT
                        ruled: 2026-10-21
                        note: a ruling
                """;
        assertRefused(ok + "        tagged: by hand\n", "unknown key 'tagged'");
        assertRefused(ok + ok, "question 1 is ruled twice");
        assertRefused(ok.replace("primary: PHY.11.ROT", "primary: PHY.11.ROT\n        secondary: [A, B, C]"),
                "3 secondary codes; at most two");
        assertRefused(ok.replace("        note: a ruling\n", ""), "'note' is required");
        assertRefused(ok.replace("        primary: PHY.11.ROT\n", ""), "'primary' is required");
        assertRefused(ok.replace("2026-10-21", "21 October"), "must be a date like 2026-10-21");
        assertRefused(ok.replace("question: 1", "question: one"), "'question' must be a question number");
        assertRefused(ok.replace("primary: PHY.11.ROT", "primary: PHY.11.ROT\n        out_of_syllabus: maybe"),
                "'out_of_syllabus' must be true or false");
        Path noSha = write("papers:\n  - code: neet-2025-45\n    rulings:\n" + ok);
        assertThatThrownBy(() -> PyqTagRulingsYamlReader.read(noSha)).hasMessageContaining("'approved_file_sha256' is required");
    }

    @Test
    void refusesARulingForAQuestionThePaperDoesNotHave() throws IOException {
        PaperDefinition paper = paper();
        Path rulings = write(rulingsFor("neet-2025-45", sha(), 5));

        assertThatThrownBy(() -> PyqTagRulingsYamlReader.forPaper(rulings, paper, Set.of(paper.code()), approvedFile()))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("question 5 is ruled, but the paper has 4 questions");
    }

    /** A mistyped paper code is refused, never left unapplied; another registered paper's rulings are not this one's. */
    @Test
    void refusesAPaperTheRegisterDoesNotList() throws IOException {
        PaperDefinition paper = paper();

        Path mistyped = write(rulingsFor("neet-2025-54", sha(), 1));
        assertThatThrownBy(() -> PyqTagRulingsYamlReader.forPaper(mistyped, paper, Set.of(paper.code()), approvedFile()))
                .hasMessageContaining("paper 'neet-2025-54' is not in the register");

        Path another = write(rulingsFor("neet-2024-s6", SHA, 1));
        assertThat(PyqTagRulingsYamlReader.forPaper(another, paper, Set.of(paper.code(), "neet-2024-s6"), approvedFile()))
                .isEmpty();
    }

    /** Rulings made against an earlier approved file are refused until confirmed against the file as it is now. */
    @Test
    void refusesRulingsMadeAgainstAnotherApprovedFile() throws IOException {
        PaperDefinition paper = paper();
        Path stale = write(rulingsFor("neet-2025-45", SHA, 1));

        assertThatThrownBy(() -> PyqTagRulingsYamlReader.forPaper(stale, paper, Set.of(paper.code()), approvedFile()))
                .hasMessageContaining("its rulings were made against the approved file sha256 " + SHA)
                .hasMessageContaining("set approved_file_sha256 to " + sha());
        assertThat(PyqTagRulingsYamlReader.forPaper(write(rulingsFor("neet-2025-45", sha(), 1)), paper,
                Set.of(paper.code()), approvedFile())).containsOnlyKeys((short) 1);
    }

    /**
     * The founder's committed file reads against the committed register and approved files, and every ruling holds
     * against the taxonomy for the subject its question is printed under — so an edit that would fail {@code pyq tag}
     * or {@code pyq load} fails the commit instead.
     */
    @Test
    void theCommittedFileHoldsAgainstTheRegisterAndTheTaxonomy() {
        Path inputs = InputReadersTest.INPUTS;
        List<PaperDefinition> papers = PyqPapersYamlReader.read(inputs.resolve(PyqPaperCommand.FILE));
        Set<String> registered = papers.stream().map(PaperDefinition::code).collect(Collectors.toSet());
        TaxonomyOutline taxonomy = new TaxonomyOutline(TaxonomyCsvReader.read(inputs.resolve(TaxonomyLoadCommand.FILE)));
        Path file = inputs.resolve(PyqTagRulingsYamlReader.FILE);

        List<String> problems = new ArrayList<>();
        int rulings = 0;
        for (PaperDefinition paper : papers) {
            Path approved = inputs.resolve("papers").resolve(paper.code() + ".json");
            if (!Files.isRegularFile(approved)) {
                continue;
            }
            for (PyqTagRuling ruling : PyqTagRulingsYamlReader.forPaper(file, paper, registered, approved).values()) {
                rulings++;
                String problem = taxonomy.problem(paper.row().sectionOf(ruling.question()).subject(), new PyqTag(
                        ruling.primary(), ruling.secondary(), ruling.outOfSyllabus(), BigDecimal.ONE, ruling.note()));
                if (problem != null) {
                    problems.add(paper.code() + " Q" + ruling.question() + ": " + problem);
                }
            }
        }

        assertThat(rulings).isPositive();
        assertThat(problems).isEmpty();
    }

    private PaperDefinition paper() throws IOException {
        PyqTagCommandTest.writeInputs(dir, PyqTagCommandTest.approved(PaperFile.Answer.single("2")));
        return PyqPapersYamlReader.read(dir.resolve(PyqPaperCommand.FILE)).getFirst();
    }

    private Path approvedFile() {
        return dir.resolve("papers").resolve(PyqTagCommandTest.CODE + ".json");
    }

    private String sha() {
        return Report.sha256(approvedFile());
    }

    static String rulingsFor(String code, String sha, int question) {
        return """
                papers:
                  - code: %s
                    approved_file_sha256: %s
                    rulings:
                      - question: %d
                        primary: PHY.11.ROT
                        ruled: 2026-10-21
                        note: a ruling
                """.formatted(code, sha, question);
    }

    private void assertRefused(String rulings, String message) throws IOException {
        Path file = write("papers:\n  - code: neet-2025-45\n    approved_file_sha256: " + SHA + "\n    rulings:\n" + rulings);
        assertThatThrownBy(() -> PyqTagRulingsYamlReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining(PyqTagRulingsYamlReader.FILE)
                .hasMessageContaining(message);
    }

    private Path write(String yaml) throws IOException {
        Path file = dir.resolve(PyqTagRulingsYamlReader.FILE);
        Files.writeString(file, yaml);
        return file;
    }
}
