package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * {@code ncert align} (TECH_PLAN §6.3, D16): one section's Hindi paragraphs mapped onto its English ones in
 * order. English is canonical (founder, 2026-10-01), so the mapping names what the Hindi needs — a split
 * where one Hindi paragraph carries two English ones, a join where two carry one — and never the reverse.
 */
class AlignmentTest {

    /** Similarity by (hindi ¶, english ¶); anything unnamed is a stranger's 0.2. */
    private static Alignment.Similarity table(Map<String, Double> pairs) {
        return (hi, en) -> pairs.getOrDefault(hi + ":" + en, 0.2);
    }

    @Test
    void equalCountsThatMatchPairOneToOne() {
        Alignment.Similarity sims = table(Map.of("1:1", 0.8, "2:2", 0.75, "3:3", 0.7));

        List<Alignment.Step> steps = Alignment.map(3, 3, sims);

        assertThat(steps).extracting(Object::toString).containsExactly("hi ¶1 = en ¶1", "hi ¶2 = en ¶2", "hi ¶3 = en ¶3");
    }

    /** The Hindi print often keeps two English paragraphs in one: that Hindi row needs a split. */
    @Test
    void oneHindiParagraphCarryingTwoEnglishOnesNeedsASplit() {
        Alignment.Similarity sims = table(Map.of("1:1", 0.8, "2:2", 0.7, "2:3", 0.72, "3:4", 0.8));

        List<Alignment.Step> steps = Alignment.map(3, 4, sims);

        assertThat(steps).extracting(Object::toString)
                .containsExactly("hi ¶1 = en ¶1", "hi ¶2 = en ¶2+¶3 (split hi)", "hi ¶3 = en ¶4");
    }

    @Test
    void twoHindiParagraphsCarryingOneEnglishOneNeedAJoin() {
        Alignment.Similarity sims = table(Map.of("1:1", 0.8, "2:2", 0.7, "3:2", 0.68, "4:3", 0.8));

        List<Alignment.Step> steps = Alignment.map(4, 3, sims);

        assertThat(steps).extracting(Object::toString)
                .containsExactly("hi ¶1 = en ¶1", "hi ¶2+¶3 = en ¶2 (join hi)", "hi ¶4 = en ¶3");
    }

    /** A shift — the Hindi rows one place behind — is not paired by position when the content says otherwise. */
    @Test
    void positionAloneDoesNotPairShiftedParagraphs() {
        Alignment.Similarity sims = table(Map.of("1:1", 0.8, "2:3", 0.8, "3:4", 0.8));

        List<Alignment.Step> steps = Alignment.map(3, 4, sims);

        assertThat(steps).extracting(Object::toString)
                .containsExactly("hi ¶1 = en ¶1", "en ¶2 has no hindi", "hi ¶2 = en ¶3", "hi ¶3 = en ¶4");
    }

    @Test
    void anEmptySideMapsToNothingButSkips() {
        assertThat(Alignment.map(0, 2, table(Map.of()))).extracting(Object::toString)
                .containsExactly("en ¶1 has no hindi", "en ¶2 has no hindi");
        assertThat(Alignment.map(1, 0, table(Map.of()))).extracting(Object::toString)
                .containsExactly("hi ¶1 has no english");
    }
}
