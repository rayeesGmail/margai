package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiFlags;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * One official booklet page into its questions (D19), on the VISION tier with the
 * {@code pyq_extract} prompt and the ledger category {@code pipeline_extract}: the booklets are
 * scans or bilingual pages whose text layer, where there is one, cannot be trusted for layout,
 * so the model reads the rendered page as it reads an NCERT page ({@link PageExtractTask}).
 *
 * <p>Only a fact travels from the previous page — the number of a question it ended inside — never
 * its words, which the NCERT extraction showed a model will echo (D15).
 */
@Component
public class PyqPageExtractTask implements PyqPageExtractor {

    private final AiClient ai;
    private final PromptRegistry prompts;
    private final PromptRef prompt;
    private final AiProperties properties;
    private final AiFlags flags;

    PyqPageExtractTask(AiClient ai, PromptRegistry prompts, AiProperties properties, AiFlags flags) {
        this.ai = ai;
        this.prompts = prompts;
        this.properties = properties;
        this.flags = flags;
        this.prompt = prompts.require("pyq_extract");
    }

    @Override
    public AiResponse<PyqPage> read(String paper, int page, List<ImagePart> images, boolean bilingual, String pageText,
            Integer inQuestion, AiCallContext ctx) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("paper", paper);
        variables.put("page", page);
        variables.put("tiles", images.size() > 1 ? images.size() : null);
        variables.put("bilingual", bilingual ? true : null);
        variables.put("page_text", pageText == null || pageText.isBlank() ? null : pageText);
        variables.put("in_question", inQuestion);
        return ai.complete(AiRequest.of(AiFeature.pipeline_extract, Tier.vision, prompt, variables, PyqPage.class, ctx)
                .withImages(images));
    }

    /** As {@link PageExtractTask#model()}: the fallback's VISION id while the completion fallback serves. */
    @Override
    public String model() {
        return flags.aiFallback() ? properties.fallbackModelFor(Tier.vision) : properties.modelFor(Tier.vision);
    }

    @Override
    public String promptVersion() {
        return prompt.name() + ".v" + prompts.activeVersion(prompt.name());
    }
}
