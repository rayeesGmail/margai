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
        report.section("coverage per chapter")
                .table(List.of("chapter", "pages extracted", "pages with text", "paragraphs", "coverage"), perChapter);

        long pagesWithText = pages.stream().filter(page -> !page.paragraphs().isEmpty()).count();
        report.section("coverage for the book").table(
                List.of("pages extracted", "pages with text", "paragraphs", "coverage"),
                List.of(List.of(String.valueOf(pages.size()), String.valueOf(pagesWithText),
                        String.valueOf(result.total()), percent(pagesWithText, pages.size()))));
        report.section("addresses in the database this extraction no longer carries").list(result.orphans());
    }

    /**
     * JSONL pages into paragraph rows, joining the halves of a paragraph that straddles a page
     * break: same address, page order, one space between. The row's extraction then names every
     * page it came from, and carries the lowest confidence of them — a paragraph is only as
     * trustworthy as the least certain page it was read from.
     */
    static List<NcertParagraphRow> join(List<ExtractedPage> pages) {
        Map<String, Joined> byAddress = new LinkedHashMap<>();
        for (ExtractedPage page : pages) {
            for (NcertPage.Paragraph paragraph : page.paragraphs()) {
                String address = page.chapterNo() + " " + paragraph.section() + " " + paragraph.paraNo();
                byAddress.computeIfAbsent(address, key -> new Joined(page.chapterNo(), paragraph))
                        .add(page, paragraph);
            }
        }
        return byAddress.values().stream().map(Joined::row).toList();
    }

    private static String percent(long part, long whole) {
        return whole == 0 ? "—" : "%.1f%%".formatted(100.0 * part / whole);
    }

    /** One paragraph while its halves are still arriving. */
    private static final class Joined {

        private final short chapterNo;
        private final NcertPage.Paragraph first;
        private final StringBuilder text = new StringBuilder();
        private final List<Integer> pages = new ArrayList<>();
        private final List<String> figureRefs = new ArrayList<>();
        private BigDecimal confidence;
        private UUID aiCallId;
        private boolean hasEquations;

        private Joined(short chapterNo, NcertPage.Paragraph first) {
            this.chapterNo = chapterNo;
            this.first = first;
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
            return new NcertParagraphRow(chapterNo, first.section(), (short) first.paraNo(), text.toString(),
                    hasEquations, figureRefs, new ParagraphExtraction(pages, confidence, aiCallId));
        }
    }
}
