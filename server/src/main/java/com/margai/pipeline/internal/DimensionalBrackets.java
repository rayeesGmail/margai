package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A zero exponent the book sets as a degree sign, put back into the convention (D15, 2026-09-17).
 *
 * <p>`keph101.pdf` page 7 prints the same quantity twice. The running text of §1.5 sets the zero
 * exponent as a degree sign — the page's own text layer holds {@code [M° L3 T°]}, U+00B0 — while
 * the displayed equations five lines below set the digit, {@code [M0 L3 T0]}. The transcription is
 * faithful to the layer, as §6.1 requires, so it carries the book's inconsistency into the corpus:
 * a dimensional formula that reads "M degrees", which is not what the page means and would not
 * match {@code M^0} at retrieval. Code can see this one, so code decides it rather than the model
 * (.claude/rules/ai-layer.md) and the frozen prompt is untouched.
 *
 * <p>The rule is deliberately narrow, because a degree sign is not always a lost zero. Chemistry
 * sets the limiting molar conductivity as {@code Λ°m} and the layer renders that Lambda as a plain
 * L, so {@code L°m} is a Greek letter to recover rather than an exponent — 22 of them in
 * `chem12-part1` chapter 2 alone. An angle keeps its degree sign by the prompt's own convention,
 * and a stray degree standing for a Symbol-font τ is {@link NotationFlags}' business. So only a
 * bracket whose whole content is a dimensional formula — base quantities, each with at most one
 * exponent — is touched, and inside it only the degree signs.
 */
final class DimensionalBrackets {

    /** Any short bracketed span; the content decides whether it is a dimensional formula. */
    private static final Pattern BRACKET = Pattern.compile("\\[([^\\[\\]]{1,40})\\]");

    /** The seven base quantities, each with at most one exponent, separated by spaces. */
    private static final Pattern DIMENSIONS = Pattern.compile(
            "(?:mol|cd|[MLTAK])(?:\\^-?\\d+|°)?(?: +(?:mol|cd|[MLTAK])(?:\\^-?\\d+|°)?)*");

    /** The pages as the load will number them, and one line per paragraph this rewrote. */
    record Applied(List<ExtractedPage> pages, List<String> notes) {
    }

    private DimensionalBrackets() {
    }

    /**
     * Every paragraph of every page, with each rewrite named. A paragraph is addressed the way the
     * extract report addresses one — chapter, page, section — because the load has not numbered
     * them yet.
     */
    static Applied apply(List<ExtractedPage> pages) {
        List<ExtractedPage> out = new ArrayList<>(pages.size());
        List<String> notes = new ArrayList<>();
        for (ExtractedPage page : pages) {
            List<NcertPage.Paragraph> rewritten = new ArrayList<>(page.paragraphs().size());
            boolean changed = false;
            for (NcertPage.Paragraph paragraph : page.paragraphs()) {
                String text = normalise(paragraph.text());
                if (text.equals(paragraph.text())) {
                    rewritten.add(paragraph);
                    continue;
                }
                changed = true;
                rewritten.add(new NcertPage.Paragraph(paragraph.section(), text,
                        paragraph.continuesPreviousPage(), paragraph.figureRefs()));
                notes.add("ch " + page.chapterNo() + " p" + page.page() + " §" + paragraph.section()
                        + ": a zero exponent set as a degree sign, written as the convention has it — "
                        + quoteBrackets(text));
            }
            out.add(changed
                    ? new ExtractedPage(page.chapterNo(), page.page(), page.confidence(), page.aiCallId(),
                            rewritten, page.skipped())
                    : page);
        }
        return new Applied(List.copyOf(out), List.copyOf(notes));
    }

    /** The rewritten brackets themselves, so the report says what changed without the prose around it. */
    private static String quoteBrackets(String text) {
        List<String> found = new ArrayList<>();
        Matcher matcher = BRACKET.matcher(text);
        while (matcher.find()) {
            if (DIMENSIONS.matcher(matcher.group(1).strip()).matches() && matcher.group(1).contains("^0")) {
                found.add(matcher.group());
            }
        }
        return String.join(", ", found);
    }

    /** The text with every degree sign inside a dimensional bracket written as the exponent it is. */
    static String normalise(String text) {
        if (text == null || text.isEmpty() || text.indexOf('°') < 0) {
            return text;
        }
        Matcher matcher = BRACKET.matcher(text);
        StringBuilder out = new StringBuilder();
        while (matcher.find()) {
            String content = matcher.group(1);
            String rewritten = DIMENSIONS.matcher(content.strip()).matches()
                    ? "[" + content.replace("°", "^0") + "]"
                    : matcher.group();
            matcher.appendReplacement(out, Matcher.quoteReplacement(rewritten));
        }
        return matcher.appendTail(out).toString();
    }
}
