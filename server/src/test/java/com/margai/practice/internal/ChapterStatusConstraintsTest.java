package com.margai.practice.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.practice.api.CoverageStatus;
import com.margai.practice.api.StatusSource;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Repository slice (TECH_PLAN §8.1): migration V4 applies and its constraints hold — one row
 * per {@code (user, node)} and {@code ON DELETE RESTRICT} towards {@code users}. The user and
 * node rows are inserted over JDBC: their entities belong to other modules (§1.3), and this
 * module only ever holds their ids.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class ChapterStatusConstraintsTest {

    @Autowired
    private ChapterStatusRepository statuses;

    @Autowired
    private JdbcTemplate jdbc;

    private UUID userId;
    private UUID nodeId;

    @BeforeEach
    void insertOwnerRows() {
        userId = jdbc.queryForObject(
                "INSERT INTO users (phone) VALUES ('+919876543299') RETURNING id", UUID.class);
        nodeId = jdbc.queryForObject(
                "INSERT INTO syllabus_nodes (code, subject, kind, name_en, sort_order)"
                        + " VALUES ('PHY', 'physics', 'subject', 'Physics', 1) RETURNING id", UUID.class);
    }

    @Test
    void newRowStartsUntouchedAndNotWeak() {
        ChapterStatus status = statuses.saveAndFlush(new ChapterStatus(userId, nodeId, StatusSource.self_report));

        assertThat(status.getStatus()).isEqualTo(CoverageStatus.untouched);
        assertThat(status.isFeelsWeak()).isFalse();
        assertThat(status.getAbilityEstimate()).isNull();
    }

    @Test
    void oneRowPerUserAndNode() {
        statuses.saveAndFlush(new ChapterStatus(userId, nodeId, StatusSource.self_report));

        assertThatThrownBy(() -> statuses.saveAndFlush(new ChapterStatus(userId, nodeId, StatusSource.inferred)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("chapter_status_user_id_node_id_key");
    }

    @Test
    void userRowCannotBeDeletedWhileAStatusReferencesIt() {
        statuses.saveAndFlush(new ChapterStatus(userId, nodeId, StatusSource.self_report));

        assertThatThrownBy(() -> jdbc.update("DELETE FROM users WHERE id = ?", userId))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("chapter_status_user_id_fkey");
    }
}
