package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Repair;
import com.margai.ai.api.Tier;
import com.margai.ai.tasks.SmokeAnswer;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TECH_PLAN §4.1 "FakeAiClient": fixtures by case or by a deterministic hash, realistic usage
 * with a simulated prompt cache, the configured model id on every row, and a deterministic
 * embedding — so the ledger and the breaker behave in every profile (DECISIONS D5).
 */
class FakeAiClientTest {

    private static final AiCallContext CTX = AiCallContext.system("req-1");

    private final AiProperties properties = TestAiProperties.standard();

    private final PromptRegistry prompts = PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of());

    private final FakeAiClient fake = new FakeAiClient(properties, prompts, new StructuredOutput(),
            new PathMatchingResourcePatternResolver());

    private AiRequest<SmokeAnswer> smoke(Map<String, Object> variables) {
        return AiRequest.of(AiFeature.smoke, Tier.cheap, PromptRef.named("smoke"), variables, SmokeAnswer.class, CTX);
    }

    @Test
    void returnsTheFixtureTypedWithRealisticUsageAndTheConfiguredModel() {
        AiResponse<SmokeAnswer> first = fake.complete(smoke(Map.of("number", 7, "fixture_case", "default")));

        assertThat(first.output()).isEqualTo(new SmokeAnswer("ok", 7));
        assertThat(first.modelId()).isEqualTo("cheap-model");
        assertThat(first.aiCallId()).isNull();
        assertThat(first.latency()).isNotNull();
        int systemTokens = FakeAiClient.tokens(prompts.render(PromptRef.named("smoke"), Map.of("number", 7)).system());
        assertThat(first.usage().inputTokens()).isPositive().isLessThan(100);
        assertThat(first.usage().outputTokens()).isPositive();
        assertThat(first.usage().cacheWriteTokens()).isEqualTo(systemTokens).isGreaterThan(4_096);
        assertThat(first.usage().cacheReadTokens()).isZero();

        AiResponse<SmokeAnswer> second = fake.complete(smoke(Map.of("number", 8, "fixture_case", "default")));

        assertThat(second.usage().cacheReadTokens()).isEqualTo(systemTokens);
        assertThat(second.usage().cacheWriteTokens()).isZero();
    }

    @Test
    void explicitCaseWinsAndUnknownCaseFailsLoudly() {
        assertThat(fake.complete(smoke(Map.of("number", 1, "fixture_case", "alt"))).output().greeting())
                .isEqualTo("namaste");
        assertThatThrownBy(() -> fake.complete(smoke(Map.of("number", 1, "fixture_case", "nope"))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("smoke.nope.json")
                .hasMessageContaining("default");
    }

    @Test
    void hashChoiceIsDeterministicSpreadsOverPublicCasesAndNeverPicksAFailureCase() {
        assertThat(fake.cases("smoke")).containsExactly("alt", "default");
        Set<String> greetings = new HashSet<>();
        for (int number = 0; number < 40; number++) {
            SmokeAnswer once = fake.complete(smoke(Map.of("number", number))).output();
            SmokeAnswer again = fake.complete(smoke(Map.of("number", number))).output();
            assertThat(again).isEqualTo(once);
            greetings.add(once.greeting());
        }
        assertThat(greetings).containsExactlyInAnyOrder("ok", "namaste");
    }

    @Test
    void failureCaseIsInvalidOutputWithUsageAndTheRepairedFixtureFixesIt() {
        AiRequest<SmokeAnswer> invalid = smoke(Map.of("number", 1, "fixture_case", "_invalid"));

        assertThatThrownBy(() -> fake.complete(invalid))
                .isInstanceOf(InvalidOutputException.class)
                .satisfies(e -> {
                    InvalidOutputException failure = (InvalidOutputException) e;
                    assertThat(failure.errors()).anySatisfy(m -> assertThat(m).contains("number"));
                    assertThat(failure.errors()).anySatisfy(m -> assertThat(m).contains("extra"));
                    assertThat(failure.usage().outputTokens()).isPositive();
                    assertThat(failure.modelId()).isEqualTo("cheap-model");
                });

        AiResponse<SmokeAnswer> repaired = fake.complete(invalid.withRepair(new Repair("{}", List.of("number: required"))));

        assertThat(repaired.output()).isEqualTo(new SmokeAnswer("ok", 42));
    }

    @Test
    void promptWithoutAnyFixtureFailsWithInstructions() {
        AiRequest<SmokeAnswer> echo = AiRequest.of(AiFeature.eval, Tier.cheap, PromptRef.named("echo"),
                Map.of("text", "hi"), SmokeAnswer.class, CTX);

        assertThatThrownBy(() -> fake.complete(echo))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ai-fixtures/echo.")
                .hasMessageContaining("src/test/resources");
    }

    @Test
    void imagesCostInputTokensAndVisionUsesTheVisionModel() {
        AiRequest<SmokeAnswer> vision = new AiRequest<>(AiFeature.doubt_extract, Tier.vision, null,
                PromptRef.named("smoke"), Map.of("number", 3, "fixture_case", "default"),
                List.of(new ImagePart(new byte[] {1, 2, 3}, "image/jpeg")), SmokeAnswer.class, CTX, null);

        AiResponse<SmokeAnswer> response = fake.complete(vision);

        assertThat(response.modelId()).isEqualTo("vision-model");
        assertThat(response.usage().inputTokens()).isGreaterThan(FakeAiClient.IMAGE_TOKENS);
    }

    @Test
    void embeddingIsAUnitVectorOf1024DeterministicPerText() {
        EmbedRequest request = new EmbedRequest(AiFeature.embed, "Newton's second law",
                EmbedRequest.InputType.search_query, CTX);

        AiResponse<float[]> once = fake.embed(request);
        AiResponse<float[]> again = fake.embed(request);
        AiResponse<float[]> other = fake.embed(new EmbedRequest(AiFeature.embed, "Le Chatelier's principle",
                EmbedRequest.InputType.search_document, CTX));

        assertThat(once.output()).hasSize(properties.embed().dimensions()).containsExactly(again.output());
        assertThat(once.output()).isNotEqualTo(other.output());
        double norm = 0;
        for (float component : once.output()) {
            norm += component * component;
        }
        assertThat(Math.sqrt(norm)).isCloseTo(1.0, within(1e-3));
        assertThat(once.modelId()).isEqualTo("embed-model");
        assertThat(once.usage().inputTokens()).isEqualTo("Newton's second law".length() / 4);
    }
}
