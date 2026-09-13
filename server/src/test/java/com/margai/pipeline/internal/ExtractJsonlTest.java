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
                List.of(new NcertPage.Paragraph("7.2", 4, "the text of the paragraph", List.of("Fig. 7.9"))), null);

        List<ExtractedPage> read = ExtractJsonl.read(KEY, ExtractJsonl.write(List.of(page)));

        assertThat(read).containsExactly(page);
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
                .hasMessageContaining("Delete " + KEY)
                .hasMessageContaining("has_equations");
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
