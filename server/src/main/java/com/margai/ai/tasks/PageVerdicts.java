package com.margai.ai.tasks;

import java.util.List;

/**
 * Output record of the {@code ncert_verify} prompt (D15, DECISIONS 2026-09-14 "the pair"): a second,
 * independent model's reading of one page against the paragraphs transcribed from it — one verdict per
 * numbered item, and the running text the page prints that no item carries.
 *
 * <p>It answers one question per item and judges nothing code can compute: spacing, glyph variants and
 * figure references are the pipeline's to check (.claude/rules/ai-layer.md).
 *
 * <p>The first field is {@code verdicts}, not {@code items}: named after the JSON-Schema keyword, it
 * made Sonnet 5 read the tool as taking one {@code items} parameter and send its whole answer as a
 * string inside it, on every page of the first calibration run (2026-09-15, D15).
 *
 * @param verdicts one verdict per item of the call, by the item's number
 * @param omitted  the opening words of each passage of running text on the page that no item carries
 */
public record PageVerdicts(List<ItemVerdict> verdicts, List<String> omitted) {

    public PageVerdicts {
        verdicts = verdicts == null ? List.of() : List.copyOf(verdicts);
        omitted = omitted == null ? List.of() : List.copyOf(omitted);
    }

    /** Lowercase codes, as the schema carries them. */
    public enum Verdict {
        /** Every word and symbol of the item is what the page prints. */
        matches,
        /** At least one span is not what the page prints; each is named. */
        differs,
        /** The item's text is not printed on this page. */
        not_on_page
    }

    /**
     * One item's verdict. The shape is refused where the output is decoded — a {@code differs} that
     * names nothing, or another verdict that names something — so the schema layer's repair call
     * answers it, as for a blank paragraph in {@link NcertPage}. The refusals state the fault and give
     * no instruction: how to name a difference is the prompt's to say (.claude/rules/ai-layer.md).
     */
    public record ItemVerdict(int item, Verdict verdict, List<Difference> differences) {

        public ItemVerdict {
            differences = differences == null ? List.of() : List.copyOf(differences);
            if (verdict == Verdict.differs && differences.isEmpty()) {
                throw new IllegalArgumentException("item " + item + " is 'differs' but names no difference");
            }
            if (verdict != Verdict.differs && !differences.isEmpty()) {
                throw new IllegalArgumentException("item " + item + " is '" + verdict
                        + "' and must name no difference");
            }
        }
    }

    /**
     * One place the item is not what the page prints.
     *
     * @param printed     the span as the page prints it, written in the transcription's notation
     * @param transcribed the same span copied exactly from the item, so the pipeline can find it there
     */
    public record Difference(String printed, String transcribed) {

        public Difference {
            if (printed == null || printed.isBlank() || transcribed == null || transcribed.isBlank()) {
                throw new IllegalArgumentException("both spans must quote text");
            }
            if (printed.equals(transcribed)) {
                throw new IllegalArgumentException("the printed and transcribed spans are identical ('" + printed
                        + "'), so they name no difference");
            }
        }
    }
}
