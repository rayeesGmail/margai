package com.margai.account.internal;

import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.SignIn;
import com.margai.account.api.UserSummary;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link Accounts} over {@code users} (TECH_PLAN §2.2, §3.7). A first login creates the row with
 * the identifier the OTP verified; the language starts at {@code en} until the mentor intro
 * (SPEC §5, D25) or {@code PATCH /me} (D10) sets it. A phone login stamps
 * {@code phone_verified_at} each time; an email account's proof is the verified email itself.
 * Simultaneous first logins for one identifier are serialised on a per-identifier advisory lock
 * held for the transaction (PLAN D9), so the second one finds the row the first one created.
 */
@Service
@Transactional
class AccountService implements Accounts {

    static final String LOCK_PREFIX = "users:";

    private final UserRepository users;
    private final IstClock clock;

    AccountService(UserRepository users, IstClock clock) {
        this.users = users;
        this.clock = clock;
    }

    @Override
    public SignIn signIn(LoginIdentifier identifier) {
        users.lockIdentifier(LOCK_PREFIX + identifier.value());
        Optional<User> existing = switch (identifier) {
            case LoginIdentifier.Phone phone -> users.findByPhoneAndStatus(phone.e164(), UserStatus.active);
            case LoginIdentifier.Email email -> users.findByEmailAndStatus(email.address(), UserStatus.active);
        };
        User user = existing.orElseGet(() -> users.save(newUser(identifier)));
        if (identifier instanceof LoginIdentifier.Phone) {
            user.markPhoneVerified(clock.now());
        }
        return new SignIn(summary(user), existing.isEmpty());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSummary> findActive(UUID userId) {
        return users.findById(userId)
                .filter(user -> user.getStatus() == UserStatus.active)
                .map(AccountService::summary);
    }

    private static User newUser(LoginIdentifier identifier) {
        return switch (identifier) {
            case LoginIdentifier.Phone phone -> new User(phone.e164(), Language.en);
            case LoginIdentifier.Email email -> User.withEmail(email.address(), Language.en);
        };
    }

    private static UserSummary summary(User user) {
        return new UserSummary(user.getId(), user.getPhone(), user.getEmail(), user.getLanguage(), user.getRole(),
                user.getDisplayName());
    }
}
