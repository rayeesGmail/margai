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
 * this chapter is still the frozen corpus — its paragraph set, and the English text the vectors
 * describe — and names every row a load would move. It does not ask whether two runs agree, which
 * they never do and need not.
 *
 * <p>Hindi text is outside it while nothing embeds Hindi: rewriting a row's Hindi touches no vector
 * and moves no row, and is how the D16 corrections land on the frozen books. The founder's ruling
 * (DECISIONS 2026-10-18, Q2) is that if a Hindi vector column is ever added, Hindi text changes are
 * guarded the same way — that change widens this class.
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
        Map<String, AlignmentRow> stored = new LinkedHashMap<>();
        current.forEach(row -> stored.put(row.address(), row));
        Set<String> carried = new HashSet<>();
        List<String> changes = new ArrayList<>();
        for (NcertParagraphRow row : rows) {
            carried.add(row.address());
            AlignmentRow existing = stored.get(row.address());
            if (existing == null) {
                changes.add(row.address() + " — a new row");
            } else if (language == BookLanguage.en && !Objects.equals(existing.textEn(), row.text())) {
                changes.add(row.address() + " — its English rewritten");
            }
        }
        for (AlignmentRow row : stored.values()) {
            if (carried.contains(row.address()) || text(row, language) == null) {
                continue;
            }
            // The row is this edition's and the load no longer carries it: the importer deletes it when
            // no other edition does, and otherwise strips this edition's text from it.
            if (text(row, other(language)) == null) {
                changes.add(row.address() + " — deleted (no edition carries it)");
            } else if (language == BookLanguage.en) {
                changes.add(row.address() + " — its English removed (the Hindi edition still carries the row)");
            }
        }
        return changes;
    }

    private static String text(AlignmentRow row, BookLanguage language) {
        return language == BookLanguage.en ? row.textEn() : row.textHi();
    }

    private static BookLanguage other(BookLanguage language) {
        return language == BookLanguage.en ? BookLanguage.hi : BookLanguage.en;
    }
}
