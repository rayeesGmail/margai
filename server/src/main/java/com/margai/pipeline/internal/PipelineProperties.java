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
 */
@ConfigurationProperties(prefix = "margai.pipeline")
public record PipelineProperties(int renderDpi, int extractBatchSize, int pageTiles) {

    public PipelineProperties {
        renderDpi = renderDpi <= 0 ? 150 : renderDpi;
        extractBatchSize = extractBatchSize <= 0 ? 10 : extractBatchSize;
        pageTiles = pageTiles <= 0 ? 1 : pageTiles;
    }
}
