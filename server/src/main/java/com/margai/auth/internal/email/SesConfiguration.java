package com.margai.auth.internal.email;

import com.margai.auth.internal.AuthProperties;
import com.margai.auth.internal.OtpSender;
import com.margai.common.api.Messages;
import java.time.Duration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;

/**
 * The email channel over Amazon SES v2 (founder decision 2 at the D7 plan review, 2026-09-08):
 * active when {@code margai.auth.otp.sender = ses}. Credentials come from the SDK default chain —
 * the founder's {@code aws login} session locally, the task role in AWS (TECH_PLAN §7.4) — never
 * from the tree. A blank {@code margai.auth.otp.email-from} refuses to start: SES rejects
 * unverified senders and a silent misconfiguration would only show up as failed logins.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "margai.auth.otp", name = "sender", havingValue = "ses")
class SesConfiguration {

    @Bean
    SesV2Client sesV2Client(AuthProperties properties) {
        return SesV2Client.builder()
                .region(Region.of(properties.otp().sesRegion()))
                .overrideConfiguration(override -> override.apiCallTimeout(Duration.ofSeconds(15)))
                .build();
    }

    @Bean
    OtpSender sesOtpSender(SesV2Client ses, AuthProperties properties, Messages messages) {
        String from = properties.otp().emailFrom();
        if (from == null || from.isBlank()) {
            throw new IllegalStateException(
                    "margai.auth.otp.email-from must name a verified SES identity when margai.auth.otp.sender = ses");
        }
        return new SesOtpSender(ses, from.strip(), messages);
    }
}
