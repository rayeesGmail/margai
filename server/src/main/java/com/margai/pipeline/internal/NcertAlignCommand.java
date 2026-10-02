package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiSpend;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.ParagraphToEmbed;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code ncert align} (TECH_PLAN §6.3, D16): the Hindi rows of a book set against its canonical English ones,
 * section by section. English is canonical and Hindi follows it (DECISIONS 2026-10-01), and the load put each
 * Hindi paragraph at the English address of the same place in its section — so a Hindi print that keeps two
 * English paragraphs in one, as it often does, shifts every pair after it. This finds where: each Hindi
 * paragraph embedded on the pin, its similarity to every English row of its section read from the stored
 * vectors ({@link CurriculumImport#similarityToEnglish}), and {@link Alignment} proposing the joins and splits
 * that make the pairs one to one. It changes no row and stores no vector; the corrections file does the rest.
 *
 * <p>It also names each pair's numbers that one edition carries and the other does not — a misread digit on
 * either side — and draws the ✅ sample (PLAN D16, DECISIONS 2026-10-01): aligned pairs weighted toward the
 * matra- and conjunct-dense paragraphs where a Devanagari reading fails, seeded so a re-run draws the same.
 */
@Component
@Profile("pipeline")
@Command(name = "align", mixinStandardHelpOptions = true,
        description = "Set the book's Hindi rows against its English ones: proposed joins and splits, numbers, the ✅ sample.")
class NcertAlignCommand extends NcertBookCommand {

    /** A pair below this similarity is listed; above what strangers reach, below what a translation should. */
    static final double WEAK_PAIR = Alignment.STRANGERS + 0.1;

    private static final Pattern NUMBER = Pattern.compile("\\d+(?:\\.\\d+)?");
    private static final int EXCERPT_WORDS = 8;

    @Option(names = "--sample", paramLabel = "N", defaultValue = "40",
            description = "How many aligned pairs the ✅ sample draws (default: ${DEFAULT-VALUE}).")
    int sample;

    @Option(names = "--founder", paramLabel = "N", defaultValue = "10",
            description = "How many of the sample — the most matra- and conjunct-dense — the founder reads (default: ${DEFAULT-VALUE}).")
    int founder;

    @Option(names = "--seed", paramLabel = "N", defaultValue = "16",
            description = "The sample's seed, so a re-run draws the same pairs (default: ${DEFAULT-VALUE}).")
    long seed;

    private final CurriculumImport imports;
    private final EmbeddingService embeddings;
    private final AiSpend spend;
    private final AiClientInfo client;
    private final PipelineProperties properties;

    NcertAlignCommand(CurriculumImport imports, EmbeddingService embeddings, AiSpend spend, AiClientInfo client,
            PipelineProperties properties, Reports reports) {
        super(reports);
        this.imports = imports;
        this.embeddings = embeddings;
        this.spend = spend;
        this.client = client;
        this.properties = properties;
    }

    /** One aligned pair: the English row and the Hindi row the mapping put beside it. */
    private record Pair(AlignmentRow en, AlignmentRow hi, double similarity) {
    }

    @Override
    void run(BookDefinition definition, List<BookDefinition.Chapter> selected, Report report) {
        if (!client.isLive()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0,
                    "the AI client is the fake (" + client + "): its vectors would make every similarity a fixture "
                            + "and the proposals noise — run with AI_LIVE=1 and the live profile");
        }
        List<Short> chapters = selected.stream().map(BookDefinition.Chapter::no).toList();
        List<ParagraphToEmbed> unembedded = imports.paragraphsToEmbed(definition.code(), chapters, false);
        if (!unembedded.isEmpty()) {
            throw new InputFormatException(Path.of(NcertRegisterCommand.FILE), 0, unembedded.size()
                    + " English row(s) of these chapters carry no vector, and each would read as a stranger to every "
                    + "Hindi paragraph — run `ncert embed` first: "
                    + unembedded.stream().map(ParagraphToEmbed::address).limit(10).toList()
                    + (unembedded.size() > 10 ? " …" : ""));
        }
        String runId = "pipeline-ncert-align-" + UUID.randomUUID();
        report.line("request id: " + runId);
        report.line("English is canonical; each Hindi paragraph is embedded on the pin and compared to the stored "
                + "English vectors of its section — nothing is stored or changed");
        AiCallContext ctx = AiCallContext.system(runId);
        List<AlignmentRow> rows = imports.alignmentRows(definition.code(), chapters);

        List<List<String>> sections = new ArrayList<>();
        List<String> proposals = new ArrayList<>();
        List<String> weak = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        List<Pair> pairs = new ArrayList<>();
        // A chapter whose Hindi is not loaded yet would list every English row as unpaired; it is named instead.
        Set<Short> withHindi = rows.stream().filter(row -> row.textHi() != null).map(AlignmentRow::chapterNo)
                .collect(Collectors.toSet());
        List<Short> notAligned = chapters.stream().filter(no -> !withHindi.contains(no)).toList();
        CallPacer pacer = CallPacer.perMinute(properties.embedCallsPerMinute(), Thread::sleep);
        long toEmbed = rows.stream().filter(row -> row.textHi() != null).count();
        if (pacer.isThrottled() && toEmbed > 0) {
            report.line("pacing: " + properties.embedCallsPerMinute() + " calls/minute"
                    + " (margai.pipeline.embed-calls-per-minute) — about "
                    + pacer.estimateFor(Math.toIntExact(toEmbed)).toMinutes() + " min for " + toEmbed
                    + " Hindi paragraphs");
        }
        int english = 0;
        int hindi = 0;
        try {
            for (Map.Entry<String, List<AlignmentRow>> section : bySection(rows).entrySet()) {
                if (!withHindi.contains(section.getValue().getFirst().chapterNo())) {
                    continue;
                }
                List<AlignmentRow> en = section.getValue().stream().filter(row -> row.textEn() != null).toList();
                List<AlignmentRow> hi = section.getValue().stream().filter(row -> row.textHi() != null).toList();
                english += en.size();
                hindi += hi.size();
                List<UUID> englishIds = en.stream().map(AlignmentRow::id).toList();
                List<Map<UUID, Double>> similarity = new ArrayList<>();
                for (AlignmentRow row : hi) {
                    pacer.awaitTurn();
                    similarity.add(imports.similarityToEnglish(definition.code(),
                            embeddings.ofDocument(row.textHi(), ctx), englishIds));
                }
                List<Alignment.Step> steps = Alignment.map(hi.size(), en.size(),
                        (h, e) -> similarity.get(h - 1).getOrDefault(en.get(e - 1).id(), 0.0));
                AlignmentRow first = section.getValue().getFirst();
                String where = "ch " + first.chapterNo() + " §" + first.section();
                long reshaped = 0;
                for (Alignment.Step step : steps) {
                    if (step.kind() == Alignment.Step.Kind.PAIR) {
                        AlignmentRow enRow = en.get(step.en().getFirst() - 1);
                        AlignmentRow hiRow = hi.get(step.hi().getFirst() - 1);
                        double alike = similarity.get(step.hi().getFirst() - 1).getOrDefault(enRow.id(), 0.0);
                        pairs.add(new Pair(enRow, hiRow, alike));
                        if (alike < WEAK_PAIR) {
                            weak.add(where + ": " + step + " — similarity " + "%.2f".formatted(alike)
                                    + " — en «" + excerpt(enRow.textEn()) + "» · hi «" + excerpt(hiRow.textHi()) + "»");
                        }
                        String mismatch = numbers(enRow.textEn(), hiRow.textHi());
                        if (!mismatch.isEmpty()) {
                            numbers.add(enRow.address() + ": " + mismatch);
                        }
                    } else {
                        reshaped++;
                        proposals.add(where + ": " + step);
                    }
                }
                if (en.size() != hi.size() || reshaped > 0) {
                    sections.add(List.of(String.valueOf(first.chapterNo()), first.section(), String.valueOf(en.size()),
                            String.valueOf(hi.size()), String.valueOf(reshaped)));
                }
            }
        } finally {
            AiSpend.RunSpend bill = spend.of(runId);
            report.section("cost").table(List.of("calls", "input tokens", "spent"),
                    List.of(List.of(String.valueOf(bill.calls()), String.valueOf(bill.usage().inputTokens()),
                            bill.rupees())));
        }

        report.read(english + " English and " + hindi + " Hindi row(s) in " + (chapters.size() - notAligned.size())
                + " chapter(s)");
        if (!notAligned.isEmpty()) {
            report.line("chapter(s) with no Hindi row, not aligned: "
                    + notAligned.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
        report.section("sections whose rows do not pair one to one")
                .table(List.of("chapter", "section", "en", "hi", "proposals"), sections);
        report.section("proposed Hindi joins and splits — adjudicate each; nothing is applied")
                .list(proposals, "none: every section pairs one to one");
        report.section("pairs weaker than " + "%.2f".formatted(WEAK_PAIR) + " — read both against the pages")
                .list(weak, "none");
        report.section("numbers one edition of a pair carries and the other does not")
                .list(numbers, "none");
        sample(pairs, report);
    }

    /** The rows grouped by chapter and section, in reading order. */
    private static Map<String, List<AlignmentRow>> bySection(List<AlignmentRow> rows) {
        Map<String, List<AlignmentRow>> sections = new LinkedHashMap<>();
        rows.forEach(row -> sections.computeIfAbsent(row.chapterNo() + "/" + row.section(), key -> new ArrayList<>())
                .add(row));
        return sections;
    }

    /** The numbers only one side of a pair carries, or empty when they agree. */
    static String numbers(String en, String hi) {
        Map<String, Integer> left = counts(en);
        Map<String, Integer> right = counts(hi);
        List<String> onlyEn = difference(left, right);
        List<String> onlyHi = difference(right, left);
        if (onlyEn.isEmpty() && onlyHi.isEmpty()) {
            return "";
        }
        return "only in en " + (onlyEn.isEmpty() ? "—" : String.join(", ", onlyEn))
                + " · only in hi " + (onlyHi.isEmpty() ? "—" : String.join(", ", onlyHi));
    }

    private static Map<String, Integer> counts(String text) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        Matcher matcher = NUMBER.matcher(text == null ? "" : text);
        while (matcher.find()) {
            counts.merge(matcher.group(), 1, Integer::sum);
        }
        return counts;
    }

    private static List<String> difference(Map<String, Integer> from, Map<String, Integer> against) {
        List<String> missing = new ArrayList<>();
        from.forEach((number, count) -> {
            for (int extra = against.getOrDefault(number, 0); extra < count; extra++) {
                missing.add(number);
            }
        });
        return missing;
    }

    /**
     * The ✅ sample: pairs drawn with weight on Devanagari density — signs and halants per character, where a
     * reading fails — by weighted sampling without replacement, seeded; the densest {@code --founder} of them
     * are the founder's to read (DECISIONS 2026-10-01).
     */
    private void sample(List<Pair> pairs, Report report) {
        Random random = new Random(seed);
        Map<Pair, Double> keys = new HashMap<>();
        for (Pair pair : pairs) {
            double weight = Math.max(density(pair.hi().textHi()), 1e-6);
            keys.put(pair, Math.pow(random.nextDouble(), 1 / weight));
        }
        List<Pair> drawn = pairs.stream().sorted(Comparator.comparing((Pair pair) -> keys.get(pair)).reversed())
                .limit(Math.max(sample, 0)).toList();
        List<Pair> founders = drawn.stream()
                .sorted(Comparator.comparingDouble((Pair pair) -> density(pair.hi().textHi())).reversed())
                .limit(Math.max(founder, 0)).toList();
        // The pairs came in reading order, so the sample keeps theirs.
        Set<Pair> chosen = new HashSet<>(drawn);
        List<Pair> ordered = pairs.stream().filter(chosen::contains).toList();
        Report section = report.section("the ✅ sample: " + ordered.size() + " aligned pair(s), seed " + seed)
                .line("read each Hindi paragraph against its page and its English partner; "
                        + founders.size() + " of them, the most matra- and conjunct-dense, are the founder's");
        int index = 0;
        for (Pair pair : ordered) {
            section.line("")
                    .line("### " + (++index) + ". " + pair.en().address() + " — en p" + pages(pair.en().pagesEn())
                            + ", hi p" + pages(pair.hi().pagesHi()) + ", similarity " + "%.2f".formatted(pair.similarity())
                            + (founders.contains(pair) ? " — the founder reads this one" : ""))
                    .line("- en: " + pair.en().textEn())
                    .line("- hi: " + pair.hi().textHi());
        }
    }

    /** Dependent vowel signs and halants per character of a Hindi text. */
    static double density(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        long marks = text.chars().filter(c -> (c >= 'ा' && c <= '्') || c == 'ॢ' || c == 'ॣ').count();
        return (double) marks / text.length();
    }

    private static String pages(List<Integer> pages) {
        return pages.isEmpty() ? "—" : pages.stream().map(String::valueOf).reduce((a, b) -> a + "–" + b).orElseThrow();
    }

    private static String excerpt(String text) {
        String[] words = text == null ? new String[0] : text.strip().split("\\s+");
        return String.join(" ", Arrays.copyOf(words, Math.min(words.length, EXCERPT_WORDS)))
                + (words.length > EXCERPT_WORDS ? "…" : "");
    }
}
