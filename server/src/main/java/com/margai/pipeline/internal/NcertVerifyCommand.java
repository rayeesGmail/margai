package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiCallModels;
import com.margai.ai.api.AiClientInfo;
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
import java.util.regex.Pattern;
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
 * ({@link LayoutChecks} over {@link PdfLayout}), and the reads already in {@code verify/{book}/{lang}.jsonl}
 * are re-judged with the current rulings and their verdicts written onto the rows — so a ruling takes
 * effect without paying again. With it, every page of the selected chapters that has no current read is
 * read by the verify tier with the parts of the paragraphs printed on it, one call per page, first; a
 * row gets a verdict once all its pages are read. It resumes like {@code ncert extract}, and reports its
 * cost from the ledger. A page whose paragraphs a correction changed has no current read until
 * {@code --read-pages} reads it again.
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

    /**
     * A section heading as the verifier quotes one — "7.4 THE GRAVITATIONAL CONSTANT", "5.2.2 Inheritance of
     * One Gene" — a printed section number and a word; "3.84 × 10^8 m" is not one.
     */
    private static final Pattern OMITTED_HEADING = Pattern.compile("\\d{1,2}(\\.\\d{1,2})+\\s+\\p{L}{2,}");

    /** An artefact tag becomes part of an object key. */
    private static final Pattern TAG = Pattern.compile("[a-z0-9][a-z0-9-]{0,31}");

    @Option(names = "--read-pages", description = "Also read every page with the verify tier (spends; resumes).")
    boolean readPages;

    @Option(names = "--pages", paramLabel = "N[,N…]", split = ",",
            description = "With --read-pages, read only these page numbers within each selected chapter.")
    List<Integer> pages;

    @Option(names = "--redo", description = "With --read-pages, read again pages already in the artefact.")
    boolean redo;

    @Option(names = "--artefact-tag", paramLabel = "TAG",
            description = "Keep this run's reads in verify/{book}/{lang}.TAG.jsonl — for a scratch run such as the seeded recall run.")
    String artefactTag;

    private final ObjectStore content;
    private final CurriculumImport imports;
    private final NcertPageVerifier verifier;
    private final AiCallModels models;
    private final AiSpend spend;
    private final PipelineProperties properties;
    private final AiClientInfo client;

    NcertVerifyCommand(ObjectStore content, CurriculumImport imports, NcertPageVerifier verifier, AiCallModels models,
            AiSpend spend, AiClientInfo client, PipelineProperties properties, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
        this.verifier = verifier;
        this.models = models;
        this.spend = spend;
        this.client = client;
        this.properties = properties;
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        report.line("content store: " + content.describe());
        if (!readPages && ((pages != null && !pages.isEmpty()) || redo)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "--pages and --redo choose what --read-pages reads — add --read-pages, or drop them for the free checks");
        }
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

        List<NcertCorrection> rulings = rulings(definition, chapterNos, report);
        CodeFlags codeFlags = layoutChecks(definition, selected, byChapter, rulings, report);

        if (artefactTag != null && !TAG.matcher(artefactTag).matches()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "--artefact-tag must be lowercase letters, digits and hyphens, at most 32 — it becomes part of an object key");
        }
        String key = ContentKeys.verify(definition.code(), language, artefactTag);
        Map<String, VerifiedPage> done = new TreeMap<>();
        if (content.exists(key)) {
            VerifyJsonl.read(key, content.get(key)).forEach(page -> done.put(page.address(), page));
        }
        if (readPages) {
            // Every refusal before the first call: the second read is only worth its cost when it is the
            // ruling's verifier, a real one, and not the model that transcribed.
            guardLiveClient();
            guardVerifyModel();
            guardIndependence(rows, report);
            read(definition, byChapter, done, key, report);
        } else {
            report.line("no model was called: add --read-pages for the second read");
        }

        // A verdict already on a row counts while it names the row's current text and was given by the
        // ruling's verifier on the current prompt; the artefact, where there is one, then re-judges every
        // row it covers at no cost, so a ruling added since a read takes effect on a free run too.
        Map<String, ParagraphVerification> verdicts = new HashMap<>();
        for (NcertParagraphRow row : rows) {
            ParagraphVerification stored = row.extraction().verification();
            if (stored != null && stored.textSha256().equals(ParagraphVerification.sha256(row.text()))
                    && Objects.equals(stored.model(), properties.verifyModel())
                    && Objects.equals(stored.promptVersion(), verifier.promptVersion())) {
                verdicts.put(row.address(), stored);
            }
        }
        int omitted = 0;
        if (!done.isEmpty()) {
            Judged judged = judge(definition.code(), byChapter, done, rulings, report);
            verdicts.putAll(judged.verdicts());
            omitted = judged.omitted();
        } else {
            report.line("no second read in the artefact yet: " + key + " does not exist");
        }
        clean(definition, selected, byChapter, verdicts, codeFlags, omitted, report);
    }

    private void guardLiveClient() {
        if (!client.isLive()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the AI client is the fake (" + client + "): a second read from fixtures would be recorded on "
                            + "the real rows — run with AI_LIVE=1 and the live profile");
        }
    }

    private void guardVerifyModel() {
        if (!Objects.equals(verifier.model(), properties.verifyModel())) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the verify tier is " + verifier.model() + ", not " + properties.verifyModel()
                            + " (margai.pipeline.verify-model, DECISIONS 2026-09-14 \"the pair\") — run with "
                            + "`--spring.profiles.active=pipeline,live,visionsonnet`");
        }
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

    /**
     * What the free checks raised: the kinds of flag on each row, by address, and the two page-level
     * counts — where the print starts paragraphs the rows do not, and the equation numbers it carries
     * that they do not. Neither names a row, so neither can enter the clean share.
     */
    private record CodeFlags(Map<String, Set<LayoutChecks.Kind>> onRows, int startFlags, int equationFlags) {
    }

    /** The verdicts a re-judging of the artefact gave, and how many passages no row carries it found. */
    private record Judged(Map<String, ParagraphVerification> verdicts, int omitted) {
    }

    /** The free checks, per chapter. */
    private CodeFlags layoutChecks(BookDefinition definition, List<BookDefinition.Chapter> selected,
            Map<Short, List<NcertParagraphRow>> byChapter, List<NcertCorrection> rulings, Report report) {
        List<String> starts = new ArrayList<>();
        List<String> joins = new ArrayList<>();
        List<String> figures = new ArrayList<>();
        List<String> equations = new ArrayList<>();
        List<String> paired = new ArrayList<>();
        List<String> ruledNoise = new ArrayList<>();
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
            List<String> pageTexts = PdfTextLayer.pages(pdf);
            if (!PdfTextLayer.isLegible(pageTexts)) {
                summary.add(List.of(String.valueOf(chapter.no()), "withheld: illegible text layer",
                        "—", "—", "—", "—", "—", "—", "—"));
                continue;
            }
            // The page the apparatus starts on is compared only as far as its heading: extract sends
            // it for what is taught above, and the Summary below is not text any row owes (D15).
            List<PdfLayout.PageShape> shapes = ChapterApparatus.find(pageTexts)
                    .map(boundary -> PdfLayout.pages(pdf, boundary.page(), boundary.heading()))
                    .orElseGet(() -> PdfLayout.pages(pdf));
            LayoutChecks.Result result = LayoutChecks.check(ofChapter, shapes);
            paired.addAll(result.paired());
            long startFlags = 0;
            long joinFlags = 0;
            long figureFlags = 0;
            long equationFlags = 0;
            Map<String, NcertParagraphRow> byAddress = ofChapter.stream()
                    .collect(Collectors.toMap(NcertParagraphRow::address, row -> row, (first, second) -> first));
            for (LayoutChecks.Flag flag : result.flags()) {
                NcertCorrection ruled = noiseRuling(rulings, chapter.no(), flag, byAddress.get(flag.address()));
                if (ruled != null) {
                    ruledNoise.add(flag.message() + " — ruled noise: " + ruled.reason());
                    continue;
                }
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
                    case equation -> {
                        equations.add(flag.message());
                        equationFlags++;
                    }
                }
                if (flag.address() != null) {
                    onRows.computeIfAbsent(flag.address(), address -> new LinkedHashSet<>()).add(flag.kind());
                }
            }
            summary.add(List.of(String.valueOf(chapter.no()), String.valueOf(result.pagesCompared()),
                    String.valueOf(result.boundariesJudged()), String.valueOf(result.boundariesUndecided()),
                    String.valueOf(startFlags), String.valueOf(joinFlags), String.valueOf(figureFlags),
                    String.valueOf(equationFlags), String.valueOf(result.paired().size())));
        }
        report.section("the print's typography against the rows (free; routes attention, never refuses)")
                .table(List.of("chapter", "pages compared", "page breaks judged", "page breaks undecided",
                        "start flags", "join flags", "figure flags", "equation flags", "starts paired"), summary);
        report.section("where rows start against where the print starts paragraphs").list(starts);
        report.section("printed starts paired with a row only after allowing for math the layer dropped")
                .line("each of these quieted a page; read them against the page if a page looks too clean")
                .list(paired);
        report.section("joins across page breaks against the print").list(joins);
        report.section("figure_refs against the paragraph and the chapter's captions").list(figures);
        report.section("numbered equations the print carries that the rows do not").list(equations);
        report.section("free-check flags set aside by the founder's rulings")
                .line("each was read against its page and ruled noise; the row counts clean")
                .list(ruledNoise);
        return new CodeFlags(onRows, starts.size(), equations.size());
    }

    /**
     * The founder's {@code noise} ruling on this flag, or null. Named the way every other entry is — by
     * page and by the words the paragraph starts with, never by an address, which a join or a split moves
     * (D15, 2026-09-19). The typography rules are measured guesses, so an adjudicated flag needs a way
     * off the clean share; without one, phy11-part1's twelve ruled join flags cost it twelve rows.
     */
    private static NcertCorrection noiseRuling(List<NcertCorrection> rulings, short chapter,
            LayoutChecks.Flag flag, NcertParagraphRow row) {
        if (row == null || flag.address() == null) {
            return null;
        }
        String text = row.text() == null ? "" : row.text().replaceAll("\\s+", " ").strip();
        return rulings.stream()
                .filter(entry -> entry.kind() == NcertCorrection.Kind.noise)
                .filter(entry -> entry.chapter() == chapter && entry.page() == flag.page()
                        && entry.flag() == flag.kind())
                .filter(entry -> text.startsWith(entry.at().replaceAll("\\s+", " ").strip()))
                .findFirst().orElse(null);
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

    /** Reads every selected page the artefact does not already hold a current read of, flushing as it goes. */
    private void read(BookDefinition definition, Map<Short, List<NcertParagraphRow>> byChapter,
            Map<String, VerifiedPage> done, String key, Report report) {
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
                // Every page, not every batch: a page is paid for when it is read, and the first
                // calibration run lost both of its pages to an interruption before a ten-page flush.
                flush(key, done);
                log.info("ncert verify {} {}: ch {} p{} read and written ({} this run)", definition.code(), language,
                        chapter.getKey(), page, called);
            }
        }
        report.section("pages read by the second read")
                .table(List.of("pages", "read this run", "from earlier runs"), List.of(List.of(
                        String.valueOf(considered), String.valueOf(called), String.valueOf(earlier))));
        report.line("artefact: " + key + " (" + done.size() + " pages)");
        if (strayVerdicts > 0) {
            report.line("verdicts for item numbers the call did not have, ignored: " + strayVerdicts);
        }
        // Before anything that can refuse: a run that paid for its pages reports what they cost whatever
        // happens after.
        AiSpend.RunSpend bill = spend.of(runId);
        report.section("cost (from the ai_calls ledger)").table(
                List.of("calls", "input", "output", "cache read", "cache write", "cost"),
                List.of(List.of(String.valueOf(bill.calls()),
                        String.valueOf(bill.usage().inputTokens()), String.valueOf(bill.usage().outputTokens()),
                        String.valueOf(bill.usage().cacheReadTokens()), String.valueOf(bill.usage().cacheWriteTokens()),
                        bill.rupees())));
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
        // Unknown is refused too: a corpus loaded into a database without its ai_calls rows could be the
        // verifier's own transcription (the bucket holds a Sonnet run beside the Opus one).
        if (unknown > 0) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the ledger cannot name the model that transcribed " + unknown + " of these rows, so the second "
                            + "read cannot be shown independent of it — verify against the database whose ai_calls "
                            + "ledger holds the extraction's calls");
        }
        if (rowsByModel.containsKey(verifying)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the verifier must not be the transcriber: " + verifying + " transcribed " + rowsByModel.get(verifying)
                            + " of these rows — run the second read on the other model's shape "
                            + "(`--spring.profiles.active=pipeline,live,visionsonnet` for an Opus corpus)");
        }
        report.line("verifier: " + verifying + " (prompt " + verifier.promptVersion() + "); transcribed by: "
                + rowsByModel.entrySet().stream()
                        .map(entry -> entry.getKey() + " (" + entry.getValue() + " rows)").collect(Collectors.joining(", ")));
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

    /**
     * Whether the artefact's read of a page was of exactly these parts, by the ruling's verifier, with this
     * prompt. The parts are the words the page prints, so they are what is compared: a correction that
     * splits or joins a paragraph moves every later row of its section down a number, and renumbering alone
     * is not a reason to pay for the page again (founder's ruling, 2026-09-16).
     */
    private boolean current(VerifiedPage existing, List<Placed> placed) {
        return valid(existing) && sameParts(existing, placed);
    }

    /** Whether the read was made of exactly these parts, in this order — whatever the rows are called now. */
    private static boolean sameParts(VerifiedPage read, List<Placed> placed) {
        if (read.items().size() != placed.size()) {
            return false;
        }
        for (int index = 0; index < placed.size(); index++) {
            if (!read.items().get(index).partSha256()
                    .equals(ParagraphVerification.sha256(placed.get(index).part().text()))) {
                return false;
            }
        }
        return true;
    }

    /** A read counts only when the ruling's verifier made it with the current prompt; an earlier misconfigured run's does not. */
    private boolean valid(VerifiedPage read) {
        return Objects.equals(read.model(), properties.verifyModel())
                && Objects.equals(read.promptVersion(), verifier.promptVersion());
    }

    private record Recorded(VerifiedPage page, int stray) {
    }

    /** The model's answer, as it gave it, against the items it was given; an item it skipped is not judged. */
    private Recorded record(short chapter, int page, List<Placed> placed, AiResponse<PageVerdicts> response) {
        Map<Integer, PageVerdicts.ItemVerdict> byNumber = new HashMap<>();
        int stray = 0;
        for (PageVerdicts.ItemVerdict verdict : response.output().verdicts()) {
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
    private Judged judge(String bookCode, Map<Short, List<NcertParagraphRow>> byChapter,
            Map<String, VerifiedPage> done, List<NcertCorrection> rulings, Report report) {
        List<String> flags = new ArrayList<>();
        List<String> notOnPage = new ArrayList<>();
        List<String> notJudged = new ArrayList<>();
        List<String> glyphOnly = new ArrayList<>();
        List<String> notCarried = new ArrayList<>();
        List<String> omitted = new ArrayList<>();
        List<String> omittedHeadings = new ArrayList<>();
        int ruled = 0;
        List<NcertVerificationRow> recorded = new ArrayList<>();
        Map<String, ParagraphVerification> verdicts = new HashMap<>();

        for (Map.Entry<Short, List<NcertParagraphRow>> chapter : byChapter.entrySet()) {
            Set<Integer> pagesOfChapter = new LinkedHashSet<>();
            Map<Integer, Map<String, VerifiedPage.Item>> itemsOfPage = itemsByPage(chapter.getKey(),
                    chapter.getValue(), done);
            for (NcertParagraphRow row : chapter.getValue()) {
                List<ParagraphParts.Part> parts = ParagraphParts.of(row);
                List<VerifiedPage> reads = new ArrayList<>();
                List<VerifiedPage.Item> items = new ArrayList<>();
                for (ParagraphParts.Part part : parts) {
                    pagesOfChapter.add(part.page());
                    VerifiedPage read = done.get(chapter.getKey() + "/" + part.page());
                    VerifiedPage.Item item = itemsOfPage.getOrDefault(part.page(), Map.of()).get(row.address());
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
                boolean misquoted = false;
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
                                    misquoted = true;
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
                // A claim of a difference the verifier could not place is not a match: nothing is known
                // about that part, so the row is not judged, and it is not counted clean.
                ParagraphVerification.Verdict verdict = absent ? ParagraphVerification.Verdict.not_on_page
                        : !differences.isEmpty() ? ParagraphVerification.Verdict.differs
                        : unjudged || misquoted ? ParagraphVerification.Verdict.not_judged
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
                if (read != null && valid(read) && (pages == null || pages.isEmpty() || pages.contains(page))) {
                    for (String text : read.omitted()) {
                        String line = "ch " + chapter.getKey() + " p" + page + ": \"" + text + "\"";
                        // A heading or a caption is never running text, which code can see for itself.
                        (headingOrCaption(text) ? omittedHeadings : omitted).add(line);
                    }
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
        report.section("set aside by code: the spans differ only in spacing or a glyph variant, or not at all").list(glyphOnly);
        report.section("set aside by code: the verifier quoted a transcription the row does not carry")
                .line("the row is left not judged: the verifier claimed a difference it could not place")
                .list(notCarried);
        report.section("set aside by code: a heading or a caption listed as omitted text").list(omittedHeadings);
        report.line("flags set aside by the founder's rulings in " + NcertCorrectionsYamlReader.FILE + ": " + ruled);
        report.line("verdicts recorded on rows: " + recorded.size());
        return new Judged(verdicts, omitted.size());
    }

    /** A printed section number followed by a word, or a figure or table label — as omitted text, never running text. */
    private static boolean headingOrCaption(String text) {
        String stripped = text == null ? "" : text.strip();
        return OMITTED_HEADING.matcher(stripped).lookingAt() || FigureLabels.of(stripped).isPresent();
    }

    /**
     * Which item of each page's read judged which row, by page and row address. A read that was made of
     * exactly this page's parts, in order, is mapped item by item — which is right however the rows have
     * since been renumbered, and right even where a page prints the same words twice. Where the page's
     * parts have changed since (a correction rewrote, split or joined one of them), a part keeps its verdict
     * only where its words appear exactly once on both sides — one item of the read, one part of the page.
     * Two parts and one item, or one part and two items, name nobody, and the page is read again.
     */
    private Map<Integer, Map<String, VerifiedPage.Item>> itemsByPage(short chapter,
            List<NcertParagraphRow> rows, Map<String, VerifiedPage> done) {
        Map<Integer, Map<String, VerifiedPage.Item>> byPage = new HashMap<>();
        for (Map.Entry<Integer, List<Placed>> onPage : partsByPage(rows).entrySet()) {
            VerifiedPage read = done.get(chapter + "/" + onPage.getKey());
            if (read == null || !valid(read)) {
                continue;
            }
            List<Placed> placed = onPage.getValue();
            Map<String, VerifiedPage.Item> ofRow = new HashMap<>();
            if (sameParts(read, placed)) {
                for (int index = 0; index < placed.size(); index++) {
                    ofRow.put(placed.get(index).row().address(), read.items().get(index));
                }
            } else {
                Map<String, Long> partsWithTheWords = placed.stream().collect(Collectors.groupingBy(
                        at -> ParagraphVerification.sha256(at.part().text()), Collectors.counting()));
                for (Placed at : placed) {
                    String sha = ParagraphVerification.sha256(at.part().text());
                    List<VerifiedPage.Item> sameWords = read.items().stream()
                            .filter(candidate -> candidate.partSha256().equals(sha)).toList();
                    if (sameWords.size() == 1 && partsWithTheWords.get(sha) == 1) {
                        ofRow.put(at.row().address(), sameWords.getFirst());
                    }
                }
            }
            byPage.put(onPage.getKey(), ofRow);
        }
        return byPage;
    }

    private static boolean ruledOn(List<NcertCorrection> rulings, short chapter, int page, VerifiedPage.Span span) {
        // A noise entry rules on a free check and quotes no span, so it can never answer for one here.
        return rulings.stream().filter(ruling -> ruling.kind() != NcertCorrection.Kind.noise)
                .anyMatch(ruling -> ruling.chapter() == chapter && ruling.page() == page
                        && VerdictSpans.normalise(ruling.printed()).equals(VerdictSpans.normalise(span.printed()))
                        && VerdictSpans.normalise(ruling.transcribed()).equals(VerdictSpans.normalise(span.transcribed())));
    }

    /**
     * PLAN D15's ✅, "% paragraphs extracted cleanly per book": a paragraph is clean when its second read
     * matches — after code's judgements and the founder's rulings — and no join or figure flag names it.
     * Two signals name no row and so cannot enter the share: where the print starts paragraphs the rows
     * do not (a page-level flag), and running text no row carries. They are counted beside it, to be
     * adjudicated before the number is recorded (DECISIONS 2026-09-14).
     */
    private void clean(BookDefinition definition, List<BookDefinition.Chapter> selected,
            Map<Short, List<NcertParagraphRow>> byChapter, Map<String, ParagraphVerification> verdicts,
            CodeFlags codeFlags, int omitted, Report report) {
        Map<String, Set<LayoutChecks.Kind>> rowCodeFlags = codeFlags.onRows();
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
        boolean wholeBook = selected.size() == definition.chapters().size() && byChapter.size() == selected.size();
        if (complete && wholeBook) {
            report.line("clean for the book (PLAN D15 ✅): " + allClean + " of " + allRows + " paragraphs, "
                    + percent(allClean, allRows));
        } else {
            report.line("clean for the book (PLAN D15 ✅): not computed — "
                    + (!complete ? "some rows have no verdict yet"
                            : selected.size() != definition.chapters().size() ? "only some chapters were selected"
                            : "a selected chapter has no loaded rows"));
        }
        report.line("not in the clean share, adjudicate before recording it: " + codeFlags.startFlags()
                + " page-level start flags, " + codeFlags.equationFlags() + " numbered equations the print carries"
                + " that the rows do not, " + omitted + " passages no row carries");
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
