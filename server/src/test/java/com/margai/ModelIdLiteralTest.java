package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * TECH_PLAN §4.13 "model IDs, prices, limits from config": no model id literal anywhere in the
 * ai module's sources. Bytecode offers no string-literal API, so this is a source scan of
 * {@code src/main/java/com/margai/ai}; a model *family* prefix that selects a wire format
 * ({@code amazon.titan}) is not an id and is allowed.
 */
class ModelIdLiteralTest {

    static final Pattern MODEL_ID = Pattern.compile(
            "anthropic\\.claude-|cohere\\.embed-|amazon\\.titan-|global\\.anthropic|apac\\.anthropic|claude-[a-z]+-\\d");

    @Test
    void noModelIdLiteralInTheAiModule() throws IOException {
        Path root = Path.of("src/main/java/com/margai/ai");
        assertThat(root).isDirectory();
        List<String> hits = new ArrayList<>();
        try (Stream<Path> files = Files.walk(root)) {
            for (Path file : files.filter(p -> p.toString().endsWith(".java")).toList()) {
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                for (int i = 0; i < lines.size(); i++) {
                    if (MODEL_ID.matcher(lines.get(i)).find()) {
                        hits.add(root.relativize(file) + ":" + (i + 1) + " " + lines.get(i).strip());
                    }
                }
            }
        }
        assertThat(hits).as("model id literals in ai sources (use margai.ai.* config)").isEmpty();
    }

    @Test
    void theScanRecognisesTheConfiguredIdShapes() {
        assertThat(MODEL_ID.matcher("global.anthropic.claude-haiku-4-5-20251001-v1:0").find()).isTrue();
        assertThat(MODEL_ID.matcher("cohere.embed-multilingual-v3").find()).isTrue();
        assertThat(MODEL_ID.matcher("amazon.titan-embed-text-v2:0").find()).isTrue();
        assertThat(MODEL_ID.matcher("modelId.startsWith(\"amazon.titan\")").find()).isFalse();
    }
}
