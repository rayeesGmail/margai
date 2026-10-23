package com.margai.pipeline.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code pyq-tag-rulings.yaml}, the founder's rulings on PYQ tags (D19; DECISIONS 2026-10-21), into each paper's
 * {@link PyqTagRuling}s by question. Founder-owned, so strict as {@link PyqPapersYamlReader}: an unknown key, a
 * paper listed twice or not in the register, a question ruled twice, more than two secondary codes, a blank primary,
 * ruling date or note, a one-word anchor other than the marks {@code none} and {@code pending} (DECISIONS
 * 2026-10-23), or a ruling for a question the paper does not have is refused, naming the file. Each paper's
 * rulings name the sha256 of the approved paper file they were made against, and are refused once the file has
 * changed, until they are confirmed against it again — a corrected question's ruling is never applied unread.
 * Whether each code is a node of the right kind and subject is the curriculum module's check, inside
 * {@code pyq load}'s transaction, as for a model's tag.
 */
final class PyqTagRulingsYamlReader {

    static final String FILE = "pyq-tag-rulings.yaml";

    static final Set<String> PAPER_KEYS = Set.of("code", "approved_file_sha256", "rulings");
    static final Set<String> RULING_KEYS = Set.of("question", "primary", "secondary", "out_of_syllabus", "anchor", "gap",
            "ruled", "note");

    private static final YAMLMapper YAML = YAMLMapper.builder().build();
    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

    private PyqTagRulingsYamlReader() {
    }

    /** A paper's rulings by question, and the sha256 of the approved file they were made against. */
    record PaperRulings(String approvedFileSha256, Map<Short, PyqTagRuling> byQuestion) {
    }

    /**
     * One paper's rulings by question, empty when the file rules on none of its questions; refused when the file
     * names a paper the register does not, or when the paper's approved file is not the one its rulings were made
     * against.
     */
    static Map<Short, PyqTagRuling> forPaper(Path file, PaperDefinition paper, Set<String> registered, Path approvedFile) {
        Map<String, PaperRulings> all = read(file);
        for (String code : all.keySet()) {
            if (!registered.contains(code)) {
                throw new InputFormatException(file, 0, "paper '" + code + "' is not in the register (" + PyqPaperCommand.FILE
                        + ") — a mistyped code would leave its rulings unapplied");
            }
        }
        PaperRulings rulings = all.get(paper.code());
        if (rulings == null) {
            return Map.of();
        }
        String now = Report.sha256(approvedFile);
        if (!now.equals(rulings.approvedFileSha256())) {
            throw new InputFormatException(file, 0, "paper " + paper.code() + ": its rulings were made against the approved "
                    + "file sha256 " + rulings.approvedFileSha256() + ", but " + approvedFile.getFileName() + " is now " + now
                    + " — confirm each ruling against the changed file, then set approved_file_sha256 to " + now);
        }
        for (short question : rulings.byQuestion().keySet()) {
            if (question > paper.row().questionCount()) {
                throw new InputFormatException(file, 0, "paper " + paper.code() + ": question " + question
                        + " is ruled, but the paper has " + paper.row().questionCount() + " questions");
            }
        }
        return rulings.byQuestion();
    }

    /** The rulings across every paper that mark "no NCERT anchor" — the numerator of the founder's ~2% line. */
    static long noNcertAnchorCount(Path file) {
        return read(file).values().stream().flatMap(paper -> paper.byQuestion().values().stream())
                .filter(PyqTagRuling::noNcertAnchor).count();
    }

