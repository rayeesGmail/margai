package com.margai.curriculum.api;

import java.util.UUID;

/**
 * One NCERT paragraph a retrieval query found, with the score of the half that found it
 * (TECH_PLAN §1.3, §4.3 stage 6). The vector half scores cosine similarity in 0..1; the text half
 * scores {@code ts_rank}, which is unbounded and not comparable to it — which is exactly why
 * {@code HybridRetriever} fuses the two by <em>rank</em> rather than by score.
 *
 * <p>Both texts travel, not just the canonical English one: the answer a student reads is rendered
 * in their language (SPEC §6.3, D43), and a paragraph that only the Hindi {@code tsv} matched
 * would otherwise arrive with nothing to show.
 */
public record ParagraphMatch(
        UUID paragraphId,
        String bookCode,
        short chapterNo,
        String section,
        short paraNo,
        String textEn,
        String textHi,
        double score) {

    /** What the app shows under an answer: "Class 11 Physics, Ch 7, §7.9" (SPEC §6.3). */
    public String address() {
        return bookCode + " ch " + chapterNo + " §" + section + " ¶" + paraNo;
    }

    /** The grounding text: English is canonical (§6.4), Hindi only where a row has no English. */
    public String text() {
        return textEn != null && !textEn.isBlank() ? textEn : textHi;
    }

    public ParagraphMatch withScore(double score) {
        return new ParagraphMatch(paragraphId, bookCode, chapterNo, section, paraNo, textEn, textHi, score);
    }
}
