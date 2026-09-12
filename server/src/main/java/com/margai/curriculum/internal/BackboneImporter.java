package com.margai.curriculum.internal;

import com.margai.common.api.AttemptType;
import com.margai.curriculum.api.ArchetypeStepRow;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.TrackPhase;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * {@code backbone load} (TECH_PLAN §6.3): tracks upserted on {@code code}, steps on (track,
 * sequence), sequences the file no longer has removed. The file is checked before anything is
 * written — every step names an existing node of the kind its phase demands (learn → chapter,
 * revision → unit, mock → subject; DECISIONS 2026-09-10 D13), a track learns a chapter at most
 * once, and no track learns a chapter before a prerequisite it also learns (the edges in
 * {@code syllabus_prerequisites}, compared by sequence — the check the drafts' generator ran, kept
 * here so an edited YAML cannot regress it). Reports the nodes no step of any track names and the
 * tracks the file no longer names. Runs inside {@link CurriculumImportService}'s transaction.
 */
@Component
class BackboneImporter {

    /** The node kind each step phase names (DECISIONS 2026-09-10 D13, archetype conventions). */
    private static final Map<TrackPhase, NodeKind> STEP_KIND = Map.of(
            TrackPhase.learn, NodeKind.chapter,
            TrackPhase.revision, NodeKind.unit,
            TrackPhase.mock, NodeKind.subject);

    private final SyllabusNodeRepository nodes;
    private final SyllabusPrerequisiteRepository prerequisites;
    private final ArchetypeTrackRepository tracks;
    private final ArchetypeTrackStepRepository steps;

    BackboneImporter(SyllabusNodeRepository nodes, SyllabusPrerequisiteRepository prerequisites,
            ArchetypeTrackRepository tracks, ArchetypeTrackStepRepository steps) {
        this.nodes = nodes;
        this.prerequisites = prerequisites;
        this.tracks = tracks;
        this.steps = steps;
    }

    BackboneLoadReport load(List<ArchetypeTrackRow> rows) {
        Set<AttemptType> codes = new HashSet<>();
        for (ArchetypeTrackRow row : rows) {
            if (!codes.add(row.code())) {
                throw new CurriculumImportException("track '" + row.code() + "' appears twice in the file");
            }
        }
        Set<String> nodeCodes = rows.stream()
                .flatMap(row -> row.steps().stream())
                .map(ArchetypeStepRow::nodeCode)
                .collect(Collectors.toCollection(TreeSet::new));
        Map<String, SyllabusNode> byCode = NodeLookup.resolve(nodes, nodeCodes, "track steps name nodes");
        rows.forEach(row -> checkSteps(row, byCode));

        int tracksInserted = 0;
        int tracksUpdated = 0;
        int tracksUnchanged = 0;
        int stepsInserted = 0;
        int stepsUpdated = 0;
        int stepsUnchanged = 0;
        int stepsRemoved = 0;
        Map<AttemptType, Integer> stepsPerTrack = new EnumMap<>(AttemptType.class);
        Set<UUID> named = new HashSet<>();
        for (ArchetypeTrackRow row : rows) {
            ArchetypeTrack track = tracks.findByCode(row.code()).orElse(null);
            if (track == null) {
                track = new ArchetypeTrack(row.code(), row.nameEn(), row.weeks());
                track.apply(row);
                track = tracks.save(track);
                tracksInserted++;
            } else if (track.apply(row)) {
                tracksUpdated++;
            } else {
                tracksUnchanged++;
            }
            Map<Integer, ArchetypeTrackStep> existing = new HashMap<>();
            steps.findByTrackIdOrderBySequence(track.getId()).forEach(step -> existing.put(step.getSequence(), step));
            Set<Integer> inFile = new HashSet<>();
            for (ArchetypeStepRow stepRow : row.steps()) {
                inFile.add(stepRow.sequence());
                UUID nodeId = byCode.get(stepRow.nodeCode()).getId();
                named.add(nodeId);
                ArchetypeTrackStep step = existing.get(stepRow.sequence());
                if (step == null) {
                    steps.save(new ArchetypeTrackStep(track.getId(), nodeId, stepRow.sequence(), stepRow.phase(),
                            stepRow.targetWeek()));
                    stepsInserted++;
                } else if (step.apply(nodeId, stepRow.phase(), stepRow.targetWeek())) {
                    stepsUpdated++;
                } else {
                    stepsUnchanged++;
                }
            }
            for (Map.Entry<Integer, ArchetypeTrackStep> stale : existing.entrySet()) {
                if (!inFile.contains(stale.getKey())) {
                    steps.delete(stale.getValue());
                    stepsRemoved++;
                }
            }
            stepsPerTrack.put(row.code(), row.steps().size());
        }
        steps.flush();

        List<String> nodesInNoTrack = nodes.findAll().stream()
                .filter(node -> node.getKind() != NodeKind.topic && !named.contains(node.getId()))
                .map(SyllabusNode::getCode)
                .sorted()
                .toList();
        List<String> orphanTracks = tracks.findAll().stream()
                .map(ArchetypeTrack::getCode)
                .filter(code -> !codes.contains(code))
                .map(AttemptType::name)
                .sorted()
                .toList();
        return new BackboneLoadReport(tracksInserted, tracksUpdated, tracksUnchanged,
                stepsInserted, stepsUpdated, stepsUnchanged, stepsRemoved, stepsPerTrack, nodesInNoTrack, orphanTracks);
    }

    /** Step kinds, a chapter learned once per track, and prerequisites before dependants within the track. */
    private void checkSteps(ArchetypeTrackRow row, Map<String, SyllabusNode> byCode) {
        Map<UUID, Integer> learnedAt = new HashMap<>();
        Map<UUID, String> codeById = new HashMap<>();
        for (ArchetypeStepRow step : row.steps()) {
            SyllabusNode node = byCode.get(step.nodeCode());
            NodeKind expected = STEP_KIND.get(step.phase());
            if (node.getKind() != expected) {
                throw new CurriculumImportException("track " + row.code() + " step " + step.sequence() + ": a "
                        + step.phase() + " step names a " + expected + ", not the " + node.getKind() + " " + step.nodeCode());
            }
            if (step.phase() == TrackPhase.learn) {
                Integer earlier = learnedAt.put(node.getId(), step.sequence());
                if (earlier != null) {
                    throw new CurriculumImportException("track " + row.code() + ": chapter " + step.nodeCode()
                            + " is learned twice (sequences " + earlier + " and " + step.sequence() + ")");
                }
                codeById.put(node.getId(), step.nodeCode());
            }
        }
        for (SyllabusPrerequisite edge : prerequisites.findAll()) {
            Integer before = learnedAt.get(edge.getFromNodeId());
            Integer after = learnedAt.get(edge.getToNodeId());
            if (before != null && after != null && before > after) {
                throw new CurriculumImportException("track " + row.code() + ": " + codeById.get(edge.getToNodeId())
                        + " (sequence " + after + ") is learned before its prerequisite " + codeById.get(edge.getFromNodeId())
                        + " (sequence " + before + ")");
            }
        }
    }
}
