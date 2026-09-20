package com.margai.pipeline.internal;

import java.util.List;

/**
 * One line of {@code eval/retrieval-queries.json} (TECH_PLAN §6.3): a question in a student's
 * words and the paragraph addresses that answer it.
 *
 * <p>{@code expect} is a list, not one address, because "the right paragraph" is often two or
 * three — a definition and the sentence that applies it — and scoring a run wrong for returning
 * the neighbouring half of the same idea would make the number mean less, not more.
 *
 * @param language {@code en} or {@code hi}; a Hindi query is expected to reach an <em>English</em>
 *                 paragraph, which is the whole reason the embedding pin is cross-lingual (§6.4)
 * @param note     why this query is in the set, or what had to be changed about it — the record
 *                 of how it was written, which is what stops the set quietly flattering itself
 */
public record RetrievalQuery(String id, String language, String text, List<Address> expect, String note) {

    public RetrievalQuery {
        expect = List.copyOf(expect);
    }

    public boolean isHindi() {
        return "hi".equals(language);
    }

    /** True when this address is one the query was written to reach. */
    public boolean isExpected(short chapterNo, String section, short paraNo) {
        return expect.stream().anyMatch(address -> address.matches(chapterNo, section, paraNo));
    }

    /** A paragraph address inside the book under test. */
    public record Address(short chapter, String section, short para) {

        boolean matches(short chapterNo, String sectionName, short paraNo) {
            return chapter == chapterNo && section.equals(sectionName) && para == paraNo;
        }

        @Override
        public String toString() {
            return "ch " + chapter + " §" + section + " ¶" + para;
        }
    }
}
