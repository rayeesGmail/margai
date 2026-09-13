package com.margai.ai.api;

/**
 * What one request id cost, read back from the {@code ai_calls} ledger (TECH_PLAN §4.8, §10.5:
 * the ledger is the source of truth for spend, not a caller's own arithmetic). A pipeline run
 * uses one request id for every call it makes, so this is the run's bill — the cost line the §6.3
 * reports carry, and the first thing the founder looks at after a day that spends.
 */
public interface AiSpend {

    RunSpend of(String requestId);

    /**
     * @param calls     ledger rows with this request id, whatever their outcome
     * @param costPaise what they cost, as the ledger computed it at insert
     * @param usage     the tokens behind that cost
     */
    record RunSpend(int calls, long costPaise, Usage usage) {

        public String rupees() {
            return "₹%d.%02d".formatted(costPaise / 100, costPaise % 100);
        }
    }
}
