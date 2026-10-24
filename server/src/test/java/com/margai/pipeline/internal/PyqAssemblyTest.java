package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.PyqPage;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.AsPrintedDisplay;
import com.margai.curriculum.api.KeyProvenance;
import com.margai.curriculum.api.PaperMarking;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSourceFile;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.PyqPaperRow;
import com.margai.curriculum.api.QuestionAsPrinted;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The draft a paper's pages assemble into (D19): a question that runs onto the next page is one
 * question with both pages and both calls; every mechanical check routes a flag to the question or
 * the page it is about and none of them refuses; and an approved file is held to its register entry
 * — every declared question once, (1) to (4), an answer shaped like its status and agreeing with the
 * founder's special entries for the code.
 */
class PyqAssemblyTest {

    @TempDir
    Path dir;

    @Test
    void aQuestionThatCrossesAPageIsOneQuestionWithBothPagesAndCalls() {
        PyqAssembly.Draft draft = PyqAssembly.assemble(paper(4, List.of()), List.of(
                page(1, List.of(item(1, false, "First", 1, 4), item(2, false, "Second, which runs on", 1, 2))),
                page(2, List.of(item(2, true, "and ends here.", 3, 2), item(3, false, "Third", 1, 4),
                        item(4, false, "Fourth", 1, 4)))), Map.of());

        PaperFile.Question second = draft.file().questions().get(1);
        assertThat(second.stemEn()).isEqualTo("Second, which runs on\nand ends here.");
        assertThat(second.options()).extracting(option -> option.key()).containsExactly("1", "2", "3", "4");
        assertThat(second.extraction().pages()).containsExactly(1, 2);
        assertThat(second.extraction().aiCallIds()).hasSize(2);
        assertThat(draft.paperFlags()).isEmpty();
        assertThat(draft.allFlags()).isEmpty();
    }

    @Test
    void everyCheckRaisesAFlagAndNoneRefuses() {
        PyqPage.Item drawn = new PyqPage.Item(3, false, "Which graph?", List.of(new PyqPage.Option("1", ""),
                new PyqPage.Option("2", "b"), new PyqPage.Option("3", "c"), new PyqPage.Option("4", "d")),
                List.of(new PyqPage.Figure("option:1", "a graph of v against t, a straight line through the origin")), true);
        PyqExtractedPage low = new PyqExtractedPage(2, new BigDecimal("0.70"), UUID.randomUUID(), "claude-opus-5-5",
                "pyq_extract.v1", List.of(new PyqPage.Heading("CHEMISTRY", 3)),
                List.of(item(1, false, "First, printed twice", 1, 4), drawn, item(4, false, "Fourth", 1, 3)), null);

        PyqAssembly.Draft draft = PyqAssembly.assemble(paper(4, List.of()), List.of(
                page(1, List.of(item(1, false, "First", 1, 4))), low), Map.of());

        assertThat(draft.file().questions()).extracting(PaperFile.Question::no).containsExactly((short) 1, (short) 3, (short) 4);
        assertThat(draft.allFlags()).anySatisfy(flag -> assertThat(flag).contains("page 2 was read at confidence 0.70"))
                .anySatisfy(flag -> assertThat(flag).contains("question 1 is printed again on page 2"))
                .anySatisfy(flag -> assertThat(flag).contains("'CHEMISTRY' comes before question 3, which the register puts in physics"))
                .anySatisfy(flag -> assertThat(flag).contains("Q3: handwriting touches the question"))
                .anySatisfy(flag -> assertThat(flag).contains("Q3: figure at option:1 on page 2"))
                .anySatisfy(flag -> assertThat(flag).contains("question 2 is missing"))
                .anySatisfy(flag -> assertThat(flag).contains("question 4 has options [1, 2, 3]"))
                .noneSatisfy(flag -> assertThat(flag).contains("option 1 has no text"));
    }

    @Test
    void aContinuationWithNothingToContinueIsFlaggedNotJoined() {
        PyqAssembly.Draft draft = PyqAssembly.assemble(paper(1, List.of()), List.of(
                page(1, List.of(item(1, false, "First", 1, 4))),
                page(2, List.of(item(1, true, "stray", 1, 1)))), Map.of());

        assertThat(draft.file().questions().getFirst().options()).hasSize(4);
        assertThat(draft.paperFlags()).anySatisfy(flag -> assertThat(flag)
                .contains("page 2 continues question 1, which the previous page did not end inside"));
    }

