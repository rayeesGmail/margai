package com.margai.curriculum.api;

import java.util.List;

/**
 * The founder's ruling a question's tag comes from ({@code questions.tagging.ruling}; {@code tag_status = founder};
 * {@code pipeline/inputs/pyq-tag-rulings.yaml}, DECISIONS 2026-10-21), with the tagger's tag it replaced, so the two
 * are kept side by side.
 *
 * @param ruled                  the date of the ruling, ISO
 * @param anchor                 where NCERT teaches the question; {@code none} marks "no NCERT anchor"; null when
 *                               the ruling records none
 * @param gap                    the taxonomy node proposed for it, or null
 * @param replacedPrimary        the tagger's primary the ruling replaced, null when the question had no valid tag
 *                               of its current words
 * @param replacedSecondary      the tagger's secondary codes it replaced
 * @param replacedOutOfSyllabus  the tagger's flag it replaced, null with {@code replacedPrimary}
 * @param replacedReason         the tagger's one-line reason it replaced, null with {@code replacedPrimary}; the
 *                               tag's own reason is the ruling's note (the founder, DECISIONS 2026-10-22)
 */
public record TagRuling(String ruled, String note, String anchor, String gap, String replacedPrimary,
        List<String> replacedSecondary, Boolean replacedOutOfSyllabus, String replacedReason) {

    public TagRuling {
        replacedSecondary = replacedSecondary == null ? List.of() : List.copyOf(replacedSecondary);
    }
}
