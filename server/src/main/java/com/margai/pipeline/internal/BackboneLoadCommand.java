package com.margai.pipeline.internal;

import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImport;
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

    private final CurriculumImport imports;

    BackboneLoadCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(inputFile);
        int stepsRead = tracks.stream().mapToInt(track -> track.steps().size()).sum();
        report.read(tracks.size() + " tracks, " + stepsRead + " steps");
        BackboneLoadReport result = imports.loadBackbone(tracks);
        report.section("archetype_tracks")
                .table(List.of("inserted", "updated", "unchanged"), List.of(List.of(
                        String.valueOf(result.tracksInserted()), String.valueOf(result.tracksUpdated()),
                        String.valueOf(result.tracksUnchanged()))));
        report.section("archetype_track_steps")
                .table(List.of("inserted", "updated", "unchanged", "removed"), List.of(List.of(
                        String.valueOf(result.stepsInserted()), String.valueOf(result.stepsUpdated()),
                        String.valueOf(result.stepsUnchanged()), String.valueOf(result.stepsRemoved()))));
        report.section("steps per track")
                .table(List.of("track", "steps"), result.stepsPerTrack().entrySet().stream()
                        .map(entry -> List.of(entry.getKey().name(), String.valueOf(entry.getValue())))
                        .toList());
        report.section("nodes in no track (subjects, units and chapters no step names)").list(result.nodesInNoTrack());
        report.section("orphan tracks (in the database, not in the file)").list(result.orphanTracks());
    }
}
