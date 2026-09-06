package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.margai.ai.api.AiBudgetExceededException;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.api.TierPolicyException;
import com.margai.ai.api.Usage;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * TECH_PLAN §4.13 "every Bedrock call logs an {@code ai_calls} row": one row per outcome with
 * the right status, error code, tokens and cost; metrics per §10.2. The ledger itself is
 * mocked; {@link AiCallRepositoryTest} covers the database side.
 */
class LedgerAiClientTest {

    private static final UUID USER = UUID.randomUUID();
    private static final UUID ROW = UUID.randomUUID();

    private final StubAiClient inner = new StubAiClient();
    private final AiCallLedger ledger = mock(AiCallLedger.class);
    private final ArgumentCaptor<AiCall> rows = ArgumentCaptor.forClass(AiCall.class);
    private final SimpleMeterRegistry meters = new SimpleMeterRegistry();
    private final AiProperties properties = new AiProperties("ap-south-1",
            new AiProperties.Tiers("cheap-model", "reason-model", "vision-model"),
            new AiProperties.Embed("embed-model"), "{}", BigDecimal.ONE, new AiProperties.Budget(1, 1), 100, 1024,
            java.time.Duration.ofSeconds(20), Map.of());
    private final PriceTable prices = PriceTable.parse("""
            {"cheap-model": {"input": 1.00, "output": 5.00, "cache_read": 0.10, "cache_write": 1.25},
             "stub-model": {"input": 1.00, "output": 5.00},
             "embed-model": {"input": 0.10}}
            """);
    private final PromptRegistry prompts = new PromptRegistry(Map.of("echo.v1.stg",
            "system(v) ::= <<s>>\nuser(v) ::= <<u>>\n"), Map.of());
    private final LedgerAiClient client = new LedgerAiClient(inner, ledger,
            new CostCalculator(prices, new BigDecimal("90")), properties, prompts, meters);

    private static AiRequest<String> request() {
        return AiRequest.of(AiFeature.doubt, Tier.cheap, PromptRef.named("echo"), Map.of(), String.class,
                AiCallContext.forUser(USER, "req-9"));
    }

    private AiCall recordedRow() {
        org.mockito.Mockito.verify(ledger).record(rows.capture());
        return rows.getValue();
    }

    @Test
    void successWritesAnOkRowWithTokensAndCostAndReturnsItsId() {
        when(ledger.record(any())).thenReturn(ROW);
        inner.then(StubAiClient.ok("answer", new Usage(1_000, 200, 4_000, 0)));

        AiResponse<String> response = client.complete(request());

        assertThat(response.aiCallId()).isEqualTo(ROW);
        AiCall row = recordedRow();
        assertThat(row.getUserId()).isEqualTo(USER);
        assertThat(row.getFeature()).isEqualTo(AiFeature.doubt);
        assertThat(row.getTier()).isEqualTo(Tier.cheap);
        assertThat(row.getModelId()).isEqualTo(StubAiClient.MODEL);
        assertThat(row.getPromptName()).isEqualTo("echo");
        assertThat(row.getPromptVersion()).isEqualTo((short) 1);
        assertThat(row.getRequestId()).isEqualTo("req-9");
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
        assertThat(row.getInputTokens()).isEqualTo(1_000);
        assertThat(row.getOutputTokens()).isEqualTo(200);
        assertThat(row.getCacheReadTokens()).isEqualTo(4_000);
        // stub-model has no cache price: (1000×1 + 200×5) / 1e6 × 90 × 100 = 18 paise
        assertThat(row.getCostPaise()).isEqualTo(18);
        assertThat(row.getLatencyMs()).isNotNull();
        assertThat(row.isBatch()).isFalse();
        assertThat(meters.counter("ai.calls", "feature", "doubt", "tier", "cheap", "status", "ok").count()).isEqualTo(1);
        assertThat(meters.counter("ai.cost.paise", "feature", "doubt", "tier", "cheap").count()).isEqualTo(18);
        assertThat(meters.timer("ai.latency", "tier", "cheap").count()).isEqualTo(1);
        assertThat(meters.counter("ai.attempts", "tier", "cheap").count()).isEqualTo(1);
    }

    @Test
    void attemptsOfARetriedOrRepairedRequestAreCounted() {
        when(ledger.record(any())).thenReturn(ROW);
        inner.then(StubAiClient.ok("third try").withAttempts(3));

        client.complete(request());

        assertThat(meters.counter("ai.attempts", "tier", "cheap").count()).isEqualTo(3);
        assertThat(meters.counter("ai.calls", "feature", "doubt", "tier", "cheap", "status", "ok").count()).isEqualTo(1);
    }

