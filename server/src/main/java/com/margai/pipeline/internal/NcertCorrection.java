package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;

/**
 * One entry of {@code pipeline/inputs/ncert-corrections.yaml} (D15): the founder's ruling on
 * something {@code ncert verify} flagged. The model's work is frozen as one canonical run per book
 * (DECISIONS 2026-09-13, FIX 5), so a defect found in it is fixed here, deterministically and on the
 * record, rather than by re-extracting a page and drawing again.
 *
 * <p>An entry is keyed on its page and a span of that page's transcription, never on a paragraph
 * address: addresses are assigned by the load and move when a join or a split is applied, while
 * the page and the words on it do not.
 *
 * @param transcribed the span as the extraction carries it ({@code text}), or as the verifier quoted
 *                    the transcription ({@code misprint}, {@code false_positive})
 * @param printed     the span as the page prints it, in the transcription's notation
 * @param at          the words a paragraph starts with ({@code join}), or where a new one must start
 *                    ({@code split})
 * @param address     where the flag was raised, for a reader of the file; never used to apply it
 */
record NcertCorrection(
        String book,
        BookLanguage language,
        short chapter,
        int page,
        Kind kind,
        String transcribed,
        String printed,
        String at,
        String reason,
        String address) {

    enum Kind {
        /** Replace a transcribed span with what the page prints. */
        text,
        /** The paragraph starting {@code at} is the rest of the one before it. */
        join,
        /** A new paragraph starts at {@code at}. */
        split,
        /** The book prints an error and the transcription rightly keeps it: a flag, not a defect. */
        misprint,
        /** The verifier was wrong about this span: a flag, not a defect. */
        false_positive;

        /** Whether this kind changes the extraction, or only rules on a flag. */
        boolean changesText() {
            return this == text || this == join || this == split;
        }
    }

    /** How a report or a refusal names the page. */
    String where() {
        return "ch " + chapter + " page " + page;
    }
}
