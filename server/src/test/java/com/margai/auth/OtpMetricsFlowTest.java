package com.margai.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.MutableClock;
import com.margai.TestcontainersConfiguration;
import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import com.margai.auth.internal.JwtService;
import com.margai.auth.internal.OtpDelivery;
import com.margai.auth.internal.OtpSender;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.UserRole;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * PLAN D11 ✅ "OTP success metric visible", in test form (SPEC §11; TECH_PLAN §10.2, §10.3): over
 * the real chain, database and services, three logins — a clean one, one after a wrong code,
 * and one whose code is left to die — then the admin report shows exactly that, and the actuator
 * shows the same counter. Numbers are asserted as deltas against a report taken first.
 *
 * <p>The clock starts a day ahead: every test class shares one database, the other flow tests
 * commit unverified challenges a few minutes past real time, and this test must be the only
 * writer inside its own {@code since} window.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, OtpMetricsFlowTest.Fixture.class})
class OtpMetricsFlowTest {

    @TestConfiguration(proxyBeanMethods = false)
    static class Fixture {

        static final List<OtpDelivery> DELIVERIES = new CopyOnWriteArrayList<>();
        static final MutableClock CLOCK = new MutableClock(
                Instant.now().plus(1, ChronoUnit.DAYS).with(java.time.temporal.ChronoField.NANO_OF_SECOND, 999_999_999));

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

    private static final String FROM = "203.0.113.211";
    private static final Principal ADMIN = new Principal(UUID.randomUUID(), UserRole.admin, Language.en);

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JwtService jwts;

    @Autowired
    private OtpMetrics metrics;

    @Test
    void threeLoginsShowUpInTheAdminReportAndTheActuator() throws Exception {
        OtpChannelReport before = email(metrics.report());

        // (a) a clean login
        String clean = "metrics-clean-" + UUID.randomUUID() + "@example.com";
        assertThat(verify(request(clean), codeFor(clean))).hasStatusOk();

        // (b) a wrong code, then the right one
        String retried = "metrics-retry-" + UUID.randomUUID() + "@example.com";
        String challenge = request(retried);
        String code = codeFor(retried);
        MvcTestResult wrong = verify(challenge, code.equals("000000") ? "111111" : "000000");
        assertThat(wrong).hasStatus(401);
        assertThat(wrong).bodyJson().extractingPath("$.error.code").isEqualTo("OTP_INVALID");
        assertThat(verify(challenge, code)).hasStatusOk();

        // (c) a code nobody types, left to expire (§3.2: five minutes)
        request("metrics-lost-" + UUID.randomUUID() + "@example.com");
        Fixture.CLOCK.advance(Duration.ofSeconds(301));

        OtpDeliveryReport report = metrics.report();
        OtpChannelReport after = email(report);
        assertThat(after.sent() - before.sent()).isEqualTo(3);
        assertThat(after.sendFailed() - before.sendFailed()).isZero();
        assertThat(after.verified() - before.verified()).isEqualTo(2);
        assertThat(after.verifiedFirstAttempt() - before.verifiedFirstAttempt()).isEqualTo(1);
        assertThat(after.wrongCodes() - before.wrongCodes()).isEqualTo(1);
        assertThat(after.expiredUnverified() - before.expiredUnverified()).isEqualTo(1);
        assertThat(after.successRate()).isEqualTo(rounded(after.verified(), after.sent()));
        assertThat(after.firstAttemptRate()).isEqualTo(rounded(after.verifiedFirstAttempt(), after.sent()));

        // the founder's view: GET /admin/metrics/otp with an admin bearer
        MvcTestResult admin = mvc.get().uri("/api/v1/admin/metrics/otp")
                .header("Authorization", "Bearer " + jwts.issue(ADMIN)).exchange();
        assertThat(admin).hasStatusOk();
        assertThat(admin).bodyJson().extractingPath("$.since").asString().startsWith(report.since().toString().substring(0, 19));
        assertThat(admin).bodyJson().extractingPath("$.channels[1].channel").isEqualTo("email");
        assertThat(admin).bodyJson().extractingPath("$.channels[1].sent").isEqualTo((int) after.sent());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].send_failed").isEqualTo((int) after.sendFailed());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].verified").isEqualTo((int) after.verified());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].verified_first_attempt").isEqualTo((int) after.verifiedFirstAttempt());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].wrong_codes").isEqualTo((int) after.wrongCodes());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].expired_unverified").isEqualTo((int) after.expiredUnverified());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].success_rate").isEqualTo(after.successRate());
        assertThat(admin).bodyJson().extractingPath("$.channels[1].first_attempt_rate").isEqualTo(after.firstAttemptRate());
        assertThat(admin).bodyJson().extractingPath("$.channels[0].channel").isEqualTo("sms");
        assertThat(admin).bodyJson().extractingPath("$.channels[0].sent").isEqualTo(0);
        assertThat(admin).bodyJson().doesNotHavePath("$.channels[0].success_rate");

        // the same counter through Micrometer's own endpoint (admin only, D11)
        MvcTestResult meter = mvc.get().uri("/actuator/metrics/otp.verified?tag=channel:email&tag=first_attempt:true")
                .header("Authorization", "Bearer " + jwts.issue(ADMIN)).exchange();
        assertThat(meter).hasStatusOk();
        assertThat(meter).bodyJson().extractingPath("$.measurements[0].statistic").isEqualTo("COUNT");
        assertThat(meter).bodyJson().extractingPath("$.measurements[0].value").isEqualTo((double) after.verifiedFirstAttempt());
    }

    private static OtpChannelReport email(OtpDeliveryReport report) {
        return report.channels().stream().filter(c -> c.channel().equals("email")).findFirst().orElseThrow();
    }

    private static double rounded(long part, long whole) {
        return Math.round(1000.0 * part / whole) / 1000.0;
    }

    /** Requests a code for {@code email} and answers the challenge id. */
    private String request(String email) throws Exception {
        MvcTestResult requested = post("/api/v1/auth/otp/request", "{\"email\":\"" + email + "\"}");
        assertThat(requested).hasStatusOk();
        return requested.getResponse().getContentAsString().replaceAll(".*\"challenge_id\":\"([^\"]+)\".*", "$1");
    }

    private static String codeFor(String email) {
        return Fixture.DELIVERIES.stream().filter(d -> d.destination().equals(email)).reduce((a, b) -> b).orElseThrow().code();
    }

    private MvcTestResult verify(String challengeId, String code) {
        return post("/api/v1/auth/otp/verify", "{\"challenge_id\":\"" + challengeId + "\",\"code\":\"" + code + "\"}");
    }

    private MvcTestResult post(String uri, String json) {
        return mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).header("X-Forwarded-For", FROM).content(json).exchange();
    }
}
