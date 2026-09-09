package com.margai.account.web;

import com.margai.account.api.Accounts;
import com.margai.account.api.Me;
import com.margai.account.web.MePayloads.UpdateBody;
import com.margai.common.api.AuthException;
import com.margai.common.api.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code /me} (TECH_PLAN §3.7 account rows; SPEC §8 screen 13): thin, one service call each
 * (§1.4), the caller from the {@link Principal} the chain published (§1.5 step 6). An account that
 * cannot be served — deleted, or without its profile row — is {@code AUTH_INVALID}: the app signs
 * out and a fresh login heals it (D10).
 */
@RestController
@RequestMapping("/api/v1/me")
class MeController {

    private final Accounts accounts;

    MeController(Accounts accounts) {
        this.accounts = accounts;
    }

    @GetMapping
    Me me(Principal caller) {
        return accounts.me(caller.userId()).orElseThrow(AuthException::invalid);
    }

    /** {@code PATCH /me} (§3.7): absent fields stay as they are; the answer is the same shape as {@code GET /me}. */
    @PatchMapping
    Me update(@RequestBody UpdateBody body, Principal caller) {
        return accounts.update(caller.userId(), body.toUpdate()).orElseThrow(AuthException::invalid);
    }
}
