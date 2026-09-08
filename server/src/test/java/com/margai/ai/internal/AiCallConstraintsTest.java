package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.Tier;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Repository slice (TECH_PLAN §8.1): migration V5 applies and {@code ai_calls} is the ledger of
 * §2.8 — CHECK lists on feature, tier and status, a mandatory cost, the two lookup indexes, a
 * nullable user reference with {@code ON DELETE RESTRICT}, and no {@code updated_at} (§2.1).
 * Invalid enumeration values are inserted over JDBC because the entity's enums cannot express them.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class AiCallConstraintsTest {

    private static final String INSERT = "INSERT INTO ai_calls (feature, model_id, tier, status, cost_paise)"
            + " VALUES (?, 'model-x', ?, ?, 0)";

    @Autowired
    private AiCallRepository calls;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void rowGetsIdAndTimestampAndKeepsEveryColumn() {
        AiCall saved = calls.saveAndFlush(new AiCall(null, AiFeature.smoke, "model-x", Tier.cheap, "req-1")
                .prompt("smoke", (short) 1)
                .tokens(120, 8, 100, 0)
                .latencyMs(350)
                .costPaise(3));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo(AiCallStatus.ok);
        assertThat(saved.isBatch()).isFalse();
        assertThat(saved.getUserId()).isNull();
        assertThat(calls.findByRequestIdOrderByCreatedAt("req-1")).extracting(AiCall::getCacheReadTokens)
                .containsExactly(100);
    }

    // One violation per test: after the first, Postgres only reports "current transaction is aborted".

    @Test
    void featureIsACheckedList() {
        assertThatThrownBy(() -> jdbc.update(INSERT, "bogus", "cheap", "ok"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ai_calls_feature_check");
    }

    @Test
    void tierIsACheckedList() {
        assertThatThrownBy(() -> jdbc.update(INSERT, "smoke", "premium", "ok"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ai_calls_tier_check");
    }

    @Test
    void statusIsACheckedList() {
        assertThatThrownBy(() -> jdbc.update(INSERT, "smoke", "cheap", "unknown"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ai_calls_status_check");
    }

    @Test
    void costIsMandatory() {
        assertThatThrownBy(() -> jdbc.update(
                "INSERT INTO ai_calls (feature, model_id, tier, status) VALUES ('smoke', 'model-x', 'cheap', 'ok')"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("cost_paise");
    }

    @Test
    void userMustExistWhenGiven() {
        assertThatThrownBy(() -> calls.saveAndFlush(
                new AiCall(UUID.randomUUID(), AiFeature.doubt, "model-x", Tier.cheap, null).costPaise(0)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ai_calls_user_id_fkey");
    }

    @Test
    void userRowCannotBeDeletedWhileALedgerRowReferencesIt() {
        UUID userId = jdbc.queryForObject("INSERT INTO users (phone) VALUES ('+919876543298') RETURNING id", UUID.class);
        calls.saveAndFlush(new AiCall(userId, AiFeature.doubt, "model-x", Tier.cheap, null).costPaise(0));

        assertThatThrownBy(() -> jdbc.update("DELETE FROM users WHERE id = ?", userId))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ai_calls_user_id_fkey");
    }

    @Test
    void tableIsAppendOnlyAndIndexedForTheTwoLookups() {
        List<String> columns = jdbc.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_name = 'ai_calls'", String.class);
        assertThat(columns).contains("created_at").doesNotContain("updated_at");

        List<String> indexes = jdbc.queryForList(
                "SELECT indexname FROM pg_indexes WHERE tablename = 'ai_calls'", String.class);
        assertThat(indexes).contains("ai_calls_feature_created_at_idx", "ai_calls_user_id_created_at_idx");
    }
}
