package com.margai.curriculum.api;

import com.margai.common.api.Language;

/**
 * The language of a book edition ({@code ncert_books.s3_key_en|s3_key_hi}, the {@code --lang}
 * option of the D14–D16 {@code ncert} commands). A narrowing of {@link Language}: NCERT prints an
 * English and a Hindi edition and nothing else, while {@code hinglish} is how the app renders
 * text to a student, never how a book is printed.
 */
public enum BookLanguage {
    en, hi;

    /** The student-facing language this edition satisfies; {@code hinglish} reads the English one. */
    public Language asLanguage() {
        return this == en ? Language.en : Language.hi;
    }
}
