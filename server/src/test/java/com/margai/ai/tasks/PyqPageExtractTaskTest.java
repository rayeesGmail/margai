package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.internal.PromptRegistry;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
 * The D19 booklet-page task on the fake client: the page decodes into {@link PyqPage}, the call is
 * ledgered as a {@code vision} {@code pipeline_extract} row under the {@code pyq_extract} prompt, the
 * prompt carries the founder's rulings — English only, watermarks, stamps and handwriting never
 * content, never an answer — and the call is told the question the previous page ended inside.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class PyqPageExtractTaskTest {

    @Autowired
    private PyqPageExtractTask task;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PromptRegistry prompts;

    @Test
    void readsAPageAndLedgersTheCallOnTheVisionTier() {
        UUID requestId = UUID.randomUUID();

        AiResponse<PyqPage> response = task.read("NEET (UG) 2025, booklet code 45", 3, List.of(image()), true, null,
                null, new AiCallContext(null, requestId.toString(), false));

        PyqPage page = response.output();
        assertThat(page.confidence()).isEqualByComparingTo(new BigDecimal("0.96"));
        assertThat(page.questions()).extracting(PyqPage.Item::number).containsExactly(1, 2, 3);
        assertThat(page.questions().get(2).figures()).extracting(PyqPage.Figure::at).containsExactly("stem");
        assertThat(page.headings()).containsExactly(new PyqPage.Heading("PHYSICS", 1));
        assertThat(task.promptVersion()).isEqualTo("pyq_extract.v1");

        Map<String, Object> row = jdbc.queryForMap(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?", requestId.toString());
        assertThat(row.get("feature")).isEqualTo("pipeline_extract");
        assertThat(row.get("tier")).isEqualTo("vision");
        assertThat(row.get("prompt_name")).isEqualTo("pyq_extract");
        assertThat(row.get("status")).isEqualTo("ok");
    }

    @Test
    void thePrefixCarriesTheFoundersRulings() {
        String system = prompts.systemPrefix("pyq_extract");

        assertThat(system).contains("Transcribe the English version only")
                .contains("\"aglasem.com\"").contains("\"admission.aglasem.com\"").contains("\"prepp\"")
                .contains("a booklet serial number stamped")
                .contains("handwriting of any kind")
                .contains("An option someone has marked by hand is NOT the answer")
                .contains("handwriting_touches")
                .contains("Markdown table")
                .doesNotContain("\\<");
    }

    @Test
    void theCallIsToldTheQuestionThePreviousPageEndedInside() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("paper", "NEET (UG) 2025, booklet code 45");
        variables.put("page", 4);
        variables.put("bilingual", true);
        variables.put("in_question", 17);

        String user = prompts.render(PromptRef.named("pyq_extract"), variables).user();

        assertThat(user).contains("ended inside question 17").contains("continues_previous")
                .contains("Transcribe the English").contains("no usable text layer");

        variables.put("in_question", null);
        assertThat(prompts.render(PromptRef.named("pyq_extract"), variables).user())
                .contains("ended on a complete question");
    }

    @Test
    void aQuestionWithoutAStemOrWithALetteredOptionIsRefusedWhereItIsDecoded() {
        assertThatThrownBy(() -> new PyqPage.Item(5, false, " ", List.of(), List.of(), false))
                .hasMessageContaining("question 5 has no stem");
        assertThatThrownBy(() -> new PyqPage.Option("a", "text"))
                .hasMessageContaining("the booklet prints (1) to (4)");
        assertThatThrownBy(() -> new PyqPage(List.of(), List.of(
                new PyqPage.Item(1, false, "one", List.of(), List.of(), false),
                new PyqPage.Item(1, true, "", List.of(), List.of(), false)), BigDecimal.ONE))
                .hasMessageContaining("only a page's first item can continue the previous page");
    }

    private static ImagePart image() {
        return new ImagePart("not a real png, the fake never looks".getBytes(StandardCharsets.UTF_8), "image/png");
    }
}
