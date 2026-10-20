package com.margai.curriculum.api;

/**
 * How a question's official key reads ({@code questions.answer_status}, migration V9): one key,
 * several accepted keys — NTA's "(2,3)" — or dropped, where NTA's final key names no answer. The
 * column's CHECK ties each status to what {@code correct_key} and {@code accepted_keys} may hold
 * (founder's D19 rulings, DECISIONS 2026-10-20). Lowercase database codes, as everywhere.
 */
public enum AnswerStatus {
    single, multiple, dropped
}
