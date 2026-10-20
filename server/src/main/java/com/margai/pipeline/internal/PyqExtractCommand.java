package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.ImagePart;
import com.margai.ai.tasks.PyqPage;
import com.margai.ai.tasks.PyqPageExtractor;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSourceFile;
import com.margai.storage.api.ObjectStore;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code pyq extract --paper}: every question page of a paper's booklet through the VISION tier into
 * {@code extract/pyq/{paper}.jsonl}, then the draft paper file assembled from it with its checks
 * (D19; the founder's rulings, README §1, §7). This is the command that spends — one call per page.
 *
 * <p>Before the first call it refuses the fake client, a VISION model that is not the PYQ
 * transcriber, and a booklet whose bytes are not the file the founder's MANIFEST names (sha256).
 * Every page is rendered strictly — a missing image decoder stops the run, as at D14 — and a page
 * that renders as blank paper is refused rather than sent; a page is never read ahead of the page
 * before it, since the call is told whether that one ended inside a question. It resumes: a page
 * already in the JSONL is not called for again unless {@code --redo} says so, the JSONL is flushed
 * every {@code margai.pipeline.extract-batch-size} pages and again whatever stops the run, and the
 * ledger's bill is reported either way. The draft goes to {@code --drafts}, which may not lie inside
 * the inputs: approving it is a person's act (.claude/rules/pipeline.md).
 *
 * <p>Every page image a call reads is kept at {@code pages/pyq/{paper}/{page}.png} in the content
 * bucket, as {@code ncert render} keeps a book's, and its line in the JSONL records the image's sha256,
 * dpi and renderer, so any later check can prove it looks at the picture the model read; code 45's are
 * re-rendered with no model call (founder, 2026-10-20). Keeping it before the call, the local copy
 * under {@code --drafts}{@code /pages/{paper}/} for the review, the tiles in the record and the
 * {@code --images-only} flag are Claude's (DECISIONS 2026-10-20): that flag draws the pages already
 * read, keeping the image of a page read before images were kept (marked re-rendered) and checking
 * every other against the hash its line records.
 */
@Component
@Profile("pipeline")
@Command(name = "extract", mixinStandardHelpOptions = true,
        description = "Read a paper's booklet pages with the VISION tier into its JSONL and assemble the draft paper file; resumes.")
class PyqExtractCommand extends PyqPaperCommand {

    static final String DEFAULT_DRAFTS = "../pipeline/data/pyq";
    /** Below this share of dark pixels a rendered page is blank paper — a decoder that drew nothing. */
    static final double MIN_INK = 0.002;

    private static final Logger log = LoggerFactory.getLogger(PyqExtractCommand.class);

    @Option(names = "--pages", paramLabel = "N[,N…]", split = ",",
            description = "Only these booklet pages (default: every page the register lists).")
    List<Integer> pages;

    @Option(names = "--redo", description = "Call again for pages already in the JSONL, replacing them.")
    boolean redo;

    @Option(names = "--images-only", description = "Call no model: keep the image of every page already read "
            + "that has none (re-rendered), and check every kept image against a fresh drawing.")
    boolean imagesOnly;

    @Option(names = "--drafts", paramLabel = "DIR", defaultValue = DEFAULT_DRAFTS,
            description = "Where the draft paper file and the local page images are written (default: ${DEFAULT-VALUE}); "
                    + "never the inputs.")
    Path drafts;

    private final ObjectStore content;
    private final PyqPageExtractor extract;
    private final AiSpend spend;
    private final AiClientInfo client;
    private final PipelineProperties properties;
    private final PyqProperties pyq;

    PyqExtractCommand(ObjectStore content, PyqPageExtractor extract, AiSpend spend, AiClientInfo client,
            PipelineProperties properties, PyqProperties pyq, Reports reports) {
        super(reports);
        this.content = content;
        this.extract = extract;
        this.spend = spend;
        this.client = client;
        this.properties = properties;
        this.pyq = pyq;
    }

    @Override
    void run(PaperDefinition paper, Report report) {
        if (imagesOnly && redo) {
            throw new InputFormatException(Path.of(FILE), 0, "--images-only calls no model, so there is no page to "
                    + "--redo; a page is read again, and its image kept again, by --redo alone");
        }
        if (!imagesOnly) {
            guardLiveClient();
            guardTranscribeModel();
        }
        if (drafts.toAbsolutePath().normalize().startsWith(io.inputs.toAbsolutePath().normalize())) {
            throw new InputFormatException(drafts, 0, "is inside the founder's inputs — a draft is never written there; "
                    + "approving it and committing it to the inputs is a person's act (.claude/rules/pipeline.md)");
        }
        PaperSourceFile booklet = paper.transcribed();
        boolean bilingual = booklet.languages().equals(PyqPapersYamlReader.BILINGUAL);
        report.line("content store: " + content.describe());
        if (!imagesOnly) {
            report.line("transcriber: " + extract.model() + ", " + extract.promptVersion()
                    + " (the effort is the visionopus55 profile's)");
        }
        report.line("page images: " + properties.renderDpi() + " dpi, " + PdfPageRenderer.version() + ", "
                + properties.pageTiles() + " tile(s) a page");
        report.line("booklet: " + booklet.file() + " (" + booklet.languages() + "; pages " + rangeOf(booklet.pages()) + ")");

        byte[] pdf = content.get(booklet.file());
        String sha256 = sha256(pdf);
        if (!sha256.equals(booklet.sha256())) {
            throw new InputFormatException(Path.of(FILE), 0, "the booklet in the store is sha256 " + sha256
                    + ", but the register (from MANIFEST.csv) says " + booklet.sha256() + " — it is not the file the "
                    + "founder checked; upload the manifest's file before extracting");
        }
        List<String> layer = PdfTextLayer.pages(pdf);
        int documentPages = layer.size();
        for (int page : booklet.pages()) {
            if (page > documentPages) {
                throw new InputFormatException(Path.of(FILE), 0, "the register reads page " + page + " of " + booklet.file()
                        + ", which has " + documentPages + " pages");
            }
        }
        if (imagesOnly) {
            keepImagesOnly(paper, booklet, pdf, report);
            return;
        }
        // The layer is judged over the whole booklet, as TECH_PLAN §6.1 judges an NCERT chapter's, and only
        // an English-only booklet's travels: a bilingual layer interleaves the Hindi, which README §4 found
        // legacy-encoded in 2018 (the other years' were not measured; DECISIONS 2026-10-20).
        List<String> bookletLayer = booklet.pages().stream().map(page -> layer.get(page - 1)).toList();
        boolean layerTrusted = !bilingual && PdfTextLayer.isLegible(bookletLayer);
        Map<Integer, String> pageTexts = new TreeMap<>();
        if (layerTrusted) {
            booklet.pages().forEach(page -> pageTexts.put(page, layer.get(page - 1)));
        }
        report.line("text layer: " + (bilingual ? "withheld — a bilingual booklet's layer interleaves its Hindi"
                : (layerTrusted ? "fed as the character authority" : "withheld: illegible")
                        + " (legibility " + "%.3f".formatted(PdfTextLayer.meanLegibility(bookletLayer)) + ")"));

        String jsonlKey = ContentKeys.pyqExtract(paper.code());
        TreeMap<Integer, PyqExtractedPage> done = new TreeMap<>();
        if (content.exists(jsonlKey)) {
            PyqExtractJsonl.read(jsonlKey, content.get(jsonlKey)).forEach(page -> done.put(page.page(), page));
        }
        int alreadyDone = done.size();
        Set<Integer> toRead = new LinkedHashSet<>();
        for (int page : booklet.pages()) {
            boolean selected = pages == null || pages.isEmpty() || pages.contains(page);
            if (selected && (redo || !done.containsKey(page))) {
                toRead.add(page);
            }
        }
        // The call is told whether the page before ended inside a question; that is a fact only once
        // the page before has been read, so a page is never read ahead of it.
        for (int page : toRead) {
            int index = booklet.pages().indexOf(page);
            if (index > 0) {
                int before = booklet.pages().get(index - 1);
                if (!done.containsKey(before) && !toRead.contains(before)) {
                    throw new InputFormatException(Path.of(FILE), 0, "page " + page + " would be read before page " + before
                            + ", the page before it, has been — read the booklet's pages in order");
                }
            }
        }
        String runId = "pipeline-pyq-extract-" + UUID.randomUUID();
        report.line("request id: " + runId);
        AiCallContext ctx = AiCallContext.system(runId);

        int[] called = {0};
        List<String> lowConfidence = new ArrayList<>();
        try {
            readPages(paper, booklet, pdf, toRead, done, pageTexts, bilingual, jsonlKey, ctx, called, lowConfidence);
        } catch (RuntimeException e) {
            // Every page paid for is kept and the bill is reported, whatever stopped the run: a rerun
            // resumes after the last page read rather than paying for it again.
            flush(jsonlKey, done);
            report.section("pages read before the run stopped").line(called[0] + " page(s) called this run are kept in "
                    + jsonlKey + "; a rerun resumes after them");
            reportCost(report, runId);
            throw e;
        }
        flush(jsonlKey, done);

        List<PyqExtractedPage> ordered = booklet.pages().stream().map(done::get).filter(Objects::nonNull).toList();
        PyqAssembly.Draft draft = PyqAssembly.assemble(paper, ordered, pageTexts);
        Path draftFile = drafts.resolve(paper.code() + ".draft.json");
        boolean replaced = Files.exists(draftFile);
        try {
            Files.createDirectories(drafts);
            Files.writeString(draftFile, PaperFileJson.write(draft.file()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write the draft " + draftFile, e);
        }

        report.section("pages").table(List.of("in the register", "read this run", "from earlier runs", "in the jsonl"),
                List.of(List.of(String.valueOf(booklet.pages().size()), String.valueOf(called[0]),
                        String.valueOf(alreadyDone), String.valueOf(ordered.size()))));
        if (ordered.size() < booklet.pages().size()) {
            report.line("PARTIAL: " + (booklet.pages().size() - ordered.size()) + " page(s) not read yet — the draft "
                    + "covers only the pages read");
        }
        List<List<String>> imageRows = new ArrayList<>();
        for (PyqExtractedPage line : ordered) {
            PyqExtractedPage.Image image = line.image();
            imageRows.add(List.of(String.valueOf(line.page()), image == null ? "—" : image.sha256(),
                    image == null ? "none — read before images were kept; `pyq extract --images-only` keeps it"
                            : toRead.contains(line.page()) ? "kept with this run's call"
                            : "kept by an earlier run" + (image.rerendered() ? " (re-rendered)" : "")));
        }
        report.section("page images — what each call read, at pages/pyq/" + paper.code() + "/ and under "
                + drafts.resolve("pages").resolve(paper.code()).normalize())
                .table(List.of("page", "sha256", "image"), imageRows);
        report.section("questions per section — against the count the booklet declares").table(
                List.of("subject", "section", "questions", "declared", "with a figure"),
                sectionRows(paper, draft.file()));
        report.section("flags — every one is checked against the page image before the file is approved")
                .list(draft.allFlags(), "none");
        report.section("low-confidence pages (below " + PyqAssembly.LOW_CONFIDENCE + ") — a routing signal, not a guarantee")
                .list(lowConfidence);
        reportCost(report, runId);
        report.line("jsonl: " + jsonlKey + " (" + done.size() + " pages)");
        report.line("draft: " + draftFile.normalize() + " (" + draft.file().questions().size()
                + " questions; answers empty until NTA's key is filled in at review"
                + (replaced ? "; it replaced the earlier draft" : "") + ")");
    }

    /** Every page to read, in booklet order: rendered strictly, refused when blank, one call each. */
    private void readPages(PaperDefinition paper, PaperSourceFile booklet, byte[] pdf, Set<Integer> toRead,
            TreeMap<Integer, PyqExtractedPage> done, Map<Integer, String> pageTexts, boolean bilingual, String jsonlKey,
            AiCallContext ctx, int[] called, List<String> lowConfidence) {
        String paperName = paper.row().exam() + " (UG) " + paper.row().year() + ", booklet code " + paper.row().paperCode();
        new PdfPageRenderer(properties.renderDpi()).render(pdf, page -> !toRead.contains(page), (png, page) -> {
            refuseBlank(booklet, page, png);
            // Kept before the call, so no page is ever paid for without the picture it was read from.
            PyqExtractedPage.Image image = keep(paper.code(), page, png, false);
            List<ImagePart> images = PageTiles.split(png, properties.pageTiles()).stream()
                    .map(band -> new ImagePart(band, PdfPageRenderer.MEDIA_TYPE)).toList();
            Integer inQuestion = previousOf(booklet.pages(), page, done);
            AiResponse<PyqPage> response = extract.read(paperName, page, images, bilingual, pageTexts.get(page),
                    inQuestion, ctx);
            PyqPage read = response.output();
            done.put(page, new PyqExtractedPage(page, read.confidence(), response.aiCallId(), response.modelId(),
                    extract.promptVersion(), read.headings(), read.questions(), image));
            if (read.confidence() != null && read.confidence().compareTo(PyqAssembly.LOW_CONFIDENCE) < 0) {
                lowConfidence.add("page " + page + " — confidence " + read.confidence());
            }
            called[0]++;
            if (called[0] % properties.extractBatchSize() == 0) {
                flush(jsonlKey, done);
                log.info("pyq extract {}: {} pages called, flushed", paper.code(), called[0]);
            }
        });
    }

    /**
     * {@code --images-only}: no model call. A page already read whose line has no image gets it now,
     * marked re-rendered; a page whose line has one is drawn again and must give the same bytes, and
     * so must the object in the store. Whatever stops the run, the images kept so far are recorded.
     */
    private void keepImagesOnly(PaperDefinition paper, PaperSourceFile booklet, byte[] pdf, Report report) {
        String jsonlKey = ContentKeys.pyqExtract(paper.code());
        if (!content.exists(jsonlKey)) {
            throw new InputFormatException(Path.of(FILE), 0, "no page of " + paper.code() + " has been read yet ("
                    + jsonlKey + " is not in the store) — there is no image a model read to keep");
        }
        TreeMap<Integer, PyqExtractedPage> done = new TreeMap<>();
        PyqExtractJsonl.read(jsonlKey, content.get(jsonlKey)).forEach(page -> done.put(page.page(), page));
        Set<Integer> wanted = new LinkedHashSet<>();
        for (int page : booklet.pages()) {
            if (done.containsKey(page) && (pages == null || pages.isEmpty() || pages.contains(page))) {
                wanted.add(page);
            }
        }
        List<List<String>> rows = new ArrayList<>();
        int[] kept = {0};
        try {
            new PdfPageRenderer(properties.renderDpi()).render(pdf, page -> !wanted.contains(page), (png, page) -> {
                refuseBlank(booklet, page, png);
                String sha = sha256(png);
                String key = ContentKeys.pyqPage(paper.code(), page);
                String stored = content.exists(key) ? sha256(content.get(key)) : null;
                PyqExtractedPage line = done.get(page);
                PyqExtractedPage.Image recorded = line.image();
                if (recorded == null) {
                    if (stored != null && !stored.equals(sha)) {
                        throw new IllegalStateException("page " + page + ": the image in the store at " + key + " ("
                                + stored + ") is not what the booklet draws now (" + sha + ") — neither is kept until "
                                + "the difference is understood");
                    }
                    done.put(page, line.withImage(keep(paper.code(), page, png, true)));
                    kept[0]++;
                    rows.add(List.of(String.valueOf(page), sha, "kept now — re-rendered, the page was read before images were kept"));
                    return;
                }
                if (!recorded.sha256().equals(sha)) {
                    throw new IllegalStateException("page " + page + " draws differently now (" + sha + ", "
                            + properties.renderDpi() + " dpi, " + PdfPageRenderer.version() + ") than the image its line "
                            + "records (" + recorded.sha256() + ", " + recorded.dpi() + " dpi, " + recorded.renderer()
                            + ") — a later check could not prove it looks at what the model read");
                }
                if (!sha.equals(stored)) {
                    throw new IllegalStateException("page " + page + ": the image in the store at " + key + " is "
                            + (stored == null ? "missing" : stored) + ", not " + sha + ", the image its line records");
                }
                writeLocal(paper.code(), key, png);
                rows.add(List.of(String.valueOf(page), sha, "the same bytes as the image its line records"
                        + (recorded.rerendered() ? " (re-rendered)" : "")));
            });
        } catch (RuntimeException e) {
            // The images kept before the stop are recorded and named, so the artefact never changes unreported.
            if (kept[0] > 0) {
                flush(jsonlKey, done);
            }
            report.section("page images handled before the run stopped (--images-only)")
                    .table(List.of("page", "sha256", "image"), rows)
                    .line("jsonl: " + jsonlKey + (kept[0] > 0 ? " (rewritten with the " + kept[0] + " image record(s) kept)"
                            : " (unchanged)"));
            throw e;
        }
        if (kept[0] > 0) {
            flush(jsonlKey, done);
        }
        report.section("page images — no model call (--images-only)").table(List.of("page", "sha256", "image"), rows);
        report.line(kept[0] + " image(s) kept now, " + (rows.size() - kept[0]) + " checked against their line; "
                + (booklet.pages().size() - wanted.size()) + " register page(s) not read yet or not selected");
        report.line("jsonl: " + jsonlKey + (kept[0] > 0 ? " (rewritten with the images' records)" : " (unchanged)"));
        report.line("local copies: " + drafts.resolve("pages").resolve(paper.code()).normalize());
    }

    /** One page image into the content bucket and beside the draft, and the record its JSONL line keeps. */
    private PyqExtractedPage.Image keep(String paper, int page, byte[] png, boolean rerendered) {
        String key = ContentKeys.pyqPage(paper, page);
        content.put(key, png, PdfPageRenderer.MEDIA_TYPE);
        writeLocal(paper, key, png);
        return new PyqExtractedPage.Image(key, sha256(png), properties.renderDpi(), PdfPageRenderer.version(),
                properties.pageTiles(), rerendered);
    }

    /** The review's copy: {@code --drafts}{@code /pages/{paper}/{page}.png}, the bucket object's bytes. */
    private void writeLocal(String paper, String key, byte[] png) {
        Path local = drafts.resolve("pages").resolve(paper).resolve(Path.of(key).getFileName().toString());
        try {
            Files.createDirectories(local.getParent());
            Files.write(local, png);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write the page image " + local, e);
        }
    }

    private static void refuseBlank(PaperSourceFile booklet, int page, byte[] png) {
        double ink = ink(png);
        if (ink < MIN_INK) {
            throw new IllegalStateException("page " + page + " of " + booklet.file() + " rendered as blank paper (ink "
                    + "%.4f".formatted(ink) + ") — an image the renderer could not draw; nothing was sent for it");
        }
    }

    private void reportCost(Report report, String runId) {
        AiSpend.RunSpend bill = spend.of(runId);
        report.section("cost (from the ai_calls ledger)").table(
                List.of("calls", "input", "output", "cache read", "cache write", "cost"),
                List.of(List.of(String.valueOf(bill.calls()),
                        String.valueOf(bill.usage().inputTokens()), String.valueOf(bill.usage().outputTokens()),
                        String.valueOf(bill.usage().cacheReadTokens()), String.valueOf(bill.usage().cacheWriteTokens()),
                        bill.rupees())));
    }

    /** The question the page before this one in the booklet ended inside, or null. */
    static Integer previousOf(List<Integer> bookletPages, int page, Map<Integer, PyqExtractedPage> done) {
        int index = bookletPages.indexOf(page);
        if (index <= 0) {
            return null;
        }
        PyqExtractedPage previous = done.get(bookletPages.get(index - 1));
        return previous == null ? null : previous.endsInside();
    }

    static List<List<String>> sectionRows(PaperDefinition paper, PaperFile file) {
        List<List<String>> rows = new ArrayList<>();
        for (PaperSection section : paper.row().sections()) {
            long questions = file.questions().stream().filter(question -> section.contains(question.no())).count();
            long figures = file.questions().stream()
                    .filter(question -> section.contains(question.no()) && !question.figures().isEmpty()).count();
            rows.add(List.of(section.subject().name(), section.section() == null ? "—" : section.section(),
                    String.valueOf(questions), String.valueOf(section.size()), String.valueOf(figures)));
        }
        return rows;
    }

    /** The share of a page's pixels that are not near-white. */
    static double ink(byte[] png) {
        BufferedImage image;
        try {
            image = ImageIO.read(new ByteArrayInputStream(png));
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read a rendered page", e);
        }
        long dark = 0;
        int step = 2;
        long sampled = 0;
        for (int y = 0; y < image.getHeight(); y += step) {
            for (int x = 0; x < image.getWidth(); x += step) {
                int rgb = image.getRGB(x, y);
                int luminance = (((rgb >> 16) & 0xff) * 299 + ((rgb >> 8) & 0xff) * 587 + (rgb & 0xff) * 114) / 1000;
                if (luminance < 200) {
                    dark++;
                }
                sampled++;
            }
        }
        return sampled == 0 ? 0 : (double) dark / sampled;
    }

    private void guardLiveClient() {
        if (!client.isLive()) {
            throw new InputFormatException(Path.of(FILE), 0, "the AI client is the fake (" + client + "): fixture text "
                    + "would be written to the paper's JSONL, and a later run resumes past those pages rather than "
                    + "calling for them — run with AI_LIVE=1 and the live profile");
        }
    }

    /** The transcriber is the founder's ruling's, or the run does not start (README §1: Opus 5.5 at low). */
    private void guardTranscribeModel() {
        if (!Objects.equals(extract.model(), pyq.transcribeModel())) {
            throw new InputFormatException(Path.of(FILE), 0, "the vision tier is " + extract.model() + ", not "
                    + pyq.transcribeModel() + ", the PYQ transcriber (margai.pipeline.pyq.transcribe-model; the "
                    + "founder's D19 ruling) — run with `--spring.profiles.active=pipeline,live,visionopus55`");
        }
    }

    private void flush(String key, TreeMap<Integer, PyqExtractedPage> done) {
        content.put(key, PyqExtractJsonl.write(new ArrayList<>(done.values())), "application/jsonl");
    }

    static String sha256(byte[] bytes) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String rangeOf(List<Integer> pages) {
        return pages.isEmpty() ? "none" : pages.getFirst() + "–" + pages.getLast() + ", " + pages.size() + " in all";
    }
}
