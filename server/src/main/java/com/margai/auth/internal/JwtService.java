package com.margai.auth.internal;

import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * Access tokens (TECH_PLAN §3.2, §9.1; DECISIONS D3.12): JWT HS256, 15 minutes, claims
 * {@code sub} (user id), {@code role}, {@code lang}, {@code jti}. Decoding tries the current key
 * and then, while one is configured, the previous key (§9.2 rotation). The 60-second clock skew
 * Spring Security allows by default is kept: phones drift (§3.1 clock-skew diagnostics, D9).
 */
public class JwtService {

    static final String ROLE_CLAIM = "role";
    static final String LANG_CLAIM = "lang";

    private final JwtEncoder encoder;
    private final JwtDecoder current;
    private final Optional<JwtDecoder> previous;
    private final Duration accessTtl;
    private final IstClock clock;

    JwtService(AuthKeys keys, Duration accessTtl, IstClock clock) {
        this.encoder = new NimbusJwtEncoder(new ImmutableSecret<>(keys.jwtKey()));
        this.current = decoderFor(keys.jwtKey(), clock);
        this.previous = keys.previousJwtKey().map(key -> decoderFor(key, clock));
        this.accessTtl = accessTtl;
        this.clock = clock;
    }

    public String issue(Principal principal) {
        return issue(principal, clock.now());
    }

    String issue(Principal principal, Instant issuedAt) {
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(principal.userId().toString())
                .id(UUID.randomUUID().toString())
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plus(accessTtl))
                .claim(ROLE_CLAIM, principal.role().name())
                .claim(LANG_CLAIM, principal.language().name())
                .build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    /** Seconds until a freshly issued access token expires ({@code expires_in} on the wire). */
    public long expiresInSeconds() {
        return accessTtl.toSeconds();
    }

    /** Decodes and validates with the current key, then the previous one; throws {@link JwtException}. */
    public Jwt decode(String token) {
        try {
            return current.decode(token);
        } catch (BadJwtException rejected) {
            if (previous.isEmpty() || isExpired(rejected)) {
                throw rejected;
            }
            try {
                return previous.get().decode(token);
            } catch (BadJwtException alsoRejected) {
                throw rejected;
            }
        }
    }

    public Principal toPrincipal(Jwt jwt) {
        try {
            return new Principal(
                    UUID.fromString(jwt.getSubject()),
                    UserRole.valueOf(jwt.getClaimAsString(ROLE_CLAIM)),
                    Language.valueOf(jwt.getClaimAsString(LANG_CLAIM)));
        } catch (RuntimeException malformedClaims) {
            throw new BadJwtException("token claims do not describe a principal", malformedClaims);
        }
    }

    /** Whether a rejection was about time rather than signature or shape (→ {@code AUTH_EXPIRED}). */
    public static boolean isExpired(Throwable rejected) {
        for (Throwable cause = rejected; cause != null; cause = cause.getCause()) {
            if (cause.getMessage() != null && cause.getMessage().contains("expired")) {
                return true;
            }
        }
        return false;
    }

    /** Expiry is judged on the application clock (TECH_PLAN §11.1), with Spring's default 60-second skew. */
    private static JwtDecoder decoderFor(SecretKey key, IstClock clock) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
        JwtTimestampValidator timestamps = new JwtTimestampValidator();
        timestamps.setClock(clock.asClock());
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(timestamps));
        return decoder;
    }
}
