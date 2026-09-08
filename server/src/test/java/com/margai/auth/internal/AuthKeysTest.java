package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.Base64;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** DECISIONS 2026-09-08 (D7): blank secrets become per-process random keys; short ones refuse to start. */
class AuthKeysTest {

    private static final byte[] THIRTY_TWO = new byte[32];
    private static final byte[] SIXTEEN = new byte[16];

    static {
        for (int i = 0; i < THIRTY_TWO.length; i++) {
            THIRTY_TWO[i] = (byte) (i * 7);
        }
    }

    @Test
    void blankSecretsBecomeEphemeralRandomKeys() {
        AuthKeys first = AuthKeys.from(properties("", "", ""));
        AuthKeys second = AuthKeys.from(properties("", "", ""));

        assertThat(first.jwtKey().getEncoded()).hasSize(32).isNotEqualTo(second.jwtKey().getEncoded());
        assertThat(first.otpPepper()).hasSize(32).isNotEqualTo(second.otpPepper());
        assertThat(first.previousJwtKey()).isEmpty();
    }

    @Test
    void configuredKeysAreDecodedFromBase64() {
        String encoded = Base64.getEncoder().encodeToString(THIRTY_TWO);

        AuthKeys keys = AuthKeys.from(properties(encoded, encoded, encoded));

        assertThat(keys.jwtKey().getEncoded()).isEqualTo(THIRTY_TWO);
        assertThat(keys.previousJwtKey()).isPresent();
        assertThat(keys.otpPepper()).isEqualTo(THIRTY_TWO);
    }

    @Test
    void aShortOrMalformedKeyRefusesToStart() {
        String tooShort = Base64.getEncoder().encodeToString(SIXTEEN);

        assertThatThrownBy(() -> AuthKeys.from(properties(tooShort, "", "")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("margai.auth.jwt.secret")
                .hasMessageContaining("256 bits");
        assertThatThrownBy(() -> AuthKeys.from(properties("", "", "not*base64!")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("margai.auth.otp.pepper");
    }

    static AuthProperties properties(String jwt, String previous, String pepper) {
        return new AuthProperties(
                new AuthProperties.Jwt(jwt, previous, Duration.ofMinutes(15), Duration.ofDays(30)),
                new AuthProperties.Otp(pepper, Duration.ofMinutes(5), 5, Duration.ofSeconds(30), 6,
                        Set.of(OtpChannel.email), AuthProperties.Sender.log, "", "ap-south-1"));
    }
}
