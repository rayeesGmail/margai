package com.margai.pipeline.internal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

/**
 * The artefact of {@code ncert verify --read-pages} (D15): one {@link VerifiedPage} per line, snake_case
 * like {@link ExtractJsonl}. In the content bucket beside the extraction, so a rebuilt database, a new
 * ruling or an interrupted book costs nothing that was already read.
 */
final class VerifyJsonl {

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private VerifyJsonl() {
    }

    static byte[] write(List<VerifiedPage> pages) {
        StringBuilder jsonl = new StringBuilder();
        for (VerifiedPage page : pages) {
            jsonl.append(MAPPER.writeValueAsString(page)).append('\n');
        }
        return jsonl.toString().getBytes(StandardCharsets.UTF_8);
    }

    static List<VerifiedPage> read(String key, byte[] jsonl) {
        List<VerifiedPage> pages = new ArrayList<>();
        long lineNo = 0;
        for (String line : new String(jsonl, StandardCharsets.UTF_8).split("\n")) {
            lineNo++;
            if (line.isBlank()) {
                continue;
            }
            try {
                pages.add(MAPPER.readValue(line, VerifiedPage.class));
            } catch (RuntimeException e) {
                throw new InputFormatException(Path.of(key), lineNo, "is not a verified page this version reads: "
                        + e.getMessage() + " — move the artefact aside and read the pages again");
            }
        }
        return pages;
    }
}
