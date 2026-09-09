package com.margai.auth.internal;

import com.margai.account.api.Accounts;
import com.margai.account.api.UserSummary;
import com.margai.common.api.ApiException;
import com.margai.common.api.AuthException;
import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.MeterRegistry;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Refresh-token families (TECH_PLAN §3.2, §9.1; DECISIONS D3.12). A login opens a family with a
 * 256-bit opaque token stored as SHA-256. A refresh rotates: the presented token is spent
 * ({@code revoked_at}, {@code replaced_by_id}) and a successor in the same family is issued with a
 * fresh 30-day life, alongside a new access token carrying the account's current role and
 * language (§3.8). Presenting a token that was already spent (rotated out) is reuse — somebody
 * holds a copy — so the whole family is revoked and the caller must sign in again; that
 * revocation commits even though the call fails. A token revoked without a successor (logout,
 * D10) is merely dead. Logout revokes the caller's family (§3.7).
 */
@Service
public class TokenService {

    static final String REUSE_METRIC = "auth.refresh.reuse";

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final RefreshTokenRepository tokens;
    private final Accounts accounts;
    private final JwtService jwts;
    private final Duration refreshTtl;
    private final IstClock clock;
    private final MeterRegistry meters;

    TokenService(RefreshTokenRepository tokens, Accounts accounts, JwtService jwts, AuthProperties properties,
            IstClock clock, MeterRegistry meters) {
        this.tokens = tokens;
        this.accounts = accounts;
        this.jwts = jwts;
        this.refreshTtl = properties.jwt().refreshTtl();
        this.clock = clock;
        this.meters = meters;
    }

    /** A new family for a fresh login (one per device, §3.2). */
    @Transactional
    public TokenPair issue(UserSummary user, String deviceLabel) {
        Instant now = clock.now();
        String refreshToken = newOpaqueToken();
        tokens.save(new RefreshToken(user.id(), Sha256.hex(Sha256.utf8(refreshToken)), UUID.randomUUID(),
                now.plus(refreshTtl), deviceLabel));
        return new TokenPair(jwts.issue(user.toPrincipal()), refreshToken, jwts.expiresInSeconds());
    }

    /** Rotation with reuse detection; every failure is {@code AUTH_INVALID} (§3.3: re-login). */
    @Transactional(noRollbackFor = ApiException.class)
    public TokenPair refresh(String presented) {
        Instant now = clock.now();
        RefreshToken current = tokens.findByTokenHash(Sha256.hex(Sha256.utf8(presented == null ? "" : presented)))
                .orElseThrow(AuthException::invalid);
        if (current.isReplaced()) {
            // A rotated-out token: somebody holds a copy (§3.2 "reuse of a rotated refresh token").
            int revoked = tokens.revokeFamily(current.getFamilyId(), now);
            meters.counter(REUSE_METRIC).increment();
            log.warn("refresh token reuse detected: family {} revoked ({} live token(s))", current.getFamilyId(), revoked);
            throw AuthException.invalid();
        }
        if (current.isRevoked() || current.isExpired(now)) {
            // Revoked without a successor — logged out (D10), or a family killed by an earlier reuse — is a
            // stale session, not a new incident: no alarm, the caller signs in again.
            throw AuthException.invalid();
        }
        UserSummary user = accounts.findActive(current.getUserId()).orElseThrow(AuthException::invalid);
        String refreshToken = newOpaqueToken();
        RefreshToken successor = tokens.save(new RefreshToken(user.id(), Sha256.hex(Sha256.utf8(refreshToken)),
                current.getFamilyId(), now.plus(refreshTtl), current.getDeviceLabel()));
        current.rotateTo(successor.getId(), now);
        tokens.save(current);
        return new TokenPair(jwts.issue(user.toPrincipal()), refreshToken, jwts.expiresInSeconds());
    }

    /**
     * {@code POST /auth/logout} (TECH_PLAN §3.2, §3.7; D10): revokes the family of the presented
     * token when that family is the caller's. A spent token still names its family; an already
     * revoked family, an unknown token or another user's token change nothing — the call is
     * naturally idempotent and answers nothing either way (DECISIONS D10).
     */
    @Transactional
    public void logout(UUID caller, String presented) {
        if (presented == null || caller == null) {
            return;
        }
        tokens.findByTokenHash(Sha256.hex(Sha256.utf8(presented)))
                .filter(token -> caller.equals(token.getUserId()))
                .ifPresent(token -> {
                    int revoked = tokens.revokeFamily(token.getFamilyId(), clock.now());
                    log.info("logout: family {} revoked ({} live token(s))", token.getFamilyId(), revoked);
                });
    }

    private static String newOpaqueToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