    @Test
    void aWordTheTextLayerDoesNotCarryIsFlaggedOnItsQuestion() {
        PyqAssembly.Draft draft = PyqAssembly.assemble(paper(1, List.of()), List.of(
                page(1, List.of(item(1, false, "The resistance of the wire is doubled when its lenght is doubled", 1, 4)))),
                Map.of(1, "1. The resistance of the wire is doubled when its length is doubled (1) option 1 (2) option 2 "
                        + "(3) option 3 (4) option 4 and the rest of the words on the page"));

        assertThat(draft.allFlags()).anySatisfy(flag -> assertThat(flag).startsWith("Q1: text layer, page 1:"));
    }

    @Test
    void anApprovedFileRoundTripsAndPassesItsRegisterEntry() throws IOException {
        PaperDefinition paper = paper(3, List.of(new PaperDefinition.KeySpecial((short) 2, false, List.of("1", "2")),
                new PaperDefinition.KeySpecial((short) 3, true, List.of())));
        PaperFile file = approved(PaperFile.Answer.single("3"), PaperFile.Answer.multiple(List.of("1", "2")),
                PaperFile.Answer.dropped());
        Path path = dir.resolve("neet-2025-45.json");
        Files.writeString(path, PaperFileJson.write(file));

        PaperFile read = PaperFileJson.read(path);

        assertThat(read).isEqualTo(file);
        assertThat(PaperFileJson.problems(read, paper, true)).isEmpty();
        assertThat(Files.readString(path)).contains("\"stem_en\"").doesNotContain("stem_hi").doesNotContain("\"keys\" : null")
                .doesNotContain("as_printed");
    }

    /** A misprint kept as printed rides with its question, and must quote what its field still prints (2026-10-21). */
    @Test
    void aMisprintKeptAsPrintedRoundTripsAndIsHeldToItsField() throws IOException {
        PaperDefinition paper = paper(1, List.of());
        PaperFile.Question question = approved(PaperFile.Answer.single("2")).questions().getFirst();
        List<QuestionAsPrinted> kept = List.of(
                new QuestionAsPrinted("stem", "Question", "The question", "'The' is missing before 'Question'",
                        AsPrintedDisplay.record_only),
                new QuestionAsPrinted("option:2", "b", "B", "'b' is printed for 'B'", AsPrintedDisplay.show));
        PaperFile file = withAsPrinted(question, kept);
        Path path = dir.resolve("neet-2025-45.json");
        Files.writeString(path, PaperFileJson.write(file));

        assertThat(PaperFileJson.read(path)).isEqualTo(file);
        assertThat(Files.readString(path)).contains("\"as_printed\"").contains("\"intended\" : \"The question\"")
                .contains("\"display\" : \"record_only\"").contains("\"display\" : \"show\"");
        assertThat(PaperFileJson.problems(file, paper, true)).isEmpty();

        AsPrintedDisplay show = AsPrintedDisplay.show;
        PaperFile wrong = withAsPrinted(question, List.of(
                new QuestionAsPrinted("option:7", "a", "A", "no such option", show),
                new QuestionAsPrinted("stem", "breaks", "brakes", "'breaks' is printed for 'brakes'", show),
                new QuestionAsPrinted("stem", " ", "The question", "a blank span names nothing", show),
                new QuestionAsPrinted("option:1", "a", "a", "the same", show),
                new QuestionAsPrinted("stem", "Question", "The question", "two\nlines", show),
                new QuestionAsPrinted("stem", "Question", "The question", "x".repeat(PaperFileJson.AS_PRINTED_NOTE_MAX + 1), show),
                new QuestionAsPrinted("stem", "Question", "The question", "no class", null)));
        assertThat(PaperFileJson.problems(wrong, paper, true)).containsExactly(
                "question 1's as-printed note at option:7 names no stem or option text of the question",
                "question 1's as-printed note at stem quotes 'breaks', which that text does not print",
                "question 1's as-printed note at stem quotes ' ', which that text does not print",
                "question 1's as-printed note at option:1 has no intended reading that differs from the print",
                "question 1's as-printed note at stem needs a one-line note of at most 160 characters",
                "question 1's as-printed note at stem needs a one-line note of at most 160 characters",
                "question 1's as-printed note at stem has no display class — show, or record_only for a slip a student reads correctly");
        assertThat(PaperFileJson.problems(withAsPrinted(question, List.of(new QuestionAsPrinted("stem", "Question",
                "The question", "x".repeat(PaperFileJson.AS_PRINTED_NOTE_MAX), show))), paper, true)).isEmpty();
    }

