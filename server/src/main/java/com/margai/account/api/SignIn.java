package com.margai.account.api;

/** Outcome of a verified login: the account, and whether this login created it ({@code is_new_user}, TECH_PLAN §3.7). */
public record SignIn(UserSummary user, boolean isNew) {
}
