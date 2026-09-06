package com.margai.ai.internal;

import com.margai.ai.api.AiBudgetExceededException;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Tier;
import com.margai.ai.api.Usage;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * The outermost decorator (TECH_PLAN §4.1, §4.8, §4.13 "every Bedrock call logs an
 * {@code ai_calls} row"): one ledger row per call for every outcome — {@code ok},
 * {@code invalid_output} (with the tokens the failed attempts consumed), {@code timeout},
 * {@code error} and {@code breaker} — with {@code cost_paise} computed at insert and the
 * §10.2 metrics {@code ai.calls}, {@code ai.cost.paise}, {@code ai.latency}. The model id of a
 * failed call is the tier's configured model; the prompt version is what the registry has active.
 */
public final class LedgerAiClient implements AiClient {

    private final AiClient inner;
    private final AiCallLedger ledger;
    private final CostCalculator cost;
    private final AiProperties properties;
    private final PromptRegistry prompts;
    private final MeterRegistry meters;

    public LedgerAiClient(AiClient inner, AiCallLedger ledger, CostCalculator cost, AiProperties properties,
            PromptRegistry prompts, MeterRegistry meters) {
        this.inner = inner;
        this.ledger = ledger;
        this.cost = cost;
        this.properties = properties;
        this.prompts = prompts;
        this.meters = meters;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        OptionalInt version = prompts.versionIfKnown(request.prompt().name());
        AiCall row = new AiCall(request.ctx().userId(), request.feature(), properties.modelFor(request.tier()),
                request.tier(), request.ctx().requestId())
                .prompt(request.prompt().name(), version.isPresent() ? (short) version.getAsInt() : null);
        return recorded(row, () -> inner.complete(request));
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        AiCall row = new AiCall(request.ctx().userId(), request.feature(), properties.embed().model(), Tier.embed,
                request.ctx().requestId());
        return recorded(row, () -> inner.embed(request));
    }

    private <T> AiResponse<T> recorded(AiCall row, Supplier<AiResponse<T>> call) {
        long started = System.nanoTime();
        try {
            AiResponse<T> response = call.get();
            UUID id = write(row, response.modelId(), response.usage(), AiCallStatus.ok, null, started);
            return response.withAiCallId(id);
        } catch (AiBudgetExceededException e) {
            write(row, null, Usage.none(), AiCallStatus.breaker, e.scope().name(), started);
            throw e;
        } catch (InvalidOutputException e) {
            write(row, e.modelId(), e.usage(), AiCallStatus.invalid_output, "invalid_output", started);
            throw e;
        } catch (AiUnavailableException e) {
            write(row, null, Usage.none(), e.isTimeout() ? AiCallStatus.timeout : AiCallStatus.error, e.code(), started);
            throw e;
        } catch (RuntimeException e) {
            write(row, null, Usage.none(), AiCallStatus.error, e.getClass().getSimpleName(), started);
            throw e;
        }
    }

    private UUID write(AiCall row, String actualModelId, Usage usage, AiCallStatus status, String errorCode,
            long started) {
        Duration latency = Duration.ofNanos(System.nanoTime() - started);
        AiCall attributed = actualModelId == null || actualModelId.equals(row.getModelId())
                ? row
                : new AiCall(row.getUserId(), row.getFeature(), actualModelId, row.getTier(), row.getRequestId())
                        .prompt(row.getPromptName(), row.getPromptVersion());
        long paise = usage.isEmpty() ? 0 : cost.paise(attributed.getModelId(), usage, false);
        if (!usage.isEmpty()) {
            attributed.tokens(usage.inputTokens(), usage.outputTokens(), usage.cacheReadTokens(), usage.cacheWriteTokens());
        }
        UUID id = ledger.record(attributed
                .latencyMs((int) Math.min(Integer.MAX_VALUE, latency.toMillis()))
                .outcome(status, errorCode)
                .costPaise(paise));
        AiFeature feature = attributed.getFeature();
        String tier = attributed.getTier().name();
        meters.counter("ai.calls", "feature", feature.name(), "tier", tier, "status", status.name()).increment();
        meters.counter("ai.cost.paise", "feature", feature.name(), "tier", tier).increment(paise);
        meters.timer("ai.latency", "tier", tier).record(latency);
        return id;
    }
}
