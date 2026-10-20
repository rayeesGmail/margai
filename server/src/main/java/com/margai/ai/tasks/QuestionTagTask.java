package com.margai.ai.tasks;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.RouteDecision;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiFlags;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * A past-paper question onto its syllabus node (D19), on the REASON tier with the {@code pyq_tag}
 * prompt and the ledger category {@code pipeline_tag}: the founder ruled Sonnet 5.5, batched, for
 * tagging (README §1). The call carries {@link RouteDecision#generation()} — Claude's reading that a
 * pipeline content decision may take the producer TECH_PLAN §4.2 names for pipeline solution
 * generation (DECISIONS 2026-10-20) — and goes through {@code completeBatch}, the on-demand loop
 * today (TECH_PLAN §4.11).
 *
 * <p>The outline and the question travel as data and the template renders them, so every word the
 * model reads is in the versioned prompt. The outline is the same for every call of a batch, so it is
 * written to the cache once and read for the rest.
 */
@Component
public class QuestionTagTask implements QuestionTagger {

    private final AiClient ai;
    private final PromptRegistry prompts;
    private final PromptRef prompt;
    private final AiProperties properties;
    private final AiFlags flags;

    QuestionTagTask(AiClient ai, PromptRegistry prompts, AiProperties properties, AiFlags flags) {
        this.ai = ai;
        this.prompts = prompts;
        this.properties = properties;
        this.flags = flags;
        this.prompt = prompts.require("pyq_tag");
    }

    @Override
    public List<AiResponse<PyqTag>> tag(List<OutlineLine> outline, List<Question> questions, AiCallContext ctx) {
        List<Map<String, Object>> lines = outline.stream().map(QuestionTagTask::line).toList();
        List<AiRequest<PyqTag>> requests = questions.stream().map(question -> {
            Map<String, Object> variables = new LinkedHashMap<>();
            variables.put("outline", lines);
            variables.put("subject", question.subject());
            variables.put("number", question.number());
            variables.put("stem", question.stem());
            variables.put("options", question.options().stream()
                    .map(option -> Map.<String, Object>of("key", option.key(), "text", option.text())).toList());
            return AiRequest.of(AiFeature.pipeline_tag, Tier.reason, prompt, variables, PyqTag.class, ctx)
                    .withRoute(RouteDecision.generation());
        }).toList();
        return ai.completeBatch(requests);
    }

    /** One outline line as the template reads it: its depth as flags, its kind as a flag, its code and name. */
    private static Map<String, Object> line(OutlineLine line) {
        Map<String, Object> map = new HashMap<>();
        map.put("depth" + line.depth(), true);
        map.put(line.kind(), true);
        map.put("code", line.code());
        map.put("name", line.name());
        return map;
    }

    @Override
    public String model() {
        return flags.aiFallback() ? properties.fallbackModelFor(Tier.reason) : properties.modelFor(Tier.reason);
    }

    @Override
    public String promptVersion() {
        return prompt.name() + ".v" + prompts.activeVersion(prompt.name());
    }
}
