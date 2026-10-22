package com.margai.pipeline.internal;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code margai.pipeline.pyq.*} — the PYQ commands' knobs (D19), beside {@link PipelineProperties}
 * rather than inside it, so the NCERT commands' record is not reshaped for a second corpus.
 *
 * @param transcribeModel   the model {@code pyq extract} must run on: Claude Opus 5.5 at effort low,
 *                          by the founder's D19 ruling (README §1, the open choices), reached through
 *                          the {@code visionopus55} profile. The command refuses any other VISION
 *                          model before the first call, as {@code ncert extract} does — a forgotten
 *                          profile must fail, not transcribe a paper on the default.
 * @param tagModel          the model {@code pyq tag} must run on: Claude Sonnet 5.5, the founder's
 *                          ruling; the REASON tier's default today, refused if it ever is not
 * @param tagBatchSize      how many questions one {@code completeBatch} carries before the tags are
 *                          flushed; a failed batch is retried question by question
 * @param tagLowConfidence  below this a tag is listed for the founder to look at — a routing signal
 * @param tagReviewFloor    below this a tag never loads as a normal one: {@code pyq load} stores it as
 *                          {@code review} and the tag report lists it for the founder (his ruling of 2026-10-21:
 *                          0.5, strictly below)
 * @param noAnchorRevisitShare above this share of the PYQ bank, the questions ruled "no NCERT anchor" are flagged
 *                          in {@code pyq load}'s report for the founder to revisit his policy (his ruling of
 *                          2026-10-22: ~2%)
 * @param papersDirectory   where the approved paper files live under {@code --inputs}
 *                          ({@code papers/}, TECH_PLAN §6.2)
 */
@ConfigurationProperties(prefix = "margai.pipeline.pyq")
public record PyqProperties(String transcribeModel, String tagModel, int tagBatchSize, BigDecimal tagLowConfidence,
        BigDecimal tagReviewFloor, BigDecimal noAnchorRevisitShare, String papersDirectory) {

    public PyqProperties {
        tagBatchSize = tagBatchSize <= 0 ? 10 : tagBatchSize;
        tagLowConfidence = tagLowConfidence == null ? new BigDecimal("0.70") : tagLowConfidence;
        tagReviewFloor = tagReviewFloor == null ? new BigDecimal("0.50") : tagReviewFloor;
        noAnchorRevisitShare = noAnchorRevisitShare == null ? new BigDecimal("0.02") : noAnchorRevisitShare;
        papersDirectory = papersDirectory == null || papersDirectory.isBlank() ? "papers" : papersDirectory;
    }
}
