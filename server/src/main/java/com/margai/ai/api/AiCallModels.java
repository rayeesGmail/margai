package com.margai.ai.api;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Which model made a call, read back from the {@code ai_calls} ledger (TECH_PLAN §4.8). The second read
 * of the NCERT corpus is only worth having if a different model makes it (DECISIONS 2026-09-14 "the
 * pair"), and the ledger's own record of the transcription is the one source for which model that was
 * that no configuration change can rewrite.
 */
public interface AiCallModels {

    /** The model id of each call the ledger holds; ids it does not hold are absent from the map. */
    Map<UUID, String> modelsOf(Collection<UUID> aiCallIds);
}
