package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.UserSummary;
import com.margai.common.api.AuthException;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.Language;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TECH_PLAN §3.2 and DECISIONS D3.12 over the real database: the token is never stored, refresh
 * rotates and links, reuse of a spent token revokes the whole family, expiry and deletion are
 * {@code AUTH_INVALID}, families are independent, the new access token speaks the account's
 * current language (§3.8), and logout revokes the caller's family only (§3.7, D10).
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class TokenServiceTest {

    @Autowired
    private TokenService service;

    @Autowired
    private Accounts accounts;

    @Autowired
    private RefreshTokenRepository tokens;

    @Autowired
    private JwtService jwts;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MeterRegistry meters;

    @Test
    void issueStoresAHashNeverTheToken() {
        UserSummary user = newUser();

        TokenPair pair = service.issue(user, "Pixel 7a");

        assertThat(pair.expiresIn()).isEqualTo(900);
        assertThat(pair.refreshToken()).hasSizeGreaterThanOrEqualTo(43);
        assertThat(tokens.findByTokenHash(pair.refreshToken())).as("raw token is not a lookup key").isEmpty();
        RefreshToken row = tokens.findByTokenHash(Sha256.hex(Sha256.utf8(pair.refreshToken()))).orElseThrow();
        assertThat(row.getUserId()).isEqualTo(user.id());
        assertThat(row.getFamilyId()).isNotNull();
        assertThat(row.getDeviceLabel()).isEqualTo("Pixel 7a");
        assertThat(row.getExpiresAt()).isBetween(Instant.now().plus(29, ChronoUnit.DAYS), Instant.now().plus(31, ChronoUnit.DAYS));
        assertThat(jwts.toPrincipal(jwts.decode(pair.accessToken())).userId()).isEqualTo(user.id());
    }

    @Test
    void refreshRotatesWithinTheFamilyAndLinksTheSuccessor() {
        TokenPair first = service.issue(newUser(), null);

        TokenPair second = service.refresh(first.refreshToken());

        assertThat(second.refreshToken()).isNotEqualTo(first.refreshToken());
        RefreshToken spent = row(first.refreshToken());
        RefreshToken live = row(second.refreshToken());
        assertThat(spent.isRevoked()).isTrue();
        assertThat(spent.getReplacedById()).isEqualTo(live.getId());
        assertThat(spent.getLastUsedAt()).isNotNull();
        assertThat(live.getFamilyId()).isEqualTo(spent.getFamilyId());
        assertThat(live.isRevoked()).isFalse();
        assertThat(jwts.decode(second.accessToken()).getId()).isNotEqualTo(jwts.decode(first.accessToken()).getId());
    }

    @Test
    void reuseOfASpentTokenRevokesTheWholeFamily() {
        TokenPair first = service.issue(newUser(), null);
        TokenPair second = service.refresh(first.refreshToken());

        assertThatThrownBy(() -> service.refresh(first.refreshToken()))
                .isInstanceOf(AuthException.class)
                .satisfies(failure -> assertThat(((AuthException) failure).code()).isEqualTo(ErrorCode.AUTH_INVALID));

        List<RefreshToken> family = tokens.findByFamilyIdOrderByCreatedAt(row(first.refreshToken()).getFamilyId());
        assertThat(family).hasSize(2).allMatch(RefreshToken::isRevoked);
        assertThatThrownBy(() -> service.refresh(second.refreshToken()))
                .as("the fresh token of a revoked family is dead too")
                .isInstanceOf(AuthException.class);
    }

    @Test
    void familiesAreIndependent() {
        UserSummary user = newUser();
        TokenPair phone = service.issue(user, "phone");
        TokenPair tablet = service.issue(user, "tablet");
        TokenPair phoneRotated = service.refresh(phone.refreshToken());
        assertThatThrownBy(() -> service.refresh(phone.refreshToken())).isInstanceOf(AuthException.class);

        assertThat(service.refresh(tablet.refreshToken()).refreshToken()).isNotBlank();
        assertThat(row(phoneRotated.refreshToken()).isRevoked()).isTrue();
    }

    @Test
    void unknownExpiredAndDeletedAreAllInvalid() {
        assertThatThrownBy(() -> service.refresh("never-issued")).isInstanceOf(AuthException.class);
        assertThatThrownBy(() -> service.refresh(null)).isInstanceOf(AuthException.class);

        TokenPair expired = service.issue(newUser(), null);
        jdbc.update("UPDATE refresh_tokens SET expires_at = now() - interval '1 minute' WHERE token_hash = ?",
                Sha256.hex(Sha256.utf8(expired.refreshToken())));
        assertThatThrownBy(() -> service.refresh(expired.refreshToken())).isInstanceOf(AuthException.class);

        UserSummary gone = newUser();
        TokenPair ofGone = service.issue(gone, null);
        jdbc.update("UPDATE users SET status = 'deleted', email = NULL, phone = NULL, deleted_at = now() WHERE id = ?", gone.id());
        assertThatThrownBy(() -> service.refresh(ofGone.refreshToken())).isInstanceOf(AuthException.class);
    }

    @Test
    void refreshedAccessTokenSpeaksTheAccountsCurrentLanguage() {
        UserSummary user = newUser();
        TokenPair first = service.issue(user, null);
        assertThat(jwts.decode(first.accessToken()).getClaimAsString("lang")).isEqualTo("en");

        jdbc.update("UPDATE users SET language = 'hi' WHERE id = ?", user.id());
        TokenPair second = service.refresh(first.refreshToken());

        assertThat(jwts.decode(second.accessToken()).getClaimAsString("lang")).isEqualTo("hi");
        assertThat(jwts.decode(second.accessToken()).getExpiresAt())
                .isAfter(Instant.now().plus(Duration.ofMinutes(14)));
    }

    @Test
    void logoutRevokesTheCallersFamilyAndLeavesOtherDevicesSignedIn() {
        UserSummary user = newUser();
        TokenPair phone = service.issue(user, "phone");
        TokenPair tablet = service.issue(user, "tablet");

        service.logout(user.id(), phone.refreshToken());

        List<RefreshToken> phoneFamily = tokens.findByFamilyIdOrderByCreatedAt(row(phone.refreshToken()).getFamilyId());
        assertThat(phoneFamily).hasSize(1).allMatch(RefreshToken::isRevoked);
        assertThatThrownBy(() -> service.refresh(phone.refreshToken())).isInstanceOf(AuthException.class);
        assertThat(service.refresh(tablet.refreshToken()).refreshToken()).as("the tablet stays signed in").isNotBlank();
    }

    @Test
    void logoutOfASpentTokenRevokesItsFamilyWithoutAReuseAlarm() {
        UserSummary user = newUser();
        TokenPair first = service.issue(user, null);
        TokenPair second = service.refresh(first.refreshToken());
        double alarmsBefore = reuseAlarms();

        service.logout(user.id(), first.refreshToken());
        service.logout(user.id(), first.refreshToken());

        List<RefreshToken> family = tokens.findByFamilyIdOrderByCreatedAt(row(first.refreshToken()).getFamilyId());
        assertThat(family).hasSize(2).allMatch(RefreshToken::isRevoked);
        assertThatThrownBy(() -> service.refresh(second.refreshToken())).isInstanceOf(AuthException.class);
        assertThat(reuseAlarms()).as("logging out is not reuse; revoking twice is a no-op").isEqualTo(alarmsBefore);
    }

    @Test
    void logoutOfAnUnknownOrAnotherUsersTokenChangesNothing() {
        UserSummary alice = newUser();
        UserSummary bob = newUser();
        TokenPair alicePair = service.issue(alice, null);

        service.logout(bob.id(), alicePair.refreshToken());
        service.logout(alice.id(), "never-issued");
        service.logout(alice.id(), null);

        assertThat(row(alicePair.refreshToken()).isRevoked()).isFalse();
        assertThat(service.refresh(alicePair.refreshToken()).refreshToken()).isNotBlank();
    }

    private double reuseAlarms() {
        return meters.find(TokenService.REUSE_METRIC).counter() == null ? 0
                : meters.find(TokenService.REUSE_METRIC).counter().count();
    }

    private UserSummary newUser() {
        return accounts.signIn(new LoginIdentifier.Email("tokens-" + UUID.randomUUID() + "@example.com"), Language.en).user();
    }

    private RefreshToken row(String rawToken) {
        return tokens.findByTokenHash(Sha256.hex(Sha256.utf8(rawToken))).orElseThrow();
    }
}
