package com.margai.auth.internal;

import com.margai.common.api.ApiException;
import com.margai.common.api.AuthException;
import com.margai.common.api.ErrorResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Security failures as the §3.3 envelope (TECH_PLAN §3.3 401 row): no usable token →
 * {@code AUTH_REQUIRED}; a token past its time → {@code AUTH_EXPIRED} (the app refreshes); a
 * token that fails signature or shape → {@code AUTH_INVALID} (the app returns to login); a valid
 * token without the right role → {@code FORBIDDEN}.
 */
final class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ErrorResponses responses;

    ApiAuthenticationEntryPoint(ErrorResponses responses) {
        this.responses = responses;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure)
            throws IOException {
        responses.write(request, response, classify(failure));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException denied)
            throws IOException {
        responses.write(request, response, AuthException.forbidden());
    }

    static ApiException classify(AuthenticationException failure) {
        if (failure instanceof InvalidBearerTokenException) {
            return JwtService.isExpired(failure) ? AuthException.expired() : AuthException.invalid();
        }
        return AuthException.required();
    }
}
