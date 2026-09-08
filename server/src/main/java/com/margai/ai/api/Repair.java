package com.margai.ai.api;

import java.util.List;

/**
 * The context of the one repair retry (TECH_PLAN §4.1 {@code SchemaValidatingAiClient}): what
 * the model produced and why it was rejected, so the next attempt can fix it.
 */
public record Repair(String previousOutputJson, List<String> errors) {

    public Repair {
        errors = errors == null ? List.of() : List.copyOf(errors);
    }
}
