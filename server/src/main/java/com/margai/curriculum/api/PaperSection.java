package com.margai.curriculum.api;

/**
 * One printed section of a paper ({@code pyq_papers.sections}, migration V9): the question
 * numbers it runs over, inclusive, and how many of them a candidate attempts — all of them, or 10
 * of Section B's 15 in 2021–2024 — read whole by the D35 mock (TECH_PLAN §15.6).
 *
 * @param subject the subject as printed, not as tagged ({@link PaperSubject})
 * @param section the section letter where the paper prints one ("A", "B"), else null
 * @param from    first question number, 1-based
 * @param to      last question number, inclusive
 * @param attempt how many of the section's questions count; equal to its size unless it is optional
 */
public record PaperSection(PaperSubject subject, String section, short from, short to, short attempt) {

    /** How many questions the section prints: the official count the D19 ✅ checks against. */
    public int size() {
        return to - from + 1;
    }

    public boolean contains(int questionNo) {
        return questionNo >= from && questionNo <= to;
    }
}
