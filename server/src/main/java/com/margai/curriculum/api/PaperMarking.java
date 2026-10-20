package com.margai.curriculum.api;

/**
 * A paper's marking scheme ({@code pyq_papers.marking}, migration V9). The marks for a correct and
 * an incorrect response come from the booklet's own instructions; the marks for a question left
 * blank where the booklet prints them, and the rules for a dropped question and for one with several
 * accepted answers from NTA's notice for that year, cited — because mock scoring follows the
 * register, never a default (founder's D19 ruling, DECISIONS 2026-10-20). Until a value is sourced
 * it is null, the load report names the paper, and the D35 mock must refuse to score it.
 *
 * @param correct      marks for a correct response, e.g. 4
 * @param wrong        marks for an incorrect response, e.g. -1
 * @param unanswered   marks for a question left blank; null where the booklet does not print them and
 *                     the notice is not yet sourced
 * @param schemeSource where the printed marks are, e.g. "booklet p1, instruction 4"
 * @param droppedRule  NTA's rule for a dropped question, as a short code; null until sourced
 * @param multipleRule NTA's rule for several accepted answers, as a short code; null until sourced
 * @param rulesSource  the notice the rules are read from; null until sourced
 */
public record PaperMarking(
        int correct,
        int wrong,
        Integer unanswered,
        String schemeSource,
        String droppedRule,
        String multipleRule,
        String rulesSource) {

    /** Whether every value a mock needs is sourced — the blank mark and both special-answer rules. */
    public boolean rulesSourced() {
        return unanswered != null && droppedRule != null && multipleRule != null && rulesSource != null;
    }
}
