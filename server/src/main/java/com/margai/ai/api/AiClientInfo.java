package com.margai.ai.api;

import java.util.List;

/**
 * What the wired {@link AiClient} is made of: the inner implementation — {@code fake}, or the
 * configured provider — and the decorator chain, outermost first. Logged at startup and asserted
 * by the boot and smoke tests.
 */
public record AiClientInfo(String inner, List<String> chain) {

    /** The name of the inner client that reaches no provider; anything else costs money. */
    public static final String FAKE = "fake";

    public AiClientInfo {
        chain = List.copyOf(chain);
    }

    /** True when calls leave the process for a real provider, whichever one is configured. */
    public boolean isLive() {
        return !FAKE.equals(inner);
    }

    @Override
    public String toString() {
        return String.join(" > ", chain) + " > " + inner;
    }
}
