package com.margai.curriculum.api;

import java.util.UUID;

/**
 * One paragraph waiting for a vector ({@code ncert embed}, TECH_PLAN §6.3, §6.4): its id, the
 * address the report names it by, and the canonical English text that is what gets embedded.
 *
 * <p>Hindi is not a second row to embed. §6.4 embeds {@code text_en} with the multilingual model
 * and leans on that model's cross-lingual space — plus the {@code tsv} match over {@code text_hi}
 * — for Hindi and Hinglish queries. Whether that holds is exactly what D15's retrieval spike
 * measures.
 */
public record ParagraphToEmbed(UUID paragraphId, short chapterNo, String section, short paraNo, String text) {

    /** The address the founder reads in a report and spot-checks against the PDF. */
    public String address() {
        return "ch " + chapterNo + " §" + section + " ¶" + paraNo;
    }
}
