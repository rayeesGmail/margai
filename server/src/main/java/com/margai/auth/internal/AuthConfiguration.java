package com.margai.auth.internal;

import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configuration-derived beans of the auth module: key material, the JWT service (TECH_PLAN §3.2,
 * §11.5) and the client-clock diagnostics filter on the public auth routes (§3.1, PLAN D9).
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({AuthProperties.class, RateLimitProperties.class})
class AuthConfiguration {

    @Bean
    AuthKeys authKeys(AuthProperties properties) {
        return AuthKeys.from(properties);
    }

    @Bean
    JwtService jwtService(AuthKeys keys, AuthProperties properties, IstClock clock) {
        return new JwtService(keys, properties.jwt().accessTtl(), clock);
    }

    /**
     * A servlet filter (not a security-chain one): it must see every auth request, including the
     * ones the rate limiter turns away, and it runs right after {@code common}'s request-id filter
     * so its WARN can name the request. Registered here rather than as a component so that
     * {@code @WebMvcTest} slices, which pick up every {@code Filter} component, stay unaware of it.
     */
    @Bean
    FilterRegistrationBean<ClientTimeFilter> clientTimeFilter(AuthProperties properties, IstClock clock,
            MeterRegistry meters) {
        FilterRegistrationBean<ClientTimeFilter> registration = new FilterRegistrationBean<>(
                new ClientTimeFilter(clock, properties.clockSkewWarn(), meters));
        registration.addUrlPatterns(ClientTimeFilter.URL_PATTERN);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}
