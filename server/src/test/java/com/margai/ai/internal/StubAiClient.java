package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.Usage;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Scripted inner client for decorator tests: queued outcomes (a response or an exception to
 * throw) are consumed in order, every request is recorded. With an empty queue it answers
 * {@link #ok(Object)} with the request's output type ignored.
 */
final class StubAiClient implements AiClient {

    static final String MODEL = "stub-model";
    static final Usage USAGE = new Usage(10, 5, 0, 0);

    final List<AiRequest<?>> requests = new ArrayList<>();
    final List<EmbedRequest> embeds = new ArrayList<>();
    private final Deque<Object> outcomes = new ArrayDeque<>();
    private final Object fallback;

    StubAiClient(Object fallback) {
        this.fallback = fallback;
    }

    StubAiClient() {
        this(ok("fallback"));
    }

    StubAiClient then(Object outcome) {
        outcomes.add(outcome);
        return this;
    }

    static <T> AiResponse<T> ok(T output) {
        return new AiResponse<>(output, USAGE, MODEL, Duration.ofMillis(1), null);
    }

    static <T> AiResponse<T> ok(T output, Usage usage) {
        return new AiResponse<>(output, usage, MODEL, Duration.ofMillis(1), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        requests.add(request);
        return (AiResponse<T>) next();
    }

    @Override
    @SuppressWarnings("unchecked")
    public AiResponse<float[]> embed(EmbedRequest request) {
        embeds.add(request);
        return (AiResponse<float[]>) next();
    }

    private Object next() {
        Object outcome = outcomes.isEmpty() ? fallback : outcomes.pop();
        if (outcome instanceof RuntimeException failure) {
            throw failure;
        }
        return outcome;
    }
}
