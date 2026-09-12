package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/**
 * One command's run report (TECH_PLAN §6.3 "each writes {@code pipeline/reports/<date>-<command>.md},
 * committed as the evidence for that day's ✅ check"): what was run over which file (its SHA-256
 * pins the exact input), what was read, the counts and lists the load returned, and the result —
 * {@code ok}, or the reason the run failed, because a refused file is evidence too. Markdown, so
 * the founder reads it where it lands and the same text goes to stdout.
 */
final class Report {

    private static final DateTimeFormatter WHEN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm 'IST'");
    private static final String COMMAND_PREFIX = PipelineRunner.COMMAND_NAME + " ";

    private final String title;
    private final Path input;
    private final String sha256;
    private final List<String> body = new ArrayList<>();
    private String read = "nothing yet";
    private String result = "ok";

    /** {@code title} is the command's qualified name, e.g. {@code margai-pipeline taxonomy load}. */
    Report(String title, Path input) {
        this.title = title;
        this.input = input.toAbsolutePath().normalize();
        this.sha256 = sha256(input);
    }

    /** The file name's command part: {@code taxonomy-load} for {@code margai-pipeline taxonomy load}. */
    String slug() {
        String command = title.startsWith(COMMAND_PREFIX) ? title.substring(COMMAND_PREFIX.length()) : title;
        return command.trim().replace(' ', '-');
    }

    String result() {
        return result;
    }

    Report read(String what) {
        this.read = what;
        return this;
    }

    Report section(String heading) {
        body.add("");
        body.add("## " + heading);
        body.add("");
        return this;
    }

    Report line(String text) {
        body.add(text);
        return this;
    }

    /** A markdown table; {@code rows} are already-formatted cells. */
    Report table(List<String> header, List<List<String>> rows) {
        body.add("| " + String.join(" | ", header) + " |");
        body.add("|" + "---|".repeat(header.size()));
        for (List<String> row : rows) {
            body.add("| " + String.join(" | ", row) + " |");
        }
        return this;
    }

    /** A sorted list of names, or {@code none}. */
    Report list(List<String> items) {
        if (items.isEmpty()) {
            body.add("none");
        } else {
            items.forEach(item -> body.add("- " + item));
        }
        return this;
    }

    Report failed(String reason) {
        this.result = "FAILED: " + reason;
        return this;
    }

    String render(ZonedDateTime when) {
        StringBuilder text = new StringBuilder();
        text.append("# ").append(title).append('\n').append('\n');
        text.append("- run: ").append(WHEN.format(when)).append('\n');
        text.append("- input: ").append(input).append('\n');
        text.append("- sha256: ").append(sha256).append('\n');
        text.append("- read: ").append(read).append('\n');
        text.append("- result: ").append(result).append('\n');
        body.forEach(line -> text.append(line).append('\n'));
        return text.toString();
    }

    static String sha256(Path file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(Files.readAllBytes(file)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is part of every Java runtime", e);
        }
    }
}
