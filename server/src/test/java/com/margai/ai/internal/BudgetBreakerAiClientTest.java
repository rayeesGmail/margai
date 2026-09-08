package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiBudgetExceededException;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §4.8: the per-user and global daily caps, checked before every call. */
class BudgetBreakerAiClientTest {

    private static final UUID USER = UUID.randomUUID();

    private final Map<UUID, Long> userSpend = new HashMap<>();
    private long globalSpend;

    private final DailySpend spend = new DailySpend() {
        @Override
        public long userSpendToday(UUID userId) {
            return userSpend.getOrDefault(userId, 0L);
        }

        @Override
        public long globalSpendToday() {
            return globalSpend;
        }
    };

    private final StubAiClient inner = new StubAiClient();
    private final BudgetBreakerAiClient breaker = new BudgetBreakerAiClient(inner, spend,
            new AiProperties.Budget(2_500, 50_000));

    private static AiRequest<String> request(AiCallContext ctx) {
        return AiRequest.of(AiFeature.doubt, Tier.cheap, PromptRef.named("echo"), Map.of(), String.class, ctx);
    }

    @Test
    void underBothCapsTheCallGoesThrough() {
        userSpend.put(USER, 2_499L);
        globalSpend = 49_999;

        breaker.complete(request(AiCallContext.forUser(USER, "req")));

        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void atTheUserCapTheCallIsRefused() {
        userSpend.put(USER, 2_500L);

        assertThatThrownBy(() -> breaker.complete(request(AiCallContext.forUser(USER, "req"))))
                .isInstanceOf(AiBudgetExceededException.class)
                .satisfies(e -> {
                    AiBudgetExceededException exceeded = (AiBudgetExceededException) e;
                    assertThat(exceeded.scope()).isEqualTo(AiBudgetExceededException.Scope.user);
                    assertThat(exceeded.spentPaise()).isEqualTo(2_500);
                    assertThat(exceeded.limitPaise()).isEqualTo(2_500);
                });
        assertThat(inner.requests).isEmpty();
    }

    @Test
    void theGlobalCapTripsEvenForAUserWhoSpentNothingAndForSystemCalls() {
        globalSpend = 50_000;

        assertThatThrownBy(() -> breaker.complete(request(AiCallContext.forUser(USER, "req"))))
                .isInstanceOf(AiBudgetExceededException.class)
                .satisfies(e -> assertThat(((AiBudgetExceededException) e).scope())
                        .isEqualTo(AiBudgetExceededException.Scope.global));
        assertThatThrownBy(() -> breaker.complete(request(AiCallContext.system("req"))))
                .isInstanceOf(AiBudgetExceededException.class);
        assertThat(inner.requests).isEmpty();
    }

    @Test
    void systemCallsOnlyFaceTheGlobalCap() {
        userSpend.put(USER, 9_999L);

        breaker.complete(request(AiCallContext.system("req")));

        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void embeddingsAreBudgetedToo() {
        userSpend.put(USER, 2_500L);

        assertThatThrownBy(() -> breaker.embed(new EmbedRequest(AiFeature.embed, "t",
                EmbedRequest.InputType.search_query, AiCallContext.forUser(USER, "req"))))
                .isInstanceOf(AiBudgetExceededException.class);
        assertThat(inner.embeds).isEmpty();
    }
}
