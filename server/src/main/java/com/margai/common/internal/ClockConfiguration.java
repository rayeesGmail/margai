package com.margai.common.internal;

import com.margai.common.api.IstClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** One {@link IstClock} bean for the whole application (TECH_PLAN §11.1). */
@Configuration(proxyBeanMethods = false)
class ClockConfiguration {

    @Bean
    IstClock istClock() {
        return IstClock.system();
    }
}
