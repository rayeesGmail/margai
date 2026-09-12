package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D14 extraction task on the fake client: the page decodes into {@link NcertPage}, the call
 * is ledgered as a {@code vision} {@code pipeline_extract} row (CLAUDE.md's "every model call
 * logs an ai_calls row"), and the tail handed to the next page is the last paragraph's ending.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class NcertExtractTaskTest {

    @Autowired
    private NcertExtractTask task;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private com.margai.ai.internal.PromptRegistry prompts;

    @Test
    void readsAPageAndLedgersTheCallOnTheVisionTier() {
        UUID requestId = UUID.randomUUID();

        AiResponse<NcertPage> response = task.read("Physics Part-I, Textbook for Class XI",
                (short) 7, 12, image(), null, new AiCallContext(null, requestId.toString(), false));

        NcertPage page = response.output();
        assertThat(page.confidence()).isEqualByComparingTo(new BigDecimal("0.96"));
        assertThat(page.paragraphs()).hasSize(2);
        assertThat(page.paragraphs().getFirst().section()).isEqualTo("7.9");
        assertThat(page.paragraphs().getFirst().paraNo()).isEqualTo(1);
        assertThat(page.paragraphs().getFirst().hasEquations()).isFalse();
        assertThat(page.paragraphs().getLast().hasEquations()).isTrue();
        assertThat(page.paragraphs().getLast().figureRefs()).containsExactly("Fig. 7.9");

        Map<String, Object> row = jdbc.queryForMap(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?",
                requestId.toString());
        assertThat(row.get("feature")).isEqualTo("pipeline_extract");
        assertThat(row.get("tier")).isEqualTo("vision");
        assertThat(row.get("prompt_name")).isEqualTo("ncert_extract");
        assertThat(row.get("status")).isEqualTo("ok");
    }

    @Test
    void theTailIsTheLastParagraphsEnding() {
        NcertPage page = task.read("Physics Part-I, Textbook for Class XI", (short) 7, 12, image(), null,
                new AiCallContext(null, UUID.randomUUID().toString(), false)).output();

        assertThat(page.tail()).isEqualTo(page.paragraphs().getLast().text());
        assertThat(new NcertPage(List.of(), BigDecimal.ONE).tail()).isNull();
    }

    @Test
    void aLongTailIsCutToItsEnd() {
        String paragraph = "x".repeat(NcertPage.TAIL_LENGTH + 200) + "the end.";
        NcertPage page = new NcertPage(
                List.of(new NcertPage.Paragraph("7.9", 1, paragraph, false, List.of())), BigDecimal.ONE);

        assertThat(page.tail()).hasSize(NcertPage.TAIL_LENGTH).endsWith("the end.");
    }

    /** {@code <} opens a StringTemplate expression, so the reversible-reaction arrow is escaped. */
    @Test
    void theRenderedPromptCarriesTheLiteralArrows() {
        String system = prompts.systemPrefix("ncert_extract");

        assertThat(system).contains("a reversible reaction as \"<->\"").doesNotContain("\\<");
    }

    private static ImagePart image() {
        return new ImagePart("not a real png, the fake never looks".getBytes(StandardCharsets.UTF_8), "image/png");
    }
}
