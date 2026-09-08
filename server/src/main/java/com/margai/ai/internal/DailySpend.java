package com.margai.ai.internal;

import java.util.UUID;

/** What the breaker asks before every call (TECH_PLAN §4.8): paise spent so far this IST day. */
public interface DailySpend {

    long userSpendToday(UUID userId);

    long globalSpendToday();
}
