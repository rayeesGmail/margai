package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * TECH_PLAN §1.5 step 3, §3.3 401 row, §9.1: the chain over the real application context.
 * Health is public; a protected route without a token is {@code AUTH_REQUIRED}, with a stale
 * token {@code AUTH_EXPIRED}, with a tampered token {@code AUTH_INVALID}, all as envelopes; a
 * valid token reaches the controller with the principal published for {@code common}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class SecurityChainTest {

    private static final Principal STUDENT = new Principal(UUID.randomUUID(), UserRole.student, Language.hi);

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JwtService jwts;

    @Test
    void healthStaysPublic() {
        assertThat(mvc.get().uri("/actuator/health").exchange()).hasStatusOk();
    }

    @Test
    void protectedRouteWithoutATokenIsAuthRequired() {
        MvcTestResult result = mvc.get().uri("/api/v1/probe/whoami").header("Accept-Language", "hi").exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_REQUIRED");
        assertThat(result).bodyJson().extractingPath("$.error.message_en").isEqualTo("Please sign in to continue.");
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("जारी रखने के लिए साइन इन करें।");
        assertThat(result.getResponse().getHeader("X-Request-Id")).isNotBlank();
    }

    @Test
    void aStaleTokenIsAuthExpired() {
        String stale = jwts.issue(STUDENT, Instant.now().minus(Duration.ofMinutes(20)));

        MvcTestResult result = mvc.get().uri("/api/v1/probe/whoami").header("Authorization", "Bearer " + stale).exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_EXPIRED");
    }

    @Test
    void aTamperedOrForeignTokenIsAuthInvalid() {
        String token = jwts.issue(STUDENT);
        String tampered = token.substring(0, token.length() - 4) + (token.endsWith("AAAA") ? "BBBB" : "AAAA");

        MvcTestResult forged = mvc.get().uri("/api/v1/probe/whoami").header("Authorization", "Bearer " + tampered).exchange();
        MvcTestResult garbage = mvc.get().uri("/api/v1/probe/whoami").header("Authorization", "Bearer nonsense").exchange();

        assertThat(forged).hasStatus(401);
        assertThat(forged).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
        assertThat(garbage).hasStatus(401);
        assertThat(garbage).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
    }

    @Test
    void aValidTokenReachesTheControllerWithThePrincipal() {
        String token = jwts.issue(STUDENT);

        MvcTestResult result = mvc.get().uri("/api/v1/probe/whoami").header("Authorization", "Bearer " + token).exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.user_id").isEqualTo(STUDENT.userId().toString());
        assertThat(result).bodyJson().extractingPath("$.role").isEqualTo("student");
        assertThat(result).bodyJson().extractingPath("$.language").isEqualTo("hi");
    }

    @Test
    void errorsOnAuthenticatedRoutesSpeakThePrincipalsLanguage() {
        String token = jwts.issue(STUDENT);

        MvcTestResult result = mvc.get().uri("/api/v1/probe/otp-invalid")
                .header("Authorization", "Bearer " + token).header("Accept-Language", "en").exchange();

        assertThat(result).hasStatus(401);
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("यह कोड मेल नहीं खाया। एक बार और कोशिश करें।");
    }

    @Test
    void publicAuthRoutesNeedNoToken() {
        // The chain lets a token-less request through to MVC; without a body the controller's validation answers, not the chain.
        MvcTestResult result = mvc.post().uri("/api/v1/auth/otp/request")
                .header("X-Forwarded-For", "203.0.113.50").exchange();

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
    }

    @Test
    void otpRequestsAreLimitedPerClientAddressBeforeAnyController() {
        for (int i = 0; i < 10; i++) {
            assertThat(mvc.post().uri("/api/v1/auth/otp/request").header("X-Forwarded-For", "203.0.113.60").exchange())
                    .as("request " + (i + 1)).hasStatus(400);
        }

        MvcTestResult refused = mvc.post().uri("/api/v1/auth/otp/request")
                .header("X-Forwarded-For", "203.0.113.60").header("Accept-Language", "hi-Latn").exchange();

        assertThat(refused).hasStatus(429);
        assertThat(refused.getResponse().getHeader("Retry-After")).isNotBlank();
        assertThat(refused).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        assertThat(refused).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("Bahut saare codes maange gaye. Thoda wait karo.");
        assertThat(refused).bodyJson().extractingPath("$.error.details.retry_after_s").isEqualTo(360);
        assertThat(refused.getResponse().getHeader("Retry-After")).isEqualTo("360");
    }
}
