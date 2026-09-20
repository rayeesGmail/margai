package com.margai.ai.retrieval;

import com.margai.curriculum.api.ParagraphMatch;
import java.util.List;

/**
 * What one retrieval returned (TECH_PLAN §4.3 stage 6): the fused passages, best first, and the
 * two facts about <em>how</em> they were found that the caller must not have to guess.
 *
 * @param passages          the fused, deduplicated, token-capped passages, best first
 * @param nodeFilterDropped the node filter found nothing above the floor and was retried without
 *                          it — the guessed node was wrong, and a caller that reasons about the
 *                          node should know before it trusts one
 * @param grounded          at least one passage cleared the similarity floor. False is a
 *                          <strong>grounding failure</strong>: SPEC's R2 rule and CLAUDE.md's hard
 *                          rule both say there is no AI answer without retrieval grounding, so the
 *                          caller owes the student an honest fallback and the audit queue a row —
 *                          never an answer from an empty evidence set
 */
public record RetrievedPassages(List<Passage> passages, boolean nodeFilterDropped, boolean grounded) {

    public RetrievedPassages {
        passages = List.copyOf(passages);
    }

    public static RetrievedPassages groundingFailure(boolean nodeFilterDropped) {
        return new RetrievedPassages(List.of(), nodeFilterDropped, false);
    }

    /**
     * One passage and its provenance. The two ranks are the diagnostic the D15 spike is for: a
     * passage only the vector half found, or only the text half, says which kind of question each
     * half is carrying — embeddings are expected to miss "Eq. (7.35)" and keywords to miss
     * "pushed outward on a turn", and a hybrid that is really only ever using one half should be
     * visible rather than inferred.
     *
     * @param vectorRank 1-based rank in the vector half, or null when that half did not find it
     * @param textRank   1-based rank in the full-text half, or null when that half did not find it
     * @param similarity cosine similarity where the vector half found it, else null — this is what
     *                   the floor is applied to
     */
    public record Passage(
            ParagraphMatch paragraph,
            double fusedScore,
            Integer vectorRank,
            Integer textRank,
            Double similarity) {

        /** Which halves found it, for the run report: {@code both}, {@code vector} or {@code text}. */
        public String foundBy() {
            if (vectorRank != null && textRank != null) {
                return "both";
            }
            return vectorRank != null ? "vector" : "text";
        }
    }
}
