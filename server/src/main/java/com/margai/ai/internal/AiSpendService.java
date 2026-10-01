package com.margai.ai.internal;

import com.margai.ai.api.AiCallModels;
import com.margai.ai.api.AiSpend;
import com.margai.ai.api.Usage;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link AiSpend} over the ledger (TECH_PLAN §4.8): what a request id actually cost, summed from
 * the rows themselves rather than recomputed from a price table, so a report can never disagree
 * with the ledger it is reporting on. And {@link AiCallModels}: which model the ledger says made a call.
 */
@Service
@Transactional(readOnly = true)
class AiSpendService implements AiSpend, AiCallModels {

    private final AiCallRepository calls;

    AiSpendService(AiCallRepository calls) {
        this.calls = calls;
    }

    @Override
    public RunSpend of(String requestId) {
        List<AiCall> rows = calls.findByRequestIdOrderByCreatedAt(requestId);
        long costPaise = 0;
        Usage usage = Usage.none();
        for (AiCall row : rows) {
            costPaise += row.getCostPaise();
            usage = usage.plus(new Usage(value(row.getInputTokens()), value(row.getOutputTokens()),
                    value(row.getCacheReadTokens()), value(row.getCacheWriteTokens())));
        }
        return new RunSpend(rows.size(), costPaise, usage);
    }

    @Override
    public Map<UUID, String> modelsOf(Collection<UUID> aiCallIds) {
        Map<UUID, String> models = new HashMap<>();
        calls.findAllById(aiCallIds.stream().filter(Objects::nonNull).distinct().toList())
                .forEach(call -> models.put(call.getId(), call.getModelId()));
        return models;
    }

    private static int value(Integer tokens) {
        return tokens == null ? 0 : tokens;
    }
}
