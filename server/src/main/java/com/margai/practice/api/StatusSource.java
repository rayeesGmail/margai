package com.margai.practice.api;

/**
 * Where a {@code chapter_status} row's current values came from (TECH_PLAN §2.4): the student's
 * own check-in, behaviour, a batch timetable, or the diagnostic. Lowercase codes.
 */
public enum StatusSource {
    self_report, inferred, timetable, diagnostic
}
