package com.margai.pipeline.internal;

import com.margai.curriculum.api.KeyProvenance;
import com.margai.curriculum.api.PaperMarking;
import com.margai.curriculum.api.PaperSection;
import com.margai.curriculum.api.PaperSourceFile;
import com.margai.curriculum.api.PaperSubject;
import com.margai.curriculum.api.PyqPaperRow;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code pyq-papers.yaml}, the founder-owned register of official papers (D19; README §1, §2,
 * MANIFEST.csv), into {@link PaperDefinition}s. Each paper names its booklet file(s), the pages
 * read, the sections the booklet prints, its marking, and its key's provenance with the code's
 * special entries.
 *
 * <p>What it refuses, besides unknown keys (as {@link BooksYamlReader}): sections that do not run
 * from question 1 to the declared count without a gap or an overlap — the declared count and the
 * sections are the two halves of the D19 ✅, so they must agree before anything is counted; a
 * file outside the founder's pack ({@value #PACK}) or inside its reference-only folder, or without
 * a 64-character sha256; a page listed twice or out of order; a key tier
 * outside the founder's three; half-sourced special-answer rules (all three or none); a special
 * entry outside the paper, listed twice, or neither dropped nor two to four distinct keys; and
 * {@code official_hindi} that disagrees with the booklets (true exactly when one is bilingual).
 */
final class PyqPapersYamlReader {

    static final String ENGLISH = "en";
    static final String BILINGUAL = "hi-en";

    static final Set<String> PAPER_KEYS = Set.of("code", "exam", "year", "paper_code", "exam_date",
            "official_hindi", "question_count", "booklets", "sections", "marking", "key");
    static final Set<String> BOOKLET_KEYS = Set.of("file", "sha256", "languages", "pages");
    static final Set<String> SECTION_KEYS = Set.of("subject", "section", "from", "to", "attempt");
    static final Set<String> MARKING_KEYS = Set.of("correct", "wrong", "unanswered", "scheme_source",
            "dropped_rule", "multiple_rule", "rules_source");
    static final Set<String> KEY_KEYS = Set.of("file", "sha256", "tier", "from", "downloaded", "special");
    static final Set<String> SPECIAL_KEYS = Set.of("question", "dropped", "accepted");
    static final Set<String> EXAMS = Set.of("NEET", "AIPMT");
    static final Set<String> TIERS = Set.of("official", "archived-official", "mirrored-official");
    static final Set<String> OPTION_KEYS = Set.of("1", "2", "3", "4");
    static final int CODE_MAX_LENGTH = 32;
    static final int PAPER_CODE_MAX_LENGTH = 16;

    /** The founder's pack in the content bucket (DECISIONS 2026-10-20): the only home of a booklet or a key. */
    static final String PACK = "source/neet-pyq-sources/";
    /** The pack's coaching retype, never loaded and never a source (README §5). */
    static final String REFERENCE_ONLY = "reference-only-do-not-load/";

    private static final Pattern CODE = Pattern.compile("[a-z0-9][a-z0-9-]*");
    private static final Pattern SOURCE_KEY = Pattern.compile(Pattern.quote(PACK) + "[A-Za-z0-9._/-]+\\.pdf");
    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");
    private static final Pattern PAGE_RANGE = Pattern.compile("(\\d+)(?:-(\\d+))?");
    private static final Pattern SECTION_LETTER = Pattern.compile("[A-Z]");
    private static final YAMLMapper YAML = YAMLMapper.builder().build();

    private PyqPapersYamlReader() {
    }

    static List<PaperDefinition> read(Path file) {
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
        if (papers == null || !papers.isArray() || papers.isEmpty()) {
            throw new InputFormatException(file, 0, "'papers' must be a non-empty list");
        }
        List<PaperDefinition> definitions = new ArrayList<>();
        Set<String> codes = new HashSet<>();
        Set<String> identities = new HashSet<>();
        for (JsonNode paper : papers) {
            PaperDefinition definition = paper(file, paper);
            PyqPaperRow row = definition.row();
            if (!codes.add(row.code())) {
                throw new InputFormatException(file, 0, "paper '" + row.code() + "' appears twice");
            }
            if (!identities.add(row.exam() + "/" + row.year() + "/" + row.paperCode())) {
                throw new InputFormatException(file, 0, "paper '" + row.code() + "': " + row.exam() + " " + row.year()
                        + " code " + row.paperCode() + " is already registered under another id");
            }
            definitions.add(definition);
        }
        return definitions;
    }

