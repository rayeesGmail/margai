package com.margai.auth.internal;

import com.margai.account.api.LoginIdentifier;
import com.margai.common.api.ValidationException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Normalisation of what the student typed (TECH_PLAN §9.4; DECISIONS 2026-09-08, D7): Indian
 * mobiles only at beta — ten digits starting 6–9 with an optional {@code +91}, {@code 91} or
 * {@code 0} prefix and any spacing — stored as E.164; emails trimmed, lowercased and shape-checked,
 * stored as typed otherwise (no provider-specific canonicalisation; PARKED). Masks for logs (§9.6).
 */
final class Identifiers {

    static final int MAX_EMAIL_LENGTH = 254;

    private static final Pattern INDIAN_MOBILE = Pattern.compile("[6-9]\\d{9}");
    private static final Pattern PHONE_NOISE = Pattern.compile("[\\s().-]");
    private static final Pattern EMAIL_SHAPE = Pattern.compile("[^@\\s]{1,64}@[^@\\s.]+(\\.[^@\\s.]+)+");

    private Identifiers() {
    }

    static LoginIdentifier.Phone phone(String raw) {
        if (raw == null) {
            throw invalidPhone();
        }
        String digits = PHONE_NOISE.matcher(raw).replaceAll("");
        if (digits.startsWith("+91")) {
            digits = digits.substring(3);
        } else if (digits.length() == 12 && digits.startsWith("91")) {
            digits = digits.substring(2);
        } else if (digits.length() == 11 && digits.startsWith("0")) {
            digits = digits.substring(1);
        }
        if (!INDIAN_MOBILE.matcher(digits).matches()) {
            throw invalidPhone();
        }
        return new LoginIdentifier.Phone("+91" + digits);
    }

    static LoginIdentifier.Email email(String raw) {
        if (raw == null) {
            throw invalidEmail();
        }
        String address = raw.strip().toLowerCase(Locale.ROOT);
        if (address.length() > MAX_EMAIL_LENGTH || !EMAIL_SHAPE.matcher(address).matches()) {
            throw invalidEmail();
        }
        return new LoginIdentifier.Email(address);
    }

    static LoginIdentifier of(OtpChannel channel, String destination) {
        return switch (channel) {
            case sms -> new LoginIdentifier.Phone(destination);
            case email -> new LoginIdentifier.Email(destination);
        };
    }

    static OtpChannel channelOf(LoginIdentifier identifier) {
        return switch (identifier) {
            case LoginIdentifier.Phone phone -> OtpChannel.sms;
            case LoginIdentifier.Email email -> OtpChannel.email;
        };
    }

    /** {@code +91XXXXXX1234} for phones, {@code r***@example.com} for emails (TECH_PLAN §9.6). */
    static String mask(OtpChannel channel, String destination) {
        if (destination == null || destination.isEmpty()) {
            return "?";
        }
        return switch (channel) {
            case sms -> destination.length() <= 4
                    ? "X".repeat(destination.length())
                    : destination.substring(0, Math.min(3, destination.length() - 4))
                            + "X".repeat(Math.max(0, destination.length() - 7))
                            + destination.substring(destination.length() - 4);
            case email -> {
                int at = destination.indexOf('@');
                yield at <= 0 ? "***" : destination.charAt(0) + "***" + destination.substring(at);
            }
        };
    }

    static String mask(LoginIdentifier identifier) {
        return mask(channelOf(identifier), identifier.value());
    }

    private static ValidationException invalidPhone() {
        return ValidationException.of("phone", "enter a 10-digit Indian mobile number");
    }

    private static ValidationException invalidEmail() {
        return ValidationException.of("email", "enter a valid email address");
    }
}
