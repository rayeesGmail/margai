package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookSubject;
import java.util.List;

/**
 * One line of {@code eval/retrieval-queries.json} (TECH_PLAN §6.3): a question in a student's
 * words, the subject retrieval is filtered to, and the paragraph addresses that answer it.
 *
 * <p>{@code expect} is a list, not one address, because "the right paragraph" is often two or
 * three — a definition and the sentence that applies it — and scoring a run wrong for returning
 * the neighbouring half of the same idea would make the number mean less, not more.
 *
 * @param set      which set the query belongs to: the D17 {@code acceptance} set its ✅ is read
 *                 from, the D15 {@code baseline} it is compared against, or a {@code diagnostic}
 *                 that is scored but decides nothing (DECISIONS 2026-10-18)
 * @param subject  the book subject retrieval is filtered to, as the doubt pipeline filters
 *                 (§4.3 stage 6) — Biology is one subject here, botany and zoology together
 * @param language {@code en}, {@code hi} or {@code hinglish}; a Hindi or Hinglish query is expected
 *                 to reach an <em>English</em> paragraph, which is the whole reason the embedding
 *                 pin is cross-lingual (§6.4)
 * @param note     why this query is in the set, or what had to be changed about it — the record
 *                 of how it was written, which is what stops the set quietly flattering itself
 */
public record RetrievalQuery(String id, QuerySet set, BookSubject subject, String language, String text,
        List<Address> expect, String note) {

    /** The three sets a query can belong to; only {@code acceptance} decides D17's ✅. */
    public enum QuerySet { acceptance, baseline, diagnostic }

    public RetrievalQuery {
        expect = List.copyOf(expect);
    }

    public boolean isHindi() {
        return "hi".equals(language);
    }

    public boolean isHinglish() {
        return "hinglish".equals(language);
    }

    /** True when this paragraph is one the query was written to reach. */
    public boolean isExpected(String book, short chapterNo, String section, short paraNo) {
        return expect.stream().anyMatch(address -> address.matches(book, chapterNo, section, paraNo));
    }

    /**
     * A paragraph address in the corpus. The book is part of it because chapter numbers repeat
     * across books — phy11-part1 and phy12-part1 both open at chapter 1 — and an address without
     * one would count a paragraph of the wrong book as the right answer.
     */
    public record Address(String book, short chapter, String section, short para) {

        boolean matches(String bookCode, short chapterNo, String sectionName, short paraNo) {
            return book.equals(bookCode) && chapter == chapterNo && section.equals(sectionName) && para == paraNo;
        }

        @Override
        public String toString() {
            return book + " ch " + chapter + " §" + section + " ¶" + para;
        }
    }
}
