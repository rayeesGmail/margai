package com.margai.pipeline.internal;

import java.util.Arrays;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * The entry point of the {@code pipeline} profile (TECH_PLAN §1.2, §6.1):
 * {@code java -jar server.jar --spring.profiles.active=pipeline <command>} starts the context
 * without a web server ({@code application-pipeline.yml}), this runner hands the command line
 * to picocli, and {@link com.margai.MargaiApplication#main} exits the JVM with the code picocli
 * returned — 0 for a clean run, 1 when a command failed, 2 for a usage error or a missing input.
 */
@Component
@Profile("pipeline")
class PipelineRunner implements ApplicationRunner, ExitCodeGenerator {

    /** The root command's name, as typed and as printed in usage and report titles. */
    static final String COMMAND_NAME = "margai-pipeline";

    /** Spring's own arguments ({@code --spring.profiles.active=…}) are not picocli's business. */
    private static final String SPRING_ARGUMENT_PREFIX = "--spring.";

    private final ApplicationContext context;
    private volatile int exitCode;

    PipelineRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        exitCode = commandLine(new SpringPicocliFactory(context)).execute(commandArgs(args.getSourceArgs()));
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    /** The command tree with the given object factory; the tests build it with picocli's default one. */
    static CommandLine commandLine(CommandLine.IFactory factory) {
        return new CommandLine(PipelineCommand.class, factory);
    }

    /** Everything on the command line except Spring's {@code --spring.*} arguments. */
    static String[] commandArgs(String[] sourceArgs) {
        return Arrays.stream(sourceArgs)
                .filter(arg -> !arg.startsWith(SPRING_ARGUMENT_PREFIX))
                .toArray(String[]::new);
    }
}
