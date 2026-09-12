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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D5 live smoke (PLAN D5 ✅ "one live call logged with token counts"; TECH_PLAN §4.1 last
 * paragraph, §13.2 item 1). Founder-run only: {@code BEDROCK_LIVE=1} plus AWS credentials in the
 * SDK's default chain — the IAM Identity Center profile F8 created (TECH_PLAN §7.4), after
 * {@code aws sso login --profile margai}; otherwise
 * the test is skipped and the build never touches AWS. Two calls on the {@code cheap} tier prove
 * that the forced tool returns the record, that the ledger gets real token counts, and that the
 * second call reads the prompt cache the first one wrote.
 *
 * <pre>
 * cd server && AWS_PROFILE=margai BEDROCK_LIVE=1 ./mvnw test -Dtest=BedrockSmokeTest -Dsurefire.failIfNoSpecifiedTests=false
 * </pre>
 */
@SpringBootTest
@ActiveProfiles("bedrock")
@Import(TestcontainersConfiguration.class)
@EnabledIfEnvironmentVariable(named = "BEDROCK_LIVE", matches = "1")
class BedrockSmokeTest {

    @Autowired
    private SmokeTask smoke;

    @Autowired
    private AiClientInfo info;

    @Autowired
    private AiProperties properties;

    @Autowired
    private AiCallRepository calls;

    @Test
    void twoLiveCallsProveForcedToolUseTokenCountsAndPromptCaching() {
        assertThat(info.isLive()).as("inner client under the bedrock profile").isTrue();
        String requestId = "smoke-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> first = smoke.run(7, AiCallContext.system(requestId));
        AiResponse<SmokeAnswer> second = smoke.run(8, AiCallContext.system(requestId));

        assertThat(first.output().number()).isEqualTo(7);
        assertThat(second.output().number()).isEqualTo(8);
        assertThat(first.output().greeting()).isNotBlank();
        assertThat(first.modelId()).isEqualTo(properties.tier().cheap());

        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        System.out.println("=== D5 live smoke: ai_calls rows ===");
        rows.forEach(row -> System.out.println(describe(row)));

        assertThat(rows).hasSize(2);
        for (AiCall row : rows) {
            assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
            assertThat(row.getFeature()).isEqualTo(AiFeature.smoke);
            assertThat(row.getTier()).isEqualTo(Tier.cheap);
            assertThat(row.getModelId()).isEqualTo(properties.tier().cheap());
            assertThat(row.getPromptName()).isEqualTo("smoke");
            assertThat(row.getPromptVersion()).isEqualTo((short) 1);
            assertThat(row.getInputTokens()).as("input tokens").isPositive();
            assertThat(row.getOutputTokens()).as("output tokens").isPositive();
            assertThat(row.getCostPaise()).as("cost").isPositive();
            assertThat(row.getLatencyMs()).isPositive();
        }
        AiCall firstRow = rows.get(0);
        AiCall secondRow = rows.get(1);
        assertThat(firstRow.getCacheWriteTokens() + firstRow.getCacheReadTokens())
                .as("the system prefix was cached (written, or read if a run within the TTL preceded this one)")
                .isPositive();
        assertThat(secondRow.getCacheReadTokens())
                .as("second call read the prompt cache (§13.2 item 1)")
                .isPositive();
    }

    private static String describe(AiCall row) {
        return String.format("ai_calls %s | %s | %s | %s v%d | status=%s | in=%d out=%d cache_read=%d cache_write=%d"
                + " | %d ms | %d paise | request_id=%s",
                row.getId(), row.getFeature(), row.getModelId(), row.getPromptName(), row.getPromptVersion(),
                row.getStatus(), row.getInputTokens(), row.getOutputTokens(), row.getCacheReadTokens(),
                row.getCacheWriteTokens(), row.getLatencyMs(), row.getCostPaise(), row.getRequestId());
    }
}
