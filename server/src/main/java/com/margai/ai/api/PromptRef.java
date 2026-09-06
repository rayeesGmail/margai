package com.margai.ai.api;

import java.util.regex.Pattern;

/**
 * A prompt by name (TECH_PLAN §4.12). The active version is configuration
 * ({@code margai.ai.prompts.<name>.version}, else the highest present), resolved by the prompt
 * registry, so a rollback is a config change and the ledger stamps what actually ran.
 */
public record PromptRef(String name) {

    private static final Pattern NAME = Pattern.compile("[a-z][a-z0-9_]*");

    public PromptRef {
        if (name == null || !NAME.matcher(name).matches()) {
            throw new IllegalArgumentException("prompt name must match " + NAME + ": " + name);
        }
    }

    public static PromptRef named(String name) {
        return new PromptRef(name);
    }
}
