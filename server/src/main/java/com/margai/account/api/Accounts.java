package com.margai.account.api;

import java.util.Optional;
import java.util.UUID;

/**
 * The account module's door for the auth module (TECH_PLAN §1.3, §1.4 {@code account → auth}):
 * a verified identifier becomes an account, and a token refresh re-reads the account's current
 * role and language (§3.8). The empty {@code student_profiles} row and {@code /me} arrive at D10.
 */
public interface Accounts {

    /** Finds the active account for a verified identifier or creates it ({@code language = en}). */
    SignIn signIn(LoginIdentifier identifier);

    /** The account when it exists and is not deleted (§2.10: deletion revokes every token). */
    Optional<UserSummary> findActive(UUID userId);
}
