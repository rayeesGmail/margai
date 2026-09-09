package com.margai.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.auth.AuthFlowTest.Inbox;
import com.margai.auth.internal.OtpDelivery;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * PLAN D9 ✅ — the server half of {@code docs/runbooks/login-failure-checklist.md}, over the real
 * chain, database and services (TECH_PLAN §8.1 module flow). Each method is one checklist row:
 * expiry (3), the resend cooldown (4), the hourly cap (5), clock skew (6), simultaneous first
 * logins (7), malformed requests (8) and a verify flood (the tail). The same recording inbox and
 * movable clock as {@link AuthFlowTest}; every method uses its own client address so the
 * per-address buckets never cross between tests.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, Inbox.class})
class AuthUnhappyPathsTest {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MeterRegistry meters;

    @Test
    void theRightCodeAfterFiveMinutesIsExpired() {
        String from = "203.0.113.31";
        String email = "expiry-" + UUID.randomUUID() + "@example.com";
        Challenge sent = request(email, from);

        Inbox.CLOCK.advance(Duration.ofMinutes(5).plusSeconds(1));
        MvcTestResult late = verify(sent.id(), sent.code(), from);

        assertThat(late).hasStatus(401);
        assertThat(late).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_EXPIRED");
        assertThat(late).bodyJson().doesNotHavePath("$.error.details.attempts_left");
        assertThat(jdbc.queryForObject("SELECT verified_at IS NULL FROM otp_challenges WHERE id = ?::uuid", Boolean.class,
                sent.id())).isTrue();
    }

    @Test
    void aResendInsideTheCooldownIsRateLimitedWithTheWait() {
        String from = "203.0.113.32";
        String email = "cooldown-" + UUID.randomUUID() + "@example.com";
        request(email, from);

        Inbox.CLOCK.advance(Duration.ofSeconds(10));
        MvcTestResult tooSoon = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}", from);

