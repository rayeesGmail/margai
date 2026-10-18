package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;

/**
 * One entry of {@code pipeline/inputs/ncert-errata.yaml} (D16, DECISIONS 2026-10-17): a printing error a book makes,
 * as its loaded row stands. A row corrects an error that changes the meaning and keeps a slip that changes nothing
 * as printed; every correction is logged here, and so is every kept wrong cross-reference or garbled phrase, as an
 * errata candidate. Unlike a {@link NcertCorrection}, which is keyed on a page and a span of its transcription so
 * that it applies before the rows are numbered, an erratum names the row by its address: it records the book, not
 * the transcription, and is read against the rows once they are loaded.
 *
 * @param printed         the wrong word or phrase as the page prints it, no more of the book than that (a missing or
 *                        an extra word, or a sign or two that reads as nothing alone, takes a neighbour on each side
 *                        where it has one, and a span recurring in its row more neighbours, within the approved
 *                        entry's own span, until it names its place — DECISIONS 2026-10-17, the working-rules row)
 * @param corrected       what it should read — the row's text for a {@code corrected} entry, the other edition or
 *                        the sense for a {@code kept} one
 * @param round           the approval the correction or ruling came with and its commit, e.g. "bio11 Hindi round one,
 *                        approved 2026-10-17 (ec40175)"; for an English kept entry, the date it was parked
 * @param meaningChanging whether a student reading the printed words would learn something false
 * @param flagReviewed    whether the founder has reviewed Claude's two classifications — {@code meaningChanging}, and
 *                        whether a kept place is a wrong cross-reference or a garbled phrase (founder, 2026-10-17)
 */
record NcertErratum(
        String book,
        BookLanguage language,
        short chapter,
        String section,
        short para,
        int page,
        String printed,
        String corrected,
        String reason,
        String round,
        boolean meaningChanging,
        Action action,
        boolean flagReviewed) {

    enum Action {
        /** The row writes {@code corrected}. */
        corrected,
        /** The row keeps {@code printed}: an errata candidate. */
        kept
    }

    /** The row's address, as a report names it. */
    String address() {
        return "ch " + chapter + " §" + section + " ¶" + para;
    }

    /** The span the loaded row must carry: its correction, or the print it keeps. */
    String carried() {
        return action == Action.corrected ? corrected : printed;
    }
}
