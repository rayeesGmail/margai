package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code pipeline/inputs/ncert-section-titles.yaml}: the printed title of each numbered section,
 * per book, for {@code ncert embed --context section} (D15 experiment).
 *
 * <p>It exists because the extraction deliberately throws the heading text away — v3's prompt says
 * the heading line <em>is</em> the section, never words in a paragraph — so {@code section} in the
 * database is the number {@code "5.3"} and nothing else. A number carries no meaning to an
 * embedding model, which is why prefixing needs this file rather than the column.
 *
 * <p>A section the file does not name falls back to its parent ({@code 5.11.2} → {@code 5.11} →
 * {@code 5}), because a sub-section is about its section's subject; a paragraph whose section has
 * no title anywhere is embedded bare, exactly as it is today.
 */
final class NcertSectionTitles {

    static final String FILE = "ncert-section-titles.yaml";

    private static final YAMLMapper YAML = YAMLMapper.builder().build();
    private static final Pattern SECTION = Pattern.compile("\\d{1,2}(\\.\\d{1,2})*");

    private final Map<String, String> titles;

    private NcertSectionTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    /** An empty set: every paragraph embeds bare. What {@code --context none} uses. */
    static NcertSectionTitles none() {
        return new NcertSectionTitles(Map.of());
    }

    static NcertSectionTitles read(Path file, String bookCode) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        }
        JsonNode book = root.path("books").path(bookCode);
        if (book.isMissingNode() || book.isNull()) {
            throw new InputFormatException(file, 0, "carries no section titles for book '" + bookCode
                    + "' — harvest them, or run without --context section");
        }
        Map<String, String> titles = new LinkedHashMap<>();
        for (String section : book.propertyNames()) {
            if (!SECTION.matcher(section).matches()) {
                throw new InputFormatException(file, 0,
                        "'" + section + "' is not a section number (book '" + bookCode + "')");
            }
            String title = book.path(section).asString("").strip();
            if (title.isEmpty()) {
                throw new InputFormatException(file, 0, "section '" + section + "' has no title");
            }
            titles.put(section, title);
        }
        return new NcertSectionTitles(titles);
    }

    /** The section's own title, else its nearest parent's, else null. */
    String titleFor(String section) {
        for (String key = section; key != null; key = parentOf(key)) {
            String title = titles.get(key);
            if (title != null) {
                return title;
            }
        }
        return null;
    }

    /**
     * What actually gets embedded. The title is joined to the text with a separator rather than
     * run into it, so the model reads a heading followed by a paragraph and not one mangled
     * sentence — "5.3 Work · (iii) the force and displacement are mutually perpendicular…".
     */
    String embeddingInput(String section, String text) {
        String title = titleFor(section);
        return title == null ? text : section + " " + title + " · " + text;
    }

    int size() {
        return titles.size();
    }

    private static String parentOf(String section) {
        int dot = section.lastIndexOf('.');
        return dot < 0 ? null : section.substring(0, dot);
    }
}
