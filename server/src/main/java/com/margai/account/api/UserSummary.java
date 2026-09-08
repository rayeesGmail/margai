package com.margai.account.api;

import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.util.UUID;

/**
 * The {@code user} object other modules and the wire see (TECH_PLAN §3.7 verify response; the
 * {@code /me} payload grows around it at D10). Nulls are omitted on the wire (§11.3), so an
 * email-only account simply has no {@code phone}.
 */
public record UserSummary(UUID id, String phone, String email, Language language, UserRole role, String displayName) {

    public Principal toPrincipal() {
        return new Principal(id, role, language);
    }
}
