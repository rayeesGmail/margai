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
 * @param pages      1-based page numbers within the chapter's source PDF, in order
 * @param confidence the extracting model's confidence, 0–1
 * @param aiCallId   the {@code ai_calls} row of the page call, null only for a fake-client run
 */
public record ParagraphExtraction(List<Integer> pages, BigDecimal confidence, UUID aiCallId) {

    public ParagraphExtraction {
        pages = pages == null ? List.of() : List.copyOf(pages);
    }
}
