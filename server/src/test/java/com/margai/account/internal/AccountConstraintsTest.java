package com.margai.account.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.Language;
import com.margai.common.api.UserRole;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Repository slice (TECH_PLAN §8.1): migrations V2 and V6 apply and their constraints hold —
 * the partial unique indexes on {@code users.phone} and {@code users.email}, "an active user has
 * a phone or an email" ({@code users_identifier_status_check}, D7), and the 1:1 between
 * {@code student_profiles} and {@code users}.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class AccountConstraintsTest {

    @Autowired
    private UserRepository users;

    @Autowired
    private StudentProfileRepository profiles;

    @Test
    void savedUserGetsIdDefaultsAndTimestamps() {
        User user = users.saveAndFlush(new User("+919876543210", Language.hinglish));

        assertThat(user.getId()).isNotNull();
        assertThat(user.getRole()).isEqualTo(UserRole.student);
        assertThat(user.getStatus()).isEqualTo(UserStatus.active);
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isNotNull();
    }

    @Test
    void phoneIsUniqueWhilePresent() {
        users.saveAndFlush(new User("+919876543211", Language.en));

        assertThatThrownBy(() -> users.saveAndFlush(new User("+919876543211", Language.hi)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("users_phone_key");
    }

    @Test
    void activeUserNeedsAPhoneOrAnEmail() {
        assertThatThrownBy(() -> users.saveAndFlush(new User(null, Language.en)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("users_identifier_status_check");
    }

    @Test
    void emailAloneIdentifiesAnActiveUser() {
        User user = users.saveAndFlush(User.withEmail("d7@example.com", Language.hi));

        assertThat(user.getPhone()).isNull();
        assertThat(user.getEmail()).isEqualTo("d7@example.com");
        assertThat(user.getPhoneVerifiedAt()).isNull();
    }

    @Test
    void emailIsUniqueWhilePresent() {
        users.saveAndFlush(User.withEmail("twice@example.com", Language.en));

        assertThatThrownBy(() -> users.saveAndFlush(User.withEmail("twice@example.com", Language.hinglish)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("users_email_key");
    }

    @Test
    void profileIsOneToOneWithUser() {
        User user = users.saveAndFlush(new User("+919876543212", Language.en));
        StudentProfile profile = profiles.saveAndFlush(new StudentProfile(user.getId()));

        assertThat(profile.getOnboardingStep()).isEqualTo("intro");
        assertThat(profile.getMorningNotificationTime()).hasToString("07:00");
        assertThat(profiles.findByUserId(user.getId())).isPresent();
        assertThatThrownBy(() -> profiles.saveAndFlush(new StudentProfile(user.getId())))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("student_profiles_user_id_key");
    }

    @Test
    void profileNeedsAnExistingUser() {
        assertThatThrownBy(() -> profiles.saveAndFlush(new StudentProfile(UUID.randomUUID())))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("student_profiles_user_id_fkey");
    }
}
