package com.margai.pipeline.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.margai.curriculum.api.AnswerStatus;
import com.margai.curriculum.api.QuestionAsPrinted;
import com.margai.curriculum.api.QuestionExtraction;
import com.margai.curriculum.api.QuestionFigure;
import com.margai.curriculum.api.QuestionOption;
import java.util.List;

/**
 * A paper's questions as one file (D19; TECH_PLAN §6.2's {@code pipeline/inputs/papers/<paper>.json}:
 * stem, options, key, paper code). {@code pyq extract} writes it as a draft outside the inputs, with
 * every answer empty; Claude checks it against the page images and fills each answer from NTA's
 * final key, the founder approves it, and only then is it committed to {@code pipeline/inputs/papers/}
 * — the pipeline never writes there (.claude/rules/pipeline.md). {@code pyq tag} and
 * {@code pyq load} read the approved file.
 *
 * @param paper           the register's id, {@code neet-2025-45}
 * @param transcribedFrom the booklet's content-bucket key
 */
record PaperFile(String paper, String transcribedFrom, List<Question> questions) {

    PaperFile {
        questions = questions == null ? List.of() : List.copyOf(questions);
    }

    /**
     * One question as printed, with NTA's answer for it once the key is filled in.
     *
     * @param no         the printed question number
     * @param stemEn     the English stem as transcribed
     * @param stemHi     NTA's Hindi stem; null until the parked Hindi pass reads it
     * @param answer     null in a draft; NTA's final key in an approved file
     * @param extraction the pages and calls it came from, and the checks' flags it carried into review
     * @param asPrinted  the misprints the review keeps as printed, each with its intended reading (founder,
     *                   2026-10-21) — written only when there are any, so a draft and its review diff cleanly
     */
    record Question(
            short no,
            String stemEn,
            String stemHi,
            List<QuestionOption> options,
            List<QuestionFigure> figures,
            Answer answer,
            QuestionExtraction extraction,
            @JsonInclude(JsonInclude.Include.NON_EMPTY) List<QuestionAsPrinted> asPrinted) {

        Question {
            options = options == null ? List.of() : List.copyOf(options);
            figures = figures == null ? List.of() : List.copyOf(figures);
            asPrinted = asPrinted == null ? List.of() : List.copyOf(asPrinted);
        }

        Question withAnswer(Answer answer) {
            return new Question(no, stemEn, stemHi, options, figures, answer, extraction, asPrinted);
        }
    }

    /**
     * NTA's final key for one question: {@code single} with its {@code key}, {@code multiple} with
     * every accepted key in {@code keys}, or {@code dropped} with neither.
     */
    record Answer(AnswerStatus status, String key, List<String> keys) {

        Answer {
            keys = keys == null ? null : List.copyOf(keys);
        }

        static Answer single(String key) {
            return new Answer(AnswerStatus.single, key, null);
        }

        static Answer multiple(List<String> keys) {
            return new Answer(AnswerStatus.multiple, null, keys);
        }

        static Answer dropped() {
            return new Answer(AnswerStatus.dropped, null, null);
        }
    }
}
