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
        for (NcertCorrection written : corrections) {
            if (!written.kind().changesText()) {
                rulings++;
                continue;
            }
            NcertCorrection entry = new NcertCorrection(written.book(), written.language(), written.chapter(),
                    written.page(), written.kind(), spaced(written.transcribed()), spaced(written.printed()),
                    spaced(written.at()), written.reason(), written.address(), written.removeRef(), written.addRef());
            Integer at = index.get(entry.chapter() + "/" + entry.page());
            if (at == null) {
                throw refuse(entry, "that page is not in the extraction");
            }
            ExtractedPage page = out.get(at);
            // Single-spaced as the load will store them, so a span copied from a verify report — which
            // quotes the loaded row — is found where the extraction had a line break or a double space.
            List<NcertPage.Paragraph> paragraphs = new ArrayList<>(page.paragraphs().stream()
                    .map(paragraph -> new NcertPage.Paragraph(paragraph.section(), singleSpaced(paragraph.text()),
                            paragraph.continuesPreviousPage(), paragraph.figureRefs()))
                    .toList());
            String note = switch (entry.kind()) {
                case text -> replace(entry, paragraphs);
                case join -> join(entry, paragraphs, hasTextBefore(out, at));
                case split -> split(entry, paragraphs);
                case figure_ref -> figureRef(entry, paragraphs);
                case drop -> drop(entry, paragraphs, continuedOnNextPage(out, at));
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
        int at = onlyStart(entry, paragraphs);
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

    private static String figureRef(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        int found = onlyOccurrence(entry, entry.at(), paragraphs);
        NcertPage.Paragraph paragraph = paragraphs.get(found);
        List<String> refs = new ArrayList<>(paragraph.figureRefs());
        String label = entry.removeRef() != null ? entry.removeRef().strip() : entry.addRef().strip();
        boolean carried = refs.stream().anyMatch(ref -> ref.strip().equals(label));
        String change;
        if (entry.removeRef() != null) {
            if (!carried) {
                throw refuse(entry, "the paragraph holding \"" + entry.at() + "\" carries no \"" + label + "\"");
            }
            refs.removeIf(ref -> ref.strip().equals(label));
            change = "removed from";
        } else {
            if (carried) {
                throw refuse(entry, "the paragraph holding \"" + entry.at() + "\" already carries \"" + label + "\"");
            }
            refs.add(label);
            change = "added to";
        }
        paragraphs.set(found, new NcertPage.Paragraph(paragraph.section(), paragraph.text(),
                paragraph.continuesPreviousPage(), refs));
        return entry.where() + " §" + paragraph.section().strip() + ": \"" + label + "\" " + change
                + " the paragraph holding \"" + entry.at() + "\" (" + entry.reason() + ")";
    }

    private static String drop(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs, boolean lastContinued) {
        int at = onlyStart(entry, paragraphs);
        NcertPage.Paragraph paragraph = paragraphs.get(at);
        // Dropping part of a paragraph would leave the other part joined to its neighbour; that is a
        // text correction, not a drop.
        if (paragraph.continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.at() + "\" continues the previous page's paragraph — correct its text instead");
        }
        if (at == paragraphs.size() - 1 && lastContinued) {
            throw refuse(entry, "page " + (entry.page() + 1) + " continues the paragraph starting \"" + entry.at()
                    + "\" — correct its text instead");
        }
        paragraphs.remove(at);
        return entry.where() + " §" + paragraph.section().strip() + ": the paragraph starting \"" + entry.at()
                + "\" dropped (" + entry.reason() + ")";
    }

    /** The index of the one paragraph starting with the entry's words, refusing any other count. */
    private static int onlyStart(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        List<Integer> starting = new ArrayList<>();
        for (int at = 0; at < paragraphs.size(); at++) {
            if (paragraphs.get(at).text().strip().startsWith(entry.at())) {
                starting.add(at);
            }
        }
        if (starting.size() != 1) {
            throw refuse(entry, starting.size() + " paragraphs start with \"" + entry.at() + "\" there, not one");
        }
        return starting.getFirst();
    }

    /** Whether the next page of the chapter that carries text opens by continuing this page's last paragraph. */
    private static boolean continuedOnNextPage(List<ExtractedPage> pages, int at) {
        ExtractedPage page = pages.get(at);
        for (int after = at + 1; after < pages.size(); after++) {
            ExtractedPage next = pages.get(after);
            if (next.chapterNo() != page.chapterNo()) {
                return false;
            }
            if (!next.paragraphs().isEmpty()) {
                return next.paragraphs().getFirst().continuesPreviousPage();
            }
        }
        return false;
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

    private static String singleSpaced(String text) {
        return text.strip().replaceAll("\\s+", " ");
    }

    /** An entry's span single-spaced the same way; inner spaces only, since a span's edges are part of it. */
    private static String spaced(String span) {
        return span == null ? null : span.replaceAll("\\s+", " ");
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