    static Map<String, PaperRulings> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InputFormatException(file, 0, "is not valid YAML: " + e.getMessage());
        }
        if (root == null || !root.isObject()) {
            throw new InputFormatException(file, 0, "must be a mapping with a 'papers' list");
        }
        for (String key : names(root)) {
            if (!key.equals("papers")) {
                throw new InputFormatException(file, 0, "unknown top-level key '" + key + "'");
            }
        }
        JsonNode papers = root.get("papers");
        if (papers == null || !papers.isArray()) {
            throw new InputFormatException(file, 0, "'papers' must be a list");
        }
        Map<String, PaperRulings> byPaper = new HashMap<>();
        for (JsonNode paper : papers) {
            if (!paper.isObject()) {
                throw new InputFormatException(file, 0, "every paper must be a mapping");
            }
            String label = "paper " + (paper.get("code") == null ? "?" : paper.get("code").asString());
            unknownKeys(file, paper, PAPER_KEYS, label, "");
            String code = requiredString(file, paper, "code", label);
            if (byPaper.containsKey(code)) {
                throw new InputFormatException(file, 0, label + " appears twice");
            }
            String sha256 = requiredString(file, paper, "approved_file_sha256", label);
            if (!SHA256.matcher(sha256).matches()) {
                throw new InputFormatException(file, 0, label + ": approved_file_sha256 must be 64 lowercase hex characters");
            }
            JsonNode rulings = paper.get("rulings");
            if (rulings == null || !rulings.isArray() || rulings.isEmpty()) {
                throw new InputFormatException(file, 0, label + ": 'rulings' must be a non-empty list");
            }
            Map<Short, PyqTagRuling> byQuestion = new TreeMap<>();
            for (JsonNode ruling : rulings) {
                PyqTagRuling read = ruling(file, ruling, label);
                if (byQuestion.put(read.question(), read) != null) {
                    throw new InputFormatException(file, 0, label + ": question " + read.question() + " is ruled twice");
                }
            }
            byPaper.put(code, new PaperRulings(sha256, byQuestion));
        }
        return byPaper;
    }

    private static PyqTagRuling ruling(Path file, JsonNode ruling, String paper) {
        if (!ruling.isObject()) {
            throw new InputFormatException(file, 0, paper + ": every ruling must be a mapping");
        }
        JsonNode number = ruling.get("question");
        if (number == null || !number.isIntegralNumber() || number.intValue() < 1 || number.intValue() > 300) {
            throw new InputFormatException(file, 0, paper + ": a ruling's 'question' must be a question number");
        }
        String label = paper + " question " + number.intValue();
        unknownKeys(file, ruling, RULING_KEYS, label, "");
        List<String> secondary = new ArrayList<>();
        JsonNode codes = ruling.get("secondary");
        if (codes != null && !codes.isNull()) {
            if (!codes.isArray()) {
                throw new InputFormatException(file, 0, label + ": 'secondary' must be a list of codes");
            }
            for (JsonNode code : codes) {
                if (!code.isString() || code.asString().isBlank()) {
                    throw new InputFormatException(file, 0, label + ": every secondary must be a code");
                }
                secondary.add(code.asString().strip());
            }
            if (secondary.size() > 2) {
                throw new InputFormatException(file, 0, label + ": " + secondary.size() + " secondary codes; at most two");
            }
        }
        boolean outOfSyllabus = false;
        JsonNode out = ruling.get("out_of_syllabus");
        if (out != null && !out.isNull()) {
            if (!out.isBoolean()) {
                throw new InputFormatException(file, 0, label + ": 'out_of_syllabus' must be true or false");
            }
            outOfSyllabus = out.booleanValue();
        }
        String ruled = requiredString(file, ruling, "ruled", label);
        LocalDate date;
        try {
            date = LocalDate.parse(ruled);
        } catch (DateTimeParseException e) {
            throw new InputFormatException(file, 0, label + ": ruled '" + ruled + "' must be a date like 2026-10-21");
        }
        String anchor = optionalString(file, ruling, "anchor", label);
        // A place in NCERT is words ("bio12 ch 10 §10.1 ¶4"); a single word is one of the two marks, or a slip.
        if (anchor != null && !anchor.contains(" ") && !anchor.equals(PyqTagRuling.NO_NCERT_ANCHOR)
                && !anchor.equals(PyqTagRuling.NOT_YET_IN_CORPUS)) {
            throw new InputFormatException(file, 0, label + ": anchor '" + anchor + "' is neither a place in NCERT nor '"
                    + PyqTagRuling.NO_NCERT_ANCHOR + "' or '" + PyqTagRuling.NOT_YET_IN_CORPUS + "'");
        }
        return new PyqTagRuling((short) number.intValue(), requiredString(file, ruling, "primary", label), secondary,
                outOfSyllabus, anchor, optionalString(file, ruling, "gap", label), date,
                requiredString(file, ruling, "note", label));
    }

    private static void unknownKeys(Path file, JsonNode node, Set<String> allowed, String label, String what) {
        for (String key : names(node)) {
            if (!allowed.contains(key)) {
                throw new InputFormatException(file, 0, label + ": unknown " + what + "key '" + key + "'");
            }
        }
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
        if (!value.isValueNode() || value.isBoolean()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be text");
        }
        String text = value.asString().strip();
        return text.isEmpty() ? null : text;
    }

    private static List<String> names(JsonNode object) {
        List<String> names = new ArrayList<>();
        object.propertyNames().forEach(names::add);
        return names;
    }
}
