package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.IstClock;
import com.margai.common.api.OtpException;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * PLAN D9 row 10 through the Spring bean rather than a hand-built instance: the boot-time
 * retirement runs on the proxied {@link OtpStartup} (its {@code @Transactional} wraps the bulk
 * update), and the verify that follows answers {@code OTP_EXPIRED}. Test contexts run with a blank
 * pepper, which is exactly the ephemeral case.
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class OtpStartupFlowTest {

    @Autowired
    private OtpStartup startup;

    @Autowired
    private OtpChallengeRepository challenges;

    @Autowired
    private OtpService otp;

    @Autowired
    private AuthKeys keys;

    @Autowired
    private IstClock clock;

    @Test
    void theBootPathRetiresAPendingCodeSoVerifyAnswersExpired() {
        assertThat(keys.otpPepperEphemeral()).as("test contexts run with a blank pepper").isTrue();
        UUID id = UUID.randomUUID();
        Instant now = clock.now();
        challenges.save(new OtpChallenge(id, OtpChannel.email, "boot-" + id + "@example.com", OtpPurpose.login,
                OtpCodes.hash(keys.otpPepper(), id, "123456"), now.plusSeconds(300), null, now));

        startup.retirePendingCodes();

        assertThatThrownBy(() -> otp.verify(id, "123456", "test"))
                .isInstanceOf(OtpException.class)
                .extracting(failure -> ((OtpException) failure).code())
                .isEqualTo(ErrorCode.OTP_EXPIRED);
        assertThat(challenges.findById(id).orElseThrow().isExpired(clock.now())).isTrue();
    }
}
