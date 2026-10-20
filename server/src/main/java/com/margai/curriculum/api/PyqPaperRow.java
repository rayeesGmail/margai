package com.margai.curriculum.api;

import java.time.LocalDate;
import java.util.List;

/**
 * One paper of the founder-owned register {@code pipeline/inputs/pyq-papers.yaml}, as
 * {@code pyq load --paper} hands it to the curriculum module ({@code pyq_papers}, migration V9). {@code questionCount} is the count the booklet declares; the register's reader
 * holds it equal to the sections' sizes, and the load holds the questions to both (PLAN D19 ✅).
 *
 * @param code          the register's id, e.g. {@code neet-2025-45}
 * @param exam          {@code NEET} (or {@code AIPMT}, reserved)
 * @param year          the exam's year
 * @param paperCode     the booklet code as printed, e.g. {@code 45}, {@code S6}
 * @param examDate      the sitting's IST date
 * @param officialHindi whether the booklet carries NTA's own Hindi text (founder's D19 ruling 1)
 * @param questionCount the number of questions the booklet declares
 */
public record PyqPaperRow(
        String code,
        String exam,
        short year,
        String paperCode,
        LocalDate examDate,
        boolean officialHindi,
        short questionCount,
        List<PaperSection> sections,
        PaperMarking marking,
        KeyProvenance keySource,
        List<PaperSourceFile> source) {

    public PyqPaperRow {
        sections = sections == null ? List.of() : List.copyOf(sections);
        source = source == null ? List.of() : List.copyOf(source);
    }

    /** The printed section a question number falls in, or null when none does. */
    public PaperSection sectionOf(int questionNo) {
        return sections.stream().filter(section -> section.contains(questionNo)).findFirst().orElse(null);
    }
}
