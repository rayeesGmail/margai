package com.margai.auth.web;

import com.margai.account.api.UserSummary;
import com.margai.auth.internal.OtpChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * Request and response records of the auth endpoints (TECH_PLAN §3.7, §11.3: records, snake_case
 * on the wire, nulls omitted). Field-level rules are Bean Validation with reason codes as messages
 * (the envelope's {@code details} carry codes, never prose); "exactly one of phone or email" and the
 * identifier formats are checked by the controller and {@code Identifiers}.
 */
final class AuthPayloads {

    private AuthPayloads() {
    }

    /** {@code POST /auth/otp/request}: one of the two, never both (D7 ruling: email joins phone). */
    record OtpRequestBody(@Size(max = 24) String phone, @Size(max = 254) String email) {
    }

    /** {@code POST /auth/otp/verify}; {@code invite_code} is accepted now and honoured at D75. */
    record OtpVerifyBody(
            @NotNull UUID challengeId,
            @NotBlank @Pattern(regexp = "\\s*\\d{4,8}\\s*", message = "code.digits") String code,
            @Size(max = 16) String inviteCode) {
    }

    /** {@code POST /auth/refresh}. */
    record RefreshBody(@NotBlank @Size(max = 128) String refreshToken) {
    }

    record OtpRequestedResponse(UUID challengeId, long resendAfterS, OtpChannel channel) {
    }

    record SignedInResponse(String accessToken, String refreshToken, long expiresIn, boolean isNewUser, UserSummary user) {
    }

    record TokensResponse(String accessToken, String refreshToken, long expiresIn) {
    }
}
