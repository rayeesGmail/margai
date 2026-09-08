package com.margai.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.MutableClock;
import com.margai.TestcontainersConfiguration;
import com.margai.auth.internal.JwtService;
import com.margai.auth.internal.OtpDelivery;
import com.margai.auth.internal.OtpSender;
import com.margai.common.api.IstClock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Module-flow test (TECH_PLAN §8.1; PLAN D7 ✅ "happy path"): over the real chain, database and
 * services — request by email → the captured code → verify → tokens whose JWT names the new
 * account → a protected route → refresh rotates → reuse kills the family → the second login of
 * the same email is not new → the hourly cap → a phone request while only email is enabled.
 * A recording sender is the inbox and a movable clock steps past the 30-second cooldown.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, AuthFlowTest.Inbox.class})
class AuthFlowTest {

    @TestConfiguration(proxyBeanMethods = false)
    static class Inbox {

        static final List<OtpDelivery> DELIVERIES = new CopyOnWriteArrayList<>();
        static final MutableClock CLOCK = new MutableClock(Instant.now());

        @Bean
        @Primary
        OtpSender recordingOtpSender() {
            return DELIVERIES::add;
        }

        @Bean
        @Primary
        IstClock movableIstClock() {
            return new IstClock(CLOCK);
        }
    }

