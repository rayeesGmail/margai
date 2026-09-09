package com.margai.account.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.Me;
import com.margai.account.api.SignIn;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.UserRole;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * TECH_PLAN §3.7: first login creates the account and its empty {@code student_profiles} row (D10),
 * later logins find both; a new account takes the verify call's suggested language (SPEC §5
 * "auto-suggested, changeable"; DECISIONS D10); phone logins stamp the verification time.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainersConfiguration.class, AccountService.class, AccountServiceTest.FixedClock.class})
class AccountServiceTest {

    static final Instant NOW = Instant.parse("2026-09-08T09:30:00Z");

    @TestConfiguration(proxyBeanMethods = false)
    static class FixedClock {
        @Bean
        IstClock istClock() {
            return new IstClock(Clock.fixed(NOW, ZoneOffset.UTC));
        }
    }

    @Autowired
    private AccountService service;

    @Autowired
    private UserRepository users;

    @Autowired
    private StudentProfileRepository profiles;

    @Test
    void emailLoginCreatesThenFindsTheAccount() {
        SignIn first = service.signIn(new LoginIdentifier.Email("first@example.com"), Language.en);
        SignIn again = service.signIn(new LoginIdentifier.Email("first@example.com"), Language.en);

        assertThat(first.isNew()).isTrue();
        assertThat(again.isNew()).isFalse();
        assertThat(again.user().id()).isEqualTo(first.user().id());
        assertThat(first.user().email()).isEqualTo("first@example.com");
        assertThat(first.user().phone()).isNull();
        assertThat(first.user().language()).isEqualTo(Language.en);
        assertThat(first.user().role()).isEqualTo(UserRole.student);
        assertThat(users.findById(first.user().id()).orElseThrow().getPhoneVerifiedAt()).isNull();
    }

    @Test
    void firstLoginCreatesTheEmptyProfileRowOnce() {
        SignIn first = service.signIn(new LoginIdentifier.Email("profile@example.com"), Language.en);
        service.signIn(new LoginIdentifier.Email("profile@example.com"), Language.en);

        StudentProfile profile = profiles.findByUserId(first.user().id()).orElseThrow();
        assertThat(profiles.findAll()).filteredOn(row -> row.getUserId().equals(first.user().id())).hasSize(1);
        assertThat(profile.getOnboardingStep()).isEqualTo("intro");
        assertThat(profile.getMorningNotificationTime()).isEqualTo(LocalTime.of(7, 0));
        assertThat(profile.getCurrentStreak()).isZero();
        assertThat(profile.getLongestStreak()).isZero();
        assertThat(profile.isMinor()).isFalse();
        assertThat(profile.getAttemptType()).isNull();
    }

    @Test
    void anAccountWithoutAProfileGetsOneOnItsNextLogin() {
        // A pre-D10 account: the users row exists, the profile row does not.
        SignIn first = service.signIn(new LoginIdentifier.Email("heal@example.com"), Language.en);
        profiles.delete(profiles.findByUserId(first.user().id()).orElseThrow());
        profiles.flush();
        assertThat(profiles.findByUserId(first.user().id())).isEmpty();

        SignIn again = service.signIn(new LoginIdentifier.Email("heal@example.com"), Language.en);

        assertThat(again.isNew()).isFalse();
        assertThat(profiles.findByUserId(first.user().id())).isPresent();
    }

    @Test
    void theSuggestedLanguageLandsOnANewAccountOnly() {
        SignIn first = service.signIn(new LoginIdentifier.Email("hindi@example.com"), Language.hi);
        SignIn again = service.signIn(new LoginIdentifier.Email("hindi@example.com"), Language.en);

        assertThat(first.user().language()).isEqualTo(Language.hi);
        assertThat(again.user().language()).as("a later login keeps the stored value").isEqualTo(Language.hi);
        assertThat(users.findById(first.user().id()).orElseThrow().getLanguage()).isEqualTo(Language.hi);
    }

    @Test
    void meReturnsTheAccountWithItsProfile() {
        SignIn signIn = service.signIn(new LoginIdentifier.Email("me@example.com"), Language.hinglish);

        Me me = service.me(signIn.user().id()).orElseThrow();

        assertThat(me.user()).isEqualTo(signIn.user());
        assertThat(me.user().language()).isEqualTo(Language.hinglish);
        assertThat(me.profile().onboardingStep()).isEqualTo("intro");
        assertThat(me.profile().morningNotificationTime()).isEqualTo(LocalTime.of(7, 0));
        assertThat(me.profile().isMinor()).isFalse();
        assertThat(me.profile().currentStreak()).isZero();
        assertThat(me.profile().attemptType()).isNull();
        assertThat(me.profile().hoursWeekday()).isNull();
    }

    @Test
    void meIsEmptyForADeletedAccountOrAMissingProfile() {
        SignIn deleted = service.signIn(new LoginIdentifier.Email("me-deleted@example.com"), Language.en);
        users.saveAndFlush(markDeleted(users.findById(deleted.user().id()).orElseThrow()));
        SignIn orphan = service.signIn(new LoginIdentifier.Email("me-orphan@example.com"), Language.en);
        profiles.delete(profiles.findByUserId(orphan.user().id()).orElseThrow());
        profiles.flush();

        assertThat(service.me(deleted.user().id())).isEmpty();
        assertThat(service.me(orphan.user().id())).as("a pre-D10 account without a profile: sign in again heals it").isEmpty();
        assertThat(service.me(java.util.UUID.randomUUID())).isEmpty();
    }

    @Test
    void phoneLoginStampsTheVerificationTime() {
        SignIn signIn = service.signIn(new LoginIdentifier.Phone("+919876500001"), Language.en);

        assertThat(signIn.isNew()).isTrue();
        assertThat(signIn.user().phone()).isEqualTo("+919876500001");
        assertThat(signIn.user().email()).isNull();
        assertThat(users.findById(signIn.user().id()).orElseThrow().getPhoneVerifiedAt()).isEqualTo(NOW);
    }

    @Test
    void emailAndPhoneAccountsAreDistinct() {
        SignIn byEmail = service.signIn(new LoginIdentifier.Email("distinct@example.com"), Language.en);
        SignIn byPhone = service.signIn(new LoginIdentifier.Phone("+919876500002"), Language.en);

        assertThat(byEmail.user().id()).isNotEqualTo(byPhone.user().id());
    }

    @Test
    void findActiveIgnoresDeletedAccounts() {
        SignIn signIn = service.signIn(new LoginIdentifier.Email("deleted@example.com"), Language.en);
        assertThat(service.findActive(signIn.user().id())).isPresent();

        User user = users.findById(signIn.user().id()).orElseThrow();
        users.saveAndFlush(markDeleted(user));

        assertThat(service.findActive(signIn.user().id())).isEmpty();
        assertThat(service.signIn(new LoginIdentifier.Email("deleted@example.com"), Language.en).isNew())
                .as("a deleted account's identifier can start a fresh account").isTrue();
    }

    private static User markDeleted(User user) {
        // D64 owns the real anonymisation; here only the status flip matters.
        org.springframework.test.util.ReflectionTestUtils.setField(user, "status", UserStatus.deleted);
        org.springframework.test.util.ReflectionTestUtils.setField(user, "email", null);
        return user;
    }
}
