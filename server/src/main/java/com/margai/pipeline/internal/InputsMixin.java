package com.margai.pipeline.internal;

import java.nio.file.Path;
import picocli.CommandLine.Option;

/**
 * The two directories every command shares (TECH_PLAN §6.1, §6.3): the founder-owned inputs and
 * the per-run reports. The defaults assume the jar runs from {@code server/}, next to the
 * {@code pipeline/} directory of the repository (DECISIONS 2026-09-12 D13).
 */
final class InputsMixin {

    static final String DEFAULT_INPUTS = "../pipeline/inputs";
    static final String DEFAULT_REPORTS = "../pipeline/reports";

    @Option(names = "--inputs", paramLabel = "DIR", defaultValue = DEFAULT_INPUTS,
            description = "Directory of the founder-owned input files (default: ${DEFAULT-VALUE}, relative to the working directory).")
    Path inputs;

    @Option(names = "--reports", paramLabel = "DIR", defaultValue = DEFAULT_REPORTS,
            description = "Directory the run report is written to (default: ${DEFAULT-VALUE}).")
    Path reports;

    Path input(String fileName) {
        return inputs.resolve(fileName);
    }
}
