package com.margai.ai.internal;

import com.margai.ai.api.AiFeature;
import com.margai.ai.api.Tier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

/**
 * {@code ai_calls} (TECH_PLAN §2.8, migration V5): the cost ledger, one row per call and per
 * outcome, written by the outermost {@code AiClient} decorator (§4.8). Append-only: no
 * {@code updated_at} (§2.1). {@code user_id} is an id, not an association — {@code users}
 * belongs to the account module (§1.3) — and is nullable for system and pipeline calls.
 */
@Entity
@Table(name = "ai_calls")
public class AiCall {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private AiFeature feature;

    @Column(nullable = false, length = 120)
    private String modelId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Tier tier;

    @Column(length = 64)
    private String promptName;

    private Short promptVersion;

    private Integer inputTokens;

    private Integer outputTokens;

    private Integer cacheReadTokens;

    private Integer cacheWriteTokens;

    @Column(nullable = false)
    private boolean batch;

    private Integer latencyMs;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AiCallStatus status;

    @Column(length = 64)
    private String errorCode;

    @Column(nullable = false)
    private long costPaise;

    @Column(length = 64)
    private String requestId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected AiCall() {
        // JPA
    }

    /** The identity of a call; token counts, latency and outcome are set through the builders below. */
    public AiCall(UUID userId, AiFeature feature, String modelId, Tier tier, String requestId) {
        this.userId = userId;
        this.feature = feature;
        this.modelId = modelId;
        this.tier = tier;
        this.requestId = requestId;
        this.status = AiCallStatus.ok;
    }

    public AiCall prompt(String name, Short version) {
        this.promptName = name;
        this.promptVersion = version;
        return this;
    }

    public AiCall tokens(Integer input, Integer output, Integer cacheRead, Integer cacheWrite) {
        this.inputTokens = input;
        this.outputTokens = output;
        this.cacheReadTokens = cacheRead;
        this.cacheWriteTokens = cacheWrite;
        return this;
    }

    public AiCall batch(boolean batch) {
        this.batch = batch;
        return this;
    }

    public AiCall latencyMs(Integer latencyMs) {
        this.latencyMs = latencyMs;
        return this;
    }

    public AiCall outcome(AiCallStatus status, String errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        return this;
    }

    public AiCall costPaise(long costPaise) {
        this.costPaise = costPaise;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public AiFeature getFeature() {
        return feature;
    }

    public String getModelId() {
        return modelId;
    }

    public Tier getTier() {
        return tier;
    }

    public String getPromptName() {
        return promptName;
    }

    public Short getPromptVersion() {
        return promptVersion;
    }

    public Integer getInputTokens() {
        return inputTokens;
    }

    public Integer getOutputTokens() {
        return outputTokens;
    }

    public Integer getCacheReadTokens() {
        return cacheReadTokens;
    }

    public Integer getCacheWriteTokens() {
        return cacheWriteTokens;
    }

    public boolean isBatch() {
        return batch;
    }

    public Integer getLatencyMs() {
        return latencyMs;
    }

    public AiCallStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public long getCostPaise() {
        return costPaise;
    }

    public String getRequestId() {
        return requestId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
