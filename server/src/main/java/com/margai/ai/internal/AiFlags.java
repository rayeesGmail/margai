package com.margai.ai.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The AI module's switches under {@code margai.flags.*} (TECH_PLAN §11.6): booleans read from
 * configuration, where a flip is a config change and a task restart and there is no flag service.
 * Other modules bind their own flags under the same prefix.
 *
 * @param aiFallback completions go to {@code margai.ai.fallback-provider} instead of
 *                   {@code margai.ai.provider} (CS-2 §4.11, TECH_PLAN §15.12) — the outage switch the
 *                   D70 drill exercises. Embeddings keep their pin either way
 */
@ConfigurationProperties(prefix = "margai.flags")
public record AiFlags(boolean aiFallback) {
}
