package com.margai.common.api;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The only way code learns "today" (TECH_PLAN §11.1): every study day, streak, limit,
 * notification cap and the nightly run keys on the Asia/Kolkata calendar date. Storage stays
 * {@code TIMESTAMPTZ} in UTC; this class converts. Production uses {@link #system()}; tests
 * construct one over {@link Clock#fixed} to sit at 23:59 or 00:01 IST.
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

    public Instant now() {
        return clock.instant();
    }

    public ZonedDateTime nowIst() {
        return ZonedDateTime.now(clock);
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
