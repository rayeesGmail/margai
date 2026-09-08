package com.margai.auth.internal;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Codes and their hashes (TECH_PLAN §3.2, §9.1; DECISIONS 2026-09-08, D7): a code is
 * {@code n} random digits from {@link SecureRandom}; the row keeps
 * {@code SHA-256(pepper ‖ challenge_id ‖ code)} so a leaked table reveals nothing without the
 * pepper and every challenge salts differently; comparison is constant-time.
 */
final class OtpCodes {

    private static final SecureRandom RANDOM = new SecureRandom();

    private OtpCodes() {
    }

    static String generate(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10));
        }
        return code.toString();
    }

    static String hash(byte[] pepper, UUID challengeId, String code) {
        return Sha256.hex(pepper, Sha256.utf8(challengeId.toString()), Sha256.utf8(code));
    }

    static boolean matches(String storedHash, byte[] pepper, UUID challengeId, String presentedCode) {
        if (storedHash == null || presentedCode == null) {
            return false;
        }
        String presentedHash = hash(pepper, challengeId, presentedCode.strip());
        return MessageDigest.isEqual(Sha256.utf8(storedHash), Sha256.utf8(presentedHash));
    }
}
