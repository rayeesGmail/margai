package com.margai.ai.internal;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Which provider serves completions (TECH_PLAN §15.12): {@code margai.ai.provider}, or
 * {@code margai.ai.fallback-provider} while {@code margai.flags.ai-fallback} is on. The switch is
 * the flag and a task restart — there is no automatic failover (CS-2 §4.11). Read from the
 * environment rather than from the bound records because it decides which provider configuration
 * exists at all, and a condition runs before any bean does.
 *
 * @param provider the provider whose configuration wires the live completion client
 * @param fallback whether that is the fallback, so the client takes the fallback's model ids
 */
public record CompletionRoute(AiProperties.Provider provider, boolean fallback) {

    public static CompletionRoute of(Environment environment) {
        Binder binder = Binder.get(environment);
        if (!binder.bind("margai.flags.ai-fallback", Boolean.class).orElse(false)) {
            return new CompletionRoute(binder.bind("margai.ai.provider", AiProperties.Provider.class)
                    .orElse(AiProperties.Provider.anthropic), false);
        }
        return new CompletionRoute(binder.bind("margai.ai.fallback-provider", AiProperties.Provider.class)
                .orElse(AiProperties.Provider.bedrock), true);
    }

    /** The inner client's name in {@code AiClientInfo} and the startup log: the fallback says so. */
    public String clientName() {
        return fallback ? provider.name() + " (fallback)" : provider.name();
    }

    /** Matches when Anthropic's own API serves completions. */
    public static final class OnAnthropic implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return of(context.getEnvironment()).provider() == AiProperties.Provider.anthropic;
        }
    }

    /** Matches when Bedrock serves completions: the fallback, or the primary by configuration. */
    public static final class OnBedrock implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return of(context.getEnvironment()).provider() == AiProperties.Provider.bedrock;
        }
    }
}
