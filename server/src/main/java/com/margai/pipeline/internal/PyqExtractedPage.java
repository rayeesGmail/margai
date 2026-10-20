package com.margai.pipeline.internal;

import com.margai.ai.tasks.PyqPage;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * One line of {@code extract/pyq/{paper}.jsonl} (D19): one booklet page as the model read it, with
 * the ledger row, the model and the prompt version that read it, so every question the assembly
 * builds can name the calls it came from and a resumed run never pays for a page twice.
 *
 * @param page      the 1-based page of the transcribed booklet
 * @param aiCallId  the page call's {@code ai_calls} row; null only for a fake-client run
 * @param model     the model the response names
 * @param prompt    the active prompt, {@code pyq_extract.v1}
 */
record PyqExtractedPage(
        int page,
        BigDecimal confidence,
        UUID aiCallId,
        String model,
        String prompt,
        List<PyqPage.Heading> headings,
        List<PyqPage.Item> questions) {

    PyqExtractedPage {
        headings = headings == null ? List.of() : List.copyOf(headings);
        questions = questions == null ? List.of() : List.copyOf(questions);
    }

    /** The number of the question this page ends inside, its options incomplete, or null. */
    Integer endsInside() {
        if (questions.isEmpty()) {
            return null;
        }
        PyqPage.Item last = questions.getLast();
        return last.options().size() < PyqAssembly.OPTIONS ? last.number() : null;
    }
}
