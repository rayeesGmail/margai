package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The cases are real: every string here is either a paragraph the D14 extraction produced or the
 * page it came from. The one the model got wrong — a law stated entirely in words, flagged as
 * carrying an equation — is the reason this is computed in Java at all.
 */
class EquationsTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "a_m = V^2 / R_m = 4 pi^2 R_m / T^2 [7.3]",
            "| F | = G m_1 m_2 / r^2 [7.5]",
            "g(h) approx= g (1 - 2h / R_E) [7.15]",
            "R_m was already known then to be about 3.84 x 10^8 m.",
            "W(r) = - 4 G m^2 / l - 2 G m^2 / (sqrt(2) l)",
            "2H_2 + O_2 -> 2H_2O (heat)",
            "The reaction N_2 + 3H_2 <-> 2NH_3 reaches equilibrium.",
            "Zinc reacts as Zn(s) + 2HCl(aq) -> ZnCl_2(aq) + H_2(g)",
            "T^2 = k (R_E + h)^3 where k = 4 pi^2 / G M_E [7.38]",
    })
    void anExpressionIsFound(String text) {
        assertThat(Equations.present(text)).as(text).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // The model flagged this one. It is Kepler's third law in words, and holds no expression.
            "3. Law of periods : The square of the time period of revolution of a planet is "
                    + "proportional to the cube of the semi-major axis of the ellipse traced out by the planet.",
            "Legend has it that observing an apple falling from a tree, Newton was inspired to "
                    + "arrive at an universal law of gravitation.",
            "The time period T is about 27.3 days.",
            "The value of the gravitational constant G was first determined by Henry Cavendish in 1798.",
            "Most planets have nearly circular orbits about the Sun, within about 1 per cent.",
            "Photosynthesis converts light energy into chemical energy in the chloroplast.",
    })
    void proseIsNotAnExpression(String text) {
        assertThat(Equations.present(text)).as(text).isFalse();
    }

    @Test
    void nothingIsNotAnExpression() {
        assertThat(Equations.present(null)).isFalse();
        assertThat(Equations.present("   ")).isFalse();
    }
}
