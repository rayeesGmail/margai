package com.margai.auth.internal;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * The one hash the auth module stores (TECH_PLAN §3.2, §9.1): SHA-256 as 64 lowercase hex
 * characters, which is why {@code code_hash} and {@code token_hash} are {@code CHAR(64)}.
 */
final class Sha256 {

    private Sha256() {
    }

    static String hex(byte[]... parts) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is mandatory in every JVM", impossible);
        }
        for (byte[] part : parts) {
            digest.update(part);
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    static byte[] utf8(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
