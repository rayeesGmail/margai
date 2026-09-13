package com.margai.ai.internal;

import com.margai.ai.api.AiSpend;
import com.margai.ai.api.Usage;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link AiSpend} over the ledger (TECH_PLAN §4.8): what a request id actually cost, summed from
 * the rows themselves rather than recomputed from a price table, so a report can never disagree
 * with the ledger it is reporting on.
 */
@Service
@Transactional(readOnly = true)
class AiSpendService implements AiSpend {

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

    private static int value(Integer tokens) {
        return tokens == null ? 0 : tokens;
    }
}
