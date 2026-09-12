package com.margai.ai.internal.anthropic;

import static org.assertj.core.api.Assertions.assertThat;

import com.anthropic.models.messages.ContentBlockParam;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.MessageParam;
import com.anthropic.models.messages.OutputConfig;
import com.anthropic.models.messages.TextBlockParam;
import com.anthropic.models.messages.Tool;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Repair;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import com.margai.ai.tasks.SmokeAnswer;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TECH_PLAN §4.11: cached system prefix, forced tool with the record's schema, images, repair
 * turns, and the per-model request shape — a field the model rejects must be absent from the
 * request, not defaulted (the reasoning tier sends no temperature, the cheap tier no effort).
 */
class MessageRequestMapperTest {

    private static final RenderedPrompt PROMPT = new RenderedPrompt("smoke", 1, "SYSTEM PREFIX", "Number 7 please.");

    private static final AiProperties.Model CHEAP =
            new AiProperties.Model("model-x", 0.0, AiProperties.Thinking.disabled, null, 4096);
    private static final AiProperties.Model REASON =
            new AiProperties.Model("model-r", null, AiProperties.Thinking.adaptive, AiProperties.Effort.medium, 1024);

    private final MessageRequestMapper mapper = new MessageRequestMapper(new StructuredOutput(),
            PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of()), 1024);

    private static AiRequest<SmokeAnswer> request() {
        return AiRequest.of(AiFeature.smoke, Tier.cheap, PromptRef.named("smoke"), Map.of("number", 7),
                SmokeAnswer.class, AiCallContext.system("req"));
    }

    @Test
    void theSystemPrefixIsOneCachedBlockAheadOfTheUserTurn() {
        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, request());

        assertThat(params.model().asString()).isEqualTo("model-x");
        assertThat(params.maxTokens()).isEqualTo(1024);
        List<TextBlockParam> system = params.system().orElseThrow().asTextBlockParams();
        assertThat(system).hasSize(1);
        assertThat(system.get(0).text()).isEqualTo("SYSTEM PREFIX");
        assertThat(system.get(0).cacheControl()).isPresent();

        assertThat(params.messages()).hasSize(1);
        MessageParam user = params.messages().get(0);
        assertThat(user.role()).isEqualTo(MessageParam.Role.USER);
        List<ContentBlockParam> content = user.content().asBlockParams();
        assertThat(content).hasSize(1);
        assertThat(content.get(0).text().orElseThrow().text()).isEqualTo("Number 7 please.");
    }

    @Test
    void theOutputRecordsSchemaIsTheForcedToolsInputSchema() {
        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, request());

        List<com.anthropic.models.messages.ToolUnion> tools = params.tools().orElseThrow();
        assertThat(tools).hasSize(1);
        Tool tool = tools.get(0).tool().orElseThrow();
        assertThat(tool.name()).isEqualTo("smoke");
        assertThat(tool.description()).isPresent();
        Tool.InputSchema schema = tool.inputSchema();
        assertThat(schema.properties().orElseThrow()._additionalProperties()).containsOnlyKeys("greeting", "number");
        assertThat(schema.required().orElseThrow()).containsExactlyInAnyOrder("greeting", "number");
        assertThat(schema._additionalProperties()).containsKey("additionalProperties");

        assertThat(params.toolChoice().orElseThrow().asTool().name()).isEqualTo("smoke");
    }

    @Test
    void theCheapTierSendsATemperatureAndNoEffort() {
        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, request());

        assertThat(params.temperature()).hasValue(0.0);
        assertThat(params.outputConfig()).isEmpty();
        assertThat(params.thinking().orElseThrow().isDisabled()).isTrue();
    }

    /** The current reasoning models answer a temperature with a 400, so the field must be absent. */
    @Test
    void theReasoningTierSendsEffortAndNoTemperature() {
        MessageCreateParams params = mapper.toRequest(REASON, PROMPT, request());

        assertThat(params.temperature()).isEmpty();
        assertThat(params.outputConfig().orElseThrow().effort()).hasValue(OutputConfig.Effort.MEDIUM);
        assertThat(params.thinking().orElseThrow().isAdaptive()).isTrue();
    }

    @Test
    void imagesFollowTheQuestionInTheUserTurn() {
        AiRequest<SmokeAnswer> withImage = request()
                .withImages(List.of(new ImagePart(new byte[] {1, 2, 3}, "image/png")));

        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, withImage);

        List<ContentBlockParam> content = params.messages().get(0).content().asBlockParams();
        assertThat(content).hasSize(2);
        assertThat(content.get(0).text()).isPresent();
        assertThat(content.get(1).image().orElseThrow().source().base64().orElseThrow().data()).isNotBlank();
    }

    @Test
    void aRepairRetryReplaysTheRejectedCallAndTheErrorResult() {
        AiRequest<SmokeAnswer> repair = request()
                .withRepair(new Repair("{\"greeting\":\"hi\"}", List.of("number: must be a number")));

        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, repair);

        assertThat(params.messages()).hasSize(3);
        assertThat(params.messages().get(1).role()).isEqualTo(MessageParam.Role.ASSISTANT);
        var rejected = params.messages().get(1).content().asBlockParams().get(0).toolUse().orElseThrow();
        assertThat(rejected.id()).isEqualTo(MessageRequestMapper.REPAIR_TOOL_USE_ID);
        assertThat(rejected.name()).isEqualTo("smoke");
        assertThat(rejected.input()._additionalProperties()).containsKey("greeting");

        MessageParam result = params.messages().get(2);
        assertThat(result.role()).isEqualTo(MessageParam.Role.USER);
        var toolResult = result.content().asBlockParams().get(0).toolResult().orElseThrow();
        assertThat(toolResult.toolUseId()).isEqualTo(MessageRequestMapper.REPAIR_TOOL_USE_ID);
        assertThat(toolResult.isError()).hasValue(true);
        assertThat(toolResult.content().orElseThrow().string().orElseThrow())
                .contains("number: must be a number");
    }

    /** A repair whose previous call carried nothing usable still replays a well-formed turn. */
    @Test
    void aRepairWithoutAPreviousOutputReplaysAnEmptyCall() {
        AiRequest<SmokeAnswer> repair = request().withRepair(new Repair(null, List.of("no tool call")));

        MessageCreateParams params = mapper.toRequest(CHEAP, PROMPT, repair);

        var rejected = params.messages().get(1).content().asBlockParams().get(0).toolUse().orElseThrow();
        assertThat(rejected.input()._additionalProperties()).isEmpty();
    }
}
