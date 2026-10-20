package com.margai.ai;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiCall;
import com.margai.ai.internal.AiCallRepository;
import com.margai.ai.internal.AiCallStatus;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.tasks.SmokeAnswer;
import com.margai.ai.tasks.SmokeTask;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * The completion fallback's smoke (CS-2 §4.11, TECH_PLAN §15.12): with `margai.flags.ai-fallback` on,
 * one call per runtime tier — CHEAP and REASON — through Bedrock on the fallback's inference profiles
 * (founder ruling, DECISIONS 2026-10-20). The fallback is called ready only when this passes, since
 * CS-2's "access now restored" cannot be checked from a Claude session.
 *
 * <p>Founder-run only, behind the single switch of every live run, {@code AI_LIVE=1}, and a system
 * property naming this test, so neither can unlock spend alone. It needs AWS credentials in the SDK's
 * default chain — the IAM Identity Center profile F8 created, after {@code aws sso login --profile
 * margai} — and no Anthropic key: under the flag the Anthropic configuration does not wire.
 *
 * <pre>
 * cd server && unset AWS_BEARER_TOKEN_BEDROCK && AWS_PROFILE=margai AI_LIVE=1 \
 *   ./mvnw test -Dtest=BedrockFallbackSmokeTest -Dbedrock.fallback.smoke=true -Dsurefire.failIfNoSpecifiedTests=false
 * </pre>
 */
@SpringBootTest(properties = "margai.flags.ai-fallback=true")
@ActiveProfiles("live")
@Import(TestcontainersConfiguration.class)
@EnabledIfEnvironmentVariable(named = "AI_LIVE", matches = "1")
@EnabledIfSystemProperty(named = "bedrock.fallback.smoke", matches = "true")
class BedrockFallbackSmokeTest {

    @Autowired
    private SmokeTask smoke;

    @Autowired
    private AiClientInfo info;

    @Autowired
    private AiProperties properties;

    @Autowired
    private AiCallRepository calls;

    @Test
    void theInnerClientIsBedrockAsTheFallback() {
        assertThat(info.inner()).isEqualTo("bedrock (fallback)");
    }

    @Test
    void theCheapTierAnswersThroughTheFallback() {
        String requestId = "fallback-smoke-cheap-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> answer = smoke.run(7, AiCallContext.system(requestId));

        assertThat(answer.output().number()).isEqualTo(7);
        assertOkRow(report(requestId), Tier.cheap);
    }

    /** The tier whose request shape the dormant client got wrong (temperature, forced tool, no thinking setting). */
    @Test
    void theReasonTierAnswersThroughTheFallbackOnItsOwnRequestShape() {
        String requestId = "fallback-smoke-reason-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> answer = smoke.runOnReasonTier(9, AiCallContext.system(requestId));

        assertThat(answer.output().number()).isEqualTo(9);
        assertOkRow(report(requestId), Tier.reason);
    }

    private void assertOkRow(List<AiCall> rows, Tier tier) {
        assertThat(rows).hasSize(1);
        AiCall row = rows.getFirst();
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
        assertThat(row.getFeature()).isEqualTo(AiFeature.smoke);
        assertThat(row.getTier()).isEqualTo(tier);
        assertThat(row.getModelId()).isEqualTo(properties.fallbackModelFor(tier));
        assertThat(row.getInputTokens()).as("input tokens").isPositive();
        assertThat(row.getOutputTokens()).as("output tokens").isPositive();
        assertThat(row.getCacheWriteTokens() + row.getCacheReadTokens())
                .as("the system prefix was cached through the Converse cache point (written, or read within the TTL)")
                .isPositive();
        assertThat(row.getCostPaise()).as("cost").isPositive();
    }

    private List<AiCall> report(String requestId) {
        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        System.out.println("=== fallback smoke: ai_calls rows for " + requestId + " ===");
        rows.forEach(row -> System.out.println(String.format(
                "ai_calls %s | %s | %s | %s | status=%s | in=%d out=%d cache_read=%d cache_write=%d | %d ms | %d paise",
                row.getId(), row.getFeature(), row.getModelId(), row.getTier(), row.getStatus(), row.getInputTokens(),
                row.getOutputTokens(), row.getCacheReadTokens(), row.getCacheWriteTokens(), row.getLatencyMs(),
                row.getCostPaise())));
        return rows;
    }
}
