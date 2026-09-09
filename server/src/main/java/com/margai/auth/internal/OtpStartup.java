package com.margai.auth.internal;

import com.margai.common.api.IstClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * PLAN D9 checklist row 10. With a blank {@code margai.auth.otp.pepper} the pepper is random per
 * process (DECISIONS 2026-09-08, D7), so no code sent before this start can ever match: without
 * this step a student would see five {@code OTP_INVALID} "didn't match" answers for a code that
 * was right. Retiring every pending challenge at boot turns that into one honest
 * {@code OTP_EXPIRED} — "ask for a new one" — with the resend offered. A configured pepper
 * survives restarts, so a deploy leaves in-flight codes alone; a pepper <em>rotation</em> has
 * the same ≤ 5-minute effect as an ephemeral one (noted in the F8 runbook).
 */
@Component
class OtpStartup {

    private static final Logger log = LoggerFactory.getLogger(OtpStartup.class);

    private final AuthKeys keys;
    private final OtpChallengeRepository challenges;
    private final IstClock clock;

    OtpStartup(AuthKeys keys, OtpChallengeRepository challenges, IstClock clock) {
        this.keys = keys;
        this.challenges = challenges;
        this.clock = clock;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void retirePendingCodes() {
        if (!keys.otpPepperEphemeral()) {
            return;
        }
        int retired = challenges.retireLive(clock.now());
        if (retired > 0) {
            log.info("retired {} pending OTP code(s): the OTP pepper is per process, so codes sent before this start"
                    + " cannot be verified — students will be asked for a new one (D9)", retired);
        }
    }
}
