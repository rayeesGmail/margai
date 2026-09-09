package com.margai.account.internal;

import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.Me;
import com.margai.account.api.ProfileSummary;
import com.margai.account.api.SignIn;
import com.margai.account.api.UserSummary;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link Accounts} over {@code users} and {@code student_profiles} (TECH_PLAN §2.2, §3.7). A first
 * login creates the user row with the identifier the OTP verified, in the language the verify call
 * suggested (the app's device locale via {@code Accept-Language}: SPEC §5 "language auto-suggested,
 * changeable"; the D25 mentor intro and {@code PATCH /me} change it later), and the empty profile
 * row beside it. The profile is find-or-create on every login, so an account from before D10 heals
 * on its next sign-in. A phone login stamps {@code phone_verified_at} each time; an email account's
 * proof is the verified email itself. Simultaneous first logins for one identifier are serialised
 * on a per-identifier advisory lock held for the transaction (PLAN D9), so the second one finds the
 * rows the first one created.
 */
@Service
@Transactional
class AccountService implements Accounts {

    static final String LOCK_PREFIX = "users:";

    private final UserRepository users;
    private final StudentProfileRepository profiles;
    private final IstClock clock;

    AccountService(UserRepository users, StudentProfileRepository profiles, IstClock clock) {
        this.users = users;
        this.profiles = profiles;
        this.clock = clock;
    }

    @Override
    public SignIn signIn(LoginIdentifier identifier, Language suggested) {
        users.lockIdentifier(LOCK_PREFIX + identifier.value());
        Optional<User> existing = switch (identifier) {
            case LoginIdentifier.Phone phone -> users.findByPhoneAndStatus(phone.e164(), UserStatus.active);
            case LoginIdentifier.Email email -> users.findByEmailAndStatus(email.address(), UserStatus.active);
        };
        User user = existing.orElseGet(() -> users.save(newUser(identifier, suggested)));
        if (identifier instanceof LoginIdentifier.Phone) {
            user.markPhoneVerified(clock.now());
        }
        profiles.findByUserId(user.getId()).orElseGet(() -> profiles.save(new StudentProfile(user.getId())));
        return new SignIn(summary(user), existing.isEmpty());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSummary> findActive(UUID userId) {
        return users.findById(userId)
                .filter(user -> user.getStatus() == UserStatus.active)
                .map(AccountService::summary);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Me> me(UUID userId) {
        return users.findById(userId)
                .filter(user -> user.getStatus() == UserStatus.active)
                .flatMap(user -> profiles.findByUserId(userId)
                        .map(profile -> new Me(summary(user), summary(profile))));
    }

    private static ProfileSummary summary(StudentProfile profile) {
        return new ProfileSummary(profile.getAttemptType(), profile.getTargetYear(), profile.getCoachingMode(),
                profile.getCoachingProvider(), profile.getHoursWeekday(), profile.getHoursWeekend(), profile.getGoal(),
                profile.getStateCode(), profile.getCategory(), profile.getDob(), profile.isMinor(),
                profile.getLastNeetYear(), profile.getLastNeetScore(), profile.getLastNeetRank(),
                profile.getOnboardingStep(), profile.getOnboardingCompletedAt(), profile.getExamDate(),
                profile.getMorningNotificationTime(), profile.getCurrentStreak(), profile.getLongestStreak());
    }

    private static User newUser(LoginIdentifier identifier, Language language) {
        return switch (identifier) {
            case LoginIdentifier.Phone phone -> new User(phone.e164(), language);
            case LoginIdentifier.Email email -> User.withEmail(email.address(), language);
        };
    }

    private static UserSummary summary(User user) {
        return new UserSummary(user.getId(), user.getPhone(), user.getEmail(), user.getLanguage(), user.getRole(),
                user.getDisplayName());
    }
}
