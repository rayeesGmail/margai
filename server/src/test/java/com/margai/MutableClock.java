package com.margai;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * A clock tests move by hand (TECH_PLAN §8.6 "a MutableClock bean for IST-boundary tests"):
 * wrap it in an {@code IstClock} and {@link #advance(Duration)} or {@link #set(Instant)} between
 * calls. Thread-safe enough for tests: reads and writes are volatile.
 */
public final class MutableClock extends Clock {

    private volatile Instant now;
    private final ZoneId zone;

    public MutableClock(Instant start) {
        this(start, ZoneOffset.UTC);
    }

    private MutableClock(Instant start, ZoneId zone) {
        this.now = start;
        this.zone = zone;
    }

    public void advance(Duration by) {
        now = now.plus(by);
    }

    public void set(Instant instant) {
        now = instant;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    /** A view in another zone that still follows this clock's moves (IstClock wraps one). */
    @Override
    public Clock withZone(ZoneId newZone) {
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return newZone;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return MutableClock.this.withZone(zone);
            }

            @Override
            public Instant instant() {
                return MutableClock.this.now;
            }
        };
    }

    @Override
    public Instant instant() {
        return now;
    }
}
