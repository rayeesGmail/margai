package com.margai.ai.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * The on-demand half of {@code completeBatch} (TECH_PLAN §4.11): every request runs through
 * {@code complete} — and therefore through the whole decorator chain — on a virtual thread,
 * at most {@link #CONCURRENCY} at a time, results in request order.
 */
final class OnDemandBatch {

    static final int CONCURRENCY = 4;

    private OnDemandBatch() {
    }

    static <T> List<AiResponse<T>> run(List<AiRequest<T>> requests,
            Function<AiRequest<T>, AiResponse<T>> call, int concurrency) {
        Semaphore permits = new Semaphore(concurrency);
        try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<AiResponse<T>>> futures = new ArrayList<>(requests.size());
            for (AiRequest<T> request : requests) {
                futures.add(pool.submit(() -> {
                    permits.acquire();
                    try {
                        return call.apply(request);
                    } finally {
                        permits.release();
                    }
                }));
            }
            List<AiResponse<T>> responses = new ArrayList<>(requests.size());
            RuntimeException failure = null;
            for (Future<AiResponse<T>> future : futures) {
                try {
                    responses.add(future.get());
                } catch (ExecutionException e) {
                    RuntimeException cause = e.getCause() instanceof RuntimeException runtime
                            ? runtime : new IllegalStateException(e.getCause());
                    if (failure == null) {
                        failure = cause;
                    } else {
                        failure.addSuppressed(cause);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("interrupted while waiting for a batch record", e);
                }
            }
            if (failure != null) {
                throw failure;
            }
            return responses;
        }
    }
}
