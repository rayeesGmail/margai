package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import com.margai.storage.api.ObjectStore;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * {@code ncert load}: the book's JSONL into {@code ncert_paragraphs} (TECH_PLAN §6.3), upserted
 * on the paragraph address, with the coverage percentage PLAN D15's ✅ asks for.
 *
 * <p>Since v3 the loader assigns the paragraph numbers (DECISIONS 2026-09-14). The model returns
 * each paragraph's section and text and, for a page's first paragraph, whether it continues the
 * previous page; the loader counts ¶1, ¶2, ¶3 per section in reading order across pages. Two
 * different paragraphs at one address — the failure three separate D14 runs hit, and that the
 * loader then refused by name — cannot occur any more, so that refusal is gone. A paragraph that
 * runs across a page break appears in the JSONL twice, flagged on the second page, and is joined
 * in page order into the one paragraph it is; taking the last page's half instead would silently
 * drop the first, which is exactly the kind of loss a spot-check of 20 paragraphs might miss.
 */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Upsert the book's extracted paragraphs into ncert_paragraphs; reports coverage per chapter.")
class NcertLoadCommand extends NcertBookCommand {

    /** {@code ncert_paragraphs.section} is VARCHAR(16); a printed section is "7" or "7.9.2". */
    static final int SECTION_MAX_LENGTH = 16;

    private static final Pattern SECTION = Pattern.compile("\\d{1,2}(\\.\\d{1,3})*");

    private final ObjectStore content;
    private final CurriculumImport imports;

    NcertLoadCommand(ObjectStore content, CurriculumImport imports, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        report.line("content store: " + content.describe());
        String jsonlKey = ContentKeys.extract(definition.code(), language);
        if (!content.exists(jsonlKey)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "no extraction at " + jsonlKey + " — run `ncert extract` first");
        }
        Set<Short> chapters = new TreeSet<>();
        selected.forEach(chapter -> chapters.add(chapter.no()));
        List<ExtractedPage> pages = ExtractJsonl.read(jsonlKey, content.get(jsonlKey)).stream()
                .filter(page -> chapters.contains(page.chapterNo()))
                .sorted((left, right) -> left.chapterNo() != right.chapterNo()
                        ? Short.compare(left.chapterNo(), right.chapterNo())
                        : Integer.compare(left.page(), right.page()))
                .toList();

        // Deterministic corrections first, each reported: a re-transcribed tail is cut and a
        // labelled paragraph is never a continuation. Then the numbering and the join, which
        // refuse a continuation that cannot be one (D14, the first full-book load; D15).
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(pages);
        pages = repaired.pages();
        // Then the founder's rulings on what `ncert verify` flagged (D15): the canonical run is
        // frozen, so a defect in it is corrected here, on the record, rather than re-drawn.
        NcertCorrections.Applied corrected = NcertCorrections.apply(pages, corrections(definition, chapters, report));
        pages = corrected.pages();
        // Last, so a correction's span is the one the verify report quoted: the book's own degree
        // sign for a zero exponent, written as the convention has it (D15, 2026-09-17).
        DimensionalBrackets.Applied dimensioned = DimensionalBrackets.apply(pages);
        pages = dimensioned.pages();
        Numbered numbered = number(pages, jsonlKey);
        List<NcertParagraphRow> rows = numbered.rows();
        report.section("page-break repairs to the model's continuation flags (deterministic, each one named)")
                .list(repaired.notes());
        report.section("corrections from " + NcertCorrectionsYamlReader.FILE + " (founder-adjudicated, each one named)")
                .list(corrected.notes())
                .line("rulings on verifier flags that change no text: " + corrected.rulings());
        report.section("zero exponents the book set as a degree sign (deterministic, each one named)")
                .list(dimensioned.notes());
        report.section("figure_refs that are not figure or table labels — dropped")
                .list(numbered.droppedRefs());
        NcertLoadReport result = imports.loadParagraphs(definition.code(), language, rows);

