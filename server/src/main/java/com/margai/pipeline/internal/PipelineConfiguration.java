package com.margai.pipeline.internal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** The pipeline module's wiring: its knobs, under the profile that runs the §6 commands. */
@Configuration(proxyBeanMethods = false)
@Profile("pipeline")
@EnableConfigurationProperties(PipelineProperties.class)
class PipelineConfiguration {
}
