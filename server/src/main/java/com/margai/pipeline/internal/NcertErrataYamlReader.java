package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code ncert-errata.yaml} into {@link NcertErratum}s (D16): an {@code errata} list, every entry with all of
 * {@code book, lang, chapter, section, para, page, printed, corrected, reason, round, meaning_changing, action,
 * flag_reviewed}. Strict like {@link NcertCorrectionsYamlReader}: an unknown key, a missing field or a value of
 * the wrong shape is refused by entry number.
 */
final class NcertErrataYamlReader {

    static final String FILE = "ncert-errata.yaml";

    private static final Set<String> KEYS = Set.of("book", "lang", "chapter", "section", "para", "page", "printed",
            "corrected", "reason", "round", "meaning_changing", "action", "flag_reviewed");
    private static final YAMLMapper YAML = YAMLMapper.builder().build();

    private NcertErrataYamlReader() {
    }

    static List<NcertErratum> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InputFormatException(file, 0, "is not valid YAML: " + e.getMessage());
        }
        if (root == null || !root.isObject()) {
            throw new InputFormatException(file, 0, "must be a mapping with an 'errata' list");
        }
        for (String key : root.propertyNames()) {
            if (!key.equals("errata")) {
                throw new InputFormatException(file, 0, "unknown top-level key '" + key + "'");
            }
        }
        JsonNode entries = root.get("errata");
        if (entries == null || !entries.isArray()) {
            throw new InputFormatException(file, 0, "'errata' must be a list (it may be empty)");
        }
        List<NcertErratum> errata = new ArrayList<>();
        int number = 0;
        for (JsonNode entry : entries) {
            errata.add(entry(file, entry, ++number));
        }
        return List.copyOf(errata);
    }

    private static NcertErratum entry(Path file, JsonNode entry, int number) {
        String label = "erratum " + number;
        if (!entry.isObject()) {
            throw new InputFormatException(file, 0, label + ": must be a mapping");
        }
        for (String key : entry.propertyNames()) {
            if (!KEYS.contains(key)) {
                throw new InputFormatException(file, 0, label + ": unknown key '" + key + "'");
            }
        }
        BookLanguage language = Enums.parse(text(file, entry, "lang", label), BookLanguage.class,
                () -> new InputFormatException(file, 0, label + ": lang must be one of "
                        + Arrays.toString(BookLanguage.values())));
        NcertErratum.Action action = Enums.parse(text(file, entry, "action", label), NcertErratum.Action.class,
                () -> new InputFormatException(file, 0, label + ": action must be one of "
                        + Arrays.toString(NcertErratum.Action.values())));
        short chapter = (short) whole(file, entry, "chapter", 1, 99, label);
        String printed = span(file, entry, "printed", label);
        String corrected = span(file, entry, "corrected", label);
        if (printed.equals(corrected)) {
            throw new InputFormatException(file, 0, label + ": 'printed' and 'corrected' are the same");
        }
        return new NcertErratum(
                text(file, entry, "book", label),
                language,
                chapter,
                section(file, entry, chapter, label),
                (short) whole(file, entry, "para", 1, 999, label),
                whole(file, entry, "page", 1, 999, label),
                printed,
                corrected,
                text(file, entry, "reason", label),
                text(file, entry, "round", label),
                flag(file, entry, "meaning_changing", label),
                action,
                flag(file, entry, "flag_reviewed", label));
    }

    /** Quoted, since YAML reads an unquoted 8.10 as the number 8.1, and a section of the entry's own chapter. */
    private static String section(Path file, JsonNode entry, short chapter, String label) {
        JsonNode value = entry.get("section");
        if (value != null && value.isNumber()) {
            throw new InputFormatException(file, 0, label + ": 'section' must be quoted — unquoted, 8.10 reads as 8.1");
        }
        String section = text(file, entry, "section", label).strip();
        if (!NcertLoadCommand.SECTION.matcher(section).matches() || !(section + ".").startsWith(chapter + ".")) {
            throw new InputFormatException(file, 0, label + ": '" + section + "' is not a section of chapter " + chapter);
        }
        return section;
    }

    /**
     * A span quoted, since YAML reads an unquoted 1.10 as the number 1.1, which a row carrying "1.10" would still
     * contain; a cross-reference is a span this file logs.
     */
    private static String span(Path file, JsonNode entry, String key, String label) {
        JsonNode value = entry.get(key);
        if (value != null && value.isNumber()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be quoted — unquoted, 1.10 reads as 1.1");
        }
        return text(file, entry, key, label);
    }

    /** A value is kept exactly as written; only an all-blank value counts as missing. */
    private static String text(Path file, JsonNode entry, String key, String label) {
        JsonNode value = entry.get(key);
        if (value == null || value.isNull()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' is required");
        }
        if (!value.isString() && !value.isNumber()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be a string");
        }
        String text = value.isString() ? value.stringValue() : value.asString();
        if (text.isBlank()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' is required");
        }
        return text;
    }

    private static boolean flag(Path file, JsonNode entry, String key, String label) {
        JsonNode value = entry.get(key);
        if (value == null || !value.isBoolean()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be true or false");
        }
        return value.booleanValue();
    }

    private static int whole(Path file, JsonNode entry, String key, int min, int max, String label) {
        JsonNode value = entry.get(key);
        if (value == null || !value.isIntegralNumber()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be a whole number");
        }
        int number = value.intValue();
        if (number < min || number > max) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be between " + min + " and " + max);
        }
        return number;
    }
}
