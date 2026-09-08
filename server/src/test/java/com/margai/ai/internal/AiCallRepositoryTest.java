package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.IstClock;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TECH_PLAN §4.8, §11.1: the breaker's sums are per IST day. With the clock at 00:01 IST on
 * 7 September, a row stamped 23:59 IST on 6 September (18:29 UTC) is yesterday's spend.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class AiCallRepositoryTest {

    private static final Instant YESTERDAY_2359_IST = Instant.parse("2026-09-06T18:29:00Z");
    private static final Instant TODAY_0000_IST = Instant.parse("2026-09-06T18:30:00Z");
    private static final Instant TODAY_0001_IST = Instant.parse("2026-09-06T18:31:00Z");

    @Autowired
    private AiCallRepository calls;

    @Autowired
    private JdbcTemplate jdbc;

    private void insert(UUID userId, long costPaise, Instant createdAt) {
        jdbc.update("INSERT INTO ai_calls (user_id, feature, model_id, tier, status, cost_paise, created_at)"
                + " VALUES (?, 'doubt', 'm', 'cheap', 'ok', ?, ?)", userId, costPaise, java.sql.Timestamp.from(createdAt));
    }

    @Test
    void spendSumsOnlyRowsFromTheCurrentIstDay() {
        IstClock clock = new IstClock(Clock.fixed(TODAY_0001_IST, IstClock.IST));
        AiCallLedger ledger = new AiCallLedger(calls, clock);
        // The test database is shared by every context in the JVM and @SpringBootTest classes commit
        // ledger rows, so the global sum is asserted as a delta; user sums use fresh users.
        long globalBefore = ledger.globalSpendToday();

        UUID alice = jdbc.queryForObject("INSERT INTO users (phone) VALUES ('+919876543201') RETURNING id", UUID.class);
        UUID bob = jdbc.queryForObject("INSERT INTO users (phone) VALUES ('+919876543202') RETURNING id", UUID.class);
        insert(alice, 1_000, YESTERDAY_2359_IST);
        insert(alice, 300, TODAY_0000_IST);
        insert(alice, 200, TODAY_0001_IST);
        insert(bob, 700, TODAY_0001_IST);
        insert(null, 50, TODAY_0001_IST);

        assertThat(ledger.userSpendToday(alice)).isEqualTo(500);
        assertThat(ledger.userSpendToday(bob)).isEqualTo(700);
        assertThat(ledger.userSpendToday(UUID.randomUUID())).isZero();
        assertThat(ledger.globalSpendToday() - globalBefore).isEqualTo(1_250);

        // Seen from 23:59 IST on 6 September the day began at 2026-09-05T18:30Z: every row counts
        // (the sum has no upper bound — at runtime no row can be stamped in the future).
        IstClock yesterday = new IstClock(Clock.fixed(YESTERDAY_2359_IST, IstClock.IST));
        assertThat(new AiCallLedger(calls, yesterday).userSpendToday(alice)).isEqualTo(1_500);
    }

    @Test
    void recordReturnsTheRowId() {
        AiCallLedger ledger = new AiCallLedger(calls, IstClock.system());

        UUID id = ledger.record(new AiCall(null, com.margai.ai.api.AiFeature.smoke, "m", com.margai.ai.api.Tier.cheap, "r")
                .costPaise(3));

        assertThat(calls.findById(id)).isPresent();
    }
}
