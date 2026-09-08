package com.margai.auth.internal;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * The caller's address behind the ALB (TECH_PLAN §1.5 step 1): the first {@code X-Forwarded-For}
 * hop when present, else the socket address. Used as a rate-limit key (§3.4) and recorded on
 * OTP challenges ({@code request_ip}, §2.2).
 */
public final class ClientAddress {

    static final String FORWARDED_FOR = "X-Forwarded-For";

    private ClientAddress() {
    }

    public static String of(HttpServletRequest request) {
        String forwarded = request.getHeader(FORWARDED_FOR);
        if (forwarded != null && !forwarded.isBlank()) {
            String first = forwarded.split(",")[0].strip();
            if (!first.isEmpty()) {
                return first;
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
