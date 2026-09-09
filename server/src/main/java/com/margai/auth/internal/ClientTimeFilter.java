package com.margai.auth.internal;

import com.margai.common.api.IstClock;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.OptionalLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * TECH_PLAN §3.1: clients send {@code X-Client-Time} "for clock-skew diagnostics in OTP flows"
 * (PLAN D9). On the public auth routes this filter reads it, puts the drift in seconds
 * ({@code client_skew_s}, positive when the phone runs ahead) into the MDC so the request's
 * own log lines carry it (§10.1), and past {@code margai.auth.clock-skew-warn} warns once and
 * counts {@code auth.clock_skew} by band (§10.2). Nothing else changes: the login flow compares
 * durations, never wall clocks, and token validation runs on the server clock alone (§9.1) — a
 * client-chosen header can never move either. An absent or unreadable header is ignored, and
 * the header is never echoed. Registered by {@code AuthConfiguration} on {@code /api/v1/auth/*}
 * right after the request-id filter (DECISIONS 2026-09-09, D9).
 */
final class ClientTimeFilter extends OncePerRequestFilter {

    static final String HEADER = "X-Client-Time";
    static final String MDC_KEY = "client_skew_s";
    static final String METRIC = "auth.clock_skew";
    static final String BAND_TAG = "band";
    static final String URL_PATTERN = "/api/v1/auth/*";

    private static final Logger log = LoggerFactory.getLogger(ClientTimeFilter.class);
    /** The request-id MDC key of TECH_PLAN §10.1, set by {@code common}'s filter ahead of this one. */
    private static final String REQUEST_ID_MDC_KEY = "request_id";

    private final IstClock clock;
    private final long warnAboveSeconds;
    private final MeterRegistry meters;

    ClientTimeFilter(IstClock clock, Duration warnAbove, MeterRegistry meters) {
        this.clock = clock;
        this.warnAboveSeconds = warnAbove.toSeconds();
        this.meters = meters;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        OptionalLong skew = skewSeconds(request.getHeader(HEADER), clock.now());
        if (skew.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        long seconds = skew.getAsLong();
        MDC.put(MDC_KEY, Long.toString(seconds));
        try {
            long drift = Math.abs(seconds);
            if (drift > warnAboveSeconds) {
                meters.counter(METRIC, BAND_TAG, band(drift)).increment();
                log.warn("client clock is {} s {} ours on {} {} (request_id={})", drift,
                        seconds > 0 ? "ahead of" : "behind", request.getMethod(), request.getRequestURI(),
                        MDC.get(REQUEST_ID_MDC_KEY));
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }

    /** Client minus server, in whole seconds; empty when there is nothing readable to compare. */
    static OptionalLong skewSeconds(String header, Instant now) {
        if (header == null || header.isBlank()) {
            return OptionalLong.empty();
        }
        try {
            Instant client = OffsetDateTime.parse(header.strip()).toInstant();
            return OptionalLong.of(Duration.between(now, client).getSeconds());
        } catch (DateTimeParseException unreadable) {
            return OptionalLong.empty();
        }
    }

    /** The order of magnitude of a drift, for the metric's tag. */
    static String band(long driftSeconds) {
        if (driftSeconds < Duration.ofHours(1).toSeconds()) {
            return "minutes";
        }
        if (driftSeconds < Duration.ofDays(1).toSeconds()) {
            return "hours";
        }
        return "days";
    }
}
