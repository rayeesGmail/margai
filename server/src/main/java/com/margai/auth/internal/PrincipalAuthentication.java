package com.margai.auth.internal;

import com.margai.common.api.Principal;
import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * The {@code Authentication} a valid bearer token becomes (TECH_PLAN §1.5 step 3): the
 * {@link Principal} plus {@code ROLE_<role>} for {@code @PreAuthorize} on admin routes (§9.3).
 * The token value is not retained.
 */
final class PrincipalAuthentication extends AbstractAuthenticationToken {

    private final Principal principal;
    private final String jti;

    PrincipalAuthentication(Principal principal, String jti) {
        super(List.of(new SimpleGrantedAuthority("ROLE_" + principal.role().name().toUpperCase())));
        this.principal = principal;
        this.jti = jti;
        setAuthenticated(true);
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    String jti() {
        return jti;
    }
}
