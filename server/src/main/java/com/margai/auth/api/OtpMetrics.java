package com.margai.auth.api;

/**
 * The auth module's door for the ops module (TECH_PLAN §1.3, §10.3; PLAN D11): the OTP delivery
 * numbers behind SPEC §11's "OTP success ≥ 98% first attempt", computed on demand from the §10.2
 * counters and the challenge table. Read-only.
 */
public interface OtpMetrics {

    OtpDeliveryReport report();
}
