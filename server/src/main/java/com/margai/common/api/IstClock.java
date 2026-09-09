package com.margai.common.api;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The only way code learns "today" (TECH_PLAN §11.1): every study day, streak, limit,
 * notification cap and the nightly run keys on the Asia/Kolkata calendar date. Storage stays
 * {@code TIMESTAMPTZ} in UTC; this class converts. Production uses {@link #system()}; tests
 * construct one over {@link Clock#fixed} to sit at 23:59 or 00:01 IST.
 *
 * <p>Instants are truncated to microseconds, the precision {@code TIMESTAMPTZ} keeps: a Linux
 * JDK hands out nanosecond instants and Postgres rounds them to the nearest microsecond on the
 * way in, so an untruncated {@code created_at} read back a fraction later than the clock that
 * wrote it — enough for a rounded-up 20-second wait to read 21 (D9 CI, DECISIONS 2026-09-09).
 */
public final class IstClock {

    public static final ZoneId IST = ZoneId.of("Asia/Kolkata");

    private final Clock clock;

    public IstClock(Clock clock) {
        this.clock = clock.withZone(IST);
    }

    public static IstClock system() {
        return new IstClock(Clock.systemUTC());
    }

    /** The current instant at {@code TIMESTAMPTZ} precision (whole microseconds). */
    public Instant now() {
        return clock.instant().truncatedTo(ChronoUnit.MICROS);
    }

    /** The underlying clock, for libraries that validate time themselves (token expiry, rate limits). */
    public Clock asClock() {
        return clock;
    }

    public ZonedDateTime nowIst() {
        return now().atZone(IST);
    }

    /** The IST calendar date. */
    public LocalDate today() {
        return LocalDate.now(clock);
    }

    /** The instant at which the current IST day began (00:00 Asia/Kolkata as UTC). */
    public Instant startOfToday() {
        return today().atStartOfDay(IST).toInstant();
    }
}
