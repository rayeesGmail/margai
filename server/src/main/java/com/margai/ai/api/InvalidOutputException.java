package com.margai.ai.api;

import java.util.List;

/**
 * The model answered, but not in the shape of the output record (TECH_PLAN §4.1
 * {@code SchemaValidatingAiClient}). Carries the tokens the failed attempt(s) consumed so the
 * ledger still bills them, and the validation messages the repair retry puts in context.
 */
public final class InvalidOutputException extends RuntimeException {

    private final List<String> errors;
    private final String outputJson;
    private final Usage usage;
    private final String modelId;
    private final boolean repairable;

    public InvalidOutputException(List<String> errors, String outputJson, Usage usage, String modelId) {
        this(errors, outputJson, usage, modelId, true);
    }

    public InvalidOutputException(List<String> errors, String outputJson, Usage usage, String modelId,
            boolean repairable) {
        super("model output failed schema validation: " + String.join("; ", errors));
        this.errors = List.copyOf(errors);
        this.outputJson = outputJson;
        this.usage = usage == null ? Usage.none() : usage;
        this.modelId = modelId;
        this.repairable = repairable;
    }

    /**
     * Whether asking the model again could plausibly help. False when the fault is ours rather than
     * the model's — an answer cut off at the configured output-token limit is the case that matters:
     * the repair would re-run the same page, truncate identically and double the cost of the
     * pipeline's most expensive failure (spec-auditor, D14).
     */
    public boolean repairable() {
        return repairable;
    }

    public List<String> errors() {
        return errors;
    }

    /** The rejected output as JSON, or null when the model produced no structured output at all. */
    public String outputJson() {
        return outputJson;
    }

    public Usage usage() {
        return usage;
    }

    public String modelId() {
        return modelId;
    }

    public InvalidOutputException withUsage(Usage total) {
        return new InvalidOutputException(errors, outputJson, total, modelId, repairable);
    }
}
