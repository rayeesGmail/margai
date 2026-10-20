package com.margai.pipeline.internal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

/** {@code pyq tag}'s artefact: one {@link PyqTagLine} per line, snake_case, refused by line when malformed. */
final class PyqTagJsonl {

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private PyqTagJsonl() {
    }

    static byte[] write(List<PyqTagLine> lines) {
        StringBuilder jsonl = new StringBuilder();
        for (PyqTagLine line : lines) {
            jsonl.append(MAPPER.writeValueAsString(line)).append('\n');
        }
        return jsonl.toString().getBytes(StandardCharsets.UTF_8);
    }

    static List<PyqTagLine> read(String key, byte[] jsonl) {
        List<PyqTagLine> lines = new ArrayList<>();
        long lineNo = 0;
        for (String line : new String(jsonl, StandardCharsets.UTF_8).split("\n")) {
            lineNo++;
            if (line.isBlank()) {
                continue;
            }
            try {
                lines.add(MAPPER.readValue(line, PyqTagLine.class));
            } catch (JacksonException e) {
                throw new InputFormatException(Path.of(key), lineNo, "is not a tag line of this version — "
                        + e.getOriginalMessage() + "; re-tag with `pyq tag --redo --questions N`");
            }
        }
        return lines;
    }
}
