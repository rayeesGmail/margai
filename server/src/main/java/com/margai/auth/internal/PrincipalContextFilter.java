package com.margai.auth.internal;

import com.margai.common.api.Principal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * After bearer authentication: publishes the {@link Principal} as a request attribute so that
 * {@code common} can pick the response language without depending on Spring Security (TECH_PLAN
 * §3.8), and puts {@code user_id} and {@code jti} in the MDC for the rest of the request
 * (§9.1 "jti logged with every request", §10.1).
 */
final class PrincipalContextFilter extends OncePerRequestFilter {

    static final String USER_ID_MDC_KEY = "user_id";
    static final String JTI_MDC_KEY = "jti";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof PrincipalAuthentication authentication) {
            Principal principal = authentication.getPrincipal();
            request.setAttribute(Principal.REQUEST_ATTRIBUTE, principal);
            MDC.put(USER_ID_MDC_KEY, principal.userId().toString());
            MDC.put(JTI_MDC_KEY, authentication.jti());
            try {
                chain.doFilter(request, response);
            } finally {
                MDC.remove(USER_ID_MDC_KEY);
                MDC.remove(JTI_MDC_KEY);
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
