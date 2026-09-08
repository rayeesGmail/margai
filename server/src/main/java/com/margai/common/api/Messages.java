package com.margai.common.api;

/**
 * Server-side copy (TECH_PLAN §11.7): error messages and notification or OTP text, addressed by
 * key and language, authored per language in {@code messages_{en,hi,hinglish}.properties} —
 * never machine-translated. A key missing in a language falls back to English; a key missing
 * everywhere renders as the key itself, which a test would catch.
 */
public interface Messages {

    String message(String key, Language language, Object... args);
}
