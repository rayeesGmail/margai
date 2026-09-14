package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

/** The label shapes the chapter-7 runs actually produced, read into one comparable form (D15). */
class FigureLabelsTest {

    @Test
    void thePrintedVariantsOfOneLabelAgree() {
        assertThat(List.of("Fig. 7.1(a)", "Fig. 7.1 (a)", "Fig.7.1a", "fig 7.1a"))
                .allSatisfy(ref -> assertThat(FigureLabels.of(ref)).hasValueSatisfying(label ->
                        assertThat(label).hasToString("fig 7.1a")));
        assertThat(FigureLabels.of("Figure 10.2 b")).hasValueSatisfying(label ->
                assertThat(label).hasToString("fig 10.2"));
        assertThat(FigureLabels.of("Table 7.1")).hasValueSatisfying(label ->
                assertThat(label.base()).isEqualTo("table 7.1"));
    }

    @Test
    void anEquationIsNotALabel() {
        assertThat(FigureLabels.of("Eq. (7.5)")).isEmpty();
    }

    /** "Fig 7.3 a point" is the next word, not part (a). */
    @Test
    void aLetterAfterASpaceIsTheNextWord() {
        assertThat(FigureLabels.in("as shown in Fig. 7.3 a point mass is")).singleElement()
                .hasToString("fig 7.3");
        assertThat(FigureLabels.in("Table 7.1 gives the periods")).singleElement().hasToString("table 7.1");
    }

    @Test
    void everyMentionInATextIsFoundInOrder() {
        assertThat(FigureLabels.in("in Fig. 7.1(a); compare Fig.7.6 and Table 7.1 (see Eq. (7.5))"))
                .extracting(FigureLabels.Label::toString)
                .containsExactly("fig 7.1a", "fig 7.6", "table 7.1");
    }
}
