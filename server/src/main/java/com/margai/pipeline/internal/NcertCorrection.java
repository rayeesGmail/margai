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
 * @param printed     the span as the page prints it, in the transcription's notation — or, for a Hindi
 *                    {@code text} entry where the print states something false, its meaning (DECISIONS 2026-10-10)
 * @param at          the words a paragraph or row starts with ({@code join}, {@code drop}, {@code defer},
 *                    {@code section}, {@code move}, {@code unprinted}), where a new one must start
 *                    ({@code split}), or a span naming the paragraph ({@code figure_ref})
 * @param address     where the flag was raised, for a reader of the file; never used to apply it
 * @param removeRef   the figure or table label a {@code figure_ref} entry removes, or null
 * @param addRef      the label a {@code figure_ref} entry adds, or null
 * @param flag        which free check a {@code noise} entry rules on, or null
 * @param section     the section the English numbers what a {@code section} entry names, or null
 * @param after       the words the row a {@code move} entry's row follows starts with, or null
 * @param afterPage   the page that row starts on, or null
 * @param gapAfter    whether the English paragraph an {@code unprinted} entry names is the one after its row
 *                    rather than the one before it — a section's last, which no row follows
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
        LayoutChecks.Kind flag,
        String section,
        String after,
        Integer afterPage,
        boolean gapAfter) {

    /**
     * An entry of any kind but {@code figure_ref}, {@code noise}, {@code section} and {@code move}, which name more,
     * and an {@code unprinted} entry's gap after its row.
     */
    NcertCorrection(String book, BookLanguage language, short chapter, int page, Kind kind, String transcribed,
            String printed, String at, String reason, String address) {
        this(book, language, chapter, page, kind, transcribed, printed, at, reason, address, null, null, null, null,
                null, null, false);
    }

    enum Kind {
        /**
         * Replace a transcribed span with what the page prints — or, in a Hindi row, with its meaning where the
         * print states something false (DECISIONS 2026-10-10).
         */
        text,
        /** The paragraph starting {@code at} is the rest of the one before it. */
        join,
        /** A new paragraph starts at {@code at}. */
        split,
        /** Remove or add one figure or table label on the paragraph holding {@code at} (DECISIONS 2026-09-15). */
        figure_ref,
        /** The paragraph starting {@code at} is not running text — a caption, a margin note — and goes (DECISIONS 2026-09-15). */
        drop,
        /**
         * The paragraph starting {@code at} takes its number after the next paragraph its section starts
         * (D16). A footnote is numbered where its page puts it, and the two editions break their pages in
         * different places; this is what lets the Hindi footnote sit beside the English one. It moves no
         * word and leaves the paragraph on the page it is printed on.
         */
        defer,
        /**
         * The paragraph starting {@code at} is numbered in section {@code section} in the English, and so is every
         * later paragraph the transcription put in its section (D16). The Hindi print can number a section
         * differently — phy11-part2 goes from 8.5.4 to "8.5.6", and adds a 13.8.1 the English does not print
         * — and the transcription copies the heading, leaving rows at an address no English row has, which no
         * join can mend because a join never crosses a section. Usually the paragraph is the first under the
         * heading; a later one is a section the Hindi prints with no heading of its own, which the transcription
         * left in the section above (bio11's §10.5, 2026-10-17). Only a Hindi entry: the English is canonical.
         */
        section,
        /**
         * The row starting {@code at} on {@code page} takes its number right after the row starting {@code after}
         * on {@code afterPage}, in that row's section, earlier or later (D16). Applied to whole rows once the page
         * breaks are joined, so a row that runs across pages moves whole and no continuation changes. The Hindi
         * prints footnotes and boxes where its own pages fall: phy11-part2 sets a footnote a page late and in the
         * next section, one eight paragraphs before the English numbers it, one two after — which {@code defer},
         * one paragraph later and no further, cannot place.
         */
        move,
        /**
         * The English paragraph before the row starting {@code at} is one this edition does not print: the row takes
         * the number after it, and that number stays the English row's alone (D16). phy12-part1's Hindi leaves out
         * §3.12's English ¶5, so numbered in order its next row sat beside ¶5 and every later row of the section one
         * off; no join or split can mend a paragraph that is not there. Applied to whole rows once the page breaks
         * are joined, like {@code move}; each entry leaves one number, and two on one row leave two. With
         * {@code gapAfter} the paragraph not printed is the one after the row, for a section's last English
         * paragraph, which no row follows (bio11's §4.2.11.7 ¶3, 2026-10-17); on a row another follows the
         * numbering refuses it, that gap being the next row's to name. Only a Hindi entry: the English is canonical.
         */
        unprinted,
        /**
         * The book prints an error: a flag, not a defect. The row keeps it — or, in a Hindi row where the error
         * states something false, a {@code text} entry writes the meaning and this rules on the flag (DECISIONS
         * 2026-10-10).
         */
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
