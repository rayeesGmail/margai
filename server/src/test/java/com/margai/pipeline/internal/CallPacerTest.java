package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The pacer that keeps {@code ncert embed} under a per-minute provider quota. Built after the
 * first live run on phy11-part1 died on call 101 of 894 against a trial key capped at 100
 * calls/minute (2026-09-20).
 */
class CallPacerTest {

    private final List<Duration> slept = new ArrayList<>();

    @Test
    void theFirstCallNeverWaits() {
        CallPacer pacer = CallPacer.perMinute(60, slept::add);

        pacer.awaitTurn();

        assertThat(slept).isEmpty();
    }

    @Test
    void laterCallsWaitOutTheRemainderOfTheInterval() {
        CallPacer pacer = CallPacer.perMinute(60, slept::add);

        pacer.awaitTurn();
        pacer.awaitTurn();
        pacer.awaitTurn();

        assertThat(slept).hasSize(2);
        assertThat(slept).allSatisfy(wait -> assertThat(wait)
                .as("60/minute is one call a second, minus the time already elapsed")
                .isLessThanOrEqualTo(Duration.ofSeconds(1))
                .isGreaterThan(Duration.ofMillis(800)));
    }

    /**
     * The interval runs between call <em>starts</em>, so a slow provider is never made slower than
     * the rate requires — the call's own latency already counted toward the window.
     */
    @Test
    void timeSpentInTheCallCountsTowardTheInterval() throws InterruptedException {
        CallPacer pacer = CallPacer.perMinute(120, slept::add);

        pacer.awaitTurn();
        Thread.sleep(Duration.ofMillis(600));
        pacer.awaitTurn();

        assertThat(slept).as("120/minute is 500 ms apart, and 600 ms already passed").isEmpty();
    }

    @Test
    void aNonPositiveRateDoesNotPaceAtAll() {
        CallPacer pacer = CallPacer.perMinute(0, slept::add);

        pacer.awaitTurn();
        pacer.awaitTurn();

        assertThat(slept).isEmpty();
        assertThat(pacer.isThrottled()).isFalse();
        assertThat(CallPacer.unthrottled().isThrottled()).isFalse();
    }

    /** What the report tells the founder before a ten-minute run starts. */
    @Test
    void estimatesHowLongARunWillTake() {
        CallPacer pacer = CallPacer.perMinute(90, slept::add);

        assertThat(pacer.estimateFor(894).toMinutes()).isEqualTo(9);
        assertThat(pacer.estimateFor(1)).isZero();
        assertThat(pacer.estimateFor(0)).isZero();
        assertThat(CallPacer.unthrottled().estimateFor(894)).isZero();
    }
}
