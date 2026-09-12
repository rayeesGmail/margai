package com.margai.curriculum.api;

import com.margai.common.api.AttemptType;
import java.util.List;

/**
 * One track of {@code pipeline/inputs/archetypes.yaml} (TECH_PLAN §2.3, §6.2) with its steps in
 * sequence order. Upserted by {@code backbone load} on {@code code}, its steps on
 * (track, sequence) (§6.3).
 */
public record ArchetypeTrackRow(
        AttemptType code,
        String nameEn,
        String nameHi,
        short weeks,
        String descriptionMd,
        List<ArchetypeStepRow> steps) {

    public ArchetypeTrackRow {
        steps = List.copyOf(steps);
    }
}
