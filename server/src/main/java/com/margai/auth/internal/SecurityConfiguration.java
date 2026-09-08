package com.margai.auth.internal;

import com.margai.common.api.ErrorResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * TECH_PLAN §1.5 step 3 and §9.1: one stateless chain. Public routes are {@code /auth/otp/*},
 * {@code /auth/refresh}, {@code /billing/webhook} and {@code /actuator/health}; everything else
 * needs a bearer JWT, which becomes a {@link PrincipalAuthentication}. Failures are written as
 * the §3.3 envelope by {@link ApiAuthenticationEntryPoint}. No sessions, CSRF, form or basic
 * login, no request cache: a mobile API.
 */
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
class SecurityConfiguration {

    static final String[] PUBLIC_ROUTES = {
            "/api/v1/auth/otp/**",
            "/api/v1/auth/refresh",
            "/api/v1/billing/webhook",
            "/actuator/health",
            "/error"
    };

    @Bean
    JwtDecoder jwtDecoder(JwtService jwts) {
        return jwts::decode;
    }

    @Bean
    SecurityFilterChain apiSecurity(HttpSecurity http, JwtDecoder decoder, JwtService jwts, ErrorResponses responses)
            throws Exception {
        ApiAuthenticationEntryPoint failures = new ApiAuthenticationEntryPoint(responses);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(routes -> routes
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwt -> jwt
                                .decoder(decoder)
                                .jwtAuthenticationConverter(token -> new PrincipalAuthentication(
                                        jwts.toPrincipal(token), token.getId())))
                        .authenticationEntryPoint(failures)
                        .accessDeniedHandler(failures))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(failures)
                        .accessDeniedHandler(failures))
                .addFilterAfter(new PrincipalContextFilter(), BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}
