package com.margai.pipeline.internal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.core.JacksonException;
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

    /**
     * Reads the artefact, refusing by name rather than by stack trace when a line is not the page
     * shape this version writes.
     *
     * <p>That refusal earns its place. `has_equations` left the paragraph record at D14, so every
     * line written before it — and there are such lines in the bucket — carries a property this
     * reader is deliberately strict about. Without this the founder's next `ncert extract` would
     * die on a raw Jackson message while reading the file it resumes from, before a single model
     * call, and the remedy would have to be guessed (spec-auditor, D14). It is also the right
     * answer rather than merely a kinder one: an extraction is canonical per prompt version
     * (DECISIONS 2026-09-13), so a run cannot extend an artefact written by a different one.
     */
    static List<ExtractedPage> read(String key, byte[] jsonl) {
        List<ExtractedPage> pages = new ArrayList<>();
        long lineNo = 0;
        for (String line : new String(jsonl, StandardCharsets.UTF_8).split("\n")) {
            lineNo++;
            if (line.isBlank()) {
                continue;
            }
            try {
                pages.add(MAPPER.readValue(line, ExtractedPage.class));
            } catch (JacksonException e) {
                throw new InputFormatException(Path.of(key), lineNo,
                        "this line is not the page shape this version of the pipeline writes, so the "
                                + "extraction was produced by an earlier one — and an extraction is canonical "
                                + "per prompt version (DECISIONS 2026-09-13), never half one and half another. "
                                + "Delete " + key + " from the content bucket and extract the book again. ("
                                + firstLineOf(e) + ")");
            }
        }
        return pages;
    }

    /** Jackson's own message, without the location suffix that would bury the sentence above it. */
    private static String firstLineOf(JacksonException cause) {
        String message = cause.getMessage() == null ? cause.toString() : cause.getMessage();
        int newline = message.indexOf('\n');
        return newline < 0 ? message : message.substring(0, newline);
    }
}
