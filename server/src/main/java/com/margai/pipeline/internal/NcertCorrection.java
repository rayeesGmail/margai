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
 * @param at          the words a paragraph starts with ({@code join}, {@code drop}), where a new one must
 *                    start ({@code split}), or a span naming the paragraph ({@code figure_ref})
 * @param address     where the flag was raised, for a reader of the file; never used to apply it
 * @param removeRef   the figure or table label a {@code figure_ref} entry removes, or null
 * @param addRef      the label a {@code figure_ref} entry adds, or null
 * @param flag        which free check a {@code noise} entry rules on, or null
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
        String address,
        String removeRef,
        String addRef,
        LayoutChecks.Kind flag) {

    /** An entry of any kind but {@code figure_ref} and {@code noise}, which alone name a label or a check. */
    NcertCorrection(String book, BookLanguage language, short chapter, int page, Kind kind, String transcribed,
            String printed, String at, String reason, String address) {
        this(book, language, chapter, page, kind, transcribed, printed, at, reason, address, null, null, null);
    }

    enum Kind {
        /** Replace a transcribed span with what the page prints. */
        text,
        /** The paragraph starting {@code at} is the rest of the one before it. */
        join,
        /** A new paragraph starts at {@code at}. */
        split,
        /** Remove or add one figure or table label on the paragraph holding {@code at} (DECISIONS 2026-09-15). */
        figure_ref,
        /** The paragraph starting {@code at} is not running text — a caption, a margin note — and goes (DECISIONS 2026-09-15). */
        drop,
        /** The book prints an error and the transcription rightly keeps it: a flag, not a defect. */
        misprint,
        /** The verifier was wrong about this span: a flag, not a defect. */
        false_positive,
        /**
         * One of the free checks was wrong about the paragraph starting {@code at}: a flag, not a defect
         * (D15, 2026-09-19). The typography rules are measured guesses and a page top can be set flush
         * where a paragraph really does start, so an adjudicated flag needs a way off the clean share —
         * which `misprint` and `false_positive` cannot give it, since they are matched on the verifier's
         * quoted spans and a free check quotes none.
         */
        noise;

        /** Whether this kind changes the extraction, or only rules on a flag. */
        boolean changesText() {
            return this != misprint && this != false_positive && this != noise;
        }
    }

    /** How a report or a refusal names the page. */
    String where() {
        return "ch " + chapter + " page " + page;
    }
}
