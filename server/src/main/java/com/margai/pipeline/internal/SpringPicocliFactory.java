package com.margai.pipeline.internal;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

/**
 * picocli creates command, mixin and converter objects through an {@link CommandLine.IFactory}.
 * This one hands back the Spring bean when the class is one (the leaf commands, which carry the
 * injected import services) and falls back to picocli's default factory for everything else
 * (command groups, mixins, picocli's own helpers). The whole reason no
 * {@code picocli-spring-boot-starter} is on the classpath (DECISIONS 2026-09-12 D13).
 */
final class SpringPicocliFactory implements CommandLine.IFactory {

    private final ApplicationContext context;
    private final CommandLine.IFactory fallback = CommandLine.defaultFactory();

    SpringPicocliFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        try {
            return context.getBean(cls);
        } catch (NoSuchBeanDefinitionException notABean) {
            return fallback.create(cls);
        }
    }
}
