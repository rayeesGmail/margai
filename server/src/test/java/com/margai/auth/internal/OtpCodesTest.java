package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §3.2, §9.1: six digits, a 64-hex hash salted by the challenge id, constant-time comparison. */
class OtpCodesTest {

    private final byte[] pepper = "pepper-for-tests-only-32-bytes!!".getBytes();

    @Test
    void codesAreDigitsOfTheRequestedLength() {
        for (int i = 0; i < 50; i++) {
            assertThat(OtpCodes.generate(6)).matches("\\d{6}");
        }
        assertThat(OtpCodes.generate(4)).matches("\\d{4}");
    }

    @Test
    void hashIsSixtyFourHexAndSaltedByTheChallenge() {
        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        String hash = OtpCodes.hash(pepper, one, "123456");

        assertThat(hash).matches("[0-9a-f]{64}");
        assertThat(OtpCodes.hash(pepper, two, "123456")).isNotEqualTo(hash);
        assertThat(OtpCodes.hash("other-pepper-with-32-bytes-inside".getBytes(), one, "123456")).isNotEqualTo(hash);
        assertThat(hash).doesNotContain("123456");
    }

    @Test
    void matchesToleratesWhitespaceAndRejectsTheRest() {
        UUID id = UUID.randomUUID();
        String hash = OtpCodes.hash(pepper, id, "654321");

        assertThat(OtpCodes.matches(hash, pepper, id, "654321")).isTrue();
        assertThat(OtpCodes.matches(hash, pepper, id, " 654321 ")).isTrue();
        assertThat(OtpCodes.matches(hash, pepper, id, "654322")).isFalse();
        assertThat(OtpCodes.matches(hash, pepper, UUID.randomUUID(), "654321")).isFalse();
        assertThat(OtpCodes.matches(hash, pepper, id, null)).isFalse();
        assertThat(OtpCodes.matches(null, pepper, id, "654321")).isFalse();
    }
}
