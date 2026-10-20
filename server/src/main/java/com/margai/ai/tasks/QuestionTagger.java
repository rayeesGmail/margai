package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import java.util.List;

/**
 * Tagging past-paper questions onto the syllabus (D19). The pipeline depends on this rather than on
 * {@link QuestionTagTask} itself, so {@code pyq tag} can be driven without a model; the task is the
 * only implementation and the only caller of {@code AiClient} (TECH_PLAN §4.1). Everything here is
 * data: the words the model reads are the {@code pyq_tag} template's (.claude/rules/ai-layer.md).
 */
public interface QuestionTagger {

    /**
     * Tags every question in one batch, one ledgered call each, responses in request order; the
     * batch's first failure is rethrown once the rest have run ({@code completeBatch}).
     *
     * @param outline the syllabus the questions are tagged against — identical for every call of the
     *                batch, so it is the cached prefix
     */
    List<AiResponse<PyqTag>> tag(List<OutlineLine> outline, List<Question> questions, AiCallContext ctx);

    /** The model the REASON tier will reach, for the guard and each tag's provenance. */
    String model();

    /** The active prompt as the ledger names it, {@code pyq_tag.v1}. */
    String promptVersion();

    /**
     * One line of the syllabus outline: a subject's name, or a unit, chapter or topic with its code.
     *
     * @param depth 0 for a subject, 1 for its units, and so on down
     * @param kind  {@code subject}, {@code unit}, {@code chapter} or {@code topic}
     * @param code  null for a subject
     */
    record OutlineLine(int depth, String kind, String code, String name) {
    }

    /**
     * One question as the tagger sees it: never its answer.
     *
     * @param subject the subject the booklet prints it under
     */
    record Question(int number, String subject, String stem, List<Option> options) {

        public Question {
            options = List.copyOf(options);
        }
    }

    /** An option as printed. */
    record Option(String key, String text) {
    }
}
