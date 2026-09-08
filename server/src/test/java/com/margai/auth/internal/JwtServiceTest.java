package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;

/**
 * TECH_PLAN §3.2: HS256, the four claims, 15-minute lifetime; §9.2: the previous key is accepted
 * while configured. Expiry is reported as such so the entry point can say {@code AUTH_EXPIRED}.
 */
class JwtServiceTest {

    private static final Instant NOW = Instant.parse("2026-09-08T10:00:00Z");
    private static final Principal STUDENT = new Principal(UUID.randomUUID(), UserRole.student, Language.hinglish);

    private final SecretKey keyA = key(1);
    private final SecretKey keyB = key(2);
    private final IstClock clock = new IstClock(Clock.fixed(NOW, ZoneOffset.UTC));

    @Test
    void issuedTokenCarriesTheFourClaimsAndTheLifetime() {
        JwtService jwts = service(keyA, null);

        String token = jwts.issue(STUDENT);
        Jwt decoded = jwts.decode(token);

        assertThat(decoded.getSubject()).isEqualTo(STUDENT.userId().toString());
        assertThat(decoded.getClaimAsString("role")).isEqualTo("student");
        assertThat(decoded.getClaimAsString("lang")).isEqualTo("hinglish");
        assertThat(decoded.getId()).isNotBlank();
        assertThat(decoded.getIssuedAt()).isEqualTo(NOW);
        assertThat(decoded.getExpiresAt()).isEqualTo(NOW.plus(Duration.ofMinutes(15)));
        assertThat(decoded.getHeaders()).containsEntry("alg", "HS256");
        assertThat(jwts.toPrincipal(decoded)).isEqualTo(STUDENT);
        assertThat(jwts.expiresInSeconds()).isEqualTo(900);
    }

    @Test
    void everyTokenHasItsOwnJti() {
        JwtService jwts = service(keyA, null);

        Jwt first = jwts.decode(jwts.issue(STUDENT));
        Jwt second = jwts.decode(jwts.issue(STUDENT));

        assertThat(first.getId()).isNotEqualTo(second.getId());
    }

    @Test
    void aTokenInsideTheSkewWindowStillPasses() {
        JwtService jwts = service(keyA, null);
        String justExpired = jwts.issue(STUDENT, NOW.minus(Duration.ofMinutes(15)).minusSeconds(30));

        assertThat(jwts.decode(justExpired).getSubject()).isEqualTo(STUDENT.userId().toString());
    }

    @Test
    void anExpiredTokenIsRejectedAsExpired() {
        JwtService jwts = service(keyA, null);
        String stale = jwts.issue(STUDENT, NOW.minus(Duration.ofMinutes(17)));

        assertThatThrownBy(() -> jwts.decode(stale))
                .isInstanceOf(JwtException.class)
                .satisfies(rejected -> assertThat(JwtService.isExpired(rejected)).isTrue());
    }

    @Test
    void aTokenSignedWithAnotherKeyIsRejectedAsInvalid() {
        String foreign = service(keyB, null).issue(STUDENT, NOW);
        JwtService jwts = service(keyA, null);

        assertThatThrownBy(() -> jwts.decode(foreign))
                .isInstanceOf(JwtException.class)
                .satisfies(rejected -> assertThat(JwtService.isExpired(rejected)).isFalse());
        assertThatThrownBy(() -> jwts.decode("not.a.jwt")).isInstanceOf(JwtException.class);
    }

    @Test
    void thePreviousKeyIsAcceptedWhileConfigured() {
        String signedWithOldKey = service(keyB, null).issue(STUDENT, NOW);

        assertThat(service(keyA, keyB).decode(signedWithOldKey).getSubject()).isEqualTo(STUDENT.userId().toString());
        assertThatThrownBy(() -> service(keyA, null).decode(signedWithOldKey)).isInstanceOf(JwtException.class);
    }

    @Test
    void malformedClaimsDoNotBecomeAPrincipal() {
        JwtService jwts = service(keyA, null);
        Jwt odd = Jwt.withTokenValue("x").header("alg", "HS256").subject("not-a-uuid")
                .claim("role", "student").claim("lang", "en").build();

        assertThatThrownBy(() -> jwts.toPrincipal(odd)).isInstanceOf(JwtException.class);
    }

    private JwtService service(SecretKey current, SecretKey previous) {
        return new JwtService(new AuthKeys(current, previous, new byte[32]), Duration.ofMinutes(15), clock);
    }

    private static SecretKey key(int seed) {
        byte[] bytes = new byte[32];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (seed * 31 + i);
        }
        return new SecretKeySpec(bytes, "HmacSHA256");
    }
}
