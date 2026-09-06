package com.margai.account.api;

/**
 * The student's language (SPEC §5.1 "Language confirm", §6.11). {@code hinglish} is the server
 * value of the app's {@code hi_Latn} locale (DECISIONS D3.3). Constants are the lowercase
 * database and wire codes.
 */
public enum Language {
    en, hi, hinglish
}
