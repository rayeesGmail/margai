package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.tasks.NcertPage;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * The artefact is the only thing that survives a run, and it outlives the code that wrote it. So
 * the round trip is pinned, and so is the refusal when a line was written by a version whose page
 * shape no longer exists — the case that is real right now, because `has_equations` left the
 * paragraph record at D14 while lines carrying it sit in the content bucket.
 */
class ExtractJsonlTest {

    private static final String KEY = "extract/phy11-part1/en.jsonl";

    @Test
    void aPageSurvivesTheRoundTrip() {
        ExtractedPage page = new ExtractedPage((short) 7, 3, new BigDecimal("0.95"), UUID.randomUUID(),
                List.of(new NcertPage.Paragraph("7.2", "the text of the paragraph", true, List.of("Fig. 7.9"))), null);

        List<ExtractedPage> read = ExtractJsonl.read(KEY, ExtractJsonl.write(List.of(page)));

        assertThat(read).containsExactly(page);
        assertThat(new String(ExtractJsonl.write(List.of(page)), StandardCharsets.UTF_8))
                .contains("\"continues_previous_page\":true").doesNotContain("para_no");
    }

    /**
     * A v2 line, as both pilot books' archived artefacts hold it: `para_no` on the paragraph. v3
     * has no such field, and an extraction is canonical per prompt version, so a v3 run refuses to
     * extend a v2 file — the founder moves it aside and extracts again (D15).
     */
    @Test
    void aV2LineWithAParagraphNumberIsRefusedByNameWithItsRemedy() {
        byte[] v2 = ("""
                {"chapter_no":7,"page":3,"confidence":0.95,"ai_call_id":null,"skipped":null,\
                "paragraphs":[{"section":"7.2","para_no":4,"text":"t","figure_refs":[]}]}
                """).getBytes(StandardCharsets.UTF_8);
        assertThatThrownBy(() -> ExtractJsonl.read(KEY, v2))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("en.jsonl:1")
                .hasMessageContaining("canonical per prompt version")
                .hasMessageContaining("para_no");
    }

    @Test
    void aSkippedPageSurvivesToo() {
        ExtractedPage skipped = ExtractedPage.skipped((short) 7, 14, "apparatus from SUMMARY");

        assertThat(ExtractJsonl.read(KEY, ExtractJsonl.write(List.of(skipped)))).containsExactly(skipped);
    }

    /**
     * A v1 line, as the bucket holds it today: `has_equations` on the paragraph. It must fail with
     * the remedy in the message and not with a Jackson stack trace, because the command that hits
     * it first is `ncert extract` — reading the file it resumes from, before any model call.
     */
    @Test
    void aLineFromAnEarlierPageShapeIsRefusedByNameWithItsRemedy() {
        byte[] v1 = ("""
                {"chapter_no":7,"page":3,"confidence":0.95,"ai_call_id":null,"skipped":null,\
                "paragraphs":[{"section":"7.2","para_no":4,"text":"t","has_equations":true,"figure_refs":[]}]}
                """).getBytes(StandardCharsets.UTF_8);

        assertThatThrownBy(() -> ExtractJsonl.read(KEY, v1))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("en.jsonl:1")
                .hasMessageContaining("canonical per prompt version")
                .hasMessageContaining("Move " + KEY + " aside")
                .hasMessageContaining("has_equations");
    }

    /**
     * A v3 line the page itself rejects — a blank paragraph, which this build's own output can no
     * longer contain but an older or hand-edited artefact can — is a defect of one page, not of the
     * file's version: it is refused with the page and the redo that fixes it (spec-auditor, D15).
     */
    @Test
    void aV3LineWithABlankParagraphIsRefusedWithThePagesOwnRemedy() {
        byte[] blank = ("""
                {"chapter_no":4,"page":2,"confidence":0.95,"ai_call_id":null,"skipped":null,\
                "paragraphs":[{"section":"4.1","text":"  ","continues_previous_page":true,"figure_refs":[]}]}
                """).getBytes(StandardCharsets.UTF_8);
        assertThatThrownBy(() -> ExtractJsonl.read(KEY, blank))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("en.jsonl:1")
                .hasMessageContaining("ch 4 page 2")
                .hasMessageContaining("no text")
                .hasMessageContaining("ncert extract --redo --chapters 4 --pages 2")
                .hasMessageNotContaining("produced by an earlier one");
    }

    /** The same for a flag on any paragraph but the page's first, which the model's output cannot carry either. */
    @Test
    void aFlagOnANonFirstParagraphIsRefusedWithThePagesOwnRemedy() {
        byte[] flagged = ("""
                {"chapter_no":6,"page":8,"confidence":0.95,"ai_call_id":null,"skipped":null,\
                "paragraphs":[{"section":"6.2","text":"first","continues_previous_page":false,"figure_refs":[]},\
                {"section":"6.3","text":"second","continues_previous_page":true,"figure_refs":[]}]}
                """).getBytes(StandardCharsets.UTF_8);
        assertThatThrownBy(() -> ExtractJsonl.read(KEY, flagged))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("ch 6 page 8")
                .hasMessageContaining("only a page's first paragraph")
                .hasMessageContaining("ncert extract --redo --chapters 6 --pages 8");
    }

    /** A half-written line — an interrupted flush — is the same class of problem and the same answer. */
    @Test
    void aTruncatedLineIsRefusedByNameToo() {
        byte[] truncated = "{\"chapter_no\":7,\"page\":3,\"para".getBytes(StandardCharsets.UTF_8);

        assertThatThrownBy(() -> ExtractJsonl.read(KEY, truncated))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining("en.jsonl:1");
    }

    @Test
    void blankLinesAreNotPages() {
        assertThat(ExtractJsonl.read(KEY, "\n\n".getBytes(StandardCharsets.UTF_8))).isEmpty();
    }
}
