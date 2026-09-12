package com.margai.pipeline.internal;

import com.margai.curriculum.api.ArchetypeTrackRow;
import java.nio.file.Path;
import java.util.List;
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

    @Override
    int run(Path inputFile) {
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(inputFile);
        int steps = tracks.stream().mapToInt(track -> track.steps().size()).sum();
        print(FILE + ": " + tracks.size() + " tracks, " + steps + " steps read");
        return EXIT_OK;
    }
}
