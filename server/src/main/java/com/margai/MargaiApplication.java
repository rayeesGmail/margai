package com.margai;

import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/** MARG AI API: one deployable modular monolith (DEV_SPEC §2). */
@SpringBootApplication
public class MargaiApplication {

    /** The environment variable a human sets to swap FakeAiClient for Bedrock (DEV_SPEC §13.7 item 6). */
    static final String BEDROCK_LIVE = "BEDROCK_LIVE";

    /** The profile that runs one content command and exits (TECH_PLAN §1.2, §6.1). */
    static final String PIPELINE_PROFILE = "pipeline";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MargaiApplication.class);
        app.setAdditionalProfiles(additionalProfiles(System.getenv()).toArray(String[]::new));
        ConfigurableApplicationContext context = app.run(args);
        if (exitsAfterRun(context.getEnvironment())) {
            System.exit(SpringApplication.exit(context));
        }
    }

    /**
     * The api and nightly profiles serve until stopped; the pipeline profile is a command-line run
     * whose exit code is the command's (the {@code ExitCodeGenerator} in the pipeline module).
     */
    static boolean exitsAfterRun(Environment environment) {
        return environment.matchesProfiles(PIPELINE_PROFILE);
    }

    /**
     * TECH_PLAN §1.2: {@code BEDROCK_LIVE=1} adds the {@code bedrock} profile on top of whatever
     * {@code spring.profiles.active} says; the ECS task definition sets the profile directly.
     */
    static List<String> additionalProfiles(Map<String, String> environment) {
        return "1".equals(environment.get(BEDROCK_LIVE)) ? List.of("bedrock") : List.of();
    }
}
