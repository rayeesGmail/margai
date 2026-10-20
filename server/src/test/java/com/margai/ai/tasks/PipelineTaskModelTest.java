package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.internal.AiFlags;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.TestAiProperties;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * The pipeline's two VISION tasks name the model their calls will reach, which the transcriber and
 * verifier guards compare against the pair ruling's models (DECISIONS 2026-09-14). Under the completion
 * fallback that is the fallback's VISION id (TECH_PLAN §15.12), so the guards refuse instead of letting
 * the flag send a book to a model that was ruled out.
 */
class PipelineTaskModelTest {

    private final AiProperties properties = TestAiProperties.withFallback("global.p.cheap-model",
            "global.p.reason-model", "global.p.vision-model");
    private final PromptRegistry prompts = PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(),
            Map.of("ncert_extract", 5, "ncert_verify", 1));

    @Test
    void withTheFlagOffBothNameThePrimaryVisionModel() {
        assertThat(new PageExtractTask(null, prompts, properties, new AiFlags(false)).model())
                .isEqualTo(TestAiProperties.VISION);
        assertThat(new PageVerifyTask(null, prompts, properties, new AiFlags(false)).model())
                .isEqualTo(TestAiProperties.VISION);
    }

    @Test
    void underTheFallbackBothNameTheFallbacksVisionModel() {
        assertThat(new PageExtractTask(null, prompts, properties, new AiFlags(true)).model())
                .isEqualTo("global.p.vision-model");
        assertThat(new PageVerifyTask(null, prompts, properties, new AiFlags(true)).model())
                .isEqualTo("global.p.vision-model");
    }
}
