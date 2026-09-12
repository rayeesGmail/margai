package com.margai.ai.internal.anthropic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.anthropic.core.JsonValue;
import com.anthropic.core.http.Headers;
import com.anthropic.errors.AnthropicIoException;
import com.anthropic.errors.AnthropicRetryableException;
import com.anthropic.errors.BadRequestException;
import com.anthropic.errors.InternalServerException;
import com.anthropic.errors.RateLimitException;
import com.anthropic.errors.UnauthorizedException;
import com.anthropic.models.messages.DirectCaller;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.StopReason;
import com.anthropic.models.messages.ToolUseBlock;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.margai.ai.internal.StructuredOutput;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;

/**
 * TECH_PLAN §4.11: the forced tool's input is the answer, usage carries the cache columns the
 * ledger prices separately, and every SDK failure becomes the typed failure the retry decorator
 * and the ledger read — with the provider's own wait hint where a rate limit sends one.
 */
class AnthropicAiClientTest {

    private final StructuredOutput codec = new StructuredOutput();

    @Test
    void usageKeepsTheCacheColumnsApartFromInput() {
        Usage usage = AnthropicAiClient.toUsage(usage(11, 4, 5_000L, 0L));

        assertThat(usage.inputTokens()).isEqualTo(11);
        assertThat(usage.outputTokens()).isEqualTo(4);
        assertThat(usage.cacheReadTokens()).isEqualTo(5_000);
        assertThat(usage.cacheWriteTokens()).isZero();
        assertThat(AnthropicAiClient.toUsage(null).isEmpty()).isTrue();
    }

    @Test
    void theForcedToolsInputIsTheAnswer() {
        Message response = message(ToolUseBlock.builder()
                .id("tool-1")
                .name("smoke")
                .caller(ToolUseBlock.Caller.ofDirect(DirectCaller.builder().build()))
                .input(JsonValue.from(Map.of("greeting", "ok", "number", 7)))
                .build());

        JsonNode output = AnthropicAiClient.toolInput(response, Usage.none(), "model-x", codec);

        assertThat(output.path("greeting").stringValue()).isEqualTo("ok");
        assertThat(output.path("number").asInt()).isEqualTo(7);
    }

    @Test
    void aResponseWithoutAToolCallIsInvalidOutputNamingTheStopReason() {
        Message response = message(StopReason.MAX_TOKENS).build();

        assertThatThrownBy(() -> AnthropicAiClient.toolInput(response, Usage.none(), "model-x", codec))
                .isInstanceOf(InvalidOutputException.class)
                .hasMessageContaining("no tool call");
    }

    @Test
    void rateLimitsAreRetryableAndCarryTheProvidersWaitHint() {
        RateLimitException rateLimit = RateLimitException.builder()
                .headers(Headers.builder().put(AnthropicAiClient.RETRY_AFTER_HEADER, "2.5").build())
                .body(JsonValue.from(Map.of()))
                .build();

        AiUnavailableException translated = AnthropicAiClient.translate(rateLimit);

        assertThat(translated.isRetryable()).isTrue();
        assertThat(translated.isTimeout()).isFalse();
        assertThat(translated.retryAfter()).hasValue(Duration.ofMillis(2_500));
    }

    @Test
    void serverFailuresRetryAndClientFailuresDoNot() {
        assertThat(AnthropicAiClient.translate(InternalServerException.builder()
                .statusCode(503).headers(Headers.builder().build()).body(JsonValue.from(Map.of()))
                .build()).isRetryable()).isTrue();
        assertThat(AnthropicAiClient.translate(new AnthropicRetryableException("flaky")).isRetryable()).isTrue();

        AiUnavailableException unauthorized = AnthropicAiClient.translate(UnauthorizedException.builder()
                .headers(Headers.builder().build()).body(JsonValue.from(Map.of())).build());
        assertThat(unauthorized.isRetryable()).isFalse();
        assertThat(unauthorized.code()).isEqualTo("UnauthorizedException");
        assertThat(AnthropicAiClient.translate(BadRequestException.builder()
                .headers(Headers.builder().build()).body(JsonValue.from(Map.of())).build()).isRetryable()).isFalse();
    }

    @Test
    void aReadThatRanOutOfTimeIsATimeoutAndAConnectionFailureIsRetryable() {
        AiUnavailableException timeout = AnthropicAiClient.translate(
                new AnthropicIoException("read timed out", new SocketTimeoutException("timeout")));
        assertThat(timeout.isTimeout()).isTrue();
        assertThat(timeout.isRetryable()).isFalse();

        AiUnavailableException reset = AnthropicAiClient.translate(
                new AnthropicIoException("connection reset", new IOException("reset")));
        assertThat(reset.isTimeout()).isFalse();
        assertThat(reset.isRetryable()).isTrue();
    }

    @Test
    void anUnreadableWaitHintIsSimplyAbsent() {
        assertThat(AnthropicAiClient.retryAfter(null)).isNull();
        assertThat(AnthropicAiClient.retryAfter(Headers.builder().build())).isNull();
        assertThat(AnthropicAiClient.retryAfter(Headers.builder()
                .put(AnthropicAiClient.RETRY_AFTER_HEADER, "soon").build())).isNull();
        assertThat(AnthropicAiClient.retryAfter(Headers.builder()
                .put(AnthropicAiClient.RETRY_AFTER_HEADER, " 3 ").build())).isEqualTo(Duration.ofSeconds(3));
    }

    private static Message message(ToolUseBlock toolUse) {
        return message(StopReason.TOOL_USE).addContent(toolUse).build();
    }

    /** Every optional field of the SDK's response record has to be set explicitly. */
    private static Message.Builder message(StopReason stopReason) {
        return Message.builder()
                .id("msg-1")
                .model("model-x")
                .content(java.util.List.of())
                .stopReason(stopReason)
                .stopDetails(Optional.empty())
                .stopSequence(Optional.empty())
                .container(Optional.empty())
                .usage(usage(11, 4, 0L, 0L));
    }

    /** The SDK's usage record needs every optional field set explicitly; only four carry values. */
    private static com.anthropic.models.messages.Usage usage(long input, long output, long cacheRead,
            long cacheWrite) {
        return com.anthropic.models.messages.Usage.builder()
                .inputTokens(input)
                .outputTokens(output)
                .cacheReadInputTokens(cacheRead)
                .cacheCreationInputTokens(cacheWrite)
                .cacheCreation(Optional.empty())
                .inferenceGeo(Optional.empty())
                .outputTokensDetails(Optional.empty())
                .serverToolUse(Optional.empty())
                .serviceTier(Optional.empty())
                .build();
    }
}
