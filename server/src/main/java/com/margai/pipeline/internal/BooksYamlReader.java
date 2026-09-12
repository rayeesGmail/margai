package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.NcertBookRow;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code books.yaml} (TECH_PLAN §6.2) into {@link BookDefinition}s: a {@code books} list, each
 * book with {@code code, subject, class_level, part, title_en, title_hi, edition_year, source,
 * chapters}, each chapter with {@code no, en, hi}. Unknown keys are refused, as in
 * {@link ArchetypesYamlReader} — a misspelt key would otherwise be silently ignored — and so are
 * a repeated book code, a repeated or non-ascending chapter number, and a source prefix that does
 * not end in {@code /} (it is concatenated with a file name, and a missing slash would silently
 * address a neighbouring key).
 */
final class BooksYamlReader {

    static final Set<String> BOOK_KEYS = Set.of("code", "subject", "class_level", "part",
            "title_en", "title_hi", "edition_year", "source", "chapters");
    static final Set<String> SOURCE_KEYS = Set.of("en", "hi");
    static final Set<String> CHAPTER_KEYS = Set.of("no", "en", "hi");
    static final int CODE_MAX_LENGTH = 16;
    static final int TITLE_MAX_LENGTH = 160;
    static final int KEY_MAX_LENGTH = 256;

    private static final Pattern CODE = Pattern.compile("[a-z0-9][a-z0-9-]*");
    private static final Pattern FILE = Pattern.compile("[a-z0-9][a-z0-9._-]*\\.pdf");
    private static final YAMLMapper YAML = YAMLMapper.builder().build();

    private BooksYamlReader() {
    }

