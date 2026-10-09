package com.margai.pipeline.internal;

import com.margai.curriculum.api.NcertParagraphRow;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

/**
 * A book's English rows and a Hindi load's rows side by side, by address (D16: English is canonical and the
 * Hindi follows it, founder 2026-10-01) — what `ncert load` holds a Hindi load to, and what its dry run writes
 * out to be read before a reload: the per-section counts, and the pairs.
 */
final class EditionPairs {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    /** Chapter, then the printed section compared number by number (7.2 before 7.10). */
    private static final Comparator<Key> ORDER = Comparator.comparing(Key::chapter)
            .thenComparing(Key::section, EditionPairs::compareSections);

    /** One section's row counts in each edition. */
    record Section(short chapter, String section, int english, int hindi) {

        boolean oneToOne() {
            return english == hindi;
        }
    }

    private record Key(short chapter, String section) {
    }

    private EditionPairs() {
    }

    /** Every section either edition has rows in, in reading order. */
    static List<Section> sections(List<NcertParagraphRow> english, List<NcertParagraphRow> hindi) {
        Map<Key, int[]> counts = new TreeMap<>(ORDER);
        english.forEach(row -> counts.computeIfAbsent(key(row), key -> new int[2])[0]++);
        hindi.forEach(row -> counts.computeIfAbsent(key(row), key -> new int[2])[1]++);
        List<Section> sections = new ArrayList<>();
        counts.forEach((key, count) -> sections.add(new Section(key.chapter(), key.section(), count[0], count[1])));
        return sections;
    }

    /**
     * One JSON line per address either edition has, in reading order: the address, the English text, the Hindi
     * text and the Hindi row's pages — absent where an edition has no row there.
     */
    static byte[] jsonl(List<NcertParagraphRow> english, List<NcertParagraphRow> hindi) {
        Map<Key, Map<Short, NcertParagraphRow[]>> byAddress = new TreeMap<>(ORDER);
        english.forEach(row -> at(byAddress, row)[0] = row);
        hindi.forEach(row -> at(byAddress, row)[1] = row);
        StringBuilder out = new StringBuilder();
        byAddress.forEach((key, paragraphs) -> paragraphs.forEach((paraNo, pair) -> {
            ObjectNode line = JSON.createObjectNode();
            line.put("address", "ch " + key.chapter() + " §" + key.section() + " ¶" + paraNo);
            line.put("chapter", key.chapter());
            line.put("section", key.section());
            line.put("para", paraNo);
            if (pair[0] != null) {
                line.put("en", pair[0].text());
            }
            if (pair[1] != null) {
                line.put("hi", pair[1].text());
                if (pair[1].extraction() != null && pair[1].extraction().pages() != null) {
                    pair[1].extraction().pages().forEach(line.putArray("hi_pages")::add);
                }
            }
            out.append(JSON.writeValueAsString(line)).append('\n');
        }));
        return out.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static NcertParagraphRow[] at(Map<Key, Map<Short, NcertParagraphRow[]>> byAddress, NcertParagraphRow row) {
        return byAddress.computeIfAbsent(key(row), key -> new TreeMap<>())
                .computeIfAbsent(row.paraNo(), paraNo -> new NcertParagraphRow[2]);
    }

    private static Key key(NcertParagraphRow row) {
        return new Key(row.chapterNo(), row.section());
    }

    private static int compareSections(String left, String right) {
        String[] a = left.split("\\.");
        String[] b = right.split("\\.");
        for (int index = 0; index < Math.min(a.length, b.length); index++) {
            int compared = Integer.compare(Integer.parseInt(a[index]), Integer.parseInt(b[index]));
            if (compared != 0) {
                return compared;
            }
        }
        return Integer.compare(a.length, b.length);
    }
}
