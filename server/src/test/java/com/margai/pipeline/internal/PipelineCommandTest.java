package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * The command tree without Spring: a factory that hands the leaf commands a recording
 * {@link CurriculumImport} and everything else to picocli's default factory. Every D13 command
 * reads its input under {@code --inputs} and passes the rows on, a missing file is a usage error
 * with the path, a malformed file fails the run, groups without a subcommand and unknown commands
 * are usage errors, and Spring's own arguments never reach picocli.
 */
class PipelineCommandTest {

    @TempDir
    Path inputs;

    private final StringWriter out = new StringWriter();
    private final StringWriter err = new StringWriter();
    private final RecordingImport imports = new RecordingImport();
    private CommandLine commandLine;

    @BeforeEach
    void commandLine() {
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == TaxonomyLoadCommand.class) {
                    return cls.cast(new TaxonomyLoadCommand(imports));
                }
                if (cls == TaxonomyPrerequisitesCommand.class) {
                    return cls.cast(new TaxonomyPrerequisitesCommand(imports));
                }
                return CommandLine.defaultFactory().create(cls);
            }
        };
        commandLine = PipelineRunner.commandLine(factory)
                .setOut(new PrintWriter(out, true))
                .setErr(new PrintWriter(err, true));
    }

    @Test
    void eachCommandReadsItsInputFileAndHandsTheRowsOn() {
        String committed = InputReadersTest.INPUTS.toString();

        assertThat(commandLine.execute("taxonomy", "load", "--inputs", committed)).isZero();
        assertThat(commandLine.execute("taxonomy", "prerequisites", "--inputs", committed)).isZero();
        assertThat(commandLine.execute("backbone", "load", "--inputs", committed)).isZero();
        assertThat(commandLine.execute("cutoffs", "load", "--inputs", committed)).isZero();

        assertThat(imports.nodes).hasSize(516);
        assertThat(imports.edges).hasSize(104);
        assertThat(out.toString())
                .contains("taxonomy.csv: 516 nodes read")
                .contains("syllabus_nodes: 516 inserted, 0 updated, 0 unchanged")
                .contains("orphans (in the database, not in the file): none")
                .contains("prerequisites.csv: 104 edges read")
                .contains("syllabus_prerequisites: 104 inserted, 0 already present; 104 edges over 83 nodes, no cycle")
                .contains("archetypes.yaml: 4 tracks, 744 steps read")
                .contains("cutoffs.csv: 40 rows read");
        assertThat(err.toString()).isEmpty();
    }

    @Test
    void aMissingInputFileIsAUsageErrorNamingThePath() {
        assertThat(run("taxonomy", "load")).isEqualTo(InputFileCommand.EXIT_USAGE);

        assertThat(err.toString()).contains("input file not found: " + inputs.resolve(TaxonomyLoadCommand.FILE));
        assertThat(out.toString()).isEmpty();
        assertThat(imports.nodes).isNull();
    }

    @Test
    void aMalformedInputFailsTheRunBeforeAnythingElse() throws IOException {
        Files.writeString(inputs.resolve(TaxonomyLoadCommand.FILE), "code,subject\nPHY,physics\n");

        assertThat(run("taxonomy", "load")).isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(err.toString()).contains("taxonomy.csv:1: header must be");
        assertThat(out.toString()).isEmpty();
        assertThat(imports.nodes).isNull();
    }

    @Test
    void theInputsDirectoryDefaultsToThePipelineDirectoryBesideServer() {
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
        return commandLine.execute(group, command, "--inputs", inputs.toString());
    }

    /** Records what the commands hand over and answers with a report shaped like a first clean load. */
    static final class RecordingImport implements CurriculumImport {

        List<SyllabusNodeRow> nodes;
        List<PrerequisiteRow> edges;

        @Override
        public TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows) {
            nodes = rows;
            return new TaxonomyLoadReport(rows.size(), 0, 0, Map.of(), List.of());
        }

        @Override
        public PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows) {
            edges = rows;
            return new PrerequisiteLoadReport(rows.size(), 0, rows.size(), 83, List.of());
        }
    }
}
