package com.margai.pipeline.internal;

import com.margai.ai.tasks.NcertPage;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * One line of {@code extract/{book}/{lang}.jsonl} (TECH_PLAN §6.3): everything one page call
 * produced. §6.3 lists these fields flat, per paragraph; they are grouped per page here because
 * three of them belong to the page and not to a paragraph — the confidence, the {@code ai_calls}
 * row that paid for it, and the page number — and because a page with no paragraphs at all (a
 * plate, a full-page figure, an exercise page) must still be recorded as read, or every re-run
 * would extract it again (DECISIONS D14).
 *
 * @param aiCallId the ledger row, null only on a fake-client run
 */
record ExtractedPage(
        short chapterNo,
        int page,
        BigDecimal confidence,
        UUID aiCallId,
        List<NcertPage.Paragraph> paragraphs) {

    ExtractedPage {
        paragraphs = paragraphs == null ? List.of() : List.copyOf(paragraphs);
    }

    static ExtractedPage of(short chapterNo, int page, NcertPage read, UUID aiCallId) {
        return new ExtractedPage(chapterNo, page, read.confidence(), aiCallId, read.paragraphs());
    }

    /** The address of this page, as a report and a resume check use it. */
    String address() {
        return chapterNo + "/" + page;
    }
}
