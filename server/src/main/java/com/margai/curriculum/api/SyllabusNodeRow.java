package com.margai.curriculum.api;

/**
 * One row of {@code pipeline/inputs/taxonomy.csv} (TECH_PLAN §6.2), validated by the reader and
 * upserted by {@code taxonomy load} (§6.3) on {@code code}. {@code parentCode} is null for a
 * subject, {@code classLevel} is null for subjects, units and the syllabus-only chapters,
 * {@code nameHi} and {@code defaultLearnMinutes} are null where the file leaves them empty.
 */
public record SyllabusNodeRow(
        String code,
        Subject subject,
        Short classLevel,
        String parentCode,
        NodeKind kind,
        String nameEn,
        String nameHi,
        int sortOrder,
        Integer defaultLearnMinutes,
        boolean neetRelevant) {
}
