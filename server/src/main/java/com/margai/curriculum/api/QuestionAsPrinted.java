package com.margai.curriculum.api;

/**
 * A printing error an official paper makes and the bank keeps as printed ({@code questions.as_printed},
 * migration V10): the question text stays the paper's own words, and this is the flag the app can show
 * as an "as printed in the original paper" note, like the NCERT errata notes (founder, 2026-10-21;
 * PLAN D31, D40; the shape is Claude's, DECISIONS 2026-10-21). A wrong character the paper prints is a misprint and kept; a stroke the scan lost is read as
 * meant and never lands here (DECISIONS 2026-10-20, 2026-10-21).
 *
 * @param at       {@code stem}, or {@code option:<key>} for a misprint in an option
 * @param printed  the span as printed and kept in that text — no more of the paper than the wrong word or
 *                 sign and what it needs to name its place
 * @param intended what the paper meant there
 * @param note     one line describing the intended reading
 * @param display  whether the app shows the note ({@code show}) or only records it ({@code record_only}) — the
 *                 founder's class, 2026-10-21
 */
public record QuestionAsPrinted(String at, String printed, String intended, String note, AsPrintedDisplay display) {
}
