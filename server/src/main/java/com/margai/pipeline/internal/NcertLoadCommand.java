package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import com.margai.storage.api.ObjectStore;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
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
 * <p>One thing here is not a simple mapping. A paragraph that runs across a page break appears in
 * the JSONL twice — the extraction prompt deliberately gives the continuation the number it
 * already had, so that the two halves share one address — and the loader joins them in page
 * order into the one paragraph they are. Taking the last page's half instead would silently drop
 * the first, which is exactly the kind of loss a spot-check of 20 paragraphs might miss.
 */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Upsert the book's extracted paragraphs into ncert_paragraphs; reports coverage per chapter.")
class NcertLoadCommand extends NcertBookCommand {

    /** {@code ncert_paragraphs.section} is VARCHAR(16); a printed section is "7" or "7.9.2". */
    static final int SECTION_MAX_LENGTH = 16;

    /** A page holds a handful of paragraphs; a number beyond this is a misread, not a long page. */
    static final int PARA_NO_MAX = 200;

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
        List<ExtractedPage> pages = ExtractJsonl.read(content.get(jsonlKey)).stream()
                .filter(page -> chapters.contains(page.chapterNo()))
                .sorted((left, right) -> left.chapterNo() != right.chapterNo()
                        ? Short.compare(left.chapterNo(), right.chapterNo())
                        : Integer.compare(left.page(), right.page()))
                .toList();

        List<NcertParagraphRow> rows = join(pages);
        NcertLoadReport result = imports.loadParagraphs(definition.code(), language, rows);

