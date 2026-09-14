package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiCallModels;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.ImagePart;
import com.margai.ai.tasks.NcertPageVerifier;
import com.margai.ai.tasks.PageVerdicts;
import com.margai.ai.tasks.VerifyItem;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertVerificationRow;
import com.margai.curriculum.api.ParagraphVerification;
import com.margai.storage.api.ObjectStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert verify [--read-pages]}: the second read of a loaded book (D15, DECISIONS 2026-09-14
 * "the pair" — Claude Opus 5 transcribes, Claude Sonnet 5 verifies).
 *
 * <p>Without {@code --read-pages} it spends nothing: the rows are held to the print's typography
 * ({@link LayoutChecks} over {@link PdfLayout}) and the clean share is counted from verdicts already on
 * the rows. With it, every page of the selected chapters is read by the verify tier with the parts of
 * the paragraphs printed on it, one call per page, and each row gets a verdict once all its pages are
 * read. It resumes from {@code verify/{book}/{lang}.jsonl} like {@code ncert extract} resumes from its
 * JSONL, and reports its cost from the ledger.
 *
 * <p>What it never does is change a word. A flag is adjudicated by the founder against the rendered
 * page, and the outcome is an entry in {@code ncert-corrections.yaml} that {@code ncert load} applies —
 * and a {@code misprint} or {@code false_positive} entry that this command reads, so a flag ruled on is
 * not raised again.
 */
@Component
@Profile("pipeline")
@Command(name = "verify", mixinStandardHelpOptions = true,
        description = "Hold the loaded paragraphs to the print: free layout checks, and with --read-pages a second model reads every page.")
class NcertVerifyCommand extends NcertBookCommand {

    private static final Logger log = LoggerFactory.getLogger(NcertVerifyCommand.class);

    /** How many words of a row a report line quotes. */
    private static final int QUOTED_WORDS = 7;

    @Option(names = "--read-pages", description = "Also read every page with the verify tier (spends; resumes).")
    boolean readPages;

    @Option(names = "--pages", paramLabel = "N[,N…]", split = ",",
            description = "With --read-pages, read only these page numbers within each selected chapter.")
    List<Integer> pages;

    @Option(names = "--redo", description = "With --read-pages, read again pages already in the artefact.")
    boolean redo;

    private final ObjectStore content;
    private final CurriculumImport imports;
    private final NcertPageVerifier verifier;
    private final AiCallModels models;
    private final AiSpend spend;
    private final PipelineProperties properties;

    NcertVerifyCommand(ObjectStore content, CurriculumImport imports, NcertPageVerifier verifier, AiCallModels models,
            AiSpend spend, PipelineProperties properties, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
        this.verifier = verifier;
        this.models = models;
        this.spend = spend;
        this.properties = properties;
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        report.line("content store: " + content.describe());
        Set<Short> chapterNos = new LinkedHashSet<>();
        selected.forEach(chapter -> chapterNos.add(chapter.no()));
        List<NcertParagraphRow> rows = imports.paragraphs(definition.code(), language, chapterNos);
        if (rows.isEmpty()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, "no paragraphs of " + definition.code()
                    + " (" + language + ") chapters " + chapterNos + " are loaded — run `ncert load` first");
        }
        refuseRowsWithoutOffsets(definition, rows);
        Map<Short, List<NcertParagraphRow>> byChapter = new LinkedHashMap<>();
        for (BookDefinition.Chapter chapter : selected) {
            List<NcertParagraphRow> ofChapter = rows.stream().filter(row -> row.chapterNo() == chapter.no()).toList();
            if (!ofChapter.isEmpty()) {
                byChapter.put(chapter.no(), ofChapter);
            }
        }

        Map<String, Set<LayoutChecks.Kind>> rowCodeFlags = layoutChecks(definition, selected, byChapter, report);

