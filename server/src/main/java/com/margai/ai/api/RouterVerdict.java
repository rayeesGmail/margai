package com.margai.ai.api;

import com.margai.curriculum.api.Subject;
import java.util.Objects;
import java.util.Optional;

/**
 * The difficulty router's output record (TECH_PLAN §4.2: {@code {tier, is_numerical, subject,
 * node_code_guess, answer_type}}). Only {@link RouteDecision#router(RouterVerdict)} consumes it.
 * {@link Optional} components are optional and nullable in the derived schema (DECISIONS D5).
 *
 * @param tier          the model's suggestion, {@code cheap} or {@code reason}
 * @param isNumerical   a numerical question forces {@code reason} whatever the suggestion
 * @param subject       best guess, may be absent
 * @param nodeCodeGuess best-guess syllabus code such as {@code PHY.11.ROT}, may be absent
 * @param answerType    option, numeric or text
 */
public record RouterVerdict(Tier tier, boolean isNumerical, Optional<Subject> subject, Optional<String> nodeCodeGuess,
        AnswerType answerType) {

    public RouterVerdict {
        Objects.requireNonNull(tier, "tier");
        Objects.requireNonNull(answerType, "answerType");
        subject = subject == null ? Optional.empty() : subject;
        nodeCodeGuess = nodeCodeGuess == null ? Optional.empty() : nodeCodeGuess;
        if (tier != Tier.cheap && tier != Tier.reason) {
            throw new IllegalArgumentException("router may suggest cheap or reason, not " + tier);
        }
    }
}
