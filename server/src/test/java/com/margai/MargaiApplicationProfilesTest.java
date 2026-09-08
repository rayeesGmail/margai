package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §1.2, DEV_SPEC §13.7 item 6: only {@code BEDROCK_LIVE=1} adds the bedrock profile. */
class MargaiApplicationProfilesTest {

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
