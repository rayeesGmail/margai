package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * TECH_PLAN §3.1 "X-Client-Time (for clock-skew diagnostics in OTP flows, PLAN D9)": the
 * difference between the phone's clock and ours travels in the MDC for the request's log lines;
 * a drift past the threshold is warned about once and counted by band; a missing or unreadable
 * header changes nothing — the flow itself never depends on the phone's clock.
 */
class ClientTimeFilterTest {

    private static final Instant NOW = Instant.parse("2026-09-09T06:00:00Z");
    private static final Duration WARN_ABOVE = Duration.ofMinutes(2);

    private final SimpleMeterRegistry meters = new SimpleMeterRegistry();
    private final ClientTimeFilter filter = new ClientTimeFilter(new IstClock(Clock.fixed(NOW, ZoneOffset.UTC)),
            WARN_ABOVE, meters);
    private final ListAppender<ILoggingEvent> authLog = new ListAppender<>();

    @BeforeEach
    void listen() {
        authLog.start();
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).addAppender(authLog);
    }

    @AfterEach
    void stopListening() {
        ((Logger) LoggerFactory.getLogger("com.margai.auth")).detachAppender(authLog);
    }

    @Test
    void aSmallDriftGoesToTheMdcOnlyAndLeavesWithTheRequest() throws Exception {
        String seen = run(NOW.minusSeconds(30).toString());

        assertThat(seen).isEqualTo("-30");
        assertThat(MDC.get(ClientTimeFilter.MDC_KEY)).isNull();
        assertThat(meters.find(ClientTimeFilter.METRIC).counters()).isEmpty();
        assertThat(authLog.list).isEmpty();
    }

    @Test
    void aLargeDriftWarnsOnceAndIsCountedByBand() throws Exception {
        String seen = run(NOW.plus(Duration.ofHours(3)).toString());

        assertThat(seen).isEqualTo("10800");
        assertThat(meters.counter(ClientTimeFilter.METRIC, ClientTimeFilter.BAND_TAG, "hours").count()).isEqualTo(1.0);
        assertThat(authLog.list).hasSize(1);
        assertThat(authLog.list.getFirst().getLevel()).isEqualTo(Level.WARN);
        assertThat(authLog.list.getFirst().getFormattedMessage()).contains("10800");
    }

    @Test
    void bandsFollowTheSizeOfTheDrift() throws Exception {
        run(NOW.minus(Duration.ofMinutes(5)).toString());
        run(NOW.plus(Duration.ofDays(2)).toString());

        assertThat(meters.counter(ClientTimeFilter.METRIC, ClientTimeFilter.BAND_TAG, "minutes").count()).isEqualTo(1.0);
        assertThat(meters.counter(ClientTimeFilter.METRIC, ClientTimeFilter.BAND_TAG, "days").count()).isEqualTo(1.0);
    }

    @Test
    void anAbsentOrUnreadableHeaderChangesNothing() throws Exception {
        assertThat(run(null)).isNull();
        assertThat(run("yesterday at noon")).isNull();
        assertThat(run("")).isNull();

        assertThat(meters.find(ClientTimeFilter.METRIC).counters()).isEmpty();
        assertThat(authLog.list).isEmpty();
    }

    @Test
    void theHeaderIsNeverEchoed() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/otp/request");
        request.addHeader(ClientTimeFilter.HEADER, NOW.plus(Duration.ofHours(3)).toString());
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        assertThat(response.getHeaderNames()).isEmpty();
    }

    /** Runs one request through the filter and returns what the chain saw in the MDC. */
    private String run(String clientTime) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/otp/request");
        if (clientTime != null) {
            request.addHeader(ClientTimeFilter.HEADER, clientTime);
        }
        AtomicReference<String> seen = new AtomicReference<>();
        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse res) {
                seen.set(MDC.get(ClientTimeFilter.MDC_KEY));
            }
        });
        return seen.get();
    }
}
