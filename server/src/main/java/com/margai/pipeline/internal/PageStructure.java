package com.margai.pipeline.internal;

import java.util.Optional;

/**
 * A soft check that a page's paragraph count is in the same country as its text layer's shape.
 *
 * <p>It exists to be <em>independent</em> of the other two checks. The character diff compares the
 * model against the text layer it was given, and the model was told to trust that layer — so where
 * the layer is subtly wrong, both agree and neither notices. This looks at something else
 * entirely: how many blocks of text the page has, against how many paragraphs came back. It cannot
 * see a wrong character and does not try to; it sees a page cut into three when it holds twelve,
 * or into thirty when it holds eight (FIX 6, D14).
 *
 * <p>Deliberately generous. The model legitimately merges a list into its introduction, joins a
 * paragraph across a page break, and drops captions, headers and boxed questions — so only a
 * divergence far past any of that is worth a founder's attention. A flag here is a prompt to look,
 * never a refusal: the loader's invariants are the safety net, and confidence and this are both
 * routing signals (DECISIONS 2026-09-13).
 */
final class PageStructure {

    /** Blocks separated by a blank line, the text layer's own paragraph-ish unit. */
    private static final String BLOCK_SEPARATOR = "\\R\\s*\\R";

    /** Shorter than this and a block is a heading, a folio or a stray line, not a paragraph. */
    private static final int MIN_BLOCK_CHARACTERS = 120;

    /** Below this share of the page's blocks, the page has been cut too coarsely to be right. */
    private static final double TOO_FEW = 0.34;

    /** Above this multiple, it has been cut too finely — a displayed equation per paragraph. */
    private static final double TOO_MANY = 2.5;

    private PageStructure() {
    }

    /**
     * Why this page's paragraph count looks wrong, or empty when it looks reasonable.
     *
     * @param pageText   the page's text layer
     * @param paragraphs how many paragraphs the model returned for it
     */
    static Optional<String> check(String pageText, int paragraphs) {
        if (pageText == null || pageText.isBlank()) {
            return Optional.empty();
        }
        long blocks = java.util.Arrays.stream(pageText.split(BLOCK_SEPARATOR))
                .map(String::strip)
                .filter(block -> block.length() >= MIN_BLOCK_CHARACTERS)
                .count();
        if (blocks < 3) {
            // Too little structure to judge: a figure page, a plate, a page of formulae.
            return Optional.empty();
        }
        if (paragraphs == 0) {
            return Optional.of("no paragraphs returned, but the text layer has " + blocks + " blocks of prose");
        }
        if (paragraphs < blocks * TOO_FEW) {
            return Optional.of(paragraphs + " paragraphs against " + blocks
                    + " blocks of prose on the page — cut too coarsely?");
        }
        if (paragraphs > blocks * TOO_MANY) {
            return Optional.of(paragraphs + " paragraphs against " + blocks
                    + " blocks of prose on the page — cut too finely?");
        }
        return Optional.empty();
    }
}
