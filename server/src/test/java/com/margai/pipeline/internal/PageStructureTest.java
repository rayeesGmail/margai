package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * The independent flag. It knows nothing about characters, so it still has something to say when
 * the text layer is subtly wrong and the character diff agrees with the model about it.
 */
class PageStructureTest {

    private static String blocks(int count) {
        StringBuilder page = new StringBuilder();
        for (int block = 0; block < count; block++) {
            page.append("This is a block of running prose on the page, long enough to count as a "
                    + "paragraph rather than a heading or a folio, number ").append(block).append(".\n\n");
        }
        return page.toString();
    }

    @Test
    void aPlausibleCountSaysNothing() {
        assertThat(PageStructure.check(blocks(8), 8)).isEmpty();
        assertThat(PageStructure.check(blocks(8), 6)).isEmpty();
        assertThat(PageStructure.check(blocks(8), 12)).isEmpty();
    }

    @Test
    void aPageCutTooCoarselyIsFlagged() {
        assertThat(PageStructure.check(blocks(12), 3))
                .hasValueSatisfying(reason -> assertThat(reason).contains("cut too coarsely"));
    }

    @Test
    void aPageCutTooFinelyIsFlagged() {
        assertThat(PageStructure.check(blocks(8), 30))
                .hasValueSatisfying(reason -> assertThat(reason).contains("cut too finely"));
    }

    @Test
    void proseReturningNothingIsFlagged() {
        assertThat(PageStructure.check(blocks(6), 0))
                .hasValueSatisfying(reason -> assertThat(reason).contains("no paragraphs returned"));
    }

    /** A figure page, a plate or a page of formulae has too little structure to judge. */
    @Test
    void aPageWithLittleProseIsNotJudged() {
        assertThat(PageStructure.check(blocks(2), 0)).isEmpty();
        assertThat(PageStructure.check("Fig. 7.9\n\n139\n\nGRAVITATION", 0)).isEmpty();
        assertThat(PageStructure.check(null, 5)).isEmpty();
        assertThat(PageStructure.check("   ", 5)).isEmpty();
    }
}
