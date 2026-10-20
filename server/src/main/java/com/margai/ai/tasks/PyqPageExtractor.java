package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import java.util.List;

/**
 * Reading one page of an official question booklet (D19). The pipeline depends on this rather
 * than on {@link PyqPageExtractTask} itself, so {@code pyq extract} can be driven end to end
 * without a model — the task is the only implementation and the only caller of {@code AiClient}
 * (TECH_PLAN §4.1), as {@link NcertPageExtractor} is for the NCERT pages.
 */
public interface PyqPageExtractor {

    /**
     * @param paper       the paper as a student would name it, "NEET (UG) 2025, booklet code 45"
     * @param page        the 1-based page of the booklet
     * @param images      the rendered page: one image, or overlapping bands top to bottom
     * @param bilingual   whether the booklet prints Hindi beside the English, which is never read
     * @param pageText    the page's text layer, authoritative for characters, or null — for every
     *                    bilingual booklet, whose layer interleaves the Hindi, and for an English
     *                    booklet whose layer as a whole does not read as text
     * @param inQuestion  the question the previous page ended inside, its options incomplete, or
     *                    null when the previous page ended on a complete question
     */
    AiResponse<PyqPage> read(String paper, int page, List<ImagePart> images, boolean bilingual, String pageText,
            Integer inQuestion, AiCallContext ctx);

    /** The model the VISION tier will reach, for the transcriber guard of {@code pyq extract}. */
    String model();

    /** The active prompt as the ledger names it, {@code pyq_extract.v1}, for each question's provenance. */
    String promptVersion();
}
