package com.margai.ai.internal;

import com.margai.ai.api.AiClient;

/**
 * The innermost client the chain wraps, with its name for {@code AiClientInfo}. A bean of this
 * type exists only in the {@code live} profile, supplied by the package of whichever provider
 * {@code margai.ai.provider} selects; without one the chain wraps {@link FakeAiClient}.
 * Deliberately not an {@link AiClient} itself, so the one {@code AiClient} bean stays unambiguous.
 */
public record InnerAiClient(String name, AiClient client) {
}
