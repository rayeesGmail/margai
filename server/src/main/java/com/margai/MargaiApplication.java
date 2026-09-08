package com.margai;

import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** MARG AI API: one deployable modular monolith (DEV_SPEC §2). */
@SpringBootApplication
public class MargaiApplication {

    /** The environment variable a human sets to swap FakeAiClient for Bedrock (DEV_SPEC §13.7 item 6). */
    static final String BEDROCK_LIVE = "BEDROCK_LIVE";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MargaiApplication.class);
        app.setAdditionalProfiles(additionalProfiles(System.getenv()).toArray(String[]::new));
        app.run(args);
    }

    /**
     * TECH_PLAN §1.2: {@code BEDROCK_LIVE=1} adds the {@code bedrock} profile on top of whatever
     * {@code spring.profiles.active} says; the ECS task definition sets the profile directly.
     */
    static List<String> additionalProfiles(Map<String, String> environment) {
        return "1".equals(environment.get(BEDROCK_LIVE)) ? List.of("bedrock") : List.of();
    }
}
