package com.margai.pipeline.internal;

import com.margai.ai.tasks.PyqPage;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The pages {@code pyq extract} read, assembled into the paper's draft (D19) — each question joined
 * across a page break — and every mechanical check that can route a person's eye, raised as a flag
 * on the question it is about. Like {@code ncert extract}'s checks they never refuse: the draft is
 * written whatever they say, because what follows it is a person reading every question against the
 * page image (README §7), and a flag is where that reading starts.
 *
 * <p>The checks: a page read below {@link #LOW_CONFIDENCE}; a question number printed twice, or a
 * continuation with nothing to continue; anything {@link PaperFileJson#problems} finds against the
 * register (a missing question, options other than (1)–(4), an option with no text and no figure);
 * handwriting touching a question (README §5); a printed heading that names another subject or
 * section than the register puts its first question in; every figure; and, where the page came with
 * a text layer, words the transcription carries that the page does not ({@link TranscriptionDiff})
 * and notation a garbled glyph left ({@link NotationFlags}).
 */
final class PyqAssembly {

    static final int OPTIONS = 4;
    static final BigDecimal LOW_CONFIDENCE = new BigDecimal("0.80");

    private static final Pattern SECTION_LETTER = Pattern.compile("section\\s*[-–:]?\\s*([a-z])\\b");

    private PyqAssembly() {
    }

    /**
     * @param pages     the pages read, in booklet order
     * @param pageTexts each page's text layer where one was sent with it; absent otherwise
     */
    static Draft assemble(PaperDefinition paper, List<PyqExtractedPage> pages, Map<Integer, String> pageTexts) {
        Map<Short, Building> questions = new LinkedHashMap<>();
        List<String> paperFlags = new ArrayList<>();
        // The question the previous page ended inside, its options incomplete — the only one a
        // continuation may join (the same fact the call was given, PyqExtractedPage#endsInside).
        Integer openQuestion = null;
        for (PyqExtractedPage page : pages) {
            if (page.confidence() != null && page.confidence().compareTo(LOW_CONFIDENCE) < 0) {
                paperFlags.add("page " + page.page() + " was read at confidence " + page.confidence()
                        + " — check its questions against the image");
            }
            Integer endedInside = openQuestion;
            openQuestion = page.endsInside();
            for (PyqPage.Item item : page.questions()) {
                short no = (short) item.number();
                if (item.continuesPrevious()) {
                    Building continued = questions.get(no);
                    if (continued == null || endedInside == null || endedInside != item.number()) {
                        paperFlags.add("page " + page.page() + " continues question " + no
                                + ", which the previous page did not end inside");
                        continue;
                    }
                    continued.add(page, item);
                    continue;
                }
                if (questions.containsKey(no)) {
                    questions.get(no).flags.add("question " + no + " is printed again on page " + page.page()
                            + " — the draft keeps the first; check which the booklet prints");
                    continue;
                }
                questions.put(no, new Building(no, page, item));
            }
            for (PyqPage.Heading heading : page.headings()) {
                String mismatch = headingMismatch(paper, heading);
                if (mismatch != null) {
                    paperFlags.add("page " + page.page() + ": " + mismatch);
                }
            }
        }

        List<PaperFile.Question> built = new ArrayList<>();
        for (Building question : questions.values()) {
            String text = question.stem + " " + String.join(" ", question.options.stream().map(QuestionOption::textEn).toList());
            for (int page : question.pages) {
                String layer = pageTexts.get(page);
                if (layer != null) {
                    TranscriptionDiff.check(layer, text).forEach(finding ->
                            question.flags.add("text layer, page " + page + ": " + finding));
                }
            }
            if (!pageTexts.isEmpty()) {
                NotationFlags.check(text).forEach(finding -> question.flags.add("notation: " + finding));
            }
            if (question.handwriting) {
                question.flags.add("handwriting touches the question — check it against page " + question.pages.getFirst());
            }
            for (QuestionFigure figure : question.figures) {
                question.flags.add("figure at " + figure.at() + " on page " + figure.page() + " — " + figure.description());
            }
            built.add(new PaperFile.Question(question.no, question.stem, null, question.options, question.figures, null,
                    new QuestionExtraction(question.pages, question.aiCallIds, question.model, question.prompt,
                            question.flags), null));
        }
        PaperFile file = new PaperFile(paper.code(), paper.transcribed().file(), built);
        paperFlags.addAll(PaperFileJson.problems(file, paper, false));
        return new Draft(file, paperFlags);
    }

    /**
     * A subject heading names the section its first question falls in by the register, and a
     * section heading ("Section-B") its letter; anything else is a mismatch worth a look. A
     * heading that names neither — a page's own title — is ignored.
     */
    static String headingMismatch(PaperDefinition paper, PyqPage.Heading heading) {
        PaperSection section = paper.row().sectionOf(heading.beforeQuestion());
        String text = heading.text().toLowerCase(Locale.ROOT);
        if (section == null) {
            return "the heading '" + heading.text() + "' comes before question " + heading.beforeQuestion()
                    + ", which no section of the register holds";
        }
        for (PaperSubject subject : PaperSubject.values()) {
            if (text.contains(subject.name()) && subject != section.subject()) {
                return "the heading '" + heading.text() + "' comes before question " + heading.beforeQuestion()
                        + ", which the register puts in " + section.subject();
            }
        }
        Matcher letter = SECTION_LETTER.matcher(text);
        if (letter.find() && section.section() != null
                && !letter.group(1).equalsIgnoreCase(section.section())) {
            return "the heading '" + heading.text() + "' comes before question " + heading.beforeQuestion()
                    + ", which the register puts in Section " + section.section();
        }
        return null;
    }

    /** The draft and the flags that belong to the paper rather than to one question. */
    record Draft(PaperFile file, List<String> paperFlags) {

        Draft {
            paperFlags = List.copyOf(paperFlags);
        }

        /** Every flag, the paper's and each question's, for the report. */
        List<String> allFlags() {
            List<String> all = new ArrayList<>(paperFlags);
            for (PaperFile.Question question : file.questions()) {
                question.extraction().flags().forEach(flag -> all.add("Q" + question.no() + ": " + flag));
            }
            return all;
        }
    }

    /** A question while its pages are read: the stem and options of each page appended in order. */
    private static final class Building {

        final short no;
        final List<Integer> pages = new ArrayList<>();
        final List<UUID> aiCallIds = new ArrayList<>();
        final String model;
        final String prompt;
        final List<QuestionOption> options = new ArrayList<>();
        final List<QuestionFigure> figures = new ArrayList<>();
        final List<String> flags = new ArrayList<>();
        String stem = "";
        boolean handwriting;

        Building(short no, PyqExtractedPage page, PyqPage.Item item) {
            this.no = no;
            this.model = page.model();
            this.prompt = page.prompt();
            add(page, item);
        }

        void add(PyqExtractedPage page, PyqPage.Item item) {
            if (!pages.contains(page.page())) {
                pages.add(page.page());
                if (page.aiCallId() != null) {
                    aiCallIds.add(page.aiCallId());
                }
            }
            if (!item.stem().isBlank()) {
                stem = stem.isEmpty() ? item.stem() : stem + "\n" + item.stem();
            }
            item.options().forEach(option -> options.add(new QuestionOption(option.key(), option.text(), null)));
            item.figures().forEach(figure -> figures.add(new QuestionFigure(figure.at(), page.page(), figure.description())));
            handwriting |= item.handwritingTouches();
        }
    }
}
