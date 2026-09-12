package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * RFC 4180 reading of one founder-owned CSV (TECH_PLAN §6.2) with the header checked against the
 * contract and every field parsed with the file and line in the error. Values are trimmed; an
 * empty field is the file's way of saying null.
 */
final class CsvInput {

    /** {@code syllabus_nodes.code}: dotted upper-case segments, at most 32 characters (TECH_PLAN §2.3). */
    static final Pattern CODE = Pattern.compile("[A-Z0-9]+(\\.[A-Z0-9]+)*");
    static final int CODE_MAX_LENGTH = 32;

    private CsvInput() {
    }

    /** Every data row of the file, or an {@link InputFormatException} naming what is wrong. */
    static List<Row> read(Path file, String... expectedHeader) {
        CSVFormat format = CSVFormat.RFC4180.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .setIgnoreEmptyLines(true)
                .build();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
                CSVParser parser = format.parse(reader)) {
            List<String> header = parser.getHeaderNames();
            if (!header.equals(Arrays.asList(expectedHeader))) {
                throw new InputFormatException(file, 1,
                        "header must be " + String.join(",", expectedHeader) + " but is " + String.join(",", header));
            }
            List<Row> rows = new ArrayList<>();
            for (CSVRecord record : parser) {
                if (!record.isConsistent()) {
                    throw new InputFormatException(file, record.getRecordNumber() + 1,
                            "expected " + expectedHeader.length + " fields, found " + record.size());
                }
                rows.add(new Row(file, record.getRecordNumber() + 1, record));
            }
            return rows;
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        }
    }

    /** One data row: typed, validated access to its fields; the header is line 1, so the first row is line 2. */
    static final class Row {

        private final Path file;
        private final long line;
        private final CSVRecord record;

        Row(Path file, long line, CSVRecord record) {
            this.file = file;
            this.line = line;
            this.record = record;
        }

        long line() {
            return line;
        }

        /** The trimmed value, null when empty. */
        String optional(String column) {
            String value = record.get(column);
            return value == null || value.isEmpty() ? null : value;
        }

        String optional(String column, int maxLength) {
            String value = optional(column);
            if (value != null && value.length() > maxLength) {
                throw error(column + " is longer than " + maxLength + " characters");
            }
            return value;
        }

        String required(String column) {
            String value = optional(column);
            if (value == null) {
                throw error(column + " is required");
            }
            return value;
        }

        String required(String column, int maxLength) {
            String value = required(column);
            if (value.length() > maxLength) {
                throw error(column + " is longer than " + maxLength + " characters");
            }
            return value;
        }

        String code(String column) {
            String value = required(column);
            if (!CODE.matcher(value).matches() || value.length() > CODE_MAX_LENGTH) {
                throw error(column + " '" + value + "' is not a node code (upper-case dotted segments, at most "
                        + CODE_MAX_LENGTH + " characters)");
            }
            return value;
        }

        String optionalCode(String column) {
            return optional(column) == null ? null : code(column);
        }

        int requiredInt(String column, int min) {
            return parseInt(column, required(column), min);
        }

        Integer optionalInt(String column, int min) {
            String value = optional(column);
            return value == null ? null : parseInt(column, value, min);
        }

        short requiredShort(String column, int min, int max) {
            int value = parseInt(column, required(column), min);
            if (value > max) {
                throw error(column + " must be at most " + max);
            }
            return (short) value;
        }

        Short optionalShort(String column, int min, int max) {
            return optional(column) == null ? null : requiredShort(column, min, max);
        }

        boolean requiredBoolean(String column) {
            String value = required(column);
            return switch (value) {
                case "true" -> true;
                case "false" -> false;
                default -> throw error(column + " must be true or false, found '" + value + "'");
            };
        }

        <E extends Enum<E>> E requiredEnum(String column, Class<E> type) {
            return Enums.parse(required(column), type, () -> error(column + " must be one of "
                    + Arrays.toString(type.getEnumConstants()) + ", found '" + optional(column) + "'"));
        }

        InputFormatException error(String message) {
            return new InputFormatException(file, line, message);
        }

        private int parseInt(String column, String value, int min) {
            int parsed;
            try {
                parsed = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw error(column + " must be a whole number, found '" + value + "'");
            }
            if (parsed < min) {
                throw error(column + " must be at least " + min);
            }
            return parsed;
        }
    }
}