        assertThat(tooSoon).hasStatus(429);
        assertThat(tooSoon).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        int retryAfter = Integer.parseInt(tooSoon.getResponse().getHeader("Retry-After"));
        assertThat(retryAfter).isEqualTo(20);
        assertThat(tooSoon).bodyJson().extractingPath("$.error.details.retry_after_s").isEqualTo(retryAfter);
    }

    @Test
    void theFourthCodeInAnHourWaitsForTheWindowToPass() {
        String from = "203.0.113.33";
        String email = "cap-" + UUID.randomUUID() + "@example.com";
        request(email, from);
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        request(email, from);
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        request(email, from);
        Inbox.CLOCK.advance(Duration.ofSeconds(31));

        MvcTestResult capped = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}", from);

        assertThat(capped).hasStatus(429);
        assertThat(capped).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_RATE_LIMITED");
        int wait = Integer.parseInt(capped.getResponse().getHeader("Retry-After"));
        assertThat(wait).isEqualTo(3600 - 93);
        assertThat(capped).bodyJson().extractingPath("$.error.details.retry_after_s").isEqualTo(wait);

        Inbox.CLOCK.advance(Duration.ofSeconds(wait).plusSeconds(1));
        assertThat(post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}", from)).hasStatusOk();
    }

    @Test
    void malformedRequestsAreReasonCodesNeverProse() {
        String from = "203.0.113.34";

        MvcTestResult brokenJson = post("/api/v1/auth/otp/request", "{\"email\":", from);
        assertThat(brokenJson).hasStatus(400);
        assertThat(brokenJson).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(brokenJson).bodyJson().extractingPath("$.error.details.body").isEqualTo("malformed");

        MvcTestResult wrongType = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.TEXT_PLAIN)
                .header("X-Forwarded-For", from).content("email=someone@example.com").exchange();
        assertThat(wrongType).hasStatus(400);
        assertThat(wrongType).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(wrongType).bodyJson().extractingPath("$.error.details.content_type").isEqualTo("unsupported");

        MvcTestResult notAUuid = post("/api/v1/auth/otp/verify", "{\"challenge_id\":\"not-a-uuid\",\"code\":\"123456\"}", from);
        assertThat(notAUuid).hasStatus(400);
        assertThat(notAUuid).bodyJson().extractingPath("$.error.code").isEqualTo("VALIDATION_FAILED");
        assertThat(notAUuid).bodyJson().extractingPath("$.error.details.body").isEqualTo("malformed");

        for (MvcTestResult result : List.of(brokenJson, wrongType, notAUuid)) {
            assertThat(body(result)).doesNotContain("Exception").doesNotContain("java.").doesNotContain("challengeId");
        }
    }

    @Test
    void twoSimultaneousFirstLoginsShareOneAccount() throws Exception {
        String from = "203.0.113.35";
        String email = "twins-" + UUID.randomUUID() + "@example.com";
        Challenge first = request(email, from);
        Inbox.CLOCK.advance(Duration.ofSeconds(31));
        Challenge second = request(email, from);

        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            CyclicBarrier start = new CyclicBarrier(2);
            List<Future<MvcTestResult>> verifies = new ArrayList<>();
            for (Challenge challenge : List.of(first, second)) {
                verifies.add(pool.submit(() -> {
                    start.await();
                    return verify(challenge.id(), challenge.code(), from);
                }));
            }
            List<MvcTestResult> outcomes = new ArrayList<>();
            for (Future<MvcTestResult> verify : verifies) {
                outcomes.add(verify.get());
            }

            for (MvcTestResult outcome : outcomes) {
                assertThat(outcome).hasStatusOk();
                assertThat(outcome).bodyJson().extractingPath("$.user.email").isEqualTo(email);
            }
            assertThat(outcomes.stream().map(o -> body(o).replaceAll(".*\"user\":\\{\"id\":\"([^\"]+)\".*", "$1")).distinct())
                    .hasSize(1);
            assertThat(outcomes.stream().filter(o -> body(o).contains("\"is_new_user\":true")))
                    .as("exactly one login created the account").hasSize(1);
            assertThat(jdbc.queryForObject("SELECT count(*) FROM users WHERE email = ?", Integer.class, email)).isEqualTo(1);
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void aSkewedClientClockStillSignsInAndIsCounted() {
        String from = "203.0.113.36";
        String email = "skew-" + UUID.randomUUID() + "@example.com";
        String threeHoursAhead = Inbox.CLOCK.instant().plus(Duration.ofHours(3)).toString();
        double before = meters.counter("auth.clock_skew", "band", "hours").count();

        MvcTestResult requested = mvc.post().uri("/api/v1/auth/otp/request").contentType(MediaType.APPLICATION_JSON)
                .header("X-Forwarded-For", from).header("X-Client-Time", threeHoursAhead)
                .content("{\"email\":\"" + email + "\"}").exchange();
        assertThat(requested).hasStatusOk();
        assertThat(requested.getResponse().getHeaderNames()).doesNotContain("X-Client-Time");
        Challenge sent = new Challenge(challengeId(requested), codeFor(email));
        MvcTestResult verified = mvc.post().uri("/api/v1/auth/otp/verify").contentType(MediaType.APPLICATION_JSON)
                .header("X-Forwarded-For", from).header("X-Client-Time", threeHoursAhead)
                .content("{\"challenge_id\":\"" + sent.id() + "\",\"code\":\"" + sent.code() + "\"}").exchange();
        assertThat(verified).hasStatusOk();
        assertThat(meters.counter("auth.clock_skew", "band", "hours").count()).isEqualTo(before + 2);

        // Outside /auth the header is not looked at.
        assertThat(mvc.get().uri("/actuator/health").header("X-Client-Time", threeHoursAhead).exchange()).hasStatusOk();
        assertThat(meters.counter("auth.clock_skew", "band", "hours").count()).isEqualTo(before + 2);
    }

    @Test
    void aVerifyFloodIsRateLimitedBeforeTheService() {
        String from = "203.0.113.37";
        String guess = "{\"challenge_id\":\"" + UUID.randomUUID() + "\",\"code\":\"000000\"}";

        for (int i = 0; i < 60; i++) {
            MvcTestResult attempt = post("/api/v1/auth/otp/verify", guess, from);
            assertThat(attempt).hasStatus(401);
            assertThat(attempt).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_EXPIRED");
        }
        MvcTestResult flooded = post("/api/v1/auth/otp/verify", guess, from);

        assertThat(flooded).hasStatus(429);
        assertThat(flooded).bodyJson().extractingPath("$.error.code").isEqualTo("RATE_LIMITED");
        assertThat(Integer.parseInt(flooded.getResponse().getHeader("Retry-After"))).isBetween(1, 60);
    }

    private record Challenge(String id, String code) {
    }

    private Challenge request(String email, String from) {
        MvcTestResult requested = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}", from);
        assertThat(requested).hasStatusOk();
        return new Challenge(challengeId(requested), codeFor(email));
    }

    private MvcTestResult verify(String challengeId, String code, String from) {
        return post("/api/v1/auth/otp/verify", "{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + code + "\"}", from);
    }

    private MvcTestResult post(String uri, String json, String from) {
        return mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).header("X-Forwarded-For", from).content(json)
                .exchange();
    }

    private static String challengeId(MvcTestResult requested) {
        return body(requested).replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
    }

    private static String body(MvcTestResult result) {
        try {
            return result.getResponse().getContentAsString();
        } catch (java.io.UnsupportedEncodingException impossible) {
            throw new IllegalStateException(impossible);
        }
    }

    private static String codeFor(String email) {
        return Inbox.DELIVERIES.stream().filter(d -> d.destination().equals(email)).map(OtpDelivery::code)
                .reduce((a, b) -> b).orElseThrow();
    }
}
