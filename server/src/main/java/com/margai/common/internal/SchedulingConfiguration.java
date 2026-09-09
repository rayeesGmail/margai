package com.margai.common.internal;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Switches on {@code @Scheduled} for the whole application (TECH_PLAN §1.2: the {@code api} task
 * runs its in-process dispatcher, sweepers and, since D11, the OTP delivery-rate line). Lives in
 * {@code common} so no feature module owns the platform switch; each schedule stays with the
 * module whose work it is, and its interval is that module's config (§11.5).
 */
@Configuration(proxyBeanMethods = false)
@EnableScheduling
class SchedulingConfiguration {
}
