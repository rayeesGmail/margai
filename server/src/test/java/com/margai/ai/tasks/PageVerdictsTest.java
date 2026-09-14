package com.margai.ai.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The verifier's output held to what a verdict means (D15): a verdict of {@code differs} names where,
 * and every other verdict names nothing. A shape that breaks this is refused where the output is
 * decoded, so the schema layer's one repair call answers it rather than the report.
 */
class PageVerdictsTest {

    @Test
    void aDifferenceIsNamedByBothItsSpans() {
        PageVerdicts.ItemVerdict verdict = new PageVerdicts.ItemVerdict(3, PageVerdicts.Verdict.differs,
                List.of(new PageVerdicts.Difference("|r|^3 r where", "|r|^3 r_hat where")));

        assertThat(verdict.differences()).singleElement()
                .extracting(PageVerdicts.Difference::printed).isEqualTo("|r|^3 r where");
    }

    @Test
    void differsWithoutADifferenceIsRefused() {
        assertThatThrownBy(() -> new PageVerdicts.ItemVerdict(3, PageVerdicts.Verdict.differs, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("item 3 is 'differs' but names no difference");
    }

    @Test
    void aVerdictThatIsNotDiffersNamesNoDifference() {
        assertThatThrownBy(() -> new PageVerdicts.ItemVerdict(2, PageVerdicts.Verdict.matches,
                List.of(new PageVerdicts.Difference("a", "b"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("item 2 is 'matches' and must name no difference");
    }

    /** A span the pipeline must find in the row cannot be empty, and two equal spans are not a difference. */
    @Test
    void aBlankOrIdenticalSpanIsRefused() {
        assertThatThrownBy(() -> new PageVerdicts.Difference("r", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("both spans must quote text");
        assertThatThrownBy(() -> new PageVerdicts.Difference("G m", "G m"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the printed and transcribed spans are identical");
    }

    @Test
    void absentListsAreEmpty() {
        PageVerdicts verdicts = new PageVerdicts(null, null);

        assertThat(verdicts.items()).isEmpty();
        assertThat(verdicts.omitted()).isEmpty();
    }
}
