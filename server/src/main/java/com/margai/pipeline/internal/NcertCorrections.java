package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The founder's corrections applied to an extraction's pages (D15): after {@link PageBreakRepairs},
 * before {@code ncert load} numbers and joins, so a corrected page is loaded exactly as an extraction
 * that had read it right would have been. Each applied correction is one report line; each that
 * cannot be applied refuses the load by name, and the refusal writes nothing.
 *
 * <p>A span must be found on its page exactly once. Not at all means the page is not what the entry
 * was written against — a re-extraction, a wrong page number — and twice means the entry does not say
 * which of the two it means. Either way applying it would be a guess.
 *
 * <p>Entries apply in file order, each to the page as the entries before it left it.
 */
final class NcertCorrections {

    private NcertCorrections() {
    }

    /**
     * The corrected pages, one line per change, and how many entries were rulings on verifier
     * flags that change no text.
     */
    record Applied(List<ExtractedPage> pages, List<String> notes, int rulings) {
    }

    /** The entries this load carries: its book, its edition, its chapters. */
    static List<NcertCorrection> forLoad(List<NcertCorrection> file, String book, BookLanguage language,
            Collection<Short> chapters) {
        return file.stream()
                .filter(entry -> entry.book().equals(book) && entry.language() == language
                        && chapters.contains(entry.chapter()))
                .toList();
    }

    static Applied apply(List<ExtractedPage> pages, List<NcertCorrection> corrections) {
        List<ExtractedPage> out = new ArrayList<>(pages);
        Map<String, Integer> index = new HashMap<>();
        for (int at = 0; at < out.size(); at++) {
            index.put(out.get(at).chapterNo() + "/" + out.get(at).page(), at);
        }
        List<String> notes = new ArrayList<>();
        int rulings = 0;
        for (NcertCorrection entry : corrections) {
            if (!entry.kind().changesText()) {
                rulings++;
                continue;
            }
            Integer at = index.get(entry.chapter() + "/" + entry.page());
            if (at == null) {
                throw refuse(entry, "that page is not in the extraction");
            }
            ExtractedPage page = out.get(at);
            List<NcertPage.Paragraph> paragraphs = new ArrayList<>(page.paragraphs());
            String note = switch (entry.kind()) {
                case text -> replace(entry, paragraphs);
                case join -> join(entry, paragraphs, hasTextBefore(out, at));
                case split -> split(entry, paragraphs);
                default -> throw new IllegalStateException("not a text-changing kind: " + entry.kind());
            };
            out.set(at, new ExtractedPage(page.chapterNo(), page.page(), page.confidence(), page.aiCallId(),
                    paragraphs, page.skipped()));
            notes.add(note);
        }
        return new Applied(List.copyOf(out), List.copyOf(notes), rulings);
    }

    private static String replace(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        int found = onlyOccurrence(entry, entry.transcribed(), paragraphs);
        NcertPage.Paragraph paragraph = paragraphs.get(found);
        paragraphs.set(found, new NcertPage.Paragraph(paragraph.section(),
                paragraph.text().replace(entry.transcribed(), entry.printed()),
                paragraph.continuesPreviousPage(), paragraph.figureRefs()));
        return entry.where() + " §" + paragraph.section().strip() + ": \"" + entry.transcribed() + "\" → \""
                + entry.printed() + "\" (" + entry.reason() + ")";
    }

