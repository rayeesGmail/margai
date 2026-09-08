package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.account.api.LoginIdentifier;
import com.margai.common.api.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** DECISIONS 2026-09-08 (D7): Indian mobiles to E.164, emails lowercased, everything else a field error. */
class IdentifiersTest {

    @ParameterizedTest
    @ValueSource(strings = {"+919876543210", "9876543210", "09876543210", "919876543210", " 98765 43210 ",
            "+91-98765-43210", "(+91) 98765.43210"})
    void indianMobilesNormaliseToE164(String typed) {
        assertThat(Identifiers.phone(typed)).isEqualTo(new LoginIdentifier.Phone("+919876543210"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5876543210", "987654321", "98765432101", "+447700900123", "+91 12345 67890", "abc", ""})
    void otherPhonesAreAFieldError(String typed) {
        assertThatThrownBy(() -> Identifiers.phone(typed))
                .isInstanceOf(ValidationException.class)
                .satisfies(failure -> assertThat(((ValidationException) failure).details()).containsKey("phone"));
    }

    @Test
    void emailsAreTrimmedAndLowercased() {
        assertThat(Identifiers.email("  Founder@Example.COM ")).isEqualTo(new LoginIdentifier.Email("founder@example.com"));
        assertThat(Identifiers.email("a.b+tag@sub.example.co.in").address()).isEqualTo("a.b+tag@sub.example.co.in");
    }

    @ParameterizedTest
    @ValueSource(strings = {"founder", "a@b", "@example.com", "two@@example.com", "space in@example.com", ""})
    void malformedEmailsAreAFieldError(String typed) {
        assertThatThrownBy(() -> Identifiers.email(typed))
                .isInstanceOf(ValidationException.class)
                .satisfies(failure -> assertThat(((ValidationException) failure).details()).containsKey("email"));
    }

    @Test
    void overlongEmailIsRejectedAndNullsAreFieldErrors() {
        String tooLong = "x".repeat(250) + "@e.io";

        assertThatThrownBy(() -> Identifiers.email(tooLong)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> Identifiers.email(null)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> Identifiers.phone(null)).isInstanceOf(ValidationException.class);
    }

    @Test
    void masksKeepOnlyWhatSupportNeeds() {
        assertThat(Identifiers.mask(OtpChannel.sms, "+919876543210")).isEqualTo("+91XXXXXX3210");
        assertThat(Identifiers.mask(OtpChannel.email, "rayees@gmail.com")).isEqualTo("r***@gmail.com");
        assertThat(Identifiers.mask(new LoginIdentifier.Email("x@y.in"))).isEqualTo("x***@y.in");
        assertThat(Identifiers.mask(OtpChannel.email, "")).isEqualTo("?");
    }

    @Test
    void channelAndIdentifierRoundTrip() {
        assertThat(Identifiers.channelOf(new LoginIdentifier.Phone("+919876543210"))).isEqualTo(OtpChannel.sms);
        assertThat(Identifiers.channelOf(new LoginIdentifier.Email("a@b.io"))).isEqualTo(OtpChannel.email);
        assertThat(Identifiers.of(OtpChannel.sms, "+919876543210")).isEqualTo(new LoginIdentifier.Phone("+919876543210"));
        assertThat(Identifiers.of(OtpChannel.email, "a@b.io")).isEqualTo(new LoginIdentifier.Email("a@b.io"));
    }
}
