package com.margai.pipeline.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code ncert align}'s core (TECH_PLAN §6.3, D16): one section's Hindi paragraphs mapped onto its English
 * ones, in order, by how alike each pair is. The English rows are canonical and Hindi follows them (founder,
 * 2026-10-01), so every step says what the Hindi needs: nothing (one to one), a split (one Hindi paragraph
 * carries two English ones, as the Hindi print often keeps them), a join (two carry one), or nothing to pair
 * with on one side. The mapping is a proposal for adjudication and changes no row.
 *
 * <p>A monotone dynamic programme over the two sequences. Every link scores its similarity above what two
 * unrelated paragraphs earn ({@link #STRANGERS}), so a link to a stranger counts against the mapping that
 * makes it: a pair scores its one link, a split or a join its two links less a small price — so it wins only
 * when both links are real — and a paragraph left unpaired costs a little. Position alone never pairs: the
 * load put the Hindi at the English addresses by order, and a single merged paragraph shifts every number
 * after it. The three constants are opening values, to be read against the first real book's similarities.
 */
final class Alignment {

    /** The similarity two unrelated paragraphs of one section can be expected to reach. */
    static final double STRANGERS = 0.35;

    /** What a split or join costs beyond its links, so one-to-one wins a tie. */
    static final double RESHAPE_PRICE = 0.05;

    /** What a paragraph left without a partner costs. */
    static final double UNPAIRED_PRICE = 0.1;

    private Alignment() {
    }

    /** How alike Hindi ¶{@code hi} and English ¶{@code en} are, both numbered from 1 within the section. */
    @FunctionalInterface
    interface Similarity {
        double between(int hi, int en);
    }

    /** One step of a mapping; paragraph numbers from 1 within the section. */
    record Step(Kind kind, List<Integer> hi, List<Integer> en) {

        enum Kind { PAIR, SPLIT_HI, JOIN_HI, EN_ONLY, HI_ONLY }

        @Override
        public String toString() {
            return switch (kind) {
                case PAIR -> "hi ¶" + hi.getFirst() + " = en ¶" + en.getFirst();
                case SPLIT_HI -> "hi ¶" + hi.getFirst() + " = en ¶" + en.get(0) + "+¶" + en.get(1) + " (split hi)";
                case JOIN_HI -> "hi ¶" + hi.get(0) + "+¶" + hi.get(1) + " = en ¶" + en.getFirst() + " (join hi)";
                case EN_ONLY -> "en ¶" + en.getFirst() + " has no hindi";
                case HI_ONLY -> "hi ¶" + hi.getFirst() + " has no english";
            };
        }
    }

    /** The best mapping of {@code hiCount} Hindi paragraphs onto {@code enCount} English ones. */
    static List<Step> map(int hiCount, int enCount, Similarity similarity) {
        double[][] best = new double[hiCount + 1][enCount + 1];
        Step.Kind[][] via = new Step.Kind[hiCount + 1][enCount + 1];
        for (int h = 0; h <= hiCount; h++) {
            for (int e = 0; e <= enCount; e++) {
                if (h == 0 && e == 0) {
                    continue;
                }
                best[h][e] = Double.NEGATIVE_INFINITY;
                if (h >= 1 && e >= 1) {
                    consider(best, via, h, e, best[h - 1][e - 1] + link(similarity, h, e), Step.Kind.PAIR);
                }
                if (h >= 1 && e >= 2) {
                    double links = link(similarity, h, e - 1) + link(similarity, h, e);
                    consider(best, via, h, e, best[h - 1][e - 2] + links - RESHAPE_PRICE, Step.Kind.SPLIT_HI);
                }
                if (h >= 2 && e >= 1) {
                    double links = link(similarity, h - 1, e) + link(similarity, h, e);
                    consider(best, via, h, e, best[h - 2][e - 1] + links - RESHAPE_PRICE, Step.Kind.JOIN_HI);
                }
                if (e >= 1) {
                    consider(best, via, h, e, best[h][e - 1] - UNPAIRED_PRICE, Step.Kind.EN_ONLY);
                }
                if (h >= 1) {
                    consider(best, via, h, e, best[h - 1][e] - UNPAIRED_PRICE, Step.Kind.HI_ONLY);
                }
            }
        }
        List<Step> steps = new ArrayList<>();
        int h = hiCount;
        int e = enCount;
        while (h > 0 || e > 0) {
            Step.Kind kind = via[h][e];
            switch (kind) {
                case PAIR -> steps.add(new Step(kind, List.of(h--), List.of(e--)));
                case SPLIT_HI -> {
                    steps.add(new Step(kind, List.of(h), List.of(e - 1, e)));
                    h -= 1;
                    e -= 2;
                }
                case JOIN_HI -> {
                    steps.add(new Step(kind, List.of(h - 1, h), List.of(e)));
                    h -= 2;
                    e -= 1;
                }
                case EN_ONLY -> steps.add(new Step(kind, List.of(), List.of(e--)));
                case HI_ONLY -> steps.add(new Step(kind, List.of(h--), List.of()));
            }
        }
        Collections.reverse(steps);
        return steps;
    }

    /** One link's worth: its similarity above what strangers reach. */
    private static double link(Similarity similarity, int hi, int en) {
        return similarity.between(hi, en) - STRANGERS;
    }

    private static void consider(double[][] best, Step.Kind[][] via, int h, int e, double score, Step.Kind kind) {
        if (score > best[h][e]) {
            best[h][e] = score;
            via[h][e] = kind;
        }
    }
}
