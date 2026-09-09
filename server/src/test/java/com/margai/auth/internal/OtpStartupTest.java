package com.margai.auth.internal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.margai.common.api.IstClock;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import org.junit.jupiter.api.Test;

/**
 * PLAN D9 checklist row 10: a restart with a per-process pepper (blank
 * {@code margai.auth.otp.pepper}, the D7 DECISIONS row) cannot verify any code sent before it,
 * so those codes are retired at boot and answer {@code OTP_EXPIRED} ("ask for a new one") instead
 * of five {@code OTP_INVALID}s. A configured pepper survives restarts, so a deploy leaves in-flight
 * codes alone.
 */
class OtpStartupTest {

    private static final Instant NOW = Instant.parse("2026-09-09T06:00:00Z");
    private static final IstClock CLOCK = new IstClock(Clock.fixed(NOW, ZoneOffset.UTC));

    private final OtpChallengeRepository challenges = mock(OtpChallengeRepository.class);

    @Test
    void anEphemeralPepperRetiresEveryPendingCode() {
        AuthKeys ephemeral = AuthKeys.from(AuthKeysTest.properties("", "", ""));
        when(challenges.retireLive(any())).thenReturn(3);

        new OtpStartup(ephemeral, challenges, CLOCK).retirePendingCodes();

        verify(challenges).retireLive(NOW);
    }

    @Test
    void aConfiguredPepperLeavesPendingCodesAlone() {
        String pepper = Base64.getEncoder().encodeToString(new byte[32]);
        AuthKeys configured = AuthKeys.from(AuthKeysTest.properties("", "", pepper));

        new OtpStartup(configured, challenges, CLOCK).retirePendingCodes();

        verifyNoInteractions(challenges);
    }
}