    private static String join(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs, boolean textBefore) {
        List<Integer> starting = new ArrayList<>();
        for (int at = 0; at < paragraphs.size(); at++) {
            if (paragraphs.get(at).text().strip().startsWith(entry.at())) {
                starting.add(at);
            }
        }
        if (starting.size() != 1) {
            throw refuse(entry, starting.size() + " paragraphs start with \"" + entry.at() + "\" there, not one");
        }
        int at = starting.getFirst();
        NcertPage.Paragraph paragraph = paragraphs.get(at);
        String section = paragraph.section().strip();
        if (at == 0) {
            if (!textBefore) {
                throw refuse(entry, "\"" + entry.at() + "\" has no paragraph before it to join");
            }
            // The load joins a page's first paragraph onto the previous page's last on this flag, and
            // refuses it there by name if the two cannot be one paragraph.
            paragraphs.set(0, new NcertPage.Paragraph(paragraph.section(), paragraph.text(), true,
                    paragraph.figureRefs()));
            return entry.where() + " §" + section + ": \"" + entry.at() + "\" continues the previous page's paragraph ("
                    + entry.reason() + ")";
        }
        NcertPage.Paragraph before = paragraphs.get(at - 1);
        List<String> refs = new ArrayList<>(before.figureRefs());
        paragraph.figureRefs().stream().filter(ref -> !refs.contains(ref)).forEach(refs::add);
        paragraphs.set(at - 1, new NcertPage.Paragraph(before.section(),
                before.text().strip() + " " + paragraph.text().strip(), before.continuesPreviousPage(), refs));
        paragraphs.remove(at);
        return entry.where() + " §" + section + ": \"" + entry.at() + "\" joined onto the paragraph before it ("
                + entry.reason() + ")";
    }

    private static String split(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        int found = onlyOccurrence(entry, entry.at(), paragraphs);
        NcertPage.Paragraph paragraph = paragraphs.get(found);
        String section = paragraph.section().strip();
        String text = paragraph.text().strip();
        int offset = text.indexOf(entry.at());
        if (offset == 0) {
            if (found == 0 && paragraph.continuesPreviousPage()) {
                paragraphs.set(0, new NcertPage.Paragraph(paragraph.section(), paragraph.text(), false,
                        paragraph.figureRefs()));
                return entry.where() + " §" + section + ": \"" + entry.at()
                        + "\" starts a new paragraph, not the previous page's (" + entry.reason() + ")";
            }
            throw refuse(entry, "a paragraph already starts at \"" + entry.at() + "\"");
        }
        String first = text.substring(0, offset).strip();
        String second = text.substring(offset).strip();
        List<String> firstRefs = new ArrayList<>();
        List<String> secondRefs = new ArrayList<>();
        for (String ref : paragraph.figureRefs()) {
            (mentions(second, ref) && !mentions(first, ref) ? secondRefs : firstRefs).add(ref);
        }
        paragraphs.set(found, new NcertPage.Paragraph(paragraph.section(), first,
                paragraph.continuesPreviousPage(), firstRefs));
        paragraphs.add(found + 1, new NcertPage.Paragraph(paragraph.section(), second, false, secondRefs));
        return entry.where() + " §" + section + ": a new paragraph starts at \"" + entry.at() + "\" ("
                + entry.reason() + ")";
    }

    /** The index of the one paragraph holding the span, refusing when the page holds it other than once. */
    private static int onlyOccurrence(NcertCorrection entry, String span, List<NcertPage.Paragraph> paragraphs) {
        int count = 0;
        int where = -1;
        for (int at = 0; at < paragraphs.size(); at++) {
            String text = paragraphs.get(at).text();
            for (int from = text.indexOf(span); from >= 0; from = text.indexOf(span, from + 1)) {
                count++;
                where = at;
            }
        }
        if (count != 1) {
            throw refuse(entry, "\"" + span + "\" occurs " + count + " times there, not once");
        }
        return where;
    }

    private static boolean hasTextBefore(List<ExtractedPage> pages, int at) {
        ExtractedPage page = pages.get(at);
        for (int before = at - 1; before >= 0; before--) {
            if (pages.get(before).chapterNo() != page.chapterNo()) {
                return false;
            }
            if (!pages.get(before).paragraphs().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean mentions(String text, String ref) {
        return FigureLabels.of(ref)
                .map(label -> FigureLabels.in(text).stream().anyMatch(mention -> mention.base().equals(label.base())))
                .orElse(false);
    }

    private static InputFormatException refuse(NcertCorrection entry, String reason) {
        return new InputFormatException(Path.of(NcertCorrectionsYamlReader.FILE), 0,
                entry.kind() + " correction on " + entry.where() + ": " + reason
                        + " — fix the entry against the page, or remove it");
    }
}
