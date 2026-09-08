package com.margai.auth.internal;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The D7 queries over {@code otp_challenges}, all served by the {@code (destination, created_at)}
 * index of TECH_PLAN §2.2: the hourly per-destination cap and the resend cooldown (§3.4).
 */
public interface OtpChallengeRepository extends JpaRepository<OtpChallenge, UUID> {

    long countByDestinationAndPurposeAndCreatedAtAfter(String destination, OtpPurpose purpose, Instant since);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeOrderByCreatedAtDesc(String destination, OtpPurpose purpose);

    Optional<OtpChallenge> findFirstByDestinationAndPurposeAndCreatedAtAfterOrderByCreatedAtAsc(String destination,
            OtpPurpose purpose, Instant since);
}
