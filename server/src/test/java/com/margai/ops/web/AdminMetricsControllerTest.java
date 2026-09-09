package com.margai.ops.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.margai.auth.api.OtpChannelReport;
import com.margai.auth.api.OtpDeliveryReport;
import com.margai.auth.api.OtpMetrics;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * Controller slice (TECH_PLAN §8.1) for the first ops route, {@code GET /admin/metrics/otp}
 * (§3.7 ops rows, §10.3; D11): the report in snake_case without nulls (§11.3) for an admin, and
 * the {@code FORBIDDEN} envelope — not a bare 403, not a 500 — for a signed-in student, which is
 * §9.3's {@code @PreAuthorize} rendered through {@code common}'s handler.
 */
@WebMvcTest(controllers = AdminMetricsController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.margai\\.common\\.internal\\..*"))
class AdminMetricsControllerTest {

    /** The chain lets everything through; the route's own {@code @PreAuthorize} is what is under test. */
    @TestConfiguration(proxyBeanMethods = false)
    @EnableMethodSecurity
    static class PermitEverythingWithMethodSecurity {
        @Bean
        SecurityFilterChain permitEverything(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(routes -> routes.anyRequest().permitAll()).build();
        }
    }

    private static final OtpDeliveryReport REPORT = new OtpDeliveryReport(Instant.parse("2026-09-09T04:30:00Z"), List.of(
            new OtpChannelReport("sms", 0, 0, 0, 0, 0, 0, null, null),
            new OtpChannelReport("email", 5, 1, 4, 3, 2, 1, 0.8, 0.6)));

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private OtpMetrics metrics;

    @Test
    void anAdminReadsTheReportInSnakeCaseWithoutNulls() {
        when(metrics.report()).thenReturn(REPORT);

        MvcTestResult result = mvc.get().uri("/api/v1/admin/metrics/otp").with(user("founder").roles("ADMIN")).exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.since").isEqualTo("2026-09-09T04:30:00Z");
        assertThat(result).bodyJson().extractingPath("$.channels[0].channel").isEqualTo("sms");
        assertThat(result).bodyJson().doesNotHavePath("$.channels[0].success_rate");
        assertThat(result).bodyJson().doesNotHavePath("$.channels[0].first_attempt_rate");
        assertThat(result).bodyJson().extractingPath("$.channels[1].channel").isEqualTo("email");
        assertThat(result).bodyJson().extractingPath("$.channels[1].sent").isEqualTo(5);
        assertThat(result).bodyJson().extractingPath("$.channels[1].send_failed").isEqualTo(1);
        assertThat(result).bodyJson().extractingPath("$.channels[1].verified").isEqualTo(4);
        assertThat(result).bodyJson().extractingPath("$.channels[1].verified_first_attempt").isEqualTo(3);
        assertThat(result).bodyJson().extractingPath("$.channels[1].wrong_codes").isEqualTo(2);
        assertThat(result).bodyJson().extractingPath("$.channels[1].expired_unverified").isEqualTo(1);
        assertThat(result).bodyJson().extractingPath("$.channels[1].success_rate").isEqualTo(0.8);
        assertThat(result).bodyJson().extractingPath("$.channels[1].first_attempt_rate").isEqualTo(0.6);
    }

    @Test
    void aStudentIsForbiddenWithTheEnvelope() {
        MvcTestResult result = mvc.get().uri("/api/v1/admin/metrics/otp").with(user("asha").roles("STUDENT"))
                .header("Accept-Language", "hi").exchange();

        assertThat(result).hasStatus(403);
        assertThat(result).bodyJson().extractingPath("$.error.code").isEqualTo("FORBIDDEN");
        assertThat(result).bodyJson().extractingPath("$.error.message_en").asString().isNotBlank();
        assertThat(result).bodyJson().extractingPath("$.error.message_user_lang").asString().isNotBlank();
        verify(metrics, never()).report();
    }
}
