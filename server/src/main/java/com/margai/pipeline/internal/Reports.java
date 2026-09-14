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
 * date being the IST day of the run. A re-run on the same day gets its own file, numbered from
 * {@code -2}, and never touches an earlier run's: the report is the day's committed evidence
 * (.claude/rules/pipeline.md), and once `ncert extract` started running several times a day —
 * a chapter-7 dry run, a whole book, a one-page redo — the last run's report silently replaced
 * the one the commit was meant to carry (TRACKER 2026-09-14, PARKED at D14).
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
        String stem = clock.today() + "-" + report.slug();
        Path file = directory.resolve(stem + ".md");
        for (int run = 2; Files.exists(file); run++) {
            file = directory.resolve(stem + "-" + run + ".md");
        }
        try {
            Files.createDirectories(directory);
            Files.writeString(file, text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write the run report " + file.toAbsolutePath().normalize(), e);
        }
        return new Written(file, text);
    }
}
