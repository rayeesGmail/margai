package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

/**
 * TECH_PLAN §1.2, §4.1 and CLAUDE.md's grounding rule: under the {@code live} profile the fake must
 * never stand in for a provider. Both provider configurations are conditional on
 * {@code margai.ai.provider}, so before this guard a value matching neither — a typo in the
 * deployed parameter — left the chain wrapping {@link FakeAiClient} and started the application
 * normally, serving fixtures as answers with one log line as the only sign (spec-auditor,
 * 2026-09-12).
 */
class LiveProfileRefusalTest {

    private static final InnerAiClient PROVIDER = new InnerAiClient("a-provider", new StubInner());

    @Test
    void theLiveProfileWithoutAProviderClientRefusesToStart() {
        assertThatThrownBy(() -> AiClientConfiguration.requireProviderInLiveProfile(
                null, withProfiles(AiClientConfiguration.LIVE_PROFILE), TestAiProperties.standard()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("live")
                .hasMessageContaining("anthropic");
    }

    @Test
    void theLiveProfileWithAProviderClientIsFine() {
        assertThatCode(() -> AiClientConfiguration.requireProviderInLiveProfile(
                PROVIDER, withProfiles(AiClientConfiguration.LIVE_PROFILE), TestAiProperties.standard()))
                .doesNotThrowAnyException();
    }

    /** Every other profile is allowed — and expected — to run on the fake (DEV_SPEC §13.7 item 6). */
    @Test
    void anyOtherProfileFallsBackToTheFakeAsDesigned() {
        assertThatCode(() -> AiClientConfiguration.requireProviderInLiveProfile(
                null, withProfiles("local"), TestAiProperties.standard())).doesNotThrowAnyException();
        assertThatCode(() -> AiClientConfiguration.requireProviderInLiveProfile(
                null, new MockEnvironment(), TestAiProperties.standard())).doesNotThrowAnyException();
        assertThat(AiClientConfiguration.CHAIN).containsExactly("ledger", "breaker", "tier-policy", "schema", "retry");
    }

    private static MockEnvironment withProfiles(String... profiles) {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles(profiles);
        return environment;
    }

    /** A provider client the guard only has to see, never call. */
    private static final class StubInner implements com.margai.ai.api.AiClient {

        @Override
        public <T> com.margai.ai.api.AiResponse<T> complete(com.margai.ai.api.AiRequest<T> request) {
            throw new UnsupportedOperationException();
        }

        @Override
        public com.margai.ai.api.AiResponse<float[]> embed(com.margai.ai.api.EmbedRequest request) {
            throw new UnsupportedOperationException();
        }
    }
}