    @Test
    void breakerWritesABreakerRowAtZeroCost() {
        inner.then(new AiBudgetExceededException(AiBudgetExceededException.Scope.user, 2_500, 2_500));

        assertThatThrownBy(() -> client.complete(request())).isInstanceOf(AiBudgetExceededException.class);

        AiCall row = recordedRow();
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.breaker);
        assertThat(row.getErrorCode()).isEqualTo("user");
        assertThat(row.getModelId()).isEqualTo("cheap-model");
        assertThat(row.getCostPaise()).isZero();
        assertThat(row.getInputTokens()).isNull();
    }

    @Test
    void invalidOutputIsBilledForTheTokensItConsumed() {
        inner.then(new InvalidOutputException(List.of("$.n: required"), "{}", new Usage(2_000, 100, 0, 0), "cheap-model"));

        assertThatThrownBy(() -> client.complete(request())).isInstanceOf(InvalidOutputException.class);

        AiCall row = recordedRow();
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.invalid_output);
        assertThat(row.getErrorCode()).isEqualTo("invalid_output");
        assertThat(row.getInputTokens()).isEqualTo(2_000);
        // (2000×1 + 100×5) / 1e6 × 90 × 100 = 22.5 → 23 paise
        assertThat(row.getCostPaise()).isEqualTo(23);
    }

    @Test
    void unavailableAndTimeoutRowsCarryTheCode() {
        inner.then(AiUnavailableException.retryable("ThrottlingException", "slow", null));
        assertThatThrownBy(() -> client.complete(request())).isInstanceOf(AiUnavailableException.class);
        AiCall error = recordedRow();
        assertThat(error.getStatus()).isEqualTo(AiCallStatus.error);
        assertThat(error.getErrorCode()).isEqualTo("ThrottlingException");
        assertThat(error.getCostPaise()).isZero();

        org.mockito.Mockito.clearInvocations(ledger);
        inner.then(AiUnavailableException.timeout("20 s", null));
        assertThatThrownBy(() -> client.complete(request())).isInstanceOf(AiUnavailableException.class);
        assertThat(recordedRow().getStatus()).isEqualTo(AiCallStatus.timeout);
        assertThat(meters.counter("ai.calls", "feature", "doubt", "tier", "cheap", "status", "timeout").count()).isEqualTo(1);
    }

    @Test
    void aProgrammingErrorIsStillARowNamedAfterTheException() {
        inner.then(new TierPolicyException("reason tier needs a RouteDecision"));

        assertThatThrownBy(() -> client.complete(request())).isInstanceOf(TierPolicyException.class);

        AiCall row = recordedRow();
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.error);
        assertThat(row.getErrorCode()).isEqualTo("TierPolicyException");
    }

    @Test
    void embeddingRowsUseTheEmbedTierAndModel() {
        when(ledger.record(any())).thenReturn(ROW);
        inner.then(new AiResponse<>(new float[] {1f}, new Usage(20_000, 0, 0, 0), "embed-model",
                java.time.Duration.ofMillis(3), null));

        AiResponse<float[]> response = client.embed(new EmbedRequest(AiFeature.embed, "text",
                EmbedRequest.InputType.search_document, AiCallContext.system("req-e")));

        assertThat(response.aiCallId()).isEqualTo(ROW);
        AiCall row = recordedRow();
        assertThat(row.getTier()).isEqualTo(Tier.embed);
        assertThat(row.getModelId()).isEqualTo("embed-model");
        assertThat(row.getPromptName()).isNull();
        assertThat(row.getUserId()).isNull();
        assertThat(row.getCostPaise()).isEqualTo(18);
    }

    @Test
    void anUnknownPromptStillGetsARowWithoutAVersion() {
        inner.then(new IllegalArgumentException("unknown prompt: nope"));

        assertThatThrownBy(() -> client.complete(AiRequest.of(AiFeature.doubt, Tier.cheap, PromptRef.named("nope"),
                Map.of(), String.class, AiCallContext.system("r")))).isInstanceOf(IllegalArgumentException.class);

        AiCall row = recordedRow();
        assertThat(row.getPromptName()).isEqualTo("nope");
        assertThat(row.getPromptVersion()).isNull();
        assertThat(row.getErrorCode()).isEqualTo("IllegalArgumentException");
    }
}
