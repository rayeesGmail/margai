package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * The cases are the real D14 defects and the real text of the pages they came from. Every one of
 * these took a human reading a rendered page against a database row to find; each should now cost
 * a glance at a report line.
 */
class TranscriptionDiffTest {

    @Test
    void aFaithfulTranscriptionFlagsNothing() {
        String page = "The time period T is about 27.3 days and R m was already known then to be "
                + "about 3.84 × 108 m.";
        String said = "The time period T is about 27.3 days and R_m was already known then to be "
                + "about 3.84 x 10^8 m.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    /** The whole point: our own notation must not read as divergence. */
    @Test
    void ourNotationIsNotADivergence() {
        String page = "T = 2π √(l/g) and the angle θ is small, with ΔH negative.";
        String said = "T = 2 pi sqrt(l / g) and the angle theta is small, with Delta H negative.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    @Test
    void aDigitReadAsALetterIsFlagged() {
        String page = "F GA = Gm(2m) / 1 j and the individual forces in vector notation are these";
        String said = "F_GA = Gm(2m) / l j_hat and the individual forces in vector notation are these";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("'l'"));
    }

    @Test
    void aMisreadSubscriptSymbolIsFlagged() {
        String page = "The magnitude of the angular momentum at P is L p = m p r p v p, since "
                + "inspection tells us that they are mutually perpendicular";
        String said = "The magnitude of the angular momentum at P is L_p = m_r r_p v_p, since "
                + "inspection tells us that they are mutually perpendicular";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("'r'"));
    }

    @Test
    void aWrongExponentIsFlagged() {
        String page = "where R MS is the Mars-Sun distance and R ES is the Earth-Sun distance. "
                + "Therefore T M = (1.52)3/2 × 365 = 684 days";
        String said = "where R_MS is the Mars-Sun distance and R_ES is the Earth-Sun distance. "
                + "Therefore T_M = (1.52)^(1/3) x 365 = 684 days";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("digit '1'"));
    }

    @Test
    void aDroppedPrimeIsFlagged() {
        String page = "Now if the mass at vertex A is doubled then F'GB = FGB and F'GC = FGC "
                + "follows immediately from the symmetry of the arrangement";
        String said = "Now if the mass at vertex A is doubled then F_GB = F_GB and F_GC = F_GC "
                + "follows immediately from the symmetry of the arrangement";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("prime"));
    }

    @Test
    void anInventedWordIsFlagged() {
        String page = "Once again Kepler's third law comes to our aid for the martian year, "
                + "which follows from the relation between the periods and the radii";
        String said = "Once again Kepler's third law comes to our aid. For the moon, the year "
                + "follows from the relation between the periods and the radii";

        assertThat(TranscriptionDiff.check(page, said))
                .anySatisfy(finding -> assertThat(finding).contains("'moon'"));
    }

    @Test
    void textTheModelDeliberatelySkippedIsNotADivergence() {
        String page = "GRAVITATION 139 Fig. 7.9 The gravitational potential energy of a body. "
                + "SUMMARY The gravitational force between two particles is central and attractive.";
        String said = "The gravitational force between two particles is central and attractive.";

        assertThat(TranscriptionDiff.check(page, said)).isEmpty();
    }

    @Test
    void nothingToCompareAgainstIsNotADivergence() {
        assertThat(TranscriptionDiff.check(null, "some text")).isEmpty();
        assertThat(TranscriptionDiff.check("", "some text")).isEmpty();
        assertThat(TranscriptionDiff.check("a page", "  ")).isEmpty();
    }
}
