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
 * One NCERT page image into its paragraphs (TECH_PLAN §4.1's {@code PageExtractTask}, §6.1, §6.3),
 * on the VISION tier: NCERT's two-column layout, its equations and the legacy Hindi fonts defeat
 * text extractors, so the model reads the rendered page.
 *
 * <p>Where the previous page ended travels with the call — its section, its last paragraph number
 * and the tail of that paragraph's text — so a paragraph broken across a page boundary is
 * completed rather than duplicated, and paragraph numbering continues within a section instead of
 * restarting at every page (§6.3; {@link PreviousPage}).
 *
 * <p>The chapter number is passed in, never asked of the model: it comes from the
 * founder-reviewed {@code books.yaml}, because a Part-II file's printed chapter differs from its
 * file sequence (DECISIONS D14).
 */
@Component
public class PageExtractTask implements NcertPageExtractor {

    private final AiClient ai;
    private final PromptRef prompt;

    PageExtractTask(AiClient ai, PromptRegistry prompts) {
        this.ai = ai;
        this.prompt = prompts.require("ncert_extract");
    }

    @Override
    public AiResponse<NcertPage> read(String bookTitle, short chapter, int page, List<ImagePart> images,
            PreviousPage previous, AiCallContext ctx) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("book_title", bookTitle);
        variables.put("chapter", chapter);
        variables.put("page", page);
        // Told to the model only when the page arrives in bands, so the single-image prompt is
        // unchanged and the two configurations stay comparable.
        variables.put("tiles", images.size() > 1 ? images.size() : null);
        variables.put("previous_section", previous == null ? null : previous.section());
        variables.put("previous_para_no", previous == null ? null : previous.paraNo());
        variables.put("previous_tail", previous == null ? null : previous.tail());
        return ai.complete(AiRequest.of(AiFeature.pipeline_extract, Tier.vision, prompt, variables,
                        NcertPage.class, ctx)
                .withImages(images));
    }
}
