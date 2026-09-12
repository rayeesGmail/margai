package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * The command tree without Spring (picocli's default factory): every D13 command resolves its
 * input under {@code --inputs}, a missing file is a usage error with the path, groups without a
 * subcommand and unknown commands are usage errors, and Spring's own arguments never reach picocli.
 */
class PipelineCommandTest {

    @TempDir
    Path inputs;

    private final StringWriter out = new StringWriter();
    private final StringWriter err = new StringWriter();
    private CommandLine commandLine;

    @BeforeEach
    void commandLine() {
        commandLine = PipelineRunner.commandLine(CommandLine.defaultFactory())
                .setOut(new PrintWriter(out, true))
                .setErr(new PrintWriter(err, true));
    }

    @Test
    void eachCommandResolvesItsInputFile() throws IOException {
        for (String file : new String[] {TaxonomyLoadCommand.FILE, TaxonomyPrerequisitesCommand.FILE,
                BackboneLoadCommand.FILE, CutoffsLoadCommand.FILE}) {
            Files.writeString(inputs.resolve(file), "header\n");
        }

        assertThat(run("taxonomy", "load")).isZero();
        assertThat(run("taxonomy", "prerequisites")).isZero();
        assertThat(run("backbone", "load")).isZero();
        assertThat(run("cutoffs", "load")).isZero();

        assertThat(out.toString())
                .contains("margai-pipeline taxonomy load: " + inputs.resolve(TaxonomyLoadCommand.FILE))
                .contains("margai-pipeline taxonomy prerequisites: " + inputs.resolve(TaxonomyPrerequisitesCommand.FILE))
                .contains("margai-pipeline backbone load: " + inputs.resolve(BackboneLoadCommand.FILE))
                .contains("margai-pipeline cutoffs load: " + inputs.resolve(CutoffsLoadCommand.FILE));
        assertThat(err.toString()).isEmpty();
    }

    @Test
    void aMissingInputFileIsAUsageErrorNamingThePath() {
        assertThat(run("taxonomy", "load")).isEqualTo(InputFileCommand.EXIT_USAGE);

        assertThat(err.toString()).contains("input file not found: " + inputs.resolve(TaxonomyLoadCommand.FILE));
        assertThat(out.toString()).isEmpty();
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
}
