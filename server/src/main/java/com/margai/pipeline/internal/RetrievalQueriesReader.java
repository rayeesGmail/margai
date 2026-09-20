package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * {@code eval/retrieval-queries.json} (TECH_PLAN §6.3) into {@link RetrievalQuery}s: a
 * {@code book} the set is written against and a {@code queries} list, each with {@code id},
 * {@code language}, {@code text}, {@code expect} and {@code note}.
 *
 * <p>Unknown keys are refused, like every other founder-owned input reader here — a misspelt
 * {@code expect} would otherwise silently become a query with no expectation, which scores as a
 * miss and looks like a retrieval failure.
 */
final class RetrievalQueriesReader {

    static final Set<String> ROOT_KEYS = Set.of("book", "queries");
    static final Set<String> QUERY_KEYS = Set.of("id", "language", "text", "expect", "note");
    static final Set<String> ADDRESS_KEYS = Set.of("chapter", "section", "para");
    static final Set<String> LANGUAGES = Set.of("en", "hi");

    private static final JsonMapper JSON = JsonMapper.builder().build();

    private RetrievalQueriesReader() {
    }

    /** The queries, and the book code the file says they are written against. */
    record QuerySet(String book, List<RetrievalQuery> queries) {
    }

    static QuerySet read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = JSON.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        }
        refuseUnknown(file, root, ROOT_KEYS, "the file");
        String book = text(file, root, "book", "the file");
        JsonNode queries = root.path("queries");
        if (!queries.isArray() || queries.isEmpty()) {
            throw new InputFormatException(file, 0, "has no 'queries' list");
        }

        List<RetrievalQuery> read = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (JsonNode node : queries) {
            String id = text(file, node, "id", "a query");
            if (!ids.add(id)) {
                throw new InputFormatException(file, 0, "has two queries with id '" + id + "'");
            }
            refuseUnknown(file, node, QUERY_KEYS, "query '" + id + "'");
            String language = text(file, node, "language", "query '" + id + "'");
            if (!LANGUAGES.contains(language)) {
                throw new InputFormatException(file, 0,
                        "query '" + id + "' has language '" + language + "', not one of " + LANGUAGES);
            }
            read.add(new RetrievalQuery(id, language, text(file, node, "text", "query '" + id + "'"),
                    addresses(file, node, id), node.path("note").asString(null)));
        }
        return new QuerySet(book, read);
    }

    private static List<RetrievalQuery.Address> addresses(Path file, JsonNode query, String id) {
        JsonNode expect = query.path("expect");
        if (!expect.isArray() || expect.isEmpty()) {
            throw new InputFormatException(file, 0, "query '" + id + "' expects no paragraph — "
                    + "a query with nothing to reach scores as a miss and reads as a retrieval failure");
        }
        List<RetrievalQuery.Address> addresses = new ArrayList<>();
        for (JsonNode node : expect) {
            refuseUnknown(file, node, ADDRESS_KEYS, "an expectation of query '" + id + "'");
            if (!node.path("chapter").isNumber() || !node.path("para").isNumber()) {
                throw new InputFormatException(file, 0,
                        "an expectation of query '" + id + "' is missing a numeric 'chapter' or 'para'");
            }
            addresses.add(new RetrievalQuery.Address((short) node.path("chapter").asInt(),
                    text(file, node, "section", "an expectation of query '" + id + "'"),
                    (short) node.path("para").asInt()));
        }
        return addresses;
    }

    private static String text(Path file, JsonNode node, String field, String where) {
        String value = node.path(field).asString(null);
        if (value == null || value.isBlank()) {
            throw new InputFormatException(file, 0, where + " has no '" + field + "'");
        }
        return value;
    }

    private static void refuseUnknown(Path file, JsonNode node, Set<String> known, String where) {
        for (String name : node.propertyNames()) {
            if (!known.contains(name)) {
                throw new InputFormatException(file, 0,
                        where + " carries an unknown key '" + name + "'; known keys are " + known);
            }
        }
    }
}
