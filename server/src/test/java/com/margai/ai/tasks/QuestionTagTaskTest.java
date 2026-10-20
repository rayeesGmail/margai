package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.PromptRef;
import com.margai.ai.internal.PromptRegistry;
import java.math.BigDecimal;
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
 * The D19 tagging task on the fake client: a batch of questions is one ledgered {@code reason}
 * {@code pipeline_tag} call each — the REASON tier's policy satisfied by the generation route — the
 * tag decodes into {@link PyqTag}, and the template renders the outline into the cached prefix and the
 * question with its options into the user turn: every word the model reads comes from the prompt.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class QuestionTagTaskTest {

    private static final List<QuestionTagger.OutlineLine> OUTLINE = List.of(
            new QuestionTagger.OutlineLine(0, "subject", null, "Physics"),
            new QuestionTagger.OutlineLine(1, "unit", "PHY.U05", "Rotational Motion"),
            new QuestionTagger.OutlineLine(2, "chapter", "PHY.11.ROT", "Systems of Particles and Rotational Motion"),
            new QuestionTagger.OutlineLine(3, "topic", "PHY.11.ROT.MOI", "Moment of inertia"));

    @Autowired
    private QuestionTagTask task;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PromptRegistry prompts;

    @Test
    void tagsABatchOneLedgeredReasonCallPerQuestion() {
        UUID requestId = UUID.randomUUID();

        List<AiResponse<PyqTag>> tags = task.tag(OUTLINE, List.of(question(1, "A rod..."), question(2, "A disc...")),
                new AiCallContext(null, requestId.toString(), false));

        assertThat(tags).hasSize(2);
        assertThat(tags.getFirst().output().primary()).isEqualTo("PHY.11.ROT.MOI");
        assertThat(tags.getFirst().output().confidence()).isEqualByComparingTo(new BigDecimal("0.91"));
        assertThat(task.promptVersion()).isEqualTo("pyq_tag.v1");
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?", requestId.toString());
        assertThat(rows).hasSize(2).allSatisfy(row -> {
            assertThat(row.get("feature")).isEqualTo("pipeline_tag");
            assertThat(row.get("tier")).isEqualTo("reason");
            assertThat(row.get("prompt_name")).isEqualTo("pyq_tag");
            assertThat(row.get("status")).isEqualTo("ok");
        });
    }

    /** The outline and options travel as data; the template — not Java — writes the words around them. */
    @Test
    void theTemplateRendersTheOutlineAndTheQuestion() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("outline", List.of(
                Map.of("depth0", true, "subject", true, "name", "Physics"),
                Map.of("depth2", true, "chapter", true, "code", "PHY.11.ROT", "name", "Systems of Particles"),
                Map.of("depth3", true, "topic", true, "code", "PHY.11.ROT.MOI", "name", "Moment of inertia")));
        variables.put("subject", "physics");
        variables.put("number", 7);
        variables.put("stem", "Which point of a rolling wheel moves fastest?");
        variables.put("options", List.of(Map.of("key", "1", "text", "the top"), Map.of("key", "2", "text", "the centre")));

        var rendered = prompts.render(PromptRef.named("pyq_tag"), variables);

        assertThat(rendered.system()).contains("Physics\n    Chapter PHY.11.ROT — Systems of Particles\n"
                        + "      Topic PHY.11.ROT.MOI — Moment of inertia")
                .contains("codes from that list only").contains("Never state or hint at the correct option")
                .doesNotContain("nearest chapter, with your reason");
        assertThat(rendered.user()).contains("Question 7, printed under physics")
                .contains("Which point of a rolling wheel moves fastest?\n(1) the top\n(2) the centre");
    }

    @Test
    void aTagWithoutAPrimaryOrWithThreeSecondariesIsRefusedWhereItIsDecoded() {
        assertThatThrownBy(() -> new PyqTag(" ", List.of(), false, BigDecimal.ONE, "r"))
                .hasMessageContaining("names no primary");
        assertThatThrownBy(() -> new PyqTag("A", List.of("B", "C", "D"), false, BigDecimal.ONE, "r"))
                .hasMessageContaining("at most two");
    }

    private static QuestionTagger.Question question(int number, String stem) {
        return new QuestionTagger.Question(number, "physics", stem, List.of(new QuestionTagger.Option("1", "a"),
                new QuestionTagger.Option("2", "b"), new QuestionTagger.Option("3", "c"), new QuestionTagger.Option("4", "d")));
    }
}
