package com.margai.account.api;

import com.margai.common.api.Category;
import com.margai.common.api.Language;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * {@code PATCH /me} (TECH_PLAN §3.7, D10) as the service sees it: every field optional, a
 * {@code null} means "unchanged". Nothing can be cleared through this call at D10 — no screen needs
 * it (DECISIONS D10). The web layer has already validated shape and vocabulary, so the values here
 * are typed. A language change affects generated content going forward only (SPEC §6.11,
 * DEV_SPEC §8.3) and reaches the JWT on the next refresh (§3.8).
 */
public record ProfileUpdate(
        Language language,
        String displayName,
        LocalTime morningNotificationTime,
        BigDecimal hoursWeekday,
        BigDecimal hoursWeekend,
        Goal goal,
        String stateCode,
        Category category) {

    public boolean isEmpty() {
        return language == null && displayName == null && morningNotificationTime == null && hoursWeekday == null
                && hoursWeekend == null && goal == null && stateCode == null && category == null;
    }
}
