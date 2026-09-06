package com.margai.ai.api;

import java.util.List;

/**
 * What the wired {@link AiClient} is made of: the inner implementation ({@code fake} or
 * {@code bedrock}) and the decorator chain, outermost first. Logged at startup and asserted by
 * the boot and smoke tests.
 */
public record AiClientInfo(String inner, List<String> chain) {

    public AiClientInfo {
        chain = List.copyOf(chain);
    }

    public boolean isLive() {
        return "bedrock".equals(inner);
    }

    @Override
    public String toString() {
        return String.join(" > ", chain) + " > " + inner;
    }
}
