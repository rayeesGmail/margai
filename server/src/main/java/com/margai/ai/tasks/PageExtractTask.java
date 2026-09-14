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
 * <p>Where the previous page ended travels with the call — its section and the tail of its last
 * paragraph — so a page with no heading keeps its section and a paragraph broken across the page
 * boundary is recognised as a continuation rather than duplicated (§6.3; {@link PreviousPage}).
 * The paragraph number is not asked of the model either: since v3 the loader counts (D15).
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
            String pageText, PreviousPage previous, AiCallContext ctx) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("book_title", bookTitle);
        variables.put("chapter", chapter);
        variables.put("page", page);
        // Absent for a chapter whose layer is noise, and the prompt then says the image is all
        // there is — so the model is never left guessing which source it was given.
        variables.put("page_text", pageText == null || pageText.isBlank() ? null : pageText);
        // Told to the model only when the page arrives in bands, so the single-image prompt is
        // unchanged and the two configurations stay comparable. Bands only: a whole-page image
        // beside them, at any size, cost every prime on chapter 7 (PageTiles, D14).
        variables.put("tiles", images.size() > 1 ? images.size() : null);
        variables.put("previous_section", previous == null ? null : previous.section());
        variables.put("previous_tail", previous == null ? null : previous.tail());
        return ai.complete(AiRequest.of(AiFeature.pipeline_extract, Tier.vision, prompt, variables,
                        NcertPage.class, ctx)
                .withImages(images));
    }
}
