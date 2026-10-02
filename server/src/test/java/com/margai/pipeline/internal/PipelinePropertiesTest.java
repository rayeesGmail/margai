package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.BookLanguage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Each edition's transcriber as the shipped configuration binds it (DECISIONS 2026-10-02): English stays on
 * Claude Opus 5 under every profile, the Hindi transcriber's profile included, and Hindi is cut on Opus 5.5.
 */
class PipelinePropertiesTest {

    @Configuration
    @EnableConfigurationProperties(PipelineProperties.class)
    static class Bind {
    }

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer())
            .withConfiguration(UserConfigurations.of(Bind.class));

    @Test
    void eachEditionHasItsTranscriberAndTheHindiProfileLeavesEnglishAlone() {
        for (String profiles : new String[] {"pipeline", "pipeline,visionopus55"}) {
            runner.withPropertyValues("spring.profiles.active=" + profiles).run(context -> {
                PipelineProperties properties = context.getBean(PipelineProperties.class);
                assertThat(properties.transcribeModel(BookLanguage.en)).as(profiles).isEqualTo("claude-opus-5");
                assertThat(properties.transcribeModel(BookLanguage.hi)).as(profiles).isEqualTo("claude-opus-5-5");
            });
        }
    }

    @Test
    void aBlankHindiTranscriberMeansTheEnglishOne() {
        PipelineProperties properties = new PipelineProperties(72, 10, 1, "claude-sonnet-5", "claude-opus-5", " ", 100, 0, 40);

        assertThat(properties.transcribeModel(BookLanguage.hi)).isEqualTo("claude-opus-5");
    }
}
