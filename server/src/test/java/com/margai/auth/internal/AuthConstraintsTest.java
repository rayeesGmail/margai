package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.Language;
import com.margai.account.internal.User;
import com.margai.account.internal.UserRepository;
import java.net.InetAddress;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Repository slice (TECH_PLAN §8.1): migration V6 applies and the auth tables of §2.2 hold their
 * constraints — the channel and purpose CHECK lists, the unique token hash, the foreign keys to
 * {@code users} and to the successor token, and the family-wide revocation update. Invalid
 * enumeration values are inserted over JDBC because the entity's enums cannot express them.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class AuthConstraintsTest {

    private static final String INSERT_CHALLENGE = "INSERT INTO otp_challenges"
            + " (channel, destination, purpose, code_hash, expires_at)"
            + " VALUES (?, 'someone@example.com', ?, repeat('a', 64), now() + interval '5 minutes')";

    private static final String HASH_A = "a".repeat(64);
    private static final String HASH_B = "b".repeat(64);

    @Autowired
    private OtpChallengeRepository challenges;

    @Autowired
    private RefreshTokenRepository tokens;

    @Autowired
    private UserRepository users;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void challengeKeepsEveryColumnAndItsAssignedId() throws Exception {
        UUID id = UUID.randomUUID();
        Instant expires = Instant.now().plus(5, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MILLIS);
        OtpChallenge challenge = new OtpChallenge(id, OtpChannel.email, "someone@example.com", OtpPurpose.login,
                HASH_A, expires, InetAddress.getByName("203.0.113.7"));

        OtpChallenge saved = challenges.saveAndFlush(challenge);

        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.isNew()).isFalse();
        assertThat(saved.getAttempts()).isZero();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.isVerified()).isFalse();
        assertThat(challenges.findById(id)).get().satisfies(found -> {
            assertThat(found.getRequestIp()).isEqualTo(InetAddress.getByName("203.0.113.7"));
            assertThat(found.getExpiresAt()).isEqualTo(expires);
            assertThat(found.getCodeHash()).isEqualTo(HASH_A);
            assertThat(found.getChannel()).isEqualTo(OtpChannel.email);
        });
    }

    @Test
    void challengeQueriesFollowDestinationAndPurpose() {
        Instant now = Instant.now();
        challenges.saveAndFlush(challenge("one@example.com", HASH_A, now));
        challenges.saveAndFlush(challenge("one@example.com", HASH_B, now));
        challenges.saveAndFlush(challenge("two@example.com", HASH_A, now));

        assertThat(challenges.countByDestinationAndPurposeAndCreatedAtAfter("one@example.com", OtpPurpose.login,
                now.minusSeconds(3600))).isEqualTo(2);
        assertThat(challenges.countByDestinationAndPurposeAndCreatedAtAfter("one@example.com", OtpPurpose.parent_consent,
                now.minusSeconds(3600))).isZero();
        assertThat(challenges.findFirstByDestinationAndPurposeOrderByCreatedAtDesc("two@example.com", OtpPurpose.login))
                .isPresent();
        assertThat(challenges.findFirstByDestinationAndPurposeOrderByCreatedAtDesc("three@example.com", OtpPurpose.login))
                .isEmpty();
    }

    // One violation per test: after the first, Postgres only reports "current transaction is aborted".

    @Test
    void channelIsACheckedList() {
        assertThatThrownBy(() -> jdbc.update(INSERT_CHALLENGE, "pigeon", "login"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("otp_challenges_channel_check");
    }

    @Test
    void purposeIsACheckedList() {
        assertThatThrownBy(() -> jdbc.update(INSERT_CHALLENGE, "email", "reset"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("otp_challenges_purpose_check");
    }

    @Test
    void tokenHashIsUnique() {
        User user = users.saveAndFlush(User.withEmail("tokens@example.com", Language.en));
        UUID family = UUID.randomUUID();
        tokens.saveAndFlush(new RefreshToken(user.getId(), HASH_A, family, Instant.now().plusSeconds(60), "test"));

        assertThatThrownBy(() -> tokens.saveAndFlush(
                new RefreshToken(user.getId(), HASH_A, family, Instant.now().plusSeconds(60), "test")))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("refresh_tokens_token_hash_key");
    }

    @Test
    void refreshTokenNeedsAnExistingUser() {
        assertThatThrownBy(() -> tokens.saveAndFlush(
                new RefreshToken(UUID.randomUUID(), HASH_A, UUID.randomUUID(), Instant.now().plusSeconds(60), null)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("refresh_tokens_user_id_fkey");
    }

    @Test
    void successorMustBeAnExistingToken() {
        User user = users.saveAndFlush(User.withEmail("successor@example.com", Language.en));
        RefreshToken token = tokens.saveAndFlush(
                new RefreshToken(user.getId(), HASH_A, UUID.randomUUID(), Instant.now().plusSeconds(60), "test"));

        token.rotateTo(UUID.randomUUID(), Instant.now());

        assertThatThrownBy(() -> tokens.saveAndFlush(token))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("refresh_tokens_replaced_by_id_fkey");
    }

    @Test
    void revokingAFamilyLeavesOtherFamiliesAlone() {
        User user = users.saveAndFlush(User.withEmail("families@example.com", Language.en));
        UUID phoneFamily = UUID.randomUUID();
        UUID tabletFamily = UUID.randomUUID();
        Instant later = Instant.now().plusSeconds(60);
        tokens.saveAndFlush(new RefreshToken(user.getId(), HASH_A, phoneFamily, later, "phone"));
        tokens.saveAndFlush(new RefreshToken(user.getId(), HASH_B, phoneFamily, later, "phone"));
        tokens.saveAndFlush(new RefreshToken(user.getId(), "c".repeat(64), tabletFamily, later, "tablet"));

        int revoked = tokens.revokeFamily(phoneFamily, Instant.now());

        assertThat(revoked).isEqualTo(2);
        assertThat(tokens.findByFamilyIdOrderByCreatedAt(phoneFamily)).allMatch(RefreshToken::isRevoked);
        assertThat(tokens.findByFamilyIdOrderByCreatedAt(tabletFamily)).noneMatch(RefreshToken::isRevoked);
        assertThat(tokens.revokeFamily(phoneFamily, Instant.now())).as("revoking again is a no-op").isZero();
    }

    private static OtpChallenge challenge(String destination, String hash, Instant now) {
        return new OtpChallenge(UUID.randomUUID(), OtpChannel.email, destination, OtpPurpose.login, hash,
                now.plusSeconds(300), null);
    }
}
