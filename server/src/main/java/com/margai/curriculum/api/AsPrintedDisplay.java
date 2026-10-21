package com.margai.curriculum.api;

/**
 * Whether the app shows a misprint kept as printed (founder, 2026-10-21): {@code show} for one that could change
 * how a student reads the question — a wrong unit, symbol, number, sign or formula, or a misspelling that forms a
 * different real word — and {@code record_only} for a pure spelling or grammar slip a student reads correctly,
 * which is kept and recorded but not surfaced, as the NCERT rule keeps such slips.
 */
public enum AsPrintedDisplay {
    show,
    record_only
}
