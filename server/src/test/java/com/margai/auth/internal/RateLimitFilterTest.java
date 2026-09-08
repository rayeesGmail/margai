package com.margai.auth.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.margai.MutableClock;
import com.margai.common.api.ApiException;
import com.margai.common.api.ErrorCode;
import com.margai.common.api.ErrorResponses;
import com.margai.common.api.IstClock;
import com.margai.common.api.Language;
import com.margai.common.api.Principal;
import com.margai.common.api.RateLimitedException;
import com.margai.common.api.UserRole;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * TECH_PLAN §3.4 on a clock the test moves: 10 OTP requests per hour per address, 60 requests
 * per minute per signed-in user, keys independent, refill with time, {@code Retry-After} when
 * refused, and no bucket at all for anonymous traffic on the other public routes.
 */
class RateLimitFilterTest {

    private final MutableClock clock = new MutableClock(Instant.parse("2026-09-08T10:00:00Z"));
    private final ErrorResponses responses = mock(ErrorResponses.class);
    private final RateLimitFilter filter = new RateLimitFilter(new RateLimitProperties(3, 10, 60), new IstClock(clock), responses);
    private final AtomicInteger reachedController = new AtomicInteger();

    @BeforeEach
    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void tenOtpRequestsPerHourPerAddressThenRetryAfter() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertThat(otpRequestFrom("203.0.113.9")).as("request " + (i + 1)).isTrue();
        }

        assertThat(otpRequestFrom("203.0.113.9")).isFalse();
        RateLimitedException refusal = lastRefusal();
        assertThat(refusal.code()).isEqualTo(ErrorCode.OTP_RATE_LIMITED);
        assertThat(refusal.retryAfterSeconds()).isBetween(1L, 360L);
        assertThat(otpRequestFrom("203.0.113.10")).as("another address has its own bucket").isTrue();
        assertThat(reachedController.get()).isEqualTo(11);
    }

    @Test
    void bucketsRefillAsTheClockMoves() throws Exception {
        for (int i = 0; i < 10; i++) {
            otpRequestFrom("198.51.100.1");
        }
        assertThat(otpRequestFrom("198.51.100.1")).isFalse();

        clock.advance(Duration.ofMinutes(6));
        assertThat(otpRequestFrom("198.51.100.1")).as("greedy refill: one token per 6 minutes at 10/hour").isTrue();
        assertThat(otpRequestFrom("198.51.100.1")).isFalse();

        clock.advance(Duration.ofHours(1));
        for (int i = 0; i < 10; i++) {
            assertThat(otpRequestFrom("198.51.100.1")).isTrue();
        }
    }

    @Test
    void firstForwardedHopIsTheAddress() throws Exception {
        for (int i = 0; i < 10; i++) {
            otpRequest("10.0.0.5", "203.0.113.77, 10.0.0.5");
        }

        assertThat(otpRequest("10.0.0.6", "203.0.113.77, 10.0.0.6")).as("same first hop, other socket").isFalse();
        assertThat(otpRequest("10.0.0.5", "203.0.113.78, 10.0.0.5")).as("other first hop, same socket").isTrue();
    }

    @Test
    void sixtyRequestsPerMinutePerUser() throws Exception {
        Principal one = new Principal(UUID.randomUUID(), UserRole.student, Language.en);
        Principal two = new Principal(UUID.randomUUID(), UserRole.student, Language.en);

        for (int i = 0; i < 60; i++) {
            assertThat(authenticatedRequest(one)).isTrue();
        }
        assertThat(authenticatedRequest(one)).isFalse();
        assertThat(lastRefusal().code()).isEqualTo(ErrorCode.RATE_LIMITED);
        assertThat(authenticatedRequest(two)).as("another user has its own bucket").isTrue();

        clock.advance(Duration.ofMinutes(1));
        assertThat(authenticatedRequest(one)).isTrue();
    }

    @Test
    void anonymousTrafficOnOtherPublicRoutesIsNotBucketed() throws Exception {
        for (int i = 0; i < 100; i++) {
            MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/otp/verify");
            request.setRequestURI("/api/v1/auth/otp/verify");
            request.setRemoteAddr("203.0.113.1");
            assertThat(run(request)).isTrue();
        }
        verify(responses, never()).write(any(), any(), any());
    }

    private boolean otpRequestFrom(String address) throws Exception {
        return otpRequest(address, null);
    }

    private boolean otpRequest(String remoteAddress, String forwardedFor) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", RateLimitFilter.OTP_REQUEST_PATH);
        request.setRequestURI(RateLimitFilter.OTP_REQUEST_PATH);
        request.setRemoteAddr(remoteAddress);
        if (forwardedFor != null) {
            request.addHeader(ClientAddress.FORWARDED_FOR, forwardedFor);
        }
        return run(request);
    }

    private boolean authenticatedRequest(Principal principal) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new PrincipalAuthentication(principal, "jti"));
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/probe/whoami");
        request.setRequestURI("/api/v1/probe/whoami");
        return run(request);
    }

    /** Runs the filter; true when the chain (the controller) was reached. */
    private boolean run(MockHttpServletRequest request) throws Exception {
        int before = reachedController.get();
        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse res) {
                reachedController.incrementAndGet();
            }
        });
        return reachedController.get() > before;
    }

    private RateLimitedException lastRefusal() throws Exception {
        ArgumentCaptor<ApiException> failure = ArgumentCaptor.forClass(ApiException.class);
        verify(responses, atLeastOnce()).write(any(), any(), failure.capture());
        assertThat(failure.getValue()).isInstanceOf(RateLimitedException.class);
        return (RateLimitedException) failure.getValue();
    }
}
