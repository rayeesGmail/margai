package com.margai.curriculum.api;

import java.util.List;
import java.util.UUID;

/**
 * {@code questions.extraction} (migration V9): how a PYQ's text came to exist — the booklet pages
 * it was read from (a question may straddle two), the {@code ai_calls} rows of those page reads,
 * the transcriber and prompt version, and the mechanical checks' flags it carried into review —
 * so any question can be traced to the calls that produced it and the page it is checked against.
 *
 * @param pages         1-based pages of the source booklet, in order
 * @param aiCallIds     the page calls' ledger rows, one per page; empty only for a fake-client run
 * @param model         the transcribing model
 * @param promptVersion the extraction prompt's version, e.g. {@code pyq_extract.v1}
 * @param flags         the mechanical checks' flags raised on it, each adjudicated before approval
 */
public record QuestionExtraction(
        List<Integer> pages,
        List<UUID> aiCallIds,
        String model,
        String promptVersion,
        List<String> flags) {

    public QuestionExtraction {
        pages = pages == null ? List.of() : List.copyOf(pages);
        aiCallIds = aiCallIds == null ? List.of() : List.copyOf(aiCallIds);
        flags = flags == null ? List.of() : List.copyOf(flags);
    }
}
