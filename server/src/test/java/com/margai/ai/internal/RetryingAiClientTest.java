package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.api.Usage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §4.11: 2 retries with jitter on throttling and 5xx; nothing else is retried. */
class RetryingAiClientTest {

    private static final AiRequest<String> REQUEST = AiRequest.of(AiFeature.doubt, Tier.cheap,
            PromptRef.named("echo"), Map.of(), String.class, AiCallContext.system("req"));
    private static final Duration BASE = Duration.ofMillis(500);

    private final StubAiClient inner = new StubAiClient();
    private final List<Duration> sleeps = new ArrayList<>();
    private final RetryingAiClient retrying = new RetryingAiClient(inner, 2, BASE, sleeps::add, new Random(7));

    private static AiUnavailableException throttled() {
        return AiUnavailableException.retryable("ThrottlingException", "slow down", null);
    }

    @Test
    void retryableFailuresAreRetriedTwiceWithGrowingJitteredDelays() {
        inner.then(throttled()).then(throttled()).then(StubAiClient.ok("third time"));

        assertThat(retrying.complete(REQUEST).output()).isEqualTo("third time");

        assertThat(inner.requests).hasSize(3);
        assertThat(sleeps).hasSize(2);
        assertThat(sleeps.get(0).toMillis()).isBetween(250L, 750L);
        assertThat(sleeps.get(1).toMillis()).isBetween(500L, 1500L);
    }

    @Test
    void theThirdFailureIsFinal() {
        inner.then(throttled()).then(throttled()).then(throttled()).then(StubAiClient.ok("never"));

        assertThatThrownBy(() -> retrying.complete(REQUEST))
                .isInstanceOf(AiUnavailableException.class)
                .satisfies(e -> assertThat(((AiUnavailableException) e).code()).isEqualTo("ThrottlingException"));
        assertThat(inner.requests).hasSize(3);
        assertThat(sleeps).hasSize(2);
    }

    @Test
    void permanentFailuresAndTimeoutsAreNotRetried() {
        inner.then(AiUnavailableException.permanent("AccessDeniedException", "denied", null));
        assertThatThrownBy(() -> retrying.complete(REQUEST)).isInstanceOf(AiUnavailableException.class);

        inner.then(AiUnavailableException.timeout("20 s elapsed", null));
        assertThatThrownBy(() -> retrying.complete(REQUEST))
                .isInstanceOf(AiUnavailableException.class)
                .satisfies(e -> assertThat(((AiUnavailableException) e).isTimeout()).isTrue());

        assertThat(inner.requests).hasSize(2);
        assertThat(sleeps).isEmpty();
    }

    @Test
    void invalidOutputIsNotARetryMatter() {
        inner.then(new InvalidOutputException(List.of("bad"), "{}", Usage.none(), "m"));

        assertThatThrownBy(() -> retrying.complete(REQUEST)).isInstanceOf(InvalidOutputException.class);
        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void embeddingsAreRetriedTheSameWay() {
        inner.then(throttled()).then(StubAiClient.ok(new float[] {1f}));

        assertThat(retrying.embed(new EmbedRequest(AiFeature.embed, "t", EmbedRequest.InputType.search_query,
                AiCallContext.system("req"))).output()).containsExactly(1f);
        assertThat(inner.embeds).hasSize(2);
        assertThat(sleeps).hasSize(1);
    }

    @Test
    void defaultsAreTwoRetriesFromHalfASecond() {
        assertThat(RetryingAiClient.DEFAULT_RETRIES).isEqualTo(2);
        assertThat(RetryingAiClient.DEFAULT_BASE_DELAY).isEqualTo(Duration.ofMillis(500));
        assertThat(new RetryingAiClient(inner).backoff(1).toMillis()).isBetween(250L, 750L);
    }
}
