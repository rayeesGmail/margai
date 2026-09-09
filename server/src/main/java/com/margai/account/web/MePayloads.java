package com.margai.account.web;

import com.margai.account.api.Goal;
import com.margai.account.api.ProfileUpdate;
import com.margai.common.api.Category;
import com.margai.common.api.Language;
import com.margai.common.api.ValidationException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * Request records of {@code /me} (TECH_PLAN §3.7, §11.3). {@code PATCH /me} arrives as raw strings
 * and numbers and is checked here in one pass, so the envelope names every bad field at once with
 * a reason code and never prose (§3.3): {@code <field>.invalid} for a value outside the vocabulary
 * (the enum is the single source of truth — no regex to keep in step with it), the bare constraint
 * names of D7 ({@code not_blank}, {@code size}, {@code decimal_min}, {@code decimal_max}) otherwise.
 */
final class MePayloads {

    static final int DISPLAY_NAME_MAX = 80;
    static final BigDecimal HOURS_MIN = BigDecimal.ZERO;
    static final BigDecimal HOURS_MAX = new BigDecimal("16.0");

    private MePayloads() {
    }

    /** {@code PATCH /me}: every field optional; an absent field is unchanged. */
    record UpdateBody(
            String language,
            String displayName,
            String morningNotificationTime,
            BigDecimal hoursWeekday,
            BigDecimal hoursWeekend,
            String goal,
            String stateCode,
            String category) {

        ProfileUpdate toUpdate() {
            Map<String, String> reasons = new LinkedHashMap<>();
            Language lang = vocabulary("language", language, Language::valueOf, reasons);
            String name = name(reasons);
            LocalTime time = time(reasons);
            BigDecimal weekday = hours("hours_weekday", hoursWeekday, reasons);
            BigDecimal weekend = hours("hours_weekend", hoursWeekend, reasons);
            Goal wantedGoal = vocabulary("goal", goal, Goal::valueOf, reasons);
            String state = state(reasons);
            Category wantedCategory = vocabulary("category", category, Category::valueOf, reasons);
            if (!reasons.isEmpty()) {
                throw ValidationException.of(reasons);
            }
            return new ProfileUpdate(lang, name, time, weekday, weekend, wantedGoal, state, wantedCategory);
        }

        private String name(Map<String, String> reasons) {
            if (displayName == null) {
                return null;
            }
            String trimmed = displayName.strip();
            if (trimmed.isEmpty()) {
                reasons.put("display_name", "not_blank");
            } else if (trimmed.length() > DISPLAY_NAME_MAX) {
                reasons.put("display_name", "size");
            }
            return trimmed;
        }

        private LocalTime time(Map<String, String> reasons) {
            if (morningNotificationTime == null) {
                return null;
            }
            try {
                return LocalTime.parse(morningNotificationTime.strip());
            } catch (DateTimeParseException malformed) {
                reasons.put("morning_notification_time", "time.invalid");
                return null;
            }
        }

        private String state(Map<String, String> reasons) {
            if (stateCode == null) {
                return null;
            }
            String code = stateCode.strip().toUpperCase(Locale.ROOT);
            if (code.length() != 2 || !code.chars().allMatch(Character::isLetter)) {
                reasons.put("state_code", "state_code.invalid");
            }
            return code;
        }

        private static BigDecimal hours(String field, BigDecimal value, Map<String, String> reasons) {
            if (value == null) {
                return null;
            }
            if (value.compareTo(HOURS_MIN) < 0) {
                reasons.put(field, "decimal_min");
            } else if (value.compareTo(HOURS_MAX) > 0) {
                reasons.put(field, "decimal_max");
            }
            return value.setScale(1, java.math.RoundingMode.HALF_UP);
        }

        private static <E extends Enum<E>> E vocabulary(String field, String raw, Function<String, E> valueOf,
                Map<String, String> reasons) {
            if (raw == null) {
                return null;
            }
            try {
                return valueOf.apply(raw.strip());
            } catch (IllegalArgumentException outsideTheVocabulary) {
                reasons.put(field, field + ".invalid");
                return null;
            }
        }
    }
}
