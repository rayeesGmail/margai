package com.margai.pipeline.internal;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

/**
 * The command tree of TECH_PLAN §6.3, D13's part: {@code taxonomy load}, {@code taxonomy
 * prerequisites}, {@code backbone load}, {@code cutoffs load}. The group commands only route;
 * calling a group without a subcommand is a usage error.
 */
@Command(name = PipelineRunner.COMMAND_NAME, mixinStandardHelpOptions = true,
        description = "MARG AI content pipeline (TECH_PLAN §6). Every command is idempotent and writes a report.",
        subcommands = {PipelineCommand.Taxonomy.class, PipelineCommand.Backbone.class, PipelineCommand.Cutoffs.class})
final class PipelineCommand implements Runnable {

    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        throw missingSubcommand(spec);
    }

    static ParameterException missingSubcommand(CommandSpec spec) {
        return new ParameterException(spec.commandLine(), "Missing subcommand: " + spec.subcommands().keySet());
    }

    @Command(name = "taxonomy", mixinStandardHelpOptions = true,
            description = "The syllabus tree and its prerequisite edges (D13).",
            subcommands = {TaxonomyLoadCommand.class, TaxonomyPrerequisitesCommand.class})
    static final class Taxonomy implements Runnable {

        @Spec
        CommandSpec spec;

        @Override
        public void run() {
            throw missingSubcommand(spec);
        }
    }

    @Command(name = "backbone", mixinStandardHelpOptions = true,
            description = "The archetype tracks and their steps (D13).",
            subcommands = {BackboneLoadCommand.class})
    static final class Backbone implements Runnable {

        @Spec
        CommandSpec spec;

        @Override
        public void run() {
            throw missingSubcommand(spec);
        }
    }

    @Command(name = "cutoffs", mixinStandardHelpOptions = true,
            description = "The qualifying and seat-type cut-off rows (D13).",
            subcommands = {CutoffsLoadCommand.class})
    static final class Cutoffs implements Runnable {

        @Spec
        CommandSpec spec;

        @Override
        public void run() {
            throw missingSubcommand(spec);
        }
    }
}
