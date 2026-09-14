package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code ncert-corrections.yaml} into {@link NcertCorrection}s (D15): a {@code corrections} list,
 * each entry with {@code book, lang, chapter, page, kind, reason}, the fields its kind needs and no
 * others, and an optional {@code address}. Strict like {@link BooksYamlReader}: an unknown key, a
 * missing field or a field that does not belong to the kind is refused by entry number, because a
 * correction silently ignored is a defect silently kept.
 */
final class NcertCorrectionsYamlReader {

    static final String FILE = "ncert-corrections.yaml";

    private static final Set<String> COMMON_KEYS = Set.of("book", "lang", "chapter", "page", "kind", "reason", "address");
    private static final Map<NcertCorrection.Kind, Set<String>> KIND_KEYS = Map.of(
            NcertCorrection.Kind.text, Set.of("transcribed", "printed"),
            NcertCorrection.Kind.join, Set.of("at"),
            NcertCorrection.Kind.split, Set.of("at"),
            NcertCorrection.Kind.misprint, Set.of("transcribed", "printed"),
            NcertCorrection.Kind.false_positive, Set.of("transcribed", "printed"));
    private static final Set<String> KIND_SPECIFIC = Set.of("transcribed", "printed", "at");
    private static final YAMLMapper YAML = YAMLMapper.builder().build();

    private NcertCorrectionsYamlReader() {
    }

    static List<NcertCorrection> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InputFormatException(file, 0, "is not valid YAML: " + e.getMessage());
        }
        if (root == null || !root.isObject()) {
            throw new InputFormatException(file, 0, "must be a mapping with a 'corrections' list");
        }
        for (String key : root.propertyNames()) {
            if (!key.equals("corrections")) {
                throw new InputFormatException(file, 0, "unknown top-level key '" + key + "'");
            }
        }
        JsonNode entries = root.get("corrections");
        if (entries == null || !entries.isArray()) {
            throw new InputFormatException(file, 0, "'corrections' must be a list (it may be empty)");
        }
        List<NcertCorrection> corrections = new ArrayList<>();
        int number = 0;
        for (JsonNode entry : entries) {
            corrections.add(entry(file, entry, ++number));
        }
        return List.copyOf(corrections);
    }

    private static NcertCorrection entry(Path file, JsonNode entry, int number) {
        String numbered = "correction " + number;
        if (!entry.isObject()) {
            throw new InputFormatException(file, 0, numbered + ": must be a mapping");
        }
        for (String key : entry.propertyNames()) {
            if (!COMMON_KEYS.contains(key) && !KIND_SPECIFIC.contains(key)) {
                throw new InputFormatException(file, 0, numbered + ": unknown key '" + key + "'");
            }
        }
        NcertCorrection.Kind kind = Enums.parse(required(file, entry, "kind", numbered), NcertCorrection.Kind.class,
                () -> new InputFormatException(file, 0, numbered + ": kind must be one of "
                        + Arrays.toString(NcertCorrection.Kind.values())));
        String label = numbered + " (" + kind + ")";
        for (String key : KIND_SPECIFIC) {
            if (entry.has(key) && !KIND_KEYS.get(kind).contains(key)) {
                throw new InputFormatException(file, 0, label + ": '" + key + "' does not belong to this kind");
            }
        }
        BookLanguage language = Enums.parse(required(file, entry, "lang", label), BookLanguage.class,
                () -> new InputFormatException(file, 0, label + ": lang must be one of "
                        + Arrays.toString(BookLanguage.values())));
        boolean spans = KIND_KEYS.get(kind).contains("printed");
        return new NcertCorrection(
                required(file, entry, "book", label),
                language,
                (short) whole(file, entry, "chapter", 1, 99, label),
                whole(file, entry, "page", 1, 999, label),
                kind,
                spans ? required(file, entry, "transcribed", label) : null,
                spans ? required(file, entry, "printed", label) : null,
                spans ? null : required(file, entry, "at", label),
                required(file, entry, "reason", label),
                optional(file, entry, "address", label));
    }

    /**
     * A span is kept exactly as written — its spaces are part of what must be found — so only an
     * all-blank value counts as missing.
     */
    private static String required(Path file, JsonNode node, String key, String label) {
        String value = optional(file, node, key, label);
        if (value == null) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' is required");
        }
        return value;
    }

    private static String optional(Path file, JsonNode node, String key, String label) {
        JsonNode value = node.get(key);
        if (value == null || value.isNull()) {
            return null;
        }
        if (!value.isString() && !value.isNumber()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be a string");
        }
        String text = value.isString() ? value.stringValue() : value.asString();
        return text.isBlank() ? null : text;
    }

    private static int whole(Path file, JsonNode node, String key, int min, int max, String label) {
        JsonNode value = node.get(key);
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
