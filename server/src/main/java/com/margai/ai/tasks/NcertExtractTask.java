package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.PromptRegistry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * One NCERT page image into its paragraphs (TECH_PLAN §6.1, §6.3), on the VISION tier: NCERT's
 * two-column layout, its equations and the legacy Hindi fonts defeat text extractors, so the
 * model reads the rendered page.
 *
 * <p>The previous page's tail travels with the call so a paragraph broken across a page boundary
 * is completed rather than duplicated (§6.3, "the previous page's tail for paragraph
 * continuity"). The chapter number is passed in, never asked of the model: it comes from the
 * founder-reviewed {@code books.yaml}, because a Part-II file's printed chapter differs from its
 * file sequence (DECISIONS D14).
 */
@Component
public class NcertExtractTask {

    /** How much of the previous page travels with the next call. Enough for a long paragraph. */
    static final int TAIL_LENGTH = 600;

    private final AiClient ai;
    private final PromptRef prompt;

    NcertExtractTask(AiClient ai, PromptRegistry prompts) {
        this.ai = ai;
        this.prompt = prompts.require("ncert_extract");
    }

    /**
     * Reads one page.
     *
     * @param bookTitle    the book as printed on its cover, for the model's orientation
     * @param chapter      the chapter number printed in the book
     * @param page         the 1-based page within the chapter's source PDF
     * @param image        the rendered page
     * @param previousTail the tail of the previous page's last paragraph, or null for the first
     */
    public AiResponse<NcertPage> read(String bookTitle, short chapter, int page, ImagePart image,
            String previousTail, AiCallContext ctx) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("book_title", bookTitle);
        variables.put("chapter", chapter);
        variables.put("page", page);
        variables.put("previous_tail", previousTail);
        return ai.complete(AiRequest.of(AiFeature.pipeline_extract, Tier.vision, prompt, variables,
                        NcertPage.class, ctx)
                .withImages(List.of(image)));
    }

    /** The tail of a page's text, as the next page's call will receive it. */
    public static String tailOf(NcertPage page) {
        if (page.paragraphs().isEmpty()) {
            return null;
        }
        String text = page.paragraphs().getLast().text();
        return text.length() <= TAIL_LENGTH ? text : text.substring(text.length() - TAIL_LENGTH);
    }
}
