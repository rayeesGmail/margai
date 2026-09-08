package com.margai.auth.internal;

import com.margai.common.api.IstClock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration-derived beans of the auth module: key material and the JWT service (TECH_PLAN §3.2, §11.5). */
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
}
