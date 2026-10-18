package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.NcertParagraphRow;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * The freeze guard's question (DECISIONS 2026-09-13, built at D17): does this load leave the frozen
 * corpus as it is — the English rows and the English text the vectors describe? Every row it would
 * move is named. Hindi is not frozen while nothing embeds it, so a Hindi load is never refused
 * (Claude's reading of the founder's Q2, DECISIONS 2026-10-18).
 */
class FrozenCorpusTest {

    private static final List<AlignmentRow> CURRENT = List.of(
            row(7, "7.9", 1, "Energy of an orbit.", "कक्षा की ऊर्जा।"),
            row(7, "7.9", 2, "Escape speed.", "पलायन चाल।"));

    @Test
    void anIdenticalEnglishLoadChangesNothing() {
        assertThat(FrozenCorpus.changes(BookLanguage.en, CURRENT,
                List.of(load(7, "7.9", 1, "Energy of an orbit."), load(7, "7.9", 2, "Escape speed."))))
                .isEmpty();
    }

    @Test
    void anEnglishRewriteIsNamed() {
        assertThat(FrozenCorpus.changes(BookLanguage.en, CURRENT,
                List.of(load(7, "7.9", 1, "Energy of a satellite."), load(7, "7.9", 2, "Escape speed."))))
                .containsExactly("ch 7 §7.9 ¶1 — its English rewritten");
    }

    @Test
    void aNewRowIsNamed() {
        assertThat(FrozenCorpus.changes(BookLanguage.en, CURRENT, List.of(load(7, "7.9", 1, "Energy of an orbit."),
                load(7, "7.9", 2, "Escape speed."), load(7, "7.9", 3, "A third paragraph."))))
                .containsExactly("ch 7 §7.9 ¶3 — a new row");
    }

    /** A row the Hindi edition also carries keeps its Hindi and loses only its English. */
    @Test
    void anEnglishRowTheLoadNoLongerCarriesIsNamedWhetherDeletedOrStripped() {
        List<AlignmentRow> current = List.of(CURRENT.get(0), CURRENT.get(1), row(7, "7.9", 3, "Alone.", null));

        assertThat(FrozenCorpus.changes(BookLanguage.en, current, List.of(load(7, "7.9", 1, "Energy of an orbit."))))
                .containsExactly("ch 7 §7.9 ¶2 — its English removed (the Hindi edition still carries the row)",
                        "ch 7 §7.9 ¶3 — deleted (no edition carries it)");
    }

    /** The D16 workflow: Hindi corrections rewrite Hindi text on a frozen book, and that stays allowed. */
    @Test
    void aHindiRewriteIsNotAChangeToTheFrozenCorpus() {
        assertThat(FrozenCorpus.changes(BookLanguage.hi, CURRENT,
                List.of(load(7, "7.9", 1, "कक्षा में ऊर्जा।"), load(7, "7.9", 2, "पलायन चाल।"))))
                .isEmpty();
    }

    /** …and so does a Hindi load that no longer carries a row's Hindi: the row keeps its English. */
    @Test
    void aHindiLoadThatDropsARowsHindiLeavesTheSetAsItIs() {
        assertThat(FrozenCorpus.changes(BookLanguage.hi, CURRENT, List.of(load(7, "7.9", 1, "कक्षा की ऊर्जा।"))))
                .isEmpty();
    }

    /**
     * A first Hindi load as written puts Hindi rows at addresses the English lacks — phy11-part1's put 23
     * there (D16) — and nothing embeds them, so it is not a change to the frozen English set.
     */
    @Test
    void aHindiLoadThatAddsAHindiOnlyRowGoesThrough() {
        assertThat(FrozenCorpus.changes(BookLanguage.hi, CURRENT, List.of(load(7, "7.9", 1, "कक्षा की ऊर्जा।"),
                load(7, "7.9", 2, "पलायन चाल।"), load(7, "7.9", 3, "तीसरा।"))))
                .isEmpty();
    }

    /** …and the corrections block that numbers it to the English deletes the Hindi-only row again. */
    @Test
    void aHindiLoadThatDeletesAHindiOnlyRowGoesThrough() {
        List<AlignmentRow> current = List.of(CURRENT.get(0), row(7, "7.9", 2, null, "केवल हिंदी।"));

        assertThat(FrozenCorpus.changes(BookLanguage.hi, current, List.of(load(7, "7.9", 1, "कक्षा की ऊर्जा।"))))
                .isEmpty();
    }

    /** An English load that writes English onto a Hindi-only row adds a row to the English set. */
    @Test
    void anEnglishLoadThatWritesEnglishOntoAHindiOnlyRowIsNamed() {
        List<AlignmentRow> current = List.of(CURRENT.get(0), row(7, "7.9", 2, null, "केवल हिंदी।"));

        assertThat(FrozenCorpus.changes(BookLanguage.en, current,
                List.of(load(7, "7.9", 1, "Energy of an orbit."), load(7, "7.9", 2, "Now in English."))))
                .containsExactly("ch 7 §7.9 ¶2 — English written onto a Hindi-only row");
    }

    /** The importer deletes only within chapters the load carries, so a chapter with no loaded row loses nothing. */
    @Test
    void aStoredChapterTheLoadCarriesNoRowOfIsNotNamed() {
        List<AlignmentRow> current = List.of(CURRENT.get(0), row(8, "8.1", 1, "Another chapter.", null));

        assertThat(FrozenCorpus.changes(BookLanguage.en, current, List.of(load(7, "7.9", 1, "Energy of an orbit."))))
                .isEmpty();
    }

    private static AlignmentRow row(int chapter, String section, int para, String english, String hindi) {
        return new AlignmentRow(UUID.randomUUID(), (short) chapter, section, (short) para, english, hindi,
                List.of(1), List.of(1));
    }

    private static NcertParagraphRow load(int chapter, String section, int para, String text) {
        return new NcertParagraphRow((short) chapter, section, (short) para, text, false, List.of(), null);
    }
}
