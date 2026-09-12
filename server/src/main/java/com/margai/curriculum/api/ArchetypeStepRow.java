package com.margai.curriculum.api;

/**
 * One step of a track in {@code pipeline/inputs/archetypes.yaml} (TECH_PLAN §2.3, §6.2):
 * {@code nodeCode} names a chapter for {@code learn}, an NTA unit for {@code revision}, the
 * subject node for {@code mock} (DECISIONS 2026-09-10 D13, archetype conventions);
 * {@code targetWeek} is the week of the track by which the step should be reached.
 */
public record ArchetypeStepRow(int sequence, String nodeCode, TrackPhase phase, short targetWeek) {
}
