package com.margai.ai.internal.anthropic;

import com.anthropic.core.JsonValue;
import com.anthropic.models.messages.Base64ImageSource;
import com.anthropic.models.messages.CacheControlEphemeral;
import com.anthropic.models.messages.ContentBlockParam;
import com.anthropic.models.messages.ImageBlockParam;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.MessageParam;
import com.anthropic.models.messages.OutputConfig;
import com.anthropic.models.messages.TextBlockParam;
import com.anthropic.models.messages.ThinkingConfigAdaptive;
import com.anthropic.models.messages.ThinkingConfigDisabled;
import com.anthropic.models.messages.Tool;
import com.anthropic.models.messages.ToolChoiceTool;
import com.anthropic.models.messages.ToolResultBlockParam;
import com.anthropic.models.messages.ToolUseBlockParam;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Repair;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tools.jackson.databind.JsonNode;

/**
 * Builds the Messages request of TECH_PLAN §4.11: the rendered system template as one cached
 * text block, the user turn with the question and any images, one tool named after the prompt
 * whose input schema is the output record's schema, forced through {@code tool_choice}, and the
 * request shape the tier's model accepts. A repair retry appends the model's rejected tool call
 * and an error tool result so the next answer can fix it. The model-facing fragments (tool
 * description, repair message) come from {@code prompts/_protocol.v<N>.stg}, never from code.
 *
 * <p>Per-model request shape is configuration, not a code branch (§11.5): the reasoning models
 * of the current generation reject {@code temperature} with a 400 and the cheap model rejects
 * {@code effort}, so both fields are sent only when the tier configures them.
 */
final class MessageRequestMapper {

    static final String REPAIR_TOOL_USE_ID = "repair-1";
    static final String PROTOCOL = "_protocol";

    /** Top-level schema keywords this mapper places itself; the rest are copied verbatim. */
    private static final Set<String> PLACED = Set.of("type", "properties", "required");

    private final StructuredOutput codec;
    private final PromptRegistry prompts;
    private final int maxOutputTokens;

    MessageRequestMapper(StructuredOutput codec, PromptRegistry prompts, int maxOutputTokens) {
        this.codec = codec;
        this.prompts = prompts;
        this.maxOutputTokens = maxOutputTokens;
    }

    MessageCreateParams toRequest(AiProperties.Model model, RenderedPrompt prompt, AiRequest<?> request) {
        String tool = prompt.name();
        MessageCreateParams.Builder params = MessageCreateParams.builder()
                .model(model.id())
                .maxTokens(maxOutputTokens)
                .systemOfTextBlockParams(List.of(TextBlockParam.builder()
                        .text(prompt.system())
                        .cacheControl(CacheControlEphemeral.builder().build())
                        .build()))
                .messages(messages(prompt, request, tool))
                .addTool(Tool.builder()
                        .name(tool)
                        .description(prompts.renderFragment(PROTOCOL, "tool_description", Map.of("task", tool)))
                        .inputSchema(inputSchema(request.outputType()))
                        .build())
                .toolChoice(ToolChoiceTool.builder().name(tool).build());
        if (model.temperature() != null) {
            params.temperature(model.temperature());
        }
        switch (model.thinking()) {
            case disabled -> params.thinking(ThinkingConfigDisabled.builder().build());
            case adaptive -> params.thinking(ThinkingConfigAdaptive.builder().build());
        }
        if (model.effort() != null) {
            params.outputConfig(OutputConfig.builder().effort(effort(model.effort())).build());
        }
        return params.build();
    }

    private static OutputConfig.Effort effort(AiProperties.Effort effort) {
        return switch (effort) {
            case low -> OutputConfig.Effort.LOW;
            case medium -> OutputConfig.Effort.MEDIUM;
            case high -> OutputConfig.Effort.HIGH;
            case xhigh -> OutputConfig.Effort.XHIGH;
            case max -> OutputConfig.Effort.MAX;
        };
    }

    /**
     * The output record's JSON schema as the tool's input schema. The schema is a Jackson 3 tree
     * and the SDK takes its own; the values cross as plain Java objects (StructuredOutput
     * {@code toPlain}) so no second JSON library is imported here.
     */
    private Tool.InputSchema inputSchema(Class<?> outputType) {
        JsonNode schema = codec.schemaFor(outputType);
        Tool.InputSchema.Builder builder = Tool.InputSchema.builder();
        Tool.InputSchema.Properties.Builder properties = Tool.InputSchema.Properties.builder();
        for (Map.Entry<String, JsonNode> property : schema.path("properties").properties()) {
            properties.putAdditionalProperty(property.getKey(), value(property.getValue()));
        }
        builder.properties(properties.build());
        JsonNode required = schema.path("required");
        if (required.isArray()) {
            List<String> names = new ArrayList<>();
            required.forEach(name -> names.add(name.stringValue()));
            builder.required(names);
        }
        for (Map.Entry<String, JsonNode> keyword : schema.properties()) {
            if (!PLACED.contains(keyword.getKey())) {
                builder.putAdditionalProperty(keyword.getKey(), value(keyword.getValue()));
            }
        }
        return builder.build();
    }

    private JsonValue value(JsonNode node) {
        return JsonValue.from(codec.toPlain(node));
    }

    private List<MessageParam> messages(RenderedPrompt prompt, AiRequest<?> request, String tool) {
        List<ContentBlockParam> user = new ArrayList<>();
        user.add(ContentBlockParam.ofText(TextBlockParam.builder().text(prompt.user()).build()));
        for (ImagePart image : request.images()) {
            user.add(ContentBlockParam.ofImage(ImageBlockParam.builder()
                    .source(Base64ImageSource.builder()
                            .mediaType(Base64ImageSource.MediaType.of(image.mediaType()))
                            .data(Base64.getEncoder().encodeToString(image.bytes()))
                            .build())
                    .build()));
        }
        List<MessageParam> messages = new ArrayList<>();
        messages.add(MessageParam.builder().role(MessageParam.Role.USER).contentOfBlockParams(user).build());
        Repair repair = request.repair();
        if (repair != null) {
            messages.add(MessageParam.builder().role(MessageParam.Role.ASSISTANT)
                    .contentOfBlockParams(List.of(ContentBlockParam.ofToolUse(ToolUseBlockParam.builder()
                            .id(REPAIR_TOOL_USE_ID)
                            .name(tool)
                            .input(rejected(repair))
                            .build())))
                    .build());
            messages.add(MessageParam.builder().role(MessageParam.Role.USER)
                    .contentOfBlockParams(List.of(ContentBlockParam.ofToolResult(ToolResultBlockParam.builder()
                            .toolUseId(REPAIR_TOOL_USE_ID)
                            .isError(true)
                            .content(prompts.renderFragment(PROTOCOL, "repair", Map.of("errors", repair.errors())))
                            .build())))
                    .build());
        }
        return messages;
    }

    /** The tool input the model sent and the validator rejected; empty when it sent nothing usable. */
    private ToolUseBlockParam.Input rejected(Repair repair) {
        ToolUseBlockParam.Input.Builder input = ToolUseBlockParam.Input.builder();
        if (repair.previousOutputJson() != null) {
            JsonNode previous = codec.parse(repair.previousOutputJson(), null, null);
            for (Map.Entry<String, JsonNode> field : previous.properties()) {
                input.putAdditionalProperty(field.getKey(), value(field.getValue()));
            }
        }
        return input.build();
    }
}
