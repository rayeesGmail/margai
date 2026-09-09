package com.margai.auth.internal;

import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * The D7 queries over {@code otp_challenges}: the hourly per-destination cap and the resend
 * cooldown (TECH_PLAN §3.4, served by the {@code (destination, created_at)} index of §2.2), a
 * locked read for verification so concurrent guesses serialise on the row, (D9) the bulk
 * retirement of pending codes after a start with a per-process pepper, and (D11) the count of
 * codes that died unverified, for the delivery report.
 */
public interface OtpChallengeRepository extends JpaRepository<OtpChallenge, UUID> {

    /**
     * Codes of one channel and purpose, created at or after {@code since}, that reached their
     * expiry without ever being verified — whether the student typed wrong codes or nothing at
     * all. The one delivery signal the counters cannot carry: a code that never arrived looks
     * exactly like this (TECH_PLAN §10.2; PLAN D11 "delivery-rate logging"). A row deleted after
     * a delivery failure (D7) is not here; that is {@code otp.send_failed}.
     */
    @Query("select count(c) from OtpChallenge c where c.channel = :channel and c.purpose = :purpose"
            + " and c.createdAt >= :since and c.verifiedAt is null and c.expiresAt <= :now")
    long countExpiredUnverified(OtpChannel channel, OtpPurpose purpose, Instant since, Instant now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from OtpChallenge c where c.id = :id")
    Optional<OtpChallenge> lockById(UUID id);

    /**
     * Expires every unverified, still-live challenge of any purpose at once — codes hashed with a
     * pepper this process no longer has (PLAN D9 row 10; DECISIONS 2026-09-09). Returns how many.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update OtpChallenge c set c.expiresAt = :now, c.updatedAt = :now"
            + " where c.verifiedAt is null and c.expiresAt > :now")
    int retireLive(Instant now);

    long countByDestinationAndPurposeAndCreatedAtAfter(String destination, OtpPurpose purpose, Instant since);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeOrderByCreatedAtDesc(String destination, OtpPurpose purpose);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeAndCreatedAtAfterOrderByCreatedAtAsc(String destination,
            OtpPurpose purpose, Instant since);
}