        // A verdict already on a row counts while it names the row's current text.
        Map<String, ParagraphVerification> verdicts = new HashMap<>();
        for (NcertParagraphRow row : rows) {
            ParagraphVerification stored = row.extraction().verification();
            if (stored != null && stored.textSha256().equals(ParagraphVerification.sha256(row.text()))) {
                verdicts.put(row.address(), stored);
            }
        }
        if (readPages) {
            verdicts.putAll(readPages(definition, rows, byChapter, rulings(definition, chapterNos, report), report));
        } else {
            report.line("no model was called: add --read-pages for the second read");
        }
        clean(definition, selected, byChapter, verdicts, rowCodeFlags, report);
    }

    private void refuseRowsWithoutOffsets(BookDefinition definition, List<NcertParagraphRow> rows) {
        Map<Short, Long> without = rows.stream().filter(row -> !ParagraphParts.divisible(row))
                .collect(Collectors.groupingBy(NcertParagraphRow::chapterNo, TreeMap::new, Collectors.counting()));
        if (without.isEmpty()) {
            return;
        }
        throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, without.entrySet().stream()
                .map(entry -> entry.getValue() + " row(s) of chapter " + entry.getKey()
                        + " were loaded before page offsets existed — reload them first (no model, no cost): "
                        + "`ncert load --book " + definition.code() + " --lang " + language + " --chapters "
                        + entry.getKey() + "`")
                .collect(Collectors.joining("\n  ")));
    }

    /** The free checks, per chapter; returns the kinds of flag raised on each row, by address. */
    private Map<String, Set<LayoutChecks.Kind>> layoutChecks(BookDefinition definition,
            List<BookDefinition.Chapter> selected, Map<Short, List<NcertParagraphRow>> byChapter, Report report) {
        List<String> starts = new ArrayList<>();
        List<String> joins = new ArrayList<>();
        List<String> figures = new ArrayList<>();
        List<List<String>> summary = new ArrayList<>();
        Map<String, Set<LayoutChecks.Kind>> onRows = new HashMap<>();
        for (BookDefinition.Chapter chapter : selected) {
            List<NcertParagraphRow> ofChapter = byChapter.get(chapter.no());
            if (ofChapter == null) {
                continue;
            }
            byte[] pdf = content.get(definition.sourceKey(language, chapter));
            // The same per-chapter trust judgement extract makes: a layer that is not the page's words
            // (every Hindi book, one Chemistry file) has no prose for the typography rules to find.
            if (!PdfTextLayer.isLegible(PdfTextLayer.pages(pdf))) {
                summary.add(List.of(String.valueOf(chapter.no()), "withheld: illegible text layer", "—", "—", "—", "—", "—"));
                continue;
            }
            LayoutChecks.Result result = LayoutChecks.check(ofChapter, PdfLayout.pages(pdf));
            long startFlags = 0;
            long joinFlags = 0;
            long figureFlags = 0;
            for (LayoutChecks.Flag flag : result.flags()) {
                switch (flag.kind()) {
                    case starts -> {
                        starts.add(flag.message());
                        startFlags++;
                    }
                    case join -> {
                        joins.add(flag.message());
                        joinFlags++;
                    }
                    case figure -> {
                        figures.add(flag.message());
                        figureFlags++;
                    }
                }
                if (flag.address() != null) {
                    onRows.computeIfAbsent(flag.address(), address -> new LinkedHashSet<>()).add(flag.kind());
                }
            }
            summary.add(List.of(String.valueOf(chapter.no()), String.valueOf(result.pagesCompared()),
                    String.valueOf(result.boundariesJudged()), String.valueOf(result.boundariesUndecided()),
                    String.valueOf(startFlags), String.valueOf(joinFlags), String.valueOf(figureFlags)));
        }
        report.section("the print's typography against the rows (free; routes attention, never refuses)")
                .table(List.of("chapter", "pages compared", "page breaks judged", "page breaks undecided",
                        "start flags", "join flags", "figure flags"), summary);
        report.section("where rows start against where the print starts paragraphs").list(starts);
        report.section("joins across page breaks against the print").list(joins);
        report.section("figure_refs against the paragraph and the chapter's captions").list(figures);
        return onRows;
    }

    /** The founder's rulings on flags: the corrections file's misprint and false_positive entries for these chapters. */
    private List<NcertCorrection> rulings(BookDefinition definition, Set<Short> chapterNos, Report report) {
        Path file = io.input(NcertCorrectionsYamlReader.FILE);
        if (!Files.isRegularFile(file)) {
            return List.of();
        }
        report.line("rulings: " + file.normalize() + " sha256 " + Report.sha256(file));
        return NcertCorrections.forLoad(NcertCorrectionsYamlReader.read(file), definition.code(), language, chapterNos)
                .stream().filter(entry -> !entry.kind().changesText()).toList();
    }

    private Map<String, ParagraphVerification> readPages(BookDefinition definition, List<NcertParagraphRow> rows,
            Map<Short, List<NcertParagraphRow>> byChapter, List<NcertCorrection> rulings, Report report) {
        guardIndependence(rows, report);

        String key = ContentKeys.verify(definition.code(), language);
        Map<String, VerifiedPage> done = new TreeMap<>();
        if (content.exists(key)) {
            VerifyJsonl.read(key, content.get(key)).forEach(page -> done.put(page.address(), page));
        }
        String runId = "pipeline-ncert-verify-" + UUID.randomUUID();
        report.line("request id: " + runId);
        AiCallContext ctx = AiCallContext.system(runId);

        int considered = 0;
        int called = 0;
        int earlier = 0;
        int strayVerdicts = 0;
        for (Map.Entry<Short, List<NcertParagraphRow>> chapter : byChapter.entrySet()) {
            for (Map.Entry<Integer, List<Placed>> onPage : partsByPage(chapter.getValue()).entrySet()) {
                int page = onPage.getKey();
                if (pages != null && !pages.isEmpty() && !pages.contains(page)) {
                    continue;
                }
                considered++;
                List<Placed> placed = onPage.getValue();
                VerifiedPage existing = done.get(chapter.getKey() + "/" + page);
                if (!redo && existing != null && current(existing, placed)) {
                    earlier++;
                    continue;
                }
                String pageKey = ContentKeys.page(definition.code(), language, chapter.getKey(), page);
                if (!content.exists(pageKey)) {
                    throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, "ch " + chapter.getKey()
                            + " page " + page + " has no rendered image at " + pageKey + " — run `ncert render` first");
                }
                List<ImagePart> images = PageTiles.split(content.get(pageKey), properties.pageTiles()).stream()
                        .map(band -> new ImagePart(band, PdfPageRenderer.MEDIA_TYPE))
                        .toList();
                List<VerifyItem> items = new ArrayList<>();
                for (int index = 0; index < placed.size(); index++) {
                    ParagraphParts.Part part = placed.get(index).part();
                    items.add(new VerifyItem(index + 1, part.text(), part.continuesFromPrevious(), part.continuesOnNext()));
                }
                AiResponse<PageVerdicts> response = verifier.verify(definition.row().titleEn(), chapter.getKey(), page,
                        images, items, ctx);
                Recorded recorded = record(chapter.getKey(), page, placed, response);
                strayVerdicts += recorded.stray();
                done.put(recorded.page().address(), recorded.page());
                called++;
                if (called % properties.extractBatchSize() == 0) {
                    flush(key, done);
                    log.info("ncert verify {} {}: {} pages read, flushed", definition.code(), language, called);
                }
            }
        }
        if (called > 0) {
            flush(key, done);
        }
        report.section("pages read by the second read")
                .table(List.of("pages", "read this run", "from earlier runs"), List.of(List.of(
                        String.valueOf(considered), String.valueOf(called), String.valueOf(earlier))));
        report.line("artefact: " + key + " (" + done.size() + " pages)");
        if (strayVerdicts > 0) {
            report.line("verdicts for item numbers the call did not have, ignored: " + strayVerdicts);
        }

        Map<String, ParagraphVerification> verdicts = judge(definition.code(), byChapter, done, rulings, report);

        AiSpend.RunSpend bill = spend.of(runId);
        report.section("cost (from the ai_calls ledger)").table(
                List.of("calls", "input", "output", "cache read", "cache write", "cost"),
                List.of(List.of(String.valueOf(bill.calls()),
                        String.valueOf(bill.usage().inputTokens()), String.valueOf(bill.usage().outputTokens()),
                        String.valueOf(bill.usage().cacheReadTokens()), String.valueOf(bill.usage().cacheWriteTokens()),
                        bill.rupees())));
        return verdicts;
    }

    /**
     * Refused before a single call when the verify tier's model is one the ledger says transcribed these
     * rows: a second read by the same model shares the first read's habits, which is the thing it
     * exists to catch (dry runs 10 and 11: Opus wrote r_hat for the vector r four times of four).
     */
    private void guardIndependence(List<NcertParagraphRow> rows, Report report) {
        List<UUID> ids = rows.stream().map(row -> row.extraction().aiCallId()).filter(Objects::nonNull).toList();
        Map<UUID, String> transcribers = ids.isEmpty() ? Map.of() : models.modelsOf(ids);
        Map<String, Long> rowsByModel = new TreeMap<>();
        long unknown = 0;
        for (NcertParagraphRow row : rows) {
            String model = row.extraction().aiCallId() == null ? null : transcribers.get(row.extraction().aiCallId());
            if (model == null) {
                unknown++;
            } else {
                rowsByModel.merge(model, 1L, Long::sum);
            }
        }
        String verifying = verifier.model();
        if (rowsByModel.containsKey(verifying)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the verifier must not be the transcriber: " + verifying + " transcribed " + rowsByModel.get(verifying)
                            + " of these rows — run the second read on the other model's shape "
                            + "(`--spring.profiles.active=pipeline,live,visionsonnet` for an Opus corpus)");
        }
        report.line("verifier: " + verifying + " (prompt " + verifier.promptVersion() + "); transcribed by: "
                + (rowsByModel.isEmpty() ? "unknown" : rowsByModel.entrySet().stream()
                        .map(entry -> entry.getKey() + " (" + entry.getValue() + " rows)").collect(Collectors.joining(", ")))
                + (unknown > 0 && !rowsByModel.isEmpty() ? ", unknown (" + unknown + " rows with no ledger row)" : ""));
    }

    /** One row's part on one page. */
    private record Placed(NcertParagraphRow row, ParagraphParts.Part part) {
    }

    /**
     * Each page's parts in reading order: the part continuing from the previous page first, then the
     * paragraphs that begin on the page, in the rows' own order.
     */
    private static Map<Integer, List<Placed>> partsByPage(List<NcertParagraphRow> rows) {
        Map<Integer, List<Placed>> continuing = new TreeMap<>();
        Map<Integer, List<Placed>> beginning = new TreeMap<>();
        for (NcertParagraphRow row : rows) {
            for (ParagraphParts.Part part : ParagraphParts.of(row)) {
                (part.continuesFromPrevious() ? continuing : beginning)
                        .computeIfAbsent(part.page(), page -> new ArrayList<>()).add(new Placed(row, part));
            }
        }
        Map<Integer, List<Placed>> byPage = new TreeMap<>();
        continuing.forEach((page, parts) -> byPage.computeIfAbsent(page, p -> new ArrayList<>()).addAll(parts));
        beginning.forEach((page, parts) -> byPage.computeIfAbsent(page, p -> new ArrayList<>()).addAll(parts));
        return byPage;
    }

    /** Whether the artefact's read of a page was of exactly these parts, with this prompt. */
    private boolean current(VerifiedPage existing, List<Placed> placed) {
        if (!Objects.equals(existing.promptVersion(), verifier.promptVersion()) || existing.items().size() != placed.size()) {
            return false;
        }
        for (int index = 0; index < placed.size(); index++) {
            VerifiedPage.Item item = existing.items().get(index);
            if (!item.address().equals(placed.get(index).row().address())
                    || !item.partSha256().equals(ParagraphVerification.sha256(placed.get(index).part().text()))) {
                return false;
            }
        }
        return true;
    }

    private record Recorded(VerifiedPage page, int stray) {
    }

    /** The model's answer, as it gave it, against the items it was given; an item it skipped is not judged. */
    private Recorded record(short chapter, int page, List<Placed> placed, AiResponse<PageVerdicts> response) {
        Map<Integer, PageVerdicts.ItemVerdict> byNumber = new HashMap<>();
        int stray = 0;
        for (PageVerdicts.ItemVerdict verdict : response.output().items()) {
            if (verdict.item() < 1 || verdict.item() > placed.size()) {
                stray++;
            } else {
                byNumber.putIfAbsent(verdict.item(), verdict);
            }
        }
        List<VerifiedPage.Item> items = new ArrayList<>();
        for (int index = 0; index < placed.size(); index++) {
            Placed at = placed.get(index);
            PageVerdicts.ItemVerdict verdict = byNumber.get(index + 1);
            ParagraphVerification.Verdict code = verdict == null ? ParagraphVerification.Verdict.not_judged
                    : ParagraphVerification.Verdict.valueOf(verdict.verdict().name());
            List<VerifiedPage.Span> spans = verdict == null ? List.of() : verdict.differences().stream()
                    .map(difference -> new VerifiedPage.Span(difference.printed(), difference.transcribed())).toList();
            items.add(new VerifiedPage.Item(index + 1, at.row().address(),
                    ParagraphVerification.sha256(at.part().text()), code, spans));
        }
        return new Recorded(new VerifiedPage(chapter, page, response.aiCallId(), response.modelId(),
                verifier.promptVersion(), items, response.output().omitted()), stray);
    }

    /** Written in page order, so the file reads like the book however the run was interrupted. */
    private void flush(String key, Map<String, VerifiedPage> done) {
        List<VerifiedPage> ordered = new ArrayList<>(done.values());
        ordered.sort((left, right) -> left.chapterNo() != right.chapterNo()
                ? Short.compare(left.chapterNo(), right.chapterNo())
                : Integer.compare(left.page(), right.page()));
        content.put(key, VerifyJsonl.write(ordered), "application/jsonl");
    }

    /**
     * Every row whose pages all carry a current read gets a verdict, after code's judgements and the
     * founder's rulings; the report lists what is left for a person.
     */
    private Map<String, ParagraphVerification> judge(String bookCode, Map<Short, List<NcertParagraphRow>> byChapter,
            Map<String, VerifiedPage> done, List<NcertCorrection> rulings, Report report) {
        List<String> flags = new ArrayList<>();
        List<String> notOnPage = new ArrayList<>();
        List<String> notJudged = new ArrayList<>();
        List<String> glyphOnly = new ArrayList<>();
        List<String> notCarried = new ArrayList<>();
        List<String> omitted = new ArrayList<>();
        int ruled = 0;
        List<NcertVerificationRow> recorded = new ArrayList<>();
        Map<String, ParagraphVerification> verdicts = new HashMap<>();

        for (Map.Entry<Short, List<NcertParagraphRow>> chapter : byChapter.entrySet()) {
            Set<Integer> pagesOfChapter = new LinkedHashSet<>();
            for (NcertParagraphRow row : chapter.getValue()) {
                List<ParagraphParts.Part> parts = ParagraphParts.of(row);
                List<VerifiedPage> reads = new ArrayList<>();
                List<VerifiedPage.Item> items = new ArrayList<>();
                for (ParagraphParts.Part part : parts) {
                    pagesOfChapter.add(part.page());
                    VerifiedPage read = done.get(chapter.getKey() + "/" + part.page());
                    String sha = ParagraphVerification.sha256(part.text());
                    VerifiedPage.Item item = read == null ? null : read.items().stream()
                            .filter(candidate -> candidate.address().equals(row.address()) && candidate.partSha256().equals(sha))
                            .findFirst().orElse(null);
                    if (item == null) {
                        break;
                    }
                    reads.add(read);
                    items.add(item);
                }
                if (items.size() != parts.size()) {
                    continue;
                }
                List<ParagraphVerification.Difference> differences = new ArrayList<>();
                boolean absent = false;
                boolean unjudged = false;
                for (int index = 0; index < parts.size(); index++) {
                    ParagraphParts.Part part = parts.get(index);
                    VerifiedPage.Item item = items.get(index);
                    String at = "ch " + chapter.getKey() + " p" + part.page() + " §" + row.section() + " ¶" + row.paraNo();
                    switch (item.verdict()) {
                        case not_on_page -> {
                            absent = true;
                            notOnPage.add(at + " \"" + quote(part.text()) + "\"");
                        }
                        case not_judged -> {
                            unjudged = true;
                            notJudged.add(at);
                        }
                        case differs -> {
                            for (VerifiedPage.Span span : item.differences()) {
                                String line = at + ": printed \"" + span.printed() + "\" · transcribed \"" + span.transcribed() + "\"";
                                if (VerdictSpans.sameExceptSpacingAndGlyphs(span.printed(), span.transcribed())) {
                                    glyphOnly.add(line);
                                } else if (!VerdictSpans.carries(part.text(), span.transcribed())) {
                                    notCarried.add(at + ": transcribed \"" + span.transcribed() + "\" (printed \"" + span.printed() + "\")");
                                } else if (ruledOn(rulings, chapter.getKey(), part.page(), span)) {
                                    ruled++;
                                } else {
                                    flags.add(line);
                                    differences.add(new ParagraphVerification.Difference(part.page(), span.printed(), span.transcribed()));
                                }
                            }
                        }
                        case matches -> {
                        }
                    }
                }
                ParagraphVerification.Verdict verdict = absent ? ParagraphVerification.Verdict.not_on_page
                        : !differences.isEmpty() ? ParagraphVerification.Verdict.differs
                        : unjudged ? ParagraphVerification.Verdict.not_judged
                        : ParagraphVerification.Verdict.matches;
                ParagraphVerification verification = new ParagraphVerification(verdict,
                        verdict == ParagraphVerification.Verdict.differs ? differences : List.of(),
                        ParagraphVerification.sha256(row.text()),
                        reads.stream().map(VerifiedPage::aiCallId).filter(Objects::nonNull).distinct().toList(),
                        reads.getFirst().model(), reads.getFirst().promptVersion());
                recorded.add(new NcertVerificationRow(row.chapterNo(), row.section(), row.paraNo(), verification));
                verdicts.put(row.address(), verification);
            }
            for (int page : pagesOfChapter) {
                VerifiedPage read = done.get(chapter.getKey() + "/" + page);
                if (read != null && (pages == null || pages.isEmpty() || pages.contains(page))) {
                    read.omitted().forEach(text -> omitted.add("ch " + chapter.getKey() + " p" + page + ": \"" + text + "\""));
                }
            }
        }
        if (!recorded.isEmpty()) {
            imports.recordVerifications(bookCode, language, recorded);
        }

        report.section("the second read's flags — adjudicate these against the page")
                .line("each line: the address, the span as the page prints it, the span as the row carries it")
                .list(flags);
        report.section("rows the verifier could not find on their page").list(notOnPage);
        report.section("running text the page prints that no row carries").list(omitted);
        report.section("items the verifier returned no verdict for").list(notJudged);
        report.section("set aside by code: the spans differ only in spacing or a glyph variant").list(glyphOnly);
        report.section("set aside by code: the verifier quoted a transcription the row does not carry").list(notCarried);
        report.line("flags set aside by the founder's rulings in " + NcertCorrectionsYamlReader.FILE + ": " + ruled);
        report.line("verdicts recorded on rows: " + recorded.size());
        return verdicts;
    }

    private static boolean ruledOn(List<NcertCorrection> rulings, short chapter, int page, VerifiedPage.Span span) {
        return rulings.stream().anyMatch(ruling -> ruling.chapter() == chapter && ruling.page() == page
                && VerdictSpans.normalise(ruling.printed()).equals(VerdictSpans.normalise(span.printed()))
                && VerdictSpans.normalise(ruling.transcribed()).equals(VerdictSpans.normalise(span.transcribed())));
    }

    /**
     * PLAN D15's ✅, "% paragraphs extracted cleanly per book": a paragraph is clean when its second read
     * matches — after code's judgements and the founder's rulings — and no join or figure flag names it.
     */
    private void clean(BookDefinition definition, List<BookDefinition.Chapter> selected,
            Map<Short, List<NcertParagraphRow>> byChapter, Map<String, ParagraphVerification> verdicts,
            Map<String, Set<LayoutChecks.Kind>> rowCodeFlags, Report report) {
        List<List<String>> table = new ArrayList<>();
        long allRows = 0;
        long allClean = 0;
        boolean complete = true;
        for (Map.Entry<Short, List<NcertParagraphRow>> chapter : byChapter.entrySet()) {
            long rows = chapter.getValue().size();
            Map<ParagraphVerification.Verdict, Long> counts = new HashMap<>();
            long withVerdict = 0;
            long flagged = 0;
            long clean = 0;
            for (NcertParagraphRow row : chapter.getValue()) {
                ParagraphVerification verdict = verdicts.get(row.address());
                boolean codeFlag = rowCodeFlags.getOrDefault(row.address(), Set.of()).stream()
                        .anyMatch(kind -> kind != LayoutChecks.Kind.starts);
                if (codeFlag) {
                    flagged++;
                }
                if (verdict != null) {
                    withVerdict++;
                    counts.merge(verdict.verdict(), 1L, Long::sum);
                    if (verdict.verdict() == ParagraphVerification.Verdict.matches && !codeFlag) {
                        clean++;
                    }
                }
            }
            boolean chapterComplete = withVerdict == rows;
            complete &= chapterComplete;
            allRows += rows;
            allClean += clean;
            table.add(List.of(String.valueOf(chapter.getKey()), String.valueOf(rows), String.valueOf(withVerdict),
                    count(counts, ParagraphVerification.Verdict.matches), count(counts, ParagraphVerification.Verdict.differs),
                    count(counts, ParagraphVerification.Verdict.not_on_page),
                    count(counts, ParagraphVerification.Verdict.not_judged), String.valueOf(flagged),
                    chapterComplete ? percent(clean, rows) : "— (" + (rows - withVerdict) + " rows without a verdict)"));
        }
        report.section("clean paragraphs — the second read matches and no join or figure flag names the row")
                .table(List.of("chapter", "rows", "with a verdict", "matches", "differs", "not on page", "not judged",
                        "join or figure flags", "clean"), table);
        if (complete && selected.size() == definition.chapters().size()) {
            report.line("clean for the book (PLAN D15 ✅): " + allClean + " of " + allRows + " paragraphs, "
                    + percent(allClean, allRows));
        } else {
            report.line("clean for the book (PLAN D15 ✅): not computed — "
                    + (complete ? "only some chapters were selected" : "some rows have no verdict yet"));
        }
    }

    private static String count(Map<ParagraphVerification.Verdict, Long> counts, ParagraphVerification.Verdict verdict) {
        return String.valueOf(counts.getOrDefault(verdict, 0L));
    }

    private static String percent(long part, long whole) {
        return whole == 0 ? "—" : "%.1f%%".formatted(100.0 * part / whole);
    }

    private static String quote(String text) {
        String[] words = text.strip().split("\\s+");
        return String.join(" ", Arrays.copyOf(words, Math.min(words.length, QUOTED_WORDS)));
    }
}
