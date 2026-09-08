package com.margai.auth.internal;

import com.margai.account.api.UserSummary;

/** {@code POST /auth/otp/verify} outcome (TECH_PLAN §3.7): tokens, the account, and whether this login created it. */
public record OtpVerified(TokenPair tokens, UserSummary user, boolean isNewUser) {
}