        report.section("ncert_paragraphs")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()),
                        String.valueOf(result.unchanged()))));

        // Named rather than left silent (D15): a load that rewrites a paragraph's words throws
        // away the vector that described the old ones, and a load that deletes an embedded row
        // throws away its vector outright. Both are re-earned by `ncert embed` at the price of the
        // paragraphs involved — but a run that quietly destroys paid work should say so.
        if (result.embeddingsCleared() > 0 || result.embeddedOrphans() > 0) {
            report.section("embeddings this load dropped")
                    .line("run `ncert embed --book " + definition.code() + "` to earn them back")
                    .table(List.of("cleared (text rewritten)", "deleted with their paragraph"),
                            List.of(List.of(String.valueOf(result.embeddingsCleared()),
                                    String.valueOf(result.embeddedOrphans()))));
        }

        List<List<String>> perChapter = new ArrayList<>();
        for (BookDefinition.Chapter chapter : selected) {
            long chapterPages = pages.stream().filter(page -> page.chapterNo() == chapter.no()).count();
            long withText = pages.stream()
                    .filter(page -> page.chapterNo() == chapter.no() && !page.paragraphs().isEmpty()).count();
            int paragraphs = result.perChapter().getOrDefault(chapter.no(), 0);
            perChapter.add(List.of(String.valueOf(chapter.no()), String.valueOf(chapterPages),
                    String.valueOf(withText), String.valueOf(paragraphs), percent(withText, chapterPages)));
        }
        report.section("pages per chapter")
                .table(List.of("chapter", "pages extracted", "pages with text", "paragraphs", "text yield"),
                        perChapter);

        // Free, and it found a real defect class on the first full book: 32 paragraphs of
        // Physics Part-I were one sentence cut at a tile boundary (D14). It lives here rather than
        // in `extract` because this is where the paragraphs exist in printed order, joined.
        List<String> split = SplitSentences.find(rows);
        report.section("paragraphs that begin in the middle of the previous one's sentence")
                .line("a band boundary is not a paragraph boundary; these are where it was read as one")
                .list(split);

        // Coverage divides by the pages `ncert render` produced, not by the pages this JSONL happens
        // to carry: an extraction that stopped at page 40 of 240 must not report itself complete.
        Integer rendered = imports.renderedPages(definition.code(), language);
        long pagesWithText = pages.stream().filter(page -> !page.paragraphs().isEmpty()).count();
        boolean wholeBook = chapters.size() == definition.chapters().size();
        report.section("coverage for the book").table(
                List.of("pages rendered", "pages extracted", "pages with text", "paragraphs", "coverage"),
                List.of(List.of(
                        rendered == null ? "unknown" : String.valueOf(rendered),
                        String.valueOf(pages.size()), String.valueOf(pagesWithText),
                        String.valueOf(result.total()),
                        rendered == null || !wholeBook ? "—" : percent(pages.size(), rendered))));
        if (rendered == null) {
            report.line("coverage unavailable: ncert_books.pages_" + language + " is not set — "
                    + "`ncert render` records it, and a chapter-subset render deliberately leaves it alone");
        } else if (wholeBook && pages.size() < rendered) {
            report.line("INCOMPLETE: " + (rendered - pages.size()) + " rendered pages are not in the "
                    + "extraction — run `ncert extract` again before trusting this load");
        }
        report.section("addresses in the database this extraction no longer carried — deleted")
                .list(result.orphans());
    }

    /**
     * This load's entries of the corrections file, which is optional: until the first adjudication
     * there is nothing to apply. A file that is present is read in full, so a broken entry for another
     * book still fails this load — the file is one founder-owned input, not one per book.
     */
    private List<NcertCorrection> corrections(BookDefinition definition, Set<Short> chapters, Report report) {
        Path file = io.input(NcertCorrectionsYamlReader.FILE);
        if (!Files.isRegularFile(file)) {
            report.line("no " + NcertCorrectionsYamlReader.FILE + " under the inputs: nothing to apply");
            return List.of();
        }
        report.line("corrections: " + file.normalize() + " sha256 " + Report.sha256(file));
        return NcertCorrections.forLoad(NcertCorrectionsYamlReader.read(file), definition.code(), language, chapters);
    }

    /**
     * JSONL pages into numbered paragraph rows. Numbers are assigned here, per chapter and
     * section, in reading order across pages: a section's first paragraph is ¶1 wherever on the
     * page it begins, and a section that resumes after another keeps counting rather than
     * restarting, so no two paragraphs can ever share an address.
     *
     * <p>A page's first paragraph flagged as continuing the previous page is joined onto that
     * page's last paragraph — same address, page order, one space between — and the row then
     * names every page it came from and carries the lowest confidence of them. Only a genuine
     * continuation is joined: something must precede it in the chapter, it must be in the section
     * the previous page ended in, and every page in between must be present and text-free. The
     * gap matters — a paragraph can run from page 10 to page 12 across a full-page figure, and
     * requiring strict adjacency refused that legitimate book (spec-auditor, D14). A flag that
     * fails any of these is refused by name, every one at once, with the pages to re-extract:
     * nothing is written either way, so there is no reason to withhold the rest of the list.
     */
    /** The numbered rows, and the figure_refs entries that were not figure or table labels. */
    record Numbered(List<NcertParagraphRow> rows, List<String> droppedRefs) {
    }

    /**
     * What figure_refs may hold: a figure or a table label as the books print them, singular or
     * plural — a sentence naming several parts at once prints "Figs. 3.15(a) to (d)" or
     * "Figures 4.8(b)", and taking only the singular dropped four real references from phy11-part1
     * (D15, 2026-09-17). An equation number a paragraph cites is still not a figure reference.
     */
    private static final Pattern FIGURE_REF =
            Pattern.compile("^(Fig(?:ure)?s?\\.?|Tables?)\\b.*", Pattern.CASE_INSENSITIVE);

    /**
     * A footnote, which the books set at the foot of the page under a rule. It is the page's last
     * paragraph but never what the next page continues: the body above it is. Before this, four of
     * phy11-part1's nine footnotes had absorbed the following page's opening words — polluting the
     * footnote and truncating the body paragraph, two rows each (D15, 2026-09-17).
     */
    private static final Pattern FOOTNOTE = Pattern.compile("^\\*+\\s");

    static Numbered number(List<ExtractedPage> pages, String jsonlKey) {
        List<Joined> rows = new ArrayList<>();
        List<String> refusals = new ArrayList<>();
        Map<Short, TreeSet<Integer>> redo = new LinkedHashMap<>();
        // (chapter, section) → the last number given out; a resumed section continues its count.
        Map<String, Integer> counters = new HashMap<>();
        short chapter = -1;
        Joined last = null;
        for (ExtractedPage page : pages) {
            if (page.chapterNo() != chapter) {
                chapter = page.chapterNo();
                last = null;
            }
            List<NcertPage.Paragraph> paragraphs = page.paragraphs();
            for (int index = 0; index < paragraphs.size(); index++) {
                NcertPage.Paragraph paragraph = paragraphs.get(index);
                String section = check(page, paragraph, jsonlKey);
                if (index == 0 && paragraph.continuesPreviousPage()) {
                    String why = last == null ? "no paragraph precedes it in chapter " + chapter
                            : !last.section.equals(section) ? "the previous page ended in §" + last.section
                                    + ", not §" + section
                            : gap(pages, chapter, last.lastPage(), page.page());
                    if (why != null) {
                        refusals.add("ch " + chapter + " page " + page.page() + " says its first paragraph (§"
                                + section + ") continues the previous page, but " + why);
                        TreeSet<Integer> affected = redo.computeIfAbsent(chapter, c -> new TreeSet<>());
                        affected.add(page.page());
                        if (last != null) {
                            affected.add(last.lastPage());
                        }
                        continue;
                    }
                    last.add(page, paragraph);
                    continue;
                }
                int paraNo = counters.merge(chapter + " " + section, 1, Integer::sum);
                Joined joined = new Joined(chapter, section, paraNo, page, paragraph);
                rows.add(joined);
                // A footnote is never the paragraph the next page continues, so it never becomes the
                // anchor: the body above it stays the one a continuation joins to.
                if (!FOOTNOTE.matcher(paragraph.text().stripLeading()).find()) {
                    last = joined;
                }
            }
        }
        if (!refusals.isEmpty()) {
            throw new InputFormatException(Path.of(jsonlKey), 0, refusals.size()
                    + " page(s) claim a continuation that cannot be one:\n  "
                    + String.join("\n  ", refusals) + "\nRe-extract every affected page:\n  "
                    + redo.entrySet().stream()
                            .map(entry -> "ncert extract --redo --chapters " + entry.getKey() + " --pages "
                                    + entry.getValue().stream().map(String::valueOf)
                                            .collect(java.util.stream.Collectors.joining(",")))
                            .collect(java.util.stream.Collectors.joining("\n  ")));
        }
        // A model put "Eq. (7.5)" in figure_refs (D15, the Opus run). A rule can ask; this
        // guarantees, and names what it dropped so a wrong label is never silently discarded.
        List<String> dropped = new ArrayList<>();
        List<NcertParagraphRow> result = new ArrayList<>(rows.size());
        for (Joined joined : rows) {
            NcertParagraphRow row = joined.row();
            List<String> kept = row.figureRefs().stream().filter(ref -> FIGURE_REF.matcher(ref.strip()).matches()).toList();
            if (kept.size() == row.figureRefs().size()) {
                result.add(row);
                continue;
            }
            row.figureRefs().stream().filter(ref -> !FIGURE_REF.matcher(ref.strip()).matches())
                    .forEach(ref -> dropped.add(row.address() + ": \"" + ref + "\""));
            result.add(new NcertParagraphRow(row.chapterNo(), row.section(), row.paraNo(), row.text(),
                    row.hasEquations(), kept, row.extraction()));
        }
        return new Numbered(List.copyOf(result), List.copyOf(dropped));
    }

    /**
     * Why these two halves cannot be one paragraph, or null when they can. A continuation attaches
     * to the nearest page that carried text, so every page between them is text-free by
     * construction — a figure page between them is not a break in the paragraph — and the one
     * thing left to check is that each of them is <em>present</em>.
     *
     * <p>An absent page is refused rather than assumed blank: the JSONL legitimately has holes —
     * after an interrupted render, after a targeted {@code --redo --pages}, after an extract
     * resumed over a different {@code --chapters} selection — and treating a hole as a figure page
     * would silently concatenate two unrelated paragraphs, which is the original blocker wearing a
     * different hat (spec-auditor, D14).
     */
    private static String gap(List<ExtractedPage> pages, short chapter, int from, int to) {
        for (int number = from + 1; number < to; number++) {
            int between = number;
            boolean present = pages.stream()
                    .anyMatch(candidate -> candidate.chapterNo() == chapter && candidate.page() == between);
            if (!present) {
                return "page " + between + " is not in the extraction, so whether it broke the paragraph "
                        + "is unknown";
            }
        }
        return null;
    }

    /**
     * The model's own fields, checked before they reach the schema — the same strictness every D13
     * reader applies to a founder's file (DECISIONS 2026-09-12 D13), and for a sharper reason: the
     * section is what the app shows as the anchor a student taps (SPEC §6.3), so a section that
     * does not belong to this chapter is a student sent to the wrong page of a book they are
     * holding. Refusing by name beats a raw constraint violation at the end of a 260-page load.
     *
     * <p>A blank text is refused where the model's output is decoded, since v3, and re-called; the
     * check here is the net under that, for an artefact written by hand or by an older build.
     */
    private static String check(ExtractedPage page, NcertPage.Paragraph paragraph, String jsonlKey) {
        String where = "ch " + page.chapterNo() + " page " + page.page() + ": ";
        // Normalised once and returned, so the address, the stored row and these checks all use the
        // same string: validating the stripped value while keying on the raw one let "7.9 " pass
        // every guard, split a section in two and slip past the collision check (spec-auditor, D14).
        String section = paragraph.section() == null ? "" : paragraph.section().strip();
        if (section.isEmpty()) {
            throw refuse(page, jsonlKey, where + "a paragraph has no section");
        }
        if (section.length() > SECTION_MAX_LENGTH) {
            throw refuse(page, jsonlKey, where + "section '" + section + "' is longer than "
                    + SECTION_MAX_LENGTH + " characters");
        }
        if (!SECTION.matcher(section).matches()) {
            throw refuse(page, jsonlKey, where + "section '" + section + "' is not a printed section number");
        }
        String chapterOfSection = section.contains(".") ? section.substring(0, section.indexOf('.')) : section;
        if (!chapterOfSection.equals(String.valueOf(page.chapterNo()))) {
            throw refuse(page, jsonlKey, where + "section '" + section + "' belongs to chapter " + chapterOfSection
                    + ", not to chapter " + page.chapterNo() + " — the page was read as the wrong chapter");
        }
        if (paragraph.text() == null || paragraph.text().isBlank()) {
            throw refuse(page, jsonlKey, where + "a paragraph of §" + section + " has no text");
        }
        return section;
    }

    /** The remedy names the offending page and its chapter: `--pages N` alone re-extracts page N of every chapter. */
    private static InputFormatException refuse(ExtractedPage page, String jsonlKey, String reason) {
        return new InputFormatException(Path.of(jsonlKey), 0, reason
                + " — re-extract that page (`ncert extract --redo --chapters " + page.chapterNo()
                + " --pages " + page.page() + "`) or fix the prompt");
    }

    /**
     * A ratio the founder reads as a percentage. It is capped at 100 and flagged when the parts
     * exceed the whole — an extraction holding pages a later render no longer produces (a reprint
     * with fewer pages, a stale JSONL) is a state to notice, not to report as 112% coverage.
     */
    private static String percent(long part, long whole) {
        if (whole == 0) {
            return "—";
        }
        return part > whole ? "100% (stale: " + part + " extracted of " + whole + " rendered)"
                : "%.1f%%".formatted(100.0 * part / whole);
    }

    /** One paragraph while its halves are still arriving. */
    private static final class Joined {

        private final short chapterNo;
        private final String section;
        private final int paraNo;
        private final StringBuilder text = new StringBuilder();
        private final List<Integer> pages = new ArrayList<>();
        private final List<Integer> pageStarts = new ArrayList<>();
        private final List<String> figureRefs = new ArrayList<>();
        private BigDecimal confidence;
        private UUID aiCallId;

        private Joined(short chapterNo, String section, int paraNo, ExtractedPage page, NcertPage.Paragraph first) {
            this.chapterNo = chapterNo;
            this.section = section;
            this.paraNo = paraNo;
            add(page, first);
        }

        private int lastPage() {
            return pages.getLast();
        }

        private void add(ExtractedPage page, NcertPage.Paragraph paragraph) {
            if (!text.isEmpty()) {
                text.append(' ');
            }
            // Where this page's part begins, so `ncert verify` can hold each page to its own words (D15).
            pageStarts.add(text.length());
            // Single spaces within a paragraph, as the prompt asks: one model set its displayed
            // equations on their own lines (D15, run 5), and a line break is not a difference.
            text.append(paragraph.text().strip().replaceAll("\\s+", " "));
            pages.add(page.page());
            paragraph.figureRefs().stream().filter(ref -> !figureRefs.contains(ref)).forEach(figureRefs::add);
            if (confidence == null || (page.confidence() != null && page.confidence().compareTo(confidence) < 0)) {
                confidence = page.confidence();
            }
            if (aiCallId == null) {
                aiCallId = page.aiCallId();
            }
        }

        private NcertParagraphRow row() {
            return new NcertParagraphRow(chapterNo, section, (short) paraNo, text.toString(),
                    // Decided from the joined text, once the halves are together: a paragraph
                    // whose equation sits on the second page is still a paragraph with an equation.
                    Equations.present(text.toString()),
                    figureRefs, new ParagraphExtraction(pages, pageStarts, confidence, aiCallId, null));
        }
    }
}
