package com.margai.curriculum.api;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * {@code questions.tagging} (migration V9): who put the question on its node — the
 * {@code pyq tag} call's ledger row, model and prompt version, its confidence and its one-line
 * reason — so a disputed tag is traced to the call that made it; and, for a tag the founder ruled, his ruling
 * beside the tagger's tag it replaced.
 *
 * @param aiCallId      the tagging call's ledger row; null for a fake-client run, and — with the model, prompt and
 *                      confidence — for a question the founder ruled that has no tagger's tag of its current words
 * @param model         the tagging model
 * @param promptVersion e.g. {@code pyq_tag.v1}
 * @param confidence    the model's own confidence, 0–1, a review-routing signal only
 * @param reason        why this node, and why out of syllabus when it is: the tagger's, or for a tag the founder
 *                      ruled, his ruling's note, the tagger's kept as {@code ruling.replacedReason}
 * @param ruling        the founder's ruling the tag comes from instead, with the tagger's tag it replaced; null
 *                      for the tagger's own tag (V11's {@code tag_status} {@code model} or {@code review})
 */
public record QuestionTagging(UUID aiCallId, String model, String promptVersion, BigDecimal confidence, String reason,
        TagRuling ruling) {

    /**
     * The confidence without trailing zeros: JSONB hands 0.90 back as 0.9, and a {@code BigDecimal}
     * of another scale is not equal, so a reload of the same tag would otherwise count as a change.
     */
    public QuestionTagging {
        confidence = confidence == null ? null : confidence.stripTrailingZeros();
    }

    /** The tagger's own tag. */
    public QuestionTagging(UUID aiCallId, String model, String promptVersion, BigDecimal confidence, String reason) {
        this(aiCallId, model, promptVersion, confidence, reason, null);
    }
}
