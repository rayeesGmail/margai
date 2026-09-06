package com.margai.account.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.Language;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Repository slice (TECH_PLAN §8.1): migration V2 applies and its constraints hold —
 * the partial unique index on {@code users.phone}, {@code status = 'deleted' OR phone IS NOT
 * NULL}, and the 1:1 between {@code student_profiles} and {@code users}.
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
    void activeUserMustHaveAPhone() {
        assertThatThrownBy(() -> users.saveAndFlush(new User(null, Language.en)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("users_phone_status_check");
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