    /**
     * Words printed run together are a typesetting fault: the text carries them spaced and a record_only note keeps
     * the printed form, which the text no longer prints (the founder, 2026-10-24); only spaces between letters count,
     * and a single missing space is read the same way (the founder, 2026-10-24).
     */
    @Test
    void wordsPrintedRunTogetherAreSpacedInTheTextAndOnlyRecorded() {
        PaperDefinition paper = paper(1, List.of());
        PaperFile.Question approved = approved(PaperFile.Answer.single("2")).questions().getFirst();
        PaperFile.Question question = withStem(approved, "Presence of paired gill slits in a single character of 10 5.");
        AsPrintedDisplay recordOnly = AsPrintedDisplay.record_only;

        assertThat(PaperFileJson.problems(withAsPrinted(question, List.of(
                new QuestionAsPrinted("stem", "gillslits", "gill slits", "Printed with no space", recordOnly),
                new QuestionAsPrinted("stem", "asinglecharacter", "a single character", "Printed with no spaces",
                        recordOnly))), paper, true)).isEmpty();
        assertThat(PaperFileJson.problems(withAsPrinted(question, List.of(
                new QuestionAsPrinted("stem", "105.", "10 5.", "a number, not words", recordOnly))), paper, true))
                .containsExactly("question 1's as-printed note at stem quotes '105.', which that text does not print");
        assertThat(PaperFileJson.problems(withAsPrinted(question, List.of(
                new QuestionAsPrinted("stem", "gillslits", "gill slits", "shown, not recorded", AsPrintedDisplay.show),
                new QuestionAsPrinted("stem", "gilslits", "gill slits", "letters differ, not spaces", recordOnly),
                new QuestionAsPrinted("stem", "gillslits", "gil lslits", "the field does not print the intended", recordOnly),
                new QuestionAsPrinted("stem", "as inglecharacter", "a single character", "a space moved", recordOnly),
                new QuestionAsPrinted("stem", "gi ll slits", "gill slits", "a space more, not fewer", recordOnly))),
                paper, true)).containsExactly(
                "question 1's as-printed note at stem records words printed run together, which only a record_only note keeps",
                "question 1's as-printed note at stem quotes 'gilslits', which that text does not print",
                "question 1's as-printed note at stem quotes 'gillslits', which that text does not print",
                "question 1's as-printed note at stem quotes 'as inglecharacter', which that text does not print",
                "question 1's as-printed note at stem quotes 'gi ll slits', which that text does not print");
        assertThat(PaperFileJson.problems(withAsPrinted(withStem(approved, "Presence of paired gillslits."), List.of(
                new QuestionAsPrinted("stem", "gillslits", "gill slits", "Printed with no space", recordOnly))),
                paper, true)).containsExactly(
                "question 1's as-printed note at stem keeps words printed run together in the text, which shows them spaced");
    }

    private static PaperFile.Question withStem(PaperFile.Question question, String stem) {
        return new PaperFile.Question(question.no(), stem, question.stemHi(), question.options(), question.figures(),
                question.answer(), question.extraction(), question.asPrinted());
    }

    private static PaperFile withAsPrinted(PaperFile.Question question, List<QuestionAsPrinted> kept) {
        return new PaperFile("neet-2025-45", "source/neet-pyq-sources/2025/booklet.pdf", List.of(new PaperFile.Question(
                question.no(), question.stemEn(), question.stemHi(), question.options(), question.figures(), question.answer(),
                question.extraction(), kept)));
    }

