package com.margai.ai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiBudgetExceededException;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Tier;
import com.margai.ai.api.TierPolicyException;
import com.margai.ai.internal.AiCall;
import com.margai.ai.internal.AiCallRepository;
import com.margai.ai.internal.AiCallStatus;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.tasks.SmokeAnswer;
import com.margai.ai.tasks.SmokeTask;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Module-flow test (TECH_PLAN §8.1, PLAN D5 ✅ "app runs fully on FakeAiClient"): the wired
 * chain over the fake writes a ledger row per call for success, breaker and policy failures,
 * the row survives a rolled-back caller transaction (§4.8), and the chain is what §4.1 lists.
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class AiSeamFlowTest {

    @Autowired
    private SmokeTask smoke;

    @Autowired
    private AiClient ai;

    @Autowired
    private AiClientInfo info;

    @Autowired
    private AiProperties properties;

    @Autowired
    private AiCallRepository calls;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PlatformTransactionManager transactions;

    @Test
    void theWiredClientIsTheFullChainOverTheFake() {
        assertThat(info.inner()).isEqualTo("fake");
        assertThat(info.isLive()).isFalse();
        assertThat(info.chain()).containsExactly("ledger", "breaker", "tier-policy", "schema", "retry");
    }

    @Test
    void aSmokeCallOnTheFakeLeavesOneOkLedgerRow() {
        String requestId = "flow-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> response = smoke.run(7, AiCallContext.system(requestId));

        assertThat(response.output().greeting()).isNotBlank();
        assertThat(response.modelId()).isEqualTo(properties.tier().cheap());
        assertThat(response.aiCallId()).isNotNull();
        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        assertThat(rows).hasSize(1);
        AiCall row = rows.get(0);
        assertThat(row.getId()).isEqualTo(response.aiCallId());
        assertThat(row.getFeature()).isEqualTo(AiFeature.smoke);
        assertThat(row.getTier()).isEqualTo(Tier.cheap);
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
        assertThat(row.getPromptName()).isEqualTo("smoke");
        assertThat(row.getPromptVersion()).isEqualTo((short) 1);
        assertThat(row.getInputTokens()).isPositive();
        assertThat(row.getOutputTokens()).isPositive();
        assertThat(row.getCacheWriteTokens() + row.getCacheReadTokens()).isGreaterThan(4_096);
        assertThat(row.getCostPaise()).isPositive();
        assertThat(row.getUserId()).isNull();
    }

    @Test
    void theLedgerRowSurvivesARolledBackCallerTransaction() {
        String requestId = "rollback-" + UUID.randomUUID();
        TransactionTemplate template = new TransactionTemplate(transactions);

        template.executeWithoutResult(status -> {
            smoke.run(1, AiCallContext.system(requestId));
            status.setRollbackOnly();
        });

        assertThat(calls.findByRequestIdOrderByCreatedAt(requestId)).hasSize(1);
    }

    @Test
    void aUserAtTheDailyCapGetsABreakerRowAndNoModelCall() {
        UUID userId = jdbc.queryForObject("INSERT INTO users (phone) VALUES ('+919876543250') RETURNING id", UUID.class);
        jdbc.update("INSERT INTO ai_calls (user_id, feature, model_id, tier, status, cost_paise)"
                + " VALUES (?, 'doubt', ?, 'cheap', 'ok', ?)", userId, properties.tier().cheap(),
                properties.budget().userDailyPaise());
        String requestId = "breaker-" + UUID.randomUUID();

        assertThatThrownBy(() -> smoke.run(2, AiCallContext.forUser(userId, requestId)))
                .isInstanceOf(AiBudgetExceededException.class);

        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0).getStatus()).isEqualTo(AiCallStatus.breaker);
        assertThat(rows.get(0).getErrorCode()).isEqualTo("user");
        assertThat(rows.get(0).getUserId()).isEqualTo(userId);
        assertThat(rows.get(0).getCostPaise()).isZero();
    }

    @Test
    void aReasonRequestWithoutARouteIsRefusedAndRecorded() {
        String requestId = "policy-" + UUID.randomUUID();

        assertThatThrownBy(() -> ai.complete(AiRequest.of(AiFeature.doubt_verify, Tier.reason, PromptRef.named("smoke"),
                Map.of("number", 3), SmokeAnswer.class, AiCallContext.system(requestId))))
                .isInstanceOf(TierPolicyException.class);

        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0).getStatus()).isEqualTo(AiCallStatus.error);
        assertThat(rows.get(0).getErrorCode()).isEqualTo("TierPolicyException");
        assertThat(rows.get(0).getModelId()).isEqualTo(properties.tier().reason());
    }
}
