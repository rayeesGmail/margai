package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * The second read of an NCERT page (D15, DECISIONS 2026-09-14 "the pair"): Claude Sonnet 5 — whatever
 * model the VISION tier is configured with, which the {@code visionsonnet} profile sets — reads the
 * page's bands and judges every paragraph transcribed from it with one fixed question: does this text
 * match the print?
 *
 * <p>The page travels as the bands the transcriber read, never with its text layer: the extraction was
 * told the layer is authoritative for characters, and a verifier handed the same authority would repeat
 * the same misreadings. Independence is the whole value of the second read.
 */
@Component
public class PageVerifyTask implements NcertPageVerifier {

    static final String PROMPT = "ncert_verify";

    private final AiClient ai;
    private final PromptRef prompt;
    private final PromptRegistry prompts;
    private final AiProperties properties;

    PageVerifyTask(AiClient ai, PromptRegistry prompts, AiProperties properties) {
        this.ai = ai;
        this.prompts = prompts;
        this.properties = properties;
        this.prompt = prompts.require(PROMPT);
    }

    @Override
    public AiResponse<PageVerdicts> verify(String bookTitle, short chapter, int page, List<ImagePart> images,
            List<VerifyItem> items, AiCallContext ctx) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("book_title", bookTitle);
        variables.put("chapter", chapter);
        variables.put("page", page);
        variables.put("tiles", images.size() > 1 ? images.size() : null);
        variables.put("items", itemVariables(items));
        return ai.complete(AiRequest.of(AiFeature.pipeline_verify, Tier.vision, prompt, variables,
                        PageVerdicts.class, ctx)
                .withImages(images));
    }

    @Override
    public String model() {
        return properties.modelFor(Tier.vision);
    }

    @Override
    public String promptVersion() {
        return PROMPT + ".v" + prompts.activeVersion(PROMPT);
    }

    /** Items as the template reads them: maps, because a StringTemplate expression cannot call a record accessor. */
    static List<Map<String, Object>> itemVariables(List<VerifyItem> items) {
        return items.stream().map(item -> {
            Map<String, Object> variables = new LinkedHashMap<>();
            variables.put("number", item.number());
            variables.put("text", item.text());
            variables.put("opens", item.continuesFromPreviousPage() ? true : null);
            variables.put("runs_on", item.continuesOnNextPage() ? true : null);
            return variables;
        }).toList();
    }
}
