package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.AiUnavailableException;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.PromptRef;
import com.margai.ai.api.Repair;
import com.margai.ai.api.Tier;
import com.margai.ai.api.Usage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** TECH_PLAN §4.1: one repair retry with the validation error in context, then InvalidOutputException. */
class SchemaValidatingAiClientTest {

    private static final AiRequest<String> REQUEST = AiRequest.of(AiFeature.doubt, Tier.cheap,
            PromptRef.named("echo"), Map.of("text", "hi"), String.class, AiCallContext.system("req"));

    private final StubAiClient inner = new StubAiClient();
    private final SchemaValidatingAiClient validating = new SchemaValidatingAiClient(inner);

    private static InvalidOutputException invalid(String json, Usage usage) {
        return new InvalidOutputException(List.of("$.number: required property 'number' not found"), json, usage, "m");
    }

    @Test
    void validOutputPassesThroughUntouched() {
        inner.then(StubAiClient.ok("answer"));

        AiResponse<String> response = validating.complete(REQUEST);

        assertThat(response.output()).isEqualTo("answer");
        assertThat(response.usage()).isEqualTo(StubAiClient.USAGE);
        assertThat(inner.requests).hasSize(1);
        assertThat(inner.requests.get(0).repair()).isNull();
    }

    @Test
    void invalidOutputGetsOneRepairAttemptWithTheErrorsInContextAndSummedUsage() {
        inner.then(invalid("{\"greeting\":\"ok\"}", new Usage(100, 20, 0, 0)))
                .then(StubAiClient.ok("repaired", new Usage(120, 30, 0, 0)));

        AiResponse<String> response = validating.complete(REQUEST);

        assertThat(response.output()).isEqualTo("repaired");
        assertThat(response.usage()).isEqualTo(new Usage(220, 50, 0, 0));
        assertThat(response.attempts()).isEqualTo(2);
        assertThat(inner.requests).hasSize(2);
        Repair repair = inner.requests.get(1).repair();
        assertThat(repair.previousOutputJson()).isEqualTo("{\"greeting\":\"ok\"}");
        assertThat(repair.errors()).containsExactly("$.number: required property 'number' not found");
        assertThat(inner.requests.get(1).variables()).isEqualTo(REQUEST.variables());
    }

    @Test
    void aSecondInvalidOutputPropagatesWithBothAttemptsBilled() {
        inner.then(invalid("{}", new Usage(100, 20, 0, 0))).then(invalid("{}", new Usage(110, 25, 0, 0)));

        assertThatThrownBy(() -> validating.complete(REQUEST))
                .isInstanceOf(InvalidOutputException.class)
                .satisfies(e -> assertThat(((InvalidOutputException) e).usage()).isEqualTo(new Usage(210, 45, 0, 0)));
        assertThat(inner.requests).hasSize(2);
    }

    @Test
    void aRequestThatAlreadyCarriesARepairIsAttemptedOnce() {
        inner.then(invalid("{}", Usage.none()));

        assertThatThrownBy(() -> validating.complete(REQUEST.withRepair(new Repair("{}", List.of("x")))))
                .isInstanceOf(InvalidOutputException.class);
        assertThat(inner.requests).hasSize(1);
    }

    @Test
    void otherFailuresAreNotRepaired() {
        inner.then(AiUnavailableException.permanent("AccessDeniedException", "no", null));

        assertThatThrownBy(() -> validating.complete(REQUEST)).isInstanceOf(AiUnavailableException.class);
        assertThat(inner.requests).hasSize(1);
    }
}
