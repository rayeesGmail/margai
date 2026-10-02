package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.tasks.EmbeddingService;
import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.ParagraphToEmbed;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert align} (TECH_PLAN §6.3, D16): each section's Hindi paragraphs mapped onto its canonical English
 * ones, the joins and splits the Hindi needs proposed, never applied, and the ✅ sample drawn.
 */
class NcertAlignCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final PipelineCommandTest.RecordingImport imports = new PipelineCommandTest.RecordingImport();
    private final List<String> embedded = new ArrayList<>();
    /** Each Hindi text's index, carried in its vector, so the stub's similarity can look the pair up. */
    private final Map<String, Integer> hindiIndex = new HashMap<>();
    private final Map<UUID, Integer> englishIndex = new HashMap<>();
    private final Map<String, Double> similarities = new HashMap<>();
    private AiClientInfo client = new AiClientInfo("anthropic", List.of("ledger", "breaker"));
    private int embedCallsPerMinute = 0;
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), """
                books:
                  - code: phy11-part1
                    subject: physics
                    class_level: 11
                    part: 1
                    title_en: "Physics Part-I, Textbook for Class XI"
                    edition_year: 2022
                    source:
                      en: source/ncert/2022-ed/en/phy11-part1/
                      hi: source/ncert/2022-ed/hi/phy11-part1/
                    chapters:
                      - {no: 1, en: keph101.pdf, hi: khph101.pdf}
                      - {no: 2, en: keph102.pdf, hi: khph102.pdf}
                """);
        imports.similarity = (vector, id) -> similarities.getOrDefault(
                Math.round(vector[0]) + ":" + englishIndex.get(id), 0.2);
        build();
    }

    private void build() {
        Reports writer = new Reports(ReportTest.CLOCK);
        EmbeddingService embeddings = new EmbeddingService() {
            @Override
            public float[] ofDocument(String text, AiCallContext ctx) {
                embedded.add(text);
                return new float[] {hindiIndex.getOrDefault(text, 0)};
            }

            @Override
            public float[] ofQuery(String text, AiCallContext ctx) {
                throw new AssertionError("align embeds documents only");
            }
        };
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertAlignCommand.class) {
                    PipelineProperties properties = new PipelineProperties(72, 10, 1, "claude-sonnet-5",
                            "claude-opus-5", "claude-opus-5-5", 100, embedCallsPerMinute, 40);
                    return cls.cast(new NcertAlignCommand(imports, embeddings, new NcertExtractCommandTest.StubSpend(),
                            client, properties, writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter printer = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(printer).setErr(printer);
    }

    /** Section 1.3: English ¶1–3, Hindi ¶1–2, the second Hindi paragraph carrying English ¶2 and ¶3. */
    private void aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne() {
        List<AlignmentRow> rows = new ArrayList<>();
        rows.add(row("1.3", 1, "The first paragraph, 1.28 m long.", "पहला अनुच्छेद, 1.28 m लंबा।", 1));
        rows.add(row("1.3", 2, "The second paragraph.", "दूसरा और तीसरा अनुच्छेद एक साथ।", 2));
        rows.add(row("1.3", 3, "The third paragraph.", null, 0));
        imports.alignmentAnswer = rows;
        similarities.put("1:1", 0.82);
        similarities.put("2:2", 0.71);
        similarities.put("2:3", 0.69);
    }

    private AlignmentRow row(String section, int paraNo, String en, String hi, int hindi) {
        return row(1, section, paraNo, en, hi, hindi);
    }

    private AlignmentRow row(int chapter, String section, int paraNo, String en, String hi, int hindi) {
        UUID id = UUID.randomUUID();
        englishIndex.put(id, paraNo);
        if (hi != null) {
            hindiIndex.put(hi, hindi);
        }
        return new AlignmentRow(id, (short) chapter, section, (short) paraNo, en, hi, List.of(3),
                hi == null ? List.of() : List.of(3));
    }

    private int run(String... extra) {
        List<String> args = new ArrayList<>(List.of("ncert", "align", "--book", "phy11-part1",
                "--inputs", inputs.toString(), "--reports", reports.toString()));
        args.addAll(List.of(extra));
        return commandLine.execute(args.toArray(String[]::new));
    }

    @Test
    void aHindiParagraphCarryingTwoEnglishOnesIsProposedForASplit() {
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();

        assertThat(run()).isZero();

        assertThat(embedded).containsExactly("पहला अनुच्छेद, 1.28 m लंबा।", "दूसरा और तीसरा अनुच्छेद एक साथ।");
        assertThat(out.toString())
                .contains("## proposed Hindi joins and splits — adjudicate each; nothing is applied")
                .contains("- ch 1 §1.3: hi ¶2 = en ¶2+¶3 (split hi)")
                .contains("| 1 | 1.3 | 3 | 2 | 1 |");
    }

    /** A section the Hindi lacks, inside a chapter it carries, is named; a chapter with no Hindi yet is one line. */
    @Test
    void aSectionWithoutHindiIsNamedAndAChapterWithoutHindiIsNotAligned() {
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();
        List<AlignmentRow> rows = new ArrayList<>(imports.alignmentAnswer);
        rows.add(row("1.4", 1, "A section the Hindi print lacks.", null, 0));
        rows.add(row(2, "2.1", 1, "A chapter whose Hindi is not loaded yet.", null, 0));
        imports.alignmentAnswer = rows;

        assertThat(run()).isZero();

        assertThat(out.toString())
                .contains("- ch 1 §1.4: en ¶1 has no hindi")
                .contains("| 1 | 1.4 | 1 | 0 | 1 |")
                .contains("chapter(s) with no Hindi row, not aligned: 2")
                .doesNotContain("§2.1");
    }

    /** The embedding provider's allowance is the one {@code ncert embed} keeps to. */
    @Test
    void theHindiEmbedsKeepToTheConfiguredPace() {
        embedCallsPerMinute = 6000;
        build();
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();

        assertThat(run()).isZero();

        assertThat(out.toString()).contains("pacing: 6000 calls/minute (margai.pipeline.embed-calls-per-minute)");
    }

    /** A pair's numbers are the same in both editions, or one of them was misread. */
    @Test
    void aNumberOneEditionCarriesAndItsPartnerDoesNotIsNamed() {
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();
        imports.alignmentAnswer = List.of(row("1.3", 1, "The diameter is 1.28 m.", "व्यास 1.29 m है।", 1));

        assertThat(run()).isZero();

        assertThat(out.toString()).contains("## numbers one edition of a pair carries and the other does not")
                .contains("- ch 1 §1.3 ¶1: only in en 1.28 · only in hi 1.29");
    }

    @Test
    void theSampleIsDrawnFromThePairsAndNamesTheFoundersShare() {
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();

        assertThat(run("--sample", "1", "--founder", "1")).isZero();

        assertThat(out.toString())
                .contains("## the ✅ sample: 1 aligned pair(s), seed 16")
                .contains("### 1. ch 1 §1.3 ¶1 — en p3, hi p3, similarity 0.82 — the founder reads this one")
                .contains("- en: The first paragraph, 1.28 m long.")
                .contains("- hi: पहला अनुच्छेद, 1.28 m लंबा।");
    }

    /** An English row with no vector would read as a stranger to every Hindi paragraph and skew the mapping. */
    @Test
    void refusesWhileAnEnglishRowOfTheChaptersHasNoVector() {
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();
        imports.toEmbedAnswer = List.of(new ParagraphToEmbed(UUID.randomUUID(), (short) 1, "1.3", (short) 3,
                "The third paragraph."));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(embedded).isEmpty();
        assertThat(out.toString()).contains("1 English row(s) of these chapters carry no vector")
                .contains("ch 1 §1.3 ¶3");
    }

    @Test
    void refusesTheFakeClient() {
        client = new AiClientInfo(AiClientInfo.FAKE, List.of("ledger"));
        build();
        aSectionWhoseHindiKeepsTwoEnglishParagraphsInOne();

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(embedded).isEmpty();
        assertThat(out.toString()).contains("the AI client is the fake");
    }
}
