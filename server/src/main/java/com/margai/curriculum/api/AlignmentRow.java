package com.margai.curriculum.api;

import java.util.List;
import java.util.UUID;

/**
 * One paragraph address as {@code ncert align} reads it (TECH_PLAN §6.3, D16): both editions' text, either of
 * which may be absent, and the pages each was read from. The English rows are canonical and Hindi follows
 * them (DECISIONS 2026-10-01), so this is what a Hindi paragraph is aligned against.
 *
 * @param pagesEn the pages the English text was read from; empty when the row has no English
 * @param pagesHi the pages the Hindi text was read from; empty when the row has no Hindi
 */
public record AlignmentRow(UUID id, short chapterNo, String section, short paraNo, String textEn, String textHi,
        List<Integer> pagesEn, List<Integer> pagesHi) {

    public AlignmentRow {
        pagesEn = pagesEn == null ? List.of() : List.copyOf(pagesEn);
        pagesHi = pagesHi == null ? List.of() : List.copyOf(pagesHi);
    }

    /** The address the founder spot-checks against the PDF. */
    public String address() {
        return "ch " + chapterNo + " §" + section + " ¶" + paraNo;
    }
}
