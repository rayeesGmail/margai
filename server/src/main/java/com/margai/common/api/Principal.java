package com.margai.common.api;

import java.util.Objects;
import java.util.UUID;

/**
 * The authenticated caller (TECH_PLAN §1.5 step 3: {@code {user_id, role, language}}), built by
 * the auth module from the JWT claims {@code sub}, {@code role} and {@code lang} and read by
 * every module's controllers. It lives in {@code common.api} because the error envelope's
 * {@code message_user_lang} needs the language and §1.4 gives feature modules no edge to
 * {@code auth} (DECISIONS 2026-09-08, D7).
 */
public record Principal(UUID userId, UserRole role, Language language) {

    /**
     * Request attribute under which the security layer publishes the principal, so that
     * {@code common} can resolve the caller's language without depending on Spring Security.
     */
    public static final String REQUEST_ATTRIBUTE = Principal.class.getName();

    public Principal {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(language, "language");
    }

    public boolean isAdmin() {
        return role == UserRole.admin;
    }
}
