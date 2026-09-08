package com.margai.common.api;

/**
 * The student's language (SPEC §5.1 "Language confirm", §6.11). {@code hinglish} is the server
 * value of the app's {@code hi_Latn} locale (DECISIONS D3.3). Constants are the lowercase
 * database and wire codes. Lives in {@code common.api} since D7: it is stored by {@code users},
 * carried in the JWT {@code lang} claim and selects the message catalog (TECH_PLAN §3.8).
 */
public enum Language {
    en, hi, hinglish
}
