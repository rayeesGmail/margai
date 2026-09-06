package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.PromptRef;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TECH_PLAN §4.12: templates load at startup, duplicates are refused, the active version comes
 * from configuration or defaults to the highest, and rendering fills the variables. The smoke
 * prompt's system text must exceed the prompt-cache minimum (§13.2 item 1), so its length is
 * pinned here.
 */
class PromptRegistryTest {

    private static final String ZETA_V1 = """
            system(v) ::= <<
            System one.
            >>
            user(v) ::= <<
            Question: <v.question>
            >>
            """;

    private static final String ZETA_V2 = """
            system(v) ::= <<
            System two.
            >>
            user(v) ::= <<
            Q2: <v.question>
            >>
            """;

    @Test
    void loadsTheSmokePromptFromTheClasspath() {
        PromptRegistry registry = PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of());

        assertThat(registry.names()).contains("smoke");
        assertThat(registry.activeVersion("smoke")).isEqualTo(1);

        RenderedPrompt rendered = registry.render(registry.require("smoke"), Map.of("number", 7));

        assertThat(rendered.name()).isEqualTo("smoke");
        assertThat(rendered.version()).isEqualTo(1);
        assertThat(rendered.user()).contains("number set to 7").doesNotContain("<v.");
        assertThat(rendered.system()).startsWith("You are MARG AI").doesNotContain("<v.");
        // ≈ 4 characters per token: 20,000+ characters clears the 4,096-token cache minimum with margin.
        assertThat(rendered.system().length()).isGreaterThan(20_000);
    }

    @Test
    void highestVersionIsActiveUnlessConfigured() {
        Map<String, String> sources = Map.of("zeta.v1.stg", ZETA_V1, "zeta.v2.stg", ZETA_V2);

        PromptRegistry byDefault = new PromptRegistry(sources, Map.of());
        assertThat(byDefault.activeVersion("zeta")).isEqualTo(2);
        assertThat(byDefault.render(PromptRef.named("zeta"), Map.of("question", "why")).user()).isEqualTo("Q2: why");

        PromptRegistry pinned = new PromptRegistry(sources, Map.of("zeta", 1));
        assertThat(pinned.activeVersion("zeta")).isEqualTo(1);
        assertThat(pinned.render(PromptRef.named("zeta"), Map.of("question", "why")))
                .isEqualTo(new RenderedPrompt("zeta", 1, "System one.", "Question: why"));
    }

    @Test
    void refusesDuplicatesMissingTemplatesBadNamesAndUnknownVersions() {
        assertThatThrownBy(() -> new PromptRegistry(Map.of("zeta.v1.stg", ZETA_V1, "zeta.v01.stg", ZETA_V2), Map.of()))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("duplicate prompt zeta v1");
        assertThatThrownBy(() -> new PromptRegistry(Map.of("zeta.v1.stg", "system(v) ::= <<x>>\n"), Map.of()))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("user(v)");
        assertThatThrownBy(() -> new PromptRegistry(Map.of("Zeta.stg", ZETA_V1), Map.of()))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("<name>.v<N>.stg");
        assertThatThrownBy(() -> new PromptRegistry(Map.of("zeta.v1.stg", ZETA_V1), Map.of("zeta", 3)))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("zeta.v3.stg");
        assertThatThrownBy(() -> new PromptRegistry(Map.of("zeta.v1.stg", ZETA_V1), Map.of()).activeVersion("omega"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("omega");
    }

    @Test
    void fragmentGroupsCarryNamedFragmentsInsteadOfAPrompt() {
        PromptRegistry registry = PromptRegistry.fromClasspath(new PathMatchingResourcePatternResolver(), Map.of());

        assertThat(registry.renderFragment("_protocol", "tool_description", Map.of("task", "smoke")))
                .isEqualTo("Return the structured result of the smoke task.");
        assertThat(registry.renderFragment("_protocol", "repair", Map.of("errors", List.of("a: required", "b: bad"))))
                .startsWith("The tool input was rejected: a: required; b: bad.");
        assertThatThrownBy(() -> registry.renderFragment("_protocol", "nope", Map.of()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("nope");
        assertThatThrownBy(() -> registry.renderFragment("smoke", "user", Map.of()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("fragment group");

        PromptRegistry inMemory = new PromptRegistry(Map.of("_bits.v1.stg", "greet(v) ::= <<\nhi <v.name>\n>>\n"), Map.of());
        assertThat(inMemory.renderFragment("_bits", "greet", Map.of("name", "Asha"))).isEqualTo("hi Asha");
    }

    @Test
    void aTemplateSyntaxErrorFailsAtLoadTime() {
        assertThatThrownBy(() -> new PromptRegistry(Map.of("zeta.v1.stg", "system(v) ::= <<<v.x>>\nuser(v) ::= <<u>>\n"), Map.of()))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("zeta.v1.stg");
    }
}
