package com.margai.pipeline.internal;

import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImport;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
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

    private final CurriculumImport imports;

    BackboneLoadCommand(CurriculumImport imports) {
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    int run(Path inputFile) {
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(inputFile);
        int stepsRead = tracks.stream().mapToInt(track -> track.steps().size()).sum();
        print(FILE + ": " + tracks.size() + " tracks, " + stepsRead + " steps read");
        BackboneLoadReport report = imports.loadBackbone(tracks);
        print("archetype_tracks: " + report.tracksInserted() + " inserted, " + report.tracksUpdated() + " updated, "
                + report.tracksUnchanged() + " unchanged");
        print("archetype_track_steps: " + report.stepsInserted() + " inserted, " + report.stepsUpdated() + " updated, "
                + report.stepsUnchanged() + " unchanged, " + report.stepsRemoved() + " removed");
        print("  steps per track: " + report.stepsPerTrack().entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining(", ")));
        print("chapters in no track: " + listOrNone(report.chaptersInNoTrack()));
        return EXIT_OK;
    }
}
