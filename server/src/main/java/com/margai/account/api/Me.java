package com.margai.account.api;

/**
 * {@code GET /me} and the answer of {@code PATCH /me} (TECH_PLAN §3.7): the account and its profile
 * in one call on app start. {@code subscription} (D61), {@code limits} (D37) and
 * {@code consent_state} (D27) join as optional fields on their days (§3.1: additive within v1;
 * DECISIONS D10).
 */
public record Me(UserSummary user, ProfileSummary profile) {
}
