package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

/** TECH_PLAN §9.1 and §10.1: user id and jti in the MDC during the request, gone after; the principal published for common. */
class PrincipalContextFilterTest {

    private final PrincipalContextFilter filter = new PrincipalContextFilter();

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void publishesThePrincipalAndScopesTheMdcToTheRequest() throws Exception {
        Principal principal = new Principal(UUID.randomUUID(), UserRole.admin, Language.en);
        SecurityContextHolder.getContext().setAuthentication(new PrincipalAuthentication(principal, "jti-1"));
        MockHttpServletRequest request = new MockHttpServletRequest();
        AtomicReference<String> userIdInside = new AtomicReference<>();
        AtomicReference<String> jtiInside = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse res) {
                userIdInside.set(MDC.get(PrincipalContextFilter.USER_ID_MDC_KEY));
                jtiInside.set(MDC.get(PrincipalContextFilter.JTI_MDC_KEY));
            }
        });

        assertThat(request.getAttribute(Principal.REQUEST_ATTRIBUTE)).isEqualTo(principal);
        assertThat(userIdInside.get()).isEqualTo(principal.userId().toString());
        assertThat(jtiInside.get()).isEqualTo("jti-1");
        assertThat(MDC.get(PrincipalContextFilter.USER_ID_MDC_KEY)).isNull();
        assertThat(MDC.get(PrincipalContextFilter.JTI_MDC_KEY)).isNull();
    }

    @Test
    void anonymousRequestsPassUntouched() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain());

        assertThat(request.getAttribute(Principal.REQUEST_ATTRIBUTE)).isNull();
    }
}
