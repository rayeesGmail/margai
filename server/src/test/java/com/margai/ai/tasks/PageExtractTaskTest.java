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
        assertThat(page.paragraphs().getFirst().continuesPreviousPage()).isFalse();
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
     * paragraph number, so the state handed forward carried the address through v2. Since v3 the
     * loader counts, and the state carries the section and the tail (D15).
     */
    @Test
    void theStateHandedForwardCarriesTheSectionAndTheTail() {
        NcertPage page = task.read("Physics Part-I, Textbook for Class XI", (short) 7, 12, List.of(image()),
                "the page's own text layer", PreviousPage.none(),
                new AiCallContext(null, UUID.randomUUID().toString(), false)).output();

        PreviousPage previous = PreviousPage.of(page, PreviousPage.none());

        // The fixture's last paragraph ends "(Fig. 7.9)." — a finished sentence.
        assertThat(previous).isEqualTo(new PreviousPage("7.9", false));
    }

    /**
     * A text-free page keeps the section and drops the tail: a chapter plate or a full-page figure
     * mid-section must not make the next page guess its section (spec-auditor, D14).
     */
    @Test
    void aPageWithNoParagraphsCarriesTheSectionAcrossWithoutATail() {
        NcertPage empty = new NcertPage(List.of(), BigDecimal.ONE);
        PreviousPage before = new PreviousPage("7.9", true);

        PreviousPage after = PreviousPage.of(empty, before);

        assertThat(after.section()).isEqualTo("7.9");
        assertThat(after).isNotNull();
        assertThat(after).isEqualTo(before);
        assertThat(empty.paragraphs()).isEmpty();
    }

    @Test
    void aTextFreeFirstPageOfAChapterStillHandsNothingForward() {
        assertThat(PreviousPage.of(new NcertPage(List.of(), BigDecimal.ONE), PreviousPage.none())).isNull();
    }

    @Test
    void aPageHandsForwardItsLastSectionAndWhetherItsLastSentenceIsOpen() {
        String paragraph = "the end of the page, in section 7.9.";
        NcertPage page = new NcertPage(
                List.of(new NcertPage.Paragraph("7.9", paragraph, false, List.of())), BigDecimal.ONE);

        assertThat(PreviousPage.of(page, PreviousPage.none())).isEqualTo(new PreviousPage("7.9", false));
        // Page 4 of chapter 7 ends "The individual forces in vector notation are" — the sentence
        // is open, and the next page's call is told so, without the words (D15, run 3).
        NcertPage open = new NcertPage(
                List.of(new NcertPage.Paragraph("7.3", "The individual forces in vector notation are", false, List.of())),
                BigDecimal.ONE);
        assertThat(PreviousPage.of(open, PreviousPage.none())).isEqualTo(new PreviousPage("7.3", true));
        // A displayed equation number closes a paragraph as far as this fact is concerned.
        NcertPage equation = new NcertPage(
                List.of(new NcertPage.Paragraph("7.5", "and hence F = (G m M_E / R_E^3) r (7.10)", false, List.of())),
                BigDecimal.ONE);
        assertThat(PreviousPage.of(equation, PreviousPage.none())).isEqualTo(new PreviousPage("7.5", false));
    }

    /** {@code <} opens a StringTemplate expression, so the reversible-reaction arrow is escaped. */
    @Test
    void theRenderedPromptCarriesTheLiteralArrows() {
        String system = prompts.systemPrefix("ncert_extract");

        assertThat(system).contains("a reversible reaction as \"<->\"").doesNotContain("\\<");
    }

    /**
     * v3 (D15): the model no longer numbers anything, and the prefix must say so rather than
     * leave the v2 counting rules in place beside a schema that has no number to put them in.
     * The one addressing decision left with it is the continuation flag.
     */
    @Test
    void theSystemPrefixLeavesNumberingToThePipelineAndAsksForTheFlag() {
        String system = prompts.systemPrefix("ncert_extract");

        assertThat(system).contains("continues_previous_page")
                .contains("Paragraph numbering is done for you")
                .doesNotContain("Numbering runs across pages");
    }

    /**
     * No tail travels any more (D15, the first v3 measurement): the cheap model echoed the quoted
     * ending on two of twelve pages, once verbatim and once as a paraphrase the repair cannot see.
     * The turn names the section and asks for the flag from this page's own typography.
     */
    @Test
    void theUserTurnNamesTheSectionAndQuotesNoTail() {
        Map<String, Object> variables = new java.util.LinkedHashMap<>();
        variables.put("book_title", "Physics Part-I");
        variables.put("chapter", 6);
        variables.put("page", 8);
        variables.put("previous_section", "6.2");
        variables.put("previous_ended_mid_sentence", true);
        String open = prompts.render(com.margai.ai.api.PromptRef.named("ncert_extract"), variables).user();
        assertThat(open).contains("section 6.2")
                .contains("ended in the middle of a sentence")
                .contains("top of the left")
                .contains("continues_previous_page")
                .contains("first word printed here")
                .doesNotContain("last words of the previous page")
                .doesNotContain("previous_tail").doesNotContain("previous_para_no").doesNotContain("plus one");
        variables.put("previous_ended_mid_sentence", false);
        String closed = prompts.render(com.margai.ai.api.PromptRef.named("ncert_extract"), variables).user();
        assertThat(closed).contains("ended with a finished sentence").doesNotContain("ended in the middle of a sentence");
    }

    /** The two rules the first measurement asked for, both in the cached prefix. */
    @Test
    void theSystemPrefixChecksTheHeadingsAndKeepsEachNumberedLawItsOwnParagraph() {
        String system = prompts.systemPrefix("ncert_extract");
        assertThat(system).contains("check the headings")
                .contains("Law of periods")
                .contains("top of the left")
                // The Opus full read (D15): a degree sign copied through for τ, a hat added by
                // analogy, "alpha" for ∝, paragraphs merged across an indent after an equation.
                .contains("degree sign")
                .contains("Never add a mark")
                .contains("proportional to")
                .doesNotContain("last words of the previous page");
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

        assertThat(allowed).contains("section", "continues_previous_page", "figure_refs")
                .doesNotContain("has_equations", "para_no");
        assertThat(system).as("a field named in the prompt but absent from the schema")
                .doesNotContain("has_equations").doesNotContain("para_no");
    }

    /** camelCase to snake_case, the naming the tool schema is generated with. */
    private static final Pattern SNAKE = Pattern.compile("(?<=[a-z0-9])[A-Z]");

    private static ImagePart image() {
        return new ImagePart("not a real png, the fake never looks".getBytes(StandardCharsets.UTF_8), "image/png");
    }
}
