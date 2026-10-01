package com.margai.pipeline.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code margai.pipeline.*} — the knobs of the §6 commands, configuration rather than constants
 * (the server rule). Only the {@code pipeline} profile reads them.
 *
 * @param renderDpi        page-image resolution for {@code ncert render}. 150 puts an A4 page at
 *                         about 1240×1754, just under the provider's 1568-pixel long-edge limit:
 *                         rendering larger costs upload and gets downscaled before the model sees
 *                         it, rendering smaller loses subscripts and equation glyphs.
 * @param extractBatchSize how many pages one {@code ncert extract} transaction writes to the
 *                         JSONL before flushing, so an interrupted run resumes near where it
 *                         stopped rather than from the start of the book.
 * @param pageTiles        how many overlapping horizontal bands each page is sent as. 1 sends the
 *                         whole page, which the provider then shrinks below the size at which
 *                         subscripts survive; 2 sends each half unscaled. The D14 audit found
 *                         every transcription error in a small glyph and none in the prose, which
 *                         is what this exists to fix ({@link PageTiles}).
 * @param verifyModel      the model the second read must run on (DECISIONS 2026-09-14 "the pair":
 *                         Sonnet 5 verifies). {@code ncert verify --read-pages} refuses when the
 *                         verify tier is any other model, and counts no verdict another model gave —
 *                         a forgotten profile must fail, not verify with whatever VISION is.
 * @param transcribeModel  the model the first read must run on (DECISIONS 2026-09-14 "the pair":
 *                         Opus 5 transcribes). {@code ncert extract} refuses when the VISION tier
 *                         is any other model — the mirror of {@code verifyModel}, and added on
 *                         2026-09-22 because it was missing: the VISION default is
 *                         {@code claude-haiku-4-5}, which the D15 dry runs put out of this job
 *                         after three runs and three layout failures, and only the
 *                         {@code visionopus} profile selects Opus. Without the guard a forgotten
 *                         profile does not fail — it spends a whole book's budget on the rejected
 *                         model and says so nowhere but the ledger.
 * @param embedBatchSize   how many paragraph vectors one {@code ncert embed} transaction writes
 *                         before committing. Embedding is cheap but not free, and a run
 *                         interrupted at paragraph 800 should keep the 800 it paid for rather
 *                         than re-buy them.
 * @param embedCallsPerMinute
 *                         the rate {@code ncert embed} holds itself to. The embedding key is a
 *                         trial key capped at 100 calls per minute, which one call per paragraph
 *                         reaches in about forty seconds — the first live run died on call 101 of
 *                         894 (2026-09-20). A quota is a fixed property of the key, so it is
 *                         paced rather than tripped and retried; 0 or less means no pacing, which
 *                         is what a production key wants.
 */
@ConfigurationProperties(prefix = "margai.pipeline")
public record PipelineProperties(int renderDpi, int extractBatchSize, int pageTiles, String verifyModel,
        String transcribeModel, int embedBatchSize, int embedCallsPerMinute, int embedMinCharacters) {

    public PipelineProperties {
        renderDpi = renderDpi <= 0 ? 150 : renderDpi;
        extractBatchSize = extractBatchSize <= 0 ? 10 : extractBatchSize;
        pageTiles = pageTiles <= 0 ? 1 : pageTiles;
        embedBatchSize = embedBatchSize <= 0 ? 100 : embedBatchSize;
        embedMinCharacters = embedMinCharacters <= 0 ? 40 : embedMinCharacters;
    }
}
