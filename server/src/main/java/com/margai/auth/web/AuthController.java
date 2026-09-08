package com.margai.auth.web;

import com.margai.account.api.LoginIdentifier;
import com.margai.auth.internal.ClientAddress;
import com.margai.auth.internal.Identifiers;
import com.margai.auth.internal.OtpRequested;
import com.margai.auth.internal.OtpService;
import com.margai.auth.internal.OtpVerified;
import com.margai.auth.internal.TokenPair;
import com.margai.auth.internal.TokenService;
import com.margai.auth.web.AuthPayloads.OtpRequestBody;
import com.margai.auth.web.AuthPayloads.OtpRequestedResponse;
import com.margai.auth.web.AuthPayloads.OtpVerifyBody;
import com.margai.auth.web.AuthPayloads.RefreshBody;
import com.margai.auth.web.AuthPayloads.SignedInResponse;
import com.margai.auth.web.AuthPayloads.TokensResponse;
import com.margai.common.api.RequestLanguage;
import com.margai.common.api.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The public auth routes (TECH_PLAN §3.7; SPEC §8 screen 1): thin, one service call each
 * (§1.4). {@code X-App-Version} (else {@code User-Agent}) labels the device's token family;
 * {@code Accept-Language} picks the language of the code's message (§3.8). Bodies are never logged
 * on these routes (§9.6) — nothing here logs at all.
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    static final int DEVICE_LABEL_MAX = 80;

    private final OtpService otp;
    private final TokenService tokens;

    AuthController(OtpService otp, TokenService tokens) {
        this.otp = otp;
        this.tokens = tokens;
    }

    @PostMapping("/otp/request")
    OtpRequestedResponse request(@Valid @RequestBody OtpRequestBody body, HttpServletRequest request) {
        OtpRequested requested = otp.request(identifierOf(body), ClientAddress.inet(request).orElse(null),
                RequestLanguage.of(request));
        return new OtpRequestedResponse(requested.challengeId(), requested.resendAfterSeconds(), requested.channel());
    }

    @PostMapping("/otp/verify")
    SignedInResponse verify(@Valid @RequestBody OtpVerifyBody body, HttpServletRequest request) {
        OtpVerified verified = otp.verify(body.challengeId(), body.code(), deviceLabel(request));
        TokenPair pair = verified.tokens();
        return new SignedInResponse(pair.accessToken(), pair.refreshToken(), pair.expiresIn(), verified.isNewUser(),
                verified.user());
    }

    @PostMapping("/refresh")
    TokensResponse refresh(@Valid @RequestBody RefreshBody body) {
        TokenPair pair = tokens.refresh(body.refreshToken());
        return new TokensResponse(pair.accessToken(), pair.refreshToken(), pair.expiresIn());
    }

    private static LoginIdentifier identifierOf(OtpRequestBody body) {
        boolean hasPhone = body.phone() != null && !body.phone().isBlank();
        boolean hasEmail = body.email() != null && !body.email().isBlank();
        if (hasPhone == hasEmail) {
            String message = hasPhone ? "send either phone or email, not both" : "send phone or email";
            throw ValidationException.of(Map.of("phone", message, "email", message));
        }
        return hasPhone ? Identifiers.phone(body.phone()) : Identifiers.email(body.email());
    }

    static String deviceLabel(HttpServletRequest request) {
        String label = request.getHeader("X-App-Version");
        if (label == null || label.isBlank()) {
            label = request.getHeader("User-Agent");
        }
        if (label == null || label.isBlank()) {
            return null;
        }
        label = label.strip();
        return label.length() <= DEVICE_LABEL_MAX ? label : label.substring(0, DEVICE_LABEL_MAX);
    }
}
