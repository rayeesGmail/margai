package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.common.api.AttemptType;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertRegisterReport;
import com.margai.curriculum.api.NcertVerificationRow;
import com.margai.curriculum.api.ParagraphEmbedding;
import com.margai.curriculum.api.ParagraphToEmbed;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * The command tree without Spring: a factory that hands the leaf commands a recording
 * {@link CurriculumImport} and a {@link Reports} on a fixed clock, and everything else to
 * picocli's default factory. Every D13 command reads its input under {@code --inputs}, passes the
 * rows on and writes its dated report under {@code --reports}; a missing file is a usage error
 * with the path and no report; a malformed file, a contradicted taxonomy and an unexpected failure
 * all fail the run and still leave a report; groups without a subcommand and unknown commands are
 * usage errors; Spring's own arguments never reach picocli.
 */
class PipelineCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final StringWriter err = new StringWriter();
    private final RecordingImport imports = new RecordingImport();
    private CommandLine commandLine;

    @BeforeEach
    void commandLine() {
        Reports writer = new Reports(ReportTest.CLOCK);
        commandLine = PipelineRunner.commandLine(siblingFactory(imports, writer))
                .setOut(new PrintWriter(out, true))
                .setErr(new PrintWriter(err, true));
    }

    @Test
    void eachCommandReadsItsInputHandsTheRowsOnAndWritesItsReport() throws IOException {
        String committed = InputReadersTest.INPUTS.toString();

        assertThat(commandLine.execute("taxonomy", "load", "--inputs", committed, "--reports", reports.toString())).isZero();
        assertThat(commandLine.execute("taxonomy", "prerequisites", "--inputs", committed, "--reports", reports.toString())).isZero();
        assertThat(commandLine.execute("backbone", "load", "--inputs", committed, "--reports", reports.toString())).isZero();
        assertThat(commandLine.execute("cutoffs", "load", "--inputs", committed, "--reports", reports.toString())).isZero();

        assertThat(imports.nodes).hasSize(516);
        assertThat(imports.edges).hasSize(104);
        assertThat(imports.tracks).hasSize(4);
        assertThat(imports.cutoffs).hasSize(40);
        assertThat(out.toString())
                .contains("# margai-pipeline taxonomy load\n")
                .contains("- read: 516 nodes\n- result: ok\n")
                .contains("| inserted | updated | unchanged |\n|---|---|---|\n| 516 | 0 | 0 |\n")
                .contains("## orphans (in the database, not in the file)\n\nnone\n")
                .contains("- read: 104 edges\n")
                .contains("| 104 | 0 | 104 | 83 | passed (Kahn's remainder empty; a remainder fails the run) |\n")
                .contains("- read: 4 tracks, 744 steps\n")
                .contains("| 744 | 0 | 0 | 0 |\n")
                .contains("## nodes in no track (subjects, units and chapters no step names)\n\nnone\n")
                .contains("## orphan tracks (in the database, not in the file)\n\nnone\n")
                .contains("- read: 40 rows\n")
                .contains("| 40 | 0 | 0 |\n")
                .contains("report: " + reports.resolve("2026-09-12-taxonomy-load.md"));
        assertThat(err.toString()).isEmpty();
        assertThat(Files.list(reports).map(path -> path.getFileName().toString()))
                .containsExactlyInAnyOrder("2026-09-12-taxonomy-load.md", "2026-09-12-taxonomy-prerequisites.md",
                        "2026-09-12-backbone-load.md", "2026-09-12-cutoffs-load.md");
        assertThat(Files.readString(reports.resolve("2026-09-12-backbone-load.md")))
                .contains("| track | steps |\n|---|---|\n| fresher_2yr | 210 |\n| fresher_1yr | 166 |\n| dropper | 178 |\n| repeater | 190 |\n");
    }

    @Test
    void ncertRegisterReadsBooksYamlHandsTheRowsOnAndWritesItsReport() throws IOException {
        String committed = InputReadersTest.INPUTS.toString();

        assertThat(commandLine.execute("ncert", "register", "--inputs", committed, "--reports", reports.toString()))
                .isZero();

        assertThat(imports.books).hasSize(10);
        assertThat(imports.books).extracting(NcertBookRow::code).contains("bio11", "phy11-part1");
        assertThat(out.toString())
                .contains("# margai-pipeline ncert register\n")
                .contains("- read: 10 books\n- result: ok\n")
                .contains("| inserted | updated | unchanged |\n|---|---|---|\n| 10 | 0 | 0 |\n")
                .contains("| bio11 | biology | 11 | — | 1–19 | 19 | en + hi |\n")
                .contains("| phy11-part2 | physics | 11 | 2 | 8–14 | 7 | en + hi |\n")
                .contains("## books in the database that books.yaml no longer names\n\nnone\n")
                .contains("report: " + reports.resolve("2026-09-12-ncert-register.md"));
        assertThat(err.toString()).isEmpty();
    }

    @Test
    void aMissingInputFileIsAUsageErrorNamingThePathAndWritesNoReport() throws IOException {
        assertThat(run("taxonomy", "load")).isEqualTo(InputFileCommand.EXIT_USAGE);

        assertThat(err.toString()).contains("input file not found: " + inputs.resolve(TaxonomyLoadCommand.FILE));
        assertThat(out.toString()).isEmpty();
        assertThat(imports.nodes).isNull();
        assertThat(Files.list(reports)).isEmpty();
    }

    @Test
    void aMalformedInputFailsTheRunAndTheReportSaysWhy() throws IOException {
        Files.writeString(inputs.resolve(TaxonomyLoadCommand.FILE), "code,subject\nPHY,physics\n");

        assertThat(run("taxonomy", "load")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(err.toString()).contains("FAILED: taxonomy.csv:1: header must be").doesNotContain("\tat ");
        assertThat(out.toString()).contains("- result: FAILED: taxonomy.csv:1: header must be");
        assertThat(imports.nodes).isNull();
        assertThat(Files.readString(reports.resolve("2026-09-12-taxonomy-load.md")))
                .contains("- read: nothing yet\n- result: FAILED: taxonomy.csv:1: header must be");
    }

    @Test
    void aContradictedTaxonomyFailsTheRunWithTheLoadersReason() throws IOException {
        imports.failure = new CurriculumImportException("parent 'PHY.U01' of PHY.11.UNITS is not in the file");

        assertThat(commandLine.execute("taxonomy", "load", "--inputs", InputReadersTest.INPUTS.toString(),
                "--reports", reports.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(err.toString())
                .contains("FAILED: parent 'PHY.U01' of PHY.11.UNITS is not in the file")
                .doesNotContain("\tat ");
        assertThat(Files.readString(reports.resolve("2026-09-12-taxonomy-load.md")))
                .contains("- read: 516 nodes\n- result: FAILED: parent 'PHY.U01' of PHY.11.UNITS is not in the file\n");
    }

    @Test
    void anUnexpectedFailureStillLeavesAReportAndPrintsTheStackTrace() throws IOException {
        imports.failure = new IllegalStateException("database unreachable");

        assertThat(commandLine.execute("taxonomy", "load", "--inputs", InputReadersTest.INPUTS.toString(),
                "--reports", reports.toString())).isEqualTo(InputFileCommand.EXIT_FAILED);

        // The stack trace is the exception's, so its frames are where the test built it, not where the fake threw it.
        assertThat(err.toString())
                .contains("FAILED: IllegalStateException: database unreachable")
                .contains("\tat com.margai.pipeline.internal.PipelineCommandTest");
        assertThat(Files.readString(reports.resolve("2026-09-12-taxonomy-load.md")))
                .contains("- result: FAILED: IllegalStateException: database unreachable\n");
    }

    @Test
    void theDirectoriesDefaultToThePipelineDirectoryBesideServer() {
        CommandLine.ParseResult parsed = commandLine.parseArgs("taxonomy", "load");
        InputFileCommand load = (InputFileCommand) parsed.subcommand().subcommand().commandSpec().userObject();

        InputsMixin io = load.io;
        assertThat(io.inputs).isEqualTo(Path.of(InputsMixin.DEFAULT_INPUTS));
        assertThat(io.reports).isEqualTo(Path.of(InputsMixin.DEFAULT_REPORTS));
    }

    @Test
    void aGroupWithoutASubcommandIsAUsageError() {
        assertThat(commandLine.execute()).isEqualTo(InputFileCommand.EXIT_USAGE);
        assertThat(commandLine.execute("taxonomy")).isEqualTo(InputFileCommand.EXIT_USAGE);
        assertThat(commandLine.execute("backbone")).isEqualTo(InputFileCommand.EXIT_USAGE);
        assertThat(commandLine.execute("cutoffs")).isEqualTo(InputFileCommand.EXIT_USAGE);

        assertThat(err.toString()).contains("Missing subcommand");
    }

    @Test
    void anUnknownCommandIsAUsageError() {
        assertThat(commandLine.execute("ncert", "extract")).isEqualTo(InputFileCommand.EXIT_USAGE);
        assertThat(err.toString()).contains("ncert");
    }

    @Test
    void helpIsACleanRun() {
        assertThat(commandLine.execute("--help")).isZero();
        assertThat(out.toString()).contains("taxonomy").contains("backbone").contains("cutoffs");
    }

    @Test
    void springsOwnArgumentsAreNotPassedToPicocli() {
        assertThat(PipelineRunner.commandArgs(new String[] {
                "--spring.profiles.active=pipeline", "taxonomy", "load", "--inputs", "/tmp/in", "--spring.main.banner-mode=off"}))
                .containsExactly("taxonomy", "load", "--inputs", "/tmp/in");
    }

    private int run(String group, String command) {
        return commandLine.execute(group, command, "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    /**
     * The leaf commands every test in this package needs picocli to be able to build, whatever it
     * is actually exercising: the tree is constructed whole, so a sibling without a constructor
     * fails the run before the command under test is reached.
     */
    static CommandLine.IFactory siblingFactory(CurriculumImport imports, Reports writer) {
        return new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == TaxonomyLoadCommand.class) {
                    return cls.cast(new TaxonomyLoadCommand(imports, writer));
                }
                if (cls == TaxonomyPrerequisitesCommand.class) {
                    return cls.cast(new TaxonomyPrerequisitesCommand(imports, writer));
                }
                if (cls == BackboneLoadCommand.class) {
                    return cls.cast(new BackboneLoadCommand(imports, writer));
                }
                if (cls == CutoffsLoadCommand.class) {
                    return cls.cast(new CutoffsLoadCommand(imports, writer));
                }
                if (cls == NcertRegisterCommand.class) {
                    return cls.cast(new NcertRegisterCommand(imports, writer));
                }
                if (cls == NcertRenderCommand.class) {
                    return cls.cast(new NcertRenderCommand(new NcertRenderCommandTest.RecordingStore(), imports,
                            new PipelineProperties(72, 10, 1, "claude-sonnet-5", 100, 0, 40), writer));
                }
                if (cls == NcertExtractCommand.class) {
                    return cls.cast(new NcertExtractCommand(new NcertRenderCommandTest.RecordingStore(),
                            new NcertExtractCommandTest.RecordingExtract(), new NcertExtractCommandTest.StubSpend(),
                            new PipelineProperties(72, 10, 1, "claude-sonnet-5", 100, 0, 40), writer));
                }
                if (cls == NcertLoadCommand.class) {
                    return cls.cast(new NcertLoadCommand(new NcertRenderCommandTest.RecordingStore(),
                            imports, writer));
                }
                if (cls == NcertVerifyCommand.class) {
                    return cls.cast(new NcertVerifyCommand(new NcertRenderCommandTest.RecordingStore(), imports,
                            new NcertVerifyCommandTest.StubVerifier(), ids -> Map.of(),
                            new NcertExtractCommandTest.StubSpend(),
                            new com.margai.ai.api.AiClientInfo("anthropic", List.of("ledger")),
                            new PipelineProperties(72, 10, 1, "claude-sonnet-5", 100, 0, 40), writer));
                }
                if (cls == NcertEmbedCommand.class) {
                    return cls.cast(new NcertEmbedCommand(imports, new NcertEmbedCommandTest.StubEmbeddings(),
                            NcertEmbedCommandTest.noRetriever(), new NcertExtractCommandTest.StubSpend(),
                            new com.margai.ai.api.AiClientInfo("cohere", List.of("ledger")),
                            new PipelineProperties(72, 10, 1, "claude-sonnet-5", 100, 0, 40), writer));
                }
                return CommandLine.defaultFactory().create(cls);
            }
        };
    }

    /**
     * Records what the commands hand over and answers with a report shaped like a first clean load;
     * {@code failure}, when set, is thrown by the taxonomy load instead.
     */
    static class RecordingImport implements CurriculumImport {

        List<SyllabusNodeRow> nodes;
        List<PrerequisiteRow> edges;
        List<ArchetypeTrackRow> tracks;
        List<CutoffRow> cutoffs;
        List<NcertBookRow> books;
        final List<String> renderedPages = new ArrayList<>();
        Integer renderedPagesAnswer;
        RuntimeException failure;

        @Override
        public TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows) {
            nodes = rows;
            if (failure != null) {
                throw failure;
            }
            return new TaxonomyLoadReport(rows.size(), 0, 0, Map.of(), List.of());
        }

        @Override
        public PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows) {
            edges = rows;
            return new PrerequisiteLoadReport(rows.size(), 0, rows.size(), 83, List.of());
        }

        @Override
        public BackboneLoadReport loadBackbone(List<ArchetypeTrackRow> rows) {
            tracks = rows;
            int steps = rows.stream().mapToInt(track -> track.steps().size()).sum();
            Map<AttemptType, Integer> perTrack = new LinkedHashMap<>();
            rows.forEach(track -> perTrack.put(track.code(), track.steps().size()));
            return new BackboneLoadReport(rows.size(), 0, 0, steps, 0, 0, 0, perTrack, List.of(), List.of());
        }

        @Override
        public CutoffLoadReport loadCutoffs(List<CutoffRow> rows) {
            cutoffs = rows;
            return new CutoffLoadReport(rows.size(), 0, 0, Map.of(), List.of());
        }

        @Override
        public NcertRegisterReport registerBooks(List<NcertBookRow> rows) {
            books = rows;
            return new NcertRegisterReport(rows.size(), 0, 0, List.of());
        }

        @Override
        public void recordRenderedPages(String bookCode, BookLanguage language, int pages) {
            renderedPages.add(bookCode + " " + language + " " + pages);
        }

        @Override
        public NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
            return new NcertLoadReport(rows.size(), 0, 0, Map.of(), List.of(), 0, 0);
        }

        @Override
        public Integer renderedPages(String bookCode, BookLanguage language) {
            return renderedPagesAnswer;
        }

        /** What {@link #paragraphsToEmbed} answers: the rows `ncert embed` still has to read. */
        List<ParagraphToEmbed> toEmbedAnswer = List.of();
        final List<ParagraphEmbedding> storedEmbeddings = new ArrayList<>();

        @Override
        public List<ParagraphToEmbed> paragraphsToEmbed(String bookCode, Collection<Short> chapters, boolean redo) {
            return toEmbedAnswer;
        }

        @Override
        public int storeEmbeddings(String bookCode, List<ParagraphEmbedding> embeddings) {
            storedEmbeddings.addAll(embeddings);
            return embeddings.size();
        }

        /** Ids whose vectors a run dropped, so a fragment leaves the index rather than lingering. */
        final List<java.util.UUID> clearedEmbeddings = new ArrayList<>();

        @Override
        public int clearEmbeddings(String bookCode, Collection<java.util.UUID> paragraphIds) {
            clearedEmbeddings.addAll(paragraphIds);
            return paragraphIds.size();
        }

        /** What {@link #paragraphs} answers: the rows `ncert verify` reads. */
        List<NcertParagraphRow> paragraphsAnswer = List.of();
        final List<NcertVerificationRow> verifications = new ArrayList<>();

        @Override
        public List<NcertParagraphRow> paragraphs(String bookCode, BookLanguage language, Collection<Short> chapters) {
            return paragraphsAnswer.stream().filter(row -> chapters.contains(row.chapterNo())).toList();
        }

        /** When set, {@link #recordVerifications} refuses as the real door does on a changed text. */
        boolean refuseVerifications;

        @Override
        public int recordVerifications(String bookCode, BookLanguage language, List<NcertVerificationRow> verdicts) {
            if (refuseVerifications) {
                throw new CurriculumImportException("1 verdict(s) cannot be recorded, nothing was written:\n  "
                        + "ch 7 §7.2 ¶1: the text has changed since it was verified");
            }
            verifications.addAll(verdicts);
            return verdicts.size();
        }
    }
}
