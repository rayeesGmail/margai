package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Free checks on a transcription's notation, beside the character diff. The first case is real:
 * chapter 7 page 6 of phy11-part1 sets τ in a Symbol font that the text layer renders as a
 * degree sign, and the strongest model copied the layer through — "Where ° is the restoring
 * couple per unit angle of twist" — twice (D15, the Opus run). A degree sign only ever follows a
 * number, so one that does not is layer garbage the model failed to read from the image.
 */
class NotationFlagsTest {

    @Test
    void aDegreeSignThatDoesNotFollowADigitIsFlagged() {
        assertThat(NotationFlags.check("If theta is the angle of twist of the suspended wire, the restoring torque "
                + "is proportional to theta, equal to tau theta. Where ° is the restoring couple per unit angle of "
                + "twist. ° can be measured independently"))
                .hasSize(2)
                .allSatisfy(finding -> assertThat(finding).contains("degree sign").contains("not after a number"));
    }

    @Test
    void aDegreeSignAfterADigitIsNotFlagged() {
        assertThat(NotationFlags.check("The angle between GC and the positive x-axis is 30° and so is the angle "
                + "between GB and the negative x-axis; cos 30 ° appears too.")).isEmpty();
    }

    @Test
    void textWithoutADegreeSignIsNotFlagged() {
        assertThat(NotationFlags.check("The gravitational force is attractive.")).isEmpty();
        assertThat(NotationFlags.check("")).isEmpty();
    }
}
