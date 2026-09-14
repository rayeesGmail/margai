package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiCallModels;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.PromptRef;
import com.margai.ai.internal.PromptRegistry;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
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
 * The second read on the fake client (D15, DECISIONS 2026-09-14 "the pair"): one page's bands and its
 * transcription in, one verdict per item out, ledgered as a {@code vision} {@code pipeline_verify} row.
 * The prompt carries the extraction prompt's notation conventions word for word, so a notation the
 * transcriber was told to write is never a difference to the verifier.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class PageVerifyTaskTest {

    @Autowired
    private PageVerifyTask task;

    @Autowired
    private PromptRegistry prompts;

    @Autowired
    private AiCallModels models;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void verifiesAPageAndLedgersTheCallOnTheVisionTier() {
        String requestId = UUID.randomUUID().toString();

        AiResponse<PageVerdicts> response = task.verify("Physics Part-I, Textbook for Class XI", (short) 7, 4,
                List.of(image(), image()), items(), new AiCallContext(null, requestId, false));

        assertThat(response.output().items()).extracting(PageVerdicts.ItemVerdict::item).containsExactly(1, 2);
        assertThat(response.output().items().get(1).verdict()).isEqualTo(PageVerdicts.Verdict.differs);
        Map<String, Object> row = jdbc.queryForMap(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?", requestId);
        assertThat(row.get("feature")).isEqualTo("pipeline_verify");
        assertThat(row.get("tier")).isEqualTo("vision");
        assertThat(row.get("prompt_name")).isEqualTo("ncert_verify");
        assertThat(row.get("status")).isEqualTo("ok");
    }

    /** The independence guard reads which model made a call from the ledger, not from a caller's word. */
    @Test
    void theLedgerSaysWhichModelMadeACall() {
        AiResponse<PageVerdicts> response = task.verify("Physics Part-I", (short) 7, 4, List.of(image()), items(),
                new AiCallContext(null, UUID.randomUUID().toString(), false));

        assertThat(models.modelsOf(List.of(response.aiCallId(), UUID.randomUUID())))
                .containsExactly(Map.entry(response.aiCallId(), task.model()));
        assertThat(task.model()).isEqualTo(response.modelId());
        assertThat(task.promptVersion()).isEqualTo("ncert_verify.v1");
    }

    @Test
    void theUserTurnNumbersEveryItemAndMarksThePartsOfStraddlingParagraphs() {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("book_title", "Physics Part-I");
        variables.put("chapter", 7);
        variables.put("page", 4);
        variables.put("tiles", 2);
        variables.put("items", PageVerifyTask.itemVariables(List.of(
                new VerifyItem(1, "This clearly shows that the force due to", true, false),
                new VerifyItem(2, "F = G m_1m_2 / r^2 (7.5), where h << R_E", false, true))));

        String user = prompts.render(PromptRef.named("ncert_verify"), variables).user();

        assertThat(user).contains("page 4 of Chapter 7 of Physics Part-I")
                .contains("2 images")
                .contains("[1] (its beginning is printed on the previous page)\nThis clearly shows that the force due to")
                .contains("[2] (it runs on to the next page)\nF = G m_1m_2 / r^2 (7.5), where h << R_E")
                .contains("2 items");
    }

    /**
     * One convention in two places drifts the moment one of them changes (TranscriptionDiff, D14). The
     * verifier must know the transcriber's notation exactly, or every "theta" and "i_hat" is a flag.
     */
    @Test
    void theSystemPrefixCarriesTheExtractionPromptsNotationWordForWord() {
        String extract = prompts.systemPrefix("ncert_extract");
        int from = extract.indexOf("Transcribe mathematics inline in plain text");
        int to = extract.indexOf("FIGURES AND TABLES");
        String notation = extract.substring(from, to).strip();

        assertThat(notation).contains("i_hat").contains("_bar").contains("Biology adds");
        assertThat(prompts.systemPrefix("ncert_verify")).contains(notation);
    }

    /** The kinds of difference that matter — the hat, the grouping bracket, the misprint kept — taught in the cached prefix. */
    @Test
    void theSystemPrefixTeachesTheDifferencesThatMatter() {
        String system = prompts.systemPrefix("ncert_verify");

        assertThat(system).contains("r_hat").contains("unit vector")
                .contains("u + at / 2").contains("How products and quotients are grouped")
                .contains("resistence")
                .contains("not_on_page").contains("omitted")
                .contains("≅, ≃ and ≈")
                .doesNotContain("text layer of this page");
    }

    /**
     * Chapter 7 is the verifier's calibration set: a prompt that carried its answers would make a pass
     * prove nothing (spec-auditor, D15). The must-find and the defects the dry runs read on its pages stay
     * out of everything but the notation block copied from the frozen extraction prompt.
     */
    @Test
    void theSystemPrefixCarriesNoneOfTheCalibrationChaptersAnswers() {
        String system = prompts.systemPrefix("ncert_verify");

        assertThat(system).doesNotContain("|r|^3").doesNotContain("Mm / d^2").doesNotContain("4p/3")
                .doesNotContain("Cavendish").doesNotContain("neighouring").doesNotContain("(V_i)")
                .doesNotContain("THE GRAVITATIONAL CONSTANT").doesNotContain("superposition")
                .doesNotContain("R_E + h").doesNotContain("torsion");
    }

    private static List<VerifyItem> items() {
        return List.of(new VerifyItem(1, "Early in our lives, we become aware.", false, false),
                new VerifyItem(2, "F = - G m_1m_2 / |r|^3 r_hat", false, false));
    }

    private static ImagePart image() {
        return new ImagePart("not a real png, the fake never looks".getBytes(StandardCharsets.UTF_8), "image/png");
    }
}
