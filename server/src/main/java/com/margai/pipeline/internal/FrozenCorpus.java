package com.margai.pipeline.internal;

import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertParagraphRow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The freeze guard's question (DECISIONS 2026-09-13; built at D17): what a load would change in a
 * frozen book's corpus. One verified run per book is canonical and frozen, and a later extraction is
 * a migration that re-embeds and re-anchors what it moved, not a re-run; so the guard asks whether
 * this chapter is still the frozen corpus — its English rows, and the English text the vectors
 * describe — and names every row a load would move. It does not ask whether two runs agree, which
 * they never do and need not.
 *
 * <p>What is frozen is the English set: the rows that carry English text, which are the rows with
 * vectors. A Hindi load cannot change it — it writes or clears Hindi on rows that keep their English,
 * and adds or deletes only Hindi-only rows, none of which carries a vector — so a Hindi load is never
 * refused (Claude's reading of the founder's Q2, DECISIONS 2026-10-18). That is how every D16 Hindi book landed (a first load as written puts Hindi rows at
 * addresses the English lacks, and the corrections block deletes them again), and how the four
 * Chemistry Hindi books will land in the beta period. The founder's ruling (DECISIONS 2026-10-18, Q2)
 * is that if a Hindi vector column is ever added, Hindi text changes are guarded the same way — that
 * change widens this class.
 */
final class FrozenCorpus {

    private FrozenCorpus() {
    }

    /**
     * The rows a load of {@code rows} in {@code language} would move, in reading order of the load
     * and then of the stored rows, each named with what would happen to it; empty when it leaves the
     * frozen corpus as it is.
     *
     * @param current every stored row of the chapters the load carries, both editions' text
     */
    static List<String> changes(BookLanguage language, List<AlignmentRow> current, List<NcertParagraphRow> rows) {
        if (language != BookLanguage.en) {
            return List.of();
        }
        Map<String, AlignmentRow> stored = new LinkedHashMap<>();
        current.forEach(row -> stored.put(row.address(), row));
        Set<String> carried = new HashSet<>();
        Set<Short> loadedChapters = new HashSet<>();
        List<String> changes = new ArrayList<>();
        for (NcertParagraphRow row : rows) {
            carried.add(row.address());
            loadedChapters.add(row.chapterNo());
            AlignmentRow existing = stored.get(row.address());
            if (existing == null) {
                changes.add(row.address() + " — a new row");
            } else if (existing.textEn() == null) {
                changes.add(row.address() + " — English written onto a Hindi-only row");
            } else if (!Objects.equals(existing.textEn(), row.text())) {
                changes.add(row.address() + " — its English rewritten");
            }
        }
        for (AlignmentRow row : stored.values()) {
            // The importer deletes and strips only within the chapters the load carries a row of, so a
            // selected chapter the extraction has no page for loses nothing and is not named.
            if (!loadedChapters.contains(row.chapterNo()) || carried.contains(row.address()) || row.textEn() == null) {
                continue;
            }
            // An English row the load no longer carries: the importer deletes it when the Hindi edition
            // does not carry it either, and otherwise strips its English.
            changes.add(row.address() + (row.textHi() == null ? " — deleted (no edition carries it)"
                    : " — its English removed (the Hindi edition still carries the row)"));
        }
        return changes;
    }
}
