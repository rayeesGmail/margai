package com.margai.account.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.SignIn;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.UserRole;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/** TECH_PLAN §3.7: first login creates the account, later logins find it; phone logins stamp the verification time. */
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

    @Test
    void emailLoginCreatesThenFindsTheAccount() {
        SignIn first = service.signIn(new LoginIdentifier.Email("first@example.com"));
        SignIn again = service.signIn(new LoginIdentifier.Email("first@example.com"));

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
    void phoneLoginStampsTheVerificationTime() {
        SignIn signIn = service.signIn(new LoginIdentifier.Phone("+919876500001"));

        assertThat(signIn.isNew()).isTrue();
        assertThat(signIn.user().phone()).isEqualTo("+919876500001");
        assertThat(signIn.user().email()).isNull();
        assertThat(users.findById(signIn.user().id()).orElseThrow().getPhoneVerifiedAt()).isEqualTo(NOW);
    }

    @Test
    void emailAndPhoneAccountsAreDistinct() {
        SignIn byEmail = service.signIn(new LoginIdentifier.Email("distinct@example.com"));
        SignIn byPhone = service.signIn(new LoginIdentifier.Phone("+919876500002"));

        assertThat(byEmail.user().id()).isNotEqualTo(byPhone.user().id());
    }

    @Test
    void findActiveIgnoresDeletedAccounts() {
        SignIn signIn = service.signIn(new LoginIdentifier.Email("deleted@example.com"));
        assertThat(service.findActive(signIn.user().id())).isPresent();

        User user = users.findById(signIn.user().id()).orElseThrow();
        users.saveAndFlush(markDeleted(user));

        assertThat(service.findActive(signIn.user().id())).isEmpty();
        assertThat(service.signIn(new LoginIdentifier.Email("deleted@example.com")).isNew())
                .as("a deleted account's identifier can start a fresh account").isTrue();
    }

    private static User markDeleted(User user) {
        // D64 owns the real anonymisation; here only the status flip matters.
        org.springframework.test.util.ReflectionTestUtils.setField(user, "status", UserStatus.deleted);
        org.springframework.test.util.ReflectionTestUtils.setField(user, "email", null);
        return user;
    }
}
