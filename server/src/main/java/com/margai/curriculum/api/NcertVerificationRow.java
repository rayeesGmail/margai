package com.margai.curriculum.api;

import java.util.Objects;

/**
 * One verdict as {@code ncert verify --read-pages} hands it over: the paragraph address and what
 * the second read found there (DECISIONS 2026-09-14).
 */
public record NcertVerificationRow(short chapterNo, String section, short paraNo, ParagraphVerification verification) {

    public NcertVerificationRow {
        Objects.requireNonNull(section, "section");
        Objects.requireNonNull(verification, "verification");
    }

    /** The address as {@link NcertParagraphRow#address()} spells it. */
    public String address() {
        return "ch " + chapterNo + " §" + section + " ¶" + paraNo;
    }
}
