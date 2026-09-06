package com.margai.ai.api;

import com.margai.curriculum.api.Subject;
import java.util.Objects;

/**
 * The difficulty router's output record (TECH_PLAN §4.2: {@code {tier, is_numerical, subject,
 * node_code_guess, answer_type}}). Only {@link RouteDecision#router(RouterVerdict)} consumes it.
 *
 * @param tier          the model's suggestion, {@code cheap} or {@code reason}
 * @param isNumerical   a numerical question forces {@code reason} whatever the suggestion
 * @param subject       best guess, nullable
 * @param nodeCodeGuess best-guess syllabus code such as {@code PHY.11.ROT}, nullable
 * @param answerType    option, numeric or text
 */
public record RouterVerdict(Tier tier, boolean isNumerical, Subject subject, String nodeCodeGuess,
        AnswerType answerType) {

    public RouterVerdict {
        Objects.requireNonNull(tier, "tier");
        if (tier != Tier.cheap && tier != Tier.reason) {
            throw new IllegalArgumentException("router may suggest cheap or reason, not " + tier);
        }
    }
}
