package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.common.api.AttemptType;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
        CommandLine.IFactory factory = new CommandLine.IFactory() {
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
                return CommandLine.defaultFactory().create(cls);
            }
        };
        commandLine = PipelineRunner.commandLine(factory)
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
     * Records what the commands hand over and answers with a report shaped like a first clean load;
     * {@code failure}, when set, is thrown by the taxonomy load instead.
     */
    static final class RecordingImport implements CurriculumImport {

        List<SyllabusNodeRow> nodes;
        List<PrerequisiteRow> edges;
        List<ArchetypeTrackRow> tracks;
        List<CutoffRow> cutoffs;
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
    }
}
