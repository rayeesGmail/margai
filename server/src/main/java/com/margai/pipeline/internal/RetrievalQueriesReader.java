package com.margai.pipeline.internal;

import com.margai.curriculum.api.BookSubject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * {@code eval/retrieval-queries.json} (TECH_PLAN §6.3) into {@link RetrievalQuery}s: a
 * {@code queries} list, each with {@code id}, {@code set}, {@code subject}, {@code language},
 * {@code text}, {@code expect} and {@code note}, every expected address naming its {@code book}.
 * Since D17 the file covers the whole corpus; at D15 it named one book at its root, and that key
 * is refused now rather than ignored.
 *
 * <p>Unknown keys are refused, like every other founder-owned input reader here — a misspelt
 * {@code expect} would otherwise silently become a query with no expectation, which scores as a
 * miss and looks like a retrieval failure.
 */
final class RetrievalQueriesReader {

    static final Set<String> ROOT_KEYS = Set.of("queries");
    static final Set<String> QUERY_KEYS = Set.of("id", "set", "subject", "language", "text", "expect", "note");
    static final Set<String> ADDRESS_KEYS = Set.of("book", "chapter", "section", "para");
    static final Set<String> LANGUAGES = Set.of("en", "hi", "hinglish");

    private static final JsonMapper JSON = JsonMapper.builder().build();

    private RetrievalQueriesReader() {
    }

    static List<RetrievalQuery> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = JSON.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        }
        refuseUnknown(file, root, ROOT_KEYS, "the file");
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
            String where = "query '" + id + "'";
            refuseUnknown(file, node, QUERY_KEYS, where);
            String set = text(file, node, "set", where);
            RetrievalQuery.QuerySet querySet = Enums.parse(set, RetrievalQuery.QuerySet.class,
                    () -> new InputFormatException(file, 0, where + " has set '" + set + "', not one of "
                            + Arrays.toString(RetrievalQuery.QuerySet.values())));
            String subject = text(file, node, "subject", where);
            BookSubject bookSubject = Enums.parse(subject, BookSubject.class,
                    () -> new InputFormatException(file, 0, where + " has subject '" + subject + "', not one of "
                            + Arrays.toString(BookSubject.values()) + " — retrieval filters on the book's subject"));
            String language = text(file, node, "language", where);
            if (!LANGUAGES.contains(language)) {
                throw new InputFormatException(file, 0,
                        where + " has language '" + language + "', not one of " + LANGUAGES);
            }
            read.add(new RetrievalQuery(id, querySet, bookSubject, language, text(file, node, "text", where),
                    addresses(file, node, id), node.path("note").asString(null)));
        }
        return read;
    }

    /**
     * Every expected address is in a book the list holds, of the query's own subject, in a chapter
     * that book prints. Retrieval is filtered to the query's subject, so an address outside it is a
     * miss retrieval was never allowed to avoid — refused here rather than scored there.
     */
    static void checkBooks(Path file, List<RetrievalQuery> queries, List<BookDefinition> books) {
        Map<String, BookDefinition> byCode = books.stream()
                .collect(Collectors.toMap(BookDefinition::code, Function.identity()));
        for (RetrievalQuery query : queries) {
            for (RetrievalQuery.Address address : query.expect()) {
                String where = "query '" + query.id() + "' expects " + address;
                BookDefinition book = byCode.get(address.book());
                if (book == null) {
                    throw new InputFormatException(file, 0, where + ", but book '" + address.book()
                            + "' is not in " + NcertRegisterCommand.FILE);
                }
                if (book.row().subject() != query.subject()) {
                    throw new InputFormatException(file, 0, where + ", but " + address.book() + " is "
                            + book.row().subject() + " and the query is filtered to " + query.subject());
                }
                if (book.chapters().stream().noneMatch(chapter -> chapter.no() == address.chapter())) {
                    throw new InputFormatException(file, 0, where + ", but " + address.book()
                            + " has no chapter " + address.chapter());
                }
            }
        }
    }

    private static List<RetrievalQuery.Address> addresses(Path file, JsonNode query, String id) {
        JsonNode expect = query.path("expect");
        if (!expect.isArray() || expect.isEmpty()) {
            throw new InputFormatException(file, 0, "query '" + id + "' expects no paragraph — "
                    + "a query with nothing to reach scores as a miss and reads as a retrieval failure");
        }
        List<RetrievalQuery.Address> addresses = new ArrayList<>();
        for (JsonNode node : expect) {
            String where = "an expectation of query '" + id + "'";
            refuseUnknown(file, node, ADDRESS_KEYS, where);
            if (!node.path("chapter").isNumber() || !node.path("para").isNumber()) {
                throw new InputFormatException(file, 0, where + " is missing a numeric 'chapter' or 'para'");
            }
            addresses.add(new RetrievalQuery.Address(text(file, node, "book", where),
                    (short) node.path("chapter").asInt(), text(file, node, "section", where),
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
