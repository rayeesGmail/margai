package com.margai.pipeline.internal;

import java.time.LocalDate;
import java.util.List;

/**
 * One of the founder's rulings on a PYQ tag ({@code pipeline/inputs/pyq-tag-rulings.yaml}, D19; DECISIONS
 * 2026-10-21): the tag {@code pyq load} puts in place of the model's, whole — a field it leaves out is empty,
 * never the model's — with where NCERT teaches the question and the taxonomy node proposed for it.
 *
 * @param anchor where NCERT teaches it; {@value #NO_NCERT_ANCHOR} marks "no NCERT anchor" — a topic NCERT has
 *               cut but the NMC syllabus still lists, which D20 does not solve until the founder rules; null when
 *               the ruling records none
 * @param gap    the taxonomy node proposed for it where none fits (the taxonomy-gap list), or null
 */
record PyqTagRuling(short question, String primary, List<String> secondary, boolean outOfSyllabus, String anchor,
        String gap, LocalDate ruled, String note) {

    static final String NO_NCERT_ANCHOR = "none";

    PyqTagRuling {
        secondary = secondary == null ? List.of() : List.copyOf(secondary);
    }

    boolean noNcertAnchor() {
        return NO_NCERT_ANCHOR.equals(anchor);
    }
}