    private static PaperDefinition paper(Path file, JsonNode paper) {
        if (!paper.isObject()) {
            throw new InputFormatException(file, 0, "every paper must be a mapping");
        }
        String label = "paper " + (paper.get("code") == null ? "?" : paper.get("code").asString());
        unknownKeys(file, paper, PAPER_KEYS, label, "");
        String code = requiredString(file, paper, "code", label);
        if (!CODE.matcher(code).matches() || code.length() > CODE_MAX_LENGTH) {
            throw new InputFormatException(file, 0, label + ": code must be lowercase letters, digits and hyphens, at most "
                    + CODE_MAX_LENGTH + " characters");
        }
        String exam = requiredString(file, paper, "exam", label);
        if (!EXAMS.contains(exam)) {
            throw new InputFormatException(file, 0, label + ": exam must be one of " + new TreeSet<>(EXAMS));
        }
        short year = (short) requiredInt(file, paper, "year", 2000, 2100, label);
        String paperCode = requiredString(file, paper, "paper_code", label);
        if (paperCode.length() > PAPER_CODE_MAX_LENGTH) {
            throw new InputFormatException(file, 0, label + ": paper_code is longer than " + PAPER_CODE_MAX_LENGTH);
        }
        LocalDate examDate = date(file, requiredString(file, paper, "exam_date", label), label, "exam_date");
        if (examDate.getYear() != year) {
            throw new InputFormatException(file, 0, label + ": exam_date " + examDate + " is not in " + year);
        }
        boolean officialHindi = requiredBoolean(file, paper, "official_hindi", label);
        short questionCount = (short) requiredInt(file, paper, "question_count", 1, 300, label);

        List<PaperSourceFile> booklets = booklets(file, paper.get("booklets"), label);
        boolean bilingual = booklets.stream().anyMatch(booklet -> booklet.languages().equals(BILINGUAL));
        if (officialHindi != bilingual) {
            throw new InputFormatException(file, 0, label + ": official_hindi is " + officialHindi
                    + " but " + (bilingual ? "a booklet is" : "no booklet is") + " " + BILINGUAL);
        }
        List<PaperSection> sections = sections(file, paper.get("sections"), questionCount, label);
        PaperMarking marking = marking(file, paper.get("marking"), label);
        JsonNode key = paper.get("key");
        KeyProvenance provenance = key(file, key, label);
        List<PaperDefinition.KeySpecial> specials = specials(file, key.get("special"), questionCount, label);
        return new PaperDefinition(new PyqPaperRow(code, exam, year, paperCode, examDate, officialHindi, questionCount,
                sections, marking, provenance, booklets), specials);
    }

    private static List<PaperSourceFile> booklets(Path file, JsonNode node, String label) {
        if (node == null || !node.isArray() || node.isEmpty()) {
            throw new InputFormatException(file, 0, label + ": 'booklets' must be a non-empty list");
        }
        List<PaperSourceFile> booklets = new ArrayList<>();
        Set<String> languages = new HashSet<>();
        for (JsonNode booklet : node) {
            if (!booklet.isObject()) {
                throw new InputFormatException(file, 0, label + ": every booklet must be a mapping");
            }
            unknownKeys(file, booklet, BOOKLET_KEYS, label, "booklet ");
            String key = sourceKey(file, requiredString(file, booklet, "file", label), label, "booklet file");
            String sha256 = sha256(file, requiredString(file, booklet, "sha256", label), label, key);
            String language = requiredString(file, booklet, "languages", label);
            if (!language.equals(ENGLISH) && !language.equals(BILINGUAL)) {
                throw new InputFormatException(file, 0, label + ": booklet languages must be '" + ENGLISH + "' or '"
                        + BILINGUAL + "', not '" + language + "'");
            }
            if (!languages.add(language)) {
                throw new InputFormatException(file, 0, label + ": two booklets are '" + language + "'");
            }
            booklets.add(new PaperSourceFile(key, sha256, language, pages(file, booklet.get("pages"), label, key)));
        }
        return booklets;
    }

