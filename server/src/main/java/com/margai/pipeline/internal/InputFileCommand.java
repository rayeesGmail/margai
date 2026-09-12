package com.margai.pipeline.internal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

/**
 * A leaf command over one founder-owned input file (TECH_PLAN §6.2). The file must exist before
 * anything runs: a missing input is a usage error (exit 2) with the resolved path, and a file
 * that breaks its contract fails the run (exit 1) with the file and line — in both cases before
 * anything is written. Subclasses name their file and do the work in {@link #run(Path)}.
 */
abstract class InputFileCommand implements Callable<Integer> {

    static final int EXIT_OK = 0;
    static final int EXIT_FAILED = 1;
    static final int EXIT_USAGE = 2;

    @Mixin
    InputsMixin io;

    @Spec
    CommandSpec spec;

    /** The file under {@code --inputs} this command reads. */
    abstract String inputFileName();

    /** The command's work over an existing input file; returns the exit code. */
    abstract int run(Path inputFile);

    @Override
    public final Integer call() {
        Path file = io.input(inputFileName());
        if (!Files.isRegularFile(file)) {
            spec.commandLine().getErr().println("input file not found: " + file.toAbsolutePath().normalize());
            return EXIT_USAGE;
        }
        try {
            return run(file);
        } catch (InputFormatException e) {
            spec.commandLine().getErr().println(e.getMessage());
            return EXIT_FAILED;
        }
    }

    void print(String line) {
        spec.commandLine().getOut().println(line);
    }
}
