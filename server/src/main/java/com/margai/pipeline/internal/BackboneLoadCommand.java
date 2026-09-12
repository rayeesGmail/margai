package com.margai.pipeline.internal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/** {@code backbone load}: {@code archetypes.yaml} into {@code archetype_tracks} and their steps (TECH_PLAN §6.3). */
@Component
@Profile("pipeline")
@Command(name = "load", mixinStandardHelpOptions = true,
        description = "Upsert archetypes.yaml into archetype_tracks (by code) and archetype_track_steps (by track and sequence).")
class BackboneLoadCommand extends InputFileCommand {

    static final String FILE = "archetypes.yaml";

    @Override
    String inputFileName() {
        return FILE;
    }
}
