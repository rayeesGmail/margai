package com.margai.pipeline.internal;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * One line of {@code tags/pyq/{paper}.jsonl} (D19): a question's tag as the model gave it, the call
 * that gave it, the sha256 of the question text it was given, and — when the tag fails
 * {@link TaxonomyOutline#problem}, or the call itself failed — what is wrong. {@code pyq load} takes
 * only a line with no problem whose hash is the approved question's current text, so a bad tag, or a
 * tag of words since corrected, can never reach the bank; {@code pyq tag} re-tags both.
 *
 * @param primary      null only when the call failed
 * @param questionSha256 the sha256 of the stem and options the tagger read ({@link PyqTagCommand#textSha})
 */
record PyqTagLine(
        short question,
        String primary,
        List<String> secondary,
        boolean outOfSyllabus,
        BigDecimal confidence,
        String reason,
        UUID aiCallId,
        String model,
        String prompt,
        String questionSha256,
        String problem) {

    PyqTagLine {
        secondary = secondary == null ? List.of() : List.copyOf(secondary);
    }

    boolean valid() {
        return problem == null;
    }

    /** Below the review floor: such a tag loads as {@code review}, never as a normal tag (the founder, 2026-10-21). */
    boolean belowFloor(BigDecimal floor) {
        return confidence == null || confidence.compareTo(floor) < 0;
    }

    /** A line for a question whose call failed every time: no tag, the failure as its problem. */
    static PyqTagLine failed(short question, String questionSha256, String prompt, String failure) {
        return new PyqTagLine(question, null, List.of(), false, null, null, null, null, prompt, questionSha256,
                "the call failed: " + failure);
    }
}
