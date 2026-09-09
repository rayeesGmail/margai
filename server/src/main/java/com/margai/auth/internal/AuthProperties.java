package com.margai.auth.internal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * {@code margai.auth.*} (TECH_PLAN §3.2, §7.3, §9.1, §11.5): token lifetimes and keys, OTP
 * policy and delivery. Secrets arrive as environment variables from SSM
 * ({@code MARGAI_AUTH_JWT_SECRET}, {@code MARGAI_AUTH_OTP_PEPPER}); a blank one yields a random
 * value per boot with a WARN (DECISIONS 2026-09-08, D7) — never a fixed key in the tree.
 *
 * @param jwt           access-token signing and lifetimes
 * @param otp           code policy, enabled channels and the delivery adapter
 * @param clockSkewWarn drift between {@code X-Client-Time} and the server clock above which the
 *                      auth routes warn and count it (§3.1 clock-skew diagnostics, PLAN D9)
 */
@ConfigurationProperties(prefix = "margai.auth")
@Validated
public record AuthProperties(@NotNull @Valid Jwt jwt, @NotNull @Valid Otp otp, @NotNull Duration clockSkewWarn) {

    /**
     * @param secret         base64 256-bit HS256 key; blank → ephemeral
     * @param secretPrevious the previous key, accepted while configured (§9.2 rotation window)
     * @param accessTtl      access-token lifetime (§3.2: 15 minutes)
     * @param refreshTtl     refresh-token lifetime (§3.2: 30 days)
     */
    public record Jwt(String secret, String secretPrevious, @NotNull Duration accessTtl, @NotNull Duration refreshTtl) {
    }

    /**
     * @param pepper         secret mixed into every code hash; blank → ephemeral
     * @param ttl            code lifetime (§3.2: 5 minutes)
     * @param maxAttempts    wrong codes per challenge (§3.4: 5)
     * @param resendCooldown minimum gap between codes to one destination (§3.2: 30 seconds)
     * @param codeLength     digits per code
     * @param channels       channels a student may request a code on; {@code sms} joins when F1 lands
     * @param sender         the delivery adapter: {@code log} (sandbox) or {@code ses}
     * @param emailFrom      verified SES sender identity; required when {@code sender = ses}
     * @param sesRegion      region of the SES endpoint
     * @param reportEvery    how often the delivery report is written to the log (§10.1; PLAN D11
     *                       "delivery-rate logging"); also the wait before the first line
     */
    public record Otp(
            String pepper,
            @NotNull Duration ttl,
            @Min(1) int maxAttempts,
            @NotNull Duration resendCooldown,
            @Min(4) @Max(8) int codeLength,
            @NotEmpty Set<OtpChannel> channels,
            @NotNull Sender sender,
            String emailFrom,
            @NotBlank String sesRegion,
            @NotNull Duration reportEvery) {

        public Otp {
            channels = Set.copyOf(channels);
        }

        public boolean allows(OtpChannel channel) {
            return channels.contains(channel);
        }
    }

    /** The configured {@code OtpSender} implementation. */
    public enum Sender {
        log, ses
    }
}
