package com.margai.pipeline.internal;

import com.margai.curriculum.api.CurriculumImportException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

/**
 * A leaf command over one founder-owned input file (TECH_PLAN §6.2). The file must exist before
 * anything runs: a missing input is a usage error (exit 2) with the resolved path and no report.
 * Otherwise the command runs, its {@link Report} is written under {@code --reports} and printed
 * (§6.3), and the exit code says how it went: 0, or 1 when the file broke its own contract or
 * contradicted the taxonomy — the load's transaction has rolled back by then, so nothing is
 * half-written, and the report carries the reason. Subclasses name their file and do the work in
 * {@link #run(Path, Report)}.
 */
abstract class InputFileCommand implements Callable<Integer> {

    static final int EXIT_OK = 0;
    static final int EXIT_FAILED = 1;
    static final int EXIT_USAGE = 2;

    @Mixin
    InputsMixin io;

    @Spec
    CommandSpec spec;

    private final Reports reports;

    InputFileCommand(Reports reports) {
        this.reports = reports;
    }

    /** The file under {@code --inputs} this command reads. */
    abstract String inputFileName();

    /** The command's work over an existing input file, told to the report; throws when the run fails. */
    abstract void run(Path inputFile, Report report);

    @Override
    public final Integer call() {
        Path file = io.input(inputFileName());
        if (!Files.isRegularFile(file)) {
            spec.commandLine().getErr().println("input file not found: " + file.toAbsolutePath().normalize());
            return EXIT_USAGE;
        }
        Report report = new Report(spec.qualifiedName(), file);
        int exitCode = EXIT_OK;
        try {
            run(file, report);
        } catch (InputFormatException | CurriculumImportException e) {
            report.failed(e.getMessage());
            exitCode = EXIT_FAILED;
        }
        Reports.Written written = reports.write(io.reports, report);
        spec.commandLine().getOut().print(written.text());
        spec.commandLine().getOut().println("report: " + written.path().toAbsolutePath().normalize());
        if (exitCode != EXIT_OK) {
            spec.commandLine().getErr().println(report.result());
        }
        return exitCode;
    }
}
