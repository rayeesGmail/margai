/**
 * auth module (TECH_PLAN §1.3): OTP login, tokens, rate limits, the security filter chain, the
 * parent-consent OTP (every OTP-backed fact) and beta invite codes. Owns {@code otp_challenges}
 * and {@code refresh_tokens} from D7 ({@code parent_consents} D27, {@code invite_codes} D75).
 * Allowed dependencies per §1.4: {@code common :: api}, {@code account :: api}. Only this module
 * imports an OTP delivery client — the SMS client of §1.4 and, since the D7 founder ruling
 * (DECISIONS 2026-09-08), the SES client for the email channel.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "auth",
        allowedDependencies = {"common :: api", "account :: api"})
package com.margai.auth;
