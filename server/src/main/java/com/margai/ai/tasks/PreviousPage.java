package com.margai.ai.tasks;

import java.util.regex.Pattern;

/**
 * Where the previous page of this chapter ended, handed to the next page's call (TECH_PLAN §6.3).
 *
 * <p>Through v2 this carried the last paragraph's number, because the model was counting, and
 * the end of its text, so a continuation could be recognised. Since v3 the loader counts
 * (DECISIONS 2026-09-14), and since v3's first measurement the same afternoon no text travels
 * either: the cheap model echoed the quoted ending on two of twelve pages, once verbatim and once
 * as a paraphrase no repair can see. The third run then showed what the text had been doing
 * besides tempting an echo — telling the model that a sentence was open, and so where to look:
 * without it, on a two-column page whose left column opens with displayed equations, the model
 * took the right column's mid-sentence top for the continuation and lost the left column. So
 * the call now carries one <em>fact</em> about the previous page and no words: its section, and
 * whether its last paragraph stopped without finishing a sentence.
 *
 * @param section          the section the previous page ended in, as printed ("7.9")
 * @param endedMidSentence true when the previous page's last paragraph ended without terminal
 *                         punctuation — a sentence is open and this page's first lines finish it
 */
public record PreviousPage(String section, boolean endedMidSentence) {

    /**
     * How a finished paragraph ends. A closing bracket counts: "…(7.10)" is a displayed equation
     * number, and the paragraph that follows it is the model's call (the same signature as the
     * loader's split check).
     */
    private static final Pattern FINISHED = Pattern.compile("[.?!:)\\]]\\s*$");

    /** The state at the top of a chapter: no page has been read yet. */
    public static PreviousPage none() {
        return null;
    }

    /**
     * What {@code page} leaves behind for the next call, given what the page before it left.
     *
     * <p>A page with no paragraphs — a chapter plate, a full-page figure, a blank verso, a page of
     * pure table — does not reset the state: it carries the running section and the open-sentence
     * fact across untouched, since the paragraph the next page may continue is still the one
     * before the plate. Figure pages are common mid-chapter in Biology (spec-auditor, D14).
     */
    public static PreviousPage of(NcertPage page, PreviousPage before) {
        if (page.paragraphs().isEmpty()) {
            return before;
        }
        NcertPage.Paragraph last = page.paragraphs().getLast();
        return new PreviousPage(last.section(), !FINISHED.matcher(last.text().strip()).find());
    }
}
