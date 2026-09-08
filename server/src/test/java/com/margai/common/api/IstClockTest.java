package com.margai.common.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * TECH_PLAN §11.1: "today" is the Asia/Kolkata date. 18:29 UTC is 23:59 IST, 18:31 UTC is 00:01
 * IST of the next day — the boundary every daily limit and the breaker key on.
 */
class IstClockTest {

    @Test
    void todayIsTheIstCalendarDate() {
        IstClock beforeMidnight = new IstClock(Clock.fixed(Instant.parse("2026-09-06T18:29:00Z"), IstClock.IST));
        IstClock afterMidnight = new IstClock(Clock.fixed(Instant.parse("2026-09-06T18:31:00Z"), IstClock.IST));

        assertThat(beforeMidnight.today()).isEqualTo(LocalDate.of(2026, 9, 6));
        assertThat(afterMidnight.today()).isEqualTo(LocalDate.of(2026, 9, 7));
    }

    @Test
    void startOfTodayIsIstMidnightAsAnInstant() {
        IstClock clock = new IstClock(Clock.fixed(Instant.parse("2026-09-06T18:31:00Z"), IstClock.IST));

        assertThat(clock.startOfToday()).isEqualTo(Instant.parse("2026-09-06T18:30:00Z"));
        assertThat(clock.now()).isEqualTo(Instant.parse("2026-09-06T18:31:00Z"));
        assertThat(clock.nowIst().getHour()).isZero();
        assertThat(clock.nowIst().getMinute()).isEqualTo(1);
    }

    @Test
    void anyZoneOnTheGivenClockIsIgnored() {
        IstClock clock = new IstClock(Clock.fixed(Instant.parse("2026-09-06T18:31:00Z"), java.time.ZoneOffset.UTC));

        assertThat(clock.today()).isEqualTo(LocalDate.of(2026, 9, 7));
    }
}
