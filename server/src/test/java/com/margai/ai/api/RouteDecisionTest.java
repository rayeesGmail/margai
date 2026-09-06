package com.margai.ai.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.Subject;
import org.junit.jupiter.api.Test;

/**
 * TECH_PLAN §4.2, DECISIONS D3.23: three producers of the reasoning-tier ticket, and the rule
 * {@code is_numerical ⇒ reason} lives in {@link RouteDecision#router}, not in the model.
 */
class RouteDecisionTest {

    @Test
    void verificationAndGenerationAlwaysRunOnReason() {
        assertThat(RouteDecision.verification().tier()).isEqualTo(Tier.reason);
        assertThat(RouteDecision.verification().producer()).isEqualTo(RouteDecision.Producer.verification);
        assertThat(RouteDecision.verification().isNumerical()).isTrue();

        assertThat(RouteDecision.generation().tier()).isEqualTo(Tier.reason);
        assertThat(RouteDecision.generation().producer()).isEqualTo(RouteDecision.Producer.generation);
        assertThat(RouteDecision.generation().verdict()).isEmpty();
    }

    @Test
    void routerKeepsACheapVerdictForNonNumericalQuestions() {
        RouterVerdict verdict = new RouterVerdict(Tier.cheap, false, Subject.physics, "PHY.11.KIN", AnswerType.option);

        RouteDecision decision = RouteDecision.router(verdict);

        assertThat(decision.tier()).isEqualTo(Tier.cheap);
        assertThat(decision.producer()).isEqualTo(RouteDecision.Producer.router);
        assertThat(decision.verdict()).contains(verdict);
    }

    @Test
    void numericalQuestionsForceReasonWhateverTheModelSuggested() {
        RouteDecision decision = RouteDecision.router(
                new RouterVerdict(Tier.cheap, true, Subject.physics, "PHY.11.KIN", AnswerType.numeric));

        assertThat(decision.tier()).isEqualTo(Tier.reason);
        assertThat(decision.isNumerical()).isTrue();
    }

    @Test
    void routerMayOnlySuggestCheapOrReason() {
        assertThatThrownBy(() -> new RouterVerdict(Tier.vision, false, null, null, AnswerType.text))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vision");
    }
}