        report.section("ncert_paragraphs")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()),
                        String.valueOf(result.unchanged()))));

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
        report.section("addresses in the database this extraction no longer carries").list(result.orphans());
    }

    /**
     * JSONL pages into paragraph rows, joining the halves of a paragraph that straddles a page
     * break: same address, page order, one space between. The row's extraction then names every
     * page it came from, and carries the lowest confidence of them — a paragraph is only as
     * trustworthy as the least certain page it was read from.
     *
     * <p>Only a genuine continuation is joined: the same address, as that page's first paragraph,
     * with nothing but text-free pages in between. The gap matters — a paragraph can run from page
     * 10 to page 12 across a full-page figure, and requiring strict adjacency refused that
     * legitimate book (spec-auditor, D14). Any other collision means the extraction numbered two
     * different paragraphs the same — the failure mode of a model that restarts at 1 on every page
     * — and it fails the run by name instead of quietly concatenating unrelated text, which is
     * what a 20-paragraph spot check would not catch.
     */
    static List<NcertParagraphRow> join(List<ExtractedPage> pages) {
        Map<String, Joined> byAddress = new LinkedHashMap<>();
        for (ExtractedPage page : pages) {
            List<NcertPage.Paragraph> paragraphs = page.paragraphs();
            for (int index = 0; index < paragraphs.size(); index++) {
                NcertPage.Paragraph paragraph = paragraphs.get(index);
                String section = check(page, paragraph);
                String address = page.chapterNo() + " " + section + " " + paragraph.paraNo();
                Joined joined = byAddress.get(address);
                if (joined == null) {
                    byAddress.put(address, new Joined(page.chapterNo(), section, paragraph, page));
                    continue;
                }
                String why = index != 0 ? "it is not that page's first paragraph"
                        : gap(pages, page.chapterNo(), joined.lastPage(), page.page());
                if (why != null) {
                    throw new InputFormatException(Path.of(ContentKeys.EXTRACT), 0,
                            "ch " + page.chapterNo() + " §" + section + " ¶" + paragraph.paraNo()
                                    + " is claimed by page " + joined.lastPage() + " and page " + page.page()
                                    + ", which cannot be one paragraph continuing across a page break: " + why
                                    + " — re-extract those pages (`ncert extract --redo --chapters "
                                    + page.chapterNo() + " --pages " + joined.lastPage() + "," + page.page() + "`)");
                }
                joined.add(page, paragraph);
            }
        }
        return byAddress.values().stream().map(Joined::row).toList();
    }

    /**
     * Why these two halves cannot be one paragraph, or null when they can. A figure page between
     * them is not a break in the paragraph; a page of prose between them means two different
     * paragraphs were given one number.
     *
     * <p>Every page in between must be <em>present and empty</em>. An absent page is refused rather
     * than assumed blank: the JSONL legitimately has holes — after an interrupted render, after a
     * targeted {@code --redo --pages}, after an extract resumed over a different {@code --chapters}
     * selection — and treating a hole as a figure page would silently concatenate two unrelated
     * paragraphs, which is the original blocker wearing a different hat (spec-auditor, D14).
     */
    private static String gap(List<ExtractedPage> pages, short chapter, int from, int to) {
        for (int number = from + 1; number < to; number++) {
            int between = number;
            ExtractedPage page = pages.stream()
                    .filter(candidate -> candidate.chapterNo() == chapter && candidate.page() == between)
                    .findFirst().orElse(null);
            if (page == null) {
                return "page " + between + " is not in the extraction, so whether it broke the paragraph "
                        + "is unknown";
            }
            if (!page.paragraphs().isEmpty()) {
                return "page " + between + " between them carries text";
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
     */
    private static String check(ExtractedPage page, NcertPage.Paragraph paragraph) {
        String where = "ch " + page.chapterNo() + " page " + page.page() + ": ";
        // Normalised once and returned, so the address, the stored row and these checks all use the
        // same string: validating the stripped value while keying on the raw one let "7.9 " pass
        // every guard, split a section in two and slip past the collision check (spec-auditor, D14).
        String section = paragraph.section() == null ? "" : paragraph.section().strip();
        if (section.isEmpty()) {
            throw refuse(page, where + "a paragraph has no section");
        }
        if (section.length() > SECTION_MAX_LENGTH) {
            throw refuse(page, where + "section '" + section + "' is longer than "
                    + SECTION_MAX_LENGTH + " characters");
        }
        if (!SECTION.matcher(section).matches()) {
            throw refuse(page, where + "section '" + section + "' is not a printed section number");
        }
        String chapterOfSection = section.contains(".") ? section.substring(0, section.indexOf('.')) : section;
        if (!chapterOfSection.equals(String.valueOf(page.chapterNo()))) {
            throw refuse(page, where + "section '" + section + "' belongs to chapter " + chapterOfSection
                    + ", not to chapter " + page.chapterNo() + " — the page was read as the wrong chapter");
        }
        if (paragraph.paraNo() < 1 || paragraph.paraNo() > PARA_NO_MAX) {
            throw refuse(page, where + "§" + section + " has paragraph number " + paragraph.paraNo());
        }
        if (paragraph.text() == null || paragraph.text().isBlank()) {
            throw refuse(page, where + "§" + section + " ¶" + paragraph.paraNo() + " has no text");
        }
        return section;
    }

    /** The remedy names the offending page and its chapter: `--pages N` alone re-extracts page N of every chapter. */
    private static InputFormatException refuse(ExtractedPage page, String reason) {
        return new InputFormatException(Path.of(ContentKeys.EXTRACT), 0, reason
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
        private final NcertPage.Paragraph first;
        private final StringBuilder text = new StringBuilder();
        private final List<Integer> pages = new ArrayList<>();
        private final List<String> figureRefs = new ArrayList<>();
        private BigDecimal confidence;
        private UUID aiCallId;
        private boolean hasEquations;

        private Joined(short chapterNo, String section, NcertPage.Paragraph first, ExtractedPage page) {
            this.chapterNo = chapterNo;
            this.section = section;
            this.first = first;
            add(page, first);
        }

        private int lastPage() {
            return pages.getLast();
        }

        private void add(ExtractedPage page, NcertPage.Paragraph paragraph) {
            if (!text.isEmpty()) {
                text.append(' ');
            }
            text.append(paragraph.text().strip());
            pages.add(page.page());
            paragraph.figureRefs().stream().filter(ref -> !figureRefs.contains(ref)).forEach(figureRefs::add);
            hasEquations |= paragraph.hasEquations();
            if (confidence == null || (page.confidence() != null && page.confidence().compareTo(confidence) < 0)) {
                confidence = page.confidence();
            }
            if (aiCallId == null) {
                aiCallId = page.aiCallId();
            }
        }

        private NcertParagraphRow row() {
            return new NcertParagraphRow(chapterNo, section, (short) first.paraNo(), text.toString(),
                    hasEquations, figureRefs, new ParagraphExtraction(pages, confidence, aiCallId));
        }
    }
}
