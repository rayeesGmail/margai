package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.PromptRef;
import com.margai.ai.internal.PromptRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * The query rewrite on the fake client (D18; DECISIONS 2026-10-19): a question in, an English search
 * version out, ledgered as a {@code cheap} {@code eval} row. The prompt is generic by the founder's
 * ruling — written before the measuring run, with nothing taken from the queries it is measured on —
 * and its syllabus outline is smoke.v1's, word for word, which also carries it past the cheap model's
 * cache minimum.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class QueryRewriteTaskTest {

    /** The committed query set, read from the repository. */
    private static final Path QUERIES = Path.of("..", "eval", "retrieval-queries.json");

    @Autowired
    private QueryRewriteTask task;

    @Autowired
    private PromptRegistry prompts;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void rewritesAQuestionAndLedgersTheCallAsAnEvalOnTheCheapTier() {
        String requestId = UUID.randomUUID().toString();

        AiResponse<SearchQuery> response = task.run("कोई प्रश्न", new AiCallContext(null, requestId, false));

        assertThat(response.output().searchQuery()).isNotBlank();
        Map<String, Object> row = jdbc.queryForMap(
                "SELECT feature, tier, prompt_name, status FROM ai_calls WHERE request_id = ?", requestId);
        assertThat(row.get("feature")).isEqualTo("eval");
        assertThat(row.get("tier")).isEqualTo("cheap");
        assertThat(row.get("prompt_name")).isEqualTo("query_rewrite");
        assertThat(row.get("status")).isEqualTo("ok");
    }

    @Test
    void theUserTurnCarriesTheQuestionAsTyped() {
        String user = prompts.render(PromptRef.named("query_rewrite"),
                Map.of("question", "bhai ye kyu hota hai, matlab samjhao")).user();

        assertThat(user).isEqualTo("Question: bhai ye kyu hota hai, matlab samjhao");
    }

    /** One outline in two prompts drifts the moment one changes; the rewrite's copy must stay smoke's. */
    @Test
    void theSyllabusOutlineIsSmokesWordForWord() {
        String smoke = prompts.systemPrefix("smoke");
        String outline = smoke.substring(smoke.indexOf("PHYSICS — CLASS 11"),
                smoke.indexOf("End of the syllabus outline.")).strip();

        assertThat(outline).contains("BIOLOGY — CLASS 12").contains("Ramsar sites");
        assertThat(prompts.systemPrefix("query_rewrite")).contains(outline);
    }

    /** Below the cheap model's 4,096-token minimum the cache point is a silent no-op (§4.11), at ~4 chars a token. */
    @Test
    void theSystemPrefixClearsTheCheapModelsCacheMinimum() {
        assertThat(prompts.systemPrefix("query_rewrite").length() / 4).isGreaterThan(4096);
    }

    /**
     * The founder's ruling: the prompt is written generically, with no per-query tuning. No query of the
     * set it is measured on appears in it, and neither does the one coaching term the held-out set uses
     * that NCERT never prints.
     */
    @Test
    void thePromptCarriesNoneOfTheQueriesItIsMeasuredOn() throws IOException {
        String system = prompts.systemPrefix("query_rewrite");
        List<String> texts = new ArrayList<>();
        for (JsonNode query : JsonMapper.builder().build().readTree(Files.readString(QUERIES)).path("queries")) {
            texts.add(query.path("text").asString());
        }

        assertThat(texts).hasSizeGreaterThan(30);
        assertThat(texts).allSatisfy(text -> assertThat(system).doesNotContain(text));
        assertThat(system.toLowerCase(Locale.ROOT)).doesNotContain("okazaki");
    }
}
