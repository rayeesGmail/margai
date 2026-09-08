package com.margai.ai.internal;

/**
 * A prompt after template rendering (TECH_PLAN §4.11, §4.12): the system text is the cached
 * prefix, the user text carries the question and retrieved passages. Name and version are
 * what the ledger stamps.
 */
public record RenderedPrompt(String name, int version, String system, String user) {
}
