package com.margai.ai.tasks;

import java.math.BigDecimal;
import java.util.List;

/**
 * Output record of the {@code pyq_tag} prompt (D19): the syllabus node a past-paper question tests,
 * the at most two further topics it draws on, whether the current syllabus still covers it, and why.
 *
 * <p>Codes only, never names: whether a code exists, is a chapter or a topic, belongs to the
 * printed subject and differs from the primary is checked by {@code pyq tag} against the taxonomy
 * — what code can compute, the model is never asked to judge (.claude/rules/ai-layer.md).
 *
 * @param primary        the topic's code, or the chapter's when the question spans its topics or
 *                       the current syllabus no longer covers it
 * @param secondary      at most two further topic or chapter codes it draws on
 * @param outOfSyllabus  true when the current NEET syllabus no longer covers what it tests
 * @param confidence     0–1, a review-routing signal only
 * @param reason         one line: what the question tests, and why it is out of syllabus when it is
 */
public record PyqTag(String primary, List<String> secondary, boolean outOfSyllabus, BigDecimal confidence, String reason) {

    public PyqTag {
        if (primary == null || primary.isBlank()) {
            throw new IllegalArgumentException("the tag names no primary node — every question tests something on the list");
        }
        secondary = secondary == null ? List.of() : List.copyOf(secondary);
        if (secondary.size() > 2) {
            throw new IllegalArgumentException("the tag names " + secondary.size() + " secondary nodes; at most two");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("the tag gives no reason");
        }
    }
}
