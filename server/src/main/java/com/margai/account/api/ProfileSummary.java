package com.margai.account.api;

import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code profile} half of {@code GET /me} (TECH_PLAN §3.7, D10): the scalar columns of
 * {@code student_profiles} (§2.2) as the app needs them on start — the onboarding step for the
 * router, the answers of SPEC §5.1 for Profile & settings, the streaks for Today. The JSON
 * documents ({@code scorecard}, {@code board_marks}) wait for their record shapes (D28); nulls are
 * omitted on the wire (§11.3).
 */
public record ProfileSummary(
        AttemptType attemptType,
        Short targetYear,
        CoachingMode coachingMode,
        CoachingProvider coachingProvider,
        BigDecimal hoursWeekday,
        BigDecimal hoursWeekend,
        Goal goal,
        String stateCode,
        Category category,
        LocalDate dob,
        boolean isMinor,
        Short lastNeetYear,
        Short lastNeetScore,
        Integer lastNeetRank,
        String onboardingStep,
        Instant onboardingCompletedAt,
        LocalDate examDate,
        LocalTime morningNotificationTime,
        int currentStreak,
        int longestStreak) {
}
