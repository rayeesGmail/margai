package com.margai.ai.api;

/**
 * Ledger category of an AI call: {@code ai_calls.feature} (TECH_PLAN §2.8, migration V5).
 * Constants are the lowercase database codes (DECISIONS D4). Adding a value is a one-line
 * migration on the CHECK constraint plus a constant here.
 */
public enum AiFeature {
    doubt,
    doubt_route,
    doubt_verify,
    doubt_render,
    doubt_extract,
    plan,
    mentor_message,
    classify,
    srs_variant,
    extract_document,
    embed,
    pipeline_extract,
    pipeline_solution,
    pipeline_verify,
    pipeline_distractor,
    pipeline_difficulty,
    pipeline_trap,
    pipeline_generate,
    eval,
    smoke
}
