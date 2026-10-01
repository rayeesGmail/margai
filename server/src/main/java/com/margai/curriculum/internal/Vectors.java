package com.margai.curriculum.internal;

/**
 * The one place a {@code float[]} becomes something pgvector will accept, and the reason the
 * {@code embedding} column stays unmapped in {@link NcertParagraph}.
 *
 * <p>Hibernate can map {@code vector(n)} with its own module, which is not on this classpath, and
 * adding one would buy nothing: the vector search has to be a native query regardless
 * ({@code ORDER BY embedding <=> …} has no JPQL), and TECH_PLAN §8 sanctions exactly that —
 * "SQL only through JPA/JPQL or parameterised native queries". So every statement that touches the
 * column binds the vector as text and casts it, and they all go through here.
 *
 * <p>The literal is bound as a <em>parameter</em>, never concatenated into SQL. It is built from
 * floats and cannot carry an injection, but a helper that returns SQL-shaped text is exactly the
 * kind that gets concatenated by the next caller.
 */
final class Vectors {

    private Vectors() {
    }

    /** {@code [0.013,-0.44,…]} — pgvector's input form, for binding into {@code CAST(? AS vector)}. */
    static String literal(float[] vector) {
        StringBuilder text = new StringBuilder(vector.length * 12 + 2).append('[');
        for (int index = 0; index < vector.length; index++) {
            if (index > 0) {
                text.append(',');
            }
            text.append(vector[index]);
        }
        return text.append(']').toString();
    }
}
