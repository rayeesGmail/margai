package com.margai.curriculum.api;

import java.util.List;
import java.util.Objects;

/**
 * One paragraph as {@code ncert load} hands it over (TECH_PLAN §6.3), read from the JSONL that
 * {@code ncert extract} wrote. {@code (chapterNo, section, paraNo)} with the book is the upsert
 * key and the address the app displays as "Class 11 Physics, Ch 7, §7.9" (SPEC §6.3).
 *
 * <p>{@code text} is the paragraph in the edition's own language: {@code ncert load --lang}
 * decides whether it lands in {@code text_en} or {@code text_hi}, so the D16 Hindi pass is the
 * same call with the same address and no second row.
 *
 * @param section the numbered section ("7.9"), or the chapter's own number for text that
 *                precedes the first numbered section ("7")
 */
public record NcertParagraphRow(
        short chapterNo,
        String section,
        short paraNo,
        String text,
        boolean hasEquations,
        List<String> figureRefs,
        ParagraphExtraction extraction) {

    public NcertParagraphRow {
        Objects.requireNonNull(section, "section");
        Objects.requireNonNull(text, "text");
        figureRefs = figureRefs == null ? List.of() : List.copyOf(figureRefs);
    }

    /** The address as the founder reads it in a report or a spot-check row. */
    public String address() {
        return "ch " + chapterNo + " §" + section + " ¶" + paraNo;
    }
}
