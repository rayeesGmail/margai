package com.margai.curriculum.api;

import java.util.List;

/**
 * What {@code pyq load} did (D19): whether the paper row was new or changed, the questions' upsert
 * counts, and every question of the paper as the database now holds it — read back after the write,
 * inside the load's transaction, so the D19 ✅ counts the bank and not the file. There is no orphan
 * list: a question of the paper in the bank but not in the file refuses the load.
 *
 * @param stored        the paper's questions in the database after the load, in question order
 * @param bankQuestions every PYQ in the bank after the load, across papers — the denominator of the founder's
 *                      ~2% line for questions with no NCERT anchor (DECISIONS 2026-10-22)
 */
public record PyqLoadReport(
        boolean paperInserted,
        boolean paperUpdated,
        int inserted,
        int updated,
        int unchanged,
        List<StoredQuestion> stored,
        long bankQuestions) {

    public PyqLoadReport {
        stored = List.copyOf(stored);
    }

    /**
     * One question as stored, with what the report counts — never its text and never its key.
     *
     * @param nodeCode           the primary node's code
     * @param asPrintedShow       the misprints kept as printed whose note the app shows
     * @param asPrintedRecordOnly the misprints kept as printed and only recorded
     * @param tagStatus           whose tag it carries (V11)
     */
    public record StoredQuestion(short no, String nodeCode, int secondaryTopics, boolean outOfSyllabus, boolean hasFigure,
            AnswerStatus answerStatus, int asPrintedShow, int asPrintedRecordOnly, TagStatus tagStatus) {
    }
}
