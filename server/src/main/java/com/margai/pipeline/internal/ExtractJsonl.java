package com.margai.pipeline.internal;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

/**
 * The JSONL artefact of {@code ncert extract} (TECH_PLAN §6.3): one {@link ExtractedPage} per
 * line, snake_case like every other wire shape in this codebase. It is written to the content
 * bucket rather than the database so that {@code ncert load} can be re-run — and a bad load
 * re-done — without paying for the pages again.
 */
final class ExtractJsonl {

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private ExtractJsonl() {
    }

    static byte[] write(List<ExtractedPage> pages) {
        StringBuilder jsonl = new StringBuilder();
        for (ExtractedPage page : pages) {
            jsonl.append(MAPPER.writeValueAsString(page)).append('\n');
        }
        return jsonl.toString().getBytes(StandardCharsets.UTF_8);
    }

    static List<ExtractedPage> read(byte[] jsonl) {
        List<ExtractedPage> pages = new ArrayList<>();
        for (String line : new String(jsonl, StandardCharsets.UTF_8).split("\n")) {
            if (!line.isBlank()) {
                pages.add(MAPPER.readValue(line, ExtractedPage.class));
            }
        }
        return pages;
    }
}
