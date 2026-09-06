package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.EmbedRequest;
import java.time.Duration;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TECH_PLAN §4.1 {@code RetryingAiClient}, §4.11: two retries with jittered exponential backoff
 * on the failures the inner client marks retryable (throttling, 5xx); timeouts, access denied
 * and invalid output are not retried. The only retry policy in the stack: the SDK client runs
 * with a single attempt (DECISIONS D5).
 */
public final class RetryingAiClient implements AiClient {

    /** Seam for tests; production sleeps for real. */
    @FunctionalInterface
    public interface Sleeper {
        void sleep(Duration duration) throws InterruptedException;
    }

    public static final int DEFAULT_RETRIES = 2;
    public static final Duration DEFAULT_BASE_DELAY = Duration.ofMillis(500);

    private static final Logger log = LoggerFactory.getLogger(RetryingAiClient.class);

    private final AiClient inner;
    private final int maxRetries;
    private final Duration baseDelay;
    private final Sleeper sleeper;
    private final RandomGenerator random;

    public RetryingAiClient(AiClient inner) {
        this(inner, DEFAULT_RETRIES, DEFAULT_BASE_DELAY, Thread::sleep, RandomGenerator.getDefault());
    }

    public RetryingAiClient(AiClient inner, int maxRetries, Duration baseDelay, Sleeper sleeper, RandomGenerator random) {
        this.inner = inner;
        this.maxRetries = maxRetries;
        this.baseDelay = baseDelay;
        this.sleeper = sleeper;
        this.random = random;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        return attempt(() -> inner.complete(request), request.feature() + "/" + request.prompt().name());
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        return attempt(() -> inner.embed(request), request.feature() + "/embed");
    }

    private <R> R attempt(Supplier<R> call, String what) {
        for (int retry = 0; ; retry++) {
            try {
                return call.get();
            } catch (AiUnavailableException e) {
                if (!e.isRetryable() || retry == maxRetries) {
                    throw e;
                }
                Duration delay = backoff(retry + 1);
                log.warn("{} failed with {} ({}); retry {}/{} after {} ms", what, e.code(), e.getMessage(),
                        retry + 1, maxRetries, delay.toMillis());
                try {
                    sleeper.sleep(delay);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
    }

    /** {@code base × 2^(n-1)} scaled by a jitter factor in [0.5, 1.5). */
    Duration backoff(int retry) {
        double jitter = 0.5 + random.nextDouble();
        return Duration.ofMillis(Math.round(baseDelay.toMillis() * (1L << (retry - 1)) * jitter));
    }
}
