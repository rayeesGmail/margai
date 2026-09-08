package com.margai.auth.internal;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.margai.common.api.ErrorResponses;
import com.margai.common.api.IstClock;
import com.margai.common.api.RateLimitedException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.TimeMeter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * TECH_PLAN §1.5 step 4 and §3.4: in-process token buckets (Bucket4j, DECISIONS D3.8) keyed by
 * client address for {@code POST /auth/otp/request} (10/hour) and for the other public auth
 * routes — verify and refresh — (60/min, so a guessing or token-stuffing client is throttled
 * before it reaches the service), and by user id for every authenticated request (60/min); values
 * from {@code margai.limits.*}. An exhausted bucket answers 429 with {@code Retry-After} and the
 * envelope, without reaching the controller. The per-destination OTP cap is not here — it is a
 * durable check in {@code OtpService}. Buckets refill greedily and idle ones are evicted after an
 * hour; this holds for one API task (§13.3).
 */
final class RateLimitFilter extends OncePerRequestFilter {

    static final String OTP_REQUEST_PATH = "/api/v1/auth/otp/request";
    static final String AUTH_PREFIX = "/api/v1/auth/";

    private final RateLimitProperties limits;
    private final ErrorResponses responses;
    private final TimeMeter time;
    private final Cache<String, Bucket> buckets = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofHours(1))
            .maximumSize(200_000)
            .build();

    RateLimitFilter(RateLimitProperties limits, IstClock clock, ErrorResponses responses) {
        this.limits = limits;
        this.responses = responses;
        this.time = new ClockTimeMeter(clock);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Rule rule = ruleFor(request);
        if (rule == null) {
            chain.doFilter(request, response);
            return;
        }
        Bucket bucket = buckets.get(rule.key(), key -> newBucket(rule.capacity(), rule.period()));
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            chain.doFilter(request, response);
            return;
        }
        Duration wait = Duration.ofNanos(probe.getNanosToWaitForRefill());
        responses.write(request, response,
                rule.otp() ? RateLimitedException.otp(wait) : RateLimitedException.general(wait));
    }

    private Rule ruleFor(HttpServletRequest request) {
        String path = request.getRequestURI();
        if ("POST".equals(request.getMethod()) && OTP_REQUEST_PATH.equals(path)) {
            return new Rule("ip:" + ClientAddress.of(request), limits.otpRequestPerIpHourly(), Duration.ofHours(1), true);
        }
        if (SecurityContextHolder.getContext().getAuthentication() instanceof PrincipalAuthentication authentication) {
            return new Rule("user:" + authentication.getPrincipal().userId(), limits.authenticatedPerMinute(),
                    Duration.ofMinutes(1), false);
        }
        if (path != null && path.startsWith(AUTH_PREFIX)) {
            return new Rule("ip-auth:" + ClientAddress.of(request), limits.publicAuthPerIpPerMinute(),
                    Duration.ofMinutes(1), false);
        }
        return null;
    }

    private Bucket newBucket(long capacity, Duration period) {
        return Bucket.builder()
                .addLimit(Bandwidth.builder().capacity(capacity).refillGreedy(capacity, period).build())
                .withCustomTimePrecision(time)
                .build();
    }

    private record Rule(String key, long capacity, Duration period, boolean otp) {
    }

    /** Bucket time from the application clock (TECH_PLAN §11.1), so tests can move it. */
    private static final class ClockTimeMeter implements TimeMeter {

        private final IstClock clock;

        ClockTimeMeter(IstClock clock) {
            this.clock = clock;
        }

        @Override
        public long currentTimeNanos() {
            return Math.multiplyExact(clock.asClock().millis(), 1_000_000L);
        }

        @Override
        public boolean isWallClockBased() {
            return true;
        }
    }
}
