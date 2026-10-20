package com.margai.ai.internal;

import com.margai.ai.api.AiClient;

/**
 * The innermost client the chain wraps, with its name for {@code AiClientInfo}. A bean of this
 * type exists only in the {@code live} profile, supplied by the package of whichever provider
 * {@link CompletionRoute} selects; without one the chain wraps {@link FakeAiClient}.
 * Deliberately not an {@link AiClient} itself, so the one {@code AiClient} bean stays unambiguous.
 *
 * @param fallback the client serves completions as the fallback (TECH_PLAN §15.12), so its calls
 *                 bill under the fallback's model ids — the ledger stamps them before the call, and
 *                 a call that fails never reports the id it used
 */
public record InnerAiClient(String name, AiClient client, boolean fallback) {

    public InnerAiClient(String name, AiClient client) {
        this(name, client, false);
    }
}
