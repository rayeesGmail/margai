package com.margai.auth.internal;

import com.margai.common.api.ErrorResponses;
import com.margai.common.api.IstClock;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.PathContainer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * TECH_PLAN §1.5 steps 3–4 and §9.1: one stateless chain. Public routes are {@code /auth/otp/*},
 * {@code /auth/refresh}, {@code /billing/webhook} and {@code /actuator/health}, where a bearer is
 * not even read (D10); everything else — {@code /auth/logout} included — needs a bearer JWT, which
 * becomes a {@link PrincipalAuthentication}; the rest of {@code /actuator/**} (metrics, D11) needs
 * the admin role on top, decided here because actuator endpoints carry no {@code @PreAuthorize}
 * of their own (§9.3). After authentication the
 * {@link PrincipalContextFilter} publishes the principal and the {@link RateLimitFilter} applies
 * §3.4. Failures are written as the §3.3 envelope by {@link ApiAuthenticationEntryPoint}. No
 * sessions, CSRF, form or basic login, no request cache: a mobile API.
 */
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
class SecurityConfiguration {

    /**
     * §1.5 step 3's four public routes plus {@code /error}: Boot forwards there when an exception
     * escapes a filter, and the authorization filter also runs on that ERROR dispatch, so without
     * the entry the student would see a bare 401 instead of the envelope (DECISIONS 2026-09-08, D7).
     */
    static final String[] PUBLIC_ROUTES = {
            "/api/v1/auth/otp/**",
            "/api/v1/auth/refresh",
            "/api/v1/billing/webhook",
            "/actuator/health",
            "/error"
    };

    /** Every actuator endpoint but health (matched first, above): the founder's metrics view (D11). */
    static final String ACTUATOR_ROUTES = "/actuator/**";
    /** {@code hasRole} adds the {@code ROLE_} prefix; {@link PrincipalAuthentication} grants {@code ROLE_ADMIN}. */
    static final String ADMIN_ROLE = "ADMIN";

    private static final List<PathPattern> PUBLIC_PATTERNS = Arrays.stream(PUBLIC_ROUTES)
            .map(PathPatternParser.defaultInstance::parse)
            .toList();

    @Bean
    JwtDecoder jwtDecoder(JwtService jwts) {
        return jwts::decode;
    }

    /**
     * On a public route the bearer is not read at all (D10, DECISIONS): Spring's bearer filter would
     * otherwise reject a stale or foreign token even where no token is required, and an app whose
     * access token has expired must still reach {@code /auth/refresh} to replace it.
     */
    static BearerTokenResolver bearerExceptOnPublicRoutes() {
        DefaultBearerTokenResolver headers = new DefaultBearerTokenResolver();
        return request -> isPublic(request) ? null : headers.resolve(request);
    }

    static boolean isPublic(HttpServletRequest request) {
        PathContainer path = PathContainer.parsePath(request.getRequestURI());
        return PUBLIC_PATTERNS.stream().anyMatch(pattern -> pattern.matches(path));
    }

    @Bean
    SecurityFilterChain apiSecurity(HttpSecurity http, JwtDecoder decoder, JwtService jwts, ErrorResponses responses,
            RateLimitProperties limits, IstClock clock) throws Exception {
        ApiAuthenticationEntryPoint failures = new ApiAuthenticationEntryPoint(responses);
        // Built here, not as beans: a Filter bean would also be registered with the servlet container and run twice.
        PrincipalContextFilter principalContext = new PrincipalContextFilter();
        RateLimitFilter rateLimits = new RateLimitFilter(limits, clock, responses);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(routes -> routes
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers(ACTUATOR_ROUTES).hasRole(ADMIN_ROLE)
                        .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .bearerTokenResolver(bearerExceptOnPublicRoutes())
                        .jwt(jwt -> jwt
                                .decoder(decoder)
                                .jwtAuthenticationConverter(token -> new PrincipalAuthentication(
                                        jwts.toPrincipal(token), token.getId())))
                        .authenticationEntryPoint(failures)
                        .accessDeniedHandler(failures))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(failures)
                        .accessDeniedHandler(failures))
                .addFilterAfter(principalContext, BearerTokenAuthenticationFilter.class)
                .addFilterAfter(rateLimits, PrincipalContextFilter.class);
        return http.build();
    }
}
