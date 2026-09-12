package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.ImagePart;
import com.margai.ai.tasks.NcertPage;
import com.margai.ai.tasks.NcertPageExtractor;
import com.margai.ai.tasks.PreviousPage;
import com.margai.storage.api.ObjectStore;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert extract}: every rendered page through the VISION tier into
 * {@code extract/{book}/{lang}.jsonl} (TECH_PLAN §6.3). This is the command that spends — one
 * model call per page — so three things matter as much as the extraction itself.
 *
 * <p>It resumes: the existing JSONL is read first and a page already in it is not called for
 * again, so an interrupted book costs nothing to finish. It flushes every
 * {@code margai.pipeline.extract-batch-size} pages, so an interruption loses at most that many.
 * And it reports the run's cost from the ledger (§10.5), not from its own arithmetic.
 */
@Component
@Profile("pipeline")
@Command(name = "extract", mixinStandardHelpOptions = true,
        description = "Read every rendered page with the VISION tier into the book's JSONL; resumes where it stopped.")
class NcertExtractCommand extends NcertBookCommand {

    /** Below this the page is listed in the report for the founder to look at (§6.3). */
    static final BigDecimal LOW_CONFIDENCE = new BigDecimal("0.80");

    private static final Logger log = LoggerFactory.getLogger(NcertExtractCommand.class);

    @Option(names = "--pages", paramLabel = "N[,N…]", split = ",",
            description = "Only these page numbers within each selected chapter (default: every page).")
    List<Integer> pages;

    @Option(names = "--redo", description = "Call again for pages already in the JSONL, replacing them.")
    boolean redo;

    private final ObjectStore content;
    private final NcertPageExtractor extract;
    private final AiSpend spend;
    private final PipelineProperties properties;

    NcertExtractCommand(ObjectStore content, NcertPageExtractor extract, AiSpend spend,
            PipelineProperties properties, Reports reports) {
        super(reports);
        this.content = content;
        this.extract = extract;
        this.spend = spend;
        this.properties = properties;
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        report.line("content store: " + content.describe());
        String runId = "pipeline-ncert-extract-" + UUID.randomUUID();
        report.line("request id: " + runId);
        AiCallContext ctx = AiCallContext.system(runId);

        String jsonlKey = ContentKeys.extract(definition.code(), language);
        Map<String, ExtractedPage> done = new LinkedHashMap<>();
        if (content.exists(jsonlKey)) {
            ExtractJsonl.read(content.get(jsonlKey)).forEach(page -> done.put(page.address(), page));
        }
        int alreadyDone = done.size();

        List<List<String>> perChapter = new ArrayList<>();
        List<String> lowConfidence = new ArrayList<>();
        int called = 0;
        int skipped = 0;
        for (BookDefinition.Chapter chapter : selected) {
            List<String> pageKeys = content.list(ContentKeys.pagePrefix(definition.code(), language, chapter.no()));
            if (pageKeys.isEmpty()) {
                throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                        "chapter " + chapter.no() + " of '" + definition.code() + "' has no rendered pages — "
                                + "run `ncert render` first");
            }
            int chapterCalled = 0;
            int chapterParagraphs = 0;
            PreviousPage previous = PreviousPage.none();
            for (String pageKey : pageKeys) {
                int page = ContentKeys.pageNumber(pageKey);
                ExtractedPage existing = done.get(chapter.no() + "/" + page);
                // A page this run is not calling for still advances the address, when we know it:
                // otherwise `--pages 3` would call page 3 with no previous address, the model would
                // restart numbering, and the collision the founder was re-extracting to fix would
                // come straight back — the remedy printed by the load's refusal could never work
                // (spec-auditor, D14).
                if (pages != null && !pages.isEmpty() && !pages.contains(page)) {
                    previous = existing == null ? null : previousOf(existing, previous);
                    continue;
                }
                if (existing != null && !redo) {
                    skipped++;
                    chapterParagraphs += existing.paragraphs().size();
                    previous = previousOf(existing, previous);
                    continue;
                }
                AiResponse<NcertPage> response = extract.read(definition.row().titleEn(), chapter.no(), page,
                        new ImagePart(content.get(pageKey), PdfPageRenderer.MEDIA_TYPE), previous, ctx);
                ExtractedPage read = ExtractedPage.of(chapter.no(), page, response.output(), response.aiCallId());
                done.put(read.address(), read);
                called++;
                chapterCalled++;
                chapterParagraphs += read.paragraphs().size();
                previous = PreviousPage.of(response.output(), previous);
                if (read.confidence() != null && read.confidence().compareTo(LOW_CONFIDENCE) < 0) {
                    lowConfidence.add("ch " + chapter.no() + " page " + page + " — confidence "
                            + read.confidence() + ", " + read.paragraphs().size() + " paragraphs");
                }
                if (called % properties.extractBatchSize() == 0) {
                    flush(jsonlKey, done);
                    log.info("ncert extract {} {}: {} pages called, flushed", definition.code(), language, called);
                }
            }
            perChapter.add(List.of(String.valueOf(chapter.no()), String.valueOf(pageKeys.size()),
                    String.valueOf(chapterCalled), String.valueOf(chapterParagraphs)));
        }
        flush(jsonlKey, done);

        int paragraphs = done.values().stream().mapToInt(page -> page.paragraphs().size()).sum();
        report.section("pages per chapter")
                .table(List.of("chapter", "pages", "called", "paragraphs"), perChapter);
        report.section("total").table(
                List.of("pages in jsonl", "called this run", "already done", "paragraphs"),
                List.of(List.of(String.valueOf(done.size()), String.valueOf(called),
                        String.valueOf(skipped), String.valueOf(paragraphs))));
        report.section("low-confidence pages (below " + LOW_CONFIDENCE + ")").list(lowConfidence);

        AiSpend.RunSpend bill = spend.of(runId);
        report.section("cost (from the ai_calls ledger)").table(
                List.of("calls", "input", "output", "cache read", "cache write", "cost"),
                List.of(List.of(String.valueOf(bill.calls()),
                        String.valueOf(bill.usage().inputTokens()), String.valueOf(bill.usage().outputTokens()),
                        String.valueOf(bill.usage().cacheReadTokens()), String.valueOf(bill.usage().cacheWriteTokens()),
                        bill.rupees())));
        report.line("jsonl: " + jsonlKey + " (" + done.size() + " pages, " + alreadyDone + " of them from earlier runs)");
    }

    /** Written in page order, so the file reads like the book however the run was interrupted. */
    private void flush(String key, Map<String, ExtractedPage> done) {
        List<ExtractedPage> ordered = new ArrayList<>(done.values());
        ordered.sort((left, right) -> left.chapterNo() != right.chapterNo()
                ? Short.compare(left.chapterNo(), right.chapterNo())
                : Integer.compare(left.page(), right.page()));
        content.put(key, ExtractJsonl.write(ordered), "application/jsonl");
    }

    /** Where a page already in the JSONL left off, so a resumed run continues the numbering too. */
    private static PreviousPage previousOf(ExtractedPage page, PreviousPage before) {
        return PreviousPage.of(new NcertPage(page.paragraphs(), page.confidence()), before);
    }
}
