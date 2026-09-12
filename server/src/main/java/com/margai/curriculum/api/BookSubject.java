package com.margai.curriculum.api;

/**
 * The subject of an NCERT book ({@code ncert_books.subject}, migration V7), which is not
 * {@link Subject}: NCERT ships one Biology book while the NEET taxonomy splits Biology into
 * botany and zoology. A paragraph reaches that split through {@code node_id} at the D23 anchor
 * pass (DECISIONS D14). Lowercase database codes, as everywhere.
 */
public enum BookSubject {
    physics, chemistry, biology
}
