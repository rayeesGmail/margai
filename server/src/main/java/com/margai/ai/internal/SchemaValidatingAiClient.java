package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Repair;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TECH_PLAN §4.1 {@code SchemaValidatingAiClient}: the inner client validates every answer
 * against the output record's schema ({@link StructuredOutput}) and raises
 * {@link InvalidOutputException}; this decorator grants exactly one repair attempt with the
 * validation errors in context ({@link Repair}) and, if that fails too, propagates the failure
 * with the tokens of both attempts so the ledger bills them. A request that already carries a
 * repair is attempted once.
 */
public final class SchemaValidatingAiClient implements AiClient {

    private static final Logger log = LoggerFactory.getLogger(SchemaValidatingAiClient.class);

    private final AiClient inner;

    public SchemaValidatingAiClient(AiClient inner) {
        this.inner = inner;
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        if (request.repair() != null) {
            return inner.complete(request);
        }
        long started = System.nanoTime();
        InvalidOutputException first;
        try {
            return inner.complete(request);
        } catch (InvalidOutputException e) {
            first = e;
        }
        log.warn("invalid model output for {}/{} ({}); one repair attempt", request.feature(),
                request.prompt().name(), first.errors());
        AiRequest<T> repair = request.withRepair(new Repair(first.outputJson(), first.errors()));
        try {
            AiResponse<T> repaired = inner.complete(repair);
            return new AiResponse<>(repaired.output(), first.usage().plus(repaired.usage()), repaired.modelId(),
                    Duration.ofNanos(System.nanoTime() - started), repaired.aiCallId(), repaired.attempts() + 1);
        } catch (InvalidOutputException second) {
            throw second.withUsage(first.usage().plus(second.usage()));
        }
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        return inner.embed(request);
    }
}
