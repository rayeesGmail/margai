package com.margai.ai.api;

/**
 * Model tier of a call (TECH_PLAN §4.2): the model id behind each tier is configuration
 * ({@code margai.ai.tier.*}, {@code margai.ai.embed.model}), never code. Constants are the
 * lowercase {@code ai_calls.tier} codes (DECISIONS D4).
 */
public enum Tier {
    /** Routing, routine answers, plan selection, mentor notes, classification, rendering. */
    cheap,
    /** Hard or numerical answers, independent verification, variant and solution generation. */
    reason,
    /** Photo doubts, document images, pipeline page extraction. */
    vision,
    /** Paragraph, question and doubt embeddings. */
    embed
}
