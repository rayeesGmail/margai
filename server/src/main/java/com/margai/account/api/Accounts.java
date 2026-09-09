package com.margai.account.api;

import com.margai.common.api.Language;
import java.util.Optional;
import java.util.UUID;

/**
 * The account module's door for the auth module (TECH_PLAN §1.3, §1.4 {@code account → auth}):
 * a verified identifier becomes an account with its empty {@code student_profiles} row (§3.7,
 * D10), and a token refresh re-reads the account's current role and language (§3.8).
 */
public interface Accounts {

    /**
     * Finds the active account for a verified identifier or creates it. {@code suggested} is the
     * language of the verify call ({@code Accept-Language}, the app's device locale): a new account
     * starts in it (SPEC §5 "language auto-suggested, changeable"; DECISIONS D10), an existing one
     * keeps what it has.
     */
    SignIn signIn(LoginIdentifier identifier, Language suggested);

    /** The account when it exists and is not deleted (§2.10: deletion revokes every token). */
    Optional<UserSummary> findActive(UUID userId);
}
