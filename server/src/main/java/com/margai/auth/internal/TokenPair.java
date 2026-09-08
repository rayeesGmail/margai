package com.margai.auth.internal;

/**
 * What a login or refresh hands the device (TECH_PLAN §3.2): the 15-minute JWT, the opaque
 * refresh token — the only copy that ever exists in the clear — and the access lifetime in seconds.
 */
public record TokenPair(String accessToken, String refreshToken, long expiresIn) {
}
