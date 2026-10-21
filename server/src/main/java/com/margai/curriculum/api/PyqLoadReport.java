package com.margai.curriculum.api;

import java.util.List;

/**
 * What {@code pyq load} did (D19): whether the paper row was new or changed, the questions' upsert
 * counts, and every question of the paper as the database now holds it — read back after the write,
 * inside the load's transaction, so the D19 ✅ counts the bank and not the file. There is no orphan
 * list: a question of the paper in the bank but not in the file refuses the load.
 *
 * @param stored the paper's questions in the database after the load, in question order
 */
public record PyqLoadReport(
        boolean paperInserted,
        boolean paperUpdated,
        int inserted,
        int updated,
        int unchanged,
        List<StoredQuestion> stored) {

    public PyqLoadReport {
        stored = List.copyOf(stored);
    }

    /**
     * One question as stored, with what the report counts — never its text and never its key.
     *
     * @param nodeCode  the primary node's code
     * @param asPrinted how many misprints the question keeps as printed, each with its note
     */
    public record StoredQuestion(short no, String nodeCode, int secondaryTopics, boolean outOfSyllabus, boolean hasFigure,
            AnswerStatus answerStatus, int asPrinted) {
    }
}
