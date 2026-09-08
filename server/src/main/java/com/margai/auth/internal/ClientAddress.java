package com.margai.auth.internal;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;

/**
 * The caller's address behind the ALB (TECH_PLAN §1.5 step 1). The ALB runs in its default
 * {@code append} mode: whatever a client sends as {@code X-Forwarded-For}, the ALB appends the
 * address it actually saw as the <em>last</em> hop — so the last hop is the only one a client
 * cannot choose, and it is what keys the per-address rate limits (§3.4) and lands in
 * {@code otp_challenges.request_ip} (§2.2). Without the header (a direct connection) the socket
 * address is used.
 */
public final class ClientAddress {

    static final String FORWARDED_FOR = "X-Forwarded-For";

    private ClientAddress() {
    }

    public static String of(HttpServletRequest request) {
        String forwarded = request.getHeader(FORWARDED_FOR);
        if (forwarded != null && !forwarded.isBlank()) {
            String[] hops = forwarded.split(",");
            String last = hops[hops.length - 1].strip();
            if (!last.isEmpty()) {
                return last;
            }
        }
        return request.getRemoteAddr();
    }

    /** The same address as an {@link InetAddress}, or empty when it is not a literal IP. */
    public static Optional<InetAddress> inet(HttpServletRequest request) {
        String literal = of(request);
        if (literal == null || literal.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(InetAddress.ofLiteral(literal));
        } catch (IllegalArgumentException notALiteral) {
            return Optional.empty();
        }
    }
}
