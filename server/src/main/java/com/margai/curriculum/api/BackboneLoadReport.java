package com.margai.curriculum.api;

import com.margai.common.api.AttemptType;
import java.util.List;
import java.util.Map;

/**
 * What {@code backbone load} did (TECH_PLAN §6.3 "steps per track, nodes not in any track"):
 * track and step upsert counts, {@code stepsRemoved} for sequences a track no longer has,
 * {@code stepsPerTrack} from the file, {@code nodesInNoTrack} — the subjects, units and chapters
 * no step of any track in the file names (topics are never named by steps) — and
 * {@code orphanTracks}, the tracks in {@code archetype_tracks} the file no longer names, kept in
 * place.
 */
public record BackboneLoadReport(
        int tracksInserted,
        int tracksUpdated,
        int tracksUnchanged,
        int stepsInserted,
        int stepsUpdated,
        int stepsUnchanged,
        int stepsRemoved,
        Map<AttemptType, Integer> stepsPerTrack,
        List<String> nodesInNoTrack,
        List<String> orphanTracks) {
}
