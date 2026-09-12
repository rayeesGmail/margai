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
public class NcertExtractTask implements NcertPageExtractor {

    private final AiClient ai;
    private final PromptRef prompt;

    NcertExtractTask(AiClient ai, PromptRegistry prompts) {
        this.ai = ai;
        this.prompt = prompts.require("ncert_extract");
    }

    @Override
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
}