    /** {@code "3-46"} or {@code [3, "5-9"]}: 1-based pages, strictly ascending across the whole list. */
    static List<Integer> pages(Path file, JsonNode node, String label, String booklet) {
        List<JsonNode> parts = new ArrayList<>();
        if (node != null && node.isArray()) {
            node.forEach(parts::add);
        } else if (node != null && !node.isNull()) {
            parts.add(node);
        }
        if (parts.isEmpty()) {
            throw new InputFormatException(file, 0, label + ": " + booklet + " must list its pages");
        }
        Set<Integer> pages = new LinkedHashSet<>();
        int last = 0;
        for (JsonNode part : parts) {
            Matcher range = PAGE_RANGE.matcher(part.isIntegralNumber() ? String.valueOf(part.intValue()) : part.asString());
            if (!range.matches()) {
                throw new InputFormatException(file, 0, label + ": " + booklet + " page '" + part.asString()
                        + "' is not a page or a range like 3-46");
            }
            int from = Integer.parseInt(range.group(1));
            int to = range.group(2) == null ? from : Integer.parseInt(range.group(2));
            if (from < 1 || to < from) {
                throw new InputFormatException(file, 0, label + ": " + booklet + " pages " + part.asString()
                        + " must be ascending from page 1");
            }
            for (int page = from; page <= to; page++) {
                if (!pages.add(page)) {
                    throw new InputFormatException(file, 0, label + ": " + booklet + " lists page " + page + " twice");
                }
                if (page < last) {
                    throw new InputFormatException(file, 0, label + ": " + booklet + " lists page " + page
                            + " after page " + last + " — pages are read in booklet order");
                }
                last = page;
            }
        }
        return List.copyOf(pages);
    }

    private static List<PaperSection> sections(Path file, JsonNode node, short questionCount, String label) {
        if (node == null || !node.isArray() || node.isEmpty()) {
            throw new InputFormatException(file, 0, label + ": 'sections' must be a non-empty list");
        }
        List<PaperSection> sections = new ArrayList<>();
        int next = 1;
        for (JsonNode section : node) {
            if (!section.isObject()) {
                throw new InputFormatException(file, 0, label + ": every section must be a mapping");
            }
            unknownKeys(file, section, SECTION_KEYS, label, "section ");
            PaperSubject subject = Enums.parse(requiredString(file, section, "subject", label), PaperSubject.class,
                    () -> new InputFormatException(file, 0, label + ": section subject must be one of "
                            + Arrays.toString(PaperSubject.values())));
            String letter = optionalString(file, section, "section", label);
            if (letter != null && !SECTION_LETTER.matcher(letter).matches()) {
                throw new InputFormatException(file, 0, label + ": section must be one capital letter, not '" + letter + "'");
            }
            short from = (short) requiredInt(file, section, "from", 1, questionCount, label);
            short to = (short) requiredInt(file, section, "to", from, questionCount, label);
            if (from != next) {
                throw new InputFormatException(file, 0, label + ": the " + subject + " section starts at question " + from
                        + " but question " + next + " is the next one no section has covered — sections run from 1 to "
                        + questionCount + " in order, without a gap or an overlap");
            }
            int size = to - from + 1;
            short attempt = section.get("attempt") == null ? (short) size
                    : (short) requiredInt(file, section, "attempt", 1, size, label);
            sections.add(new PaperSection(subject, letter, from, to, attempt));
            next = to + 1;
        }
        if (next != questionCount + 1) {
            throw new InputFormatException(file, 0, label + ": the sections end at question " + (next - 1)
                    + " but the booklet declares " + questionCount + " questions");
        }
        return sections;
    }

    private static PaperMarking marking(Path file, JsonNode node, String label) {
        if (node == null || !node.isObject()) {
            throw new InputFormatException(file, 0, label + ": 'marking' must be a mapping");
        }
        unknownKeys(file, node, MARKING_KEYS, label, "marking ");
        int correct = requiredInt(file, node, "correct", 1, 10, label);
        int wrong = requiredInt(file, node, "wrong", -10, 0, label);
        int unanswered = requiredInt(file, node, "unanswered", -10, 10, label);
        String schemeSource = requiredString(file, node, "scheme_source", label);
        String dropped = optionalString(file, node, "dropped_rule", label);
        String multiple = optionalString(file, node, "multiple_rule", label);
        String rulesSource = optionalString(file, node, "rules_source", label);
        int given = (dropped == null ? 0 : 1) + (multiple == null ? 0 : 1) + (rulesSource == null ? 0 : 1);
        if (given != 0 && given != 3) {
            throw new InputFormatException(file, 0, label + ": dropped_rule, multiple_rule and rules_source are "
                    + "sourced together from the year's notice, or all left empty until it is found");
        }
        return new PaperMarking(correct, wrong, unanswered, schemeSource, dropped, multiple, rulesSource);
    }

