package com.margai.ai.internal;

import com.margai.common.api.IstClock;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code ai_calls} writer and the breaker's spend source (TECH_PLAN §4.8). A row is written
 * in its own transaction ({@code REQUIRES_NEW}) so a feature transaction that rolls back still
 * leaves the cost on record; "today" is the IST day of {@link IstClock} (§11.1).
 */
@Component
public class AiCallLedger implements DailySpend {

    private final AiCallRepository calls;
    private final IstClock clock;

    public AiCallLedger(AiCallRepository calls, IstClock clock) {
        this.calls = calls;
        this.clock = clock;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID record(AiCall row) {
        return calls.saveAndFlush(row).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public long userSpendToday(UUID userId) {
        return calls.spendSince(userId, clock.startOfToday());
    }

    @Override
    @Transactional(readOnly = true)
    public long globalSpendToday() {
        return calls.globalSpendSince(clock.startOfToday());
    }
}
