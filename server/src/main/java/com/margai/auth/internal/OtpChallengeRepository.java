package com.margai.auth.internal;

import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

/**
 * The D7 queries over {@code otp_challenges}: the hourly per-destination cap and the resend
 * cooldown (TECH_PLAN §3.4, served by the {@code (destination, created_at)} index of §2.2), and a
 * locked read for verification so concurrent guesses serialise on the row.
 */
public interface OtpChallengeRepository extends JpaRepository<OtpChallenge, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from OtpChallenge c where c.id = :id")
    Optional<OtpChallenge> lockById(UUID id);

    long countByDestinationAndPurposeAndCreatedAtAfter(String destination, OtpPurpose purpose, Instant since);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeOrderByCreatedAtDesc(String destination, OtpPurpose purpose);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeAndCreatedAtAfterOrderByCreatedAtAsc(String destination,
            OtpPurpose purpose, Instant since);
}