    static List<BookDefinition> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InputFormatException(file, 0, "is not valid YAML: " + e.getMessage());
        }
        if (root == null || !root.isObject()) {
            throw new InputFormatException(file, 0, "must be a mapping with a 'books' list");
        }
        for (String key : names(root)) {
            if (!key.equals("books")) {
                throw new InputFormatException(file, 0, "unknown top-level key '" + key + "'");
            }
        }
        JsonNode books = root.get("books");
        if (books == null || !books.isArray() || books.isEmpty()) {
            throw new InputFormatException(file, 0, "'books' must be a non-empty list");
        }
        List<BookDefinition> definitions = new ArrayList<>();
        Set<String> codes = new HashSet<>();
        for (JsonNode book : books) {
            BookDefinition definition = book(file, book);
            if (!codes.add(definition.code())) {
                throw new InputFormatException(file, 0, "book '" + definition.code() + "' appears twice");
            }
            definitions.add(definition);
        }
        return definitions;
    }

    private static BookDefinition book(Path file, JsonNode book) {
        if (!book.isObject()) {
            throw new InputFormatException(file, 0, "every book must be a mapping");
        }
        String label = "book " + (book.get("code") == null ? "?" : book.get("code").asString());
        for (String key : names(book)) {
            if (!BOOK_KEYS.contains(key)) {
                throw new InputFormatException(file, 0, label + ": unknown key '" + key + "'");
            }
        }
        String code = requiredString(file, book, "code", label);
        if (!CODE.matcher(code).matches() || code.length() > CODE_MAX_LENGTH) {
            throw new InputFormatException(file, 0,
                    label + ": code '" + code + "' must be lowercase letters, digits and hyphens, at most "
                            + CODE_MAX_LENGTH + " characters");
        }
        BookSubject subject = Enums.parse(requiredString(file, book, "subject", label), BookSubject.class,
                () -> new InputFormatException(file, 0, label + ": subject must be one of "
                        + Arrays.toString(BookSubject.values())));
        short classLevel = (short) requiredInt(file, book, "class_level", 11, 12, label);
        Short part = book.get("part") == null ? null : (short) requiredInt(file, book, "part", 1, 4, label);
        String titleEn = string(file, requiredString(file, book, "title_en", label), TITLE_MAX_LENGTH, label, "title_en");
        String titleHi = string(file, optionalString(file, book, "title_hi", label), TITLE_MAX_LENGTH, label, "title_hi");
        short editionYear = (short) requiredInt(file, book, "edition_year", 2000, 2100, label);

        JsonNode source = book.get("source");
        if (source == null || !source.isObject()) {
            throw new InputFormatException(file, 0, label + ": 'source' must be a mapping of en and hi prefixes");
        }
        for (String key : names(source)) {
            if (!SOURCE_KEYS.contains(key)) {
                throw new InputFormatException(file, 0, label + ": unknown source key '" + key + "'");
            }
        }
        String sourceEn = prefix(file, optionalString(file, source, "en", label), label, "en");
        String sourceHi = prefix(file, optionalString(file, source, "hi", label), label, "hi");
        if (sourceEn == null && sourceHi == null) {
            throw new InputFormatException(file, 0, label + ": 'source' must give at least one edition");
        }

        JsonNode chapters = book.get("chapters");
        if (chapters == null || !chapters.isArray() || chapters.isEmpty()) {
            throw new InputFormatException(file, 0, label + ": 'chapters' must be a non-empty list");
        }
        List<BookDefinition.Chapter> chapterList = new ArrayList<>();
        short previous = 0;
        for (JsonNode node : chapters) {
            BookDefinition.Chapter chapter = chapter(file, node, label, sourceEn != null, sourceHi != null);
            if (chapter.no() <= previous) {
                throw new InputFormatException(file, 0, label + ": chapter " + chapter.no()
                        + " does not follow chapter " + previous + " — chapters must ascend and appear once");
            }
            previous = chapter.no();
            chapterList.add(chapter);
        }
        return new BookDefinition(new NcertBookRow(code, subject, classLevel, part, titleEn, titleHi,
                editionYear, sourceEn, sourceHi), chapterList);
    }

    private static BookDefinition.Chapter chapter(Path file, JsonNode node, String book,
            boolean needsEn, boolean needsHi) {
        if (!node.isObject()) {
            throw new InputFormatException(file, 0, book + ": every chapter must be a mapping");
        }
        for (String key : names(node)) {
            if (!CHAPTER_KEYS.contains(key)) {
                throw new InputFormatException(file, 0, book + ": unknown chapter key '" + key + "'");
            }
        }
        short no = (short) requiredInt(file, node, "no", 1, 99, book);
        String label = book + " chapter " + no;
        String en = fileName(file, needsEn ? requiredString(file, node, "en", label)
                : optionalString(file, node, "en", label), label, "en");
        String hi = fileName(file, needsHi ? requiredString(file, node, "hi", label)
                : optionalString(file, node, "hi", label), label, "hi");
        return new BookDefinition.Chapter(no, en, hi);
    }

    private static String prefix(Path file, String value, String label, String key) {
        if (value == null) {
            return null;
        }
        if (!value.endsWith("/")) {
            throw new InputFormatException(file, 0,
                    label + ": source '" + key + "' must end in '/' — it is a prefix, not an object key");
        }
        return string(file, value, KEY_MAX_LENGTH, label, "source " + key);
    }

    private static String fileName(Path file, String value, String label, String key) {
        if (value == null) {
            return null;
        }
        if (!FILE.matcher(value).matches()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be a .pdf file name, not '" + value + "'");
        }
        return value;
    }

    private static String string(Path file, String value, int maxLength, String label, String key) {
        if (value != null && value.length() > maxLength) {
            throw new InputFormatException(file, 0, label + ": " + key + " is longer than " + maxLength);
        }
        return value;
    }

    private static String requiredString(Path file, JsonNode node, String key, String label) {
        String value = optionalString(file, node, key, label);
        if (value == null) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' is required");
        }
        return value;
    }

    private static String optionalString(Path file, JsonNode node, String key, String label) {
        JsonNode value = node.get(key);
        if (value == null || value.isNull()) {
            return null;
        }
        if (!value.isString()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be a string");
        }
        String text = value.stringValue().trim();
        return text.isEmpty() ? null : text;
    }

    private static int requiredInt(Path file, JsonNode node, String key, int min, int max, String label) {
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

    private static List<String> names(JsonNode object) {
        List<String> names = new ArrayList<>();
        object.propertyNames().forEach(names::add);
        return names;
    }
}
