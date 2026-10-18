package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.ParagraphExtraction;
import com.margai.storage.api.ObjectStore;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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

    /** A printed section number; a {@code section} correction is held to it too. */
    static final Pattern SECTION = Pattern.compile("\\d{1,2}(\\.\\d{1,3})*");

    @Option(names = "--artefact-tag", paramLabel = "TAG",
            description = "Load extract/{book}/{lang}.TAG.jsonl, a scratch run's draw, as written — no correction is "
                    + "applied — and only into a database where the book has no embedded paragraph.")
    String artefactTag;

    @Option(names = "--dry-run",
            description = "Read, repair, correct and number exactly as a load does, and write nothing: the database "
                    + "write runs inside a transaction that is rolled back, and the report says what the load would do.")
    boolean dryRun;

    @Option(names = "--extraction", paramLabel = "FILE",
            description = "With --dry-run: read the extraction from this local copy of extract/{book}/{lang}.jsonl "
                    + "instead of the content store.")
    Path extraction;

    @Option(names = "--migrate", paramLabel = "REASON",
            description = "Let this load change a frozen (embedded) book's corpus — add or delete a row, or rewrite or "
                    + "remove a row's English — as a corpus event; REASON goes in the report. Then run ncert embed.")
    String migrate;

    @Option(names = "--pairs", paramLabel = "FILE",
            description = "With --dry-run: write each address's English and Hindi text to this local JSONL, to read "
                    + "the pairs the load would leave.")
    Path pairsFile;

    private final ObjectStore content;
    private final CurriculumImport imports;

    NcertLoadCommand(ObjectStore content, CurriculumImport imports, Reports reports) {
        super(reports);
        this.content = content;
        this.imports = imports;
    }

    /** A dry run's report is filed under its own name, so it is never taken for a load of record. */
    @Override
    String reportTitle() {
        return dryRun ? super.reportTitle() + " dry-run" : super.reportTitle();
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        if (!dryRun && (extraction != null || pairsFile != null)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, "--extraction and --pairs belong to "
                    + "--dry-run: a load of record reads the content store's artefact and writes the database");
        }
        if (pairsFile != null && language != BookLanguage.hi) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "--pairs sets a Hindi load's rows beside the English ones: give --lang hi");
        }
        if (extraction != null && artefactTag != null) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "--extraction is a local copy of the canonical artefact; --artefact-tag names another — give one");
        }
        if (dryRun) {
            report.line("DRY RUN: nothing is written — the database write runs inside a transaction that is rolled "
                    + "back, so every count below is what this load would do");
        }
        String jsonlKey = ContentKeys.extract(definition.code(), language, ContentKeys.tag(artefactTag));
        if (extraction != null && !Files.isRegularFile(extraction)) {
            throw new InputFormatException(extraction, 0, "no local extraction at "
                    + extraction.toAbsolutePath().normalize());
        }
        if (extraction == null) {
            report.line("content store: " + content.describe());
        }
        if (artefactTag != null) {
            // Every canonical book is embedded and a scratch database never is, so this is what keeps a
            // trial draw out of the frozen corpus — the database, unlike the artefact, has no tag (D16).
            long embedded = imports.embeddedParagraphs(definition.code());
            if (embedded > 0) {
                throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, definition.code() + " has "
                        + embedded + " embedded paragraph(s) in this database: a tagged load belongs in a scratch "
                        + "database, never over the canonical corpus — point DB_URL at one; nothing was written");
            }
            report.line("artefact: " + jsonlKey + " (a scratch run's draw)");
        }
        if (extraction == null && !content.exists(jsonlKey)) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "no extraction at " + jsonlKey + " — run `ncert extract` first");
        }
        String source = extraction == null ? jsonlKey : extraction.normalize().toString();
        byte[] jsonl = extraction == null ? content.get(jsonlKey) : read(extraction);
        // The input's own hash on every load: a dry run read from a local file rehearses a reload only if the
        // load of record later prints the same one for the content store's artefact.
        // A local file by its name alone: the approved dry run's report is committed, and a committed report
        // reads the same on every machine — the hash, not the scratch path, is what pins the input.
        report.line(extraction == null ? "extraction: " + jsonlKey + " sha256 " + Report.sha256(jsonl)
                : "extraction: " + extraction.getFileName() + " sha256 " + Report.sha256(jsonl) + " — a local file "
                        + "read in place of " + jsonlKey + ", not the content store; the reload's report prints the "
                        + "artefact's sha256 to match");
        Set<Short> chapters = new TreeSet<>();
        selected.forEach(chapter -> chapters.add(chapter.no()));
        List<ExtractedPage> pages = ExtractJsonl.read(source, jsonl).stream()
                .filter(page -> chapters.contains(page.chapterNo()))
                .sorted((left, right) -> left.chapterNo() != right.chapterNo()
                        ? Short.compare(left.chapterNo(), right.chapterNo())
                        : Integer.compare(left.page(), right.page()))
                .toList();
        if (extraction != null) {
            // The JSONL names neither book nor edition, so a local file is held to what it must be.
            if (pages.isEmpty()) {
                throw new InputFormatException(extraction, 0, "the local extraction carries no page of chapter(s) "
                        + chapters + " — it is not " + jsonlKey);
            }
            String wrong = wrongEdition(pages, language);
            if (wrong != null) {
                throw new InputFormatException(extraction, 0, "the local extraction " + wrong + " — it is not "
                        + jsonlKey);
            }
        }

        // Deterministic corrections first, each reported: a re-transcribed tail is cut and a
        // labelled paragraph is never a continuation. Then the numbering and the join, which
        // refuse a continuation that cannot be one (D14, the first full-book load; D15).
        PageBreakRepairs.Repaired repaired = PageBreakRepairs.apply(pages);
        pages = repaired.pages();
        // A Hindi load then puts back the visargas the transcriber wrote as colons (D16) — before the
        // corrections, so an entry is written against the words the rows will show.
        VisargaColons.Applied visargas = language == BookLanguage.hi ? VisargaColons.apply(pages) : null;
        if (visargas != null) {
            pages = visargas.pages();
        }
        // Then the founder's rulings on what `ncert verify` flagged (D15): the canonical run is
        // frozen, so a defect in it is corrected here, on the record, rather than re-drawn.
        NcertCorrections.Applied corrected = NcertCorrections.apply(pages, corrections(definition, chapters, report));
        pages = corrected.pages();
        // Last, so a correction's span is the one the verify report quoted: the book's own degree
        // sign for a zero exponent, written as the convention has it (D15, 2026-09-17).
        DimensionalBrackets.Applied dimensioned = DimensionalBrackets.apply(pages);
        pages = dimensioned.pages();
        Numbered numbered = number(pages, source, corrected.deferred(), corrected.moves(), corrected.unprinted());
        List<NcertParagraphRow> rows = numbered.rows();
        report.section("page-break repairs to the model's continuation flags (deterministic, each one named)")
                .list(repaired.notes());
        if (visargas != null) {
            report.section("a visarga the model wrote as a colon (deterministic, each one named)")
                    .list(visargas.notes());
            report.section("a word glued to a colon that the visarga list does not know — adjudicate")
                    .line("left as written: a `text` entry if the page means a visarga, nothing if it is a colon")
                    .list(visargas.undecided());
        }
        report.section("corrections from " + NcertCorrectionsYamlReader.FILE + " (founder-adjudicated, each one named)")
                .list(corrected.notes())
                .line("rulings on verifier flags that change no text: " + corrected.rulings());
        report.section("zero exponents the book set as a degree sign (deterministic, each one named)")
                .list(dimensioned.notes());
        report.section("figure_refs that are not figure or table labels — dropped")
                .list(numbered.droppedRefs());
        guardFrozenCorpus(definition, chapters, rows, report);
        NcertLoadReport result = dryRun ? imports.previewParagraphs(definition.code(), language, rows)
                : imports.loadParagraphs(definition.code(), language, rows);

        report.section("ncert_paragraphs")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.inserted()), String.valueOf(result.updated()),
                        String.valueOf(result.unchanged()))));

        // English is canonical and the Hindi follows it (D16): the first thing a Hindi load is held to is
        // whether each section now has as many Hindi rows as English ones.
        if (language == BookLanguage.hi) {
            List<NcertParagraphRow> english = imports.paragraphs(definition.code(), BookLanguage.en, chapters);
            List<EditionPairs.Section> sections = EditionPairs.sections(english, rows, numbered.unprinted());
            List<List<String>> differing = sections.stream().filter(section -> !section.oneToOne())
                    .map(section -> List.of(String.valueOf(section.chapter()), section.section(),
                            String.valueOf(section.english()), String.valueOf(section.hindi()),
                            String.valueOf(section.unprinted())))
                    .toList();
            report.section("sections whose Hindi rows do not number as the English (English is canonical)")
                    .line((sections.size() - differing.size()) + " of " + sections.size() + " sections one to one")
                    .table(List.of("chapter", "section", "en", "hi", "unprinted"), differing);
            report.section("English paragraphs this edition does not print — left without Hindi by an `unprinted` entry")
                    .list(numbered.unprinted().stream().map(EditionPairs.Address::toString).toList());
            if (pairsFile != null) {
                write(pairsFile, EditionPairs.jsonl(english, rows, numbered.unprinted()));
                report.line("pairs: " + pairsFile.getFileName() + " — each address's English and Hindi, to read");
            }
        }

        // Named rather than left silent (D15): a load that rewrites a paragraph's words throws
        // away the vector that described the old ones, and a load that deletes an embedded row
        // throws away its vector outright. Both are re-earned by `ncert embed` at the price of the
        // paragraphs involved — but a run that quietly destroys paid work should say so.
        if (result.embeddingsCleared() > 0 || result.embeddedOrphans() > 0) {
            report.section(dryRun ? "embeddings this load would drop" : "embeddings this load dropped")
                    .line(dryRun ? "the load of record would leave `ncert embed --book " + definition.code() + "` to earn "
                            + "them back" : "run `ncert embed --book " + definition.code() + "` to earn them back")
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
        report.section("addresses in the database this extraction no longer carried — "
                        + (dryRun ? "a load would delete them" : "deleted"))
                .list(result.orphans());
        report.section("rows the other edition still carries — this edition's text "
                        + (dryRun ? "a load would clear from them" : "cleared from them"))
                .list(result.cleared());
    }

    /**
     * The freeze guard (DECISIONS 2026-09-13; built at D17). A book with embedded paragraphs is the
     * frozen corpus — every canonical book is embedded, a scratch database's never is — and a load
     * that would add or delete one of its rows, or rewrite or remove a row's English text, is a corpus
     * event: refused, every such row named, unless {@code --migrate} gives the reason, and then
     * recorded in the report with the re-embedding it calls for. A dry run is refused the same way, so
     * it still predicts the load. Anchored rows (D23) stay the importer's refusal even under
     * {@code --migrate}: re-anchoring is that migration's own step.
     */
    private void guardFrozenCorpus(BookDefinition definition, Set<Short> chapters, List<NcertParagraphRow> rows,
            Report report) {
        long embedded = imports.embeddedParagraphs(definition.code());
        if (embedded == 0) {
            report.line("freeze guard: " + definition.code() + " has no embedded paragraph — not frozen yet"
                    + (migrate == null ? "" : "; --migrate has nothing to permit"));
            return;
        }
        List<String> changes = FrozenCorpus.changes(language, imports.alignmentRows(definition.code(), chapters), rows);
        if (changes.isEmpty()) {
            report.line("freeze guard: " + definition.code() + " is frozen (" + embedded + " embedded paragraphs) and "
                    + "this load leaves its paragraph set and English text as they are"
                    + (migrate == null ? "" : "; --migrate has nothing to permit"));
            return;
        }
        if (migrate == null || migrate.isBlank()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, definition.code() + " is frozen ("
                    + embedded + " embedded paragraphs) and this load would change " + changes.size()
                    + " row(s) of its corpus:\n  " + String.join("\n  ", changes) + "\nA change to the frozen corpus "
                    + "is a corpus event, not a load (DECISIONS 2026-09-13): re-run with --migrate \"<reason>\", "
                    + "then `ncert embed --book " + definition.code() + "`; nothing was written");
        }
        report.section("CORPUS EVENT (--migrate): " + migrate)
                .line(changes.size() + " row(s) of the frozen corpus move" + (dryRun ? " in this rehearsal" : "")
                        + "; the vectors of rewritten and deleted rows are dropped below, and `ncert embed --book "
                        + definition.code() + "` earns them back")
                .list(changes);
    }

    /** Why a local file is not this edition's transcription: a Hindi one is Devanagari, an English one Latin. */
    private static String wrongEdition(List<ExtractedPage> pages, BookLanguage language) {
        long devanagari = 0;
        long latin = 0;
        for (ExtractedPage page : pages) {
            for (NcertPage.Paragraph paragraph : page.paragraphs()) {
                for (char c : paragraph.text().toCharArray()) {
                    if (c >= 'ऀ' && c <= 'ॿ') {
                        devanagari++;
                    } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                        latin++;
                    }
                }
            }
        }
        if (language == BookLanguage.hi && devanagari == 0) {
            return "carries no Devanagari";
        }
        return language == BookLanguage.en && devanagari > latin ? "is mostly Devanagari" : null;
    }

    private static byte[] read(Path file) {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read the local extraction " + file.toAbsolutePath().normalize(), e);
        }
    }

    private static void write(Path file, byte[] bytes) {
        try {
            Path parent = file.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(file, bytes);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write the pairs to " + file.toAbsolutePath().normalize(), e);
        }
    }

    /**
     * This load's entries of the corrections file, which is optional: until the first adjudication
     * there is nothing to apply. A file that is present is read in full, so a broken entry for another
     * book still fails this load — the file is one founder-owned input, not one per book.
     */
    private List<NcertCorrection> corrections(BookDefinition definition, Set<Short> chapters, Report report) {
        if (artefactTag != null) {
            // The rulings name spans of the canonical draw; a trial is measured as the model wrote it.
            report.line("corrections: not applied to a scratch run — its draw is loaded as the model wrote it");
            return List.of();
        }
        Path file = io.input(NcertCorrectionsYamlReader.FILE);
        if (!Files.isRegularFile(file)) {
            report.line("no " + NcertCorrectionsYamlReader.FILE + " under the inputs: nothing to apply");
            return List.of();
        }
        report.line("corrections: " + file.normalize() + " sha256 " + Report.sha256(file));
        return NcertCorrections.forLoad(NcertCorrectionsYamlReader.read(file), definition.code(), language, chapters);
    }

    /**
     * The numbered rows, the figure_refs entries that were not figure or table labels, and the English addresses
     * an {@code unprinted} entry left without a row of this edition.
     */
    record Numbered(List<NcertParagraphRow> rows, List<String> droppedRefs, List<EditionPairs.Address> unprinted) {
    }

    /**
     * What figure_refs may hold: a figure or a table label as the books print them, singular or
     * plural — a sentence naming several parts at once prints "Figs. 3.15(a) to (d)" or
     * "Figures 4.8(b)", and taking only the singular dropped four real references from phy11-part1
     * (D15, 2026-09-17). An equation number a paragraph cites is still not a figure reference.
     *
     * <p>The Hindi edition's words are चित्र and सारणी, चित्रों and सारणियों for several; taking only the
     * English ones named every real label of the first Hindi book as dropped (D16). They are matched
     * without {@code \b}, which marks no boundary after a Devanagari vowel sign.
     */
    private static final Pattern FIGURE_REF = Pattern.compile(
            "^(?:(?:Fig(?:ure)?s?\\.?|Tables?)\\b|(?:चित्र(?:ों)?|सारण(?:ी|ियों))(?![\\p{L}\\p{M}])).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * A footnote, which the books set at the foot of the page under a rule. It is the page's last
     * paragraph but never what the next page continues: the body above it is. Before this, four of
     * phy11-part1's nine footnotes had absorbed the following page's opening words — polluting the
     * footnote and truncating the body paragraph, two rows each (D15, 2026-09-17).
     *
     * <p>The mark may touch its first word: the Hindi Physics Part I prints "*Delta t->0 सीमा में…",
     * and asking for a space after the asterisk let that footnote take a continuation (D16). Of the 61
     * English rows that start with an asterisk one lacks the space, and it is a footnote too (chem12-part1
     * §2.1, the last row of its section), so no English load changes.
     */
    private static final Pattern FOOTNOTE = Pattern.compile("^\\*");

    /** Whether a paragraph is a footnote: it starts with the mark. */
    static boolean footnote(String text) {
        return FOOTNOTE.matcher(text.stripLeading()).find();
    }

    /** A deferred paragraph until the next one of its section has taken its number. */
    private record Waiting(ExtractedPage page, int index, NcertPage.Paragraph paragraph) {
    }

    /** Where a row's first paragraph sits on its page, as a {@code move} or an {@code unprinted} entry names it. */
    private record Start(short chapter, int page, int index) {
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
     *
     * @param deferred the paragraphs a {@code defer} correction names (D16): each takes its number after
     *                 the next paragraph its section starts, so a footnote the other edition prints a
     *                 page earlier still sits beside its own English. It stays on its page, and like a
     *                 footnote it is never what the next page continues.
     */
    static Numbered number(List<ExtractedPage> pages, String jsonlKey, Set<NcertCorrections.Deferred> deferred) {
        return number(pages, jsonlKey, deferred, List.of());
    }

    /**
     * @param moves the rows a {@code move} correction names (D16), in file order: once every page break is
     *              joined, each row is taken out of its section and numbered right after the row it names, in
     *              that row's section, and both sections are numbered again from ¶1.
     */
    static Numbered number(List<ExtractedPage> pages, String jsonlKey, Set<NcertCorrections.Deferred> deferred,
            List<NcertCorrections.Moved> moves) {
        return number(pages, jsonlKey, deferred, moves, List.of());
    }

    /**
     * @param unprinted the rows an {@code unprinted} correction names (D16), once per entry: each takes the number
     *                  after the English paragraph its edition does not print, wherever it is numbered — in page
     *                  order, after a deferral, or again after a move — so that number stays the English row's alone;
     *                  an entry with {@code after} leaves the number after its row instead (2026-10-17).
     */
    static Numbered number(List<ExtractedPage> pages, String jsonlKey, Set<NcertCorrections.Deferred> deferred,
            List<NcertCorrections.Moved> moves, List<NcertCorrections.Unprinted> unprinted) {
        Map<Start, Integer> gaps = new LinkedHashMap<>();
        Map<Start, Integer> gapsAfter = new LinkedHashMap<>();
        unprinted.forEach(gap -> (gap.after() ? gapsAfter : gaps).merge(new Start(gap.chapter(), gap.page(), gap.index()),
                1, Integer::sum));
        List<Joined> rows = new ArrayList<>();
        Map<Start, Joined> starts = new HashMap<>();
        List<String> refusals = new ArrayList<>();
        Map<Short, TreeSet<Integer>> redo = new LinkedHashMap<>();
        // (chapter, section) → the last number given out; a resumed section continues its count.
        Map<String, Integer> counters = new HashMap<>();
        // (chapter, section) → the deferred paragraph waiting for that section's next one.
        Map<String, Waiting> waiting = new LinkedHashMap<>();
        List<String> unplaced = new ArrayList<>();
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
                String counter = chapter + " " + section;
                if (deferred.contains(new NcertCorrections.Deferred(chapter, page.page(), index))) {
                    Waiting earlier = waiting.put(counter, new Waiting(page, index, paragraph));
                    if (earlier != null) {
                        unplaced.add(stranded(chapter, section, earlier) + " before the next deferred one");
                    }
                    continue;
                }
                Start start = new Start(chapter, page.page(), index);
                int gap = gaps.getOrDefault(start, 0);
                Joined joined = new Joined(chapter, section, counters.merge(counter, 1 + gap, Integer::sum), page,
                        paragraph);
                joined.unprintedBefore = gap;
                joined.unprintedAfter = gapsAfter.getOrDefault(start, 0);
                counters.merge(counter, joined.unprintedAfter, Integer::sum);
                rows.add(joined);
                starts.put(start, joined);
                // A footnote is never the paragraph the next page continues, so it never becomes the
                // anchor: the body above it stays the one a continuation joins to.
                if (!footnote(paragraph.text())) {
                    last = joined;
                }
                Waiting held = waiting.remove(counter);
                if (held != null) {
                    Start heldStart = new Start(chapter, held.page().page(), held.index());
                    int heldGap = gaps.getOrDefault(heldStart, 0);
                    Joined placed = new Joined(chapter, section, counters.merge(counter, 1 + heldGap, Integer::sum),
                            held.page(), held.paragraph());
                    placed.unprintedBefore = heldGap;
                    placed.unprintedAfter = gapsAfter.getOrDefault(heldStart, 0);
                    counters.merge(counter, placed.unprintedAfter, Integer::sum);
                    rows.add(placed);
                    starts.put(heldStart, placed);
                }
            }
        }
        for (Map.Entry<String, Waiting> left : waiting.entrySet()) {
            String[] key = left.getKey().split(" ");
            unplaced.add(stranded(Short.parseShort(key[0]), key[1], left.getValue()));
        }
        if (!unplaced.isEmpty()) {
            throw new InputFormatException(Path.of(NcertCorrectionsYamlReader.FILE), 0, unplaced.size()
                    + " defer correction(s) cannot be placed:\n  " + String.join("\n  ", unplaced)
                    + "\nFix each entry against the page, or remove it.");
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
        // The numbering's own net, as for a move: the corrections step refuses a continuation by name first.
        List<String> unnumbered = unprinted.stream()
                .filter(gap -> !starts.containsKey(new Start(gap.chapter(), gap.page(), gap.index())))
                .map(gap -> "ch " + gap.chapter() + " page " + gap.page() + ": the row starting \"" + gap.at()
                        + "\" is not a row of its own")
                .toList();
        if (!unnumbered.isEmpty()) {
            throw new InputFormatException(Path.of(NcertCorrectionsYamlReader.FILE), 0, unnumbered.size()
                    + " unprinted correction(s) cannot be placed:\n  " + String.join("\n  ", unnumbered)
                    + "\nFix each entry against the page, or remove it.");
        }
        move(rows, starts, moves);
        // `after` is for a section's last English paragraph, which no row follows; on any other row the gap is the
        // next row's, and a plain entry there says so — one spelling, which a move or a deferral cannot pull apart.
        Map<String, Integer> lastNumber = new HashMap<>();
        rows.forEach(row -> lastNumber.merge(row.counter(), row.paraNo, Math::max));
        List<String> notLast = rows.stream().filter(row -> row.unprintedAfter > 0 && row.paraNo < lastNumber.get(row.counter()))
                .map(row -> "ch " + row.chapterNo + " §" + row.section + " ¶" + row.paraNo + " is not its section's last row")
                .toList();
        if (!notLast.isEmpty()) {
            throw new InputFormatException(Path.of(NcertCorrectionsYamlReader.FILE), 0, notLast.size()
                    + " unprinted correction(s) with after name a row another follows:\n  " + String.join("\n  ", notLast)
                    + "\nName the row after the gap, without after.");
        }
        List<EditionPairs.Address> left = new ArrayList<>();
        for (Joined row : rows) {
            for (int before = row.unprintedBefore; before > 0; before--) {
                left.add(new EditionPairs.Address(row.chapterNo, row.section, row.paraNo - before));
            }
            for (int after = 1; after <= row.unprintedAfter; after++) {
                left.add(new EditionPairs.Address(row.chapterNo, row.section, row.paraNo + after));
            }
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
        return new Numbered(List.copyOf(result), List.copyOf(dropped), List.copyOf(left));
    }

    /**
     * The {@code move} entries, on whole rows: each row leaves its section and is numbered right after the row it
     * names, in that row's section; every section a move touched is then numbered from ¶1 in its new order, still
     * leaving the numbers an {@code unprinted} entry leaves. A row's words, pages and page parts go with it, so
     * nothing a continuation joined is split again.
     */
    private static void move(List<Joined> rows, Map<Start, Joined> starts, List<NcertCorrections.Moved> moves) {
        if (moves.isEmpty()) {
            return;
        }
        Map<String, List<Joined>> sections = new LinkedHashMap<>();
        rows.stream().sorted(Comparator.comparingInt(row -> row.paraNo))
                .forEach(row -> sections.computeIfAbsent(row.counter(), key -> new ArrayList<>()).add(row));
        Set<String> touched = new LinkedHashSet<>();
        List<String> unplaced = new ArrayList<>();
        for (NcertCorrections.Moved move : moves) {
            Joined row = starts.get(new Start(move.chapter(), move.page(), move.index()));
            Joined after = starts.get(new Start(move.chapter(), move.afterPage(), move.afterIndex()));
            if (row == null || after == null) {
                unplaced.add("ch " + move.chapter() + " page " + move.page() + ": the row starting \"" + move.at()
                        + "\" or the row starting \"" + move.after() + "\" (page " + move.afterPage()
                        + ") is not a row of its own");
                continue;
            }
            touched.add(row.counter());
            sections.get(row.counter()).remove(row);
            List<Joined> target = sections.get(after.counter());
            target.add(target.indexOf(after) + 1, row);
            row.section = after.section;
            touched.add(after.counter());
        }
        if (!unplaced.isEmpty()) {
            throw new InputFormatException(Path.of(NcertCorrectionsYamlReader.FILE), 0, unplaced.size()
                    + " move correction(s) cannot be placed:\n  " + String.join("\n  ", unplaced)
                    + "\nFix each entry against the page, or remove it.");
        }
        for (String key : touched) {
            int paraNo = 0;
            for (Joined row : sections.get(key)) {
                // A row beside an English paragraph its edition does not print leaves that number wherever it goes.
                paraNo += 1 + row.unprintedBefore;
                row.paraNo = paraNo;
                paraNo += row.unprintedAfter;
            }
        }
    }

    private static String stranded(short chapter, String section, Waiting waiting) {
        String text = waiting.paragraph().text().strip().replaceAll("\\s+", " ");
        return "ch " + chapter + " page " + waiting.page().page() + ": the deferred paragraph starting \""
                + (text.length() > 60 ? text.substring(0, 60) + "…" : text) + "\" has no later paragraph of §"
                + section + " to follow";
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
        // Not final: a move entry gives a row another section and both sections new numbers (D16).
        private String section;
        private int paraNo;
        // How many English paragraphs right before this row its edition does not print: numbers it leaves (D16).
        private int unprintedBefore;
        // And right after it, where no row follows them in the section: the numbers it leaves after its own.
        private int unprintedAfter;
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

        /** The (chapter, section) key the numbering counts in. */
        private String counter() {
            return chapterNo + " " + section;
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
