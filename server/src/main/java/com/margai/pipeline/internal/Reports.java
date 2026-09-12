package com.margai.pipeline.internal;

import com.margai.common.api.IstClock;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Writes a {@link Report} to {@code <reports>/<yyyy-MM-dd>-<command>.md} (TECH_PLAN §6.3), the
 * date being the IST day of the run. A re-run on the same day overwrites its own file: the
 * commands are idempotent, and the last run of the day is the one worth committing.
 */
@Component
@Profile("pipeline")
class Reports {

    /** The rendered text and where it went. */
    record Written(Path path, String text) {
    }

    private final IstClock clock;

    Reports(IstClock clock) {
        this.clock = clock;
    }

    Written write(Path directory, Report report) {
        String text = report.render(clock.nowIst());
        Path file = directory.resolve(clock.today() + "-" + report.slug() + ".md");
        try {
            Files.createDirectories(directory);
            Files.writeString(file, text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write the run report " + file.toAbsolutePath().normalize(), e);
        }
        return new Written(file, text);
    }
}
