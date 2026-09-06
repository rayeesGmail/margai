package com.margai.ai.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §4.11: below the batch minimum, requests run on-demand with concurrency 4, in order. */
class OnDemandBatchTest {

    private static AiRequest<String> request(int i) {
        return AiRequest.of(AiFeature.smoke, Tier.cheap, PromptRef.named("smoke"), Map.of("i", i),
                String.class, AiCallContext.system("req-" + i));
    }

    @Test
    void runsEveryRequestAtMostFourAtATimeAndKeepsOrder() throws Exception {
        AtomicInteger inFlight = new AtomicInteger();
        AtomicInteger peak = new AtomicInteger();

        List<AiResponse<String>> responses = OnDemandBatch.run(
                IntStream.range(0, 12).mapToObj(OnDemandBatchTest::request).toList(),
                request -> {
                    int now = inFlight.incrementAndGet();
                    peak.accumulateAndGet(now, Math::max);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        inFlight.decrementAndGet();
                    }
                    return new AiResponse<>("out-" + request.variables().get("i"), Usage.none(), "m", Duration.ZERO, null);
                }, OnDemandBatch.CONCURRENCY);

        assertThat(responses).extracting(AiResponse::output)
                .containsExactlyElementsOf(IntStream.range(0, 12).mapToObj(i -> "out-" + i).toList());
        assertThat(peak.get()).isBetween(2, 4);
    }

    @Test
    void firstFailureIsRethrownAfterEveryRecordRan() {
        AtomicInteger ran = new AtomicInteger();

        assertThatThrownBy(() -> OnDemandBatch.run(
                IntStream.range(0, 6).mapToObj(OnDemandBatchTest::request).toList(),
                request -> {
                    ran.incrementAndGet();
                    int i = (Integer) request.variables().get("i");
                    if (i % 2 == 1) {
                        throw new IllegalStateException("record " + i + " failed");
                    }
                    return new AiResponse<>("ok", Usage.none(), "m", Duration.ZERO, null);
                }, 2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("record 1 failed")
                .satisfies(e -> assertThat(e.getSuppressed()).hasSize(2));
        assertThat(ran.get()).isEqualTo(6);
    }

    @Test
    void emptyBatchIsEmpty() {
        assertThat(OnDemandBatch.run(List.<AiRequest<String>>of(), r -> null, 4)).isEmpty();
    }
}