    private static final String FROM = "203.0.113.200";

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JwtService jwts;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void emailLoginEndToEnd() throws Exception {
        String typed = "Flow-" + UUID.randomUUID() + "@Example.COM";
        String email = typed.toLowerCase();

        // 1. request → challenge, code captured by the inbox
        MvcTestResult requested = post("/api/v1/auth/otp/request", "{\"email\":\"" + typed + "\"}");
        assertThat(requested).hasStatusOk();
        assertThat(requested).bodyJson().extractingPath("$.channel").isEqualTo("email");
        assertThat(requested).bodyJson().extractingPath("$.resend_after_s").isEqualTo(30);
        String challengeId = requested.getResponse().getContentAsString().replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
        OtpDelivery delivery = Inbox.DELIVERIES.stream().filter(d -> d.destination().equals(email)).reduce((a, b) -> b).orElseThrow();
        assertThat(delivery.code()).matches("\\d{6}");
        Map<String, Object> challengeRow = jdbc.queryForMap("SELECT code_hash, verified_at, channel, destination FROM otp_challenges WHERE id = ?::uuid", challengeId);
        assertThat(challengeRow.get("code_hash").toString()).matches("[0-9a-f]{64}").doesNotContain(delivery.code());
        assertThat(challengeRow.get("verified_at")).isNull();
        assertThat(challengeRow.get("destination")).isEqualTo(email);

        // 2. verify → tokens + user, first login
        MvcTestResult verified = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .header("X-Forwarded-For", FROM).header("X-App-Version", "margai/0.1.0 (android 14)")
                .content("{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + delivery.code() + "\"}").exchange();
        assertThat(verified).hasStatusOk();
        assertThat(verified).bodyJson().extractingPath("$.is_new_user").isEqualTo(true);
        assertThat(verified).bodyJson().extractingPath("$.expires_in").isEqualTo(900);
        assertThat(verified).bodyJson().extractingPath("$.user.email").isEqualTo(email);
        assertThat(verified).bodyJson().extractingPath("$.user.language").isEqualTo("en");
        String body = verified.getResponse().getContentAsString();
        String access = field(body, "access_token");
        String refresh = field(body, "refresh_token");
        String userId = body.replaceAll(".*\"user\":\\{\"id\":\"([^\"]+)\".*", "$1");
        Jwt jwt = jwts.decode(access);
        assertThat(jwt.getSubject()).isEqualTo(userId);
        assertThat(jwt.getClaimAsString("lang")).isEqualTo("en");
        assertThat(jwt.getClaimAsString("role")).isEqualTo("student");
        assertThat(jwt.getId()).isNotBlank();
        assertThat(jdbc.queryForObject("SELECT email FROM users WHERE id = ?::uuid", String.class, userId)).isEqualTo(email);
        assertThat(jdbc.queryForObject("SELECT verified_at IS NOT NULL FROM otp_challenges WHERE id = ?::uuid", Boolean.class, challengeId)).isTrue();
        assertThat(jdbc.queryForObject("SELECT device_label FROM refresh_tokens WHERE user_id = ?::uuid", String.class, userId))
                .isEqualTo("margai/0.1.0 (android 14)");

        // 3. the access token opens a protected route with the principal
        MvcTestResult whoami = mvc.get().uri("/api/v1/probe/whoami").header("Authorization", "Bearer " + access).exchange();
        assertThat(whoami).hasStatusOk();
        assertThat(whoami).bodyJson().extractingPath("$.user_id").isEqualTo(userId);

        // 4. refresh rotates; the spent token then revokes the family; the fresh one dies with it
        MvcTestResult rotated = post("/api/v1/auth/refresh", "{\"refresh_token\":\"" + refresh + "\"}");
        assertThat(rotated).hasStatusOk();
        String refresh2 = field(rotated.getResponse().getContentAsString(), "refresh_token");
        assertThat(refresh2).isNotEqualTo(refresh);
        MvcTestResult reuse = post("/api/v1/auth/refresh", "{\"refresh_token\":\"" + refresh + "\"}");
        assertThat(reuse).hasStatus(401);
        assertThat(reuse).bodyJson().extractingPath("$.error.code").isEqualTo("AUTH_INVALID");
        MvcTestResult dead = post("/api/v1/auth/refresh", "{\"refresh_token\":\"" + refresh2 + "\"}");
        assertThat(dead).hasStatus(401);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM refresh_tokens WHERE user_id = ?::uuid AND revoked_at IS NULL", Integer.class, userId)).isZero();

        // 5. the same email inside the cooldown is refused; past it, the second login is not new
        MvcTestResult tooSoon = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}");
        assertThat(tooSoon).hasStatus(429);
        assertThat(tooSoon).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        assertThat(tooSoon.getResponse().getHeader("Retry-After")).isNotBlank();
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        MvcTestResult again = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}");
        assertThat(again).hasStatusOk();
        String challenge2 = again.getResponse().getContentAsString().replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
        OtpDelivery second = Inbox.DELIVERIES.stream().filter(d -> d.destination().equals(email)).reduce((a, b) -> b).orElseThrow();
        MvcTestResult verifiedAgain = post("/api/v1/auth/otp/verify",
                "{\"challenge_id\":\"" + challenge2 + "\",\"code\":\"" + second.code() + "\"}");
        assertThat(verifiedAgain).hasStatusOk();
        assertThat(verifiedAgain).bodyJson().extractingPath("$.is_new_user").isEqualTo(false);
        assertThat(verifiedAgain).bodyJson().extractingPath("$.user.id").isEqualTo(userId);

        // 6. the third code in the hour is the last; the fourth waits
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        assertThat(post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}")).hasStatusOk();
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        MvcTestResult capped = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}");
        assertThat(capped).hasStatus(429);
        assertThat(capped).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        assertThat(Integer.parseInt(capped.getResponse().getHeader("Retry-After"))).isBetween(3000, 3600);
    }

    @Test
    void wrongCodesCountDownThenTheChallengeDies() throws Exception {
        String email = "attempts-" + UUID.randomUUID() + "@example.com";
        MvcTestResult requested = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}");
        String challengeId = requested.getResponse().getContentAsString().replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
        String code = Inbox.DELIVERIES.stream().filter(d -> d.destination().equals(email)).reduce((a, b) -> b).orElseThrow().code();
        String wrong = code.equals("000000") ? "111111" : "000000";

        for (int left = 4; left >= 0; left--) {
            MvcTestResult attempt = post("/api/v1/auth/otp/verify", "{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + wrong + "\"}");
            assertThat(attempt).hasStatus(401);
            assertThat(attempt).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_INVALID");
            assertThat(attempt).bodyJson().extractingPath("$.error.details.attempts_left").isEqualTo(left);
        }
        MvcTestResult exhausted = post("/api/v1/auth/otp/verify", "{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + code + "\"}");
        assertThat(exhausted).hasStatus(401);
        assertThat(exhausted).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_EXPIRED");
        assertThat(jdbc.queryForObject("SELECT attempts FROM otp_challenges WHERE id = ?::uuid", Integer.class, challengeId)).isEqualTo(5);
    }

    @Test
    void phoneLoginIsNotAvailableWhileOnlyEmailIsEnabled() {
        MvcTestResult result = post("/api/v1/auth/otp/request", "{\"phone\":\"9876543210\"}");

        assertThat(result).hasStatus(400);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(result).bodyJson().extractingPath("$.error.details.phone").isEqualTo("channel.unavailable");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM otp_challenges WHERE channel = 'sms'", Integer.class)).isZero();
    }

    private MvcTestResult post(String uri, String json) {
        return mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).header("X-Forwarded-For", FROM).content(json).exchange();
    }

    private static String field(String json, String name) {
        return json.replaceAll(".*\"" + name + "\":\"([^\"]+)\".*", "$1");
    }
}
