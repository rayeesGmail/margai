package com.margai.pipeline.internal;

import com.margai.curriculum.api.ParagraphVerification;
import java.util.List;
import java.util.UUID;

/**
 * One line of {@code verify/{book}/{lang}.jsonl} (D15): what the second read of one page said, as the
 * model said it. The pipeline's own judgements — a span that differs only in spacing, a span the row
 * does not carry, a flag the founder has ruled on — are applied when a run reports, never stored here,
 * so a ruling added after a read takes effect on the next run without paying for the page again.
 *
 * <p>Each item names the paragraph address and the hash of the part it read. A page is read again only
 * when those no longer match the loaded rows — a correction changed a word, a reload moved a boundary —
 * or the prompt version changed, or the founder asks with {@code --redo}.
 *
 * @param aiCallId the ledger row of the page call, null only on a fake-client run
 * @param model    the model the call reports it ran on
 * @param omitted  running text the page prints that no item carried, as the model quoted it
 */
record VerifiedPage(
        short chapterNo,
        int page,
        UUID aiCallId,
        String model,
        String promptVersion,
        List<Item> items,
        List<String> omitted) {

    VerifiedPage {
        items = items == null ? List.of() : List.copyOf(items);
        omitted = omitted == null ? List.of() : List.copyOf(omitted);
    }

    /** The page's key in a run's map, as {@link ExtractedPage#address()} spells it. */
    String address() {
        return chapterNo + "/" + page;
    }

    /**
     * @param number     the item's number in the call
     * @param address    the paragraph the item was a part of
     * @param partSha256 the hash of the text the item carried
     * @param verdict    the model's verdict, or {@code not_judged} when it returned none for this item
     */
    record Item(int number, String address, String partSha256, ParagraphVerification.Verdict verdict,
            List<Span> differences) {

        Item {
            differences = differences == null ? List.of() : List.copyOf(differences);
        }
    }

    record Span(String printed, String transcribed) {
    }
}
