package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import java.util.List;

/**
 * The second read of one NCERT page (D15, DECISIONS 2026-09-14 "the pair"). The pipeline depends on
 * this rather than on {@link PageVerifyTask}, so {@code ncert verify --read-pages} can be driven end to
 * end without a model, as {@link NcertPageExtractor} lets {@code ncert extract} be.
 */
public interface NcertPageVerifier {

    /**
     * @param images the page as the transcriber saw it — overlapping bands, top to bottom — and never
     *               its text layer: the transcriber was told to trust that layer, and a verifier handed
     *               the same source would share its mistakes
     * @param items  what the page's paragraphs say it prints, in reading order, numbered from 1
     */
    AiResponse<PageVerdicts> verify(String bookTitle, short chapter, int page, List<ImagePart> images,
            List<VerifyItem> items, AiCallContext ctx);

    /** The model the verify tier is configured with, for the guard that it is not the transcriber. */
    String model();

    /** The verify prompt as a verdict records it: {@code ncert_verify.v1}. */
    String promptVersion();
}
