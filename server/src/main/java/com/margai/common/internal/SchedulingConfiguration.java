package com.margai.common.internal;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Switches on scheduling — the {@code TaskScheduler} bean and {@code @Scheduled} — for the whole
 * application, in every profile (TECH_PLAN §1.2 as amended at D11: the {@code api} task runs its
 * in-process dispatcher, sweepers and the OTP delivery-rate line). Lives in {@code common} so no
 * feature module owns the platform switch; each schedule stays with the module whose work it is,
 * and its interval is that module's config (§11.5).
 */
@Configuration(proxyBeanMethods = false)
@EnableScheduling
class SchedulingConfiguration {
}
