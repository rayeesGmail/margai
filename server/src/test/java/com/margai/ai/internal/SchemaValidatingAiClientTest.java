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
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

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

    /**
     * A pipeline call's rejected output is NCERT text, not a student's, and it is the only evidence of what
     * the model sent: the ncert_verify calibration of 2026-09-15 hit "/items: string found" on its first
     * pages and nothing recorded the shape (D15). A student-facing feature's output stays out of the log.
     */
    @Test
    void aPipelineCallsRejectedOutputIsExcerptedInTheWarningAndAStudentsIsNot() {
        ListAppender<ILoggingEvent> log = new ListAppender<>();
        log.start();
        Logger logger = (Logger) LoggerFactory.getLogger(SchemaValidatingAiClient.class);
        logger.addAppender(log);
        try {
            AiRequest<String> pipeline = AiRequest.of(AiFeature.pipeline_verify, Tier.vision,
                    PromptRef.named("echo"), Map.of("text", "hi"), String.class, AiCallContext.system("req"));
            inner.then(invalid("{\"items\":\"[{\\\"item\\\": 1}]\"}", Usage.none())).then(StubAiClient.ok("ok"));
            validating.complete(pipeline);
            inner.then(invalid("{\"greeting\":\"a student's words\"}", Usage.none())).then(StubAiClient.ok("ok"));
            validating.complete(REQUEST);
        } finally {
            logger.detachAppender(log);
        }

        assertThat(log.list).hasSize(2);
        assertThat(log.list.get(0).getFormattedMessage()).contains("rejected output: {\"items\":\"[{\\\"item\\\": 1}]\"}");
        assertThat(log.list.get(1).getFormattedMessage()).doesNotContain("a student's words").doesNotContain("rejected output");
    }

    @Test
    void aLongRejectedOutputIsCutInTheWarning() {
        ListAppender<ILoggingEvent> log = new ListAppender<>();
        log.start();
        Logger logger = (Logger) LoggerFactory.getLogger(SchemaValidatingAiClient.class);
        logger.addAppender(log);
        try {
            AiRequest<String> pipeline = AiRequest.of(AiFeature.pipeline_extract, Tier.vision,
                    PromptRef.named("echo"), Map.of("text", "hi"), String.class, AiCallContext.system("req"));
            inner.then(invalid("{\"text\":\"" + "x".repeat(5000) + "\"}", Usage.none())).then(StubAiClient.ok("ok"));
            validating.complete(pipeline);
        } finally {
            logger.detachAppender(log);
        }

        assertThat(log.list.getFirst().getFormattedMessage()).contains("… (5011 characters)")
                .hasSizeLessThan(SchemaValidatingAiClient.EXCERPT + 400);
    }

    @Test
    void otherFailuresAreNotRepaired() {
        inner.then(AiUnavailableException.permanent("AccessDeniedException", "no", null));

        assertThatThrownBy(() -> validating.complete(REQUEST)).isInstanceOf(AiUnavailableException.class);
        assertThat(inner.requests).hasSize(1);
    }
}
