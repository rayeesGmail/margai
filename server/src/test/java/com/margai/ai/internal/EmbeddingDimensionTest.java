package com.margai.ai.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * TECH_PLAN §4.9, §2.3: the embedding width is pinned in one place, and the {@code vector(n)}
 * columns have to agree with it. A stored vector is only comparable to vectors of the same
 * provider, model and width, so a mismatch between configuration and the schema is not a runtime
 * error to be caught later — it is a corpus to re-embed.
 *
 * <p>No vector column exists yet: V1 creates the extension and the columns of §2.3 arrive with
 * the content tables (D14 onwards). This test therefore guards nothing today and starts guarding
 * on the day the first one lands, which is the day it matters.
 */
class EmbeddingDimensionTest {

    private static final Path MIGRATIONS = Path.of("src/main/resources/db/migration");
    private static final Pattern VECTOR_COLUMN = Pattern.compile("vector\\s*\\(\\s*(\\d+)\\s*\\)");

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer())
            .withConfiguration(UserConfigurations.of(AiConfiguration.class));

    @Test
    void everyVectorColumnIsAsWideAsTheConfiguredEmbedding() throws IOException {
        List<String> declarations = vectorDeclarations();

        runner.run(context -> {
            int configured = context.getBean(AiProperties.class).embed().dimensions();
            assertThat(declarations)
                    .as("vector(n) columns against margai.ai.embed.dimensions = %d", configured)
                    .allSatisfy(declaration -> assertThat(declaration).endsWith("vector(" + configured + ")"));
        });
    }

    /** Every {@code vector(n)} in the migrations, as "file:line vector(n)". */
    private static List<String> vectorDeclarations() throws IOException {
        assertThat(MIGRATIONS).isDirectory();
        List<String> found = new ArrayList<>();
        try (Stream<Path> files = Files.walk(MIGRATIONS)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".sql")).sorted().toList()) {
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                for (int i = 0; i < lines.size(); i++) {
                    Matcher match = VECTOR_COLUMN.matcher(lines.get(i));
                    while (match.find()) {
                        found.add(MIGRATIONS.relativize(file) + ":" + (i + 1) + " vector(" + match.group(1) + ")");
                    }
                }
            }
        }
        return found;
    }
}
