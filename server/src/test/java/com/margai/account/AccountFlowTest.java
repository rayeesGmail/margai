package com.margai.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.auth.internal.JwtService;
import com.margai.auth.internal.OtpDelivery;
import com.margai.auth.internal.OtpSender;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Module-flow test (TECH_PLAN §8.1; the server half of PLAN D10 ✅): over the real chain, database
 * and services — login → {@code GET /me} (the empty profile, the suggested language) →
 * {@code PATCH /me {language}} → {@code GET /me} agrees → the refreshed access token carries the new
 * {@code lang} and errors on authenticated routes speak it (§3.8) → logout → the refresh token is
 * dead ({@code AUTH_INVALID}) and {@code /me} needs a bearer.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, AccountFlowTest.Inbox.class})
class AccountFlowTest {

    @TestConfiguration(proxyBeanMethods = false)
    static class Inbox {

        static final List<OtpDelivery> DELIVERIES = new CopyOnWriteArrayList<>();

        @Bean
        @Primary
        OtpSender recordingOtpSender() {
            return DELIVERIES::add;
        }
    }

    private static final String FROM = "203.0.113.210";

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JwtService jwts;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void profileLanguageAndLogoutEndToEnd() throws Exception {
        String email = "account-" + UUID.randomUUID() + "@example.com";
        MvcTestResult requested = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}", null);
        assertThat(requested).hasStatusOk();
        String challengeId = requested.getResponse().getContentAsString().replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
        String code = Inbox.DELIVERIES.stream().filter(d -> d.destination().equals(email)).reduce((a, b) -> b).orElseThrow().code();
        MvcTestResult verified = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .header("X-Forwarded-For", FROM).header("Accept-Language", "en-IN")
                .content("{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + code + "\"}").exchange();
        assertThat(verified).hasStatusOk();
        String body = verified.getResponse().getContentAsString();
        String access = field(body, "access_token");
        String refresh = field(body, "refresh_token");
        String userId = body.replaceAll(".*\"user\":\\{\"id\":\"([^\"]+)\".*", "$1");

        // 1. one call on app start: the account and its empty profile
        MvcTestResult me = mvc.get().uri("/api/v1/me").header("Authorization", "Bearer " + access).exchange();
        assertThat(me).hasStatusOk();
        assertThat(me).bodyJson().extractingPath("$.user.id").isEqualTo(userId);
        assertThat(me).bodyJson().extractingPath("$.user.email").isEqualTo(email);
        assertThat(me).bodyJson().extractingPath("$.user.language").isEqualTo("en");
        assertThat(me).bodyJson().extractingPath("$.profile.onboarding_step").isEqualTo("intro");
        assertThat(me).bodyJson().extractingPath("$.profile.is_minor").isEqualTo(false);
        assertThat(me).bodyJson().doesNotHavePath("$.profile.goal");

        // 2. the language switch (SPEC §6.11): stored at once, visible on the next read
        MvcTestResult patched = mvc.patch().uri("/api/v1/me").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + access).content("{\"language\":\"hi\"}").exchange();
        assertThat(patched).hasStatusOk();
        assertThat(patched).bodyJson().extractingPath("$.user.language").isEqualTo("hi");
        assertThat(jdbc.queryForObject("SELECT language FROM users WHERE id = ?::uuid", String.class, userId)).isEqualTo("hi");
        MvcTestResult meAgain = mvc.get().uri("/api/v1/me").header("Authorization", "Bearer " + access).exchange();
        assertThat(meAgain).bodyJson().extractingPath("$.user.language").isEqualTo("hi");

        // 3. a bad value is a reason code on its field, never prose; nothing changes
        MvcTestResult refused = mvc.patch().uri("/api/v1/me").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + access).content("{\"language\":\"fr\"}").exchange();
        assertThat(refused).hasStatus(400);
        assertThat(refused).bodyJson().extractingPath("$.error.details.language").isEqualTo("language.invalid");
        assertThat(refused).bodyJson().extractingPath("$.error.message_user_lang").asString().isNotEmpty();

        // 4. the next refresh re-issues the access token in the new language (§3.8) and errors speak it
        MvcTestResult rotated = post("/api/v1/auth/refresh", "{\"refresh_token\":\"" + refresh + "\"}", null);
        assertThat(rotated).hasStatusOk();
        String access2 = field(rotated.getResponse().getContentAsString(), "access_token");
        String refresh2 = field(rotated.getResponse().getContentAsString(), "refresh_token");
        assertThat(jwts.decode(access).getClaimAsString("lang")).isEqualTo("en");
        assertThat(jwts.decode(access2).getClaimAsString("lang")).isEqualTo("hi");
        MvcTestResult hindiError = mvc.get().uri("/api/v1/probe/otp-invalid").header("Authorization", "Bearer " + access2).exchange();
        assertThat(hindiError).bodyJson().extractingPath("$.error.message_user_lang").isEqualTo("यह कोड मेल नहीं खाया। एक बार और कोशिश करें।");

        // 5. logout kills the family; /me stays behind the chain
        MvcTestResult loggedOut = mvc.post().uri("/api/v1/auth/logout").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + access2).header("X-Forwarded-For", FROM)
                .content("{\"refresh_token\":\"" + refresh2 + "\"}").exchange();
        assertThat(loggedOut).hasStatus(204);
        MvcTestResult dead = post("/api/v1/auth/refresh", "{\"refresh_token\":\"" + refresh2 + "\"}", null);
        assertThat(dead).hasStatus(401);
        assertThat(dead).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM refresh_tokens WHERE user_id = ?::uuid AND revoked_at IS NULL",
                Integer.class, userId)).isZero();
        MvcTestResult noBearer = mvc.get().uri("/api/v1/me").exchange();
        assertThat(noBearer).hasStatus(401);
        assertThat(noBearer).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_REQUIRED");
    }

    private MvcTestResult post(String uri, String json, String bearer) {
        var request = mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).header("X-Forwarded-For", FROM).content(json);
        if (bearer != null) {
            request = request.header("Authorization", "Bearer " + bearer);
        }
        return request.exchange();
    }

    private static String field(String json, String name) {
        return json.replaceAll(".*\"" + name + "\":\"([^\"]+)\".*", "$1");
    }
}
