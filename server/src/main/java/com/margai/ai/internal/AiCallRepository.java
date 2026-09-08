package com.margai.ai.internal;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Ledger rows (TECH_PLAN §2.8) and the IST-day spend sums the breaker reads (§4.8). */
public interface AiCallRepository extends JpaRepository<AiCall, UUID> {

    /** Paise spent by one user on calls created at or after {@code since} (the IST day start). */
    @Query("select coalesce(sum(c.costPaise), 0) from AiCall c where c.userId = :userId and c.createdAt >= :since")
    long spendSince(UUID userId, Instant since);

    /** Paise spent by everyone on calls created at or after {@code since}. */
    @Query("select coalesce(sum(c.costPaise), 0) from AiCall c where c.createdAt >= :since")
    long globalSpendSince(Instant since);

    List<AiCall> findByRequestIdOrderByCreatedAt(String requestId);
}
