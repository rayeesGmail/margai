package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

/**
 * The {@code pipeline} profile boots (TECH_PLAN §1.2, §6.1): the production Flyway locations
 * against its own database in the shared container, no web server, the runner in the context,
 * and the command line — here {@code --help} — executed at startup with its exit code recorded for
 * {@code MargaiApplication.main} to return.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, args = "--help")
@ActiveProfiles("pipeline")
@Import(TestcontainersConfiguration.class)
class PipelineProfileTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment environment;

    @Autowired
    private PipelineRunner runner;

    @Test
    void theProfileRunsWithoutAWebServer() {
        assertThat(environment.getProperty("spring.main.web-application-type")).isEqualTo("none");
        assertThat(context.getBeanNamesForType(PipelineRunner.class)).hasSize(1);
    }

    @Test
    void theCommandLineRanAtStartupAndItsExitCodeIsRecorded() {
        assertThat(runner.getExitCode()).isZero();
    }
}
