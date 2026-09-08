package com.margai.auth.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.UserSummary;
import com.margai.auth.internal.OtpChannel;
import com.margai.auth.internal.OtpRequested;
import com.margai.auth.internal.OtpService;
import com.margai.auth.internal.OtpVerified;
import com.margai.auth.internal.TokenPair;
import com.margai.auth.internal.TokenService;
import com.margai.common.api.AuthException;
import com.margai.common.api.Language;
import com.margai.common.api.OtpException;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.UserRole;
import java.net.InetAddress;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Controller slice (TECH_PLAN §8.1; the /endpoint skill's list): the three shapes of §3.7 in
 * snake_case without nulls, validation of every body, and each service outcome rendered as its
 * envelope — with the caller's language on these public routes (§3.8).
 */
@WebMvcTest(controllers = AuthController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.margai\\.common\\.internal\\..*"))
class AuthControllerTest {

    @TestConfiguration(proxyBeanMethods = false)
    static class PermitEverything {
        @Bean
        SecurityFilterChain permitEverything(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(routes -> routes.anyRequest().permitAll()).build();
        }
    }

    private static final UUID CHALLENGE = UUID.randomUUID();
    private static final UserSummary USER = new UserSummary(UUID.randomUUID(), null, "founder@example.com", Language.en,
            UserRole.student, null);

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private OtpService otp;

    @MockitoBean
    private TokenService tokens;

    @Test
    void requestByEmailNormalisesAndAnswersWithTheChallenge() throws Exception {
        when(otp.request(any(), any(), any())).thenReturn(new OtpRequested(CHALLENGE, 30, OtpChannel.email));

        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "hi").header("X-Forwarded-For", "203.0.113.4, 10.0.0.1")
                .content("{\"email\":\" Founder@Example.COM \"}").exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.challenge_id").isEqualTo(CHALLENGE.toString());
        assertThat(result).bodyJson().extractingPath("$.resend_after_s").isEqualTo(30);
        assertThat(result).bodyJson().extractingPath("$.channel").isEqualTo("email");
        verify(otp).request(eq(new LoginIdentifier.Email("founder@example.com")), eq(InetAddress.getByName("203.0.113.4")),
                eq(Language.hi));
    }

    @Test
    void requestByPhoneNormalisesToE164() {
        when(otp.request(any(), any(), any())).thenReturn(new OtpRequested(CHALLENGE, 30, OtpChannel.sms));

        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"98765 43210\"}").exchange();

        assertThat(result).hasStatusOk();
        ArgumentCaptor<LoginIdentifier> identifier = ArgumentCaptor.forClass(LoginIdentifier.class);
        verify(otp).request(identifier.capture(), any(), eq(Language.en));
        assertThat(identifier.getValue()).isEqualTo(new LoginIdentifier.Phone("+919876543210"));
    }

    @Test
    void requestNeedsExactlyOneIdentifier() {
        MvcTestResult neither = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{}").exchange();
        MvcTestResult both = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"9876543210\",\"email\":\"a@b.io\"}").exchange();

        for (MvcTestResult result : new MvcTestResult[] {neither, both}) {
            assertThat(result).hasStatus(400);
            assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
            assertThat(result).bodyJson().extractingPath("$.error.details.phone").asString().isNotBlank();
            assertThat(result).bodyJson().extractingPath("$.error.details.email").asString().isNotBlank();
        }
    }

    @Test
    void malformedIdentifiersNameTheField() {
        MvcTestResult phone = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"12345\"}").exchange();
        MvcTestResult email = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"not-an-email\"}").exchange();

        assertThat(phone).hasStatus(400);
        assertThat(phone).bodyJson().extractingPath("$.error.details.phone").isEqualTo("enter a 10-digit Indian mobile number");
        assertThat(email).hasStatus(400);
        assertThat(email).bodyJson().extractingPath("$.error.details.email").isEqualTo("enter a valid email address");
    }

    @Test
    void verifyReturnsTokensAndTheUserWithoutNulls() {
        when(otp.verify(eq(CHALLENGE), eq("482913"), eq("margai/0.1.0 (android 14)")))
                .thenReturn(new OtpVerified(new TokenPair("jwt-1", "refresh-1", 900), USER, true));

        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .header("X-App-Version", "margai/0.1.0 (android 14)")
                .content("{\"challenge_id\":\"" + CHALLENGE + "\",\"code\":\"482913\",\"invite_code\":\"BETA1\"}").exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.access_token").isEqualTo("jwt-1");
        assertThat(result).bodyJson().extractingPath("$.refresh_token").isEqualTo("refresh-1");
        assertThat(result).bodyJson().extractingPath("$.expires_in").isEqualTo(900);
        assertThat(result).bodyJson().extractingPath("$.is_new_user").isEqualTo(true);
        assertThat(result).bodyJson().extractingPath("$.user.id").isEqualTo(USER.id().toString());
        assertThat(result).bodyJson().extractingPath("$.user.email").isEqualTo("founder@example.com");
        assertThat(result).bodyJson().extractingPath("$.user.language").isEqualTo("en");
        assertThat(result).bodyJson().extractingPath("$.user.role").isEqualTo("student");
        assertThat(result).bodyJson().doesNotHavePath("$.user.phone");
        assertThat(result).bodyJson().doesNotHavePath("$.user.display_name");
    }

    @Test
    void verifyValidatesItsBody() {
        MvcTestResult shortCode = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .content("{\"challenge_id\":\"" + CHALLENGE + "\",\"code\":\"12\"}").exchange();
        MvcTestResult noChallenge = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"123456\"}").exchange();

        assertThat(shortCode).hasStatus(400);
        assertThat(shortCode).bodyJson().extractingPath("$.error.details.code").isEqualTo("enter the digits of the code");
        assertThat(noChallenge).hasStatus(400);
        assertThat(noChallenge).bodyJson().extractingPath("$.error.details.challenge_id").asString().isNotBlank();
    }

    @Test
    void aWrongCodeIsOtpInvalidInTheCallersLanguage() {
        when(otp.verify(any(), any(), any())).thenThrow(OtpException.invalid(3));

        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "hi")
                .content("{\"challenge_id\":\"" + CHALLENGE + "\",\"code\":\"000000\"}").exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_INVALID");
        assertThat(result).bodyJson().extractingPath("$.error.details.attempts_left").isEqualTo(3);
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("यह कोड मेल नहीं खाया। एक बार और कोशिश करें।");
    }

    @Test
    void aRateLimitedRequestCarriesRetryAfter() {
        when(otp.request(any(), any(), any())).thenThrow(RateLimitedException.otp(Duration.ofSeconds(25)));

        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"a@b.io\"}").exchange();

        assertThat(result).hasStatus(429);
        assertThat(result.getResponse().getHeader("Retry-After")).isEqualTo("25");
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
    }

    @Test
    void refreshRotatesOrSaysSignInAgain() {
        when(tokens.refresh("good")).thenReturn(new TokenPair("jwt-2", "refresh-2", 900));
        when(tokens.refresh("reused")).thenThrow(AuthException.invalid());

        MvcTestResult rotated = mvc.post().uri("/api/v1/auth/refresh").contentType(MediaType.APPLICATION_JSON)
                .content("{\"refresh_token\":\"good\"}").exchange();
        MvcTestResult reused = mvc.post().uri("/api/v1/auth/refresh").contentType(MediaType.APPLICATION_JSON)
                .content("{\"refresh_token\":\"reused\"}").exchange();
        MvcTestResult empty = mvc.post().uri("/api/v1/auth/refresh").contentType(MediaType.APPLICATION_JSON)
                .content("{}").exchange();

        assertThat(rotated).hasStatusOk();
        assertThat(rotated).bodyJson().extractingPath("$.access_token").isEqualTo("jwt-2");
        assertThat(rotated).bodyJson().extractingPath("$.refresh_token").isEqualTo("refresh-2");
        assertThat(rotated).bodyJson().extractingPath("$.expires_in").isEqualTo(900);
        assertThat(reused).hasStatus(401);
        assertThat(reused).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
        assertThat(empty).hasStatus(400);
        assertThat(empty).bodyJson().extractingPath("$.error.details.refresh_token").asString().isNotBlank();
    }

    @Test
    void deviceLabelPrefersAppVersionAndIsBounded() {
        org.springframework.mock.web.MockHttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
        request.addHeader("User-Agent", "okhttp/4.12");
        assertThat(AuthController.deviceLabel(request)).isEqualTo("okhttp/4.12");

        request.addHeader("X-App-Version", "x".repeat(100));
        assertThat(AuthController.deviceLabel(request)).hasSize(80);
        assertThat(AuthController.deviceLabel(new org.springframework.mock.web.MockHttpServletRequest())).isNull();
    }
}
