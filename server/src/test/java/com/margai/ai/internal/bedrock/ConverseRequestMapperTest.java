package com.margai.ai.internal.bedrock;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Repair;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import com.margai.ai.tasks.SmokeAnswer;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import software.amazon.awssdk.services.bedrockruntime.model.CachePointType;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ImageFormat;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultStatus;
import software.amazon.awssdk.services.bedrockruntime.model.ToolSpecification;
import software.amazon.awssdk.core.document.Document;

/** TECH_PLAN §4.11: cached system prefix, forced tool with the record's schema, images, repair turns. */
class ConverseRequestMapperTest {

    private static final RenderedPrompt PROMPT = new RenderedPrompt("smoke", 1, "SYSTEM PREFIX", "Number 7 please.");

    private final ConverseRequestMapper mapper = new ConverseRequestMapper(new StructuredOutput(),
            PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of()), 1024);

    private static AiRequest<SmokeAnswer> request() {
        return AiRequest.of(AiFeature.smoke, Tier.cheap, PromptRef.named("smoke"), Map.of("number", 7),
                SmokeAnswer.class, AiCallContext.system("req"));
    }

    @Test
    void systemPrefixThenCachePointThenTheUserTurn() {
        ConverseRequest converse = mapper.toRequest("model-x", PROMPT, request());

        assertThat(converse.modelId()).isEqualTo("model-x");
        assertThat(converse.system()).hasSize(2);
        assertThat(converse.system().get(0).text()).isEqualTo("SYSTEM PREFIX");
        assertThat(converse.system().get(1).cachePoint().type()).isEqualTo(CachePointType.DEFAULT);
        assertThat(converse.messages()).hasSize(1);
        Message user = converse.messages().get(0);
        assertThat(user.role()).isEqualTo(ConversationRole.USER);
        assertThat(user.content()).hasSize(1);
        assertThat(user.content().get(0).text()).isEqualTo("Number 7 please.");
        assertThat(converse.inferenceConfig().maxTokens()).isEqualTo(1024);
        assertThat(converse.inferenceConfig().temperature()).isZero();
    }

    @Test
    void oneToolNamedAfterThePromptWithTheRecordSchemaForcedByToolChoice() {
        ConverseRequest converse = mapper.toRequest("model-x", PROMPT, request());

        assertThat(converse.toolConfig().tools()).hasSize(1);
        ToolSpecification spec = converse.toolConfig().tools().get(0).toolSpec();
        assertThat(spec.name()).isEqualTo("smoke");
        assertThat(spec.description()).isEqualTo("Return the structured result of the smoke task.");
        Document schema = spec.inputSchema().json();
        assertThat(schema.asMap().get("type").asString()).isEqualTo("object");
        assertThat(schema.asMap().get("properties").asMap()).containsKeys("greeting", "number");
        assertThat(schema.asMap().get("properties").asMap().get("number").asMap().get("type").asString()).isEqualTo("integer");
        assertThat(schema.asMap().get("additionalProperties").asBoolean()).isFalse();
        assertThat(converse.toolConfig().toolChoice().tool().name()).isEqualTo("smoke");
    }

    @Test
    void imagesFollowTheTextInTheUserTurn() {
        ConverseRequest converse = mapper.toRequest("model-v", PROMPT, request()
                .withImages(List.of(new ImagePart(new byte[] {1, 2}, "image/png"), new ImagePart(new byte[] {3}, "image/jpeg"))));

        List<ContentBlock> content = converse.messages().get(0).content();
        assertThat(content).hasSize(3);
        assertThat(content.get(1).image().format()).isEqualTo(ImageFormat.PNG);
        assertThat(content.get(1).image().source().bytes().asByteArray()).containsExactly(1, 2);
        assertThat(content.get(2).image().format()).isEqualTo(ImageFormat.JPEG);
    }

    @Test
    void aRepairAppendsTheRejectedToolCallAndAnErrorToolResult() {
        ConverseRequest converse = mapper.toRequest("model-x", PROMPT, request()
                .withRepair(new Repair("{\"greeting\": 5}", List.of("$.number: required property 'number' not found"))));

        assertThat(converse.messages()).hasSize(3);
        Message assistant = converse.messages().get(1);
        assertThat(assistant.role()).isEqualTo(ConversationRole.ASSISTANT);
        assertThat(assistant.content().get(0).toolUse().name()).isEqualTo("smoke");
        assertThat(assistant.content().get(0).toolUse().toolUseId()).isEqualTo(ConverseRequestMapper.REPAIR_TOOL_USE_ID);
        assertThat(assistant.content().get(0).toolUse().input().asMap().get("greeting").asNumber().intValue()).isEqualTo(5);
        Message result = converse.messages().get(2);
        assertThat(result.role()).isEqualTo(ConversationRole.USER);
        assertThat(result.content().get(0).toolResult().toolUseId()).isEqualTo(ConverseRequestMapper.REPAIR_TOOL_USE_ID);
        assertThat(result.content().get(0).toolResult().status()).isEqualTo(ToolResultStatus.ERROR);
        assertThat(result.content().get(0).toolResult().content().get(0).text())
                .contains("required property 'number'").contains("Call the tool again");
    }
}
