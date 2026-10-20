package com.margai.curriculum.api;

/**
 * A paper's marking scheme ({@code pyq_papers.marking}, migration V9). The marks per response come
 * from the booklet's own instructions; the rules for a dropped question and for one with several
 * accepted answers come from NTA's notice for that year, cited, because they changed between years
 * and mock scoring follows the register, never a default (founder's D19 ruling, DECISIONS
 * 2026-10-20). Until a year's notice is sourced its two rules are null, the load report names the
 * paper, and the D35 mock must refuse to score it.
 *
 * @param correct      marks for a correct response, e.g. 4
 * @param wrong        marks for an incorrect response, e.g. -1
 * @param unanswered   marks for a question left blank, e.g. 0
 * @param schemeSource where the three marks are printed, e.g. "booklet p1, instruction 4"
 * @param droppedRule  NTA's rule for a dropped question, as a short code; null until sourced
 * @param multipleRule NTA's rule for several accepted answers, as a short code; null until sourced
 * @param rulesSource  the notice both rules are read from; null until sourced
 */
public record PaperMarking(
        int correct,
        int wrong,
        int unanswered,
        String schemeSource,
        String droppedRule,
        String multipleRule,
        String rulesSource) {

    /** Whether both of NTA's special-answer rules are sourced, so a mock may score the paper. */
    public boolean rulesSourced() {
        return droppedRule != null && multipleRule != null && rulesSource != null;
    }
}
