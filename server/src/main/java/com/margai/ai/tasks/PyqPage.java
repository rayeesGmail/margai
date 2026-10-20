package com.margai.ai.tasks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Output record of the {@code pyq_extract} prompt (D19): one booklet page's questions in printed
 * order, transcribed from the English text only, with the headings the page prints and the
 * model's confidence in having read it correctly.
 *
 * <p>Nothing here is what code can compute (.claude/rules/ai-layer.md): whether a question is
 * complete is its option count, whether it sits in the right section is the register's section
 * table against the printed number, and the answer is never read from the page at all — it comes
 * from NTA's final key (README §2, §5).
 *
 * @param headings   subject and section headings printed on the page, in order
 * @param questions  in printed order, possibly empty for a page of instructions or rough work
 * @param confidence 0–1, how sure the model is of this page's text
 */
public record PyqPage(List<Heading> headings, List<Item> questions, BigDecimal confidence) {

    static final Set<String> OPTION_KEYS = Set.of("1", "2", "3", "4");

    public PyqPage {
        headings = headings == null ? List.of() : List.copyOf(headings);
        questions = questions == null ? List.of() : List.copyOf(questions);
        for (int index = 1; index < questions.size(); index++) {
            if (questions.get(index).continuesPrevious()) {
                throw new IllegalArgumentException("continues_previous is set on question " + questions.get(index).number()
                        + ", but only a page's first item can continue the previous page");
            }
        }
    }

    /**
     * A heading as printed — "PHYSICS", "Section-A" — and the number of the first question printed
     * after it on this page, so the pipeline can hold the register's sections to the booklet.
     */
    public record Heading(String text, int beforeQuestion) {
    }

    /**
     * One question, or the rest of the previous page's last question.
     *
     * <p>A blank stem is refused here, where the model's output is decoded, so the schema layer's
     * repair call answers it — unless the item continues the previous page, whose remainder may
     * be options alone.
     *
     * @param number             the printed question number; for a continuation, the number the
     *                           call said the previous page ended inside
     * @param continuesPrevious  true only for a page's first item, when it is the rest of the
     *                           question the previous page ended inside
     * @param stem               the question text, every statement, list and table included
     * @param options            as printed, keys "1"–"4"
     * @param figures            each figure the question prints, where it sits and what it shows
     * @param handwritingTouches true where any handwriting, tick or drawn line touches the stem,
     *                           an option or a table, so the question is checked against the image
     */
    public record Item(
            int number,
            boolean continuesPrevious,
            String stem,
            List<Option> options,
            List<Figure> figures,
            boolean handwritingTouches) {

        public Item {
            if (number < 1) {
                throw new IllegalArgumentException("question number " + number + " is not a printed question number");
            }
            if ((stem == null || stem.isBlank()) && !continuesPrevious) {
                throw new IllegalArgumentException("question " + number + " has no stem — every question prints one; "
                        + "only the rest of a question continued from the previous page may be options alone");
            }
            stem = stem == null ? "" : stem;
            options = options == null ? List.of() : List.copyOf(options);
            figures = figures == null ? List.of() : List.copyOf(figures);
        }
    }

    /** An option as printed; its text may be empty only when the option is a figure. */
    public record Option(String key, String text) {

        public Option {
            if (key == null || !OPTION_KEYS.contains(key)) {
                throw new IllegalArgumentException("option key '" + key + "' — the booklet prints (1) to (4), "
                        + "and the key is that digit");
            }
            text = text == null ? "" : text;
        }
    }

    /**
     * A figure: {@code stem}, or {@code option:<key>} for an option that is drawn, and what it
     * shows in words for the cropper and the reviewer (founder's D19 ruling 4).
     */
    public record Figure(String at, String description) {

        public Figure {
            if (at == null || !(at.equals("stem") || at.matches("option:[1-4]"))) {
                throw new IllegalArgumentException("figure at '" + at + "' — it is 'stem' or 'option:1' to 'option:4'");
            }
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("the figure at " + at + " has no description");
            }
        }
    }
}
