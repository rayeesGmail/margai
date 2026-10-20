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
 * @param image     the page image the call read; null only on a line written before images were kept
 */
record PyqExtractedPage(
        int page,
        BigDecimal confidence,
        UUID aiCallId,
        String model,
        String prompt,
        List<PyqPage.Heading> headings,
        List<PyqPage.Item> questions,
        Image image) {

    PyqExtractedPage {
        headings = headings == null ? List.of() : List.copyOf(headings);
        questions = questions == null ? List.of() : List.copyOf(questions);
    }

    /**
     * The page image the model read (founder, 2026-10-20: the image, its sha256, dpi and renderer), kept
     * at {@code key} in the content bucket so a later check — a flag at review, D20's transcription
     * check, the crop tool — provably looks at the same picture. {@code sha256} is the whole page's PNG;
     * the call read it as {@code tiles} overlapping bands cut by {@link PageTiles} (lossless), so the
     * bands follow from it while PageTiles is unchanged. {@code rerendered} marks an image drawn again
     * after its page was read, its hash taken then (2025 code 45, read before images were kept): that
     * is a drawing of the same booklet today, not a record of the bytes the model read — DECISIONS
     * 2026-10-20 says what supports it and what was never recorded.
     */
    record Image(String key, String sha256, int dpi, String renderer, int tiles, boolean rerendered) {
    }

    PyqExtractedPage withImage(Image kept) {
        return new PyqExtractedPage(page, confidence, aiCallId, model, prompt, headings, questions, kept);
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
