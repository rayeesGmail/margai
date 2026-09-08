package com.margai.account.api;

/**
 * What a student proved they own by completing an OTP: an E.164 phone, or — since the D7 founder
 * ruling (DECISIONS 2026-09-08) — a lowercased email. The auth module normalises the input; this
 * type carries the normalised value only.
 */
public sealed interface LoginIdentifier permits LoginIdentifier.Phone, LoginIdentifier.Email {

    String value();

    record Phone(String e164) implements LoginIdentifier {
        @Override
        public String value() {
            return e164;
        }
    }

    record Email(String address) implements LoginIdentifier {
        @Override
        public String value() {
            return address;
        }
    }
}
