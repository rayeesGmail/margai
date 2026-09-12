package com.margai.pipeline.internal;

import com.margai.common.api.AttemptType;
import com.margai.curriculum.api.ArchetypeStepRow;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.TrackPhase;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * {@code archetypes.yaml} (TECH_PLAN §6.2) into {@link ArchetypeTrackRow}s: a {@code tracks}
 * list, each track with {@code code, name_en, name_hi, weeks, description_md, steps}, each step
 * with {@code sequence, node_code, phase, target_week}. Unknown keys are refused (a misspelt key
 * would otherwise be silently ignored), sequences are unique within a track, target weeks lie
 * inside the track, track codes are unique. YAML has no useful line numbers after parsing, so
 * errors name the track and the sequence instead.
 */
final class ArchetypesYamlReader {

    static final Set<String> TRACK_KEYS = Set.of("code", "name_en", "name_hi", "weeks", "description_md", "steps");
    static final Set<String> STEP_KEYS = Set.of("sequence", "node_code", "phase", "target_week");
    static final int NAME_MAX_LENGTH = 160;

    private static final YAMLMapper YAML = YAMLMapper.builder().build();

    private ArchetypesYamlReader() {
    }

    static List<ArchetypeTrackRow> read(Path file) {
        JsonNode root;
        try (InputStream in = Files.newInputStream(file)) {
            root = YAML.readTree(in);
        } catch (IOException e) {
            throw new InputFormatException(file, 0, "cannot be read: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InputFormatException(file, 0, "is not valid YAML: " + e.getMessage());
        }
        if (root == null || !root.isObject()) {
            throw new InputFormatException(file, 0, "must be a mapping with a 'tracks' list");
        }
        for (String key : names(root)) {
            if (!key.equals("tracks")) {
                throw new InputFormatException(file, 0, "unknown top-level key '" + key + "'");
            }
        }
        JsonNode tracks = root.get("tracks");
        if (tracks == null || !tracks.isArray() || tracks.isEmpty()) {
            throw new InputFormatException(file, 0, "'tracks' must be a non-empty list");
        }
        List<ArchetypeTrackRow> rows = new ArrayList<>();
        Set<AttemptType> codes = new HashSet<>();
        for (JsonNode track : tracks) {
            ArchetypeTrackRow row = track(file, track);
            if (!codes.add(row.code())) {
                throw new InputFormatException(file, 0, "track '" + row.code() + "' appears twice");
            }
            rows.add(row);
        }
        return rows;
    }

    private static ArchetypeTrackRow track(Path file, JsonNode track) {
        if (!track.isObject()) {
            throw new InputFormatException(file, 0, "every track must be a mapping");
        }
        String label = "track " + (track.get("code") == null ? "?" : track.get("code").asString());
        for (String key : names(track)) {
            if (!TRACK_KEYS.contains(key)) {
                throw new InputFormatException(file, 0, label + ": unknown key '" + key + "'");
            }
        }
        AttemptType code = Enums.parse(requiredString(file, track, "code", label), AttemptType.class,
                () -> new InputFormatException(file, 0, label + ": code must be one of "
                        + Arrays.toString(AttemptType.values())));
        String nameEn = requiredString(file, track, "name_en", label);
        if (nameEn.length() > NAME_MAX_LENGTH) {
            throw new InputFormatException(file, 0, label + ": name_en is longer than " + NAME_MAX_LENGTH);
        }
        String nameHi = optionalString(file, track, "name_hi", label);
        if (nameHi != null && nameHi.length() > NAME_MAX_LENGTH) {
            throw new InputFormatException(file, 0, label + ": name_hi is longer than " + NAME_MAX_LENGTH);
        }
        short weeks = (short) requiredInt(file, track, "weeks", 1, Short.MAX_VALUE, label);
        String description = optionalString(file, track, "description_md", label);
        JsonNode steps = track.get("steps");
        if (steps == null || !steps.isArray() || steps.isEmpty()) {
            throw new InputFormatException(file, 0, label + ": 'steps' must be a non-empty list");
        }
        List<ArchetypeStepRow> stepRows = new ArrayList<>();
        Set<Integer> sequences = new HashSet<>();
        for (JsonNode step : steps) {
            ArchetypeStepRow stepRow = step(file, step, weeks, label);
            if (!sequences.add(stepRow.sequence())) {
                throw new InputFormatException(file, 0, label + ": sequence " + stepRow.sequence() + " appears twice");
            }
            stepRows.add(stepRow);
        }
        return new ArchetypeTrackRow(code, nameEn, nameHi, weeks, description, stepRows);
    }

    private static ArchetypeStepRow step(Path file, JsonNode step, short weeks, String track) {
        if (!step.isObject()) {
            throw new InputFormatException(file, 0, track + ": every step must be a mapping");
        }
        for (String key : names(step)) {
            if (!STEP_KEYS.contains(key)) {
                throw new InputFormatException(file, 0, track + ": unknown step key '" + key + "'");
            }
        }
        int sequence = requiredInt(file, step, "sequence", 1, Integer.MAX_VALUE, track);
        String label = track + " step " + sequence;
        String nodeCode = requiredString(file, step, "node_code", label);
        if (!CsvInput.CODE.matcher(nodeCode).matches() || nodeCode.length() > CsvInput.CODE_MAX_LENGTH) {
            throw new InputFormatException(file, 0, label + ": node_code '" + nodeCode + "' is not a node code");
        }
        TrackPhase phase = Enums.parse(requiredString(file, step, "phase", label), TrackPhase.class,
                () -> new InputFormatException(file, 0, label + ": phase must be one of " + Arrays.toString(TrackPhase.values())));
        short targetWeek = (short) requiredInt(file, step, "target_week", 1, weeks, label);
        return new ArchetypeStepRow(sequence, nodeCode, phase, targetWeek);
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
