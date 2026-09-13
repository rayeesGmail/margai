package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.internal.PromptRegistry;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D14 extraction task on the fake client: the page decodes into {@link NcertPage}, the call is
 * ledgered as a {@code vision} {@code pipeline_extract} row (CLAUDE.md's "every model call logs an
 * ai_calls row"), and the state handed to the next page carries the address it must continue from,
 * not only the text.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class PageExtractTaskTest {

    @Autowired
    private PageExtractTask task;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PromptRegistry prompts;

    @Test
    void readsAPageAndLedgersTheCallOnTheVisionTier() {
        UUID requestId = UUID.randomUUID();

        AiResponse<NcertPage> response = task.read("Physics Part-I, Textbook for Class XI",
                (short) 7, 12, List.of(image()), "the page's own text layer", PreviousPage.none(), new AiCallContext(null, requestId.toString(), false));

        NcertPage page = response.output();
        assertThat(page.confidence()).isEqualByComparingTo(new BigDecimal("0.96"));
        assertThat(page.paragraphs()).hasSize(2);
        assertThat(page.paragraphs().getFirst().section()).isEqualTo("7.9");
        assertThat(page.paragraphs().getFirst().paraNo()).isEqualTo(1);
        assertThat(page.paragraphs().getLast().figureRefs()).containsExactly("Fig. 7.9");

        Map<String, Object> row = jdbc.queryForMap(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?",
                requestId.toString());
        assertThat(row.get("feature")).isEqualTo("pipeline_extract");
        assertThat(row.get("tier")).isEqualTo("vision");
        assertThat(row.get("prompt_name")).isEqualTo("ncert_extract");
        assertThat(row.get("status")).isEqualTo("ok");
    }

    /**
     * The defect the spec-auditor found: with only the tail, a model cannot know the running
     * paragraph number, so the state handed forward has to carry the address as well.
     */
    @Test
    void theStateHandedForwardCarriesTheAddressNotOnlyTheText() {
        NcertPage page = task.read("Physics Part-I, Textbook for Class XI", (short) 7, 12, List.of(image()),
                "the page's own text layer", PreviousPage.none(),
                new AiCallContext(null, UUID.randomUUID().toString(), false)).output();

        PreviousPage previous = PreviousPage.of(page, PreviousPage.none());

        assertThat(previous.section()).isEqualTo("7.9");
        assertThat(previous.paraNo()).isEqualTo(2);
        assertThat(previous.tail()).isEqualTo(page.paragraphs().getLast().text());
    }

    /**
     * A text-free page keeps the address and drops the tail: a chapter plate or a full-page figure
     * mid-section must not send the next page back to paragraph 1 (spec-auditor, D14).
     */
    @Test
    void aPageWithNoParagraphsCarriesTheAddressAcrossWithoutATail() {
        NcertPage empty = new NcertPage(List.of(), BigDecimal.ONE);
        PreviousPage before = new PreviousPage("7.9", 4, "the text of the page before");

        PreviousPage after = PreviousPage.of(empty, before);

        assertThat(after.section()).isEqualTo("7.9");
        assertThat(after.paraNo()).isEqualTo(4);
        assertThat(after.tail()).isNull();
        assertThat(empty.tail()).isNull();
    }

    @Test
    void aTextFreeFirstPageOfAChapterStillHandsNothingForward() {
        assertThat(PreviousPage.of(new NcertPage(List.of(), BigDecimal.ONE), PreviousPage.none())).isNull();
    }

    @Test
    void aLongTailIsCutToItsEnd() {
        String paragraph = "x".repeat(NcertPage.TAIL_LENGTH + 200) + "the end.";
        NcertPage page = new NcertPage(
                List.of(new NcertPage.Paragraph("7.9", 1, paragraph, List.of())), BigDecimal.ONE);

        assertThat(page.tail()).hasSize(NcertPage.TAIL_LENGTH).endsWith("the end.");
    }

    /** {@code <} opens a StringTemplate expression, so the reversible-reaction arrow is escaped. */
    @Test
    void theRenderedPromptCarriesTheLiteralArrows() {
        String system = prompts.systemPrefix("ncert_extract");

        assertThat(system).contains("a reversible reaction as \"<->\"").doesNotContain("\\<");
    }

    /** The prompt must tell the model to continue numbering, not restart it (the D14 blocker). */
    @Test
    void theSystemPrefixForbidsRestartingTheNumberingOnEveryPage() {
        String system = prompts.systemPrefix("ncert_extract");

        assertThat(system).contains("Numbering runs across pages, not within them");
    }

    /**
     * FIX 1 is paid for in input tokens on every page of every book, so the thing it buys has to be
     * demonstrably in the turn. Both branches are rendered here: given a layer the turn carries it
     * under its delimiters and says it is authoritative for characters; given none, the turn says
     * the image is the only source instead of silently dropping the section (spec-auditor, D14).
     */
    @Test
    void theUserTurnCarriesThePageTextLayerWhenThereIsOneAndSaysSoWhenThereIsNot() {
        Map<String, Object> common = Map.of("book_title", "Physics Part-I", "chapter", 7, "page", 12);
        Map<String, Object> withLayer = new java.util.LinkedHashMap<>(common);
        withLayer.put("page_text", "Lp = mp rp vp, since inspection tells us");

        String fed = prompts.render(com.margai.ai.api.PromptRef.named("ncert_extract"), withLayer).user();
        String withheld = prompts.render(com.margai.ai.api.PromptRef.named("ncert_extract"), common).user();

        assertThat(fed).contains("Lp = mp rp vp, since inspection tells us")
                .contains("--- text layer of this page ---")
                .contains("authoritative for characters");
        assertThat(withheld).contains("no usable text layer")
                .doesNotContain("--- text layer of this page ---");
    }

    /**
     * The prompt may not ask for a field the tool schema forbids. `has_equations` was removed from
     * the output record at D14 and left in four places in the prefix, where the tool's
     * additionalProperties:false would have turned every page into a validation failure and a
     * repair retry — billed twice, on a ₹430 run (spec-auditor, D14). A green build did not catch
     * it because nothing compared the two; this does.
     */
    @Test
    void theSystemPrefixAsksForNoFieldTheSchemaForbids() {
        String system = prompts.systemPrefix("ncert_extract");
        Set<String> allowed = Arrays.stream(NcertPage.Paragraph.class.getRecordComponents())
                .map(component -> SNAKE.matcher(component.getName()).replaceAll("_$0").toLowerCase(Locale.ROOT))
                .collect(java.util.stream.Collectors.toSet());

        assertThat(allowed).contains("section", "para_no", "figure_refs").doesNotContain("has_equations");
        assertThat(system).as("a field named in the prompt but absent from the schema")
                .doesNotContain("has_equations");
    }

    /** camelCase to snake_case, the naming the tool schema is generated with. */
    private static final Pattern SNAKE = Pattern.compile("(?<=[a-z0-9])[A-Z]");

    private static ImagePart image() {
        return new ImagePart("not a real png, the fake never looks".getBytes(StandardCharsets.UTF_8), "image/png");
    }
}
