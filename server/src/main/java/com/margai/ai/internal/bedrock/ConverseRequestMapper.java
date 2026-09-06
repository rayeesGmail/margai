package com.margai.ai.internal.bedrock;

import com.margai.ai.api.AiRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Repair;
import com.margai.ai.internal.RenderedPrompt;
import com.margai.ai.internal.StructuredOutput;
import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.core.SdkBytes;
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
 * cache checkpoint (the cached prefix), the user turn with the question and any images, one
 * tool named after the prompt whose input schema is the output record's schema, forced through
 * {@code toolChoice}, and temperature 0. A repair retry appends the model's rejected tool call
 * and an error tool result so the next answer can fix it.
 */
final class ConverseRequestMapper {

    static final String REPAIR_TOOL_USE_ID = "repair-1";
    static final float TEMPERATURE = 0f;

    private final StructuredOutput codec;
    private final int maxOutputTokens;

    ConverseRequestMapper(StructuredOutput codec, int maxOutputTokens) {
        this.codec = codec;
        this.maxOutputTokens = maxOutputTokens;
    }

    ConverseRequest toRequest(String modelId, RenderedPrompt prompt, AiRequest<?> request) {
        String tool = prompt.name();
        return ConverseRequest.builder()
                .modelId(modelId)
                .system(SystemContentBlock.fromText(prompt.system()),
                        SystemContentBlock.fromCachePoint(CachePointBlock.builder().type(CachePointType.DEFAULT).build()))
                .messages(messages(prompt, request, tool))
                .toolConfig(ToolConfiguration.builder()
                        .tools(Tool.fromToolSpec(ToolSpecification.builder()
                                .name(tool)
                                .description("Return the structured result of the " + tool + " task.")
                                .inputSchema(ToolInputSchema.fromJson(Documents.fromJson(codec.schemaFor(request.outputType()))))
                                .build()))
                        .toolChoice(ToolChoice.fromTool(SpecificToolChoice.builder().name(tool).build()))
                        .build())
                .inferenceConfig(InferenceConfiguration.builder()
                        .maxTokens(maxOutputTokens)
                        .temperature(TEMPERATURE)
                        .build())
                .build();
    }

    private List<Message> messages(RenderedPrompt prompt, AiRequest<?> request, String tool) {
        List<ContentBlock> user = new ArrayList<>();
        user.add(ContentBlock.fromText(prompt.user()));
        for (ImagePart image : request.images()) {
            user.add(ContentBlock.fromImage(ImageBlock.builder()
                    .format(ImageFormat.fromValue(image.format()))
                    .source(ImageSource.fromBytes(SdkBytes.fromByteArray(image.bytes())))
                    .build()));
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
                            .content(ToolResultContentBlock.fromText("The tool input was rejected: "
                                    + String.join("; ", repair.errors())
                                    + ". Call the tool again with an input that matches its schema exactly."))
                            .build()))
                    .build());
        }
        return messages;
    }
}
