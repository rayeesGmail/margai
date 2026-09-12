package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

/**
 * TECH_PLAN §1.2, DEV_SPEC §13.7 item 6: only {@code AI_LIVE=1} adds the live profile — which
 * provider it wires is configuration, so the switch outlives a provider change (DECISIONS
 * 2026-09-12); TECH_PLAN §6.1: only the pipeline profile makes {@code main} exit after the run
 * (D13).
 */
class MargaiApplicationProfilesTest {

    @Test
    void onlyThePipelineProfileExitsAfterTheRun() {
        assertThat(MargaiApplication.exitsAfterRun(new MockEnvironment())).isFalse();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("local"))).isFalse();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("pipeline"))).isTrue();
        assertThat(MargaiApplication.exitsAfterRun(withProfiles("pipeline", MargaiApplication.LIVE_PROFILE))).isTrue();
    }

    private static MockEnvironment withProfiles(String... profiles) {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles(profiles);
        return environment;
    }

    @Test
    void aiLiveOneAddsTheLiveProfile() {
        assertThat(MargaiApplication.additionalProfiles(Map.of(MargaiApplication.AI_LIVE, "1")))
                .containsExactly(MargaiApplication.LIVE_PROFILE);
    }

    @Test
    void anythingElseAddsNothing() {
        assertThat(MargaiApplication.additionalProfiles(Map.of())).isEmpty();
        assertThat(MargaiApplication.additionalProfiles(Map.of(MargaiApplication.AI_LIVE, "0"))).isEmpty();
        assertThat(MargaiApplication.additionalProfiles(Map.of(MargaiApplication.AI_LIVE, "true"))).isEmpty();
        assertThat(MargaiApplication.additionalProfiles(Map.of("BEDROCK_LIVE", "1"))).isEmpty();
    }
}
