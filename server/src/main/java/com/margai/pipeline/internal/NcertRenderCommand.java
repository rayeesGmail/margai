package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.storage.api.ObjectStore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert render}: every page of every selected chapter as a PNG in the content bucket
 * (TECH_PLAN §6.3), the input the VISION tier reads at {@code ncert extract} — NCERT's two-column
 * layout, its equations and the legacy Hindi fonts defeat text extractors (§6.1).
 *
 * <p>Idempotent by the key: a page already in the bucket is not rendered again, so a run
 * interrupted halfway resumes for the cost of one listing per chapter. The total is written back
 * to {@code ncert_books.pages_*}, which is what the coverage percentage at {@code ncert load}
 * divides by.
 *
 * <p>{@code --redo} exists because that skip is a liability when the pages already there are
 * wrong: the first real run rendered with no JPEG2000 decoder on the classpath, which PDFBox
 * answers by drawing the page without the image rather than failing, so the bucket held pages
 * whose figures were blank and a plain re-run would have kept every one of them (D14).
 */
@Component
@Profile("pipeline")
@Command(name = "render", mixinStandardHelpOptions = true,
        description = "Render each source PDF page to a PNG in the content bucket; skips pages already there.")
class NcertRenderCommand extends NcertBookCommand {

    @Option(names = "--redo", description = "Render pages again even when they are already in the bucket.")
    boolean redo;

    private final ObjectStore content;
    private final CurriculumImport imports;
    private final PdfPageRenderer renderer;

    NcertRenderCommand(ObjectStore content, CurriculumImport imports, PipelineProperties properties, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
        this.renderer = new PdfPageRenderer(properties.renderDpi());
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        report.line("content store: " + content.describe());

        List<List<String>> rows = new ArrayList<>();
        int rendered = 0;
        int skipped = 0;
        int pages = 0;
        for (BookDefinition.Chapter chapter : selected) {
            String sourceKey = definition.sourceKey(language, chapter);
            String prefix = ContentKeys.pagePrefix(definition.code(), language, chapter.no());
            Set<String> existing = redo ? Set.of() : new HashSet<>(content.list(prefix));
            byte[] pdf = content.get(sourceKey);

            int[] chapterRendered = {0};
            int chapterPages = renderer.render(pdf,
                    page -> existing.contains(ContentKeys.page(definition.code(), language, chapter.no(), page)),
                    (png, page) -> {
                        content.put(ContentKeys.page(definition.code(), language, chapter.no(), page), png,
                                PdfPageRenderer.MEDIA_TYPE);
                        chapterRendered[0]++;
                    });
            int chapterSkipped = chapterPages - chapterRendered[0];
            rendered += chapterRendered[0];
            skipped += chapterSkipped;
            pages += chapterPages;
            rows.add(List.of(String.valueOf(chapter.no()), chapter.file(language),
                    String.valueOf(chapterPages), String.valueOf(chapterRendered[0]),
                    String.valueOf(chapterSkipped)));
        }

        report.section("pages per chapter")
                .table(List.of("chapter", "source", "pages", "rendered", "already there"), rows);
        report.section("total").table(List.of("chapters", "pages", "rendered", "skipped"),
                List.of(List.of(String.valueOf(selected.size()), String.valueOf(pages),
                        String.valueOf(rendered), String.valueOf(skipped))));

        if (chapters == null || chapters.isEmpty()) {
            imports.recordRenderedPages(definition.code(), language, pages);
            report.line("ncert_books.pages_" + language + " = " + pages);
        } else {
            report.line("ncert_books.pages_" + language + " left alone: this run rendered a chapter subset");
        }
    }
}
