package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The founder's corrections applied to an extraction's pages (D15): after {@link PageBreakRepairs},
 * before {@code ncert load} numbers and joins, so a corrected page is loaded exactly as an extraction
 * that had read it right would have been. A {@code move} or {@code unprinted} entry (D16) is the exception: it
 * is checked here and named in its place, and the numbering carries it out on whole rows once the page breaks
 * are joined.
 * Each applied correction is one report line; each that cannot be applied refuses the load by name, and
 * the refusal writes nothing.
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
     * The corrected pages, one line per change, how many entries were rulings on verifier flags that
     * change no text, the paragraphs a {@code defer} entry makes wait for their number, the rows a
     * {@code move} entry renumbers, in file order, and the rows an {@code unprinted} entry leaves a number before —
     * or, with {@code after}, after — once per entry.
     */
    record Applied(List<ExtractedPage> pages, List<String> notes, int rulings, Set<Deferred> deferred,
            List<Moved> moves, List<Unprinted> unprinted) {
    }

    /** A paragraph that takes its number after the next one its section starts: its place on its page. */
    record Deferred(short chapter, int page, int index) {
    }

    /**
     * A row numbered right after another, each named by the place its first paragraph has on its page once
     * every page-level entry has applied; {@code at} and {@code after} are the entry's words, for a refusal.
     */
    record Moved(short chapter, int page, int index, int afterPage, int afterIndex, String at, String after) {
    }

    /**
     * A row that takes the number after the English paragraph its edition does not print — or, {@code after}, that
     * leaves the number after its own to it — named by the place its first paragraph has on its page once every
     * page-level entry has applied; {@code at} is the entry's words, for a refusal.
     */
    record Unprinted(short chapter, int page, int index, String at, boolean after) {
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
        List<NcertCorrection> deferrals = new ArrayList<>();
        List<NcertCorrection> moves = new ArrayList<>();
        List<NcertCorrection> gaps = new ArrayList<>();
        int rulings = 0;
        for (NcertCorrection written : corrections) {
            if (!written.kind().changesText()) {
                rulings++;
                continue;
            }
            NcertCorrection entry = new NcertCorrection(written.book(), written.language(), written.chapter(),
                    written.page(), written.kind(), spaced(written.transcribed()), spaced(written.printed()),
                    spaced(written.at()), written.reason(), written.address(), written.removeRef(), written.addRef(),
                    written.flag(), written.section(), spaced(written.after()), written.afterPage(), written.gapAfter());
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
                case defer -> {
                    deferrals.add(entry);
                    yield defer(entry, paragraphs, continuedOnNextPage(out, at));
                }
                case section -> section(entry, paragraphs, out, at);
                case move -> {
                    moves.add(entry);
                    yield move(entry, paragraphs, out, index);
                }
                case unprinted -> {
                    gaps.add(entry);
                    yield unprinted(entry, paragraphs);
                }
                default -> throw new IllegalStateException("not a text-changing kind: " + entry.kind());
            };
            out.set(at, new ExtractedPage(page.chapterNo(), page.page(), page.confidence(), page.aiCallId(),
                    paragraphs, page.skipped()));
            notes.add(note);
        }
        // Placed last: an entry names its paragraph by its words, and a later join or split on the page
        // moves that paragraph up or down it.
        Set<Deferred> deferred = new LinkedHashSet<>();
        for (NcertCorrection entry : deferrals) {
            ExtractedPage page = out.get(index.get(entry.chapter() + "/" + entry.page()));
            deferred.add(new Deferred(page.chapterNo(), page.page(), onlyStart(entry, page.paragraphs())));
        }
        List<Moved> moved = new ArrayList<>();
        for (NcertCorrection entry : moves) {
            List<NcertPage.Paragraph> page = singleSpaced(out.get(index.get(entry.chapter() + "/" + entry.page())));
            List<NcertPage.Paragraph> afterPage = singleSpaced(out.get(index.get(entry.chapter() + "/" + entry.afterPage())));
            moved.add(new Moved(entry.chapter(), entry.page(), onlyStart(entry, page), entry.afterPage(),
                    onlyStart(entry, entry.after(), afterPage), entry.at(), entry.after()));
        }
        List<Unprinted> unprinted = new ArrayList<>();
        for (NcertCorrection entry : gaps) {
            List<NcertPage.Paragraph> page = singleSpaced(out.get(index.get(entry.chapter() + "/" + entry.page())));
            unprinted.add(new Unprinted(entry.chapter(), entry.page(), onlyStart(entry, page), entry.at(), entry.gapAfter()));
        }
        return new Applied(List.copyOf(out), List.copyOf(notes), rulings, Set.copyOf(deferred), List.copyOf(moved),
                List.copyOf(unprinted));
    }

    /**
     * Names the row only: the numbering leaves the number before it — or after it — once the page breaks are joined.
     * It must start a paragraph — a continuation is the rest of another page's row.
     */
    private static String unprinted(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        int at = onlyStart(entry, paragraphs);
        if (at == 0 && paragraphs.getFirst().continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.at() + "\" continues the previous page's row — name the row by its first words");
        }
        String section = entry.where() + " §" + paragraphs.get(at).section().strip();
        if (entry.gapAfter()) {
            return section + ": the English paragraph after the row starting \"" + entry.at() + "\" is not printed in "
                    + "this edition — the number after the row is left to it (" + entry.reason() + ")";
        }
        return section + ": the English paragraph before the row starting \"" + entry.at() + "\" is not printed in "
                + "this edition — the row takes the number after it (" + entry.reason() + ")";
    }

    /**
     * Names the two rows only: the numbering moves the row once the page breaks are joined, so a row that runs
     * across pages moves whole. Both must start a paragraph — a continuation is the rest of another page's row.
     */
    private static String move(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs, List<ExtractedPage> pages,
            Map<String, Integer> index) {
        int first = onlyStart(entry, paragraphs);
        if (first == 0 && paragraphs.getFirst().continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.at() + "\" continues the previous page's row — name the row by its first words");
        }
        Integer afterAt = index.get(entry.chapter() + "/" + entry.afterPage());
        if (afterAt == null) {
            throw refuse(entry, "page " + entry.afterPage() + " is not in the extraction");
        }
        // The page as the entries so far left it: this page's own copy when it is the same page.
        List<NcertPage.Paragraph> afterParagraphs = entry.afterPage() == entry.page() ? paragraphs
                : singleSpaced(pages.get(afterAt));
        int after = onlyStart(entry, entry.after(), afterParagraphs);
        if (after == 0 && afterParagraphs.getFirst().continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.after() + "\" continues the previous page's row — name the row by its first words");
        }
        if (entry.afterPage() == entry.page() && after == first) {
            throw refuse(entry, "a row cannot follow itself");
        }
        return entry.where() + " §" + paragraphs.get(first).section().strip() + ": the row starting \"" + entry.at()
                + "\" is numbered after the row starting \"" + entry.after() + "\" (page " + entry.afterPage() + ") ("
                + entry.reason() + ")";
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
        // The last body paragraph, not the page's last: a footnote below it is never what the next page continues.
        if (at == lastBody(paragraphs) && lastContinued) {
            throw refuse(entry, "page " + (entry.page() + 1) + " continues the paragraph starting \"" + entry.at()
                    + "\" — correct its text instead");
        }
        paragraphs.remove(at);
        return entry.where() + " §" + paragraph.section().strip() + ": the paragraph starting \"" + entry.at()
                + "\" dropped (" + entry.reason() + ")";
    }

    private static String defer(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs, boolean lastContinued) {
        int at = onlyStart(entry, paragraphs);
        NcertPage.Paragraph paragraph = paragraphs.get(at);
        // Half a paragraph cannot wait while the other half is numbered. The half the next page continues is the
        // last body paragraph, not the page's last: a footnote below it is never continued.
        if (paragraph.continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.at() + "\" continues the previous page's paragraph — join or split it first");
        }
        if (at == lastBody(paragraphs) && lastContinued) {
            throw refuse(entry, "page " + (entry.page() + 1) + " continues the paragraph starting \"" + entry.at()
                    + "\" — join or split it first");
        }
        return entry.where() + " §" + paragraph.section().strip() + ": the paragraph starting \"" + entry.at()
                + "\" takes its number after the next paragraph of its section (" + entry.reason() + ")";
    }

    /**
     * The paragraph starting {@code at} and every later paragraph of its section take the English number, on its
     * page and on every later page of the chapter; the later pages are changed in {@code pages} directly, their
     * words untouched. Named at the heading it re-addresses the whole section; named at a later paragraph, the
     * paragraphs before it keep their number — the Hindi printed that one with no heading of its own (2026-10-17).
     * It must start a row: a continuation is the rest of another page's row, which keeps its section.
     */
    private static String section(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs, List<ExtractedPage> pages,
            int at) {
        int first = onlyStart(entry, paragraphs);
        String from = paragraphs.get(first).section().strip();
        String to = entry.section();
        if (from.equals(to)) {
            throw refuse(entry, "\"" + entry.at() + "\" is in §" + to + " already");
        }
        if (first == 0 && paragraphs.getFirst().continuesPreviousPage()) {
            throw refuse(entry, "\"" + entry.at() + "\" continues the previous page's row — name the row by its first words");
        }
        // The next page's continuation is the rest of this page's last body paragraph: named at a footnote below it,
        // the entry would move the continuation and leave its first half behind.
        int body = lastBody(paragraphs);
        if (body >= 0 && body < first && paragraphs.get(body).section().strip().equals(from)
                && continuedOnNextPage(pages, at)) {
            throw refuse(entry, "the next page continues a paragraph of §" + from + " before \"" + entry.at()
                    + "\" — name that paragraph or an earlier one");
        }
        int moved = 0;
        for (int index = first; index < paragraphs.size(); index++) {
            if (paragraphs.get(index).section().strip().equals(from)) {
                paragraphs.set(index, inSection(paragraphs.get(index), to));
                moved++;
            }
        }
        for (int after = at + 1; after < pages.size() && pages.get(after).chapterNo() == entry.chapter(); after++) {
            ExtractedPage page = pages.get(after);
            List<NcertPage.Paragraph> later = new ArrayList<>(page.paragraphs());
            for (int index = 0; index < later.size(); index++) {
                if (later.get(index).section().strip().equals(from)) {
                    later.set(index, inSection(later.get(index), to));
                    moved++;
                }
            }
            pages.set(after, new ExtractedPage(page.chapterNo(), page.page(), page.confidence(), page.aiCallId(), later,
                    page.skipped()));
        }
        return entry.where() + " §" + from + ": the English numbers it §" + to + ", from \"" + entry.at() + "\" on — "
                + moved + " paragraph(s) (" + entry.reason() + ")";
    }

    private static NcertPage.Paragraph inSection(NcertPage.Paragraph paragraph, String section) {
        return new NcertPage.Paragraph(section, paragraph.text(), paragraph.continuesPreviousPage(), paragraph.figureRefs());
    }

    /** The index of the one paragraph starting with the entry's words, refusing any other count. */
    private static int onlyStart(NcertCorrection entry, List<NcertPage.Paragraph> paragraphs) {
        return onlyStart(entry, entry.at(), paragraphs);
    }

    private static int onlyStart(NcertCorrection entry, String words, List<NcertPage.Paragraph> paragraphs) {
        List<Integer> starting = new ArrayList<>();
        for (int at = 0; at < paragraphs.size(); at++) {
            if (paragraphs.get(at).text().strip().startsWith(words)) {
                starting.add(at);
            }
        }
        if (starting.size() != 1) {
            throw refuse(entry, starting.size() + " paragraphs start with \"" + words + "\" there, not one");
        }
        return starting.getFirst();
    }

    /** A page's paragraphs single-spaced as an entry's words are, for matching a page no entry has touched yet. */
    private static List<NcertPage.Paragraph> singleSpaced(ExtractedPage page) {
        return page.paragraphs().stream()
                .map(paragraph -> new NcertPage.Paragraph(paragraph.section(), singleSpaced(paragraph.text()),
                        paragraph.continuesPreviousPage(), paragraph.figureRefs()))
                .toList();
    }

    /**
     * The index of the page's last paragraph that is not a footnote: the one the next page continues, when it
     * continues one. A footnote set below it is never that paragraph (NcertLoadCommand's continuation rule).
     */
    private static int lastBody(List<NcertPage.Paragraph> paragraphs) {
        for (int at = paragraphs.size() - 1; at >= 0; at--) {
            if (!NcertLoadCommand.footnote(paragraphs.get(at).text())) {
                return at;
            }
        }
        return -1;
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
