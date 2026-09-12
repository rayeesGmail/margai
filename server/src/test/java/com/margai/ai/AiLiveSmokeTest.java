package com.margai.ai;

import static org.assertj.core.api.Assertions.assertThat;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.batches.BatchCreateParams;
import com.anthropic.models.messages.batches.MessageBatch;
import com.anthropic.models.messages.batches.MessageBatchIndividualResponse;
import com.margai.TestcontainersConfiguration;
import com.margai.ai.api.AiCallContext;
import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiClientInfo;
import com.margai.ai.api.AiFeature;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.ImagePart;
import com.margai.ai.api.Tier;
import com.margai.ai.internal.AiCall;
import com.margai.ai.internal.AiCallRepository;
import com.margai.ai.internal.AiCallStatus;
import com.margai.ai.internal.AiProperties;
import com.margai.ai.tasks.SmokeAnswer;
import com.margai.ai.tasks.SmokeTask;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D5 live smoke, re-run under the direct-provider adapters (PLAN D5 ✅ "one live call logged
 * with token counts"; TECH_PLAN §4.1 last paragraph, §13.2 item 1). Founder-run only:
 * {@code AI_LIVE=1} plus both provider keys in the environment; otherwise every test here is
 * skipped and the build reaches no provider.
 *
 * <pre>
 * cd server && AI_LIVE=1 ./mvnw test -Dtest=AiLiveSmokeTest -Dsurefire.failIfNoSpecifiedTests=false
 * </pre>
 *
 * <p>Together the tests close the open items of the provider switch: the configured ids exist,
 * forced tool use and prompt caching work on the cheap model, the reasoning model accepts its own
 * request shape, the vision tier takes an image, the embedding provider returns a vector of the
 * pinned width, and the batch lane accepts the reasoning model — each completion and embedding
 * logged to {@code ai_calls} with token counts and a cost.
 */
@SpringBootTest
@ActiveProfiles("live")
@Import(TestcontainersConfiguration.class)
@EnabledIfEnvironmentVariable(named = "AI_LIVE", matches = "1")
class AiLiveSmokeTest {

    /** A one-record batch is usually minutes; the cap keeps a stuck job from hanging the build. */
    private static final Duration BATCH_LIMIT = Duration.ofMinutes(10);
    private static final Duration BATCH_POLL = Duration.ofSeconds(15);

    @Autowired
    private SmokeTask smoke;

    @Autowired
    private AiClient ai;

    @Autowired
    private AiClientInfo info;

    @Autowired
    private AiProperties properties;

    @Autowired
    private AiCallRepository calls;

    @Autowired
    private AnthropicClient provider;

    @Test
    void theConfiguredModelIdsExistAtTheProvider() {
        assertThat(info.isLive()).as("inner client under the live profile").isTrue();
        assertThat(info.inner()).isEqualTo(properties.provider());

        for (Tier tier : List.of(Tier.cheap, Tier.reason, Tier.vision)) {
            String configured = properties.modelFor(tier);
            assertThat(provider.models().retrieve(configured).id())
                    .as("%s tier model exists at the provider", tier)
                    .isEqualTo(configured);
        }
    }

    @Test
    void twoCheapCallsProveForcedToolUseTokenCountsAndPromptCaching() {
        String requestId = "smoke-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> first = smoke.run(7, AiCallContext.system(requestId));
        AiResponse<SmokeAnswer> second = smoke.run(8, AiCallContext.system(requestId));

        assertThat(first.output().number()).isEqualTo(7);
        assertThat(second.output().number()).isEqualTo(8);
        assertThat(first.output().greeting()).isNotBlank();
        assertThat(first.modelId()).isEqualTo(properties.modelFor(Tier.cheap));

        List<AiCall> rows = report(requestId);
        assertThat(rows).hasSize(2);
        rows.forEach(row -> assertOkRow(row, Tier.cheap));
        assertThat(rows.get(0).getCacheWriteTokens() + rows.get(0).getCacheReadTokens())
                .as("the system prefix was cached (written, or read if a run within the TTL preceded this one)")
                .isPositive();
        assertThat(rows.get(1).getCacheReadTokens())
                .as("second call read the prompt cache (§13.2 item 1)")
                .isPositive();
    }

    /** The reasoning model rejects a temperature; this proves the configured shape is the right one. */
    @Test
    void theReasoningTierAnswersOnItsOwnRequestShape() {
        String requestId = "smoke-reason-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> answer = smoke.runOnReasonTier(9, AiCallContext.system(requestId));

        assertThat(answer.output().number()).isEqualTo(9);
        assertThat(answer.modelId()).isEqualTo(properties.modelFor(Tier.reason));
        List<AiCall> rows = report(requestId);
        assertThat(rows).hasSize(1);
        assertOkRow(rows.get(0), Tier.reason);
    }

    @Test
    void theVisionTierAcceptsAnImage() throws Exception {
        String requestId = "smoke-vision-" + UUID.randomUUID();

        AiResponse<SmokeAnswer> answer = smoke.runOnVisionTier(4, onePixelPng(), AiCallContext.system(requestId));

        assertThat(answer.output().number()).isEqualTo(4);
        List<AiCall> rows = report(requestId);
        assertThat(rows).hasSize(1);
        assertOkRow(rows.get(0), Tier.vision);
    }

    /** §4.9: the width the schema depends on, in both languages the app serves. */
    @Test
    void theEmbeddingProviderReturnsVectorsOfThePinnedWidth() {
        String requestId = "smoke-embed-" + UUID.randomUUID();

        for (String text : List.of("Newton's second law of motion", "न्यूटन का गति का दूसरा नियम")) {
            AiResponse<float[]> embedding = ai.embed(new EmbedRequest(AiFeature.embed, text,
                    EmbedRequest.InputType.search_document, AiCallContext.system(requestId)));
            assertThat(embedding.output()).hasSize(properties.embed().dimensions());
            assertThat(embedding.modelId()).isEqualTo(properties.embed().model());
        }

        List<AiCall> rows = report(requestId);
        assertThat(rows).hasSize(2);
        rows.forEach(row -> {
            assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
            assertThat(row.getTier()).isEqualTo(Tier.embed);
            assertThat(row.getInputTokens()).as("input tokens").isPositive();
            assertThat(row.getCostPaise()).as("cost").isPositive();
        });
    }

    /**
     * The finding the offline lane rests on (DECISIONS 2026-09-12): the batch endpoint accepts the
     * reasoning model. A probe, not the nightly runner — the seam's batch path is D55 work, so
     * this writes no ledger row and prints the usage instead.
     */
    @Test
    void theBatchLaneAcceptsTheReasoningModel() throws Exception {
        String customId = "smoke-batch-" + UUID.randomUUID();
        MessageBatch submitted = provider.messages().batches().create(BatchCreateParams.builder()
                .addRequest(BatchCreateParams.Request.builder()
                        .customId(customId)
                        .params(BatchCreateParams.Request.Params.builder()
                                .model(properties.modelFor(Tier.reason))
                                .maxTokens(16)
                                .addUserMessage("Reply with the single word ok.")
                                .build())
                        .build())
                .build());

        MessageBatch ended = awaitEnd(submitted.id());
        assertThat(ended.processingStatus()).isEqualTo(MessageBatch.ProcessingStatus.ENDED);

        try (var results = provider.messages().batches().resultsStreaming(ended.id())) {
            List<MessageBatchIndividualResponse> responses = results.stream().toList();
            assertThat(responses).hasSize(1);
            MessageBatchIndividualResponse response = responses.get(0);
            assertThat(response.customId()).isEqualTo(customId);
            assertThat(response.result().isSucceeded())
                    .as("the reasoning model is supported on the batch endpoint")
                    .isTrue();
            var usage = response.result().asSucceeded().message().usage();
            System.out.printf("=== batch lane: %s | in=%d out=%d ===%n",
                    properties.modelFor(Tier.reason), usage.inputTokens(), usage.outputTokens());
            assertThat(usage.inputTokens()).isPositive();
        }
    }

    private MessageBatch awaitEnd(String batchId) throws InterruptedException {
        Instant deadline = Instant.now().plus(BATCH_LIMIT);
        while (true) {
            MessageBatch batch = provider.messages().batches().retrieve(batchId);
            if (batch.processingStatus().equals(MessageBatch.ProcessingStatus.ENDED)) {
                return batch;
            }
            if (Instant.now().isAfter(deadline)) {
                throw new AssertionError("batch " + batchId + " was still " + batch.processingStatus()
                        + " after " + BATCH_LIMIT + "; results arrive within 24 h, so re-run rather than read this"
                        + " as a lack of support");
            }
            Thread.sleep(BATCH_POLL.toMillis());
        }
    }

    private void assertOkRow(AiCall row, Tier tier) {
        assertThat(row.getStatus()).isEqualTo(AiCallStatus.ok);
        assertThat(row.getFeature()).isEqualTo(AiFeature.smoke);
        assertThat(row.getTier()).isEqualTo(tier);
        assertThat(row.getModelId()).isEqualTo(properties.modelFor(tier));
        assertThat(row.getPromptName()).isEqualTo("smoke");
        assertThat(row.getPromptVersion()).isEqualTo((short) 1);
        assertThat(row.getInputTokens()).as("input tokens").isPositive();
        assertThat(row.getOutputTokens()).as("output tokens").isPositive();
        assertThat(row.getCostPaise()).as("cost").isPositive();
        assertThat(row.getLatencyMs()).isPositive();
    }

    private List<AiCall> report(String requestId) {
        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        System.out.println("=== D5 live smoke: ai_calls rows for " + requestId + " ===");
        rows.forEach(row -> System.out.println(describe(row)));
        return rows;
    }

    private static ImagePart onePixelPng() throws Exception {
        ByteArrayOutputStream png = new ByteArrayOutputStream();
        ImageIO.write(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), "png", png);
        return new ImagePart(png.toByteArray(), "image/png");
    }

    private static String describe(AiCall row) {
        return String.format("ai_calls %s | %s | %s | %s v%s | status=%s | in=%d out=%d cache_read=%d cache_write=%d"
                + " | %d ms | %d paise | request_id=%s",
                row.getId(), row.getFeature(), row.getModelId(), row.getPromptName(), row.getPromptVersion(),
                row.getStatus(), row.getInputTokens(), row.getOutputTokens(), row.getCacheReadTokens(),
                row.getCacheWriteTokens(), row.getLatencyMs(), row.getCostPaise(), row.getRequestId());
    }
}
