package com.margai.account.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.margai.account.api.Accounts;
import com.margai.account.api.Goal;
import com.margai.account.api.Me;
import com.margai.account.api.ProfileSummary;
import com.margai.account.api.ProfileUpdate;
import com.margai.account.api.UserSummary;
import com.margai.common.api.Category;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Controller slice (TECH_PLAN §8.1) for {@code /me} (§3.7, D10): the shape in snake_case without
 * nulls (§11.3), the caller from the published principal, and each service outcome as its envelope.
 */
@WebMvcTest(controllers = MeController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.margai\\.common\\.internal\\..*"))
class MeControllerTest {

    @TestConfiguration(proxyBeanMethods = false)
    static class PermitEverything {
        @Bean
        SecurityFilterChain permitEverything(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(routes -> routes.anyRequest().permitAll()).build();
        }
    }

    static final UserSummary USER = new UserSummary(UUID.randomUUID(), null, "founder@example.com", Language.hi,
            UserRole.student, null);
    static final ProfileSummary EMPTY_PROFILE = new ProfileSummary(null, null, null, null, null, null, null, null, null,
            null, false, null, null, null, "intro", null, null, LocalTime.of(7, 0), 0, 0);
    static final Principal CALLER = new Principal(USER.id(), UserRole.student, Language.hi);

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private Accounts accounts;

    @Test
    void meRendersTheUserAndTheProfileInSnakeCaseWithoutNulls() {
        when(accounts.me(USER.id())).thenReturn(Optional.of(new Me(USER, EMPTY_PROFILE)));

        MvcTestResult result = mvc.get().uri("/api/v1/me").requestAttr(Principal.REQUEST_ATTRIBUTE, CALLER).exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.user.id").isEqualTo(USER.id().toString());
        assertThat(result).bodyJson().extractingPath("$.user.email").isEqualTo("founder@example.com");
        assertThat(result).bodyJson().extractingPath("$.user.language").isEqualTo("hi");
        assertThat(result).bodyJson().extractingPath("$.profile.onboarding_step").isEqualTo("intro");
        assertThat(result).bodyJson().extractingPath("$.profile.is_minor").isEqualTo(false);
        assertThat(result).bodyJson().extractingPath("$.profile.morning_notification_time").asString().startsWith("07:00");
        assertThat(result).bodyJson().extractingPath("$.profile.current_streak").isEqualTo(0);
        assertThat(result).bodyJson().extractingPath("$.profile.longest_streak").isEqualTo(0);
        assertThat(result).bodyJson().doesNotHavePath("$.profile.attempt_type");
        assertThat(result).bodyJson().doesNotHavePath("$.profile.hours_weekday");
        assertThat(result).bodyJson().doesNotHavePath("$.user.phone");
        assertThat(result).bodyJson().doesNotHavePath("$.subscription");
    }

    @Test
    void patchMapsTheBodyOntoATypedUpdateAndAnswersMe() {
        ArgumentCaptor<ProfileUpdate> update = ArgumentCaptor.forClass(ProfileUpdate.class);
        when(accounts.update(eq(USER.id()), update.capture())).thenReturn(Optional.of(new Me(USER, EMPTY_PROFILE)));

        MvcTestResult result = mvc.patch().uri("/api/v1/me").contentType(MediaType.APPLICATION_JSON)
                .requestAttr(Principal.REQUEST_ATTRIBUTE, CALLER)
                .content("{\"language\":\"hi\",\"display_name\":\"  Asha \",\"morning_notification_time\":\"06:30\","
                        + "\"hours_weekday\":5.5,\"hours_weekend\":\"8\",\"goal\":\"govt_mbbs\",\"state_code\":\"mh\","
                        + "\"category\":\"obc\"}")
                .exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.user.language").isEqualTo("hi");
        assertThat(result).bodyJson().extractingPath("$.profile.onboarding_step").isEqualTo("intro");
        ProfileUpdate sent = update.getValue();
        assertThat(sent.language()).isEqualTo(Language.hi);
        assertThat(sent.displayName()).isEqualTo("Asha");
        assertThat(sent.morningNotificationTime()).isEqualTo(LocalTime.of(6, 30));
        assertThat(sent.hoursWeekday()).isEqualByComparingTo("5.5");
        assertThat(sent.hoursWeekend()).isEqualByComparingTo("8.0");
        assertThat(sent.goal()).isEqualTo(Goal.govt_mbbs);
        assertThat(sent.stateCode()).isEqualTo("MH");
        assertThat(sent.category()).isEqualTo(Category.obc);
    }

    @Test
    void patchOfAnEmptyBodyChangesNothingAndAnswersMe() {
        ArgumentCaptor<ProfileUpdate> update = ArgumentCaptor.forClass(ProfileUpdate.class);
        when(accounts.update(eq(USER.id()), update.capture())).thenReturn(Optional.of(new Me(USER, EMPTY_PROFILE)));

        MvcTestResult result = mvc.patch().uri("/api/v1/me").contentType(MediaType.APPLICATION_JSON)
                .requestAttr(Principal.REQUEST_ATTRIBUTE, CALLER).content("{}").exchange();

        assertThat(result).hasStatusOk();
        assertThat(update.getValue().isEmpty()).isTrue();
    }

    @Test
    void patchNamesEveryBadFieldWithAReasonCodeNeverProse() throws Exception {
        MvcTestResult result = mvc.patch().uri("/api/v1/me").contentType(MediaType.APPLICATION_JSON)
                .requestAttr(Principal.REQUEST_ATTRIBUTE, CALLER)
                .content("{\"language\":\"fr\",\"display_name\":\"   \",\"morning_notification_time\":\"25:00\","
                        + "\"hours_weekday\":17,\"hours_weekend\":-1,\"goal\":\"rich\",\"state_code\":\"Maharashtra\","
                        + "\"category\":\"x\"}")
                .exchange();

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(result).bodyJson().extractingPath("$.error.details.language").isEqualTo("language.invalid");
        assertThat(result).bodyJson().extractingPath("$.error.details.display_name").isEqualTo("not_blank");
        assertThat(result).bodyJson().extractingPath("$.error.details.morning_notification_time").isEqualTo("time.invalid");
        assertThat(result).bodyJson().extractingPath("$.error.details.hours_weekday").isEqualTo("decimal_max");
        assertThat(result).bodyJson().extractingPath("$.error.details.hours_weekend").isEqualTo("decimal_min");
        assertThat(result).bodyJson().extractingPath("$.error.details.goal").isEqualTo("goal.invalid");
        assertThat(result).bodyJson().extractingPath("$.error.details.state_code").isEqualTo("state_code.invalid");
        assertThat(result).bodyJson().extractingPath("$.error.details.category").isEqualTo("category.invalid");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("java.", "Exception", "Maharashtra");
        verify(accounts, never()).update(any(), any());
    }

    @Test
    void meWithoutAPublishedPrincipalIsAuthRequired() {
        MvcTestResult result = mvc.get().uri("/api/v1/me").exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_REQUIRED");
    }

    @Test
    void anAccountThatCannotBeServedIsAuthInvalidInTheCallersLanguage() {
        when(accounts.me(USER.id())).thenReturn(Optional.empty());

        MvcTestResult result = mvc.get().uri("/api/v1/me").requestAttr(Principal.REQUEST_ATTRIBUTE, CALLER).exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("कृपया दोबारा साइन इन करें।");
    }
}
