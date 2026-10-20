package com.margai.curriculum.api;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * {@code questions.tagging} (migration V9): who put the question on its node — the
 * {@code pyq tag} call's ledger row, model and prompt version, its confidence and its one-line
 * reason — so a disputed tag is traced to the call that made it.
 *
 * @param aiCallId      the tagging call's ledger row; null only for a fake-client run
 * @param model         the tagging model
 * @param promptVersion e.g. {@code pyq_tag.v1}
 * @param confidence    the model's own confidence, 0–1, a review-routing signal only
 * @param reason        why this node, and why out of syllabus when it is
 */
public record QuestionTagging(UUID aiCallId, String model, String promptVersion, BigDecimal confidence, String reason) {
}
