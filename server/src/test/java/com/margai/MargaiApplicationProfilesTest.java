package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

/**
 * TECH_PLAN §1.2, DEV_SPEC §13.7 item 6: only {@code BEDROCK_LIVE=1} adds the bedrock profile;
 * TECH_PLAN §6.1: only the pipeline profile makes {@code main} exit after the run (D13).
 */
class MargaiApplicationProfilesTest {

    @Test
    void onlyThePipelineProfileExitsAfterTheRun() {
        assertThat(MargaiApplication.exitsAfterRun(new MockEnvironment())).isFalse();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("local"))).isFalse();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("pipeline"))).isTrue();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("pipeline", "bedrock"))).isTrue();
    }

    private static MockEnvironment withProfiles(String... profiles) {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles(profiles);
        return environment;
    }

    @Test
    void bedrockLiveOneAddsTheBedrockProfile() {
        assertThat(MargaiApplication.additionalProfiles(Map.of("BEDROCK_LIVE", "1"))).containsExactly("bedrock");
    }

    @Test
    void anythingElseAddsNothing() {
        assertThat(MargaiApplication.additionalProfiles(Map.of())).isEmpty();
        assertThat(MargaiApplication.additionalProfiles(Map.of("BEDROCK_LIVE", "0"))).isEmpty();
        assertThat(MargaiApplication.additionalProfiles(Map.of("BEDROCK_LIVE", "true"))).isEmpty();
    }
}
