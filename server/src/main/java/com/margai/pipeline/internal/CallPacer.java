package com.margai.pipeline.internal;

import java.time.Duration;

/**
 * Holds a loop of provider calls to a fixed rate, so a per-minute quota is never tripped rather
 * than tripped and retried.
 *
 * <p>Built after the first live {@code ncert embed} on phy11-part1 (2026-09-20) died on call 101
 * of 894: the embedding key is a trial key capped at <strong>100 calls per minute</strong>, and one
 * call per paragraph reaches that in about forty seconds. Retry could not save it —
 * {@code RetryingAiClient} backs off 0.6 s then 1.1 s, which is the right shape for a transient 5xx
 * and hopeless against a window measured in minutes, and a provider that sends no
 * {@code Retry-After} gives it nothing better to go on.
 *
 * <p>So this paces rather than reacts. A quota is a known, fixed property of the key, and waiting
 * for it to be exceeded before slowing down wastes a call and a retry every time. It is deliberately
 * a floor on the interval between call <em>starts</em>: the call's own latency counts toward the
 * interval, so a slow provider is never made slower than the rate requires.
 */
final class CallPacer {

    /** Seam for tests; production sleeps for real. */
    interface Sleeper {
        void sleep(Duration duration) throws InterruptedException;
    }

    private final long minimumIntervalNanos;
    private final Sleeper sleeper;
    private long lastStartNanos = Long.MIN_VALUE;

    /** A pacer that never waits — for a production key, or a test that does not want the clock. */
    static CallPacer unthrottled() {
        return new CallPacer(0, duration -> { });
    }

    static CallPacer perMinute(int callsPerMinute, Sleeper sleeper) {
        return new CallPacer(callsPerMinute <= 0 ? 0 : Duration.ofMinutes(1).toNanos() / callsPerMinute, sleeper);
    }

    private CallPacer(long minimumIntervalNanos, Sleeper sleeper) {
        this.minimumIntervalNanos = minimumIntervalNanos;
        this.sleeper = sleeper;
    }

    /** Call immediately before each provider call; returns once the rate allows it. */
    void awaitTurn() {
        if (minimumIntervalNanos == 0) {
            return;
        }
        long now = System.nanoTime();
        if (lastStartNanos != Long.MIN_VALUE) {
            long waitNanos = minimumIntervalNanos - (now - lastStartNanos);
            if (waitNanos > 0) {
                try {
                    sleeper.sleep(Duration.ofNanos(waitNanos));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("interrupted while pacing provider calls", e);
                }
                now = System.nanoTime();
            }
        }
        lastStartNanos = now;
    }

    /** How long a run of this many calls will take at this rate, for the report. */
    Duration estimateFor(int calls) {
        return minimumIntervalNanos == 0 || calls <= 1
                ? Duration.ZERO
                : Duration.ofNanos(minimumIntervalNanos * (calls - 1L));
    }

    boolean isThrottled() {
        return minimumIntervalNanos > 0;
    }
}
