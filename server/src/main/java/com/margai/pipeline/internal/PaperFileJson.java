package com.margai.pipeline.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.AsPrintedDisplay;
import com.margai.curriculum.api.QuestionAsPrinted;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * {@link PaperFile} to and from JSON, and the checks an approved file must pass before
 * {@code pyq tag} or {@code pyq load} reads it (D19). snake_case, nulls left out, indented — the
 * file is read and approved by a person, and committed, so it must diff line by line.
 *
 * <p>An approved file is held to its register entry: every question number the sections declare,
 * once, in order; four options keyed 1 to 4; a stem; an option's text unless a figure is that
 * option; and an answer whose shape matches its status, agreeing with the special entries the
 * founder listed for the code — a key typed into the file can never differ from the one he checked
 * (README §3); and every misprint kept as printed quoting what its field prints, with an intended
 * reading, a one-line note and its display class — save words printed run together, which the field
 * carries spaced and a record_only note keeps (the founder, 2026-10-24; a single missing space, Claude's
 * application, for him to confirm — DECISIONS). A draft passes the same checks
 * except that its answers are empty.
 */
final class PaperFileJson {

    static final Set<String> OPTION_KEYS = Set.of("1", "2", "3", "4");
    /** An as-printed note is one line under the question on a phone. */
    static final int AS_PRINTED_NOTE_MAX = 160;

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .changeDefaultPropertyInclusion(include -> include.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();

    private PaperFileJson() {
    }

    static String write(PaperFile file) {
        return MAPPER.writeValueAsString(file) + "\n";
    }

    static PaperFile read(Path path) {
        try {
            return MAPPER.readValue(Files.readString(path, StandardCharsets.UTF_8), PaperFile.class);
        } catch (IOException e) {
            throw new InputFormatException(path, 0, "cannot be read: " + e.getMessage());
        } catch (JacksonException e) {
            throw new InputFormatException(path, 0, "is not a paper file: " + e.getOriginalMessage());
        }
    }

    /**
     * Everything the file gets wrong against its register entry, each named with the question;
     * empty when the file may be tagged and loaded.
     *
     * @param approved whether answers are required — an approved file's are, a draft's are empty
     */
    static List<String> problems(PaperFile file, PaperDefinition paper, boolean approved) {
        List<String> problems = new ArrayList<>();
        if (!paper.code().equals(file.paper())) {
            problems.add("the file is paper '" + file.paper() + "', not '" + paper.code() + "'");
        }
        int expected = 1;
        Set<Short> seen = new HashSet<>();
        for (PaperFile.Question question : file.questions()) {
            String at = "question " + question.no();
            if (!seen.add(question.no())) {
                problems.add(at + " appears twice");
                continue;
            }
            if (question.no() != expected) {
                problems.add(at + " follows question " + (expected - 1) + " — questions run 1 to "
                        + paper.row().questionCount() + " in order");
            }
            expected = question.no() + 1;
            if (question.stemEn() == null || question.stemEn().isBlank()) {
                problems.add(at + " has no stem");
            }
            problems.addAll(optionProblems(at, question));
            problems.addAll(asPrintedProblems(at, question));
            if (approved) {
                problems.addAll(answerProblems(at, question.answer()));
            }
        }
        for (int no = 1; no <= paper.row().questionCount(); no++) {
            if (!seen.contains((short) no)) {
                problems.add("question " + no + " is missing — the booklet declares " + paper.row().questionCount());
            }
        }
        if (seen.size() > paper.row().questionCount()) {
            problems.add("the file has " + seen.size() + " questions, the booklet declares " + paper.row().questionCount());
        }
        if (approved) {
            problems.addAll(specialProblems(file, paper));
        }
        return problems;
    }

    private static List<String> optionProblems(String at, PaperFile.Question question) {
        List<String> problems = new ArrayList<>();
        List<String> keys = question.options().stream().map(QuestionOption::key).toList();
        if (!keys.equals(List.of("1", "2", "3", "4"))) {
            problems.add(at + " has options " + keys + " — the booklet prints (1) to (4)");
        }
        Set<String> drawn = new HashSet<>();
        for (QuestionFigure figure : question.figures()) {
            if (figure.at().startsWith("option:")) {
                drawn.add(figure.at().substring("option:".length()));
            }
        }
        for (QuestionOption option : question.options()) {
            if ((option.textEn() == null || option.textEn().isBlank()) && !drawn.contains(option.key())) {
                problems.add(at + " option " + option.key() + " has no text and no figure");
            }
        }
        return problems;
    }

    /**
     * A misprint kept as printed names a field the question has, a span that field still prints, its
     * intended reading, a one-line note, and its display class — the app shows the note of a
     * {@code show} entry and keeps a {@code record_only} one without it (the founder, 2026-10-21) — the
     * record the founder asked for (2026-10-21), in a shape and with checks that are Claude's
     * (DECISIONS) — so a note can never point at words the question no longer carries. One exception: words
     * the paper runs together with no spaces are a typesetting fault, so the text carries them normally spaced
     * and a {@code record_only} note keeps the printed form (the founder, 2026-10-24; a single missing space and
     * the letters-only and left-in-the-text checks are Claude's, DECISIONS, for him to confirm) — the entry's intended
     * reading is then what the field prints.
     */
    private static List<String> asPrintedProblems(String at, PaperFile.Question question) {
        List<String> problems = new ArrayList<>();
        for (QuestionAsPrinted kept : question.asPrinted()) {
            String text = kept.at() == null ? null : kept.at().equals("stem") ? question.stemEn()
                    : question.options().stream().filter(option -> kept.at().equals("option:" + option.key()))
                            .map(QuestionOption::textEn).findFirst().orElse(null);
            String where = at + "'s as-printed note at " + kept.at();
            if (text == null) {
                problems.add(where + " names no stem or option text of the question");
            } else if (kept.printed() == null || kept.printed().isBlank()
                    || !text.contains(kept.printed()) && !runTogether(kept, text)) {
                problems.add(where + " quotes '" + kept.printed() + "', which that text does not print");
            } else if (spacesDropped(kept.printed(), kept.intended()) && text.contains(kept.printed())) {
                problems.add(where + " keeps words printed run together in the text, which shows them spaced");
            } else if (!text.contains(kept.printed()) && kept.display() != AsPrintedDisplay.record_only) {
                problems.add(where + " records words printed run together, which only a record_only note keeps");
            }
            if (kept.intended() == null || kept.intended().isBlank() || kept.intended().equals(kept.printed())) {
                problems.add(where + " has no intended reading that differs from the print");
            }
            if (kept.note() == null || kept.note().isBlank() || kept.note().contains("\n")
                    || kept.note().length() > AS_PRINTED_NOTE_MAX) {
                problems.add(where + " needs a one-line note of at most " + AS_PRINTED_NOTE_MAX + " characters");
            }
            if (kept.display() == null) {
                problems.add(where + " has no display class — show, or record_only for a slip a student reads correctly");
            }
        }
        return problems;
    }

    /** The field prints the intended reading, and the printed form is that reading with some of its spaces gone. */
    private static boolean runTogether(QuestionAsPrinted kept, String text) {
        return kept.intended() != null && text.contains(kept.intended()) && spacesDropped(kept.printed(), kept.intended());
    }

    /**
     * {@code printed} is {@code intended} with one or more of its spaces between two letters deleted and nothing
     * else changed — words run together, never a number or a sign.
     */
    static boolean spacesDropped(String printed, String intended) {
        if (printed == null || intended == null || printed.length() >= intended.length()) {
            return false;
        }
        int at = 0;
        for (int i = 0; i < intended.length(); i++) {
            char c = intended.charAt(i);
            if (at < printed.length() && printed.charAt(at) == c) {
                at++;
            } else if (c != ' ' || i == 0 || i == intended.length() - 1
                    || !Character.isLetter(intended.charAt(i - 1)) || !Character.isLetter(intended.charAt(i + 1))) {
                return false;
            }
        }
        return at == printed.length();
    }

    private static List<String> answerProblems(String at, PaperFile.Answer answer) {
        if (answer == null || answer.status() == null) {
            return List.of(at + " has no answer — fill it from NTA's final key");
        }
        return switch (answer.status()) {
            case single -> answer.key() != null && OPTION_KEYS.contains(answer.key()) && answer.keys() == null
                    ? List.of() : List.of(at + " is single but its key is '" + answer.key() + "' with keys " + answer.keys());
            case multiple -> answer.key() == null && answer.keys() != null && answer.keys().size() >= 2
                    && OPTION_KEYS.containsAll(answer.keys()) && new HashSet<>(answer.keys()).size() == answer.keys().size()
                    ? List.of() : List.of(at + " is multiple but its keys are " + answer.keys() + " with key '" + answer.key() + "'");
            case dropped -> answer.key() == null && answer.keys() == null
                    ? List.of() : List.of(at + " is dropped but carries a key");
        };
    }

    /** The file's non-single answers against the founder's special entries for the code (README §3). */
    private static List<String> specialProblems(PaperFile file, PaperDefinition paper) {
        List<String> problems = new ArrayList<>();
        TreeSet<Short> listed = new TreeSet<>();
        for (PaperDefinition.KeySpecial special : paper.specials()) {
            listed.add(special.question());
            PaperFile.Answer answer = file.questions().stream().filter(question -> question.no() == special.question())
                    .map(PaperFile.Question::answer).findFirst().orElse(null);
            if (answer == null) {
                continue;
            }
            boolean agrees = special.dropped()
                    ? answer.status() == AnswerStatus.dropped
                    : answer.status() == AnswerStatus.multiple && answer.keys() != null
                            && new TreeSet<>(answer.keys()).equals(new TreeSet<>(special.accepted()));
            if (!agrees) {
                problems.add("question " + special.question() + " is " + (special.dropped() ? "dropped" : "accepts "
                        + special.accepted()) + " in the register's special entries, but the file's answer is "
                        + answer.status() + (answer.keys() == null ? "" : " " + answer.keys()));
            }
        }
        for (PaperFile.Question question : file.questions()) {
            if (question.answer() != null && question.answer().status() != null
                    && question.answer().status() != AnswerStatus.single && !listed.contains(question.no())) {
                problems.add("question " + question.no() + " is " + question.answer().status()
                        + " in the file but not among the register's special entries");
            }
        }
        return problems;
    }
}
