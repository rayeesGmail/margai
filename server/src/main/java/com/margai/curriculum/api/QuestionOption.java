package com.margai.curriculum.api;

/**
 * One option of a question ({@code questions.options}, migration V9). A PYQ's keys are "1"–"4",
 * as NTA prints them and as its final key names them (DECISIONS 2026-10-20).
 *
 * @param key    the option's label
 * @param textEn its English text
 * @param textHi its Hindi text; null until the parked Hindi pass reads NTA's own Hindi
 */
public record QuestionOption(String key, String textEn, String textHi) {
}
