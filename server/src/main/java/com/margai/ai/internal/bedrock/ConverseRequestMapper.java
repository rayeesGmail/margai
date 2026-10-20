package com.margai.ai.internal.bedrock;

import com.margai.ai.api.AiRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Repair;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.internal.PromptRegistry;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.services.bedrockruntime.model.AutoToolChoice;
import software.amazon.awssdk.services.bedrockruntime.model.CachePointBlock;
import software.amazon.awssdk.services.bedrockruntime.model.CachePointType;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ImageBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ImageFormat;
import software.amazon.awssdk.services.bedrockruntime.model.ImageSource;
import software.amazon.awssdk.services.bedrockruntime.model.InferenceConfiguration;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.SpecificToolChoice;
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.Tool;
import software.amazon.awssdk.services.bedrockruntime.model.ToolChoice;
import software.amazon.awssdk.services.bedrockruntime.model.ToolConfiguration;
import software.amazon.awssdk.services.bedrockruntime.model.ToolInputSchema;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultStatus;
import software.amazon.awssdk.services.bedrockruntime.model.ToolSpecification;
import software.amazon.awssdk.services.bedrockruntime.model.ToolUseBlock;

/**
 * Builds the Converse request of TECH_PLAN §4.11: the rendered system template followed by a
 * cache checkpoint (the cached prefix — where the Anthropic client puts {@code cache_control},
 * since Converse has no such field), the user turn with the question and any images, one tool
 * named after the prompt whose input schema is the output record's schema, forced through
 * {@code toolChoice} — or, on a tier configured {@code auto}, a strict tool the user turn asks
 * for (D16) — and the request shape the tier's model accepts. A repair retry appends the model's
 * rejected tool call and an error tool result so the next answer can fix it. The model-facing
 * fragments (tool description, tool instruction, repair message) come from
 * {@code prompts/_protocol.v<N>.stg}, never from code.
 *
 * <p>The request shape is the tier's configuration, as in the Anthropic client (§11.5): the
 * reasoning models reject {@code temperature} and the cheap one rejects {@code effort}, so each is
 * sent only when the tier sets it. Converse has fields for neither {@code thinking} nor
 * {@code effort}; both go to the model in {@code additionalModelRequestFields}, in the body the
 * provider's own API takes (TECH_PLAN §15.12, DECISIONS 2026-10-20).
 */
final class ConverseRequestMapper {

    static final String REPAIR_TOOL_USE_ID = "repair-1";
    static final String PROTOCOL = "_protocol";

    private final StructuredOutput codec;
    private final PromptRegistry prompts;
    private final int maxOutputTokens;

    ConverseRequestMapper(StructuredOutput codec, PromptRegistry prompts, int maxOutputTokens) {
        this.codec = codec;
        this.prompts = prompts;
        this.maxOutputTokens = maxOutputTokens;
    }

    ConverseRequest toRequest(AiProperties.Model model, RenderedPrompt prompt, AiRequest<?> request) {
        String tool = prompt.name();
        ToolSpecification.Builder answerTool = ToolSpecification.builder()
                .name(tool)
                .description(prompts.renderFragment(PROTOCOL, "tool_description", Map.of("task", tool)))
                .inputSchema(ToolInputSchema.fromJson(Documents.fromJson(codec.schemaFor(request.outputType()))));
        ToolChoice choice;
        if (model.forcesTheTool()) {
            choice = ToolChoice.fromTool(SpecificToolChoice.builder().name(tool).build());
        } else {
            answerTool.strict(true);
            choice = ToolChoice.fromAuto(AutoToolChoice.builder().build());
        }
        InferenceConfiguration.Builder inference = InferenceConfiguration.builder().maxTokens(maxOutputTokens);
        if (model.temperature() != null) {
            inference.temperature(model.temperature().floatValue());
        }
        return ConverseRequest.builder()
                .modelId(model.id())
                .system(SystemContentBlock.fromText(prompt.system()),
                        SystemContentBlock.fromCachePoint(CachePointBlock.builder().type(CachePointType.DEFAULT).build()))
                .messages(messages(prompt, request, tool, !model.forcesTheTool()))
                .toolConfig(ToolConfiguration.builder()
                        .tools(Tool.fromToolSpec(answerTool.build()))
                        .toolChoice(choice)
                        .build())
                .inferenceConfig(inference.build())
                .additionalModelRequestFields(modelFields(model))
                .build();
    }

    /**
     * What Converse has no field of its own for: {@code thinking} on every tier, as the Anthropic
     * client sends it (the models differ in how they turn it off, D16), and
     * {@code output_config.effort} where the tier sets an effort.
     */
    static Document modelFields(AiProperties.Model model) {
        Map<String, Document> fields = new LinkedHashMap<>();
        fields.put("thinking", Document.fromMap(Map.of("type", Document.fromString(model.thinking().name()))));
        if (model.effort() != null) {
            fields.put("output_config", Document.fromMap(Map.of("effort", Document.fromString(model.effort().name()))));
        }
        return Document.fromMap(fields);
    }

    private List<Message> messages(RenderedPrompt prompt, AiRequest<?> request, String tool, boolean askForTheTool) {
        List<ContentBlock> user = new ArrayList<>();
        user.add(ContentBlock.fromText(prompt.user()));
        for (ImagePart image : request.images()) {
            user.add(ContentBlock.fromImage(ImageBlock.builder()
                    .format(ImageFormat.fromValue(image.format()))
                    .source(ImageSource.fromBytes(SdkBytes.fromByteArray(image.bytes())))
                    .build()));
        }
        if (askForTheTool) {
            user.add(ContentBlock.fromText(prompts.renderFragment(PROTOCOL, "tool_instruction", Map.of("task", tool))));
        }
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().role(ConversationRole.USER).content(user).build());
        Repair repair = request.repair();
        if (repair != null) {
            messages.add(Message.builder().role(ConversationRole.ASSISTANT)
                    .content(ContentBlock.fromToolUse(ToolUseBlock.builder()
                            .toolUseId(REPAIR_TOOL_USE_ID)
                            .name(tool)
                            .input(repair.previousOutputJson() == null
                                    ? Documents.fromJson(null)
                                    : Documents.fromJson(codec.parse(repair.previousOutputJson(), null, null)))
                            .build()))
                    .build());
            messages.add(Message.builder().role(ConversationRole.USER)
                    .content(ContentBlock.fromToolResult(ToolResultBlock.builder()
                            .toolUseId(REPAIR_TOOL_USE_ID)
                            .status(ToolResultStatus.ERROR)
                            .content(ToolResultContentBlock.fromText(prompts.renderFragment(PROTOCOL, "repair",
                                    Map.of("errors", repair.errors()))))
                            .build()))
                    .build());
        }
        return messages;
    }
}