    @Test
    void anApprovedFileWhoseAnswersDisagreeWithTheRegisterIsNamed() {
        PaperDefinition paper = paper(3, List.of(new PaperDefinition.KeySpecial((short) 2, false, List.of("1", "2"))));
        PaperFile file = approved(PaperFile.Answer.multiple(List.of("2", "4")), PaperFile.Answer.single("1"),
                new PaperFile.Answer(AnswerStatus.single, "5", null));

        assertThat(PaperFileJson.problems(file, paper, true))
                .anySatisfy(problem -> assertThat(problem).contains("question 2 is accepts [1, 2] in the register's special "
                        + "entries, but the file's answer is single"))
                .anySatisfy(problem -> assertThat(problem).contains("question 1 is multiple in the file but not among the "
                        + "register's special entries"))
                .anySatisfy(problem -> assertThat(problem).contains("question 3 is single but its key is '5'"));
    }

    @Test
    void aDraftNeedsNoAnswersButAnApprovedFileDoes() {
        PaperFile draft = approved(null, null, null);

        assertThat(PaperFileJson.problems(draft, paper(3, List.of()), false)).isEmpty();
        assertThat(PaperFileJson.problems(draft, paper(3, List.of()), true))
                .containsExactly("question 1 has no answer — fill it from NTA's final key",
                        "question 2 has no answer — fill it from NTA's final key",
                        "question 3 has no answer — fill it from NTA's final key");
    }

    private static PaperFile approved(PaperFile.Answer... answers) {
        List<PaperFile.Question> questions = new ArrayList<>();
        for (int no = 1; no <= answers.length; no++) {
            questions.add(new PaperFile.Question((short) no, "Question " + no, null,
                    List.of(new com.margai.curriculum.api.QuestionOption("1", "a", null),
                            new com.margai.curriculum.api.QuestionOption("2", "b", null),
                            new com.margai.curriculum.api.QuestionOption("3", "c", null),
                            new com.margai.curriculum.api.QuestionOption("4", "d", null)),
                    List.of(), answers[no - 1],
                    new com.margai.curriculum.api.QuestionExtraction(List.of(1), List.of(), "claude-opus-5-5",
                            "pyq_extract.v1", List.of()), null));
        }
        return new PaperFile("neet-2025-45", "source/neet-pyq-sources/2025/booklet.pdf", questions);
    }

    private static PyqExtractedPage page(int page, List<PyqPage.Item> items) {
        return new PyqExtractedPage(page, new BigDecimal("0.97"), UUID.randomUUID(), "claude-opus-5-5", "pyq_extract.v1",
                List.of(), items, null);
    }

    private static PyqPage.Item item(int number, boolean continues, String stem, int first, int count) {
        List<PyqPage.Option> options = new ArrayList<>();
        for (int key = first; key < first + count; key++) {
            options.add(new PyqPage.Option(String.valueOf(key), "option " + key));
        }
        return new PyqPage.Item(number, continues, stem, options, List.of(), false);
    }

    /** A paper of {@code count} questions, all physics but the last, which is chemistry. */
    static PaperDefinition paper(int count, List<PaperDefinition.KeySpecial> specials) {
        List<PaperSection> sections = count == 1
                ? List.of(new PaperSection(PaperSubject.physics, null, (short) 1, (short) 1, (short) 1))
                : List.of(new PaperSection(PaperSubject.physics, null, (short) 1, (short) (count - 1), (short) (count - 1)),
                        new PaperSection(PaperSubject.chemistry, null, (short) count, (short) count, (short) 1));
        return new PaperDefinition(new PyqPaperRow("neet-2025-45", "NEET", (short) 2025, "45", LocalDate.of(2025, 5, 4), true,
                (short) count, sections, new PaperMarking(4, -1, 0, "booklet p1", null, null, null),
                new KeyProvenance("mirrored-official", "a mirror", LocalDate.of(2026, 10, 20),
                        "source/neet-pyq-sources/2025/key.pdf", "c".repeat(64)),
                List.of(new PaperSourceFile("source/neet-pyq-sources/2025/booklet.pdf", "a".repeat(64), "hi-en",
                        List.of(1, 2)))), specials);
    }
}
