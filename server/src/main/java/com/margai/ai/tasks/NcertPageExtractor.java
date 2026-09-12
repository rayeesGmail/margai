package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;

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
     * @param image     the rendered page
     * @param previous  where the previous page of this chapter ended, null for its first page
     */
    AiResponse<NcertPage> read(String bookTitle, short chapter, int page, ImagePart image,
            PreviousPage previous, AiCallContext ctx);
}
