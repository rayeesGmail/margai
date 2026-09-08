package com.margai.ai.api;

/**
 * Token usage of one call as the model reports it (TECH_PLAN §4.1, §4.8). {@code inputTokens}
 * excludes the cache read and write tokens, which are priced separately.
 */
public record Usage(int inputTokens, int outputTokens, int cacheReadTokens, int cacheWriteTokens) {

    private static final Usage NONE = new Usage(0, 0, 0, 0);

    public static Usage none() {
        return NONE;
    }

    public Usage plus(Usage other) {
        return new Usage(inputTokens + other.inputTokens, outputTokens + other.outputTokens,
                cacheReadTokens + other.cacheReadTokens, cacheWriteTokens + other.cacheWriteTokens);
    }

    public boolean isEmpty() {
        return inputTokens == 0 && outputTokens == 0 && cacheReadTokens == 0 && cacheWriteTokens == 0;
    }
}
