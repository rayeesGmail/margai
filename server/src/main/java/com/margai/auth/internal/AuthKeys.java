package com.margai.auth.internal;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Key material derived from {@link AuthProperties} once at startup (TECH_PLAN §9.1, §9.2): the
 * HS256 signing key, the previous key during a rotation window, and the OTP pepper. A blank
 * setting becomes a random 256-bit value for this process with a WARN — tokens and pending codes
 * then die on restart, which is the right failure locally and a loud one anywhere else
 * (DECISIONS 2026-09-08, D7). A configured value shorter than 256 bits refuses to start.
 */
final class AuthKeys {

    static final int MIN_BYTES = 32;

    private static final Logger log = LoggerFactory.getLogger(AuthKeys.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final SecretKey jwtKey;
    private final SecretKey previousJwtKey;
    private final byte[] otpPepper;
    private final boolean otpPepperEphemeral;

    /** Configured keys (tests); the pepper counts as configured. */
    AuthKeys(SecretKey jwtKey, SecretKey previousJwtKey, byte[] otpPepper) {
        this(jwtKey, previousJwtKey, otpPepper, false);
    }

    private AuthKeys(SecretKey jwtKey, SecretKey previousJwtKey, byte[] otpPepper, boolean otpPepperEphemeral) {
        this.jwtKey = jwtKey;
        this.previousJwtKey = previousJwtKey;
        this.otpPepper = otpPepper.clone();
        this.otpPepperEphemeral = otpPepperEphemeral;
    }

    static AuthKeys from(AuthProperties properties) {
        SecretKey jwt = hmacKey(configuredOrEphemeral(properties.jwt().secret(), "margai.auth.jwt.secret"));
        SecretKey previous = isBlank(properties.jwt().secretPrevious())
                ? null
                : hmacKey(decode(properties.jwt().secretPrevious(), "margai.auth.jwt.secret-previous"));
        boolean pepperEphemeral = isBlank(properties.otp().pepper());
        byte[] pepper = configuredOrEphemeral(properties.otp().pepper(), "margai.auth.otp.pepper");
        return new AuthKeys(jwt, previous, pepper, pepperEphemeral);
    }

    /** True when the pepper is this process's random one: codes from before the start are unverifiable (D9). */
    boolean otpPepperEphemeral() {
        return otpPepperEphemeral;
    }

    SecretKey jwtKey() {
        return jwtKey;
    }

    Optional<SecretKey> previousJwtKey() {
        return Optional.ofNullable(previousJwtKey);
    }

    byte[] otpPepper() {
        return otpPepper.clone();
    }

    private static byte[] configuredOrEphemeral(String base64, String property) {
        if (isBlank(base64)) {
            byte[] ephemeral = new byte[MIN_BYTES];
            RANDOM.nextBytes(ephemeral);
            log.warn("{} is not set: using a random value for this process only — tokens and pending codes"
                    + " will not survive a restart. Set it from SSM in any deployed environment (TECH_PLAN §7.3).",
                    property);
            return ephemeral;
        }
        return decode(base64, property);
    }

    private static byte[] decode(String base64, String property) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(base64.strip());
        } catch (IllegalArgumentException notBase64) {
            throw new IllegalStateException(property + " must be base64", notBase64);
        }
        if (bytes.length < MIN_BYTES) {
            throw new IllegalStateException(property + " must decode to at least " + MIN_BYTES + " bytes (256 bits)");
        }
        return bytes;
    }

    private static SecretKey hmacKey(byte[] bytes) {
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
