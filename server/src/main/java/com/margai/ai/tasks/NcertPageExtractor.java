package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import java.util.List;

/**
 * Reading one rendered NCERT page (TECH_PLAN §6.3). The pipeline depends on this rather than on
 * {@link PageExtractTask} itself, so the command that spends can be driven end to end without a
 * model — the task is the only implementation, and the only caller of {@code AiClient} (§4.1).
 */
public interface NcertPageExtractor {

    /**
     * @param bookTitle the book as printed on its cover, for the model's orientation
     * @param chapter   the chapter number printed in the book, from {@code books.yaml}
     * @param page      the 1-based page within the chapter's source PDF
     * @param images    the rendered page: one image, or overlapping bands top to bottom when the
     *                  page is tiled so its small glyphs arrive unscaled ({@code PageTiles})
     * @param pageText  the page's own embedded text layer, authoritative for characters, or null
     *                  when this chapter's layer cannot be trusted — all ten Hindi books and one
     *                  Chemistry file, where the image is the only source (DECISIONS 2026-09-13)
     * @param previous  where the previous page of this chapter ended, null for its first page
     */
    AiResponse<NcertPage> read(String bookTitle, short chapter, int page, List<ImagePart> images,
            String pageText, PreviousPage previous, AiCallContext ctx);

    /**
     * The model the VISION tier is configured with, for the guard that it is the transcriber. The
     * mirror of {@link NcertPageVerifier#model()}, and for the same reason: which model reads the
     * page is a profile away from being wrong, and only one of the two commands could say so.
     */
    String model();
}
