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
 * The JSONL artefact of {@code pyq extract} (D19): one {@link PyqExtractedPage} per line,
 * snake_case like every other wire shape here, in the content bucket so the draft can be
 * re-assembled — and a defect re-checked — without paying for the pages again ({@link ExtractJsonl}
 * is the NCERT one's). A line that is not this shape is refused by name and line.
 */
final class PyqExtractJsonl {

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private PyqExtractJsonl() {
    }

    static byte[] write(List<PyqExtractedPage> pages) {
        StringBuilder jsonl = new StringBuilder();
        for (PyqExtractedPage page : pages) {
            jsonl.append(MAPPER.writeValueAsString(page)).append('\n');
        }
        return jsonl.toString().getBytes(StandardCharsets.UTF_8);
    }

    static List<PyqExtractedPage> read(String key, byte[] jsonl) {
        List<PyqExtractedPage> pages = new ArrayList<>();
        long lineNo = 0;
        for (String line : new String(jsonl, StandardCharsets.UTF_8).split("\n")) {
            lineNo++;
            if (line.isBlank()) {
                continue;
            }
            try {
                pages.add(MAPPER.readValue(line, PyqExtractedPage.class));
            } catch (JacksonException e) {
                throw new InputFormatException(Path.of(key), lineNo, "is not a page of this version's artefact — "
                        + e.getOriginalMessage() + "; re-read that page with `pyq extract --redo --pages N`");
            }
        }
        return pages;
    }
}
