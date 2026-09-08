package com.margai.ai.internal;

import com.margai.ai.api.AiBudgetExceededException;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import java.util.UUID;

/**
 * TECH_PLAN §4.8 breaker, SPEC §3 "per-user AI cost circuit breakers exist": before every call
 * the global and, for a user call, the per-user spend of the current IST day is compared with
 * the configured caps; at or over a cap the call is refused with
 * {@link AiBudgetExceededException} and the ledger records a {@code breaker} row. Active in
 * every profile, including the fake (DEV_SPEC §13.7 item 6).
 */
public final class BudgetBreakerAiClient implements AiClient {

    private final AiClient inner;
    private final DailySpend spend;
    private final AiProperties.Budget budget;

    public BudgetBreakerAiClient(AiClient inner, DailySpend spend, AiProperties.Budget budget) {
        this.inner = inner;
        this.spend = spend;
        this.budget = budget;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        check(request.ctx().userId());
        return inner.complete(request);
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        check(request.ctx().userId());
        return inner.embed(request);
    }

    private void check(UUID userId) {
        long global = spend.globalSpendToday();
        if (global >= budget.globalDailyPaise()) {
            throw new AiBudgetExceededException(AiBudgetExceededException.Scope.global, global, budget.globalDailyPaise());
        }
        if (userId != null) {
            long user = spend.userSpendToday(userId);
            if (user >= budget.userDailyPaise()) {
                throw new AiBudgetExceededException(AiBudgetExceededException.Scope.user, user, budget.userDailyPaise());
            }
        }
    }
}
