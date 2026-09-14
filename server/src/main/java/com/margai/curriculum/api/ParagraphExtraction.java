package com.margai.curriculum.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * {@code ncert_paragraphs.extraction} (TECH_PLAN §2.3): how this paragraph came to exist —
 * the page images it was read from (a paragraph may straddle two), the model's own confidence,
 * and the {@code ai_calls} row that paid for it, so any paragraph can be traced back to the
 * call that produced it and to the page the founder spot-checks it against (PLAN D14 ✅).
 *
 * <p>Since D15 it also says which characters each page printed, and carries the second read's
 * verdict: {@code ncert verify --read-pages} judges a paragraph against each page for the part
 * that page carries, so the text of a paragraph straddling a page break must be divisible by page
 * (DECISIONS 2026-09-14, the pair).
 *
 * @param pages        1-based page numbers within the chapter's source PDF, in order
 * @param pageStarts   the offset in the text where each page's part begins, one per page; empty
 *                     for a row loaded before D15, which verify refuses until it is reloaded
 * @param confidence   the extracting model's confidence, 0–1
 * @param aiCallId     the {@code ai_calls} row of the page call, null only for a fake-client run
 * @param verification the second read's verdict on this text, null until verified
 */
public record ParagraphExtraction(
        List<Integer> pages,
        List<Integer> pageStarts,
        BigDecimal confidence,
        UUID aiCallId,
        ParagraphVerification verification) {

    public ParagraphExtraction {
        pages = pages == null ? List.of() : List.copyOf(pages);
        pageStarts = pageStarts == null ? List.of() : List.copyOf(pageStarts);
    }

    /** A single-page paragraph's provenance: its text starts where its page does. */
    public ParagraphExtraction(List<Integer> pages, BigDecimal confidence, UUID aiCallId) {
        this(pages, pages == null || pages.size() != 1 ? List.of() : List.of(0), confidence, aiCallId, null);
    }

    public ParagraphExtraction withVerification(ParagraphVerification verification) {
        return new ParagraphExtraction(pages, pageStarts, confidence, aiCallId, verification);
    }
}
