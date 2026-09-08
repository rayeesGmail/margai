package com.margai.auth.internal;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * {@code margai.limits.*} (TECH_PLAN §3.4, §7.3): the request-rate limits, values from config
 * with the plan's defaults in {@code application.yml}. The per-destination OTP cap is applied by
 * {@code OtpService} against {@code otp_challenges}; the other three by {@code RateLimitFilter}.
 *
 * @param otpRequestPerDestinationHourly codes one phone or email may request per hour
 * @param otpRequestPerIpHourly          OTP requests one client address may make per hour
 * @param publicAuthPerIpPerMinute       verify and refresh calls one client address may make per minute (§1.5 step 4)
 * @param authenticatedPerMinute         requests one signed-in user may make per minute
 */
@ConfigurationProperties(prefix = "margai.limits")
@Validated
public record RateLimitProperties(
        @Min(1) int otpRequestPerDestinationHourly,
        @Min(1) int otpRequestPerIpHourly,
        @Min(1) int publicAuthPerIpPerMinute,
        @Min(1) int authenticatedPerMinute) {
}
