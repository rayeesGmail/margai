package com.margai.pipeline.internal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

/**
 * A leaf command over one founder-owned input file (TECH_PLAN §6.2). The file must exist before
 * anything runs: a missing input is a usage error (exit 2) with the resolved path, never a
 * half-run. Subclasses name their file and do the work in {@link #run(Path)}; the base behaviour
 * resolves the file and prints where it was found, which is also what a dry check of the input
 * directory needs.
 */
abstract class InputFileCommand implements Callable<Integer> {

    static final int EXIT_OK = 0;
    static final int EXIT_USAGE = 2;

    @Mixin
    InputsMixin io;

    @Spec
    CommandSpec spec;

    /** The file under {@code --inputs} this command reads. */
    abstract String inputFileName();

    @Override
    public final Integer call() {
        Path file = io.input(inputFileName());
        if (!Files.isRegularFile(file)) {
            spec.commandLine().getErr().println("input file not found: " + file.toAbsolutePath().normalize());
            return EXIT_USAGE;
        }
        return run(file);
    }

    int run(Path inputFile) {
        spec.commandLine().getOut().println(spec.qualifiedName() + ": " + inputFile.toAbsolutePath().normalize());
        return EXIT_OK;
    }
}
