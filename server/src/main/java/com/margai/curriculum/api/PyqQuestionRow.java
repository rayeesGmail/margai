package com.margai.curriculum.api;

import java.util.List;

/**
 * One question of an approved paper file ({@code pipeline/inputs/papers/<paper>.json}) joined
 * with its tag, as {@code pyq load --paper} hands it to the curriculum module
 * ({@code questions} and {@code question_topics}, migration V9). Nodes travel as taxonomy codes;
 * the curriculum module resolves them, so a tag naming no node fails the load by name.
 *
 * <p>The answer is NTA's final key for the paper's code: one key, several accepted keys
 * ({@code correctKey} the lowest of them), or dropped (no key at all). {@code correctKey} and
 * {@code acceptedKeys} are server-only from here on (TECH_PLAN §2.3, CLAUDE.md hard rule 1).
 *
 * @param secondaryNodeCodes at most two further topics the question draws on
 * @param asPrinted          the misprints kept as printed, each with its intended reading (V10)
 */
public record PyqQuestionRow(
        short questionNo,
        String nodeCode,
        List<String> secondaryNodeCodes,
        boolean outOfSyllabus,
        String stemEn,
        String stemHi,
        List<QuestionOption> options,
        AnswerStatus answerStatus,
        String correctKey,
        List<String> acceptedKeys,
        List<QuestionFigure> figures,
        QuestionExtraction extraction,
        QuestionTagging tagging,
        List<QuestionAsPrinted> asPrinted) {

    public PyqQuestionRow {
        secondaryNodeCodes = secondaryNodeCodes == null ? List.of() : List.copyOf(secondaryNodeCodes);
        options = options == null ? List.of() : List.copyOf(options);
        acceptedKeys = acceptedKeys == null ? null : List.copyOf(acceptedKeys);
        figures = figures == null ? List.of() : List.copyOf(figures);
        asPrinted = asPrinted == null ? List.of() : List.copyOf(asPrinted);
    }
}