    private static KeyProvenance key(Path file, JsonNode node, String label) {
        if (node == null || !node.isObject()) {
            throw new InputFormatException(file, 0, label + ": 'key' must be a mapping");
        }
        unknownKeys(file, node, KEY_KEYS, label, "key ");
        String key = sourceKey(file, requiredString(file, node, "file", label), label, "key file");
        String sha256 = sha256(file, requiredString(file, node, "sha256", label), label, key);
        String tier = requiredString(file, node, "tier", label);
        if (!TIERS.contains(tier)) {
            throw new InputFormatException(file, 0, label + ": key tier must be one of " + new TreeSet<>(TIERS)
                    + " — a publisher or coaching key is never a source (README §2)");
        }
        String from = requiredString(file, node, "from", label);
        LocalDate downloaded = date(file, requiredString(file, node, "downloaded", label), label, "key downloaded");
        return new KeyProvenance(tier, from, downloaded, key, sha256);
    }

    private static List<PaperDefinition.KeySpecial> specials(Path file, JsonNode node, short questionCount, String label) {
        if (node == null || node.isNull()) {
            return List.of();
        }
        if (!node.isArray()) {
            throw new InputFormatException(file, 0, label + ": key 'special' must be a list");
        }
        List<PaperDefinition.KeySpecial> specials = new ArrayList<>();
        Set<Short> questions = new HashSet<>();
        for (JsonNode special : node) {
            if (!special.isObject()) {
                throw new InputFormatException(file, 0, label + ": every special entry must be a mapping");
            }
            unknownKeys(file, special, SPECIAL_KEYS, label, "special ");
            short question = (short) requiredInt(file, special, "question", 1, questionCount, label);
            if (!questions.add(question)) {
                throw new InputFormatException(file, 0, label + ": question " + question + " has two special entries");
            }
            boolean dropped = special.get("dropped") != null && requiredBoolean(file, special, "dropped", label);
            JsonNode acceptedNode = special.get("accepted");
            if (dropped == (acceptedNode != null)) {
                throw new InputFormatException(file, 0, label + ": question " + question
                        + " must be either 'dropped: true' or 'accepted: [..]', not both or neither");
            }
            List<String> accepted = new ArrayList<>();
            if (acceptedNode != null) {
                if (!acceptedNode.isArray()) {
                    throw new InputFormatException(file, 0, label + ": question " + question + " 'accepted' must be a list");
                }
                TreeSet<String> keys = new TreeSet<>();
                for (JsonNode value : acceptedNode) {
                    String option = value.isIntegralNumber() ? String.valueOf(value.intValue()) : value.asString();
                    if (!OPTION_KEYS.contains(option) || !keys.add(option)) {
                        throw new InputFormatException(file, 0, label + ": question " + question
                                + " accepts '" + option + "' — keys are distinct and 1 to 4");
                    }
                }
                if (keys.size() < 2) {
                    throw new InputFormatException(file, 0, label + ": question " + question
                            + " accepts one key — a special entry is for several, or a drop");
                }
                accepted.addAll(keys);
            }
            specials.add(new PaperDefinition.KeySpecial(question, dropped, accepted));
        }
        return specials;
    }

    /**
     * A booklet or a key is a file of the founder's pack, never of the retired {@code source/pyq/}
     * (compilations and publisher keys, README §1 ruling 1) and never the coaching retype the pack
     * keeps for cross-checking (README §5) — the tier label alone would let either through.
     */
    private static String sourceKey(Path file, String value, String label, String what) {
        if (value.contains(REFERENCE_ONLY)) {
            throw new InputFormatException(file, 0, label + ": " + what + " '" + value
                    + "' is the pack's reference-only retype — never loaded, never a source (README §5)");
        }
        if (!SOURCE_KEY.matcher(value).matches()) {
            throw new InputFormatException(file, 0, label + ": " + what + " '" + value + "' must be a .pdf of the "
                    + "founder's pack, under " + PACK + " in the content bucket — compilations and publisher keys are "
                    + "never a source (README §1, §2)");
        }
        return value;
    }

    private static String sha256(Path file, String value, String label, String key) {
        if (!SHA256.matcher(value).matches()) {
            throw new InputFormatException(file, 0, label + ": the sha256 of " + key + " must be 64 lowercase hex digits");
        }
        return value;
    }

    private static LocalDate date(Path file, String value, String label, String key) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new InputFormatException(file, 0, label + ": " + key + " '" + value + "' must be a date like 2025-05-04");
        }
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

    private static boolean requiredBoolean(Path file, JsonNode node, String key, String label) {
        JsonNode value = node.get(key);
        if (value == null || !value.isBoolean()) {
            throw new InputFormatException(file, 0, label + ": '" + key + "' must be true or false");
        }
        return value.booleanValue();
    }

    private static List<String> names(JsonNode object) {
        List<String> names = new ArrayList<>();
        object.propertyNames().forEach(names::add);
        return names;
    }
}
