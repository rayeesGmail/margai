package com.margai.pipeline.internal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
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
            // The fields that mark an earlier shape, named before Jackson gets to complain about
            // whatever it meets first (a missing boolean, on a v2 line) — the founder reads the
            // field's name and knows which run wrote the file.
            List<String> earlier = new ArrayList<>();
            for (String marker : List.of("para_no", "has_equations")) {
                if (line.contains("\"" + marker + "\"")) {
                    earlier.add("`" + marker + "`");
                }
            }
            if (!earlier.isEmpty()) {
                throw new InputFormatException(Path.of(key), lineNo, refusal(key, "it carries "
                        + String.join(" and ", earlier) + ", which this version's page shape does not have"));
            }
            try {
                pages.add(MAPPER.readValue(line, ExtractedPage.class));
            } catch (JacksonException e) {
                // A line of this version's shape that the page itself rejects — a blank paragraph,
                // a flag off the first paragraph — is one page's defect, not the file's version:
                // it is named with the page and the redo, the way the loader names its refusals.
                IllegalArgumentException rejected = rejection(e);
                String page = rejected == null ? null : pageOf(line);
                if (page != null) {
                    throw new InputFormatException(Path.of(key), lineNo, page + rejected.getMessage()
                            + " — re-extract that page (`ncert extract --redo --chapters " + chapterOf(line)
                            + " --pages " + pageNumberOf(line) + "`)");
                }
                throw new InputFormatException(Path.of(key), lineNo, refusal(key, firstLineOf(e)));
            }
        }
        return pages;
    }

    /** The record's own objection, when that is what Jackson is wrapping; null for a shape problem. */
    private static IllegalArgumentException rejection(Throwable failure) {
        for (Throwable cause = failure; cause != null; cause = cause.getCause()) {
            if (cause instanceof IllegalArgumentException rejected) {
                return rejected;
            }
        }
        return null;
    }

    /** "ch 4 page 2: " from the line's own fields, or null when the line does not carry them. */
    private static String pageOf(String line) {
        try {
            JsonNode node = MAPPER.readTree(line);
            if (!node.path("chapter_no").isNumber() || !node.path("page").isNumber()) {
                return null;
            }
            return "ch " + node.path("chapter_no").asInt() + " page " + node.path("page").asInt() + ": ";
        } catch (JacksonException e) {
            return null;
        }
    }

    private static int chapterOf(String line) {
        return MAPPER.readTree(line).path("chapter_no").asInt();
    }

    private static int pageNumberOf(String line) {
        return MAPPER.readTree(line).path("page").asInt();
    }

    private static String refusal(String key, String detail) {
        return "this line is not the page shape this version of the pipeline writes, so the extraction was "
                + "produced by an earlier one — and an extraction is canonical per prompt version (DECISIONS "
                + "2026-09-13), never half one and half another. Move " + key + " aside in the content bucket "
                + "and extract the book again. (" + detail + ")";
    }

    /** Jackson's own message, without the location suffix that would bury the sentence above it. */
    private static String firstLineOf(JacksonException cause) {
        String message = cause.getMessage() == null ? cause.toString() : cause.getMessage();
        int newline = message.indexOf('\n');
        return newline < 0 ? message : message.substring(0, newline);
    }
}
