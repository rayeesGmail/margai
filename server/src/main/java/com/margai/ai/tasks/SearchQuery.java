package com.margai.ai.tasks;

/**
 * Output record of the {@code query_rewrite} prompt: the forced tool returns exactly this field, an
 * English search query for the NCERT textbooks in the books' own terms.
 */
public record SearchQuery(String searchQuery) {
}
