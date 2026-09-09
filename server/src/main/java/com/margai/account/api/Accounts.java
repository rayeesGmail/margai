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

    /**
     * {@code GET /me} (§3.7): the active account with its profile row; empty when either is
     * missing, which the caller answers as {@code AUTH_INVALID} (D10).
     */
    Optional<Me> me(UUID userId);

    /**
     * {@code PATCH /me} (§3.7): applies the present fields and answers the new {@code me}; empty
     * when the account cannot be served (as {@link #me}). A language change reaches the JWT on the
     * next refresh (§3.8) and affects new content only (SPEC §6.11).
     */
    Optional<Me> update(UUID userId, ProfileUpdate update);
}
